package alite.leetcode.newExtra;
/**
 * Sphenic Number - GeeksforGeeks

Sphenic Number - GeeksforGeeks
A Sphenic Number is a positive integer n which is product of exactly three distinct primes. 
The first few sphenic numbers are 30, 42, 66, 70, 78, 102, 105, 110, 114, …
Given a number n, determine whether it is a Sphenic Number or not. 

Sphenic number can be checked by first generating the Least prime factor of numbers till n.
Then we could simply divide the number by its least prime factor and then that number by its least prime factor, and so on and then check if the number has exactly 3 distinct prime factors.

const int MAX = 1000;

 

// Create a vector to store least primes.

// Initialize all entries as 0.

vector<int> least_pf(MAX, 0);

 

// This function fills values in least_pf[].

// such that the value of least_pf[i] stores

// smallest prime factor of i.

// This function is based on sieve of Eratosthenes

void leastPrimeFactor(int n)

{

    // Least prime factor for 1 is 1

    least_pf[1] = 1;

 

    // Store least prime factor for all other

    // numbers.

    for (int i = 2; i <= n; i++)

    {

        // least_pf[i] == 0 means i is prime

        if (least_pf[i] == 0)

        {

            // Initializing prime number as its own

            // least prime factor.

            least_pf[i] = i;

 

            // Mark 'i' as a divisor for all its

            // multiples that are not already marked

            for (int j = 2*i; j <= n; j += i)

                if (least_pf[j] == 0)

                    least_pf[j] = i;

        }

    }

}

 

// Function returns true if n is a sphenic number and

// No otherwise

bool isSphenic(int n)

{

    // Stores three prime factors of n. We have at-most

    // three elements in s.

    set<int> s;

 

    // Keep finding least prime factors until n becomes 1

    while (n > 1)

    {

        // Find least prime factor of current value of n.

        int lpf = least_pf[n];

 

        // We store current size of s to check if a prime

        // factor repeats

        int init_size = s.size();

 

        // Insert least prime factor of current value of n

        s.insert(lpf);

 

        // If either lpf repeats or number of lpfs becomes

        // more than 3, then return false.

        if (s.size() == init_size || s.size() > 3)

 

            // same prime divides the

            // number more than once

            return false;

 
//
//        // Divide n by lpf
//
//        n /= lpf;

    }

 

    // Return true if size of set is 3

    return (s.size() == 3);
 * @author het
 *
 */
public class Sphenic_Number {

}
