package important.backtrack;
/**
 * Compute sum of digits in all numbers from 1 to n
Given a number x, find sum of digits in all numbers from 1 to n. Examples:
Input: n = 5
Output: Sum of digits in numbers from 1 to 5 = 15

Input: n = 12
Output: Sum of digits in numbers from 1 to 12 = 51

Input: n = 328
Output: Sum of digits in numbers from 1 to 328 = 3241
Naive Solution: A naive solution is to go through every number x from 1 to n, and compute sum in x by traversing all digits of x. Below is C++ implementation of this idea.
// A Simple C++ program to compute sum of digits in numbers from 1 to n
#include<iostream>
using namespace std;
 
int sumOfDigits(int );
 
// Returns sum of all digits in numbers from 1 to n
int sumOfDigitsFrom1ToN(int n)
{
    int result = 0; // initialize result
 
    // One by one compute sum of digits in every number from
    // 1 to n
    for (int x=1; x<=n; x++)
        result += sumOfDigits(x);
 
    return result;
}
 
// A utility function to compute sum of digits in a
// given number x
int sumOfDigits(int x)
{
    int sum = 0;
    while (x != 0)
    {
        sum += x %10;
        x   = x /10;
    }
    return sum;
}
 
// Driver Program
int main()
{
    int n = 328;
    cout << "Sum of digits in numbers from 1 to " << n << " is "
         << sumOfDigitsFrom1ToN(n);
    return 0;
}
Run on IDE
Output
Sum of digits in numbers from 1 to 328 is 3241
  Efficient Solution: Above is a naive solution. We can do it more efficiently by finding a pattern.
Let us take few examples.
sum(9) = 1 + 2 + 3 + 4 ........... + 9
       = 9*10/2 
       = 45

sum(99)  = 45 + (10 + 45) + (20 + 45) + ..... (90 + 45)
         = 45*10 + (10 + 20 + 30 ... 90)
         = 45*10 + 10(1 + 2 + ... 9)
         = 45*10 + 45*10
         = sum(9)*10 + 45*10 

sum(999) = sum(99)*10 + 45*100
sun(10^d - 1) = sum(10 ^ (d-1))*10 + 45 * 
0-9
10 - 19
20 -29
..
90-99
100 - 109

110 - 119

..

190 - 199

..
200 - 209
..
290 - 299
...
300 - 309
...
390  - 399
...
400 - 409
..
490 - 499
..

990 - 999

In general, we can compute sum(10d – 1) using below formula
   sum(10d - 1) = sum(10d-1 - 1) * 10 + 45*(10d-1) 
In below implementation, the above formula is implemented using dynamic programming as there are overlapping subproblems. The above formula is one core step of the idea. Below is complete algorithm
Algorithm: sum(n)
1) Find number of digits minus one in n. Let this value be 'd'.  
   For 328, d is 2.

2) Compute some of digits in numbers from 1 to 10d - 1.  
   Let this sum be w. For 328, we compute sum of digits from 1 to 
   99 using above formula.

3) Find Most significant digit (msd) in n. For 328, msd is 3.

4) Overall sum is sum of following terms

    a) Sum of digits in 1 to "msd * 10d - 1".  For 328, sum of 
       digits in numbers from 1 to 299.
        For 328, we compute 3*sum(99) + (1 + 2)*100.  Note that sum of
        sum(299) is sum(99) + sum of digits from 100 to 199 + sum of digits
        from 200 to 299.  
        Sum of 100 to 199 is sum(99) + 1*100 and sum of 299 is sum(99) + 2*100.
        In general, this sum can be computed as w*msd + (msd*(msd-1)/2)*10d

    b) Sum of digits in msd * 10d to n.  For 328, sum of digits in 
       300 to 328.
        For 328, this sum is computed as 3*29 + recursive call "sum(28)"
        In general, this sum can be computed as  msd * (n % (msd*10d) + 1) 
        + sum(n % (10d))
Below is C++ implementation of above aglorithm.
// C++ program to compute sum of digits in numbers from 1 to n
#include<bits/stdc++.h>
using namespace std;
 
// Function to computer sum of digits in numbers from 1 to n
// Comments use example of 328 to explain the code
int sumOfDigitsFrom1ToN(int n)
{
    // base case: if n<10 return sum of
    // first n natural numbers
    if (n<10)
      return n*(n+1)/2;
 
    // d = number of digits minus one in n. For 328, d is 2
    int d = log10(n);
 
    // computing sum of digits from 1 to 10^d-1,
    // d=1 a[0]=0;
    // d=2 a[1]=sum of digit from 1 to 9 = 45
    // d=3 a[2]=sum of digit from 1 to 99 = a[1]*10 + 45*10^1 = 900
    // d=4 a[3]=sum of digit from 1 to 999 = a[2]*10 + 45*10^2 = 13500
    int *a = new int[d+1];
    a[0] = 0, a[1] = 45;
    for (int i=2; i<=d; i++)
        a[i] = a[i-1]*10 + 45*ceil(pow(10,i-1));
 
    // computing 10^d
    int p = ceil(pow(10, d));
 
    // Most significant digit (msd) of n,
    // For 328, msd is 3 which can be obtained using 328/100
    int msd = n/p;
 
    // EXPLANATION FOR FIRST and SECOND TERMS IN BELOW LINE OF CODE
    // First two terms compute sum of digits from 1 to 299
    // (sum of digits in range 1-99 stored in a[d]) +
    // (sum of digits in range 100-199, can be calculated as 1*100 + a[d]
    // (sum of digits in range 200-299, can be calculated as 2*100 + a[d]
    //  The above sum can be written as 3*a[d] + (1+2)*100
 
    // EXPLANATION FOR THIRD AND FOURTH TERMS IN BELOW LINE OF CODE
    // The last two terms compute sum of digits in number from 300 to 328
    // The third term adds 3*29 to sum as digit 3 occurs in all numbers 
    //                from 300 to 328
    // The fourth term recursively calls for 28
    return msd*a[d] + (msd*(msd-1)/2)*p +  
           msd*(1+n%p) + sumOfDigitsFrom1ToN(n%p);
}
 
// Driver Program
int main()
{
    int n = 328;
    cout << "Sum of digits in numbers from 1 to " << n << " is "
         << sumOfDigitsFrom1ToN(n);
    return 0;
}
Run on IDE
Output
Sum of digits in numbers from 1 to 328 is 3241
The efficient algorithm has one more advantage that we need to compute the array ‘a[]’ only once even when we are given multiple inputs.
This article is computed by Shubham Gupta. Please write comments if you find anything incorrect, or you want to share more information about the topic discussed above
 * @author het
 *
 */
public class Compute_sum_of_digits_in_all_numbers_from1ton {

}
