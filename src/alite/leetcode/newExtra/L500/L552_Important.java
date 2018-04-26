package alite.leetcode.newExtra.L500;
/**
 * LeetCode 552 - Student Attendance Record II

https://leetcode.com/problems/student-attendance-record-ii
Given a positive integer n, return the number of all possible attendance records with length n,
 which will be regarded as rewardable. The answer may be very large, return it after mod 109 + 7.
A student attendance record is a string that only contains the following three characters:
'A' : Absent.
'L' : Late.
'P' : Present.
A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).
Example 1:
Input: n = 2
Output: 8 
Explanation:
There are 8 records with length 2 will be regarded as rewardable:
"PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
Only "AA" won't be regarded as rewardable owing to more than one absent times. 
Note: The value of n won't exceed 100,000.

https://discuss.leetcode.com/topic/86526/improving-the-runtime-from-o-n-to-o-log-n
Let f[i][j][k] denote the # of valid sequences of length i where:
There can be at most j A's in the entire sequence.
There can be at most k trailing L's.
We give the recurrence in the following code, which should be self-explanatory, and the final answer is f[n][1][2].
public int checkRecord(int n) {
    final int MOD = 1000000007;
    int[][][] f = new int[n + 1][2][3];


    f[0] = new int[][]{{1, 1, 1}, {1, 1, 1}};
    for (int i = 1; i <= n; i++)
        for (int j = 0; j < 2; j++)
            for (int k = 0; k < 3; k++) {
                int val = f[i - 1][j][2]; // ...P
                if (j > 0) val = (val + f[i - 1][j - 1][2]) % MOD; // ...A
                if (k > 0) val = (val + f[i - 1][j][k - 1]) % MOD; // ...L
                f[i][j][k] = val;
            }
    return f[n][1][2];
}
The runtime of this solution is clearly O(n), using linear space (which can be easily optimized to O(1) though). Now,
 let's see how to further improve the runtime.
In fact, if we treat f[i][][] and f[i-1][][] as two vectors, we can represent the recurrence of f[i][j][k] as follows:
f[i][0][0]   | 0 0 1 0 0 0 |   f[i-1][0][0]
f[i][0][1]   | 1 0 1 0 0 0 |   f[i-1][0][1]
f[i][0][2] = | 0 1 1 0 0 0 | * f[i-1][0][2]
f[i][1][0]   | 0 0 1 0 0 1 |   f[i-1][1][0]
f[i][1][1]   | 0 0 1 1 0 1 |   f[i-1][1][1]
f[i][1][2]   | 0 0 1 0 1 1 |   f[i-1][1][2]
Let A be the matrix above, then f[n][][] = A^n * f[0][][], where f[0][][] = [1 1 1 1 1 1]. The point of this approach is that we can compute A^n using exponentiating by squaring (thanks to @StefanPochmann for the name correction), which will take O(6^3 * log n) = O(log n) time. Therefore, the runtime improves to O(log n), which suffices to handle the case for much larger n, say 10^18.
Update: The final answer is f[n][1][2], which involves multiplying the last row of A^n and the column vector [1 1 1 1 1 1]. Interestingly, it is also equal to A^(n+1)[5][2] as the third column of A is just that vector

final int MOD = 1000000007;
final int M = 6;

int[][] mul(int[][] A, int[][] B) {
    int[][] C = new int[M][M];
    for (int i = 0; i < M; i++)
        for (int j = 0; j < M; j++)
            for (int k = 0; k < M; k++)
                C[i][j] = (int) ((C[i][j] + (long) A[i][k] * B[k][j]) % MOD);
    return C;
}


int[][] pow(int[][] A, int n) {
    int[][] res = new int[M][M];
    for (int i = 0; i < M; i++)
        res[i][i] = 1;
    while (n > 0) {
        if (n % 2 == 1)
            res = mul(res, A);
        A = mul(A, A);
        n /= 2;
    }
    return res;
}

public int checkRecord(int n) {
    int[][] A = {
            {0, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 1},
            {0, 0, 1, 1, 0, 1},
            {0, 0, 1, 0, 1, 1},
    };
    return pow(A, n + 1)[5][2];
}
https://discuss.leetcode.com/topic/86479/o-n-time-o-1-space-solution

X. DP
http://bookshadow.com/weblog/2017/04/16/leetcode-student-attendance-record-ii/
动态规划（Dynamic Programming）
利用dp[n][A][L]表示长度为n，包含A个字符'A'，以L个连续的'L'结尾的字符串的个数。
状态转移方程：
dp[n][0][0] = sum(dp[n - 1][0])
dp[n][0][1] = dp[n - 1][0][0]
dp[n][0][2] = dp[n - 1][0][1]
dp[n][1][0] = sum(dp[n - 1][0]) + sum(dp[n - 1][1])
dp[n][1][1] = dp[n - 1][1][0]
dp[n][1][2] = dp[n - 1][1][1]

初始令dp[1] = [[1, 1, 0], [1, 0, 0]]
由于dp[n]只和dp[n - 1]有关，因此上述转移方程可以使用滚动数组，将空间复杂度降低一维。
    private final int MOD = 1000000007;
    public long sum(int[] nums) {
        long ans = 0;
        for (int n : nums) ans += n;
        return ans % MOD;
    }
    
    public int checkRecord(int n) {
        int dp[][] = {{1, 1, 0}, {1, 0, 0}};
        for (int i = 2; i <= n; i++) {
            int ndp[][] = {{0, 0, 0}, {0, 0, 0}};
            ndp[0][0] = (int)sum(dp[0]);
            ndp[0][1] = dp[0][0];
            ndp[0][2] = dp[0][1];
            ndp[1][0] = (int)((sum(dp[0]) + sum(dp[1])) % MOD);
            ndp[1][1] = dp[1][0];
            ndp[1][2] = dp[1][1];
            dp = ndp;
        }
        return (int)((sum(dp[0]) + sum(dp[1])) % MOD);
    }


https://discuss.leetcode.com/topic/86507/simple-java-o-n-solution
static final int M = 1000000007;

public int checkRecord(int n) {
    long[] PorL = new long[n + 1]; // ending with P or L, no A
    long[] P = new long[n + 1]; // ending with P, no A
    PorL[0] = P[0] = 1; PorL[1] = 2; P[1] = 1;

    for (int i = 2; i <= n; i++) {
        P[i] = PorL[i - 1];
        PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
    }
    
    long res = PorL[n];
    for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
     long s = (PorL[i] * PorL[n - i - 1]) % M;
        res = (res + s) % M;
    }
    
    return (int) res;
}
 * @author het
 *
 */
