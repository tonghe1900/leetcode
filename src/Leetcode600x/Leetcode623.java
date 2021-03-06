package Leetcode600x;
/**
 * Given the root of a binary tree, then value v and depth d, you need to add a row of nodes with value v at the given depth d. The root node is at depth 1.

The adding rule is: given a positive integer depth d, for each NOT null tree nodes N in depth d-1, create two tree nodes with value v as N's left subtree root and right subtree root. And N's original left subtree should be the left subtree of the new left subtree root, its original right subtree should be the right subtree of the new right subtree root. If depth d is 1 that means there is no depth d-1 at all, then create a tree node with value v as the new root of the whole original tree, and the original tree is the new root's left subtree.

Example 1:
Input: 
A binary tree as following:
       4
     /   \
    2     6
   / \   / 
  3   1 5   

v = 1

d = 2

Output: 
       4
      / \
     1   1
    /     \
   2       6
  / \     / 
 3   1   5   

Example 2:
Input: 
A binary tree as following:
      4
     /   
    2    
   / \   
  3   1    

v = 1

d = 3

Output: 
      4
     /   
    2
   / \    
  1   1
 /     \  
3       1
Note:
The given d is in range [1, maximum depth of the given tree + 1].
The given binary tree has at least one tree node.
Seen this question in a real interview before?



 * @author tonghe
 *
 */
public class Leetcode623 {
//https://leetcode.com/problems/add-one-row-to-tree/solution/
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	public class Solution {
	    public TreeNode addOneRow(TreeNode t, int v, int d) {
	        if (d == 1) {
	            TreeNode n = new TreeNode(v);
	            n.left = t;
	            return n;
	        }
	        insert(v, t, 1, d);
	        return t;
	    }

	    public void insert(int val, TreeNode node, int depth, int n) {
	        if (node == null)
	            return;
	        if (depth == n - 1) {
	            TreeNode t = node.left;
	            node.left = new TreeNode(val);
	            node.left.left = t;
	            t = node.right;
	            node.right = new TreeNode(val);
	            node.right.right = t;
	        } else {
	            insert(val, node.left, depth + 1, n);
	            insert(val, node.right, depth + 1, n);
	        }
	    }
	}

	
	
	
	
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	public class Solution {
	    class Node{
	        Node(TreeNode n,int d){
	            node=n;
	            depth=d;
	        }
	        TreeNode node;
	        int depth;
	    }
	    public TreeNode addOneRow(TreeNode t, int v, int d) {
	        if (d == 1) {
	            TreeNode n = new TreeNode(v);
	            n.left = t;
	            return n;
	        } 
	        Stack<Node> stack=new Stack<>();
	        stack.push(new Node(t,1));
	        while(!stack.isEmpty())
	        {
	            Node n=stack.pop();
	            if(n.node==null)
	                continue;
	            if (n.depth == d - 1 ) {
	                TreeNode temp = n.node.left;
	                n.node.left = new TreeNode(v);
	                n.node.left.left = temp;
	                temp = n.node.right;
	                n.node.right = new TreeNode(v);
	                n.node.right.right = temp;
	                
	            } else{
	                stack.push(new Node(n.node.left, n.depth + 1));
	                stack.push(new Node(n.node.right, n.depth + 1));
	            }
	        }
	        return t;
	    }
	}

	
	
	
	
	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	public class Solution {
	    public TreeNode addOneRow(TreeNode t, int v, int d) {
	        if (d == 1) {
	            TreeNode n = new TreeNode(v);
	            n.left = t;
	            return n;
	        }
	        Queue < TreeNode > queue = new LinkedList < > ();
	        queue.add(t);
	        int depth = 1;
	        while (depth < d - 1) {
	            Queue < TreeNode > temp = new LinkedList < > ();
	            while (!queue.isEmpty()) {
	                TreeNode node = queue.remove();
	                if (node.left != null) temp.add(node.left);
	                if (node.right != null) temp.add(node.right);
	            }
	            queue = temp;
	            depth++;
	        }
	        while (!queue.isEmpty()) {
	            TreeNode node = queue.remove();
	            TreeNode temp = node.left;
	            node.left = new TreeNode(v);
	            node.left.left = temp;
	            temp = node.right;
	            node.right = new TreeNode(v);
	            node.right.right = temp;
	        }
	        return t;
	    }
	}
	
	
}
