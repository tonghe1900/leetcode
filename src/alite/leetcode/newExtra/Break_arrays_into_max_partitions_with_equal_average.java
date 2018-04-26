package alite.leetcode.newExtra;
/**
 * Break an array into maximum number of sub-arrays such that their averages are same - GeeksforGeeks

Break an array into maximum number of sub-arrays such that their averages are same - GeeksforGeeks
Given an integer array, the task is to divide the array into maximum number of sub-arrays such that averages of all subarrays is same. If it is not possible to divide, then print "Not possible".

The idea is based on the fact that if an array can be divided in subarrays of same average, then anerage of all these subarrays must be same as overall average.
1) Find average of whole array.
2) Traverse array again and keep track of average of current subarray. As soon as the average
 becomes same as overall overall average, print current subarray and begin new subarray.


This solution divides to maximum number of subarrays because we begin a new subarray as soon as we find average same as overall average.


// C++ prpgram to break given array into maximum

// number of subarrays with equal average.

#include<bits/stdc++.h>

using namespace std;

 

void findSubarrays(int arr[], int n)

{

    // To store all points where we can break

    // given array into equal average subarrays.

    vector<int> result;

 

    // Compute total array sum

    int sum = 0;

    for (int i = 0; i < n; i++)

        sum += arr[i];

 

    int curr_sum = 0;     // Current Sum

    int prev_index = -1;  // Index of previous subarray

 

    for (int i = 0; i < n ; i++)

    {

        curr_sum += arr[i];

 

        // If current point is a break point. Note that

        // we don't compare actual averages to avoid

        // floating point errors.

        if (sum *(i - prev_index) == curr_sum*n)

        {

            // Update current sum and previous index

            curr_sum = 0;

            prev_index = i;

 

            // Add current break point

            result.push_back(i);

        }

    }

 

    // If last break point was not end of array, we

    // cannot break the whole array.

    if (prev_index != n-1)

    {

        cout << "Not Possible";

        return;

    }

 

    // Printing the result in required format

    cout << "(0, " << result[0] << ")\n";

    for (int i=1; i<result.size(); i++)

        cout << "(" << result[i-1] + 1 << ", "

             << result[i] << ")\n";

}
Read full article from Break an array into maximum number of sub-arrays such that their averages are same - GeeksforGeeks
 * @author het
 *
 */
public class Break_arrays_into_max_partitions_with_equal_average {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
