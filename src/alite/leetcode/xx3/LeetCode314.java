package alite.leetcode.xx3;
/**
 * LeetCode 314 Binary Tree Vertical Order Traversal

http://www.cnblogs.com/EdwardLiu/p/5093131.html
Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
If two nodes are in the same row and column, the order should be from left to right.
Given binary tree [3,9,20,4,5,2,7],
    _3_
   /   \
  9    20
 / \   / \
4   5 2   7
return its vertical order traversal as:
[
  [4],
  [9],
  [3,5,2],
  [20],
  [7]
]
https://leetcode.com/discuss/75054/5ms-java-clean-solution
public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if(root == null) return res;

    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    Queue<TreeNode> q = new LinkedList<>();
    Queue<Integer> cols = new LinkedList<>();

    q.add(root); 
    cols.add(0);

    int min = 0, max = 0;
    while(!q.isEmpty()) {
        TreeNode node = q.poll();
        int col = cols.poll();
        if(!map.containsKey(col)) map.put(col, new ArrayList<Integer>());
        map.get(col).add(node.val);

        if(node.left != null) {
            q.add(node.left); 
            cols.add(col - 1);
            if(col <= min) min = col - 1;
        }
        if(node.right != null) {
            q.add(node.right);
            cols.add(col + 1);
            if(col >= max) max = col + 1;
        }
    }

    for(int i = min; i <= max; i++) {
        res.add(map.get(i));
    }

    return res;
}
https://leetcode.com/discuss/73113/using-hashmap-bfs-java-solution
Only need track min, not max level.
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
       //map's key is column, we assume the root column is zero, the left node will minus 1 ,and the right node will plus 1
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        Queue<TreeNode> queue = new LinkedList<>();
       //use a HashMap to store the TreeNode and the according cloumn value
        HashMap<TreeNode, Integer> weight = new HashMap<TreeNode, Integer>();
        queue.offer(root);
        weight.put(root, 0);
        int min = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int w = weight.get(node);
            if (!map.containsKey(w)) {
                map.put(w, new ArrayList<>());
            }
            map.get(w).add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                weight.put(node.left, w - 1);
            } 
            if (node.right != null) {
                queue.add(node.right);
                weight.put(node.right, w + 1);
            }
            //update min ,min means the minimum column value, which is the left most node
            min = Math.min(min, w);
        }
        while (map.containsKey(min)) {
            res.add(map.get(min++));
        }
        return res;
    }

public int index = 0;
public TreeMap<Integer, List<Integer>> tm;

public class Pair {
    TreeNode node;
    int index;
    public Pair(TreeNode n, int i) {
        node = n;
        index = i;
    }
}

public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<List<Integer>>();
    tm = new TreeMap<Integer, List<Integer>>();
    if (root == null) return res;

    Queue<Pair> q = new LinkedList<Pair>();
    q.offer(new Pair(root, 0));

    while (!q.isEmpty()) {
        Pair cur = q.poll();
        if (!tm.containsKey(cur.index)) tm.put(cur.index, new ArrayList<Integer>());
        tm.get(cur.index).add(cur.node.val);

        if (cur.node.left != null) q.offer(new Pair(cur.node.left, cur.index-1));
        if (cur.node.right != null) q.offer(new Pair(cur.node.right, cur.index+1));
    }

    for (int key : tm.keySet()) res.add(tm.get(key));
    return res;
}
http://www.itdadao.com/article/48930/
https://leetcode.com/discuss/79821/java-bfs-concise-code-using-2-maps
用了treemap来维护左右关系，其实也可以不用，记录一个min的index就好。
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (root == null) {
            return result;
        }
        HashMap<TreeNode, Integer> map1 = new HashMap<TreeNode, Integer>();
        TreeMap<Integer, List<Integer>> map2 = new TreeMap<Integer, List<Integer>>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        map1.put(root, 0);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int index = map1.get(node);
                List<Integer> list = map2.containsKey(index) ? map2.get(index) : new ArrayList<Integer>();
                list.add(node.val);
                map2.put(index, list);
                if (node.left != null) {
                    queue.offer(node.left);
                    map1.put(node.left, index - 1);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    map1.put(node.right, index + 1);
                }
            }
        }
        Iterator<Integer> it = map2.keySet().iterator();
        while (it.hasNext()) {
            result.add(map2.get(it.next()));
        }
        return result;
    }
}
X. DFS
http://www.zrzahid.com/vertical-and-zigzag-traversal-of-binary-tree/
public static void verticalTrversal(BTNode root){
 int[] minmax = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
 Map<Integer, ArrayList<BTNode>> verticals = new HashMap<Integer, ArrayList<BTNode>>();
 traverse(root, verticals, 0, minmax);
 for(int i = minmax[0]; i<=minmax[1]; i++){
  if(verticals.containsKey(i)){
   for(BTNode vnode : verticals.get(i)){
    System.out.print(vnode.key+",");
   }
   System.out.println();
  }
 }
 
}

private static void traverse(BTNode node, Map<Integer, ArrayList<BTNode>> verticals, int score, int[] minmax){
 if(!verticals.containsKey(score)){
  verticals.put(score, new ArrayList<BTNode>());
 }
 
 verticals.get(score).add(node);
 minmax[0] = Math.min(minmax[0], score);
 minmax[1] = Math.max(minmax[1], score);
 
 if(node.left != null){
  traverse(node.left, verticals, score-1, minmax);
 }
 if(node.right != null){
  traverse(node.right, verticals, score+1, minmax);
 }
}
https://leetcode.com/discuss/77236/just-to-be-different-java-4-5-ms-95%25-dfs-solution
First I traverse the tree and determine the min, max indices as well as the depth of the tree.
I use these limits to construct a 2D array of size (max index - min index + 1). e.g. if min index = -2, and max index = 3, then the length of the array rows is 3 + 2 + 1(root).
The length of the array columns is determined by the depth (I call it 'height') of the tree.
I then create an empty ArrayList in each cell of our 2D array to handle the collisions. Note that I preallocate 2 slots of capacity for each ArrayList, since we can have at most 2 nodes competing for the same cell.
Now that all the memory is preallocated I traverse the tree the second time and fill in the cells of the 2D array. Note that some of the cells will contain empty arrayLists in the end, so this is not the most space efficient method.


public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    int[] minmax = new int[3];

    getEnds(root, 0, 1, minmax);

    ArrayList<Integer>[][] tree = new ArrayList[minmax[1] - minmax[0] + 1][minmax[2]];
    for (int i = 0; i < tree.length; i++)
        for (int j = 0; j < tree[0].length; j++)
            tree[i][j] = new ArrayList<Integer>(2);

    order(root, 0, -minmax[0], 0, tree); 

    for (int i = 0; i < tree.length; i++) {
        res.add(new ArrayList<Integer>());
        for (int j = 0; j < tree[0].length; j++) {
            if (!tree[i][j].isEmpty()) {
                res.get(i).addAll(tree[i][j]);
            }
        }
    }
    return res;
}

private void order(TreeNode root, int level, int l, int height, ArrayList<Integer>[][] tree) {
    if (root == null) return;
    tree[l + level][height].add(root.val);
    order(root.left, level-1, l, height + 1, tree);
    order(root.right, level+1, l, height + 1, tree);
}

private void getEnds(TreeNode root, int level, int height, int[] minmax) {
    if (root == null) return;
    minmax[0] = Math.min(level, minmax[0]);
    minmax[1] = Math.max(level, minmax[1]);
    minmax[2] = Math.max(height, minmax[2]);
    getEnds(root.left, level-1, height + 1, minmax);
    getEnds(root.right, level+1, height + 1, minmax);
}
https://leetcode.com/discuss/73108/level-order-traversal-wrapping-nodes-with-a-shift-index
 * @author het
 *
 */
public class LeetCode314 {
	public static void verticalTrversal(BTNode root){
		 int[] minmax = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
		 Map<Integer, ArrayList<BTNode>> verticals = new HashMap<Integer, ArrayList<BTNode>>();
		 traverse(root, verticals, 0, minmax);
		 for(int i = minmax[0]; i<=minmax[1]; i++){
		  if(verticals.containsKey(i)){
		   for(BTNode vnode : verticals.get(i)){
		    System.out.print(vnode.key+",");
		   }
		   System.out.println();
		  }
		 }
		 
		}

		private static void traverse(BTNode node, Map<Integer, ArrayList<BTNode>> verticals, int score, int[] minmax){
		 if(!verticals.containsKey(score)){
		  verticals.put(score, new ArrayList<BTNode>());
		 }
		 
		 verticals.get(score).add(node);
		 minmax[0] = Math.min(minmax[0], score);
		 minmax[1] = Math.max(minmax[1], score);
		 
		 if(node.left != null){
		  traverse(node.left, verticals, score-1, minmax);
		 }
		 if(node.right != null){
		  traverse(node.right, verticals, score+1, minmax);
		 }
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
