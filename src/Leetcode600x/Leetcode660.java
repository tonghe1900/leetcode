package Leetcode600x;
/**
 * 
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/zjucor/article/details/77152399

Start from integer 1, remove any integer that contains 9 such as 9, 19, 29...

So now, you will have a new integer sequence: 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, ...

Given a positive integer n, you need to return the n-th integer after removing. Note that 1 will be the first integer.

Example 1:

Input: 9
Output: 10
Hint: n will not exceed 9 x 10^8.



思路：就是转换成9进制，就可以跳过9这个数值了

The set of numbers without 9s is the same as the set of base-9 numbers, and they occur in the same order. The answer is therefore just the n-th base-9 number.



I try to explain why it works.
As the problem want to move every digit has 9, which means 1 2 3 4 5 6 7 8 10 11 12 13 14 15 16 17 18 20... That is once you count to 8, we need jump to 10( which luckily 9 in 9 based )... And when change number in 9 base, it will not appear 9 any more...
It only useful to move 9, and not suit for other digit, such as 8.. since in 8 base, will get list as this : 1 2 3 4 5 6 7 8 10 11.. will pass 9..


[java] view plain copy
public class Solution {  
    public int newInteger(int n) {  
        return Integer.valueOf(Integer.toString(n, 9));  
    }  
}  
 * @author tonghe
 *
 */
public class Leetcode660 {

}
