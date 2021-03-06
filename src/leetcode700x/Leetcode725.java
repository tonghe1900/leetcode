package leetcode700x;
/**
 * 725. Split Linked List in Parts
DescriptionHintsSubmissionsDiscussSolution
Given a (singly) linked list with head node root, write a function to split the linked list into k consecutive linked list "parts".

The length of each part should be as equal as possible: no two parts should have a size differing by more than 1. This may lead to some parts being null.

The parts should be in order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal parts occurring later.

Return a List of ListNode's representing the linked list parts that are formed.

Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]
Example 1:
Input: 
root = [1, 2, 3], k = 5
Output: [[1],[2],[3],[],[]]
Explanation:
The input and each element of the output are ListNodes, not arrays.
For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3, and root.next.next.next = null.
The first element output[0] has output[0].val = 1, output[0].next = null.
The last element output[4] is null, but it's string representation as a ListNode is [].
Example 2:
Input: 
root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
Explanation:
The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.
Note:

The length of root will be in the range [0, 1000].
Each value of a node in the input will be an integer in the range [0, 999].
k will be an integer in the range [1, 50].
Seen this question in a real interview before?



Approach #1: Create New Lists [Accepted]
Intuition and Algorithm

If there are NN nodes in the linked list root, then there are N / kN/k items in each part, plus the first N \% kN%k parts have an extra item. We can count NN with a simple loop.

Now for each part, we have calculated how many nodes that part will have: width + (i < remainder ? 1 : 0). We create a new list and write the part to that list.

Our solution showcases constructs of the form a = b = c. Note that this syntax behaves differently for different languages.


Complexity Analysis

Time Complexity: O(N + k)O(N+k), where NN is the number of nodes in the given list. If kk is large, it could still require creating many new empty lists.

Space Complexity: O(max(N, k))O(max(N,k)), the space used in writing the answer.

Approach #2: Split Input List [Accepted]
Intuition and Algorithm

As in Approach #1, we know the size of each part. Instead of creating new lists, we will split the input list directly and return a list of pointers to nodes in the original list as appropriate.

Our solution proceeds similarly. For a part of size L = width + (i < remainder ? 1 : 0), instead of stepping L times, we will step L-1 times, and our final time will also sever the link between the last node from the previous part and the first node from the next part.


Complexity Analysis

Time Complexity: O(N + k)O(N+k), where NN is the number of nodes in the given list. If kk is large, it could still require creating many new empty lists.

Space Complexity: O(k)O(k), the additional space used in writing the answer.


 * @author tonghe
 *
 */
public class Leetcode725 {
	class Solution {
	    public ListNode[] splitListToParts(ListNode root, int k) {
	        ListNode cur = root;
	        int N = 0;
	        while (cur != null) {
	            cur = cur.next;
	            N++;
	        }

	        int width = N / k, rem = N % k;

	        ListNode[] ans = new ListNode[k];
	        cur = root;
	        for (int i = 0; i < k; ++i) {
	            ListNode head = new ListNode(0), write = head;
	            for (int j = 0; j < width + (i < rem ? 1 : 0); ++j) {
	                write = write.next = new ListNode(cur.val);
	                if (cur != null) cur = cur.next;
	            }
	            ans[i] = head.next;
	        }
	        return ans;
	    }
	}
	
	
	
	
	
	class Solution {
	    public ListNode[] splitListToParts(ListNode root, int k) {
	        ListNode cur = root;
	        int N = 0;
	        while (cur != null) {
	            cur = cur.next;
	            N++;
	        }

	        int width = N / k, rem = N % k;

	        ListNode[] ans = new ListNode[k];
	        cur = root;
	        for (int i = 0; i < k; ++i) {
	            ListNode head = cur;
	            for (int j = 0; j < width + (i < rem ? 1 : 0) - 1; ++j) {
	                if (cur != null) cur = cur.next;
	            }
	            if (cur != null) {
	                ListNode prev = cur;
	                cur = cur.next;
	                prev.next = null;
	            }
	            ans[i] = head;
	        }
	        return ans;
	    }
	}
}
