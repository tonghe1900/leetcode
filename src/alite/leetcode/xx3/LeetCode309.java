package alite.leetcode.xx3;

import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode 309 - Best Time to Buy and Sell Stock with Cooldown

leetcode Best Time to Buy and Sell Stock with Cooldown - 细语呢喃
Say you have an array for which the ith element is the price of a given stock on day i.
Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one 
and sell one share of the stock multiple times) with the following restrictions:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
Example:
prices = [1, 2, 3, 0, 2]
maxProfit = 3

profit( i, action ) =   profit(i-1, prevAction)   // 

profit(prices.length-1)
transactions = [buy, sell, cooldown, buy, sell]
https://leetcode.com/discuss/72030/share-my-dp-solution-by-state-machine-thinking
enter image description here
There are three states, according to the action that you can take.
Hence, from there, you can now the profit at a state at time i as:
s0[i] = max(s0[i - 1], s2[i - 1]); // Stay at s0, or rest from s2
s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]); // Stay at s1, or buy from s0
s2[i] = s1[i - 1] + prices[i]; // Only one way from s1
Then, you just find the maximum of s0[n] and s2[n], since they will be the maximum profit we need (No one can buy stock and left with more profit that sell right :) )
Define base case:
s0[0] = 0; // At the start, you don't have any stock if you just rest
s1[0] = -prices[0]; // After buy, you should have -prices[0] profit. Be positive!
s2[0] = INT_MIN; // Lower base case
I think the definitions of buy[i] and sell[i] can be refined to these:
buy[i] : Maximum profit which end with buying on day i or end with buying on a day before i and takes rest until the day i since then.
sell[i] : Maximum profit which end with selling on day i or end with selling on a day before i and takes rest until the day i since then.
int maxProfit(vector<int>& prices){
    if (prices.size() <= 1) return 0;
    vector<int> s0(prices.size(), 0);
    vector<int> s1(prices.size(), 0);
    vector<int> s2(prices.size(), 0);
    s1[0] = -prices[0];
    s0[0] = 0;
    s2[0] = INT_MIN;
    for (int i = 1; i < prices.size(); i++) {
        s0[i] = max(s0[i - 1], s2[i - 1]);
        s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]);
        s2[i] = s1[i - 1] + prices[i];
    }
    return max(s0[prices.size() - 1], s2[prices.size() - 1]);
}

https://leetcode.com/discuss/71391/easiest-java-solution-with-explanations
1. Define States
To represent the decision at index i:
buy[i]: Max profit till index i. The series of transaction is ending with a buy.
sell[i]: Max profit till index i. The series of transaction is ending with a sell.
To clarify:
Till index i, the buy / sell action must happen and must be the last action. It may not happen at index i. It may happen at i - 1, i - 2, ... 0.
In the end n - 1, return sell[n - 1]. Apparently we cannot finally end up with a buy. In that case, we would rather take a rest at n - 1.
For special case no transaction at all, classify it as sell[i], so that in the end, we can still return sell[n - 1]. Thanks @alex153 @kennethliaoke @anshu2.
2. Define Recursion
buy[i]: To make a decision whether to buy at i, we either take a rest, by just using the old decision at i - 1, or sell at/before i - 2, then buy at i, We cannot sell at i - 1, then buy at i, because of cooldown.
sell[i]: To make a decision whether to sell at i, we either take a rest, by just using the old decision at i - 1, or buy at/before i - 1, then sell at i.
So we get the following formula:
buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);   
sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
3. Optimize to O(1) Space
DP solution only depending on i - 1 and i - 2 can be optimized using O(1) space.
Let b2, b1, b0 represent buy[i - 2], buy[i - 1], buy[i]
Let s2, s1, s0 represent sell[i - 2], sell[i - 1], sell[i]
Then arrays turn into Fibonacci like recursion:
b0 = Math.max(b1, s2 - prices[i]);
s0 = Math.max(s1, b1 + prices[i]);
4. Write Code in 5 Minutes
First we define the initial states at i = 0:
We can buy. The max profit at i = 0 ending with a buy is -prices[0].
We cannot sell. The max profit at i = 0 ending with a sell is 0.
http://www.cnblogs.com/grandyang/p/4997417.html
而这道题与上面这些不同之处在于加入了一个冷冻期Cooldown之说，就是如果某天卖了股票，那么第二天不能买股票，有一天的冷冻期
此题需要维护三个一维数组buy, sell，和rest。其中：
buy[i]表示在第i天之前最后一个操作是买，此时的最大收益。
sell[i]表示在第i天之前最后一个操作是卖，此时的最大收益。
rest[i]表示在第i天之前最后一个操作是冷冻期，此时的最大收益。
我们写出递推式为：
buy[i]  = max(rest[i-1] - price, buy[i-1]) 
sell[i] = max(buy[i-1] + price, sell[i-1])
rest[i] = max(sell[i-1], buy[i-1], rest[i-1])

