package alite.leetcode.newExtra.L500;
/**
 * LeetCode 566 - Reshape the Matrix

https://leetcode.com/problems/reshape-the-matrix
In MATLAB, there is a very useful function called 'reshape', which can reshape a matrix into a new one with different size but keep its original data.
You're given a matrix represented by a two-dimensional array, and two positive integers r and c representing the row number and columnnumber of the wanted reshaped matrix, respectively.
The reshaped matrix need to be filled with all the elements of the original matrix in the same row-traversing order as they were.
If the 'reshape' operation with given parameters is possible and legal, output the new reshaped matrix; Otherwise, output the original matrix.
https://discuss.leetcode.com/topic/87851/java-concise-o-nm-time
public int[][] matrixReshape(int[][] nums, int r, int c) {
    int n = nums.length, m = nums[0].length, k = 0;
    if (r*c != n*m) return nums;
    int[][] res = new int[r][c];
    for (int i=0;i<r;i++) 
        for (int j=0;j<c;j++,k++) 
            res[i][j] = nums[k/m][k%m];
    return res;
}

https://discuss.leetcode.com/topic/87901/one-loop
We can use matrix[index / width][index % width] for both the input and the output matrix.
public int[][] matrixReshape(int[][] nums, int r, int c) {
    int m = nums.length, n = nums[0].length;
    if (r * c != m * n)
        return nums;
    int[][] reshaped = new int[r][c];
    for (int i = 0; i < r * c; i++)
        reshaped[i/c][i%c] = nums[i/n][i%n];
    return reshaped;
}
http://blog.csdn.net/u014688145/article/details/71023781
public int[][] matrixReshape(int[][] nums, int r, int c) {

        int row = nums.length;
        if (row == 0) return nums;
        int col = nums[0].length;
        if (col == 0) return nums;

        if (row * col != r * c) return nums;
        int[][] res = new int[r][c];
        for (int i = 0; i < row; i++){
            for (int j =0; j < col; j++){
                int x = i * col + j;
                int nr = x / c;
                int nc = x % c;
                res[nr][nc] = nums[i][j];
            }
        }
        return res;
    }
https://discuss.leetcode.com/topic/87853/easy-java-solution
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int m = nums.length, n = nums[0].length;
        if (m * n != r * c) return nums;
        
        int[][] result = new int[r][c];
        int row = 0, col = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[row][col] = nums[i][j];
                col++;
                if (col == c) {
                    col = 0;
                    row++;
                }
            }
        }
        
        return result;
    }
 * @author het
 *
 */
public class L566 {
	
//	In MATLAB, there is a very useful function called 'reshape', which can reshape a matrix into a new one with different size but keep its original data.
//	You're given a matrix represented by a two-dimensional array, and two positive integers r and c representing the row number and columnnumber of the wanted reshaped matrix, respectively.
//	The reshaped matrix need to be filled with all the elements of the original matrix in the same row-traversing order as they were.
//	If the 'reshape' operation with given parameters is possible and legal, output the new reshaped matrix; Otherwise, output the original matrix.
//	https://discuss.leetcode.com/topic/87851/java-concise-o-nm-time
	public int[][] matrixReshape(int[][] nums, int r, int c) {
	    int n = nums.length, m = nums[0].length, k = 0;
	    if (r*c != n*m) return nums;
	    int[][] res = new int[r][c];
	    for (int i=0;i<r;i++) 
	        for (int j=0;j<c;j++,k++) 
	            res[i][j] = nums[k/m][k%m];
	    return res;
	    
//	    for (int i=0;i<r;i++) 
//	        for (int j=0;j<c;j++,k++) 
//	            res[i][j] = nums[k/m][k%m];
//	    return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
