package alite.leetcode.newExtra;
/**
 * Partition a number into two divisble parts - GeeksforGeeks

Partition a number into two divisble parts - GeeksforGeeks
Given a number (as string) and two integers a and b, divide the string in two non-empty parts such that the first part is divisible
 by a and second part is divisible by b. If string can not be divided into two non-empty parts, output "NO", else print "YES" with 
 the two parts.


A simple solution is to one by one partition array around all points. For every partition, check if left 
and right of it are divisible by a and b respectively. If yes, print the left and right parts and return.
An efficient solution is to do some preprocessing and save the division modulo by ‘a’ by scanning the string
 from left to right and division modulo by ‘b’ from right to left.
If we know the remainder of prefix from 0 to i, when divided by a, then we compute remainder of prefix 
from 0 to i+1 using below formula.
lr[i+1] = (lr[i]*10 + str[i] -‘0’)%a.

lr[i+1] = (lr[i]*10 + str[i] -‘0’)%a.
Same way, modulo by b can be found by scanning from right to left. We create another rl[] to store remainders 
with b from right to left.


Once we have precomputed two remainders, we can easily find the point that partition string in two parts.


// Finds if it is possible to partition str

// into two parts such that first part is

// divisible by a and second part is divisible

// by b.

void findDivision(string &str, int a, int b)

{

    int len = str.length();
    // Create an array of size len+1 and initialize

    // it with 0.

    // Store remainders from left to right when

    // divided by 'a'

    vector<int> lr(len+1, 0);

    lr[0] = (str[0] - '0')%a;

    for (int i=1; i<len; i++)

        lr[i] = ((lr[i-1]*10)%a + (str[i]-'0'))%a;

 

    // Compute remainders from right to left when

    // divided by 'b'

    vector<int> rl(len+1, 0);

    rl[len-1] = (str[len-1] - '0')%b;

    int power10 = 10;

    for (int i= len-2; i>=0; i--)

    {

        rl[i] = (rl[i+1] + (str[i]-'0')*power10)%b;

        power10 = (power10 * 10) % b;

    }

 

    // Find a point that can partition a number

    for (int i=0; i<len-1; i++)

    {

        // If split is not possible at this point

        if (lr[i] != 0)

            continue;

 

        // We can split at i if one of the following

        // two is true.

        // a) All charactes after str[i] are 0

        // b) String after str[i] is divisible by b, i.e.,

        //    str[i+1..n-1] is divisible by b.

        if (rl[i+1] == 0)

        {

            cout << "YES\n";

            for (int k=0; k<=i; k++)

                cout << str[k];

 

            cout << ", ";

 

            for (int k=i+1; k<len; k++)

                cout << str[k];

            return;

        }

    }

 

    cout << "NO\n";

}
Read full article from Partition a number into two divisble parts - GeeksforGeeks
 * @author het
 *
 */
public class Partition_a_number_into_two_divisble_parts {

}
