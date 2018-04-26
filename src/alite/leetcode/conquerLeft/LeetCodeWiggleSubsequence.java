package alite.leetcode.conquerLeft;
/**
 * LeetCode – Wiggle Subsequence (Java)
 
Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. 
A subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence, 
leaving the remaining elements in their original order.

Java Solution

The problem is converted to finding the turning point. When nums[i] < nums[i+1], we want to keep going to the right until finding
 the largest one, which is the turning point. Similarly, when nums[i] > nums[i+1], we want to keep going to the right until 
 finding the smallest one, which is the turning point. We always want to use the largest or smallest as the turning point 
 because that guarantees the optimal solution.

public int wiggleMaxLength(int[] nums) {
    if(nums == null || nums.length==0)
        return 0;
    if(nums.length<2){
        return nums.length;
    }    
 
    int count=1;
 
 
    for(int i=1, j=0; i<nums.length; j=i, i++){
        if(nums[j]<nums[i]){
            count++;
            while(i<nums.length-1 && nums[i]<=nums[i+1]){
                i++;
            }
        }else if(nums[j]>nums[i]){
            count++;
            while(i<nums.length-1 && nums[i]>=nums[i+1]){
                i++;
            }
        }
    }
 
    return count;
}
 * @author het
 *
 */
public class LeetCodeWiggleSubsequence {
	public int wiggleMaxLength(int[] nums) {
	    if(nums == null || nums.length==0)
	        return 0;
	    if(nums.length<2){
	        return nums.length;
	    }    
	 
	    int count=1;
	 
	 
	    for(int i=1, j=0; i<nums.length; j=i, i++){
	        if(nums[j]<nums[i]){
	            count++;
	            while(i<nums.length-1 && nums[i]<=nums[i+1]){
	                i++;
	            }
	        }else if(nums[j]>nums[i]){
	            count++;
	            while(i<nums.length-1 && nums[i]>=nums[i+1]){
	                i++;
	            }
	        }
	    }
	 
	    return count;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
