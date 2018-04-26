package alite.leetcode.selected;
/**
 * LEETCODE 279. PERFECT SQUARES
LC address: Perfect Squares

Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.

Analysis:

基本DP。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
public class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        int root = 1;
        for (int i = 1; i <= n; i++) {
            if (root * root == i) {
                dp[i] = 1;
                root += 1;
            } else {
                int temp = i;
                for (int j = 1; j < root; j++) {
                    temp = Math.min(temp, dp[i - j * j]);
                }
                dp[i] = temp + 1;
            }
        }
        return dp[n];
    }
}
 * @author het
 *
 */
public class L279 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new L279().numSquares(13));

	}
	
	
	 public int numSquares(int n) {
	        int[] dp = new int[n + 1];
	        dp[0] = 0;
	        int root = 1;
	        for (int i = 1; i <= n; i++) {
	            if (root * root == i) {
	                dp[i] = 1;
	                root += 1;
	            } else {
	                int temp = i;
	                for (int j = 1; j < root; j++) {
	                    temp = Math.min(temp, dp[i - j * j]+1);
	                }
	                dp[i] = temp;
	            }
	        }
	        return dp[n];
	    }

}
