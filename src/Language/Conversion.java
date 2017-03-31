package Language;

import java.util.Arrays;
import java.util.HashMap;

import opencc.OpenCC;
/**
 * Chinese conversion between Traditional Chinese and Simplified Chinese. (Based on OpenCC)
 * Fix the conversion problems on "著", "幺"
 * @author Shu Jiang
 * @version 1.0 (2017-03-24)
 * @see https://github.com/BYVoid/OpenCC
 */
public class Conversion {

	public static final int Simplified = 0;
	public static final int Traditional = 1;
	public static final int TWtraditional = 2;
	public static final int HKtraditional = 3;
	
	static HashMap<String, String> hanthans = new HashMap<String, String>();
	static HashMap<String, String> hanshant = new HashMap<String, String>();
	static HashMap<String, String> cntw = new HashMap<String, String>();
	static HashMap<String, String> cnhk = new HashMap<String, String>();

	
	static OpenCC s2t = new OpenCC("s2t");		//Simplified Chinese to Traditional Chinese
	static OpenCC t2s = new OpenCC("t2s"); 		//Traditional Chinese to Simplified Chinese
	static OpenCC s2tw = new OpenCC("s2tw");		//Simplified Chinese to Traditional Chinese (Taiwan Standard)
	static OpenCC tw2s = new OpenCC("tw2s");		//Traditional Chinese (Taiwan Standard) to Simplified Chinese
	static OpenCC s2hk = new OpenCC("s2hk");		//Simplified Chinese to Traditional Chinese (Hong Kong Standard) 
	static OpenCC hk2s = new OpenCC("hk2s");		//Traditional Chinese (Hong Kong Standard) to Simplified Chinese
	static OpenCC s2twp = new OpenCC("s2twp");	//Simplified Chinese to Traditional Chinese (Taiwan Standard) with Taiwanese idiom
	static OpenCC tw2sp = new OpenCC("tw2sp");	//Traditional Chinese (Taiwan Standard) to Simplified Chinese with Mainland Chinese idiom
	static OpenCC t2tw = new OpenCC("t2tw");		//Traditional Chinese (OpenCC Standard) to Taiwan Standard 
	static OpenCC t2hk = new OpenCC("t2tw");		//Traditional Chinese (OpenCC Standard) to Hong Kong Standard 
	
