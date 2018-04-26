package alite.leetcode.xx3.extra;
/**
 * LeetCode 373 - House Robber III

https://www.hrwhisper.me/leetcode-house-robber-iii/
The thief has found himself a new place for his thievery again. There is only one entrance to this area,
 called the "root." Besides the root, each house has one and only one parent house. After a tour, 
 the smart thief realized that "all houses in this place forms a binary tree". It will automatically 
 contact the police if two directly-linked houses were broken into on the same night.
Determine the maximum amount of money the thief can rob tonight without alerting the police.
Example 1:
     3
    / \
   2   3
    \   \ 
     3   1
Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
Example 2:
     3
    / \
   4   5
  / \   \ 
 1   3   1
Maximum amount of money the thief can rob = 4 + 5 = 9.

题意：给定一颗二叉树，求能获取的权值最大和（相邻的不能同时取）
思路: 树形dp
显然有：
rob_root = max(rob_L + rob_R , no_rob_L + no_nob_R + root.val)
no_rob_root = rob_L + rob_R
https://leetcode.com/discuss/91899/step-by-step-tackling-of-the-problem
Step I -- Think naively
At first glance, the problem exhibits the feature of "optimal substructure": if we want to "rob" maximum amount of money from current binary tree (rooted at "root"), we surely hope that we can do the same to its left and right subtrees.
So going along this line, let's define the function rob(root) which will return the maximum amount of money that we can rob for the binary tree rooted at "root"; the key now is to construct the solution to the original problem from solutions to its subproblems, i.e., how to get rob(root)from rob(root.left), rob(root.right), ... etc.
Apparently the analyses above suggest a recursive solution. And for recursion, it's always worthwhile to figure out the following two properties:
Termination condition: when do we know the answer to rob(root) without any calculation? Of course when the tree is empty -- we've got nothing to rob so the amount of money is zero.
Recurrence relation: i.e., how to get rob(root) from rob(root.left), rob(root.right), ... etc. From the point of view of the tree root, there are only two scenarios at the end: "root" is robbed or is not. If it is, due to the constraint that "we cannot rob any two directly-linked houses", the next level of subtrees that are available would be the four "grandchild-subtrees" (root.left.left, root.left.right, root.right.left, root.right.right). However if root is not robbed, the next level of available subtrees would just be the two "child-subtrees" (root.left, root.right). We only need to choose the scenario which yields the larger amount of money.
public int rob(TreeNode root) {
    if (root == null) {
        return 0;
    }
    
    int val = 0;
    
    if (root.left != null) {
        val += rob(root.left.left) + rob(root.left.right);
    }
    
    if (root.right != null) {
        val += rob(root.right.left) + rob(root.right.right);
    }
    
    return Math.max(val + root.val, rob(root.left) + rob(root.right));
}
Step II -- Think one step further
In step I, we only considered the aspect of "optimal substructure", but think little about the possibilities of overlapping of the subproblems. For example, to obtain rob(root), we needrob(root.left), rob(root.right), rob(root.left.left), rob(root.left.right), rob(root.right.left), rob(root.right.right); but to get rob(root.left), we also needrob(root.left.left), rob(root.left.right), similarly for rob(root.right). The naive solution above computed these subproblems repeatedly, which resulted in bad time performance. Now if you recall the two conditions for dynamic programming: "optimal substructure" + "overlapping of subproblems", we actually have a DP problem. A naive way to implement DP here is to use a hash map to record the results for visited subtrees.
public int rob(TreeNode root) {
    Map<TreeNode, Integer> map = new HashMap<>();
    return robSub(root, map);
}

private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
    if (root == null) return 0;
    if (map.containsKey(root)) return map.get(root);
    
    int val = 0;
    
    if (root.left != null) {
        val += robSub(root.left.left, map) + robSub(root.left.right, map);
    }
    
    if (root.right != null) {
        val += robSub(root.right.left, map) + robSub(root.right.right, map);
    }
    
    val = Math.max(val + root.val, robSub(root.left, map) + robSub(root.right, map));
    map.put(root, val);
    
    return val;
}
The runtime is sharply reduced to 9ms, at the expense of O(n) space cost (n is the total number of nodes; stack cost for recursion is not counted).
Step III -- Think one step back
In step I, we defined our problem as rob(root), which will yield the maximum amount of money that can be robbed of the binary tree rooted at "root". This leads to the DP problem summarized in step II.
Now let's take one step back and ask why do we have overlapping subproblems? If you trace all the way back to the beginning, you'll find the answer lies in the way how we have definedrob(root). As I mentioned, for each tree root, there are two scenarios: it is robbed or is not.rob(root) does not distinguish between these two cases, so "information is lost as the recursion goes deeper and deeper", which resulted in repeated subproblems.
If we were able to maintain the information about the two scenarios for each tree root, let's see how it plays out. Redefine rob(root) as a new function which will return an array of two elements, the first element of which denotes the maximum amount of money that can be robbed if "root" is not robbed, while the second element signifies the maximum amount of money robbed if root is robbed.
Let's relate rob(root) to rob(root.left) and rob(root.right), etc. For the 1st element ofrob(root), we only need to sum up the larger elements of rob(root.left) androb(root.right), respectively, since root is not robbed and we are free to rob the left and right subtrees. For the 2nd element of rob(root), however, we only need to add up the 1st elements of rob(root.left) and rob(root.right), respectively, plus the value robbed from "root" itself, since in this case it's guaranteed that we cannot rob the nodes of root.left and root.right.
As you can see, by keeping track of the information of both scenarios, we decoupled the subproblems and the solution essentially boiled down to a greedy one.
https://discuss.leetcode.com/topic/40301/2ms-java-ac-o-n-solution
https://discuss.leetcode.com/topic/39700/c-java-python-explanation
https://discuss.leetcode.com/topic/39659/easy-understanding-solution-with-dfs
public int rob(TreeNode root) {
 int[] res = robSub(root);
    return Math.max(res[0], res[1]);
}

private int[] robSub(TreeNode root) {
    if (root == null) {
     return new int[2];
    }
    
    int[] left = robSub(root.left);
    int[] right = robSub(root.right);
    
    int[] res = new int[2];
    res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    res[1] = root.val + left[0] + right[0];
    
    return res;
}
http://bookshadow.com/weblog/2016/03/13/leetcode-house-robber-iii/
记当前房间为root，如果偷窃当前房间，则其左右孩子left和right均不能偷窃；而其4个孙子节点（ll，lr，rl，rr）不受影响。
因此最大收益为：
maxBenifit = max(rob(left) + rob(right), root.val + rob(ll) + rob(lr) + rob(rl) + rob(rr))

使用字典valMap记录每一个访问过的节点，可以避免重复运算。
    def rob(self, root):
        """
        :type root: TreeNode
        :rtype: int
        """
        valMap = dict()
        def solve(root, path):
            if root is None: return 0
            if path not in valMap:
                left, right = root.left, root.right
                ll = lr = rl = rr = None
                if left:  ll, lr = left.left, left.right
                if right: rl, rr = right.left, right.right
                passup = solve(left, path + 'l') + solve(right, path + 'r')
                grabit = root.val + solve(ll, path + 'll') + solve(lr, path + 'lr') \
                         + solve(rl, path + 'rl') + solve(rr, path + 'rr')
                valMap[path] = max(passup, grabit)
            return valMap[path]
        return solve(root, '')

https://leetcode.com/discuss/91597/easy-understanding-solution-with-dfs

http://yaoxin.me/index.php/2016/03/13/337-house-robber-iii/
似乎一个返回值是解决不了问题的。需要2个。一个表示抢劫了当前结点，一个是表示没有抢劫当前结点。
dfs all the nodes of the tree, each node return two number, int[] num, num[0] is the max value while rob this node, num[1] is max value while not rob this value. Current node return value only depend on its children's value.
public int rob(TreeNode root) {
    int[] num = dfs(root);
    return Math.max(num[0], num[1]);
}
private int[] dfs(TreeNode x) {
    if (x == null) return new int[2];
    int[] left = dfs(x.left);
    int[] right = dfs(x.right);
    int[] res = new int[2];
    res[0] = left[1] + right[1] + x.val;
    res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    return res;
}
https://leetcode.com/discuss/91652/c-java-python-%26-explanation

    public int rob(TreeNode root) {

        return dfs(root)[0];

    }

    

    private int[] dfs(TreeNode root) {

        int dp[]={0,0};

        if(root != null){

            int[] dp_L = dfs(root.left);

            int[] dp_R = dfs(root.right);

            dp[1] = dp_L[0] + dp_R[0];

            dp[0] = Math.max(dp[1] ,dp_L[1] + dp_R[1] + root.val);

        }

        return dp;

    }
https://leetcode.com/discuss/91601/14ms-java-solution
http://www.cnblogs.com/chess/p/5271039.html
对于树的的前序遍历（根-左-右）；编写代码时，有效代码（处理函数）写在递归左子树之后，递归右子树之前；
中序遍历（左-根-右）用于二叉排序树的顺序输出，编写代码时，有效代码（处理函数）写在递归左右子树之前；
后序遍历（左-右-根），有效代码写在递归左右子树之后；
本题就是采用的后序遍历，对于每一个节点，先处理好其左右子树之后，再考虑本身节点的情况，非常符合实际
    int rob(TreeNode* root) {
        if(root==NULL)
           return 0;
        dfs(root);
        return max(dp1[root],dp2[root]);
    }
    void dfs(TreeNode* node){
        if(node==NULL)
           return;
        dfs(node->left);
        dfs(node->right);//实际干活的代码写在这两个递归之后，表示先处理一个节点的左右子树之后，才考虑本身节点（后序遍历处理手法）
        dp1[node]=dp2[node->left]+dp2[node->right]+node->val;//dp2[NULL]是等于0的，注意，只有key是指针类型才有效，本题满足条件
        dp2[node]=max(max(dp1[node->left]+dp1[node->right],dp2[node->left]+dp2[node->right]),max(dp1[node->left]+dp2[node->right],dp1[node->right]+dp2[node->left]));
    }
private:
      map<TreeNode*,int> dp1;
      map<TreeNode*,int> dp2;

https://discuss.leetcode.com/topic/39846/easy-to-understand-java
public int rob(TreeNode root) {
    if (root == null) return 0;
    return Math.max(robInclude(root), robExclude(root));
}

public int robInclude(TreeNode node) {
    if(node == null) return 0;
    return robExclude(node.left) + robExclude(node.right) + node.val;
}

public int robExclude(TreeNode node) {
    if(node == null) return 0;
    return rob(node.left) + rob(node.right);
}

http://siukwan.sinaapp.com/?p=1013
 * @author het
 *
 */
public class LeetCode373_ {
	public int rob(TreeNode root) {
	    Map<TreeNode, Integer> map = new HashMap<>();
	    return robSub(root, map);
	}

	private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
	    if (root == null) return 0;
	    if (map.containsKey(root)) return map.get(root);
	    
	    int val = 0;
	    
	    if (root.left != null) {
	        val += robSub(root.left.left, map) + robSub(root.left.right, map);
	    }
	    
	    if (root.right != null) {
	        val += robSub(root.right.left, map) + robSub(root.right.right, map);
	    }
	    
	    val = Math.max(val + root.val, robSub(root.left, map) + robSub(root.right, map));
	    map.put(root, val);
	    
	    return val;
	}
	public static void main(String[] args) {
		//// TODO Auto-generated method stub

	}

}
