package alite.leetcode.xx2.sucess;
/**
 * Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.
Note:
Given target value is a floating point.
You may assume k is always valid, that is: k ≤ total nodes.
You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
Follow up:
Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
Hint:

Consider implement these two helper functions:
getPredecessor(N), which returns the next smaller node to N.
getSuccessor(N), which returns the next larger node to N.
Try to assume that each node has a parent pointer, it makes the problem much easier.
Without parent pointer we just need to keep track of the path from the root to the current node using a stack.
You would need two stacks to track the path in finding predecessor and successor node separately.




LeetCode [272] Closest Binary Search Tree Value II

http://buttercola.blogspot.com/2015/09/leetcode-closest-binary-search-tree_8.html
LIKE CODING: LeetCode [272] Closest Binary Search Tree Value II
Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.
Note:
Given target value is a floating point.
You may assume k is always valid, that is: k ≤ total nodes.
You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
Follow up:
Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
Hint:

Consider implement these two helper functions:
getPredecessor(N), which returns the next smaller node to N.
getSuccessor(N), which returns the next larger node to N.
Try to assume that each node has a parent pointer, it makes the problem much easier.
Without parent pointer we just need to keep track of the path from the root to the current node using a stack.
You would need two stacks to track the path in finding predecessor and successor node separately.
http://likesky3.iteye.com/blog/2239868
中序遍历结果是将树中元素从小到大排列，逆式的中序遍历即先遍历右子树再访问根节点最后遍历左子树会得到树中元素从大到小排列的结果，因此可通过中序遍历获取
最接近target节点的perdecessors，通过逆中序遍历获取最接近target节点的successors,然后merge perdecessors 和 successors 获取最
接近target节点的 k个节点值。 
注意到在中序遍历时遇到比target 大的节点即停止，因为由BST的性质可知后面的元素均会比target 大，即所有target的
predecessors均已找到，同理逆中序遍历时遇到不大于 target的节点即可停止递归。 
http://buttercola.blogspot.com/2015/09/leetcode-closest-binary-search-tree_8.html
    public List<Integer> closestKValues(TreeNode root, double target, int k) {  
        List<Integer> result = new ArrayList<Integer>();  
        LinkedList<Integer> stackPre = new LinkedList<Integer>();  
        LinkedList<Integer> stackSucc = new LinkedList<Integer>();  
        inorder(root, target, false, stackPre);  
        inorder(root, target, true, stackSucc);  
        while (k-- > 0) {  
            if (stackPre.isEmpty()) {  
                result.add(stackSucc.pop());  
            } else if (stackSucc.isEmpty()) {  
                result.add(stackPre.pop());  
            } else if (Math.abs(stackPre.peek() - target) < Math.abs(stackSucc.peek() - target)) {  
                result.add(stackPre.pop());  
            } else {  
                result.add(stackSucc.pop());  
            }  
        }  
        return result;  
    }  
    public void inorder(TreeNode root, double target, boolean reverse, LinkedList<Integer> stack) {  
        if (root == null) return;  
        inorder(reverse ? root.right : root.left, target, reverse, stack);  
        if ((reverse && root.val <= target) || (!reverse && root.val > target))  
            return;  
        stack.push(root.val);  
        inorder(reverse ? root.left : root.right, target, reverse, stack);  
    } 
http://buttercola.blogspot.com/2015/09/leetcode-closest-binary-search-tree_8.html

    public List<Integer> closestKValues(TreeNode root, double target, int k) {

        List<Integer> result = new ArrayList<>();

        if (root == null) {

            return result;

        }

         

        Stack<Integer> precedessor = new Stack<>();

        Stack<Integer> successor = new Stack<>();

         

        getPredecessor(root, target, precedessor);

        getSuccessor(root, target, successor);

         

        for (int i = 0; i < k; i++) {

            if (precedessor.isEmpty()) {

                result.add(successor.pop());

            } else if (successor.isEmpty()) {

                result.add(precedessor.pop());

            } else if (Math.abs((double) precedessor.peek() - target) < Math.abs((double) successor.peek() - target)) {

                result.add(precedessor.pop());

            } else {

                result.add(successor.pop());

            }

        }

         

        return result;

    }

     

    private void getPredecessor(TreeNode root, double target, Stack<Integer> precedessor) {

        if (root == null) {

            return;

        }

         

        getPredecessor(root.left, target, precedessor);

         

        if (root.val > target) {

            return;

        }

         

        precedessor.push(root.val);

         

        getPredecessor(root.right, target, precedessor);

    }

     

    private void getSuccessor(TreeNode root, double target, Stack<Integer> successor) {

        if (root == null) {

            return;

        }

         

        getSuccessor(root.right, target, successor);

         

        if (root.val <= target) {

            return;

        }

         

        successor.push(root.val);

         

        getSuccessor(root.left, target, successor);

    }

X.
https://leetcode.com/discuss/55486/java-two-stacks-iterative-solution
public List<Integer> closestKValues(TreeNode root, double target, int k) {
    Deque<TreeNode> bigger = new ArrayDeque<TreeNode>();
    Deque<TreeNode> smaller = new ArrayDeque<TreeNode>();
    TreeNode node = root;
    // log(n)
    while(node != null)
    {
        if(node.val > target)
        {
            bigger.push(node);
            node = node.left;
        }
        else
        {
            smaller.push(node);
            node = node.right;
        }
    }

    // k
    List<Integer> ret = new ArrayList<Integer>();
    while(ret.size() < k)
    {
        if(bigger.isEmpty() ||
           !smaller.isEmpty() &&
            ((bigger.peek().val - target) > (target - smaller.peek().val)))
        {
            node = smaller.pop();
            ret.add(node.val);

            // Get next smaller
            node = node.left;
            while(node != null)
            {
                smaller.push(node);
                node = node.right;
            }
        }
        else
        {
            node = bigger.pop();
            ret.add(node.val);

            // get next bigger
            node = node.right;
            while(node != null)
            {
                bigger.push(node);
                node = node.left;
            }                
        }
    }

    return ret;
}
https://discuss.leetcode.com/topic/30597/java-5ms-iterative-following-hint-o-klogn-time-and-space
Following the hint, I use a predecessor stack and successor stack. I do a logn traversal to initialize them
 until I reach the null node. Then I use the getPredecessor and getSuccessor method to pop k closest nodes and update the stacks.
Time complexity is O(klogn), since k BST traversals are needed and each is bounded by O(logn) time. Note that it is not O(logn + k) which is the time complexity for k closest numbers in a linear array.
Space complexity is O(klogn), since each traversal brings O(logn) new nodes to the stack.

https://discuss.leetcode.com/topic/23151/o-logn-java-solution-with-two-stacks-following-hint/10
https://discuss.leetcode.com/topic/39192/java-o-logn-k-two-stacks-solution-w-improvement
My solution is inspired by following link. I gave all the credit to that post.
https://leetcode.com/discuss/55682/o-logn-java-solution-with-two-stacks-following-hint
However, I did some improvement, dealing with the case that target is a whole name like 12.0 and in the bst, there is also a node with int value of 12.
In the original post, the author put node 12 input both stacks and use if(!succ.isEmpty() && !pred.isEmpty() && succ.peek().val == pred.peek().val) {
getNextPredecessor(pred);
}
to get rid of the duplicate. My approach is to prevent the duplicate node being added into both stack in the first place. The predecessor stack only allows nodes strictly smaller than targetInt. So 12 is added only to successor stack.
2nd the double type target is rounded to nearest int targetInt and is used to compare. In original post.
if(root.val == target) is not a good idea to check if a double is equal to an int. Double has precision issue.
So I think it is better just to compare int with int by rounding target first.
The space complexity is O(logn + k) depending on if k is bigger or not. Runtime is also O(logn + k), as the initialization of stack takes O(logn), after that getting each next successor or predecessor takes O(1) most likely. Sometimes it takes more if you need to push left part into stack. but that can be accounted to logn. part..
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        int targetInt = (int) Math.round(target); // round target to the nearest int
        List<Integer> closestValues = new ArrayList<>();
        Stack<TreeNode> predecessors = new Stack<>();
        Stack<TreeNode> successors = new Stack<>();
        initializeSucc(successors, root, targetInt);
        initializePred(predecessors, root, targetInt);
        int pred, succ;
        while (k > 0) {
            if (successors.isEmpty()) {
                closestValues.add(getPredecessor(predecessors));
            } else if (predecessors.isEmpty()) {
                closestValues.add(getSuccessor(successors));
            } else {
                pred = predecessors.peek().val;
                succ = successors.peek().val;
                if (succ - target < target - pred) {
                    closestValues.add(getSuccessor(successors));
                } else {
                    closestValues.add(getPredecessor(predecessors));
                }
            }
            k--;
        }
        return closestValues;
    }
    // search from root, push all nodes >= target in stack 
    private void initializeSucc(Stack<TreeNode> successors, TreeNode root, int target) {
        while (root != null) {
            if (root.val > target) {
                successors.push(root);
                root = root.left;
            } else if (root.val < target) {
                root = root.right;
            } else {
                successors.push(root);
                break; // if targetInt is found, stop as its right child will be pushed into stack next time
            }
        }
    }
    // push nodes that are strictly less than targetInt
    private void initializePred(Stack<TreeNode> predecessors, TreeNode root, int target) {
        while (root != null) {
            if (root.val < target) {
                predecessors.push(root);
                root = root.right;
            } else {
                root = root.left;
            }
        }
    }
    private int getPredecessor(Stack<TreeNode> predecessors) {
        TreeNode nextPred = predecessors.pop();
        TreeNode root = nextPred.left;
        while (root != null) {
            predecessors.push(root);
            root = root.right;
        }
        return nextPred.val;
    }
    
    
    private int getSuccessor(Stack<TreeNode> successors) {
        TreeNode nextSucc = successors.pop();
        TreeNode root = nextSucc.right;
        while (root != null) {
            successors.push(root);
            root = root.left;
        }
        return nextSucc.val;
    }
X. Use Deque
http://buttercola.blogspot.com/2015/09/leetcode-closest-binary-search-tree_8.html
Brute-force solution:
The straight-forward solution would be to use a heap. We just treat the BST just as a usual array and do a in-order traverse. Then we compare the current element with the minimum element in the heap, the same as top k problem.

http://www.cnblogs.com/yrbbest/p/5031304.html
我们可以使用in-order的原理，从最左边的元素开始，维护一个Deque或者doubly linked list，将这个元素的值从后端加入到Deque中，然后继续遍历下一个元素。当Deque的大小为k时， 比较当前元素和队首元素与target的差来尝试更新deque。循环结束条件是队首元素与target的差更小或者遍历完全部元素。这样的话时间复杂度是O(n)， 空间复杂度应该是O(k)。
Time Complexity - O(n)， Space Complexity - O(k)
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> res = new LinkedList<>();
        inOrder(root, target, k, res);
        return res;
    }
    
    private void inOrder(TreeNode root, double target, int k, LinkedList<Integer> res) {
        if(root == null) {
            return;
        }
        inOrder(root.left, target, k, res);
        if(res.size() == k) {
            if(Math.abs(res.get(0) - target) >= Math.abs(root.val - target)) {
                res.removeFirst();
                res.add(root.val);
            } else {
                return;
            }
        } else {
            res.add(root.val);
        }
        inOrder(root.right, target, k, res);
    }
https://segmentfault.com/a/1190000003797291
二叉搜索树的中序遍历就是顺序输出二叉搜索树，所以我们只要中序遍历二叉搜索树，同时维护一个大小为K的队列，前K个数直接加入队列，之后每来一个新的数（较大的数），如果该数和目标的差，相比于队头的数离目标的差来说，更小，则将队头拿出来，将新数加入队列。如果该数的差更大，则直接退出并返回这个队列，因为后面的数更大，差值也只会更大。
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        Queue<Integer> klist = new LinkedList<Integer>();
        Stack<TreeNode> stk = new Stack<TreeNode>();
        // 迭代中序遍历二叉搜索树的代码
        while(root != null){
            stk.push(root);
            root = root.left;
        }
        while(!stk.isEmpty()){
            TreeNode curr = stk.pop();
            // 维护一个大小为k的队列
            // 队列不到k时直接加入
            if(klist.size() < k){
                klist.offer(curr.val);
            } else {
            // 队列到k时，判断下新的数是否更近，更近就加入队列并去掉队头
                int first = klist.peek();
                if(Math.abs(first - target) > Math.abs(curr.val - target)){
                    klist.poll();
                    klist.offer(curr.val);
                } else {
                // 如果不是更近则直接退出，后面的数只会更大
                    break;
                }
            }
            // 中序遍历的代码
            if(curr.right != null){
                curr = curr.right;
                while(curr != null){
                    stk.push(curr);
                    curr = curr.left;
                }
            }
        }
        // 强制转换成List，是用LinkedList实现的所以可以转换
        return (List<Integer>)klist;
    }
X. O(n) https://discuss.leetcode.com/topic/30081/java-in-order-traversal-1ms-solution
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        closestKValuesHelper(list, root, target, k);
        return list;
    }
    
    /**
     * @return <code>true</code> if result is already found.
     */
    private boolean closestKValuesHelper(LinkedList<Integer> list, TreeNode root, double target, int k) {
        if (root == null) {
            return false;
        }
        
        if (closestKValuesHelper(list, root.left, target, k)) {
            return true;
        }
        
        if (list.size() == k) {
            if (Math.abs(list.getFirst() - target) < Math.abs(root.val - target)) {
                return true;
            } else {
                list.removeFirst();
            }
        }
        
        list.addLast(root.val);
        return closestKValuesHelper(list, root.right, target, k);
    }

