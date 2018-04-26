package alite.leetcode.xx3;
/**
 * Leetcode 312 - Burst Balloons

leetcode Burst Balloons - 细语呢喃
Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. 
You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] 
coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
Find the maximum coins you can collect by bursting the balloons wisely.
Note:
(1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
(2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
Example:
Given [3, 1, 5, 8]
Return 167
    nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
   coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
给定n个气球。每次你可以打破一个，打破第i个，那么你会获得nums[left] * nums[i] * nums[right]个积分。 （nums[-1] = nums[n] = 1）求你可以获得的最大积分数
https://leetcode.com/discuss/72216/share-some-analysis-and-explanations
Be Naive First
When I first get this problem, it is far from dynamic programming to me. I started with the most naive idea the backtracking.
We have n balloons to burst, which mean we have n steps in the game. In the i th step we have n-i balloons to burst, i = 0~n-1. Therefore we are looking at an algorithm of O(n!). Well, it is slow, probably works for n < 12 only.
Of course this is not the point to implement it. We need to identify the redundant works we did in it and try to optimize.
Well, we can find that for any balloons left the maxCoins does not depends on the balloons already bursted. This indicate that we can use memorization (top down) or dynamic programming (bottom up) for all the cases from small numbers of balloon until n balloons. How many cases are there? For k balloons there are C(n, k) cases and for each case it need to scan the k balloons to compare. The sum is quite big still. It is better than O(n!) but worse than O(2^n).
Better idea
We than think can we apply the divide and conquer technique? After all there seems to be many self similar sub problems from the previous analysis.
Well, the nature way to divide the problem is burst one balloon and separate the balloons into 2 sub sections one on the left and one one the right. However, in this problem the left and right become adjacent and have effects on the maxCoins in the future.
Then another interesting idea come up. Which is quite often seen dp problem analysis. That is reverse thinking. Like I said the coins you get for a ballon does not depend on the balloons already burst. Therefore instead of divide the problem by the first balloon to burst, we divide the problem by the last balloon to burst.
Why is that? Because only the first and last balloons we are sure of their adjacent balloons before hand!
For the first we have nums[i-1]*nums[i]*nums[i+1] for the last we havenums[-1]*nums[i]*nums[n].
OK. Think about n balloons if i is the last one to burst, what now?
We can see that the balloons is again separated into 2 sections. But this time since the balloon i is the last balloon of all to burst, the left and right section now has well defined boundary and do not effect each other! Therefore we can do either recursive method with memoization or dp.
Final
Here comes the final solutions. Note that we put 2 balloons with 1 as boundaries and also burst all the zero balloons in the first round since they won't give any coins. The algorithm runs in O(n^3) which can be easily seen from the 3 loops in dp solution.
Java D&C with Memoization
public int maxCoins(int[] iNums) {
    int[] nums = new int[iNums.length + 2];
    int n = 1;
    for (int x : iNums) if (x > 0) nums[n++] = x;
    nums[0] = nums[n++] = 1;


    int[][] memo = new int[n][n];
    return burst(memo, nums, 0, n - 1);
}

public int burst(int[][] memo, int[] nums, int left, int right) {
    if (left + 1 == right) return 0;
    if (memo[left][right] > 0) return memo[left][right];
    int ans = 0;
    for (int i = left + 1; i < right; ++i)
        ans = Math.max(ans, nums[left] * nums[i] * nums[right] 
        + burst(memo, nums, left, i) + burst(memo, nums, i, right));
    memo[left][right] = ans;
    return ans;
}
Java DP
public int maxCoins(int[] iNums) {
    int[] nums = new int[iNums.length + 2];
    int n = 1;
    for (int x : iNums) if (x > 0) nums[n++] = x;
    nums[0] = nums[n++] = 1;


    int[][] dp = new int[n][n];
    for (int k = 2; k < n; ++k)
        for (int left = 0; left < n - k; ++left) {
            int right = left + k;
            for (int i = left + 1; i < right; ++i)
                dp[left][right] = Math.max(dp[left][right], 
                nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
        }

    return dp[0][n - 1];
}
// 154 ms
http://www.hrwhisper.me/leetcode-burst-balloons/
一开始想dp[i][j] 为第 i 天打破第j 个气球，然后枚举上一轮打破的为第k个气球 dp[i][j] =max( dp[i – 1][k] + left * nums[j] * right) （当然要记录都打了哪些O） 复杂度 O(n^3) 然而TLE (python)
看了discuss是dp[i][j]为打破的气球为i~j之间。
我们可以想象：最后的剩下一个气球为i的时候，可以获得的分数为：nums[-1]*nums[i]*nums[n].
那么介于i,j之间的x，有： dp[i][j] = max(dp[i][j], dp[i][x – 1] + nums[i – 1] * nums[x] * nums[j + 1] + dp[x + 1][j]);

    public int maxCoins(int[] iNums) {

        int n = iNums.length;

        int[] nums = new int[n + 2];

        for (int i = 0; i < n; i++) nums[i + 1] = iNums[i];

        nums[0] = nums[n + 1] = 1;

        int[][] dp = new int[n + 2][n + 2];

        for (int k = 1; k <= n; k++) {

            for (int i = 1; i <= n - k + 1; i++) {

                int j = i + k - 1;

                for (int x = i; x <= j; x++) {

                    dp[i][j] = Math.max(dp[i][j], dp[i][x - 1] + nums[i - 1] * nums[x] * nums[j + 1] + dp[x + 1][j]);

                }

            }

        }

        return dp[1][n];

    }
http://www.cnblogs.com/grandyang/p/5006441.html
像这种求极值问题，我们一般都要考虑用动态规划Dynamic Programming来做，我们维护一个二维动态数组dp，其中dp[i][j]表示打爆区间[i,j]中的所有气球能得到的最多金币。题目中说明了边界情况，当气球周围没有气球的时候，旁边的数字按1算，这样我们可以在原数组两边各填充一个1，这样方便于计算。这道题的最难点就是找递归式，如下所示：
dp[i][j] = max(dp[i][j], nums[i - 1]*nums[k]*nums[j + 1] + dp[i][k - 1] + dp[k + 1][j])                 ( i ≤ k ≤ j )
有了递推式，我们可以写代码，我们其实只是更新了dp数组的右上三角区域，我们最终要返回的值存在dp[1][n]中，其中n是两端添加1之前数组nums的个数。
http://yuancrackcode.com/2015/11/29/burst-balloons/

    public int maxCoins(int[] nums) {

        if (nums == null || nums.length == 0) return 0;

        

        int[] ballons = new int[nums.length + 2];

        int len = 1;

        for (int num : nums) {

            if (num > 0) {

                ballons[len++] = num;

            }

        }

        ballons[0] = ballons[len++] = 1;

        int[][] coins = new int[len][len];//len此时是原长度+2

        

        for (int k = 2; k < len; k++) {

            for (int left = 0; left + k < len; left ++) {//left一直起始于0

                int right = left + k;

                for (int i = left + 1; i < right; i ++) {//i必须大于0，因为ballons[0]是人为加进去的1

                    coins[left][right] = Math.max(coins[left][right], ballons[left] * ballons[i] * ballons[right] + coins[left][i] + coins[i][right]);

                }

            }

        }

        return coins[0][len-1];

    }

https://leetcode.com/discuss/72215/java-dp-solution-with-detailed-explanation-o-n-3
public int maxCoins(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int[][] dp = new int[nums.length][nums.length];
    for (int len = 1; len <= nums.length; len++) {
        for (int start = 0; start <= nums.length - len; start++) {
            int end = start + len - 1;
            for (int i = start; i <= end; i++) {
                int coins = nums[i] * getValue(nums, start - 1) * getValue(nums, end + 1);
                coins += i != start ? dp[start][i - 1] : 0; // If not first one, we can add subrange on its left.
                coins += i != end ? dp[i + 1][end] : 0; // If not last one, we can add subrange on its right
                dp[start][end] = Math.max(dp[start][end], coins);
            }
        }
    }
    return dp[0][nums.length - 1];
}

private int getValue(int[] nums, int i) { // Deal with num[-1] and num[num.length]
    if (i < 0 || i >= nums.length) {
        return 1;
    }
    return nums[i];
}

https://leetcode.com/discuss/89797/java-solution-with-explanations
Read full article from leetcode Burst Balloons - 细语呢喃

at 12:35 AM 
Email This
BlogThis!
Share to Twitter
Share to Facebook
Share to Pinterest

Labels: Dynamic Programming, LeetCode, to-do
 * @author het
 *
 */

