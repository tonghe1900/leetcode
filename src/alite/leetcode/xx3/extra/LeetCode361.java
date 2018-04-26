package alite.leetcode.xx3.extra;
/**
 * LeetCode 361 - Bomb Enemy

https://discuss.leetcode.com/topic/10/bomb-enemy/
Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), return the maximum enemies
 you can kill 
using one bomb.
The bomb kills all the enemies in the same row and column from the planted point until it hits the wall since the wall 
is too strong to be destroyed.
Note that you can only put the bomb at an empty cell.
Example:
For the given grid

0 E 0 0
E 0 W E
0 E 0 0

return 3. (Placing a bomb at (1,1) kills 3 enemies)

X. http://www.cnblogs.com/grandyang/p/5599289.html
建立四个累加数组v1, v2, v3, v4，其中v1是水平方向从左到右的累加数组，v2是水平方向从右到左的累加数组，v3是竖直方向从上到下的累加数组，v4是竖直方向从下到上的累加数组，我们建立好这个累加数组后，对于任意位置(i, j)，其可以炸死的最多敌人数就是v1[i][j] + v2[i][j] + v3[i][j] + v4[i][j]，最后我们通过比较每个位置的累加和，就可以得到结果
    int maxKilledEnemies(vector<vector<char>>& grid) {
        if (grid.empty() || grid[0].empty()) return 0;
        int m = grid.size(), n = grid[0].size(), res = 0;
        vector<vector<int>> v1(m, vector<int>(n, 0)), v2 = v1, v3 = v1, v4 = v1;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                int t = (j == 0 || grid[i][j] == 'W') ? 0 : v1[i][j - 1];
                v1[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
            for (int j = n - 1; j >= 0; --j) {
                int t = (j == n - 1 || grid[i][j] == 'W') ? 0 : v2[i][j + 1];
                v2[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
        }
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                int t = (i == 0 || grid[i][j] == 'W') ? 0 : v3[i - 1][j];
                v3[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
            for (int i = m - 1; i >= 0; --i) {
                int t = (i == m - 1 || grid[i][j] == 'W') ? 0 : v4[i + 1][j];
                v4[i][j] = grid[i][j] == 'E' ? t + 1 : t;
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '0') {
                    res = max(res, v1[i][j] + v2[i][j] + v3[i][j] + v4[i][j]);
                }
            }
        }
        return res;
    }

https://discuss.leetcode.com/topic/52170/share-my-java-codes
X. https://leetcode.com/discuss/109116/short-o-mn-solution
这种解法比较省空间，写法也比较简洁，需要一个rowCnt变量，用来记录到下一个墙之前的敌人个数。
还需要一个数组colCnt，其中colCnt[j]表示第j列到下一个墙之前的敌人个数。
算法思路是遍历整个数组grid，对于一个位置grid[i][j]，对于水平方向，如果当前位置是开头一个或者前面一个是墙壁，
我们开始从当前位置往后遍历，遍历到末尾或者墙的位置停止，计算敌人个数。对于竖直方向也是同样，
如果当前位置是开头一个或者上面一个是墙壁，我们开始从当前位置向下遍历，遍历到末尾或者墙的位置停止，计算敌人个数。
有了水平方向和竖直方向敌人的个数，那么如果当前位置是0，表示可以放炸弹，我们更新结果res即可

https://discuss.leetcode.com/topic/48565/short-o-mn-solution
int maxKilledEnemies(vector<vector<char>>& grid) {
    int m = grid.size(), n = m ? grid[0].size() : 0;
    int result = 0, rowhits, colhits[n];
    for (int i=0; i<m; i++) {
        for (int j=0; j<n; j++) {
            if (!j || grid[i][j-1] == 'W') {
                rowhits = 0;
                for (int k=j; k<n && grid[i][k] != 'W'; k++)
                    rowhits += grid[i][k] == 'E';
            }
            if (!i || grid[i-1][j] == 'W') {
                colhits[j] = 0;
                for (int k=i; k<m && grid[k][j] != 'W'; k++)
                    colhits[j] += grid[k][j] == 'E';
            }
            if (grid[i][j] == '0')
                result = max(result, rowhits + colhits[j]);
        }
    }
    return result;
}
https://discuss.leetcode.com/topic/48742/simple-dp-solution-in-java
only need to store one killed enemies value for a row and an array of each column;
if current grid value is W, this means previous stored value becomes invalid, you need to recalculate.
I think it is O(m * n). Although there is another for loop k inside for loops of i and j. 
It just calculates the enemies in advance. In the end, it will traverse this grid once to compute the enemies that are killed.

 it is little a DP-like SOLUTION, the only place col[], which is storing col enemies count, is only updated once for consecutive enemies column, and reused for later calculation (when there is '0', where bomb can be planted)
 public int maxKilledEnemies(char[][] grid) {
    if(grid == null || grid.length == 0 ||  grid[0].length == 0) return 0;
    int max = 0;
    int row = 0;
    int[] col = new int[grid[0].length];
    for(int i = 0; i<grid.length; i++){
        for(int j = 0; j<grid[0].length;j++){
            if(grid[i][j] == 'W') continue;
            if(j == 0 || grid[i][j-1] == 'W'){
                 row = killedEnemiesRow(grid, i, j);
            }
            if(i == 0 || grid[i-1][j] == 'W'){
                 col[j] = killedEnemiesCol(grid,i,j);
            }
            if(grid[i][j] == '0'){
                max = (row + col[j] > max) ? row + col[j] : max;
            }
        }

    }
    
    return max;
}

//calculate killed enemies for row i from column j
private int killedEnemiesRow(char[][] grid, int i, int j){
    int num = 0;
    while(j <= grid[0].length-1 && grid[i][j] != 'W'){
        if(grid[i][j] == 'E') num++;
        j++;
    }
    return num;
}
//calculate killed enemies for  column j from row i
private int killedEnemiesCol(char[][] grid, int i, int j){
    int num = 0;
    while(i <= grid.length -1 && grid[i][j] != 'W'){
        if(grid[i][j] == 'E') num++;
        i++;
    }
    return num;
}

https://discuss.leetcode.com/topic/48577/short-java-accepted-solution-55ms
public int maxKilledEnemies(char[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) {
        return 0;
    }
    int ret = 0;
    int row = grid.length;
    int col = grid[0].length;
    int rowCache = 0;
    int[] colCache = new int[col];
    for (int i = 0;i < row; i++) {
        for (int j = 0; j < col; j++) {
            if (j == 0 || grid[i][j-1] == 'W') {
                rowCache = 0;
                for (int k = j; k < col && grid[i][k] != 'W'; k++) {
                    rowCache += grid[i][k] == 'E' ? 1 : 0;
                }
            }
            if (i == 0 || grid[i-1][j] == 'W') {
                colCache[j] = 0;
                for (int k = i;k < row && grid[k][j] != 'W'; k++) {
                    colCache[j] += grid[k][j] == 'E' ? 1 :0;
                }
            }
            if (grid[i][j] == '0') {
                ret = Math.max(ret, rowCache + colCache[j]);
            }
        }
    }
    return ret;
}

http://dartmooryao.blogspot.com/2016/06/leetcode-361-bomb-enemy.html
My idea is to create a 2D matrix, and go from Left, Right, Top, and Down to calculate the count. Then add the count into the 2D matrix.

This solution is better because the code looks much shorter.

Idea:
(1) Use a 1D array to store the current count of the previous cols, and another int variable to store the count from the left.
(2) Use two for loops to visit all elements in the matrix.
(3) When the left of current position is a wall, we reset the rowHit variable to 0. Then we use a for loop that start from current position to the right, adding the count of E to the rowHit. Until we hit a wall.
(4) When the top col is a wall, we reset the colHit to be zero. Then use the same way to calculate the colHit to the end of col.
(5) If the current position is a '0', we compare the rowHit+colHit with the max result.
    public int maxKilledEnemies(char[][] grid) {
        if(grid.length==0){ return 0; }
        int rowNo = grid.length, colNo = grid[0].length;
        int[] colHit = new int[colNo];
        int rowHit = 0;
        int result = 0;
       
        for(int i=0; i<rowNo; i++){
            for(int j=0; j<colNo; j++){
                if(j==0 || grid[i][j-1]=='W'){
                    rowHit = 0;
                    for(int k=j; k<colNo && grid[i][k] != 'W'; k++){
                        if(grid[i][k]=='E'){ rowHit++; }
                    }
                }
               
                if(i==0 || grid[i-1][j]=='W'){
                    colHit[j] = 0;
                    for(int k=i; k<rowNo && grid[k][j] != 'W'; k++){
                        if(grid[k][j]=='E'){ colHit[j]++; }
                    }
                }
               
                if(grid[i][j]=='0'){
                    result = Math.max(result, rowHit+colHit[j]);
                }
            }
        }
       
        return result;
    }


Walk through the matrix. At the start of each non-wall-streak (row-wise or column-wise), count the number of hits in that streak and remember it. O(mn) time, O(n) space.
int maxKilledEnemies(vector<vector<char>>& grid) {
    int m = grid.size(), n = m ? grid[0].size() : 0;
    int result = 0, rowhits, colhits[n];
    for (int i=0; i<m; i++) {
        for (int j=0; j<n; j++) {
            if (!j || grid[i][j-1] == 'W') {
                rowhits = 0;
                for (int k=j; k<n && grid[i][k] != 'W'; k++)
                    rowhits += grid[i][k] == 'E';
            }
            if (!i || grid[i-1][j] == 'W') {
                colhits[j] = 0;
                for (int k=i; k<m && grid[k][j] != 'W'; k++)
                    colhits[j] += grid[k][j] == 'E';
            }
            if (grid[i][j] == '0')
                result = max(result, rowhits + colhits[j]);
        }
    }
    return result;
}
https://leetcode.com/discuss/109134/short-java-accepted-solution-55ms
public int maxKilledEnemies(char[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) {
        return 0;
    }
    int ret = 0;
    int row = grid.length;
    int col = grid[0].length;
    int rowCache = 0;
    int[] colCache = new int[col];
    for (int i = 0;i < row; i++) {
        for (int j = 0; j < col; j++) {
            if (j == 0 || grid[i][j-1] == 'W') {
                rowCache = 0;
                for (int k = j; k < col && grid[i][k] != 'W'; k++) {
                    rowCache += grid[i][k] == 'E' ? 1 : 0;
                }
            }
            if (i == 0 || grid[i-1][j] == 'W') {
                colCache[j] = 0;
                for (int k = i;k < row && grid[k][j] != 'W'; k++) {
                    colCache[j] += grid[k][j] == 'E' ? 1 :0;
                }
            }
            if (grid[i][j] == '0') {
                ret = Math.max(ret, rowCache + colCache[j]);
            }
        }
    }
    return ret;
}
X. Brute Force
https://leetcode.com/discuss/109110/simple-java-solution-easy-to-understand
    public int maxKilledEnemies(char[][] grid) {

        if (grid == null || grid.length == 0)
            return 0;

        int ret=0;

        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                if (grid[i][j] != '0')
                    continue;
                ret = Math.max(ret, countKilled(i, j, grid));                     
            }
        }        

        return ret;

    }

    int countKilled(int row, int col, char[][] grid) {
        int ret=0;

        for (int i=row; i<grid.length && grid[i][col] != 'Y'; i++) {
            if (grid[i][col] == 'X')
                ret++;
        }
        for (int i=row; i>=0 && grid[i][col] != 'Y'; i--) {
            if (grid[i][col] == 'X')
                ret++;
        }
        for (int i=col; i<grid[0].length && grid[row][i] != 'Y'; i++) {
            if (grid[row][i] == 'X')
                ret++;
        }
        for (int i=col; i>=0 && grid[row][i] != 'Y'; i--) {
            if (grid[row][i] == 'X')
                ret++;
        }
        return ret;
    }

https://discuss.leetcode.com/topic/50374/java-dp-solultion-o-mn-time-o-mn-space-beats-90
Using only O(mn) memory for additional grid (not as perfect as (O(m) space solutions given by other users but anyway..) we can solve this problem through O(4mn) iterations. We just need to scan the grid from left to right and accumulate the sum of enemies by dp[i][j] from left since the last wall. Then we do the same from top to bottom, from right to left and finally from bottom to top. On each iteration we increase the dp[i][j] value by cumulative sum.
  public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // from left to right
        for (int i = 0; i < m; i++) {
            int current = 0;
            for (int j = 0; j < n; j++) 
                current = process(grid, dp, i, current, j);
        }
        // from top to bottom
        for (int j = 0; j < n; j++) {
            int current = 0;
            for (int i = 0; i < m; i++) 
                current = process(grid, dp, i, current, j);
        }
        // from right to left
        for (int i = 0; i < m; i++) {
            int current = 0;
            for (int j = n - 1; j >= 0; j--) 
                current = process(grid, dp, i, current, j);
        }
        int ans = 0;
        // from bottom to top
        for (int j = 0; j < n; j++) {
            int current = 0;
            for (int i = m - 1; i >= 0; i--) {
                current = process(grid, dp, i, current, j);
                if (grid[i][j] == '0')  ans = Math.max(ans, dp[i][j]);
            }
        }

        return ans;
    }

    private int process(char[][] c, int[][] dp, int i, int current, int j) {
        if (c[i][j] == 'W') current = 0;
        if (c[i][j] == 'E') current++;
        dp[i][j] += current;
        return current;
    }

https://all4win78.wordpress.com/2016/07/24/leetcode-361-bomb-enemy/
如果遍历数组，找到’0’之后再向四个方向拓展数’E’的个数，一定会超时。一般的解决方法就是DP，用一个cache来存下部分结果来避免重复运算。我这里用的是一个m*2的array，叫做enemyCount，来储存每一行的结果。enemyCount[i][0]储存了第i行某一个’W’的位置，enemyCount[i][1]储存了enemyCount[i][0]所标识的’W’到之前一个’W’之间’E’的个数，初始化的时候enemyCount[i][0] = -1，enemyCount[i][1] = 0。
然后我按照列遍历，因为之前enemyCount是按照行来储存的，如果发现当前j大于了enemyCount[i][0]的值，我们就要更新enemyCount。同时，每列在遍历的时候都会有一个临时的变量，叫做colEnemy，用来记录在当前的列两个’W’之间有多少’E’，每次遇到’W’就需要更新这个值。每次遇到一个’0’的时候，我们就可以把这个临时变量和enemyCount[i][1]的值加起来，然后视情况更新我们的全局最大值。
初始：
361_1
在检查grid[1][1]的时候，此时我们正在考虑第二列，所以enemyCount上的数值已经更新过了，第一行是4和1，因为第一行没有’W’，所以我们第一个终止的地方就是4这个位置，(-1, 4)范围内一共一个’E’；第二行是2和1，因为grid[1][2] = ‘W’，而且在(-1, 2)范围内是一个’E’；第三行同第一行。colEnemy也可以直接看出是2，因为第二列在(-1, 3)范围内有两个’E’。
361_2

    public int maxKilledEnemies(char[][] grid) {

        if (grid == null || grid.length == 0) {

            return 0;

        }

         

        int[][] enemyCount = new int[grid.length][2];

        for (int i = 0; i < enemyCount.length; i++) {

            enemyCount[i][0] = -1;

        }

         

        int max = 0;

        for (int j = 0; j < grid[0].length; j++) {

            int colEnemy = countColEnemy(grid, j, 0);

            for (int i = 0; i < grid.length; i++) {

                if (grid[i][j] == '0') {

                    if (j > enemyCount[i][0]) {

                        update(enemyCount, grid, i, j);

                    }

                    max = Math.max(colEnemy + enemyCount[i][1], max);

                }

                if (grid[i][j] == 'W') {

                    colEnemy = countColEnemy(grid, j, i + 1);

                }

            }

        }

        return max;

    }

     

    private void update(int[][] enemyCount, char[][] grid, int i, int j) {

        int count = 0;

        int k = enemyCount[i][0] + 1;

        while (k < grid[0].length && (grid[i][k] != 'W' || k < j)) {

            if (grid[i][k] == 'E') {

                count += 1;

            }

            if (grid[i][k] == 'W') {

                count = 0;

            }

            k += 1;

        }

        enemyCount[i][0] = k;

        enemyCount[i][1] = count;

    }

     

    private int countColEnemy(char[][] grid, int j, int start) {

        int count = 0;

        int i = start;

        while (i < grid.length && grid[i][j] != 'W') {

            if (grid[i][j] == 'E') {

                count += 1;

            }

            i += 1;

        }

        return count;

    }

X.
https://fishercoder.com/2016/07/12/bomb-enemy/
https://discuss.leetcode.com/topic/53511/straightforward-java-solution-with-space-complex-o-1
https://discuss.leetcode.com/topic/51035/super-straightforward-java-solution
public int maxKilledEnemies(char[][] grid) {
    int m = grid.length;
    if(grid == null || m == 0) return 0;
    int n = grid[0].length;
    
    int[][] max = new int[m][n];
    
    for(int i = 0; i < m; i++){
        for(int j = 0; j < n; j++){
            
            if(grid[i][j] == '0'){
                int count = 0;
                
                //count all possible hits in its upward direction
                for(int k = j-1; k >= 0; k--){
                    if(grid[i][k] == 'E') {
                        count++;
                    } else if(grid[i][k] == 'W') {
                        break;
                    }
                }
                
                //count all possible hits in its downward direction
                for(int k = j+1; k < n; k++){
                    if(grid[i][k] == 'E') {
                        count++;
                    } else if(grid[i][k] == 'W') {
                        break;
                    }
                }
                
                //count all possible hits in its right direction
                for(int k = i+1; k < m; k++){
                    if(grid[k][j] == 'E') {
                        count++;
                    } else if(grid[k][j] == 'W') {
                        break;
                    }
                }
                
                //count all possible hits in its left direction
                for(int k = i-1; k >= 0; k--){
                    if(grid[k][j] == 'E') {
                        count++;
                    } else if(grid[k][j] == 'W') {
                        break;
                    }
                }
                
                max[i][j] = count;
                
            } 
        }
    }
    
    int result = 0;
    
    for(int i = 0; i < m; i++){
        for(int j = 0; j < n; j++){
            if(max[i][j] > result) result = max[i][j];
        }
    }
    return result;
}
follow up是如果这个地图很大，很多点都是空的没东西，你怎么优化你的算法。楼主想了下稀疏矩阵乘法那道题就说用一个二维矩阵矩阵来记录每一行中，各个位置中不为0的列数和其对应的值，然后遍历这个新矩阵
 * @author het
 *
 */
