package alite.leetcode.xx3.extra;
/**
 * LeetCode 357 - Count Numbers with Unique Digits

https://www.hrwhisper.me/leetcode-count-numbers-unique-digits/
Given a non-negative integer n, count all numbers with unique digits, x, where 0 ≤ x < 10n.
Example:
n =1   10
n=2   9* 9
n=3   9*9*8
n=4    9*9*8*7

 n=10      9*9*8*7*....*1
 n=11       0
Given n = 2, return 91. (The answer should be the total numbers in the range of 0 ≤ x < 100, excluding [11,22,33,44,55,66,77,88,99])
题意：给定非负的整数n，求在 0 ≤ x < 10n  中，有多少每个位上的数字互不相同的数？ 如 n =2 时，范围为[0,100]， 共有91个数（除了11,22,33,44,55,66,77,88,99）
思路：
排列组合题。
https://leetcode.com/discuss/107945/java-dp-o-1-solution
public int countNumbersWithUniqueDigits(int n) {

    if( n == 0 ){
        return 1;
    }

    if( n == 1 ){
        return 10;
    }

    if( n >= 10 ){
        return 0;
    }

    int current = 81; // n == 2;  f(n) = f(n-1)*(11-n);
    int total = 91;   // n == 2;  
    for(int i = 3 ; i <= n; i++){
        current *= (11-i);
        total += current;
    }

    return total;
}
设i为长度为i的各个位置上数字互不相同的数。
i==1 : 1 0（0~9共10个数，均不重复）
i==2: 9 * 9 （第一个位置上除0外有9种选择，第2个位置上除第一个已经选择的数，还包括数字0，也有9种选择）
i ==3: 9* 9 * 8 （前面两个位置同i==2，第三个位置除前两个位置已经选择的数还有8个数可以用）
……
i== n: 9 * 9 * 8 *…… (9-i+2)
需要注意的是，9- i + 2 >0 即 i < 11，也就是i最大为10，正好把每个数都用了一遍。
so , 其实可以算出来然后打表的，然后速度就飞快→_→

    public int countNumbersWithUniqueDigits(int n) {

        n = Math.min(n,10);

        int[] dp = new int[n+1];

        dp[0] = 1;

        for(int i = 1;i<=n;i++){

            dp[i] = 9;

            for(int x = 9; x >= 9 - i + 2;x--){

                dp[i] *= x;

            }

        }

        int ans = 0;

        for(int i= 0;i<dp.length;i++) ans += dp[i];

        return ans;

    }
http://dullnull.org/?p=129

    public static int countNumbersWithUniqueDigits(int n) {

        int result = 0;

        for (int i = 1; i <= n; i++)

            result += getK(i);

        return result == 0 ? 1 : result;

    }

 

    public static int getK(int k) {

        if (k == 1)

            return 10;

        int val = 9;

        for (int i = 2; i <= k; i++)

            val *= (9 - i + 2);

        return val;

    }
http://blog.csdn.net/ebowtang/article/details/51658886
    int countNumbersWithUniqueDigits(int n) {
        int result=0;
        for(int i=1;i<=n;i++)
            result+=getfk(i);
        return result==0?1:result;
    }
    
    int getfk(int k)
    {
        if(k==1)
            return 10;
        int val=9;    
        for(int i=2;i<=k;i++)
            val*=(9-i+2);
        return val;
    }
};
https://leetcode.com/discuss/108119/java-concise-dp-solution
public int countNumbersWithUniqueDigits(int n) {
    if (n == 0) {
        return 1;
    } 
    int ret = 10, count = 9;
    for (int i = 2; i <= n; i++) {
        count *= 9-i+2;
        ret += count;
    }
    return ret;
}
    int countNumbersWithUniqueDigits(int n) {
        if ( n < 0 )  
            return 0;
        int result = 1;
        int multiplier = 9;
        n = min(n, 10);
        for (int i = 1; i <= n; i++) {
            result += multiplier;
            multiplier *= (i > 9 ? 0: (10 - i));
        }
        return result;
    }
http://bookshadow.com/weblog/2016/06/13/leetcode-count-numbers-with-unique-digits/
    def countNumbersWithUniqueDigits(self, n):
        """
        :type n: int
        :rtype: int
        """
        nums = [9]
        for x in range(9, 0, -1):
            nums += nums[-1] * x,
        return sum(nums[:n]) + 1

X.
https://leetcode.com/discuss/107981/backtracking-solution
The idea is to append one digit at a time recursively (only append digits that has not been appended before). Number zero is a special case, because we don't want to deal with the leading zero, so it is counted separately at the beginning of the program. The running time for this program is O(10!) worst case, or O(n!) if n < 10.
The OJ gives wrong answer when n = 0 and n = 1. The correct answer should be:
0, 1
1, 10
2, 91
3, 739
4, 5275
5, 32491
6, 168571
7, 712891
8, 2345851
9, 5611771
10 and beyond, 8877691
    public static int countNumbersWithUniqueDigits(int n) {
        if (n > 10) {
            return countNumbersWithUniqueDigits(10);
        }
        int count = 1; // x == 0
        long max = (long) Math.pow(10, n);

        boolean[] used = new boolean[10];

        for (int i = 1; i < 10; i++) {
            used[i] = true;
            count += search(i, max, used);
            used[i] = false;
        }

        return count;
    }

    private static int search(long prev, long max, boolean[] used) {
        int count = 0;
        if (prev < max) {
            count += 1;
        } else {
            return count;
        }

        for (int i = 0; i < 10; i++) {
            if (!used[i]) {
                used[i] = true;
                long cur = 10 * prev + i;
                count += search(cur, max, used);
                used[i] = false;
            }
        }

        return count;
    }
http://www.cnblogs.com/grandyang/p/5582633.html
最后我们来看题目提示中所说的回溯的方法，我们需要一个变量used，其二进制第i位为1表示数字i出现过，刚开始我们遍历1到9，对于每个遍历到的数字，现在used中标记已经出现过，然后在调用递归函数。在递归函数中，如果这个数字小于最大值，则结果res自增1，否则返回res。然后遍历0到9，如果当前数字没有在used中出现过，此时在used中标记，然后给当前数字乘以10加上i，再继续调用递归函数，这样我们可以遍历到所有的情况
 * @author het
 *
 */
public class LeetCode357 {
	public int countNumbersWithUniqueDigits(int n) {

	    if( n == 0 ){
	        return 1;
	    }

	    if( n == 1 ){
	        return 10;
	    }

	    if( n >= 10 ){
	        return 0;
	    }

	    int current = 81; // n == 2;  f(n) = f(n-1)*(11-n);
	    int total = 91;   // n == 2;  
	    for(int i = 3 ; i <= n; i++){
	        current *= (11-i);
	        total += current;
	    }

	    return total;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
