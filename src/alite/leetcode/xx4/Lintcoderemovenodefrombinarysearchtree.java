package alite.leetcode.xx4;



import java.util.Iterator;
import java.util.NoSuchElementException;





/**
 * LeetCode 450 - Delete Node in a BST
Lintcode: remove node from binary search tree | codesolutiony
Given a root of Binary Search Tree with unique value for each node. Remove the node with given value. 
If there is no such a node with given value in the binary search tree, do nothing. 
You should keep the tree still a binary search tree after removal.
Given binary search tree:
          5
       /    \
    3          6
/    \
2       4
Remove 3, you can either return:
          5
       /    \
    2          6
      \
         4
or :
          5
       /    \
    4          6
/  
2
http://hehejun.blogspot.com/2015/01/lintcoderemove-node-in-binary-search.html
删除的时候三种情况：
没有子节点
有一个子节点
有两个子节点

三种情况和相应的删除方法如下图所示：

























1) find the node with the given value;
2) if left subtree is NULL, return the right subtree
3) if right subtree is NULL, return the left subtree
4) find and delete the maximum node in the left subtree,  and replace value( If we want to actually delete the node, that will be more complicated.)


public TreeNode removeNode(TreeNode root, int value) {


if (root == null)


return null;


if (value < root.val)


root.left = removeNode(root.left, value);


else if (value > root.val)


root.right = removeNode(root.right, value);


else {


if (root.left == null)


return root.right;


if (root.right == null)


return root.left;


TreeNode x = root;


root = findMin(root.right);


root.right = deleteMin(x.right);


root.left = x.left;


}


return root;


}

http://techinpad.blogspot.com/2015/05/lintcode-remove-node-in-binary-search.html
    TreeNode *getAndDeleteMax(TreeNode*parent, TreeNode* node)  
    {  
        while(node->right)  
        {  
            parent = node;  
            node = node->right;  
        }  
        if(parent->left == node) parent->left = node->left;  
        else parent->right = node->left;  
        return node;  
    }  
      
    TreeNode* removeNode(TreeNode* root, int value)   
    {  
        if(!root) return NULL;  
          
        if(root->val > value)  
        {  
            root->left = removeNode(root->left, value);  
        }  
        else if(root->val < value)  
        {  
            root->right = removeNode(root->right, value);  
        }  
        else  
        {  
            if(!root->left)  
            {  
                TreeNode * t = root->right;  
                root->right = NULL;  // not needed
                return t;  
            }  
            if(!root->right)  
            {  
                TreeNode *t = root->left;  
                root->left = NULL; // this is not needed, as already have root->left = removeNode(root->left, value);  
                return t;  
            }  
            TreeNode *leftMax = getAndDeleteMax(root, root->left);  
            root->val = leftMax->val;  
        }  
        return root;  
    } 
Too complex: http://www.jiuzhang.com/solutions/remove-node-in-binary-search-tree/
In some case, maybe it's good to have the dummy root node.
    public TreeNode removeNode(TreeNode root, int value) {
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        
        TreeNode parent = findNode(dummy, root, value);
        TreeNode node;
        if (parent.left != null && parent.left.val == value) {
            node = parent.left;
        } else if (parent.right != null && parent.right.val == value) {
            node = parent.right;
        } else {
            return dummy.left;
        }
        
        deleteNode(parent, node);
        
        return dummy.left;
    }
    
    private TreeNode findNode(TreeNode parent, TreeNode node, int value) {
        if (node == null) {
            return parent;
        }
        
        if (node.val == value) {
            return parent;
        }
        if (value < node.val) {
            return findNode(node, node.left, value);
        } else {
            return findNode(node, node.right, value);
        }
    }
    
    private void deleteNode(TreeNode parent, TreeNode node) {
        if (node.right == null) {
            if (parent.left == node) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        } else {
            TreeNode temp = node.right;
            TreeNode father = node;
            
            while (temp.left != null) {
                father = temp;
                temp = temp.left;
            }
            
            if (father.left == temp) {
                father.left = temp.right;
            } else {
                father.right = temp.right;
            }
            
            if (parent.left == node) {
                parent.left = temp;
            } else {
                parent.right = temp;
            }
            
            temp.left = node.left;
            temp.right = node.right;
        }
    }
Iterative Version:
https://codesolutiony.wordpress.com/2015/01/31/lintcode-remove-node-from-binary-search-tree/

    public TreeNode removeNode(TreeNode root, int value) {

        TreeNode dummy = new TreeNode(-1), cur = root, pre = dummy;

        dummy.right = root;

        while (cur != null) {

            if (cur.val == value) {

                if (pre.right == cur) {

                    pre.right = makeNew(cur);

                } else {

                    pre.left = makeNew(cur);

                }

                break;

            } else if (cur.val < value) {

                pre = cur;

                cur = cur.right;

            } else {

                pre = cur;

                cur = cur.left;

            }

        }

        return dummy.right;

    }

     

    private TreeNode makeNew(TreeNode node) { // better method name

        if (node.left == null && node.right == null) {

            return null;

        }

        if (node.left == null || node.right == null) {

            return node.left == null ? node.right : node.left;

        }

        TreeNode left = node.left.right;

        TreeNode leftMost = node.right; //the left most node of right child

        while (leftMost.left != null) {

            leftMost = leftMost.left;

        }

        leftMost.left = left;//appen left's right tree to right's left most node

        node.left.right = node.right;

        return node.left;

    }

Read full article from Lintcode: remove node from binary search tree | codesolutiony
 * @author het
 *
 */


 class Queue<Item> implements Iterable<Item> {
    private int N;               // number of elements on queue
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty queue.
     */
    public Queue() {
        first = null;
        last  = null;
        N = 0;
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;     
    }

    /**
     * Returns the item least recently added to this queue.
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldlast.next = last;
        N++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


 class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

   /***********************************************************************
    *  Search BST for given key, and return associated value if found,
    *  return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

   /***********************************************************************
    *  Insert key-value pair into BST
    *  If key already exists, update with new value
    ***********************************************************************/
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
        
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

   /***********************************************************************
    *  Delete
    ***********************************************************************/

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
       
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
       
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    } 


   /***********************************************************************
    *  Min, max, floor, and ceiling
    ***********************************************************************/
    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Key max() {
        if (isEmpty()) return null;
        return max(root).key;
    } 

    private Node max(Node x) { 
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    } 

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp <  0) return floor(x.left, key);
        Node t = floor(x.right, key); 
        if (t != null) return t;
        else return x; 
    } 

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) { 
            Node t = ceiling(x.left, key); 
            if (t != null) return t;
            else return x; 
        } 
        return ceiling(x.right, key); 
    } 

   /***********************************************************************
    *  Rank and selection
    ***********************************************************************/
    public Key select(int k) {
        if (k < 0 || k >= size()) return null;
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k. 
    private Node select(Node x, int k) {
        if (x == null) return null; 
        int t = size(x.left); 
        if      (t > k) return select(x.left,  k); 
        else if (t < k) return select(x.right, k-t-1); 
        else            return x; 
    } 

    public int rank(Key key) {
        return rank(key, root);
    } 

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

   /***********************************************************************
    *  Range count and range search.
    ***********************************************************************/
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }


    // height of this BST (one-node tree has height 0)
    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    // level order traversal
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

 

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.N != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }
public class Lintcoderemovenodefrombinarysearchtree {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}}
 }
