package alite.leetcode.xx4;
/**
 * http://www.itdadao.com/articles/c15a774486p0.htmlzhttps://leetcode.com/problems/island-perimeter/
You are given a map in form of a two-dimensional integer grid where 1 
represents land and 0 represents water. Grid cells are connected 
horizontally/vertically (not diagonally). The grid is completely 
surrounded by water, and there is exactly one island (i.e., one or
 more connected land cells). The island doesn't have "lakes" (water inside that isn't connected to the water around the island). One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.
Example:


[[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

Answer: 16
Explanation: The perimeter is the 16 yellow stripes in the image below:

http://blog.csdn.net/mebiuw/article/details/53265123
    public int islandPerimeter(int[][] grid) {
        int permeter = 0;
        int n = grid.length;
        int m = grid[0].length;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1){
                    if(i==0 || grid[i-1][j] == 0) permeter++;
                    if(i==n-1 || grid[i+1][j] == 0) permeter++;
                    if(j==0 || grid[i][j-1] == 0) permeter++;
                    if(j==m-1 || grid[i][j+1] == 0) permeter++;
                }
            }
        }
        return permeter;
    }
http://blog.csdn.net/jmspan/article/details/53254264
方法：逐个扫描。

    public int islandPerimeter(int[][] grid) {  
        int p = 0;  
        for(int i = 0; i < grid.length; i++) {  
            for(int j = 0; j < grid[i].length; j++) {  
                if (grid[i][j] == 0) continue;  
                if (i == 0 || grid[i - 1][j] == 0) p++;  
                if (i == grid.length - 1 || grid[i + 1][j] == 0) p++;  
                if (j == 0 || grid[i][j - 1] == 0) p++;  
                if (j == grid[i].length - 1 || grid[i][j + 1] == 0) p++;  
            }  
        }  
        return p;  
    }

http://bookshadow.com/weblog/2016/11/20/leetcode-island-perimeter/
每一个陆地单元格的周长为4，当两单元格上下或者左右相邻时，令周长减2。
    def islandPerimeter(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        h = len(grid)
        w = len(grid[0]) if h else 0
        ans = 0
        for x in range(h):
            for y in range(w):
                if grid[x][y] == 1:
                    ans += 4
                    if x > 0 and grid[x - 1][y]:
                        ans -= 2
                    if y > 0 and grid[x][y - 1]:
                        ans -= 2
        return ans

http://www.itdadao.com/articles/c15a774486p0.html
解法：如果只有一个点，那么周长就是4，如果这个点和其他的一个点相邻，那么对于周长的贡献就要少一。同理，对于另外一个相邻的点，那么
贡献的周长也少一。于是先遍历，看看有多少个为1的点，算出“最大”的周长以后，减去因为相邻而减少的周长长度就可以了。
public int islandPerimeter(int[][] grid) {
        boolean[][] mark = new boolean[grid.length][grid[0].length];
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    mark[i][j] = true;
                    count++;
                } else {
                    mark[i][j] = false;
                }
            }
        }
        int perimeter = count * 4;
        System.out.println("perimeter:" + perimeter);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (mark[i][j]) {
                    perimeter -= neigbors(i, j, mark); //减去相邻的点
                }
            }
        }
        return perimeter;
    }


    private int neigbors(int i, int j, boolean[][] mark) {
        int count = 0;
        for (int x = -1; x <= 1; x += 2) {
            int tempx = x + i;
            int tempy = j;
            if (isSafe(tempx, tempy, mark.length, mark[0].length) && mark[tempx][tempy]) {
                count++;
            }
        }
        for (int y = -1; y <= 1; y += 2) {
            int tempx = i;
            int tempy = y + j;
            if (isSafe(tempx, tempy, mark.length, mark[0].length) && mark[tempx][tempy]) {
                count++;
            }
        }
        System.out.printf("i:%d,j:%d\n", i, j);
        System.out.println("count:" + count);
        return count;
    }

    private boolean isSafe(int x, int y, int xlength, int ylength) {
        if (x < 0 || x >= xlength || y < 0 || y >= ylength) {
            return false;
        } else {
            return true;
        }
    }
http://www.geeksforgeeks.org/find-perimeter-shapes-formed-1s-binary-matrix/
The idea is to traverse the matrix, find all ones and find their contribution in perimeter. The maximum contribution of a 1 is four if it is surrounded by all 0s. The contribution reduces by one with 1 around it.
Algorithm for solving this problem:
Traverse the whole matrix and find the cell having value equal to 1.
Calculate the number of closed side for that cell and add, 4 – number of closed side to the total perimeter.

int numofneighbour(int mat[][C], int i, int j)

{

    int count = 0;

 

    // UP

    if (i > 0 && mat[i - 1][j])

        count++;

 

    // LEFT

    if (j > 0 && mat[i][j - 1])

        count++;

 

    // DOWN

    if (i < R-1 && mat[i + 1][j])

        count++;

 

    // RIGHT

    if (j < C-1 && mat[i][j + 1])

        count++;

 

    return count;

}

 

// Returns sum of perimeter of shapes formed with 1s

int findperimeter(int mat[R][C])

{

    int perimeter = 0;

 

    // Traversing the matrix and finding ones to

    // calculate their contribution.

    for (int i = 0; i < R; i++)

        for (int j = 0; j < C; j++)

            if (mat[i][j])

                perimeter += (4 - numofneighbour(mat, i ,j));

 

    return perimeter;

}
 * @author het
 *
 */
public class LeetCode463 {
	 public int islandPerimeter(int[][] grid) {
	        int permeter = 0;
	        int n = grid.length;
	        int m = grid[0].length;
	        for(int i=0;i<n;i++){
	            for(int j=0;j<m;j++){
	                if(grid[i][j] == 1){
	                    if(i==0 || grid[i-1][j] == 0) permeter++;
	                    if(i==n-1 || grid[i+1][j] == 0) permeter++;
	                    if(j==0 || grid[i][j-1] == 0) permeter++;
	                    if(j==m-1 || grid[i][j+1] == 0) permeter++;
	                }
	            }
	        }
	        return permeter;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
