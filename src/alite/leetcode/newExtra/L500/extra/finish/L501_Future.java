package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 501 - Find Mode in Binary Search Tree

https://leetcode.com/problems/find-mode-in-binary-search-tree/
Given a binary search tree (BST) with duplicates, find all the mode(s) (the most frequently occurred element) in the given BST.
Assume a BST is defined as follows:
The left subtree of a node contains only nodes with keys less than or equal to the node's key.
The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
Both the left and right subtrees must also be binary search trees.
For example:
Given BST [1,null,2,2],
   1
    \
     2
    /
   2
return [2].
Note: If a tree has more than one mode, you can return them in any order.
Follow up: Could you do that without using any extra space? (Assume that the implicit stack space incurred due to recursion does not count).
http://bookshadow.com/weblog/2017/01/29/leetcode-find-mode-in-binary-tree/
二叉树遍历 + 计数

    def findMode(self, root):
        """
        :type root: TreeNode
        :rtype: List[int]
        """
        counter = collections.Counter()
        def traverse(root):
            if not root: return
            counter[root.val] += 1
            traverse(root.left)
            traverse(root.right)
        traverse(root)
        return [e for e, v in counter.iteritems() if v == max(counter.values())]
 * @author het
 *
 */
public class L501_Future {

}
