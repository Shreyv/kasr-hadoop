package util;

import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author shrey
 */
public class PPKUtil {

    public static String getPPK(String comment) {

        HashSet<String> allKeywords = KeywordConstant.allKeywordValues;

        String[] words = comment.split(" ");
        HashSet<String> matched = new HashSet<>();
        for (String word : words) {

            if (allKeywords.contains(word.toLowerCase())) {
                for (Map.Entry<String, String> entry : KeywordConstant.keywordMap.entrySet()) {
                    if (entry.getValue().indexOf(word.toLowerCase()) != -1) {
                        matched.add(entry.getKey());
                    }
                }
            }
        }

        // adding FOOD by default
        matched.add("FOOD");

        return String.join(",", matched);
    }
}
