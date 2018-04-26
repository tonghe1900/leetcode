package alite.leetcode.xx3;
/**
 * 304.	Range Sum Query 2D - Immutable
题目：

Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).


The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:

Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
 

Note:

You may assume that the matrix does not change.
There are many calls to sumRegion function.
You may assume that row1 ≤ row2 and col1 ≤ col2.
链接： http://leetcode.com/problems/range-sum-query-2d-immutable/

题解：

二维矩阵求Range Sum。这题我们也是用DP，不过dp的方法是: dp[i][j]等于从坐标matrix[0][0]到matrix[i - 1][j - 1]中所有元素的和。 这样我们就可以用中小学时计算矩形重叠面积的方法来计算出我们想要的结果。

Time Complexity - O(n2)， Space Complexity - O(n2)。

复制代码
复制代码
public class NumMatrix {
    private int[][] sum;
    public NumMatrix(int[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return;
        }
        int rowNum = matrix.length, colNum = matrix[0].length;
        sum = new int[rowNum + 1][colNum + 1];
        
        for(int i = 1; i < sum.length; i++) {
            for(int j = 1; j < sum[0].length; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
            return sum[row2 + 1][col2 + 1] - sum[row1][col2 + 1] - sum[row2 + 1][col1] + sum[row1][col1];
    }
}


// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.sumRegion(1, 2, 3, 4);
 * @author het
 *
 */
public class LeetCode304 {
	public class NumMatrix {
	    private int[][] sum;
	    public NumMatrix(int[][] matrix) {
	        if(matrix == null || matrix.length == 0) {
	            return;
	        }
	        int rowNum = matrix.length, colNum = matrix[0].length;
	        sum = new int[rowNum + 1][colNum + 1];
	        
	        for(int i = 1; i < sum.length; i++) {
	            for(int j = 1; j < sum[0].length; j++) {
	                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + matrix[i - 1][j - 1];
	            }
	        }
	    }

	    public int sumRegion(int row1, int col1, int row2, int col2) {
	            return sum[row2 + 1][col2 + 1] - sum[row1][col2 + 1] - sum[row2 + 1][col1] + sum[row1][col1];
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
