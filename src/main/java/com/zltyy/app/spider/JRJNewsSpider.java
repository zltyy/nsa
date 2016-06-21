package com.zltyy.app.spider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

import com.zltyy.app.utils.HtmlManager;


public class JRJNewsSpider extends Spider{

	public static void main(String[] args) throws Exception {
		String url = "http://finance.jrj.com.cn/2016/06/21084521092799.shtml";
		JRJNewsSpider jrj = new JRJNewsSpider();
		jrj.startSpider(url,"a");
		Map<String, List<String>> map = HtmlManager.getContent();
		List<String> list = map.get(url);
		
		String path = "src/main/resources/dic/stock_jrj.dic";
		Forest forest = Library.makeForest(path);
		Map<String, Integer> m = new HashMap<String, Integer>();
		for (String str : list) {
			Result to = ToAnalysis.parse(str, forest);
			Iterator<Term> it = to.iterator();
			while(it.hasNext()){
				Term t = it.next();
				if(m.containsKey(t.getName())){
					m.put(t.getName(), m.get(t.getName())+1);
				}else{
					m.put(t.getName(), 1);
				}
			}
		}
		System.out.println(m);
	}
	
}
