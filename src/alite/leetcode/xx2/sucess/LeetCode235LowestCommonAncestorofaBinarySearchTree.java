package alite.leetcode.xx2.sucess;
/**
 * Related: Lowest Common Ancestor in a Binary Tree
Given a binary search tree (BST), find the lowest common ancestor of two given nodes in the BST.
Both nodes are to the left of the tree.
Both nodes are to the right of the tree.
One node is on the left while the other is on the right
When the current node equals to either of the two nodes, this node must be the LCA too.
For case 1), the LCA must be in its left subtree. Similar with case 2), LCA must be in its right subtree. 
For case 3), the current node must be the LCA.
Therefore, using a top-down approach, we traverse to the left/right subtree depends on the case of 1) or 2),
 until we reach case 3), which we concludes that we have found the LCA.
A careful reader might notice that we forgot to handle one extra case. What if the node itself is equal to
 one of the two nodes? This is the exact case from our earlier example! (The LCA of 2 and 4 should be 2). 
 Therefore, we add case number 4)
 * @author het
 *
 */
public class LeetCode235LowestCommonAncestorofaBinarySearchTree {
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	    if (root == null || p == null || q == null) return null;
	    if(root.val<Math.min(p.val,q.val)) return lowestCommonAncestor(root.right,p,q);
	    if(root.val>Math.max(p.val,q.val)) return lowestCommonAncestor(root.left,p,q);
	    return root;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
