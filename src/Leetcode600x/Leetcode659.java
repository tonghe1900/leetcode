package Leetcode600x;
/**
 * You are given an integer array sorted in ascending order (may contain duplicates), you need to split them into several subsequences, where each subsequences consist of at least 3 consecutive integers. Return whether you can make such a split.

Example 1:
Input: [1,2,3,3,4,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3
3, 4, 5
Example 2:
Input: [1,2,3,3,4,4,5,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3, 4, 5
3, 4, 5
Example 3:
Input: [1,2,3,4,4,5]
Output: False
Note:
The length of the input is in range of [1, 10000]
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode659 {
//https://leetcode.com/problems/split-array-into-consecutive-subsequences/solution/
	
	class Solution {
	    public boolean isPossible(int[] nums) {
	        Integer prev = null;
	        int prevCount = 0;
	        Queue<Integer> starts = new LinkedList();
	        int anchor = 0;
	        for (int i = 0; i < nums.length; ++i) {
	            int t = nums[i];
	            if (i == nums.length - 1 || nums[i+1] != t) {
	                int count = i - anchor + 1;
	                if (prev != null && t - prev != 1) {
	                    while (prevCount-- > 0)
	                        if (prev < starts.poll() + 2) return false;
	                    prev = null;
	                }

	                if (prev == null || t - prev == 1) {
	                    while (prevCount > count) {
	                        prevCount--;
	                        if (t-1 < starts.poll() + 2)
	                            return false;
	                    }
	                    while (prevCount++ < count)
	                        starts.add(t);
	                }
	                prev = t;
	                prevCount = count;
	                anchor = i+1;
	            }
	        }

	        while (prevCount-- > 0)
	            if (nums[nums.length - 1] < starts.poll() + 2)
	                return false;
	        return true;
	    }
	}
	
	
	
	
	
	class Solution {
	    public boolean isPossible(int[] nums) {
	        Counter count = new Counter();
	        Counter tails = new Counter();
	        for (int x: nums) count.add(x, 1);

	        for (int x: nums) {
	            if (count.get(x) == 0) {
	                continue;
	            } else if (tails.get(x) > 0) {
	                tails.add(x, -1);
	                tails.add(x+1, 1);
	            } else if (count.get(x+1) > 0 && count.get(x+2) > 0) {
	                count.add(x+1, -1);
	                count.add(x+2, -1);
	                tails.add(x+3, 1);
	            } else {
	                return false;
	            }
	            count.add(x, -1);
	        }
	        return true;
	    }
	}

	class Counter extends HashMap<Integer, Integer> {
	    public int get(int k) {
	        return containsKey(k) ? super.get(k) : 0;
	    }

	    public void add(int k, int v) {
	        put(k, get(k) + v);
	    }
	}
	
	
}
