package wiki;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import opencc.OpenCC;


public class WikiProcess {
	
	public static String originFilename = "";
	public static String outputFilename = "";
	
	public static int namespace = 0;	//namespace 
	
	public static boolean removeTitle = false;
	
	public static String title = "";
	
	public static String leftQuote="“";
	public static String rightQuote="”";
	
	public static OpenCC openCC = new OpenCC("tw2sp");
	
	public static void processData(){
		String line = "";
		String pageText = "";
		
		int docID = 0;
		int ns = 0;
		
		boolean writeFlag = false;
//		originFilename = "test.txt";////////////////
//		outputFilename = "out.txt"; ////////////////
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream (originFilename), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (outputFilename), "UTF-8"));
			
			while ((line = reader.readLine()) != null){
				if(line.equals("  <page>")){
					for(int i = 0;i<4; i++){
						line = reader.readLine();
						String strs[]=line.split("<|>");
						if(line.matches("    <id>.+"))		docID = Integer.parseInt(strs[2]);					
						if(line.matches("    <title>.+")) 	title = strs[2];
						if(line.matches("    <ns>.+")) 		ns = Integer.parseInt(strs[2]);				
					}
					
					if(title.matches("Unicode中日韩统一表意文字拓展.*")) {	
						writeFlag = false;
						continue;
					}
					
					if(ns == namespace){
						writeFlag=true;
						System.out.println(docID+"\t"+title);
						if(removeTitle==false){
							pageText += "\n\n<doc id=\""+docID+"\" url=\"https://zh.wikipedia.org/wiki?curid="+docID+"\" title=\""+title+"\">"+"\n";
						}
					}
				}

				
				if(writeFlag) pageText+=line+"\n";
					
				if(writeFlag && line.equals("  </page>")){
					
					pageText = preText(pageText);
					pageText = extractText(pageText);
					
					
					if(removeTitle==false) pageText+="</doc>";
					writer.write(pageText+"\n\n");
					writeFlag = false;
					pageText = "";
				}
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        System.out.println("error");
	        e.printStackTrace();
		}
	}
	
	/*===============================Pretreatment Start===============================================*/
	/**
	 * Preprocess the xml file to guarantee the line feed correctly
	 * @author Shu Jiang
	 * @param pageText from the original wiki xml file
	 * @return preText which line feeds correctly
	 * @version 1.0 (2017-03-23)
	 */
	public static String preText(String pageText){
		String preText = "";
		CheckMatches.clear();
		String lines[] = pageText.split("\n");
		for(String line: lines){
			if(Ending.checkEnding(line)) return preText; // read the ending label eg. reference

			line = Html.replaceHtml(line);
			
			CheckMatches.count(line);
			if(line.matches("==.*==")){
				CheckMatches.clear();
				pageText+="\n";
			}

			preText+=line;
			if(CheckMatches.equal()) preText+="\n";
		}
		return preText;	
	}
	/*===============================Pretreatment End===============================================*/
	
	
	/*===============================Extract Start===============================================*/
	/**
	 * Extract the correct sentences from the preText from the function preText()
	 * @author Shu Jiang
	 * @param preText from the function preText()
	 * @return finalText which extract from the Wiki
	 * @version 1.0 (2017-03-23)
	 */
	public static String extractText(String preText){
		String finalText = "";
		String lines[] = preText.split("\n");
		for(String line: lines){
			System.out.println(line); // origin line
			CheckMatches.clear();
			if(line.trim().matches("<doc id.*")) {finalText+=line+"\n";continue;}
			if(!CheckMatches.equal()) continue;
			if(checkUseless(line)) continue;

			
			
			line = removeBraces(line);
			

			System.out.println(line);

			line = removeLabel(line);
			
			line = Math.correctFormula(line);
			
			line = removeRef2(line);
			
			line = Template.removeTemplate(line);
			line = removeBraces2(line);
			
			line = removeBrackets2(line);
			line = replaceQuotes(line);
			line = replaceBrackets(line);
			line = correctPunc(line);
			
			line = Html.replaceHtml(line);
			
			line = tw2cn(line);
			
			line = correctFormat(line);
			
			line = line.replaceAll("\'\'\'?", "");
			
			if(line.trim().equals("")) continue;

//			System.out.println("final:"+line);
			
//			if(line.matches("[。？！；：）’”〗】〕〉》」』].*")) finalText+=line;
			finalText+=line+"\n";
				
		}
			
		return finalText;
	}
	/*===============================Extract End===============================================*/
	
	/**
	 * check the line which needs to be filtered out
	 * @author Shu Jiang
	 * @param line which to be checked whether need to filter
	 * @return finalText which extract from the Wiki
	 * @version 1.0 (2017-03-23)
	 */
	
	public static boolean checkUseless(String line){
		line = line.replaceAll("< *([A-Za-z &&[^=]])*/ *>", "");
		if(line.trim().matches("<text xml.*")) line=line.trim().replaceAll("<text xml.*>", "");
		
		if(line.toLowerCase().matches("\\[?\\[?(file|image|sk|category)\\:.*")) return true;	
		if(line.trim().matches("\\:?\\{\\|.*\\}")) return true;	
		if(line.trim().matches("<!--.*")) return true;
		if(line.trim().matches("<td.*")) return true;
		if(line.trim().matches("#REDIRECT.*")) return true;
		if(line.trim().matches("<.*>") && !line.trim().matches("</?ref.*")) return true;
		if(line.trim().matches("\\{\\|.*\\|\\}")) return true;
		if(line.trim().matches("\\{\\{.*\\}\\}")
				&& !line.matches(".*\\{\\{(lang|link|le|fact|doi|nowrap|audio|quotation|tsl|cquote).*")
				&& !(line.indexOf("\\}\\}")<line.lastIndexOf("\\{\\{"))) return true;
		if(line.trim().toLowerCase().matches("\\{\\{([A-Za-z]*box|各地中文名|.*信息框|selfref|update|noteta|國家基礎資訊).*")) return true;
		if(line.trim().toLowerCase().matches("\\{.*(wt)|(wikitable).*\\}")) return true;
		
		return false;
	}
	
	
	
	public	static String removeLabel(String line){
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
	//			System.out.println(label);
	//			System.out.println(line);
				int index = line.indexOf("<"+label);
	//			if(index == -1) index=0;
				int index1 = line.indexOf(">",index);
	//			if(index1 == -1) index1=line.length()-1;
	//			System.out.println(index);
	//			System.out.println(index1);
	//			System.out.println(line.substring(0,index));
	//			System.out.println(line.substring(index1+">".length()));
				line = line.replace("</"+label+">", "");
				if(index<index1 && index != -1 && index != -1)line = line.substring(0,index)+line.substring(index1+">".length());
				else break;
	//			System.out.println(line);
				
			}
		}
		
		return line;
	}
	
