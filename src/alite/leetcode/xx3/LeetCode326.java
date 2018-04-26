package alite.leetcode.xx3;
/**
 * LeetCode 326 - Power of Three

http://www.hrwhisper.me/leetcode-power-of-three/
Given an integer, write a function to determine if it is a power of three.
Follow up:
Could you do it without using any loop / recursion?

    bool isPowerOfThree(int n) {

        if(n <= 0) return false;

        while(n > 1){

            if(n %3 != 0) return false;

             n/=3;

        }

        return true;

    }

 bool isPowerOfThree(int n) {

 if (n <= 0) return false;

 if (n == 1) return true;

 return n % 3 == 0 && isPowerOfThree(n / 3);

 }


 bool isPowerOfThree(int n) {

 return n <= 0 ? false : n == pow(3, round(log(n) / log(3)));

 }
http://algobox.org/power-of-three/
A trivial loop / recursion solution is to divide the number by 3 and look at the remainder again and again.
Another idea is based on logarithm rules and float numbers which i don’t like very much. After all the log function it self may actually implemented using loop / recursion.
Since the 32 bit signed integer has at most 20 numbers, here is a list.
1
3
9
27
81
243
729
2187
6561
19683
59049
177147
531441
1594323
4782969
14348907
43046721
129140163
387420489
1162261467

If we put them in a hash set beforehand, then we can query is power of three in O(1) time. I have seen people write in python like this
Python

1
2
3
4
5


class Solution(object):


    def isPowerOfThree(self, n):


        return n in [1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 


                     59049, 177147, 531441, 1594323, 4782969, 14348907,


                     43046721, 129140163, 387420489, 1162261467]

Although it seems not using any loop, the in operation for list in python takes linear time, which indicating a loop underneath. We can use a set instead and put it into a class variable so we can share between calls.
Python

1
2
3
4
5
6


class Solution(object):


    powerOfThree = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 


                     59049, 177147, 531441, 1594323, 4782969, 14348907,


                     43046721, 129140163, 387420489, 1162261467}


    def isPowerOfThree(self, n):


        return n in powerOfThree

However, the fastest solution is this
Java

1
2
3
4
5


public class Solution {


    boolean isPowerOfThree(int n) {


        return n > 0 && 1162261467 % n == 0;


    }


}



This works because 3 is a prime number. A 32 bit positive integer is a power of 3 is equivalent to it is a factor of 3^19 = 1162261467.
This doesn’t work for composite number like 4 or 6.
http://www.cnblogs.com/grandyang/p/5138212.html
题目中的Follow up让我们不用循环，那么有一个投机取巧的方法，由于输入是int，正数范围是0-231，在此范围中允许的最大的3的次方数为319=1162261467，那么我们只要看这个数能否被n整除即可，参见代码如下：
解法二：
class Solution {
public:
    bool isPowerOfThree(int n) {
        return (n > 0 && 1162261467 % n == 0);
    }
};

最后还有一种巧妙的方法，利用对数的换底公式来做，高中学过的换地公式为logab = logcb / logca，那么如果n是3的倍数，则log3n一定是整数，我们利用换底公式可以写为log3n = log10n / log103，注意这里一定要用10为底数，不能用自然数或者2为底数，否则当n=243时会出错，原因请看这个帖子。现在问题就变成了判断log10n / log103是否为整数，在c++中判断数字a是否为整数，我们可以用 a - int(a) == 0 来判断，参见代码如下：
解法三：
class Solution {
public:
    bool isPowerOfThree(int n) {
        return (n > 0 && int(log10(n) / log10(3)) - log10(n) / log10(3) == 0);
    }
};
http://fujiaozhu.me/?p=799

    bool isPowerOfThree(int n) {

        if(n == 0) return false;

        else return log10(n)/log10(3) - (int)(log10(n)/log10(3)) < 1e-15;

    }
 * @author het
 *
 */
public class LeetCode326 {
	bool isPowerOfThree(int n) {

		 return n <= 0 ? false : n == pow(3, round(log(n) / log(3)));

		 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
