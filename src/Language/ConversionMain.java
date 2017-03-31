package Language;

public class ConversionMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int languageMode  =  0;

		String line ="'''信息學'''（-{zh-hans:台湾称'''资讯学'''; zh-hant:中國稱'''信息學''';}-），又称'''-{zh-hans:信息; zh-hant:資訊;}-科学'''、'''-{zh-hans:资讯; zh-hant:信息;}-科學'''，旧称'''情报学'''（[[漢語中的日語借詞|外來語]]），主要是指以[[信息]]为研究对象，利用[[计算机]]及其[[程序]]设计等技术为研究工具来分析问题、解决问题的学问，是以扩展人类的信息功能为主要目标的一门综合性学科。";
		System.out.println(Conversion.convert(line, languageMode));
		line = Conversion.removeConversionLabel(line,languageMode);
		System.out.println(line);
	}

}
