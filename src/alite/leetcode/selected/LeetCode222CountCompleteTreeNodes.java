package alite.leetcode.selected;

public class LeetCode222CountCompleteTreeNodes {
	public int countNodes(TreeNode root) {
		if(root == null) return 0;
	    int leftDepth = leftDepth(root);
	 int rightDepth = rightDepth(root);

	 if (leftDepth == rightDepth)
	  return (1 << leftDepth) - 1;
	 else
	  return 1+countNodes(root.left) + countNodes(root.right);

	}

	private int rightDepth(TreeNode root) {
	 // TODO Auto-generated method stub
	 int dep = 0;
	 while (root != null) {
	  root = root.right;
	  dep++;
	 }
	 return dep;
	}

	private int leftDepth(TreeNode root) {
	 // TODO Auto-generated method stub
	 int dep = 0;
	 while (root != null) {
	  root = root.left;
	  dep++;
	 }
	 return dep;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
