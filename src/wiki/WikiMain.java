package wiki;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WikiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		defineParameter(args);
		WikiProcess.processData();
		

	}
	
	public static void defineParameter(String[] args){
		if(args==null) return;
		ArrayList<String> para = new ArrayList<String>();
		Collections.addAll(para, args);
		if(para.contains("-in")){
			WikiProcess.originFilename = para.get(para.indexOf("-in")+1);
			System.out.println("The input filename is: "+WikiProcess.originFilename);
		}
		if(para.contains("-out")){
			WikiProcess.outputFilename = para.get(para.indexOf("-out")+1);
			System.out.println("The output filename is: "+WikiProcess.outputFilename);
		}
		if(para.contains("-ns")) {
			WikiProcess.namespace = Integer.valueOf(para.get(para.indexOf("-ns")+1));
			System.out.println("The namespace: "+WikiProcess.namespace);
		}
		if(para.contains("-lm")) {
			WikiProcess.languageMode = Integer.valueOf(para.get(para.indexOf("-lm")+1));
			if(WikiProcess.languageMode == 0) System.out.println("The language mode: Simplified Chinese");
			else if(WikiProcess.languageMode == 1) System.out.println("The language mode: Traditional Chinese");
			else if(WikiProcess.languageMode == 2) System.out.println("The language mode: Taiwan Traditional Chinese");
			else if(WikiProcess.languageMode == 3) System.out.println("The language mode: Hong Kong Traditional Chinese");
			else{
				
				System.out.println("\"-lm "+WikiProcess.languageMode+"\" is error!");
				WikiProcess.languageMode = 0;
				System.out.println("The language mode: Simplified Chinese");
			}
		}
		
	}

}
