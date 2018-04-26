package alite.leetcode.newExtra.L500;
/**
 * https://leetcode.com/problems/convert-bst-to-greater-tree
Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the 
original key plus sum of all keys greater than the original key in BST.
Example:
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13

Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
https://discuss.leetcode.com/topic/83455/java-recursive-o-n-time
Since this is a BST, we can do a reverse inorder traversal to traverse the nodes of the tree in descending order. In the process, we keep track of the running sum of all nodes which we have traversed thus far.
    int sum = 0;
    
    public TreeNode convertBST(TreeNode root) {
        convert(root);
        return root;
    }
    
    public void convert(TreeNode cur) {
        if (cur == null) return;
        convert(cur.right);
        cur.val += sum;
        sum = cur.val;
        convert(cur.left);
    }
Recursive version not using global variable.
public TreeNode convertBST(TreeNode root) {
    dfs(root, 0);
    return root;
}
public int dfs(TreeNode root, int val) {
    if(root == null) return val;
    int right = dfs(root.right, val);
    int left = dfs(root.left, root.val + right);
    root.val = root.val + right;
    return left;
}
    public TreeNode convertBST(TreeNode root) {
        if (root == null) return null;
        int[] rightVal = new int[1];
        reverseInorder(root, rightVal);
        return root;
    }
    
    private void reverseInorder(TreeNode root, int[] rightVal) {
        if (root == null) return;
        
        reverseInorder(root.right, rightVal);
        root.val = root.val + rightVal[0];
        rightVal[0] = root.val;
        reverseInorder(root.left, rightVal);
    }
https://discuss.leetcode.com/topic/83477/java-6-lines
    public TreeNode convertBST(TreeNode root) {
        if(root == null) return null;
        DFS(root, 0);
        return root;
    }
    
    public int DFS(TreeNode root, int preSum){
        if(root.right != null) preSum = DFS(root.right, preSum);
        root.val = root.val + preSum;
        return (root.left != null) ? DFS(root.left, root.val) : root.val;
    }
http://www.cnblogs.com/dongling/p/6579689.html
用"中序遍历"的方式访问每个节点的值，并将该节点的值累加到一个 int sum 变量上，并用该 sum 变量更新该节点的值。要注意的是，此处的中序遍历是先遍历右子树，再访问根节点，然后再遍历左子树（因为 BST 根节点的值小于右子树所有节点的值，大于左子树所有节点的值）。
    int sum=0;
    public TreeNode convertBST(TreeNode root) {
        sum=0;
        addSum(root);
        return root;
    }
    
    public void addSum(TreeNode root){
        if(root!=null){
            addSum(root.right);
            sum+=root.val;
            root.val=sum;
            addSum(root.left);
            
        }
    }
http://bookshadow.com/weblog/2017/03/19/leetcode-convert-bst-to-greater-tree/
“右 - 根 - 左”顺序遍历BST
    def convertBST(self, root):
        """
        :type root: TreeNode
        :rtype: TreeNode
        """
        self.total = 0
        def traverse(root):
            if not root: return
            traverse(root.right)
            root.val += self.total
            self.total = root.val
            traverse(root.left)
        traverse(root)
        return root
X.
    public TreeNode ConvertBST(TreeNode root) {
        TreeNode node = root;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        
        int sum = 0;
        while (node != null || stack.Count > 0)
        {
            if (node != null)
            {
                stack.Push(node);
                node = node.right;
            }
            else
            {
                node = stack.Pop();
                sum += node.val;
                node.val = sum;
                
                node = node.left;
            }
        }
        
        return root;
    }
    public static TreeNode convertBSTIt(TreeNode root){
        if(root==null) return root;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        boolean done = false;
        int sum = 0;
        while(!done){
            if(cur!=null){
                stack.push(cur);
                cur = cur.right;
            }else{
                if(stack.isEmpty()) done=true;
                else{//after the completion of right tree, processing the node after popping
                    cur = stack.pop();
                    cur.val += sum;
                    sum = cur.val;
                    cur = cur.left;
                }
            }
        }
        return root;
    }
 * @author het
 *
 */
public class LeetCode538_Convert_BST_to_Greater_Tree {
	 int sum = 0;
	    
	    public TreeNode convertBST(TreeNode root) {
	        convert(root);
	        return root;
	    }
	    
	    public void convert(TreeNode cur) {
	        if (cur == null) return;
	        convert(cur.right);
	        cur.val += sum;
	        sum = cur.val;
	        convert(cur.left);
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
