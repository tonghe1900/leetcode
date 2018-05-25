package alite.leetcode.newExtra.fight;
/**
 * LeetCode 474 - Ones and Zeroes

https://leetcode.com/problems/ones-and-zeroes/
In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with strings
 consisting of only 0sand 1s.
Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can be used at most once.
Note:
The given numbers of 0s and 1s will both not exceed 100
The size of given string array won't exceed 600.
Example 1:
Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
Output: 4

Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
Example 2:


Input: Array = {"10", "0", "1"}, m = 1, n = 1
Output: 2

Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
https://discuss.leetcode.com/topic/71417/java-iterative-dp-solution-o-mn-space
https://discuss.leetcode.com/topic/71459/java-28ms-solution
Time Complexity: O(kl + kmn), where k is the length of input string array and l is the average length of a string within the array.
The problem can be interpreted as: What's the max number of str can we pick from strs with limitation of m "0"s and n "1"s. 
Thus we can define dp[i][j] stands for max number of str can we pick from strs with limitation of i "0"s and j "1"s.
 For each str, assume it has a "0"s and b "1"s, we update the dp array iteratively and set dp[i][j] = Math.max(dp[i][j], dp[i - a][j - b] + 1). So and the end, dp[m][n] is the answer.
    dp[k][i][j] = max(dp[k-1][i][j], dp[k-1][i-count[0]][j-count[1]])  
    
    
    
    public static int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
           int[] count = count(str); 
            for (int i = m; i >= count[0]; i--) { // this iteration sequence is very important
                for (int j = n; j >= count[1]; j--) {   // this iteration sequence is very important
                    dp[i][j] = Math.max(dp[i][j], dp[i - count[0]][j - count[1]] + 1);
                }
            }
        }
        return dp[m][n];
    }
public int findMaxForm(String[] strs, int m, int n) {
    int[][] dp = new int[m+1][n+1];
    for (int s = strs.length-1; s>=0;s--) {
        int[] count = count(strs[s]);
        for (int i=m;i>=0;i--) 
            for (int j=n;j>=0;j--) 
                if (i >= count[0] && j >= count[1]) 
                   dp[i][j] = Math.max(1 + dp[i-count[0]][j-count[1]], dp[i][j]);
    }
    return dp[m][n];
}
    
public int[] count(String str) {
    int[] res = new int[]{0,0};
    for (int i=0;i<str.length();i++) {
        if (str.charAt(i) == '0') res[0]++;
        else res[1]++;
    }
    return res;
 }
http://bookshadow.com/weblog/2016/12/11/leetcode-ones-and-zeroes/
二维01背包问题（Knapsack Problem）
状态转移方程：
for s in strs:
    zero, one = s.count('0'), s.count('1')
    for x in range(m, zero - 1, -1):
        for y in range(n, one - 1, -1):
            dp[x][y] = max(dp[x - zero][y - one] + 1, dp[x][y])
上式中，dp[x][y]表示至多使用x个0，y个1可以组成字符串的最大数目
    public int findMaxForm(String[] strs, int m, int n) {
        int dp[][] = new int[m + 1][n + 1];
        int ans = dp[0][0] = 0;
        for (String s : strs) {
            int zero = 0, one = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0') {
                    zero++;
                } else {
                    one++;
                }
            }
            for (int i = m; i > zero - 1; i--) {
                for (int j = n; j > one - 1; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zero][j - one] + 1);
                }
            }
        }
        return dp[m][n];
    }

X. DP 2
https://discuss.leetcode.com/topic/71432/java-memoization-and-accepted-dp-solutions-with-explanations
A state of my DP (int i, int numOfZeros, int numOfOnes) describes the maximum number of strings we can construct starting from index 'i' by having numOfZeros zeros and numOfOnes ones.
There are two simple transition functions from upper state to lower state.
First transition is skipping the ith string and just taking the maximum value we can construct starting from i-1 th string.
Second transition is constructing current string (ith string) then adding maximum number of strings that can be constructed starting from i-1 th string by the rest of ones and zeros (numOfZeros - pair[i][0] and numOfOnes-pair[i][1]).
The value for the current state is the maximum of values of the two transaction. Finally the answer is the value of state that describes the number of strings that can be constructed starting from the last(or the first,actually does not matter) index of the input string by m zeros and n ones. In other words just return dp[strs.length-1][m][n];
    public int findMaxForm(String[] strs, int m, int n) {
        if(strs == null || strs.length == 0 || (m == 0 && n == 0)) return 0;
        int dp [][][] = new int[strs.length][m+1][n+1];
        int [][] pairs = new int[strs.length][2];
        for(int i = 0;i<strs.length;i++){
            for(int j = 0;j<strs[i].length();j++){
                char ch  = strs[i].charAt(j);
                if(ch == '0') pairs[i][0]++;
                else pairs[i][1]++;
            }
        }
        for(int zeros =  pairs[0][0];zeros<=m;zeros++){
               for(int ones = pairs[0][1];ones<=n;ones++){
                   dp[0][zeros][ones] = 1;
               }
        } 
        for(int i  = 1;i<strs.length;i++){
           for(int zeros =  0;zeros<=m;zeros++){
               for(int ones = 0;ones<=n;ones++){
                   dp[i][zeros][ones] = dp[i-1][zeros][ones];
               }
           }
           for(int zeros =  pairs[i][0];zeros<=m;zeros++){
               for(int ones = pairs[i][1];ones<=n;ones++){
                   dp[i][zeros][ones] = Math.max(dp[i-1][zeros][ones], 1+dp[i-1][zeros-pairs[i][0]][ones-pairs[i][1]]);
               }
           }
        }
        return dp[strs.length-1][m][n];
    }

X. dfs + cache
https://discuss.leetcode.com/topic/71432/java-memoization-and-accepted-dp-solutions-with-explanations
the first thing we have to do, is to turn the array of string into array of pairs. The ith pair contains
 number of zeros and ones in ith string. Next step is to determine how many pairs from the array we can cover 
 by m and n;
The strightforward idea is backtracking. So we can just try out covering strings starting from different
 positions and maximize the result
Time and space complexity of the solution is O(n*m*pairs.length)
    Integer memo[][][];
    public int findMaxForm(String[] strs, int m, int n) {
        if(strs == null || strs.length == 0 || (m == 0 && n == 0)) return 0;
        memo = new Integer[m+1][n+1][strs.length];
        int [][] pairs = new int[strs.length][2];
        for(int i = 0;i<strs.length;i++){
            for(int j = 0;j<strs[i].length();j++){
                char ch  = strs[i].charAt(j);
                if(ch == '0') pairs[i][0]++;
                else pairs[i][1]++;
            }
        }
        return go(pairs, 0, m, n);
    }
    
    public int go(int pairs[][], int s, int m, int n){
        if(s >= pairs.length) return 0;
        if(memo[m][n][s] != null) return memo[m][n][s];
        int count = 0;
        for(int i = s;i<pairs.length;i++){
            int dm = m - pairs[i][0];
            int dn = n - pairs[i][1];
            if(dm >= 0 && dn >=0) {
                count = Math.max(count, 1+go(pairs, i+1, dm, dn));
            }
        }
        memo[m][n][s] = count;
        return count;
    }
 * @author het
 *
 */
