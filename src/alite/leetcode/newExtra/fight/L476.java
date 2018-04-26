package alite.leetcode.newExtra.fight;
/**
 * LeetCode 476 - Number Complement

http://bookshadow.com/weblog/2017/01/08/leetcode-number-complement/
Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.
Note:
The given integer is guaranteed to fit within the range of a 32-bit signed integer.
You could assume no leading zero bit in the integer’s binary representation.
Example 1:
Input: 5
Output: 2
Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need to output 2.
Example 2:
Input: 1
Output: 0
Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is 0. So you need to output 0.

解法I 位运算（异或）
    def findComplement(self, num):
        """
        :type num: int
        :rtype: int
        """
        // mask = (1 << 1 + int(math.log(num, 2))) - 1
        mask = (1 << 1 + int(math.log(num, 2))) - 1
        return mask ^ num

解法II 按位取反并累加
    def findComplement(self, num):
        """
        :type num: int
        :rtype: int
        """
        return int(''.join(str(1 - int(x)) for x in bin(num)[2:]), 2)
 * @author het
 *
 */
public class L476 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