public	static String removeRef2(String line){
		String line1 = "";
		
		
		String strs[] = line.split("<|>");
		
		for (int i=0;i<strs.length;i++){
			if(strs[i].matches("!--.*--")) continue;
			if(strs[i].toLowerCase().matches("ref.*/")) continue;
			else if(i+1<strs.length && strs[i].toLowerCase().matches("ref.*") && strs[i+1].toLowerCase().equals("/ref")) i=i+1;
			else if(i+2<strs.length && strs[i].toLowerCase().matches("ref.*") && strs[i+2].toLowerCase().equals("/ref")) i=i+2;
			else if(i+3<strs.length && strs[i].toLowerCase().matches("ref.*") && strs[i+3].toLowerCase().equals("/ref")) i=i+3;
			else if(i+4<strs.length && strs[i].toLowerCase().matches("ref.*") && strs[i+4].toLowerCase().equals("/ref")) i=i+4;
			else if(i+5<strs.length && strs[i].toLowerCase().matches("ref.*") && strs[i+5].toLowerCase().equals("/ref")) i=i+5;
			else if(strs[i].toLowerCase().matches("/?ref.*")) continue;
			else line1=line1.concat(strs[i]);
		}
		return line1;
	}
	
public	static String removeBrackets2(String line1){	//remove [[ | ]] ''   
		String line2="";
		String[]  list = {"file","image","category","分类","分類"};
		if(line1.trim().toLowerCase().matches(".*\\[\\[.*(file|image|sk|category)\\:.*\\]\\].*")){
			for(String note : list){
				long startTime=System.nanoTime();
				while(line1.toLowerCase().trim().matches(".*\\[\\[ ?"+note+"[A-Za-z/\\- ]*\\:.*\\]\\].*")){
	//				System.out.println(line1);	
					int beginIndexA = line1.toLowerCase().indexOf("[["+note);	
	//				System.out.println(beginIndexA);
					if(beginIndexA == -1 ) beginIndexA = line1.toLowerCase().indexOf(note);
	//				System.out.println(beginIndexA);
					beginIndexA = line1.indexOf(":",beginIndexA);
	//				System.out.println("beginIndexA"+beginIndexA+" "+line1.substring(beginIndexA));
					int endIndexA = line1.indexOf("]]",beginIndexA);
	//				System.out.println("endIndexA"+endIndexA);
					String A = line1.substring(beginIndexA+(":").length(), endIndexA);
					while (Operation.countSubstr(A , "\\[\\[") != Operation.countSubstr(A , "\\]\\]")){				
						endIndexA = line1.indexOf("]]",endIndexA+1);
						A = line1.substring(beginIndexA+(":").length(), endIndexA);
						
					}
	//				System.out.println(A);
					A=Template.toRegex(A);
					line1=Template.replaceAll3(line1, "\\[\\[ ?"+note+"[A-Za-z/\\- ]*\\: *"+A+"\\]\\]","");
	//				System.out.println(line1);
					
					double sDuration = (System.nanoTime() - startTime)/1000000000;
					if(sDuration>300){
						System.out.println(line1);
						break;
					}
				}
			}
		}
		
		
		//if(line1.matches("\\[\\[Category:.*")) return line1;
		
		
		
		String strs1[] = line1.split("\\[\\[|\\]\\]");
		for (String str : strs1){
		
			str.replace(":en:", "");
			if(str.matches(".*\\{\\{.*\\}\\}.*")) str = removeBraces2(str);
			if(str.trim().toLowerCase().matches("(file|image|sk|category|分類|分类)\\:.*")) continue;
			if(str.matches(".*\\|.*")) str=str.substring(str.lastIndexOf("|")+1);
			line2=line2.concat(str);
			
		}
		//line2=line2.replaceAll("\\'", "");
		return line2;
	}
	
