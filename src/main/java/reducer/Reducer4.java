package reducer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Reducer4 extends Reducer<Text, Text, Text, Text> {

    private final double threshold = 0.50;
    private static DecimalFormat df = new DecimalFormat("#.####");

    @Override
    protected void reduce(Text restaurantId, Iterable<Text> comments, Context context)
            throws IOException, InterruptedException {
        Iterator<Text> commentIterator = comments.iterator();
        List<String> list = new LinkedList<>();
        double r = 0;
        int count = 0;
        while (commentIterator.hasNext()) {
            String comment = commentIterator.next().toString();
            list.add(comment);
            String[] split = comment.split("\t");
            if (Double.valueOf(split[2]) > threshold) {
                r += Double.valueOf(split[2]);
                count += 1;
            }
        }

        double avg = r / count;

        double sum = 0;

        Iterator<String> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            String[] split = listIterator.next().split("\t");
            sum += ((Double.valueOf(split[2])) * (Double.valueOf(split[1]) - avg));
        }

        double pr = 0;

        if (r != 0.0) {
            pr = avg + ((1 / r) * (sum));
        }

        context.write(restaurantId, new Text(df.format(pr)));

    }
}
