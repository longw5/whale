package org.whale.orc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

public class TestOrc {

	public static void main(String[] args) throws Exception, IOException {

		Configuration conf = new Configuration();
		TypeDescription schema = TypeDescription.fromString("struct<x:int,y:int>");
		Writer writer = OrcFile.createWriter(new Path("D://my-file.orc"),
				OrcFile.writerOptions(conf).setSchema(schema));

		VectorizedRowBatch batch = schema.createRowBatch();
		LongColumnVector x = (LongColumnVector) batch.cols[0];
		LongColumnVector y = (LongColumnVector) batch.cols[1];
		for (int r = 0; r < 10000; ++r) {
			int row = batch.size++;
			x.vector[row] = r;
			y.vector[row] = r * 3;
			// If the batch is full, write it out and start over.
			if (batch.size == batch.getMaxSize()) {
				System.out.println(batch.getMaxSize());
				writer.addRowBatch(batch);
				batch.reset();
			}
		}
		if (batch.size != 0) {
			writer.addRowBatch(batch);
			batch.reset();
		}
		writer.close();

	}

}
