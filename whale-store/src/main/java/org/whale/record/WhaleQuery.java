package org.whale.record;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.whale.util.Constant;
import org.apache.lucene.search.BooleanClause.Occur;

public class WhaleQuery{

	private String keyWord = "7A64650CB9FB11F699D538E4B24D0AA882154427";
	private String[] fields = { "content" };
	private BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD };
	private Query query;
	
	public WhaleQuery(String keyWord, String[] fields, Occur[] clauses) throws Exception {
		super();
		this.keyWord = keyWord;
		this.fields = fields;
		this.clauses = clauses;
		this.query = MultiFieldQueryParser.parse(this.keyWord, this.fields, this.clauses, Constant.DEFAULT_ANALYZER);
	}
	
}
