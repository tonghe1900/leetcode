package Leetcode600x;
/**
 * Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. And you need to output the maximum average value.

Example 1:
Input: [1,12,-5,-6,50,3], k = 4
Output: 12.75
Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
Note:
1 <= k <= n <= 30,000.
Elements of the given array will be in the range [-10,000, 10,000].
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode643 {
//https://leetcode.com/problems/maximum-average-subarray-i/solution/
	
	public class Solution {
		public double findMaxAverage(int[] nums, int k) {
			int[] sum = new int[nums.length];
			sum[0] = nums[0];
			for (int i = 1; i < nums.length; i++)
			sum[i] = sum[i - 1] + nums[i];
			double res = sum[k - 1] * 1.0 / k;
			for (int i = k; i < nums.length; i++) {
				res = Math.max(res, (sum[i] - sum[i - k]) * 1.0 / k);
			}
			return res;
		}
	}
	
	
	public class Solution {
	    public double findMaxAverage(int[] nums, int k) {
	        double sum=0;
	        for(int i=0;i<k;i++)
	            sum+=nums[i];
	        double res=sum;
	        for(int i=k;i<nums.length;i++){
	            sum+=nums[i]-nums[i-k];
	                res=Math.max(res,sum);
	        }
	        return res/k;
	    }
	}

	
	
}
