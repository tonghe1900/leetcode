package Leetcode600x;
/**
 * 
 * 
 * @author tonghe
 * 
 * Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary representations do NOT contain consecutive ones.

Example 1:
Input: 5
Output: 5
Explanation: 
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule. 
Note: 1 <= n <= 109






Solution
Approach #1 Brute Force [Time Limit Exceeded]
The brute force approach is simple. We can traverse through all the numbers from 11 to numnum. For every current number chosen, we can check all the consecutive positions in this number to check if the number contains two consecutive ones or not. If not, we increment the countcount of the resultant numbers with no consecutive ones.

To check if a 11 exists at the position xx(counting from the LSB side), in the current number nn, we can proceed as follows. We can shift a binary 11 x-1x−1 times towards the left to get a number yy which has a 11 only at the x^{th}x
​th
​​ position. Now, logical ANDing of nn and yy will result in a logical 11 output only if nn contains 11 at the x^{th}x
​th
​​  position.


Complexity Analysis

Time complexity : O(32*n)O(32∗n). We test the 32 consecutive positions of every number from 00 to nn. Here, nn refers to given number.

Space complexity : O(1)O(1). Constant space is used.

Approach #2 Better Brute Force [Time Limit Exceeded]
Algorithm

In the last approach, we generated every number and then checked if it contains consecutive ones at any position or not. Instead of this, we can generate only the required kind of numbers. e.g. If we genearte numbers in the order of the number of bits in the current number, if we get a binary number 110 on the way at the step of 3-bit number generation. Now, since this number already contains two consecutive ones, it is useless to generate number with more number of bits with the current bitstream as the suffix(e.g. numbers of the form 1110 and 0110).

The current approach is based on the above idea. We can start with the LSB position, by placing a 0 and a 1 at the LSB. These two initial numbers correspond to the 1-bit numbers which don't contain any consecutive ones. Now, taking 0 as the initial suffix, if we want to generate two bit numbers with no two consecutive 1's, we can append a 1 and a 0 both in front of the initial 0 generating the numbers 10 and 00 as the two bit numbers ending with a 0 with no two consecutive 1's.

But, when we take 1 as the initial suffix, we can append a 0 to it to generate 01 which doesn't contain any consecutive ones. But, adding a 1 won't satisfy this criteria(11 will be generated). Thus, while generating the current number, we need to keep a track of the point that whether a 1 was added as the last prefix or not. If yes, we can't append a new 1 and only 0 can be appended. If a 0 was appended as the last prefix, both 0 and 1 can be appended in the new bit-pattern without creating a violating number. Thus, we can continue forward with the 3-bit number generation only with 00, 01 and 10 as the new suffixes in the same manner.

To get a count of numbers lesser than numnum, with no two consecutive 1's, based on the above discussion, we make use of a recursive function find(i, sum, num, prev). This function returns the count of binary numbers with ii bits with no two consecutive 1's. Here, sumsum refers to the binary number generated till now(the prefix obtained as the input). numnum refers to the given number. prevprev is a boolean variable that indicates whether the last prefix added was a 1 or a 0.

If the last prefix was a 0, we can add both 1 and 0 as the new prefix. Thus, we need to make a function call find(i + 1, sum, num, false) + find(i + 1, sum + (1 << i), num, true). Here, the first sub-part refers to a 0 being added at the i^{th}i
​th
​​  position. Thus, we pass a false as the prefix in this case. The second sub-part refers to a 1 being added at the i^{th}i
​th
​​  position. Thus, we pass true as the prefix in this case.

If the last prefix was a 1, we can add only a 0 as the new prefix. Thus, only one function call find(i + 1, sum, num, false) is made in this case.

Further, we need to stop the number generation whenver the current input number(sumsum) exceeds the given number numnum.

Tree


Complexity Analysis

Time complexity : O(x)O(x). Only xx numbers are generated. Here, xx refers to the resultant count to be returned.

Space complexity : O(log(max\_int)=32)O(log(max_int)=32). The depth of recursion tree can go upto 3232.

Approach #3 Using Bit Manipulation [Accepted]
Algorithm

Before we discuss the idea behind this approach, we consider another simple idea that will be used in the current approach.

Suppose, we need to find the count of binary numbers with nn bits such that these numbers don't contain consecutive 1's. In order to do so, we can look at the problem in a recursive fashion. Suppose f[i]f[i] gives the count of such binary numbers with ii bits. In order to determine the value of f[n]f[n], which is the requirement, we can consider the cases shown below:

Recursive_Function

From the above figure, we can see that if we know the value of f[n-1]f[n−1] and f[n-2]f[n−2], in order to generate the required binary numbers with nn bits, we can append a 0 to all the binary numbers contained in f[n-1]f[n−1] without creating an invalid number. These numbers give a factor of f[n-1]f[n−1] to be included in f[n]f[n]. But, we can't append a 1 to all these numbers, since it could lead to the presence of two consecutive ones in the newly generated numbers. Thus, for the currently generated numbers to end with a 1, we need to ensure that the second last position is always 0. Thus, we need to fix a 01 at the end of all the numbers contained in f[n-2]f[n−2]. This gives a factor of f[n-2]f[n−2] to be included in f[n]f[n]. Thus, in total, we get f[n] = f[n-1] + f[n-2]f[n]=f[n−1]+f[n−2].

Now, let's look into the current approach. We'll try to understand the idea behind the approach by taking two simple examples. Firstly, we look at the case where the given number doesn't contain any consecutive 1's.Say, num = \text{1010100}num=1010100(7 bit number). Now, we'll see how we can find the numbers lesser than numnum with no two consecutive 1's. We start off with the MSB of numsnums. If we fix a \text{0}0 at the MSB position, and find out the count of 6 bit numbers(corresponding to the 6 LSBs) with no two consecutive 1's, these 6-bit numbers will lie in the range \textbf{0}\text{000000} -> \textbf{0}\text{111111}0000000−>0111111. For finding this count we can make use of f[6]f[6] which we'll have already calculated based on the discussion above.

But, even after doing this, all the numbers in the required range haven't been covered yet. Now, if we try to fix \text{1}1 at the MSB, the numbers considered will lie in the range \textbf{1}\text{000000} -> \textbf{1}\text{111111}1000000−>1111111. As we can see, this covers the numbers in the range \textbf{1}\text{000000} -> \textbf{1}\text{010100}1000000−>1010100, but it covers the numbers in the range beyond limit as well. Thus, we can't fix \text{1}1 at the MSB and consider all the 6-bit numbers at the LSBs.

For covering the pending range, we fix \text{1}1 at the MSB, and move forward to proceed with the second digit(counting from MSB). Now, since we've already got a \text{0}0 at this position, we can't substitute a \text{1}1 here, since doing so will lead to generation of numbers exceeding numnum. Thus, the only option left here is to substitute a \text{0}0 at the second position. But, if we do so, and consider the 5-bit numbers(at the 5 LSBs) with no two consecutive 1's, these new numbers will fall in the range \textbf{10}\text{00000} -> \textbf{10}\text{11111}1000000−>1011111. But, again we can observe that considering these numbers leads to exceeding the required range. Thus, we can't consider all the 5-bit numbers for the required count by fixing \text{0}0 at the second position.

Thus, now, we fix \text{0}0 at the second position and proceed further. Again, we encounter a \text{1}1 at the third position. Thus, as discussed above, we can fix a \text{0}0 at this position and find out the count of 4-bit consecutive numbers with no two consecutive 1's(by varying only the 4 LSB bits). We can obtain this value from f[4]f[4]. Thus, now the numbers in the range \textbf{100}\text{0000} -> \textbf{100}\text{1111}1000000−>1001111 have been covered up.

Again, as discussed above, now we fix a \text{1}1 at the third position, and proceed with the fourth bit. It is a \text{0}0. So, we need to fix it as such as per the above discussion, and proceed with the fifth bit. It is a \text{1}1. So, we fix a \text{0}0 here and consider all the numbers by varying the two LSBs for finding the required count of numbers in the range \textbf{10101}\text{00} -> \textbf{10101}\text{11}1010100−>1010111. Now, we proceed to the sixth bit, find a \text{0}0 there. So, we fix \text{0}0 at the sixth position and proceed to the seventh bit which is again \text{0}0. So, we fix a \text{0}0 at the seventh position as well.

Now, we can see, that based on the above procedure, the numbers in the range \textbf{1}\text{000000} -> \textbf{1}\text{111111}1000000−>1111111, \textbf{100}\text{0000} -> \textbf{100}\text{1111}1000000−>1001111, \textbf{100}\text{0000} -> \textbf{100}\text{1111}1000000−>1001111 have been considered and the counts for these ranges have been obtained as f[6]f[6], f[4]f[4] and f[2]f[2] respectively. Now, only \text{1010100}1010100 is pending to be considered in the required count. Since, it doesn't contain any consecutive 1's, we add a 1 to the total count obtained till now to consider this number. Thus, the result returned is f[6] + f[4] + f[2] + 1f[6]+f[4]+f[2]+1.

1 / 12
Now, we look at the case, where numnum contains some consecutive 1's. The idea will be the same as the last example, with the only exception taken when the two consecutive 1's are encountered. Let's say, num = \text{1011010}num=1011010(7 bit number). Now, as per the last discussion, we start with the MSB. We find a \text{1}1 at this position. Thus, we initially fix a \text{0}0 at this position to consider the numbers in the range \textbf{0}\text{000000} -> \textbf{0}\text{111111}0000000−>0111111, by varying the 6 LSB bits only. The count of the required numbers in this range is again given by f[6]f[6].

Now, we fix a \text{1}1 at the MSB and move on to the second bit. It is a \text{0}0, so we have no choice but to fix \text{0}0 at this position and to proceed with the third bit. It is a \text{1}1, so we fix a \text{0}0 here, considering the numbers in the range \textbf{100}\text{0000} -> \textbf{100}\text{1111}1000000−>1001111. This accounts for a factor of f[4]f[4]. Now, we fix a \text{1}1 at the third positon, and proceed with the fourth bit. It is a \text{1}1(consecutive to the previous \text{1}1). Now, initially we fix a \text{0}0 at the fourth position, considering the numbers in the range \textbf{1010}\text{000} -> \textbf{1010}\text{111}1010000−>1010111. This adds a factor of f[3]f[3] to the required count.

Now, we can see that till now the numbers in the range \textbf{0}\text{000000} -> \textbf{0}\text{111111}0000000−>0111111, \textbf{100}\text{0000} -> \textbf{100}\text{1111}1000000−>1001111, \textbf{1010}\text{000} -> \textbf{1010}\text{111}1010000−>1010111 have been considered. But, if we try to consider any number larger than \text{1010111}1010111, it leads to the presence of two consecutive 1's in the new number at the third and fourth position. Thus, all the valid numbers upto numnum have been considered with this, giving a resultant count of f[6] + f[4] + f[3]f[6]+f[4]+f[3].

1 / 8
Thus, summarizing the above discussion, we can say that we start scanning the given number numnum from its MSB. For every 1 encountered at the i^{th}i
​th
​​  bit position(counting from 0 from LSB), we add a factor of f[i]f[i] to the resultant count. For every 0 encountered, we don't add any factor. We also keep a track of the last bit checked. If we happen to find two consecutive 1's at any time, we add the factors for the positions of both the 1's and stop the traversal immediately. If we don't find any two consecutive 1's, we proceed till reaching the LSB and add an extra 1 to account for the given number numnum as well, since the procedure discussed above considers numbers upto numnum without including itself.


Complexity Analysis

Time complexity : O(log_2(max\_int)=32)O(log
​2
​​ (max_int)=32). One loop to fill ff array and one loop to check all bits of numnum.

Space complexity : O(log_2(max\_int)=32)O(log
​2
​​ (max_int)=32). ff array of size 32 is used.

Analysis written by: @vinod23
 *
 */
