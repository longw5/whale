package org.whale.test;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.store.hdfs.HdfsDirectory;
import org.whale.conf.WhaleConfiguration;
import org.whale.util.Constant;

public class TestIndexSearcher {

	public static void main(String[] args) throws Exception {
		
		Term term = new Term("content", "7A64650CB9FB11F699D538E4B24D0AA882154427");
		TermQuery termQuery = new TermQuery(term);
		
		FileSystem fs = FileSystem.get(WhaleConfiguration.getConf());
		Path indexPt = new Path("/whale/tblName");
		if (!fs.exists(indexPt)) {
			System.exit(0);
		}
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPt, WhaleConfiguration.getConf());
		DirectoryReader directoryReader = DirectoryReader.open(hdfsDirectory);
		
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		TopDocs search = indexSearcher.search(termQuery, 100);
		
		System.out.println(search.totalHits);
	}
	
	
	public static void main1(String[] args) throws Exception {
		
		String keyWord = "7A64650CB9FB11F699D538E4B24D0AA882154427";
		String[] fields = { "content" };
		BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD };
		
		Query query = MultiFieldQueryParser.parse(keyWord, fields, clauses, Constant.DEFAULT_ANALYZER);
		
		FileSystem fs = FileSystem.get(WhaleConfiguration.getConf());
		Path indexPt = new Path("/whale/tblName");
		if (!fs.exists(indexPt)) {
			System.exit(0);
		}
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPt, WhaleConfiguration.getConf());
		DirectoryReader directoryReader = DirectoryReader.open(hdfsDirectory);
		
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		TopDocs search = indexSearcher.search(query, 100);
		
		int records  = search.totalHits;
		
		ScoreDoc[] scoreDocs = search.scoreDocs;
		
		int i = 1;
		
		for (ScoreDoc scoreDoc : scoreDocs) {
			
			if(i > records)
				break;
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println(i + ":" + doc);
			i++;
		}
		System.out.println(scoreDocs.length);
	}
	
}
