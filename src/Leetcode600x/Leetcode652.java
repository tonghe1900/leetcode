package Leetcode600x;
/**
 * Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, you only need to return the root node of any one of them.

Two trees are duplicate if they have the same structure with same node values.

Example 1: 
        1
       / \
      2   3
     /   / \
    4   2   4
       /
      4
The following are two duplicate subtrees:
      2
     /
    4
and
    4
Therefore, you need to return above trees' root in the form of a list.
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode652 {
//https://leetcode.com/problems/find-duplicate-subtrees/solution/
	
	class Solution {
	    Map<String, Integer> count;
	    List<TreeNode> ans;
	    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
	        count = new HashMap();
	        ans = new ArrayList();
	        collect(root);
	        return ans;
	    }

	    public String collect(TreeNode node) {
	        if (node == null) return "#";
	        String serial = node.val + "," + collect(node.left) + "," + collect(node.right);
	        count.put(serial, count.getOrDefault(serial, 0) + 1);
	        if (count.get(serial) == 2)
	            ans.add(node);
	        return serial;
	    }
	}
	
	
	class Solution {
	    int t;
	    Map<String, Integer> trees;
	    Map<Integer, Integer> count;
	    List<TreeNode> ans;

	    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
	        t = 1;
	        trees = new HashMap();
	        count = new HashMap();
	        ans = new ArrayList();
	        lookup(root);
	        return ans;
	    }

	    public int lookup(TreeNode node) {
	        if (node == null) return 0;
	        String serial = node.val + "," + lookup(node.left) + "," + lookup(node.right);
	        int uid = trees.computeIfAbsent(serial, x-> t++);
	        count.put(uid, count.getOrDefault(uid, 0) + 1);
	        if (count.get(uid) == 2)
	            ans.add(node);
	        return uid;
	    }
	}
}
