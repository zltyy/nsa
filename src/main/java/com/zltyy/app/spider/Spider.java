package com.zltyy.app.spider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zltyy.app.utils.HtmlManager;
import com.zltyy.app.utils.UrlManager;


public class Spider {
	
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String requestGet(String url) throws Exception{
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(50)
				.setConnectTimeout(50)
				.setSocketTimeout(50).build();
		httpGet.setConfig(requestConfig);
		
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(
                new InputStreamReader(httpEntity.getContent(), "utf-8"));
         
        String s = br.readLine();
		httpGet.releaseConnection();
		return s;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String requestPost(String url,List<NameValuePair> params) throws Exception {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	         
	    HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	         
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println("response status code:"+response.getStatusLine().getStatusCode());
	         
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	         
	        httppost.releaseConnection();
	        return jsonStr;
	}
	
	/**
	 * 返回这个标签所有属性及标签内容
	 * @param html
	 * @param tag
	 * @return
	 */
	public List<Object> parseHtml(String html,String tag){
		List<Object> list = new ArrayList<Object>();
		if(null != html && !"".equals(html)){
			Document dc = Jsoup.parse(html);
			Elements elements = dc.getElementsByTag(tag);
			for (Element e : elements) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("content",e.text());
				for (Attribute a : e.attributes()) {
					map.put(a.getKey(), a.getValue());
				}
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 启动爬取程序
	 * @param url
	 * @param tag
	 * @throws Exception
	 */
	public void startSpider(String url,String...tag) throws Exception{
		System.out.println("==="+tag.length);
		UrlManager.addNewUrl(url);
		URL u = new URL(url);
		String url_host = u.getHost();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String html = requestPost(url,params);
		List<Object> a_list = parseHtml(html,"a");
		List<Object> p_list = parseHtml(html,"P");
		for (Iterator<Object> iterator = a_list.iterator(); iterator.hasNext();) {
			Map<String, String> m = (Map<String, String>) iterator.next();
			if(null != m.get("href") && m.get("href").contains(url_host)){
				UrlManager.addNewUrl(m.get("href"));
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		for (Iterator<Object> iterator = p_list.iterator(); iterator.hasNext();) {
			Map<String, String> m_p = (Map<String, String>) iterator.next();
			System.out.println(m_p);
			if(null != m_p.get("content")){
				list.add(m_p.get("content"));
			}
			map.put(url, list);
		}
		HtmlManager.addContent(map);
	}
}
