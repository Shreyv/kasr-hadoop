package runner;

import java.net.URI;
import mapper.Mapper5;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.util.*;
import reducer.Reducer5;

public class JobRunner5 extends Configured implements Tool {

    public int run(String[] args) throws Exception {

        if (args.length != 3) {

            System.err.println("Usage: JobRunner3 <input path> <outputpath> <cacheFilesPath>");
            System.exit(-1);

        }

        Job job = new Job(getConf(), "Selecting top N restaurants");

        job.setJarByClass(getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        DistributedCache.addCacheFile(new URI(args[2]), job.getConfiguration());

        job.setMapperClass(Mapper5.class);

        job.setReducerClass(Reducer5.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(DoubleWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {

        int exitCode = ToolRunner.run(new JobRunner5(), args);
        System.exit(exitCode);

    }

}
