package alite.leetcode.xx2.sucess.before;
/**
 * LeetCode 222 - Count Complete Tree Nodes

[LeetCode]Count Complete Tree Nodes | 书影博客
Given a complete binary tree, count the number of nodes.
Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2^h nodes inclusive at the last level h.
解法II：递归: Time complexity is O(h^2) - O(log(n)^2)
https://discuss.leetcode.com/topic/15533/concise-java-solutions-o-log-n-2
https://leetcode.com/discuss/38899/easy-short-c-recursive-solution
Let n be the total number of the tree. It is likely that you will get a child tree as a perfect binary tree and a non-perfect binary tree (T(n/2)) at each level.
T(n) = T(n/2) + c1 lgn
       = T(n/4) + c1 lgn + c2 (lgn - 1)
       = ...
       = T(1) + c [lgn + (lgn-1) + (lgn-2) + ... + 1]
       = O(lgn*lgn)  
    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int h = height(root);
        return h < 0 ? 0 :
               height(root.right) == h-1 ? (1 << h) + countNodes(root.right)
                                         : (1 << h-1) + countNodes(root.left);
    }
Explanation
The height of a tree can be found by just going left. Let a single node tree have height 0. Find the height h of the whole tree. If the whole tree is empty, i.e., has height -1, there are 0 nodes.
Otherwise check whether the height of the right subtree is just one less than that of the whole tree, meaning left and right subtree have the same height.
If yes, then the last node on the last tree row is in the right subtree and the left subtree is a full tree of height h-1. So we take the 2^h-1 nodes of the left subtree plus the 1 root node plus recursively the number of nodes in the right subtree.
If no, then the last node on the last tree row is in the left subtree and the right subtree is a full tree of height h-2. So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus recursively the number of nodes in the left subtree.
Since I halve the tree in every recursive step, I have O(log(n)) steps. Finding a height costs O(log(n)). So overall O(log(n)^2).

Here's an iterative version as well, with the benefit that I don't recompute h in every step.

    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int nodes = 0, h = height(root);
        while (root != null) {
            if (height(root.right) == h - 1) {
                nodes += 1 << h;
                root = root.right;
            } else {
                nodes += 1 << h-1;
                root = root.left;
            }
            h--;
        }
        return nodes;
    }
https://discuss.leetcode.com/topic/31457/a-very-clear-recursive-solution-isn-t-it
https://discuss.leetcode.com/topic/18508/accepted-clean-java-solution/2
  public int countNodes(TreeNode root) {
    if (root == null)
      return 0;
            
    int hLeft = getHeight(root.left);
    int hRight = getHeight(root.right);
        
    if (hLeft == hRight)
      return (1 << hLeft) + countNodes(root.right);
    else
      return (1 << hRight) + countNodes(root.left);
    
  }
    
  int getHeight(TreeNode root) {
    if (root == null)
      return 0;
        
    return 1 + getHeight(root.left);
  }
https://discuss.leetcode.com/topic/15525/concise-java-iterative-solution-o-logn-2

 private int getHeight(TreeNode root, UnaryOperator<TreeNode> nextNode) {
  return root==null?0:1+getHeight(nextNode.apply(root),nextNode);
 }
X. 
https://discuss.leetcode.com/topic/21317/accepted-easy-understand-java-solution/
Two cases:
when it's a full binary tree, will be the easiest case: just return (1 << leftHeight) -1
when it's not full binary tree we'll calculate its left and right subtree nodes recursively and then plus one (root node).
One more comment: I'd like to add one more line:
if(root == null) return 0;
at the beginning of your countNodes(TreeNode root) function, it at least helps me understand it better, basically, that line serves as the base case when the countNodes() function exits. Otherwise, it's not super clear to me when countNodes() reaches null, how it's going to return.
public int countNodes(TreeNode root) {

    int leftDepth = leftDepth(root);
 int rightDepth = rightDepth(root);

 if (leftDepth == rightDepth)
  return (1 << leftDepth) - 1;
 else
  return 1+countNodes(root.left) + countNodes(root.right);

}

private int rightDepth(TreeNode root) {
 // TODO Auto-generated method stub
 int dep = 0;
 while (root != null) {
  root = root.right;
  dep++;
 }
 return dep;
}

private int leftDepth(TreeNode root) {
 // TODO Auto-generated method stub
 int dep = 0;
 while (root != null) {
  root = root.left;
  dep++;
 }
 return dep;
}

A Different Solution - 544 ms
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        TreeNode left = root, right = root;
        int height = 0;
        while (right != null) {
            left = left.left;
            right = right.right;
            height++;
        }
        if (left == null)
            return (1 << height) - 1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
Note that that's basically this:
public int countNodes(TreeNode root) {
    if (root == null)
        return 0;
    return 1 + countNodes(root.left) + countNodes(root.right)

That would be O(n). But... the actual solution has a gigantic optimization. It first walks all the way left and right to determine the height and whether it's a full tree, meaning the last row is full. If so, then the answer is just 2^height-1. And since always at least one of the two recursive calls is such a full tree, at least one of the two calls immediately stops. Again we have runtime O(log(n)^2).
the recurrence relation can be expressed as T(n) = 1 * T(n/ 2) + log(n).
log(n) is not Ω(nc) for c>0, as you can see here. You need to use case 2.


如果当前子树的“极左节点”（从根节点出发一路向左）与“极右节点”（从根节点出发一路向右）的高度h相同，则当前子树为满二叉树，返回2^h - 1
否则，递归计算左子树与右子树的节点个数。
https://leetcode.com/discuss/38899/easy-short-c-recursive-solution
    int countNodes(TreeNode* root) {
        if (!root) return 0;
        int hl = 0, hr = 0;
        TreeNode *l = root, *r = root;
        while(l) {hl++; l = l->left;}
        while(r) {hr++; r = r->right;}
        if (hl == hr) return pow(2, hl) - 1;
        return 1 + countNodes(root->left) + countNodes(root->right);
    }
http://www.programcreek.com/2014/06/leetcode-count-complete-tree-nodes-java/
1) get the height of left-most part
2) get the height of right-most part
3) when they are equal, the # of nodes = 2^h -1
4) when they are not equal, recursively get # of nodes from left&right sub-trees
count-complete-tree-nodes
count-complete-tree-nodes-2
Time complexity is O(h^2).
public int countNodes(TreeNode root) {
    if(root==null)
        return 0;
 
    int left = getLeftHeight(root)+1;    
    int right = getRightHeight(root)+1;
 
    if(left==right){
        return (2<<(left-1))-1;
    }else{
        return countNodes(root.left)+countNodes(root.right)+1;
    }
}
 
public int getLeftHeight(TreeNode n){
    if(n==null) return 0;
 
    int height=0;
    while(n.left!=null){
        height++;
        n = n.left;
    }
    return height;
}
 
public int getRightHeight(TreeNode n){
    if(n==null) return 0;
 
    int height=0;
    while(n.right!=null){
        height++;
        n = n.right;
    }
    return height;
}
http://segmentfault.com/a/1190000003818177
X. 递归树高法
完全二叉树的一个性质是，如果左子树最左边的深度，等于右子树最右边的深度，说明这个二叉树是满的，即最后一层也是满的，则以该节点为根的树其节点一共有2^h-1个。如果不等于，则是左子树的节点数，加上右子树的节点数，加上自身这一个。

这里在左节点递归时代入了上次计算的左子树最左深度减1，右节点递归的时候代入了上次计算的右子树最右深度减1，可以避免重复计算这些深度
做2的幂时不要用Math.pow，这样会超时。用1<<height这个方法来得到2的幂
    public int countNodes(TreeNode root) {
        return countNodes(root, -1, -1);
    }
    
    private int countNodes(TreeNode root, int lheight, int rheight){
        // 如果没有上轮计算好的左子树深度，计算其深度
        if(lheight == -1){
            lheight = 0;
            TreeNode curr = root;
            while(curr != null){
                lheight++;
                curr = curr.left;
            }
        }
        // 如果没有上轮计算好的右子树深度，计算其深度
        if(rheight == -1){
            rheight = 0;
            TreeNode curr = root;
            while(curr != null){
                rheight++;
                curr = curr.right;
            }
        }
        // 如果两个深度一样，返回2^h-1
        if(lheight == rheight) return (1 << lheight) - 1;
        // 否则返回左子树右子树节点和加1
        return 1 + countNodes(root.left, lheight - 1, -1) + countNodes(root.right, -1, rheight - 1);
    }
用迭代法的思路稍微有点不同，因为不再是递归中那样的分治法，我们迭代只有一条正确的前进方向。所以，我们判断的时左节点的最左边的深度，和右节点的最左边深度。因为最后一层结束的地方肯定在靠左的那侧，所以我们要找的就是这个结束点。如果两个深度相同，说明左子树和右子树都是满，结束点在右侧，如果右子树算出的最左深度要小一点，说明结束点在左边，右边已经是残缺的了。根据这个大小关系，我们可以确定每一层的走向，最后找到结束点。另外，还要累加每一层的节点数，最后如果找到结束点，如果结束点是左节点，就多加1个，如果结束点是右节点，就多加2个。

注意

同样的，记录上次计算的最左深度，可以减少一些重复计算
用int记录上次最左深度更快，用Integer则会超时
public class Solution {
    public int countNodes(TreeNode root) {
        int res = 0;
        Integer lheight = null, rheight = null;
        while(root != null){
            // 得到左节点的最左深度
            int leftH = getH(root.left);
            // 得到右节点的最左深度
            int rightH = getH(root.right);
            // 左节点的最左深度大，说明右边已经残缺，结束点在左边
            if(leftH > rightH){
                if(rightH != 0) res += 1 << rightH;
                else return res + 2;
                root = root.left;
            // 否则说明结束点在右边
            } else {
                if(leftH != 0) res += 1 << leftH;
                else return res + 1;
                root = root.right;
            }
        }
        return res;
    }
    
    private int getH(TreeNode root){
        int h = 0;
        while(root != null){
            ++h;
            root = root.left;
        }
        return h;
    }
}

public class Solution {
    public int countNodes(TreeNode root) {
        int res = 0;
        int lheight = -1, rheight = -1;
        while(root != null){
            // 如果没有上次记录的左边最左深度，重新计算
            if(lheight == -1){
                TreeNode curr = root.left;
                lheight = 0;
                while(curr != null){
                    curr = curr.left;
                    lheight++;
                }    
            }
            // 如果没有上次记录的右边最左深度，重新计算
            if(rheight == -1){
                TreeNode curr = root.right;
                rheight = 0;
                while(curr != null){
                    curr = curr.left;
                    rheight++;
                }
            }
            // 深度相同，说明结束点在右边
            if(lheight == rheight){
                // 如果是不是最后一个节点，累加这一层的节点
                if(lheight != 0){
                    res += 1 << lheight;
                } else {
                // 如果是最后一个节点，结束点在右边意味着右节点是缺失的，返回res+1
                    return res + 1;
                }
                root = root.right;
                lheight = rheight - 1;
                rheight = -1;
            // 否则结束点在左边
            } else {
                // 如果是不是最后一个节点，累加这一层的节点
                if(rheight != 0){
                    res += 1 << rheight;
                } else{
                // 如果是最后一个节点，返回res+2
                    return res + 2;
                }
                root = root.left;
                lheight = lheight - 1;
                rheight = -1;
            }
        }
        return res;
    }
}
http://shibaili.blogspot.com/2015/06/day-112-count-complete-tree-nodes.html
iterative
检查右子树是不是perfect tree，如果是，则缺口在左子树，此时加上右边的个数。
如果不是，则缺口在右子树，此时加上左边的个数
此题关键在于对高度的判断，然后对树的分解

    int getHeight(TreeNode *root) {

        int height = 0;

        while (root != NULL) {

            height++;

            root = root->left;

        }

         

        return height;

    }


    int countNodes(TreeNode* root) {

        int height = getHeight(root);

        int count = 0;

        while (root != NULL) {

            if (getHeight(root->right) == height - 1) {

                count += 1 << height - 1;

                root = root->right;

            }else {

                count += 1 << height - 2;

                root = root->left;

            }

             

            height--;

        }

         

        return count;

    }
解法I：二分枚举
https://discuss.leetcode.com/topic/31392/my-java-solution-with-explanation-which-beats-99/2
Basically my solution contains 2 steps.
(1) Firstly, we need to find the height of the binary tree and count the nodes above the last level.
(2) Then we should find a way to count the nodes on the last level.
Here I used a kind of binary search. We define the "midNode" of the last level as a node following the path "root->left->right->right->...->last level".
If midNode is null, then it means we should count the nodes on the last level in the left subtree.
If midNode is not null, then we add half of the last level nodes to our result and then count the nodes on the last level in the right subtree.
Of course I used some stop condition to make the code more efficient, e.g. when a tree has height 1, it means it only has 3 cases: 1. has right son; 2. only has left son; 3. has no son.
public int countNodes(TreeNode root) {
 if (root==null) return 0;
 if (root.left==null) return 1;
 int height = 0;
    int nodesSum = 0;
 TreeNode curr = root;
    while(curr.left!=null) {
     nodesSum += (1<<height);
     height++;
     curr = curr.left;
    }
    return nodesSum + countLastLevel(root, height);
}

private int countLastLevel(TreeNode root, int height) {
 if(height==1) 
  if (root.right!=null) return 2;
  else if (root.left!=null) return 1;
  else return 0;
 TreeNode midNode = root.left;
 int currHeight = 1;
 while(currHeight<height) {
  currHeight++;
  midNode = midNode.right;
 }
 if (midNode==null) return countLastLevel(root.left, height-1);
 else return (1<<(height-1)) + countLastLevel(root.right, height-1);
}
高度为h的完全二叉树，其节点个数等于高度为h-1的满二叉树的节点个数 + 最后一层的节点个数。
因此，只需要二分枚举第h层的节点个数即可。
例如高度为2，包含6个节点的完全二叉树：
Lv0        1 
         /    \
Lv1     2      3
       /  \   /  \
Lv2   4   5  6   -

No.   00  01 10
http://www.cnblogs.com/easonliu/p/4556487.html
假设最后一层在 h 层，那么一共有 2^(h-1) 个结点，一共需要 h - 1 位来编号，从根结点出发，向左子树走编号为 0， 向右子树走编号为 1，那么最后一层的编号正好从0 ~ 2^(h-1) - 1。复杂度为 O(h*log(2^(h-1))) = O(h^2)
    int countNodes(TreeNode* root) {
        int depth = 0;
        TreeNode* node = root;
        while (node) {
            depth++;
            node = node->left;
        }
        if (depth == 0) {
            return 0;
        }
        int left = 0, right = (1 << (depth - 1)) - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (getNode(root, mid, depth - 1)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (1 << (depth - 1)) + right;
    }
    TreeNode* getNode(TreeNode* root, int path, int depth) {
        while (depth-- && root) {
            if (path & (1 << depth)) {
                root = root->right;
            } else {
                root = root->left;
            }
        }
        return root;
    }
12     bool isOK(TreeNode *root, int h, int v) {
13         TreeNode *p = root;
14         for (int i = h - 2; i >= 0; --i) {
15             if (v & (1 << i)) p = p->right;
16             else p = p->left;
17         }
18         return p != NULL;
19     }
20     
21     int countNodes(TreeNode* root) {
22         if (root == NULL) return 0;
23         TreeNode *p = root;
24         int h = 0;
25         while (p != NULL) {
26             p = p->left;
27             ++h;
28         }
29         int l = 0, r = (1 << (h - 1)) - 1, m;
30         while (l <= r) {
31             m = l + ((r - l) >> 1);
32             if (isOK(root, h, m)) l = m + 1;
33             else r = m - 1;
34         }
35         return (1 << (h - 1)) + r;
36     }
http://techinpad.blogspot.com/2015/06/leetcode-count-complete-tree-nodes.html
 Analysis: We just need find where the node stops at the lowest level. To achieve that, we need to compare the depth of the node on the left or the right side of the contour. 
Don't understand it.
    int countNodes(int depth, TreeNode *root)  
    {  
        if(!root) return 0;  
        int res = 0;  
        int level = 1;  
        TreeNode* p = root->left;  
        for(;p;p = p->right) level ++;  
          
        if(level == depth)   
        {  
            res = pow(2, depth-1) + countNodes(depth-1, root->right);  
        }  
        else  
        {  
            res = pow(2, depth-2) + countNodes(depth-1,  root->left);  
        }  
        return res;  
    }  
    int countNodes(TreeNode* root) {  
        if(!root) return 0;  
        TreeNode *p = root;  
        int depth = 0;  
        while(p)  
        {  
            depth ++;  
            p = p->left;  
        }  
        return countNodes(depth, root);  
    }  
http://blog.csdn.net/u012925008/article/details/46398141
Read full article from [LeetCode]Count Complete Tree Nodes | 书影博客
 * @author het
 *
 */
//public class LeetCode222 {
//	when it's a full binary tree, will be the easiest case: just return (1 << leftHeight) -1
//	when it's not full binary tree we'll calculate its left and right subtree nodes recursively and then plus one (root node).
//	One more comment: I'd like to add one more line:
//	if(root == null) return 0;
//	at the beginning of your countNodes(TreeNode root) function, it at least helps me understand it better, basically, that line serves as the base case when the countNodes() function exits. Otherwise, it's not super clear to me when countNodes() reaches null, how it's going to return.
// memorization	
public int countNodes(TreeNode root) {

	    int leftDepth = leftDepth(root);
	 int rightDepth = rightDepth(root);

	 if (leftDepth == rightDepth)
	  return (1 << leftDepth) - 1;
	 else
	  return 1+countNodes(root.left) + countNodes(root.right);

	}

	private int rightDepth(TreeNode root) {
	 // TODO Auto-generated method stub
	 int dep = 0;
	 while (root != null) {
	  root = root.right;
	  dep++;
	 }
	 return dep;
	}

	private int leftDepth(TreeNode root) {
	 // TODO Auto-generated method stub
	 int dep = 0;
	 while (root != null) {
	  root = root.left;
	  dep++;
	 }
	 return dep;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
