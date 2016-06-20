package com.zltyy.app.analysis;


import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

public class DicAnalysisTest {

	public static void main(String[] args) throws Exception {
		
		String str = "A股第一神忽悠被罚60万!是不是少写个0？";
		String path = "src/main/resources/dic/stock_jrj.dic";
		Forest forest = Library.makeForest(path);
		Library.insertWord(forest, new Value("A股","define","1000"));
		Result to = ToAnalysis.parse(str, forest);
		Result dic = DicAnalysis.parse(str, forest);
		Result nlp = NlpAnalysis.parse(str, forest);
		System.out.println(to);
		System.out.println(dic);
		System.out.println(nlp);
	}
}
