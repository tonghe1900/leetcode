package alite.leetcode.xx4;
/**
 * http://bookshadow.com/weblog/2016/10/02/leetcode-split-array-largest-sum/
Given an array which consists of non-negative integers and an integer m, you can split the array 
into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
//minimize the largest sum among these m subarrays
、、minimize the largest sum among these m subarrays
Note:
Given m satisfies the following constraint: 1 ≤ m ≤ length(nums) ≤ 14,000.
Examples:
Input:
nums = [1,2,3,4,5]
m = 2
O(nums.length * m * nums.length)
largest(nums, i, m) = minimize(Math.max(largest(nums, j, m-1),  sum(i,j+1)))  for j = 0 ... i-1


largest(nums, nums.length-1, m)

Output:
9

Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [1,2,3] and [4,5],
where the largest sum among the two subarrays is only 9.
X. Bisection method
https://discuss.leetcode.com/topic/61315/java-easy-binary-search-solution-8ms
Given a result, it is easy to test whether it is valid or not.
The max of the result is the sum of the input nums.
The min of the result is the max num of the input nums.
Given the 3 conditions above we can do a binary search. (need to deal with overflow)
    public int splitArray(int[] nums, int m) {
        long max = 0, min = 0;
        for(int num: nums) {
            max += num;
            min = Math.max(num, min);
        }
        return (int) binarySearch(nums, min, max, m);
    }
    private long binarySearch(int[] nums, long min, long max, int m) {
        if(min == max) return min;
        long mid = (max - min) / 2 + min;
        if(isValid(nums, mid, m)) {
            if(mid == min || !isValid(nums, mid - 1, m)) return mid;
            else return binarySearch(nums, min, mid - 1, m);
        } else return binarySearch(nums, mid + 1, max, m);
    }
https://discuss.leetcode.com/topic/61324/clear-explanation-8ms-binary-search-java
The answer is between maximum value of input array numbers and sum of those numbers.
Use binary search to approach the correct answer. We have l = max number of array; r = sum of all numbers in the array;Every time we do mid = (l + r) / 2;
Use greedy to narrow down left and right boundaries in binary search.
3.1 Cut the array from left.
3.2 Try our best to make sure that the sum of numbers between each two cuts (inclusive) is large enough but still less than mid.
3.3 We'll end up with two results: either we can divide the array into more than m subarrays or we cannot.
    public int splitArray(int[] nums, int m) {
        long sum = 0;
        int max = 0;
        for(int num: nums){
            max = Math.max(max, num);
            sum += num;
        }
        return (int)binary(nums, m, sum, max);
    }
    
    private long binary(int[] nums, int m, long high, long low){
        long mid = 0;
        while(low < high){
            mid = (high + low)/2;
            if(valid(nums, m, mid)){
                //System.out.println(mid);
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        return high;
    }
    
    private boolean valid(int[] nums, int m, long max){
        int cur = 0;
        int count = 1;
        for(int num: nums){
            cur += num;
            if(cur > max){
                cur = num;
                count++;
                if(count > m){
                    return false;
                }
            }
        }
        return true;
    }

二分枚举答案（Binary Search）
将数组nums拆分成m个子数组，每个子数组的和不小于sum(nums) / m，不大于sum(nums)
又因为数组nums中只包含非负整数，因此可以通过二分法在上下界内枚举答案。
时间复杂度O(n * log m)，其中n是数组nums的长度，m为数组nums的和
http://www.cnblogs.com/grandyang/p/5933787.html
https://all4win78.wordpress.com/2016/10/22/leetcode-410-split-array-largest-sum/
另一种就是利用sum是整数的性质进项binary search。UB (upper bound) 是所有原array的所有数字的和，LB (lower bound) 是UB/m以数组中最大值两者之间的较大者。然后根据binary search的方式去验证给定的array能否依据这个largest sum进行分割。需要注意的是，这里sum需要使用long而不是int，不然会有overflow的问题。世间复杂度是O(n log sum) <= O(n log n*Integer.MAX_VALUE) <= O(n (logn + c)) = O(n logn)，额外的空间复杂度为O(1)。

    public int splitArray(int[] nums, int m) {

        if (nums == null || nums.length == 0) {

            return 0;

        }

        long ub = 0;

        long lb = 0;

        for (int i : nums) {

            ub += i;

            lb = Math.max(lb, i);

        }

        lb = Math.max(lb, ub / m);

        while (lb < ub) {

            long mid = (ub - lb) / 2 + lb;

            if (isSplitable(nums, mid, m)) {

                ub = mid;

            } else {

                lb = mid + 1;

            }

        }

        return (int) lb;

    }

     

    private boolean isSplitable(int[] nums, long sum, int m) {

        int count = 1;

        int tempSum = 0;

        for (int i : nums) {

            tempSum += i;

            if (tempSum > sum) {

                count += 1;

                if (count > m) {

                    return false;

                }

                tempSum = i;

            }

        }

        return true;

    }

X. DP

DP的话实现起来稍微麻烦一点，需要保存一个2d的array，大小为n*m，每个位置（i，j）保存从数列位置i开始到数列尾端，分割成j份的largest sum。同时空间和时间复杂度在m比较大的情况下比较高，并不是很建议勇这种方法implement，但是可以作为一种思路。类似的题目可以参考Burst Balloons。
https://discuss.leetcode.com/topic/66289/java-dp
time complexity of naive dp is O(n2 * m)
    public int splitArray(int[] nums, int K) {
        int[] s = new int[nums.length];
        s[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            s[i] = nums[i] + s[i - 1];
        }
        
        for (int k = 2; k <= K; k++) {
            for (int i = nums.length - 1; i >= k - 1; i--) {
                int min = Integer.MAX_VALUE;
                int left = nums[i];
                for (int p = i - 1; p >= k - 2; p--) {
                    min = Math.min(min, Math.max(s[p], left));
                    left += nums[p];
                    if (left >= min) {
                        break;
                    }
                }
                s[i] = min;
            }
        }
        
        return s[nums.length - 1];
    }
https://discuss.leetcode.com/topic/61405/dp-java
This is obviously not as good as the binary search solutions; but it did pass OJ.
dp[s,j] is the solution for splitting subarray n[j]...n[L-1] into s parts.
dp[s+1,i] = min{ max(dp[s,j], n[i]+...+n[j-1]) }, i+1 <= j <= L-s
This solution does not take advantage of the fact that the numbers are non-negative (except to break the inner loop early). That is a loss. (On the other hand, it can be used for the problem containing arbitrary numbers)
public int splitArray(int[] nums, int m)
{
    int L = nums.length;
    int[] S = new int[L+1];
    S[0]=0;
    for(int i=0; i<L; i++)
        S[i+1] = S[i]+nums[i];

    int[] dp = new int[L];
    for(int i=0; i<L; i++)
        dp[i] = S[L]-S[i];

    for(int s=1; s<m; s++)
    {
        for(int i=0; i<L-s; i++)
        {
            dp[i]=Integer.MAX_VALUE;
            for(int j=i+1; j<=L-s; j++)
            {
                int t = Math.max(dp[j], S[j]-S[i]);
                if(t<=dp[i])
                    dp[i]=t;
                else
                    break;
            }
        }
    }

    return dp[0];
}
Elegant solution. Based on your solution I did some optimization, to short cut some un-necessary search:
public static int splitArray(int[] nums, int m) {
    int[] dp = new int[nums.length];

    for(int i = nums.length-1; i>=0; i--) 
        dp[i] = i== nums.length -1 ? nums[i] : dp[i+1] + nums[i];
    for(int im = 2; im <= m; im ++) {
        int maxPart = nums.length + 1 - im;
        for(int i=0; i<maxPart; i++) {
            dp[i] = Integer.MAX_VALUE;
            int leftSum = 0;
            for(int p=i; p<maxPart; p++) {
                leftSum += nums[p];
                if(leftSum > dp[i])
                    break;  // There's no more better soluiton, stop the search.
                int val = Math.max(leftSum, dp[p+1]);
                if(val < dp[i])
                    dp[i] = val;
            }
            if(im == m)  // The last round, get first one is enough
                break;
        }
    }        
    return dp[0];
}
You can add the sum on the run, you can compare the sum only since sum is accending and dp[j+1] is descending. Unlike the binary search solution, O(nlogk) (k: related to the range of num), this one is not related to the num range, O(n^2*m), I think it could do better for certain test cases, such as [700000000,200000000,500000000,1000000000,800000000], 2
public int splitArray(int[] nums, int m) {
    int n = nums.length;
    int[] dp = new int[n+1];
    for (int i = n-1; i >= m-1; --i) {
        dp[i] = dp[i+1] + nums[i];
    }
    for (int k = 2; k <= m; ++k) {
        for (int i = m-k; i <= n-k; ++i) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i, sum = 0; j <= n-k; ++j) {
                sum += nums[j];
                if (sum >= dp[i]) break;
                dp[i] = Math.max(sum, dp[j+1]);
            }
            if (k == m) break;
        }
    }
    return dp[0];
}
X. brute force
https://discuss.leetcode.com/topic/64189/java-recursive-dp-having-trouble-in-iterative-dp
public int splitArray(int[] nums, int m) {
 if (nums.length == 0 || nums == null || m == 0)
  return Integer.MAX_VALUE;
 return splitArray(nums, m, 0);
}

public int splitArray(int[] nums, int m, int start) {
 if (nums.length == 0 || nums == null || m == 0)
  return Integer.MAX_VALUE;
 if (start > nums.length)
  return Integer.MAX_VALUE;
 if (m == 1) {
  int sum = 0;
  for (int i = start; i < nums.length; i++)
   sum += nums[i];
  return sum;
 }
 int sum = 0;
 int split = 0;
 int min = Integer.MAX_VALUE;
 for (int i = start; i < nums.length; i++) {
  sum += nums[i];
  split = Math.max(sum, splitArray(nums, m - 1, i + 1));
  min = Math.min(min, split);
 }
 return min;
}
 * @author het
 *
 */
public class LeetCode410SplitArrayLargestSum {
	//time complexity of naive dp is O(n2 * m)
    public int splitArray(int[] nums, int K) {
        int[] s = new int[nums.length];
        s[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            s[i] = nums[i] + s[i - 1];
        }
        
        for (int k = 2; k <= K; k++) {
            for (int i = nums.length - 1; i >= k - 1; i--) {// i iterate from big to small
                int min = Integer.MAX_VALUE;
                int left = nums[i];
                for (int p = i - 1; p >= k - 2; p--) {
                    min = Math.min(min, Math.max(s[p], left));
                    left += nums[p];
                    if (left >= min) {
                        break;
                    }
                }
                s[i] = min;
            }
        }
        
        return s[nums.length - 1];
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
