package alite.leetcode.xx2;
/**
 * Minimum Size Subarray Sum

Given an array of n positive integers and a positive integer s, find the minimal length of a subarray of which 
the sum ≥ s
. If there isn't one, return 0 instead.

For example, given the array [2,3,1,2,4,3] and s = 7,
the subarray [4,3] has the minimal length under the problem constraint.

click to show more practice.

Credits:
Special thanks to @Freezen for adding this problem and creating all test cases.

 

典型的双指针滑动窗口，前指针扩展，后指针收缩。

class Solution {
public:
    int minSubArrayLen(int s, vector<int>& nums) {
        if(nums.empty())
            return 0;
        int ret = INT_MAX;
        int n = nums.size();
        int begin = 0;
        int end = 0;
        int cursum = nums[0];   // guaranteed to exist
        while(end < n)
        {
            // expand
            while(end < n && cursum < s)
            {
                end ++;
                cursum += nums[end];
            }
            if(end == n)    // cursum of [begin, n) < s
                break;
            // shrink (cursum >= s)
            while(begin <= end && cursum >= s)
            {
                ret = min(ret, end-begin+1);
                cursum -= nums[begin];
                begin ++;
            }
            end ++;
            cursum += nums[end];
        }
        if(ret == INT_MAX)
            return 0;
        else
            return ret;
    }
};

记录下每一次sum of [begin, end] ≥ s时候的窗口大小即可。
 * @author het
 *
 */
public class L209MinimumSizeSubarraySum {
     
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
