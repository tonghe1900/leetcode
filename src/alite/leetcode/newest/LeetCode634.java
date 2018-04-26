package alite.leetcode.newest;
/**
 * LeetCode 634 - Find the Derangement of An Array

http://bookshadow.com/weblog/2017/07/02/leetcode-find-the-derangement-of-an-array/
In combinatorial mathematics, a derangement is a permutation of the elements of a set, such that no element appears in its original position.
There's originally an array consisting of n integers from 1 to n in ascending order, you need to find the number of derangement it can generate.
Also, since the answer may be very large, you should return the output mod 109 + 7.
Example 1:
Input: 3
Output: 2
Explanation: The original array is [1,2,3]. The two derangements are [2,3,1] and [3,1,2].
Note:
n is in the range of [1, 106].
https://en.wikipedia.org/wiki/Derangement#Counting_derangements
https://discuss.leetcode.com/topic/94442/java-5-lines-o-1-space-solution
https://discuss.leetcode.com/topic/94421/java-solution-with-explanation-by-using-staggered-formula

http://blog.csdn.net/zjucor/article/details/74094495


    public int findDerangement(int n) {  
        if(n == 1)  return 0;  
        if(n == 2)  return 1;  
        if(n == 3)  return 2;  
          
        int[] dp = new int[1+n];  
        dp[2] = 1;  
        dp[3] = 2;  
          
        for(int i=4; i<=n; i++) {  
            long t = ((long)dp[i-1] + dp[i-2])*(i-1);  
            dp[i] = (int) (t % (1e9+7));  
        }  
          
        return (int) (dp[n] % (10e9+7));  
    }  
http://code.bitjoy.net/2017/07/02/leetcode-find-the-derangement-of-an-array/
假设dp[n]表示长度为n的数组的错排个数，显然dp[0]=1,dp[1]=0,dp[2]=1。如果已经知道dp[0,...,n-1]，怎样求dp[n]。考虑第n个数，因为错排，它肯定不能放在第n位，假设n放在第k位，则有如下两种情况：
数字k放在了第n位，这时，相当于n和k交换了一下位置，还剩下n-2个数需要错排，所以+dp[n-2]
数字k不放在第n位，此时，可以理解为k原本的位置是n，也就是1不能放在第1位、2不能放在第2位、...、k不能放在第n位，也就相当于对n-1个数进行错排，所以+dp[n-1]
因为n可以放在1~n-1这n-1个位置，所以总的情况数等于dp[n]=(n-1)(dp[n-1]+dp[n-2])。
这就类似于求斐波那契数列的第n项了，维护前两个变量，不断滚动赋值就好了，时间复杂度O(n)


dp[n] = (dp[n - 1] + dp[n - 2]) * (n - 1)
公式推导过程可以参考：https://en.wikipedia.org/wiki/Derangement
错位排列可以理解成将标号为1~n的帽子分配给标号为1~n的人，每个人拿到的帽子标号都与其自身的标号不同。
假设第一个人选取了标号为i的帽子，此时可分两种情况讨论：
1. 第i个人选择第一顶帽子，则问题规模缩小为n - 2
2, 3, 4, ..., i-1, i+1, ... n 人
2, 3, 4, ..., i-1, i+1, ... n 帽
2. 第i个人不选第一顶帽子，则问题规模缩小为n - 1（相当于第i个人不能选择第1顶帽子）
2, 3, 4, ..., i-1, i, i+1, ..., n 人
2, 3, 4, ..., i-1, 1, i+1, ..., n 帽
 * @author het
 *
 */
public class LeetCode634 {

}
