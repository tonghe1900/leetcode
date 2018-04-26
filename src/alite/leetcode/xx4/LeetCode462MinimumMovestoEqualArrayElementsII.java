package alite.leetcode.xx4;

import java.util.Arrays;

/**
 * http://bookshadow.com/weblog/2016/11/20/leetcode-minimum-moves-to-equal-array-elements-ii/
Given a non-empty integer array, find the minimum number of 
moves required to make all array elements equal, where a move is 
incrementing a selected element by 1 or decrementing a selected element by 1.
You may assume the array's length is at most 10,000.
Example:
Input:
[1,2,3]

Output:
2

Explanation:
Only two moves are needed (remember each move increments or decrements one element):

[1,2,3]  =>  [2,2,3]  =>  [2,2,2]
https://discuss.leetcode.com/topic/68736/java-just-like-meeting-point-problem
https://discuss.leetcode.com/topic/68762/java-solution-with-thinking-process/5
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        int count = 0;
        while(i < j){
            count += nums[j]-nums[i];
            i++;
            j--;
        }
        return count;
    }
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int res = 0, mid = nums.length/2;
        for(int i = 0; i < nums.length; i++){
            res += i > mid ? nums[i] - nums[mid] : nums[mid] - nums[i];
        }
        return res;
    }

求数组各元素与中位数差的绝对值之和
    def minMoves2(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        nums.sort()
        median = nums[len(nums) / 2]
        return sum(abs(num - median) for num in nums)

参考《编程之美》 小飞的电梯调度算法 解析
    def minMoves2(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        cnt = collections.Counter(nums)
        last, size = min(nums), len(nums)
        ans = mov = sum(nums) - last * size
        lo, hi = 0, size
        for k in sorted(cnt):
            mov += (lo - hi) * (k - last)
            hi -= cnt[k]
            lo += cnt[k]
            ans = min(ans, mov)
            last = k
        return ans

https://discuss.leetcode.com/topic/68758/java-o-n-time-using-quickselect
public int minMoves2(int[] nums) {
    int sum = 0;
    int median = findMedian(nums);
    for (int i=0;i<nums.length;i++) {
        sum += Math.abs(nums[i] - median);
    }
    return sum;
}

public int findMedian(int[] nums) {
    return getKth(nums.length/2+1, nums, 0, nums.length - 1);
}

public int getKth(int k, int[] nums, int start, int end) {
    int pivot = nums[end];
    int left = start;
    int right = end;

    while (true) {
        while (nums[left] < pivot && left < right) left++;
        while (nums[right] >= pivot && right > left) right--;
        if (left == right) break;
        swap(nums, left, right);
    }

    swap(nums, left, end);
    if (k == left + 1)  return pivot;
    else if (k < left + 1) return getKth(k, nums, start, left - 1);
    else return getKth(k, nums, left + 1, end);
}

public void swap(int[] nums, int n1, int n2) {
 int tmp = nums[n1];
 nums[n1] = nums[n2];
 nums[n2] = tmp;
}
 * @author het
 *
 */
public class LeetCode462MinimumMovestoEqualArrayElementsII {
	
	public int minMoves2(int[] nums) {
	    int sum = 0;
	    int median = findMedian(nums);
	    for (int i=0;i<nums.length;i++) {
	        sum += Math.abs(nums[i] - median);
	    }
	    return sum;
	}

	public int findMedian(int[] nums) {
	    return getKth(nums.length/2+1, nums, 0, nums.length - 1);
	}

	public int getKth(int k, int[] nums, int start, int end) {
	    int pivot = nums[end];
	    int left = start;
	    int right = end;

	    while (true) {
	        while (nums[left] < pivot && left < right) left++;
	        while (nums[right] >= pivot && right > left) right--;
	        if (left == right) break;
	        swap(nums, left, right);
	    }

	    swap(nums, left, end);
	    if (k == left + 1)  return pivot;
	    else if (k < left + 1) return getKth(k, nums, start, left - 1);
	    else return getKth(k, nums, left + 1, end);
	}

	public void swap(int[] nums, int n1, int n2) {
	 int tmp = nums[n1];
	 nums[n1] = nums[n2];
	 nums[n2] = tmp;
	}
//	 public int minMoves2(int[] nums) {
//	        Arrays.sort(nums);
//	        int i = 0, j = nums.length-1;
//	        int count = 0;
//	        while(i < j){
//	            count += nums[j]-nums[i];
//	            i++;
//	            j--;
//	        }
//	        return count;
//	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
