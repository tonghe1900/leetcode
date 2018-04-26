package Leetcode600x;
/**
 *  694. Number of Distinct Islands
2017年10月08日 16:01:01727人阅读 评论(0) 收藏  举报
 分类： LeetCode其他（13）  
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/zjucor/article/details/78175448

Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Count the number of distinct islands. An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.

Example 1:

11000
11000
00011
00011
Given the above grid map, return 1.
Example 2:

11011
10000
00001
11011
Given the above grid map, return 3.

Notice that:
11
1
and
 1
11
are considered different island shapes, because we do not consider reflection / rotation.
Note: The length of each dimension in the given grid does not exceed 50.





思路：就是个BFS，唯一需要做文章的是怎么把一个block转换成string

我的方法是把左上角的点尽量往（0,0）方向移动


[java] view plain copy
package l694;  
  
import java.util.HashSet;  
import java.util.LinkedList;  
import java.util.Queue;  
import java.util.Set;  
  
class Solution {  
    public int numDistinctIslands(int[][] grid) {  
        int n = grid.length, m = grid[0].length;  
        boolean[][] mark = new boolean[n][m];  
        int[][] dirs = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};  
        Set<String> s = new HashSet<String>();  
          
        for(int i=0; i<n; i++) {  
            for(int j=0; j<m; j++) {  
                if(!mark[i][j] && grid[i][j] == 1) {  
                    int miny=j;  
                      
                    Queue<int[]> q = new LinkedList<int[]>();  
                    q.add(new int[]{i, j});  
                    mark[i][j] = true;  
                    grid[i][j] = 0;  
                    while(!q.isEmpty()) {  
                        int[] t = q.remove();  
                        int x=t[0], y = t[1];  
                        for(int[] dir : dirs) {  
                            int xx = x+dir[0], yy=y+dir[1];  
                            if(xx<0||yy<0||xx>=n||yy>=m)    continue;  
                            if(mark[xx][yy]||grid[xx][yy]==0)continue;  
                            q.add(new int[]{xx, yy});  
                            mark[xx][yy] = true;  
                            grid[xx][yy] = 0;  
                            miny = Math.min(miny, yy);  
                        }  
                    }  
                      
                    for(int ii=0; ii<n; ii++)  
                        for(int jj=0; jj<m; jj++)   
                            if(mark[ii][jj]) {  
                                mark[ii][jj]  =false;  
                                mark[ii-i][jj-miny] = true;  
                            }  
                      
                    StringBuilder sb  = new StringBuilder();  
                    for(int ii=0; ii<n; ii++)  
                        for(int jj=0; jj<m; jj++) {  
                            if(mark[ii][jj]) {  
                                mark[ii][jj] = false;  
                                sb.append(1);  
                            } else {  
                                sb.append(0);  
                            }  
                        }  
                    s.add(sb.toString());  
                }  
            }  
        }  
          
        return s.size();  
    }  
}  
 * @author tonghe
 *
 */
public class Leetcode694 {

}
