package alite.leetcode.xx2.sucess.extra;
/**
 * http://buttercola.blogspot.com/2015/09/leetcode-paint-house-ii.html
There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with 
a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, 
costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.
Note:
All costs are positive integers.
Follow up:
Could you solve it in O(nk) runtime?
Understand the problem:
This is a classic back pack problem. 
 -- Define dp[n][k], where dp[i][j] means for house i with color j the minimum cost. 
 -- Initial value: dp[0][j] = costs[0][j]. For others, dp[i][j] = Integer.MAX_VALUE;, i >= 1
 -- Transit function: dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + cost[i][j]), where k != j.
 -- Final state: Min(dp[n - 1][k]).
Time complexity: O(n*k*k).
Space complexity: O(n*k).

    public int minCostII(int[][] costs) {

        if (costs == null || costs.length == 0) {

            return 0;

        }

         

        int n = costs.length;

        int k = costs[0].length;

         

        // dp[i][j] means the min cost painting for house i, with color j

        int[][] dp = new int[n][k];

         

        // Initialization

        for (int i = 0; i < k; i++) {

            dp[0][i] = costs[0][i];

        }

         

        for (int i = 1; i < n; i++) {

            for (int j = 0; j < k; j++) {

                dp[i][j] = Integer.MAX_VALUE;

                for (int m = 0; m < k; m++) {

                    if (m != j) {

                        dp[i][j] = Math.min(dp[i - 1][m] + costs[i][j], dp[i][j]);

                    }

                }

            }

        }

         

        // Final state

        int minCost = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {

            minCost = Math.min(minCost, dp[n - 1][i]);

        }

         

        return minCost;

    }
http://segmentfault.com/a/1190000003903965
时间 O(N) 空间 O(1)
和I的思路一样，不过这里我们有K个颜色，不能简单的用Math.min方法了。如果遍历一遍颜色数组就找出除了自身外最小的颜色呢？
我们只要把最小和次小的都记录下来就行了，
这样如果和最小的是一个颜色，就加上次小的开销，反之，则加上最小的开销。
    public int minCostII(int[][] costs) {
        if(costs != null && costs.length == 0) return 0;
        int prevMin = 0, prevSec = 0, prevIdx = -1;
        for(int i = 0; i < costs.length; i++){
            int currMin = Integer.MAX_VALUE, currSec = Integer.MAX_VALUE, currIdx = -1;
            for(int j = 0; j < costs[0].length; j++){
                costs[i][j] = costs[i][j] + (prevIdx == j ? prevSec : prevMin);
                // 找出最小和次小的，最小的要记录下标，方便下一轮判断
                if(costs[i][j] < currMin){
                    currSec = currMin;
                    currMin = costs[i][j];
                    currIdx = j;
                } else if (costs[i][j] < currSec){
                    currSec = costs[i][j];
                }
            }
            prevMin = currMin;
            prevSec = currSec;
            prevIdx = currIdx;
        }
        return prevMin;
    }
A O(k) Space Solution:Since dp[i][k] only depends on dp[i-1][j], we can use a 1-D DP solution. 

    public int minCostII(int[][] costs) {

        if (costs == null || costs.length == 0) {

            return 0;

        }

         

        int n = costs.length;

        int k = costs[0].length;

         

        // dp[j] means the min cost for color j

        int[] dp1 = new int[k];

        int[] dp2 = new int[k];

         

        // Initialization

        for (int i = 0; i < k; i++) {

            dp1[i] = costs[0][i];

        }

         

        for (int i = 1; i < n; i++) {

            for (int j = 0; j < k; j++) {

                dp2[j] = Integer.MAX_VALUE;

                for (int m = 0; m < k; m++) {

                    if (m != j) {

                        dp2[j] = Math.min(dp1[m] + costs[i][j], dp2[j]);

                    }

                }

            }

             

            for (int j = 0; j < k; j++) {

                dp1[j] = dp2[j];

            }

        }

         

        // Final state

        int minCost = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {

            minCost = Math.min(minCost, dp1[i]);

        }

         

        return minCost;

    }

}
A Time O(n*K) Solution:
Use two variables min1 and min2, where min1 is the minimum value, whereas min2 is next to the minimum value. 
http://www.cnblogs.com/airwindow/p/4804011.html
f we continue follow the old coding structure, we definitely would end up with the time complexity: O(nk^2).
level 1: n is the total number of houses we have to paint. 
level 2: the first k represent for each house we need to try k colors. 
level 3: the second k was caused by the process to search the minimum cost (if not use certain color).

Apparently, if we want reach the time complexity O(nk), we have to optimize our operation at level 3. 
If we choose the color[i][j], how could we reduce the comparision between (color[i-1][0] to color[i-1][k], except color[i-1][j])
And we know there are acutally extra comparisions, since fore each color, we have to find the smallest amongst other colors. 

There must be way to solve it, Right?
Yup!!! There is a magic skill for it!!!
Let us assume, we have "min_1" and "min_2". 
min_1 : the lowest cost at previous stage.
min_2 : the 2nd lowest cost at previous stage. 

And we have the minimum costs for all colors at previous stage.
color[i-1][k]

Then, iff we decide to paint house "i" with color "j", we can compute the minimum cost of other colors at "i-1" stage through following way.
case 1: iff "color[i-1][j] == min_1", it means the min_1 actually records the minimum value of color[i-1][j] (previous color is j), we have to use min_2;
case 2: iff "color[i-1][j] != min_1", it means min_1 is not the value of color[i-1][j] (previous color is not j), we can use the min_1's color.
Note: iff "pre_min_1 == pre_min_2", it means there are two minimum costs, anyway, no matter which color is pre_min_1, we can use pre_min_2.
----------------------------------------------------------
if (dp[j] != pre_min_1 || pre_min_1 == pre_min_2) {
    dp[j] = pre_min_1 + costs[i][j];
} else{
    dp[j] = pre_min_2 + costs[i][j];
}
----------------------------------------------------------
The way to maintain "min_1" and "min_2".
for (int i = 0; i < len; i++) {
    ...
    min_1 = Integer.MAX_VALUE;
    min_2 = Integer.MAX_VALUE;
    ...
    if (dp[j] <= min_1) {
        min_2 = min_1;
        min_1 = dp[j];
    } else if (dp[j] < min_2){
        min_2 = dp[j];
    }
}

Note:
To reduce the burden of handling case, we absolutely could start from i=0, when we could assume all previous cost is 0 since we have no house.

    public int minCostII(int[][] costs) {

        if (costs == null || costs.length == 0) {

            return 0;

        }

         

        int n = costs.length;

        int k = costs[0].length;

         

        // dp[j] means the min cost for color j

        int[] dp = new int[k];

        int min1 = 0;

        int min2 = 0;


        for (int i = 0; i < n; i++) {

            int oldMin1 = min1;

            int oldMin2 = min2;

             

            min1 = Integer.MAX_VALUE;

            min2 = Integer.MAX_VALUE;

             

            for (int j = 0; j < k; j++) {

                if (dp[j] != oldMin1 || oldMin1 == oldMin2) {

                    dp[j] = oldMin1 + costs[i][j];

                } else {

                    dp[j] = oldMin2 + costs[i][j];

                }

                 

                if (min1 <= dp[j]) {

                    min2 = Math.min(min2, dp[j]);

                } else {

                    min2 = min1;

                    min1 = dp[j];

                }

            }

             

        }

         

        return min1;

    }
LIKE CODING: LeetCode [265] Paint House II

    int minCostII(vector<vector<int>>& costs) {

        int n = costs.size();

        if(n==0) return 0;

        int k = costs[0].size();

        if(k==1) return costs[0][0];


        vector<int> dp(k, 0);

        int min1, min2;


        for(int i=0; i<n; ++i){

            int min1_old = (i==0)?0:min1;

            int min2_old = (i==0)?0:min2;

            min1 = INT_MAX;

            min2 = INT_MAX;

            for(int j=0; j<k; ++j){

                if(dp[j]!=min1_old || min1_old==min2_old){

                    dp[j] = min1_old + costs[i][j];

                }else{

                    dp[j] = min2_old + costs[i][j];

                }


                if(min1<=dp[j]){

                    min2 = min(min2, dp[j]);

                }else{

                    min2 = min1;

                    min1 = dp[j];

                }

            }

        }


        return min1;

    }
https://leetcode.com/discuss/56185/java-typical-dp-solution
public int minCostII(int[][] costs) {
    if (costs.length == 0 || costs[0].length == 0) {
        return 0;
    }
    int n = costs.length, k = costs[0].length;

    //dp[i][j] indicate the optimal cost for the house i if it is painted with color j.
    int[][] dp = new int[n][k];
    for (int j = 0; j < k; j++) 
        dp[0][j] = costs[0][j];

    for (int i = 1; i < n; i++) {
        for (int j = 0; j < k; j++) {
            dp[i][j] = minCost(dp[i-1], j) + costs[i][j];
        }
    }
    return minCost(dp[n-1], -1);
}

//find the minimum cost if the current house is painted with different color except j.
//if j == -1, then find minimum cost for the current house comparing different color.
private int minCost(int[] dp, int j) {
    int min = Integer.MAX_VALUE;
    int i = 0; 
    while (i < dp.length) {
        if (j != -1 && i == j) 
            ;
        else
            min = Math.min(min, dp[i]);
        i++;
    }
    return min;
}
https://leetcode.com/discuss/54415/ac-java-solution-without-extra-space
The idea is similar to the problem Paint House I, for each house and each color, the minimum cost of painting the house with that color should be the minimum cost of painting previous houses, and make sure the previous house doesn't paint with the same color.
We can use min1 and min2 to track the indices of the 1st and 2nd smallest cost till previous house, if the current color's index is same as min1, then we have to go with min2, otherwise we can safely go with min1.
The code below modifies the value of costs[][] so we don't need extra space.
public int minCostII(int[][] costs) {
    if (costs == null || costs.length == 0) return 0;

    int n = costs.length, k = costs[0].length;
    // min1 is the index of the 1st-smallest cost till previous house
    // min2 is the index of the 2nd-smallest cost till previous house
    int min1 = -1, min2 = -1;

    for (int i = 0; i < n; i++) {
        int last1 = min1, last2 = min2;
        min1 = -1; min2 = -1;

        for (int j = 0; j < k; j++) {
            if (j != last1) {
                // current color j is different to last min1
                costs[i][j] += last1 < 0 ? 0 : costs[i - 1][last1];
            } else {
                costs[i][j] += last2 < 0 ? 0 : costs[i - 1][last2];
            }

            // find the indices of 1st and 2nd smallest cost of painting current house i
            if (min1 < 0 || costs[i][j] < costs[i][min1]) {
                min2 = min1; min1 = j;
            } else if (min2 < 0 || costs[i][j] < costs[i][min2]) {
                min2 = j;
            }
        }
    }

    return costs[n - 1][min1];
}

https://leetcode.com/discuss/oj/paint-house-ii
Read full article from LIKE CODING: LeetCode [265] Paint House II
 * @author het
 *
 */