X. Using Heap/PriorityQueue
Brute-force solution:
The straight-forward solution would be to use a heap. We just treat the BST just as a usual array and do a in-order traverse. Then we compare the current element with the minimum element in the heap, the same as top k problem.
The time complexity would be O(k + (n - k) logk). 
Space complexity is O(k).

private PriorityQueue<Integer> minPQ;
private int count = 0;
public List<Integer> closestKValues(TreeNode root, double target, int k) {
    minPQ = new PriorityQueue<Integer>(k);
    List<Integer> result = new ArrayList<Integer>();
    
    inorderTraverse(root, target, k);
    
    // Dump the pq into result list
    for (Integer elem : minPQ) {
        result.add(elem);
    }
    
    return result;
}

private void inorderTraverse(TreeNode root, double target, int k) {
    if (root == null) {
        return;
    }
    
    inorderTraverse(root.left, target, k);
    
    if (count < k) {
        minPQ.offer(root.val);
    } else {
        if (Math.abs((double) root.val - target) < Math.abs((double) minPQ.peek() - target)) {
            minPQ.poll();
            minPQ.offer(root.val);
        }
    }
    count++;
    
    inorderTraverse(root.right, target, k);
}
http://blog.csdn.net/xudli/article/details/48752907
prefix traverse. 同时维护一个大小为k的 max heap. 注意根据bst的性质,在diff 大于 maxHeap时, 可以只遍历一边的子树. 
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        PriorityQueue<Double> maxHeap = new PriorityQueue<Double>(k, new Comparator<Double>() { 
            @Override
            public int compare(Double x, Double y) {
                return (int)(y-x);
            }
        });
        Set<Integer> set = new HashSet<Integer>();
        
        rec(root, target, k, maxHeap, set);
        
        return new ArrayList<Integer>(set);
    }
    
    private void rec(TreeNode root, double target, int k, PriorityQueue<Double> maxHeap, Set<Integer> set) {
        if(root==null) return;
        double diff = Math.abs(root.val-target);
        if(maxHeap.size()<k) {
            maxHeap.offer(diff);
            set.add(root.val);
        } else if( diff < maxHeap.peek() ) {
            double x = maxHeap.poll();
            if(! set.remove((int)(target+x))) set.remove((int)(target-x));
            maxHeap.offer(diff);
            set.add(root.val);
        } else {
            if(root.val > target) rec(root.left, target, k, maxHeap,set);
            else rec(root.right, target, k, maxHeap, set);
            return;
        }
        rec(root.left, target, k, maxHeap, set);
        rec(root.right, target, k, maxHeap, set);
    }

