package com.qa.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {

	public static JSONObject responsejson;
	
	
	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	
	
	}
	
	public static String getHeaderValueJpath(HashMap<String,String> headerAllMap, String headerTitle) {
//		HashMap<String, String> ToPrintMap = new HashMap<String, String>();
		String HeaderValue = null;
		for(Map.Entry<String, String> entry : headerAllMap.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(headerTitle)) {
				HeaderValue = entry.getValue();
				break;
			}
				
		}
			
		return HeaderValue;
		
	}
	
	
	}
	
	
	
	
	
	

