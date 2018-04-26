package alite.leetcode.newest;
/**
 * LeetCode 663 - Equal Tree Partition


http://bookshadow.com/weblog/2017/08/21/leetcode-equal-tree-partition/
Given a binary tree with n nodes, your task is to check if it's possible to partition the tree to two trees which have the equal sum of values after removing exactly one edge on the original tree.
Example 1:
Input:     
    5
   / \
  10 10
    /  \
   2   3

Output: True
Explanation: 
    5
   / 
  10
      
Sum: 15

   10
  /  \
 2    3

Sum: 15
https://discuss.leetcode.com/topic/100179/java-c-simple-solution-with-only-one-hashmap
The idea is to use a hash table to record all the different sums of each subtree in the tree. If the total sum of the tree is sum, we just need to check if the hash table constains sum/2.
The following code has the correct result at a special case when the tree is [0,-1,1], which many solutions dismiss. I think this test case should be added.
    public boolean checkEqualTree(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = getsum(root, map);
        if(sum == 0)return map.getOrDefault(sum, 0) > 1;
        return sum%2 == 0 && map.containsKey(sum/2);
    }
    
    public int getsum(TreeNode root, Map<Integer, Integer> map ){
        if(root == null)return 0;
        int cur = root.val + getsum(root.left, map) + getsum(root.right, map);
        map.put(cur, map.getOrDefault(cur,0) + 1);
        return cur;
    }
http://blog.csdn.net/TheSnowBoy_2/article/details/77448762
    public boolean checkEqualTree(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = getsum(root, map);
        if(sum == 0)return map.getOrDefault(sum, 0) > 1;
        return sum%2 == 0 && map.containsKey(sum/2);
    }
    
    public int getsum(TreeNode root, Map<Integer, Integer> map ){
        if(root == null)return 0;
        int cur = root.val + getsum(root.left, map) + getsum(root.right, map);
        map.put(cur, map.getOrDefault(cur,0) + 1);
        return cur;
    }
https://discuss.leetcode.com/topic/100126/java-solution-tree-traversal-and-sum
    boolean equal = false;
    long total = 0;
    
    public boolean checkEqualTree(TreeNode root) {
        if (root.left == null && root.right == null) return false;
        total = getTotal(root);
        checkEqual(root);
        return equal;
    }
    
    private long getTotal(TreeNode root) {
        if (root == null) return 0;
        return getTotal(root.left) + getTotal(root.right) + root.val;
    }
    
    private long checkEqual(TreeNode root) {
        if (root == null || equal) return 0;
        
        long curSum = checkEqual(root.left) + checkEqual(root.right) + root.val;
        if (total - curSum == curSum) {
            equal = true;
            return 0;
        }
        return curSum;
    }

 * @author het
 *
 */
public class LeetCode663 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
