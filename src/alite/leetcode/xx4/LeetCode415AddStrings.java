package alite.leetcode.xx4;
/**
 * https://leetcode.com/problems/add-strings/
Given two non-negative numbers num1 and num2 represented as string, return the sum of num1 and num2.
Note:
The length of both num1 and num2 is < 5100.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
https://discuss.leetcode.com/topic/62310/straight-forward-java-10-main-lines-25ms
https://discuss.leetcode.com/topic/62270/easy-to-understand-java-solution/2
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0; i--, j--){
            int x = i < 0 ? 0 : num1.charAt(i) - '0';
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            sb.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        if(carry != 0)
            sb.append(carry);
        return sb.reverse().toString();
    }
http://bookshadow.com/weblog/2016/10/09/leetcode-add-strings/
    def addStrings(self, num1, num2):
        """
        :type num1: str
        :type num2: str
        :rtype: str
        """
        result = []
        carry = 0
        idx1, idx2 = len(num1), len(num2)
        while idx1 or idx2 or carry:
            digit = carry
            if idx1:
                idx1 -= 1
                digit += int(num1[idx1])
            if idx2:
                idx2 -= 1
                digit += int(num2[idx2])
            carry = digit > 9
            result.append(str(digit % 10))
        return ''.join(result[::-1])

https://discuss.leetcode.com/topic/65648/no-reverse-java-solution-21-ms

 * @author het
 *
 */
public class LeetCode415AddStrings {
	public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0; i--, j--){
            int x = i < 0 ? 0 : num1.charAt(i) - '0';
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            sb.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        if(carry != 0)
            sb.append(carry);
        return sb.reverse().toString();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
