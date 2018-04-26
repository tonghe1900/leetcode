package alite.leetcode.newest;
/**
 * Lintcode 43 - Maximum Subarray III

Related: Lintcode 41,42 - Maximum Subarray I,II
http://www.lintcode.com/en/problem/maximum-subarray-iii/
Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.
The number in each subarray should be contiguous.
Return the largest sum.
 Notice

The subarray should contain at least one number
Example
Given [-1,4,-2,3,-2,3], k=2, return 8
https://wxx5433.gitbooks.io/interview-preparation/content/part_ii_leetcode_lintcode/dp/maximum_subarray_iii.html
Time: O(k * n^2)
Space: O(k * n)
Using sums[i][j] to denote the maximum total sum of choosing i subarrays from the first j numbers.
We can update by sums[i][j] = max(sums[i - 1][t] + maxSubarraySum(nums[t+1...j])), which means using the first t numbers to choose i - 1 subarrays, and plus the maximum sum from remaining numbers(nums[t]...nums[j-1]). We want to try all possible split point, so t ranges from nums[i-1] to nums[j-1].
In the most inner loop, it will try to examine the max sum from the subarray nums[t] to nums[j-1], where t goes from j-1 down to i-1. We can compute for each t the maximum sum. However, if we scan from right to left instead of left to right, we only needs to update the maximum value incrementally. For example, t's range is [1..5], so at first, the max sum is pick from [5], then it's picked from [4...5], ..., finally picked from [1...5]. By scanning from right to left, we are able to include the new number into computing on the fly.
  public int maxSubArray(ArrayList<Integer> nums, int k) {
    if (nums == null || nums.size() < k) {
      return 0;
    }
    int len = nums.size();
    int[][] sums = new int[k + 1][len + 1];
    for (int i = 1; i <= k; i++) {
      for (int j = i; j <= len; j++) { // at least need one number in each subarray
        sums[i][j] = Integer.MIN_VALUE;
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int t = j - 1; t >= i - 1; t--) {
          sum = Math.max(nums.get(t), sum + nums.get(t));
          max = Math.max(max, sum);
          sums[i][j] = Math.max(sums[i][j], sums[i - 1][t] + max);
        }
      }
    }
    return sums[k][len];
  }
http://blog.csdn.net/nicaishibiantai/article/details/44585383
d[i][j]代表0->i-1元素中j个subarray的maxsum  (注意不包含元素i)
d[i][j] = max(d[i][j], d[m][j-1] + max) (m = j-1 .... i-1; max需要单独求，是从元素i-1到m的max subarray, 用求max subarray的方法，需要从后往前算）
public int maxSubArray(ArrayList<Integer> nums, int k) {  
    int n = nums.size();  
    int[][] d = new int[n+1][k+1];  
    for (int j = 1; j <= k; j++) {  
        for (int i = j; i <= n; i++) {  
            d[i][j] = Integer.MIN_VALUE;  
            int max = Integer.MIN_VALUE;  
            int localMax = 0;  
            for (int m = i-1; m >= j-1; m--) {  
                localMax = Math.max(nums.get(m), nums.get(m)+localMax);  
                max = Math.max(localMax, max);  
                d[i][j] = Math.max(d[i][j], d[m][j-1] + max);  
            }  
        }  
    }  
    return d[n][k];  
}  
http://www.cnblogs.com/lishiblog/p/4183917.html
DP. d[i][j] means the maximum sum we can get by selecting j subarrays from the first i elements.
d[i][j] = max{d[p][j-1]+maxSubArray(p+1,i)}
we iterate p from i-1 to j-1, so we can record the max subarray we get at current p, this value can be used to calculate the max subarray from p-1 to i when p becomes p-1.

 7     public int maxSubArray(ArrayList<Integer> nums, int k) {
 8         if (nums.size()<k) return 0;
 9         int len = nums.size();
10         //d[i][j]: select j subarrays from the first i elements, the max sum we can get.
11         int[][] d = new int[len+1][k+1];
12         for (int i=0;i<=len;i++) d[i][0] = 0;        
13         
14         for (int j=1;j<=k;j++)
15             for (int i=j;i<=len;i++){
16                 d[i][j] = Integer.MIN_VALUE;
17                 //Initial value of endMax and max should be taken care very very carefully.
18                 int endMax = 0;
19                 int max = Integer.MIN_VALUE;                
20                 for (int p=i-1;p>=j-1;p--){
21                     endMax = Math.max(nums.get(p), endMax+nums.get(p));
22                     max = Math.max(endMax,max);
23                     if (d[i][j]<d[p][j-1]+max)
24                         d[i][j] = d[p][j-1]+max;                    
25                 }
26             }
27 
28         return d[len][k];
31     }
Use one dimension array.
 7     public int maxSubArray(ArrayList<Integer> nums, int k) {
 8         if (nums.size()<k) return 0;
 9         int len = nums.size();
10         //d[i][j]: select j subarrays from the first i elements, the max sum we can get.
11         int[] d = new int[len+1];
12         for (int i=0;i<=len;i++) d[i] = 0;        
13         
14         for (int j=1;j<=k;j++)
15             for (int i=len;i>=j;i--){
16                 d[i] = Integer.MIN_VALUE;
17                 int endMax = 0;
18                 int max = Integer.MIN_VALUE;                
19                 for (int p=i-1;p>=j-1;p--){
20                     endMax = Math.max(nums.get(p), endMax+nums.get(p));
21                     max = Math.max(endMax,max);
22                     if (d[i]<d[p]+max)
23                         d[i] = d[p]+max;                    
24                 }
25             }
26 
27         return d[len];
30     }

X.
http://hehejun.blogspot.com/2015/01/lintcodemaximum-subarray-iii.html
跟之前一样这里我们维护两个东西，localMax[i][j]为进行了i次partition前j个数字的最大subarray，并且最后一个subarray以A[j - 1](A为输入的数组)结尾；globalMax[i][j]为进行了i次partition前j个数字的最大subarray(最后一个subarray不一定需要以j - 1结尾)。类比之前的DP方程，我们推出新的DP方程：
globalMax[i][j] = max(globalMax[i][j - 1], localMax[i][j]);
localMax[i][j] = max(globalMax[i - 1][k] + sumFromTo(A[k + 1], A[j])) 其中 0< k < j;
第一眼看上去对于第二个DP方程我们需要每次构建localMax[i][j]的时候把后面都扫一遍，这样总的复杂度会达到O(n^2)，但是我们有方法把这一步优化到O(n)。
设想如下例子：
globalMax[i - 1]: 3, 5, -1, 8, 7
A[]:                    1, 2, 6, -2, 0
我们可以看到当算完A[2] max(globalMax[i - 1][k] + sumFromTo(A[k + 1], A[j])) = 11。当我们算到A[3]的时候，如果我们不考虑新加的globalMax[i - 1][2] + A[3]的组合, 之前的所有组合（globalMax[i - 1][0] + sumFromTo(A[1], A[2]), globalMax[i - 1][1] + sumFromTo(A[2], A[2])）都只需要再加一个A[3]就是新的globalMax[i - 1][k] + sumFromTo(A[k + 1], A[j])的值，所以之前的最大的值，到新的还是原来所组合的最大值，只需要和新加的比一下就可以了，所以这个值我们从最左边向右边扫一遍一直维护是可行的，并且只需要维护一个变量localMax而不是数组。时间复杂度O(k * n)，空间复杂度O(k * n)，空间应该可以维护一个滚动数组来优化到n不过这道题的逻辑比较复杂，为了维护逻辑的清晰性，我们不优化空间

https://zhengyang2015.gitbooks.io/lintcode/maximum_subarray_iii_43.html
local[i][k]表示前i个元素取k个子数组并且必须包含第i个元素的最大和。
global[i][k]表示前i个元素取k个子数组不一定包含第i个元素的最大和。
local[i][k]的状态函数：
max(global[i-1][k-1], local[i-1][k]) + nums[i-1]
有两种情况，第一种是第i个元素自己组成一个子数组，则要在前i－1个元素中找k－1个子数组，第二种情况是第i个元素属于前一个元素的子数组，因此要在i－1个元素中找k个子数组（并且必须包含第i－1个元素，这样第i个元素才能合并到最后一个子数组中），取两种情况里面大的那个。
global[i][k]的状态函数：
max(global[i-1][k]，local[i][k])
有两种情况，第一种是不包含第i个元素，所以要在前i－1个元素中找k个子数组，第二种情况为包含第i个元素，在i个元素中找k个子数组且必须包含第i个元素，取两种情况里面大的那个
    public int maxSubArray(int[] nums, int k) {
        if(nums.length < k){
            return 0;
        }

        int len = nums.length;

        //local[i][k]表示前i个元素取k个子数组并且必须包含第i个元素的最大和。
        int[][] localMax = new int[len + 1][k + 1];
        //global[i][k]表示前i个元素取k个子数组不一定包含第i个元素的最大和。
        int[][] globalMax = new int[len + 1][k + 1];

        for(int j = 1; j <= k; j++){
        //前j－1个元素不可能找到不重叠的j个子数组，因此初始化为最小值，以防后面被取到
            localMax[j - 1][j] = Integer.MIN_VALUE;
            for(int i = j; i <= len; i++){
                localMax[i][j] = Math.max(globalMax[i - 1][j - 1], localMax[i - 1][j]) + nums[i - 1];
                if(i == j){
                    globalMax[i][j] = localMax[i][j];
                }else{
                    globalMax[i][j] = Math.max(globalMax[i - 1][j], localMax[i][j]);
                }
            }
        }

        return globalMax[len][k];
    }

https://leilater.gitbooks.io/codingpractice/content/dynamic_programming/maximum_subarray_iii.html
dp[i][j]表示从前i个数( i.e., [0, i-1]) 中取j个subarray得到的最大值，那么要求的结果就是dp[n][k]：从前n个数中取k个subarray得到的最大和。
状态转移：从前i个中取j个，那么我们可以从前p个数（i.e., [0, p-1]）中取j-1个subarray，再加上从P到i-1这个subarray中的最大和（也就是Maximum Subarray问题）。从这句话里我们也可以读出来p的范围应该是j-1~i-1
    int maxSubArray(vector<int> nums, int k) {
        if(nums.empty()) return 0;
        int n = nums.size();
        if(n < k) return 0;
        vector<vector<int> > max_sum(n+1, vector<int>(k+1, INT_MIN));
        //max_sum[i][j]: max sum of j subarrays generated from nums[0] ~ nums[i-1]
        //note that j is always <= i
        //init
        for(int i=0; i<=n; i++) {
            max_sum[i][0] = 0;
        }
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=min(i,k); j++) {
                max_sum[i][j] = max_sum[i - 1][j];
                //max_sum[i][j] equals to 1) max sum of j-1 subarrays generated from nums[0] ~ nums[p-1]
                //plus 2) max sum of the subarry from nums[p] ~ nums[i-1]
                //p can be j-1 ~ i-1
                for(int p = i-1; p >= j-1; p--) {
                    //compute the max of of the subarry from nums[p] ~ nums[i-1]
                    int global = maxSubArray(nums, p, i-1);
                    max_sum[i][j] = max(max_sum[i][j], max_sum[p][j-1] + global);
                }
            }
        }
        return max_sum[n][k];
    }
    //compute max sum of subarray [start, end]
    int maxSubArray(vector<int> &nums, int start, int end) {
        int local = 0; 
        int global = INT_MIN;
        for(int i=start; i<=end; i++) {
            local = max(nums[i], local + nums[i]);
            global = max(global, local);
        }
        return global;
    }

上面的算法是O(k*n^3)的复杂度，因为最里层求subarray的max sum也要耗费O(n). for(int p = i-1; p >= j-1; p--)和里面调用maxSubarry有重复计算的部分，p是从i-1往前加，这样的话直接update global就行了，就像在maxSubArray函数里那样。
http://blog.hyoung.me/cn/2017/02/maximum-subarray/

+http://www.jiuzhang.com/solutions/maximum-subarray-iii/
 * @author het
 *
 */
public class Lintcode_43MaximumSubarrayIII {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
