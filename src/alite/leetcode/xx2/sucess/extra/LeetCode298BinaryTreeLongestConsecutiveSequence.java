package alite.leetcode.xx2.sucess.extra;
/**
 * http://shibaili.blogspot.com/2015/10/day-131-288-unique-word-abbreviation.html
Given a binary tree, find the length of the longest consecutive sequence path.
The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).
For example,
   1
    \
     3
    / \
   2   4
        \
         5
Longest consecutive sequence path is 3-4-5, so return 3.
   2
    \
     3
    / 
   2    
  / 
 1
Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
X. PreOrder Java
Simple Recursive DFS without global variable
https://discuss.leetcode.com/topic/29205/simple-recursive-dfs-without-global-variable
    public int longestConsecutive(TreeNode root) {
        return (root==null)?0:Math.max(dfs(root.left, 1, root.val), dfs(root.right, 1, root.val));
    }
    
    public int dfs(TreeNode root, int count, int val){
        if(root==null) return count;
        count = (root.val - val == 1)?count+1:1;
        int left = dfs(root.left, count, root.val);
        int right = dfs(root.right, count, root.val);
        return Math.max(Math.max(left, right), count);
    }
https://discuss.leetcode.com/topic/50962/concise-java-recursive-solution-without-static-variable
    public int longestConsecutive(TreeNode root) {
        if(root == null)    return 0;
        return helper(root, 0, root.val);
    }
    
    // currLen is the length of longest consecutive path ending with node n's parent
    private int helper(TreeNode n, int currLen, int expect) {
        if (n == null)  return 0;
        currLen = n.val == expect ? currLen+1 : 1;
        return Math.max(currLen,
                        Math.max(helper(n.left, currLen, n.val+1), helper(n.right, currLen, n.val+1)));
    }
http://www.chenguanghe.com/binary-tree-longest-consecutive-sequence/

private int result = Integer.MIN_VALUE;

    public int longestConsecutive(TreeNode root) {

        if(root == null)

            return 0;

        dfs(root, 0, root);

        return result;

    }

    private void dfs(TreeNode root, int cur, TreeNode pre) {

        if(root == null)

            return;

        if(root.val == pre.val+1)

            cur++;

        else

            cur = 1;

        result = Math.max(result, cur);

        dfs(root.left, cur, root);

        dfs(root.right, cur, root);

    }
https://leetcode.com/discuss/66584/easy-java-dfs-is-there-better-time-complexity-solution

public int longestConsecutive(TreeNode root) {

    int[] ans = new int[1];

    dfs(root, 0, 0, ans);

    return ans[0];

}

public void dfs(TreeNode root, int length, int target, int[] ans) {

    if (root == null) return;

    length = root.val == target ? length + 1 : 1;

    dfs(root.left, length, root.val + 1, ans);

    dfs(root.right, length, root.val + 1, ans);

    ans[0] = Math.max(length, ans[0]);

}
Python Iterative Depth-First Search
http://algobox.org/binary-tree-longest-consecutive-sequence/
def longestConsecutive(self, root):
    if not root:
        return 0
    ans, stack = 0, [[root, 1]]
    while stack:
        node, length = stack.pop()
        ans = max(ans, length)
        for child in [node.left, node.right]:
            if child:
                l = length + 1 if child.val == node.val + 1 else 1
                stack.append([child, l])
    return ans
http://www.cnblogs.com/jcliBlogger/p/4923745.html
12     int longestConsecutive(TreeNode* root) {
13         return longest(root, NULL, 0);
14     }
16     int longest(TreeNode* now, TreeNode* parent, int len) {
17         if (!now) return len;
18         len = (parent && now->val == parent->val + 1) ? len + 1 : 1;
19         return max(len, max(longest(now->left, now, len), longest(now->right, now, len)));
20     }
http://segmentfault.com/a/1190000003957798
https://leetcode.com/discuss/68723/simple-recursive-dfs-without-global-variable
因为要找最长的连续路径，我们在遍历树的时候需要两个信息，一是目前连起来的路径有多长，二是目前路径的上一个节点的值。我们通过递归把这些信息代入，然后通过返回值返回一个最大的就行了。这种需要遍历二叉树，然后又需要之前信息的题目思路都差不多，比如Maximum Depth of Binary Tree和Binary Tree Maximum Path Sum。
    public int longestConsecutive(TreeNode root) {
        if(root == null){
            return 0;
        }
        return findLongest(root, 0, root.val - 1);
    }
    
    private int findLongest(TreeNode root, int length, int preVal){
        if(root == null){
            return length;
        }
        // 判断当前是否连续
        int currLen = preVal + 1 == root.val ? length + 1 : 1;
        // 返回当前长度，左子树长度，和右子树长度中较大的那个
        return Math.max(currLen, Math.max(findLongest(root.left, currLen, root.val), findLongest(root.right, currLen, root.val)));  
    }
X. BFS
http://algobox.org/binary-tree-longest-consecutive-sequence/
https://discuss.leetcode.com/topic/28269/two-simple-iterative-solutions-bfs-and-dfs
Every node is attached with a property “length” when pushed into the queue. It is the length of longest consecutive sequence end with that node. The answer is then the max of all popped lengths. The only difference of the two solutions is the data structure. BFS using deque and DFS using stack.

from collections import deque

def longestConsecutive(self, root):

    if not root:

        return 0

    ans, dq = 0, deque([[root, 1]])

    while dq:

        node, length = dq.popleft()

        ans = max(ans, length)

        for child in [node.left, node.right]:

            if child:

                l = length + 1 if child.val == node.val + 1 else 1

                dq.append([child, l])

    return ans
Related:
题目： find Longest Consective Increasing Sequence in Binary Tree from root
public class LICSinBinaryTree {
    public int getLICSinBT(TreeNode root) {
        if(root == null) {
            return 0;
        }
        List<Integer> list = new ArrayList<Integer>();
        list.add(Integer.MIN_VALUE);
        helper(root, root.left, 1, list);
        helper(root, root.right, 1, list);
        return list.get(0);
    }
  
    public void helper(TreeNode pre, TreeNode cur, int num, List<Integer> list) {
        if(cur == null || cur.val <= pre.val) {
            if(num > list.get(0)) {
                list.add(0, num);
            }
            return;
        }
        helper(cur, cur.left, num+1, list);
        helper(cur, cur.right, num+1, list);
    }
}

Follow up
http://www.1point3acres.com/bbs/thread-210141-1-1.html
一些空间放不下的follow up
 * @author het
 *
 */
public class LeetCode298BinaryTreeLongestConsecutiveSequence {
	 public int longestConsecutive(TreeNode root) {
	        return (root==null)?0:Math.max(dfs(root.left, 1, root.val), dfs(root.right, 1, root.val));
	    }
	    
	    public int dfs(TreeNode root, int count, int val){
	        if(root==null) return count;
	        count = (root.val - val == 1)?count+1:1;
	        int left = dfs(root.left, count, root.val);
	        int right = dfs(root.right, count, root.val);
	        return Math.max(Math.max(left, right), count);
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
