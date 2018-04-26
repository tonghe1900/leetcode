package alite.leetcode.newExtra.L500;
/**
 * 
https://leetcode.com/problems/minimum-absolute-difference-in-bst
Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
Note: There are at least two nodes in this BST.

X. https://discuss.leetcode.com/topic/80823/two-solutions-in-order-traversal-and-a-more-general-way-using-treeset
Solution 1 - In-Order traverse, time complexity O(N), space complexity O(1).
public class Solution {
    int min = Integer.MAX_VALUE;
    Integer prev = null;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        getMinimumDifference(root.left);
        
        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }
        prev = root.val;
        
        getMinimumDifference(root.right);
        
        return min;
    }
    
}
What if it is not a BST? (Follow up of the problem) The idea is to put values in a TreeSet and then every time
 we can use O(lgN) time to lookup for the nearest values.
https://discuss.leetcode.com/topic/80796/java-o-n-time-inorder-traversal-solution

Solution 2 - Pre-Order traverse, time complexity O(NlgN), space complexity O(N).
    TreeSet<Integer> set = new TreeSet<>();
    int min = Integer.MAX_VALUE;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        if (!set.isEmpty()) {
            if (set.floor(root.val) != null) {
                min = Math.min(min, Math.abs(root.val - set.floor(root.val)));
            }
            if (set.ceiling(root.val) != null) {
                min = Math.min(min, Math.abs(root.val - set.ceiling(root.val)));
            }
        }
        
        set.add(root.val);
        
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        
        return min;
    }
中序遍历BST得到的序列是有序序列。因此创建一个vector存储中序遍历的每一个节点就得到一个有序序列，然后遍历这个有序序列，在遍历过程中求出两两之差并更新最小的绝对值之差就能得到结果。
http://www.liuchuo.net/archives/3714

    int getMinimumDifference(TreeNode* root) {

        inOrder(root);

        int result = INT_MAX;

        for (int i = 1; i < tree.size(); i++)

            result = min(result, tree[i] - tree[i-1]);

        return result;

    }

private:

    vector<int> tree;

    void inOrder(TreeNode* root) {

        if (root->left != NULL) inOrder(root->left);

        tree.push_back(root->val);

        if (root->right != NULL) inOrder(root->right); 

    }

};

X. https://discuss.leetcode.com/topic/80916/java-no-in-order-traverse-solution-just-pass-upper-bound-and-lower-bound
Make use of the property of BST that value of nodes is bounded by their "previous" and "next" node.
int minDiff = Integer.MAX_VALUE;
public int getMinimumDifference(TreeNode root) {
    helper(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    return minDiff;
}
private void helper(TreeNode curr, int lb, int rb){
    if(curr==null) return;
    if(lb!=Integer.MIN_VALUE){
        minDiff = Math.min(minDiff,curr.val - lb);
    }
    if(rb!=Integer.MAX_VALUE){
        minDiff = Math.min(minDiff,rb - curr.val);
    }
    helper(curr.left,lb,curr.val);
    helper(curr.right,curr.val,rb);
}
def getMinimumDifference(self, root):
    def mindiff(root, lo, hi):
        if not root:
            return float('inf')
        return min(root.val - lo,
                   hi - root.val,
                   mindiff(root.left, lo, root.val),
                   mindiff(root.right, root.val, hi))
    return mindiff(root, float('-inf'), float('inf'))
 * @author het
 *
 */
public class LeetCode530_Minimum_Absolute_Difference_in_BST {
	int minDiff = Integer.MAX_VALUE;
	public int getMinimumDifference(TreeNode root) {
	    helper(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
	    return minDiff;
	}
	private void helper(TreeNode curr, int lb, int rb){
	    if(curr==null) return;
	    if(lb!=Integer.MIN_VALUE){
	        minDiff = Math.min(minDiff,curr.val - lb);
	    }
	    if(rb!=Integer.MAX_VALUE){
	        minDiff = Math.min(minDiff,rb - curr.val);
	    }
	    helper(curr.left,lb,curr.val);
	    helper(curr.right,curr.val,rb);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
