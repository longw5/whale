package org.whale.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.fs.FileSystem;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.whale.conf.WhaleConfiguration;

public class TestLuceneIndex {

	/**
	 * 构建文件索引
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// indexFile();

		// 生成的索引目录
		FSDirectory fsdirectory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));

		RAMDirectory directory = new RAMDirectory(fsdirectory, null);

		indexRam(directory);
		
		// 解析器
		Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

		IndexWriter indexWriter = new IndexWriter(fsdirectory, indexWriterConfig);

		long addIndexes = indexWriter.addIndexes(directory);

		System.out.println(addIndexes);
		
		indexWriter.commit();
		
		FileSystem fs = FileSystem.get(WhaleConfiguration.getConf());
		DirectoryReader directoryReader = DirectoryReader.open(fsdirectory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		
		IndexReader indexReader = indexSearcher.getIndexReader();
		
		int maxDoc = indexReader.maxDoc();
		
		System.out.println(maxDoc);
		
	}

	/**
	 * 批量生成索引, 生成文件
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void indexRam(Directory directory) throws IOException, Exception {

		// 生成的索引目录
		// FSDirectory directory =
		// FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));
		// 放在内存

		// 解析器
		Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		indexWriter.deleteAll();

		File file = new File("E://lucene/weibo/src");

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {

				File f = files[i];

				System.out.println(f.getName() + ":开始构建索引：时间：" + System.currentTimeMillis());

				FileInputStream fis = new FileInputStream(f.getAbsolutePath());
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				String line = "";

				String line1 = br.readLine();
				String[] split = line1.split("\t");

				int count = 1;
				int circle = 1;
				List<Document> records = new ArrayList<>();
				while ((line = br.readLine()) != null) {
					// 构建文档
					Document doc = new Document();
					doc.add(new Field("id", line.split("\t")[0], TextField.TYPE_STORED));
					doc.add(new Field("date", new Date(line.split("\t")[4]).getTime() + "", TextField.TYPE_STORED));
					doc.add(new Field("content", line, TextField.TYPE_STORED));
					System.out.println(doc);
					records.add(doc);

					// 批处理，没5000个文档处理一批
					if (records.size() == 5000) {
						indexWriter.addDocuments(records);
						records = new ArrayList<>();
						count = 1;
						// 处理的批数
						if (circle == 10) {
							break;
						}
						circle++;
					}
					count++;
				}
				br.close();

				// 不足5000的处理
				if (records.size() > 0) {
					indexWriter.addDocuments(records);
				}
				System.out.println(f.getName() + ":结束构建索引：时间：" + System.currentTimeMillis());
				System.out.println();
			}
		}
		indexWriter.close();
	}

	/**
	 * 批量生成索引, 生成文件
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void indexFile() throws IOException, Exception {

		// 生成的索引目录
		FSDirectory directory = FSDirectory.open(FileSystems.getDefault().getPath("E://lucene/weibo/index"));

		// 解析器
		Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		indexWriter.deleteAll();

		File file = new File("E://lucene/weibo/src");

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {

				File f = files[i];

				System.out.println(f.getName() + ":开始构建索引：时间：" + System.currentTimeMillis());

				FileInputStream fis = new FileInputStream(f.getAbsolutePath());
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				String line = "";

				String line1 = br.readLine();
				String[] split = line1.split("\t");

				int count = 1;
				int circle = 1;
				List<Document> records = new ArrayList<>();
				while ((line = br.readLine()) != null) {
					// 构建文档
					Document doc = new Document();
					doc.add(new Field("id", line.split("\t")[0], TextField.TYPE_STORED));
					doc.add(new Field("date", new Date(line.split("\t")[4]).getTime() + "", TextField.TYPE_STORED));
					doc.add(new Field("content", line, TextField.TYPE_STORED));
					System.out.println(doc);
					records.add(doc);

					// 批处理，没5000个文档处理一批
					if (records.size() == 5000) {
						indexWriter.addDocuments(records);
						records = new ArrayList<>();
						count = 1;
						// 处理的批数
						if (circle == 30) {
							break;
						}
						circle++;
					}
					count++;
				}
				br.close();

				// 不足5000的处理
				if (records.size() > 0) {
					indexWriter.addDocuments(records);
				}
				System.out.println(f.getName() + ":结束构建索引：时间：" + System.currentTimeMillis());
				System.out.println();
			}
		}
		indexWriter.close();
	}
}
