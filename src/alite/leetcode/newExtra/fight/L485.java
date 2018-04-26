package alite.leetcode.newExtra.fight;
/**
 * LeetCode 485 - Max Consecutive Ones

https://leetcode.com/problems/max-consecutive-ones/
Given a binary array, find the maximum number of consecutive 1s in this array.
Example 1:
Input: [1,1,0,1,1,1]
Output: 3
Explanation: The first two digits or the last three digits are consecutive 1s.
    The maximum number of consecutive 1s is 3.
Note:
The input array will only contain 0 and 1.
The length of input array is a positive integer and will not exceed 10,000
http://blog.csdn.net/mebiuw/article/details/54573429

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int now = 0;
        for(int num:nums){
            if (num==1) now++;
            else now = 0;
            max= Math.max(now,max);
        }
        return max;
    }
https://discuss.leetcode.com/topic/75437/java-4-lines-concise-solution-with-explanation
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxHere = 0, max = 0;
        for (int n : nums)
            max = Math.max(max, maxHere = n == 0 ? 0 : maxHere + 1);
        return max; 
    } 
 * @author het
 *
 */
public class L485 {
	public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int now = 0;
        for(int num:nums){
            if (num==1) now++;
            else now = 0;
            max= Math.max(now,max);
        }
        return max;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
