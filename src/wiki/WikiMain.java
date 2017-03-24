package wiki;
import java.util.ArrayList;

public class WikiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		WikiProcess.processData();
		

	}
	
	public static void defineParameter(String[] args){
		if(args==null) return;
		ArrayList<String> para = new ArrayList<String>();
		if(para.contains("-in")) WikiProcess.originFilename = para.get(para.indexOf("-in")+1);
		if(para.contains("-out")) WikiProcess.outputFilename = para.get(para.indexOf("-out")+1);
		
		if(para.contains("-ns")) WikiProcess.namespace = Integer.valueOf(para.get(para.indexOf("-ns")+1));
		
	}

}
