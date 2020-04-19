package runner;

import java.net.URI;
import mapper.Mapper3;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import reducer.Reducer3;

public class JobRunner3 {

    public int run(String[] args) throws Exception {

        if (args.length != 4) {

            System.err.println("Usage: JobRunner3 <input path> <outputpath> <cacheFilesPath> <cacheFilesPath>");
            System.exit(-1);

        }

        Job job = new Job();
        job.setJobName("Calculating cosine similarity of active user with previous users");

        job.setJarByClass(getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        DistributedCache.addCacheFile(new URI(args[2]), job.getConfiguration());
        DistributedCache.addCacheFile(new URI(args[3]), job.getConfiguration());

        job.setMapperClass(Mapper3.class);

        job.setReducerClass(Reducer3.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {

        JobRunner3 driver = new JobRunner3();
        driver.run(args);

    }

}
