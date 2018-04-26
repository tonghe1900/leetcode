package alite.leetcode.newExtra.L500;
/**
 * Leetcode 568 - Maximum Vacation Days

http://bookshadow.com/weblog/2017/04/30/leetcode-maximum-vacation-days/
LeetCode wants to give one of its best employees the option to travel among N cities to collect algorithm problems. 
But all work and no play makes Jack a dull boy, you could take vacations in some particular cities and weeks. 
Your job is to schedule the traveling to maximize the number of vacation days you could take, but there are certain
 rules and restrictions you need to follow.
Rules and restrictions:
You can only travel among N cities, represented by indexes from 0 to N-1. Initially, you are in the city indexed 0 
on Monday.
The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical),
 called flights representing the airline status from the city i to the city j. If there is no flight from the city i 
 to the city j, flights[i][j] = 0; Otherwise, flights[i][j] = 1. Also, flights[i][i] = 0 for all i.
You totally have K weeks (each week has 7 days) to travel. You can only take flights at most once per day and 
can only take flights on each week's Monday morning. Since flight time is so short, we don't consider the impact of 
flight time.
For each city, you can only have restricted vacation days in different weeks, given an N*K matrix called days
 representing this relationship. For the value of days[i][j], it represents the maximum days you could take vacation 
 in the city i in the week j.
You're given the flights matrix and days matrix, and you need to output the maximum vacation days you
 could take during K weeks.
Example 1:
Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
Output: 12
Explanation: 
Ans = 6 + 3 + 3 = 12. 

One of the best strategies is:
1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day. 
(Although you start at city 0, we could also fly to and start at other cities since it is Monday.) 
2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
3rd week : stay at city 2, and play 3 days and work 4 days.
Example 2:
Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
Output: 3
Explanation: 
Ans = 1 + 1 + 1 = 3. 

Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks. 
For each week, you only have one day to play and six days to work. 
So the maximum number of vacation days is 3.
Example 3:
Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
Output: 21
Explanation:
Ans = 7 + 7 + 7 = 21

One of the best strategies is:
1st week : stay at city 0, and play 7 days. 
2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
Note:
N and K are positive integers, which are in the range of [1, 100].
In the matrix days, all the values are integers in the range of [0, 1].
In the matrix flights, all the values are integers in the range [0, 7].
You could stay at a city beyond the number of vacation days, but you should work on the extra days, which won't be counted as vacation days.
If you fly from the city A to the city B and take the vacation on that day, the deduction towards vacation days will count towards
 the vacation days of city B in that week.
We don't consider the impact of flight hours towards the calculation of vacation days.
题目大意：
给定N个城市，K周时间。
矩阵flights描述N个城市之间是否存在航班通路。
若flights[i][j] = 1，表示i与j存在通路，否则表示不存在。特别的，flights[i][i]恒等于0。
矩阵days表示可以在某城市逗留的最长天数。
例如days[i][j] = k，表示第i个城市第j周最长可以逗留k天。
初始位于0号城市，每周可以选择一个能够到达的城市逗留（也可以留在当前城市）。
求最优策略下的最长逗留总天数。
注意：
N和K是正整数，范围[1, 100]
矩阵flights的元素范围[0, 1]
矩阵days的元素范围[0, 7]
解题思路：
动态规划（Dynamic Programming）
dp[w][c]表示第w周选择留在第c个城市可以获得的最大总收益


dp[w][c]表示第w周选择留在第c个城市可以获得的最大总收益
初始令dp[w][0] = 0, dp[w][1 .. c - 1] = -1

当dp[w][c] < 0时，表示第c个城市在第w周时还不可达。
状态转移方程：
for w in (0 .. K)
    for sc in (0 .. N)
        if dp[w][sc] < 0:
            continue
        for tc in (0 .. N)
            if sc == tc or flights[sc][tc] == 1:
                dp[w + 1][tc] = max(dp[w + 1][tc], dp[w][sc] + days[tc][w])
    def maxVacationDays(self, flights, days):
        """
        :type flights: List[List[int]]
        :type days: List[List[int]]
        :rtype: int
        """
        N, K = len(days), len(days[0])
        dp = [0] + [-1] * (N - 1)
        for w in range(K):
            ndp = [x for x in dp]
            for sc in range(N):
                if dp[sc] < 0: continue
                for tc in range(N):
                    if sc == tc or flights[sc][tc]:
                        ndp[tc] = max(ndp[tc], dp[sc] + days[tc][w])
            dp = ndp
        return max(dp)

https://discuss.leetcode.com/topic/87865/java-dfs-tle-and-dp-solutions
Solution 1. DFS. The idea is just try each possible city for every week and keep tracking the max vacation days. Time complexity O(N^K). Of course it will TLE....
    int max = 0, N = 0, K = 0;
    
    public int maxVacationDays(int[][] flights, int[][] days) {
        N = flights.length;
        K = days[0].length;
        dfs(flights, days, 0, 0, 0);
        
        return max;
    }
    
    private void dfs(int[][] f, int[][] d, int curr, int week, int sum) {
        if (week == K) {
            max = Math.max(max, sum);
            return;
        }
        
        for (int dest = 0; dest < N; dest++) {
            if (curr == dest || f[curr][dest] == 1) {
                dfs(f, d, dest, week + 1, sum + d[dest][week]);
            }
        }
    }
Solution 2. DP. dp[i][j] stands for the max vacation days we can get in week i staying in city j. It's obvious that dp[i][j] = max(dp[i - 1][k] + days[j][i]) (k = 0...N - 1, if we can go from city k to city j). Also because values of week ionly depends on week i - 1, so we can compress two dimensional dp array to one dimension. Time complexity O(K * N * N) as we can easily figure out from the 3 level of loops.

    public int maxVacationDays(int[][] flights, int[][] days) {
        int N = flights.length;
        int K = days[0].length;
        int[] dp = new int[N];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i < K; i++) {
            int[] temp = new int[N];
            Arrays.fill(temp, Integer.MIN_VALUE);
            for (int j = 0; j < N; j++) {
                for(int k = 0; k < N; k++) {
                    if (j == k || flights[k][j] == 1) {
                        temp[j] = Math.max(temp[j], dp[k] + days[j][i]);
                    }
                }
            }
            dp = temp;
        }
        
        int max = 0;
        for (int v : dp) {
            max = Math.max(max, v);
        }
        
        return max;
    }
Similar idea by using DP. The main difference is that I use DP backward.
The problem boils down to solving the following equation:
V[i, k] = max( days[i][k] + V[i, k+1], days[j][k] + V[j][k+1]), where j satisfies j != i && flights[i][j] == 1, for i = 0, ... ,N-1, k = 0, ... , K-1, and V[i, k] represent the maximum vocation days in City i begining from Week k.
public int maxVacationDays(int[][] flights, int[][] days) {
        if (days.length == 0 || flights.length == 0) return 0;
        int N = flights.length;
        int K = days[0].length;
        int[][] vocationDays = new int[N][K + 1];
        for (int k = K - 1; k >= 0; k--) {
            for (int i = 0; i < N; i++) {
                vocationDays[i][k] = days[i][k] + vocationDays[i][k + 1];
                for (int j = 0; j < N; j++) {
                    if (flights[i][j] == 1) {
                        vocationDays[i][k] = Math.max(days[j][k] + vocationDays[j][k + 1], vocationDays[i][k]);
                    }
                }
            }
        }
        return vocationDays[0][0];
    }
 * @author het
 *
 */
