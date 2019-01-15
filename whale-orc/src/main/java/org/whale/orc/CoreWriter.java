package org.whale.orc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.OrcFile.WriterOptions;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

public class CoreWriter {
	public static void main(Configuration conf, String[] args) throws IOException {
		TypeDescription schema = TypeDescription.fromString("struct<x:int,y:string>");

		Writer writer = OrcFile.createWriter(new Path("D://my-file.orc"), OrcFile.writerOptions(conf).setSchema(schema));

		VectorizedRowBatch batch = schema.createRowBatch();
		LongColumnVector x = (LongColumnVector) batch.cols[0];
		BytesColumnVector y = (BytesColumnVector) batch.cols[1];
		for (int r = 0; r < 10000; ++r) {
			int row = batch.size++;
			x.vector[row] = r;
			byte[] buffer = ("Last-" + (r * 3+"")).getBytes(StandardCharsets.UTF_8);
			y.setRef(row, buffer, 0, buffer.length);

			if (batch.size == batch.getMaxSize()) {
				writer.addRowBatch(batch);
				batch.reset();
			}
		}
		if (batch.size != 0)
			writer.addRowBatch(batch);

		writer.close();
	}

	public static void main(String[] args) throws IOException {
		main(new Configuration(), args);
	}
}