public class L552_Important {
	public static int checkRecord(int n) {
	    final int MOD = 1000000007;
	    int[][][] f = new int[n + 1][2][3];


	    f[0] = new int[][]{{1, 1, 1}, {1, 1, 1}};
	    for (int i = 1; i <= n; i++)
	        for (int j = 0; j < 2; j++)
	            for (int k = 0; k < 3; k++) {
	                int val = f[i - 1][j][k]; // ...P
	                if (j > 0) val = (val + f[i - 1][j - 1][k]) % MOD; // ...A
	                if (k > 0) val = (val + f[i - 1][j][k - 1]) % MOD; // ...L
	                f[i][j][k] = val;
	            }
	    return f[n][1][2];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println(checkRecord(2));
	}
	
	
//、、	https://discuss.leetcode.com/topic/86526/improving-the-runtime-from-o-n-to-o-log-n
//		Let f[i][j][k] denote the # of valid sequences of length i where:
//		There can be at most j A's in the entire sequence.
//		There can be at most k trailing L's.
//		We give the recurrence in the following code, which should be self-explanatory, and the final answer is f[n][1][2].
		public int checkRecord(int n) {
		    final int MOD = 1000000007;
		    int[][][] f = new int[n + 1][2][3];


		    f[0] = new int[][]{{1, 1, 1}, {1, 1, 1}};
		    for (int i = 1; i <= n; i++)
		        for (int j = 0; j < 2; j++)
		            for (int k = 0; k < 3; k++) {
		                int val = f[i - 1][j][2]; // ...P
		                if (j > 0) val = (val + f[i - 1][j - 1][2]) % MOD; // ...A
		                if (k > 0) val = (val + f[i - 1][j][k - 1]) % MOD; // ...L
		                f[i][j][k] = val;
		            }
		    return f[n][1][2];
		}

}
