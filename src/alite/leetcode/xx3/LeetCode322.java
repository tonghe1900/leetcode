package alite.leetcode.xx3;
/**
 * Leetcode 322 - Coin Change


You are given coins of different denominations and a total amount of money amount. 
Write a function to compute the fewest number of coins that you need to make up that amount. 
If that amount of money cannot be made up by any combination of the coins, return -1.
Example 1:
coins = [1, 2, 5], amount = 11
return 3 (11 = 5 + 5 + 1)
Example 2:
coins = [2], amount = 3
return -1.
Note:
You may assume that you have an infinite number of each kind of coin.
X. DP - pruning
You can in fact, Improve it by early pruning if you sorted the coins array beforehand. so that in the iterative solution
if the sum < coin you break from the inner loop.
Early pruning is a nice idea if coins are spread out in a relatively large range. For example, if coins=[99, 50, 11, 4, 1], sorting does save some work when amount-sum becomes less than 100.
public int coinChange(int[] coins, int amount) {
    if(amount<1) return 0;
    int L = coins.length;
    int[] dp = new int[amount+1];
    int sum = 0;

    // Modification.
    Arrays.sort(coins);

    while(++sum<=amount) {
        int min = -1;
        for(int coin : coins) {
             // Modification
            if(sum < coin) break;
            if(dp[sum-coin]!=-1) {
                int temp = dp[sum-coin]+1;
                min = min<0 ? temp : (temp < min ? temp : min);
            }
        }
        dp[sum] = min;
    }
    return dp[amount];
}


X. DP - bottom up
https://segmentfault.com/a/1190000004212264
    public int coinChange(int[] coins, int amount) {
        // 无效输入的处理
        if (amount == 0)
            return 0;
        if (coins == null || coins.length == 0)
            return -1;
            
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j] && dp[i - coins[j]] != -1)
                    min = Math.min(min, dp[i - coins[j]] + 1);
            }
            
            // 根据min的值判断是否能兑换
            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
        }
        return dp[amount];
    }
http://www.jyuan92.com/blog/leetcode-coin-change/



It uses long[] to avoid overflow.
Function: dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1)
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17


public int coinChange(int[] coins, int amount) {


    if (null == coins || coins.length == 0) {


        return -1;


    }


    int[] dp = new int[amount + 1];


    Arrays.fill(dp, Integer.MAX_VALUE);


    dp[0] = 0;


    for (int i = 0; i <= amount; i++) {


        for (int coin : coins) {


            if ((i > coin && dp[i - coin] != Integer.MAX_VALUE) || i == coin) {


                dp[i] = Math.min(dp[i], dp[i - coin] + 1);


            }


        }


    }


    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];


}



Function: dp[i + coins[j]] = Math.min(dp[i + coins[j]], dp[i] + 1)

public int coinChange(int[] coins, int amount) {

    if (null == coins || coins.length == 0) {

        return -1;

    }

    long[] dp = new long[amount + 1];

    Arrays.fill(dp, Integer.MAX_VALUE);

    dp[0] = 0;

    for (int i = 0; i <= amount; i++) {

        for (int coin : coins) {

            if (i + coin <= amount) {

                dp[i + coin] = Math.min(dp[i + coin], dp[i] + 1);

            }

        }

    }

    return dp[amount] == Integer.MAX_VALUE ? -1 : (int) dp[amount];

}
http://buttercola.blogspot.com/2016/01/leetcode-coin-change.html
-- dp[n + 1][amount + 1], where dp[i][j] means the minimum number of coins in the first i coins for which the sum of amount equal to j. 
-- Initialization: 
dp[0][0] = 0;
dp[0][j] = Integer.MAX_VALUE, means there is no way to fulfill the amount j with no coins 
dp[i][0] = 0, meaning the number of coins is 0 to meet the total amount 0. 
For others, the initial state is Integer.MAX_VALUE. 

    public int coinChange(int[] coins, int amount) {

        if (coins == null || coins.length == 0 || amount <= 0) {

            return 0;

        }

         

        int n = coins.length;

        int[][] dp = new int[n + 1][amount + 1];

         

        // initilization

        for (int i = 1; i <= amount; i++) {

            dp[0][i] = Integer.MAX_VALUE;

        }

         

        for (int i = 1; i <= n; i++) {

            for (int j = 0; j <= amount; j++) {

                dp[i][j] = Integer.MAX_VALUE;

                for (int k = 0; k * coins[i - 1] <= j; k++) {

                    if (dp[i - 1][j - k * coins[i - 1]] != Integer.MAX_VALUE) {

                        dp[i][j] = Math.min(dp[i][j], 

                          dp[i - 1][j - k * coins[i - 1]] + k);

                    }

                }

            }

        }

         

        if (dp[n][amount] == Integer.MAX_VALUE) {

            return -1;

        } else {

            return dp[n][amount];

        }

    }
Since dp[i] only depends on dp[i - 1], we can simply reduce the space complexity to O(amount). 

    public int coinChange(int[] coins, int amount) {

        if (coins == null || coins.length == 0 || amount <= 0) {

            return 0;

        }

         

        int[] dp = new int[amount + 1];

        for (int i = 1; i <= amount; i++) {

            dp[i] = Integer.MAX_VALUE;

        }

         

        for (int i = 1; i <= amount; i++) {

            for (int j = 0; j < coins.length; j++) {

                if (coins[j] <= i) {

                    if (dp[i - coins[j]] != Integer.MAX_VALUE) {

                        dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);

                    }

                }

            }

        }

         

        if (dp[amount] == Integer.MAX_VALUE) {

            return -1;

        } else {

            return dp[amount];

        }

    }
http://www.bo-song.com/leetcode-coin-change/

    int coinChange(vector<int>& coins, int amount) {

        int dp[amount + 1];

        for(int i = 0; i < amount + 1; i++){

            dp[i] = amount + 1; //\\

        }

        dp[0] = 0;

        for(int i = 1; i < amount + 1; i++){

            for(int j = 0; j < coins.size(); j++){

                if(i - coins[j] < 0) continue;

                dp[i] = min(dp[i - coins[j]] + 1, dp[i]);    

            }

        }

        return dp[amount] == amount + 1? -1: dp[amount];

        

    }
http://www.hrwhisper.me/leetcode-coin-change/
dp，设dp[i] 为兑换目标i最少的硬币数。
则有：dp[i + coins[j] ] = min(dp[i + coins[j] ] , dp[i] + 1）
说白了就是用当前的硬币能组合成啥，取最小。

    public int coinChange(int[] coins, int amount) {

        int dp[] = new int[amount + 1];

        final int INF = 0x7ffffffe;

        for (int i = 1; i <= amount; i++) dp[i] = INF;

        for (int i = 0; i <= amount; i++) {

            for (int j = 0; j < coins.length; j++) {

                if (i + coins[j] <= amount)

                    dp[i + coins[j]] = Math.min(dp[i + coins[j]], dp[i] + 1);

            }

        }

        return dp[amount] == INF ? -1 : dp[amount];

    }
https://leetcode.com/discuss/76208/java-dp-solution-sorting-coins-array-to-speed-up
sorting coins array into ascending order so that we can early termination.
public int coinChange(int[] coins, int amount) {
     // DP, time complexity: O(ClogC + amount / smallest_coin), C = |coins|
     // space complexity: O(amount)
     // opt[i] = min_j(opt[j]) + 1 if j - i is a denomination in coins
     Arrays.sort(coins);
     int[] opt = new int[amount + 1];
     opt[0] = 0;
     for (int i = 1; i <= amount; i++) {
         opt[i] = Integer.MAX_VALUE;
         for (int c : coins) {
             if (i >= c) {
                 if (opt[i - c] != Integer.MAX_VALUE) {
                     opt[i] = Math.min(opt[i], opt[i - c] + 1);
                 }
             } else {
                 break;
             }
         }
     }
     return opt[amount] == Integer.MAX_VALUE ? -1 : opt[amount];
 }
X. DP - top down, memorization
The idea is very classic dynamic programming: think of the last step we take. Suppose we have already found out the best way to sum up to amount a, then for the last step, we can choose any coin type which gives us a remainder r where r = a-coins[i] for all i's. For every remainder, go through exactly the same process as before until either the remainder is 0 or less than 0 (meaning not a valid solution). With this idea, the only remaining detail is to store the minimum number of coins needed to sum up to r so that we don't need to recompute it over and over again.
public int coinChange(int[] coins, int amount) {
    if(amount<1) return 0;
    return helper(coins, amount, new int[amount]);
}

private int helper(int[] coins, int rem, int[] count) { 
// rem: remaining coins after the last step; 
// count[rem]: minimum number of coins to sum up to rem
    if(rem<0) return -1; // not valid
    if(rem==0) return 0; // completed
    if(count[rem-1] != 0) return count[rem-1]; // already computed, so reuse
    int min = Integer.MAX_VALUE;
    for(int coin : coins) {
        int res = helper(coins, rem-coin, count);
        if(res>=0 && res < min)
            min = 1+res;
    }
    count[rem-1] = (min==Integer.MAX_VALUE) ? -1 : min;
    return count[rem-1];
}
https://leetcode.com/discuss/83289/understand-recursive-solution-using-java-with-explanations
Here we tackle the problem recursively, for each coin, if I take that coin into account, then the fewest number of coins we can get is 1+coinChange(amount-thatcoinvalue). So for all the coins, we return the smallest number as min(1+coinChange(amount-coin1value), 1+coinChange(amount-coin2value, ......).
As we can see it is recursive, the solution is as below, this solution of upper time complexity O(c^n) where c is number of different denominations and n is the amount given, which is exponential:
public class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount==0)
            return 0;
        int n = amount+1;
        for(int coin : coins) {
            int curr = 0; // ==Integer.MAX_VALUE
            if (amount >= coin) {
                int next = coinChange(coins, amount-coin);
                if(next >= 0)
                    curr = 1+next;
            }
            if(curr > 0)
                n = Math.min(n,curr);
        }
        int finalCount = (n==amount+1) ? -1 : n;
        return finalCount;
    }
}
Then we observed that this algorithm may compute coinChange of same amount for many times, which are kind of duplicate, if we can store "amount->fewestcoincount" into hashtble, then we don't need to recompute again. Actually, this is DP (dynamic programming), aka. Memorization. So the final solution is to add hashtbl implementation to the previous solution and problem solved, this is of upper time complexity O(n^c), which is polynomial:
public class Solution {
    Map<Integer,Integer> amountDict = new HashMap<Integer,Integer>();
    public int coinChange(int[] coins, int amount) {
        if(amount==0)
            return 0;
        if(amountDict.containsKey(amount))
            return amountDict.get(amount);
        int n = amount+1;
        for(int coin : coins) {
            int curr = 0;
            if (amount >= coin) {
                int next = coinChange(coins, amount-coin);
                if(next >= 0)
                    curr = 1+next;
            }
            if(curr > 0)
                n = Math.min(n,curr);
        }
        int finalCount = (n==amount+1) ? -1 : n;
        amountDict.put(amount,finalCount);
        return finalCount;
    }
}
X. BFS
http://www.programcreek.com/2015/04/leetcode-coin-change-java/
Most dynamic programming problems can be solved by using BFS.
We can view this problem as going to a target position with steps that are allows in the arraycoins. We maintain two queues: one of the amount so far and the other for the minimal steps. The time is too much because of the contains method take n and total time is O(n^3).
public int coinChange(int[] coins, int amount) {
 if (amount == 0)
  return 0;
 
 LinkedList<Integer> amountQueue = new LinkedList<Integer>();
 LinkedList<Integer> stepQueue = new LinkedList<Integer>();
 
 // to get 0, 0 step is required
 amountQueue.offer(0);
 stepQueue.offer(0);
 
 while (amountQueue.size() > 0) {
  int temp = amountQueue.poll();
  int step = stepQueue.poll();
 
  if (temp == amount)
   return step;
 
  for (int coin : coins) {
   if (temp > amount) {
    continue;
   } else {
    if (!amountQueue.contains(temp + coin)) {//use visited set?
     amountQueue.offer(temp + coin);
     stepQueue.offer(step + 1);
    }
   }
  }
 }
 
 return -1;
}
https://leetcode.com/discuss/76846/java-bfs-easy-to-understand
https://leetcode.com/discuss/76432/fast-python-bfs-solution
This solution is inspired by the BFS solution for problem Perfect Square. Since it is to find the least coin solution (like a shortest path from 0 to amount), using BFS gives results much faster than DP.
def coinChange(self, coins, amount):
    """
    :type coins: List[int]
    :type amount: int
    :rtype: int
    """
    if amount == 0:
        return 0
    value1 = [0]
    value2 = []
    nc =  0
    visited = [False]*(amount+1)
    visited[0] = True
    while value1:
        nc += 1
        for v in value1:
            for coin in coins:
                newval = v + coin
                if newval == amount:
                    return nc
                elif newval > amount:
                    continue
                elif not visited[newval]:
                    visited[newval] = True
                    value2.append(newval)
        value1, value2 = value2, []
    return -1
http://bookshadow.com/weblog/2015/12/27/leetcode-coin-change/
将问题转化为求X轴0点到坐标点amount的最短距离（每次向前行进的合法距离为coin的面值）
    def coinChange(self, coins, amount):
        """
        :type coins: List[int]
        :type amount: int
        :rtype: int
        """
        steps = collections.defaultdict(int)
        queue = collections.deque([0])
        steps[0] = 0
        while queue:
            front = queue.popleft()
            level = steps[front]
            if front == amount:
                return level
            for c in coins:
                if front + c > amount:
                    continue
                if front + c not in steps:
                    queue += front + c,
                    steps[front + c] = level + 1
        return -1
https://leetcode.com/discuss/76846/java-bfs-easy-to-understand
Map<Integer,Integer> amountDict = new HashMap<Integer,Integer>();
public int coinChange(int[] coins, int amount) {
    if(amount==0)
        return 0;
    if(amountDict.containsKey(amount))
        return amountDict.get(amount);
    int n = amount+1;
    for(int coin : coins) {
        int curr = 0;
        if (amount >= coin) {
            int next = coinChange(coins, amount-coin);
            if(next >= 0)
                curr = 1+next;
        }
        if(curr > 0)
            n = Math.min(n,curr);
    }
    int finalCount = (n==amount+1) ? -1 : n;
    amountDict.put(amount,finalCount);
    return finalCount;
}

http://bookshadow.com/weblog/2015/12/27/leetcode-coin-change/
    def coinChange(self, coins, amount):
        """
        :type coins: List[int]
        :type amount: int
        :rtype: int
        """
        steps = collections.defaultdict(int)
        queue = collections.deque([0])
        steps[0] = 0
        while queue:
            front = queue.popleft()
            level = steps[front]
            if front == amount:
                return level
            for c in coins:
                if front + c > amount:
                    continue
                if front + c not in steps:
                    queue += front + c,
                    steps[front + c] = level + 1
        return -1

X. DFS
http://www.jianshu.com/p/74697c4b11bc
采用了 backtracing 的思想，复杂度达到了惊人的 O(2 ^ n)
采用backtracking 的方法，为什么复杂度会那么高？
因为里面有大量的重复的case，一次次地被再次访问。
而使用了 dp， 将已经完成的结果全部存起来，下次要用的时候，直接访问，就可以了。不用再次推导。这也是dp的意义。
    private int min = -1;
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0)
            return -1;
        else if (amount == 0)
            return 0;
        Arrays.sort(coins);
        int ret = helper(coins, amount, coins.length - 1, 0);
        return (ret == Integer.MAX_VALUE ? -1 : ret);
    }

    private int helper(int[] coins, int amount, int end, int counter) {
        if (amount == 0)
            return counter;
        else if (amount < 0)
            return Integer.MAX_VALUE;
        else if (end < 0)
            return Integer.MAX_VALUE;
        int contain = helper(coins, amount - coins[end], end, counter + 1); // contain coins[end]
        int miss = helper(coins, amount, end - 1, counter); // miss coins[end]
        return Math.min(contain, miss);
    }
https://leetcode.com/discuss/83289/understand-recursive-solution-using-java-with-explanations
As we can see it is recursive, the solution is as below, this solution of upper time complexity O(c^n) where c is number of different denominations and n is the amount given, which is exponential:
public class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount==0)
            return 0;
        int n = amount+1;
        for(int coin : coins) {
            int curr = 0;
            if (amount >= coin) {
                int next = coinChange(coins, amount-coin);
                if(next >= 0)
                    curr = 1+next;
            }
            if(curr > 0)
                n = Math.min(n,curr);
        }
        int finalCount = (n==amount+1) ? -1 : n;
        return finalCount;
    }
}
https://psyking841.wordpress.com/2014/11/01/coin-change-problem/

public static void getAllChange(int target, int[] S, int[][] dp, ArrayList<Integer> tmpres, ArrayList<ArrayList<Integer>> res){

        //no way to give change

        if(S.length == 0) return;

        //if we found a way to give change, put it in res

        if(target<=0){

            ArrayList<Integer> t = new ArrayList<Integer>(tmpres);

            res.add(t);

            return;

        }

        int m = S.length;

        //if we can give change

        if(target>=S[m-1]){

            for(int i=0;i<2;i++){

                //use S[m-1]

                if(i==0){

                    tmpres.add(S[m-1]);

                    getAllChange(target-S[m-1], S, dp, tmpres, res);

                //not use S[m-1]

                }else{

                    tmpres.remove(tmpres.size()-1);

                    getAllChange(target, Arrays.copyOfRange(S, 0, S.length-1), dp, tmpres, res);

                }

            }

        }else{

            getAllChange(target, Arrays.copyOfRange(S, 0, S.length-1), dp, tmpres, res);

        }

    }
https://leetcode.com/discuss/76307/java-recursive-solution-3ms
    int minCount = Integer.MAX_VALUE;

    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        count(amount, coins.length - 1, coins, 0);
        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }

    void count(int amount, int index, int[] coins, int count) {
        if(amount % coins[index] == 0) {
            int newCount = count + amount / coins[index];
            if(newCount < minCount)
                minCount = newCount;
        }

        if(index == 0)
            return;

        for (int i = amount / coins[index]; i >= 0; i--) {
            int newAmount = amount - i * coins[index];
            int newCount = count + i;

            int nextCoin = coins[index-1];
            if(newCount + (newAmount + nextCoin -1) / nextCoin >= minCount)
                break;

            count(newAmount, index - 1, coins, newCount);
        }
    }
这道题要求是返回硬币个数，显然Follow up可以是在最少硬币的情况下，返回具体的硬币值
我能想到的比较直接的方法就是用一个map对于每个可以组合的amount用一个List存对应的硬币组合，然后同样是DP的方法找到最小硬币组合，更新组合。
 * @author het
 *
 */
public class LeetCode322 {
	 public int coinChange(int[] coins, int amount) {
	        // 无效输入的处理
	        if (amount == 0)
	            return 0;
	        if (coins == null || coins.length == 0)
	            return -1;
	            
	        int[] dp = new int[amount + 1];
	        for (int i = 1; i <= amount; i++) {
	            int min = Integer.MAX_VALUE;
	            for (int j = 0; j < coins.length; j++) {
	                if (i >= coins[j] && dp[i - coins[j]] != -1)
	                    min = Math.min(min, dp[i - coins[j]] + 1);
	            }
	            
	            // 根据min的值判断是否能兑换
	            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
	        }
	        return dp[amount];
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
