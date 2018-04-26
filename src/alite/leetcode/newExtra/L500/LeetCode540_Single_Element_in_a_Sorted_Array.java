package alite.leetcode.newExtra.L500;
/**
 * LeetCode 540 - Single Element in a Sorted Array

https://leetcode.com/problems/single-element-in-a-sorted-array/#/description
Given a sorted array consisting of only integers where every element appears twice except for one element which appears once. Find this single element that appears only once.
Example 1:
Input: [1,1,2,3,3,4,4,8,8]
Output: 2
Example 2:
Input: [3,3,7,7,10,11,11]
Output: 10
Note: Your solution should run in O(log n) time and O(1) space.

从数组递增有序和O(log n)时间复杂度推断，题目可以采用二分查找求解。
初始令左、右指针lo, hi分别指向0, len(nums) - 1

当lo < hi时执行循环：

令mi = (lo + hi) / 2

若nums[mi] == nums[mi - 1]：

数组可以分为[lo, mi - 2], [mi + 1, hi]两部分，目标元素位于长度为奇数的子数组中。

同理，若nums[mi] == nums[mi + 1]：

数组可以分为[lo, mi - 1], [mi + 2, hi]两部分，目标元素位于长度为奇数的子数组中。

若nums[mi]与nums[mi - 1], nums[mi + 1]均不相等，则返回nums[mi]
    def singleNonDuplicate(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        lo, hi = 0, len(nums) - 1
        while lo < hi:
            mi = (lo + hi) >> 1
            if nums[mi] == nums[mi - 1]:
                if (mi - 1) & 1:
                    hi = mi - 2
                else:
                    lo = mi + 1
            elif nums[mi] == nums[mi + 1]:
                if (mi + 1) & 1:
                    lo = mi + 2
                else:
                    hi = mi - 1
            else:
                return nums[mi]
        return nums[lo]
https://discuss.leetcode.com/topic/82332/java-binary-search-o-log-n-shorter-than-others
My solution using binary search. lo and hi are not regular index, but the pair index here.
    public int singleNonDuplicate(int[] nums) {
        // binary search
        int n=nums.length, lo=0, hi=n/2;
        while (lo < hi) {
            int m = (lo + hi) / 2;
            if (nums[2*m]!=nums[2*m+1]) hi = m;
            else lo = m+1;
        }
        return nums[2*lo];
    }
https://discuss.leetcode.com/topic/83308/using-collections-binarysearch-for-fun
Longer than writing my own binary search, but I wanted to see how this would look. I let Collections.binarySearch search in a special List object which returns -1 for indexes before the single element, 0 for the index of the single element, and 1 for indexes after the single element. So I run binary search for 0. Since my special List object computes its entries only on the fly when requested, this solution takes O(log n) time and O(1) space.
    public int singleNonDuplicate(int[] nums) {
        List list = new AbstractList<Integer>() {
            public int size() {
                return nums.length;
            }
            public Integer get(int index) {
                if ((index ^ 1) < size() && nums[index] == nums[index ^ 1])
                    return -1;
                if (index == 0 || index % 2 == 0 && nums[index - 1] != nums[index])
                    return 0;
                return 1;
            }
        };
        return nums[Collections.binarySearch(list, 0)];
    }
A variation, the helper isOff tells whether the index is in the part that's "off" (the single element and everything after it):
    public int singleNonDuplicate(int[] nums) {
        List list = new AbstractList<Integer>() {
            public int size() {
                return nums.length;
            }
            public Integer get(int index) {
                return isOff(index) + isOff(index - 1);
            }
            int isOff(int i) {
                return i == size() - 1 || i >= 0 && nums[i] != nums[i ^ 1] ? 1 : 0;
            }
        };
        return nums[Collections.binarySearch(list, 1)];
    }
 * @author het
 *
 */

//从数组递增有序和O(log n)时间复杂度推断，题目可以采用二分查找求解。
//初始令左、右指针lo, hi分别指向0, len(nums) - 1
//
//当lo < hi时执行循环：
//
//令mi = (lo + hi) / 2
//
//若nums[mi] == nums[mi - 1]：
//
//数组可以分为[lo, mi - 2], [mi + 1, hi]两部分，目标元素位于长度为奇数的子数组中。
//
//同理，若nums[mi] == nums[mi + 1]：
//
//数组可以分为[lo, mi - 1], [mi + 2, hi]两部分，目标元素位于长度为奇数的子数组中。
//
//若nums[mi]与nums[mi - 1], nums[mi + 1]均不相等，则返回nums[mi]
public class LeetCode540_Single_Element_in_a_Sorted_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
