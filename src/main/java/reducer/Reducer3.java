package reducer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import util.PPKUtil;


public class Reducer3 extends Reducer<Text, Text, Text, Text> {

    private Set<String> activeUserPreferenceSet = new HashSet<>();
    private Map<String, String> userPreferences = new HashMap<>();
    private List<String> activeUserPreferenceList = new LinkedList<>();
    private static DecimalFormat df = new DecimalFormat("#.####");

    @Override
    protected void setup(Context context)
            throws IOException, InterruptedException {
        try {

            //Paths of cache files
            Path[] localFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());

            BufferedReader br = new BufferedReader(new FileReader(localFiles[0].toString()));
            String st;
            while ((st = br.readLine()) != null) {
                String[] split = st.split(",");
                activeUserPreferenceList.add((split[1]));

                if (split[1] != "0") {
                    activeUserPreferenceSet.add(split[0]);
                }
            }

            // User preferences loading 
            br = new BufferedReader(new FileReader(localFiles[1].toString()));

            while ((st = br.readLine()) != null) {
                String[] spilt = st.split("\t");
                userPreferences.put(spilt[0], spilt[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reduce(Text restaurantId, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        Iterator<Text> userReviews = values.iterator();

        while (userReviews.hasNext()) {
            String[] reviewSplit = userReviews.next().toString().split("\t");
            Set<String> intersection = new HashSet<String>(activeUserPreferenceSet);
            intersection.retainAll(Arrays.asList(reviewSplit[2].split(",")));
            if (!intersection.isEmpty()) {
                double similarity = PPKUtil.calculateCosineSimilarity(activeUserPreferenceList, Arrays.asList(userPreferences.get(reviewSplit[0]).split(",")));
                context.write(restaurantId, new Text(reviewSplit[0] + '\t' + reviewSplit[1] + '\t' + String.valueOf(df.format(similarity)) + '\t' + reviewSplit[3]));
            }
        }

    }
}
