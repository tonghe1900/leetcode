package alite.leetcode.xx2;

import java.util.Stack;

/**
 * 题目：

Given an array of numbers, verify whether it is the correct preorder traversal sequence of a binary search tree.

You may assume each number in the sequence is unique.

Follow up:
Could you do it using only constant space complexity?

链接： http://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/

题解：

使用Stack来模拟preorder traversal ->   mid, left, right。主要代码都是参考了Stefan Pochmann的解答。

Time Complexity - O(n)， Space Complexity - O(logn)

复制代码
复制代码
public class Solution {
    public boolean verifyPreorder(int[] preorder) {
        int low = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for(int i : preorder) {
            if(i < low)
                return false;
            while(!stack.isEmpty() && i > stack.peek())
                low = stack.pop();
            stack.push(i);
        }
        
        return true;
    }
}
复制代码
复制代码
 

不使用Stack，Space Complexity O(1)的解法， 利用了原数组

复制代码
复制代码
public class Solution {
    public boolean verifyPreorder(int[] preorder) {
        int low = Integer.MIN_VALUE, index = -1;
        for(int i : preorder) {
            if(i < low)
                return false;
            while(index >= 0 && i > preorder[index])
                low = preorder[index--];
            preorder[++index] = i;
        }
        
        return true;
    }
}
 * @author het
 *
 */


//while(!stack.isEmpty() && i > stack.peek())
//    low = stack.pop();
public class L255VerifyPreorderSequenceinBinarySearceTree {
	
	    public static boolean verifyPreorder(int[] preorder) {
	        int low = Integer.MIN_VALUE;
	        Stack<Integer> stack = new Stack<>();
	        for(int i : preorder) {
	            if(i < low)
	                return false;
	            while(!stack.isEmpty() && i > stack.peek())
	                low = stack.pop();
	            stack.push(i);
	        }
	        
	        return true;
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(verifyPreorder(new int[]{2, 1, 2, 3,1}));

	}

}
