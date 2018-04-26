package alite.leetcode.xx3;
/**
 * LeetCode 331 - Verify Preorder Serialization of a Binary Tree

http://bookshadow.com/weblog/2016/02/01/leetcode-verify-preorder-serialization-binary-tree/
One way to serialize a binary tree is to use pre-oder traversal. When we encounter a non-null node, 
we record the node's value. If it is a null node, we record using a sentinel value such as #.
     _9_
    /   \
   3     2
  / \   / \
 4   1  #  6
/ \ / \   / \
# # # #   # #
For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.
Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. 
Find an algorithm without reconstructing the tree.
Each comma separated value in the string must be either an integer or a character '#' representingnull pointer.
You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".
Example 1:
"9,3,4,#,#,1,#,#,2,#,6,#,#"
Return true
Example 2:
"1,#"
Return false
Example 3:
"9,#,#,1"
Return false
http://www.cnblogs.com/grandyang/p/5174738.html
我们通过举一些正确的例子，比如"9,3,4,#,#,1,#,#,2,#,6,#,#" 或者"9,3,4,#,#,1,#,#,2,#,6,#,#"等等，可以观察出如下两个规律：
1. 数字的个数总是比#号少一个
2. 最后一个一定是#号
那么我们加入先不考虑最后一个#号，那么此时数字和#号的个数应该相同，如果我们初始化一个为0的计数器，遇到数字，计数器加1，遇到#号，计数器减1，那么到最后计数器应该还是0。下面我们再来看两个返回False的例子，"#,7,6,9,#,#,#"和"7,2,#,2,#,#,#,6,#"，那么通过这两个反例我们可以看出，如果根节点为空的话，后面不能再有节点，而且不能有三个连续的#号出现。所以我们再加减计数器的时候，如果遇到#号，且此时计数器已经为0了，再减就成负数了，就直接返回False了，因为正确的序列里，任何一个位置i，在[0, i]范围内的#号数都不大于数字的个数的。当循环完成后，我们检测计数器是否为0的同时还要看看最后一个字符是不是#号
https://leetcode.com/discuss/83809/simple-o-n-solution
Use iterative preorder traversal, actually no need to use stack, just a integer to track the depth of the stack.
public boolean isValidSerialization(String preorder) {
    if (preorder == null || preorder.length() == 0) return false;
    String[] strs = preorder.split(",");
    int depth = 0;
    int i = 0;
    while (i < strs.length - 1) {
        if (strs[i++].equals("#")) {
            if (depth == 0) return false;
            else depth--;
        }
        else depth++;
    }
    if (depth != 0) return false;
    return strs[strs.length - 1].equals("#");
}

X. 入度和出度的差
https://www.hrwhisper.me/leetcode-verify-preorder-serialization-of-a-binary-tree/
对于二叉树，我们把空的地方也作为叶子节点（如题目中的#），那么有
所有的非空节点提供2个出度和1个入度（根除外）
所有的空节点但提供0个出度和1个入度
我们在遍历的时候，计算diff = outdegree – indegree. 当一个节点出现的时候，diff – 1，因为它提供一个入度；当节点不是#的时候，diff+2(提供两个出度) 如果序列式合法的，那么遍历过程中diff >=0 且最后结果为0.
https://leetcode.com/discuss/83824/7-lines-easy-java-solution
http://algobox.org/verify-preorder-serialization-of-a-binary-tree/
Some used stack. Some used the depth of a stack. Here I use a different perspective.
In a binary tree, if we consider null as leaves, then
all non-null node provides 2 outdegree and 1 indegree (2 children and 1 parent), except root
all null node provides 0 outdegree and 1 indegree (0 child and 1 parent).
Suppose we try to build this tree. During building, we record the difference between out degree and in degree diff = outdegree - indegree. When the next node comes, we then decreasediff by 1, because the node provides an in degree. If the node is not null, we increase diff by2, because it provides two out degrees. If a serialization is correct, diff should never be negative and diff will be zero when finished.

If diff is never negative and is zero when finished, the serialization is correct.
Let's assume

If a serialization is correct, diff should never be negative and diff will be zero when finished 
is sounded, how do we argue the opposite is also true, 
i.e.,if diff never becomes negative and diff is zero at the end, then the serialization is correct 

That is, we know a -> b is true, but it does not necessarily mean b -> a is also true. The method I used also has this kind of unresolved question (if we are strict to ourselves) and I cannot explain it well yet.
 this solution could solve preorder and postorder but inorder, just do some modify on '''if (--diff < 0) return false;‘’‘
in-order will be quite easy. the sequence will be 01010101010101...

public boolean isValidSerialization(String preorder) {
    String[] nodes = preorder.split(",");
    int diff = 1;
    for (String node: nodes) {
        if (--diff < 0) return false;
        if (!node.equals("#")) diff += 2;
    }
    return diff == 0;
}

If we treat null's as leaves, then the binary tree will always be full. A full binary tree has a good property that # of leaves = # of nonleaves + 1. Since we are given a pre-order serialization, we just need to find the shortest prefix of the serialization sequence satisfying the property above. If such prefix does not exist, then the serialization is definitely invalid; otherwise, the serialization is valid if and only if the prefix is the entire sequence.
// Java Code
public boolean isValidSerialization(String preorder) {
    int nonleaves = 0, leaves = 0, i = 0;
    String[] nodes = preorder.split(",");
    for (i=0; i<nodes.length && nonleaves + 1 != leaves; i++)
        if (nodes[i].equals("#")) leaves++;
        else nonleaves++;
    return nonleaves + 1 == leaves && i == nodes.length;
}
Similar idea but a different way of thinking (use dangling edges):
public boolean isValidSerialization(String preorder) {
    String[] nodes = preorder.split(",");
    int n = nodes.length;
    int numOfDanglingEdge = 1;
    for (int i = 0; i < n; ++i) {
        if (nodes[i].equals("#")) {
            numOfDanglingEdge--;
            // if nodes are exhausted, found a match
            // o.w. return false, as there is no dangling edge to place node i+1
            if (numOfDanglingEdge == 0) {
                return i == n-1;
            }
        } else {
            numOfDanglingEdge++;
        }
    }
    // there are dangling edges left
    return false;
}
Verification of postorder is answered by @ j0ames.wang.ccc and @dietpepsi. For inorder, a valid serialization has to be of the form #x#x#x#. Because we known # of leaves = # of internal node + 1, and in a valid inorder serialization, no two # can be adjacent to each other (any adjacent pair in an inorder traversal would have to have one internal node). Therefore, to verify a valid inorder serialization, we just need to check if the serialization is of the form#x#x#x#. But as @dietpepsi pointed out already, it won't be uniquely deserialized, it can generate k trees, where k is catalan number.
X.
https://leetcode.com/discuss/83809/simple-o-n-solution
Use iterative preorder traversal, actually no need to use stack, just a integer to track the depth of the stack.
public class Solution {
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) return false;
        String[] strs = preorder.split(",");
        int depth = 0;
        int i = 0; 
        while (i < strs.length - 1) {
            if (strs[i++].equals("#")) {
                if (depth == 0) return false;
                else depth--;
            }
            else depth++;
        }
        if (depth != 0) return false;
        return strs[strs.length - 1].equals("#");
    }
}
https://leetcode.com/discuss/84247/java-concise-6-lines-of-code-without-using-stack
https://leetcode.com/discuss/84671/simple-java-sol-without-tree-construction
public boolean isValidSerialization(String preorder) {
    String[] strs = preorder.split(",");
    int N = strs.length, count = 0, i = -1;
    while(++i<N-1) {
        if(!"#".equals(strs[i])) ++count;
        else if(--count<0) return false;
    }
    return count==0 && "#".equals(strs[i]);
}
Interesting fact:
number of sentinel nodes = number of non-sentinel nodes + 1
Key idea:
count: counter of non-sentinel nodes - sentinel nodes up to index i. So we increase counter if current value is not # (it is like push item into stack), otherwise decrease it (it is like pop item out of stack)
if current value is # and counter is already 0, return false (it is like the case when stack is already empty and you cannot pop anymore)
however, if counter is 0 and we already moved to the last location, return true because of the above interesting fact
https://leetcode.com/discuss/83934/6-lines-python-7-lines-java
public boolean isValidSerialization(String preorder) {
    int need = 1;
    for (String val : preorder.split(",")) {
        if (need == 0)
            return false;
        need -= " #".indexOf(val);
    }
    return need == 0;
}
X. Use Regex
https://leetcode.com/discuss/83942/2-lines-java-using-regex
Yep, only O(n^2), and that example is equivalent to the one I also gave andrei3 :-). I thought about using "(\\d+,#,)+#" which would solve it quickly, but it would still be slow for "always only left child" and I'm guessing also for lots of less simple cases, so I gave up trying to make it fast and just went for making it simple.
public boolean isValidSerialization(String preorder) {
    String s = preorder.replaceAll("\\d+,#,#", "#");
    return s.equals("#") || !s.equals(preorder) && isValidSerialization(s);
}
https://leetcode.com/discuss/83896/8-line-regex-solution-without-building-the-tree
public bool IsValidSerialization(string preorder) {
    var t = String.Empty;
    preorder = Regex.Replace(preorder, "[0-9]+", "0");
    while (preorder != t)
    {
        t = preorder;
        preorder = preorder.Replace("0,#,#", "#");
    }
    return preorder == "#";
}
X. Recursive Version
http://www.allenlipeng47.com/blog/index.php/2016/02/01/verify-preorder-serialization-of-a-binary-tree/
The idea is that if current is ‘#’, we simply return true. If current is a number, call validHelper 2 times with pos++. In the end, return true if pos == list.length – 1.
public static boolean isValidSerialization(String preorder) {
    String[] list = preorder.split(",");
    int[] pos = new int[] {-1};
    if (!validHelper(list, pos) || pos[0] != list.length - 1) { // in the end, pos[0] should equal len - 1
        return false;
    }
    return true;
}

public static boolean validHelper(String[] list, int[] pos) {
    pos[0]++;
    if (pos[0] >= list.length) {  // add pos[0] first
        return false;
    }
    if (list[pos[0]].equals("#")) { // if is '#', return true.
        return true;
    }
    // if is a number, call 2 times.
    return validHelper(list, pos) && validHelper(list, pos);
}
X. 利用栈（Stack）数据结构
https://leetcode.com/discuss/83819/java-intuitive-22ms-solution-with-stack
    public boolean isValidSerialization(String preorder) {
        // using a stack, scan left to right
        // case 1: we see a number, just push it to the stack
        // case 2: we see #, check if the top of stack is also #
        // if so, pop #, pop the number in a while loop, until top of stack is not #
        // if not, push it to stack
        // in the end, check if stack size is 1, and stack top is #
        if (preorder == null) {
            return false;
        }
        Stack<String> st = new Stack<>();
        String[] strs = preorder.split(",");
        for (int pos = 0; pos < strs.length; pos++) {
            String curr = strs[pos];
            while (curr.equals("#") && !st.isEmpty() && st.peek().equals(curr)) {
                st.pop();
                if (st.isEmpty()) {
                    return false;
                }
                st.pop();
            }
            st.push(curr);
        }
        return st.size() == 1 && st.peek().equals("#");
    }

将元素压入栈
如果当前栈的深度≥3，并且最顶端两个元素为'#', '#'，而第三个元素不为'#'，则将这三个元素弹出栈顶，然后向栈中压入一个'#'，重复此过程
最后判断栈中剩余元素是否只有一个'#'
    def isValidSerialization(self, preorder):
        stack = collections.deque()
        for item in preorder.split(','):
            stack.append(item)
            while len(stack) >= 3 and \
                  stack[-1] == stack[-2] == '#' and \
                  stack[-3] != '#':
                stack.pop(), stack.pop(), stack.pop()
                stack.append('#')
        return len(stack) == 1 and stack[0] == '#'
http://www.voidcn.com/blog/sun_wangdong/article/p-5025350.html
http://www.programcreek.com/2015/01/leetcode-verify-preorder-serialization-of-a-binary-tree-java/
已例子一为例：”9,3,4,#,#,1,#,#,2,#,6,#,#” 遇到x # #的时候，就把它变为 #
我模拟一遍过程：
9,3,4,#,# => 9,3,# 继续读
9,3,#,1,#,# => 9,3,#,# => 9,# 继续读
9,#2,#,6,#,# => 9,#,2,#,# => 9,#,# => #
从以上步骤可以得到规律，也就是二叉树先序遍历的过程，此题按照倒退，可以发现如果出现的连续两个"#"，那么可以和之前的那个非"#"的数字合并为一个"#"，其实也就是考虑将一个节点的合并为它的父节点的左子树或者是右子树。
用堆栈来做，其中堆栈的头顶的元素可以用stack.get(stack.size() - 1)来得到，并且判断stack中的元素和某一个元素是否相等的方法不能用"=="，而必须用equals来做。
public boolean isValidSerialization(String preorder)
{
  Stack<String> stack = new Stack<String>();
  String[] num = preorder.split(",");
  for(int i = 0; i < num.length; i++)
  {
   stack.push(num[i]);
   while(stack.size() >= 3)
   {
    //这里必须要使用equals，如果用==就会报错
    if(stack.get(stack.size() - 1).equals("#") && stack.get(stack.size() - 2).equals("#") && !stack.get(stack.size() - 3).equals("#"))
    {
     stack.pop();
     stack.pop();
     stack.pop();
     stack.push("#");
    }
    else 
     break;
   }
  }
  //注意一种特殊的情况，如果是只有一个"#"，那么返回的是true，而不是false。
  return stack.size() == 1 && stack.peek().equals("#");
 }
此外，还需要注意一种特殊情况，也就是只有一个"#"的情况，此表示没有节点，那么返回的是true，而不是false.
另附递归重建树的解法（Time Limit Exceeded），详见代码。
    def isValidSerialization(self, preorder):
        items = preorder.split(',')
        map = dict()
        def isValid(start, end):
            size = end - start + 1
            if size == 0:
                return False
            if items[start] == '#':
                return size == 1
            if size == 3:
                return items[start + 1] == items[start + 2] == '#'
            for k in range(2, size):
                left, right = (start + 1, start + k - 1), (start + k, end)
                vl = map.get(left)
                if vl is None:
                    vl = map[left] = isValid(*left)
                vr = map.get(right)
                if vr is None:
                    vr = map[right] = isValid(*right)
                if vl and vr:
                    return True
            return False
        return isValid(0, len(items) - 1)

http://www.jiuzhang.com/solutions/verify-preorder-serialization-of-a-binary-tree/
    public boolean isValidSerialization(String preorder) {
        String s = preorder;
        boolean flag = true;
        while (s.length() > 1) {
            int index = s.indexOf(",#,#");
            if (index < 0) {
                flag = false;
                break;
            }
            int start = index;
            while (start > 0 && s.charAt(start - 1) != ',')
            {
                start --;
            }
            if (s.charAt(start) == '#') {
                flag = false;
                break;
            }
            s = s.substring(0, start) + s.substring(index + 3);
        }
        if (s.equals("#") && flag) 
            return true;
        else 
            return false;
    }

public boolean isValidSerialization_PostOrder(String postorder) {
    String[] nodes = postorder.split(",");
    int diff = 1;
    for (String node : nodes) {
        diff--;
        if (!node.equals("#")) diff += 2;
        // Post-Order traversal fail criteria
        if (diff > 0) return false;
    }
    return diff == 0;
}
 * @author het
 *
 */
