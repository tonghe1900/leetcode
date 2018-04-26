package alite.leetcode.xx2.sucess;
/**
 * LeetCode – Pain Fence (Java)
 
There is a fence with n posts, each post can be painted with one of the k colors. You have to paint 
all the posts such that no more than two adjacent fence posts have the same color. Return the total number
 of ways you can paint the fence.
no more than three adjgcent fence posts have the same color

n=0 0
n=1 k
n=2 k*K
n=3 k*K*K
n=4 n >=4   f(n) =(k-1) (f(n-3) + f(n-2)+f(n-1))
no more than two
n=0 0
n=1 k
n=2 k*k
n=3 
f(n) = f(n-1)*(k-1) + f(n-2)* (k-1)
f(n) = (k-1)(f(n-1) + f(n-2))



no more than three adjgcent fence posts have the same color

 f(n) =(k-1) (f(n-3) + f(n-2)+f(n-1)) 

no more than four  adjacent fence posts have the same color   f(n) = (k-1)(f(n-4)+f(n-3) + f(n-2)+f(n-1)) 

 f(n) = (k-1)(f(n-4)+f(n-3) + f(n-2)+f(n-1)) 
   the first three are all the same color
   
   the first three are not all the same colors
   
   

Java Solution

The key to solve this problem is finding this relation.

f(n) = (k-1)(f(n-1)+f(n-2))

f(n ) = k*(k-1)*f(n-3)+ k*(k-1)*

 k f(n-1)
n>3

f(n) = k* (k-1)f(n-2) + k


//  dp[3] = (k - 1) * (dp[1] + dp[2]);

Assuming there are 3 posts, if the first one and the second one has the same color, then the third one has k-1 options. 
The first and second together has k options.
If the first and the second do not have same color, the total is k * (k-1), then the third 
one has k options. Therefore, f(3) = (k-1)*k + k*(k-1)*k = (k-1)(k+k*k)
f(n) = k* (k)

// f(n ) = (n-1)*f(n-1) + (n-1)*f(n-2)
public int numWays(int n, int k) {
    int dp[] = {0, k , k*k, 0};
 
    if(n <= 2)
        return dp[n];
 
    for(int i = 2; i < n; i++){
        dp[3] = (k - 1) * (dp[1] + dp[2]);
        dp[1] = dp[2];
        dp[2] = dp[3];
    }
 
    return dp[3];
}
 * @author het
 *
 */
public class PainFenceFrustrateFrustrate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
/**
 * There is a fence with n posts, each post can be painted with one of the k colors.

You have to paint all the posts such that no more than two adjacent fence posts have the same color.

Return the total number of ways you can paint the fence.

Note:
n and k are non-negative integers.

 

这道题让我们粉刷篱笆，有n个部分需要刷，有k种颜色的油漆，规定了不能有超过两个的相同颜色涂的部分，问我们总共有多少种刷法。
那么我们首先来分析一下，如果n=0的话，说明没有需要刷的部分，直接返回0即可，如果n为1的话，那么有几种颜色，就有几种刷法，所以应该返回k，
当n=2时，k=2时，我们可以分两种情况来统计，一种是相邻部分没有相同的，一种相同部分有相同的颜色，那么对于没有相同的，对于第一个格子，
我们有k种填法，对于下一个相邻的格子，由于不能相同，所以我们只有k-1种填法。而有相同部分颜色的刷法和上一个格子的不同颜色刷法相同，
因为我们下一格的颜色和之前那个格子颜色刷成一样的即可，最后总共的刷法就是把不同和相同两个刷法加起来，参见代码如下：

n=3
k*(k-1)*k + k(k-1)

 (k-1)(k+k*K)
 n>3
 f(n) = (k-1)f(n-1)   the first and second have different colors
  +  (k-1) f(n-2)     the first and second have same colors
 =(k-1)(f(n-1)+f(n-2))

解法一：

复制代码
class Solution {
public:
    int numWays(int n, int k) {
        if (n == 0) return 0;
        int same = 0, diff = k;
        for (int i = 2; i <= n; ++i) {
            int t = diff;
            diff = (same + diff) * (k - 1);
            same = t;
        }
        return same + diff;
    }
};
复制代码
 

下面这种解法和上面那方法几乎一样，只不过多了一个变量，参见代码如下：

 

解法二：

复制代码
class Solution {
public:
    int numWays(int n, int k) {
        if (n == 0) return 0;
        int same = 0, diff = k, res = same + diff;
        for (int i = 2; i <= n; ++i) {
            same = diff;
            diff = res * (k - 1);
            res = same + diff;
        }
        return res;
    }
};
88**/
