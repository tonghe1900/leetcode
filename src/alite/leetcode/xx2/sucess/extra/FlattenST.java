package alite.leetcode.xx2.sucess.extra;
/**
 * 感觉一面挂了，这道题没见过没见过没见过，这道题怎么也是hard吧？只有时间做一道题，用惨痛经历求大米求大米求大米，求安慰. from: 1point3acres.com/bbs 


Here is a struct definition for a node that works for both binary trees and doubly-linked lists:. 1point3acres.com/bbs
. 1point 3acres 璁哄潧
class Node {
  public string data;
  public Node left;
  public Node right;
}

Given a binary tree made of these nodes, convert it, in-place (i.e. don't allocate new Nodes), into a circular
 doubly linked list in the same order. That is, a traversal of the linked list and an inorder traversal of the tree 
 should yield the elements in the same order. You should return the head of the linked list.. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
Stupid ASCII art diagram. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴

Input
    A
   /\
  B  C
/\   \
D  E   F

a - b - d - e - c-f
Output

-> D<=>B<=>E<=>A<=>C<=>F
   ^                   ^
   |___________________|
    (circular)

 * @author het
 *
 */

class Node {
	  public int val;
	  public Node left;
	  public Node right;
	  
	  Node(int val){
		  this.val = val;
		  
	  }
	}
public class FlattenST {
	
	
	public static void main(String [] args){
		int []  arr = {1,2,3,4,5,6,7, 8, 9};
		Node root = createBST(arr, 0, arr.length-1);
		preOrder(root);
		Pointers head = transformToPreorderSinglyLinkList(root);
		Node runnerRight =  head.head;
		//Node runnerLeft =  head.left;
		while(runnerRight != null){
			System.out.print(runnerRight.val+",");
			runnerRight = runnerRight.right;
		}
//		System.out.println();
//		while(true){
//			System.out.print(runnerLeft.val+",");
//			runnerLeft = runnerLeft.left;
//		}
		
	}
	
	private static void preOrder(Node root) {
		if(root == null) return;
		System.out.print(root.val);
		preOrder(root.left);
		preOrder(root.right);
		
	}

	private static  Node createBST(int[] arr, int start, int end){
		if(start > end) return null;
		int mid = (start + end)>>1;
		Node node = new Node(arr[mid]);
		node.left= createBST(arr, start, mid-1);
		node.right = createBST(arr, mid+1, end);
		return node;
	}
	
	static class Pointers{
		Node head;
		Node tail;
		Pointers(Node head, Node tail){
			this.head = head;
			this.tail = tail;
		}
	}
	
	public static  Pointers transformToPreorderSinglyLinkList(Node node){
		   if(node == null) return null;
		   Pointers leftPart = transformToPreorderSinglyLinkList(node.left);
		   
		   Pointers rightPart = transformToPreorderSinglyLinkList(node.right);
		   if(leftPart == null && rightPart == null){
			  
			   return new Pointers(node, node);
		   }else if(leftPart == null && rightPart != null){
			   node.right = rightPart.head;
			   return new Pointers(node, rightPart.tail);
			   
		   }else if(leftPart != null && rightPart == null){
			  node.right = leftPart.head;
			  
			   return new Pointers(node, leftPart.tail);
		   }else{
			   node.right = leftPart.head;
			   leftPart.tail.right = rightPart.head;
			   return new Pointers(node, rightPart.tail);
		   }
		   
		   
	   }
	
	public static  Node transformToPreorderLinkList(Node node){
		   if(node == null) return null;
		   Node leftPart = transformToPreorderLinkList(node.left);
		   
		   Node rightPart = transformToPreorderLinkList(node.right);
		   if(leftPart == null && rightPart == null){
			   node.right = node;
			   node.left = node;
			   return node;
		   }else if(leftPart == null && rightPart != null){
			   node.right = rightPart;
			   Node rightPartTail = rightPart.left;
			   rightPart.left = node;
			   rightPartTail.right = node;
			   node.left = rightPartTail;
			   return node;
			   
		   }else if(leftPart != null && rightPart == null){
			   Node leftPartTail = leftPart.left;
			   node.right = leftPart;
			   leftPart.left = node;
			   leftPartTail.right = node;
			   node.left = leftPartTail;
			   return node;
		   }else{
			   Node rightPartTail = rightPart.left;
			   Node leftPartTail = leftPart.left;
			   node.right = leftPart;
			   leftPart.left = node;
			   leftPartTail.right = rightPart;
			   rightPart.left = leftPartTail;
			   rightPartTail.right = node;
			   node.left = rightPartTail;
			   return node;
		   }
		   
		   
	   }
	
   public static  Node transformToLinkList(Node node){
	   if(node == null) return null;
	   Node leftPart = transformToLinkList(node.left);
	   
	   Node rightPart = transformToLinkList(node.right);
	   if(leftPart == null && rightPart == null){
		   node.right = node;
		   node.left = node;
		   return node;
	   }else if(leftPart == null && rightPart != null){
		   node.right = rightPart;
		   Node rightPartTail = rightPart.left;
		   rightPart.left = node;
		   rightPartTail.right = node;
		   node.left = rightPartTail;
		   return node;
		   
	   }else if(leftPart != null && rightPart == null){
		   Node leftPartTail = leftPart.left;
		   leftPart.left = node;
		   node.right = leftPart;
		   leftPartTail.right = node;
		   node.left = leftPartTail;
		   return leftPart;
	   }else{
		   Node rightPartTail = rightPart.left;
		   Node leftPartTail = leftPart.left;
		   leftPartTail.right = node;
		   node.left = leftPartTail;
		   rightPart.left = node;
		   node.right = rightPart;
		   rightPartTail.right = leftPart;
		   leftPart.left = rightPartTail;
		   return leftPart;
	   }
	   
	   
   }
}
