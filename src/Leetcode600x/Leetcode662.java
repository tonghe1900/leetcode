package Leetcode600x;
/**
 * Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is the maximum width among all levels. The binary tree has the same structure as a full binary tree, but some nodes are null.

The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level, where the null nodes between the end-nodes are also counted into the length calculation.

Example 1:
Input: 

           1
         /   \
        3     2
       / \     \  
      5   3     9 

Output: 4
Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
Example 2:
Input: 

          1
         /  
        3    
       / \       
      5   3     

Output: 2
Explanation: The maximum width existing in the third level with the length 2 (5,3).
Example 3:
Input: 

          1
         / \
        3   2 
       /        
      5      

Output: 2
Explanation: The maximum width existing in the second level with the length 2 (3,2).
Example 4:
Input: 

          1
         / \
        3   2
       /     \  
      5       9 
     /         \
    6           7
Output: 8
Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).


Note: Answer will in the range of 32-bit signed integer.

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode662 {
//https://leetcode.com/problems/maximum-width-of-binary-tree/solution/
	
	
	class Solution {
	    public int widthOfBinaryTree(TreeNode root) {
	        Queue<AnnotatedNode> queue = new LinkedList();
	        queue.add(new AnnotatedNode(root, 0, 0));
	        int curDepth = 0, left = 0, ans = 0;
	        while (!queue.isEmpty()) {
	            AnnotatedNode a = queue.poll();
	            if (a.node != null) {
	                queue.add(new AnnotatedNode(a.node.left, a.depth + 1, a.pos * 2));
	                queue.add(new AnnotatedNode(a.node.right, a.depth + 1, a.pos * 2 + 1));
	                if (curDepth != a.depth) {
	                    curDepth = a.depth;
	                    left = a.pos;
	                }
	                ans = Math.max(ans, a.pos - left + 1);
	            }
	        }
	        return ans;
	    }
	}

	class AnnotatedNode {
	    TreeNode node;
	    int depth, pos;
	    AnnotatedNode(TreeNode n, int d, int p) {
	        node = n;
	        depth = d;
	        pos = p;
	    }
	}
	
	
	
	
	
	class Solution {
	    int ans;
	    Map<Integer, Integer> left;
	    public int widthOfBinaryTree(TreeNode root) {
	        ans = 0;
	        left = new HashMap();
	        dfs(root, 0, 0);
	        return ans;
	    }
	    public void dfs(TreeNode root, int depth, int pos) {
	        if (root == null) return;
	        left.computeIfAbsent(depth, x-> pos);
	        ans = Math.max(ans, pos - left.get(depth) + 1);
	        dfs(root.left, depth + 1, 2 * pos);
	        dfs(root.right, depth + 1, 2 * pos + 1);
	    }
	}
}
