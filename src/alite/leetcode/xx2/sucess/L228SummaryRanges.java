package alite.leetcode.xx2.sucess;

import java.util.ArrayList;
import java.util.List;

public class L228SummaryRanges {
	 public List<String> summaryRanges(int[] nums) {

	        List<String> res = new ArrayList<String>();

	        if (nums.length == 0) {

	            return res;

	        }

	        int start = 0;

	        for (int i = 1; i < nums.length; i++) {

	            if (nums[i] == nums[i-1] + 1) {

	                continue;

	            }

	            if (i - 1 == start) {

	                res.add(String.valueOf(nums[start]));

	            } else {

	                res.add(nums[start] + "->" + nums[i-1]);

	            }

	            start = i;

	        }

	        if (nums.length - 1 == start) {

	            res.add(String.valueOf(nums[start]));

	        } else {

	            res.add(nums[start] + "->" + nums[nums.length - 1]);

	        }

	        return res;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