public class LeetCode361 {
//	https://discuss.leetcode.com/topic/48742/simple-dp-solution-in-java
//		only need to store one killed enemies value for a row and an array of each column;
//		if current grid value is W, this means previous stored value becomes invalid, you need to recalculate.
//		I think it is O(m * n). Although there is another for loop k inside for loops of i and j. 
//		It just calculates the enemies in advance. In the end, it will traverse this grid once to compute the enemies that are killed.
//
//		 it is little a DP-like SOLUTION, the only place col[], which is storing col enemies count, is only updated once for consecutive enemies column, and reused for later calculation (when there is '0', where bomb can be planted)
		 public int maxKilledEnemies(char[][] grid) {
		    if(grid == null || grid.length == 0 ||  grid[0].length == 0) return 0;
		    int max = 0;
		    int row = 0;
		    int[] col = new int[grid[0].length];
		    for(int i = 0; i<grid.length; i++){
		        for(int j = 0; j<grid[0].length;j++){
		            if(grid[i][j] == 'W') continue;
		            if(j == 0 || grid[i][j-1] == 'W'){
		                 row = killedEnemiesRow(grid, i, j);
		            }
		            if(i == 0 || grid[i-1][j] == 'W'){
		                 col[j] = killedEnemiesCol(grid,i,j);
		            }
		            if(grid[i][j] == '0'){
		                max = (row + col[j] > max) ? row + col[j] : max;
		            }
		        }

		    }
		    
		    return max;
		}

