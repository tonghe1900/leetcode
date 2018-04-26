package alite.leetcode.xx3.extra;
/**
 * LeetCode 371 - Sum of Two Integers

https://leetcode.com/problems/sum-of-two-integers/
Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
Example:
Given a = 1 and b = 2, return 3.
http://bookshadow.com/weblog/2016/06/30/leetcode-sum-of-two-integers/
解法I 位运算（Bit Manipulation）异或 + 移位
http://www.geeksforgeeks.org/add-two-numbers-without-using-arithmetic-operators/
Sum of two bits can be obtained by performing XOR (^) of the two bits. 
Carry bit can be obtained by performing AND (&) of two bits.
Above is simple Half Adder logic that can be used to add 2 single bits. 
We can extend this logic for integers. If x and y don’t have set bits at same position(s), then bitwise XOR (^) of x and y gives the sum of x and y. To incorporate common set bits also, bitwise AND (&) is used. Bitwise AND of x and y gives all carry bits. We calculate (x & y) << 1 and add it to x ^ y to get the required result.

int Add(int x, int y)

{

    // Iterate till there is no carry  

    while (y != 0)

    {

        // carry now contains common set bits of x and y

        int carry = x & y;  


        // Sum of bits of x and y where at least one of the bits is not set

        x = x ^ y; 


        // Carry is shifted by one so that adding it to x gives the required sum

        y = carry << 1;

    }

    return x;

}
    public int getSum(int a, int b) {
        while (b != 0) {
            int c = a ^ b;
            b = (a & b) << 1;
            a = c;
        }
        return a;
    }

解法II 位运算（Bit Manipulation) 模拟加法
    public int getSum(int a, int b) {
        int r = 0, c = 0, p = 1;
        while ((a | b | c) != 0) {
            if (((a ^ b ^ c) & 1) != 0)
                r |= p;
            p <<= 1;
            c = (a & b | b & c | a & c) & 1;
            a >>>= 1;
            b >>>= 1;
        }
        return r;
    }

http://www.cnblogs.com/grandyang/p/5631814.html
    int getSum(int a, int b) {
        if (b == 0) return a;
        int sum = a ^ b;
        int carry = (a & b) << 1;
        return getSum(sum, carry);
    }

    int getSum(int a, int b) {
        return b == 0 ? a : getSum(a ^ b, (a & b) << 1);
    }
https://levy.gitbooks.io/leetcode/content/sum_of_two_integers.html
    public int getSum(int a, int b) {
        if(a == 0){
            return b;
        }
        if(b == 0){
            return a;
        }
        int sum = a ^ b;
        int carry = (a & b) <<1;
        return getSum(sum, carry);
    }

https://www.hrwhisper.me/leetcode-sum-two-integers/
首先要知道，异或也被称为“模2和” ，so 这题就是运用异或的位运算啦。
我们可以每次取最低位来计算, 然后每次右移一位，注意点有：
进位为两个数字1
负数的情况下，右移最高位补的是1 ,因此值得注意要取到什么时候为止。 java有一个无符号右移>>>高位补0，因此结束条件可以为a!=0 || b!=0

    public int getSum(int a, int b) {

        int ans = 0, carry = 0;

        for (int i = 0; a!=0 || b!=0; a >>>= 1, b >>>= 1, i++) {

            int lower_a = a & 1, lower_b = b & 1;

            ans |= (lower_a ^ lower_b ^ carry) << i;

            carry = (carry & lower_a) | (carry & lower_b) | (lower_a & lower_b);

        }

        return ans;

    }
 * @author het
 *
 */
public class LeetCode371 {
//	解法I 位运算（Bit Manipulation）异或 + 移位
//	http://www.geeksforgeeks.org/add-two-numbers-without-using-arithmetic-operators/
//	Sum of two bits can be obtained by performing XOR (^) of the two bits. 
//	Carry bit can be obtained by performing AND (&) of two bits.
//	Above is simple Half Adder logic that can be used to add 2 single bits. 
//	We can extend this logic for integers. If x and y don’t have set bits at same position(s), 
	//then bitwise XOR (^) of x and y gives the sum of x and y. To incorporate common set bits also, 
	//bitwise AND (&) is used. Bitwise AND of x and y gives all carry bits. 
	//We calculate (x & y) << 1 and add it to x ^ y to get the required result.

	int Add(int x, int y)

	{

	    // Iterate till there is no carry  

	    while (y != 0)

	    {

	        // carry now contains common set bits of x and y

	        int carry = x & y;  


	        // Sum of bits of x and y where at least one of the bits is not set

	        x = x ^ y; 


	        // Carry is shifted by one so that adding it to x gives the required sum

	        y = carry << 1;

	    }

	    return x;

	}
	    public int getSum(int a, int b) {
	        while (b != 0) {
	            int c = a ^ b;
	            b = (a & b) << 1;
	            a = c;
	        }
	        return a;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
