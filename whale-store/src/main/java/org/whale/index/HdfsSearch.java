package org.whale.index;

import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
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
import org.apache.lucene.store.FSDirectory;

public class HdfsSearch {

	public static void main(String[] args) throws Exception {
		
		String srcDir = "E://lucene/xiandaishi";
		String indexDir = "E://lucene/xiandaishi_index";
		
		String keyWord = "帝国主义";
		
//		searchIndex(indexDir, keyWord);
	}
	
	public static void searchIndex(String indexDir, String keyWord) throws Exception {
		
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath(indexDir));
		
		Analyzer analyzer = new StandardAnalyzer();

		DirectoryReader directoryReader = DirectoryReader.open(directory);

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
