package alite.leetcode.newExtra;
/**
 * Longest prefix that contains same number of X and Y in an array - GeeksforGeeks

Longest prefix that contains same number of X and Y in an array - GeeksforGeeks
Given two positive integers X and Y, and an array arr[] of positive integers. We need to find 
the longest prefix index which contains an equal number of X and Y. Print the maximum index of largest prefix 
if exist otherwise print -1.

We start from the index 0 and run a loop till the end of array. We keep increasing counters for both numbers X and Y.
 After iterating over the whole array, the last index when counts of X and y were equal is our result.

int findIndex(int arr[], int X, int Y, int n)

{

    // counters for X and Y

    int nx = 0,ny = 0;

 

    int result = -1;

    for (int i=0; i<n; i++)

    {

        // If value is equal to X increment counter of X

        if (arr[i]==X)

            nx++;

 

        // If value is equal to X increment counter of X

        if (arr[i]==Y)

            ny++;

 

        // If counters are equal(but not zero) save

        // the result as i

        if ((nx==ny) && (nx!=0 && ny!=0))

            result = i;

    }

    return result;

}
Read full article from Longest prefix that contains same number of X and Y in an array - GeeksforGeeks
 * @author het
 *
 */

/**
 * Find a partition point in array - GeeksforGeeks

Find a partition point in array - GeeksforGeeks
Given an unsorted array of integers. Find an element such that all the elements to its left are smaller and to its right are greater. Print -1 if no such element exists. 
Note that there can be more than one such elements. For example an array which is sorted in increasing order all elements
 follow the property. We need to find only one such element.

Efficient solution take O(n) time.
Create an auxiliary array ‘GE[]’. GE[] should store the element which is greater than A[i] and is on left side of A[i].
Create an another Auxliary array ‘SE[]’. SE[i] should store the element which is smaller than A[i] and is on right side of A[i].
Find element in array that hold condition GE[i-1] < A[i] < SE[i+1].

int FindElement(int A[], int n)

{

    // Create an array 'SE[]' that will store smaller

    // element on right side.

    int SE[n];

 

    // Create an another array 'GE[]' that will store

    // greatest element on left side.

    int GE[n];

 

    // initalize first and last index of SE[] , GE[]

    GE[0] = A[0];

    SE[n-1] = A[n-1];

 

    // store greatest element from left to right

    for (int i=1; i<n ; i++)

    {

        if (GE[i-1] < A[i])

            GE[i] = A[i];

        else

            GE[i] = GE[i-1];

    }

 

    // store smallest element from right to left

    for (int i = n-2 ; i >= 0 ; i-- )

    {

        if (A[i] < SE[i+1])

            SE[i] = A[i];

        else

            SE[i] = SE[i+1];

    }

 

    // Now find a number which is greater then all

    // elements at it's left and smaller the all

    // then elements to it's right

    for (int j =0 ; j <n ; j++)

    {

        if ( ( j == 0  && A[j] < SE[j+1] ) ||

            ( j == n-1 && A[j] > GE[j-1] ) ||

            ( A[j] < SE[j+1] && A[j] > GE[j-1] ) )

            return A[j];

    }

 

    return -1;

}

Simple solution takes O(n2). Idea is to pick each array element one by one and for each element we have to check it is greater than all the elements to its left side and smaller than all the elements to its right side.

int FindElement( int A[] , int n )

{

     // traverse array elements

    for (int i=0; i < n ; i++)

    {

        // If we found that such number

        int flag = 0 ;

 

        // check All the elements on its left are smaller

        for (int j = 0 ; j < i ; j++ )

            if (A[j] >= A[i] )

            {

                flag = 1 ;

                break;

            }

 

        // check All the elements on its right are Greater

        for( int j = i + 1 ; j < n; j++ )

            if( A[j] <= A[i] )

            {

                flag = 1 ;

                break;

            }

 

        // If flag == 0 indicates we found that number

        if (flag == 0)

            return A[i];

    }

    return -1;

}
Read full article from Find a partition point in array - GeeksforGeeks
 * @author het
 *
 */
public class Longest_prefix_that_contains_same_number_of_X_and_Y {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
