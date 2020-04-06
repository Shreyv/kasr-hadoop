package reducer;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import util.PPKUtil;

/**
 *
 * @author shrey
 */
public class Reducer2 extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text userId, Iterable<Text> comments, Context context)
            throws IOException, InterruptedException {
        

        context.write(userId,new Text(PPKUtil.getPreferenceWeightVector(comments.iterator())));
    }
}
