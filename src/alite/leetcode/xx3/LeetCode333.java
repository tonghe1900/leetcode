package alite.leetcode.xx3;

import alite.leetcode.xx3.LeetCode315.TreeNode;

/**
 * LeetCode 333 - Largest BST Subtree

http://www.cnblogs.com/tonix/p/5187027.html
Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), 
where largest means subtree with largest number of nodes in it.
Note:
A subtree must include all of its descendants.
Here's an example:
    10
    / \
   5  15
  / \   \ 
 1   8   7
The Largest BST Subtree in this case is the highlighted one. 
The return value is the subtree's size, which is 3.
Hint:
You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, which will result in O(nlogn) time complexity.
Follow up:
Can you figure out ways to solve it with O(n) time complexity?



Also http://blog.csdn.net/likecool21/article/details/44080779
http://buttercola.blogspot.com/2016/02/leetcode-largest-bst-subtree.html

    private int max = 0;

    public int largestBSTSubtree(TreeNode root) {

        if (root == null) {

            return 0;

        }

         

        largestBSTHelper(root);

         

        return max;

    }

     

    private Data largestBSTHelper(TreeNode root) {

        Data curr = new Data();

        if (root == null) {

            curr.isBST = true;

            return curr;

        }

         

        Data left = largestBSTHelper(root.left);

        Data right = largestBSTHelper(root.right);

         

        curr.lower = Math.min(root.val, Math.min(left.lower, right.lower));

        curr.upper = Math.max(root.val, Math.max(left.upper, right.upper));

         

        if (left.isBST && root.val > left.upper && right.isBST && root.val < right.lower) {

            curr.size = left.size + right.size + 1;

            curr.isBST = true;

            max = Math.max(max, curr.size);

        } else {

            curr.size = 0;

        }

         

        return curr;

    } 

class Data {

    int size = 0;

    int lower = Integer.MAX_VALUE;

    int upper = Integer.MIN_VALUE;

    boolean isBST = false;

}

http://www.programcreek.com/2014/07/leetcode-largest-bst-subtree-java/
class Wrapper{
    int size;
    int lower, upper;
    boolean isBST;
 
    public Wrapper(){
        lower = Integer.MAX_VALUE;
        upper = Integer.MIN_VALUE;
        isBST = false;
        size = 0;
    }
} 
    public int largestBSTSubtree(TreeNode root) {
        return helper(root).size;
    }
 
    public Wrapper helper(TreeNode node){
        Wrapper curr = new Wrapper();
 
        if(node == null){
            curr.isBST= true;
            return curr;
        }
 
        Wrapper l = helper(node.left);
        Wrapper r = helper(node.right);
 
        //current subtree's boundaries
        curr.lower = Math.min(node.val, l.lower);
        curr.upper = Math.max(node.val, r.upper);
 
        //check left and right subtrees are BST or not
        //check left's upper again current's value and right's lower against current's value
        if(l.isBST && r.isBST && l.upper<=node.val && r.lower>=node.val){
            curr.size = l.size+r.size+1;
            curr.isBST = true;
        }else{
            curr.size = Math.max(l.size, r.size);
            curr.isBST  = false;
        }
 
        return curr;
    }
https://leetcode.com/discuss/86027/share-my-o-n-java-code-with-brief-explanation-and-comments
    class Result {
        int res;
        int min;
        int max;
        public Result(int res, int min, int max) {
            this.res = res;
            this.min = min;
            this.max = max;
        }
    }

    public int largestBSTSubtree(TreeNode root) {
        Result res = BSTSubstree(root);
        return Math.abs(res.res);
    }

    private Result BSTSubstree(TreeNode root) {
        if (root == null) return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        Result left = BSTSubstree(root.left);
        Result right = BSTSubstree(root.right);
        if (left.res < 0 || right.res < 0 || root.val < left.max || root.val > right.min) {
            return new Result(Math.max(Math.abs(left.res), Math.abs(right.res)) * -1, 0, 0);
        } else {
            return new Result(left.res + right.res + 1, Math.min(root.val, left.min), Math.max(root.val, right.max));
        }
    }
http://www.cnblogs.com/tonix/p/5187027.html

https://asanchina.wordpress.com/2016/02/14/333-largest-bst-subtree/


    int largestBSTSubtree(TreeNode* root) {

        int mmin;

        int maxx;

        int nodes = 0;

        helper(root, mmin, maxx, nodes);

        return nodes;

    }

    bool helper(TreeNode* root, int& mmin, int& maxx, int& nodes) {

        if (!root) {

            return true;

        }

        int lMin = INT_MAX;

        int lMax = INT_MIN;

        int lNode = 0;

        auto lValid = helper(root->left, lMin, lMax, lNode);

        int rMin = INT_MAX;

        int rMax = INT_MIN;

        int rNode = 0;

        auto rValid = helper(root->right, rMin, rMax, rNode);

        if (lValid == false || rValid == false || root->val <= lMax || root->val >= rMin) {

            nodes = max(lNode, rNode);

            return false;

        }

        mmin = min(lMin, root->val);

        maxx = max(rMax, root->val);

        nodes = lNode + rNode + 1;

        return true;

    }
http://www.geeksforgeeks.org/find-the-largest-subtree-in-a-tree-that-is-also-a-bst/
Time Complexity: The worst case time complexity of this method will be O(n^2). Consider a skewed tree for worst case analysis.

int largestBST(struct node *root)

{

   if (isBST(root))

     return size(root); 

   else

    return max(largestBST(root->left), largestBST(root->right));

}
https://leetcode.com/discuss/85914/o-nlogn-java-solution

The idea is pretty straight forward: we just recursively check if the subtree is valid BST or not, if it is, we just return the result. When we recursively check from the root, the first result returned would be the largest subtree. Still trying to figure out a O(n) solution.

try to recursive from button to up, main the size of each subtree, you can get O(n) solution
but that would require a parent point right? - NO

It's not O(nlogn) but O(n^2). For example for input [1,null,2,null,...,998,null,999,null,0], which is a tree with a thousand nodes, your helper function gets called over a million times.

        public int largestBSTSubtree(TreeNode root) {
            if(root == null) {
                return 0;
            }
            if(helper(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
                return nodeCount(root);
            }
            int left = largestBSTSubtree(root.left);
            int right = largestBSTSubtree(root.right);
            return Math.max(left, right);
        }
        
        private int nodeCount(TreeNode root) {
            if(root == null) {
                return 0;
            }
            return nodeCount(root.left) + nodeCount(root.right) + 1;
        }
        
        private boolean helper(TreeNode root, long left, long right) {
            if(root == null) {
                return true;
            }
            if(root.val <= left || root.val >= right) {
                return false;
            }
            return helper(root.left, left, root.val) && helper(root.right, root.val, right);
        }
    }
http://yuancrackcode.com/2016/02/23/largest-bst-subtree/
Use a class(DS), not

        //result[0]: is BST? result[2] : total number of nodes, result[3]: maximum value, result[4] minimum value

X. Brute Force
https://discuss.leetcode.com/topic/36995/share-my-o-n-java-code-with-brief-explanation-and-comments
    in brute-force solution, we get information in a top-down manner.
    for O(n) solution, we do it in bottom-up manner, meaning we collect information during backtracking.     
    class Result {  // (size, rangeLower, rangeUpper) -- size of current tree, range of current tree [rangeLower, rangeUpper]
        int size;
        int lower;
        int upper;
        
        Result(int size, int lower, int upper) {
            this.size = size;
            this.lower = lower;
            this.upper = upper;
        }
    }
    
    int max = 0;
    
    public int largestBSTSubtree(TreeNode root) {
        if (root == null) { return 0; }    
        traverse(root, null);
        return max;
    }
    
    private Result traverse(TreeNode root, TreeNode parent) {
        if (root == null) { return new Result(0, parent.val, parent.val); }
        Result left = traverse(root.left, root);
        Result right = traverse(root.right, root);
        if (left.size==-1 || right.size==-1 || root.val<left.upper || root.val>right.lower) {
            return new Result(-1, 0, 0);
        }
        int size = left.size + 1 + right.size;
        max = Math.max(size, max);
        return new Result(size, left.lower, right.upper);
    }
 * @author het
 *
 */
