package alite.leetcode.xx2.sucess.before;
/**
 * LeetCode 233 - Number of Digit One

[LeetCode] Number of Digit One 数字1的个数 - Grandyang - 博客园
Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.
For example:
Given n = 13,
Return 6, because digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.
Hint:
Beware of overflow.
1的个数          含1的数字                                                                        数字范围
1                   1                                                                                     [1, 9]
11                 10  11  12  13  14  15  16  17  18  19                              [10, 19]
1                   21                                                                                   [20, 29]
1                   31                                                                                   [30, 39]
1                   41                                                                                   [40, 49]
1                   51                                                                                   [50, 59]
1                   61                                                                                   [60, 69]
1                   71                                                                                   [70, 79]
1                   81                                                                                   [80, 89]
1                   91                                                                                   [90, 99]
11                 100  101  102  103  104  105  106  107  108  109          [100, 109]
21                 110  111  112  113  114  115  116  117  118  119             [110, 119]
11                 120  121  122  123  124  125  126  127  128  129          [120, 129]
通过上面的列举我们可以发现，100以内的数字，除了10-19之间有11个‘1’之外，其余都只有1个。如果我们不考虑[10, 19]区间上那多出来的10个‘1’的话，那么我们在对任意一个两位数，十位数上的数字(加1)就代表1出现的个数，这时候我们再把多出的10个加上即可。比如56就有(5+1)+10=16个。如何知道是否要加上多出的10个呢，我们就要看十位上的数字是否大于等于2，是的话就要加上多余的10个'1'。那么我们就可以用(x+8)/10来判断一个数是否大于等于2。对于三位数也是一样，除了[110, 119]之间多出的10个数之外，其余的每10个数的区间都只有11个‘1’，那么还是可以用相同的方法来判断并累加1的个数

https://leetcode.com/discuss/64962/java-python-one-pass-solution-easy-to-understand
The idea is to calculate occurrence of 1 on every digit. There are 3 scenarios, for example
if n = xyzdabc
and we are considering the occurrence of one on thousand, it should be:
(1) xyz * 1000                     if d == 0
(2) xyz * 1000 + abc + 1           if d == 1
(3) xyz * 1000 + 1000              if d > 1
iterate through all digits and sum them all will give the final answer
public int countDigitOne(int n) {

    if (n <= 0) return 0;
    int q = n, x = 1, ans = 0;
    do {
        int digit = q % 10;
        q /= 10;
        ans += q * x;
        if (digit == 1) ans += n % x + 1;
        if (digit >  1) ans += x;
        x *= 10;
    } while (q > 0);
    return ans;
}
https://leetcode.com/discuss/58868/easy-understand-java-solution-with-detailed-explaination
Very Easy to Understand Java Solution 0ms. 附中文解释


解法I：预处理+从高位向低位枚举1的出现次数
预处理数组ones，ones[x]表示[0, 10 ^ x)范围内包含的1的个数

由高位向低位依次遍历数字n的每一位digit

记digit右边（低位）数字片段为n，size为当前位数，cnt为1的总数

若digit > 1，则cnt += digit * ones[size - 1] + 10 ^ size

若digit = 1，则cnt += n + ones[size - 1] + 1
int countDigitOne(int n) {
    long long int res(0);
    int highn(n), lowc(1), lown(0);
    while(highn > 0){
        int curn = highn % 10;
        highn = highn / 10;
        if (1 == curn) {
            //higher: 0~(highn-1);  lower:  0 ~ (lowc-1)
            res += highn * lowc;
            //higher: highn ~ highn;     lower:0~lown
            res += lown + 1;
        } else if (0 == curn) {  
            //curn < 1
            //higher: 0~(highn-1);  lower:  0 ~ (lowc-1)
            res += highn * lowc;
        } else {              
            //curn > 1
            res += (highn + 1) * lowc;
        }
        //update lown and lowc
        lown = curn * lowc + lown;
        lowc = lowc * 10;
    }
    return res;
}
http://shaowei-su.github.io/2015/11/29/leetcode233/
预处理数组ones，ones[x]表示[0, 10 ^ x)范围内包含的1的个数 由高位向低位依次遍历数字n的每一位digit 记digit右边（低位）数字片段为n，size为当前位数，cnt为1的总数 若digit > 1，则cnt += digit * ones[size - 1] + 10 ^ size 若digit = 1，则cnt += n + ones[size - 1] + 1 

Pattern: # of 1: < 1, # of 1 < 100, # of 1 < 1000...
cur = prev * 10 + 10 ** digits
    public int countDigitOne(int n) {
        if (n <= 0) {
            return 0;
        }
        int ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m;
            long b = n % m;
            ones += (a + 8) / 10 * m;
            if (a % 10 == 1) {
                ones += b + 1;
            }
        }
        return ones;
    }

    def countDigitOne(self, n):
        """
        :type n: int
        :rtype: int
        """
        if n < 0:
            return 0
        ones, x = [0], 0
        imax = (1 << 32) - 1
        while 10 ** x < imax:
            ones.append(ones[x] * 10 + 10 ** x)
            x += 1
        count, size = 0, len(str(n))
        for digit in str(n):
            digit = int(digit)
            size -= 1
            n -= digit * 10 ** size
            if digit == 1:
                count += ones[size] + 1 + n
            elif digit > 1:
                count += digit * ones[size] + 10 ** size
        return count
http://www.faceye.net/search/209814.html
intuitive: 每10个数, 有一个个位是1, 每100个数, 有10个十位是1, 每1000个数, 有100个百位是1.  做一个循环, 每次计算单个位上1得总个数(个位,十位, 百位).  
例子:
以算百位上1为例子:   假设百位上是0, 1, 和 >=2 三种情况: 
    case 1: n=3141092, a= 31410, b=92. 计算百位上1的个数应该为 3141 *100 次.
    case 2: n=3141192, a= 31411, b=92. 计算百位上1的个数应该为 3141 *100 + (92+1) 次. 
    case 3: n=3141592, a= 31415, b=92. 计算百位上1的个数应该为 (3141+1) *100 次. 
以上三种情况可以用 一个公式概括:
(a + 8) / 10 * m + (a % 10 == 1) * ( + 1);
https://leetcode.com/discuss/44281/4-lines-o-log-n-c-java-python
Go through the digit positions by using position multiplier m with values 1, 10, 100, 1000, etc.
For each position, split the decimal representation into two parts, for example split n=3141592 into a=31415 and b=92 when we're at m=100 for analyzing the hundreds-digit. And then we know that the hundreds-digit of n is 1 for prefixes "" to "3141", i.e., 3142 times. Each of those times is a streak, though. Because it's the hundreds-digit, each streak is 100 long. So (a / 10 + 1) * 100 times, the hundreds-digit is 1.
Consider the thousands-digit, i.e., when m=1000. Then a=3141 and b=592. The thousands-digit is 1 for prefixes "" to "314", so 315 times. And each time is a streak of 1000 numbers. However, since the thousands-digit is a 1, the very last streak isn't 1000 numbers but only 593 numbers, for the suffixes "000" to "592". So (a / 10 * 1000) + (b + 1) times, the thousands-digit is 1.
The case distincton between the current digit/position being 0, 1 and >=2 can easily be done in one expression. With (a + 8) / 10 you get the number of full streaks, and a % 10 == 1 tells you whether to add a partial streak.
Java version
public int countDigitOne(int n) {
    int ones = 0;
    for (long m = 1; m <= n; m *= 10)
        ones += (n/m + 8) / 10 * m + (n/m % 10 == 1 ? n%m + 1 : 0);
    return ones;
}
https://leetcode.com/discuss/46366/ac-short-java-solution

http://blog.csdn.net/xudli/article/details/46798619
intuitive: 每10个数, 有一个个位是1, 每100个数, 有10个十位是1, 每1000个数, 有100个百位是1.  做一个循环, 每次计算单个位上1得总个数(个位,十位, 百位).  
例子:
以算百位上1为例子:   假设百位上是0, 1, 和 >=2 三种情况: 
    case 1: n=3141092, a= 31410, b=92. 计算百位上1的个数应该为 3141 *100 次.
    case 2: n=3141192, a= 31411, b=92. 计算百位上1的个数应该为 3141 *100 + (92+1) 次. 
    case 3: n=3141592, a= 31415, b=92. 计算百位上1的个数应该为 (3141+1) *100 次. 
以上三种情况可以用 一个公式概括:
(a + 8) / 10 * m + (a % 10 == 1) * (b + 1);
[CODE]
public class Solution {
    public int countDigitOne(int n) {
        int ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n/m, b = n%m;
            ones += (a + 8) / 10 * m;
            if(a % 10 == 1) ones += b + 1;
        }
        return ones;
    }
}
http://www.cnblogs.com/grandyang/p/4629032.html
    int countDigitOne(int n) {
        int res = 0, a = 1, b = 1;
        while (n > 0) {
            res += (n + 8) / 10 * a + (n % 10 == 1) * b;
            b += n % 10 * a;
            a *= 10;
            n /= 10;
        }
        return res;
    }
http://codechen.blogspot.com/2015/07/leetcode-number-of-digit-one.html
    public int countDigitOne(int n) {
        if (n <= 0) {
            return 0;
        }
        
        ArrayList<Integer> ones = new ArrayList<Integer>();
        ones.add(0, 0);
        for (int i = 0; Math.pow(10, i) < (double)Integer.MAX_VALUE; i++) {
            ones.add(i + 1, ones.get(i) * 10 + (int)Math.pow(10, i));
        }
        
        int result = 0;
        int k = n;
        for (int i = 0; k > 0; i++) {
            int digit = k % 10;
            if (digit == 1) {
                result += ones.get(i) + (n % ((int)Math.pow(10, i))) + 1;
            } else if (digit > 1) {
                result += digit * ones.get(i) + (int)Math.pow(10, i);
            }
            k = k / 10;
        }
        
        return result;
    }

X. Recursive
http://yuanhsh.iteye.com/blog/2227478
For example '8192':
1-999 -> countDigitOne(999)
1000-1999 -> 1000 of 1s + countDigitOne(999)
2000-2999 -> countDigitOne(999)
7000-7999 -> countDigitOne(999)
8000-8192 -> countDigitOne(192)
Count of 1s : countDigitOne(999)*8 + 1000 + countDigitOne(192)
Noticed that, if the target is '1192':
Count of 1s : countDigitOne(999)*1 + (1192 - 1000 + 1) + countDigitOne(192)
(1192 - 1000 + 1) is the 1s in thousands from 1000 to 1192.
public int countDigitOne(int n) {  
    if(n <= 0) return 0;  
    if(n < 10) return 1;  
    int base = (int)Math.pow(10, (n+"").length() - 1);  
    int k = n / base;  
    return countDigitOne(base - 1) * k +   
            (k == 1 ? (n-base+1) : base) +   
            countDigitOne(n % base);  
}


https://leetcode.com/discuss/44496/5-lines-solution-using-recursion-with-explanation
var countDigitOne = function(n) {
    if(n <= 0) return 0;
    if(n < 10) return 1;
    var base = Math.pow(10, n.toString().length - 1);
    var answer = parseInt(n / base);
    return countDigitOne(base - 1) * answer + (answer === 1 ? (n - base + 1) : base) + countDigitOne(n % base);
};

https://leetcode.com/discuss/65228/java-recursive-ac-solution
public int countDigitOne(int n) {
    String numstr = Integer.toString(n);
    return helper(n, numstr.length());
}
public int helper(int n, int len){
    if(n < 1)  return 0;
    else if(len == 1)    return 1;
    int pow = (int)Math.pow(10, len-1);
    int msd = n / pow;
    if(msd == 0)    return helper(n, len - 1);
    return (msd == 1 ? n % pow + 1 : pow) + helper(n % pow, len - 1) + msd * helper(pow - 1, len - 1);
}
http://www.geeksforgeeks.org/count-numbers-0-digit/

// Returns 1 if x has 0, else 0

int has0(int x)

{

    // Traverse througn all digits of

    // x to check if it has 0.

    while (x)

    {

        // If current digit is 0, return true

        if (x % 10 == 0)

          return 1;


        x /= 10;

    }


    return 0;

}


// Returns count of numbers from 1 to n with 0 as digit

int getCount(int n)

{

    // Initialize count of numbers having 0 as digit

    int count = 0;


    // Travers through all numbers and for every number

    // check if it has 0.

    for (int i=1; i<=n; i++)

        count += has0(i);


    return count;

}
Read full article from [LeetCode] Number of Digit One 数字1的个数 - Grandyang - 博客园
 * @author het
 *
 */
public class LeetCode233 {
	//https://leetcode.com/discuss/64962/java-python-one-pass-solution-easy-to-understand
	//	The idea is to calculate occurrence of 1 on every digit. There are 3 scenarios, for example
	//	if n = xyzdabc
//		and we are considering the occurrence of one on thousand, it should be:
//		(1) xyz * 1000                     if d == 0
//		(2) xyz * 1000 + abc + 1           if d == 1
//		(3) xyz * 1000 + 1000              if d > 1
//		iterate through all digits and sum them all will give the final answer
		public int countDigitOne(int n) {

		    if (n <= 0) return 0;
		    int q = n, x = 1, ans = 0;
		    do {
		        int digit = q % 10;
		        q /= 10;
		        ans += q * x;
		        if (digit == 1) ans += n % x + 1;
		        if (digit >  1) ans += x;
		        x *= 10;
		    } while (q > 0);
		    return ans;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