public class LeetCode331 {
//	我们通过举一些正确的例子，比如"9,3,4,#,#,1,#,#,2,#,6,#,#" 或者"9,3,4,#,#,1,#,#,2,#,6,#,#"等等，可以观察出如下两个规律：
//	1. 数字的个数总是比#号少一个
//	2. 最后一个一定是#号
//	那么我们加入先不考虑最后一个#号，那么此时数字和#号的个数应该相同，如果我们初始化一个为0的计数器，遇到数字，计数器加1，遇到#号，计数器减1
	//，那么到最后计数器应该还是0。下面我们再来看两个返回False的例子，"#,7,6,9,#,#,#"和"7,2,#,2,#,#,#,6,#"，那么通过这两个反例我们可以看出，
	//如果根节点为空的话，后面不能再有节点，而且不能有三个连续的#号出现。所以我们再加减计数器的时候，如果遇到#号，且此时计数器已经为0了，再减就成负数了，
	//就直接返回False了，因为正确的序列里，任何一个位置i，在[0, i]范围内的#号数都不大于数字的个数的。
	//当循环完成后，我们检测计数器是否为0的同时还要看看最后一个字符是不是#号
//	https://leetcode.com/discuss/83809/simple-o-n-solution
//	Use iterative preorder traversal, actually no need to use stack, just a integer to track the depth of the stack.
	public boolean isValidSerialization(String preorder) {
	    if (preorder == null || preorder.length() == 0) return false;
	    String[] strs = preorder.split(",");
	    int depth = 0;
	    int i = 0;
	    while (i < strs.length - 1) {
	        if (strs[i++].equals("#")) {
	            if (depth == 0) return false;
	            else depth--;
	        }
	        else depth++;
	    }
	    if (depth != 0) return false;
	    return strs[strs.length - 1].equals("#");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