http://www.cnblogs.com/jcliBlogger/p/4771342.html
http://blog.csdn.net/xudli/article/details/48752907
There is a very simple idea to keep a max heap of size k and elements in the heap are sorted by their absolute difference from target. For the max heap, we will simply use the default priority_queue. Then we simply traverse the tree and push all its nodes to the heap while maintaining the size of heap not larger than k.

    vector<int> closestKValues(TreeNode* root, double target, int k) {

        priority_queue<pair<double, int>> que;

        dfs(root, target, k, que);

        vector<int> ret;

        while(!que.empty()){

            ret.push_back(que.top().second);

            que.pop();

        }

        return ret;

    }

    void dfs(TreeNode* root, double target, int k, priority_queue<pair<double, int>> &que){

        if(!root) return;

        que.push(pair<double, int>(abs(target-root->val), root->val));

        if(que.size()>k) que.pop();

        dfs(root->left, target, k, que);

        dfs(root->right, target, k, que);

    }
http://buttercola.blogspot.com/2015/09/leetcode-closest-binary-search-tree_8.html
The straight-forward solution would be to use a heap. We just treat the BST just as a usual array and do a in-order traverse. Then we compare the current element with the minimum element in the heap, the same as top k problem.

    private PriorityQueue<Integer> minPQ;

    private int count = 0;

    public List<Integer> closestKValues(TreeNode root, double target, int k) {

        minPQ = new PriorityQueue<Integer>(k);

        List<Integer> result = new ArrayList<Integer>();

         

        inorderTraverse(root, target, k);

         

        // Dump the pq into result list

        for (Integer elem : minPQ) {

            result.add(elem);

        }

         

        return result;

    }

     

    private void inorderTraverse(TreeNode root, double target, int k) {

        if (root == null) {

            return;

        }

         

        inorderTraverse(root.left, target, k);

         

        if (count < k) {

            minPQ.offer(root.val);

        } else {

            if (Math.abs((double) root.val - target) < Math.abs((double) minPQ.peek() - target)) {

                minPQ.poll();

                minPQ.offer(root.val);

            }

        }

        count++;

         

        inorderTraverse(root.right, target, k);

    }
