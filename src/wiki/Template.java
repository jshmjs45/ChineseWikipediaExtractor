package wiki;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Date; 
import java.util.Calendar; 

/**
 * A template is a Wikipedia page created to be included in other pages. 
 * @author Shu Jiang
 * @version 1.0 (2017-03-24)
 * @see https://en.wikipedia.org/wiki/Help:Template
 */
public class Template {
	
	public static Map<String,String> TemplateList =  new HashMap<String,String>();
	public static Set<String> NoteList =  new HashSet<String>();
	
	static{
		String line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("corpus/TemplateList.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				String strs[] = line.split("\t");
				if(strs.length>1) TemplateList.put(strs[0], strs[1]);
				else if(strs.length==1) TemplateList.put(strs[0], "");
				
			}
			reader.close();
			
			reader = new BufferedReader (new InputStreamReader (new FileInputStream ("corpus/NoteList.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				if(!line.equals("")) NoteList.add(line);
			}
			reader.close();
		
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        System.out.println("error");
	        e.printStackTrace();
		}
	}
	
	/**
	 * remove or replace the templates
	 * @author Shu Jiang
	 * @param line from pre-processed text
	 * @version 1.0 (2017-03-24)
	 */
	public static String removeTemplate(String line){

		for(String temp:TemplateList.keySet()){
	        line = replaceAll3(line, "\\{\\{"+temp+"\\}\\}", TemplateList.get(temp));  
		}

		for(String note: NoteList){ // cite note 
			long startTime=System.nanoTime();
			
			while(line.toLowerCase().trim().matches(".*\\{\\{ ?"+note+"[A-Za-z/\\- ]*\\|.*\\}\\}.*")){
				
				int beginIndexA = line.toLowerCase().indexOf("{{"+note);
				if(beginIndexA == -1 ) beginIndexA = line.toLowerCase().indexOf(note);
				beginIndexA = line.indexOf("|",beginIndexA);
				int endIndexA = line.indexOf("}}",beginIndexA);

				String A = line.substring(beginIndexA+("|").length(), endIndexA);
				while (Operation.countSubstr(A , "\\{\\{") != Operation.countSubstr(A , "\\}\\}")){				
					endIndexA = line.indexOf("}}",endIndexA+1);
					A = line.substring(beginIndexA+("|").length(), endIndexA);
					
				}

				A=toRegex(A);
				line=replaceAll3(line, "\\{\\{ ?"+note+"[A-Za-z/\\- ]*\\| *"+A+"\\}\\}","");
				
				double sDuration = (System.nanoTime() - startTime)/1000000000;
				if(sDuration>300){
					System.out.println(line);
					break;
				}
			}
		}
		return line;
	}

	
	/*** 
     * replaceAll,CASE_INSENSITIVE
     *  
     * @param input 
     * @param regex 
     * @param replacement 
     * @return output
     */  
    public static String replaceAll3(String input, String regex, String replacement) {  
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(input);  
        String result = m.replaceAll(replacement);  
        return result;  
    }  
    
    /** 
     * 转义正则特殊字符 （$()*+.[]?\^{},|） 
     *  
     * @param keyword 
     * @return 
     */  
    public static String toRegex(String keyword) {  
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
