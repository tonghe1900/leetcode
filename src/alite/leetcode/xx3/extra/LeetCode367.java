package alite.leetcode.xx3.extra;
/**
 * LeetCode 367 - Valid Perfect Square

https://leetcode.com/problems/valid-perfect-square/
Given a positive integer num, write a function which returns True if num is a perfect square else False.
Note: Do not use any built-in library function such as sqrt.
Example 1:
Input: 16
Returns: True
Example 2:


Input: 14
Returns: False
X. Binary Search
https://www.hrwhisper.me/leetcode-valid-perfect-square/
题意：给定一个数n，要求不使用sqrt函数判断该数是否为完全平方数
思路：二分。。  L = 1 ,  R = num / 2 +1开始枚举即可。。。

    public boolean isPerfectSquare(int num) {

        long L = 1, R = (num >> 1) + 1;

  while (L <= R) {

   long  m = L + ((R - L) >> 1);

   long  mul = m * m;

   if (mul == num) return true;

   else if (mul > num) R = m - 1;

   else L = m + 1;

  }

  return false;

    }
http://blog.csdn.net/qq508618087/article/details/51762492
    bool isPerfectSquare(int num) {
        if(num < 1) return false;
        if(num == 1) return true;
        int left = 0, right = num/2;
        while(left <= right)
        {
            long mid = (left+right)/2;
            long val = mid*mid;
            if(val == num) return true;
            else if(val > num) right = mid-1;
            else left = mid+1;
        }
        return false;
    }
    bool isPerfectSquare(int num) {
        long left = 0, right = num;
        while (left <= right) {
            long mid = left + (right - left) / 2, t = mid * mid;
            if (t == num) return true;
            else if (t < num) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }
X. Newton's method
https://leetcode.com/discuss/58631/3-4-short-lines-integer-newton-every-language
http://bookshadow.com/weblog/2016/06/26/leetcode-valid-perfect-square/
    long r = x;
    while (r*r > x)
        r = (r + x/r) / 2;
    return r*r == x;
https://leetcode.com/discuss/58631/3-4-short-lines-integer-newton-every-language
Apparently, using only integer division for the Newton method works. And I guessed that if I start at x, the root candidate will decrease monotonically and never get too small.
int mySqrt(int x) {
    long long r = x;
    while (r*r > x)
        r = (r + x/r) / 2;
    return r;
}

    def isPerfectSquare(self, num):
        """
        :type num: int
        :rtype: bool
        """
        x = num
        while x * x > num:
            x = (x + num / x) / 2
        return x * x == num
https://leetcode.com/discuss/110638/a-square-number-is-1-3-5-7-java-code
This is a math problem：
1 = 1
4 = 1 + 3
9 = 1 + 3 + 5
16 = 1 + 3 + 5 + 7
25 = 1 + 3 + 5 + 7 + 9
36 = 1 + 3 + 5 + 7 + 9 + 11
....
so 1+3+...+(2n-1) = (2n-1 + 1)n/2 = nn
public boolean isPerfectSquare(int num) {
    int i = 1;
    while (num > 0) {
        num -= i;
        i += 2;
    }
    return num == 0;
}
Let x be the number of iterations needed to solve the problem, and let Σ be the sum from i = 1 to x. Σ(1 + 2i) = n => x + 2Σi = n => x + 2(x(x+1)) = n => 2x^2 + 3x = n => x = [-3 +/- sqrt(9 + 8n)]/4 => you can see that n is in a square root term, so the complexity should be O(sqrt(n)).
http://www.cnblogs.com/grandyang/p/5619296.html
这道题给了我们一个数，让我们判断其是否为完全平方数，那么显而易见的是，肯定不能使用brute force，这样太不高效了，那么最小是能以指数的速度来缩小范围，那么我最先想出的方法是这样的，比如一个数字49，我们先对其除以2，得到24，发现24的平方大于49，那么再对24除以2，得到12，发现12的平方还是大于49，再对12除以2，得到6，发现6的平方小于49，于是遍历6到12中的所有数，看有没有平方等于49的，有就返回true，没有就返回false

    bool isPerfectSquare(int num) {
        if (num == 1) return true;
        long x = num / 2, t = x * x;
        while (t > num) {
            x /= 2;
            t = x * x;
        }
        for (int i = x; i <= 2 * x; ++i) {
            if (i * i == num) return true;
        }
        return false;
    }

下面这种方法也比较高效，从1搜索到sqrt(num)，看有没有平方正好等于num的数：
    bool isPerfectSquare(int num) {
        for (int i = 1; i <= num / i; ++i) {
            if (i * i == num) return true;
        }
        return false;
    }

https://leetcode.com/discuss/110659/o-1-time-c-solution-inspired-by-q_rsqrt
    bool isPerfectSquare(int num) {
        if (num < 0) return false;
        int root = floorSqrt(num);
        return root * root == num;
    }

    int32_t floorSqrt(int32_t x) {
        double y=x; int64_t i=0x5fe6eb50c7b537a9;
        y = *(double*)&(i = i-(*(int64_t*)&y)/2);
        y = y * (3 - x * y * y) * 0.5;
        y = y * (3 - x * y * y) * 0.5;
        i = x * y + 1; return i - (i * i > x);
    }
From https://en.wikipedia.org/wiki/Fastinversesquare_root
The following code is the fast inverse square root implementation from Quake III Arena, stripped of C preprocessor directives, but including the exact original comment text:
 ...
 i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
 ...

You might also like

 * @author het
 *
 */
public class LeetCode367 {
	long r = x;
    while (r*r > x)
        r = (r + x/r) / 2;
    return r*r == x;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
