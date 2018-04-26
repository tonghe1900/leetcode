package alite.leetcode.xx3;
/**
 * LeetCode 329. Longest Increasing Path in a Matrix

https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
Given an integer matrix, find the length of the longest increasing path.
From each cell, you can either move to four directions: left, right, up or down.
 You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).
Example 1:
nums = [
  [9,9,4],
  [6,6,8],
  [2,1,1]
]
Return 4
The longest increasing path is [1, 2, 6, 9].
Example 2:
nums = [
  [3,4,5],
  [3,2,6],
  [2,2,1]
]
Return 4
The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
http://algobox.org/longest-increasing-path-in-a-matrix/
https://discuss.leetcode.com/topic/34835/15ms-concise-java-solution
https://discuss.leetcode.com/topic/34755/java-dfs-dp-solution
The naive idea is that, start from any cell and do DFS to find the longest increasing path with that cell as the first. This solution is O(n^2m^2) for a matrix of n by m.
Of course, this is too slow and contains a lot of repeated calculations. An optimization is using memoization. Suppose a cell (i, j) has value v(i, j) and the longest increasing path start from (i, j) is l(i,j). Then we have

1
2
3
4


v[i][j] = 1


for p, q in neighbors(i, j):


    if (v[p][q] < v[i][j]):


         v[i][j] = max(v[i][j], v[p][q] + 1)

Here neighbors(i,j) is just a function to generate all four (if possible) neighbor indices for (i, j)
Now we could do a scan through the matrix and apply this formula/subroutine for all cells. During the scan, recursive calls will happen. But that is ok because we can use a memoization matrix to make sure no repeat calculation. The time complexity of the entire algorithm is O(nm) which is linear to the number of cells.

    private static final int[] d = {0, 1, 0, -1, 0};


    public int longestIncreasingPath(int[][] matrix) {

        if (matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;

        int[][] memo = new int[m][n];

        int ans = 0;

        for (int i = 0; i < m; ++i)

            for (int j = 0; j < n; ++j)

                ans = Math.max(ans, dfs(matrix, m, n, i, j, memo));

        return ans;

    }


    private int dfs(int[][] matrix, int m, int n, int i, int j, int[][] memo) {

        if (memo[i][j] == 0) {

            for (int k = 0; k < 4; ++k) {

                int p = i + d[k], q = j + d[k + 1];

                if (0 <= p && p < m && 0 <= q && q < n && matrix[p][q] > matrix[i][j])

                    memo[i][j] = Math.max(memo[i][j], dfs(matrix, m, n, p, q, memo));

            }

            memo[i][j]++;

        }

        return memo[i][j];

    }
DFS+DP - time complexity: O(mn)
http://www.cnblogs.com/grandyang/p/5148030.html
https://www.hrwhisper.me/leetcode-longest-increasing-path-matrix/
http://www.fgdsb.com/2015/01/07/longest-increasing-sequence-in-matrix/

直接DFS效率太低，这题主要考DP+记忆化。
DP方程很明显：
opt[i][j] = max{ opt[i+1][j], opt[i-1][j], opt[i][j+1], opt[i][j-1] } +１
这道题给我们一个二维数组，让我们求矩阵中最长的递增路径，规定我们只能上下左右行走，不能走斜线或者是超过了边界。那么这道题的解法要用递归和DP来解，用DP的原因是为了提高效率，避免重复运算。我们需要维护一个二维动态数组dp，其中dp[i][j]表示数组中以(i,j)为起点的最长递增路径的长度，初始将dp数组都赋为0，当我们用递归调用时，遇到某个位置(x, y), 如果dp[x][y]不为0的话，我们直接返回dp[x][y]即可，不需要重复计算。我们需要以数组中每个位置都为起点调用递归来做，比较找出最大值。在以一个位置为起点用DFS搜索时，对其四个相邻位置进行判断，如果相邻位置的值大于上一个位置，则对相邻位置继续调用递归，并更新一个最大值，搜素完成后返回即可，参见代码如下：

    int []dx = { 1 , -1, 0 , 0  };

    int []dy = { 0 , 0 , 1 , -1 };

    public int longestIncreasingPath(int[][] matrix) {

        if (matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;

        int[][] dis = new int [m][n]; // call it dp or cache

        int ans = 0;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                ans = Math.max(ans, dfs( i, j, m, n, matrix, dis));

            }

        }

        return ans;

    }


    int dfs(int x, int y, int m,int n,int[][] matrix, int[][] dis) {

        if (dis[x][y] != 0) return dis[x][y];

        for (int i = 0; i < 4; i++) {

            int nx = x + dx[i];

            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < m && ny < n && matrix[nx][ny] > matrix[x][y]) {

                dis[x][y] = Math.max(dis[x][y], dfs(nx, ny, m, n, matrix, dis));

            }

        }

        dis[x][y]++;

        return dis[x][y];

    }

https://asanchina.wordpress.com/2016/01/20/329-longest-increasing-path-in-a-matrix/
这是一道典型的动态规划，得使用memorandum(备忘录)。我的dfs(r, c)会求以matrix[r][c]为最后一个数字的序列的最大长度。
class Solution {
    vector<vector<int> > dp;
    vector<vector<int> > matrix;
    int row, col;
public:
    int longestIncreasingPath(vector<vector<int> >& matrix) {
        row = matrix.size();
        if(row == 0) return 0;
        col = matrix[0].size();
        if(col == 0) return 0;
        
        dp = vector<vector<int> >(row, vector(col, -1));
        this->matrix = matrix;
        
        int maxi = 0;
        for(int r = 0; r < row; ++r)
            for(int c = 0; c < col; ++c) if(dfs(r, c) > maxi)
                    maxi = dfs(r, c);
        return maxi;
    }
private:
    int dfs(int r, int c)
    {
        if(dp[r][c] != -1) return dp[r][c];
        int dir[][2] = {{-1, 0},{1,0},{0,-1},{0,1}};
        int cur = 1;
        for(int d = 0; d < 4; ++d) 
        { 
            int tmpr = r+dir[d][0]; 
            int tmpc = c+dir[d][1]; 
            if(tmpr >= 0 && tmpr < row && tmpc >= 0 && tmpc < col && matrix[r][c] > matrix[tmpr][tmpc])
                cur = max(cur, dfs(tmpr, tmpc)+1);
        }
        dp[r][c] = cur;
        return cur;
    }
};
http://www.zrzahid.com/longest-increasing-path-in-a-matrix/

http://bookshadow.com/weblog/2016/01/20/leetcode-longest-increasing-path-matrix/
将矩阵matrix按照值从小到大排序，得到列表slist，列表元素(x, y, val)存储原矩阵的(行、列、值)
引入辅助数组dp，dp[x][y]表示从矩阵(x, y)元素出发的最长递增路径长度
遍历slist，同时更新(x, y)左、右、上、下四个相邻元素的dp值
    def longestIncreasingPath(self, matrix):
        """
        :type matrix: List[List[int]]
        :rtype: int
        """
        h = len(matrix)
        if h == 0: return 0
        w = len(matrix[0])
        dp = [[1] * w for x in range(h)]
        slist = sorted([(i, j, val)
                  for i, row in enumerate(matrix)
                  for j, val in enumerate(row)], 
                  key=operator.itemgetter(2))
        for x, y, val in slist:
            for dx, dy in zip([1, 0, -1, 0], [0, 1, 0, -1]):
                nx, ny = x + dx, y + dy
                if 0 <= nx < h and 0 <= ny < w and matrix[nx][ny] > matrix[x][y]:
                    dp[nx][ny] = max(dp[nx][ny], dp[x][y] + 1)
        return max([max(x) for x in dp])
参考：https://leetcode.com/discuss/81319/short-python
代码作者使用复数表示矩阵的行、列，十分巧妙。
def longestIncreasingPath(self, matrix):
    matrix = {i + j*1j: val
              for i, row in enumerate(matrix)
              for j, val in enumerate(row)}
    length = {}
    for z in sorted(matrix, key=matrix.get):
        length[z] = 1 + max([length[Z]
                             for Z in z+1, z-1, z+1j, z-1j
                             if Z in matrix and matrix[z] > matrix[Z]]
                            or [0])
    return max(length.values() or [0])
 * @author het
 *
 */
