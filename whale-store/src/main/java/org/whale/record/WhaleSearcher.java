package org.whale.record;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.whale.index.WhaleIndexReader;
import org.whale.util.Constant;

public class WhaleSearcher {

	private WhaleIndexReader indexReader;

	public WhaleSearcher(WhaleIndexReader indexReader) {
		super();
		this.indexReader = indexReader;
	}
	
	public static void main(String[] args) throws Exception {
		
		String keyWord = "7A64650CB9FB11F699D538E4B24D0AA882154427";
		String[] fields = { "content" };
		BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD };
		Query query = MultiFieldQueryParser.parse(keyWord, fields, clauses, Constant.DEFAULT_ANALYZER);
		
		WhaleIndexReader whaleIndexReader = new WhaleIndexReader("/whale/tblName");
		
		TopDocs topDocs = whaleIndexReader.searchIndex(query, 100);
		System.out.println("共找到匹配处：" + topDocs.totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		System.out.println("共找到匹配文档数：" + scoreDocs.length);
		
		// 自定义高亮代码
		int i = 0;
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document document = whaleIndexReader.doc(scoreDoc.doc);
			System.out.println((++i) + ":" +document.get("content"));
			System.out.println();
		}
		whaleIndexReader.close();
	}
}
