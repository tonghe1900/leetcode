package alite.leetcode.xx3;
/**
 * LeetCode 334 - Increasing Triplet Subsequence

[LeetCode]Increasing Triplet Subsequence | 书影博客
Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
Formally the function should:
Return true if there exists i, j, k 
such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
Your algorithm should run in O(n) time complexity and O(1) space complexity.
Examples:
Given [1, 2, 3, 4, 5],
return true.
Given [5, 4, 3, 2, 1],
return false.
题目大意：
给定一个无序数组，判断其中是否存在一个长度为3的递增序列。
形式上，函数应当：
如果存在这样的i, j, k（0 ≤ i < j < k ≤ n-1），使得arr[i] < arr[j] < arr[k]，返回true，否则返回false。
你的算法应当满足O(n)时间复杂度和O(1)空间复杂度。
    def increasingTriplet(self, nums):
        a = b = None
        for n in nums:
            if a is None or a >= n:
                a = n
            elif b is None or b >= n:
                b = n
            else:
                return True
        return False

https://leetcode.com/discuss/86891/concise-java-solution-with-comments
   public boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) { small = n; } // update small if n is smaller than both
            else if (n <= big) { big = n; } // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
        }
        return false;
    }
https://leetcode.com/discuss/86593/clean-and-short-with-comments-c
bool increasingTriplet(vector<int>& nums) {
    int c1 = INT_MAX, c2 = INT_MAX;
    for (int x : nums) {
        if (x <= c1) {
            c1 = x;           // c1 is min seen so far (it's a candidate for 1st element)
        } else if (x <= c2) { // here when x > c1, i.e. x might be either c2 or c3
            c2 = x;           // x is better than the current c2, store it
        } else {              // here when we have/had c1 < c2 already and x > c2
            return true;      // the increasing subsequence of 3 elements exists
        }
    }
    return false;
}
X.
https://leetcode.com/discuss/86954/just-a-simplified-version-of-patient-sort
https://leetcode.com/discuss/87204/accepted-java-solution-this-question-only-lines-clear-concise
The main idea is keep two values when check all elements in the array: the minimum value minuntil now and the second minimum value secondMin from the minimum value's position until now. Then if we can find the third one that larger than those two values at the same time, it must exists the triplet subsequence and return true.
What need to be careful is: we need to include the condition that some value has the same value with minimum number, otherwise this condition will cause the secondMin change its value.
    public boolean increasingTriplet(int[] nums) {
        int min = Integer.MAX_VALUE, secondMin = Integer.MAX_VALUE;
        for(int num : nums){
            if(num <= min) min = num;
            else if(num < secondMin) secondMin = num;
            else if(num > secondMin) return true;
        }
        return false;
    }
http://www.programcreek.com/2015/02/leetcode-increasing-triplet-subsequence-java/
This problem can be converted to be finding if there is a sequence such thatthe_smallest_so_far < the_second_smallest_so_far < current. We use x, y and z to denote the 3 number respectively.
public boolean increasingTriplet(int[] nums) {
 int x = Integer.MAX_VALUE;
 int y = Integer.MAX_VALUE;
 
 for (int i = 0; i < nums.length; i++) {
  int z = nums[i];
 
  if (x >= z) {
   x = z;// update x to be a smaller value
  } else if (y >= z) {
   y = z; // update y to be a smaller value
  } else {
   return true;
  }
 }
 
 return false;
}
http://leetcode0.blogspot.com/2016/04/334-increasing-triplet-subsequence.html
就是 逐个对比。 记录最小的两个。

    public boolean increasingTriplet(int[] nums) {

        if(nums==null || nums.length ==0)

            return false;

        int min = nums[0], secondMin = Integer.MAX_VALUE;

        for(int i =1 ;i < nums.length; i++){

            int val = nums[i];

            if(val > secondMin)

                return true;

            if(val == secondMin)

                continue;

            // now val < secondMin

            if(val<= min){

                min=val;

            }else{ //now  min < val < secondMin

                secondMin  = val;

            }

        }

        return false;

    }
http://www.1point3acres.com/bbs/thread-171521-1-1.html
不过，如果这道题改成在nums中找increasing k-subsequence，你打算怎么扩展呢？
http://yuancrackcode.com/2016/01/19/find-the-increasing-sequences-with-increasing-indices/

    public static int[] get(int[] nums) {

        if (nums == null || nums.length == 0) return new int[]{};

 

        int n = nums.length;

        int[] result = new int[3], min = new int[n], max = new int[n];

        for (int i = 0; i < n; i++) {

            if (i == 0) {

                min[i] = nums[i];

            } else {

                min[i] = Math.min(min[i - 1], nums[i - 1]);

            }

        }

        for (int i = n - 1; i >= 0; i--) {

            if (i == n - 1) {

                max[i] = nums[i];

            } else {

                max[i] = Math.max(max[i + 1], nums[i + 1]);

            }

        }//find the max number after i and min number before i

        for (int i = 0; i < n; i++) {

            if (nums[i] > min[i] && nums[i] < max[i]) {

                result[0] = min[i];

                result[1] = nums[i];

                result[2] = max[i];

                break;

            }

        }

        return result;

    }

X.
http://www.cnblogs.com/grandyang/p/5194599.html
用一个dp数组，dp[i]表示在i位置之前小于等于nums[i]的数字的个数(包括其本身)，我们初始化dp数组都为1，然后我们开始遍历原数组，对当前数字nums[i]，我们遍历其之前的所有数字，如果之前某个数字nums[j]小于nums[i]，那么我们更新dp[i] = max(dp[i], dp[j] + 1)，如果此时dp[i]到3了，则返回true，若遍历完成，则返回false
    bool increasingTriplet(vector<int>& nums) {
        vector<int> dp(nums.size(), 1);
        for (int i = 0; i < nums.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i]) {
                    dp[i] = max(dp[i], dp[j] + 1);
                    if (dp[i] >= 3) return true;
                }
            }
        }
        return false;
    }

Read full article from [LeetCode]Increasing Triplet Subsequence | 书影博客
 * @author het
 *
 */
public class LeetCode334 {
//	def increasingTriplet(self, nums):
//        a = b = None
//        for n in nums:
//            if a is None or a >= n:
//                a = n
//            elif b is None or b >= n:
//                b = n
//            else:
//                return True
//        return False

    public boolean increasingTriplet(int[] nums){
    	
    	  Integer a = null,b=null;
    	  for(int num: nums){
    		  if(a == null || a >= num){
    			  a=num;
    		  }else if(b == null || b >= num){
    			  b = num;
    		  }else{
    			  return true;
    		  }
    	  }
    	  return false;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
