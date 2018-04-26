package alite.leetcode.xx3;
/**
 * Leetcode 330 - Patching Array

https://leetcode.com/problems/patching-array/
Given a sorted positive integer array nums and an integer n, add/patch elements to the array such
 that any number in range [1, n] inclusive can be formed by the sum of some elements in the array.
  Return the minimum number of patches required.
Example 1:
nums = [1, 3], n = 6
Return 1.
Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
So we only need 1 patch.
Example 2:
nums = [1, 5, 10], n = 20
Return 2.
The two patches can be [2, 4].
Example 3:
nums = [1, 2, 2], n = 5
Return 0.
https://leetcode.com/discuss/82822/solution-explanation
think about nums = [1,1,1,1,...]. Then you'll go through all those before you ever get to doubling. It's O(k+log(n)), where k is the size of the array.
int minPatches(vector<int>& nums, int n) {
    long miss = 1, added = 0, i = 0;
    while (miss <= n) {
        if (i < nums.size() && nums[i] <= miss) {
            miss += nums[i++];
        } else {
            miss += miss;
            added++;
        }
    }
    return added;
}
Let miss be the smallest sum in [1,n] that we might be missing. Meaning we already know we can build all sums in [1,miss). Then if we have a number num <= miss in the given array, we can add it to those smaller sums to build all sums in [1,miss+num). If we don't, then we must add such a number to the array, and it's best to add miss itself, to maximize the reach.
Another implementation, though I prefer the above one.
int minPatches(vector<int>& nums, int n) {
    int count = 0, i = 0;
    for (long miss=1; miss <= n; count++)
        miss += (i < nums.size() && nums[i] <= miss) ? nums[i++] : miss;
    return count - i;
}
https://leetcode.com/discuss/82827/o-log-n-time-o-1-space-java-with-explanation-trying-my-best
https://leetcode.com/discuss/82832/java-here-is-my-greedy-version-with-brief-explanations-1ms
Greedy idea: add the maximum possible element whenever there is a gap


Iterating the nums[], and keeps adding them up, and we are getting a running sum. At any position, if nums[i] > sum+1, them we are sure we have to patch a sum+1 because all nums before index i can't make sum+1 even add all of them up, and all nums after index i are all simply too large.
public class Solution {
    public int minPatches(int[] nums, int n) {
        long sum = 0;
        int count = 0;
        for (int x : nums) {
            if (sum >= n) break;
            while (sum+1 < x && sum < n) { 
                ++count;
                sum += sum+1;
            }
            sum += x;
        }
        while (sum < n) {
            sum += sum+1;
            ++count;
        }
        return count;
    }
}
http://bookshadow.com/weblog/2016/01/27/leetcode-patching-array/
假设数组nums的“部分元素和”可以表示范围[1, total)内的所有数字，
那么向nums中添加元素add可以将表示范围扩充至[1, total + add)，其中add ≤ total，当且仅当add = total时取到范围上界[1, 2 * total)
若nums数组为空，则构造[1, n]的nums为[1, 2, 4, 8, ..., k]，k为小于等于n的2的幂的最大值。
若nums数组非空，则扩展时利用nums中的已有元素，详见代码。
    def minPatches(self, nums, n):
        """
        :type nums: List[int]
        :type n: int
        :rtype: int
        """
        idx, total, ans = 0, 1, 0
        size = len(nums)
        while total <= n:
            if idx < size and nums[idx] <= total:
                total += nums[idx]
                idx += 1
            else:
                total <<= 1
                ans += 1
        return ans




https://www.hrwhisper.me/leetcode-patching-array/

    public int minPatches(int[] nums, int n) {

        int cnt = 0,i=0;

        for(long known_sum = 1;known_sum <= n;){

            if(i < nums.length && known_sum >= nums[i]){

                known_sum += nums[i++];

            }else{

                known_sum <<= 1;

                cnt++;

            }

        }

        return cnt;

    }
http://www.zrzahid.com/patching-array/
Basically, as long as cumulative sum so far is greater than the number we need to create , 
we are good. But id cumsum falls below the current number (between 1 to n) we need to create , 
then we have to add the current cumsum to itself (why?). This is because we assumed at any particular
 time cumsum is equal or greater than the current element we need to create. That is we doubling the cumsum when we add a missing element.
public static int minPatches(int[] nums, int n) {
 int sum = 1;
 int i = 0;
 int count = 0;
 
 while(sum <= n){
  if(i < nums.length && nums[i] <= sum){
   sum+=nums[i++];
  }
  else{
   sum<<=1;
   count++;
  }
 }
 
 return count;
}
 * @author het
 *
 */
public class LeetCode330frustrated {
	//Iterating the nums[], and keeps adding them up, and we are getting a running sum. At any position,
	//if nums[i] > sum+1, them we are sure we have to patch a sum+1 because all nums before index i can't
	//make sum+1 even add all of them up, and all nums after index i are all simply too large.
	
	
	public static int minPatches(int[] nums, int n) {
		 int sum = 1;
		 int i = 0;
		 int count = 0;
		 
		 while(sum <= n){
		  if(i < nums.length && nums[i] <= sum){
		   sum+=nums[i++];
		  }
		  else{
		   sum<<=1;
		   count++;
		  }
		 }
		 
		 return count;
		}
//	    public static int minPatches(int[] nums, int n) {
//	        long sum = 0;
//	        int count = 0;
//	        for (int x : nums) {
//	            if (sum >= n) break;
//	            while (sum+1 < x && sum < n) { 
//	                ++count;
//	                sum += sum+1;
//	            }
//	            sum += x;
//	        }
//	        while (sum < n) {
//	            sum += sum+1;
//	            ++count;
//	        }
//	        return count;
//	    }
//	    nums = [1, 3], n = 6
//	    		Return 1.
//	    		Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
//	    		Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
//	    		Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
//	    		So we only need 1 patch.
//	    		Example 2:
//	    		nums = [1, 5, 10], n = 20
//	    		Return 2.
//	    		The two patches can be [2, 4].
//	    		Example 3:
//	    		nums = [1, 2, 2], n = 5
//	    		Return 0.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(minPatches(new int[]{2, 10, 14},20 ));
		
		// 1   4  8   2   10   14 

	}

}
