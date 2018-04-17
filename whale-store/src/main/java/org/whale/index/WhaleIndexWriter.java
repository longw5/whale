package org.whale.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.solr.store.hdfs.HdfsDirectory;
import org.whale.conf.WhaleConfiguration;
import org.whale.record.Record;

public class WhaleIndexWriter {

	//索引writer
	private IndexWriter initIndexWriter;
	//解析器
	private Analyzer analyzer;
	
	//初始化
	public WhaleIndexWriter(String tableName) throws Exception {
		this.analyzer = new StandardAnalyzer();
		this.initIndexWriter = initIndexWriter(tableName, analyzer);
	}

	//初始化indexWriter
	private static IndexWriter initIndexWriter(String iNDEX_PATH, Analyzer analyzer) throws Exception {
		FileSystem fs = FileSystem.get(WhaleConfiguration.getConf());
		Path indexPath = new Path(iNDEX_PATH);
        if (!fs.exists(indexPath)) {
            fs.mkdirs(indexPath);
        }
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPath, WhaleConfiguration.getConf());
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		return new IndexWriter(hdfsDirectory, indexWriterConfig);
	}
	
	//将数据插入到whale中
	public void insert(Object o) {
		
		//如果是File类型
		if(o instanceof File) {
			FileToWhale(o);
		//String类型
		}else if(o instanceof String){
			
		//Whale record类型
		}else if(o instanceof Record) {
			
		}
	}
	
	public void update(Object o) {
		
		if(o instanceof String){
			updateToWhale(o);
		//Whale record类型
		}else if(o instanceof Record) {
			
		}
	}

	private void updateToWhale(Object o) {
		
		String line = (String)o;
		if(line == null)
			return;
	}

	//将文件类型写入whale中
	private void FileToWhale(Object o) {
		File file = (File)o;
		
		if(file == null)
			return;
		
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			try {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					FileInputStream fis = null;
					fis = new FileInputStream(f.getAbsolutePath());
					write(initIndexWriter, fis);
					System.out.println(file.getName() + " is complete...............");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					initIndexWriter.close();
					initIndexWriter = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//将数据流构建索引
	private static void write(IndexWriter indexWriter, InputStream is) throws IOException, Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String line;
		while ((line = br.readLine()) != null) {
			Document doc = new Document();
			doc.add(new Field("content", line, TextField.TYPE_STORED));
			
			System.out.println(doc);
			System.out.println();
			
			indexWriter.addDocument(doc);
		}
	}
}
