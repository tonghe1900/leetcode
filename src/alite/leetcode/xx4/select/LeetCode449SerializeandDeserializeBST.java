package alite.leetcode.xx4.select;

import java.util.Deque;
import java.util.LinkedList;

import alite.leetcode.xx4.TreeNode;

/**
 * TODO - LeetCode 450 - Delete Node in a BST
https://leetcode.com/problems/serialize-and-deserialize-bst/
Serialization is the process of converting a data structure or object into a sequence of bits so that 
it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed 
later in the same or another computer environment.
Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on 
how your serialization/deserialization algorithm should work. 
You just need to ensure that a binary search tree can be serialized to a string and 
this string can be deserialized to the original tree structure.
The encoded string should be as compact as possible.
Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
https://discuss.leetcode.com/topic/66832/java-o-n-recursive-dfs-without-null-changed-from-serialize-and-deserialize-bt
Thanks to this post, I realize that I can make use of lower and upper bound.
public String serialize(TreeNode root) { // preorder
    StringBuilder sb = new StringBuilder();
    serializedfs(root, sb);
    return sb.toString();
}

private void serializedfs(TreeNode root, StringBuilder sb){
    if(root == null) return; // no "null "
    sb.append(root.val).append(" ");
    serializedfs(root.left, sb);
    serializedfs(root.right, sb);
}

// Decodes your encoded data to tree.
public TreeNode deserialize(String data) {
    if(data.length() == 0) return null;
    String[] list = data.split(" ");
    TreeNode dummy = new TreeNode(0);
    deserializedfs(list, 0, dummy, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
    return dummy.left;
}

private int deserializedfs(String[] list, int pos, TreeNode par, boolean isleft, 
                                                    int lower, int upper){
    if(pos >= list.length) return pos;

    int val = Integer.valueOf(list[pos]);
    if(val < lower || val > upper) return pos-1; // have not used this pos, so minus one
    TreeNode cur = new TreeNode(val);
    
    if(isleft) par.left = cur;
    else       par.right = cur;

    pos = deserializedfs(list, ++pos, cur, true, lower, val);
    pos = deserializedfs(list, ++pos, cur, false, val, upper);
    return pos;
}
http://www.cnblogs.com/grandyang/p/4913869.html
先序遍历的递归解法，非常的简单易懂，我们需要接入输入和输出字符串流istringstream和ostringstream，对于序列化，我们从根节点开始，如果节点存在，则将值存入输出字符串流，然后分别对其左右子节点递归调用序列化函数即可。对于去序列化，我们先读入第一个字符，以此生成一个根节点，然后再对根节点的左右子节点递归调用去序列化函数即可
    string serialize(TreeNode* root) {
        ostringstream out;
        serialize(root, out);
        return out.str();
    }
    // Decodes your encoded data to tree.
    TreeNode* deserialize(string data) {
        istringstream in(data);
        return deserialize(in);
    }
private:
    void serialize(TreeNode *root, ostringstream &out) {
        if (root) {
            out << root->val << ' ';
            serialize(root->left, out);
            serialize(root->right, out);
        } else {
            out << "# ";
        }
    }
    TreeNode* deserialize(istringstream &in) {
        string val;
        in >> val;
        if (val == "#") return nullptr;
        TreeNode *root = new TreeNode(stoi(val));
        root->left = deserialize(in);
        root->right = deserialize(in);
        return root;
    }

http://bookshadow.com/weblog/2016/11/01/leetcode-serialize-and-deserialize-bst/
先序遍历（Preorder Traversal）
根据二叉搜索树（BST）的性质，左孩子 < 根节点 < 右孩子，因此可以通过先序遍历的结果唯一确定一棵原始二叉树。
序列化（Serialization）：
先序遍历原始二叉树，输出逗号分隔值字符串。
反序列化（Deserialization）：
利用栈（Stack）数据结构，节点栈nstack保存重建二叉树过程中的节点，最大值栈rstack保存当前节点的右孩子允许的最大值。

遍历序列化串，记当前数值为val，新增树节点node = TreeNode(val)；记nstack的栈顶元素为ntop（nstack[-1]）

若val < ntop，则val为ntop的左孩子，令ntop.left = node，并将node压入nstack

否则，val应为右孩子，但其父节点并不一定为ntop：

    记rstack的栈顶元素为rtop，当val > rtop时，执行循环：
    
        重复弹出nstack，直到ntop不是右孩子为止（nstack[-1] > nstack[-2]条件不成立）
        
        再次弹出nstack， 并弹出rstack

    上述过程执行完毕后，令ntop.right = node，并将node压入nstack
# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Codec:

    def serialize(self, root):
        """Encodes a tree to a single string.
        
        :type root: TreeNode
        :rtype: str
        """
        if not root: return []
        left = self.serialize(root.left)
        right = self.serialize(root.right)
        ans = str(root.val)
        if left: ans += ',' + left
        if right: ans += ',' + right
        return ans

    def deserialize(self, data):
        """Decodes your encoded data to tree.
        
        :type data: str
        :rtype: TreeNode
        """
        if not data: return None
        nstack, rstack = [], [0x7FFFFFFF]
        for val in map(int, data.split(',')):
            node = TreeNode(val)
            if nstack:
                if val < nstack[-1].val:
                    nstack[-1].left = node
                    rstack.append(nstack[-1].val)
                else:
                    while val > rstack[-1]:
                        while nstack[-1].val > nstack[-2].val:
                            nstack.pop()
                        rstack.pop()
                        nstack.pop()
                    nstack[-1].right = node
            nstack.append(node)
        return nstack[0]

# Your Codec object will be instantiated and called as such:
# codec = Codec()
# codec.deserialize(codec.serialize(root))
X. BFS, Queue
http://www.cnblogs.com/charlesblc/p/6019644.html
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) {
            return "";
        }

        // LinkedList实现了Queue接口, ArrayList没有实现
        Queue<TreeNode> qe= new LinkedList<>();
        qe.offer(root);
        sb.append(root.val+",");
        while (!qe.isEmpty()) {
            TreeNode tn = qe.poll();
            if (tn.left != null) {
                sb.append(tn.left.val+",");
                qe.offer(tn.left);
            }
            else {
                sb.append(",");
            }
            if (tn.right != null) {
                sb.append(tn.right.val+",");
                qe.offer(tn.right);
            }
            else {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) {
            return null;
        }

        String[] strs = data.split(",");
        Queue<TreeNode> qe = new LinkedList<>();

        if (strs.length < 1 || strs[0].equals("")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(strs[0]));
        qe.offer(root);
        int i = 1;
        while (!qe.isEmpty()) {
            TreeNode tn = qe.poll();

            if (strs.length > i && !strs[i].equals("")) {
                TreeNode left = new TreeNode(Integer.valueOf(strs[i]));
                tn.left = left;
                qe.offer(left);
            }
            i++;
            if (strs.length > i && !strs[i].equals("")) {
                TreeNode right = new TreeNode(Integer.valueOf(strs[i]));
                tn.right = right;
                qe.offer(right);
            }
            i++;
        }
        return root;
    }
}

另一种方法是层序遍历的非递归解法，这种方法略微复杂一些，我们需要借助queue来做，本质是BFS算法，也不是很难理解，就是BFS算法的常规套路稍作修改即可
    string serialize(TreeNode* root) {
        ostringstream out;
        queue<TreeNode*> q;
        if (root) q.push(root);
        while (!q.empty()) {
            TreeNode *t = q.front(); q.pop();
            if (t) {
                out << t->val << ' ';
                q.push(t->left);
                q.push(t->right);
            } else {
                out << "# ";
            }
        }
        return out.str();
    }
    // Decodes your encoded data to tree.
    TreeNode* deserialize(string data) {
        if (data.empty()) return nullptr;
        istringstream in(data);
        queue<TreeNode*> q;
        string val;
        in >> val;
        TreeNode *res = new TreeNode(stoi(val)), *cur = res;
        q.push(cur);
        while (!q.empty()) {
            TreeNode *t = q.front(); q.pop();
            if (!(in >> val)) break;
            if (val != "#") {
                cur = new TreeNode(stoi(val));
                q.push(cur);
                t->left = cur;
            }
            if (!(in >> val)) break;
            if (val != "#") {
                cur = new TreeNode(stoi(val));
                q.push(cur);
                t->right = cur;
            }
        }
        return res;
    }

https://discuss.leetcode.com/topic/66760/java-bfs-solution-easy-to-understand-serialize-deserialize-runtime-o-n
Inspired by solution https://discuss.leetcode.com/topic/22848/ac-java-solution/26.
Each tree node can be represented by "val/num/", where val is the value of the node and num indicate his children situation (num == 3 meaning having two children, num == 2 meaning having only left child, num == 1 meaning having only right child, num == 0 meaning having no child).
The time complexity for both serialize and deserialize is O(n), where n is the number of nodes in BST. The trade-off here is that I use an extra char "num" as in val/num/.
    // Encodes a tree to a single string.
 public String serialize(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int num = 0;
            if (node.left != null && node.right != null) {
                // 3 indicates having both left and right child
                num = 3;
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left != null) {
                // 2 indicates having left child
                num = 2;
                queue.offer(node.left);
            } else if (node.right != null) {
                // 1 indicates having right child
                num = 1;
                queue.offer(node.right);
            } // 0 indicates having no child
            
            sb.append(node.val).append("/").append(num).append("/");
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() < 4) return null;
        char[] text = data.toCharArray();
        int i = 0;
        TreeNode root = new TreeNode(-1);
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // Set node value
            i = readNode(text, i, node);
            // Read node's child number
            int num = 0;
            while (text[i] != '/') {
                num = num*10 + Character.getNumericValue(text[i]);
                i++;
            }
            i++;
            if (num == 3) {
                addLeftNode(node, queue);
                addRightNode(node, queue);
            } else if (num == 2) {
                addLeftNode(node, queue);
            } else if (num == 1) {
                addRightNode(node, queue);
            }
        }
        return root;
    }
    
    private int readNode(char[] text, int i, TreeNode node) {
        int val = 0;
        while (text[i] != '/') {
            val = val*10 + Character.getNumericValue(text[i]);
            i++;
        }
        node.val = val;
        return i+1;
    }
    
    private void addLeftNode(TreeNode parent, Queue<TreeNode> queue) {
        TreeNode node = new TreeNode(-1);
        parent.left = node;
        queue.offer(node);
    }
    
    private void addRightNode(TreeNode parent, Queue<TreeNode> queue) {
        TreeNode node = new TreeNode(-1);
        parent.right = node;
        queue.offer(node);
    }
}
 * @author het
 *
 */

