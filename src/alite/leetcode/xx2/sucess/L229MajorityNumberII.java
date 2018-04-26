package alite.leetcode.xx2.sucess;

import java.util.ArrayList;

/**
 * Given an array of integers, the majority number is the number that occurs more than 1/3 of the size of the array. Find it. 

Note There is only one majority number in the array Example For [1, 2, 1, 2, 1, 3, 3] return 1
 * @author het
 *
 */
public class L229MajorityNumberII {
	 public int majorityNumber(ArrayList<Integer> nums) {
	        if (nums == null || nums.isEmpty()) return -1;
	        // pair
	        int key1 = -1, key2 = -1;
	        int count1 = 0, count2 = 0;
	        for (int num : nums) {
	            if (count1 == 0) {
	                key1 = num;
	                count1 = 1;
	                continue;
	                //(count2 == 0 && key1 != num)
	                //(count2 == 0 && key1 != num)
	                // (count2 == 0 && key1 != num)
	            } else if (count2 == 0 && key1 != num) {
	                key2 = num;
	                count2 = 1;
	                continue;
	            }
	            if (key1 == num) {
	                count1++;
	            } else if (key2 == num) {
	                count2++;
	            } else {
	                count1--;
	                count2--;
	            }
	        }

	        count1 = 0;
	        count2 = 0;
	        for (int num : nums) {
	            if (key1 == num) {
	                count1++;
	            } else if (key2 == num) {
	                count2++;
	            }
	        }
	        return count1 > count2 ? key1 : key2;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
