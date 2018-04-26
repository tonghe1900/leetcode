package Leetcode600x;
/**
 *  [Leetcode] 656. Coin Path 解题报告
标签： Leetcode 解题报告 Dynamic Programming
2018年01月26日 20:04:37100人阅读 评论(0) 收藏  举报
 分类： IT公司面试习题（727）  
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/magicbean2/article/details/79175717

题目：

Given an array A (index starts at 1) consisting of N integers: A1, A2, ..., AN and an integer B. The integer B denotes that from any place (suppose the index is i) in the array A, you can jump to any one of the place in the array A indexed i+1, i+2, …, i+B if this place can be jumped to. Also, if you step on the index i, you have to pay Ai coins. If Ai is -1, it means you can’t jump to the place indexed i in the array.

Now, you start from the place indexed 1 in the array A, and your aim is to reach the place indexed N using the minimum coins. You need to return the path of indexes (starting from 1 to N) in the array you should take to get to the place indexed N using minimum coins.

If there are multiple paths with the same cost, return the lexicographically smallest such path.

If it's not possible to reach the place indexed N then you need to return an empty array.

Example 1:

Input: [1,2,4,-1,2], 2
Output: [1,3,5]
Example 2:

Input: [1,2,4,-1,2], 1
Output: []
Note:

Path Pa1, Pa2, ..., Pan is lexicographically smaller than Pb1, Pb2, ..., Pbm, if and only if at the first i where Pai and Pbi differ, Pai < Pbi; when no such i exists, then n < m.
A1 >= 0. A2, ..., AN (if exist) will in the range of [-1, 100].
Length of A is in the range of [1, 1000].
B is in the range of [1, 100].
思路：

我们定义dp[i]表示跳到第i个位置所需要花费的最小硬币数，则递推公式为：dp[i] = min(dp[j] + A[i])，i - j <= B，并且A[i] != -1。不过结果要求返回路径，所以我们还需要定义一个和dp对应的数组next，其中next[i]表示在最优路径下，从i位置开始的下一步将跳向哪里。

由于题目中要求返回字典序最小的路径，所以我们采用从后往前推导的方法，并且在第二层循环中，让j从小往大循环，这样最终记录下来的路径就一定是字典序最小的（可以采用数学归纳法进行证明）。

代码：

class Solution {
public:
    vector<int> cheapestJump(vector<int>& A, int B) {
        if (A.size() == 0 || A.back() == -1) {
            return {};
        }
        vector<int> ret;
        int n = A.size();
        vector<int> dp(n, INT_MAX), next(n, -1);
        dp[n - 1] = A[n - 1];
        for (int i = n - 2; i >= 0; --i) {      // work backwards
            if (A[i] == -1) {
                continue;
            }
            for (int j = i + 1; j <= min(i + B, n - 1); ++j) {
                if (dp[j] == INT_MAX) {
                    continue;
                }
                if (dp[i] > A[i] + dp[j]) {     // i to j
                    dp[i] = dp[j] + A[i];
                    next[i] = j;
                }
            }
        }
        if (dp[0] == INT_MAX) {                 // no solution
            return ret;
        }
        int k = 0;
        while (k != -1) {
            ret.push_back(k + 1);
            k = next[k];
        }
        return ret;
    }
};

 * @author tonghe
 *
 */
public class Leetcode656 {

}
