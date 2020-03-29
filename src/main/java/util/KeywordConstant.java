package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 *
 * @author shrey
 */
public final class KeywordConstant {

    public static final LinkedHashMap<String, String> keywordMap = new LinkedHashMap<>();

    static {
        BufferedReader br = null;
        String line = null;
        String[] split_line = null;
        try {

            br = new BufferedReader(new FileReader("keyword_constants.txt"));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    split_line = line.split(":");
                    keywordMap.put(split_line[0], split_line[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
