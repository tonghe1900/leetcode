package alite.leetcode.xx2.sucess.before;
/**
 * Count digits in given number N which divide N - GeeksforGeeks

Count digits in given number N which divide N - GeeksforGeeks
Given a number N which may be 10^5 digits long, the task is to count all the digits in N which 
divide N. Divisibility by 0 is not allowed. If any digit in N which is repeated divides N, then all repetitions of 
that digit should be counted i. e., N = 122324, here 2 divides N and it appears 3 times. So count for digit 2 will be 3.

A simple solution for this problem is to read number in string form and one by one check
 divisibility by each digit which appears in N. Time complexity for this approach will be O(N2).


An efficient solution for this problem is to use an extra array divide[] of size 10. Since we have only 10 digits so run a loop 
from 1 to 9 and check divisibility of N with each digit from 1 to 9. If any digit divides N then mark true in divide[] array 
at digit as index. Now traverse the number string and increment result if divide[i] is true for current digit i.
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



//Function to count digits which appears in N and

//divide N

//divide[10]  --> array which tells that particular

//             digit divides N or not

//count[10]   --> counts frequency of digits which

//             divide N

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
public class Count_digits_in_given_number_N_which_divide_N_frustrated {

}
