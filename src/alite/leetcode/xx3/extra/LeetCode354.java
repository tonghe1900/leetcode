package alite.leetcode.xx3.extra;

import java.util.Arrays;

/**
 * LeetCode 354 - Russian doll envelopes

Related: DP - Longest Common Subsequence
https://discuss.leetcode.com/topic/129/russian-doll-envelopes
https://www.glassdoor.com/Interview/You-have-a-set-of-envelopes-of-different-widths-and-heights-One-envelope-can-fit-into-another-if-and-only-if-both-the-widt-QTN_472091.htm
You have a number of envelopes with widths and heights given as a pair of integers (w, h).
 One envelope can fit into another if and only if both the width and height of one envelope is greater than
  the width and height 
 of the other envelope.
What is the maximum number of envelopes can you Russian doll? (put one inside other)
Example:
Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).

The first glance at this problem seems that it is not that easy. After diving into it, 
I found that this problem is the "Longest Common SubArray" problem.
Let me give some analysis:
There are two conditions to ensure that a smaller envelope can be wrapped into another: smaller width and smaller height.
 So let's first satisfy one condition:
Sort the envelopes with their width to get Array1: a1, a2, a3, a4, a5, a6, a7, a8 with O(n*logn)
Sort the envelopes with their height to get Array2: b1, b2, b3, b4, b5, b6, b7, b8 with O(n*logn)
Any list that satisfy the "Russian doll" must be the subarray of both Array1 and Array2. So our task here is to find the Longest Common SubArray of Array1 and Array2which can be accomplished with DP in O(n*n)
So the total time complexity is O(n*n).
X. https://leetcode.com/discuss/106946/java-nlogn-solution-with-explanation
Sort the array. Ascend on width and descend on height if width are same.
Find the longest increasing subsequence based on height.
Since the width is increasing, we only need to consider height.
[3, 4] cannot contains [3, 3], so we need to put [3, 4] before [3, 3] when sorting otherwise it will be counted as an increasing number if the order is [3, 3], [3, 4]
"descend on height if width are same"
public int maxEnvelopes(int[][] envelopes) {
    if(envelopes == null || envelopes.length == 0 
       || envelopes[0] == null || envelopes[0].length != 2)
        return 0;
    Arrays.sort(envelopes, new Comparator<int[]>(){
        public int compare(int[] arr1, int[] arr2){
            if(arr1[0] == arr2[0])
                return arr2[1] - arr1[1];
            else
                return arr1[0] - arr2[0];
       } 
    });
    int dp[] = new int[envelopes.length];
    int len = 0;
    for(int[] envelope : envelopes){
        int index = Arrays.binarySearch(dp, 0, len, envelope[1]);
        if(index < 0)
            index = -(index + 1);
        dp[index] = envelope[1];
        if(index == len)
            len++;
    }
    return len;
}
java.util.Arrays.binarySearch
index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.

http://www.cnblogs.com/tonix/p/5565733.html
http://bookshadow.com/weblog/2016/06/07/leetcode-russian-doll-envelopes/
参考Longest Increasing Subsequence（最长递增子序列）的解法。
以输入envelopes其中的一个维度进行排序，对另外一个维度求解LIS即可。
1. 对输入的envelopes进行排序：首先比较宽度，宽度小的在前；宽度相同时，高度大的在前（这样处理可以避免相同宽度的信封被重复统计）
2. 利用二分查找维护一个递增队列，以高度作为比较条件。
由于LeetCode OJ的最大测试用例规模超过4000，O(n ^ 2)的解法会超时
    def maxEnvelopes(self, envelopes):
        """
        :type envelopes: List[List[int]]
        :rtype: int
        """
        nums = sorted(envelopes, 
                      cmp = lambda x,y: x[0] - y[0] if x[0] != y[0] else y[1] - x[1])
        size = len(nums)
        dp = []
        for x in range(size):
            low, high = 0, len(dp) - 1
            while low <= high:
                mid = (low + high) / 2
                if dp[mid][1] < nums[x][1]:
                    low = mid + 1
                else:
                    high = mid - 1
            if low < len(dp):
                dp[low] = nums[x]
            else:
                dp.append(nums[x])
        return len(dp)
https://www.hrwhisper.me/leetcode-russian-doll-envelopes/
题意：给定一些信封的宽和长，当且仅当信封x的宽和长均小于另一个信封y时，x可以装入y，求最多可以嵌套的装几个？
思路: 看到就知道是求LIS了(最大上升子序列)，不明白为啥会是hard难度。
求LIS可以直接简单的dp，设dp[i]为以i为结尾的LIS长度，则dp[i] = max(0,dp[j] | j<i && A[j] < A[i]) + 1
复杂度为O(n^2)，但可以优化到O(nlogn)，排序然后二分。
本题需要注意排序的时候要注意第一项相同的情况下，第二项的顺序。


    def maxEnvelopes(self, envelopes):

        """

        :type envelopes: List[List[int]]

        :rtype: int

        """


        def lower_bound(arrays, L, R, x):

            while L < R:

                mid = (L + R) >> 1

                if x <= arrays[mid]:

                    R = mid

                else:

                    L = mid + 1

            return L


        if not envelopes: return 0

        A = sorted(envelopes, lambda x, y: x[0] - y[0] if x[0] != y[0] else y[1] - x[1])

        n = len(A)

        dp = [1] * n

        g = [0x7fffffff] * (n + 1)

        for i in xrange(n):

            k = lower_bound(g, 1, n, A[i][1])

            dp[i] = k

            g[k] = A[i][1]

        return max(dp)
X. https://leetcode.com/discuss/106888/java-answer-n-lg-n-time-using-binary-search
Sort the array of envelopes by their width and heigh
construct an array f which stores the height of envelopes. and its cell means the minimum height is f[l] while the number of envelopes is l (like Longest Increasing Subsequence)
for those envelopes whose have the same width, find the exact indexes using binary search (we must firstly put them into a temp array t)
update f and ret

X. O(N^2)
https://leetcode.com/discuss/107218/short-and-simple-java-solution-15-lines
https://leetcode.com/discuss/106836/simple-dp-solution
https://leetcode.com/discuss/106837/clean-java-solution
public int maxEnvelopes(int[][] envelopes) {
    if (   envelopes           == null
        || envelopes.length    == 0
        || envelopes[0]        == null
        || envelopes[0].length == 0){
        return 0;    
    }

    Arrays.sort(envelopes, new Comparator<int[]>(){
        @Override
        public int compare(int[] e1, int[] e2){
            return Integer.compare(e1[0], e2[0]);
        }
    });

    int   n  = envelopes.length;
    int[] dp = new int[n];

    int ret = 0;
    for (int i = 0; i < n; i++){
        dp[i] = 1;

        for (int j = 0; j < i; j++){
            if (   envelopes[i][0] > envelopes[j][0]
                && envelopes[i][1] > envelopes[j][1]){
                dp[i] = Math.max(dp[i], 1 + dp[j]);    
            }
        }

        ret = Math.max(ret, dp[i]);
    }
    return ret;
}
http://www.cnblogs.com/grandyang/p/5568818.html
 * @author het
 *
 */