public class Leetcode600_Non_negative_Integers_without_Consecutive_Ones {
	//1
	public class Solution {
	    public int findIntegers(int num) {
	        int count = 0;
	        for (int i = 0; i <= num; i++)
	            if (check(i))
	                count++;
	        return count;
	    }
	    public boolean check(int n) {
	        int i = 31;
	        while (i > 0) {
	            if ((n & (1 << i)) != 0 && (n & (1 << (i - 1))) != 0)
	                return false;
	            i--;
	        }
	        return true;
	    }
	}

	
	//2
	public class Solution {
	    public int findIntegers(int num) {
	        return find(0, 0, num, false);
	    }
	    public int find(int i, int sum, int num, boolean prev) {
	        if (sum > num)
	            return 0;
	        if (1<<i > num)
	            return 1;
	        if (prev)
	            return find(i + 1, sum, num, false);
	        return find(i + 1, sum, num, false) + find(i + 1, sum + (1 << i), num, true);
	    }
	}
	//3
	public class Solution {
	    public int findIntegers(int num) {
	        int[] f = new int[32];
	        f[0] = 1;
	        f[1] = 2;
	        for (int i = 2; i < f.length; i++)
	            f[i] = f[i - 1] + f[i - 2];
	        int i = 30, sum = 0, prev_bit = 0;
	        while (i >= 0) {
	            if ((num & (1 << i)) != 0) {
	                sum += f[i];
	                if (prev_bit == 1) {
	                    sum--;
	                    break;
	                }
	                prev_bit = 1;
	            } else
	                prev_bit = 0;
	            i--;
	        }
	        return sum + 1;
	    }
	}

}



