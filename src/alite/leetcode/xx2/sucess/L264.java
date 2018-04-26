package alite.leetcode.xx2.sucess;
/**
 * 
 * LeetCode – Super Ugly Number (Java)
 
Write a program to find the nth super ugly number.

Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.

Note:
(1) 1 is a super ugly number for any given primes.
(2) The given numbers in primes are in ascending order.
(3) 0 < k < = 100, 0 < n < = 106, 0 < primes[i] < 1000.

Java Solution

Keep adding minimum values to results and updating the time value for the chosen prime number in each loop.

public int nthSuperUglyNumber(int n, int[] primes) {
    int[] times = new int[primes.length];
    int[] result = new int[n];
    result[0] = 1; // first is 1
 
    for (int i = 1; i < n; i++) {
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < primes.length; j++) {
            min = Math.min(min, primes[j] * result[times[j]]);
        }
 
        result[i] = min;
 
        for (int j = 0; j < times.length; j++) {
            if (result[times[j]] * primes[j] == min) {
                times[j]++;
            }
        }
    }
 
    return result[n - 1];
}
 * 
 * 
 * LEETCODE 264. UGLY NUMBER II
LC address: Ugly Number II

Write a program to find the n-th ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5,
 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.

Note that 1 is typically treated as an ugly number.

Analysis:

这道题就是LeetCode 313. Super Ugly Number的简化版本，毕竟那道题是super ugly。具体解法可以看我曾经写过的Super Ugly Number的解答，
当然这里的primes只有三个，完全不必要使用priorityqueue（用了反而超时了）,O(n)的space用来dp储存前n个ugly numbers，时间也是O(n)，因为每次dp找下一个ugly number只需要O(1)的操作。为了方便读者，我这里复制黏贴一下我之前Super Ugly Number的解答，稍作优化既可达到上文说的复杂度。

此解法是 O(nk) space, O(nk) time，k为primes的数量，这个例子中的primes我选取了[2,5,7]而不是[2,3,5]（懒得改了）。可以把prod优化成O(1)的space，而res和idx两个数组需要被保留。

这个解法是首先建立一个n*k的array，叫做prod吧；一个长度为n的array，叫做idx和一个长度为k的array，叫做res。我们定义prod[i][j]=res[i] * primes[j]，res[i]就是第i个ugly number，idx[i]是对应prod的每一列我们所考虑的数的index。每一次，我们要找下一个ugly number的话我们就考虑{res[i][idx[j]]| i = 0 to k-1}，找到其中最小的。这样讲可能比较抽象，举个例子，假设我们的primes = {2, 5, 7}，n = 6，那么我们的流程如下：

Step 1：这是初始情况，我们第一个ugly number是1，prod第一行依次为2*1, 5*1, 7*1，而我们的idx是0, 0, 0。

313_1

Step 2：当前我们最小的数是2，所以2是我们下一个ugly number，更新到res，那么prod[1][0]也相应更新为2*2，因为那一列的prime基数是2，而且res[1] = 2，相应地，我们idx的第一项也要增加，因为我们开始考虑prod第一列第二行，而不是第一列第一行。

313_2

Step 3：当前我们最小的数是4，所以4是我们下一个ugly number，更新到res，那么prod[2][0]也相应更新为2*4，因为那一列的prime基数是2，而且res[2] = 4，相应更新idx。之后几步类似。

313_3313_4313_5313_6

经理了这6个step之后，我们就找到了第6个ugly number，也就是8，我们的搜索也就停止了。当然如果我们再多走两步，我们就会有重复的数字出现（两个10），这种情况我们要记得考虑和剔除。

空间是O(nk)没得说了，时间是O(nk)是因为每一次我们需要检查k个idx对应的prod值，找到最小需要O(k)，找n次，总时间是O(nk)。这个方法相对前一种就更加简单高效，同时对于“从最小数开始操作”这个思路的运用更为精妙。

Solution:

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
28
29
30
public class Solution {
    public int nthUglyNumber(int n) {
        int[] res = new int[n];
        res[0] = 1;
        int[] prod = {2, 3, 5};
        int[] primes = {2, 3, 5};
        int[] idx = {0, 0, 0};
 
        for (int i = 1; i < n; i++) {
            int k = 0;
            if (prod[1] <= prod[0] && prod[1] <= prod[2]) {
                k = 1;
            }
            if (prod[2] <= prod[0] && prod[2] <= prod[1]) {
                k = 2;
            }
             
            int cur = prod[k];
            if (cur > res[i - 1]) {
                res[i] = cur;
            } else {
                i -= 1;
            }
             
            idx[k] += 1;
            prod[k] = res[idx[k]] * primes[k];
        }
        return res[n - 1];
    }
}
 * @author het
 *
 */
public class L264 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
