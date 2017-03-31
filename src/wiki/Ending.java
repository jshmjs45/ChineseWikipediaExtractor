package wiki;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Language.Conversion;
import Language.*;

/**
 * Ending means the REFERENCE line symbol which records in corpus/EndList.txt, e.g. "参考", "注释"
 * @author Shu Jiang
 * @version 1.0 (2017-03-24)
 */
public class Ending {
	public static List<String> EndList =  new ArrayList<String>();

	static{
		String line = "";
		try{
			BufferedReader reader = new BufferedReader (new InputStreamReader (new FileInputStream ("corpus/EndList.txt"), "UTF-8"));
			while ((line = reader.readLine()) != null){
				line=line.replaceAll("(\\s|=)+", "");
				if(line!="") EndList.add(line);
			}
			reader.close();
		} catch (Exception e) {
	        // TODO Auto-generated catch block
	        System.out.println("error");
	        e.printStackTrace();
		}
	}
	
	/**
	 * Check whether the line is a REFERENCE line or not
	 * @author Shu Jiang
	 * @param line in the text
	 * @return TRUE if the line is the REFERENCE line e.g."参考", "注释"
	 * @version 1.0 (2017-03-24)
	 */
	public static boolean checkEnding(String line){
		line=Conversion.convert(line, Conversion.Simplified);
		if(EndList.contains(line.replaceAll("(\\s|=)+", ""))) return true;
		else return false;
	}
}
