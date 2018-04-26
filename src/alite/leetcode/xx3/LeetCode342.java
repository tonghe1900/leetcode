package alite.leetcode.xx3;
/**
 * LeetCode 342 - Power of Four

https://www.hrwhisper.me/leetcode-power-four/
Given an integer (signed 32 bits), write a function to check whether it is a power of 4.
Example:
Given num = 16, return true. Given num = 5, return false.
Follow up: Could you solve it without loops/recursion?

https://segmentfault.com/a/1190000003481153
时间 O(1) 空间 O(1)
思路
1   0 0000 0001
4   0 0000 0100
16  0 0001 0000
64  0 0100 0000
256 1 0000 0000
仔细观察可以发现，4的幂的二进制形式中，都是在从后向前的奇数位有一个1，所以只要一个数符合这个模式，就是4的幂
private boolean bruteForceBit(long num){
    boolean res = false;
    if(num <= 0) return res;
    for(int i = 1; i <= 64; i++){
        // 如果该位是0，则不操作
        if((num & 1) == 1){
            // 如果是偶数位为1，说明不是4的幂
            if(i % 2 == 0) return false;
            // 如果是奇数位为1，如果之前已经有1了，则也不是4的幂
            if(res){
                return false;
            } else {
            // 如果是第一次出现技术位为1，则可能是4的幂
                res = true;
            }
        }
        num = num >>> 1;
    }
    return res;
}
http://www.programcreek.com/2015/04/leetcode-power-of-four-java/
public boolean isPowerOfFour(int num) {
    int count0=0;
    int count1=0;
 
    while(num>0){
        if((num&1)==1){
            count1++;
        }else{
            count0++;
        }
        num>>=1;
    }
 
    return count1==1 && (count0%2==0);
}

在Power of Two中，我们有一个解法是通过判断n & (n - 1)是否为0来判断是否为2的幂，因为4的幂肯定也是2的幂，所以这也可以用到这题来。那4的幂和2的幂有什么区别呢？根据上一个解法，我们知道4的幂的1只可能在奇数位，而2的幂的1可能在任意位，所以我们只要判断是不是奇数位是1就行了。因为根据n & (n - 1)我们已经筛出来那些只有1个1的数了，所以和010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101 也就是0x5555555555555555相与就能知道1是在奇数位还是偶数位了。
https://www.hrwhisper.me/leetcode-power-four/
power of two的时候，我们用 n &(n-1) ==0 来判断是否是2的次方数，这里也先判断一下是否为2的次方数。然后再把不是4的次方数排除掉，比如8.
我们来看看2的次方数的规律：

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15


1 => 1


10 => 2


100 => 4


1000 => 8


10000 => 16


100000 => 32


1000000 => 64


10000000 => 128


100000000 => 256


1000000000 => 512


10000000000 => 1024


100000000000 => 2048


1000000000000 => 4096


10000000000000 => 8192


100000000000000 => 16384

我们发现，每次不过是将1向左移动一个位置，然后低位补0（这不是废话么= =|||）
那么4的次方数就是将1向左移动两个位置喽 （还是废话（╯－＿－）╯╧╧）
观察一下位置，4的次方中的1只出现在奇数的位置上！就是说，上面的二进制从右往左，第1，3，5，……位置上。
So，如果我们与上一个可以在奇数上面选择位置的数，也就是 0101 0101 ……那么就把不是4次方的排除掉啦
0101 0101 …… 十六进制表示为： 0x55555555
private boolean smartBit(long num){
    return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0x5555555555555555l) == num);
}

    public boolean isPowerOfFour(int num) {

        return num > 0 && (num & (num - 1)) ==0  && (num & 0x55555555) !=0;

    }

https://leetcode.com/discuss/97930/java-1-line-cheating-for-the-purpose-of-not-using-loops
public boolean isPowerOfFour(int num) {
    return (Math.log(num) / Math.log(4)) % 1 == 0;
}


    public boolean isPowerOfFour(int num) {
        return num > 0 && (num&(num-1)) == 0 && (num & 0x55555555) != 0;
        //0x55555555 is to get rid of those power of 2 but not power of 4
        //so that the single 1 bit always appears at the odd position 
    }
http://www.cnblogs.com/grandyang/p/5403783.html
首先根据Power of Two中的解法二，我们知道num & (num - 1)可以用来判断一个数是否为2的次方数，更进一步说，就是二进制表示下，只有最高位是1，那么由于是2的次方数，不一定是4的次方数，比如8，所以我们还要其他的限定条件，我们仔细观察可以发现，4的次方数的最高位的1都是计数位，那么我们只需与上一个数(0x55555555) <==> 1010101010101010101010101010101，如果得到的数还是其本身，则可以肯定其为4的次方数：
    bool isPowerOfFour(int num) {
        return num > 0 && !(num & (num - 1)) && (num & 0x55555555) == num;
    }

或者我们在确定其是2的次方数了之后，发现只要是4的次方数，减1之后可以被3整除
    bool isPowerOfFour(int num) {
        return num > 0 && !(num & (num - 1)) && (num - 1) % 3 == 0;
    }

https://leetcode.com/discuss/97967/java-1-line-of-code-and-can-be-extended-to-any-radix-solution
The idea is that numbers in quaternary system that is power of 4 will be like 10, 100, 1000 and such. Similar to binary case. And this can be extended to any radix.
public boolean isPowerOfFour(int num) {
    return Integer.toString(num, 4).matches("10*");
}
https://leetcode.com/discuss/97926/java-line-solution-using-bitcount-numberoftrailingzeros
public boolean isPowerOfFour(int num) {
    return num>=1 && Integer.bitCount(num) == 1 && Integer.numberOfTrailingZeros(num)%2 == 0;
}
最简单的解法，不断将原数除以4，一旦无法整除，余数不为0，则说明不是4的幂，如果整除到1，说明是4的幂。
private boolean bruteForceMod(long num){
    if(num <= 0) return false;
    while(num % 4 == 0){
        num = num / 4;
    }
    return num == 1;
}
http://www.cnblogs.com/grandyang/p/5403783.html
    bool isPowerOfFour(int num) {
        return num > 0 && int(log10(num) / log10(4)) - log10(num) / log10(4) == 0;
    }
http://www.programcreek.com/2015/04/leetcode-power-of-four-java/
We can use the following formula to solve this problem without using recursion/iteration.
Power of Four
public boolean isPowerOfFour(int num) {
   if(num==0) return false;
 
   int pow = (int) (Math.log(num) / Math.log(4));
   if(num==Math.pow(4, pow)){
       return true;
   }else{
       return false;
   }
}
 * @author het
 *
 */
public class LeetCode342 {
	//http://www.programcreek.com/2015/04/leetcode-power-of-four-java/
		public boolean isPowerOfFour(int num) {
		    int count0=0;
		    int count1=0;
		 
		    while(num>0){
		        if((num&1)==1){
		            count1++;
		        }else{
		            count0++;
		        }
		        num>>=1;
		    }
		 
		    return count1==1 && (count0%2==0);
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
