package alite.leetcode.newest;
/**
 * LeetCode 629 - K Inverse Pairs Array

https://leetcode.com/problems/k-inverse-pairs-array
Given two integers n and k, find how many different arrays consist of numbers from 1 to n such that there are exactly k inverse pairs.
We define an inverse pair as following: For ith and jth element in the array, if i < j and a[i] > a[j] then it's an inverse pair; Otherwise, it's not.
Since the answer may very large, the answer should be modulo 109 + 7.
Example 1:
Input: n = 3, k = 0
Output: 1
Explanation: 
Only the array [1,2,3] which consists of numbers from 1 to 3 has exactly 0 inverse pair.
Example 2:
Input: n = 3, k = 1
Output: 2
Explanation: 
The array [1,3,2] and [2,1,3] have exactly 1 inverse pair.
Note:
The integer n is in the range [1, 1000] and k is in the range [0, 1000].
https://leetcode.com/articles/k-inverse-pairs-array/

X. DP
https://discuss.leetcode.com/topic/93765/shared-my-c-o-n-k-solution-with-explanation
For example, if we have some permutation of 1...4
5 x x x x creates 4 new inverse pairs
x 5 x x x creates 3 new inverse pairs
...
x x x x 5 creates 0 new inverse pairs
O(N * K ^ 2) SOLUTION

We can use this formula to solve this problem
dp[i][j] //represent the number of permutations of (1...n) with k inverse pairs.
dp[i][j] = dp[i-1][j] + dp[i-1][j-1] + dp[i-1][j-2] + ..... +dp[i-1][j - i + 1]
So, We write a O(k*n^2) Solution through above formula like this
    int kInversePairs(int n, int k) {
        vector<vector<int>> dp(n + 1, vector<int>(k+1, 0));
        dp[0][0] = 1;
        for(int i = 1; i <= n; ++i){
            for(int j = 0; j < i; ++j){ // In number i, we can create 0 ~ i-1 inverse pairs 
                for(int m = 0; m <= k; ++m){ //dp[i][m] +=  dp[i-1][m-j]
                    if(m - j >= 0 && m - j <= k){
                        dp[i][m] = (dp[i][m] + dp[i-1][m-j]) % mod; 
                    }
                }
            }
        }
        return dp[n][k];
    }
    const int mod = pow(10, 9) + 7;

    public int kInversePairs(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                if (j == 0)
                    dp[i][j] = 1;
                else {
                    for (int p = 0; p <= Math.min(j, i - 1); p++)
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - p]) % 1000000007;
                }
            }
        }
        return dp[n][k];
    }
public int kInversePairs(int n, int k) {
        long[][] dp = new long[n][k+1];
        dp[0][0]=1;//mean sum of arrays that consist of 1 with 0 inverse pairs
        for(int i=1;i<n;i++){
            for(int j=0;j<=k;j++){
                for(int m=j;m>=0&&m>=(j-i);m--){
                    dp[i][j]+=dp[i-1][m];
                }
                dp[i][j]=dp[i][j]%1000000007;
            }
        }
        return (int)dp[n-1][k];
    }
X. DP O(NK)
https://discuss.leetcode.com/topic/93815/java-dp-o-nk-solution
dp[n][k] denotes the number of arrays that have k inverse pairs for array composed of 1 to n
we can establish the recursive relationship between dp[n][k] and dp[n-1][i]:
if we put n as the last number then all the k inverse pair should come from the first n-1 numbers
if we put n as the second last number then there's 1 inverse pair involves n so the rest k-1 comes from the first n-1 numbers
...
if we put n as the first number then there's n-1 inverse pairs involve n so the rest k-(n-1) comes from the first n-1 numbers
dp[n][k] = dp[n-1][k]+dp[n-1][k-1]+dp[n-1][k-2]+...+dp[n-1][k+1-n+1]+dp[n-1][k-n+1]
But the above Dp process We have to finish it in O(n * k * k ). We have to optimized it

It's possible that some where in the right hand side the second array index become negative, since we cannot generate negative inverse pairs we just treat them as 0, but still leave the item there as a place holder.
dp[n][k] = dp[n-1][k]+dp[n-1][k-1]+dp[n-1][k-2]+...+dp[n-1][k+1-n+1]+dp[n-1][k-n+1]
dp[n][k+1] = dp[n-1][k+1]+dp[n-1][k]+dp[n-1][k-1]+dp[n-1][k-2]+...+dp[n-1][k+1-n+1]
so by deducting the first line from the second line, we have
dp[n][k+1] = dp[n][k]+dp[n-1][k+1]-dp[n-1][k+1-n]

dp[i][j] = dp[i-1][j] + dp[i-1][j-1] + ......+dp[i-1][j-i+1] as per definition ---(1)
But dp[i][j-1] = dp[i-1][j-1] + dp[i-1][j-2] + ....+ dp[i-1][j-i] as per definition ---(2)
From (1) and (2)
dp[i][j] = dp[i-1][j] + (dp[i-1][j-1] + ......+dp[i-1][j-i+1]) + dp[i-1][j-i] - dp[i-1][j-i]
=>dp[i][j] = dp[i-1][j] + (dp[i-1][j-1] + ......+dp[i-1][j-i+1] + dp[i-1][j-i]) - dp[i-1][j-i]
=>dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-i]

Could you explain to me what this line is doing dp[i][j] = (dp[i][j]+mod) % mod;
Why only the line dp[i][j] = (dp[i][j]) % mod; doesn't work?
the previous line if (j >= i) dp[i][j] -= dp[i-1][j-i]; might result in a negative value, because dp[i][j] and dp[i-1][j-i] are both modulo 109+7 and we cannot guarantee the former is larger than the later. Also, % operator in java is modulo rather than remainder, so negative % mod is negative
    public static int kInversePairs(int n, int k) {
        int mod = 1000000007;
        if (k > n*(n-1)/2 || k < 0) return 0;
        if (k == 0 || k == n*(n-1)/2) return 1;
        long[][] dp = new long[n+1][k+1];
        dp[2][0] = 1;
        dp[2][1] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= Math.min(k, i*(i-1)/2); j++) {
                dp[i][j] = dp[i][j-1] + dp[i-1][j];
                if (j >= i) dp[i][j] -= dp[i-1][j-i];//
                dp[i][j] = (dp[i][j]+mod) % mod;
            }
        }
        return (int) dp[n][k];
    }
https://discuss.leetcode.com/topic/93721/python-straightforward-with-explanation/
Let's try for a top-down dp. Suppose we know dp[n][k], the number of permutations of (1...n) with k inverse pairs.
Looking at a potential recursion for dp[n+1][k], depending on where we put the element (n+1) in our permutation, we may add 0, 1, 2, ..., n new inverse pairs. For example, if we have some permutation of 1...4, then:
5 x x x x creates 4 new inverse pairs
x 5 x x x creates 3 new inverse pairs
...
x x x x 5 creates 0 new inverse pairs
where in the above I'm representing any permutation of 1...4 with x's.
Thus, dp[n+1][k] = sum_{x=0..n} dp[n][k-x].
This dp has NK states with K/2 work, which isn't fast enough. We need to optimize further.
Let ds[n][k] = sum_{x=0..k-1} dp[n][x].
Then dp[n+1][k] = ds[n][k+1] - ds[n][k-n],
and the left hand side is ds[n+1][k+1] - ds[n+1][k].
Thus, we can perform all calculations in terms of ds.
Finally, to save space, we will only store the two most recent rows of ds, using ds and new.
In the code, we refer to -ds[n][k-n+1] instead of -ds[n][k-n] because the n being considered is actually n+1. For example, when n=2, we are appending information about ds[2][k] to new, so our formula of dp[n+1][k] = ds[n][k+1] - ds[n][k-n] is dp[2][k] = ds[1][k+1] - ds[1][k-1].
http://blog.csdn.net/u010370157/article/details/73744133
  2. 动态规划(DP)：令ans[n][k]表示n，k时满足条件的解
  3. 若k>=n, ans[n][k] = ans[n-1][k]+ans[n-1][k-1]+...+ans[n-1][k-n]；否则
      ans[n][k] = ans[n-1][k]+ans[n-1][k-1]+...+ans[n-1][0]
  4. 返回ans[n][k]
public int kInversePairs(int n, int k) {
    int mo=1000000007;
        int[][] f=new int[1002][1002];
        f[1][0]=1;
        for (int i=2;i<=n;i++)
        {
            f[i][0]=1;
            for (int j=1;j<=k;j++) 
            {
                f[i][j]=(f[i][j-1]+f[i-1][j])%mo;
                if (j>=i) f[i][j]=(f[i][j]-f[i-1][j-i]+mo)%mo;
            }
        }
        return f[n][k];
}

http://blog.h5min.cn/tumaolin94/article/details/73864080
在定义中，数组a中存在第i个和第j个元素，且i<j但a[i]>a[j]，则是一个逆序对。而K Inverse Pairs Array 则是存在k个逆序对的数组。

在这道题的计算中，需要计算前n个数字组成存在k个逆序对的数组的不同排列数量，而这与动态规划息息相关。

当我们添加第n个数字的时候，其目的是为了满足k个逆序对，那么就将有如下几种可能性：

当n处于最后一位，即本身的位置时，没有增加新的逆序对，那么就应该找到前(n-1)个数字时，出现k个逆序对的情况。
当n处于倒数第二位时，增加了一个逆序对，那么就应该找到前(n-1)个数字时，出现(k-1)个逆序对的情况。
…………………………
同理，当n处于第一位的时候，增加了(n-1)个逆序对，那么就应该找到前(n-1)个数字时，出现(k-（n-1）)个逆序对的情况。

在这里我们通常使用一个二维数组dp[n][k]来表示具体情况。表示前n个数字组成存在k个逆序对数组的排列数量，有点像最开始学习背包问题的表达。

那么我们将得到如下公式：
 dp[n][k] = dp[n-1][k]+dp[n-1][k-1]+dp[n-1][k-2]+...+dp[n-1][k+1-n+1]+dp[n-1][k-n+1]
 dp[n][k+1] = dp[n-1][k+1]+dp[n-1][k]+dp[n-1][k-1]+dp[n-1][k-2]+...+dp[n-1][k+1-n+1]

上下两个公式同时相减，可以得到下式:

dp[n][k+1] = dp[n][k]+dp[n-1][k+1]-dp[n-1][k+1-n]

上面的公式，基本上就是程序中编写的主要部分，但一定要注意的是，k-(n-1)可能会小于0，需要进行处理，当小于0时，dp[n-1][k+1-n]赋值为0即可。
public int kInversePairs(int n, int k) {  
       long[][] dp = new long[n + 1][k + 1];  
       int mod = 1000000007;  
       for (int i = 1; i <= n; i++) {  
           if(i==1){  
               dp[i][0]=1;  
               continue;  
           }else{  
               dp[i][0]=1;  
           }  
           for (int j = 1; j <= k; j++) {  
  
               dp[i][j] = dp[i][j-1] + dp[i-1][j];  
               dp[i][j] =(j>=i?(dp[i][j] - dp[i-1][j-i]):dp[i][j]);  
               dp[i][j] = (dp[i][j]+mod) % mod;  
           }  
       }  
       return (int)dp[n][k];  
   } 
对于整数n来说，它能拥有的最多逆序对是有限的，这种极端情况会出现在所有的元素都变动最大的情况下，即降序排列的数组。在这种情况下，逆序对拥有n-1+n-2……1
根据等差数列求和公式，则拥有n*(n-1)/2种情况。

在程序中，如果循环中的j值大于了n*(n-1)/2，那么这种情况是不可能出现的。所以，我们可以用j<=i*(i-1)/2来约束循环，减少无用的计算。

与此同时，题目要求要对10的九次幂+7取余，在我的操作中实际上是用了long型数组取巧了，根据正规要求，应该对每一步要求都取余。
public int kInversePairs(int n, int k) {  
    if (k > n*(n-1)/2 || k < 0) return 0;  
    if (k == 0 || k == n*(n-1)/2) return 1;  
    int[][] dp = new int[n + 1][k + 1];  
    int mod = 1000000007;  
    for (int i = 1; i <= n; i++) {  
        if(i==1){  
            dp[i][0]=1;  
            continue;  
        }else{  
            dp[i][0]=1;  
        }  
        for (int j = 1; j <= k && j <= i * (i - 1) / 2; j++) {  
  
            dp[i][j] = (dp[i][j-1] + dp[i-1][j])%mod;  
            dp[i][j] =(j>=i?(dp[i][j] +mod - dp[i-1][j-i]):dp[i][j])%mod;  
            dp[i][j] = (dp[i][j]+mod) % mod;  
        }  
    }  
    return (int)dp[n][k];  
}  

X. DFS
https://leetcode.com/articles/k-inverse-pairs-array/
Time complexity : O(n^n)O(n
​n
​​ ). The recursive function is called at most nn times. Each function call itself calls the same function n-1n−1 times.
    public int kInversePairs(int n, int k) {
        if (n == 0)
            return 0;
        if (k == 0)
            return 1;
        int inv = 0;
        for (int i = 0; i <= Math.min(k, n - 1); i++)
            inv = (inv + kInversePairs(n - 1, k - i)) % 1000000007;
        return inv;
    }
X. DFS + cache
Time complexity : O(n^2*k)O(n
​2
​​ ∗k). The function kInversePairs is called n^2n
​2
​​  times to fill the memomemo array of size nnxkk. Each function call itself takes O(n)O(n)time.
    Integer[][] memo = new Integer[1001][1001];
    public int kInversePairs(int n, int k) {
        if (n == 0)
            return 0;
        if (k == 0)
            return 1;
        if (memo[n][k] != null)
            return memo[n][k];
        int inv = 0;
        for (int i = 0; i <= Math.min(k, n - 1); i++)
            inv = (inv + kInversePairs(n - 1, k - i)) % 1000000007;
        memo[n][k] = inv;
        return inv;
    }
X. Brute Force
The most naive solution is to generate every permutation of the array consisting of numbers from 11 to nn. Then, we can find out the number of inverse pairs in every array to determine if it is equal to 1. We can find out the count of permutations with the required number of inverse pairs. But, this solution is very terrible in terms of time complexity. Thus, we move on to the better approaches directly.
Time complexity : O\big(n!*nlog(n)\big)O(n!∗nlog(n)). A total of n!n! permutations will be generated. We need O\big(nlog(n)\big)O(nlog(n)) time to find the number of inverse pairs in every such permutation, by making use of merge sort. Here, nn refers to the given integer nn.
 * @author het
 *
 */
public class LeetCode629KInversePairsArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
