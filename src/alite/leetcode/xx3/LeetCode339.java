package alite.leetcode.xx3;
/**
 * LeetCode 339 - Nested List Weight Sum

http://www.cnblogs.com/grandyang/p/5340305.html
-- Same as LinkedIn: Nested Integer
Reversed Nested Integer - Linkedin
Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
Each element is either an integer, or a list -- whose elements may also be integers or other lists.
Example 1:
Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
Example 2:
Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
    int depthSum(vector<NestedInteger>& nestedList) {
        return helper(nestedList, 1);
    }
    int helper(vector<NestedInteger>& nl, int depth) {
        int res = 0;
        for (auto a : nl) {
            res += a.isInteger() ? a.getInteger() * depth : helper(a.getList(), depth + 1);
        }
        return res;
    }
https://leetcode.com/discuss/94956/2ms-easy-to-understand-java-solution
public int depthSum(List<NestedInteger> nestedList) {
    return helper(nestedList, 1);
}

private int helper(List<NestedInteger> list, int depth)
{
    int ret = 0;
    for (NestedInteger e: list)
    {
        ret += e.isInteger()? e.getInteger() * depth: helper(e.getList(), depth + 1);
    }
    return ret;
}
X. BFS
https://leetcode.com/discuss/95207/java-solution-similar-to-tree-level-order-traversal
public int depthSum(List<NestedInteger> nestedList) {
    if(nestedList == null){
        return 0;
    }

    int sum = 0;
    int level = 1;

    Queue<NestedInteger> queue = new LinkedList<NestedInteger>(nestedList);
    while(queue.size() > 0){
        int size = queue.size();

        for(int i = 0; i < size; i++){
            NestedInteger ni = queue.poll();

            if(ni.isInteger()){
                sum += ni.getInteger() * level;
            }else{
                queue.addAll(ni.getList());
            }
        }

        level++;
    }

    return sum;
}

http://coderchen.blogspot.com/2015/11/getweight-recurseive-structure.html
1 given [1, [2,3], [[4]]], return sum. 计算sum的方法是每向下一个level权重+1， 例子的sum = 1 * 1 + (2 + 3) * 2 + 4 * 3。follow up：每向下一个level 权重 - 1， sum = 3 * 1 +（2 + 3）* 2 + 4 * 1 
public static class Node {}
public static class ValNode extends Node {
 int val;
 public ValNode(int v) {
  this.val = v;
 }
}

public static class ListNode extends Node {
 List<Node> nodes;
 public ListNode() {
  nodes = new ArrayList<Node>();
 }

 public void addNode(Node node) {
  this.nodes.add(node);
 }
}

public static int getWeightIncrease(Node node, int weight) {
 int res = 0;
 if (node instanceof ValNode) {
  res += weight * ((ValNode) node).val;
 } else {
  ListNode list = (ListNode) node;
  for (Node child : list.nodes) {
   res += getWeightIncrease(child, weight + 1);
  }
 }
 return res;
}
 * @author het
 *
 */
public class LeetCode339 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
