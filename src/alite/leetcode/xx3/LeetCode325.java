package alite.leetcode.xx3;

import java.util.HashMap;

/**
 * LeetCode 325 - Maximum Size Subarray Sum Equals k
 * 
 * http://buttercola.blogspot.com/2016/01/leetcode-maximum-size-subarray-sum.html
 * Given an array nums and a target value k, find the maximum length of a
 * subarray that sums to k. \ If there isn't one, return 0 instead. Example 1:
 * Given nums = [1, -1, 5, -2, 3], k = 3, return 4. (because the subarray [1,
 * -1, 5, -2] sums to 3 and is the longest) Example 2: Given nums = [-2, -1, 2,
 * 1], k = 1, return 2. (because the subarray [-1, 2] sums to 1 and is the
 * longest) Follow Up: Can you do it in O(n) time?
 * 
 * 
 * Note the map.put(0, -1). We need to put this entry into the map before,
 * because if the maximal range starts from 0, we need to calculate sum(j) -
 * sum(i - 1). The problem is equal to: find out a range from i to j, in which
 * the sum (nums[i], ..., nums[j]) = k. What is the maximal range?
 * 
 * So we can first calculate the prefix sum of each number, so sum(i, j) =
 * sum(j) - sum(i - 1) = k. Therefore, for each sum(j), we only need to check if
 * there was a sum(i - 1) which equals to sum(j) - k. We can use a hash map to
 * store the previous calculated sum. Also:
 * https://fxrcode.gitbooks.io/leetcodenotebook/content/Leetcode_Medium/maximum_size_subarray_sum_equals_k.html
 * 
 * 
 * 
 * public int maxSubArrayLen(int[] nums, int k) {
 * 
 * if(nums == null || nums.length == 0) {
 * 
 * return 0;
 * 
 * }
 * 
 * 
 * 
 * int maxLen = 0;
 * 
 * Map<Integer, Integer> map = new HashMap<>();
 * 
 * map.put(0, -1); // IMPOARTANT
 * 
 * int sum = 0;
 * 
 * 
 * 
 * for (int i = 0; i < nums.length; i++) {
 * 
 * sum += nums[i];
 * 
 * if (!map.containsKey(sum)) {
 * 
 * map.put(sum, i);
 * 
 * }
 * 
 * 
 * 
 * if (map.containsKey(sum - k)) {
 * 
 * maxLen = Math.max(maxLen, i - map.get(sum - k));
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 * return maxLen;
 * 
 * }
 * https://leetcode.com/discuss/77879/o-n-super-clean-9-line-java-solution-with-hashmap
 * public int maxSubArrayLen(int[] nums, int k) { int sum = 0, max = 0;
 * HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); for (int i =
 * 0; i < nums.length; i++) { sum = sum + nums[i]; if (sum == k) max = i + 1;
 * else if (map.containsKey(sum - k)) max = Math.max(max, i - map.get(sum - k));
 * if (!map.containsKey(sum)) map.put(sum, i); } return max; } The HashMap
 * stores the sum of all elements before index i as key, and i as value. For
 * each i, check not only the current sum but also (currentSum - previousSum) to
 * see if there is any that equals k, and update max length.
 * https://leetcode.com/discuss/78533/java-o-n-explain-how-i-come-up-with-this-idea
 * The subarray sum reminds me the range sum problem. Preprocess the input array
 * such that you get the range sum in constant time. sum[i] means the sum from 0
 * to i inclusively the sum from i to j is sum[j] - sum[i - 1] except that from
 * 0 to j is sum[j]. j-i is equal to the length of subarray of original array.
 * we want to find the max(j - i) for any sum[j] we need to find if there is a
 * previous sum[i] such that sum[j] - sum[i] = k Instead of scanning from 0 to j
 * -1 to find such i, we use hashmap to do the job in constant time. However,
 * there might be duplicate value of of sum[i] we should avoid overriding its
 * index as we want the max j - i, so we want to keep i as left as possible.
 * public class Solution { public int maxSubArrayLen(int[] nums, int k) { if
 * (nums == null || nums.length == 0) return 0; int n = nums.length; for (int i
 * = 1; i < n; i++) nums[i] += nums[i - 1]; Map<Integer, Integer> map = new
 * HashMap<>(); map.put(0, -1); // add this fake entry to make sum from 0 to j
 * consistent int max = 0; for (int i = 0; i < n; i++) { if
 * (map.containsKey(nums[i] - k)) max = Math.max(max, i - map.get(nums[i] - k));
 * if (!map.containsKey(nums[i])) // keep only 1st duplicate as we want first
 * index as left as possible map.put(nums[i], i); } return max; } }
 * http://algobox.org/maximum-size-subarray-sum-equals-k/ Subarray sum also
 * known as Range sum can be defined as the sum of all elements in the range [i,
 * j) where i < jdenoted by S(i, j). Note that in this definition, i is
 * inclusive and j is exclusive. When i = 0, we often call it prefix sum,
 * denoted by S[j] = S(0, j). It is easy to see that S(i, j) = S[j] – S[i] and
 * 0<= i < n and 0 < j <= n Therefore if we have all the prefix sums calculated
 * in O(n) any query of S(i, j) is O(1). This is exactly what we did in the
 * problem Range sum query – Immutable OK, now let’s consider this problem.
 * Suppose we calculated all the prefix sums and stored it in an array sums then
 * the problem becomes: maximize j - i subject to sums[j] - sums[i] = k This is
 * only a variant of the famous problem two sum. The solution is almost the same
 * 
 * 
 * public int maxSubArrayLen(int[] nums, int k) {
 * 
 * 
 * int ans = 0;
 * 
 * 
 * Map<Integer, Integer> map = new HashMap<Integer, Integer>();
 * 
 * 
 * int n = nums.length;
 * 
 * 
 * int[] sums = new int[n + 1];
 * 
 * 
 * for (int i = 0; i < n; ++i) {
 * 
 * 
 * sums[i + 1] = sums[i] + nums[i];
 * 
 * 
 * map.put(sums[i + 1] - k, i + 1);
 * 
 * 
 * }
 * 
 * 
 * for (int i = 0; i < n; ++i) {
 * 
 * 
 * Integer j = map.get(sums[i]);
 * 
 * 
 * if (j != null && j - i > ans) ans = j - i;
 * 
 * 
 * }
 * 
 * 
 * return ans;
 * 
 * 
 * }
 * 
 * This solution is quite fast, it runs in 24 ms in LeetCode. But this is not
 * one pass. Similar to the two sum, we can do this in one pass too.
 * 
 * 
 * public int maxSubArrayLen(int[] nums, int k) {
 * 
 * 
 * int n = nums.length, ans = 0, sum = 0;
 * 
 * 
 * Map<Integer, Integer> map = new HashMap<>();
 * 
 * 
 * for (int i = 0; i < n; ++i) {
 * 
 * 
 * map.putIfAbsent(sum, i);
 * 
 * 
 * sum += nums[i];
 * 
 * 
 * ans = Math.max(ans, i + 1 - map.getOrDefault(sum - k, i + 1));
 * 
 * 
 * }
 * 
 * 
 * return ans;
 * 
 * 
 * }
 * 
 * Despite its shortness and conciseness, it runs much slower, 36 ms, in
 * LeetCode. HashMap operations are far slower than array in Java.
 * 
 * public int maxSubArrayLen(int[] nums, int k) {
 * 
 * int n = nums.length, ans = 0, sum = 0;
 * 
 * Map<Integer, Integer> map = new HashMap<>();
 * 
 * for (int i = 0; i < n; ++i) {
 * 
 * map.putIfAbsent(sum, i);
 * 
 * sum += nums[i];
 * 
 * if (map.containsKey(sum - k))
 * 
 * ans = Math.max(ans, i + 1 - map.get(sum - k));
 * 
 * }
 * 
 * return ans;
 * 
 * }
 * 
 * http://www.cnblogs.com/EdwardLiu/p/5104280.html
 * http://www.cnblogs.com/vision-love-programming/p/5102494.html public int
 * maxSubArrayLen(int[] nums, int k) { HashMap<Integer, Integer> map = new
 * HashMap<Integer, Integer>(); int sum = 0; int result = 0; map.put(0, -1); for
 * (int i = 0; i < nums.length; i++) { sum += nums[i]; if (map.containsKey(sum -
 * k)) { result = Math.max(result, i - map.get(sum - k)); } if
 * (!map.containsKey(sum)) { map.put(sum, i); } } return result; }
 * 
 * @author het
 *
 */
public class LeetCode325 {
	public int maxSubArrayLen(int[] nums, int k) {
		int sum = 0, max = 0;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			sum = sum + nums[i];
			if (sum == k)
				max = i + 1;
			else if (map.containsKey(sum - k))
				max = Math.max(max, i - map.get(sum - k));
			if (!map.containsKey(sum))
				map.put(sum, i);
		}
		return max;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
