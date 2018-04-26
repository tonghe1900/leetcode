package alite.leetcode.newest;
/**
 * LeetCode 658 - Find K Closest Elements


https://leetcode.com/problems/find-k-closest-elements
Given a sorted array, two integers k and x, find the k closest elements to x in the array. The result should also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.
Example 1:
Input: [1,2,3,4,5], k=4, x=3
Output: [1,2,3,4]

Example 2:
Input: [1,2,3,4,5], k=4, x=-1
Output: [1,2,3,4]

Note:
1. The value k is positive and will always be smaller than the length of the sorted array.
2. Length of the given array is positive and will not exceed 104
3. Absolute value of elements in the array and x will not exceed 104
https://discuss.leetcode.com/topic/99194/binary-search-and-two-pointers-18-ms
Noticing the array is sorted, so we can using binary search to get a rough area of target numbers, and then expand it to the left k-1 more and right k-1 more elements, then searching from the left to right. If the left element is more close or equal to the target number x than the right element, then move the right index to the left one step. Otherwise, move the left index to right one step. Once, the element between the left and right is k, then return the result.
 public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
  int n = arr.size();
  if (x <= arr.get(0)) {
   return arr.subList(0, k);
  } else if (arr.get(n - 1) <= x) {
   return arr.subList(n - k, n);
  } else {
   int index = Collections.binarySearch(arr, x);
   if (index < 0)
    index = -index - 1;
   int low = Math.max(0, index - k - 1), high = Math.min(arr.size() - 1, index + k - 1);

   while (high - low > k - 1) {
    if (low < 0 || (x - arr.get(low)) <= (arr.get(high) - x))
     high--;
    else if (high > arr.size() - 1 || (x - arr.get(low)) > (arr.get(high) - x))
     low++;
    else
     System.out.println("unhandled case: " + low + " " + high);
   }
   return arr.subList(low, high + 1);
  }
 }

https://discuss.leetcode.com/topic/99178/java-o-logn-klogk-solution-with-priorityqueue-and-binary-search

O(nlog(n)) Time Solution:
public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
     Collections.sort(arr, (a,b) -> a == b ? a - b : Math.abs(a-x) - Math.abs(b-x));
     arr = arr.subList(0, k);
     Collections.sort(arr);
     return arr;
}

 * @author het
 *
 */
public class LeetCode658 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