上述递推式很好的表示了在买之前有冷冻期，买之前要卖掉之前的股票。一个小技巧是如何保证[buy, rest, buy]的情况不会出现，这是由于buy[i] <= rest[i]， 即rest[i] = max(sell[i-1], rest[i-1])，这保证了[buy, rest, buy]不会出现。
另外，由于冷冻期的存在，我们可以得出rest[i] = sell[i-1]，这样，我们可以将上面三个递推式精简到两个：
buy[i]  = max(sell[i-2] - price, buy[i-1]) 
sell[i] = max(buy[i-1] + price, sell[i-1])

我们还可以做进一步优化，由于i只依赖于i-1和i-2，所以我们可以在O(1)的空间复杂度完成算法
    int maxProfit(vector<int>& prices) {
        int buy = INT_MIN, pre_buy = 0, sell = 0, pre_sell = 0;
        for (int price : prices) {
            pre_buy = buy;
            buy = max(pre_sell - price, pre_buy);
            pre_sell = sell;
            sell = max(pre_buy + price, pre_sell);
        }
        return sell;
    }

https://leetcode.com/discuss/71354/share-my-thinking-process
The key for dp is to find the variables to represent the states and deduce the transition function.
Of course one may come up with a O(1) space solution directly, but I think it is better to be generous when you think and be greedy when you implement.
The natural states for this problem is the 3 possible transactions : buy, sell, rest. Here restmeans no transaction on that day (aka cooldown).
Then the transaction sequences can end with any of these three states.
For each of them we make an array, buy[n], sell[n] and rest[n].
buy[i] means before day i what is the maxProfit for any sequence end with buy.
sell[i] means before day i what is the maxProfit for any sequence end with sell.
rest[i] means before day i what is the maxProfit for any sequence end with rest.
Then we want to deduce the transition functions for buy sell and rest. By definition we have:
buy[i]  = max(rest[i-1]-price, buy[i-1]) 
sell[i] = max(buy[i-1]+price, sell[i-1])
rest[i] = max(sell[i-1], buy[i-1], rest[i-1])
Where price is the price of day i. All of these are very straightforward. They simply represents :
(1) We have to `rest` before we `buy` and 
(2) we have to `buy` before we `sell`
One tricky point is how do you make sure you sell before you buy, since from the equations it seems that [buy, rest, buy] is entirely possible.
Well, the answer lies within the fact that buy[i] <= rest[i] which means rest[i] = max(sell[i-1], rest[i-1]). That made sure [buy, rest, buy] is never occurred.
A further observation is that and rest[i] <= sell[i] is also true therefore
rest[i] = sell[i-1]
Substitute this in to buy[i] we now have 2 functions instead of 3:
buy[i] = max(sell[i-2]-price, buy[i-1])
sell[i] = max(buy[i-1]+price, sell[i-1])
This is better than 3, but
we can do even better
Since states of day i relies only on i-1 and i-2 we can reduce the O(n) space to O(1). And here we are at our final solution:
public int maxProfit(int[] prices) {
    int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE, prev_buy;
    for (int price : prices) {
        prev_buy = buy;
        buy = Math.max(prev_sell - price, prev_buy);
        prev_sell = sell;
        sell = Math.max(prev_buy + price, prev_sell);
    }
    return sell;
}
DP，we can create two array, buy and sell. buy[i] means we buy a stock at day i , and sell[i] means we sell a stock at day i.
so, we have twoequations :
buy[i] = max(buy[i-1] , sell[i-2] – prices[i])  // So we should use sell[i-2] means we cooldown one day.
sell[i] = max(sell[i-1], buy[i-1] + prices[i])
finally, return the max(buy[n-1] , sell[n-1])

    def maxProfit(self, prices):

        """

        :type prices: List[int]

        :rtype: int

        """

        if not prices or len(prices) < 2: return 0

        n = len(prices)

        buy, sell = [0] * n, [0] * n

        buy[0] = -prices[0]

        buy[1] = max(-prices[0], -prices[1])

        sell[1] = max(0, prices[1] - prices[0])

        for i in xrange(2, n):

            buy[i] = max(sell[i - 2] - prices[i], buy[i - 1])

            sell[i] = max(buy[i - 1] + prices[i], sell[i - 1])


        return max(buy[n - 1], sell[n - 1])
