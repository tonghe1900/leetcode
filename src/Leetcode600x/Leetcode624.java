package Leetcode600x;
/**
 * 624. Maximum Distance in Array 
 
 
 
 
 
 
 
 
Average Rating: 5 (16 votes)

June 17, 2017  |  6.8K views
Given m arrays, and each array is sorted in ascending order. Now you can pick up two integers from two different arrays (each array picks one) and calculate the distance. We define the distance between two integers a and b to be their absolute difference |a-b|. Your task is to find the maximum distance.

Example 1:
Input: 
[[1,2,3],
 [4,5],
 [1,2,3]]
Output: 4
Explanation: 
One way to reach the maximum distance 4 is to pick 1 in the first or third array and pick 5 in the second array.
Note:
Each given array will have at least 1 number. There will be at least two non-empty arrays.
The total number of the integers in all the m arrays will be in the range of [2, 10000].
The integers in the m arrays will be in the range of [-10000, 10000].




 * @author tonghe
 *
 */
//https://leetcode.com/articles/maximum-distance-in-array/
public class Leetcode624 {
	public class Solution {
	    public int maxDistance(int[][] list) {
	        int res = 0;
	        for (int i = 0; i < list.length - 1; i++) {
	            for (int j = 0; j < list[i].length; j++) {
	                for (int k = i + 1; k < list.length; k++) {
	                    for (int l = 0; l < list[k].length; l++) {
	                        res = Math.max(res, Math.abs(list[i][j] - list[k][l]));
	                    }
	                }
	            }
	        }
	        return res;
	    }
	}

	
	
	public class Solution {
	    public int maxDistance(int[][] list) {
	        int res = 0;
	        for (int i = 0; i < list.length - 1; i++) {
	            for (int j = i + 1; j < list.length; j++) {
	                res = Math.max(res, Math.abs(list[i][0] - list[j][list[j].length - 1]));
	                res = Math.max(res, Math.abs(list[j][0] - list[i][list[i].length - 1]));
	            }
	        }
	        return res;
	    }
	}

	
	
	public class Solution {
	    public int maxDistance(int[][] list) {
	        int res = 0, min_val = list[0][0], max_val = list[0][list[0].length - 1];
	        for (int i = 1; i < list.length; i++) {
	            res = Math.max(res, Math.max(Math.abs(list[i][list[i].length - 1] - min_val), Math.abs(max_val - list[i][0])));
	            min_val = Math.min(min_val, list[i][0]);
	            max_val = Math.max(max_val, list[i][list[i].length - 1]);
	        }
	        return res;
	    }
	}
}
