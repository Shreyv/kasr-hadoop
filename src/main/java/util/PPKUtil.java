package util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.hadoop.io.Text;

/**
 *
 * @author shrey
 */
public class PPKUtil {

    private static DecimalFormat df = new DecimalFormat("#.####");

    public static String getPPK(String comment) {

        HashSet<String> allKeywords = KeywordConstant.allKeywordValues;

        String[] words = comment.split(" ");
        HashSet<String> matched = new HashSet<>();
        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "");
            if (allKeywords.contains(word.toLowerCase())) {
                for (Map.Entry<String, String> entry : KeywordConstant.keywordMap.entrySet()) {
                    if (entry.getValue().contains(word.toLowerCase())) {
                        matched.add(entry.getKey());
                    }
                }
            }
        }

        // adding FOOD by default
        matched.add("FOOD");

        return String.join(",", matched);
    }

//    public static List<Double> getPreferenceWeightVector(Iterator<Text> comments) {
    public static String getPreferenceWeightVector(Iterator<Text> comments) {

        Map<String, Integer> countMap = new LinkedHashMap<>();
        HashSet<String> allKeywords = KeywordConstant.allKeywordValues;
        long totalCommentWords = 0;
        long totalComment = 0; 
        List<String> foundKeyWords = new LinkedList<>();
        Set<String> uniqueKeys = new HashSet<>();
        //    List<Double> result = new LinkedList<>();
        List<String> result = new LinkedList<>();

        for (String key : KeywordConstant.keywordMap.keySet()) {
            countMap.put(key, 0);
        }

        while (comments.hasNext()) {
            String comment = comments.next().toString();
            for (String word : comment.split(" ")) {
                word = word.replaceAll("[^a-zA-Z]", "");
                if (allKeywords.contains(word.toLowerCase())) {
                    String key = KeywordConstant.getKey(word);
                    foundKeyWords.add(key);
                    uniqueKeys.add(key);
                }
                totalCommentWords++;

            }

            foundKeyWords.add("FOOD");
            uniqueKeys.add("FOOD");
            for (String k : uniqueKeys) {
                int count = countMap.get(k);
                countMap.put(k, count + 1);
            }

            uniqueKeys.clear();
            totalComment++;

        }

        Map<String, Long> termFrequencyMap = foundKeyWords.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {

            if (entry.getValue() == 0) {
                result.add("0.0");

            } else {
                float tf = (float) termFrequencyMap.get(entry.getKey()) / foundKeyWords.size();
                double idf_div = (double) ((totalComment) / countMap.get(entry.getKey()));
                double idf = Math.abs(Math.log(idf_div));
                result.add(df.format(tf * idf));
            }

        }

        return String.join(",", result);
    }

    public static double calculateCosineSimilarity(List<String> A, List<String> B) {

        float prod = 0;
        float Asquare = 0;
        float Bsquare = 0;

        if (A.size() == B.size()) {

            for (int i = 0; i < A.size(); i++) {
                prod += (Float.valueOf(A.get(i)) * Float.valueOf(B.get(i)));
                Asquare += (Float.valueOf(A.get(i)) * Float.valueOf(A.get(i)));
                Bsquare += (Float.valueOf(B.get(i)) * Float.valueOf(B.get(i)));
            }

            if (Asquare != 0 && Bsquare != 0) {
                return prod / (Math.sqrt(Asquare) * Math.sqrt(Bsquare));
            }
        }

        return Double.valueOf("0.0");

    }

}
