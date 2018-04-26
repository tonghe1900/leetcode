package important;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * leetcode 291 Word Pattern II
Given a pattern and a string str, find if str follows the same pattern.
Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.
Examples:
pattern = "abab", str = "redblueredblue" should return true.
pattern = "aaaa", str = "asdasdasdasd" should return true.
pattern = "aabb", str = "xyzabcxzyabc" should return false. 
Notes:
You may assume both pattern and str contains only lowercase letters.
The problem becomes much more difficult after the spaces are removed. Now we need to determine which part matchs which part by ourselves.
http://buttercola.blogspot.com/2015/10/leetcode-word-pattern-ii.html
https://kennyzhuang.gitbooks.io/leetcode-lock/content/291_word_pattern_ii.html

    public boolean wordPatternMatch(String pattern, String str) {

        if ((pattern == null || pattern.length() == 0) && (str == null || str.length() == 0)) {

            return true;

        }

         

        if ((pattern == null || pattern.length() == 0) || (str == null || str.length() == 0)) {

            return false;

        }

         

        Map<Character, String> forwardMap = new HashMap<>();

        Map<String, Character> invertedMap = new HashMap<>();

         

        return wordPatternMatchHelper(0, pattern, 0, str, forwardMap, invertedMap);

    }

     

    private boolean wordPatternMatchHelper(int pStart, String pattern, 

                                      int sStart, String str, 

                                      Map<Character, String> forwardMap, 

                                      Map<String, Character> invertedMap) {

        if (pStart == pattern.length() && sStart == str.length()) {

            return true;

        }

         

        if (pStart >= pattern.length() || sStart >= str.length()) {

            return false;

        }

         

        char pChar = pattern.charAt(pStart);

        for (int i = sStart; i < str.length(); i++) {

            String curr = str.substring(sStart, i + 1);

             

            if ((!forwardMap.containsKey(pChar)) && (!invertedMap.containsKey(curr))) {

                forwardMap.put(pChar, curr);

                invertedMap.put(curr, pChar);

                 

                if (wordPatternMatchHelper(pStart + 1, pattern, i + 1, 

                        str, forwardMap, invertedMap)) {

                    return true;

                }

                 

                forwardMap.remove(pChar);

                invertedMap.remove(curr);

            } else if (forwardMap.containsKey(pChar) && invertedMap.containsKey(curr)) {

                String dict = forwardMap.get(pChar);

                char pCharDict = invertedMap.get(curr);

                 

                // IMPORTANT !! If not equal, instead of returnning false immedidately,

                // We need to try other longer substrings. 

                if (!dict.equals(curr) || pCharDict != pChar) {

                    continue;

                }

                 

                if (wordPatternMatchHelper(pStart + 1, pattern, i + 1, str, 

                        forwardMap, invertedMap)) {

                    return true;

                }

            }

        }

         

        return false;

    }
