package alite.leetcode.newest;
/**
 * LeetCode 639 - Decode Ways II

https://leetcode.com/problems/decode-ways-ii
A message containing letters from A-Z is being encoded to numbers using the following mapping way:
'A' -> 1
'B' -> 2
...
'Z' -> 26
Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.
Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
Also, since the answer may be very large, you should return the output mod 109 + 7.
Example 1:
Input: "*"
Output: 9
Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
Example 2:
Input: "1*"
Output: 9 + 9 = 18
Note:
The length of the input string will fit in range [1, 105].
The input string will only contain the character '*' and digits '0' - '9'.

https://leetcode.com/articles/decode-ways-ii/
X. DP
https://discuss.leetcode.com/topic/95277/c-dp-o-n-time-o-1-space
For any string s longer than 2, we can decode either the last 2 characters as a whole or the last 1 character. So dp[i] = dp[i-1]* f(s.substr(i,1)) + dp[i-2]* f(s.substr(i-1, 2)). f() is the number of ways to decode a string of length 1 or 2. f() could be 0, for example f("67").
There is a lot of cases and corner cases for f(string s). For example, * cannot be '0', so ** has 15 instead of 16 possibilities, because "20" is excluded. But the time complexity is still O(n).
    int numDecodings(string s) {
        int n = s.size(), p = 1000000007;
        // f2 is the answer to sub string ending at position i; Initially i = 0.
        long f1 = 1, f2 = helper(s.substr(0,1));
        // DP to get f2 for sub string ending at position n-1;
        for (int i = 1; i < n; i++) {
            long f3 = (f2*helper(s.substr(i, 1)))+(f1*helper(s.substr(i-1, 2)));
            f1 = f2;
            f2 = f3%p;
        }
        return f2;
    }
private:
    int helper(string s) {
        if (s.size() == 1) {
            if (s[0] == '*') return 9;
            return s[0] == '0'? 0:1;
        }
        // 11-26, except 20 because '*' is 1-9
        if (s == "**")  
            return 15;
        else if (s[1] =='*') {
            if (s[0] =='1') return 9;
            return s[0] == '2'? 6:0;
        }
        else if (s[0] == '*') 
            return s[1] <= '6'? 2:1;
        else 
            // if two digits, it has to be in [10 26]; no leading 0
            return stoi(s) >= 10 && stoi(s) <= 26? 1:0; 
    }


    int M = 1000000007;
    public int numDecodings(String s) {
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                dp[i + 1] = 9 * dp[i];
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + 9 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2')
                    dp[i + 1] = (dp[i + 1] + 6 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + 15 * dp[i - 1]) % M;
            } else {
                dp[i + 1] = s.charAt(i) != '0' ? dp[i] : 0;
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + (s.charAt(i) <= '6' ? 2 : 1) * dp[i - 1]) % M;
            }
        }
        return (int) dp[s.length()];
    }
we can observe that only the last two values dp[i-2]dp[i−2] and dp[i-1]dp[i−1] are used to fill the entry at dp[i-1]dp[i−1]
    public int numDecodings(String s) {
        long first = 1, second = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            long temp = second;
            if (s.charAt(i) == '*') {
                second = 9 * second;
                if (s.charAt(i - 1) == '1')
                    second = (second + 9 * first) % M;
                else if (s.charAt(i - 1) == '2')
                    second = (second + 6 * first) % M;
                else if (s.charAt(i - 1) == '*')
                    second = (second + 15 * first) % M;
            } else {
                second = s.charAt(i) != '0' ? second : 0;
                if (s.charAt(i - 1) == '1')
                    second = (second + first) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    second = (second + first) % M;
                else if (s.charAt(i - 1) == '*')
                    second = (second + (s.charAt(i) <= '6' ? 2 : 1) * first) % M;
            }
            first = temp;
        }
        return (int) second;
    }
Approach #2 Recursion with memoization [Stack Overflow]
    public int numDecodings(String s) {
        Integer[] memo=new Integer[s.length()];
        return ways(s, s.length() - 1,memo);
    }
    public int ways(String s, int i,Integer[] memo) {
        if (i < 0)
            return 1;
        if(memo[i]!=null)
            return memo[i];
        if (s.charAt(i) == '*') {
            long res = 9 * ways(s, i - 1,memo);
            if (i > 0 && s.charAt(i - 1) == '1')
                res = (res + 9 * ways(s, i - 2,memo)) % M;
            else if (i > 0 && s.charAt(i - 1) == '2')
                res = (res + 6 * ways(s, i - 2,memo)) % M;
            else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + 15 * ways(s, i - 2,memo)) % M;
            memo[i]=(int)res;
            return memo[i];
        }
        long res = s.charAt(i) != '0' ? ways(s, i - 1,memo) : 0;
        if (i > 0 && s.charAt(i - 1) == '1')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + (s.charAt(i)<='6'?2:1) * ways(s, i - 2,memo)) % M;
        memo[i]= (int)res;
        return memo[i];
    }
 * @author het
 *
 */
public class LeetCode639 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
