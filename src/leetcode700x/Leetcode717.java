package leetcode700x;
/**
 * 717. 1-bit and 2-bit Characters
DescriptionHintsSubmissionsDiscussSolution
We have two special characters. The first character can be represented by one bit 0. The second character can be represented by two bits (10 or 11).

Now given a string represented by several bits. Return whether the last character must be a one-bit character or not. The given string will always end with a zero.

Example 1:
Input: 
bits = [1, 0, 0]
Output: True
Explanation: 
The only way to decode it is two-bit character and one-bit character. So the last character is one-bit character.
Example 2:
Input: 
bits = [1, 1, 1, 0]
Output: False
Explanation: 
The only way to decode it is two-bit character and two-bit character. So the last character is NOT one-bit character.
Note:

1 <= len(bits) <= 1000.
bits[i] is always 0 or 1.
Seen this question in a real interview before?


Approach #1: Increment Pointer [Accepted]
Intuition and Algorithm

When reading from the i-th position, if bits[i] == 0, the next character must have 1 bit; else if bits[i] == 1, the next character must have 2 bits. We increment our read-pointer i to the start of the next character appropriately. At the end, if our pointer is at bits.length - 1, then the last character must have a size of 1 bit.

Python

class Solution(object):
    def isOneBitCharacter(self, bits):
        i = 0
        while i < len(bits) - 1:
            i += bits[i] + 1
        return i == len(bits) - 1
Java

class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int i = 0;
        while (i < bits.length - 1) {
            i += bits[i] + 1;
        }
        return i == bits.length - 1;
    }
}
Complexity Analysis

Time Complexity: O(N)O(N), where NN is the length of bits.

Space Complexity: O(1)O(1), the space used by i.

Approach #2: Greedy [Accepted]
Intuition and Algorithm

The second-last 0 must be the end of a character (or, the beginning of the array if it doesn't exist). Looking from that position forward, the array bits takes the form [1, 1, ..., 1, 0] where there are zero or more 1's present in total. It is easy to show that the answer is true if and only if there are an even number of ones present.

In our algorithm, we will find the second last zero by performing a linear scan from the right. We present two slightly different approaches below.

Python

class Solution(object):
    def isOneBitCharacter(self, bits):
        parity = bits.pop()
        while bits and bits.pop(): parity ^= 1
        return parity == 0
Java

class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        int i = bits.length - 2;
        while (i >= 0 && bits[i] > 0) i--;
        return (bits.length - i) % 2 == 0;
    }
}
Complexity Analysis

Time Complexity: O(N)O(N), where NN is the length of bits.

Space Complexity: O(1)O(1), the space used by parity (or i).

Analysis written by: @awice.
 * @author tonghe
 *
 */
public class Leetcode717 {

}
