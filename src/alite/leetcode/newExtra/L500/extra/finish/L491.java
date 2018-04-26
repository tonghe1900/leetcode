package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 491 - Increasing Subsequences

https://leetcode.com/problems/increasing-subsequences/
Given an integer array, your task is to find all the different possible increasing subsequences of the given array, and the length of an increasing subsequence should be at least 2 .
Example:
Input: [4, 6, 7, 7]
Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
Note:
The length of the given array will not exceed 15.
The range of integer in the given array is [-100,100].
The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.
https://discuss.leetcode.com/topic/76249/java-20-lines-backtracking-solution-using-set-beats-100
https://discuss.leetcode.com/topic/76211/clean-20ms-solution
http://blog.csdn.net/alwaystry/article/details/54692902
    public List<List<Integer>> findSubsequences(int[] nums) {  
        Set<List<Integer>> list = new HashSet<List<Integer>>();  
        backtrack(list,new ArrayList<>(),nums,0);  
        return new ArrayList(list);  
    }  
    private void backtrack(Set<List<Integer>> list, List<Integer> tempList, int[] nums,int start){  
        if (tempList.size()>1) list.add(new ArrayList<>(tempList));  
        for(int i=start;i<nums.length;i++){  
            if(tempList.size()==0||tempList.get(tempList.size()-1)<=nums[i]){  
               tempList.add(nums[i]);  
               backtrack(list,tempList,nums,i+1);  
               tempList.remove(tempList.size()-1);  
            }  
        }  
    }
 * @author het
 *
 */
public class L491 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
