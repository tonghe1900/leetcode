package alite.leetcode.conquerLeft;
/**
 * Apples

http://www.cnblogs.com/theskulls/p/5385898.html
果园里有一堆苹果，一共n头(n大于1小于9)熊来分，第一头为小东，它把苹果均分n份后，多出了一个，它扔掉了这一个，拿走了自己的一份苹果，接着第二头熊重复这一过程，
即先均分n份，扔掉一个然后拿走一份，以此类推直到最后一头熊都是这样(最后一头熊扔掉后可以拿走0个，也算是n份均分)。问最初这堆苹果最少有多少个。
给定一个整数n,表示熊的个数，返回最初的苹果数。保证有解。
测试样例：
2
返回：3
 * @author het
 *
 */
public class DivideApples {
	
	public int getInitial(int n) {
        // write code here
          for(int i=0;;i++){
 
            int apple = i*n+1; // 第一个人得到 i 个苹果，则第一个人分苹果时共有( i * n+1)个苹果  这个数要能 除以 n 余 1
            // 对第二个人分时候的苹果总数是： apple/n * (n - 1) 同样也要除以 n 余 1 ，下面循环判断  n个人都要满足这个条件。
 
            int t = apple;
 
            boolean flag = true;
 
            for(int j = n; j>0; j--){               
 
                if(t % n == 1 )
                    t = t/n * (n-1);// 第 i 个人分后的苹果数量  也就是 第 i + 1 人分之前的苹果数量 
 
                else{
                    flag = false;
                    break;
                }
 
            }
 
            if(flag) 
                return apple;
 
        }
    }
	
	/**
	 * Count digits in given number N which divide N - GeeksforGeeks

Count digits in given number N which divide N - GeeksforGeeks
Given a number N which may be 10^5 digits long, the task is to count all the digits in N which 
divide N. Divisibility by 0 is not allowed. If any digit in N which is repeated divides N, then all repetitions of that digit should be counted i. e., N = 122324, here 2 divides N and it appears 3 times. So count for digit 2 will be 3.

A simple solution for this problem is to read number in string form and one by one check divisibility by each digit which appears in N. Time complexity for this approach will be O(N2).


An efficient solution for this problem is to use an extra array divide[] of size 10. Since we have only 10 digits so run a loop from 1 to 9 and check divisibility of N with each digit from 1 to 9. If any digit divides N then mark true in divide[] array at digit as index. Now traverse the number string and increment result if divide[i] is true for current digit i.
How to check divisibility of a digit for large N stored as string?
The idea is to use distributive property of mod operation.
(x+y)%a = ((x%a) + (y%a)) % a.


bool divisible(string N, int digit)

{

    int ans = 0;

    for (int i = 0; i < N.length(); i++)

    {

        // (N[i]-'0') gives the digit value and

        // form the number

        ans  = (ans*10 + (N[i]-'0'));

        ans %= digit;

    }

    return (ans == 0);

}

 

// Function to count digits which appears in N and

// divide N

// divide[10]  --> array which tells that particular

//                 digit divides N or not

// count[10]   --> counts frequency of digits which

//                 divide N

int allDigits(string N)

{

    // We initialize all digits of N as not divisble

    // by N.

    bool divide[10] = {false};

    divide[1] = true;  // 1 divides all numbers

 

    // start checking divisibilty of N by digits 2 to 9

    for (int digit=2; digit<=9; digit++)

    {

        // if digit divides N then mark it as true

        if (divisible(N, digit))

            divide[digit] = true;

    }

 

    // Now traverse the number string to find and increment

    // result whenever a digit divides N.

    int result = 0;

    for (int i=0; i<N.length(); i++)

    {

        if (divide[N[i]-'0'] == true)

            result++;

    }

 

    return result;

}
Read full article from Count digits in given number N which divide N - GeeksforGeeks
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new DivideApples().getInitial(7));

	}

}
