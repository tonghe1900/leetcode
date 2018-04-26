package alite.leetcode.newExtra;
/**
 * Smallest number with at least n digits in factorial - GeeksforGeeks

Smallest number with at least n digits in factorial - GeeksforGeeks
Given a number n. The task is to find the smallest number whose factorial contains at least n digits.

we need to determine the interval in which we can find a factorial which has at least n digits. Following are some observations:
For a large number we can always say that it’s factorial has more digits than the number itself. For example factorial of 100 has 158 digits which is greater than 100.
However for smaller numbers, that might not be the case. For example factorial of 8 has only 5 digits, which is less than 8. In fact numbers up to 21 follow this trend.
Hence if we search from 0! to n! to find a result having at least n digits, we won’t be able to find the result for smaller numbers.
For example suppose n = 5, now as we’re searching in [0,n] the maximum number of digits we can obtain is 3, (found in 5! = 120). However if we search in [0, 2*n] (0 to 10), we can find 8! has 5 digits.
Hence, if we can search for all factorial from 0 to 2*n , there will always be a number k which will have at least n digits in its factorial.(Readers are advised to try on their own to figure out this fact)
We can say conclude if we have to find a number k,
such that k! has at least n digits, we can be sure
that k lies in [0,2*n]

i.e., 0<= k <= 2*n


// Returns the number of digits present in n!

int findDigitsInFactorial(int n)

{

    // factorial of -ve number doesn't exists

    if (n < 0)

        return 0;

 

    // base case

    if (n <= 1)

        return 1;

 

    // Use Kamenetsky formula to calculate the

    // number of digits

    double x = ((n*log10(n/M_E)+log10(2*M_PI*n)/2.0));

 

    return floor(x)+1;

}

 

// This function receives an integer n and returns

// an integer whose factorial has at least n digits

int findNum(int n)

{

    // (2*n)! always has more digits than n

    int low = 0, hi = 2*n;

 

    // n <= 0

    if (n <= 0)

        return -1;

 

    // case for n = 1

    if (findDigitsInFactorial(low) == n)

        return low;

 

    // now use binary search to find the number

    while (low <= hi)

    {

        int mid = (low+hi) / 2;

 

        // if (mid-1)! has lesser digits than n

        // and mid has n or more then mid is the

        // required number

        if (findDigits(mid) >= n && findDigits(mid-1)<n)

            return mid;

 

        else if (findDigits(mid) < n)

            low = mid+1;

 

        else

            hi = mid-1;

    }

    return low;

}
Read full article from Smallest number with at least n digits in factorial - GeeksforGeeks
 * @author het
 *
 */
public class Smallest_number_with_at_least_n_digits_in_factorial_GeeksforGeeks {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
