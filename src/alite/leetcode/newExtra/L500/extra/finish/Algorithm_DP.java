package alite.leetcode.newExtra.L500.extra.finish;
/**
 * Algorithm - Dynamic Programming

http://www.geeksforgeeks.org/count-arrays-adjacent-elements-one-divide-another/
Given two positive integer n and n. The task is to find the number of arrays of size n that can be formed such that :
Each element is in range [1, m]
All adjacent element are such that one of them divide the another i.e element Ai divides Ai + 1or Ai + 1 divides Ai + 2.
Input : n = 3, m = 3.
Output : 17
{1,1,1}, {1,1,2}, {1,1,3}, {1,2,1}, 
{1,2,2}, {1,3,1}, {1,3,3}, {2,1,1},
{2,1,2}, {2,1,3}, {2,2,1}, {2,2,2},
{3,1,1}, {3,1,2}, {3,1,3}, {3,3,1}, 
{3,3,3} are possible arrays.

Input : n = 1, m = 10.
Output : 10
We try to find number of possible values at each index of the array. First, at index 0 all values 
are possible from 1 to m. Now observe for each index, we can reach either to its
 multiple or its factor. So, precompute that and store it for all the elements.
  Hence for each position i, ending with integer x, we can go to next position i + 1, with the 
  array ending in integer with multiple of x or factor of x. Also, multiple of x or factor of x must 
  be less than m.
So, we define an 2D array dp[i][j], which is number of possible array (divisible adjacent element) of size i with j as its first index element.
1 <= i <= m, dp[1][m] = 1.
for 1 <= j <= m and 2 <= i <= n
  dp[i][j] = dp[i-1][j] + number of factor 
             of previous element less than m 
             + number of multiples of previous
             element less than m.
Time Complexity: O(N*M).

int numofArray(int n, int m)

{

    int dp[MAX][MAX];

 

    // For storing factors.

    vector<int> di[MAX];

 

    // For storing multiples.

    vector<int> mu[MAX];

 

    memset(dp, 0, sizeof dp);

    memset(di, 0, sizeof di);

    memset(mu, 0, sizeof mu);

 

    // calculating the factors and multiples

    // of elements [1...m].

    for (int i = 1; i <= m; i++)

    {

        for (int j = 2*i; j <= m; j += i)

        {

            di[j].push_back(i);

            mu[i].push_back(j);

        }

        di[i].push_back(i);

    }

 

    // Initalising for size 1 array for

    // each i <= m.

    for (int i = 1; i <= m; i++)

        dp[1][i] = 1;

 

    // Calculating the number of array possible

    // of size i and starting with j.

    for (int i = 2; i <= n; i++)

    {

        for (int j = 1; j <= m; j++)

        {

            dp[i][j] = 0;

 

            // For all previous possible values.

            // Adding number of factors.

            for (auto x:di[j])

                dp[i][j] += dp[i-1][x];

 

            // Adding number of multiple.

            for (auto x:mu[j])

                dp[i][j] += dp[i-1][x];

        }

    }

 

    // Calculating the total count of array

    // which start from [1...m].

    int ans = 0;

    for (int i = 1; i <= m; i++)

    {

        ans += dp[n][i];

        di[i].clear();

        mu[i].clear();

    }

 

    return ans;

}
http://www.geeksforgeeks.org/check-people-can-vote-two-machines/
There are n people and two identical voting machines. We are also given an array a[] of size n such that a[i] 
stores time required by i-th person to go to any machine, mark his vote and come back. At one time instant, only one person 
can be there on
 each of the machines. Given a value x, defining the maximum allowable time for which machines are operational,
  check whether all persons can cast their vote or not.
Let sum be the total time taken by all n people. If sum <=x, then answer will obviously be YES. 
Otherwise, we need to check whether the given array can be split in two parts such that sum of first
 part and sum of second part are both less than or equal to x. The problem is similar to the knapsack problem. 
 Imagine two knapsacks each with capacity x. Now find, maximum people who can vote on any one machine i.e. 
 find maximum subset sum for knapsack of capacity x. Let this sum be s1. Now if (sum-s1) <= x, then answer is YES
  else answer is NO.

bool canVote(int a[], int n, int x)

{

    // dp[i][j] stores maximum possible number

    // of people among arr[0..i-1] can vote

    // in j time.

    int dp[n+1][x+1];

    memset(dp, 0, sizeof(dp));


    // Find sum of all times

    int sum = 0;

    for (int i=0; i<=n; i++ )

        sum += a[i];


    // Fill dp[][] in bottom up manner (Similar

    // to knapsack).

    for (int i=1; i<=n; i++)

        for (int j=1; j<=x; j++)

            if (a[i] <= j)

                dp[i][j] = max(dp[i-1][j],

                        a[i] + dp[i-1][j-a[i]]);

            else

                dp[i][j] = dp[i-1][j];


    // If remaining people can go to other machine.

    return (sum - dp[n][x] <= x);

}
http://www.geeksforgeeks.org/minimum-operations-required-to-remove-an-array/
Given an array of N integers where N is even. There are two kinds of operations allowed on the array.
Increase the value of any element A[i] by 1.
If two adjacent elements in the array are consecutive prime number, delete both the element. That is, A[i]
 is a prime number and A[i+1] is the next prime number.
The task is to find the minimum number of operation required to remove all the element of the array.
To remove numbers, we must transform two numbers to two consecutive primes. How to compute the minimum cost of
 transforming two numbers a, b to two consecutive primes, where the cost is the number of incrementation of both numbers?
We use sieve to precompute prime numbers and then find the first prime p not greater than a and the first greater than p using array.
Once we have computed two nearest prime numbers, we use Dynamic Programming to solve the problem. Let dp[i][j] be the minimum
 cost of clearing the subarray A[i, j]. If there are two numbers in the array, the answer 
 is easy to find. Now, for N > 2, try any element at position k > i as a pair for A[i], such that 
 there are even number of elements from A[i, j] between A[i] and A[k]. For a fixed k, the minimum cost 
 of clearing A[i, j], i.e dp[i][j], equals dp[i + 1][k – 1] + dp[k + 1][j] + (cost of transforming A[i] and A[k]
  into consecutive primes). We can compute the answer by iterating over all possible k.



 For a fixed k, the minimum cost 
 of clearing A[i, j], i.e dp[i][j], equals dp[i + 1][k – 1] + dp[k + 1][j] + (cost of transforming A[i] and A[k]
  into consecutive primes). We can compute the answer by iterating over all possible k.

int cost(int a, int b, int prev[], int nxt[])

{

    int sub = a + b;


    if (a <= b && prev[b-1] >= a)

        return nxt[b] + prev[b-1] - a - b;


    a = max(a, b);

    a = nxt[a];

    b = nxt[a + 1];


    return a + b - sub;

}


// Sieve to store next and previous prime

// to a number.

void sieve(int prev[], int nxt[])

{

    int pr[MAX] = { 0 };


    pr[1] = 1;

    for (int i = 2; i < MAX; i++)

    {

        if (pr[i])

            continue;


        for (int j = i*2; j < MAX; j += i)

            pr[j] = 1;

    }


    // Computing next prime each number.

    for (int i = MAX - 1; i; i--)

    {

        if (pr[i] == 0)

            nxt[i] = i;

        else

            nxt[i] = nxt[i+1];

    }


    // Computing previous prime each number.

    for (int i = 1; i < MAX; i++)

    {

        if (pr[i] == 0)

            prev[i] = i;

        else

            prev[i] = prev[i-1];

    }

}


// Return the minimum number of operation required.

int minOperation(int arr[], int nxt[], int prev[], int n)

{

    int dp[n + 5][n + 5] = { 0 };


    // For each index.

    for (int r = 0; r < n; r++)

    {

        // Each subarray.

        for (int l = r-1; l >= 0; l -= 2)

        {

            dp[l][r] = INT_MAX;


            for (int ad = l; ad < r; ad += 2)

                dp[l][r] = min(dp[l][r], dp[l][ad] +

                          dp[ad+1][r-1] +

                          cost(arr[ad], arr[r], prev, nxt));

        }

    }


    return dp[0][n - 1] + n/2;

}
 * @author het
 *
 */
public class Algorithm_DP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
