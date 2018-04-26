package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LeetCode 300 -Longest Increasing Subsequence

[LeetCode]Longest Increasing Subsequence | 书影博客
Given an unsorted array of integers, find the length of longest increasing subsequence.
For example,
Given [10, 9, 2, 5, 3, 7, 101, 18],
The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. 
Note that there may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n^2) complexity.
Follow up: Could you improve it to O(n log n) time complexity?

X. DP: O(N^2)
dp[x] = max(dp[x], dp[y] + 1) 其中 y < x
    def lengthOfLIS(self, nums):
        size = len(nums)
        dp = [1] * size
        for x in range(size):
            for y in range(x):
                if nums[x] > nums[y]:
                    dp[x] = max(dp[x], dp[y] + 1)
        return max(dp) if dp else 0
https://discuss.leetcode.com/topic/30721/my-easy-to-understand-o-n-2-solution-using-dp-with-video-explanation/6
public static int lengthOfLIS2(int[] nums) {
  if(nums.length == 0 || nums == null){
            return 0;
        }
        int []dp = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = dp[i] > dp[j] + 1 ? dp[i] : dp[j] + 1;
                }
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }
http://www.jiuzhang.com/solutions/longest-increasing-subsequence/
http://www.cnblogs.com/lishiblog/p/4190936.html
 6     public int longestIncreasingSubsequence(int[] nums) {
 7         if (nums.length==0) return 0;
 8         int len = nums.length;
 9         int[] lisLen = new int[len];
10         lisLen[0] = 1;
11         int maxLen = lisLen[0];
12         for (int i=1;i<len;i++){
13             lisLen[i]=1; //\\
14             for (int j=i-1;j>=0;j--)
15                 if (nums[i]>=nums[j] && lisLen[i]<lisLen[j]+1)
16                     lisLen[i] = lisLen[j]+1;
17             if (maxLen<lisLen[i]) maxLen = lisLen[i];
18         }
20         return maxLen;
22     }
http://buttercola.blogspot.com/2015/12/leetcode-longest-increasing-subsequence.html

    public int lengthOfLIS(int[] nums) {

        if (nums == null || nums.length == 0) {

            return 0;

        }

         

        int[] dp = new int[nums.length + 1];

        for (int i = 1; i < nums.length + 1; i++) {

            dp[i] = 1;

        }

         

        int maxLen = 1;

         

        for (int i = 1; i <= nums.length; i++) {

            for (int j = 1; j < i; j++) {

                if (nums[i - 1] > nums[j - 1]) {

                    dp[i] = Math.max(dp[i], dp[j] + 1);

                }

            }

             

            maxLen = Math.max(maxLen, dp[i]);

        }

         

        return maxLen;

    }
https://discuss.leetcode.com/topic/28719/short-java-solution-using-dp-o-n-log-n/3
public int lengthOfLIS(int[] nums) {
    ArrayList<Integer> dp = new ArrayList<>(nums.length);
    for (int num : nums) {
        if (dp.size() == 0 || dp.get(dp.size()-1) < num) dp.add(num);
        else {
            int i = Collections.binarySearch(dp, num);
            dp.set((i<0) ? -i-1 : i, num);
        }
    }
    return dp.size();
}
X.  O(n * log n)解法: 维护一个单调序列

