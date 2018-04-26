package Leetcode600x;
/**
 * 688. Knight Probability in Chessboard
DescriptionHintsSubmissionsDiscussSolution
On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K moves. The rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right square is (N-1, N-1).

A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.


Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece would go off the chessboard) and moves there.

The knight continues moving until it has made exactly K moves or has moved off the chessboard. Return the probability that the knight remains on the board after it has stopped moving.

Example:
Input: 3, 2, 0, 0
Output: 0.0625
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.
Note:
N will be between 1 and 25.
K will be between 0 and 100.
The knight always initially starts on the board.
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode688 {
//https://leetcode.com/problems/knight-probability-in-chessboard/description/
	
	https://leetcode.com/problems/knight-probability-in-chessboard/solution/
		
		
		
		class Solution {
	    public double knightProbability(int N, int K, int sr, int sc) {
	        double[][] dp = new double[N][N];
	        int[] dr = new int[]{2, 2, 1, 1, -1, -1, -2, -2};
	        int[] dc = new int[]{1, -1, 2, -2, 2, -2, 1, -1};

	        dp[sr][sc] = 1;
	        for (; K > 0; K--) {
	            double[][] dp2 = new double[N][N];
	            for (int r = 0; r < N; r++) {
	                for (int c = 0; c < N; c++) {
	                    for (int k = 0; k < 8; k++) {
	                        int cr = r + dr[k];
	                        int cc = c + dc[k];
	                        if (0 <= cr && cr < N && 0 <= cc && cc < N) {
	                            dp2[cr][cc] += dp[r][c] / 8.0;
	                        }
	                    }
	                }
	            }
	            dp = dp2;
	        }
	        double ans = 0.0;
	        for (double[] row: dp) {
	            for (double x: row) ans += x;
	        }
	        return ans;
	    }
	}



class Solution {
    public double knightProbability(int N, int K, int sr, int sc) {
        int[] dr = new int[]{-1, -1, 1, 1, -2, -2, 2, 2};
        int[] dc = new int[]{2, -2, 2, -2, 1, -1, 1, -1};

        int[] index = new int[N * N];
        int t = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (r * N + c == canonical(r, c, N)) {
                    index[r * N + c] = t;
                    t++;
                } else {
                    index[r * N + c] = index[canonical(r, c, N)];
                }
            }
        }

        double[][] T = new double[t][t];
        int curRow = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (r * N + c == canonical(r, c, N)) {
                    for (int k = 0; k < 8; k++) {
                        int cr = r + dr[k], cc = c + dc[k];
                        if (0 <= cr && cr < N && 0 <= cc && cc < N) {
                            T[curRow][index[canonical(cr, cc, N)]] += 0.125;
                        }
                    }
                    curRow++;
                }
            }
        }

        double[] row = matrixExpo(T, K)[index[sr*N + sc]];
        double ans = 0.0;
        for (double x: row) ans += x;
        return ans;
    }

    public int canonical(int r, int c, int N) {
        if (2*r > N) r = N-1-r;
        if (2*c > N) c = N-1-c;
        if (r > c) {
            int t = r;
            r = c;
            c = t;
        }
        return r * N + c;
    }
    public double[][] matrixMult(double[][] A, double[][] B) {
        double[][] ans = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    ans[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return ans;
    }
    public double[][] matrixExpo(double[][] A, int pow) {
        double[][] ans = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++) ans[i][i] = 1;
        if (pow == 0) return ans;
        if (pow == 1) return A;
        if (pow % 2 == 1) return matrixMult(matrixExpo(A, pow-1), A);
        double[][] B = matrixExpo(A, pow / 2);
        return matrixMult(B, B);
    }
}



}