		//calculate killed enemies for row i from column j
		private int killedEnemiesRow(char[][] grid, int i, int j){
		    int num = 0;
		    while(j <= grid[0].length-1 && grid[i][j] != 'W'){
		        if(grid[i][j] == 'E') num++;
		        j++;
		    }
		    return num;
		}
		//calculate killed enemies for  column j from row i
		private int killedEnemiesCol(char[][] grid, int i, int j){
		    int num = 0;
		    while(i <= grid.length -1 && grid[i][j] != 'W'){
		        if(grid[i][j] == 'E') num++;
		        i++;
		    }
		    return num;
		}
		
		
//		Using only O(mn) memory for additional grid (not as perfect as (O(m) space solutions given by other 
		//users but anyway..) we can solve this problem through O(4mn) iterations. We just need to scan the grid
		//from left to right and accumulate the sum of enemies by dp[i][j] from left since the last wall. 
		//Then we do the same from top to bottom, from right to left and finally from bottom to top.
		//On each iteration we increase the dp[i][j] value by cumulative sum.
				  public int maxKilledEnemies(char[][] grid) {
				        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

				        int m = grid.length;
				        int n = grid[0].length;
				        int[][] dp = new int[m][n];

				        // from left to right
				        for (int i = 0; i < m; i++) {
				            int current = 0;
				            for (int j = 0; j < n; j++) 
				                current = process(grid, dp, i, current, j);
				        }
				        // from top to bottom
				        for (int j = 0; j < n; j++) {
				            int current = 0;
				            for (int i = 0; i < m; i++) 
				                current = process(grid, dp, i, current, j);
				        }
				        // from right to left
				        for (int i = 0; i < m; i++) {
				            int current = 0;
				            for (int j = n - 1; j >= 0; j--) 
				                current = process(grid, dp, i, current, j);
				        }
				        int ans = 0;
				        // from bottom to top
				        for (int j = 0; j < n; j++) {
				            int current = 0;
				            for (int i = m - 1; i >= 0; i--) {
				                current = process(grid, dp, i, current, j);
				                if (grid[i][j] == '0')  ans = Math.max(ans, dp[i][j]);
				            }
				        }

				        return ans;
				    }

				    private int process(char[][] c, int[][] dp, int i, int current, int j) {
				        if (c[i][j] == 'W') current = 0;
				        if (c[i][j] == 'E') current++;
				        dp[i][j] += current;
				        return current;
				    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
