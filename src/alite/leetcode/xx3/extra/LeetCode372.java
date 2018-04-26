package alite.leetcode.xx3.extra;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 372 - Super Pow

http://bookshadow.com/weblog/2016/07/07/leetcode-super-pow/
Your task is to calculate ab mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.
Example1:
a = 2
b = [3]

Result: 8
Example2:
a = 2
b = [1,0]

Result: 1024
题目描述：
计算a ^ b mod 1337的值。a是正整数，b是一个极大的正整数，以数组形式给出。
https://all4win78.wordpress.com/2016/07/07/leetcode-372-super-pow/

从最低位到最高位，用一个临时变量记录当前位的base，比如最后一位的base是a，倒数第二位的是a^10，当然，直接用a来记录也可以。

    public int superPow(int a, int[] b) {

        int magicNum = 1337;

        a = a % magicNum;

        int prod = 1;

        for (int i = b.length - 1; i >= 0; i--) {

            int temp = 1;

            for (int j = 0; j <= 9; j++) {

                if (b[i] == j) {

                    prod = (prod * temp) % magicNum;

                }

                temp = (temp * a) % magicNum;

            }

            a = temp;

        }

        return prod;

    }

http://www.voidcn.com/blog/niuooniuoo/article/p-6093171.html
算法需要利用恒等式  (a*b)%c = (a%c)*(b%c)。以下为恒等式的证明。
设a/c=m,则mc+a%c =a;
设b/c=n,则nc+b%c =b;
    (a*b)%c 
= { (mc+a%c)*(nc+b%c ) }%c 
= { mcnc + (nc)*(a%c) + (mc)*(b%c) + (a%c)*(b%c) } % c   其中 mcnc + (nc)*(a%c) + (mc)*(b%c)可以整除c
= {(a%c)*(b%c)}%c。
 public int superPow(int a, int[] b) {
  int res = 1;
  for (int i = 0; i < b.length; i++) {
   res = pow(res, 10) * pow(a, b[i]) % 1337;
  }
  return res;
 }

 public int pow(int a, int b) {
  if (b == 0) return 1;
  if (b == 1) return a % 1337;
  return pow(a % 1337, b / 2) * pow(a % 1337, b - b / 2) % 1337;
 }
https://discuss.leetcode.com/topic/51328/7ms-java-solution-using-fast-power-algorithm
private static final int NUM = 1337;
public int superPow(int a, int[] b) {
    int ans = 1;
    //not expecting to be a part of input
    if(b==null||b.length==0)
        return 0;
    a = a%NUM;
    int len = b.length;
    for (int i = 0; i < len; i++) {
        ans = ((pow(ans,10)*pow(a,b[i]))%NUM);
    }
    return ans;
}

private int pow(int a, int b){
    if(b==1)
        return a;
    if(b==0)
        return 1;
    int x = pow(a,b/2)%NUM;
    x = (x*x)%NUM;
    if((b&1)==1)
        x = (x*a)%NUM;
    return x;
}
http://xiadong.info/2016/07/leetcode-372-super-pow/
a ^ (n + m) = (a ^ n) * (a ^ m)
用b{i}表示数组b中到下标i为止的数, a ^ b{i} = ((a ^ b{i - 1}) ^ 10) * (a ^ b[i]), 把幂运算替换为乘法运算和循环, 在每一个循环体中都对结果取余. 就可以得到结果. 其中b[i]为0和第一次循环要特别处理
http://www.cnblogs.com/grandyang/p/5651982.html

1. a^b % 1337 = (a%1337)^b % 1337
2. xy % 1337 = ((x%1337) * (y%1337)) % 1337, 其中xy是一个数字如:45, 98等等
其中第一个公式可以用来削减a的值, 第二个公式可以将数组一位位的计算, 比如 12345^678, 首先12345可以先除余1337, 设结果为X, 则原式就可以化为: 
X^678 = ((X^670 % 1337) * (X^8 % 1337)) % 1337 = (pow((X^670 % 1337), 10) * (X^8 % 1337)) % 1337
在上面我用了一个pow来化简表示 X^670 = pow(X^670, 10), 当然不是库函数里面pow, 因为会超出界限, 因此我们需要自己在写一个pow来一个个的边乘边除余.

