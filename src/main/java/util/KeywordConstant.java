package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;


public class KeywordConstant {

    final static String FOOD = "food,delicious,flavor,quality,texture,fresh,ingredients,refreshing,delightful,exquisite,appetizing,tasty,delectable,menu,options,grill,barbeque,buffet,meal,dish,authentic,cooked,flavourful,taste";
    final static String SERVICE = "service,friendly,dull,management,helpful,slow,fast,bar,rude,helped";
    final static String PRICE = "price,$,worth,money,price,cheap,expensive,value,reasonable,affordable,charge,cost,bill,charged,expensively,priced,costed,costly";
    final static String STAFF = "staff,server,waiter,waitress,manager,owner,management,bartender";
    final static String AMBIENCE = "ambience,atmosphere,environment,interior,exterior,decor,pleasant,aura,music,vibe,feel,plesentful,decorated";
    final static String LOCATION = "location,parking,locality,downtown,mall,shopping,vicinity,wait,time,drive-through,theatre,close";
    final static String RECOMMEND = "again,back,try,must,visit,definitely,recommend,suggest,tried,recommended,definite,suggested,recommendation";
    final static String EXPERIENCE = "experience,perfect,good,best,disappoint,fun,enjoy,poor,awesome,bad,surprised,nice,serene,horrible,disappointed,enjoyed,horribly,perfectly,experienced";

    public static LinkedHashMap<String, String> keywordMap = new LinkedHashMap<String, String>() {
        {
            put("FOOD", FOOD);
            put("SERVICE", SERVICE);
            put("PRICE", PRICE);
            put("STAFF", STAFF);
            put("AMBIENCE", AMBIENCE);
            put("LOCATION", LOCATION);
            put("RECOMMEND", RECOMMEND);
            put("EXPERIENCE", EXPERIENCE);

        }
    };

    public static HashSet<String> allKeywordValues = new HashSet<String>() {
        {
            addAll(Arrays.asList(FOOD.split(",")));
            addAll(Arrays.asList(SERVICE.split(",")));
            addAll(Arrays.asList(PRICE.split(",")));
            addAll(Arrays.asList(STAFF.split(",")));
            addAll(Arrays.asList(AMBIENCE.split(",")));
            addAll(Arrays.asList(LOCATION.split(",")));
            addAll(Arrays.asList(RECOMMEND.split(",")));
            addAll(Arrays.asList(EXPERIENCE.split(",")));

        }

    };

    public static String getKey(String value) {
        for (Map.Entry<String, String> entry : keywordMap.entrySet()) {
            if (entry.getValue().contains(value.toLowerCase())) {
                return entry.getKey();
            }
        }
        
        return null;
    }

//    static {
//        BufferedReader br = null;
//        String line = null;
//        String[] split_line = null;
//        try {
//
//            br = new BufferedReader(new FileReader("keyword_constants.txt"));
//            while ((line = br.readLine()) != null) {
//                if (!line.isEmpty()) {
//                    split_line = line.split(":");
//                    keywordMap.put(split_line[0], split_line[1]);
//                    allKeywordValues.addAll(Arrays.asList(split_line[1].split(",")));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
