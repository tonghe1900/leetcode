package Leetcode600x;
/**
 * Imagine you have a special keyboard with the following keys:

Key 1: (A): Print one 'A' on screen.

Key 2: (Ctrl-A): Select the whole screen.

Key 3: (Ctrl-C): Copy selection to buffer.

Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.

Now, you can only press the keyboard for N times (with the above four keys), find out the maximum numbers of 'A' you can print on screen.

Example 1:
Input: N = 3
Output: 3
Explanation: 
We can at most get 3 A's on screen by pressing following key sequence:
A, A, A
Example 2:
Input: N = 7
Output: 9
Explanation: 
We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
Note:
1 <= N <= 50
Answers will be in the range of 32-bit signed integer.
 * @author tonghe
 *
 */
public class Leetcode651 {
//https://leetcode.com/articles/4-keys-keyboard/
	class Solution {
	    public int maxA(int N) {
	        int[] best = new int[N+1];
	        for (int k = 1; k <= N; ++k) {
	            best[k] = best[k-1] + 1;
	            for (int x = 0; x < k-1; ++x)
	                best[k] = Math.max(best[k], best[x] * (k-x-1));
	        }
	        return best[N];
	    }
	}
	
	
	
	class Solution {
	    public int maxA(int N) {
	        int[] best = new int[]{0, 1, 2, 3, 4, 5, 6, 9, 12,
	                               16, 20, 27, 36, 48, 64, 81};
	        int q = N > 15 ? (N - 11) / 5 : 0;
	        return best[N - 5*q] << 2 * q;
	    }
	}
}
