package alite.leetcode.newest;
/**
 * LeetCode 664 - Strange Printer


https://leetcode.com/problems/strange-printer
There is a strange printer with the following two special requirements:
1. The printer can only print a sequence of the same character each time.
2. At each turn, the printer can print new characters starting from and ending at any places, and will cover the original existing characters.

Given a string consists of lower English letters only, your job is to count the minimum number of turns the printer needed in order to print it.
Example 1:
Input: "aaabbb"
Output: 2
Explanation: Print "aaa" first and then print "bbb".

Example 2:
Input: "aba"
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the existing character 'a'.

Hint: Length of the given string will not exceed 100.

https://discuss.leetcode.com/topic/100137/java-solution-dp
what the algorithms doing is
1. it sets basic print to 1 per character 2.it sets [i][i] to 1, which means any substring start from i to i (basically a single char).
2. it add 1 to the printing needs as soon as there is extra char. after [i]loop and [j]loop.
3. then it checks substring between [i] and [j] which is [k] then it minus the printing needs as soon as it found a same char. (it uses the data from last run so basically it is checking one char per turn)
4. [i] is decreasing. [j] is increasing. which means it is checking from the middle with k running between i and j.
5. it uses the Math.min to check wether [i][j] value (which can be lower if there is alot of character common from start to end) and take the lower value.
6. return the substring [0] [n-1] , i.e. the whole string value back.
When i saw the problem, my instincts told me it is recursive pattern. This is clever execution of recursion into iteration and use keys avoid repetitive calculations.
    public int strangePrinter(String s) {
        int n = s.length();
   
             if (n == 0) return 0;
        int[][] dp = new int[101][101];
        for (int i = 0; i < n; i++) dp[i][i] = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                dp[j][j + i] = i + 1;
                for (int k = j + 1; k <= j + i; k++) {
                    int temp = dp[j][k - 1] + dp[k][j + i];
                    if (s.charAt(k - 1) == s.charAt(j + i)) temp--;
                    dp[j][j + i] = Math.min(dp[j][j + i], temp);
                }
            }
        }
        return dp[0][n - 1];
    }
https://discuss.leetcode.com/topic/100240/java-o-n-3-dp-solution-with-explanation-and-simple-optimization
The problem wants us to find the number of ways to do something without giving specific steps like how to achieve it. This can be a typical signal that dynamic programming may come to help.
dp[i][j] stands for the minimal turns we need for string from index i to index j.
So we have
* dp[i][i] = 1: we need 1 turn to paint a single character.
* dp[i][i + 1]
    * dp[i][i + 1] = 1 if s.chartAt(i) == s.charAt(i + 1)
    * dp[i][i + 1] = 2 if s.chartAt(i) != s.charAt(i + 1)
Then we can iteration len from 2 to possibly n. For each iteration, we iteration start index from 0 to the farthest possible.
* The maximum turns for dp[start][start + len] is len + 1, i.e. print one character each time.
* We can further divide the substring to two parts: start -> start+k and start+k+1 -> start+len. It is something as following:index |start  ...  start + k| |start + k + 1 ... start + len|
* char  |  a    ...       b   | |      c       ...      b     |
* 
    * As shown above, if we have s.charAt(start + k) == s.charAt(start + len), we can make it in one turn when we print this character (i.e. b here)
    * This case we can reduce our turns to dp[start][start + k] + dp[start + k + 1][start + len] - 1
    public int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
            if (i < n - 1) {
                dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 1 : 2;
            }
        }
        
        for (int len = 2; len < n; len++) {
            for (int start = 0; start + len < n; start++) {
                dp[start][start + len] = len + 1;
                for (int k = 0; k < len; k++) {
                    int temp = dp[start][start + k] + dp[start + k + 1][start + len];
                    dp[start][start + len] = Math.min(
                        dp[start][start + len],
                        s.charAt(start + k) == s.charAt(start + len) ? temp - 1 : temp
                    );
                }
            }
        }
        
        return dp[0][n - 1];
    }
Time complexity is O(n^3)
Some simple optimization. Consecutive repeating characters do not affect our printing as we can always print them together. i.e aaabbb is equivalent with ab. So we can reduce the string first which somehow reduce n
StringBuilder sb = new StringBuilder();
for (int i = 0; i < s.length(); i++) {
    if (i > 0 && s.charAt(i) == s.charAt(i - 1)) {
        continue;
    }
    sb.append(s.charAt(i));
}
s = sb.toString();
This helps reduce running time from 60ms to 53ms.
https://discuss.leetcode.com/topic/100173/java-dp-solution
We start with a string of len n. This string can be decomposed to two substrings of len n-1 and len 1. There are only two possible start indexes of the substring with len n-1. If the initial character of two substrings are same, then we can have one less turn. Each substring can be decomposed further until reaching the boundary case, a substring with len 1.
So basically, this DP solution caches all possible decomposition cases from substring of len 1 starting from any possible index. Then we build the string from the scratch until we are able to build the whole input string.
    public int strangePrinter(String s) {
        if(s.isEmpty()) return 0;
        
        int[][] dp = new int[100][100];

        //boundary case, substring with len 1, i.e. char at each index
        for(int i = 0; i < 100; i++){
            dp[i][i] = 1;
        }
        
        for(int len = 2; len <= s.length(); len++){
            for(int start = 0; start + len <= s.length(); start++){
                dp[start][start+len-1] = len;
                for(int split = start + 1; split <= start + len -1; split++){
                    int result = dp[start][split-1] + dp[split][start + len -1];
                    if(s.charAt(start) == s.charAt(split)){
                        result --;
                    }
                    dp[start][start+len-1] = Math.min(result, dp[start][start+len-1]);
                }
            }
        }
        
        
        return dp[0][s.length()-1];
    }

 * @author het
 *
 */
public class LeetCode664 {
	
	
	public int strangePrinter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
            if (i < n - 1) {
                dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 1 : 2;
            }
        }
        
        for (int len = 2; len < n; len++) {
            for (int start = 0; start + len < n; start++) {
                dp[start][start + len] = len + 1;
                for (int k = 0; k < len; k++) {
                    int temp = dp[start][start + k] + dp[start + k + 1][start + len];
                    dp[start][start + len] = Math.min(
                        dp[start][start + len],
                        s.charAt(start + k) == s.charAt(start + len) ? temp - 1 : temp
                    );
                }
            }
        }
        
        return dp[0][n - 1];
    }
//Time complexity is O(n^3)
//Some simple optimization. Consecutive repeating characters do not affect our printing as we can always print them together. i.e aaabbb is equivalent with ab. So we can reduce the string first which somehow reduce n
//StringBuilder sb = new StringBuilder();
//for (int i = 0; i < s.length(); i++) {
//    if (i > 0 && s.charAt(i) == s.charAt(i - 1)) {
//        continue;
//    }
//    sb.append(s.charAt(i));
//}
//s = sb.toString();
//This helps reduce running time from 60ms to 53ms.

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
