package com.beagledata.hubble.hive2hubble.rewriter;

import com.beagledata.hubble.hive2hubble.HplsqlBaseListener;
import com.beagledata.hubble.hive2hubble.HplsqlParser;
import com.beagledata.hubble.hive2hubble.HplsqlParser.Create_table_stmtContext;
import com.beagledata.hubble.hive2hubble.HplsqlParser.Subselect_stmtContext;
import com.beagledata.hubble.hive2hubble.HplsqlParser.Use_stmtContext;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.io.IOException;

import static com.beagledata.hubble.hive2hubble.rewriter.FunctionConvert.CAN_NOT_CONVERT_FUNCTIONS;

public class HiveRewriteListener extends HplsqlBaseListener {

    public TokenStreamRewriter rewriter;

    private static String CATALOG_NAME = "HIVE.";
    public HiveRewriteListener(TokenStream tokens) {
        this.rewriter = new TokenStreamRewriter(tokens);
    }

    /**
     * Sub Select语法
     */
    @Override
	public void enterSubselect_stmt(Subselect_stmtContext ctx) {
    	
        //对于有database的增加catalogname
        if (ctx.from_clause().from_table_clause().from_table_name_clause().table_name() != null) {
            if (ctx.from_clause().from_table_clause().from_table_name_clause().table_name().ident().L_ID().size() > 1) {
                rewriter.insertBefore(ctx.from_clause().from_table_clause().from_table_name_clause().table_name().ident().start,CATALOG_NAME);
            }
        }
    }
    
    /**
     * USE语法转换
     * 如果使用了use关键字，转换Hubble语法时，需要补齐catalogs名称:hive
     */
    @Override
	public void enterUse_stmt(Use_stmtContext ctx) {
        rewriter.insertBefore(ctx.expr().expr_atom().ident().start, CATALOG_NAME);
	}
    
    /**
     * Create table语法
     */
	@Override
	public void enterCreate_table_stmt(Create_table_stmtContext ctx) {

		//对于有database的增加catalogname
        if (ctx.table_name() != null) {
            if (ctx.table_name().ident().L_ID().size() > 1) {
                rewriter.insertBefore(ctx.table_name().ident().start, CATALOG_NAME);
            }
        }
	}
    
    @Override
    public void enterInsert_stmt(HplsqlParser.Insert_stmtContext ctx) {
        if (ctx.T_TABLE() != null) {
            rewriter.delete(ctx.T_TABLE().getSymbol());
        }
        //对于有database的增加catalogname
        if (ctx.table_name() != null) {
            if (ctx.table_name().ident().L_ID().size() > 1) {
                rewriter.insertBefore(ctx.table_name().ident().start,CATALOG_NAME);
            }
        }
    }

    @Override
    public void enterCreate_database_stmt(HplsqlParser.Create_database_stmtContext ctx) {
        if(ctx.expr().expr_atom().ident()!=null&&ctx.expr().expr_atom().ident().L_ID().size()==1){
            rewriter.insertBefore(ctx.expr().expr_atom().ident().start,CATALOG_NAME);
        }
    }

    @Override
    public void enterExpr(HplsqlParser.ExprContext ctx) {
        if(ctx.T_DIV()!=null){
            if(ctx.expr().size()==2){
                rewriter.replace(ctx.expr(0).start,ctx.expr(0).stop,"CAST("+ctx.expr(0).getText()+" AS DOUBLE)");
            }
        }
    }

    @Override
    public void enterDtype(HplsqlParser.DtypeContext ctx) {
        //将string类型转化成varchar
        if(ctx.T_STRING()!=null){
            rewriter.replace(ctx.T_STRING().getSymbol(),"VARCHAR");
        }
        //TODO 其他语法不一致的待转换
    }

    @Override
    public void enterBool_expr(HplsqlParser.Bool_exprContext ctx) {
        //将regexp改写成regexp_like(ident,expr)=true
        if(ctx.bool_expr_atom().bool_expr_binary().bool_expr_binary_operator().T_REGEXP()!=null){
            String replaceSQL = "regexp_like";
            if(ctx.bool_expr_atom().bool_expr_binary()!=null&&ctx.bool_expr_atom().bool_expr_binary().expr().size()>1){
                if(ctx.bool_expr_atom().bool_expr_binary().expr().size()==2){
                    replaceSQL=replaceSQL+"("+ctx.bool_expr_atom().bool_expr_binary().expr(0).getText()+","+ctx.bool_expr_atom().bool_expr_binary().expr(1).getText()+")=true";
                }
                rewriter.replace(ctx.bool_expr_atom().start,ctx.bool_expr_atom().stop,replaceSQL);
            }

        }
    }

    /**
     * 函数替换入口
     */
    @Override
    public void enterExpr_func(HplsqlParser.Expr_funcContext ctx) {
        //做function替换
        //特殊的函数rand/random，如果包含参数时，实际上转化的与原来的含义不同，在hive中是seed,在hubble中是最大值
        if (ctx.ident() != null) {
            try {
                if(ctx.ident().getText().equalsIgnoreCase("rand")||ctx.ident().getText().equalsIgnoreCase("random")){
                    if(ctx.expr_func_params().func_param().size()>0){
                        //转换时记录差异
                        CAN_NOT_CONVERT_FUNCTIONS.add(ctx.ident().getText()+"(with seed)");
                        rewriter.replace(ctx.ident().start, ctx.ident().stop, new FunctionConvert().hiveFuncToHubbleFunc("random"));
                    }
                }else if(ctx.ident().getText().equalsIgnoreCase("bin")){
                    //如果是bin函数,转换bin(x) => to_base(x,2)
                    rewriter.replace(ctx.ident().start,ctx.ident().stop,"TO_BASE");
                    rewriter.insertAfter(ctx.expr_func_params().stop,",2");
                }
//                else if(ctx.ident().getText().equalsIgnoreCase("hex")){
                    //如果是hex,unhex函数,只能转换bigint的  同样还有conv
//                    rewriter.replace(ctx.ident().start,ctx.ident().stop,"TO_BASE");
//                    rewriter.replace(ctx.expr_func_params().start,ctx.expr_func_params().stop,ctx.expr_func_params().depth()",16");
//                }
                else
                    rewriter.replace(ctx.ident().start, ctx.ident().stop, new FunctionConvert().hiveFuncToHubbleFunc(ctx.ident().getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
