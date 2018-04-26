package alite.leetcode.xx2.sucess.extra;

import java.util.Arrays;

/**
 * http://www.elvisyu.com/3sum-smaller/
Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n 
that satisfy the condition nums[i] + nums[j] + nums[k] < target.
For example, given nums = [-2, 0, 1, 3], and target = 2.
Return 2. Because there are two triplets which sums are less than 2:

[-2, 0, 1]

[-2, 0, 3]
Could you solve it in O(n^2) runtime?
和2Sum，3Sum，3Sum close很如出一辙的题目。考的基本还是双指针对排序数组的.
基于已经完成排序的数组，关键点是一旦找到 i, j 满足nums[i] + nums[j] + nums[k] < target, 因为数组已排序，则（i, j-1, k),(i, j – 2, k)…(i, i+1, k)均是满足条件的解，这些解总共是 j – i个，而无需一个一个check它们。

public Integer threeSumSmaller(int[] nums, int target){

        int n = nums.length;

        int result=0;

        Arrays.sort(nums);

        for(int i=0; i<n; i++){

            int j=i+1, k=n-1;

            while(j<k){

                if(nums[i] + nums[j] + nums[k] >= target)

                    k--;

                else{

                    result = result + (k-j);

                    j++;

                }

            }

        }

        return result;

    }
https://discuss.leetcode.com/topic/26642/accepted-and-simple-java-o-n-2-solution-with-detailed-explanation
We sort the array first. Then, for each element, we use the two pointer approach to find the number of triplets that meet the requirements.
Let me illustrate how the two pointer technique works with an example:
target = 2

  i  lo    hi
[-2, 0, 1, 3]
We use a for loop (index i) to iterate through each element of the array. For each i, we create two pointers, lo and hi, where lo is initialized as the next element of i, and hi is initialized at the end of the array. If we know that nums[i] + nums[lo] + nums[hi] < target, then we know that since the array is sorted, we can replace hi with any element from lo+1 to nums.length-1, and the requirements will still be met. Just like in the example above, we know that since -2 + 0 + 3 < 2, we can replace hi (3) with 1, and it would still work. Therefore, we can just add hi - lo to the triplet count.

https://discuss.leetcode.com/topic/23421/simple-and-easy-understanding-o-n-2-java-solution
    public int threeSumSmaller(int[] nums, int target) {
        int count = 0;
        Arrays.sort(nums);
        int len = nums.length;
    
        for(int i=0; i<len-2; i++) {
            int left = i+1, right = len-1;
            while(left < right) {
                if(nums[i] + nums[left] + nums[right] < target) {
                    count += right-left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return count;
    }

LIKE CODING: LeetCode [259] 3Sum Smaller

    int threeSumSmaller(vector<int>& nums, int target) {

        sort(nums.begin(), nums.end());

        int sz = nums.size(), ret = 0;

        for(int i=0; i<=sz-3; ++i){

                int l = i+1, r = sz-1;

                while(l<r){

                    int sum = nums[i]+nums[l]+nums[r];

                    if(sum>=target){

                        r--;

                    }else{

                        ret += (r-l);

                        l++;

                    }

                }

        }

        return ret;

    }
https://leetcode.com/discuss/52467/python-solution-with-comments
# O(n*n) time
def threeSumSmaller(self, nums, target):
    count = 0
    nums.sort()
    for i in xrange(len(nums)):
        j, k = i+1, len(nums)-1
        while j < k:
            s = nums[i] + nums[j] + nums[k]
            if s < target:
                # if (i,j,k) works, then (i,j,k), (i,j,k-1),..., 
                # (i,j,j+1) all work, totally (k-j) triplets
                count += k-j
                j += 1
            else:
                k -= 1
    return count 
https://leetcode.com/discuss/52424/my-solutions-in-java-and-python
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += this.twoSumSmaller(nums, i + 1, nums.length - 1, target - nums[i]);
        }
        return total;
    }

    private int twoSumSmaller(int[] nums, int start, int end, int target) {
        int total = 0;
        while (start < end) {
            if (nums[start] + nums[end] >= target) {
                end--;
            } else {
                total += (end - start++);
            }
        }
        return total;     }
Read full article from LIKE CODING: LeetCode [259] 3Sum Smaller
 * @author het
 *
 */
public class LeetCode259_3SumSmaller {
	
	  public int threeSumSmaller(int[] nums, int target) {
	        int count = 0;
	        Arrays.sort(nums);
	        int len = nums.length;
	    
	        for(int i=0; i<len-2; i++) {
	            int left = i+1, right = len-1;
	            while(left < right) {
	                if(nums[i] + nums[left] + nums[right] < target) {
	                    count += right-left;// count+= right -left;
	                    left++;
	                } else {
	                    right--;
	                }
	            }
	        }
	        
	        return count;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
