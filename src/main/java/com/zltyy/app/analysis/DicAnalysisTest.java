package com.zltyy.app.analysis;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

public class DicAnalysisTest {

	public static void main(String[] args) throws Exception {
		
		String str = "A股第一神忽悠被罚60万!是不是少写个0？还是";
		String path = "src/main/resources/dic/jrj_news_common.dic";
		Forest forest = Library.makeForest(path);
//		Library.insertWord(forest, new Value("A股","define","1000"));
		Result to = ToAnalysis.parse(str, forest);
		Map<String, Integer> m = new HashMap<String, Integer>();
		Iterator<Term> it = to.iterator();
		while(it.hasNext()){
			Term t = it.next();
			if(m.containsKey(t.getName())){
				m.put(t.getName(), m.get(t.getName())+1);
			}else{
				m.put(t.getName(), 1);
			}
		}
		System.out.println(m);
		Result dic = DicAnalysis.parse(str, forest);
		Result nlp = NlpAnalysis.parse(str, forest);
		System.out.println(dic);
		System.out.println(nlp);
	}
}