public class LeetCode329 {
//	 private static final int[] d = {0, 1, 0, -1, 0};
//
//
//	    public int longestIncreasingPath(int[][] matrix) {
//
//	        if (matrix.length == 0) return 0;
//
//	        int m = matrix.length, n = matrix[0].length;
//
//	        int[][] memo = new int[m][n];
//
//	        int ans = 0;
//
//	        for (int i = 0; i < m; ++i)
//
//	            for (int j = 0; j < n; ++j)
//
//	                ans = Math.max(ans, dfs(matrix, m, n, i, j, memo));
//
//	        return ans;
//
//	    }
//
//
//	    private int dfs(int[][] matrix, int m, int n, int i, int j, int[][] memo) {
//
//	        if (memo[i][j] == 0) {
//
//	            for (int k = 0; k < 4; ++k) {
//
//	                int p = i + d[k], q = j + d[k + 1];
//
//	                if (0 <= p && p < m && 0 <= q && q < n && matrix[p][q] > matrix[i][j])
//
//	                    memo[i][j] = Math.max(memo[i][j], dfs(matrix, m, n, p, q, memo));
//
//	            }
//
//	            memo[i][j]++;
//
//	        }
//
//	        return memo[i][j];
//
//	    }
	
	int []dx = { 1 , -1, 0 , 0  };

    int []dy = { 0 , 0 , 1 , -1 };

    public int longestIncreasingPath(int[][] matrix) {

        if (matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;

        int[][] dis = new int [m][n]; // call it dp or cache

        int ans = 0;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                ans = Math.max(ans, dfs( i, j, m, n, matrix, dis));

            }

        }

        return ans;

    }


    int dfs(int x, int y, int m,int n,int[][] matrix, int[][] dis) {

        if (dis[x][y] != 0) return dis[x][y];

        for (int i = 0; i < 4; i++) {

            int nx = x + dx[i];

            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < m && ny < n && matrix[nx][ny] > matrix[x][y]) {

                dis[x][y] = Math.max(dis[x][y], dfs(nx, ny, m, n, matrix, dis));

            }

        }

        dis[x][y]++;

        return dis[x][y];

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
