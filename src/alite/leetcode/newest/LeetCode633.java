package alite.leetcode.newest;
/**
 * LeetCode 633 - Sum of Square Numbers

https://leetcode.com/problems/sum-of-square-numbers
Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.
Example 1:
Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5
Example 2:
Input: 3
Output: False
X. Two Pinters
https://discuss.leetcode.com/topic/94435/java-two-pointers-solution
    public boolean judgeSquareSum(int c) {
        if (c < 0) {
            return false;
        }
        int left = 0, right = (int)Math.sqrt(c);
        while (left <= right) {
            int cur = left * left + right * right;
            if (cur < c) {
                left++;
            } else if (cur > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }
X. Binary search
类似于二分查找。
从0 和sqrt(n)两端分别查找
https://leetcode.com/articles/sum-of-square-numbers/
Another method to check if c - a^2c−a
​2
​​  is a perfect square, is by making use of Binary Search. The method remains same as that of a typical Binary Search to find a number. The only difference lies in that we need to find an integer, midmid in the range [0, c - a^2][0,c−a
​2
​​ ], such that this number is the square root of c - a^2c−a
​2
​​ . Or in other words, we need to find an integer, midmid, in the range [0, c - a^2][0,c−a
​2
​​ ], such that midmidxmid = c - a^2mid=c−a
​2
​​ .
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            int b = c - (int)(a * a);
            if (binary_search(0, b, b))
                return true;
        }
        return false;
    }
    public boolean binary_search(long s, long e, int n) {
        if (s > e)
            return false;
        long mid = s + (e - s) / 2;
        if (mid * mid == n)
            return true;
        if (mid * mid > n)
            return binary_search(s, mid - 1, n);
        return binary_search(mid + 1, e, n);
    }
X.
https://discuss.leetcode.com/topic/94430/java-solution
public static Boolean check (int c){
        HashSet<Integer> hs = new HashSet<>();

        for (int i=0; i<=Math.sqrt(c); i++) {
            hs.add(i * i);
        }

        for (int i=0; i<=Math.sqrt(c); i++){
            if (hs.contains(c - (i*i)))
                return true;
        }
        return false;
    }
https://discuss.leetcode.com/topic/94428/java-3-liner
public static boolean judgeSquareSum(int c) {
  for (int i=0;i<=Math.sqrt(c);i++) 
    if (Math.floor(Math.sqrt(c-i*i)) == Math.sqrt(c-i*i)) return true;
  return false;
}  

    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            double b = Math.sqrt(c - a * a);
            if (b == (int) b)
                return true;
        }
        return false;
    }

Now, to determine, if the number c - a^2c−a
​2
​​  is a perfect square or not, we can make use of the following theorem: "The square of n^{th}n
​th
​​  positive integer can be represented as a sum of first nn odd positive integers." Or in mathematical terms:
n^2 = 1 + 3 + 5 + ... + (2*n-1) = \sum_{1}^{n} (2*i - 1)n
​2
​​ =1+3+5+...+(2∗n−1)=∑
​1
​n
​​ (2∗i−1).
To look at the proof of this statement, look at the L.H.S. of the above statement.
1 + 3 + 5 + ... + (2*n-1)=1+3+5+...+(2∗n−1)=
(2*1-1) + (2*2-1) + (2*3-1) + ... + (2*n-1)=(2∗1−1)+(2∗2−1)+(2∗3−1)+...+(2∗n−1)=
2*(1+2+3+....+n) - (1+1+...n times)=2∗(1+2+3+....+n)−(1+1+...ntimes)=
2*n*(n+1)/2 - n=2∗n∗(n+1)/2−n=
n*(n+1) - n=n∗(n+1)−n=
n^2 + n - n = n^2n
​2
​​ +n−n=n
​2
​​ 
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            int b =  c - (int)(a * a);
            int i = 1, sum = 0;
            while (sum < b) {
                sum += i;
                i += 2;
            }
            if (sum == b)
                return true;
        }
        return false;
    }

    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            for (long b = 0; b * b <= c; b++) {
                if (a * a + b * b == c)
                    return true;
            }
        }
        return false;
    }


    public boolean judgeSquareSum(int c) {
        for (int i = 2; i * i <= c; i++) {
            int count = 0;
            if (c % i == 0) {
                while (c % i == 0) {
                    count++;
                    c /= i;
                }
                if (i % 4 == 3 && count % 2 != 0)
                    return false;
            }
        }
        return c % 4 != 3;
    }
 * @author het
 *
 */
public class LeetCode633 {

}
