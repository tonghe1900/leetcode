package alite.leetcode.xx3;
/**
 * [LeetCode]Elimination Game 
作者是 在线疯狂 发布于 2016年8月28日 在 LeetCode.
题目描述：
LeetCode 390. Elimination Game

There is a list of sorted integers from 1 to n. Starting from left to right, remove the first number and every other number afterward until you reach the end of the list.

Repeat the previous step again, but this time from right to left, remove the right most number and every other number from the remaining numbers.

We keep repeating the steps again, alternating left to right and right to left, until a single number remains.

Find the last number that remains starting with a list of length n.

Example:

Input:
n = 9,
1 2 3 4 5 6 7 8 9
2 4 6 8
2 6
6

Output:
6
题目大意：
给定一个数字1到n组成的有序列表。从左边的第一个数字开始向右，每隔一位移除一个数字，直到列表末尾。

重复上面的步骤，但是这一次从右向左，移除最右侧的数字，然后从剩余的数字中每隔一位移除一个数字。

重复执行上述过程，从左到右、从右到左进行切换，直到只剩下一个数字为止。

寻找最后一个剩下的数字。

解题思路：
模拟题，时间复杂度O(log n) 空间复杂度O(1)。

根据题设要求的数字移除过程，可以发现每执行完一趟数字移除操作，列表中剩余相邻数字之间的差都会加倍。

因此解决问题的关键就是找到每一趟数字消除操作之后剩余数字的起点。

Python代码：
class Solution(object):
    def lastRemaining(self, n):
        """
        :type n: int
        :rtype: int
        """
        a = p = 1
        cnt = 0
        while n > 1:
            n /= 2
            cnt += 1
            p *= 2
            if cnt % 2:
                a += p / 2 + p * (n - 1)
            else:
                a -= p / 2 + p * (n - 1)
        return a
 

本文链接：http://bookshadow.com/weblog/2016/08/28/leetcode-elimination-game/
 * @author het
 *
 */
public class LeetCode390 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