public	static String removeBraces2(String line){ //{{ }}
		if(!(line.contains("{{") || line.contains("}}"))) return line;
		if(line.matches("(\\*|\\:|\\#)?\\{\\{.*\\}\\}") && line.split("\\{\\{|\\}\\}").length==2) return "";
		if(line.trim().matches("(\\*|\\:|\\#)?\\{\\{.*\\}\\}") && line.indexOf("}}",line.lastIndexOf("{{"))<line.lastIndexOf("}}")) return "";
		if(line.trim().toLowerCase().matches("\\{\\{noteta.*\\}\\}") && line.split("\\{\\{|\\}\\}").length==2) return "";
	//	if(line.matches(".*\\[\\[.*\\|.*\\]\\].*")) line=removeBrackets2(line);
		
		String strs[] = line.split("\\{\\{|\\}\\}");
	//	System.out.println(strs.length);
	//	System.out.println(Arrays.toString(strs));
		
		String line0="";
		for (String str : strs){
	//		System.out.println("pre  "+str);
			//if(str.matches(".*\\|\\|.*")) str=str.substring(0,str.lastIndexOf("||"));
	
			if(str.toLowerCase().trim().matches("(cite|citation|dmoz|redirect|noteta|rp|sfn|ref|div|gb/t|expert|reftag|spoken|(r\\|)).*")) continue;
			else if(str.toLowerCase().trim().matches(".*(sidebar|navigation|expand|portal).*")) continue;
			else if(str.toLowerCase().trim().matches("死鏈|請求來源|请求来源|需要解释|who|weasel word|zwsp|dubious|fact|authority control|toc limit|or|clear|-|verify credibility|pow|kia"))continue;
			else if(str.toLowerCase().trim().matches("(further|main|主條目|参见|see|dablink|distinguish|efn).*")) continue;
			
			else if(str.toLowerCase().trim().matches("col\\-.*")) continue;
			else if(str.toLowerCase().trim().matches("(#tag|\\|).*")) continue;
			//System.out.println(str);
	//		if(str.matches(".*\\[\\[.*\\|.*\\]\\].*")) str=removeBrackets2(str);
	//		if(str.matches(".*\\[\\[.*(file|image|sk|category)\\:.*\\]\\].*")) str=removeBrackets2(str);
			if(str.matches(".*\\[\\[.*\\]\\].*")) str=removeBrackets2(str);
			
			if(str.toLowerCase().trim().matches("sfrac\\|.*")) {
				str.replaceAll("sfrac\\|", "");
				str.replaceAll("\\|", "/");	
			}
			
			else if(str.toLowerCase().trim().matches("gaps\\|.*")) {
				str.replaceAll("gaps\\|", "");
				str.replaceAll("\\|", "");
			}
			
			else if(str.toLowerCase().trim().matches("pagename")) {
				str=title;
			}
			
			else if(str.toLowerCase().matches("(cquote|quote|quotation)\\|.*")) {
				//System.out.println(countSubstr(str, "\\|"));
				if(countSubstr(str, "\\|")==1) str=leftQuote+str.substring(str.indexOf("|")+1)+rightQuote;
				else if(countSubstr(str, "\\|")>=2) str=leftQuote+str.substring(str.indexOf("|")+1,str.lastIndexOf("|"))+rightQuote+"——"+str.substring(str.lastIndexOf("|")+1);
				str=str.replaceAll("\\|", "，");
			}
			
			else if(str.matches("BD\\|.*")){
				str=str.replaceAll("\\|catIdx=.*", "");
				String tmp[]=str.split("\\|");
				System.out.println(Arrays.toString(tmp));
				if(tmp.length>=5) str=tmp[1]+tmp[2]+"-"+tmp[3]+tmp[4];
				else if(tmp.length>=4) str=tmp[1]+tmp[2]+"-"+tmp[3];
				else if(tmp.length>=3) str=tmp[1]+tmp[2]+"-";
				else str=str.replaceAll("|", "");
				str = "（"+str+"）";
			}
			
			else if(str.toLowerCase().matches("(bd|kj|cj|开始结束|成结|开结)\\|.*")){
				str=str.replaceAll("\\|catIdx=.*", "");
				String tmp[]=str.split("\\|");
	//			System.out.println(Arrays.toString(tmp));
				if(tmp.length>=5) str=tmp[1]+tmp[2]+"-"+tmp[3]+tmp[4];
				else if(tmp.length>=4) str=tmp[1]+tmp[2]+"-"+tmp[3];
				else if(tmp.length>=3) str=tmp[1]+tmp[2]+"-";
				else str=str.replaceAll("|", "");
			}
			
			else if(str.toLowerCase().matches("(link-.*|le|.*-link)\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str=tmp[1];
				str=str.replaceAll("\\|", "，");
			}
			
			else if(str.toLowerCase().matches("(fact|subst:fact/auto)\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str=tmp[1];
				if(str.matches("time.*")) str="";
				str=str.replaceAll("\\|", "，");
			}
			
			else if(str.toLowerCase().matches("script\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>2) str=tmp[2];
				str=str.replaceAll("\\|", "，");
			}
			
			else if(str.toLowerCase().matches("(反應式|反应式)\\|.*")){
				String tmp[]=str.split("\\|");
				
				String tmpstr ="";
				for(int i = 1; i< tmp.length; i++){
					if(tmp[i].equals("eq")) tmp[i]="→";
					tmpstr+=tmp[i];	
				}
				str = tmpstr;			
			}
			
			else if(str.toLowerCase().matches("ipa.*\\|.*")){
				String tmp[]=str.split("\\|");
				String tmpstr ="";
				for(int i = 1; i< tmp.length; i++){
					if(!tmp[i].toLowerCase().matches(" *audio=.*") 
							&& !tmp[i].toLowerCase().matches(".*\\.og[a-z]") 
							&& !tmp[i].toLowerCase().matches("ipa.*")) 
						tmpstr+=tmp[i];					
				}
				str = tmpstr;
			}
			
			else if(str.toLowerCase().matches("bo\\|.*")){
				String tmp[]=str.split("\\|");
				for(int i = 1; i< tmp.length; i++){
					if(tmp[i].toLowerCase().matches("(w|p|t)=.*")){
						str = tmp[i].substring(2);
						break;
					}
				}
			}
			
			else if(str.toLowerCase().matches("mong\\|.*")){
				String tmp[]=str.split("\\|");
				for(int i = 1; i< tmp.length; i++){
					if(tmp[i].toLowerCase().matches("(m|k|z|n)=.*")){
						str = tmp[i].substring(2);
						break;
					}
				}
			}
			
			else if(str.toLowerCase().matches("kor\\|.*")){
				String tmp[]=str.split("\\|");
				for(int i = 1; i< tmp.length; i++){
					if(tmp[i].toLowerCase().matches("(諺|漢|羅)=.*")){
						str = tmp[i].substring(2);
						break;
					}
				}
			}
			
			else if(str.toLowerCase().matches("lang\\-.*\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str = tmp[1];		
				else str="";
			}
			
			else if(str.toLowerCase().matches(".*unicode\\|.*")){
				String tmp[]=str.split("\\|");
				str="";
				for(int i = 1; i< tmp.length; i++){
					if(!tmp[i].contains("=")) {
						str = tmp[i];
						break;
					}
				}
			}
			
			else if(str.toLowerCase().matches("convert\\|.*")){
				String tmp[]=str.split("\\|");
				String tmpstr ="";
				for(int i = 1; i< tmp.length; i++){
					if(tmp[i].toLowerCase().matches("[0-9\\.\\-]+") && i<tmp.length-1){
						tmp[i+1] = tmp[i+1].replaceAll("C", "°C");
						tmpstr += tmp[i]+tmp[i+1];
						i++;
					}
					else break;
				}
				str = tmpstr;
			}
			
			
			
			else if(str.toLowerCase().matches("(台北聯營|新北市轄|臺北聯營)\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str = tmp[1];		
			}
			
			else if(str.toLowerCase().matches("math\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str = tmp[1];		
			}
			
			else if(str.toLowerCase().matches("smn\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str = tmp[1];		
			}
			
			else if(str.toLowerCase().matches("sehk\\|.*")){
				String tmp[]=str.split("\\|");
				if(tmp.length>=2) str = "港交所："+tmp[1];		
			}
			
			
			else if(str.matches("(中国国道名|中国省道名|China Expwy Name)\\|.*")){				
				String tmp[]=str.split("\\|");
				if(tmp[0].equals("中国国道名") && tmp.length==2) str=tmp[1]+"国道";
				else if(tmp[0].equals("中国国道名") && tmp.length==3) str="G"+tmp[1]+tmp[2];
				else if(tmp[0].equals("中国省道名") && tmp.length==2) str=tmp[1]+"省道";
				else if(tmp[0].equals("中国省道名") && tmp.length==3) str="S"+tmp[1]+tmp[2];
				else if(tmp[0].equals("China Expwy Name") && tmp.length==2) str=tmp[1];
				else if(tmp[0].equals("China Expwy Name") && tmp.length>=3 && tmp[2].matches(".*=.*")) str=tmp[1];
				else if(tmp[0].equals("China Expwy Name") && tmp.length>=3) str=tmp[1]+tmp[2];
	//			System.out.println(Arrays.toString(tmp));
				
				else str=str.replaceAll("(中国国道名|中国省道名|China Expwy Name)\\|", "");
			}
			
			else if(str.toLowerCase().matches(".*(lang|link|le|doi|nowrap|audio|tsl|serif|polytonic).*\\|.*")) {
				str=str.substring(str.lastIndexOf("|")+1);
				if(str.contains("=")) str="";
			}
			
			else if(str.toLowerCase().trim().matches("(en|de|fr|ja|zh-hans|zh-hant|zh-tw|zh-cn|zh-yue|yue|zh|cn|es|ko).*|(.* icon)"))continue; //language tag
			else if(Template.TemplateList.containsKey(str.toLowerCase())) str=Template.TemplateList.get(str.toLowerCase());
			
			
			else if(str.toLowerCase().matches(".*(which).*\\|.*\\|.*")) str=str.substring(str.indexOf("|")+1,str.lastIndexOf("|"));
			else if(str.toLowerCase().matches(".*(which).*\\|.*")) str=str.substring(str.indexOf("|")+1);
			else if(str.toLowerCase().matches("车站编号\\|.*")) str=str.substring(str.lastIndexOf("|")+1);
			
			else if(str.toLowerCase().trim().matches("[A-Za-z0-9:-= ]*\\|.*")) str="";
			
			if(str.matches(".*time=.*")) str="";
			if(str.matches("[A-Za-z0-9]+=[A-Za-z0-9]+")) str="";
	//		System.out.println("post "+str);
			line0=line0.concat(str);
		}
		if(line0.matches("(\\*|\\:|\\#) *")) line0="";
		return line0;
	}
	
	static String replaceQuotes(String line3){
		String line4 = "";
		String strs[]=line3.split("\"");
		if(strs.length>1){
			for (int i=0;i<strs.length;i++){
				//System.out.println(i+": "+strs[i]);
				line4=line4.concat(strs[i]);
	
				if(i%2==0 && i<strs.length-1) line4=line4.concat(leftQuote);
				else if(i%2==1) line4=line4.concat(rightQuote);
			}
		}
		else line4 = line3;
	
		line4 = line4.replaceAll("「|“", leftQuote);
		line4 = line4.replaceAll("」|”", rightQuote);	
		line4 = line4.replaceAll("'", "");
		return line4;
	}
	
	static String replaceBrackets(String line4){
		String line5 = "";
		if(!line4.matches("(.*\\[.*\\://.*\\s.*\\].*)|(.*\\[http.*)")) line5 = line4;
		else{
			String strs1[] = line4.split("\\[|\\]");
			
			for (String str : strs1){
				
				if(str.matches("(http|news).* .*")) str = str.substring(str.indexOf(" ")+1);
				else if(str.matches("http://.*")) str ="";
				else if(str.matches(".*/ .*")) str=str.substring(str.lastIndexOf("/ ")+2);
				else if(str.matches(".*tab=summary.*")) str=str.substring(str.lastIndexOf("tab=summary ")+12);
				line5=line5.concat(str);
			}
		}
		return line5;
	}

	public static String removeBraces (String line2){//-{zh-cn:..;zh-:..;zh-hans:}-		
		String line3="";
		if(line2.matches(".*-\\{.*\\}-.*")) {
			line2 = line2.replace("{{", "&#123;&#123;");
			line2 = line2.replace("}}", "&#125;&#125;");
	//		System.out.println("line2:"+line2);
			String strs1[] = line2.split("-\\{|\\}-");
	//		System.out.println(Arrays.toString(strs1));
			for (int i=0;i<strs1.length; i++){
				if(strs1[i].matches(".*(zh-hans|zh-hant|zh-cn|zh-tw|zh-hk|zh-sg|zh-mo)\\:.*\\;.*")) {
					int beginIndex=0;
					if(strs1[i].contains("zh-cn:")) beginIndex = strs1[i].indexOf("zh-cn:")+"zh-cn:".length();
					else if(strs1[i].contains("zh-hans:")) beginIndex = strs1[i].indexOf("zh-hans:")+"zh-hans:".length();
					else if(strs1[i].contains("zh:")) beginIndex = strs1[i].indexOf("zh:")+"zh:".length();
					int endIndex =0;
					if(strs1[i].indexOf(";", beginIndex)>=0) endIndex = strs1[i].indexOf(";", beginIndex);
					else endIndex = strs1[i].length()-1;
	//				System.out.println("beginIndex"+beginIndex);
	//				System.out.println("endIndex"+endIndex);
					
					if(endIndex<beginIndex){
						if(i>=1 && strs1[i-1].matches(".*\\[\\[.*\\|")){
							beginIndex = strs1[i-1].lastIndexOf("[[")+"[[".length();
							endIndex = strs1[i-1].lastIndexOf("|");
							strs1[i] = strs1[i-1].substring(beginIndex, endIndex);
						}
						else strs1[i]="";
					}
					else strs1[i]=strs1[i].substring(beginIndex, endIndex);
					
	//				System.out.println(strs1[i]);
				}
				line3=line3.concat(strs1[i]);
			}
		}
		else line3=line2;
		
		line3 = line3.replace("&#123;", "{");
		line3 = line3.replace("&#125;", "}");
	//	System.out.println("line3:"+line3);
		return line3;
	}


	public static String correctPunc(String line){
		line = line.replaceAll("\\.\\.\\.", "…");
		line = line.replaceAll("〈", "《");
		line = line.replaceAll("〉", "》");
		line = line.replaceAll(" :", "：");
		line = line.replaceAll(" ;", "；");
		return line;
	}

	public static String tw2cn(String line){ //tw->cn
		line = openCC.convert(line);
		line=line.replaceAll("什幺", "什么");
		return line;
	}

	public static String correctFormat(String line) {
		if(line.startsWith("*")) line = line.replaceAll("^\\* *", "\\*");
		else if(line.startsWith("#")) line = line.replaceAll("^\\# *", "\\#");
		else if(line.startsWith(";")) line = line.replaceAll("^\\; *", "");
		else if(line.trim().startsWith(":::")) line = line.trim().replaceAll("^:::", "      ");
		else if(line.trim().startsWith("::")) line = line.trim().replaceAll("^::", "    ");
		else if(line.trim().startsWith(":")) line = line.trim().replaceAll("^:", "  ");
		else if(line.trim().startsWith(":*")) line = line.trim().replaceAll("^:*", "      ");
		return line;
	}
	
	public static int countSubstr(String line,String punc){
		return (line.length()-line.replace(punc, "").length())/punc.length();
	}
}
