package alite.leetcode.xx3;

import alite.leetcode.xx3.LeetCode315.TreeNode;

///**
// * Leetcode: 337. House Robber III
//The thief has found himself a new place for his thievery again. There is only one entrance to this area,
//called the "root." Besides the root, each house has one and only one parent house. 
//After a tour, the smart thief realized that "all houses in this place forms a binary tree". 
//It will automatically contact the police if two directly-linked houses were broken into on the same night.
//Determine the maximum amount of money the thief can rob tonight without alerting the police.
//Example 1:
//     3
//    / \
//   2   3
//    \   \ 
//     3   1
//Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
//Example 2:
//     3
//    / \
//   4   5
//  / \   \ 
// 1   3   1
//Maximum amount of money the thief can rob = 4 + 5 = 9.
//Native solution:
//The native solution is just to use DFS. For each node, we have two cases to consider: rob root and no rob root. If rob the root, the max value depends on the root's grandchildren. If not rob the root, the max value depends on the root's children. 
//
//Code (Java):
//?
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
//35
//36
//37
//38
//39
//40
//41
//42
//43
///**
// * Definition for a binary tree node.
// * public class TreeNode {
// *     int val;
// *     TreeNode left;
// *     TreeNode right;
// *     TreeNode(int x) { val = x; }
// * }
// */
//public class Solution {
//    public int rob(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//         
//        if (root.left == null && root.right == null) {
//            return root.val;
//        }
//         
//         
//        // case1: rob the root
//        int leftMax = 0;
//        int rightMax = 0;
//         
//        if (root.left != null) {
//            leftMax = rob(root.left.left) + rob(root.left.right);
//        }
//         
//        if (root.right != null) {
//            rightMax = rob(root.right.left) + rob(root.right.right);
//        }
//         
//        int maxRoot = root.val + leftMax + rightMax;
//         
//        // case 2: not rob the root
//        leftMax = rob(root.left);
//        rightMax = rob(root.right);
//         
//        int maxNoRoot = leftMax + rightMax;
//         
//        return Math.max(maxRoot, maxNoRoot);
//    }
//}
//
//A DP Solution:
//Note that the naive solution above would result in lots of repeated calculations. It will lead to exponential time complexity. The idea of the DP solution is for each node, maintain two fields: the max value if rob the root, and the max value without robbing the root. Then we can use a bottom-up DP to avoid the repeated calculations. 
//
//Code (Java):
//?
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
///**
// * Definition for a binary tree node.
// * public class TreeNode {
// *     int val;
// *     TreeNode left;
// *     TreeNode right;
// *     TreeNode(int x) { val = x; }
// * }
// */
//public class Solution {
//    public int rob(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//         
//        int[] result = robHelper(root);
//        return Math.max(result[0], result[1]);
//    }
//     
//    private int[] robHelper(TreeNode root) {
//        if (root == null) {
//            return new int[2];
//        }
//         
//        int[] left = robHelper(root.left);
//        int[] right = robHelper(root.right);
//         
//        int[] curr = new int[2];
//        curr[0] = root.val + left[1] + right[1];
//        curr[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
//         
//        return curr;
//    }
//}
// *
// */
public class LeetCode337 {
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
	    public int rob(TreeNode root) {
	        if (root == null) {
	            return 0;
	        }
	         
	        int[] result = robHelper(root);
	        return Math.max(result[0], result[1]);
	    }
	     
	    private int[] robHelper(TreeNode root) {
	        if (root == null) {
	            return new int[2];
	        }
	         
	        int[] left = robHelper(root.left);
	        int[] right = robHelper(root.right);
	         
	        int[] curr = new int[2];
	        curr[0] = root.val + left[1] + right[1];
	        curr[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
	         
	        return curr;
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
