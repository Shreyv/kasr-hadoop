package reducer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import util.PPKUtil;

public class Reducer5 extends Reducer<Text, DoubleWritable, DoubleWritable, Text> {

    private TreeMap<Double, String> tmap;
    private Map<String, String> restaurantMap;

    @Override
    public void setup(Context context) throws IOException,
            InterruptedException {
        tmap = new TreeMap<Double, String>(Collections.reverseOrder());
        restaurantMap = new HashMap<String, String>();

        Path[] localFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        BufferedReader br = new BufferedReader(new FileReader(localFiles[0].toString()));
        String st;
        while ((st = br.readLine()) != null) {
            String[] split = st.split(",",2);
            restaurantMap.put(split[0],split[1]);
        }

    }

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values,
            Context context) throws IOException, InterruptedException {

        String restaurantId = key.toString();
        double count = 0;

        for (DoubleWritable val : values) {
            count = val.get();
        }

        tmap.put(count, restaurantId);

        if (tmap.size() > PPKUtil.TOP_N) {
            tmap.remove(tmap.firstKey());
        }
    }

    @Override
    public void cleanup(Context context) throws IOException,
            InterruptedException {

        for (Map.Entry<Double, String> entry : tmap.entrySet()) {

            double pr = entry.getKey();
            String id = entry.getValue();
            context.write(new DoubleWritable(pr), new Text(restaurantMap.get(id).replace(",","\t")));
        }
    }
}
