package alite.leetcode.newExtra.L500;
/**
 * LeetCode 562 - Longest Line of Consecutive One in Matrix

http://bookshadow.com/weblog/2017/04/23/leetcode-longest-line-of-consecutive-one-in-matrix/
Given a 01 matrix M, find the longest line of consecutive one in the matrix. The line could be horizontal, vertical, diagonal or anti-diagonal.
Example:
Input:
[[0,1,1,0],
 [0,1,1,0],
 [0,0,0,1]]
Output: 3
Hint: The number of elements in the given matrix will not exceed 10,000.
给定01矩阵M，计算矩阵中一条线上连续1的最大长度。一条线可以为横向、纵向、主对角线、反对角线。
提示：给定矩阵元素个数不超过10,000
https://discuss.leetcode.com/topic/87197/java-o-nm-time-dp-solution
public int longestLine(int[][] M) {
    int n = M.length, max = 0;
    if (n == 0) return max;
    int m = M[0].length;
    int[][][] dp = new int[n][m][4];
    for (int i=0;i<n;i++) 
        for (int j=0;j<m;j++) {
            if (M[i][j] == 0) continue;
            for (int k=0;k<4;k++) dp[i][j][k] = 1;
            if (j > 0 && M[i][j-1] == 1) dp[i][j][0] += dp[i][j-1][0]; // horizontal line
            if (j > 0 && i > 0 &&  M[i-1][j-1] == 1) dp[i][j][1] += dp[i-1][j-1][1]; // diagonal line
            if (i > 0 && M[i-1][j] == 1) dp[i][j][2] += dp[i-1][j][2]; // vertical line
            if (j < m-1 && i > 0 &&  M[i-1][j+1] == 1) dp[i][j][3] += dp[i-1][j+1][3]; // anti-diagonal line
            max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
            max = Math.max(max, Math.max(dp[i][j][2], dp[i][j][3]));
        }
    return max;
}

动态规划（Dynamic Programming）
分表用二维数组h[x][y], v[x][y], d[x][y], a[x][y]表示以元素M[x][y]结尾，横向、纵向、主对角线、反对角线连续1的最大长度
状态转移方程如下：
h[x][y] = M[x][y] * (h[x - 1][y]  + 1)

v[x][y] = M[x][y] * (v[x][y - 1]  + 1)

d[x][y] = M[x][y] * (d[x - 1][y - 1]  + 1)

a[x][y] = M[x][y] * (a[x + 1][y - 1]  + 1)
https://discuss.leetcode.com/topic/87204/verbose-java-solution-hashset-only-search-later-cells
For each unvisited direction of each 1, we search length of adjacent 1s and mark those 1s as visited in that direction. And we only need to search 4 directions: right, down, down-right, down-left. We only access each cell at max 4 times, so time complexity is O(mn). m = number of rows, n = number of columns.
    public int longestLine(int[][] M) {
        int m = M.length;
        if (m <= 0) return 0;
        int n = M[0].length;
        if (n <= 0) return 0;
        
        int max = 0;
        int[][] dirs = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        List<Set<String>> memo = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            memo.add(new HashSet<String>());
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 0) continue;
                String pos = i + "," + j;
                for (int k = 0; k < 4; k++) {
                    if (!memo.get(k).contains(pos)) {
                        int count = 0;
                        for (int r = i, c = j; r < m && r >= 0 && c < n && c >= 0; r += dirs[k][0], c += dirs[k][1]) {
                            if (M[r][c] == 1) {
                                count++;
                                memo.get(k).add(r + "," + c);
                            }
                            else break;
                        }
                        max = Math.max(max, count);
                    }
                }
            }
        }
        
        return max;
    }
https://discuss.leetcode.com/topic/87389/simple-and-concise-java-solution-easy-to-understand-o-m-n-space

public int longestLine(int[][] M) {
    if (M.length == 0 || M[0].length == 0) {
        return 0;
    }
    int max = 0;
    int[] col = new int[M[0].length];
    int[] diag = new int[M.length + M[0].length];
    int[] antiD = new int[M.length + M[0].length];
    for (int i = 0; i < M.length; i++) {
        int row = 0;
        for (int j = 0; j < M[0].length; j++) {
            if (M[i][j] == 1) {
                row++;
                col[j]++;
                diag[j + i]++;
                antiD[j - i + M.length]++;
                max = Math.max(max, row);
                max = Math.max(max, col[j]);
                max = Math.max(max, diag[j + i]);
                max = Math.max(max, antiD[j - i + M.length]);
            } else {
                row = 0;
                col[j] = 0;
                diag[j + i] = 0;
                antiD[j - i + M.length] = 0;
            }
        }
    }
    return max;
X. https://discuss.leetcode.com/topic/87228/java-strightforward-solution
    public int longestLine(int[][] M) {
        if(M == null) return 0;
        int res = 0;
        for(int i =0;i<M.length;i++){
            for(int j = 0;j<M[0].length;j++){
                if(M[i][j] == 1){
                    res = Math.max(res,getMaxOneLine(M, i, j));
                }
            }
        }
        return res;
    }
    final int [][] dirs = new int[][]{{1,0},{0,1},{1,1},{1,-1}};
    private int getMaxOneLine(int [][] M, int x, int y){
        int res = 1;
        for(int [] dir:dirs){
            int i = x+dir[0];
            int j = y+dir[1];
            int count = 1;
            while(isValidPosition(M, i, j) && M[i][j] == 1){
                i+=dir[0];
                j+=dir[1];
                count++;
            }
            res = Math.max(count,res);
        }
        return res;
    }
    
    private boolean isValidPosition(int M[][], int i, int j){
        return (i<M.length && j<M[0].length && i>=0 && j>=0);
    }
 * @author het
 *
 */
public class L562 {
	public int longestLine(int[][] M) {
	    int n = M.length, max = 0;
	    if (n == 0) return max;
	    int m = M[0].length;
	    int[][][] dp = new int[n][m][4];
	    for (int i=0;i<n;i++) 
	        for (int j=0;j<m;j++) {
	            if (M[i][j] == 0) continue;
	            for (int k=0;k<4;k++) dp[i][j][k] = 1;
	            if (j > 0 && M[i][j-1] == 1) dp[i][j][0] += dp[i][j-1][0]; // horizontal line
	            if (j > 0 && i > 0 &&  M[i-1][j-1] == 1) dp[i][j][1] += dp[i-1][j-1][1]; // diagonal line
	            if (i > 0 && M[i-1][j] == 1) dp[i][j][2] += dp[i-1][j][2]; // vertical line
	            if (j < m-1 && i > 0 &&  M[i-1][j+1] == 1) dp[i][j][3] += dp[i-1][j+1][3]; // anti-diagonal line
	            max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
	            max = Math.max(max, Math.max(dp[i][j][2], dp[i][j][3]));
	        }
	    return max;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
