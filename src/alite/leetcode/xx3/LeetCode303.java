package alite.leetcode.xx3;

/**
 * LeetCode 303 - Range Sum Query - Immutable | 书影博客
 * 
 * [LeetCode]Range Sum Query - Immutable | 书影博客 Given an integer array nums,
 * find the sum of the elements between indices i and j (i ≤ j), inclusive.
 * Example: Given nums = [-2, 0, 3, -5, 2, -1] sumRange(0, 2) -> 1 sumRange(2,
 * 5) -> -1 sumRange(0, 5) -> -3 You may assume that the array does not change.
 * There are many calls to sumRange function. 计算辅助数组sums： sums[0] = 0
 * 
 * sums[i+1] = sums[i] + nums[i] 则sumRange(i, j) = sums[j+1] - sums[i] def
 * __init__(self, nums): size = len(nums) self.sums = [0] * (size + 1) for x in
 * range(size): self.sums[x + 1] += self.sums[x] + nums[x]
 * 
 * def sumRange(self, i, j): return self.sums[j + 1] - self.sums[i]
 * http://blog.csdn.net/foreverling/article/details/49760229
 * 考虑到要多次调用sumRange()函数，因此需要把结果先存起来，调用时就可以直接返回了。最开始考虑的是用dp[i][j]来直接存储i到j之间元素的和，但是内存超出限制。于是考虑用dp[i]来存储0到i之间元素的和，0到j的和减去0到i-1的和即为所求。
 * http://www.zhuangjingyang.com/leetcode-range-sum-query-immutable/
 * 
 * public class NumArray {
 * 
 * private int[] sum;
 * 
 * public NumArray(int[] nums) {
 * 
 * sum = new int[nums.length];
 * 
 * //copy数组
 * 
 * System.arraycopy(nums,0,sum,0,nums.length);
 * 
 * for(int i=1;i<sum.length;i++){
 * 
 * sum[i] += sum[i-1];
 * 
 * }
 * 
 * }
 * 
 * 
 * public int sumRange(int i, int j) {
 * 
 * if(i>j||i<0||j>=sum.length)
 * 
 * return 0;
 * 
 * return i==0?sum[j]:sum[j] - sum[i-1];
 * 
 * }
 * 
 * }
 * 
 * @author het
 *
 */
public class LeetCode303 {
	private int[] sum;

	public LeetCode303(int[] nums) {

		sum = new int[nums.length];

		// copy数组

		System.arraycopy(nums, 0, sum, 0, nums.length);

		for (int i = 1; i < sum.length; i++) {

			sum[i] += sum[i - 1];

		}

	}

	public int sumRange(int i, int j) {

		if (i > j || i < 0 || j >= sum.length)

			return 0;

		return i == 0 ? sum[j] : sum[j] - sum[i - 1];

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