public class LeetCode354 {
//	The first glance at this problem seems that it is not that easy. After diving into it, 
//	I found that this problem is the "Longest Common SubArray" problem.
//	Let me give some analysis:
//	There are two conditions to ensure that a smaller envelope can be wrapped into another: smaller width and smaller height.
//	 So let's first satisfy one condition:
//	Sort the envelopes with their width to get Array1: a1, a2, a3, a4, a5, a6, a7, a8 with O(n*logn)
//	Sort the envelopes with their height to get Array2: b1, b2, b3, b4, b5, b6, b7, b8 with O(n*logn)
//	Any list that satisfy the "Russian doll" must be the subarray of both Array1 and Array2. So our task here is to find the Longest Common SubArray of Array1 and Array2which can be accomplished with DP in O(n*n)
//	So the total time complexity is O(n*n).
//	X. https://leetcode.com/discuss/106946/java-nlogn-solution-with-explanation
//	Sort the array. Ascend on width and descend on height if width are same.
//	Find the longest increasing subsequence based on height.
	
//	Sort the array. Ascend on width and descend on height if width are same.
//	Find the longest increasing subsequence based on height.
//	Since the width is increasing, we only need to consider height.
//	[3, 4] cannot contains [3, 3], so we need to put [3, 4] before [3, 3] when sorting otherwise it will be counted as an increasing number if the order is [3, 3], [3, 4]
//	"descend on height if width are same"
	//"descend on height if width are same"
	//"descend on height if width are same"
	//"descend on height if width are same"
	//"descend on height if width are same"
	//"descend on height if width are same"
	//"descend on height if width are same"
	public int maxEnvelopes(int[][] envelopes) {
	    if(envelopes == null || envelopes.length == 0 
	       || envelopes[0] == null || envelopes[0].length != 2)
	        return 0;
	    Arrays.sort(envelopes, new Comparator<int[]>(){
	        public int compare(int[] arr1, int[] arr2){
	            if(arr1[0] == arr2[0])
	                return arr2[1] - arr1[1];
	            else
	                return arr1[0] - arr2[0];
	       } 
	    });
	    ////	Sort the array. Ascend on width and descend on height if width are same.
//		Find the longest increasing subsequence based on height.
	    //Ascend on width and descend on height if width are same.
	    int dp[] = new int[envelopes.length];
	    int len = 0;
	    for(int[] envelope : envelopes){
	        int index = Arrays.binarySearch(dp, 0, len, envelope[1]);
	        if(index < 0)
	            index = -(index + 1);
	        dp[index] = envelope[1];
	        if(index == len)
	            len++;
	    }
	    return len;
	}
	
	// (-(insertion point) - 1)
	//、、 (-(insertion point) - 1)
	// (-(insertion point) -1)
//	java.util.Arrays.binarySearch
//	index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
