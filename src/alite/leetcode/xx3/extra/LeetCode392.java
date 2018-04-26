package alite.leetcode.xx3.extra;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * LeetCode 392 - Is Subsequence

http://www.cnblogs.com/grandyang/p/5842033.html
Given a string s and a string t, check if s is subsequence of t.
You may assume that there is only lower case English letters in both s and t.
 t is potentially a very long (length ~= 500,000) string, 
and s is a short string (<=100).
A subsequence of a string is a new string which is formed from the original string by deleting some 
(can be none) of the characters without disturbing the relative positions of the remaining characters.
 (ie, "ace" is a subsequence of "abcde" while "aec" is not).
Example 1:
s = "abc", t = "ahbgdc"
Return true.
Example 2:
s = "axc", t = "ahbgdc"
Return false.
Follow up:
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check 
one by one to see if T has its subsequence. In this scenario, how would you change your code?
这道题算比较简单的一种，我们可以用两个指针分别指向字符串s和t，然后如果字符相等，则i和j自增1，
反之只有j自增1，最后看i是否等于s的长度，等于说明s已经遍历完了，而且字符都有在t中出现过
    bool isSubsequence(string s, string t) {
        if (s.empty()) return true;
        int i = 0, j = 0;
        while (i < s.size() && j < t.size()) {
            if (s[i] == t[j]) {
                ++i; ++j;
            } else {
                ++j;
            }
        }
        return i == s.size();
    }
};
    bool isSubsequence(string s, string t) {
        if (s.empty()) return true;
        int i = 0, j = 0;
        while (i < s.size() && j < t.size()) {
            if (s[i] == t[j]) ++i;
            ++j;
        }
        return i == s.size();
    }
http://blog.csdn.net/mebiuw/article/details/52444480
No need to convert to char[]
    public boolean isSubsequence(String s, String t) {
        int i=0,j=0;
        char[] ss=s.toCharArray();
        char[] tt=t.toCharArray();
        while(i<ss.length && j<tt.length){
            if(ss[i]  == tt[j]){
                i++;
            }
            j++;
        }
        return i==s.length();
    }
http://www.cnblogs.com/dongling/p/5843697.html
    public boolean isSubsequence(String s, String t) {
        if(s==null||s.length()==0)
            return true;
        int index=0;
        char ch;
        for(int i=0;i<s.length();i++){
            ch=s.charAt(i);
            while(index<t.length()&&t.charAt(index)!=ch){
                index++;
            }
            if(index>=t.length()){
                return false;
            }
            index++;
        }
        return true;
    }

X. Follow up
https://scottduan.gitbooks.io/leetcode-review/content/is_subsequence.html
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one
 to see if T has its subsequence. In this scenario, how would you change your code?
https://discuss.leetcode.com/topic/57994/java-binary-search-using-treeset-got-tle
https://discuss.leetcode.com/topic/67167/java-code-for-the-follow-up-question
https://discuss.leetcode.com/topic/60134/java-code-for-the-problem-two-pointer-and-the-follow-up-binary-search
Java binary search using TreeSet, but got TLE. For single s, it performs worse than linear
 2-pointer solution for sure, but if there are many s like in the follow up, should it be better 
 since t only got processed once?
    public boolean isSubsequence(String s, String t) {
        int sLen = s.length(), tLen = t.length();
        if(sLen == 0) return true;
        if(sLen > tLen) return false;
        
        Map<Character, TreeSet<Integer>> map = new HashMap<>();
        for(int i = 0; i < tLen; i++) {
            char c = t.charAt(i);
            if(!map.containsKey(c)) map.put(c, new TreeSet<Integer>());
            map.get(c).add(i);
        }
        
        int lowerIndex = -1;
        for(int j = 0; j < sLen; j++) {
            char c = s.charAt(j);
            if(!map.containsKey(c)) return false;
            
            Integer index = map.get(c).higher(lowerIndex);
            if(index == null) return false;
            lowerIndex = index;
        }
        
        return true;
    }
https://discuss.leetcode.com/topic/58367/binary-search-solution-for-follow-up-with-detailed-comments
I think the Map and TreeSet could be simplified by Array and binarySearch. Since we scan T from beginning to the end (index itself is in increasing order), List will be sufficient. Then we can use binarySearch to replace with TreeSet ability which is a little overkill for this problem.
    // Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
    // Eg-1. s="abc", t="bahbgdca"
    // idx=[a={1,7}, b={0,3}, c={6}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=3
    //  i=2 ('c'): prev=6 (return true)
    // Eg-2. s="abc", t="bahgdcb"
    // idx=[a={1}, b={0,6}, c={5}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=6
    //  i=2 ('c'): prev=? (return false)
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }
        
        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1;
            if (j == idx[s.charAt(i)].size()) return false;
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }
    
    bool isSubsequence(string s, string t) {
        if (s.empty()) return true;
        int i = 0, j = 0;
        while (i < s.size() && j < t.size()) {
            if (s[i] == t[j]) {
                ++i; ++j;
            } else {
                ++j;
            }
        }
        return i == s.size();
    }
};
https://discuss.leetcode.com/topic/58078/simple-c-code-as-well-as-the-followup-solution
My solution is to preprocessing, exactly constructing a hash map to store the positions for every character.
 Then scan the incoming string one by one, for every character,
 if there is no such character in the hash map, or the number of such character is greater than the original string,
  or most critically, the position is not behind the position of its previous character,
   it will return false. So I need another array to record the index for every character. 
   The time complexity is just the sum of length of incoming strings???


