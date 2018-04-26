package alite.leetcode.xx2.sucess.before;

import java.util.HashMap;
import java.util.Map;

/**
 * 290. Word Pattern

Total Accepted: 42115 Total Submissions: 140014 Difficulty: Easy
 

Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

Examples:

pattern = "abba", str = "dog cat cat dog" should return true.
pattern = "abba", str = "dog cat cat fish" should return false.
pattern = "aaaa", str = "dog cat cat dog" should return false.
pattern = "abba", str = "dog dog dog dog" should return false.
 

Notes:
You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space.

 

思路：映射。详细可以参考Leetcode 205. Isomorphic Strings的第一个方法


代码：

注意istringstream的用法，头文件<sstream>。这个可以专门用来分解含空格的字符串。

复制代码
复制代码
 1 class Solution {
 2 public:
 3     bool wordPattern(string pattern, string str) {
 4         map<char,int> p2i;
 5         map<string,int> s2i;
 6         string temp;
 7         int i=0,n=pattern.size();
 8         istringstream in(str);
 9         while(in>>temp){
10             if(i<n){
11                 //map中没有对应的键-值对，则返回0
12                 if(p2i[pattern[i]]!=s2i[temp]){
13                     return false;
14                 }
15                 p2i[pattern[i]]=s2i[temp]=i+1;
16             }
17             i++;
18         }
19         return i==n;
20     }
21 };
 * @author het
 *
 */
public class Leetcode290 {
	
	// "abba", str = "dogcatcatdog"
	public static boolean couldMatched(String pattern, String str){
		if((pattern == null || pattern.length() == 0)&& (str == null || str.length()==0)) return true;
    	if (pattern == null || pattern.length() == 0) return false;
    	if(str == null || str.length()==0) return false;
    	Map<Character, String> map = new HashMap<>();
    	Map<String, Character> reverseMap = new HashMap<>();
    	return couldMatched(pattern, 0, str, 0, map,reverseMap);
	}
	
	
	
	public static boolean couldMatched(String pattern,int pIndex, String str, int sIndex, Map<Character, String> map,Map<String, Character> reverseMap){
		if(pIndex == pattern.length() && sIndex== str.length()) return true;
		if(pIndex >= pattern.length() || sIndex>= str.length()) return false;
		char charPattern = pattern.charAt(pIndex);
		for(int i = sIndex;i< str.length();i+=1){
			String prefix = str.substring(sIndex, i+1);
			if(!map.containsKey(charPattern) && !reverseMap.containsKey(prefix)){
				map.put(charPattern, prefix);
				reverseMap.put(prefix, charPattern);
				if(couldMatched(pattern, pIndex+1, str, i+1, map, reverseMap)){
					return true;
				}
				map.remove(charPattern);
				reverseMap.remove(prefix);
			}else if(map.containsKey(charPattern) && reverseMap.containsKey(prefix)){
				String mappingStr = map.get(charPattern);
				char mappingChar = reverseMap.get(prefix);
				if(!mappingStr.equals(prefix) || mappingChar != charPattern){
					continue;
				}
				if(couldMatched(pattern, pIndex+1, str, i+1, map, reverseMap)){
					return true;
				}
			}
		}
		
		
		return false;
	}
	//pattern = "abba", str = "dog cat cat fish"
    public static boolean isMatched(String pattern, String str){
    	if((pattern == null || pattern.length() == 0)&& (str == null || str.length()==0)) return true;
    	if (pattern == null || pattern.length() == 0) return false;
    	if(str == null || str.length()==0) return false;
    	String []  splits = str.split(" ");
    	if(splits.length != pattern.length() ) return false;
    	Map<Character, String> map = new HashMap<>();
    	Map<String, Character> reverseMap = new HashMap<>();
    	for(int i=0;i<pattern.length();i+=1){
    		char character = pattern.charAt(i);
    		String split = splits[i];
    		if((map.containsKey(character)&& !map.get(character).equals(split))||
    				(reverseMap.containsKey(split)&& !reverseMap.get(split).equals(character))){
    			return false;
    		}
    		
    		map.put(character, split);
    		reverseMap.put(split, character);
    		
    		
    	}
    	return true;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(isMatched("abba", "dog cat cat dog"));
//		
//		System.out.println(isMatched("abba", "dog cat cat fish"));
//		System.out.println(isMatched("aaaa", "dog cat cat dog"));
//		System.out.println(isMatched("abba", "dog dog dog dog"));
		System.out.println(couldMatched("abba", "dogdogdogdog"));
		System.out.println(couldMatched("abba", "dogcatcatdog"));
		System.out.println(couldMatched("abba", "cddiifiifcdd"));
		
//		pattern = "abba", str = "dog cat cat dog" should return true.
//				pattern = "abba", str = "dog cat cat fish" should return false.
//				pattern = "aaaa", str = "dog cat cat dog" should return false.
//				pattern = "abba", str = "dog dog dog dog" should return false.

	}

}
