package alite.leetcode.xx2.sucess.extra;
/**
 * Write a program to check whether a given number is an ugly number.
Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.
Note that 1 is typically treated as an ugly number.

这道题让我们检测一个数是否为丑陋数，所谓丑陋数就是其质数因子只能是2,3,5。那么最直接的办法就是不停的除以这些质数，如果剩余的数字是1的话就是丑陋数了，有两种写法，
    bool isUgly(int num) {
        while (num >= 2) {
            if (num % 2 == 0) num /= 2;
            else if (num % 3 == 0) num /= 3;
            else if (num % 5 == 0) num /= 5;
            else return false;
        }
        return num == 1;
    }

    bool isUgly(int num) {
        if (num <= 0) return false;
        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1;
    }
http://bookshadow.com/weblog/2015/08/19/leetcode-ugly-number/
https://leetcode.com/discuss/78188/java-solution-greatest-divide-by-2-3-5
记num = 2^a * 3^b * 5^c * t，程序执行次数为 a + b + c，换言之，最坏情况为O(log num)
    def isUgly(self, num):
        if num <= 0:
            return False
        for x in [2, 3, 5]:
            while num % x == 0:
                num /= x
        return num == 1
public static boolean isUgly(int num) {
    if (num <= 0) {
        return false;
    }

    int[] divisors = {2, 3, 5};

    for(int d : divisors) {
        while (num % d == 0) {
            num /= d;
        }
    }
    return num == 1;
}
https://leetcode.com/discuss/52703/2-4-lines-every-language
public boolean isUgly(int num) {
    if (num > 0)
        for (int i : new int[] { 2, 3, 5 }) {
            while (num % i == 0) {
                num /= i;
            }
        }
    return num == 1;
}
https://leetcode.com/discuss/52883/simple-java-solution-with-explanation


(1) basic cases: <= 0 and == 1
(2) other cases: since the number can contain the factors of 2, 3, 5, I just remove those factors. So now, I have a number without any factors of 2, 3, 5.
(3) after the removing, the number (new number) can contain a) the factor that is prime and meanwhile it is >= 7, or b) the factor that is not the prime and the factor is not comprised of 2, 3 or 5. In both cases, it is false (not ugly number).
For example, new number can be 11, 23 --> not ugly number (case a)). new number also can be 49, 121 --> not ugly number (case b))
public boolean isUgly(int num) {
    if (num <= 0) {return false;}
    if (num == 1) {return true;}
    if (num % 2 == 0) {
        return isUgly(num/2);
    }
    if (num % 3 == 0) {
        return isUgly(num/3);
    }
    if (num % 5 == 0) {
        return isUgly(num/5);
    }
    return false;
}
When you do a recursive call like this, it will call the same values mulitple times (e.g. 18 calls 2x3x3 3x2x3 3x3x2). You can avoid this by caching the results as seen in the approach below.
public boolean isUgly(int num) {
    Map<Integer,Boolean> cache = new HashMap<Integer,Boolean>();
    return isUgly(num,cache);

}
private boolean isUgly(int num, Map<Integer,Boolean> cache) {
    if(cache.containsKey(num)) return cache.get(num);
    if (num <= 0) return false;
    if (num == 1) return true;
    if (num %5 == 0) {
        cache.put(num,isUgly(num/5,cache));
        return cache.get(num);
    }
    if (num %3 == 0) {
        cache.put(num,isUgly(num/3,cache));
        return cache.get(num);
    }
    if (num %2 == 0) {
        cache.put(num,isUgly(num/2,cache));
        return cache.get(num);
    }
    return false;
}
Is this really necessary? since it use "return isUgly(num/2);", the recursive will only go 233 for 18, and then finish, it wont go like 323 or 332, am I right?
 * @author het
 *
 */
public class LeetCode263UglyNumber {
	boolean isUgly(int num) {
        if (num <= 0) return false;
        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
