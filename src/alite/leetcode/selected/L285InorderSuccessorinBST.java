package alite.leetcode.selected;
/**
 * LEETCODE 285. INORDER SUCCESSOR IN BST
LC address: Inorder Successor in BST

Given a binary search tree and a node in it, find the in-order successor of that node in the BST.

Note: If the given node has no in-order successor in the tree, return null.

Analysis:

分两种情况考虑：一种是p.right != null，说明可以从p.right开始找successor；另一种是p.right == null，说明需要从root开始寻找，因为这个是BST，而且p没有right，所以successor一定是某个包含p的子树的root。因此我们可以用一个临时变量，也就是successor，来存当前找到的大于p.val的node，在从root往leaves寻找的时候，每次有新的node符合这个条件就更新这个临时变量，最后返回这个临时变量就可以。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31

public class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (p.right != null) {
            p = p.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        TreeNode successor = null;
        TreeNode node = root;
        while (node.val != p.val) {
            if (node.val < p.val) {
                node = node.right;
            } else {
                successor = node;
                node = node.left;
            }
        }
        return successor;
    }
}
 * @author het
 *
 */
public class L285InorderSuccessorinBST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
