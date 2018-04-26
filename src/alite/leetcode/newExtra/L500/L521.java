package alite.leetcode.newExtra.L500;

public class L521 {
/**
 * LeeCode 521 - Longest Uncommon Subsequence I

https://leetcode.com/problems/longest-uncommon-subsequence-i
Given a group of two strings, you need to find the longest uncommon subsequence of this group of two strings. 
The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence 
should not be any subsequence of the other strings.
A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing 
the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string\\\\\\\\
 is a subsequence of any string.
The input will be two strings, and the output needs to be the length of the longest uncommon subsequence. If the longest
 uncommon subsequence doesn't exist, return -1.
Example 1:
Input: "aba", "cdc"
Output: 3
Explanation: The longest uncommon subsequence is "aba" (or "cdc"), 
because "aba" is a subsequence of "aba", 
but not a subsequence of any other strings in the group of two strings. 
Note:


Both strings' lengths will not exceed 100.
Only letters from a ~ z will appear in input strings.


https://discuss.leetcode.com/topic/85020/java-1-liner
public int findLUSlength(String a, String b) {
    return a.equals(b) ? -1 : Math.max(a.length(), b.length());
}
 * @param args
 */public int findLUSlength(String a, String b) {
	    return a.equals(b) ? -1 : Math.max(a.length(), b.length());
 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