public class LeetCode333 {
	 private int max = 0;

	    public int largestBSTSubtree(TreeNode root) {

	        if (root == null) {

	            return 0;

	        }

	         

	        largestBSTHelper(root);

	         

	        return max;

	    }

	     

	    private Data largestBSTHelper(TreeNode root) {

	        Data curr = new Data();

	        if (root == null) {

	            curr.isBST = true;

	            return curr;

	        }

	         

	        Data left = largestBSTHelper(root.left);

	        Data right = largestBSTHelper(root.right);

	         

	        curr.lower = Math.min(root.val, Math.min(left.lower, right.lower));

	        curr.upper = Math.max(root.val, Math.max(left.upper, right.upper));

	         

	        if (left.isBST && root.val > left.upper && right.isBST && root.val < right.lower) {

	            curr.size = left.size + right.size + 1;

	            curr.isBST = true;

	            max = Math.max(max, curr.size);

	        } else {

	            curr.size = 0;

	        }

	         

	        return curr;

	    } 

	class Data {

	    int size = 0;

	    int lower = Integer.MAX_VALUE;

	    int upper = Integer.MIN_VALUE;

	    boolean isBST = false;

	}
	
	class Result{
	    int size;
	    boolean isBST;
	    int max;
	    int min;
	}

	public int maxBST(TreeNode treenode){
	    if(treenode == null) return 0;
	    if(treenode.left == null && treenode.right == null) return 1;
	    //int [] result = new int[1];
	    Result result =  maxBSTHelper(treenode );
	     return result.size;
	      
	}
	Result  maxBSTHelper(TreeNode treenode){
	    if(treenode == null) return new Result(0, true, Integer.MIN, Integer.MAX);
	    Result left = maxBSTHelper(treenode.left);
	     Result right = maxBSTHelper(treenode.right);
	     boolean isBST = (left.isBST && right.isBST && (left.max<= treenode.val && treenode.val < right.min));
	     int size = isBST? (left.size+right.size+1): Math.max(left.size+right.size);
	     return new Result(size, isBST, Math.max(treenode.val, right.max), Math.min(treenode.val, left.min));
	     
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
