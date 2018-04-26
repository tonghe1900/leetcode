package alite.leetcode.xx4.finalLeft;
/**
 * https://leetcode.com/problems/nth-digit/
Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
Note:
n is positive and will fit within the range of a 32-bit signed integer (n < 231).
Example
Input:
11
Output:
0
\\
n
1, 2, 3, 4, 5, 6, 7, 8, 9      1*9
10  11               ... 99     2* 90
100                  ... 999     3* 900
1000                     9999     4*9000

1000   1001    1002    1003

15  16
 public int nthDigit(int n){
     int startLength = 1;
     int startNumber = 9;
     while(n> startLength*startNumber){
         n-=startLength*startNumber;
         startLength+=1;
         startNumber*=10;
     }
     
    int rankOfNumber =  (n%startLength==0)? (n/startLength):(n/startLength+1) ;//  3   4
    int rankOfDigitsInCertainNumber =  n%startLength==0?startLength: (n%startLength);
    return ((""+(Math.pow(10, startLength-1) +  (rankOfNumber-1))).charAt(rankOfDigitsInCertainNumber-1))-'0';
     
 }    
Explanation:
The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
https://discuss.leetcode.com/topic/59314/java-solution
Straight forward way to solve the problem in 3 steps:
find the length of the number where the nth digit is from
find the actual number where the nth digit is from
find the nth digit and return
 public int findNthDigit(int n) {
  int len = 1;
  long count = 9;
  int start = 1;

  while (n > len * count) {
   n -= len * count;
   len += 1;
   count *= 10;
   start *= 10;
  }

  start += (n - 1) / len;
  String s = Integer.toString(start);
  return Character.getNumericValue(s.charAt((n - 1) % len));
 }

https://discuss.leetcode.com/topic/59314/java-solution/6

1位数到n位数共有多少个数字可以先计算出来写在程序中,

2
3
4
5
6
7
8
9
10


int main () {


    long long sum = 0;


    for(int i = 0;; i++){


        long long t = 9 * (long long)pow(10, i) * (i + 1);


        if(t > INT_MAX) break;


        sum += t;


        cout<<i<<':'<<t<<" sum:"<<sum<<endl;


    }


    return 0;


}


class Solution {

    int arr[9] = {0, 9, 189, 2889, 38889, 488889, 5888889, 68888889, 788888889};

public:

    int findNthDigit(int n) {

        int index;

        for(index = 0; index < 9 && arr[index] < n; index++); // 确定位数

        int t = (n - arr[index - 1] - 1);

        int num = (t / index) + (int)pow(10, index - 1); // 确定数

        int p = index - (t % index) - 1; // 确定第几位

        for(int i = 0; i < p; i++){ // 找出该位

            num /= 10;

        }

        return num % 10; // 个位为我们要找的数

    }

};
 * @author het
 *
 */

//The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
//https://discuss.leetcode.com/topic/59314/java-solution
//Straight forward way to solve the problem in 3 steps:
//find the length of the number where the nth digit is from
//find the actual number where the nth digit is from
//find the nth digit and return
public class LeetCode400NthDigit {
	public static int findNthDigit(int n) {
		  int len = 1;
		  long count = 9;
		  int start = 1;

		  while (n > len * count) {
		   n -= len * count;
		   len += 1;
		   count *= 10;
		   start *= 10;
		  }

		  start += (n - 1) / len;
		  String s = Integer.toString(start);
		  return Character.getNumericValue(s.charAt((n - 1) % len));
		 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(nthDigit(189+9));

	}
	
	 public static int nthDigit(int n){
	     int startLength = 1;
	     int startNumber = 9;
	     while(n> startLength*startNumber){
	         n-=startLength*startNumber;
	         startLength+=1;
	         startNumber*=10;
	     }
	     
	    int rankOfNumber =  (n%startLength==0)? (n/startLength):(n/startLength+1) ;//  3   4
	    int rankOfDigitsInCertainNumber =  n%startLength==0?startLength: (n%startLength);
	    return ((""+(Math.pow(10, startLength-1) +  (rankOfNumber-1))).charAt(rankOfDigitsInCertainNumber-1))-'0';
	     
	 }   

}
