# ChineseWikipediaExtractor

## Usage
-ns namespce(0 as default)


-in input filepath


-out output filepath


-lm language mode(0 as default)
* Simplified Chinese = 0;  
* Traditional Chinese = 1;  
* HongKong traditional Chinese = 3;  
* Taiwan traditional Chinese = 2;  

**e.g. java -jar ChineseWikipediaExtractor.jar [-ns 0] [-lm 0] -in xxxxx.xml -out out.txt**