http://www.programcreek.com/2014/07/leetcode-word-pattern-ii-java/
The time complexity then is f(n) = n*(n-1)*... *1=n^n.
https://discuss.leetcode.com/topic/26819/20-lines-java-clean-solution-easy-to-understand
Map<Character,String> map =new HashMap();
Set<String> set =new HashSet();
public boolean wordPatternMatch(String pattern, String str) {
    if(pattern.isEmpty()) return str.isEmpty();
    if(map.containsKey(pattern.charAt(0))){
        String value= map.get(pattern.charAt(0));
        if(str.length()<value.length() || !str.substring(0,value.length()).equals(value)) return false;
        if(wordPatternMatch(pattern.substring(1),str.substring(value.length()))) return true;
    }else{
        for(int i=1;i<=str.length();i++){
            if(set.contains(str.substring(0,i))) continue;
            map.put(pattern.charAt(0),str.substring(0,i));
            set.add(str.substring(0,i));
            if(wordPatternMatch(pattern.substring(1),str.substring(i))) return true;
            set.remove(str.substring(0,i));
            map.remove(pattern.charAt(0));
        }
    }
    return false;
}
https://segmentfault.com/a/1190000003827151
    Map<Character, String> map;
    Set<String> set;
    boolean res;
    
    public boolean wordPatternMatch(String pattern, String str) {
        // 和I中一样，Map用来记录字符和字符串的映射关系
        map = new HashMap<Character, String>();
        // Set用来记录哪些字符串被映射了，防止多对一映射
        set = new HashSet<String>();
        res = false;
        // 递归回溯
        helper(pattern, str, 0, 0);
        return res;
    }
    
    public void helper(String pattern, String str, int i, int j){
        // 如果pattern匹配完了而且str也正好匹配完了，说明有解
        if(i == pattern.length() && j == str.length()){
            res = true;
            return;
        }
        // 如果某个匹配超界了，则结束递归
        if(i >= pattern.length() || j >= str.length()){
            return;
        }
        char c = pattern.charAt(i);
        // 尝试从当前位置到结尾的所有划分方式
        for(int cut = j + 1; cut <= str.length(); cut++){
            // 拆出一个子串
            String substr = str.substring(j, cut);
            // 如果这个子串没有被映射过，而且当前pattern的字符也没有产生过映射
            // 则新建一对映射，并且继续递归求解
            if(!set.contains(substr) && !map.containsKey(c)){
                map.put(c, substr);
                set.add(substr);
                helper(pattern, str, i + 1, cut);
                map.remove(c);
                set.remove(substr);
            // 如果已经有映射了，但是是匹配的，也继续求解
            } else if(map.containsKey(c) && map.get(c).equals(substr)){
                helper(pattern, str, i + 1, cut);
            }
            // 否则跳过该子串，尝试下一种拆分
        }
    }
https://leetcode.com/discuss/63252/share-my-java-backtracking-solution
can we check map.containsValue(p) instead of using another hashset?
sure, it is possible to use containsValue() method. But it is slower, since the method's complexity is O(n).


Not exactly cost O(n) more, since there are only 26 keys at most for this particular problem, it may cost slightly more time. It saves some space in SET on the other hand.
-- the set<String> is at most 26, so ...
// Use Guava BidiMap,  AbstractDualBidiMap

  public boolean wordPatternMatch(String pattern, String str) {
    Map<Character, String> map = new HashMap<>();
    Set<String> set = new HashSet<>();
    return isMatch(str, 0, pattern, 0, map, set);
  }

  boolean isMatch(String str, int i, String pat, int j, Map<Character, String> map, Set<String> set) {
    // base case
    if (i == str.length() && j == pat.length()) return true;
    if (i == str.length() || j == pat.length()) return false;

    // get current pattern character
    char c = pat.charAt(j);

    // if the pattern character exists
    if (map.containsKey(c)) {
      String s = map.get(c);

      // then check if we can use it to match str[i...i+s.length()]
      if (!str.startsWith(s, i)) {
        return false;
      }

      // if it can match, great, continue to match the rest
      return isMatch(str, i + s.length(), pat, j + 1, map, set);
    }

    // pattern character does not exist in the map
    for (int k = i; k < str.length(); k++) {
      String p = str.substring(i, k + 1);

      if (set.contains(p)) {
        continue;
      }

      // create or update it
      map.put(c, p);
      set.add(p);

      // continue to match the rest
      if (isMatch(str, k + 1, pat, j + 1, map, set)) {
        return true;
      }

      // backtracking
      map.remove(c);
      set.remove(p);
    }

    // we've tried our best but still no luck
    return false;
  }
