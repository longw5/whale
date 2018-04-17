package org.whale.lucene;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.whale.collector.TestCollector;

public class TestLuceneSearch {

	/**
	 * 
	 * doc信息:
	 * Document
	 * <
	 * 		stored,indexed,tokenized<id:152733> 
	 * 		stored,indexed,tokenized<date:1343282303000> 
	 * 		stored,indexed,tokenized<content:152733 .............>
	 * >
	 * @throws IOException 
	 */

	/**
	 * 范围boolean查询
	 * @throws IOException
	 */
	@Test
	public void booleanSearch2() throws IOException {
		
		//where date > "1330130185000" and id < "1340536034000 and/or id = "40000"
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
//		TermQuery termQuery = new TermQuery(new Term("date", "1338925556000"));
//		TermQuery termQuery = new TermQuery(new Term("id", "3862"));
		TermQuery termQuery = new TermQuery(new Term("id", "40000"));

		//term range
	    Term begin = new Term("date","1330130185000");
	    Term end = new Term("date","1340536034000");
	    TermRangeQuery termRangeQuery = new TermRangeQuery("date", begin.bytes(), end.bytes(), false, true);

	    BooleanClause booleanClause1 = new BooleanClause(termQuery, Occur.MUST);
	    BooleanClause booleanClause2 = new BooleanClause(termRangeQuery, Occur.MUST);
	    
	    BooleanQuery build = new BooleanQuery.Builder().add(booleanClause1).add(booleanClause2).build();
	    
	    TopDocs search = indexSearcher.search(build, 10);
	    
	    ScoreDoc[] scoreDocs = search.scoreDocs;
	    
	    for (ScoreDoc scoreDoc : scoreDocs) {
	    	System.out.println(indexSearcher.doc(scoreDoc.doc));
		}
		directoryReader.close();
	}
	
	
	/**
	 * 等值boolean查询
	 * @throws IOException
	 */
	@Test
	public void booleanSearch1() throws IOException {
		
		//where date = "1330130185000" and/or id = "152732"
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		//term range
	    Term begin = new Term("date","1330130185000");
//	    Term end = new Term("id","152732");   	//MUST    	无值
	    Term end = new Term("id","1527321");	//MUST_NOT	有值
	    
	    TermQuery termQuery1 = new TermQuery(begin);
	    TermQuery termQuery2 = new TermQuery(end);

	    BooleanClause booleanClause1 = new BooleanClause(termQuery1, Occur.MUST);
	    BooleanClause booleanClause2 = new BooleanClause(termQuery2, Occur.MUST_NOT);
	    
	    BooleanQuery build = new BooleanQuery.Builder().add(booleanClause1).add(booleanClause2).build();
	    
	    TopDocs search = indexSearcher.search(build, 10);
	    
	    ScoreDoc[] scoreDocs = search.scoreDocs;
	    
	    for (ScoreDoc scoreDoc : scoreDocs) {
	    	System.out.println(indexSearcher.doc(scoreDoc.doc));
		}
		directoryReader.close();
	}
	
	//range query
	/**
	 * 范围检索
	 * @throws IOException
	 */
	@Test
	public void rangeSearch() throws IOException {

		//where date < 1340536034000 and date > 1330130185000;
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		//term range
		//以日期来查询
	    Term begin = new Term("date","1330130185000");
	    Term end = new Term("date","1340536034000");
		
	    //以id来查询
//	    Term begin = new Term("id","150000");
//	    Term end = new Term("id","160000");
	    
	    TermRangeQuery termRangeQuery = new TermRangeQuery("date", begin.bytes(), end.bytes(), false, true);
//	    TermRangeQuery termRangeQuery = new TermRangeQuery("id", begin.bytes(), end.bytes(), false, true);
	    
	    TopDocs search = indexSearcher.search(termRangeQuery, 100);
	    
	    ScoreDoc[] scoreDocs = search.scoreDocs;
	    
	    for (ScoreDoc scoreDoc : scoreDocs) {
	    	System.out.println(indexSearcher.doc(scoreDoc.doc));
		}
		directoryReader.close();
	}
	
	/**
	 * collector
	 * 批量查询返回
	 * @throws IOException 
	 * @throws Exception
	 */
	@Test
	public void collectorSearch() throws IOException {

		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));
		Analyzer analyzer = new StandardAnalyzer();
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		List<Document> list = new ArrayList<>();
		
		Term term = new Term("date", "1334628313000");
		
		TermQuery termQuery = new TermQuery(term);
		
		indexSearcher.search(termQuery, new TestCollector() {

			@Override
			public boolean needsScores() {
				return false;
			}

			@Override
			public void collect(int doc) throws IOException {
				list.add(indexSearcher.doc(doc));
			}
		});
		
		System.out.println(list);
		directoryReader.close();
	}
	
	/**
	 * 简单的词检索
	 * term query
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws Exception
	 */
	@Test
	public void simpleSearch() throws IOException, ParseException {

		//where date = "1330130185000";
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));

		Analyzer analyzer = new StandardAnalyzer();

		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		QueryParser parser = new QueryParser("date", analyzer);
		
		Query query = parser.parse("1330130185000");
		
		TopDocs topDocs = indexSearcher.search(query, 10);
		
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		System.out.println("总共检索到文档数量："+scoreDocs.length);
		for (ScoreDoc scoreDoc : scoreDocs) {
			
			System.out.println("---------------------------------------------");
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println(doc);
			System.out.println();
		}
		directoryReader.close();
	}
	
}
