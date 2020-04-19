package runner;

import mapper.Mapper2;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import reducer.Reducer2;

public class JobRunner2 {

    public int run(String[] args) throws Exception {

        if (args.length != 2) {

            System.err.println("Usage: JobRunner2 <input path> <outputpath>");
            System.exit(-1);

        }

        Job job = new Job();

        job.setJobName("Deriving Preference weight vector of previous users");

        job.setJarByClass(getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(Mapper2.class);

        job.setReducerClass(Reducer2.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {

        JobRunner2 driver = new JobRunner2();
        driver.run(args);

    }

}
