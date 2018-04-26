package Leetcode600x;
/**
 * 672. Bulb Switcher II
DescriptionHintsSubmissionsDiscussSolution
There is a room with n lights which are turned on initially and 4 buttons on the wall. After performing exactly m unknown operations towards buttons, you need to return how many different kinds of status of the n lights could be.

Suppose n lights are labeled as number [1, 2, 3 ..., n], function of these 4 buttons are given below:

Flip all the lights.
Flip lights with even numbers.
Flip lights with odd numbers.
Flip lights with (3k + 1) numbers, k = 0, 1, 2, ...
Example 1:
Input: n = 1, m = 1.
Output: 2
Explanation: Status can be: [on], [off]
Example 2:
Input: n = 2, m = 1.
Output: 3
Explanation: Status can be: [on, off], [off, on], [off, off]
Example 3:
Input: n = 3, m = 1.
Output: 4
Explanation: Status can be: [off, on, off], [on, off, on], [off, off, off], [off, on, on].
Note: n and m both fit in range [0, 1000].

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode672 {
//https://leetcode.com/problems/bulb-switcher-ii/solution/
	
	class Solution {
	    public int flipLights(int n, int m) {
	        Set<Integer> seen = new HashSet();
	        n = Math.min(n, 6);
	        int shift = Math.max(0, 6-n);
	        for (int cand = 0; cand < 16; ++cand) {
	            int bcount = Integer.bitCount(cand);
	            if (bcount % 2 == m % 2 && bcount <= m) {
	                int lights = 0;
	                if (((cand >> 0) & 1) > 0) lights ^= 0b111111 >> shift;
	                if (((cand >> 1) & 1) > 0) lights ^= 0b010101 >> shift;
	                if (((cand >> 2) & 1) > 0) lights ^= 0b101010 >> shift;
	                if (((cand >> 3) & 1) > 0) lights ^= 0b100100 >> shift;
	                seen.add(lights);
	            }
	        }
	        return seen.size();
	    }
	}
	
	
	
	
	
	class Solution {
	    public int flipLights(int n, int m) {
	        n = Math.min(n, 3);
	        if (m == 0) return 1;
	        if (m == 1) return n == 1 ? 2 : n == 2 ? 3 : 4;
	        if (m == 2) return n == 1 ? 2 : n == 2 ? 4 : 7;
	        return n == 1 ? 2 : n == 2 ? 4 : 8;
	    }
	}
}
