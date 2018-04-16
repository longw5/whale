package org.whale.record;

import org.whale.index.WhaleIndexWriter;

public class WhaleWriter {

	private WhaleIndexWriter indexWriter;

	public WhaleWriter(WhaleIndexWriter indexWriter) {
		super();
		this.indexWriter = indexWriter;
	}
	
	public boolean insert(Object o) {
		indexWriter.insert(o);
		return true;
	}
	
	public boolean update(Object o) {
		indexWriter.update(o);
		return true;
	}
}
