package com.beagledata.hubble.hive2hubble;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		// 新建一个词法分析器，处理输入的CharStream
        HplsqlLexer lexer = new HplsqlLexer(CharStreams.fromString("Create table test as select * from t1"));

        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //新建一个语法分析器，处理词法符号缓冲区中的内容
        HplsqlParser parser = new HplsqlParser(tokens);

        //针对program规则，开始语法分析
        ParseTree tree = parser.program(); 

		ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
		
	}
	
}
