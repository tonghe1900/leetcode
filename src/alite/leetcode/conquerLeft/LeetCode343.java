package alite.leetcode.conquerLeft;
/**
 * LeetCode 343 - Integer Break

https://leetcode.com/problems/integer-break/
Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.
For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).
X. DP
https://leetcode.com/discuss/98143/java-dp-solution
public int integerBreak(int n) {
       int[] dp = new int[n + 1];
       dp[1] = 1;
       for(int i = 2; i <= n; i ++) {
           for(int j = 1; j < i; j ++) {
               dp[i] = Math.max(dp[i], (Math.max(j,dp[j])) * (Math.max(i - j, dp[i - j])));
           }
       }
       return dp[n];
    }
https://www.hrwhisper.me/leetcode-integer-break/

    def integerBreak(self, n):

        dp = [1] * (n + 1)

        for i in xrange(1, n + 1):

            for j in xrange(1, i + 1):

                if i + j <= n:

                    dp[i + j] = max(max(dp[i],i) * max(dp[j],j), dp[i + j])

        return dp[n]

X. DP
http://blog.csdn.net/liyuefeilong/article/details/51193423
题目大意是，给定给一个正整数n，将其分解成至少两个正整数的和，使得这些整数的积达到最大。返回最大的乘积。题目给出了两个案例。这里可以假设n不小于2。
看一些规律：
2 = 1 + 1 -> 1 * 1 = 1
3 = 1 + 2 -> 1 * 2 = 2
4 = 2 + 2 -> 2 * 2 = 4
5 = 2 + 3 -> 2 * 3 = 6
6 = 3 + 3 -> 3 * 3 = 9
7 = 3 + 4 -> 3 * 4 = 12
8 = 2 + 3 + 3 -> 2 * 3 * 3 = 18
9 = 3 + 3 + 3 -> 3 * 3 * 3 = 27
10 = 3 + 3 + 4 -> 3 * 3 * 4 = 36
11 = 3 + 3 + 3 + 2 -> 3 * 3 * 3 * 2 = 54
12 = 3 + 3 + 3 + 3 -> 3 * 3 * 3 * 3 = 81 
除开n <= 4的情形，其他情况可用以下规律来描述，对于n的最大分拆乘积，记为f[n]，则有：
f[n] = max(2 * f[n - 2], 3 * f[n - 3])
因此该题目可使用动态规划来完成。
    int integerBreak(int n) {
        if (n <= 3) return n - 1;
        vector<int> dp(n + 1, 0);
        dp[2] = 2, dp[3] = 3;
        for (int i = 4; i <= n; ++i)
            dp[i] = max(2 * dp[i - 2], 3 * dp[i - 3]);
        return dp[n];
    }

在n >3的情况下，我们处理一个数拆分成2，要么拆分成3，（4的话相当于2个2 ， 拆成1的话乘积太小了），所以就可以这样啦：

    def integerBreak(self, n):

        """

        :type n: int

        :rtype: int

        """

        if n <= 3: return n - 1

        dp = [1] * (n + 1)

        dp[2] = 2

        dp[3] = 3

        for i in xrange(4, n + 1):

            dp[i] = max(dp[i - 2] * 2, dp[i - 3] * 3)

        return dp[n]
X. Math
https://leetcode.com/discuss/98276/why-factor-2-or-3-the-math-behind-this-problem
Simplifying 2*(f-2) >= f to f >= 4 shows that breaking any factor f >= 4 into factors 2 and f-2 won't hurt the product. There, done :-P
Edit: Rephrased, maybe that's better:
If an optimal product contains a factor f >= 4, then you can replace it with factors 2 and f-2 without losing optimality, as 2*(f-2) = 2f-4 >= f. So you never need a factor greater than or equal to 4, meaning you only need factors 1, 2 and 3 (and 1 is of course wasteful and you'd only use it for n=2 and n=3, where it's needed).
可以说，拆成3的比拆成2的乘积大。 比如6的时候 2*2*2 < 3*3
我们希望能尽可能的拆成3，然后才是2.
所以，如果
n % 3 == 0:  那么全部拆成3
n % 3 == 1:  2个2剩下的为3    4*3^(x-1) > 1*3^x
n % 3 == 2:  1个2剩下的为3


    def integerBreak(self, n):


        if n <= 3 :return n-1


        mod = n % 3


        if mod == 0: return pow(3, n / 3)


        if mod == 1: return 4 * pow(3, (n - 4) / 3)


        return 2 * pow(3, n / 3)


https://leetcode.com/discuss/98144/java-o-n-dp-solution-store-and-reuse-products

This is an O(n) solution, the idea is to store all previously calculated product, note any n>4 will guarantee to have a factor of 3. Modifed per suggestion of @jianbao.tao and @ericxliu, Thank you!
public int integerBreak(int n) {
    if (n <= 2) return 1;
    if (n == 3) return 2;
    if (n == 4) return 4;
    int[] p = new int[n+1];
    p[2] = 2;
    p[3] = 3;
    p[4] = 4;
    for (int i = 5; i <= n; ++i) {
        p[i] = 3 * p[i-3];
    }
    return p[n];
}
Why the max product of any n>4 must contain a factor of 3? 
1. It can't contain any factor x that is >= 5, o.w., we can further increase the max product by decomposing x, as the decomposed x when x>=5 is strictly greater than x;
2. Out of 1, 2, 3, 4, we know 1 won't be a factor of n when n>4, if n is an odd number, 3 must be there as a factor (2 and 4 can't add up to an odd number); 
3. Now say n is an even number (n>4) and only has factor of 2 and 4, we can always split a 6 to3X3, which is better than 2X2X2.
Therefore, the max product of any n (n>4) must contain a factor of 3. The recurrence relation holds.
Further, as it holds for all n (n>4), we will be only using 3 as factor for n (n>4), we keep subtracting 3 until n<=4, and adopt the remaining factor. This leads to the closed form answer:
public int integerBreak(int n) {
    if (n <= 2) return 1;
    if (n == 3) return 2;
    if (n % 3 == 0) return (int)Math.pow(3, (n/3));
    else if (n % 3 == 1) return 4 * (int)Math.pow(3, (n-4)/3);
    else return 2 * (int)Math.pow(3, (n-2)/3);
}
As for the complexity of the close form solution, it depends on the implementation of the build-in pow, it could be O(logn) (as a simple O(logn) implementation exists), but not necessarily. The build-in pow could be better than that by using caching or bit level manipulation. I don’t know the answer though.
http://blog.csdn.net/liyuanbhu/article/details/51198124
作为一道编程题，这道题还是很简单的。简单的观察就能知道拆出足够多的 3 就能使得乘积最大。
int integerBreak(int n)
{
    if(n == 2) return 1;
    if(n == 3) return 2;
    int ret = 1;
    while( n>4 )
    {
        ret *= 3;
        n -= 3;
    }
    return ret * n;
}
这道题的难点其实在于证明为什么拆出足够多的 3 就能使得乘积最大。下面我就试着证明一下。
首先证明拆出的因子大于 4 是不行的。设 x 是一个因子，x>4，那么可以将这个因子再拆成两个因子 x−2 和 2，易证 (x−2)×2>x。所以不能有大于 4 的因子。
4 这个因子也是可有可无的，4=2+2，4=2×2。因此 4 这个因子可以用两个 2 代替。
除非没有别的因子可用，1 也不能选作因子。一个数 x 当它大于 3 时，有 (x−2)×2>(x−1)×1。
这样呢，就只剩下 2 和 3 这两个因子可以选了。下面再证明 3 比 2 好。
一个数 x=3m+2n，那么 f=3m×2n=3m×2x−3m2 可以对它取个对数。 
其中 ln3−32ln2>0 所以 f 是 m 的增函数，也就是说 m 越大越好。所以 3 越多越好。
再多说一句，如果拆出的因子不限于整数的话，可以证明e=2.718… 是最佳的选择。感兴趣的可以试着证明一下。
 * @author het
 *  dp[i] = Math.max(dp[i], (Math.max(j,dp[j])) * (Math.max(i - j, dp[i - j])));
 *p[i] = Math.max(dp[i], (Math.max(j,dp[j])) * (Math.max(i - j, dp[i - j])));
 */
public class LeetCode343 {
	public static int integerBreak(int n) {
	       int[] dp = new int[n + 1];
	       dp[1] = 1;
	       for(int i = 2; i <= n; i ++) {
	           for(int j = 1; j < i; j ++) {
	               dp[i] = Math.max(dp[i], (Math.max(j,dp[j])) * (Math.max(i - j, dp[i - j])));
	           }
	       }
	       return dp[n];
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         System.out.println(integerBreak(10));
	}

}