https://www.hrwhisper.me/leetcode-super-pow/
一个数e可以写成如下形式：
e=\sum _{i=0}^{n-1}a_{i}2^{i}
显然，对于b的e次方，有：
b^{e}=b^{\left(\sum _{i=0}^{n-1}a_{i}2^{i}\right)}=\prod _{i=0}^{n-1}\left(b^{2^{i}}\right)^{a_{i}}
c\equiv \prod _{i=0}^{n-1}\left(b^{2^{i}}\right)^{a_{i}}\ ({\mbox{mod}}\ m)
此外，还有：
c mod m = (a ⋅ b) mod m  = [(a mod m) ⋅ (b mod m)] mod m
参照wiki :https://en.wikipedia.org/wiki/Modular_exponentiation
看懂了上面的式子后，回到此题，此题b用数组表示，其实就是把上面的数e的2改为10即可。

    private int mod = 1337;

    public int superPow(int a, int[] b) {

        int n = b.length;

  int ans = 1;

  for (int i = n - 1; i >= 0; i--) {

   ans = ans * quick_pow(a, b[i]) % mod;

   a = quick_pow(a, 10);

  }

  return ans;

 }

 

 int quick_pow(int a, int b) {

  int ans = 1;

  a %= mod;

  while (b > 0) {

   if ((b & 1) !=0) ans = ans * a % mod;

   a = a * a % mod;

   b >>= 1;

  }

  return ans;

 

    }

    def superPow(self, a, b):
        """
        :type a: int
        :type b: List[int]
        :rtype: int
        """
        ans, pow = 1, a
        for x in range(len(b) - 1, -1, -1):
            ans = (ans * (pow ** b[x]) % 1337) % 1337
            pow = (pow ** 10) % 1337
        return ans
https://discuss.leetcode.com/topic/50543/8ms-java-solution-using-fast-power
http://www.luozhipeng.com/?p=527
private static final int M = 1337;

    public int normalPow(int a, int b) {
        int result = 1;
        while (b != 0) {
            if (b % 2 != 0)
                result = result * a % M;
            a = a * a % M;
            b /= 2;
        }
        return result;
    }
    public int superPow(int a, int[] b) {
        a %= M;
        int result = 1;
        for (int i = b.length - 1; i >= 0; i--) {
            result = result * normalPow(a, b[i]) % M;
            a = normalPow(a, 10);
        }
        return result;
    }
X. Pigeonhole principle
https://discuss.leetcode.com/topic/51176/7ms-java-solution
if a^k % m = a^t % m, then a^(k+1) % m must be equal to a^(t+1).
Let's say a^14 %m = a^27 %m, then a^28 % m is equal to a^15%m,
a^k %m = a^(k-13)%m.
let's have an array to keep the power of 'a' and the index is mod value, example:
a=2, b=0, modToPower[1] = 0;
a=2, b =1, modToPower[2] = 1;
a=2, b =2, modToPower[4] = 2;
...
the length of modToPower is 1337(it can be extended to any other positive integer).
and once there is b=x, and modToPower[a^b%1337] is existing, then we can exit the loop.
regarding array 'b', like above if a^k % m = a^(k-13)%m, we can easily map the value represented by array 'b' to mod by 13.
like a^[1,2,3] = a^123 = a^110 =...= a^6;
public int superPow(int a, int[] b) {
    if (a <= 0 || b == null || b.length == 0) {
     return 0;
    }
 int modNum = 1337;
 a %= modNum;
 int[] log = new int[modNum];
 for (int i = 0; i < log.length; i++) {
  log[i] = -1;
 }
 int startNum = 1;
 Map<Integer, Integer> powerToModNum = new HashMap<Integer, Integer>();
 int powerMod;
 for (int startIndex = 0; ; startIndex++) {
  startNum %= modNum;
  if (log[startNum] == -1) {
   log[startNum] = startIndex;
   powerToModNum.put(startIndex, startNum);
   startNum = (startNum*a)%modNum;
   
  } else {
   powerMod = startIndex - log[startNum%modNum];
   break;
  }
 }
 
 int j = 0;
 for (int power : b) {
  j = (j*10 + power%powerMod)%powerMod;
 }
 return powerToModNum.get(j);
}
https://discuss.leetcode.com/topic/51460/java-solution-pigeonhole-principle
The idea is to find a cycle in the power. As the number is modded by 1337, there must be a duplication among the power of from 1 to 1337. This tells which position in cycle b corresponds to.

