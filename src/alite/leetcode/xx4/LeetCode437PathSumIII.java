package alite.leetcode.xx4;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/path-sum-iii/
You are given a binary tree in which each node contains an integer value.
Find the number of paths that sum to a given value.
The path does not need to start or end at the root or a leaf, but it must go 
downwards (traveling only from parent nodes to child nodes).
The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
Example:

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1



Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
https://discuss.leetcode.com/topic/64526/17-ms-o-n-java-prefix-sum-method
So the idea is similar as Two sum, using HashMap to store ( key : the prefix sum, value : how many ways get to this prefix sum) , and whenever reach a node, we check if prefix sum - target exists in hashmap or not, if it does, we added up the ways of prefix sum - target into res.
For instance : in one path we have 1,2,-1,-1,2, then the prefix sum will be: 1, 3, 2, 1, 3, let's say we want to find target sum is 2, then we will have {2}, {1, 2, -1, -1, 2}, { 2, -1, -1, 2} ways.
I used global variable count, but obviously we can avoid global variable by passing the count from bottom up. The time complexity is O(n).

why do you minus one in the last line of code?
preSum.put(sum, preSum.get(sum) - 1);
I think for a node, once you processed all of its child nodes, the number of ways to get to current prefix sum should not effect the rest of nodes, in case they have the same prefix sum.

    public int pathSum(TreeNode root, int sum) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  //Default sum = 0 has one count
        return backtrack(root, 0, sum, map); 
    }
    //BackTrack one pass
    public int backtrack(TreeNode root, int sum, int target, Map<Integer, Integer> map){
        if(root == null)
            return 0;
        sum += root.val;
        int res = map.getOrDefault(sum - target, 0);    //See if there is a subarray sum equals to target
        map.put(sum, map.getOrDefault(sum, 0)+1);
        //Extend to left and right child
        res += backtrack(root.left, sum, target, map) + backtrack(root.right, sum, target, map);
        map.put(sum, map.get(sum)-1); //\\Remove the current node so it wont affect other path
        return res;
    }
https://discuss.leetcode.com/topic/64450/dfs-java-solution
public int pathSum(TreeNode root, int sum) {
 return pathSumRec(root, sum, new ArrayList<Integer>()); 
}
 
public int pathSumRec(TreeNode node, int k, List<Integer> pathSums) {
 if(node==null) return 0;
 List<Integer> pathSumsLeft = new ArrayList<>();
 int pathsInLeft = pathSumRec(node.left, k, pathSumsLeft);
 List<Integer> pathSumsRight = new ArrayList<>();
 int pathsInRight = pathSumRec(node.right, k, pathSumsRight);
 
 int paths = 0;
 if(node.val==k) paths++;
 pathSums.add(node.val);
 
 for(int i: pathSumsLeft) {
  if(node.val+i==k) {
   paths++;
  }
  pathSums.add(node.val+i);
 }

 for(int i: pathSumsRight) {
  if(node.val+i==k) {
   paths++;
  }
  pathSums.add(node.val+i);
 }
 return paths+pathsInLeft+pathsInRight;
}


https://discuss.leetcode.com/topic/64415/easy-to-understand-java-solution-with-comment
I totally had the same idea with yours. But the my output was always greater than the expected answer. Now I understand that some child nodes have started several times. 
for each parent node in the tree, we have 2 choices:
1. include it in the path to reach sum.
2. not include it in the path to reach sum. 

for each child node in the tree, we have 2 choices:
1. take what your parent left you.
2. start from yourself to form the path.

one little thing to be careful:
every node in the tree can only try to be the start point once.

for example, When we try to start with node 1, node 3, as a child, could choose to start by itself.
             Later when we try to start with 2, node 3, still as a child, 
             could choose to start by itself again, but we don't want to add the count to result again.
     1
      \
       2
        \
         3
public class Solution {
    int target;
    Set<TreeNode> visited;
    public int pathSum(TreeNode root, int sum) {
        target = sum;
        visited = new HashSet<TreeNode>();  // to store the nodes that have already tried to start path by themselves.
        return pathSumHelper(root, sum, false);
    }
    
