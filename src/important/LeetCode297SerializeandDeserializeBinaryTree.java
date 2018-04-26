package important;

import java.util.Arrays;
import java.util.StringTokenizer;

///**
// * 
//Serialize and Deserialize Binary Tree | LeetCode OJ
//Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
//Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
//For example, you may serialize the following tree
//1
//   / \
//  2   3
//     / \
//    4   5
//as "[1,2,3,null,null,4,5]", just the same as how LeetCode OJ serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
//Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
//X. PreOrderhttp://yuanhsh.iteye.com/blog/2171113
//http://fisherlei.blogspot.com/2013/03/interview-serialize-and-de-serialize.html
//这里只能通过前序遍历来做，中序及后序是行不通的。原因很简单，除了前序以外，其他遍历方式没办法找出头结点。
//
//除了常规的三种遍历方式意外， 另一种可行的方法就是按层来遍历，同样可行。1. Convert the given BST into a doubly linked list in O(n) and then send the linked list over the channel.
//Note:: See Blog Convert tree into linked list in O(n) using tree rotations.
//
//Once you receive the tree then again convert back the linked list to tree
//2. Take the preorder traversal of the tree; write it to a file and send it over the channel. This would be done using O(n).
//
//Since you have to traverse the tree to save the nodes.
//Possible traversals are inorder, preorder, postorder and level order.
//
//If we do an inorder traversal then we would get a sorted list which if we convert into a BST would again become a list and we would loose out on the original structure of the tree.
//
//Postorder traversal would also not give back the original tree; a simple example can let you know.
//
//Now left out are preorder and level order traversal.
//Both give us the output; but level order will require more space as it uses BFS approach. So we do a preorder traversal of the tree store it in a file in O(n) and send it over the channel.
//
//On the recieving end we recieve and construct the tree back in O(n log(n)); by inserting each and every node in the preorder list into the new list using the property of BST results the same tree....
//class TreeNode{  
//    int val;  
//    TreeNode left, right;  
//    TreeNode(int val){  
//        this.val = val;  
//    }  
//}  
//   
//public String serialize(TreeNode root){  
//    StringBuilder sb = new StringBuilder();  
//    serialize(root, sb);  
//    return sb.toString();  
//}  
//// Use StringBuilder =>
//private void serialize(TreeNode x, StringBuilder sb){  
//    if (x == null) {  
//        sb.append("# ");  
//    } else {  
//        sb.append(x.val + " ");  
//        serialzie(x.left, sb);  
//        serialzie(x.right, sb);  
//    }  
//}  
//   
//public TreeNode deserialize(String s){  
//    if (s == null || s.length() == 0) return null;  
//    StringTokenizer st = new StringTokenizer(s, " ");  
//    return deserialize(st);  
//}  
//   
//private TreeNode deserialize(StringTokenizer st){  
//    if (!st.hasMoreTokens())  
//        return null;  
//    String val = st.nextToken();  
//    if (val.equals("#"))  
//        return null;  
//    TreeNode root = new TreeNode(Integer.parseInt(val));  
//    root.left = deserialize(st);  
//    root.right = deserialize(st);  
//    return root;  
//}  
//https://leetcode.com/discuss/69390/simple-solution-%23preorder-traversal-%23recursive-%23simple-logic
//Using List<. not StringBuider
//public String serialize(TreeNode root) {
//    ArrayList<Integer> result = new ArrayList<Integer>();
//    serializeHelper(root,result);
//    return result.toString();
//}
//private void serializeHelper(TreeNode root, ArrayList<Integer> result){
//    if (root == null) {
//        result.add(null);
//        return;
//    }
//    result.add(root.val);
//    serializeHelper(root.left,result);
//    serializeHelper(root.right,result);
//}
//public TreeNode deserialize(String data) {
//    String[] strArray = data.substring(1,data.length()-1).split(", ");
//    Deque<String> strList = new LinkedList<String>(Arrays.asList(strArray));
//    return deserializeHelper(strList);
//}
//private TreeNode deserializeHelper(Deque<String> strList){
//    if (strList.size() == 0) return null;
//    String str = strList.pop();
//    if (str.equals("null")) return null;
//    TreeNode currentRoot = new TreeNode(Integer.parseInt(str));
//    currentRoot.left = deserializeHelper(strList);
//    currentRoot.right = deserializeHelper(strList);
//    return currentRoot;
//}
//Previously deserialize method call String.split or StringTokenzier first, it needs scan the data twice and load all data into memory.
//https://leetcode.com/discuss/70853/recursive-dfs-iterative-dfs-and-bfs
//(1) Iterative DFS
//public String serialize(TreeNode root) {
//    StringBuilder sb=new StringBuilder();
//    TreeNode x=root;
//    Deque<TreeNode> stack=new LinkedList<>();
//    while (x!=null || !stack.isEmpty()) {
//        if (x!=null) {
//            sb.append(String.valueOf(x.val));
//            sb.append(' ');
//            stack.push(x);
//            x=x.left;
//        }
//        else {
//            sb.append("null ");
//            x=stack.pop();
//            x=x.right;
//        }
//    }
//    return sb.toString();
//}
//public TreeNode deserialize(String data) {
//    if (data.length()==0) return null;
//    String[] node=data.split(" ");
//    int n=node.length;
//    Deque<TreeNode> stack=new LinkedList<>();
//    TreeNode root=new TreeNode(Integer.valueOf(node[0]));
//    TreeNode x=root;
//    stack.push(x);
//
//    int i=1;
//    while (i<n) {
//        while (i<n && !node[i].equals("null")) {
//            x.left=new TreeNode(Integer.valueOf(node[i++]));
//            x=x.left;
//            stack.push(x);
//        }
//        while (i<n && node[i].equals("null")) {
//            x=stack.pop();
//            i++;
//        }
//        if (i<n) {
//            x.right=new TreeNode(Integer.valueOf(node[i++]));
//            x=x.right;
//            stack.push(x);
//        }
//    }
//    return root;
//}
//(2) recursive DFS
//// Encodes a tree to a single string.
//public String serialize(TreeNode root) {
//    StringBuilder sb=new StringBuilder();
//    dfs(root,sb);
//    return sb.toString();
//}
//private void dfs(TreeNode x, StringBuilder sb) {
//    if (x==null) {
//        sb.append("null ");
//        return;
//    }
//    sb.append(String.valueOf(x.val));
//    sb.append(' ');
//    dfs(x.left,sb);
//    dfs(x.right,sb);
//}
//
//// Decodes your encoded data to tree.
//public TreeNode deserialize(String data) {
//    String[] node=data.split(" ");
//    int[] d=new int[1];
//    return dfs(node,d);
//}
//private TreeNode dfs(String[] node, int[] d) {
//    if (node[d[0]].equals("null")) {
//        d[0]++;
//        return null;
//    }
//    TreeNode x=new TreeNode(Integer.valueOf(node[d[0]]));
//    d[0]++;
//    x.left=dfs(node,d);
//    x.right=dfs(node,d);
//    return x;
//}
//http://shibaili.blogspot.com/2015/11/day-133-297-serialize-and-deserialize.html
//
//    // Encodes a tree to a single string.
//
//    string serialize(TreeNode* root) {
//
//        queue<TreeNode *> que;
//
//        que.push(root);
//
//        string s = "";
//
//         
//
//        while (!que.empty()) {
//
//            TreeNode *t = que.front();
//
//            que.pop();
//
//            if (t == NULL) {
//
//                s += "n";
//
//            }else {
//
//                s += to_string(t->val) + ",";
//
//                que.push(t->left);
//
//                que.push(t->right);
//
//            }
//
//        }
//
//         
//
//        return s;
//
//    }
//
//
//    int next(string data, int &i) {
//
//        int rt = 0, sign = 1;
//
//        if (data[i] == '-') {
//
//            sign = -1;
//
//            i++;
//
//        }
//
//        while (isdigit(data[i])) {
//
//            rt = rt * 10 + data[i] - '0';
//
//            i++;
//
//        }
//
//        i++;
//
//        return rt * sign;
//
//    }
//
//
//    // Decodes your encoded data to tree.
//
//    TreeNode* deserialize(string data) {
//
//        if (data == "n") return NULL;
//
//        queue<TreeNode *> que;
//
//        int i = 0;
//
//        TreeNode *root = new TreeNode(next(data,i));
//
//        que.push(root);
//
//         
//
//        while (!que.empty()) {
//
//            TreeNode* t = que.front();
//
//            que.pop();
//
//             
//
//            if (isdigit(data[i]) || data[i] == '-') {
//
//                t->left = new TreeNode(next(data,i));
//
//                que.push(t->left);
//
//            }else {
//
//                i++;
//
//            }
//
//             
//
//            if (isdigit(data[i]) || data[i] == '-') {
//
//                t->right = new TreeNode(next(data,i));
//
//                que.push(t->right);
//
//            }else {
//
//                i++;
//
//            }
//
//        }
//
//         
//
//        return root;
//
//    }
//
//Recursive:
//
//    string serialize(TreeNode* root) {
//
//        if (root == NULL) {
//
//            return "n";
//
//        }
//
//        string rt = to_string(root->val) + ",";
//
//        rt += serialize(root->left) + serialize(root->right);
//
//        return rt;
//
//    }
//
//
//    TreeNode *helper(string &s, int &i) {
//
//        if (i == s.length()) return NULL;
//
//        if (s[i] == 'n') {
//
//            i++;
//
//            return NULL;
//
//        }
//
//         
//
//        int sign = 1;
//
//        if (s[i] == '-') {
//
//            sign = -1;
//
//            i++;
//
//        }
//
//        int rt = 0;
//
//        while (isdigit(s[i])) {
//
//            rt = rt * 10 + s[i] - '0';
//
//            i++;
//
//        }
//
//        i++;
//
//         
//
//        TreeNode *root = new TreeNode(rt * sign);
//
//        root->left = helper(s,i);
//
//        root->right = helper(s,i);
//
//        return root;
//
//    }
//
//
//    // Decodes your encoded data to tree.
//
//    TreeNode* deserialize(string data) {
//
//        int i = 0;
//
//        return helper(data,i);
//
//    }
//X. BFS
//https://leetcode.com/discuss/73461/short-and-straight-forward-bfs-java-code-with-a-queue
//Here I use typical BFS method to handle a binary tree. I use string n to represent null values. The string of the binary tree in the example will be "1 2 3 n n 4 5 n n n n ".
//When deserialize the string, I assign left and right child for each not-null node, and add the not-null children to the queue, waiting to be handled later.
//public String serialize(TreeNode root) {
//    if (root == null) return "";
//    Queue<TreeNode> q = new LinkedList<>();
//    StringBuilder res = new StringBuilder();
//    q.add(root);
//    while (!q.isEmpty()) {
//        TreeNode node = q.poll();
//        if (node == null) {
//            res.append("n ");
//            continue;
//        }
//        res.append(node.val + " ");
//        q.add(node.left);
//        q.add(node.right);
//    }
//    return res.toString();
//}
//public TreeNode deserialize(String data) {
//    if (data == "") return null;
//    Queue<TreeNode> q = new LinkedList<>();
//    String[] values = data.split(" ");
//    TreeNode root = new TreeNode(Integer.parseInt(values[0]));
//    q.add(root);
//    for (int i = 1; i < values.length; i++) {
//        TreeNode parent = q.poll();
//        if (!values[i].equals("n")) {
//            TreeNode left = new TreeNode(Integer.parseInt(values[i]));
//            parent.left = left;
//            q.add(left);
//        }
//        if (!values[++i].equals("n")) {
//            TreeNode right = new TreeNode(Integer.parseInt(values[i]));
//            parent.right = right;
//            q.add(right);
//        }
//    }
//    return root;
//}
//https://leetcode.com/discuss/70853/recursive-dfs-iterative-dfs-and-bfs
//public String serialize(TreeNode root) {
//    if (root==null) return "";
//    Queue<TreeNode> qu=new LinkedList<>();
//    StringBuilder sb=new StringBuilder();
//    qu.offer(root);
//    sb.append(String.valueOf(root.val));
//    sb.append(' ');
//    while (!qu.isEmpty()) {
//        TreeNode x=qu.poll();
//        if (x.left==null) sb.append("null ");
//        else {
//            qu.offer(x.left);
//            sb.append(String.valueOf(x.left.val));
//            sb.append(' ');
//        }
//        if (x.right==null) sb.append("null ");
//        else {
//            qu.offer(x.right);
//            sb.append(String.valueOf(x.right.val));
//            sb.append(' ');
//        }
//    }
//    return sb.toString();
//}
//public TreeNode deserialize(String data) {
//    if (data.length()==0) return null;
//    String[] node=data.split(" ");
//    Queue<TreeNode> qu=new LinkedList<>();
//    TreeNode root=new TreeNode(Integer.valueOf(node[0]));
//    qu.offer(root);
//    int i=1;
//    while (!qu.isEmpty()) {
//        Queue<TreeNode> nextQu=new LinkedList<>();
//        while (!qu.isEmpty()) {
//            TreeNode x=qu.poll();
//            if (node[i].equals("null")) x.left=null;
//            else {
//                x.left=new TreeNode(Integer.valueOf(node[i]));
//                nextQu.offer(x.left);
//            }
//            i++;
//            if (node[i].equals("null")) x.right=null;
//            else {
//                x.right=new TreeNode(Integer.valueOf(node[i]));
//                nextQu.offer(x.right);
//            }
//            i++;
//        }
//        qu=nextQu;
//    }
//    return root;
//}
//http://www.cnblogs.com/EdwardLiu/p/4391418.html
//Serialization 和 Deserialization都是用BFS, Serialization注意要删除String末尾多余的“#”, Deserialization维护一个count指示当前TreeNode对应的值
//    public String serialize(TreeNode root) {
// 8         // write your code here
// 9         StringBuffer res = new StringBuffer();
//10         if (root == null) return res.toString();
//11         LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
//12         queue.offer(root);
//13         res.append(root.val);
//14         while (!queue.isEmpty()) {
//15             TreeNode cur = queue.poll();
//16             if (cur.left != null)   queue.offer(cur.left); //add children to the queue
//17             if (cur.right != null)  queue.offer(cur.right);
//18             res.append(",");
//19             if (cur.left != null) {
//20                 res.append(cur.left.val);
//21             }
//22             else res.append("#");
//23             res.append(",");
//24             if (cur.right != null) {
//25                 res.append(cur.right.val);
//26             }
//27             else res.append("#");
//28         }
//29         int i = res.length()-1;
//30         while (i>=0 && res.charAt(i)=='#') {
//31             res.deleteCharAt(i);
//32             res.deleteCharAt(i-1);
//33             i -= 2;
//34         }
//35         return res.toString();
//36     }
//
//45     public TreeNode deserialize(String data) {
//46         // write your code here
//47         if (data==null || data.length()==0) return null;
//48         String[] arr = data.split(",");
//49         int len = arr.length;
//50         int count = 0;
//51         TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
//52         LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
//53         queue.offer(root);
//54         count++;
//55         while (!queue.isEmpty()) {
//56             TreeNode cur = queue.poll();
//57             String left="", right="";
//58             if (count < len) {
//59                 left = arr[count];
//60                 count++;
//61                 if (!left.equals("#")) {
//62                     cur.left = new TreeNode(Integer.parseInt(left));
//63                     queue.offer(cur.left);
//64                 }
//65                 else cur.left = null;
//66             }
//67             else cur.left = null;
//68             
//69             if (count < len) {
//70                 right = arr[count];
//71                 count++;
//72                 if (!right.equals("#")) {
//73                     cur.right = new TreeNode(Integer.parseInt(right));
//74                     queue.offer(cur.right);
//75                 }
//76                 else cur.right = null;
//77             }
//78             else cur.right = null;
//79         }
//80         return root;
//81     }
//http://hehejun.blogspot.com/2015/01/lintcodeserialization-and.html
//二叉树的序列化反序列化。我是按照题目描述的BFS的方法来做的，当然还会有其他序列化的方法，不过在这里只讨论BFS的方法。BFS序列化的表示方法是，按照一层一层的顺序在string尾巴上加字符，null的话用#表示，扫到最后一层为止，也就是说最后一层的地下的null不会出现最后序列化的结果当中。
//方法就是BFS的变种，BFS的时候把null也加入list中，那么这个时候就要设定一个变量来判断是不是到了最后一层。反序列的化的时候也是用BFS，注意反序列化的时候最好先按照","spliit成string数组，否则处理字符串的时候不方便处理。值得一提的是BFS用一个list就可以了，比起之前用两个代码会简洁很多。
//
//public String serialize(TreeNode root) {  if (root == null)
//return "#";
//String res = "";
//LinkedList<TreeNode> parents = new LinkedList<TreeNode>();
//parents.add(root);
//boolean end = false;
//while (!end) {
//end = true;
//int size = parents.size();
//for (int i = 0; i < size; i++) {
//TreeNode temp = parents.removeFirst();
//String s = temp == null? "#": temp.val + "";
//res = res.length() == 0? res + s: res + "," + s;
//if (temp != null) {
//if (!isLeaf(temp))
//end = false;
//parents.add(temp.left);
//parents.add(temp.right);
//}
//}
//}
//return res;
//}
///**
// * This method will be invoked second, the argument data is what exactly
// * you serialized at method "serialize", that means the data is not given by
// * system, it's given by your own serialize method. So the format of data is
// * designed by yourself, and deserialize it here as you serialize it in 
// * "serialize" method.
// */
//public TreeNode deserialize(String data) {
//if (data == null)
//return null;
//if (data.length() == 0)
//return null;
//if (data.equals("#"))
//return null;
//String[] strs = data.split(",");
//int len = strs.length;
//TreeNode root = new TreeNode(Integer.parseInt(strs[0]));
//LinkedList<TreeNode> parents = new LinkedList<TreeNode>();
//parents.add(root);
//int i = 1;
//while (i < len) {
//int size = parents.size();
//for (int j = 0; j < size; j++) {
//TreeNode temp = parents.removeFirst();
//temp.left = strs[i].equals("#")? null: new TreeNode(Integer.parseInt(strs[i]));
//i++;
//temp.right = strs[i].equals("#")? null: new TreeNode(Integer.parseInt(strs[i]));
//i++;
//if (temp.left != null)
//parents.add(temp.left);
//if (temp.right != null)
//parents.add(temp.right);
//}
//}
//return root;
//}
//X. 序列化方式2（使用字典 + json）：
//http://bookshadow.com/weblog/2015/10/26/leetcode-serialize-and-deserialize-binary-tree/
//[]
//null
//Empty tree. The root is a reference to NULL (C/C++), null (Java/C#/Javascript), None (Python), or nil (Ruby).
//
//[1,2,3]
//{"r": {"v": 3}, "l": {"v": 2}, "v": 1}
//       1
//      / \
//     2   3
//
//[1,null,2,3]
//{"r": {"l": {"v": 3}, "v": 2}, "v": 1}
//       1
//        \
//         2
//        /
//       3
//class Codec:
//
//    def serialize(self, root):
//        if not root:
//            return 'null'
//        nodes = collections.deque([root])
//        maps = collections.deque([{'v' : root.val}])
//        tree = maps[0]
//        while nodes:
//            frontNode = nodes.popleft()
//            frontMap = maps.popleft()
//            if frontNode.left:
//                frontMap['l'] = {'v' : frontNode.left.val}
//                nodes.append(frontNode.left)
//                maps.append(frontMap['l'])
//            if frontNode.right:
//                frontMap['r'] = {'v' : frontNode.right.val}
//                nodes.append(frontNode.right)
//                maps.append(frontMap['r'])
//        return json.dumps(tree)
//
//    def deserialize(self, data):
//        tree = json.loads(data)
//        if not tree:
//            return None
//        root = TreeNode(tree['v'])
//        maps = collections.deque([tree])
//        nodes = collections.deque([root])
//        while nodes:
//            frontNode = nodes.popleft()
//            frontMap = maps.popleft()
//            left, right = frontMap.get('l'), frontMap.get('r')
//            if left:
//                frontNode.left = TreeNode(left['v'])
//                maps.append(left)
//                nodes.append(frontNode.left)
//            if right:
//                frontNode.right = TreeNode(right['v'])
//                maps.append(right)
//                nodes.append(frontNode.right)
//        return root
//        
//使用json + tuple也可以，参考下面这段简洁的代码，作者：StefanPochmann
//class Codec:
//
//    def serialize(self, root):
//        def tuplify(root):
//            return root and (root.val, tuplify(root.left), tuplify(root.right))
//        return json.dumps(tuplify(root))
//
//    def deserialize(self, data):
//        def detuplify(t):
//            if t:
//                root = TreeNode(t[0])
//                root.left = detuplify(t[1])
//                root.right = detuplify(t[2])
//                return root
//        return detuplify(json.loads(data))
//
//http://www.cnblogs.com/grandyang/p/4913869.html
//Read full article from Serialize and Deserialize Binary Tree | LeetCode OJ
// * @author het
// *
// */
public class LeetCode297SerializeandDeserializeBinaryTree {
	class TreeNode{  
	    int val;  
	    TreeNode left, right;  
	    TreeNode(int val){  
	        this.val = val;  
	    }  
	}  
	   
	public String serialize(TreeNode root){  
	    StringBuilder sb = new StringBuilder();  
	    serialize(root, sb);  
	    return sb.toString();  
	}  
	// Use StringBuilder =>
	private void serialize(TreeNode x, StringBuilder sb){  
	    if (x == null) {  
	        sb.append("# ");  
	    } else {  
	        sb.append(x.val + " ");  
	        serialize(x.left, sb);  
	        serialize(x.right, sb);  
	    }  
	}  
	   
	public TreeNode deserialize(String s){  
	    if (s == null || s.length() == 0) return null;  
	    StringTokenizer st = new StringTokenizer(s, " ");  
	    return deserialize(st);  
	}  
	   
	private TreeNode deserialize(StringTokenizer st){  
	    if (!st.hasMoreTokens())  
	        return null;  
	    String val = st.nextToken();  
	     if (val.equals("#"))  
	        return null; 
	    TreeNode root = new TreeNode(Integer.parseInt(val));  
	    root.left = deserialize(st);  
	    root.right = deserialize(st);  
	    return root;  
	}  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] x = "66 # # ".split(" ");
		for(String xs: x)
		System.out.println(xs+","+x.length);

	}

}
