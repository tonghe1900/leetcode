package alite.leetcode.xx4;
///**
// * https://leetcode.com/problems/sum-of-left-leaves/
//Find the sum of all left leaves in a given binary tree.
//Example:
//    3
//   / \
//  9  20
//    /  \
//   15   7
//
//There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
//X. DFS
//https://discuss.leetcode.com/topic/60434/java-clean-no-helper-recursive/2
//    public int sumOfLeftLeaves(TreeNode n) {
//        if(n==null ||(n.left==null && n.right ==null))return 0;
//        int l=0,r=0;
//        if(n.left!=null)l=(n.left.left==null && n.left.right==null)?n.left.val:sumOfLeftLeaves(n.left);
//        if(n.right!=null)r=sumOfLeftLeaves(n.right);
//        return l+r;
//    }
//https://discuss.leetcode.com/topic/60403/java-iterative-and-recursive-solutions
//public int sumOfLeftLeaves(TreeNode root) {
//    if(root == null) return 0;
//    int ans = 0;
//    if(root.left != null) {
//        if(root.left.left == null && root.left.right == null) ans += root.left.val;
//        else ans += sumOfLeftLeaves(root.left);
//    }
//    if(root.right != null) {
//        ans += sumOfLeftLeaves(root.right);
//    }
//    
//    return ans;
//}
//X. DFS - V2
//https://discuss.leetcode.com/topic/60364/easy-java-solution-with-comment
//    public int sumOfLeftLeaves(TreeNode root) {
//        return helper(root, false);
//    }
//    
//    private int helper(TreeNode root, boolean isLeft){
//        if(root == null) return 0;
//        /*This node is a leaf, if it's a left leaf, we return the value
//          if it's a right leaf we return 0 since right leaf doesn't count*/
//        if(root.left == null && root.right == null){
//            if(isLeft){
//                return root.val;
//            }
//            return 0;
//        }
//        return helper(root.left, true) + helper(root.right, false);
//    }  
//X. DFS Iterative Version
//https://discuss.leetcode.com/topic/60403/java-iterative-and-recursive-solutions
//Iterative method. Here for each node in the tree we check whether its left child is a leaf. If it is true, we add its value to answer, otherwise add left child to the stack to process it later. For right child we add it to stack only if it is not a leaf.
//public int sumOfLeftLeaves(TreeNode root) {
//    if(root == null) return 0;
//    int ans = 0;
//    Stack<TreeNode> stack = new Stack<TreeNode>();
//    stack.push(root);
//    
//    while(!stack.empty()) {
//        TreeNode node = stack.pop();
//        if(node.left != null) {
//            if (node.left.left == null && node.left.right == null)
//                ans += node.left.val;
//            else
//                stack.push(node.left);
//        }
//        if(node.right != null) {
//            if (node.right.left != null || node.right.right != null)
//                stack.push(node.right);
//        }
//    }
//    return ans;
//}
//https://discuss.leetcode.com/topic/60415/java-solution-with-stack
//    public int sumOfLeftLeaves(TreeNode root) {
//        int res = 0;
//
//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(root);
//        
//        while (!stack.isEmpty()) {
//            TreeNode node = stack.pop();
//            if (node != null) {
//                if (node.left != null && node.left.left == null && node.left.right == null)
//                    res += node.left.val;
//                stack.push(node.left);
//                stack.push(node.right);
//            }
//        }
//
//        return res;
//    }
//
//X. BFS
//https://discuss.leetcode.com/topic/60381/java-solution-using-bfs
//    public int sumOfLeftLeaves(TreeNode root) {
//        if(root == null || root.left == null && root.right == null) return 0;
//        
//        int res = 0;
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.offer(root);
//        
//        while(!queue.isEmpty()) {
//            TreeNode curr = queue.poll();
//
//            if(curr.left != null && curr.left.left == null && curr.left.right == null) res += curr.left.val;
//            if(curr.left != null) queue.offer(curr.left);
//            if(curr.right != null) queue.offer(curr.right);
//        }
//        return res;
//    }
// * @author het
// *
// */
public class LeetCode404SumofLeftLeaves {
	public int sumOfLeftLeaves(TreeNode root) {
	    if(root == null) return 0;
	    int ans = 0;
	    if(root.left != null) {
	        if(root.left.left == null && root.left.right == null) ans += root.left.val;
	        else ans += sumOfLeftLeaves(root.left);
	    }
	    if(root.right != null) {
	        ans += sumOfLeftLeaves(root.right);
	    }
	    
	    return ans;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
