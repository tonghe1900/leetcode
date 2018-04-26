package Leetcode600x;
/*
 * [LeetCode]Find the Derangement of An Array 
作者是 在线疯狂 发布于 2017年7月2日 在 LeetCode.
题目描述：
LeetCode 634. Find the Derangement of An Array

In combinatorial mathematics, a derangement is a permutation of the elements of a set, such that no element appears in its original position.

There's originally an array consisting of n integers from 1 to n in ascending order, you need to find the number of derangement it can generate.

Also, since the answer may be very large, you should return the output mod 109 + 7.

Example 1:

Input: 3
Output: 2
Explanation: The original array is [1,2,3]. The two derangements are [2,3,1] and [3,1,2].
Note:
n is in the range of [1, 106].

题目大意：
在组合数学中，错位排列是指所有元素均不在其原始位置的排列。

给定[1, 2 , ... , n]，求其错位排列的个数。

由于结果可能很大，结果对10^9 + 7取模

解题思路：
解法I 递推式

打表找规律

利用以下Python代码打表，输出n为1 ~ 10时的结果

import operator

class Helper(object):
    def __init__(self):
        self.dmap = {}

    def findDerangement(self, n):
        """
        :type n: int
        :rtype: int
        """
        return self.solve(list(range(1, n + 1)), list(range(1, n + 1)))

    def solve(self, n, p):
        if len(set(n + p)) == len(n) * 2:
            return reduce(operator.mul, range(1, len(n) + 1))
        tn, tp = tuple(n), tuple(p)
        if (tn, tp) in self.dmap: return self.dmap[(tn, tp)]
        ans = 0
        for x in range(len(n)):
            if n[x] != p[0]:
                ans += self.solve(n[:x] + n[x+1:], p[1:])
        self.dmap[(tn, tp)] = ans
        return ans

helper = Helper()
print map(helper.findDerangement, range(1, 11))
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
输出为：[0, 1, 2, 9, 44, 265, 1854, 14833, 133496, 1334961]

观察并得到递推式：

dp[n] = (dp[n - 1] + dp[n - 2]) * (n - 1)
公式推导过程可以参考：https://en.wikipedia.org/wiki/Derangement

错位排列可以理解成将标号为1~n的帽子分配给标号为1~n的人，每个人拿到的帽子标号都与其自身的标号不同。

假设第一个人选取了标号为i的帽子，此时可分两种情况讨论：

1. 第i个人选择第一顶帽子，则问题规模缩小为n - 2

2, 3, 4, ..., i-1, i+1, ... n 人
2, 3, 4, ..., i-1, i+1, ... n 帽
2. 第i个人不选第一顶帽子，则问题规模缩小为n - 1（相当于第i个人不能选择第1顶帽子）

2, 3, 4, ..., i-1, i, i+1, ..., n 人
2, 3, 4, ..., i-1, 1, i+1, ..., n 帽
Python代码：
class Solution(object):
    def findDerangement(self, n):
        """
        :type n: int
        :rtype: int
        """
        dp = [0, 1]
        for x in range(2, n + 1):
            dp.append(x * (dp[- 1] + dp[-2]) % (10**9 + 7))
        return dp[n - 1]
1
2
3
4
5
6
7
8
9
10
解法II 公式

d(n) = sigma( n! * (-1)^i / i! ) i∈[0, n]
Python代码：
class Solution(object):
    def findDerangement(self, n):
        """
        :type n: int
        :rtype: int
        """
        MOD = 10**9 + 7
        mul, sig = 1, (-1) ** n
        ans = 0
        for x in range(n, -1, -1):
            ans = (ans + mul * sig) % MOD
            mul = (mul * x) % MOD
            sig *= -1
        return ans % MOD
 */
public class Leetcode634 {

}
