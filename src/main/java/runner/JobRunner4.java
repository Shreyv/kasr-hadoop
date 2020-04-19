package runner;

import mapper.Mapper4;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import reducer.Reducer4;

public class JobRunner4 {

    public int run(String[] args) throws Exception {

        if (args.length != 2) {

            System.err.println("Usage: JobRunner4 <input path> <outputpath>");
            System.exit(-1);
        }

        Job job = new Job();

        job.setJobName("Getting personalized rating of restaurants");

        job.setJarByClass(getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        job.setMapperClass(Mapper4.class);

        job.setReducerClass(Reducer4.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;

    }

    public static void main(String[] args) throws Exception {

        JobRunner4 driver = new JobRunner4();
        driver.run(args);

    }

}