public class LeetCode265PaintHouseII {
	public int minCostII(int[][] costs) {
        if(costs != null && costs.length == 0) return 0;
        int prevMin = 0, prevSec = 0, prevIdx = -1;
        for(int i = 0; i < costs.length; i++){
            int currMin = Integer.MAX_VALUE, currSec = Integer.MAX_VALUE, currIdx = -1;
            for(int j = 0; j < costs[0].length; j++){
                costs[i][j] = costs[i][j] + (prevIdx == j ? prevSec : prevMin);
                // 找出最小和次小的，最小的要记录下标，方便下一轮判断
                if(costs[i][j] < currMin){
                    currSec = currMin;
                    currMin = costs[i][j];
                    currIdx = j;
                } else if (costs[i][j] < currSec){
                    currSec = costs[i][j];
                }
            }
            prevMin = currMin;
            prevSec = currSec;
            prevIdx = currIdx;
        }
        return prevMin;
    }
	
	
	
	 public int minCostII2(int[][] costs) {

	        if (costs == null || costs.length == 0) {

	            return 0;

	        }

	         

	        int n = costs.length;

	        int k = costs[0].length;

	         

	        // dp[j] means the min cost for color j

	        int[] dp = new int[k];

	        int min1 = 0;

	        int min2 = 0;


	        for (int i = 0; i < n; i++) {

	            int oldMin1 = min1;

	            int oldMin2 = min2;

	             

	            min1 = Integer.MAX_VALUE;

	            min2 = Integer.MAX_VALUE;

	             

	            for (int j = 0; j < k; j++) {

	                if (dp[j] != oldMin1 || oldMin1 == oldMin2) {

	                    dp[j] = oldMin1 + costs[i][j];

	                } else {

	                    dp[j] = oldMin2 + costs[i][j];

	                }

	                 

	                if (min1 <= dp[j]) {

	                    min2 = Math.min(min2, dp[j]);

	                } else {

	                    min2 = min1;

	                    min1 = dp[j];

	                }

	            }

	             

	        }

	         

	        return min1;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