http://bookshadow.com/weblog/2015/11/24/leetcode-best-time-to-buy-and-sell-stock-with-cooldown/
https://leetcode.com/discuss/71259/share-my-3ms-java-dp-solution
https://leetcode.com/discuss/73617/7-line-java-only-consider-sell-and-cooldown
https://leetcode.com/discuss/72892/very-easy-to-understand-one-pass-solution-with-no-extra-space
Read full article from leetcode Best Time to Buy and Sell Stock with Cooldown - 细语呢喃
 * @author het
 *
 */
public class LeetCode309 {
//	prices = [1, 2, 3, 0, 2]
//			maxProfit = 3
	enum Action{
		Buy, Sell, CoolDown
	}
	public int maxProfit(int [] prices){
		if(prices == null || prices.length <2) return 0;
		Map<String, Integer> cache = new HashMap<>();
		return  Math.max((maxProfitHelper(prices,  1,  Action.Buy, true, cache) - prices[0]), maxProfitHelper(prices,  1,  Action.CoolDown, false, cache)) ;
	}
	private String createKey(int currentIndex, Action prevAction, boolean hasStock){
		return currentIndex+","+prevAction+","+hasStock;
	}
	
	private int maxProfitHelper(int[] prices, int currentIndex, Action prevAction, boolean hasStock, Map<String, Integer> cache) {
		String key = createKey(currentIndex,  prevAction,  hasStock);
		if(cache.containsKey(key)){
			return cache.get(key);
		}
		if(currentIndex == prices.length-1) {
			if(hasStock){
				return prices[currentIndex];
			}else{
				return 0;
			}
		
		}
		int maxProfit = 0;
		if(prevAction == Action.CoolDown){
			if(hasStock){
				maxProfit = Math.max(maxProfit, prices[currentIndex]+maxProfitHelper(prices, currentIndex+1, Action.Sell, false, cache));// sell 
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			}else{
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.Buy, true, cache) - prices[currentIndex]); // buy
			}
		}else if(prevAction == Action.Buy){
			maxProfit = Math.max(maxProfit, prices[currentIndex]+maxProfitHelper(prices, currentIndex+1, Action.Sell, false, cache));// sell 
			//maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			
		}else{
			maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			//maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.Buy, true, cache) - prices[currentIndex]); // buy
		}
		cache.put(key, maxProfit);
		return maxProfit;
	}
	private int maxProfitHelper1(int[] prices, int currentIndex, Action prevAction, boolean hasStock, Map<String, Integer> cache) {
		String key = createKey(currentIndex,  prevAction,  hasStock);
		if(cache.containsKey(key)){
			return cache.get(key);
		}
		if(currentIndex == prices.length-1) {
			if(hasStock){
				return prices[currentIndex];
			}else{
				return 0;
			}
		
		}
		int maxProfit = 0;
		if(prevAction == Action.CoolDown){
			if(hasStock){
				maxProfit = Math.max(maxProfit, prices[currentIndex]+maxProfitHelper(prices, currentIndex+1, Action.Sell, false, cache));// sell 
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			}else{
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
				maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.Buy, true, cache) - prices[currentIndex]); // buy
			}
		}else if(prevAction == Action.Buy){
			maxProfit = Math.max(maxProfit, prices[currentIndex]+maxProfitHelper(prices, currentIndex+1, Action.Sell, false, cache));// sell 
			maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			
		}else{
			maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.CoolDown, hasStock, cache)); // cooldown
			//maxProfit = Math.max(maxProfit, maxProfitHelper(prices, currentIndex+1, Action.Buy, true, cache) - prices[currentIndex]); // buy
		}
		cache.put(key, maxProfit);
		return maxProfit;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode309().maxProfit(new int[]{1, 2, 3, 0, 2}));

	}

}