class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int val){
		 this.val = val;
	}
}
public class LeetCode449SerializeandDeserializeBST {
//	Inspired by solution https://discuss.leetcode.com/topic/22848/ac-java-solution/26.
//		Each tree node can be represented by "val/num/", where val is the value of the node 
	//and num indicate his children situation (num == 3 meaning having two children, num == 2
	//meaning having only left child, num == 1 meaning having only right child, num == 0 meaning having no child).
//		The time complexity for both serialize and deserialize is O(n), where n is the number of nodes in BST.
	//The trade-off here is that I use an extra char "num" as in val/num/.
//		    // Encodes a tree to a single string.
		 public String serialize(TreeNode root) {
		        if (root == null) return "";
		        Deque<TreeNode> queue = new LinkedList<>();
		        queue.offer(root);
		        StringBuilder sb = new StringBuilder();
		        while (!queue.isEmpty()) {
		            TreeNode node = queue.poll();
		            int num = 0;
		            if (node.left != null && node.right != null) {
		                // 3 indicates having both left and right child
		                num = 3;
		                queue.offer(node.left);
		                queue.offer(node.right);
		            } else if (node.left != null) {
		                // 2 indicates having left child
		                num = 2;
		                queue.offer(node.left);
		            } else if (node.right != null) {
		                // 1 indicates having right child
		                num = 1;
		                queue.offer(node.right);
		            } // 0 indicates having no child
		            
		            sb.append(node.val).append("/").append(num).append("/");
		        }
		        return sb.toString();
		    }