X. A time linear solution:

public class Solution {

    public List<Integer> closestKValues(TreeNode root, double target, int k) {

        List<Integer> result = new ArrayList<>();

        if (root == null) {

            return result;

        }

         

        Stack<Integer> precedessor = new Stack<>();

        Stack<Integer> successor = new Stack<>();

         

        getPredecessor(root, target, precedessor);

        getSuccessor(root, target, successor);

         

        for (int i = 0; i < k; i++) {

            if (precedessor.isEmpty()) {

                result.add(successor.pop());

            } else if (successor.isEmpty()) {

                result.add(precedessor.pop());

            } else if (Math.abs((double) precedessor.peek() - target) < Math.abs((double) successor.peek() - target)) {

                result.add(precedessor.pop());

            } else {

                result.add(successor.pop());

            }

        }

         

        return result;

    }

     

    private void getPredecessor(TreeNode root, double target, Stack<Integer> precedessor) {

        if (root == null) {

            return;

        }

         

        getPredecessor(root.left, target, precedessor);

         

        if (root.val > target) {

            return;

        }

         

        precedessor.push(root.val);

         

        getPredecessor(root.right, target, precedessor);

    }

     

    private void getSuccessor(TreeNode root, double target, Stack<Integer> successor) {

        if (root == null) {

            return;

        }

         

        getSuccessor(root.right, target, successor);

         

        if (root.val <= target) {

            return;

        }

         

        successor.push(root.val);

         

        getSuccessor(root.left, target, successor);

    }

}
Your time linear solution is unnecessarily complicated. You are using O(N) space with those two stacks.
A simple solution with same time and space complexity of O(N) is:
1. Store InOrder traversal of the BST in an array. O(N) space and time
2. Find the element closest to target in the inorder array.(can be implemented in O(logn) using binary search variation or just traverse the array in O(N).
3. From the closest value in the array move left or right depending on which is closer similarly to what you have done with those two stacks. O(K)

https://discuss.leetcode.com/topic/22940/ac-clean-java-solution-using-two-stacks
The idea is to compare the predecessors and successors of the closest node to the target, we can use two stacks to
track the predecessors and successors, then like what we do in merge sort, we compare and pick the closest one to
the target and put it to the result list.
As we know, inorder traversal gives us sorted predecessors, whereas reverse-inorder traversal gives us sorted successors.
We can use iterative inorder traversal rather than recursion, but to keep the code clean, here is the recursion version.
public List<Integer> closestKValues(TreeNode root, double target, int k) {
  List<Integer> res = new ArrayList<>();

  Stack<Integer> s1 = new Stack<>(); // predecessors
  Stack<Integer> s2 = new Stack<>(); // successors

  inorder(root, target, false, s1);
  inorder(root, target, true, s2);
  
  while (k-- > 0) {
    if (s1.isEmpty())
      res.add(s2.pop());
    else if (s2.isEmpty())
      res.add(s1.pop());
    else if (Math.abs(s1.peek() - target) < Math.abs(s2.peek() - target))
      res.add(s1.pop());
    else
      res.add(s2.pop());
  }
  
  return res;
}

// inorder traversal
void inorder(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
  if (root == null) return;

  inorder(reverse ? root.right : root.left, target, reverse, stack);
  // early terminate, no need to traverse the whole tree
  if ((reverse && root.val <= target) || (!reverse && root.val > target)) return;
  // track the value of current node
  stack.push(root.val);
  inorder(reverse ? root.left : root.right, target, reverse, stack);
}
 * @author het
 *
 */
public class L272ClosestBinarySearchTreeValueII {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
