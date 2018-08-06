package org.whale.hadoop.mr.wc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {

		int run = ToolRunner.run(new Configuration(), new WordCountDriver(), args);
		System.exit(run);
	}

	@Override
	public int run(String[] arg0) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJobName("wc");

		job.setJarByClass(WordCountDriver.class);

		// 指定本job所采用的mapper类
		job.setMapperClass(WordCountMapper.class);
		// 指定本job所采用的reducer类
		job.setReducerClass(WordCountReducer.class);

		// 指定我们的mapper类输出的kv数据类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		// 指定我们的reducer类输出的kv数据类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		
		return 0;
	}

	class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			
		}
	}

	class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
		/**
		 * reduce方法是每获得一个<key,valueList>,执行一次
		 */
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values, Context context)
				throws IOException, InterruptedException {

		}
	}

}
