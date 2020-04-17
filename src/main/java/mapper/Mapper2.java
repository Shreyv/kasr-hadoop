package mapper;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class Mapper2 extends Mapper<LongWritable, Text, Text, Text> {

    @Override

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] line = value.toString().split(",");
        if (line.length == 5) {
            context.write(new Text(line[1]), new Text(line[4]));
        }

    }
}
