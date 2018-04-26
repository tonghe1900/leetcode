package alite.leetcode.newExtra.L500;
/**
 * LeetCode 549 - Binary Tree Longest Consecutive Sequence II

http://bookshadow.com/weblog/2017/04/09/leetcode-binary-tree-longest-consecutive-sequence-ii/
Given a binary tree, you need to find the length of Longest Consecutive Path in Binary Tree.
Especially, this path can be either increasing or decreasing. For example, [1,2,3,4] and [4,3,2,1] are both
 considered valid, but the path [1,2,4,3] is not valid. On the other hand, the path can be in the child-Parent-child
  order, where not necessarily be parent-child order.
Example 1:
Input:
        1
       / \
      2   3
Output: 2
Explanation: The longest consecutive path is [1, 2] or [2, 1].
Example 2:
Input:
        2
       / \
      1   3
Output: 3
Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].

解法I 一趟遍历
时间复杂度O(n) n为节点的个数
定义函数solve(root)，递归求解以root为根节点向子节点方向（parent-child）的路径中，最大连续递增路径长度inc，以及最大连续递减路径长度dec

则以root为根节点的子树中，最大连续路径长度=inc + dec + 1（路径不包含root）
https://discuss.leetcode.com/topic/85764/neat-java-solution-single-pass-o-n
    int maxval = 0;
    public int longestConsecutive(TreeNode root) {
        longestPath(root);
        return maxval;
    }
    public int[] longestPath(TreeNode root) {
        if (root == null)
            return new int[] {0,0};
        int inr = 1, dcr = 1;
        if (root.left != null) {
            int[] l = longestPath(root.left);
            if (root.val == root.left.val + 1)
                dcr = l[1] + 1;
            else if (root.val == root.left.val - 1)
                inr = l[0] + 1;
        }
        if (root.right != null) {
            int[] r = longestPath(root.right);
            if (root.val == root.right.val + 1)
                dcr = Math.max(dcr, r[1] + 1);
            else if (root.val == root.right.val - 1)
                inr = Math.max(inr, r[0] + 1);
        }
        maxval = Math.max(maxval, dcr + inr - 1);
        return new int[] {inr, dcr};
    }
https://discuss.leetcode.com/topic/85745/java-solution-binary-tree-post-order-traversal
    int max = 0;
    
    class Result {
        TreeNode node;
        int inc;
        int des;
    }
    
    public int longestConsecutive(TreeNode root) {
        traverse(root);
        return max;
    }
    
    private Result traverse(TreeNode node) {
        if (node == null) return null;
        
        Result left = traverse(node.left);
        Result right = traverse(node.right);
        
        Result curr = new Result();
        curr.node = node;
        curr.inc = 1;
        curr.des = 1;
        
        if (left != null) {
            if (node.val - left.node.val == 1) {
                curr.inc = Math.max(curr.inc, left.inc + 1);
            }
            else if (node.val - left.node.val == -1) {
                curr.des = Math.max(curr.des, left.des + 1);
            }
        }
        
        if (right != null) {
            if (node.val - right.node.val == 1) {
                curr.inc = Math.max(curr.inc, right.inc + 1);
            }
            else if (node.val - right.node.val == -1) {
                curr.des = Math.max(curr.des, right.des + 1);
            }
        }
        
        max = Math.max(max, curr.inc + curr.des - 1);
        
        return curr;
    }

解法II 递归 + 遍历二叉树
时间复杂度O(n^2) n为节点的个数
定义函数maxLength，递归计算从根节点出发向叶子节点可以得到的最长连续路径长度。

分别记根节点root的左右孩子为lchild, rchild

若lchild与root的差值为1，调用maxLength得到左子路径长度lsize

若rchild与root的差值为1，调用maxLength得到右子路径长度rsize

若lchild < root < rchild或者lchild > root > rchild：

则当前最长路径长度为lsize + rsize + 1

否则，最长路径为max(lsize, rsize) + 1

遍历二叉树，求最大值。
 * @author het
 *
 */
public class L549 {
//	解法I 一趟遍历
//	时间复杂度O(n) n为节点的个数
//	定义函数solve(root)，递归求解以root为根节点向子节点方向（parent-child）的路径中，最大连续递增路径长度inc，以及最大连续递减路径长度dec

//	则以root为根节点的子树中，最大连续路径长度=inc + dec + 1（路径不包含root）
//	https://discuss.leetcode.com/topic/85764/neat-java-solution-single-pass-o-n
	    int maxval = 0;
	    public int longestConsecutive(TreeNode root) {
	        longestPath(root);
	        return maxval;
	    }
	    public int[] longestPath(TreeNode root) {
	        if (root == null)
	            return new int[] {0,0};
	        int inr = 1, dcr = 1;
	        if (root.left != null) {
	            int[] l = longestPath(root.left);
	            if (root.val == root.left.val + 1)
	                dcr = l[1] + 1;
	            else if (root.val == root.left.val - 1)
	                inr = l[0] + 1;
	        }
	        if (root.right != null) {
	            int[] r = longestPath(root.right);
	            if (root.val == root.right.val + 1)
	                dcr = Math.max(dcr, r[1] + 1);
	            else if (root.val == root.right.val - 1)
	                inr = Math.max(inr, r[0] + 1);
	        }
	        maxval = Math.max(maxval, dcr + inr - 1);
	        return new int[] {inr, dcr};
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
