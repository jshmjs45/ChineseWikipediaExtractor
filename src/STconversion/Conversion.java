package STconversion;

import opencc.OpenCC;

public class Conversion {

	public static OpenCC s2t = new OpenCC("s2t");		//Simplified Chinese to Traditional Chinese
	public static OpenCC t2s = new OpenCC("t2s"); 		//Traditional Chinese to Simplified Chinese
	public static OpenCC s2tw = new OpenCC("s2tw");		//Simplified Chinese to Traditional Chinese (Taiwan Standard)
	public static OpenCC tw2s = new OpenCC("tw2s");		//Traditional Chinese (Taiwan Standard) to Simplified Chinese
	public static OpenCC s2hk = new OpenCC("s2hk");		//Simplified Chinese to Traditional Chinese (Hong Kong Standard) 
	public static OpenCC hk2s = new OpenCC("hk2s");		//Traditional Chinese (Hong Kong Standard) to Simplified Chinese
	public static OpenCC s2twp = new OpenCC("s2twp");	//Simplified Chinese to Traditional Chinese (Taiwan Standard) with Taiwanese idiom
	public static OpenCC tw2sp = new OpenCC("tw2sp");	//Traditional Chinese (Taiwan Standard) to Simplified Chinese with Mainland Chinese idiom
	public static OpenCC t2tw = new OpenCC("t2tw");		//Traditional Chinese (OpenCC Standard) to Taiwan Standard 
	public static OpenCC t2hk = new OpenCC("t2tw");		//Traditional Chinese (OpenCC Standard) to Hong Kong Standard 
	
	public static String toSimplifiledChinese(String line){
		line = t2tw.convert(line);
		line = tw2s.convert(line);
		if(line.contains("幺")){
			line=line.replaceAll("什幺", "什么");
			line=line.replaceAll("那幺", "那么");
			line=line.replaceAll("这幺", "这么");
			line=line.replaceAll("怎幺", "怎么");
		}
		if(line.contains("着")){
			line=line.replaceAll("着名","著名");
			line=line.replaceAll("着录","著录");
			line=line.replaceAll("着称","著称");
			line=line.replaceAll("着述","著述");
			line=line.replaceAll("着者","著者");
			line=line.replaceAll("着作","著作");
			line=line.replaceAll("着书","著书");
			line=line.replaceAll("着录","著录");
			line=line.replaceAll("着文","著文");
			line=line.replaceAll("显着","显著");
			line=line.replaceAll("着有","著有");
			line=line.replaceAll("土着","土著");
			line=line.replaceAll("原着","原著");
			line=line.replaceAll("专着","专著");
			line=line.replaceAll("所着","所著");
			line=line.replaceAll("巨着","巨著");
			line=line.replaceAll("译着","译著");
			line=line.replaceAll("遗着","遗著");
			line=line.replaceAll("名着","名著");
			line=line.replaceAll("合着","合著");
			line=line.replaceAll("编着","编著");
			line=line.replaceAll("卓着","卓著");
			line=line.replaceAll("论着","论著");
			line=line.replaceAll("臭名昭着","臭名昭著");
		}
		return line;
	}
	
	public static String toTraditionalChinese(String line){
		line = s2t.convert(line);

		return line;
	}
	
	public static String toTWTraditionalChinese(String line){
		line = s2tw.convert(line);
		line = t2tw.convert(line);

		return line;
	}
	
	public static String toHKTraditionalChinese(String line){
		line = s2hk.convert(line);
		line = t2hk.convert(line);

		return line;
	}
	
}