public class L474 {
	  public static int findMaxForm(String[] strs, int m, int n) {
	        int[][] dp = new int[m + 1][n + 1];
	        for (String str : strs) {
	           int[] count = count(str); 
	            for (int i = m; i >= count[0]; i--) { // this iteration sequence is very important
	                for (int j = n; j >= count[1]; j--) {   // this iteration sequence is very important
	                    dp[i][j] = Math.max(dp[i][j], dp[i - count[0]][j - count[1]] + 1);
	                }
	            }
	        }
	        return dp[m][n];
	    }
//	public int findMaxForm(String[] strs, int m, int n) {
//	    int[][] dp = new int[m+1][n+1];
//	    for (int s = strs.length-1; s>=0;s--) {
//	        int[] count = count(strs[s]);
//	        for (int i=m;i>=0;i--) 
//	            for (int j=n;j>=0;j--) 
//	                if (i >= count[0] && j >= count[1]) 
//	                   dp[i][j] = Math.max(1 + dp[i-count[0]][j-count[1]], dp[i][j]);
//	    }
//	    return dp[m][n];
//	}
	    
	public int[] count(String str) {
	    int[] res = new int[]{0,0};
	    for (int i=0;i<str.length();i++) {
	        if (str.charAt(i) == '0') res[0]++;
	        else res[1]++;
	    }
	    return res;
	 }
}