[LeetCode] Non-negative Integers without Consecutive Ones 非负整数不包括连续的1


Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary representations do NOT contain consecutive ones.

Example 1:

Input: 5
Output: 5
Explanation: 
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule. 
 

Note: 1 <= n <= 109

 

这道题给了我们一个数字，让我们求不大于这个数字的所有数字中，其二进制的表示形式中没有连续1的个数。根据题目中的例子也不难理解题意。我们首先来考虑二进制的情况，对于1来说，有0和1两种，对于11来说，有00，01，10，三种情况，那么有没有规律可寻呢，其实是有的，我们可以参见这个帖子，这样我们就可以通过DP的方法求出长度为k的二进制数的无连续1的数字个数。由于题目给我们的并不是一个二进制数的长度，而是一个二进制数，比如100，如果我们按长度为3的情况计算无连续1点个数个数，就会多计算101这种情况。所以我们的目标是要将大于num的情况去掉。下面从头来分析代码，首先我们要把十进制数转为二进制数，将二进制数存在一个字符串中，并统计字符串的长度。然后我们利用这个帖子中的方法，计算该字符串长度的二进制数所有无连续1的数字个数，然后我们从倒数第二个字符开始往前遍历这个二进制数字符串，如果当前字符和后面一个位置的字符均为1，说明我们并没有多计算任何情况，不明白的可以带例子来看。如果当前字符和后面一个位置的字符均为0，说明我们有多计算一些情况，就像之前举的100这个例子，我们就多算了101这种情况。我们怎么确定多了多少种情况呢，假如给我们的数字是8，二进制为1000，我们首先按长度为4算出所有情况，共8种。仔细观察我们十进制转为二进制字符串的写法，发现转换结果跟真实的二进制数翻转了一下，所以我们的t为"0001"，那么我们从倒数第二位开始往前遍历，到i=1时，发现有两个连续的0出现，那么i=1这个位置上能出现1的次数，就到one数组中去找，那么我们减去1，减去的就是0101这种情况，再往前遍历，i=0时，又发现两个连续0，那么i=0这个位置上能出1的次数也到one数组中去找，我们再减去1，减去的是1001这种情况，参见代码如下：

 