	static String toSimplifiledChinese(String line){
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
	
	static String toTraditionalChinese(String line){
		line = s2t.convert(line);
		return line;
	}
	
	static String toTWTraditionalChinese(String line){
		line = s2tw.convert(line);
		line = t2tw.convert(line);
		return line;
	}
	
	static String toHKTraditionalChinese(String line){
		line = s2hk.convert(line);
		line = t2hk.convert(line);
		return line;
	}
	
	/**
	 * Chinese conversion between Traditional Chinese and Simplified Chinese
	 * @param line which need to convert
	 * @param mode (Conversion.Simplified", Conversion.Traditional", Conversion.TWtraditional, Conversion.HKtraditional)
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-24)
	 * @see https://github.com/BYVoid/OpenCC
	 */
	public static String convert(String line, int mode){
		line = toTraditionalChinese(line);
		if(!hanthans.isEmpty()){
			for(String hant:hanthans.keySet()){
				if(line.contains(hant)) line = line.replace(hant, hanthans.get(hant));
			}
		}
		
		line = toSimplifiledChinese(line);
		
		if(mode == Simplified) return line;
		
		if(mode == Traditional){
			if(!hanshant.isEmpty()){
				for(String hans:hanshant.keySet()){
					if(line.contains(hans)) line = line.replace(hans, hanshant.get(hans));
				}
			}
			line = toTraditionalChinese(line);
		}
		if(mode == TWtraditional){
			if(!cntw.isEmpty()){
				for(String hans:cntw.keySet()){
					if(line.contains(hans)) line = line.replace(hans, cntw.get(hans));
				}
			}
			if(!hanshant.isEmpty()){
				for(String hans:hanshant.keySet()){
					if(line.contains(hans)) line = line.replace(hans, hanshant.get(hans));
				}
			}
			line = toTWTraditionalChinese(line);
		}
		if(mode == HKtraditional) {
			if(!cnhk.isEmpty()){
				for(String hans:cnhk.keySet()){
					if(line.contains(hans)) line = line.replace(hans, cnhk.get(hans));
				}
			}
			if(!hanshant.isEmpty()){
				for(String hans:hanshant.keySet()){
					if(line.contains(hans)) line = line.replace(hans, hanshant.get(hans));
				}
			}
			line = toHKTraditionalChinese(line);
		}
		return line;
		
	}
	
	/**
	 * clear the HashMap hanthans, hanshant, cntw, cnhk
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-24)
	 */
	public static void clearMap(){
		hanthans.clear();
		hanshant.clear();
		cntw.clear();
		cnhk.clear();
	}
	
	/**
	 * Record the Chinese conversion in each page and store then in HashMap hanthans, hanshant, cntw, cnhk
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-24)
	 */
	public static void recordConversion(String line){
		if(line.toLowerCase().matches("( *<text.*>)?\\{\\{noteta.*zh-.*")){
//			System.out.println(line);
			String strs[] = line.split("\\|[0-9A-Z]+ ?= ?");
//			System.out.println(Arrays.toString(strs));
			for(String str : strs){
				String hans = null, hant = null, hk = null,tw = null;
				if(str.matches(".*(zh-hans|zh-hant|zh-cn|zh-tw|zh-hk)\\:.*\\;.*")){
					if(str.contains("zh-hans:")) {
						hans = extractChinese(str,"zh-hans:");
//						System.out.println(hans);
					}
					if(str.contains("zh-hant:")) {
						hant = extractChinese(str,"zh-hant:");
//						System.out.println(hant);
					}
					if(str.contains("zh-cn:")) {
						hans = extractChinese(str,"zh-cn:");
//						System.out.println(hans);
					}
					if(str.contains("zh-tw:")) {
						tw = extractChinese(str,"zh-tw:");
//						System.out.println(tw);
					}
					if(str.contains("zh-hk:")) {
						hk = extractChinese(str,"zh-hk:");
//						System.out.println(hk);
					}
				}
				if(hans!=null && hant!=null && !hans.equals(hant)) hanshant.put(hans, hant);
				if(hans!=null && hk!=null && !hans.equals(hk)) cnhk.put(hans, hk);
				if(hans!=null && tw!=null && !hans.equals(tw)) cntw.put(hans, tw);
				if(hans!=null && hant!=null && !hans.equals(hant)) hanthans.put(hant, hans);
				if(hans!=null && hk!=null && !hans.equals(hk)) hanthans.put(hk, hans);
				if(hans!=null && tw!=null && !hans.equals(tw)) hanthans.put(tw, hans);
			}
//			System.out.println("hanthans:"+hanthans);
//			System.out.println("hanshant:"+hanshant);
//			System.out.println("cnhk:"+cnhk);
//			System.out.println("cntw:"+cntw);
		}
	}
	
	static String extractChinese(String str, String mode){
		int beginIndex = str.indexOf(mode)+mode.length();
		int endIndex = str.indexOf(";", beginIndex);
		if(str.indexOf(";", beginIndex)<0) endIndex = str.length()-1;
		String sub = str.substring(beginIndex, endIndex);
		return sub;
	}
	
	/**
	 * extract the conversion content in the label "-{...}- and remove the label"
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-30)
	 */
	public static String removeConversionLabel(String line, int languageMode){
		String subString = "";
		String convertedLine = convert(line,languageMode);
		try{
			while(line.matches(".*-\\{.*\\}-.*")){
			String subLine = extractSubstring(line,"-{", "}-");
			if(languageMode == Conversion.Simplified){
				if(subLine.contains("zh-hans:")){
					subString = extractSubstring(subLine,"zh-hans:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-hans:");
				}
				if(subString=="" && subLine.contains("zh-cn:")){
					subString = extractSubstring(subLine,"zh-cn:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-cn:");
				}
			}
			if(languageMode == Conversion.Traditional){
				if(subLine.contains("zh-hant:")){
					subString = extractSubstring(subLine,"zh-hant:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-hant:");
				}
			}
			if(languageMode == Conversion.TWtraditional){
				if(subLine.contains("zh-tw:")){
					subString = extractSubstring(subLine,"zh-tw:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-tw:");

				}
				if(subString=="" && subLine.contains("zh-hant:")){
					subString = extractSubstring(subLine,"zh-hant:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-hant:");
				}
			}
			if(languageMode == Conversion.HKtraditional){
				if(subLine.contains("zh-hk:")){
					subString = extractSubstring(subLine,"zh-hk:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-hk:");
				}
				if(subString=="" && subLine.contains("zh-hant:")){
					subString = extractSubstring(subLine,"zh-hant:",";");
					if(subString==null) subString = extractSubstring(subLine,"zh-hant:");
				}
			}
			line = convert(line,languageMode);
		    subLine = convert(subLine,languageMode);
			line = convertedLine.replaceAll("-\\{"+toRegex(subLine)+"\\}-", subString);
			convertedLine  = line;
//			System.out.println(line+"\n");
			}
		} catch (Exception e) {
	        // TODO Auto-generated catch block
			System.out.println(line);
	        System.out.println("error");
	        e.printStackTrace();
		}
		
		return line;
		
	}
	
	
	static String extractSubstring(String line, String beginStr, String endStr){
		String subString = null;
		if(line.matches(".*"+toRegex(beginStr)+".*"+toRegex(endStr)+".*")){
			int beginIndex = line.indexOf(beginStr)+beginStr.length();
			int endIndex = line.indexOf(endStr, beginIndex);
			subString = line.substring(beginIndex, endIndex);
		}
		return subString;
	}
	
	static String extractSubstring(String line, String beginStr){
		String subString = null;
		if(line.matches(".*"+toRegex(beginStr)+".*")){
			int beginIndex = line.indexOf(beginStr)+beginStr.length();
			subString = line.substring(beginIndex);
		}
		return subString;
	}
	
    /** 
     * 转义正则特殊字符 （$()*+.[]?\^{},|） 
     * @param keyword 
     * @return 
     */  
    static String toRegex(String keyword) {  
        if (keyword.length()!=0) {  
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
            for (String key : fbsArr) {  
                if (keyword.contains(key)) {  
                    keyword = keyword.replace(key, "\\" + key);  
                }  
            }  
        }  
        return keyword;  
    }
}
