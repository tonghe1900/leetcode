package alite.leetcode.xx2.sucess.before;
/**
 * http://sbzhouhao.net/LeetCode/LeetCode-Paint-House.html
F面经：painting house - neverlandly - 博客园
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. 
The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent 
houses have the same color.
The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, 
costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green,
 and so on... Find the minimum cost to paint all houses.
Note:
All costs are positive integers.
http://segmentfault.com/a/1190000003903965
时间 O(N) 空间 O(1)
total[h+1][c]

total[h1][c] = costs[h1-1][differentColor] + costs[h1-1][c]    differentColor!=c
直到房子i，其最小的涂色开销是直到房子i-1的最小涂色开销，加上房子i本身的涂色开销。但是房子i的涂色方式需要根据房子i-1的涂色方式来确定，所以我们对房子i-1要记录涂三种颜色分别不同的开销，这样房子i在涂色的时候，我们就知道三种颜色各自的最小开销是多少了。我们在原数组上修改，可以做到不用空间。
    public int minCost(int[][] costs) {
        if(costs != null && costs.length == 0) return 0;
        for(int i = 1; i < costs.length; i++){
            // 涂第一种颜色的话，上一个房子就不能涂第一种颜色，这样我们要在上一个房子的第二和第三个颜色的最小开销中找最小的那个加上
            costs[i][0] = costs[i][0] + Math.min(costs[i - 1][1], costs[i - 1][2]);
            // 涂第二或者第三种颜色同理
            costs[i][1] = costs[i][1] + Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] = costs[i][2] + Math.min(costs[i - 1][0], costs[i - 1][1]);
        }
        // 返回涂三种颜色中开销最小的那个
        return Math.min(costs[costs.length - 1][0], Math.min(costs[costs.length - 1][1], costs[costs.length - 1][2]));
    }

http://buttercola.blogspot.com/2015/08/leetcode-paint-house.html
houses.http://likesky3.iteye.com/blog/2235760
我习惯于从目的倒推中间过程，最后一家house选用什么颜色能使总花费最小呢，假设知道前面第 N - 1家使用不同颜色时的总花费，那问题就解决了，因此中间计算需要存储的信息dp[i][j] 表示第 i 家选用 颜色 j 时 0 - i家house所需的最小花费，递推式见代码。 
X. Optimized space.
    public int minCost(int[][] costs) {  
        if (costs == null || costs.length == 0 || costs[0].length == 0)  
            return 0;  
        int N = costs.length;  
        int[][] dp = new int[2][3];  
        int prev = 0, curr = 1;  
        for (int i = 0; i < N; i++) {  
            prev = curr;  
            curr = 1 - curr;  
            dp[curr][0] = Math.min(dp[prev][1], dp[prev][2]) + costs[i][0];  
            dp[curr][1] = Math.min(dp[prev][0], dp[prev][2]) + costs[i][1];  
            dp[curr][2] = Math.min(dp[prev][0], dp[prev][1]) + costs[i][2];  
        }  
        return Math.min(dp[curr][0], Math.min(dp[curr][1], dp[curr][2]));  
    } 
http://yuanhsh.iteye.com/blog/2232601
cost(i,b)=min(cost(i-1,g),cost(i-1,r))+cost of painting i as b; 
cost(i,g)=min(cost(i-1,b),cost(i-1,r))+cost of painting i as g; 
cost(i,r)=min(cost(i-1,g),cost(i-1,b))+cost of painting i as r; 
finally min(cost(N,b),cost(N,g),cost(N,r)) is the ans
Also http://shibaili.blogspot.com/2015/09/day-128-256-265-paint-house.html
public static int minCost(int n, int[][] cost) {  
    int m = cost.length;  
    int[][] f = new int[m][n+1];  
    for(int i=1; i<=n; i++) {  
        f[0][i] = Math.min(f[1][i-1], f[2][i-1]) + cost[0][i-1];  
        f[1][i] = Math.min(f[0][i-1], f[2][i-1]) + cost[1][i-1];  
        f[2][i] = Math.min(f[0][i-1], f[1][i-1]) + cost[2][i-1];  
    }  
    int min = Math.min(Math.min(f[0][n], f[1][n]), f[2][n]);  
    return min;  
}  
http://www.bubuko.com/infodetail-1039300.html
 3     int minCost(vector<vector<int>>& costs) {
 4         if (costs.empty()) return 0;
 5         int n = costs.size(), r = 0, g = 0, b = 0;
 6         for (int i = 0; i < n; i++) {
 7             int rr = r, bb = b, gg = g; 
 8             r = costs[i][0] + min(bb, gg);
 9             b = costs[i][1] + min(rr, gg);
10             g = costs[i][2] + min(rr, bb);
11         }
12         return min(r, min(b, g));
13     } 
X. http://likemyblogger.blogspot.com/2015/08/leetcode-256-paint-house.html

    int minCost(vector<vector<int>>& costs) {

        int n = costs.size();

        if(n==0) return 0;

        for(int i=1; i<n; ++i){

            for(int j=0; j<3; ++j){

                costs[i][j] += min(costs[i-1][(j+1)%3], costs[i-1][(j+2)%3]);

            }

        }

        return min(costs[n-1][0], min(costs[n-1][1], costs[n-1][2]));

    }
一个dp，f(i,j)表示前i个house都paint了且第i个house paint成color_j的最小cost。
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }

        int h = costs.length;
        int w = costs[0].length;

        int[][] map = new int[h + 1][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i + 1][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int k = 0; k < w; k++) {
                    if (j != k) {
                        map[i + 1][j] = Math.min(map[i + 1][j], map[i][k] + costs[i][j]);
                    }
                }
            }
        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < w; i++) {
            result = Math.min(result, map[h][i]);
        }
        return result;
    }
http://blog.csdn.net/craiglin1992/article/details/44885775
public int minPaintCost(int[][] cost) {
    if (cost == null || cost.length == 0) return 0;
    int[][] dp = new int[cost.length][3];
    dp[0][0] = cost[0][0], dp[0][1] = cost[0][1], dp[0][2] = cost[0][2];
    for (int i = 1; i < cost.length; ++i) {
        dp[i][0] = cost[i][0] + Math.min(dp[i-1][1], dp[i-1][2]);
        dp[i][1] = cost[i][1] + Math.min(dp[i-1][0], dp[i-1][2]);
        dp[i][2] = cost[i][2] + Math.min(dp[i-1][0], dp[i-1][1]);
    }
    return Math.min(dp[dp.length-1][0], Math.min(dp[dp.length-1][1],[dp.length-1][2]));
}
N house,    M color
1 int paint(int N, int M, int[][] cost) {
 2     int[][] res = new int[N+1][M];
 3     for (int t=0; t<M; t++) {
 4         res[0][t] = 0;
 5     }
 6     for (int i=1; i<N; i++) {
 7         for (int j=0; j<M; j++) {
 8             res[i][j] = Integer.MAX_VALUE;
 9         }
10     }
11     for (int i=1; i<=N; i++) {
12         for (int j=0; j<M; j++) {
13             for (int k=0; k<M; k++) {
14                 if (k != j) {
15                     res[i][j] = Math.min(res[i][j], res[i-1][k]+cost[i-1][j]); //
16                 }
17             }
18         }
19     }
20     int result = Integer.MAX_VALUE；
21     for (int t=0; t<M; t++) {
22         result = Math.min(result, res[N][t]);
23     }
24     return result;
25 }
Read full article from F面经：painting house - neverlandly - 博客园
 * @author het
 *
 */
public class LeetCode256 {
	
	
	 int minCost(vector<vector<int>>& costs) {

	        int n = costs.size();

	        if(n==0) return 0;

	        for(int i=1; i<n; ++i){

	            for(int j=0; j<3; ++j){

	                costs[i][j] += min(costs[i-1][(j+1)%3], costs[i-1][(j+2)%3]);

	            }

	        }

	        return min(costs[n-1][0], min(costs[n-1][1], costs[n-1][2]));

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
