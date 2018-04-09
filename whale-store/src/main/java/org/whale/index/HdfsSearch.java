package org.whale.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.solr.store.hdfs.HdfsDirectory;

public class HdfsSearch {

	static Map<String, String> info = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {
		
		//搜索
		searchIndex();
	}
	
	public static void searchIndex() throws Exception {
		
		Configuration conf = new Configuration();

		conf.set("fs.defaultFS", "hdfs://hadoop:8020");
		
        FileSystem fs = FileSystem.get(conf);
        
        Path indexPath = new Path("/lucene/nginx");
        if (!fs.exists(indexPath)) {
            fs.mkdirs(indexPath);
        }
        
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPath, conf);
		
		Analyzer analyzer = new StandardAnalyzer();
		
		String keyWord = "1E7016840F83DBC09679F5AE43D962ECAD03719B";

		DirectoryReader directoryReader = DirectoryReader.open(hdfsDirectory);

		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		String[] fields = { "content" };

		BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD};

		Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);

		TopDocs topDocs = indexSearcher.search(multiFieldQuery, 100);

		System.out.println("共找到匹配处：" + topDocs.totalHits); // totalHits和scoreDocs.length的区别还没搞明白
		// 6、根据TopDocs获取ScoreDoc对象
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		System.out.println("共找到匹配文档数：" + scoreDocs.length);

		QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");

		// 自定义高亮代码
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"backgroud:red\">", "</span>");
		Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		for (ScoreDoc scoreDoc : scoreDocs) {
			// 7、根据searcher和ScoreDoc对象获取具体的Document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			// TokenStream tokenStream = new
			// SimpleAnalyzer().tokenStream("content", new
			// StringReader(content));
			// TokenSources.getTokenStream("content", tvFields, content,
			// analyzer, 100);
			// TokenStream tokenStream =
			// TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(),
			// scoreDoc.doc, "content", document, analyzer);
			// System.out.println(highlighter.getBestFragment(tokenStream,
			// content));
			System.out.println("-----------------------------------------");
			System.out.println(document.get("fileName") + ":" + document.get("filePath"));
			System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
			System.out.println("");
		}

		directoryReader.close();
		
	}
}
