package Leetcode600x;
/**
 * There is a strange printer with the following two special requirements:

The printer can only print a sequence of the same character each time.
At each turn, the printer can print new characters starting from and ending at any places, and will cover the original existing characters.
Given a string consists of lower English letters only, your job is to count the minimum number of turns the printer needed in order to print it.

Example 1:
Input: "aaabbb"
Output: 2
Explanation: Print "aaa" first and then print "bbb".
Example 2:
Input: "aba"
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.
Hint: Length of the given string will not exceed 100.

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode664 {
//https://leetcode.com/problems/strange-printer/solution/
	
	class Solution {
	    int[][] memo;
	    public int strangePrinter(String s) {
	        int N = s.length();
	        memo = new int[N][N];
	        return dp(s, 0, N - 1);
	    }
	    public int dp(String s, int i, int j) {
	        if (i > j) return 0;
	        if (memo[i][j] == 0) {
	            int ans = dp(s, i+1, j) + 1;
	            for (int k = i+1; k <= j; ++k)
	                if (s.charAt(k) == s.charAt(i))
	                    ans = Math.min(ans, dp(s, i, k-1) + dp(s, k+1, j));
	            memo[i][j] = ans;
	        }
	        return memo[i][j];
	    }
	}
}
