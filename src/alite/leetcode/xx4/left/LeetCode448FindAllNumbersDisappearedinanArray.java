package alite.leetcode.xx4.left;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
Find all the elements of [1, n] inclusive that do not appear in this array.
Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]
https://discuss.leetcode.com/topic/65738/java-accepted-simple-solution
The basic idea is that we iterate through the input array and mark elements as negative using nums[nums[i] -1] = -nums[nums[i]-1]. In this way all the numbers that we have seen will be marked as negative. In the second iteration, if a value is not marked as negative, it implies we have never seen that index before, so just add it to the return list.
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();
        
        for(int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]) - 1;
            if(nums[val] > 0) {
                nums[val] = -nums[val];
            }
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                ret.add(i+1);
            }
        }
        return ret;
    }
http://bookshadow.com/weblog/2016/11/01/leetcode-find-all-numbers-disappeared-in-an-array/
正负号标记法
遍历数组nums，记当前元素为n，令nums[abs(n) - 1] = -abs(nums[abs(n) - 1])
再次遍历nums，将正数对应的下标+1返回即为答案，因为正数对应的元素没有被上一步骤标记过。
    def findDisappearedNumbers(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        for n in nums:
            nums[abs(n) - 1] = -abs(nums[abs(n) - 1])
        return [i + 1 for i, n in enumerate(nums) if n > 0]

https://discuss.leetcode.com/topic/66581/simple-java-in-place-sort-solution
The idea is simple, if nums[i] != i + 1 and nums[i] != nums[nums[i] - 1], then we swap nums[i] with nums[nums[i] - 1], for example, nums[0] = 4 and nums[3] = 7, then we swap nums[0] with nums[3]. So In the end the array will be sorted and if nums[i] != i + 1, then i + 1 is missing.
The example run as follows
[4,3,2,7,8,2,3,1]
[7,3,2,4,8,2,3,1]
[3,3,2,4,8,2,7,1]
[2,3,3,4,8,2,7,1]
[3,2,3,4,8,2,7,1]
[3,2,3,4,1,2,7,8]
[1,2,3,4,3,2,7,8]
Since every swap we put at least one number to its correct position, the time is O(n)
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                int tmp = nums[i];
                nums[i] = nums[tmp - 1];
                nums[tmp - 1] = tmp;
            }
        }
        List<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                res.add(i + 1);
            }
        }
        return res;
    }
RELATED:
http://www.cnblogs.com/grandyang/p/4756677.html
Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
For example,
Given nums = [0, 1, 3] return 2.
这道题给我们n个数字，是0到n之间的数但是有一个数字去掉了，让我们寻找这个数字，要求线性的时间复杂度和常数级的空间复杂度。那么最直观的一个方法是用等差数列的求和公式求出0到n之间所有的数字之和，然后再遍历数组算出给定数字的累积和，然后做减法，差值就是丢失的那个数字

    int missingNumber(vector<int>& nums) {
        int sum = 0, n = nums.size();
        for (auto &a : nums) {
            sum += a; // USE LONG FOR SUM
        }
        return 0.5 * n * (n + 1) - sum;
    }
    int missingNumber(vector<int>& nums) {
        int res = 0;
        for (int i = 0; i < nums.size(); ++i) {
            res ^= (i + 1) ^ nums[i];
        }
        return res;
    }

这道题还可以用二分查找法来做，我们首先要对数组排序，然后我们用二分查找法算出中间元素的下标，然后用元素值和下标值之间做对比，如果元素值大于下标值，则说明缺失的数字在左边，此时将right赋为mid，反之则将left赋为mid+1。那么看到这里，作为读者的你可能会提出，排序的时间复杂度都不止O(n)，何必要多此一举用二分查找，还不如用上面两种方法呢。对，你说的没错，但是在面试的时候，有可能能人家给你的数组就是排好序的，那么此时用二分查找法肯定要优于上面两种方法，所以这种方法最好也要掌握以下~
    int missingNumber(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        int left = 0, right = nums.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > mid) right = mid;
            else left = mid + 1;
        }
        return right;
    }
 * @author het
 *
 */
public class LeetCode448FindAllNumbersDisappearedinanArray {
	//[4,3,2,7,8,2,3,1]
	 public static List<Integer> findDisappearedNumbers(int[] nums) {
	        List<Integer> ret = new ArrayList<Integer>();
	        
	        for(int i = 0; i < nums.length; i++) {
	            int val = Math.abs(nums[i]) - 1;
	            if(nums[val] > 0) {
	                nums[val] = -nums[val];
	            }
	        }
	        
	        for(int i = 0; i < nums.length; i++) {
	            if(nums[i] > 0) {
	                ret.add(i+1);
	            }
	        }
	        return ret;
	    }
	public static void main(String[] args) {
		System.out.println(findDisappearedNumbers(new int[]{4,3,2,7,8,2,3,1}));

	}

}
