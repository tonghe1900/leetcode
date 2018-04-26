package Leetcode600x;
/**
 * 666. Path Sum IV 
 
 
 
 
 
 
 
 
Average Rating: 5 (1 votes)

Dec. 10, 2017  |  1.4K views
If the depth of a tree is smaller than 5, then this tree can be represented by a list of three-digits integers.

For each integer in this list:
The hundreds digit represents the depth D of this node, 1 <= D <= 4.
The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8. The position is the same as that in a full binary tree.
The units digit represents the value V of this node, 0 <= V <= 9.
Given a list of ascending three-digits integers representing a binary with the depth smaller than 5. You need to return the sum of all paths from the root towards the leaves.

Example 1:
Input: [113, 215, 221]
Output: 12
Explanation: 
The tree that the list represents is:
    3
   / \
  5   1

The path sum is (3 + 5) + (3 + 1) = 12.
Example 2:
Input: [113, 221]
Output: 4
Explanation: 
The tree that the list represents is: 
    3
     \
      1

The path sum is (3 + 1) = 4.
 * @author tonghe
 *
 */
public class Leetcode666 {
//https://leetcode.com/articles/path-sum-iv/
	
	
	class Solution {
	    int ans = 0;
	    public int pathSum(int[] nums) {
	        Node root = new Node(nums[0] % 10);
	        for (int num: nums) {
	            if (num == nums[0]) continue;
	            int depth = num / 100, pos = num / 10 % 10, val = num % 10;
	            pos--;
	            Node cur = root;
	            for (int d = depth - 2; d >= 0; --d) {
	                if (pos < 1<<d) {
	                    if (cur.left == null) cur.left = new Node(val);
	                    cur = cur.left;
	                } else {
	                    if (cur.right == null) cur.right = new Node(val);
	                    cur = cur.right;
	                }
	                pos %= 1<<d;
	            }
	        }

	        dfs(root, 0);
	        return ans;
	    }

	    public void dfs(Node node, int sum) {
	        if (node == null) return;
	        sum += node.val;
	        if (node.left == null && node.right == null) {
	            ans += sum;
	        } else {
	            dfs(node.left, sum);
	            dfs(node.right, sum);
	        }
	    }
	}

	class Node {
	    Node left, right;
	    int val;
	    Node(int v) {val = v;}
	}
	
	
	
	
	
	class Solution {
	    int ans = 0;
	    Map<Integer, Integer> values;
	    public int pathSum(int[] nums) {
	        values = new HashMap();
	        for (int num: nums)
	            values.put(num / 10, num % 10);

	        dfs(nums[0] / 10, 0);
	        return ans;
	    }

	    public void dfs(int node, int sum) {
	        if (!values.containsKey(node)) return;
	        sum += values.get(node);

	        int depth = node / 10, pos = node % 10;
	        int left = (depth + 1) * 10 + 2 * pos - 1;
	        int right = left + 1;

	        if (!values.containsKey(left) && !values.containsKey(right)) {
	            ans += sum;
	        } else {
	            dfs(left, sum);
	            dfs(right, sum);
	        }
	    }
	}
	
	
	
}
