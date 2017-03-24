package wiki;


public class CheckMatches {
	public static int leftBraceNum = 0; //{
	public static int rightBraceNum = 0;//}
	public static int leftBracketNum = 0;//[
	public static int rightBracketNum = 0;//]
	public static int leftAngleNum = 0;//<
	public static int rightAngleNum  = 0;//>
	
	public static int leftRefNum = 0;
	public static int rightRefNum = 0;
	public static int leftMathNum = 0;
	public static int rightMathNum = 0;
	
	public static void clear(){
		leftBraceNum = 0;
		rightBraceNum = 0;
		leftBracketNum = 0;
		rightBracketNum = 0;
		leftAngleNum = 0;
		rightAngleNum  = 0;
		leftRefNum = 0;
		rightRefNum = 0;
		leftMathNum = 0;
		rightMathNum = 0;
	}
	
	public static void count(String line){
		if(line.matches("\\{\\{((wt=?)|(subst\\:wikitable)).*\\}\\}.*")) line="{wt";
		
		leftBraceNum += countSubstr(line, "{");
		rightBraceNum += countSubstr(line, "}");
		leftBracketNum += countSubstr(line, "[");
		rightBracketNum += countSubstr(line, "]");
		leftAngleNum += countSubstr(line, "<");
		rightAngleNum  += countSubstr(line, ">");
		leftRefNum += (countSubstr(line.toLowerCase(), "<ref"));
		rightRefNum += (countSubstr(line.toLowerCase(), "</ref>")+ Operation.countSubstr(line, "/>"));
		leftMathNum += (countSubstr(line.toLowerCase(), "<math"));
		rightMathNum += (countSubstr(line.toLowerCase(), "</math>"));
		
		if(rightAngleNum > leftAngleNum) rightAngleNum =leftAngleNum;
		if(rightBracketNum > leftBracketNum) rightBracketNum=leftBracketNum;
		if(rightBraceNum > leftBraceNum) rightBraceNum=leftBraceNum;
		if(rightRefNum > leftRefNum) rightRefNum=leftRefNum;
		if(rightMathNum > leftMathNum) rightMathNum=leftMathNum;
		
		
	}
	
	public static boolean equal(){
		return(	leftBraceNum == rightBraceNum 
				&& leftBracketNum == rightBracketNum 
				&& leftAngleNum==rightAngleNum
				&& leftRefNum==rightRefNum
				&& leftMathNum==rightMathNum);
	}
	
	public static int countSubstr(String line,String punc){
		return (line.length()-line.replace(punc, "").length())/punc.length();
	}
}
