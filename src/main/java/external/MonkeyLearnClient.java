package external;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnResponse;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.Tuple;
import com.monkeylearn.ExtraParam;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MonkeyLearnClient {
	
	private static final String API_KEY = "552c4a01c856cce4d792ba9543381574963f4e88";
	
    public static void main( String[] args ) throws MonkeyLearnException {
    	
    	String[] textList = {
				"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design and the first to feature the spacesuit’s full-body look.", };
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
				System.out.println(w);
			}
			System.out.println();
		}
        
    }
    
    public static List<List<String>> extractKeywords(String[] text) {
    	
    	if(text == null || text.length == 0) {
    		return new ArrayList<>();
    	}
    	
    	// Use the API key from your account, similar to MySQLDBConnection
        MonkeyLearn ml = new MonkeyLearn(API_KEY);

        // Use the keyword extractor
        ExtraParam[] extraParams = {new ExtraParam("max_keywords", "3")};
        MonkeyLearnResponse res;
		try {
			res = ml.extractors.extract("ex_YCya9nrn", text, extraParams);
			JSONArray resultArray = res.arrayResult;
			return getKeywords(resultArray);
			
		} catch (MonkeyLearnException e) {
			
			e.printStackTrace();
		}
        return new ArrayList<>();
        
    }
    private static List<List<String>> getKeywords(JSONArray array) {
    	List<List<String>> topKeywords = new ArrayList<>();
    	
    	for(int i = 0; i < array.size(); i++) {
    		List<String> keywords = new ArrayList<>();
    		JSONArray keywordArray = (JSONArray) array.get(i);
    		for(int j = 0; j < keywordArray.size(); j++) {
    			JSONObject object = (JSONObject) keywordArray.get(j);
    			String keyword = (String) object.get("keyword");
    			keywords.add(keyword);
    		}
    		topKeywords.add(keywords);
    	}
    	
    	return topKeywords;
    }
}

