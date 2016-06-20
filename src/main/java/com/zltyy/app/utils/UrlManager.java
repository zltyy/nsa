package com.zltyy.app.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UrlManager {
	
	private static Set<String> new_url_set = new HashSet<String>();
	private static Set<String> old_url_set = new HashSet<String>();
	
	/**
	 * 
	 * @param new_url
	 */
	public static void addNewUrl(String new_url){
		if(null == new_url ||new_url.equals(""))
			return;
		if(new_url_set.contains(new_url) || old_url_set.contains(new_url))
			return;
		new_url_set.add(new_url);
	}
	/**
	 * 
	 * @param new_urls
	 */
	public static void addNewUrls(String[] new_urls){
		if(null != new_urls && new_urls.length!=0){
			for (String new_url : new_urls) {
				new_url_set.add(new_url);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getNewUrl(){
		String new_url = "";
		if(hasNewUrl()){
			Iterator<String> it = new_url_set.iterator();
			new_url = it.next();
			old_url_set.add(new_url);
			it.remove();
		}
		return new_url;
	}
	
	/**
	 * 是否含有新地址
	 * @return
	 */
	public static boolean hasNewUrl(){
		Iterator<String> it = new_url_set.iterator();
		return it.hasNext();
	}

}
