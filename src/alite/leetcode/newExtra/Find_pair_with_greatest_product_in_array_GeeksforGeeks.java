package alite.leetcode.newExtra;
/**
 * Find pair with greatest product in array - GeeksforGeeks

Find pair with greatest product in array - GeeksforGeeks
Given an array of n elements, the task is to find the greatest number such that it is product of two elements of given array.
 If no such element exists, print -1. Elements are within the range of 1 to 10^5.

Create an empty hash table and store all array elements in it.
Sort the array in ascending order.
Pick elements one by one from end of the array.
And check if there exists a pair whose product is equal to that number. In this efficiency can be achieved. 
The idea is to reach till sqrt of that number. If we don’t get the pair till sqrt that means no such pair exists. 
We use hash table to make sure that we can find other element of pair in O(1) time.
Repeat steps 2 to 3 until we get the element or whole array gets traversed.

int findGreatest(int arr[], int n)

{

    // Store occurrences of all elements in hash

    // array

    unordered_map<int, int> m;

    for (int i = 0 ; i < n; i++)

        m[arr[i]]++;

 

    // Sort the array and traverse all elements from

    // end.

    sort(arr, arr+n);

 

    for (int i=n-1; i>1; i--)

    {

        // For every element, check if there is another

        // element which divides it.

        for (int j=0; j<i && arr[j]<=sqrt(arr[i]); j++)

        {

            if (arr[i] % arr[j] == 0)

            {

                int result = arr[i]/arr[j];

 

                // Check if the result value exists in array

                // or not if yes the return arr[i]

                if (result != arr[j] && m[result] > 0)

                    return arr[i];

 

                // To handle the case like arr[i] = 4 and

                // arr[j] = 2

                else if (result == arr[j] && m[result] > 1)

                    return arr[i];

            }

        }

    }

    return -1;

}
Read full article from Find pair with greatest product in array - GeeksforGeeks

You might also like
Find a partition point in array - GeeksforGeeks
Smallest number with at least n digits in factorial - GeeksforGeeks
Count pairs of natural numbers with GCD equal to given number - GeeksforGeeks
Find pairs in array whose sums already exist in array - GeeksforGeeks
Divide array into two sub-arrays such that their averages are equal - GeeksforGeeks
Count digits in a factorial - GeeksforGeeks
Count of acute, obtuse and right triangles with given sides - GeeksforGeeks
Find all elements in array which have at-least two greater elements - GeeksforGeeks
Longest prefix that contains same number of X and Y in an array - GeeksforGeeks
Double factorial - GeeksforGeeks
 * @author het
 *
 */
public class Find_pair_with_greatest_product_in_array_GeeksforGeeks {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
