package important;

import java.util.Arrays;

/**
 * LeetCode 518 - Coin Change 2
 * 
 * https://leetcode.com/problems/coin-change-2 You are given coins of different
 * denominations and a total amount of money. Write a function to compute the
 * number of combinations that make up that amount. You may assume that you have
 * infinite number of each kind of coin. Note: You can assume that 0 <= amount
 * <= 5000 1 <= coin <= 5000 the number of coins is less than 500 the answer is
 * guaranteed to fit into signed 32-bit integer Example 1: Input: amount = 5,
 * coins = [1, 2, 5] Output: 4 Explanation: there are four ways to make up the
 * amount: 5=5 5=2+2+1 5=2+1+1+1 5=1+1+1+1+1 Example 2: Input: amount = 3, coins
 * = [2] Output: 0 Explanation: the amount of 3 cannot be made up just with
 * coins of 2. Example 3:
 * 
 * 
 * Input: amount = 10, coins = [10] Output: 1
 * 
 * @author het
 * 
 * 
 *         http://www.cnblogs.com/hexsix/p/6412298.html dp[i]表示总额为i时的方案数. 转移方程:
 *         dp[i] = Σdp[i - coins[j]]; 表示 总额为i时的方案数 = 总额为i-coins[j]的方案数的加和.
 *         记得初始化dp[0] = 1; 表示总额为0时方案数为1. 2 def change(self, amount, coins): 3
 *         """ 4 :type amount: int 5 :type coins: List[int] 6 :rtype: int 7 """
 *         8 size = len(coins) 9 dp = [1] + [0] * amount 10 for i in
 *         range(size): 11 for j in range(amount): 12 if j + coins[i] <= amount:
 *         13 dp[j + coins[i]] += dp[j] 14 return dp[-1]
 *         http://bookshadow.com/weblog/2017/02/12/leetcode-coin-change-2/
 *         动态规划（Dynamic Programmin） 状态转移方程见代码 def change(self, amount, coins):
 *         """ :type amount: int :type coins: List[int] :rtype: int """ dp = [0]
 *         * (amount + 1) dp[0] = 1 for c in coins: for x in range(c, amount +
 *         1): dp[x] += dp[x - c] return dp[amount]
 * 
 *         扩展思考：将上述代码中的循环顺序对调，即为求不同硬币的排列数（Permutation） 比如用面值{1, 2,
 *         5}的硬币组成总额5元的不重复排列共9种，分别为：
 * 
 *         [1,1,1,1,1] [1,1,1,2] [1,1,2,1] [1,2,1,1] [2,1,1,1] [1,2,2] [2,1,2]
 *         [2,2,1] [5] def change(self, amount, coins): """ :type amount: int
 *         :type coins: List[int] :rtype: int """ dp = [0] * (amount + 1) dp[0]
 *         = 1 for x in range(amount + 1): for c in coins: if c > x: continue
 *         dp[x] += dp[x - c] return dp[amount]
 * 
 */

// /usr/local/mysql/bin/mysqld
public class L518 {

	public static int makeChange(int n, int denom) {
		int next_denom = 0;
		switch (denom) {
		case 25:
			next_denom = 10;
			break;
		case 10:
			next_denom = 5;
			break;
		case 5:
			next_denom = 1;
			break;
		case 1:
			return 1;
		}
  //
		int ways = 0;
		for (int i = 0; i * denom <= n; i++) {
			ways += makeChange(n - i * denom, next_denom);
		}
		return ways;
	}
	
	
	public static int ways1(int[] coins, int total) {
		if (coins == null || coins.length == 0) {
			return total == 0 ? 1 : 0;
		}
		if (total == 0)
			return 1;
		if (total < 0)
			return 0;
		Arrays.sort(coins);
		// int largestCoinValue = coins[coins.length-1];
		int[] ways = new int[total + 1];
		ways[0]=1;
		for(int i=0;i<coins.length;i+=1){
			for(int j= total;j>=coins[i];j-=1){
				 for(int k=1;k*coins[i]<=j ;k+=1){
					 
					 ways[j] += ways[j - coins[i]*k];
//					 print(ways);
//					 System.out.print("**i="+i+",j="+j+",k="+k);
//					 System.out.println();
				 }
				
			}
		}
		
		
		

		return ways[total];

	}

	private static void print(int[] ways) {
		for(int way: ways){
			System.out.print(way+",");
		}
		
	}


	// -1
	public static int ways(int[] coins, int total) {
		if (coins == null || coins.length == 0) {
			return total == 0 ? 1 : 0;
		}
		if (total == 0)
			return 1;
		if (total < 0)
			return 0;
		Arrays.sort(coins);
		// int largestCoinValue = coins[coins.length-1];
		int[][] ways = new int[total + 1][coins.length + 1];
		for (int i = 0; i <= coins.length; i += 1) {
			ways[0][i] = 1;
		}
		for (int j = 1; j <= total; j += 1) {
			ways[j][0] = 0;
		}
		for (int i = 1; i <= total; i += 1) {

			for (int j = 1; j <= coins.length; j += 1) {

				for (int k = 1; k <= j; k += 1) {
					if (i >= coins[k - 1]) {
						ways[i][j] += ways[i - coins[k - 1]][k];
					}

				}

			}
		}
		
		
//		for (int i = 1; i <= total; i += 1) {
//
//			for (int j = 1; j <= coins.length; j += 1) {
//
//				
//					if (i >= coins[j - 1]) {
//						ways[i][j] = ways[i - coins[j - 1]][j] +  ways[i][j-1];
//					}
//
//				
//
//			}
//		}

//		for (int i = 0; i <= total; i += 1) {
//			for (int j = 0; j <= coins.length; j += 1) {
//				System.out.print(ways[i][j] + ",");
//			}
//			System.out.println();
//		}

		return ways[total][coins.length];

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(ways(new int[] { 1, 2, 5 }, 5));
		System.out.println(ways(new int[] { 1, 5, 10, 25 }, 250));

		System.out.println(makeChange(25, 25));
		System.out.println(ways1(new int[] { 1, 5, 10, 25 }, 250));
		
	}

}
