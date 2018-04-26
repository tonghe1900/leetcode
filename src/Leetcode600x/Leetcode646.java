package Leetcode600x;
/**
 * You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.

Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in this fashion.

Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs. You can select pairs in any order.

Example 1:
Input: [[1,2], [2,3], [3,4]]
Output: 2
Explanation: The longest chain is [1,2] -> [3,4]
Note:
The number of given pairs will be in the range [1, 1000].
Seen this question in a real interview before? 
 * @author tonghe
 *
 */
public class Leetcode646 {
//https://leetcode.com/problems/maximum-length-of-pair-chain/solution/
	
	class Solution {
	    public int findLongestChain(int[][] pairs) {
	        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
	        int N = pairs.length;
	        int[] dp = new int[N];
	        Arrays.fill(dp, 1);

	        for (int j = 1; j < N; ++j) {
	            for (int i = 0; i < j; ++i) {
	                if (pairs[i][1] < pairs[j][0])
	                    dp[j] = Math.max(dp[j], dp[i] + 1);
	            }
	        }

	        int ans = 0;
	        for (int x: dp) if (x > ans) ans = x;
	        return ans;
	    }
	}
	
	
	
	
	class Solution {
	    public int findLongestChain(int[][] pairs) {
	        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
	        int cur = Integer.MIN_VALUE, ans = 0;
	        for (int[] pair: pairs) if (cur < pair[0]) {
	            cur = pair[1];
	            ans++;
	        }
	        return ans;
	    }
	}
	
	
	
}
