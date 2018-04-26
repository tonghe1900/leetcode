package Leetcode600x;
/**
 * Given a Binary Search Tree and a target number, return true if there exist two elements in the BST such that their sum is equal to the given target.

Example 1:
Input: 
    5
   / \
  3   6
 / \   \
2   4   7

Target = 9

Output: True
Example 2:
Input: 
    5
   / \
  3   6
 / \   \
2   4   7

Target = 28

Output: False
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode653 {
//https://leetcode.com/problems/two-sum-iv-input-is-a-bst/solution/
	
	public class Solution {
	    public boolean findTarget(TreeNode root, int k) {
	        Set < Integer > set = new HashSet();
	        return find(root, k, set);
	    }
	    public boolean find(TreeNode root, int k, Set < Integer > set) {
	        if (root == null)
	            return false;
	        if (set.contains(k - root.val))
	            return true;
	        set.add(root.val);
	        return find(root.left, k, set) || find(root.right, k, set);
	    }
	}

	
	
	public class Solution {
	    public boolean findTarget(TreeNode root, int k) {
	        Set < Integer > set = new HashSet();
	        Queue < TreeNode > queue = new LinkedList();
	        queue.add(root);
	        while (!queue.isEmpty()) {
	            if (queue.peek() != null) {
	                TreeNode node = queue.remove();
	                if (set.contains(k - node.val))
	                    return true;
	                set.add(node.val);
	                queue.add(node.right);
	                queue.add(node.left);
	            } else
	                queue.remove();
	        }
	        return false;
	    }
	}
	
	
	public class Solution {
	    public boolean findTarget(TreeNode root, int k) {
	        List < Integer > list = new ArrayList();
	        inorder(root, list);
	        int l = 0, r = list.size() - 1;
	        while (l < r) {
	            int sum = list.get(l) + list.get(r);
	            if (sum == k)
	                return true;
	            if (sum < k)
	                l++;
	            else
	                r--;
	        }
	        return false;
	    }
	    public void inorder(TreeNode root, List < Integer > list) {
	        if (root == null)
	            return;
	        inorder(root.left, list);
	        list.add(root.val);
	        inorder(root.right, list);
	    }
	}
	
	
	
}
