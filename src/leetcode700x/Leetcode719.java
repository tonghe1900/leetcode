package leetcode700x;
/**
 * 719. Find K-th Smallest Pair Distance
DescriptionHintsSubmissionsDiscussSolution
Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.

Example 1:
Input:
nums = [1,3,1]
k = 1
Output: 0 
Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
Note:
2 <= len(nums) <= 10000.
0 <= nums[i] < 1000000.
1 <= k <= len(nums) * (len(nums) - 1) / 2.
Seen this question in a real interview before?




Approach #1: Heap [Time Limit Exceeded]
Intuition and Algorithm

Sort the points. For every point with index i, the pairs with indexes (i, j) [by order of distance] are (i, i+1), (i, i+2), ..., (i, N-1).

Let's keep a heap of pairs, initially heap = [(i, i+1) for all i], and ordered by distance (the distance of (i, j) is nums[j] - nums[i].) Whenever we use a pair (i, x) from our heap, we will add (i, x+1) to our heap when appropriate.


Complexity Analysis

Time Complexity: O((k+N) \log{N})O((k+N)logN), where NN is the length of nums. As k = O(N^2)k=O(N
​2
​​ ), this is O(N^2 \log {N})O(N
​2
​​ logN) in the worst case. The complexity added by our heap operations is either O((k+N) \log N)O((k+N)logN) in the Java solution, or O(k \log{N} + N)O(klogN+N) in the Python solution because the heapq.heapify operation is linear time. Additionally, we add O(N \log N)O(NlogN) complexity due to sorting.

Space Complexity: O(N)O(N), the space used to store our heap of at most N-1 elements.

Approach #2: Binary Search + Prefix Sum [Accepted]
Intuition

Let's binary search for the answer. It's definitely in the range [0, W], where W = max(nums) - min(nums)].

Let possible(guess) be true if and only if there are k or more pairs with distance less than or equal to guess. We will focus on evaluating our possible function quickly.

Algorithm

Let prefix[v] be the number of points in nums less than or equal to v. Also, let multiplicity[j] be the number of points i with i < j and nums[i] == nums[j]. We can record both of these with a simple linear scan.

Now, for every point i, the number of points j with i < j and nums[j] - nums[i] <= guess is prefix[x+guess] - prefix[x] + (count[i] - multiplicity[i]), where count[i] is the number of ocurrences of nums[i] in nums. The sum of this over all i is the number of pairs with distance <= guess.

Finally, because the sum of count[i] - multiplicity[i] is the same as the sum of multiplicity[i], we could just replace that term with multiplicity[i] without affecting the answer. (Actually, the sum of multiplicities in total will be a constant used in the answer, so we could precalculate it if we wanted.)

In our Java solution, we computed possible = count >= k directly in the binary search instead of using a helper function.


Complexity Analysis

Time Complexity: O(W + N \log{W} + N \log{N})O(W+NlogW+NlogN), where NN is the length of nums, and WW is equal to nums[nums.length - 1] - nums[0]. We do O(W)O(W) work to calculate prefix initially. The \log WlogW factor comes from our binary search, and we do O(N)O(N) work inside our call to possible (or to calculate count in Java). The final O(N\log N)O(NlogN) factor comes from sorting.

Space Complexity: O(N+W)O(N+W), the space used to store multiplicity and prefix.

Approach #3: Binary Search + Sliding Window [Accepted]
Intuition

As in Approach #2, let's binary search for the answer, and we will focus on evaluating our possible function quickly.

Algorithm

We will use a sliding window approach to count the number of pairs with distance <= guess.

For every possible right, we maintain the loop invariant: left is the smallest value such that nums[right] - nums[left] <= guess. Then, the number of pairs with right as it's right-most endpoint is right - left, and we add all of these up.


Complexity Analysis

Time Complexity: O(N \log{W} + N \log{N})O(NlogW+NlogN), where NN is the length of nums, and WW is equal to nums[nums.length - 1] - nums[0]. The \log WlogW factor comes from our binary search, and we do O(N)O(N) work inside our call to possible (or to calculate count in Java). The final O(N\log N)O(NlogN) factor comes from sorting.

Space Complexity: O(1)O(1). No additional space is used except for integer variables.

Analysis written by: @awice.
 * @author tonghe
 *
 */
public class Leetcode719 {

	class Solution {
	    public int smallestDistancePair(int[] nums, int k) {
	        Arrays.sort(nums);
	        PriorityQueue<Node> heap = new PriorityQueue<Node>(nums.length,
	            Comparator.<Node> comparingInt(node -> nums[node.nei] - nums[node.root]));
	        for (int i = 0; i + 1 < nums.length; ++i) {
	            heap.offer(new Node(i, i+1));
	        }

	        Node node = null;
	        for (; k > 0; --k) {
	            node = heap.poll();
	            if (node.nei + 1 < nums.length) {
	                heap.offer(new Node(node.root, node.nei + 1));
	            }
	        }
	        return nums[node.nei] - nums[node.root];
	    }
	}
	class Node {
	    int root;
	    int nei;
	    Node(int r, int n) {
	        root = r;
	        nei = n;
	    }
	}
	
	class Solution {
	    public int smallestDistancePair(int[] nums, int k) {
	        Arrays.sort(nums);
	        int WIDTH = 2 * nums[nums.length - 1];

	        //multiplicity[i] = number of nums[j] == nums[i] (j < i)
	        int[] multiplicity = new int[nums.length];
	        for (int i = 1; i < nums.length; ++i) {
	            if (nums[i] == nums[i-1]) {
	                multiplicity[i] = 1 + multiplicity[i - 1];
	            }
	        }

	        //prefix[v] = number of values <= v
	        int[] prefix = new int[WIDTH];
	        int left = 0;
	        for (int i = 0; i < WIDTH; ++i) {
	            while (left < nums.length && nums[left] == i) left++;
	            prefix[i] = left;
	        }

	        int lo = 0;
	        int hi = nums[nums.length - 1] - nums[0];
	        while (lo < hi) {
	            int mi = (lo + hi) / 2;
	            int count = 0;
	            for (int i = 0; i < nums.length; ++i) {
	                count += prefix[nums[i] + mi] - prefix[nums[i]] + multiplicity[i];
	            }
	            //count = number of pairs with distance <= mi
	            if (count >= k) hi = mi;
	            else lo = mi + 1;
	        }
	        return lo;
	    }
	}
	
	
	class Solution {
	    public int smallestDistancePair(int[] nums, int k) {
	        Arrays.sort(nums);

	        int lo = 0;
	        int hi = nums[nums.length - 1] - nums[0];
	        while (lo < hi) {
	            int mi = (lo + hi) / 2;
	            int count = 0, left = 0;
	            for (int right = 0; right < nums.length; ++right) {
	                while (nums[right] - nums[left] > mi) left++;
	                count += right - left;
	            }
	            //count = number of pairs with distance <= mi
	            if (count >= k) hi = mi;
	            else lo = mi + 1;
	        }
	        return lo;
	    }
	}
}
