package org.whale.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.whale.util.Constant;

import com.sun.tools.classfile.Annotation.element_value;

public class WhaleIndexWriter {

	private static IndexWriter initIndexWriter;
	private static final Analyzer analyzer = new StandardAnalyzer();
	private static final String tableName = "TABLE1";
	
	static {
		try {
			initIndexWriter = initIndexWriter(Constant.FILE_MAP.get(tableName), analyzer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	private static void insert(Object o) {
		
		//如果是File类型
		if(o instanceof File) {
			FileToWhale(o);
		}else if(o instanceof String){
			
		}else if(o instanceof Record) {
			
			
		}
	}

	private static void FileToWhale(Object o) {
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

	public static void write(IndexWriter indexWriter, InputStream is) throws IOException, Exception {
		Document doc = new Document();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		String s = "";
		StringBuffer sb = new StringBuffer();
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		br.close();
		String content = sb.toString();
		doc.add(new Field("content", content, TextField.TYPE_STORED));
		indexWriter.addDocument(doc);
	}
	
	public static void main(String[] args) throws Exception {
		insert(new File("E://lucene/nginx"));
		System.out.println("ok.............");
	}
}
