package Leetcode600x;
/**
 * 663. Equal Tree Partition 
 
 
 
 
 
 
 
 
Average Rating: 3 (1 votes)

Dec. 10, 2017  |  1.1K views
Given a binary tree with n nodes, your task is to check if it's possible to partition the tree to two trees which have the equal sum of values after removing exactly one edge on the original tree.

Example 1:
Input:     
    5
   / \
  10 10
    /  \
   2   3

Output: True
Explanation: 
    5
   / 
  10
      
Sum: 15

   10
  /  \
 2    3

Sum: 15
Example 2:
Input:     
    1
   / \
  2  10
    /  \
   2   20

Output: False
Explanation: You can't split the tree into two trees with equal sum after removing exactly one edge on the tree.
Note:
The range of tree node value is in the range of [-100000, 100000].
1 <= n <= 10000
 * @author tonghe
 *
 */
public class Leetcode663 {
//https://leetcode.com/articles/equal-tree-partition/
	
	class Solution {
	    Stack<Integer> seen;
	    public boolean checkEqualTree(TreeNode root) {
	        seen = new Stack();
	        int total = sum(root);
	        seen.pop();
	        if (total % 2 == 0)
	            for (int s: seen)
	                if (s == total / 2)
	                    return true;
	        return false;
	    }

	    public int sum(TreeNode node) {
	        if (node == null) return 0;
	        seen.push(sum(node.left) + sum(node.right) + node.val);
	        return seen.peek();
	    }
	}
}
