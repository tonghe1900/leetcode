package Leetcode600x;
/**
 * [LeetCode]Maximum Average Subarray II 
作者是 在线疯狂 发布于 2017年7月16日 在 LeetCode.
题目描述：
LeetCode 644. Maximum Average Subarray II

Given an array consisting of n integers, find the contiguous subarray whose length is greater than or equal to k that has the maximum average value. And you need to output the maximum average value.

Example 1:

Input: [1,12,-5,-6,50,3], k = 4
Output: 12.75
Explanation:
when length is 5, maximum average value is 10.8,
when length is 6, maximum average value is 9.16667.
Thus return 12.75.
Note:

1 <= k <= n <= 10,000.
Elements of the given array will be in range [-10,000, 10,000].
The answer with the calculation error less than 10-5 will be accepted.
题目大意：
给定包含n个整数的数组，寻找长度大于等于k的连续子数组的平均值的最大值。

解题思路：
二分枚举答案（Binary Search）

参考答案：https://leetcode.com/articles/maximum-average-subarray-ii/

初始令lo = min(nums)，hi = max(nums)

令mid = hi, lastMid = lo

循环直到mid - lastMid < 1e-5：

    令lastMid = mid，mid = (lo + hi) / 2

    若一趟遍历检查发现nums中存在长度不小于k的连续子数组，并且均值>= mid：令lo = mid

    否则：令hi = mid

返回mid
Java代码：
public class Solution {
    public double findMaxAverage(int[] nums, int k) {
        double lo = Double.POSITIVE_INFINITY;
        double hi = Double.NEGATIVE_INFINITY;
        for (int num : nums) {
            lo = Math.min(lo, num);
            hi = Math.max(hi, num);
        }
        double mid = hi, lastMid = lo;
        while (Math.abs(mid - lastMid) > 1e-5) {
            lastMid = mid;
            mid = (lo + hi) / 2.0;
            if (check(nums, k, mid)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        return mid;
    }
    
    public boolean check(int[] nums, int k, double mid) {
        double minVal = Double.POSITIVE_INFINITY;
        double sums = 0, sumt = 0;
        for (int i = 0; i < nums.length; i++) {
            sums += nums[i] - mid;
            if (i >= k - 1 && sums >= 0) {
                return true;
            }
            if (i >= k) {
                sumt += nums[i - k] - mid;
                minVal = Math.min(minVal, sumt);
                if (sums >= minVal) {
                    return true;
                }
            }
        }
        return false;
    }

}
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
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
另一种解法，参考论文：最大密度线段问题的优化算法（https://arxiv.org/pdf/cs/0311020.pdf）
 * @author tonghe
 *
 */
public class Leetcode644 {

}
