package alite.leetcode.newExtra.L500.extra;
/**
 * LintCode 469, 470 - Tweaked Identical Binary Tree

https://github.com/shawnfan/LintCode/blob/master/Java/Tweaked%20Identical%20Binary%20Tree.java
http://www.jianshu.com/p/0623cf8ad71b
检查两棵二叉树是否在经过若干次扭转后可以等价。扭转的定义是，交换任意节点的左右子树。等价的定义是，两棵二叉树必须为相同的结构，并且对应位置上的节点的值要相等。
注意：你可以假设二叉树中不会有重复的节点值。
样例
    1             1
   / \           / \
  2   3   and   3   2
 /                   \
4                     4
是扭转后可等价的二叉树。
    1             1
   / \           / \
  2   3   and   3   2
 /             /
4             4
就不是扭转后可以等价的二叉树。
Check two given binary trees are identical or not. Assuming any number of tweaks are allowed.
A tweak is defined as a swap of the children of one node in the tree.
Example
    1             1
   / \           / \
  2   3   and   3   2
 /                   \
4                     4
are identical.
    1             1
   / \           / \
  2   3   and   3   4
 /                   \
4                     2
are not identical.
Note
There is no two nodes with the same value in the tree.
Challenge
O(n) time

Recursive 比对左左,左右,右左，右右
Recursion - 递归求解，分治的思路。
注意，题目中说的是经过若干次扭转后可以等价，所以不要忘记考虑完全identical的情况，某一个节点的左右子树翻转一次对称，反转两次还原。
https://yisuang1186.gitbooks.io/java-/content/tweaked_identical_binary_tree.html
不要忘记a.val == b.val这个条件
    public boolean isTweakedIdentical(TreeNode a, TreeNode b) {
        // Write your code here
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.val != b.val) {
            return false;
        }
        return (isTweakedIdentical(a.left, b.left) && isTweakedIdentical(a.right, b.right) ) || (isTweakedIdentical(a.left, b.right) && isTweakedIdentical(a.right, b.left) );
    }

public boolean isTweakedIdentical(TreeNode a, TreeNode b) {
    if (a == null || b == null) {
        return a == null && b == null;
    }
    if (a.val != b.val) {
        return false;
    }
    return (isTweakedIdentical(a.left, b.left) && isTweakedIdentical(a.right, b.right))
        || (isTweakedIdentical(a.left, b.right) && isTweakedIdentical(a.right, b.left));
}
https://www.lintcode.com/en/problem/identical-binary-tree/
Check if two binary trees are identical. Identical means the two binary trees have the same structure and every identical position has the same value.
    1             1
   / \           / \
  2   2   and   2   2
 /             /
4             4
are identical.
    1             1
   / \           / \
  2   3   and   2   3
 /               \
4                 4
are not identical.

https://yixuanwangblog.wordpress.com/2016/09/04/lintcode-469-identical-binary-tree/
http://www.geeksforgeeks.org/write-c-code-to-determine-if-two-trees-are-identical/
http://www.jiuzhang.com/solutions/identical-binary-tree/
    public boolean isIdentical(TreeNode a, TreeNode b) {
        // Write your code here
        if (a == null && b == null)
            return true;
        if (a != null && b != null) {
            return a.val == b.val && isIdentical(a.left, b.left)
                    && isIdentical(a.right, b.right);
        }
        return false;
    }
https://xuezhashuati.blogspot.com/2016/02/identical-binary-tree.html
    public boolean isIdentical(TreeNode a, TreeNode b) {
        // Write your code here
        if (a == null && b == null) {
            return true;
        }
        
        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        } 
        
        if (a != null && b != null) {
            if (a.val != b.val) {
                return false;
            }
            else {
                return isIdentical(a.left, b.left) && isIdentical(a.right, b.right);
            }
        }
        
        return false;
    }
 * @author het
 *
 */