public class L568_important {
	//Solution 1. DFS. The idea is just try each possible city for every week and keep tracking the max vacation days. Time complexity O(N^K). Of course it will TLE....
    int max = 0, N = 0, K = 0;
    
    public int maxVacationDays(int[][] flights, int[][] days) {
        N = flights.length;
        K = days[0].length;
        dfs(flights, days, 0, 0, 0);
        
        return max;
    }
    
    private void dfs(int[][] f, int[][] d, int curr, int week, int sum) {
        if (week == K) {
            max = Math.max(max, sum);
            return;
        }
        
        for (int dest = 0; dest < N; dest++) {
            if (curr == dest || f[curr][dest] == 1) {
                dfs(f, d, dest, week + 1, sum + d[dest][week]);
            }
        }
    }
//Solution 2. DP. dp[i][j] stands for the max vacation days we can get in week i staying in 
    //city j. It's obvious that dp[i][j] = max(dp[i - 1][k] + days[j][i]) (k = 0...N - 1, if we can go from city k to city j). Also because values of week ionly depends on week i - 1, so we can compress two dimensional dp array to one dimension. Time complexity O(K * N * N) as we can easily figure out from the 3 level of loops.
//V[i, k] = max( days[i][k] + V[i, k+1], days[j][k] + V[j][k+1]),
    public int maxVacationDays(int[][] flights, int[][] days) {
        int N = flights.length;
        int K = days[0].length;
        int[] dp = new int[N];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i < K; i++) {
            int[] temp = new int[N];
            Arrays.fill(temp, Integer.MIN_VALUE);
            for (int j = 0; j < N; j++) {
                for(int k = 0; k < N; k++) {
                    if (j == k || flights[k][j] == 1) {
                        temp[j] = Math.max(temp[j], dp[k] + days[j][i]);
                    }
                }
            }
            dp = temp;
        }
        
        int max = 0;
        for (int v : dp) {
            max = Math.max(max, v);
        }
        
        return max;
    }
//Similar idea by using DP. The main difference is that I use DP backward.
//The problem boils down to solving the following equation:
//V[i, k] = max( days[i][k] + V[i, k+1], days[j][k] + V[j][k+1]), where j satisfies j != i && flights[i][j] == 1, 
    //for i = 0, ... ,N-1, k = 0, ... , K-1, and V[i, k] represent the maximum vocation days in 
    //City i begining from Week k.
    
    //V[i, k] = max( days[i][k] + V[i, k+1], days[j][k] + V[j][k+1]), where j satisfies j != i && flights[i][j] == 1, 
    
    //V[i, k] = max( days[i][k] + V[i, k+1], days[j][k] + V[j][k+1]), where j satisfies j != i && flights[i][j] == 1, 
public int maxVacationDays(int[][] flights, int[][] days) {
        if (days.length == 0 || flights.length == 0) return 0;
        int N = flights.length;
        int K = days[0].length;
        int[][] vocationDays = new int[N][K + 1];
        for (int k = K - 1; k >= 0; k--) {
            for (int i = 0; i < N; i++) {
                vocationDays[i][k] = days[i][k] + vocationDays[i][k + 1];
                for (int j = 0; j < N; j++) {
                    if (flights[i][j] == 1) {
                        vocationDays[i][k] = Math.max(days[j][k] + vocationDays[j][k + 1], vocationDays[i][k]);
                    }
                }
            }
        }
        return vocationDays[0][0];
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