http://blog.welkinlan.com/2015/11/05/longest-increasing-subsequence-leetcode-java/
Maintain a potential LIS arraylist
for each element n, append it to the tail of LIS if its the largest O/W binary search the insert position in the LIS, then replace the original element with n.
https://leetcode.com/discuss/67609/short-java-solution-using-dp-o-n-log-n
The idea is that as you iterate the sequence, you keep track of the minimum value a subsequence of given length might end with, for all so far possible subsequence lengths. So dp[i] is the minimum value a subsequence of length i+1 might end with. Having this info, for each new number we iterate to, we can determine the longest subsequence where it can be appended using binary search. The final answer is the length of the longest subsequence we found so far.
Arrays.binarySearch() returns ( - insertion_index - 1) in cases where the element was not found in the array. Initially the dp array is all zeroes. For all zeroes array the insertion index for any element greater than zero is equal to the length of the array (dp.length in this case). This means that the number needs to be added to the end of the array to keep the array sorted.
As a result pos is equal to insertion_index, which is equal to dp.length. So the dp[pos] = nums[i]; line fails, because pos is out of bounds.
This problem does not happen when searching just part of the array by using Arrays.binarySearch(dp, 0, len, nums[i]), because in this case the insertion index is len.
By the way the issue will happen on any input that contains a positive integer, e.g. [1].
    public int lengthOfLIS(int[] nums) {            
        int[] dp = new int[nums.length];
        int len = 0;

        for(int x : nums) {
            int i = Arrays.binarySearch(dp, 0, len, x);
            if(i < 0) i = -(i + 1);
            dp[i] = x;
            if(i == len) len++;
        }

        return len;
    }
public int lengthOfLIS(int[] nums) {
    ArrayList<Integer> dp = new ArrayList<>(nums.length);
    for (int num : nums) {
        if (dp.size() == 0 || dp.get(dp.size()-1) < num) dp.add(num);
        else {
            int i = Collections.binarySearch(dp, num);
            dp.set((i<0) ? -i-1 : i, num);//\\
        }
    }
    return dp.size();
}
This is a great solution. By the way, what is the difference between two versions of Arrays.binarySearch()? If substitute it with another one, it has the error: java.lang.ArrayIndexOutOfBoundsException: 8 for input [10,9,2,5,3,7,101,18]... Has anyone seen where the problem is?
    public int lengthOfLIS(int[] nums) {
        // keep track of the end elements of each lists
        int[] dp = new int[nums.length];
        int len = 0;
        for(int i = 0; i<nums.length; i++){
            int pos = Arrays.binarySearch(dp,nums[i]);
            if(pos < 0){
               pos = - pos - 1;
            }
            dp[pos] = nums[i];
            if(pos == len) len++;
        }
        return len;
    }


    public int lengthOfLIS(int[] nums) {

        if (nums == null || nums.length == 0) {

            return 0;

        }

        ArrayList<Integer> lis = new ArrayList<Integer>();

        for (int n : nums) {

            if (lis.size() == 0 || lis.get(lis.size() - 1) < n) {

                lis.add(n);

            } else {

                updateLIS(lis, n);

            }

        }

        return lis.size();

    }

    

    private void updateLIS(ArrayList<Integer> lis, int n) {

        int l = 0, r = lis.size() - 1;

        while (l < r) {

            int m = l + (r - l) / 2;

            if (lis.get(m) < n) {

                l = m + 1;

            } else {

                r = m;

            }

        }

        lis.set(l, n);

    }
https://leetcode.com/discuss/67565/simple-java-o-nlogn-solution
https://leetcode.com/discuss/74510/java-easy-version-to-understand
public int lengthOfLIS(int[] nums) 
{
    List<Integer> sequence = new ArrayList();
    for(int n : nums) update(sequence, n);

    return sequence.size();
}

private void update(List<Integer> seq, int n)
{
    if(seq.isEmpty() || seq.get(seq.size() - 1) < n) seq.add(n);
    else
    {
        seq.set(findFirstLargeEqual(seq, n), n);
    }
}

private int findFirstLargeEqual(List<Integer> seq, int target)
{
    int lo = 0;
    int hi = seq.size() - 1;
    while(lo < hi)
    {
        int mid = lo + (hi - lo) / 2;
        if(seq.get(mid) < target) lo = mid + 1;
        else hi = mid;
    }

    return lo;
}

遍历nums数组，二分查找每一个数在单调序列中的位置，然后替换之。
    def lengthOfLIS(self, nums):
        size = len(nums)
        dp = []
        for x in range(size):
            low, high = 0, len(dp) - 1
            while low <= high:
                mid = (low + high) / 2
                if dp[mid] >= nums[x]:
                    high = mid - 1
                else:
                    low = mid + 1
            if low >= len(dp):
                dp.append(nums[x])
            else:
                dp[low] = nums[x]
        return len(dp)
