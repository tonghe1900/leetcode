package Leetcode600x;
/**
 * 693. Binary Number with Alternating Bits
DescriptionHintsSubmissionsDiscussSolution
Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have different values.

Example 1:
Input: 5
Output: True
Explanation:
The binary representation of 5 is: 101
Example 2:
Input: 7
Output: False
Explanation:
The binary representation of 7 is: 111.
Example 3:
Input: 11
Output: False
Explanation:
The binary representation of 11 is: 1011.
Example 4:
Input: 10
Output: True
Explanation:
The binary representation of 10 is: 1010.
Seen this question in a real interview before?



Approach #1: Convert to String [Accepted]
Intuition and Algorithm

Let's convert the given number into a string of binary digits. Then, we should simply check that no two adjacent digits are the same.


Complexity Analysis

Time Complexity: O(1)O(1). For arbitrary inputs, we do O(w)O(w) work, where ww is the number of bits in n. However, w \leq 32w≤32.

Space complexity: O(1)O(1), or alternatively O(w)O(w).

Approach #2: Divide By Two [Accepted]
Intuition and Algorithm

We can get the last bit and the rest of the bits via n % 2 and n // 2 operations. Let's remember cur, the last bit of n. If the last bit ever equals the last bit of the remaining, then two adjacent bits have the same value, and the answer is False. Otherwise, the answer is True.

Also note that instead of n % 2 and n // 2, we could have used operators n & 1 and n >>= 1 instead.


Complexity Analysis

Time Complexity: O(1)O(1). For arbitrary inputs, we do O(w)O(w) work, where ww is the number of bits in n. However, w \leq 32w≤32.

Space complexity: O(1)O(1).
 * @author tonghe
 *
 */
public class Leetcode693 {
	class Solution {
	    public boolean hasAlternatingBits(int n) {
	        String bits = Integer.toBinaryString(n);
	        for (int i = 0; i < bits.length() - 1; i++) {
	            if (bits.charAt(i) == bits.charAt(i+1)) {
	                return false;
	            }
	        }
	        return true;
	    }
	}
	
	
	
	
	class Solution {
	    public boolean hasAlternatingBits(int n) {
	        int cur = n % 2;
	        n /= 2;
	        while (n > 0) {
	            if (cur == n % 2) return false;
	            cur = n % 2;
	            n /= 2;
	        }
	        return true;
	    }
	}
}
