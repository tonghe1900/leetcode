package alite.leetcode.newExtra.finished;
/**
 * Longest Substring with At Most K Distinct Characters
 
This is a problem asked by Google.

Given a string, find the longest substring that contains only two unique characters. For example, given "abcbbbbcccbdddadacb", the longest substring that contains 2 unique character is "bcbbbbcccb".

1. Longest Substring Which Contains 2 Unique Characters

In this solution, a hashmap is used to track the unique elements in the map. When a third character is added to the map, the left pointer needs to move right.

You can use "abac" to walk through this solution.

public int lengthOfLongestSubstringTwoDistinct(String s) {
    int max=0;
    HashMap<Character,Integer> map = new HashMap<Character, Integer>();
    int start=0;
 
    for(int i=0; i<s.length(); i++){
        char c = s.charAt(i);
        if(map.containsKey(c)){
            map.put(c, map.get(c)+1);
        }else{
            map.put(c,1);
        }
 
        if(map.size()>2){
            max = Math.max(max, i-start);
 
            while(map.size()>2){
                char t = s.charAt(start);
                int count = map.get(t);
                if(count>1){
                    map.put(t, count-1);
                }else{
                    map.remove(t);
                }
                start++;
            }
        }
    }
 
    max = Math.max(max, s.length()-start);
 
    return max;
}
Now if this question is extended to be "the longest substring that contains k unique characters", what should we do?

2. Solution for K Unique Characters

UPDATE ON 7/21/2016.

The following solution is corrected. Given "abcadcacacaca" and 3, it returns "cadcacacaca".

public int lengthOfLongestSubstringKDistinct(String s, int k) {
    if(k==0 || s==null || s.length()==0)
        return 0;
 
    if(s.length()<k)
        return s.length();
 
    HashMap<Character, Integer> map = new HashMap<Character, Integer>();
 
    int maxLen=k;
    int left=0;
    for(int i=0; i<s.length(); i++){
        char c = s.charAt(i);
        if(map.containsKey(c)){
            map.put(c, map.get(c)+1);
        }else{
            map.put(c, 1);
        }
 
        if(map.size()>k){
            maxLen=Math.max(maxLen, i-left);
 
            while(map.size()>k){
 
                char fc = s.charAt(left);
                if(map.get(fc)==1){
                    map.remove(fc);
                }else{
                    map.put(fc, map.get(fc)-1);
                }
 
                left++;
            }
        }
 
    }
 
    maxLen = Math.max(maxLen, s.length()-left);
 
    return maxLen;
}
Time is O(n).





[LeetCode] Longest Substring with At Most K Distinct Characters 最多有K个不同字符的最长子串
 

Given a string, find the length of the longest substring T that contains at most k distinct characters.

For example, Given s = “eceba” and k = 2,

T is "ece" which its length is 3.

 

这道题是之前那道Longest Substring with At Most Two Distinct Characters的拓展，而且那道题中的解法一和解法二直接将2换成k就行了，具体讲解请参考之前那篇博客：

 

解法一：

复制代码
class Solution {
public:
    int lengthOfLongestSubstringKDistinct(string s, int k) {
        int res = 0, left = 0;
        unordered_map<char, int> m;
        for (int i = 0; i < s.size(); ++i) {
            ++m[s[i]];
            while (m.size() > k) {
                if (--m[s[left]] == 0) m.erase(s[left]);
                ++left;
            }
            res = max(res, i - left + 1);
        }
        return res;
    }
};
复制代码
 

具体讲解请参考之前那篇博客Longest Substring with At Most Two Distinct Characters，参见代码如下：

 

解法二：

复制代码
class Solution {
public:
    int lengthOfLongestSubstringKDistinct(string s, int k) {
        int res = 0, left = 0;
        unordered_map<char, int> m;
        for (int i = 0; i < s.size(); ++i) {
            m[s[i]] = i;
            while (m.size() > k) {
                if (m[s[left]] == left) m.erase(s[left]);
                ++left;
            }
            res = max(res, i - left + 1);
        }
        return res;
    }
};
 * @author het
 *
 */
public class LongestSubstringwithAtMostKDistinctCharacters {

}
