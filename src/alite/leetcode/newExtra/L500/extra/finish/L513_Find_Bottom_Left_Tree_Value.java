package alite.leetcode.newExtra.L500.extra.finish;
/**
 * 
https://leetcode.com/problems/find-bottom-left-tree-value/
Given a binary tree, find the leftmost value in the last row of the tree.
Example 1:
Input:

    2
   / \
  1   3

Output:
1
Example 2: 
Input:

        1
       / \
      2   3
     /   / \
    4   5   6
       /
      7

Output:
7


Note: You may assume the tree (i.e., the given root node) is not NULL.

https://discuss.leetcode.com/topic/78962/simple-java-solution-beats-100-0
    int ans=0, h=0;
    public int findLeftMostNode(TreeNode root) {
        findLeftMostNode(root, 1);
        return ans;
    }
    public void findLeftMostNode(TreeNode root, int depth) {
        if (h<depth) {ans=root.val;h=depth;}
        if (root.left!=null) findLeftMostNode(root.left, depth+1);
        if (root.right!=null) findLeftMostNode(root.right, depth+1);
    }
X. Level Order traversal
https://discuss.leetcode.com/topic/78954/verbose-java-solution-binary-tree-level-order-traversal
https://discuss.leetcode.com/topic/78941/java-bfs
Typical way to do binary tree level order traversal. Only additional step is to remember the first element of each level.
    public int findLeftMostNode(TreeNode root) {
        if (root == null) return 0;
        
        int result = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == 0) result = node.val;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
        
        return result;
    }
https://discuss.leetcode.com/topic/78981/right-to-left-bfs-python-java
Doing BFS right to left means we can simply return the last node's value and don't have to keep track of the first node in the current tree row.
public int findLeftMostNode(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        root = queue.poll();
        if (root.right != null)
            queue.add(root.right);
        if (root.left != null)
            queue.add(root.left);
    }
    return root.val;
}
 * @author het
 *
 */
public class L513_Find_Bottom_Left_Tree_Value {
	int ans=0, h=0;
    public int findLeftMostNode(TreeNode root) {
        findLeftMostNode(root, 1);
        return ans;
    }
    public void findLeftMostNode(TreeNode root, int depth) {
        if (h<depth) {ans=root.val;h=depth;}
        if (root.left!=null) findLeftMostNode(root.left, depth+1);
        if (root.right!=null) findLeftMostNode(root.right, depth+1);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