解法一：

复制代码
class Solution {
public:
    int findIntegers(int num) {
        int cnt = 0, n = num;
        string t = "";
        while (n > 0) {
            ++cnt;
            t += (n & 1) ? "1" : "0"; 
            n >>= 1;
        }
        vector<int> zero(cnt), one(cnt);
        zero[0] = 1; one[0] = 1;
        for (int i = 1; i < cnt; ++i) {
            zero[i] = zero[i - 1] + one[i - 1];
            one[i] = zero[i - 1];
        }
        int res = zero[cnt - 1] + one[cnt - 1];
        for (int i = cnt - 2; i >= 0; --i) {
            if (t[i] == '1' && t[i + 1] == '1') break;
            if (t[i] == '0' && t[i + 1] == '0') res -= one[i];
        }
        return res;
    }
};
复制代码
 

下面这种解法其实蛮有意思的，其实长度为k的二进制数字符串没有连续的1的个数是一个斐波那契数列f(k)。比如当k=5时，二进制数的范围是00000-11111，我们可以将其分为两个部分，00000-01111和10000-10111，因为任何大于11000的数字都是不成立的，因为有开头已经有了两个连续1。而我们发现其实00000-01111就是f(4)，而10000-10111就是f(3)，所以f(5) = f(4) + f(3)，这就是一个斐波那契数列啦。那么我们要做的首先就是建立一个这个数组，方便之后直接查值。我们从给定数字的最高位开始遍历，如果某一位是1，后面有k位，就加上f(k)，因为如果我们把当前位变成0，那么后面k位就可以直接从斐波那契数列中取值了。然后标记pre为1，再往下遍历，如果遇到0位，则pre标记为0。如果当前位是1，pre也是1，那么直接返回结果。最后循环退出后我们要加上数字本身这种情况，参见代码如下： 

 

解法二：

复制代码
class Solution {
public:
    int findIntegers(int num) {
        int res = 0, k = 31, pre = 0;
        vector<int> f(32, 0);
        f[0] = 1; f[1] = 2;
        for (int i = 2; i < 31; ++i) {
            f[i] = f[i - 2] + f[i - 1];
        }
        while (k >= 0) {
            if (num & (1 << k)) {
                res += f[k];
                if (pre) return res;
                pre = 1;
            } else pre = 0;
            --k;
        }
        return res + 1;
    }
};
复制代码
 

类似题目：

House Robber II

House Robber

Ones and Zeroes

 

参考资料：

https://discuss.leetcode.com/topic/90571/java-solution-dp

https://discuss.leetcode.com/topic/90639/c-non-dp-o-32-fibonacci-solution

https://discuss.leetcode.com/topic/90671/java-o-1-time-o-1-space-dp-solution

http://www.geeksforgeeks.org/count-number-binary-strings-without-consecutive-1s/

 

LeetCode All in One 题目讲解汇总(持续更新中...)

分类: LeetCode
