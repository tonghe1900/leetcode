package alite.leetcode.newest;
/**
 * LeetCode 662 - Maximum Width of Binary Tree


https://leetcode.com/problems/maximum-width-of-binary-tree
Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is the maximum width among all levels. The binary tree has the same structure as a full binary tree, but some nodes are null.
The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level, where the null nodes between the end-nodes are also counted into the length calculation.
Example 1:
Input: 

           1
         /   \
        3     2
       / \     \  
      5   3     9 

Output: 4
Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).

We know that a binary tree can be represented by an array (assume the root begins from the position with index 1 in the array). If the index of a node is i, the indices of its two children are 2*i and 2*i + 1. The idea is to use two arrays (start[] and end[]) to record the the indices of the leftmost node and rightmost node in each level, respectively. For each level of the tree, the width isend[level] - start[level] + 1. Then, we just need to find the maximum width.
Java version:
    public int widthOfBinaryTree(TreeNode root) {
        return dfs(root, 0, 1, new ArrayList<Integer>(), new ArrayList<Integer>());
    }
    
    public int dfs(TreeNode root, int level, int order, List<Integer> start, List<Integer> end){
        if(root == null)return 0;
        if(start.size() == level){
            start.add(order); end.add(order);
        }
        else end.set(level, order);
        int cur = end.get(level) - start.get(level) + 1;
        int left = dfs(root.left, level + 1, 2*order, start, end);
        int right = dfs(root.right, level + 1, 2*order + 1, start, end);
        return Math.max(cur, Math.max(left, right));
    }

https://discuss.leetcode.com/topic/100149/c-java-bfs-dfs-clean-code-with-explanation
The idea is to use heap indexing:
        1
   2         3
 4   5     6   7
8 9 x 11  x 13 x 15
Regardless whether these nodes exist:
* Always make the id of left child as parent_id * 2;
* Always make the id of right child as parent_id * 2 + 1;
So we can just:
1. Record the id of left most node at each level of the tree(you can tell be check the size of the container to hold the first nodes);
2. At each node, compare the distance from it the left most node with the current max width;
    public int widthOfBinaryTree(TreeNode root) {
        List<Integer> lefts = new ArrayList<Integer>(); // left most nodes at each level;
        int[] res = new int[1]; // max width
        dfs(root, 1, 0, lefts, res);
        return res[0];
    }
    private void dfs(TreeNode node, int id, int depth, List<Integer> lefts, int[] res) {
        if (node == null) return;
        if (depth >= lefts.size()) lefts.add(id);   // add left most node
        res[0] = Integer.max(res[0], id + 1 - lefts.get(depth));
        dfs(node.left,  id * 2,     depth + 1, lefts, res);
        dfs(node.right, id * 2 + 1, depth + 1, lefts, res);
    }
https://discuss.leetcode.com/topic/100128/java-solution-node-position-of-binary-tree
    Map<Integer, int[]> map = new HashMap<>();
    
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        
        findMax(root, 0, 0);
        
        int res = 1;
        for (int[] rec : map.values()) {
            res = Math.max(res, rec[1] - rec[0] + 1);
        }
        
        return res;
    }
    
    private void findMax(TreeNode root, int level, int pos) {
        if (root == null) return;
        
        int[] rec = map.get(level);
        if (rec == null) {
            rec = new int[2];
            rec[0] = Integer.MAX_VALUE;
            rec[1] = Integer.MIN_VALUE;
        }

        rec[0] = Math.min(rec[0], pos);
        rec[1] = Math.max(rec[1], pos);
        map.put(level, rec);
        
        findMax(root.left, level + 1, 2 * pos);
        findMax(root.right, level + 1, 2 * pos + 1);
    }

X. BFS
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int max = 0;
        Queue<Map.Entry<TreeNode, Integer>> q = new LinkedList<Map.Entry<TreeNode, Integer>>();
        q.offer(new AbstractMap.SimpleEntry(root, 1));

        while (!q.isEmpty()) {
            int l = q.peek().getValue(), r = l; // right started same as left
            for (int i = 0, n = q.size(); i < n; i++) {
                TreeNode node = q.peek().getKey();
                r = q.poll().getValue();
                if (node.left != null) q.offer(new AbstractMap.SimpleEntry(node.left, r * 2));
                if (node.right != null) q.offer(new AbstractMap.SimpleEntry(node.right, r * 2 + 1));
            }
            max = Math.max(max, r + 1 - l);
        }

        return maxwidth;
    }

 * @author het
 *
 */
public class LeetCode662 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
