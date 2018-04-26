package alite.leetcode.newExtra.L500;
/**
 * LeetCode 523 - Continuous Subarray Sum

https://leetcode.com/problems/continuous-subarray-sum
Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous
 subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.
Example 1:
Input: [23, 2, 4, 6, 7],  k=6
Output: True
Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
Example 2:
Input: [23, 2, 6, 4, 7],  k=6
Output: True
Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
Note:
The length of the array won't exceed 10,000.
You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
https://discuss.leetcode.com/topic/80793/java-o-n-time-o-k-space
We iterate through the input array exactly once, keeping track of the running sum mod k of the elements in the process. If we find that a running sum value at index j has been previously seen before in some earlier index i in the array, then we know that the sub-array (i,j] contains a desired sum.
public boolean checkSubarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>(){{put(0,-1);}};;
    int runningSum = 0;
    for (int i=0;i<nums.length;i++) {
        runningSum += nums[i];
        if (k != 0) runningSum %= k; 
        Integer prev = map.get(runningSum);
        if (prev != null) {
            if (i - prev > 1) return true;
        }
        else map.put(runningSum, i);
    }
    return false;
}
https://discuss.leetcode.com/topic/80820/not-smart-solution-but-easy-to-understand
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0)   return false;
        
        int[] preSum = new int[nums.length+1];
        
        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+2; j <= nums.length; j++) {
                if (k == 0) {
                    if (preSum[j] - preSum[i] == 0) {
                        return true;
                    }
                } else if ((preSum[j] - preSum[i]) % k == 0) {
                    return true;
                }
            }
        }
        return false;
    }


https://discuss.leetcode.com/topic/80817/need-to-pay-attention-to-a-lot-of-corner-cases
This problem contributed a lot of bugs to my contest score... Let's read the description again, pay attention to red sections:
Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.
Some damn it! test cases:
[0], 0 -> false;
[5, 2, 4], 5 -> false;
[0, 0], 100 -> true;
[1,5], -6 -> true;
etc...
    public boolean checkSubarraySum(int[] nums, int k) {
        // Since the size of subarray is at least 2.
        if (nums.length <= 1) return false;
        // Two continuous "0" will form a subarray which has sum = 0. 0 * k == 0 will always be true. 
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0 && nums[i + 1] == 0) return true;
        }
        
        // At this point, k can't be "0" any longer.
        if (k == 0) return false;
        // Let's only check positive k. Because if there is a n makes n * k = sum, it is always true -n * -k = sum.
        if (k < 0) k = -k;
        
        Set<Integer> sums = new HashSet<>();
        int sum = 0;
        sums.add(0);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            
            if (i > 0) {
                // Validate from the biggest possible n * k to k
                for (int j = (sum / k) * k; j >= k; j -= k) {
                    if (sums.contains(sum - j)) return true;
                }
            }
            
            sums.add(sum);
        }
        
        return false;
    }
http://tianshilei.me/2017/03/25/leetcode523/
这道题目如果很直观地做，很容易就想到暴力的方法，利用二重循环遍历上界和下界，然后再用一个循环将这一段区间内的元素加起来，时间复杂度为 (n3)O(n3)。稍微进行一点优化，利用 sum(i,j)=sum(j)−sum(i−1)sum(i,j)=sum(j)−sum(i−1) 就可以得到一个时间复杂度为 (n2)O(n2) 的算法
 * @author het
 *
 */
public class L523 {
	   public boolean checkSubarraySum(int[] nums, int k) {
	        if (nums == null || nums.length == 0)   return false;
	        
	        int[] preSum = new int[nums.length+1];
	        
	        for (int i = 1; i <= nums.length; i++) {
	            preSum[i] = preSum[i-1] + nums[i-1];
	        }
	        
	        for (int i = 0; i < nums.length; i++) {
	            for (int j = i+2; j <= nums.length; j++) {
	                if (k == 0) {
	                    if (preSum[j] - preSum[i] == 0) {
	                        return true;
	                    }
	                } else if ((preSum[j] - preSum[i]) % k == 0) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
