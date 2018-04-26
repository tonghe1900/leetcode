package alite.leetcode.xx3.extra;
/**
 * Leetcode 370 - Range Addition

http://www.cnblogs.com/grandyang/p/5628786.html
Assume you have an array of length n initialized with all 0's and are given k update operations.
Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ... endIndex] (startIndex and endIndex inclusive) with inc.
Return the modified array after all k operations were executed.
Example:
Given:

    length = 5,
    updates = [
        [1,  3,  2],
        [2,  4,  3],
        [0,  2, -2]
    ]

Output:

    [-2, 0, 3, 5, 3]
Explanation:
Initial state:
[ 0, 0, 0, 0, 0 ]

After applying operation [1, 3, 2]:
[ 0, 2, 2, 2, 0 ]

After applying operation [2, 4, 3]:
[ 0, 2, 5, 5, 3 ]

After applying operation [0, 2, -2]:
[-2, 0, 3, 5, 3 ]
Hint:
Thinking of using advanced data structures? You are thinking it too complicated.
For each update operation, do you really need to update all elements between i and j?
Update only the first and end element is sufficient.
The optimal time complexity is O(k + n) and uses O(1) extra space.

https://discuss.leetcode.com/topic/49691/java-o-k-n-time-complexity-solution
http://shirleyisnotageek.blogspot.com/2016/10/range-addition.html
The idea is to utilize the fact that the array initializes with zero. The hint 
suggests us that we only needs to modify the first and last element of the range. 
In fact, we need to increment the first element in the range and decreases the last element + 1 
(if it's within the length) by inc. Then we sum up all previous results. 
Why does this work? When we sum up the array, the increment is passed along to the subsequent 
elements until the last element. When we decrement the end + 1 index, we offset the increment 
so no increment is passed along to the next element.



public int[] getModifiedArray(int length, int[][] updates) {

        int[] nums = new int[length];

        if (length == 0) {

            return nums;

        }

        for (int[] u : updates) {

            nums[u[0]] += u[2];

            if (u[1] + 1 < length) {

                nums[u[1] + 1] -= u[2];

            }

        }

        for (int i = 1; i < length; i++) {

            nums[i] += nums[i - 1];

        }

        return nums;

    }
https://discuss.leetcode.com/topic/53142/detailed-explanation-if-you-don-t-understand-especially-put-negative-inc-at-endindex-1
Now you might see why we do "puts inc at startIndex and -inc at endIndex + 1":
Put inc at startIndex allows the inc to be carried to the next index starting from startIndex when we do the sum accumulation.
Put -inc at endIndex + 1 simply means cancel out the previous carry from the next index of the endIndex, because the previous carry should not be counted beyond endIndex.
And finally, because each of the update operation is independent and the list operation is just an accumulation of the "marks" we do, so it can be "makred" all at once first and do the range sum at one time at last step.


https://all4win78.wordpress.com/2016/06/29/leetcode-370-range-addition/
https://segmentfault.com/a/1190000005948569
思想是把所有需要相加的值存在第一个数，然后把这个范围的最后一位的下一位减去这个inc, 这样我所以这个范围在求最终值的时候，都可以加上这个inc，而后面的数就不会加上inc。


    public int[] getModifiedArray(int length, int[][] updates) {

        int[] result = new int[length];

        for (int[] operation : updates) {

            result[operation[0]] += operation[2];

            if (operation[1] < length - 1) {

                result[operation[1] + 1] -= operation[2];

            }

        }

        int temp = 0;

        for (int i = 0; i < length; i++) {

            temp += result[i];

            result[i] = temp;

        }

        return result;

    }
https://discuss.leetcode.com/topic/49674/java-o-n-k-time-o-1-space-with-algorithm-explained/
segment [i,j] is made of two parts [0,i-1] and [0, j]
so [i,j] increase 2 is same as [0,j] increase 2 and [0,i-1] increase -2. so you only need to update value at nums[j] with inc and nums[i-1] -inc. initially nums[i] is defined as all elements [0,i] increases inc
then think from length-1 to 0 backward. The last spot nums[length-1] does not need any modification.
nums[length-2] value should be updated as nums[length-2] + nums[length-1] as the latter covers the front. but front does not influence what is after it. so every spot should be updated as + the accumulate sum from the end.
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] nums = new int[length];
        for (int[] update : updates) {
            nums[update[1]] += update[2];
            if (update[0] > 0) {
                nums[update[0] - 1] -= update[2];
            } 
        }
        
        int sum = nums[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            int tmp = sum + nums[i];
            nums[i] += sum;
            sum = tmp; 
        }
        return nums;
    }
http://blog.csdn.net/jmspan/article/details/51787011
方法一：类似城市天际线问题，使用最小堆来维护当前的范围。
    public int[] getModifiedArray(int length, int[][] updates) {
        Arrays.sort(updates, new Comparator<int[]>() {
            @Override
            public int compare(int[] seg1, int[] seg2) {
                return Integer.compare(seg1[0], seg2[0]);
            }
        });
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return Integer.compare(updates[i1][1], updates[i2][1]);
            }
        });
        int[] results = new int[length];
        int j = 0;
        int sum = 0;
        for(int i = 0; i < length; i++) {
            while (!heap.isEmpty() && updates[heap.peek()][1] < i) {
                int p = heap.poll();
                sum -= updates[p][2];
            }
            while (j < updates.length && i >= updates[j][0]) {
                sum += updates[j][2];
                heap.offer(j);
                j++;
            }
            results[i] = sum;
        }
        return results;
    }
X. Brute force:
https://discuss.leetcode.com/topic/49669/java-straightforward-solution
public int[] getModifiedArray(int length, int[][] updates) {
    int[] nums = new int[length];
    int k = updates.length;
 for(int i = 0; i < k; i++){
  int start = updates[i][0];
  int end = updates[i][1];
  int inc = updates[i][2];
  for(int j = start; j <= end; j++){
   nums[j] += inc;
  }
 }
 return nums;
}
 * @author het
 *
 */
public class LeetCode370 {
	public int[] getModifiedArray(int length, int[][] updates) {

        int[] nums = new int[length];

        if (length == 0) {

            return nums;

        }

        for (int[] u : updates) {

            nums[u[0]] += u[2];

            if (u[1] + 1 < length) {

                nums[u[1] + 1] -= u[2];//

            }

        }

        for (int i = 1; i < length; i++) {

            nums[i] += nums[i - 1];

        }

        return nums;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
