package alite.leetcode.newest;
/**
 * LeetCode 660 - Remove 9


https://leetcode.com/problems/remove-9
Start from integer 1, remove any integer that contains 9 such as 9, 19, 29...
So now, you will have a new integer sequence: 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, ...
Given a positive integer n, you need to return the n-th integer after removing. Note that 1 will be the first integer.
Example 1:
Input: 9
Output: 10

Hint: n will not exceed 9 x 10^8.
https://discuss.leetcode.com/topic/99195/one-line-java-solution
As the problem want to move every digit has 9, which means 1 2 3 4 5 6 7 8 10 11 12 13 14 15 16 17 18 20... That is once you count to 8, we need jump to 10( which luckily 9 in 9 based )... And when change number in 9 base, it will not appear 9 any more...
It only useful to move 9, and not suit for other digit, such as 8.. since in 8 base, will get list as this : 1 2 3 4 5 6 7 8 10 11.. will pass 9..


This is a radix problem.
Just change decimal to 9-based.
public int newInteger(int n) {
    return Integer.parseInt(Integer.toString(n, 9));
}
Of course, you can write it yourself.
public int newInteger(int n) {
 int ans = 0;
 int base = 1;
  
 while (n > 0){
  ans += n % 9 * base;
  n /= 9;
  base *= 10;
 }
 return ans;
}

因为删除的是最后一位9，所以实际上就是10进制转9进制的算法

https://discuss.leetcode.com/topic/99244/what-if-remove-number-7

http://www.cnblogs.com/pk28/p/7356218.html
    typedef long long ll;
    ll sum[11] = {0};
    ll d[11] = {0};
    void init() {
        int x = 1;
        sum [1] = 1;
        d[1] = 1;
        for (int i = 2; i <= 10; ++i) {
            d[i] = sum[i - 1] * 8 + (ll)pow(10.0,i-1);
            sum[i] = sum[i - 1] + d[i];
        }
    }
    int newInteger(int n) {
        init();
        ll x = 10,ans = 0;
        while (n > 9) {
            ll tmp = n;
            x = 0;
            for (int i = 1; i <= 10; ++i) {
                if (n  >= pow(10,i) - sum[i]) continue;
                else {
                    x = i;break;
                }
            }
            n -= pow(10.0, x - 1);
            n += sum[x - 1];
            ans += pow(10.0, x - 1);
        }
        if (n == 9) ans ++;
        return ans + n;
    }

 * @author het
 *
 */
public class LeetCode660 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