//Given [3, 1, 5, 8]
//Return 167
//    nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
//   coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
//O(n^3)
public class LeetCode312 {
    public int maxCoins(int [] input){
    	if(input == null || input.length == 0 ) return 0;
    	if(input.length == 1) return input[0];
    	return maxCoinsHelper1(input, new Integer[input.length][input.length], 0, input.length-1);
    }
    
    private int maxCoinsHelper(int [] input, Integer [][] cache, int begin, int end){
    	if(begin > end) return 0;
    	if(cache[begin][end] != null){
    		return cache[begin][end];
    	}
    	
//    	if(begin == end){
//    		
//    		int coins = getCoins(input, begin);
//    		cache[begin][end] = coins;
//			return coins;
//    	}
    	
    	int maxCoins = 0;
    	for(int i= begin; i<=end; i+=1){
    		maxCoins= Math.max(maxCoins, maxCoinsHelper(input, cache, begin, i-1) + maxCoinsHelper(input, cache,i+1, end)+ getCoins(input, begin, end, i));
    	}
    	cache[begin][end] = maxCoins;
    	return maxCoins;
    }
    
    private int maxCoinsHelper1(int [] input, Integer [][] cache, int begin, int end){
    	if(begin > end) return 0;
    	if(cache[begin][end] != null){
    		return cache[begin][end];
    	}
    	
//    	if(begin == end){
//    		
//    		int coins = getCoins(input, begin);
//    		cache[begin][end] = coins;
//			return coins;
//    	}
    	
    	int maxCoins = 0;
    	for(int i= begin; i<=end; i+=1){// coin i is the last one to burst
    		maxCoins= Math.max(maxCoins, maxCoinsHelper1(input, cache, begin, i-1) + maxCoinsHelper1(input, cache, i+1, end)+ getCoins(input, begin, end, i));
    	}
    	cache[begin][end] = maxCoins;
    	return maxCoins;
    }

	private int getCoins(int[] input, int begin, int end, int choosen) {
		int coins = (begin ==0? 1 : input[begin-1])*input[choosen]*(end == input.length-1 ? 1: input[end+1]);
		return coins;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode312().maxCoins(new int[]{3, 1, 5, 8}));

	}

}
