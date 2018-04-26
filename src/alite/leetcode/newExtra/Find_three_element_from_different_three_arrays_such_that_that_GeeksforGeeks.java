package alite.leetcode.newExtra;
/**
 * Find three element from different three arrays such that that a + b + c = sum - GeeksforGeeks

Find three element from different three arrays such that that a + b + c = sum - GeeksforGeeks
Given three integer arrays and a "sum", the task is to check if there are three elements a, b, c such that a + b + c = sum and a, b and c belong to three different arrays.


An efficient solution is to store all elements of first array in hash table (unordered_set in C++) and calculate sum of two elements last two array elements
 one by one and substract from given number k and check in hash table if it’s exist in hash table then print exist and otherwise not exist.
1. Store all elements of first array in hash table
2. Generate all pairs of elements from two arrays using
   nested loop. For every pair (a1[i], a2[j]), check if
   sum - (a1[i] + a2[j]) exists in hash table. If yes
   return true.      

bool findTriplet(int a1[], int a2[], int a3[],

              int n1, int n2, int n3, int sum)

{

    // Store elements of first array in hash

    unordered_set <int> s;

    for (int i=0; i<n1; i++)

        s.insert(a1[i]);


    // sum last two arrays element one by one

    for (int i=0; i<n2; i++)

    {

        for (int j=0; j<n3; j++)

        {

            // Consider current pair and find if there

            // is an element in a1[] such that these

            // three form a required triplet

            if (s.find(sun - a2[i] - a3[j]) != s.end())

                 return true;

        }

    }


    return false;

}

Read full article from Find three element from different three arrays such that that a + b + c = sum - GeeksforGeeks
 * @author het
 *
 */
public class Find_three_element_from_different_three_arrays_such_that_that_GeeksforGeeks {

}