/**
 * Web Address - Uber Prepare

http://www.cnblogs.com/EdwardLiu/p/6364661.html
题目是给你一堆域名，其中一些是另一些的parent，比如.com是.youku.com的parent，然后.youku.com是.service.youku.com的parent这样，
然后再给你一个网址，让你在那堆域名中找到这个网址的parent里面最长的一个，然后再往前退一个返回。语言有点不好描述，举个栗子：

Domains:[
“.com”,
“.cn”
“.service.com”
“.net”
“.youku.net”
]
.
url: “yeah.hello.youku.net”

这里.net和.youku.net都是这个url的parent,其中最长的是.youku.net，再往前退一个是hello,所以返回“hello.youku.net”
虽然我觉得这道题用set倒着来就可以解决，但是看到一个Trie的做法也很不错
这里TrieNode.val不再是char, 而是String.  children array也变成了Map<String, TrieNode>
 2     private class TrieNode {
 3         String str;
 4         Map<String, TrieNode> map;
 5         boolean isLeaf;
 6         public TrieNode(String str) {
 7             this.str = str;
 8             this.map = new HashMap<>();
 9             this.isLeaf = false;
10         }
11     }
12     TrieNode root = new TrieNode("#");
13     public static void main(String[] args) {
14         LongestSubDomain lsd = new LongestSubDomain();
15         String[] domains = {".com", ".cn", ".service.com", ".net", ".youku.net"};
16         String url = "yeah.hello.youku.net";
17         for (String str : domains) {
18             lsd.insert(str);
19         }
20         String res = lsd.startWith(url);
21         System.out.println(res);
22     }
23     public void insert(String domain) {
24         String[] temp = domain.split("\\.");
25         TrieNode node = root;
26         for (int i = temp.length - 1; i >= 0; i--) {
27             if (temp[i].length() == 0) continue;
28             if (node.map.containsKey(temp[i])) {
29                 node = node.map.get(temp[i]);
30             } else {
31                 TrieNode newNode = new TrieNode(temp[i]); 
32                 node.map.put(temp[i], newNode);
33                 node = newNode;
34             }
35         }
36         node.isLeaf = true;
37     }
38     public String startWith(String url) {
39         String[] temp = url.split("\\.");
40         TrieNode node = root;
41         String res = "";
42         int index = temp.length - 1;
43         for (int i = temp.length - 1; i >= 0; i--) {
44             if (temp[i].length() == 0) continue;
45             if (node.map.containsKey(temp[i])) {
46                 res = "." + temp[i] + res;
47                 node = node.map.get(temp[i]);
48             } else {
49                 if (!node.isLeaf) {
50                     res = "";
51                 } else {
52                     index = i;
53                 }
54                 break;
55             }
56         }
57         return temp[index] + res;
58     }
我的做法：
 6     public class TrieNode {
 7         String val;
 8         Map<String, TrieNode> children;
 9         boolean end;
10         public TrieNode(String str) {
11             val = str;
12             children = new HashMap<String, TrieNode>();
13             end = false;
14         }
15     }
16     
17 
18         TrieNode root = new TrieNode("");
19         
20         public void insert(String str) {
21             String[] arr = str.split("\\.");
22             TrieNode node = root;
23             for (int i=arr.length-1; i>=0; i--) {
24                 if (arr[i].length() == 0) continue;
25                 if (!node.children.containsKey(arr[i])) {
26                     node.children.put(arr[i], new TrieNode(arr[i]));
27                 }
28                 node = node.children.get(arr[i]);
29             }
30             node.end = true;
31         }
32         
33         public String findLongest(String str) {
34             String[] input = str.split("\\.");
35             TrieNode node = root;
36             StringBuffer res = new StringBuffer(); 
37             for (int i=input.length-1; i>=0; i--) {
38                 String cur = input[i];
39                 if (node.children.containsKey(cur)) {
40                     res.insert(0, cur);
41                     res.insert(0, ".");
42                     node = node.children.get(cur);
43                 }
44                 else {
45                     res.insert(0, cur);
46                     return res.toString();
47                 }
48             }
49             return "";
50         }
 * @author het
 *
 */

/**
 * 
 * @author het
 *
 */
public class Lintcode {

}
