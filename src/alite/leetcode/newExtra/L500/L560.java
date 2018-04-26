package alite.leetcode.newExtra.L500;
/**
 * LeetCode 560 - Subarray Sum Equals K

https://leetcode.com/problems/subarray-sum-equals-k
Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
Example 1:
Input:nums = [1,1,1], k = 2
Output: 2
Note:
The length of the array is in range [1, 20,000].
The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

https://discuss.leetcode.com/topic/87850/java-solution-presum-hashmap
Solution 1. Brute force. We just need two loops (i, j) and test if SUM[i, j] = k. Time complexity O(n^2), Space complexity O(1). I bet this solution will TLE.
Solution 2. From solution 1, we know the key to solve this problem is SUM[i, j]. So if we know SUM[0, i - 1] and SUM[0, j], then we can easily get SUM[i, j]. To achieve this, we just need to go through the array, calculate the current sum and save number of all seen PreSum to a HashMap. Time complexity O(n), Space complexity O(n).
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }
        
        return result;
    }
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        
        for (int num : nums) {
            sum += num;
            result += preSum.getOrDefault(sum - k, 0);
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }   
        return result;
    }
http://bookshadow.com/weblog/2017/04/30/leetcode-subarray-sum-equals-k/
利用字典cnt统计前N项和出现的个数

遍历数组nums：

    在cnt中将sums的计数+1

    累加前N项和为sums

    将cnt[sums - k]累加至答案
    def subarraySum(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """
        ans = sums = 0
        cnt = collections.Counter()
        for num in nums:
            cnt[sums] += 1
            sums += num
            ans += cnt[sums - k]
        return ans

You might also like

 * @author het
 *
 */
public class L560 {
	 public int subarraySum(int[] nums, int k) {
	        int sum = 0, result = 0;
	        Map<Integer, Integer> preSum = new HashMap<>();
	        preSum.put(0, 1);
	        
	        for (int num : nums) {
	            sum += num;
	            result += preSum.getOrDefault(sum - k, 0);
	            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
	        }   
	        return result;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
