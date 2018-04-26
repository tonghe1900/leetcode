package alite.leetcode.xx2.sucess.extra;
/**
 * LeetCode Binary Tree Upside Down

原题链接在这里：https://leetcode.com/problems/binary-tree-upside-down/

Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.

For example:
Given a binary tree {1,2,3,4,5},

    1
   / \
  2   3
 / \
4   5
return the root of the binary tree [4,5,2,#,#,3,1].

   4
  / \
 5   2
    / \
   3   1  
confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
Recursion 方法是自底向上。

Time Complexity: O(n). Space: O(logn).
AC Java:
复制代码
 1 /**
 2  * Definition for a binary tree node.
 3  * public class TreeNode {
 4  *     int val;
 5  *     TreeNode left;
 6  *     TreeNode right;
 7  *     TreeNode(int x) { val = x; }
 8  * }
 9  */
10 public class Solution {
11     public TreeNode upsideDownBinaryTree(TreeNode root) {
12         if(root == null || root.left  == null){
13             return root;
14         }
15         TreeNode newRoot = upsideDownBinaryTree(root.left);
16         
17         root.left.left = root.right;
18         root.left.right = root;
19         
20         root.left = null;
21         root.right = null;
22         return newRoot;
23     }
24 }
复制代码
 

Iterative 是从上到下。

Time Complexity: O(n). Space: O(1).

复制代码
 1 /**
 2  * Definition for a binary tree node.
 3  * public class TreeNode {
 4  *     int val;
 5  *     TreeNode left;
 6  *     TreeNode right;
 7  *     TreeNode(int x) { val = x; }
 8  * }
 9  */
 
 1
/ \
2   3
/ \
4   5
return the root of the binary tree [4,5,2,#,#,3,1].

4
/ \
5   2
 / \
3   1 
10 public class Solution {
11     public TreeNode upsideDownBinaryTree(TreeNode root) {
12         if(root == null || root.left  == null){
13             return root;
14         }
15         TreeNode cur = root;
16         TreeNode next = null;
17         TreeNode pre = null;
18         TreeNode temp = null;
19         while(cur != null){
20             next = cur.left;
21             cur.left = temp;
22             temp = cur.right;
23             cur.right = pre;
24             pre = cur;
25             cur = next;
26         }
27         return pre;
28     }
29 }
复制代码
 * @author het
 *
 */
public class BinaryTreeUpSideDown {

}
