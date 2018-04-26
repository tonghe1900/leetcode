package alite.leetcode.newExtra.finished;
/**
 * Maximize array sum after K negations

http://www.geeksforgeeks.org/maximize-array-sun-after-k-negation-operations/
Given an array of size n and a number k. We must modify array K number of times. Here 
modify array means in each operation we can replace any array element arr[i] by -arr[i]. We need to perform 
this operation in such a way that after K operations, sum of array must be maximum?
we just have to replace the minimum element arr[i] in array by -arr[i] for current operation. 
In this way we can make sum of array maximum after K operations. Once interesting case is, once minimum
 element becomes 0, we don’t need to make any more changes.
Time Complexity : O(k*n)

// This function does k operations on array

// in a way that maximize the array sum.

// index --> stores the index of current minimum

//           element for j'th operation

int maximumSum(int arr[], int n, int k)

{

    // Modify array K number of times

    for (int i=1; i<=k; i++)

    {

        int min = INT_MAX;

        int index = -1;

 

        // Find minimum element in array for

        // current operation and modify it

        // i.e; arr[j] --> -arr[j]

        for (int j=0; j<n; j++)

        {

            if (arr[j] < min)

            {

                min = arr[j];

                index = j;

            }

        }

 

        // this the condition if we find 0 as

        // minimum element, so it will useless to

        // replace 0 by -(0) for remaining operations

        if (min == 0)

            break;

 

        // Modify element of array

        arr[index] = -arr[index];

    }

 

    // Calculate sum of array

    int sum = 0;

    for (int i=0; i<n; i++)

        sum += arr[i];

    return sum;

}

http://www.geeksforgeeks.org/maximize-array-sum-k-negations-set-2/
In this post an optimized solution is implemented that uses a priority queue (or binary heap) to find 
minimum element quickly.
Note that this optimized solution can be implemented in O(n + kLogn) time as we can create a priority queue
 (or binary heap) in O(n) time.


    public static int maxSum(int[] a, int k)

    {

        // Create a priority queue and insert all array elements

        // int

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int x : a)

            pq.add(x);

 

        // Do k negations by removing a minimum element k times

        while (k-- > 0)

        {

            // Retrieve and remove min element

            int temp = pq.poll();

 

            // Modify the minimum element and add back

            // to priority queue

            temp *= -1;

            pq.add(temp);

        }

 

        // Compute sum of all elements in priority queue.

        int sum = 0;

        for (int x : pq)

            sum += x;

        return sum;

    }
 * @author het
 *
 */
public class Maximize_array_sum_after_K_negations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
