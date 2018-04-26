package Leetcode600x;
/**
 * Given two integers n and k, you need to construct a list which contains n different positive integers ranging from 1 to n and obeys the following requirement: 
Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|] has exactly k distinct integers.

If there are multiple answers, print any of them.

Example 1:
Input: n = 3, k = 1
Output: [1, 2, 3]
Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3, and the [1, 1] has exactly 1 distinct integer: 1.
Example 2:
Input: n = 3, k = 2
Output: [1, 3, 2]
Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3, and the [2, 1] has exactly 2 distinct integers: 1 and 2.
Note:
The n and k are in the range 1 <= k < n <= 104.
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode667 {
//https://leetcode.com/problems/beautiful-arrangement-ii/solution/
	
	
	
	
	class Solution {
	    private ArrayList<ArrayList<Integer>> permutations(int[] nums) {
	        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
	        permute(ans, nums, 0);
	        return ans;
	    }

	    private void permute(ArrayList<ArrayList<Integer>> ans, int[] nums, int start) {
	        if (start >= nums.length) {
	            ArrayList<Integer> cur = new ArrayList<Integer>();
	            for (int x : nums) cur.add(x);
	            ans.add(cur);
	        } else {
	            for (int i = start; i < nums.length; i++) {
	                swap(nums, start, i);
	                permute(ans, nums, start+1);
	                swap(nums, start, i);
	            }
	        }
	    }

	    private void swap(int[] nums, int i, int j) {
	        int temp = nums[i];
	        nums[i] = nums[j];
	        nums[j] = temp;
	    }

	    private int numUniqueDiffs(ArrayList<Integer> arr) {
	        boolean[] seen = new boolean[arr.size()];
	        int ans = 0;

	        for (int i = 0; i < arr.size() - 1; i++) {
	            int delta = Math.abs(arr.get(i) - arr.get(i+1));
	            if (!seen[delta]) {
	                ans++;
	                seen[delta] = true;
	            }
	        }
	        return ans;
	    }

	    public int[] constructArray(int n, int k) {
	        int[] nums = new int[n];
	        for (int i = 0; i < n; i++) {
	            nums[i] = i+1;
	        }
	        for (ArrayList<Integer> cand : permutations(nums)) {
	            if (numUniqueDiffs(cand) == k) {
	                int[] ans = new int[n];
	                int i = 0;
	                for (int x : cand) ans[i++] = x;
	                return ans;
	            }
	        }
	        return null;
	    }
	}
	
	
	
	
	
	class Solution {
	    public int[] constructArray(int n, int k) {
	        int[] ans = new int[n];
	        int c = 0;
	        for (int v = 1; v < n-k; v++) {
	            ans[c++] = v;
	        }
	        for (int i = 0; i <= k; i++) {
	            ans[c++] = (i%2 == 0) ? (n-k + i/2) : (n - i/2);
	        }
	        return ans;
	    }
	}
}
