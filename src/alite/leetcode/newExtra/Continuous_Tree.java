package alite.leetcode.newExtra;
/**
 * http://www.geeksforgeeks.org/continuous-tree
A tree is Continuous tree if in each root to leaf path, absolute difference between keys of two adjacent is 1. 
We are given a binary tree, we need to check if tree is continuous or not.
The solution requires a traversal of tree. The important things to check are to make sure that all corner cases 
are handled. The corner cases include, empty tree, single node tree, a node with only left child and a node with 
only right child.
In tree traversal, we recursively check if left and right subtree are continuous. We also check if 
difference between keys of current node’s key and its children keys is 1. 

bool treeContinuous(struct Node *ptr)

{

    // if next node is empty then return true

    if (ptr == NULL)

        return true;

 

    // if current node is leaf node then return true

    // because it is end of root to leaf path

    if (ptr->left == NULL && ptr->right == NULL)

        return true;

 

    // If left subtree is empty, then only check right

    if (ptr->left == NULL)

       return (abs(ptr->data - ptr->right->data) == 1) &&

              treeContinuous(ptr->right);

 

    // If right subtree is empty, then only check left

    if (ptr->right == NULL)

       return (abs(ptr->data - ptr->left->data) == 1) &&

              treeContinuous(ptr->left);

 

    // If both left and right subtrees are not empty, check

    // everything

    return  abs(ptr->data - ptr->left->data)==1 &&

            abs(ptr->data - ptr->right->data)==1 &&

            treeContinuous(ptr->left) &&

            treeContinuous(ptr->right);

}
 * @author het
 *
 */
public class Continuous_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