we will build an array mem where mem[i+1][j+1] means that S[0..j] contains T[0..i] that many times as distinct subsequences. 
Therefor the result will be mem[T.length()][S.length()]. we can build this array rows-by-rows: 
the first row must be filled with 1. That's because the empty string is a subsequence of any string but only 1 time.
 So mem[0][j] = 1 for every j. So with this we not only make our lives easier, but we also return correct value 
 if T is an empty string. the first column of every rows except the first must be 0. This is because 
 an empty string cannot contain a non-empty string as a substring -- the very first item of the array:
  mem[0][0] = 1, because an empty string contains the empty string 1 time.
So the matrix looks like this:
  S 0123....j
T +----------+
  |1111111111|
0 |0         |
1 |0         |
2 |0         |
. |0         |
. |0         |
i |0         |
From here we can easily fill the whole grid: for each (x, y), we check if S[x] == T[y] we add the previous item and the previous item in the previous row, otherwise we copy the previous item in the same row. The reason is simple:
if the current character in S doesn't equal to current character T, then we have the same number of distinct subsequences as we had without the new character. 
if the current character in S equal to the current character T, then the distinct number of subsequences: the number we had before plus the distinct number of subsequences we had with less longer T and less longer S.
An example:
S: [acdabefbc] and T: [ab]
first we check with a:
           *  *
      S = [acdabefbc]
mem[1] = [0111222222]
then we check with ab:
               *  * ]
      S = [acdabefbc]
mem[1] = [0111222222]
mem[2] = [0000022244]
And the result is 4, as the distinct subsequences are:
  S = [a   b    ]
  S = [a      b ]
  S = [   ab    ]
  S = [   a   b ]

    if (s == null && t == null) {
        return true;
    }
    if (s == null || t == null) {
        return false;
    }
    int m = s.length();
    int n = t.length();
    boolean[][] dp = new boolean[n+1][m+1];
    for (int i = 0; i <= n; i++) {
        dp[i][0] = true;
    }
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (t.charAt(i-1) == s.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] || dp[i-1][j];
            } else {
                dp[i][j] = dp[i-1][j];
            }
            if (dp[i][m] == true) {
                return true;
            }
        }
    }
    return dp[n][m];
 * @author het
 *
 */
public class LeetCode392 {
	
	 public boolean isSubsequence(String s, String t) {
	        int sLen = s.length(), tLen = t.length();
	        if(sLen == 0) return true;
	        if(sLen > tLen) return false;
	        
	        Map<Character, TreeSet<Integer>> map = new HashMap<>();
	        for(int i = 0; i < tLen; i++) {
	            char c = t.charAt(i);
	            if(!map.containsKey(c)) map.put(c, new TreeSet<Integer>());
	            map.get(c).add(i);
	        }
	        
	        int lowerIndex = -1;
	        for(int j = 0; j < sLen; j++) {
	            char c = s.charAt(j);
	            if(!map.containsKey(c)) return false;
	            
	            Integer index = map.get(c).higher(lowerIndex);
	            if(index == null) return false;
	            lowerIndex = index;
	        }
	        
	        return true;
	    }
	//I think the Map and TreeSet could be simplified by Array and binarySearch. Since we scan T from beginning to the end (index itself is in increasing order), List will be sufficient. Then we can use binarySearch to replace with TreeSet ability which is a little overkill for this problem.
    // Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
    // Eg-1. s="abc", t="bahbgdca"
    // idx=[a={1,7}, b={0,3}, c={6}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=3
    //  i=2 ('c'): prev=6 (return true)
    // Eg-2. s="abc", t="bahgdcb"
    // idx=[a={1}, b={0,6}, c={5}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=6
    //  i=2 ('c'): prev=? (return false)
    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }
        
        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1;
            if (j == idx[s.charAt(i)].size()) return false;
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }
    
    bool isSubsequence(string s, string t) {
        if (s.empty()) return true;
        int i = 0, j = 0;
        while (i < s.size() && j < t.size()) {
            if (s[i] == t[j]) {
                ++i; ++j;
            } else {
                ++j;
            }
        }
        return i == s.size();
    }
};
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	bool isSubsequence(string s, string t) {
        if (s.empty()) return true;
        int i = 0, j = 0;
        while (i < s.size() && j < t.size()) {
            if (s[i] == t[j]) {
                ++i; ++j;
            } else {
                ++j;
            }
        }
        return i == s.size();
    }

}