public int superPow(int a, int[] b) {
 int []pows = new int[1337];    // max cycle is 1337  
    Set<Integer> set = new HashSet<Integer>();
    
    // pigeon hole principle dictates that must be a duplicate among the power from 1 to 1337 if moded by 1337
    int cycle = 0;
    int val = 1;
    for (int i = 0; i < 1337; i++)  {
        val = (int)(((long)val * a) % 1337);
        // cycle found
        if (set.contains(val)) break;
        set.add(val);
        pows[cycle++] = val;
    }
    
    // b: String -> BigInteger
    StringBuilder str = new StringBuilder();
    for(int v: b) str.append(v);
    BigInteger bVal = new BigInteger(str.toString());
    
    bVal = bVal.subtract(new BigInteger("1")).mod(new BigInteger("" + cycle));
    return pows[bVal.intValue()];
}
 * @author het
 *
 */
public class LeetCode372 {
	
//	https://discuss.leetcode.com/topic/51176/7ms-java-solution
//		if a^k % m = a^t % m, then a^(k+1) % m must be equal to a^(t+1).
//		Let's say a^14 %m = a^27 %m, then a^28 % m is equal to a^15%m,
//		a^k %m = a^(k-13)%m.
//		let's have an array to keep the power of 'a' and the index is mod value, example:
//		a=2, b=0, modToPower[1] = 0;
//		a=2, b =1, modToPower[2] = 1;
//		a=2, b =2, modToPower[4] = 2;
//		...
//		the length of modToPower is 1337(it can be extended to any other positive integer).
//		and once there is b=x, and modToPower[a^b%1337] is existing, then we can exit the loop.
//		regarding array 'b', like above if a^k % m = a^(k-13)%m, we can easily map the value represented by array 'b' to mod by 13.
//		like a^[1,2,3] = a^123 = a^110 =...= a^6;
//		public static int superPow(int a, int[] b) {
//		    if (a <= 0 || b == null || b.length == 0) {
//		     return 0;
//		    }
//		 int modNum = 1337;
//		 a %= modNum;
//		 int[] log = new int[modNum];
//		 for (int i = 0; i < log.length; i++) {
//		  log[i] = -1;
//		 }
//		 int startNum = 1;
//		 Map<Integer, Integer> powerToModNum = new HashMap<Integer, Integer>();
//		 int powerMod;
//		 for (int startIndex = 0; ; startIndex++) {
//		  startNum %= modNum;
//		  if (log[startNum] == -1) {
//		   log[startNum] = startIndex;
//		   powerToModNum.put(startIndex, startNum);
//		   startNum = (startNum*a)%modNum;
//		   
//		  } else {
//		   powerMod = startIndex - log[startNum%modNum];
//		   break;
//		  }
//		 }
//		 
//		 int j = 0;
//		 for (int power : b) {
//		  j = (j*10 + power%powerMod)%powerMod;
//		 }
//		 return powerToModNum.get(j);
//		}
		
//		Example2:
//			a = 2
//			b = [1,0]
//
//			Result: 1024
////	http://www.voidcn.com/blog/niuooniuoo/article/p-6093171.html
////		算法需要利用恒等式  (a*b)%c = (a%c)*(b%c)。以下为恒等式的证明。
////		设a/c=m,则mc+a%c =a;
////		设b/c=n,则nc+b%c =b;
////		    (a*b)%c 
////		= { (mc+a%c)*(nc+b%c ) }%c 
////		= { mcnc + (nc)*(a%c) + (mc)*(b%c) + (a%c)*(b%c) } % c   其中 mcnc + (nc)*(a%c) + (mc)*(b%c)可以整除c
////		= {(a%c)*(b%c)}%c。
//		 public int superPow(int a, int[] b) {
//		  int res = 1;
//		  for (int i = 0; i < b.length; i++) {
//		   res = pow(res, 10) * pow(a, b[i]) % 1337;
//		  }
//		  return res;
//		  
//		  
//		  for (int i = 0; i < b.length; i++) {
//			   res = 10 * res + b[i];
//			  }
//			  return res;
//		 }
//
//		 public int pow(int a, int b) {
//		  if (b == 0) return 1;
//		  if (b == 1) return a % 1337;
//		  return pow(a % 1337, b / 2) * pow(a % 1337, b - b / 2) % 1337;
//		 }
	
	
//	1. a^b % 1337 = (a%1337)^b % 1337
//			2. xy % 1337 = ((x%1337) * (y%1337)) % 1337, 其中xy是一个数字如:45, 98等等
//			其中第一个公式可以用来削减a的值, 第二个公式可以将数组一位位的计算, 比如 12345^678, 首先12345可以先除余1337, 设结果为X, 则原式就可以化为: 
//			X^678 = ((X^670 % 1337) * (X^8 % 1337)) % 1337 = (pow((X^67 % 1337), 10) * (X^8 % 1337)) % 1337
//			在上面我用了一个pow来化简表示 X^670 = pow(X^670, 10), 当然不是库函数里面pow, 因为会超出界限, 因此我们需要自己在写一个pow来一个个的边乘边除余.
//
//			https://www.hrwhisper.me/leetcode-super-pow/
//			一个数e可以写成如下形式：
//			e=\sum _{i=0}^{n-1}a_{i}2^{i}
//			显然，对于b的e次方，有：
//			b^{e}=b^{\left(\sum _{i=0}^{n-1}a_{i}2^{i}\right)}=\prod _{i=0}^{n-1}\left(b^{2^{i}}\right)^{a_{i}}
//			c\equiv \prod _{i=0}^{n-1}\left(b^{2^{i}}\right)^{a_{i}}\ ({\mbox{mod}}\ m)
//			此外，还有：
//			c mod m = (a ⋅ b) mod m  = [(a mod m) ⋅ (b mod m)] mod m
//			参照wiki :https://en.wikipedia.org/wiki/Modular_exponentiation
//			看懂了上面的式子后，回到此题，此题b用数组表示，其实就是把上面的数e的2改为10即可。
	
	
//	1. a^b % 1337 = (a%1337)^b % 1337
//	2. xy % 1337 = ((x%1337) * (y%1337)) % 1337, 其中xy是一个数字如:45, 98等等
//	其中第一个公式可以用来削减a的值, 第二个公式可以将数组一位位的计算, 比如 12345^678, 首先12345可以先除余1337, 设结果为X, 则原式就可以化为: 
//	X^678 = ((X^670 % 1337) * (X^8 % 1337)) % 1337 = (pow((X^67 % 1337), 10) * (X^8 % 1337)) % 1337
//	在上面我用了一个pow来化简表示 X^670 = pow(X^670, 10), 当然不是库函数里面pow, 因为会超出界限, 因此我们需要自己在写一个pow来一个个的边乘边除余.

			    private static int mod = 1337;

			    public static int superPow(int a, int[] b) {

			        int n = b.length;

			  int ans = 1;

			  for (int i = n - 1; i >= 0; i--) {

			   ans = ans * quick_pow(a, b[i]) % mod;

			   a = quick_pow(a, 10);

			  }

			  return ans;

			 }

			    static int quick_pow(int a, int b) {
			    	  if(b == 0) return 1;
			    	  if(b == 1) return a%mod;
			    	  a %= mod;
			    	  int subResult  =quick_pow(a, b/2) % mod;
			    	  if(b % 2 ==1){
			    		   return (subResult* subResult*a) %mod;
			    	  }else{
			    		  return (subResult* subResult) %mod;
			    	  }
			    }

//			static int quick_pow(int a, int b) {
//
//			  int ans = 1;
//
//			  a %= mod;
//
//			  while (b > 0) {
//
//			   if ((b & 1) !=0) ans = ans * a % mod;
//
//			   a = a * a % mod;
//
//			   b >>= 1;
//
//			  }
//
//			  return ans;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(superPow(2, new int[]{1,0}));

	}

}