https://leetcode.com/discuss/67625/java-simple-and-easy-understanding-nlogn-solution
Read full article from [LeetCode]Longest Increasing Subsequence | 书影博客
 * @author het
 *
 */
import java.util.*;
public class LeetCode300 {
	
	
	 public int lengthOfLIS(int[] nums) {            
	        int[] dp = new int[nums.length];
	        int len = 0;

	        for(int x : nums) {
	            int i = Arrays.binarySearch(dp, 0, len, x);
	            if(i < 0) i = -(i + 1);
	            dp[i] = x;
	            if(i == len) len++;
	        }

	        return len;
	    }
	
	public static int lengthOfLIS2(int[] nums) {
		  if(nums.length == 0 || nums == null){
		            return 0;
		        }
		        int []dp = new int[nums.length];
		        int max = Integer.MIN_VALUE;
		        for (int i = 0; i < nums.length; i++) {
		            dp[i] = 1;
		            for (int j = 0; j < i; j++) {
		                if (nums[j] < nums[i]) {
		                    dp[i] = dp[i] > dp[j] + 1 ? dp[i] : dp[j] + 1;
		                }
		            }
		            if (dp[i] > max) {
		                max = dp[i];
		            }
		        }
		        return max;
		    }
	public int lengthOfLIS(int[] nums) 
	{
	    List<Integer> sequence = new ArrayList();
	    for(int n : nums) update(sequence, n);

	    return sequence.size();
	}

	private void update(List<Integer> seq, int n)
	{
	    if(seq.isEmpty() || seq.get(seq.size() - 1) < n) seq.add(n);
	    else
	    {
	        seq.set(findFirstLargeEqual(seq, n), n);
	    }
	}

	private int findFirstLargeEqual(List<Integer> seq, int target)
	{
	    int lo = 0;
	    int hi = seq.size() - 1;
	    while(lo < hi)
	    {
	        int mid = lo + (hi - lo) / 2;
	        if(seq.get(mid) < target) lo = mid + 1;
	        else hi = mid;
	    }

	    return lo;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int lengthOfLIS(int[] nums) {
	    ArrayList<Integer> dp = new ArrayList<>(nums.length);
	    for (int num : nums) {
	        if (dp.size() == 0 || dp.get(dp.size()-1) < num) dp.add(num);
	        else {
	            int i = Collections.binarySearch(dp, num);
	            dp.set((i<0) ? -i-1 : i, num);
	        }
	    }
	    return dp.size();
	}
	
	
	public int lengthOfLIS(int[] nums) {
	    ArrayList<Integer> dp = new ArrayList<>(nums.length);
	    for (int num : nums) {
	        if (dp.size() == 0 || dp.get(dp.size()-1) < num) dp.add(num);
	        else {
	            int i = Collections.binarySearch(dp, num);
	            dp.set((i<0) ? -i-1 : i, num);
	        }
	    }
	    return dp.size();
	}
	
	
	public int lengthOfLIS(int[] nums) {
	    ArrayList<Integer> dp = new ArrayList<>(nums.length);
	    for (int num : nums) {
	        if (dp.size() == 0 || dp.get(dp.size()-1) < num) dp.add(num);
	        else {
	            int i = Collections.binarySearch(dp, num);
	            dp.set((i<0) ? -i-1 : i, num);
	        }
	    }
		return dp.size();
	}
	//X.  O(n * log n)解法: 维护一个单调序列
	
	 public int longestIncreasingSubsequence(int[] nums) {
		         if (nums.length==0) return 0;
		         int len = nums.length;
		        int[] lisLen = new int[len];
		         lisLen[0] = 1;
		        int maxLen = lisLen[0];
		        for (int i=1;i<len;i++){
		            lisLen[i]=1; //\\
		             for (int j=i-1;j>=0;j--)
		                 if (nums[i]>=nums[j] && lisLen[i]<lisLen[j]+1)
		                     lisLen[i] = lisLen[j]+1;
		             if (maxLen<lisLen[i]) maxLen = lisLen[i];
		         }
		        return maxLen;
		     }

}
