package alite.leetcode.xx2.sucess;
/**
 * [LeetCode] Super Pow 超级次方
 

Your task is to calculate ab mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.

Example1:

a = 2
b = [3]

Result: 8
Example2:

a = 2
b = [1,0]
64*16
256*4     1024
Result: 1024
Credits:
Special thanks to @Stomach_ache for adding this problem and creating all test cases.

 

这道题题让我们求一个数的很大的次方对1337取余的值，开始一直在想这个1337有什么玄机，为啥突然给这么一个数，感觉很突兀，后来想来想去也没想出来为啥，估计就是怕结果太大无法表示，
随便找个数取余吧。那么这道题和之前那道Pow(x, n)的解法很类似，我们都得对半缩小，不同的是后面都要加上对1337取余。由于给定的指数b是一个一维数组的表示方法，
我们要是折半缩小处理起来肯定十分不方便，所以我们采用按位来处理，比如223 = (22)10 * 23, 所以我们可以从b的最高位开始，算出个结果存入res，然后到下一位是，res的十次方再乘以a的该位次方再对1337取余，参见代码如下：

 a^[12, 56, 78]
 
 a^[1200 + 560 + 78]
 
 (a^12) ^10  
 
 x^n %1337 = (x%1337)^n
 

(a* b) % 1337 = (a % 1337)*(b%1337)
res = pow(res, 10) * pow(a, b[i])%1337
//res = pow(res, 10) * pow(a, b[i]) % 1337;
// res = pow(res, 10) * pow(a, b[i]) % 1337;
复制代码
class Solution {
public:
    int superPow(int a, vector<int>& b) {
        long long res = 1;
        for (int i = 0; i < b.size(); ++i) {
            res = pow(res, 10) * pow(a, b[i]) % 1337;
        }
        return res;
    }
    int pow(int x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x % 1337;
        x = x % 1337;
        int subResult = pow(x, n / 2) ;
        if(n %2 ==0) return subResult* subResult %1337;
        return subResult*subResult*x%1337;
        //return pow(x % 1337, n / 2) * pow(x % 1337, n - n / 2) % 1337;
    }
};
复制代码
 

类似题目：

Pow(x, n)

 

参考资料：

//////////////////////////////https://discuss.leetcode.com/topic/50430/c-ac-recursive-solution


 * @author het
 *
 */
public class Super_pow {

}
