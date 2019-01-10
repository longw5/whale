package org.whale.mysql.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.File;
import java.io.IOException;

class Sample {
	public static void main(String[] args) {
		
		String filePath = "bin-log/mysql-bin.000002";
		File binlogFile = new File(filePath);
		EventDeserializer eventDeserializer = new EventDeserializer();
		eventDeserializer.setChecksumType(ChecksumType.CRC32);
		BinaryLogFileReader reader;
		try {
			reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
			
			Event readEvent = reader.readEvent();
			
			try {
				for (Event event; (event = reader.readEvent()) != null;) {
					System.out.println("event : "+event.toString());
					
					EventData data = event.getData();
					System.out.println(data);
				}
			}catch (Exception e) {
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}