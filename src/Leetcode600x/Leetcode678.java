package Leetcode600x;
/**
 * 678. Valid Parenthesis String
DescriptionHintsSubmissionsDiscussSolution
Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:

Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
An empty string is also valid.
Example 1:
Input: "()"
Output: True
Example 2:
Input: "(*)"
Output: True
Example 3:
Input: "(*))"
Output: True
Note:
The string size will be in the range [1, 100].
Seen this question in a real interview before? 
 * @author tonghe
 *
 */
public class Leetcode678 {
//https://leetcode.com/problems/valid-parenthesis-string/solution/
	
	class Solution {
	    boolean ans = false;

	    public boolean checkValidString(String s) {
	        solve(new StringBuilder(s), 0);
	        return ans;
	    }

	    public void solve(StringBuilder sb, int i) {
	        if (i == sb.length()) {
	            ans |= valid(sb);
	        } else if (sb.charAt(i) == '*') {
	            for (char c: "() ".toCharArray()) {
	                sb.setCharAt(i, c);
	                solve(sb, i+1);
	                if (ans) return;
	            }
	            sb.setCharAt(i, '*');
	        } else
	            solve(sb, i + 1);
	    }

	    public boolean valid(StringBuilder sb) {
	        int bal = 0;
	        for (int i = 0; i < sb.length(); i++) {
	            char c = sb.charAt(i);
	            if (c == '(') bal++;
	            if (c == ')') bal--;
	            if (bal < 0) break;
	        }
	        return bal == 0;
	    }
	}
	
	
	
	
	class Solution {
	    public boolean checkValidString(String s) {
	        int n = s.length();
	        if (n == 0) return true;
	        boolean[][] dp = new boolean[n][n];

	        for (int i = 0; i < n; i++) {
	            if (s.charAt(i) == '*') dp[i][i] = true;
	            if (i < n-1 &&
	                    (s.charAt(i) == '(' || s.charAt(i) == '*') &&
	                    (s.charAt(i+1) == ')' || s.charAt(i+1) == '*')) {
	                dp[i][i+1] = true;
	            }
	        }

	        for (int size = 2; size < n; size++) {
	            for (int i = 0; i + size < n; i++) {
	                if (s.charAt(i) == '*' && dp[i+1][i+size] == true) {
	                    dp[i][i+size] = true;
	                } else if (s.charAt(i) == '(' || s.charAt(i) == '*') {
	                    for (int k = i+1; k <= i+size; k++) {
	                        if ((s.charAt(k) == ')' || s.charAt(k) == '*') &&
	                                (k == i+1 || dp[i+1][k-1]) &&
	                                (k == i+size || dp[k+1][i+size])) {
	                            dp[i][i+size] = true;
	                        }
	                    }
	                }
	            }
	        }
	        return dp[0][n-1];
	    }
	}
	
	
	
	
	
	class Solution {
	    public boolean checkValidString(String s) {
	       int lo = 0, hi = 0;
	       for (char c: s.toCharArray()) {
	           lo += c == '(' ? 1 : -1;
	           hi += c != ')' ? 1 : -1;
	           if (hi < 0) break;
	           lo = Math.max(lo, 0);
	       }
	       return lo == 0;
	    }
	}
}
