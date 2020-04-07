package mapper;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

/**
 *
 * @author shrey
 */
public class Mapper3 extends Mapper<LongWritable, Text, Text, Text> {

    @Override

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] line = value.toString().split("\t", 2);
        context.write(new Text(line[0]), new Text(line[1]));
    }
}
