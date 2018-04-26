package Leetcode600x;
/**
 * Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

Example 1:
Input: m = 3, n = 3, k = 5
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
3	6	9

The 5-th smallest number is 3 (1, 2, 2, 3, 3).
Example 2:
Input: m = 2, n = 3, k = 6
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6

The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
Note:
The m and n will be in the range [1, 30000].
The k will be in the range [1, m * n]
Seen this question in a real interview before? 
 * @author tonghe
 *
 */
public class Leetcode668 {
//https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/solution/
	class Solution {
	    public int findKthNumber(int m, int n, int k) {
	        int[] table = new int[m*n];
	        for (int i = 1; i <= m; i++) {
	            for (int j = 1; j <= n; j++) {
	                table[(i - 1) * n + j - 1] = i * j;
	            }
	        }
	        Arrays.sort(table);
	        return table[k-1];
	    }
	}
	
	
	
	
	class Solution {
	    public int findKthNumber(int m, int n, int k) {
	        PriorityQueue<Node> heap = new PriorityQueue<Node>(m,
	            Comparator.<Node> comparingInt(node -> node.val));

	        for (int i = 1; i <= m; i++) {
	            heap.offer(new Node(i, i));
	        }

	        Node node = null;
	        for (int i = 0; i < k; i++) {
	            node = heap.poll();
	            int nxt = node.val + node.root;
	            if (nxt <= node.root * n) {
	                heap.offer(new Node(nxt, node.root));
	            }
	        }
	        return node.val;
	    }
	}

	class Node {
	    int val;
	    int root;
	    public Node(int v, int r) {
	        val = v;
	        root = r;
	    }
	}
	
	
	
	
	
	class Solution {
	    public boolean enough(int x, int m, int n, int k) {
	        int count = 0;
	        for (int i = 1; i <= m; i++) {
	            count += Math.min(x / i, n);
	        }
	        return count >= k;
	    }

	    public int findKthNumber(int m, int n, int k) {
	        int lo = 1, hi = m * n;
	        while (lo < hi) {
	            int mi = lo + (hi - lo) / 2;
	            if (!enough(mi, m, n, k)) lo = mi + 1;
	            else hi = mi;
	        }
	        return lo;
	    }
	}
	
	
	
}
