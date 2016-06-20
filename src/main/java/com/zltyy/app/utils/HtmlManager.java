package com.zltyy.app.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlManager {
	
	private static Map<String, List<String>> content_map = new HashMap<String, List<String>>();
	private static Map<String, List<String>> title_map = new HashMap<String, List<String>>();
	private static Map<String, List<String>> meta_map = new HashMap<String, List<String>>();
	
	public static void addContent(Map<String, List<String>> map){
		content_map.putAll(map);
	}
	
	public static Map<String, List<String>> getContent(){
		return content_map;
	}
	
	public static void clearContent(){
		content_map.clear();
	}
	
	public static void addTitle(Map<String, List<String>> map){
		title_map.putAll(map);
	}
	
	public static Map<String, List<String>> getTitle(){
		return title_map;
	}

}