		    // Decodes your encoded data to tree.
		    public TreeNode deserialize(String data) {
		        if (data == null || data.length() < 4) return null;
		        char[] text = data.toCharArray();
		        int i = 0;
		        TreeNode root = new TreeNode(-1);
		        Deque<TreeNode> queue = new LinkedList<TreeNode>();
		        queue.offer(root);
		        while (!queue.isEmpty()) {
		            TreeNode node = queue.poll();
		            // Set node value
		            i = readNode(text, i, node);
		            // Read node's child number
		            int num = 0;
		            while (text[i] != '/') {
		                num = num*10 + Character.getNumericValue(text[i]);
		                i++;
		            }
		            i++;
		            if (num == 3) {
		                addLeftNode(node, queue);
		                addRightNode(node, queue);
		            } else if (num == 2) {
		                addLeftNode(node, queue);
		            } else if (num == 1) {
		                addRightNode(node, queue);
		            }
		        }
		        return root;
		    }
		    
		    private int readNode(char[] text, int i, TreeNode node) {
		        int val = 0;
		        while (text[i] != '/') {
		            val = val*10 + Character.getNumericValue(text[i]);
		            i++;
		        }
		        node.val = val;
		        return i+1;
		    }
		    
		    private void addLeftNode(TreeNode parent, Deque<TreeNode> queue) {
		        TreeNode node = new TreeNode(-1);
		        parent.left = node;
		        queue.offer(node);
		    }
		    
		    private void addRightNode(TreeNode parent, Deque<TreeNode> queue) {
		        TreeNode node = new TreeNode(-1);
		        parent.right = node;
		        queue.offer(node);
		    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
