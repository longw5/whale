package com.beagledata.hubble.hive2hubble;

import com.beagledata.hubble.config.ConfigurationSourceProvider;
import com.beagledata.hubble.config.ResourceConfigurationSourceProvider;
import com.beagledata.hubble.hive2hubble.rewriter.HiveRewriteListener;
import com.beagledata.hubble.util.SqlTrimBlankUtil;
import com.google.common.base.Joiner;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.beagledata.hubble.hive2hubble.rewriter.FunctionConvert.CAN_NOT_CONVERT_FUNCTIONS;

/**
 * 利用rewrite的方式
 * 重写输入流
 * 来重构hive语法为hubble语法
 *
 * 对于库表怎么改写成catalog.库.表无法实现，原因是，库.表与别名.字段拥有同样的语法。
 * 所以只能转化部分这种情况
 */
public class ConvertHiveSQLWithRewrite {

	//reader读取
    public static String getHplsqlListenerFromReader(InputStreamReader inputStream) throws IOException {
        return getHplsqlListener(CharStreams.fromReader(inputStream));
    }

    //流数据读取
    public static String getHplsqlListenerFromStream(InputStream inputStream) throws IOException {
        return getHplsqlListener(CharStreams.fromStream(inputStream));
    }

    //字符串读取
    public static String getHplsqlListenerFromSQL(String sql) throws IOException {
        return getHplsqlListener(CharStreams.fromString(sql));
    }

    /**
     * 输出打印解析树
     * @param SQL
     * @return
     */
    public static String printSQLParser(String SQL){
        // 新建一个词法分析器，处理输入的CharStream
        HplsqlLexer lexer = new HplsqlLexer(new CaseInsensitiveStream(CharStreams.fromString(SQL)));

        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //新建一个语法分析器，处理词法符号缓冲区中的内容
        HplsqlParser parser = new HplsqlParser(tokens);

        ParseTree tree = parser.program(); //针对program规则，开始语法分析

        return tree.toStringTree(parser);
    }

    /**
     * 执行sql语法转换
     * @param inputStream
     * @return
     */
    public static String getHplsqlListener(CharStream inputStream){
    	
        // 新建一个词法分析器，处理输入的CharStream
        HplsqlLexer lexer = new HplsqlLexer(new CaseInsensitiveStream(inputStream));

        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //新建一个语法分析器，处理词法符号缓冲区中的内容
        HplsqlParser parser = new HplsqlParser(tokens);

        //针对program规则，开始语法分析
        ParseTree tree = parser.program(); 

        ParseTreeWalker walker = new ParseTreeWalker();

        //SQL语法匹配
        HiveRewriteListener sqlExtrator = new HiveRewriteListener(tokens);

        //SQL转换
        walker.walk(sqlExtrator, tree);

        return sqlExtrator.rewriter.getText();
    }
    
    public static void main(String[] args) throws IOException {
        ConfigurationSourceProvider sourceProvider = new ResourceConfigurationSourceProvider();
        // 新建一个 CharStream，从标准输入读取数据
        String hubbleSQL = getHplsqlListenerFromReader(new InputStreamReader(sourceProvider.open("test2.sql")));

        System.out.println(hubbleSQL);

        System.out.println("需要手工转换的函数如下:");
        System.out.println(Joiner.on(",").join(CAN_NOT_CONVERT_FUNCTIONS));
    }
}
