package alite.leetcode.newExtra.L500;
/**
 * LeetCode - 563 Binary Tree Tilt

https://leetcode.com/problems/binary-tree-tilt
Given a binary tree, return the tilt of the whole tree.
The tilt of a tree node is defined as the absolute difference between the sum of all left subtree 
node values and the sum of all right subtree node values. Null node has tilt 0.
The tilt of the whole tree is defined as the sum of all nodes' tilt.
Example:
Input: 
         1
       /   \
      2     3
Output: 1
Explanation: 
Tilt of node 2 : 0
Tilt of node 3 : 0
Tilt of node 1 : |2-3| = 1
Tilt of binary tree : 0 + 0 + 1 = 1
Note:
The sum of node values in any subtree won't exceed the range of 32-bit integer.
All the tilt values won't exceed the range of 32-bit integer.

https://discuss.leetcode.com/topic/87191/java-o-n-postorder-traversal
    int tilt = 0;
    
    public int findTilt(TreeNode root) {
        postorder(root);
        return tilt;
    }
    
    public int postorder(TreeNode root) {
        if (root == null) return 0;
        int leftSum = postorder(root.left);
        int rightSum = postorder(root.right);
        tilt += Math.abs(leftSum - rightSum);
        return leftSum + rightSum + root.val;
    }
 * @author het
 *
 */
public class L563 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
