package alite.leetcode.xx2;
/**
 * LEETCODE 240. SEARCH A 2D MATRIX II
LC address: Search a 2D Matrix II

Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
For example,

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.

Analysis:

假设matrix是m*n的，从右上角开始考虑，即matrix[0][n-1]。如果这个值比target小，说明当前行都不可能是target；如果比target大，那么当前列都不可能是target；如果等于target，那就返回true就可以。根据这个规律，每次我们考虑的数的位置只可能往下或者往左，所以时间复杂度是O(m + n)。当然，也可以从左下开始，往右上走，方法也是类似的。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        int i = 0;
        int j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            int cur = matrix[i][j];
            if (cur == target) {
                return true;
            }
            if (cur < target) {
                i += 1;
            } else {
                j -= 1;
            }
        }
        return false;
    }
}
 * @author het
 *
 */
public class L240 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
