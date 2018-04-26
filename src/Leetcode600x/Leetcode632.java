package Leetcode600x;
/**
 * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the k lists.

We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

Example 1:
Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
Output: [20,24]
Explanation: 
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].
Note:
The given list may contain duplicates, so ascending order means >= here.
1 <= k <= 3500
-105 <= value of elements <= 105.
For Java users, please note that the input type has been changed to List<List<Integer>>. And after you reset the code template, you'll see this point.

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode632 {
//https://leetcode.com/problems/smallest-range/solution/
	
	

public class Solution {
    public int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                for (int k = i; k < nums.length; k++) {
                    for (int l = (k == i ? j : 0); l < nums[k].length; l++) {
                        int min = Math.min(nums[i][j], nums[k][l]);
                        int max = Math.max(nums[i][j], nums[k][l]);
                        int n, m;
                        for (m = 0; m < nums.length; m++) {
                            for (n = 0; n < nums[m].length; n++) {
                                if (nums[m][n] >= min && nums[m][n] <= max)
                                    break;
                            }
                            if (n == nums[m].length)
                                break;
                        }
                        if (m == nums.length) {
                            if (miny - minx > max - min || (miny - minx == max - min && minx > min)) {
                                miny = max;
                                minx = min;
                            }
                        }
                    }
                }
            }
        }
        return new int[] {minx, miny};
    }
}





public class Solution {
    public int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                for (int k = i; k < nums.length; k++) {
                    for (int l = (k == i ? j : 0); l < nums[k].length; l++) {
                        int min = Math.min(nums[i][j], nums[k][l]);
                        int max = Math.max(nums[i][j], nums[k][l]);
                        int n, m;
                        for (m = 0; m < nums.length; m++) {
                            n = Arrays.binarySearch(nums[m], min);
                            if (n < 0)
                                n = -1 - n;
                            if (n == nums[m].length || nums[m][n] < min || nums[m][n] > max)
                                break;
                        }
                        if (m == nums.length) {
                            if (miny - minx > max - min || (miny - minx == max - min && minx > min)) {
                                miny = max;
                                minx = min;
                            }
                        }
                    }
                }
            }
        }
        return new int[] {minx, miny};
    }
}



public class Solution {
    public int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        for (int i = 0; i < nums.length && flag; i++) {
            for (int j = 0; j < nums[i].length && flag; j++) {
                int min_i = 0, max_i = 0;
                for (int k = 0; k < nums.length; k++) {
                    if (nums[min_i][next[min_i]] > nums[k][next[k]])
                        min_i = k;
                    if (nums[max_i][next[max_i]] < nums[k][next[k]])
                        max_i = k;
                }
                if (miny - minx > nums[max_i][next[max_i]] - nums[min_i][next[min_i]]) {
                    miny = nums[max_i][next[max_i]];
                    minx = nums[min_i][next[min_i]];
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length) {
                    flag = false;
                }
            }
        }
        return new int[] {minx, miny};
    }
}






public class Solution {
    public int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        PriorityQueue < Integer > min_queue = new PriorityQueue < Integer > ((i, j) -> nums[i][next[i]] - nums[j][next[j]]);
        for (int i = 0; i < nums.length; i++) {
            min_queue.offer(i);
            max = Math.max(max, nums[i][0]);
        }
        for (int i = 0; i < nums.length && flag; i++) {
            for (int j = 0; j < nums[i].length && flag; j++) {
                int min_i = min_queue.poll();
                if (miny - minx > max - nums[min_i][next[min_i]]) {
                    minx = nums[min_i][next[min_i]];
                    miny = max;
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length) {
                    flag = false;
                    break;
                }
                min_queue.offer(min_i);
                max = Math.max(max, nums[min_i][next[min_i]]);
            }
        }
        return new int[] { minx, miny};
    }
}








}
