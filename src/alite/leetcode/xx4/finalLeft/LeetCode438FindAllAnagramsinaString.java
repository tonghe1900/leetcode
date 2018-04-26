package alite.leetcode.xx4.finalLeft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/
Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
The order of output does not matter.
Example 1:
Input:
s: "cbaebabacd" p: "abc"

Output:
[0, 6]

Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
Example 2:

Input:
s: "abab" p: "ab"

Output:
[0, 1, 2]

Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
X. use map
https://discuss.leetcode.com/topic/64423/java-o-n-using-hashmap-easy-understanding
https://discuss.leetcode.com/topic/64447/java-o-n-solution-hashmap-sliding-window
https://discuss.leetcode.com/topic/71138/sliding-window-and-hashmap-o-n
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        if (p.length() > s.length()) {
            return result;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        int match = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    match++;
                }
            }
            if (i >= p.length()) {
                c = s.charAt(i - p.length());
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                    if (map.get(c) == 1) {
                        match--;
                    }
                }
            }
            if (match == map.size()) {
                result.add(i - p.length() + 1);
            }
        }
        return result;
    }
https://discuss.leetcode.com/topic/64434/shortest-concise-java-o-n-sliding-window-solution
https://tech.liuchao.me/2016/11/leetcode-solution-438/
思路：直接的方式是暴力用hash，这样时间复杂度O(m*n)．可以用hash+slide window来优化到O(n)．
Same idea from a fantastic sliding window template, please refer:
https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems
Time Complexity will be O(n) because the "start" and "end" points will only move from left to right once.
Basically, we are interested only when every hash[i] becomes 0. There are a number of ways of doing it. To understand OP's approach, we observe that:
the sum of all hash[i] is always >=0;
count is the sum of all positive hash[i];
therefore, every hash[i] is zero if and only if count is 0.
The genius of this approach is that the code is shorter, compared to our instinctive approach of maintaining the count of hash[i]==0.

eed to convert a char to int, 256 is good..
Oh, wait. only lower case letters, 26 is sufficient but don't want to type - 'a'
Well, could find out what 'z' is and use that, but probably not worth the effort
Yeah, 256 is good...
The same goes for 128, except the other author probably roughly knows 'z' is just over 100
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> list = new ArrayList<>();
    if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
    int[] hash = new int[256]; //character hash
    //record each character in p to hash
    for (char c : p.toCharArray()) {
        hash[c]++;
    }
    //two points, initialize count to p's length
    int left = 0, right = 0, count = p.length();
    while (right < s.length()) {
        //move right everytime, if the character exists in p's hash, decrease the count
        //current hash value >= 1 means the character is existing in p
        if (hash[s.charAt(right++)]-- >= 1) count--; 
        
        //when the count is down to 0, means we found the right anagram
        //then add window's left to result list
        if (count == 0) list.add(left);
    
        //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
        //++ to reset the hash because we kicked out the left
        //only increase the count if the character is in p
        //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
        if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
    }
    return list;
}
https://discuss.leetcode.com/topic/64622/17ms-java-sliding-window
public List<Integer> findAnagrams(String s, String p) {
    int[] chars = new int[26];//\\
    List<Integer> result = new ArrayList<>();

    if (s == null || p == null || s.length() < p.length())
        return result;
    for (char c : p.toCharArray())
        chars[c-'a']++;

    int start = 0, end = 0, count = p.length();
    // Go over the string
    while (end < s.length()) {
        // If the char at start appeared in p, we increase count
        if (end - start == p.length() && chars[s.charAt(start++)-'a']++ >= 0)
            count++;
        // If the char at end appeared in p (since it's not -1 after decreasing), we decrease count
        if (--chars[s.charAt(end++)-'a'] >= 0)
            count--;
        if (count == 0)
            result.add(start);
    }
    
    return result;
}
X.
https://discuss.leetcode.com/topic/64491/java-using-isanagram-helper-function-easy-to-understand
   public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (p == null || s == null || s.length() < p.length()) return res;
        int m = s.length(), n = p.length();
        for (int i = 0; i < m-n+1; i++) {
            String cur = s.substring(i, i+n);
            if (helper(cur, p)) res.add(i);
        }
        return res;
    }
    public boolean helper(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        int[] dict = new int[26];
        for (int i = 0; i < a.length(); i++) {
            char ch = a.charAt(i);
            dict[ch-'a']++;
        }
        for (int i = 0; i < b.length(); i++) {
            char ch = b.charAt(i);
            dict[ch-'a']--;
            if (dict[ch-'a'] < 0) return false;
        }
        return true;
    }

X. Not efficient
https://dyang2016.wordpress.com/2016/10/26/438-find-all-anagrams-in-a-string/
https://discuss.leetcode.com/topic/64401/java-sliding-window
https://discuss.leetcode.com/topic/64417/commented-java-solution
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if(p.length() > s.length())
            return res;
        char[] sStr = s.toCharArray();
        int[]map = new int[26];
        for(char ch:p.toCharArray())
            map[ch - 'a']++;
        int n = s.length(), m = p.length();;
        int j = 0;
        for(j=0; j<m-1; j++)
            map[sStr[j] - 'a']--;
        for(int i=0; j<n; i++, j++){
            map[sStr[j] - 'a']--;
            if(check(map))
                res.add(i);
            map[sStr[i] - 'a']++;
        }
        return res;
    }
    public boolean check(int[]map){
        for(int n:map)
            if(n > 0)   return false;
        return true;
    }
X. Using rolling hash -> convert to long


http://blog.gainlo.co/index.php/2016/04/08/if-a-string-contains-an-anagram-of-another-string/
How to check if a string contains an anagram of another string?
 * @author het
 *
 */
public class LeetCode438FindAllAnagramsinaString {
	 public static List<Integer> findAnagrams(String s, String p) {
	        List<Integer> result = new ArrayList<>();
	        if (s == null || s.length() == 0) {
	            return result;
	        }
	        if (p.length() > s.length()) {
	            return result;
	        }
	        Map<Character, Integer> map = new HashMap<>();
	        for (int i = 0; i < p.length(); i++) {
	            char c = p.charAt(i);
	            if (map.containsKey(c)) {
	                map.put(c, map.get(c) + 1);
	            } else {
	                map.put(c, 1);
	            }
	        }
	        int match = 0;
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if (map.containsKey(c)) {
	                map.put(c, map.get(c) - 1);
	                if (map.get(c) == 0) {
	                    match++;
	                }
	            }
	            if (i >= p.length()) {
	                c = s.charAt(i - p.length());
	                if (map.containsKey(c)) {
	                    map.put(c, map.get(c) + 1);
	                    if (map.get(c) == 1) {
	                        match--;
	                    }
	                }
	            }
	            if (match == map.size()) {
	                result.add(i - p.length() + 1);
	            }
	        }
	        return result;
	    }
	public static void main(String[] args) {
		System.out.println(findAnagrams("cbaebabyacd","abcabed"));
		
//		Input:
//			s: "cbaebabacd" p: "abc"
//
//			Output:
//			[0, 6]
//
//			Explanation:
//			The substring with start index = 0 is "cba", which is an anagram of "abc".
//			The substring with start index = 6 is "bac", which is an anagram of "abc".
//			Example 2:
//
//			Input:
//			s: "abab" p: "ab"
//
//			Output:
//			[0, 1, 2]
		
		// TODO Auto-generated method stub

	}

}
