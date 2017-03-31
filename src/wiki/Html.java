package wiki;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Html {
	public static Map<String,String> HtmlMap =  new HashMap<String,String>();
	public static ArrayList<String> HtmlList =  new ArrayList<String>();
	
	static{
		String line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("corpus/HtmlEntities.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				String strs[] = line.split("\t");
				if(strs.length>1) {
					HtmlMap.put(strs[0], strs[1]);	
					HtmlList.add(strs[0]);
				}
			}
			reader.close();
		
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        System.out.println("error");
	        e.printStackTrace();
		}
	}
	
	/**Replace the Html escaped characters
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-24)
	 * @param line
	 */
	public static String replaceHtml(String line){ // "&lt;...&gt;" -> "<...>" 
//		System.out.println(line);
		for(String str: HtmlList){	
			if(line.matches(".*"+str+".*")){
				line=line.replaceAll(str, HtmlMap.get(str));
			}	
		}
		
		long startTime=System.nanoTime();
		
		while(line.matches(".*[^>](&#[0-9]+;)[^<].*")){
			int ascNum = 0;
			int beginIndex = line.indexOf("&#");
			int endIndex = line.indexOf(";", beginIndex);
			String ascStr = line.substring(beginIndex+"&#".length(), endIndex);
			
			if(ascStr.startsWith("x") && ascStr.substring(1).matches("[0-9]+")) ascNum=Integer.parseInt(Integer.valueOf(ascStr.substring(1),16).toString());
			else if(ascStr.matches("[0-9]+")) ascNum = Integer.parseInt(ascStr);

			String ascChar = String.valueOf((char)ascNum) ;

			line =line.replace("&#"+ascStr+";", ascChar);

			double sDuration = (System.nanoTime() - startTime)/1000000000;
			if(sDuration>60){
//				System.out.println(line);
				break;
			}
		}
		return line;
	}
	
	/**Replace useless Html labels
	 * @author Shu Jiang
	 * @version 1.0 (2017-03-24)
	 * @param line
	 */
	public	static String removeLabels(String line){
		String[] labelList = {"font","span","nowiki","tt","cite"}; 
	
		line = line.replaceAll("< ?w?br ?/? ?>", "");
		line = line.replaceAll("< ?/? ?text ?>", "");
		line = line.replaceAll("< ?/? ?center ?>", "");
		line = line.replaceAll("<!-.*-->", "");
		line = line.replaceAll("</?blockquote>", "");
		line = line.replaceAll("</?p>", "");
		line = line.replaceAll("</?sup>", "");
		line = line.replaceAll("</?sub>", "");
		line = line.replaceAll("</?small>", "");
		line = line.replaceAll("</?big>", "");
		line = line.replaceAll("</?td>", "");
		line = line.replaceAll("</?tr>", "");
		line = line.replaceAll("</?code>", "");	
		line = line.replaceAll("</?s>", "");	
		line = line.replaceAll("</?nowiki>", "");
		
		for(String label:labelList){
			
			while(line.contains("<"+label)){
				int index = line.indexOf("<"+label);
	//			if(index == -1) index=0;
				int index1 = line.indexOf(">",index);
	//			if(index1 == -1) index1=line.length()-1;
				line = line.replace("</"+label+">", "");
				if(index<index1 && index != -1 && index != -1)line = line.substring(0,index)+line.substring(index1+">".length());
				else break;
			}
		}
		return line;
	}
}