http://www.meetqun.com/thread-11391-1-1.html
public class Solution {4 `+ U& C4 n  ?3 u9 R8 [
    public boolean wordPatternMatch(String pattern, String str) {) @* K/ S; O- {! |3 R4 ?- k
        HashMap<Character, String> mapPtoS = new HashMap<Character, String>();
        HashMap<String, Character> mapStoP = new HashMap<String, Character>();
        9 _9 i0 r$ A/ @' @+ b, ?  t
        return dfs(pattern, str, 0,0, mapPtoS, mapStoP);
    }; p! u3 b; T' d% m* l& [
    private boolean dfs(String pattern, String str, int idxPat, int idxStr, HashMap<Character, String> mapPtoS, HashMap<String, Character> mapStoP){& E: h+ C; F5 _7 ^% J1 C) ~+ g2 m
        int patLength = pattern.length();( A4 G3 G8 W4 y
        int strLength = str.length();
        if(idxPat==patLength&&idxStr==strLength) return true;* W, L# s  s) U% E. m! x3 ~
        if(idxPat==patLength||idxStr==strLength) return false;
        4 S# x) p2 o# d$ a3 j
        char p = pattern.charAt(idxPat);# ^+ E$ ^2 @# p- ]6 r  r4 N0 p
        for(int i=idxStr+1;i<=strLength;i++){
            String item = str.substring(idxStr, i);
            + k3 q. W1 y. y7 u
            boolean findP = true;
            if(mapPtoS.containsKey(p) && !mapPtoS.get(p).equals(item)) continue;
            else if(!mapPtoS.containsKey(p)){$ `1 j; n! f3 S# C
                mapPtoS.put(p, item);6 B8 p0 m9 ?' T6 n5 R" G2 l
                findP = false;
            }8 x, J% n* K. [& x
            
            boolean findStr = true;
            if(mapStoP.containsKey(item) && mapStoP.get(item)!=p) {
                if(!findP) mapPtoS.remove(p);6 O, x: r1 @; |1 M
                continue; 
            }) Y! N! y( G3 \4 }) Y6 Z
            else if(!mapStoP.containsKey(item)){
                mapStoP.put(item, p);
                findStr = false;
            }- R  ]; _- h6 S8 U# `7 Z$ ^& E$ t/ E
            
            if(dfs(pattern, str, idxPat+1, i, mapPtoS, mapStoP)) return true;0 O' b7 R6 a& o1 s: |
            else{
                if(!findP) mapPtoS.remove(p);
                if(!findStr) mapStoP.remove(item);* J* H. `, J4 ]3 s) B
            }
        }9 @3 o+ C7 ?+ V/ V3 v( X
        ! H% J6 K6 E* k: e' ?3 B
        return false;
    }9 y& A0 w2 y9 i5 f3 F) H" M
}
. |; m: d+ j- S0 v. M
==============$ }$ _& d. f: {8 G. _0 m
A typical backtracking. I use two hashmap to guarantee one pattern only map to exact one string. Note we need to remove new added element in hashmap if current splitted string is illegal
20 lines JAVA clean solution, easy to understand
http://dartmooryao.blogspot.com/2016/05/leetcode-291-word-pattern-ii.html
public class Solution {
Map<Character,String> map =new HashMap();
Set<String> set =new HashSet();
public boolean wordPatternMatch(String pattern, String str) {
    if(pattern.isEmpty()) return str.isEmpty();
    if(map.containsKey(pattern.charAt(0))){
        String value= map.get(pattern.charAt(0));
        if(str.length()<value.length() || !str.substring(0,value.length()).equals(value)) return false;
        if(wordPatternMatch(pattern.substring(1),str.substring(value.length()))) return true;
    }else{
        for(int i=1;i<=str.length();i++){
            if(set.contains(str.substring(0,i))) continue;
            map.put(pattern.charAt(0),str.substring(0,i));
            set.add(str.substring(0,i));
            if(wordPatternMatch(pattern.substring(1),str.substring(i))) return true;
            set.remove(str.substring(0,i));
            map.remove(pattern.charAt(0));
        }
    }
    return false;
}
http://shibaili.blogspot.com/2015/10/day-132-287-find-duplicate-number.html
back tracking
对一个pattern[i], 试遍每一种可能

    bool helper(string pattern, int i, string str, int is, unordered_map<char,string> &ptos, unordered_map<string,char> &stop) {

        if (i == pattern.length() && is == str.length()) return true;

        if (i == pattern.length() || is == str.length()) return false;

         

        if (ptos.find(pattern[i]) != ptos.end()) {

            if (ptos[pattern[i]] == str.substr(is,ptos[pattern[i]].length())) {

                return helper(pattern,i + 1, str, is + ptos[pattern[i]].length(),ptos,stop);

            }

            return false;

        }

         

        for (int j = is; j < str.length(); j++) {

            string word = str.substr(is,j - is + 1);

            if (stop.find(word) != stop.end()) continue;

             

            ptos[pattern[i]] = word;

            stop[word] = pattern[i];

            if (helper(pattern, i + 1, str, j + 1, ptos,stop)) return true;

            ptos.erase(pattern[i]);

            stop.erase(word);

        }

         

        return false;

    }


    bool wordPatternMatch(string pattern, string str) {

        unordered_map<char,string> ptos;

        unordered_map<string,char> stop;

         

        return helper(pattern,0,str,0,ptos,stop);

    }

Pattern Matching: You are given two strings, pattern and value. The pattern string consists of
just the letters a and b, describing a pattern within a string. For example, the string catcatgocatgo
matches the pattern aabab (where cat is a and go is b). It also matches patterns like a, ab, and b.

Write a method to determine if value matches pattern

boolean doesMatch(String pattern, String value) {
        if (pattern.length()== 0) return value.length() == 0;

       int size = value.length();
        for (int mainSize = 0; mainSize < size; mainSize++) {
                String main= value.substring(0, mainSize);
                for (int altStart = mainSize; altStart <= size; ,altStart++) {
                        for (int altEnd = altStart; altEnd <= size; altEnd++) {
                                String alt= value.substring(altStart, altEnd);
                                String cand = buildFromPattern(pattern, main, alt);
                                if (cand.equals(value)) {
                                        return true;
                                }
                        }
                }
        }
        return false;
}

String buildFromPattern(String pattern, String main, String alt) {
        StringBuffer sb = new StringBuffer();
        char first= pattern.charAt(0);
        for (char c : pattern.toCharArray()) {
                if (c == first) {
                        sb.append(main);
                } else {
                        sb.append(alt);
                }
        }
        return sb.toString();
}

https://discuss.leetcode.com/topic/26750/share-my-java-backtracking-solution
 * 
 * 
 * @author het
 *
 */
public class leetcode291WordPatternII {
	 public boolean wordPatternMatch(String pattern, String str) {

	        if ((pattern == null || pattern.length() == 0) && (str == null || str.length() == 0)) {

	            return true;

	        }

	         

	        if ((pattern == null || pattern.length() == 0) || (str == null || str.length() == 0)) {

	            return false;

	        }

	         

	        Map<Character, String> forwardMap = new HashMap<>();

	        Map<String, Character> invertedMap = new HashMap<>();

	         

	        return wordPatternMatchHelper(0, pattern, 0, str, forwardMap, invertedMap);

	    }

	     

	    private boolean wordPatternMatchHelper(int pStart, String pattern, 

	                                      int sStart, String str, 

	                                      Map<Character, String> forwardMap, 

	                                      Map<String, Character> invertedMap) {

	        if (pStart == pattern.length() && sStart == str.length()) {

	            return true;

	        }

	         

	        if (pStart >= pattern.length() || sStart >= str.length()) {

	            return false;

	        }

	         

	        char pChar = pattern.charAt(pStart);

	        for (int i = sStart; i < str.length(); i++) {

	            String curr = str.substring(sStart, i + 1);

	             

	            if ((!forwardMap.containsKey(pChar)) && (!invertedMap.containsKey(curr))) {

	                forwardMap.put(pChar, curr);

	                invertedMap.put(curr, pChar);

	                 

	                if (wordPatternMatchHelper(pStart + 1, pattern, i + 1, 

	                        str, forwardMap, invertedMap)) {

	                    return true;

	                }

	                 

	                forwardMap.remove(pChar);

	                invertedMap.remove(curr);

	            } else if (forwardMap.containsKey(pChar) && invertedMap.containsKey(curr)) {

	                String dict = forwardMap.get(pChar);

	                char pCharDict = invertedMap.get(curr);

	                 

	                // IMPORTANT !! If not equal, instead of returnning false immedidately,

	                // We need to try other longer substrings. 

	                if (!dict.equals(curr) || pCharDict != pChar) {

	                    continue;

	                }

	                 

	                if (wordPatternMatchHelper(pStart + 1, pattern, i + 1, str, 

	                        forwardMap, invertedMap)) {

	                    return true;

	                }

	            }

	        }

	         

	        return false;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
