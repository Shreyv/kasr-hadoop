package reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import util.PPKUtil;

/**
 *
 * @author shrey
 */
public class Reducer1 extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        LinkedList<Text> list = new LinkedList<>();
        Iterator<Text> avgIterator = values.iterator();
        String[] splitLine = null;

        float totalStars = 0;
        int count = 0;

        while (avgIterator.hasNext()) {
            splitLine = avgIterator.next().toString().split(",");
            list.add(new Text(splitLine[0] + "\t" + splitLine[2] + "\t" + PPKUtil.getPPK(splitLine[3])));
            totalStars += Float.parseFloat(splitLine[2]);
            count += 1;
        }

        float avg = totalStars / count;
        Iterator<Text> iter = list.iterator();

        while (iter.hasNext()) {
            context.write(key,new Text(iter.next().toString()+"\t"+String.valueOf(avg)));
        }

    }
}
