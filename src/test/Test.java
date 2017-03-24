package test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import opencc.OpenCC;
import wiki.Ending;
import wiki.Html;
import wiki.Math;
import wiki.Operation;
import wiki.Template;
import wiki.*;

public class Test {
	public static String originFilename = "F:/百度云同步盘/香港城大/wiki/zhwiki20160820_xml/zhwiki-20160820-pages-meta-current1.xml";
	public static String testFilename = "test.txt";
	public static String testoutFilename = "test-out.txt";
	public static int PageNo = 0;
	public static int docID = 0;
	public static int ns = -1;
	public static String url = "";
	public static String title = "";
	
	public static boolean writeFlag = false;
	
	public static Map<String, ArrayList<String>> TitleCategory = new HashMap<String, ArrayList<String>>();
	public static Map<Integer, String> IDTitle = new HashMap<Integer, String>();
	

	
	public static OpenCC openCC = new OpenCC("tw2s");
	
	public static void testReadfile(){
		String line = "";
		int leftBraceNum = 0;
		int rightBraceNum = 0;
		int leftBracketNum = 0;
		int rightBracketNum = 0;
		int leftAngleNum = 0;
		int rightAngleNum = 0;
		int leftRefNum = 0;
		int rightRefNum = 0;
		
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream (testFilename), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (testoutFilename), "UTF-8"));
			while ((line = reader.readLine()) != null){

				if(line.matches("\\{\\{((wt)|(subst\\:wikitable))\\}\\} ?")) line="{wt";
//					line = Operation.removeAnglebrackets(line);
					line = Operation.replaceQuotation(line);
					
					line = Html.replaceHtml(line);
					
					line = Html.replaceHtml(line);
					
					leftBraceNum += Operation.countSubstr(line, "\\{");
					rightBraceNum += Operation.countSubstr(line, "\\}");
					leftBracketNum += Operation.countSubstr(line, "\\[\\[");
					rightBracketNum += Operation.countSubstr(line, "\\]\\]");
					leftAngleNum += Operation.countSubstr(line, "(&lt;)|<");
					rightAngleNum += Operation.countSubstr(line, "(&gt;)|>");
					
					leftRefNum += (Operation.countSubstr(line, "<ref")/"<ref".length());
					rightRefNum += (Operation.countSubstr(line, "</ref>")/"</ref>".length()
							+ Operation.countSubstr(line, "/>")/"/>".length());
					
					if(rightAngleNum>leftAngleNum) rightAngleNum=leftAngleNum;
					if(rightBracketNum>leftBracketNum) rightBracketNum=leftBracketNum;
					if(rightBraceNum>leftBraceNum) rightBraceNum=leftBraceNum;
					
					if(line.matches("==.*==")){
						leftBraceNum = 0;
						rightBraceNum = 0;
						leftBracketNum = 0;
						rightBracketNum = 0;
						leftAngleNum = 0;
						rightAngleNum  = 0;
						leftRefNum = 0;
						rightRefNum = 0;
						writer.write("\n");
					}
					
//					System.out.println(line);
//					System.out.println("{}:"+leftBraceNum+" "+rightBraceNum);
//					System.out.println("[]:"+leftBracketNum+" "+rightBracketNum);
//					System.out.println("<>:"+leftAngleNum+" "+rightAngleNum);
//					System.out.println("ref:"+leftRefNum+" "+rightRefNum);

					
			
				if(line.equals("")) continue;
				else if((leftBraceNum == rightBraceNum 
						&& leftBracketNum == rightBracketNum 
						&& leftAngleNum==rightAngleNum
						&& leftRefNum==rightRefNum)
						&& !line.endsWith(",")) 
					writer.write(line+"\n");
				else writer.write(line);
//				System.out.println(line);
			
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
	
	public static void testExtract(){
		String line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream (testFilename), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (testoutFilename), "UTF-8"));
			while ((line = reader.readLine()) != null){
				System.out.println(line);
				line = Template.removeTemplate(line);
				System.out.println(line);
				
				
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
	
	public static void extractTitle(){
		String line = "";
//		String tmp_line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream (originFilename), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("out/filterTitle.txt"), "UTF-8"));
			//BufferedWriter writer1 = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("nouse.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				
				if(line.equals("  <page>")){
					PageNo++;
					for(int i = 0;i<5; i++){
						line = reader.readLine();
						String str[]=line.split("<|>");
						if(line.matches("    <id>.+")) docID = Integer.parseInt(str[2]);					
						if(line.matches("    <title>.+")) title = openCC.convert(str[2]);
						if(line.matches("    <ns>.+")) ns = Integer.parseInt(str[2]);				
					}
					IDTitle.put(docID, title);
					//if(ns == 0 ) 
						writer.write("\n\n<doc id=\""+docID+"\" url=\"https://zh.wikipedia.org/wiki?curid="+docID+"\" title=\""+title+"\">"+"\n");
						writeFlag = true;
						System.out.println(docID);
				}
				
				if(line.trim().matches("^==.*") && ns==0) {
					if(Ending.checkEnding(line)) writeFlag = false;
					if(writeFlag) writer.write("\n"+line);
					else System.out.println(line);
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

	public static void extractBraces(){
		String line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("out-pretreatment.txt"), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("extractBraces.txt"), "UTF-8"));
			BufferedWriter writer1 = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("extractBraces1.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				
				if(line.matches("<doc id=.*>")){
					String[] strs = line.split("\"");
					docID=Integer.parseInt(strs[1]);
					title = strs[5];
					System.out.println(docID+"\t"+title+"\t");
					writer.write("\n"+line+"\n");
					continue;
				}
				if(line.trim().matches("text xml.*")) continue;
			
				
				
				if(line.matches(".*\\{\\{.*\\}\\}.*") && checkBraces2(line)){
					writer.write("\n"+line);
					//System.out.println(line);
				}
				if(line.matches("\\{\\{.*\\}\\}") && line.split("\\{\\{|\\}\\}").length==2 && checkBraces2(line)){
					writer1.write("\n"+line);
					System.out.println(line);
				}
			}
			
		
			
		reader.close();
		
		writer.flush();
		writer.close();
		
		writer1.flush();
		writer1.close();
			
		} catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("error");
            e.printStackTrace();
		}
		
		
		
	}
	
	public static void extractAnglebrace(){
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("out-pretreatment.txt"), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("extractAngleBraces.txt"), "UTF-8"));
			String line = "";
			
			while ((line = reader.readLine()) != null){
				line=Operation.removeLabel(line);
				line=line.replaceAll("</?gallery>", "");
				line=line.replaceAll("</?center>", "");
				line=line.replaceAll("</?div.*", "");
				line=line.replaceAll("</?imagemap>", "");
				line=line.replaceAll("</?table>", "");
				line=line.replaceAll("</?pre>", "");
				if(line.matches("</?(td|tr|th).*")) continue;
				if(line.matches("</?(source).*")) continue;
				if(line.matches("</?(span).*")) continue;
				if(line.matches("<.*>")&&!line.matches("</?ref.*")/*&&!line.matches("</?doc.*")*/) {
					System.out.println(line);
					writer.write(line+"\n");
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
	
	static boolean checkBraces2(String line){ //{{...}}
		if(line.toLowerCase().matches(".*\\{\\{(cite|citation|dmoz|redirect|noteta|rp|sfn|ref|div|gb/t|r\\|).*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches(".*\\{\\{.*(sidebar|navigation|quote|expand|portal|cquote).*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches(".*\\{\\{(死鏈|請求來源|请求来源|需要解释|(参\\|.*)|who|weasel word|zwsp|dubious|fact|authority control|toc limit|or|clear|-|verify credibility|POW|KIA).*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches(".*\\{\\{(en|de|fr|ja|zh-hans|zh-hant|zh-tw|zh-cn|zh-yue|yue|zh|cn|es|ko).*|(.* icon).*\\}\\}.*")) return false; //language tag
		else if(line.toLowerCase().matches(".*\\{\\{(cite|citation|dmoz|redirect|noteta|rp|sfn|ref|div|gb/t|r\\|).*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches(".*\\{\\{(eqm|CURRENTYEAR|.w|minus|to|rp|sfn|ref|div|gb/t|r\\|).*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches(".*\\{\\{(lang|link|le|fact|doi|nowrap|audio|quotation).*\\|.*\\}\\}.*")) return false;
		else if(line.toLowerCase().matches("\\{\\{(main|主條目|参见|further|which|dablink|see|col).*\\}\\}.*")) return false;
		//else if(line.toLowerCase().matches("\\{\\{(literature|science|politics|sociology)\\}\\}.*")) return false;

		else return true;
	}
	

	
	
	public static void extractCategory (){
		String line = "";
		ArrayList<String> categoryList = new ArrayList<String>();
		try{
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("category.txt"), "UTF-8"));
			for(int i = 1; i < 5; i++){
				BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("F:/wiki/zhwiki20160820_xml/zhwiki-20160820-pages-meta-current"+i+".xml"), "UTF-8"));
				while ((line = reader.readLine()) != null){
					if(line.equals("  <page>")){
						PageNo++;
						
						for(int j = 0;j<5; j++){
							line = reader.readLine();
							String str[]=line.split("<|>");
							if(line.matches("    <id>.+")){
								docID = Integer.parseInt(str[2]);
							}
							if(line.matches("    <title>.+")){
								title = tw2cn(str[2]);
							}
							if(line.matches("    <ns>.+")){
								ns = Integer.parseInt(str[2]);
							}	
							
						}
						IDTitle.put(docID, title);
						
						categoryList.clear();
						if(ns==0)
							System.out.println("doc id:"+docID+" doc title:"+title+" ns:"+ns);
							/*writer.write("\n\n<doc id=\""+docID+"\" "
									+ "url=\"https://zh.wikipedia.org/wiki?curid="+docID+"\" "
											+ "title=\""+title+"\">"+"\n");		*/
					}
					if(line.toLowerCase().matches("\\[\\[(category|分类|分類).*")){
						line=tw2cn(line);
//						System.out.println(line);
//						System.out.println(line.indexOf(":")+1);
//						System.out.println(line.indexOf("]"));
						if(line.matches(".*\\|.*")) categoryList.add(line.substring(line.indexOf(":")+1, line.indexOf("|")));
						else if(line.toLowerCase().matches("\\[\\[(category|分类|分類):.*\\]\\].*")) categoryList.add(line.substring(line.indexOf(":")+1, line.indexOf("]]")));
						else categoryList.add(line.substring(line.indexOf(":")+1));
					}
					//if(line.equals("  </page>") && ns==requiredns) writer.write("\n</doc>\n\n");
					//if(line.trim().matches("<.*") && !line.trim().matches("<text.*")) continue;
					
					//if (ns==0) writer.write(line+"\n");
					
					if(line.trim().equals("</page>") && ns==0) {
//						System.out.println("category: "+categoryList.toString());
						writer.write(docID+"\t"+title+"\t"+categoryList.toString()+"\n");
						
					}
					
				}
				reader.close();
			}
			
			
			writer.flush();
			writer.close();
		} catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("error");
            e.printStackTrace();
		}
			
	}
	
	static void extractMath(){
		String line = "";
		try{
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("math-out.txt"), "UTF-8"));
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("math0.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				line=Math.correctFormula(line);
				writer.write(line+"\n");
				
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
	static String tw2cn(String line){ //tw->cn
		line = openCC.convert(line);
		line=line.replaceAll("什幺", "什么");
		return line;
	}
	
	static void extractTitles(int requiredns){
		String line = "";
		
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("test.xml"), "UTF-8"));
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("test.txt"), "UTF-8"));
			
				
			while ((line = reader.readLine()) != null){
				if(line.equals("  <page>")){
					PageNo++;
					
					for(int j = 0;j<5; j++){
						line = reader.readLine();
						String str[]=line.split("<|>");
						if(line.matches("    <id>.+")){
							docID = Integer.parseInt(str[2]);
						}
						if(line.matches("    <title>.+")){
							title = tw2cn(str[2]);
						}
						if(line.matches("    <ns>.+")){
							ns = Integer.parseInt(str[2]);
						}	
						
					}
					IDTitle.put(docID, title);
					
					System.out.println("doc id"+docID+": "+title+" ns:"+ns);
					if(ns==requiredns)
						writer.write("\n\n<doc id=\""+docID+"\" "
								+ "url=\"https://zh.wikipedia.org/wiki?curid="+docID+"\" "
										+ "title=\""+title+"\">"+"\n");		
				}
				//if(line.equals("  </page>") && ns==requiredns) writer.write("\n</doc>\n\n");
				//if(line.trim().matches("<.*") && !line.trim().matches("<text.*")) continue;
				
				if (ns==requiredns) writer.write(line+"\n");
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

	public static void extractST(){
		String line = "";
		int size = 1;
		try{
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream ("zh-hanst.txt"), "UTF-8"));
			for(int i = 1; i <= size; i++){
				BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("F:/wiki/zhwiki20160820_xml/zhwiki-20160820-pages-meta-current"+i+".xml"), "UTF-8"));
				while ((line = reader.readLine()) != null){
					if(line.equals("  <page>")){
						for(int j = 0;j<5; j++){
							line = reader.readLine();
							String str[]=line.split("<|>");
							if(line.matches("    <id>.+")){
								docID = Integer.parseInt(str[2]);
							}
							if(line.matches("    <title>.+")){
								title = tw2cn(str[2]);
							}
							if(line.matches("    <ns>.+")){
								ns = Integer.parseInt(str[2]);
							}	
							
						}
						System.out.println("doc id "+docID+": "+title);
					}
					while(line.matches(".*-\\{.*zh-.*\\}-.*")){
						line = Html.replaceHtml(line);
						if(line.matches(".*\\[.*\\].*")) break;
						if(line.matches(".*\\{\\{.*\\}\\}.*")) break;
						int beginIndedx = line.indexOf("-{");
						int endIndex = line.indexOf("}-", beginIndedx)+2;
						String subline = line.substring(beginIndedx, endIndex);
						line = line.substring(endIndex);
						writer.write(subline+"\n");
					}
					if(line.matches(".*=zh-hans:.*")){
						line = line.substring(line.indexOf("=")+1);
					}
					
				}
				reader.close();
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
            // TODO Auto-generated catch block
			System.out.println(line);
            System.out.println("error");
            e.printStackTrace();
		}
	}
	
}