    public int pathSumHelper(TreeNode root, int sum, boolean hasParent) {
        if(root == null) return 0;
        //the hasParent flag is used to handle the case when parent path sum is 0.
        //in this case we still want to explore the current node.
        if(sum == target && visited.contains(root) && !hasParent) return 0;
        if(sum == target && !hasParent) visited.add(root);
        int count = (root.val == sum)?1:0;
        count += pathSumHelper(root.left, sum - root.val, true);
        count += pathSumHelper(root.right, sum - root.val, true);
        count += pathSumHelper(root.left, target , false);
        count += pathSumHelper(root.right, target, false);
        return count;
    }
}
http://bookshadow.com/weblog/2016/10/23/leetcode-path-sum-iii/
树的遍历，在遍历节点的同时，以经过的节点为根，寻找子树中和为sum的路径。
    def pathSum(self, root, sum):
        """
        :type root: TreeNode
        :type sum: int
        :rtype: int
        """
        def traverse(root, val):
            if not root: return 0
            res = (val == root.val)
            res += traverse(root.left, val - root.val)
            res += traverse(root.right, val - root.val)
            return res
        if not root: return 0
        ans = traverse(root, sum)
        ans += self.pathSum(root.left, sum)
        ans += self.pathSum(root.right, sum)
        return ans
http://blog.csdn.net/tc_to_top/article/details/52902003
题目分析：因为只能向下，因此直接DFS即可，用个flag标记当前点是否可作为某次的起点
    void DFS(TreeNode root, boolean flag, int cur, int sum, int[] ans) {  
        cur += root.val;  
        if (cur == sum) {  
            ans[0] ++;  
        }  
        if (root.left != null) {  
            DFS(root.left, false, cur, sum, ans);  
            if (flag) {  
                DFS(root.left, true, 0, sum, ans);  
            }  
        }  
        if (root.right != null) {  
            DFS(root.right, false, cur, sum, ans);  
            if (flag) {  
                DFS(root.right, true, 0, sum, ans);  
            }  
        }  
    }  
      
    public int pathSum(TreeNode root, int sum) {  
        if (root == null) {  
            return 0;  
        }  
        int[] ans = new int[1];  
        DFS(root, true, 0, sum, ans);  
        return ans[0];  
    }  

X.
https://discuss.leetcode.com/topic/64388/simple-ac-java-solution-dfs
https://discuss.leetcode.com/topic/64461/simplest-java-dfs-32ms
Each time find all the path start from current node
Then move start node to the child and repeat.
If the tree is balanced, then each node is reached from its ancestors (+ itself) only, which are up to log n. Thus, the time complexity for a balanced tree is O (n * log n).
However, in the worst-case scenario where the binary tree has the same structure as a linked list, the time complexity is indeed O (n ^ 2).
    public int pathSum(TreeNode root, int sum) {
        if(root == null)
            return 0;
        return findPath(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }
    
    public int findPath(TreeNode root, int sum){
        int res = 0;
        if(root == null)
            return res;
        if(sum == root.val)
            res++;
        res += findPath(root.left, sum - root.val);
        res += findPath(root.right, sum - root.val);
        return res;
    }
 * @author het
 *
 *
 *root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1



Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
 */

class TreeNode{
	TreeNode left;
	TreeNode right;
	int val;
	public TreeNode(int val){//P
	 this.val = val;
	}
}
public class LeetCode437PathSumIII {
    public int numOfSum(TreeNode root, int target){
    	int [] result  =new int[1];
    	if(root == null) return result[0];
    	List<Integer> list = new ArrayList<>();
    	numOfSum(result, root, list, 0, target);
    	return result[0];
    }
    
    public int numOfSumFromRoot(TreeNode root, int target){
    	int [] result  =new int[1];
    	
    	if(root == null) return result[0];
    	numOfSumFromRoot(result, root, target, 0);
    	return result[0];
    }
	private void numOfSumFromRoot(int[] result, TreeNode root, int target, int currentSum) {
		if(root == null){
			return;
		}
		currentSum+= root.val;
		if(currentSum == target){
			result[0]+=1;
		}
		numOfSumFromRoot(result, root.left, target, currentSum);
		numOfSumFromRoot(result, root.right, target, currentSum);
		currentSum -= root.val;
		
	}

	private void numOfSum(int[] result, TreeNode root, List<Integer> list, int level, int target) {
		if(root == null) return;
		if(level == list.size()){
			list.add(root.val);
		}else{
			list.set(level, root.val);
		}
		int sum = 0;
	    for(int i = level ; i>=0; i-=1){
	    	sum+= list.get(i);
	    	if(sum == target){
	    		result[0]++;
	    	}
	    }
		numOfSum(result, root.left, list, level+1, target);
		numOfSum(result, root.right, list, level+1, target);
		list.set(level, null);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
