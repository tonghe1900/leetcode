package Leetcode600x;
/**
 * 680. Valid Palindrome II
DescriptionHintsSubmissionsDiscussSolution
Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

Example 1:
Input: "aba"
Output: True
Example 2:
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
Note:
The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode680 {
//https://leetcode.com/problems/valid-palindrome-ii/solution/
	
	class Solution {
	    public boolean isPalindrome(CharSequence s) {
	        for (int i = 0; i < s.length() / 2; i++) {
	            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
	                return false;
	            }
	        }
	        return true;
	    }
	    public boolean validPalindrome(String s) {
	        StringBuilder sb = new StringBuilder(s);
	        for (int i = 0; i < s.length(); i++) {
	            char c = sb.charAt(i);
	            sb.deleteCharAt(i);
	            if (isPalindrome(sb)) return true;
	            sb.insert(i, c);
	        }
	        return isPalindrome(s);
	    }
	}
	
	
	class Solution {
	    public boolean isPalindromeRange(String s, int i, int j) {
	        for (int k = i; k <= i + (j - i) / 2; k++) {
	            if (s.charAt(k) != s.charAt(j - k + i)) return false;
	        }
	        return true;
	    }
	    public boolean validPalindrome(String s) {
	        for (int i = 0; i < s.length() / 2; i++) {
	            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
	                int j = s.length() - 1 - i;
	                return (isPalindromeRange(s, i+1, j) ||
	                        isPalindromeRange(s, i, j-1));
	            }
	        }
	        return true;
	    }
	}
}
