package alite.leetcode.xx3.extra;
/**
 * LeetCode 366 - Find Leaves of Binary Tree

http://www.cnblogs.com/grandyang/p/5616158.html
Given a binary tree, find all leaves and then remove those leaves. Then repeat the previous steps until the tree is empty.
Example:
Given binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Returns [4, 5, 3], [2], [1].


This problem does not actually ask you to remove leaves.
Originally, it did.
https://discuss.leetcode.com/topic/49206/java-backtracking-o-n-time-o-n-space-no-hashing
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> list = new ArrayList<>();
            findLeavesHelper(list, root);
            return list;
        }
        
  // return the level of root
        private int findLeavesHelper(List<List<Integer>> list, TreeNode root) {
            if (root == null) {
                return -1;
            }
            int leftLevel = findLeavesHelper(list, root.left);
            int rightLevel = findLeavesHelper(list, root.right);
            int level = Math.max(leftLevel, rightLevel) + 1;
            if (list.size() == level) {
                list.add(new ArrayList<>());
            }
            list.get(level).add(root.val);
            root.left = root.right = null;
            return level;
        }

这道题给了我们一个二叉树，让我们返回其每层的叶节点，就像剥洋葱一样，将这个二叉树一层一层剥掉，最后一个剥掉根节点。那么题目中提示说要用DFS来做，思路是这样的，每一个节点从左子节点和右子节点分开走可以得到两个深度，由于成为叶节点的条件是左右子节点都为空，所以我们取左右子节点中较大值加1为当前节点的深度值，知道了深度值就可以将节点值加入到结果res中的正确位置了，求深度的方法我们可以参见Maximum Depth of Binary Tree中求最大深度的方法
    vector<vector<int>> findLeaves(TreeNode* root) {
        vector<vector<int>> res;
        helper(root, res);
        return res;
    }
    int helper(TreeNode *root, vector<vector<int>> &res) {
        if (!root) return -1;
        int depth = 1 + max(helper(root->left, res), helper(root->right, res));
        if (depth >= res.size()) res.resize(depth + 1);
        res[depth].push_back(root->val);
        return depth;
    }
http://www.programcreek.com/2014/07/leetcode-find-leaves-of-binary-tree-java/
public List<List<Integer>> findLeaves(TreeNode root) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, root);
    return result;
}
 
// traverse the tree bottom-up recursively
private int helper(List<List<Integer>> list, TreeNode root){
    if(root==null)
        return -1;
 
    int left = helper(list, root.left);
    int right = helper(list, root.right);
    int curr = Math.max(left, right)+1;
 
    // the first time this code is reached is when curr==0,
    //since the tree is bottom-up processed.
    if(list.size()<=curr){
        list.add(new ArrayList<Integer>());
    }
 
    list.get(curr).add(root.val);
 
    return curr;
}

https://segmentfault.com/a/1190000005938045
if (list.size() == cur) {
    list.add(new ArrayList<Integer>());
}
这段代码为什么可以这么写？
因为通过跟踪递归可以发现，他的index都是连续的，没有跳跃写的情况，一般是这样：先0，再1，再2，再0，再1，再2，再3；不会出现先0，再2，再1，再4这样的情况
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        helper(list, root);
        return list;
    }

//calculate the index of this root passed in and put it in that index, at last return where this root was put
    private int helper(List<List<Integer>> list, TreeNode root) {
        if (root == null)
            return -1;
        int left = helper(list, root.left);
        int right = helper(list, root.right);
        int cur = Math.max(left, right) + 1;
        if (list.size() == cur)
            list.add(new ArrayList<Integer>());
        list.get(cur).add(root.val);
        return cur;
    }
https://leetcode.com/discuss/110389/12-lines-simple-java-solution-using-recursion
https://discuss.leetcode.com/topic/49206/java-backtracking-o-n-time-o-n-space-no-hashing
The essential of problem is not to find the leaves, but group leaves of same level together and also to cut the tree. This is the exact role backtracking plays. The helper function returns the level which is the distance from its furthest subtree leaf to root, which helps to identify which group the root belongs to
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> list = new ArrayList<>();
            findLeavesHelper(list, root);
            return list;
        }
        
  // return the level of root
        private int findLeavesHelper(List<List<Integer>> list, TreeNode root) {
            if (root == null) {
                return -1;
            }
            int leftLevel = findLeavesHelper(list, root.left);
            int rightLevel = findLeavesHelper(list, root.right);
            int level = Math.max(leftLevel, rightLevel) + 1;
            if (list.size() == level) {
                list.add(new ArrayList<>());
            }
            list.get(level).add(root.val);
            root.left = root.right = null;
            return level;
        }

For this question we need to take bottom-up approach. The key is to find the height of each node. Here the definition of height is:
The height of a node is the number of edges from the node to the deepest leaf. --CMU 15-121 Binary Trees
I used a helper function to return the height of current node. According to the definition, the height of leaf is 0. h(node) = 1 + max(h(node.left), h(node.right)).
The height of a node is also the its index in the result list (res). For example, leaves, whose heights are 0, are stored in res[0]. Once we find the height of a node, we can put it directly into the result.
UPDATE:
Thanks @adrianliu0729 for pointing out that my previous code does not actually remove leaves. I added one line node.left = node.right = null; to remove visited nodes
UPDATE:
There seems to be some debate over whether we need to actually "remove" leaves from the input tree. Anyway, it is just a matter of one line code. In the actual interview, just confirm with the interviewer whether removal is required.

 Only real difference is that my extension criterion is like level == res.size(), checking equality. I think the < makes it look likeres.size() could actually be smaller than level, and I don't like giving that impression.
def findLeaves(self, root):
    def dfs(node):
        if not node:
            return -1
        i = 1 + max(dfs(node.left), dfs(node.right))
        if i == len(out):
            out.append([])
        out[i].append(node.val)
        return i
    out = []
    dfs(root)
    return out
https://discuss.leetcode.com/topic/51476/no-tree-modification-no-hashing-o-n-time-o-h-space
https://leetcode.com/discuss/110417/java-backtracking-o-n-time-o-n-space-no-hashing
The essential of problem is not to find the leaves, but group leaves of same level together and also to cut the tree. This is the exact role backtracking plays. The helper function returns the level which is the distance from its furthest subtree leaf to root, which helps to identify which group the root belongs to
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> list = new ArrayList<>();
            findLeavesHelper(list, root);
            return list;
        }

  // return the level of root
        private int findLeavesHelper(List<List<Integer>> list, TreeNode root) {
            if (root == null) {
                return -1;
            }
            int leftLevel = findLeavesHelper(list, root.left);
            int rightLevel = findLeavesHelper(list, root.right);
            int level = Math.max(leftLevel, rightLevel) + 1;
            if (list.size() == level) {
                list.add(new ArrayList<>());
            }
            list.get(level).add(root.val);
            root.left = root.right = null;
            return level;
        }
https://discuss.leetcode.com/topic/49663/simple-java-dfs-solution
 public List<List<Integer>> findLeaves(TreeNode root) {
    List<List<Integer>> ans = new ArrayList<>();
    dfs(root, ans);
    return ans;
}
public int dfs(TreeNode root, List<List<Integer>> ans) {
    if (root == null) {
        return 0;
    }
    int up_cnt = Math.max(dfs(root.left, ans), dfs(root.right, ans));
    while (ans.size() <= up_cnt) {
        List<Integer> list = new ArrayList<>();
        ans.add(list);
    } 
    ans.get(up_cnt).add(root.val);
    return up_cnt + 1;
}
X. https://leetcode.com/discuss/110406/simple-java-recursive-1ms-solution
This is pretty straight forward but the general idea is to simply prune the leaves at each iteration of the while loop until the root itself is pruned. We can do this using the x = change(x) paradigm for modifying a tree. Whenever we come across a leaf node, we know we must add it to our result but then we prune it by just returning null.
Clean solution. In worst cases, it can be O(N^2) right?
    private TreeNode removeLeaves(TreeNode root, List<Integer> result)
    {
        if (root == null) return null;
        if (root.left == null && root.right == null)
        {
            result.add(root.val);
            return null;
        }

        root.left = removeLeaves(root.left, result);
        root.right = removeLeaves(root.right, result);
        return root;
    }

    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (root == null) return results;

        while (root != null)
        {
            List<Integer> leaves = new ArrayList<Integer>();
            root = removeLeaves(root, leaves);
            results.add(leaves);
        }

        return results;
    }
https://discuss.leetcode.com/topic/49400/1-ms-easy-understand-java-solution
    public List<List<Integer>> findLeaves(TreeNode root) {
        
        List<List<Integer>> leavesList = new ArrayList< List<Integer>>();
        List<Integer> leaves = new ArrayList<Integer>();
        
        while(root != null) {
            if(isLeave(root, leaves)) root = null;
            leavesList.add(leaves);
            leaves = new ArrayList<Integer>();
        }
        return leavesList;
    }
    
    public boolean isLeave(TreeNode node, List<Integer> leaves) {
        
        if (node.left == null && node.right == null) {
            leaves.add(node.val);
            return true;
        }
        
        if (node.left != null) {
             if(isLeave(node.left, leaves))  node.left = null;
        }
        
        if (node.right != null) {
             if(isLeave(node.right, leaves)) node.right = null;
        }
        
        return false;
    }
下面这种DFS方法没有用计算深度的方法，而是使用了一层层剥离的方法，思路是遍历二叉树，找到叶节点，将其赋值为NULL，然后加入leaves数组中，这样一层层剥洋葱般的就可以得到最终结果了：
    vector<vector<int>> findLeaves(TreeNode* root) {
        vector<vector<int>> res;
        while (root) {
            vector<int> leaves;
            root = remove(root, leaves);
            res.push_back(leaves);
        }
        return res;
    }
    TreeNode* remove(TreeNode *node, vector<int> &leaves) {
        if (!node) return NULL;
        if (!node->left && !node->right) {
            leaves.push_back(node->val);
            return NULL;
        }
        node->left = remove(node->left, leaves);
        node->right = remove(node->right, leaves);
        return node;
    }
https://discuss.leetcode.com/topic/49205/java-use-map-straight
    public List<List<Integer>> findLeaves(TreeNode root) {
        HashMap<Integer, List<Integer>> m = new HashMap<>();
        List<List<Integer>> res = new LinkedList<>();
        int max = height(root, m);
        
        for(int i = 1; i<max; i++){
            if(m.containsKey(i))
                res.add(m.get(i));
        }
        return res;
    }
    
    public int height(TreeNode root, HashMap<Integer, List<Integer>> m){
        if(root == null) return 0;
        int max = Math.max(height(root.left, m), height(root.right, m)) + 1;
        if(m.containsKey(max))
            m.get(max).add(root.val); 
        else{
            LinkedList<Integer> l = new LinkedList<>();
            l.add(root.val);
            m.put(max, l);
        }
        return max+1;
    }
https://discuss.leetcode.com/topic/49204/how-can-i-remove-the-root/
If you mean to make the root collectable, probably it will not happen as long as the caller still holds the reference ?
 * @author het
 *
 */
public class LeetCode366 {
	public List<List<Integer>> findLeaves(TreeNode root) {
	    List<List<Integer>> result = new ArrayList<List<Integer>>();
	    helper(result, root);
	    return result;
	}
	 
	// traverse the tree bottom-up recursively
	private int helper(List<List<Integer>> list, TreeNode root){
	    if(root==null)
	        return -1;
	 
	    int left = helper(list, root.left);
	    int right = helper(list, root.right);
	    int curr = Math.max(left, right)+1;
	 
	    // the first time this code is reached is when curr==0,
	    //since the tree is bottom-up processed.
	    if(list.size()<=curr){
	        list.add(new ArrayList<Integer>());
	    }
	 
	    list.get(curr).add(root.val);
	 
	    return curr;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
