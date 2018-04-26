package Leetcode600x;
/**
 * 611. Valid Triangle Number
DescriptionHintsSubmissionsDiscussSolution
Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
Example 1:
Input: [2,2,3,4]
Output: 3
Explanation:
Valid combinations are: 
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Note:
The length of the given array won't exceed 1000.
The integers in the given array are in the range of [0, 1000].
Seen this question in a real interview before?  


https://leetcode.com/problems/valid-triangle-number/solution/



 * @author tonghe
 *
 */
public class Leetcode611 {
	public class Solution {
	    public int triangleNumber(int[] nums) {
	        int count = 0;
	        for (int i = 0; i < nums.length - 2; i++) {
	            for (int j = i + 1; j < nums.length - 1; j++) {
	                for (int k = j + 1; k < nums.length; k++) {
	                    if (nums[i] + nums[j] > nums[k] && nums[i] + nums[k] > nums[j] && nums[j] + nums[k] > nums[i])
	                        count++;
	                }
	            }
	        } I
	        return count;
	    }
	}

	
	public class Solution {
	    int binarySearch(int nums[], int l, int r, int x) {
	        while (r >= l && r < nums.length) {
	            int mid = (l + r) / 2;
	            if (nums[mid] >= x)
	                r = mid - 1;
	            else
	                l = mid + 1;
	        }
	        return l;
	    }
	    public int triangleNumber(int[] nums) {
	        int count = 0;
	        Arrays.sort(nums);
	        for (int i = 0; i < nums.length - 2; i++) {
	            int k = i + 2;
	            for (int j = i + 1; j < nums.length - 1 && nums[i] != 0; j++) {
	                k = binarySearch(nums, k, nums.length - 1, nums[i] + nums[j]);
	                count += k - j - 1;
	            }
	        }
	        return count;
	    }
	}
	
	
	
	public class Solution {
	    public int triangleNumber(int[] nums) {
	        int count = 0;
	        Arrays.sort(nums);
	        for (int i = 0; i < nums.length - 2; i++) {
	            int k = i + 2;
	            for (int j = i + 1; j < nums.length - 1 && nums[i] != 0; j++) {
	                while (k < nums.length && nums[i] + nums[j] > nums[k])
	                    k++;
	                count += k - j - 1;
	            }
	        }
	        return count;
	    }
	}
}
