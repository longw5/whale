package org.whale.udf;

import org.apache.hadoop.io.LongWritable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        long a = 1L;
        LongWritable l = new LongWritable(a);
        System.out.println(l.get());
        l.set(a++);
        System.out.println(l.get());
    }
}
