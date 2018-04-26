package alite.leetcode.xx2.sucess.extra;
///**
// * Maximum sum subarray removing at most one element - GeeksforGeeks
//
//Maximum sum subarray removing at most one element - GeeksforGeeks
//Given an array, we need to find maximum sum subarray, removing one element is also allowed to get the maximum sum.
//
//
//
//If element removal condition is not applied, we can solve this problem using Kadane’s algorithm but here one element
//can be removed also for increasing maximum sum. This condition can be handled using two arrays, forward and backward array, 
//these arrays store the current maximum subarray sum from starting to ith index, and from ith index to ending respectively.
//In below code, two loops are written, first one stores maximum current sum in forward direction in fw[] and other loop stores 
//the same in backward direction in bw[]. Getting current maximum and updation is same as Kadane’s algorithm.
//Now when both arrays are created, we can use them for one element removal conditions as follows,
//at each index i, maximum subarray sum after ignoring i’th element will be fw[i-1] + bw[i+1] so we loop for all possible 
//i values and we choose maximum among them.
//Total time complexity and space complexity of solution is O(N)
//
//
//int maxSumSubarrayRemovingOneEle(int arr[], int n)
//
//{
//
//    // Maximum sum subarrays in forward and backward
//
//    // directions
//
//    int fw[n], bw[n];
//
// 
//
//    // Initialize current max and max so far.
//
//    int cur_max = arr[0], max_so_far = arr[0];
//
// 
//
//    // calculating maximum sum subarrays in forward
//
//    // direction
//
//    fw[0] = arr[0];
//
//    for (int i = 1; i < n; i++)
//
//    {
//
//        cur_max = max(arr[i], cur_max + arr[i]);
//
//        max_so_far = max(max_so_far, cur_max);
//
// 
//
//        // storing current maximum till ith, in
//
//        // forward array
//
//        fw[i] = cur_max;
//
//    }
//
// 
//
//    // calculating maximum sum subarrays in backward
//
//    // direction
//
//    cur_max  = bw[n-1] = arr[n-1];
//     max_so_far = Math.max(max_so_far, cur_max);
//
//    for (int i = n-2; i >= 0; i--)
//
//    {
//
//        cur_max = max(arr[i], cur_max + arr[i]);
//
//        max_so_far = max(max_so_far, cur_max);
//
// 
//
//        // storing current maximum from ith, in
//
//        // backward array
//
//        bw[i] = cur_max;
//
//    }
//
// 
//
//    /*  Initializing final ans by max_so_far so that,
//
//        case when no element is removed to get max sum
//
//        subarray is also handled  */
//
//    int fans = max_so_far;
//
// 
//
//    //  choosing maximum ignoring ith element
//
//    for (int i = 1; i < n - 1; i++)
//
//        fans = max(fans, fw[i - 1] + bw[i + 1]);
//
// 
//
//    return fans;
//
//}
//Read full article from Maximum sum subarray removing at most one element - GeeksforGeeks
// * @author het
// *
// */
public class Extra {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
