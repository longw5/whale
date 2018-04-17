package org.whale.index;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.store.hdfs.HdfsDirectory;
import org.whale.conf.WhaleConfiguration;

public class WhaleIndexReader {

	// 索引writer
	private IndexSearcher initIndexSearcher;

	// 初始化
	public WhaleIndexReader(String indexPath) throws Exception {
		this.initIndexSearcher = initIndexReader(indexPath);
	}

	// 初始化indexWriter
	private static IndexSearcher initIndexReader(String indexPath) throws Exception {
		FileSystem fs = FileSystem.get(WhaleConfiguration.getConf());
		Path indexPt = new Path(indexPath);
		if (!fs.exists(indexPt)) {
			return null;
		}
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPt, WhaleConfiguration.getConf());
		DirectoryReader directoryReader = DirectoryReader.open(hdfsDirectory);
		return new IndexSearcher(directoryReader);
	}

	public TopDocs searchIndex(Query multiFieldQuery, int num) throws Exception {
		int count = initIndexSearcher.count(multiFieldQuery);
		if(num > count) 
			return initIndexSearcher.search(multiFieldQuery, count);
		return initIndexSearcher.search(multiFieldQuery, num);
	}

	public Document doc(int doc) throws Exception {
		return initIndexSearcher.doc(doc);
	}

	public void close() {
		
	}
}
