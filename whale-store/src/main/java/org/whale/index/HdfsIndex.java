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
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.solr.store.hdfs.HdfsDirectory;

public class HdfsIndex {

	static Map<String, String> info = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {
		
		File file = new File("E://lucene/nginx");
		
		//建索引
		createIndex(info, file);
		
		//搜索
//		searchIndex();
	}
	
	public static void createIndex(Map<String, String> info, File file) throws IOException, Exception {

		Configuration conf = new Configuration();

		conf.set("fs.defaultFS", "hdfs://hadoop:8020");
		conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());  
		
        FileSystem fs = FileSystem.get(conf);
		
        Path indexPath = new Path("/lucene/nginx");
        if (!fs.exists(indexPath)) {
            fs.mkdirs(indexPath);
        }
        
		HdfsDirectory hdfsDirectory = new HdfsDirectory(indexPath, conf);
		
		Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		
		IndexWriter indexWriter = new IndexWriter(hdfsDirectory, indexWriterConfig);

		indexWriter.deleteAll();

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {

				File f = files[i];

				System.out.println(f.getName()+":开始构建索引：时间："+System.currentTimeMillis());

				Document doc = new Document();

				FileInputStream fis = new FileInputStream(f.getAbsolutePath());
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				String s = "";
				StringBuffer sb = new StringBuffer();
				while ((s = br.readLine()) != null)
					sb.append(s + "\n");
				br.close();
				String content = sb.toString();

				doc.add(new Field("content", content, TextField.TYPE_STORED));
				doc.add(new Field("fileName", f.getName(), TextField.TYPE_STORED));
				doc.add(new Field("filePath", f.getAbsolutePath(), TextField.TYPE_STORED));
				doc.add(new Field("updateTime", f.lastModified()+"", TextField.TYPE_STORED));
				
				indexWriter.addDocument(doc);
				
				System.out.println(f.getName()+":结束构建索引：时间："+System.currentTimeMillis());
				
				System.out.println();
				
			}
		}
		indexWriter.close();

	}
}
