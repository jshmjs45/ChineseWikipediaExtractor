# ChineseWikipediaExtractor  
Version: 1.1   

Revised: Mar.31, 2017   

**Highlight: It can select Simplified Chinese or [HongKong/Taiwan] Traditional Chinese!**   


## Usage
-ns namespce (0 as default)

-in input filepath

-out output filepath

-lm language mode (0 as default)
* 0: Simplified Chinese （简体中文）  
* 1: Traditional Chinese （繁体中文）  
* 2: Taiwan traditional Chinese （台湾繁体）  
* 3: Hong Kong traditional Chinese （香港繁体）  

**e.g. java -jar ChineseWikipediaExtractor.jar [-ns 0] [-lm 0] -in xxxxx.xml -out out.txt**
