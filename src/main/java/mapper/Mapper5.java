package mapper;

import java.io.*;
import java.util.*;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import util.PPKUtil;

public class Mapper5 extends Mapper<Object, Text, Text, DoubleWritable> {

    private TreeMap<Double, String> tmap;

    @Override
    public void setup(Context context) throws IOException,
            InterruptedException {
        tmap = new TreeMap<Double, String>();
    }

    @Override
    public void map(Object key, Text value,
            Context context) throws IOException,
            InterruptedException {

        String[] tokens = value.toString().split("\t");

        String restaurantId = tokens[0];
        double personalizedRating = Double.parseDouble(tokens[1]);

        tmap.put(personalizedRating, restaurantId);


        if (tmap.size() > PPKUtil.TOP_N) {
            tmap.remove(tmap.firstKey());
        }
    }

    @Override
    public void cleanup(Context context) throws IOException,
            InterruptedException {
        for (Map.Entry<Double, String> entry : tmap.entrySet()) {

            context.write(new Text(entry.getValue()), new DoubleWritable(entry.getKey()));
        }
    }
}
