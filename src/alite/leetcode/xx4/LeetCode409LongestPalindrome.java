package alite.leetcode.xx4;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/longest-palindrome/
Given a string which consists of lowercase or uppercase letters, find the length of the longest 
palindromes that can be built with those letters.
This is case sensitive, for example "Aa" is not considered a palindrome here.
Note:
Assume the length of given string will not exceed 1,010.
Example:
Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.

http://www.cnblogs.com/grandyang/p/5931874.html
这又是一道关于回文字符串的问题，LeetCode上关于回文串的题有十来道呢，也算一个比较重要的知识点。但是这道题确实不算一道难题，给了我们一个字符串，让我们找出可以组成的最长的回文串的长度，由于字符顺序可以打乱，所以问题就转化为了求偶数个字符的个数，我们了解回文串的都知道，回文串主要有两种形式，一个是左右完全对称的，比如noon, 还有一种是以中间字符为中心，左右对称，比如bob，level等，那么我们统计出来所有偶数个字符的出现总和，然后如果有奇数个字符的话，我们取取出其最大偶数，然后最后结果加1即可.
    int longestPalindrome(string s) {
        int res = 0;
        bool mid = false;
        unordered_map<char, int> m;
        for (char c : s) ++m[c];
        for (auto it = m.begin(); it != m.end(); ++it) {
            res += it->second;
            if (it->second % 2 == 1) {
                res -= 1;
                mid = true;
            } 
        }
        return mid ? res + 1 : res;
    }

http://bookshadow.com/weblog/2016/10/02/leetcode-longest-palindrome/
统计每个字母的出现次数：
  若字母出现偶数次，则直接累加至最终结果
  若字母出现奇数次，则将其值-1之后累加至最终结果
若存在出现奇数次的字母，将最终结果+1
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: int
        """
        ans = odd = 0
        cnt = collections.Counter(s)
        for c in cnt:
            ans += cnt[c]
            if cnt[c] % 2 == 1:
                ans -= 1
                odd += 1
        return ans + (odd > 0)


https://discuss.leetcode.com/topic/61300/simple-hashset-solution-java/3
public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }

        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
public int longestPalindrome(String s) {
        if(s==null || s.length()==0) return 0;
        HashSet<Character> hs = new HashSet<Character>();
        int count = 0;
        for(int i=0; i<s.length(); i++){
            if(hs.contains(s.charAt(i))){
                hs.remove(s.charAt(i));
                count++;
            }else{
                hs.add(s.charAt(i));
            }
        }
        if(!hs.isEmpty()) return count*2+1;
        return count*2;
}
https://discuss.leetcode.com/topic/61359/java-solution-simple-and-clear-using-int-26
public int longestPalindrome(String s) {
    int[] lowercase = new int[26];
    int[] uppercase = new int[26];
    int res = 0;
    for (int i = 0; i < s.length(); i++){
        char temp = s.charAt(i);
        if (temp >= 97) lowercase[temp-'a']++;
        else uppercase[temp-'A']++;
    }
    for (int i = 0; i < 26; i++){
        res+=(lowercase[i]/2)*2;
        res+=(uppercase[i]/2)*2;
    }
    return res == s.length() ? res : res+1; 
 * @author het
 *
 */
public class LeetCode409LongestPalindrome {
	public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }

        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
