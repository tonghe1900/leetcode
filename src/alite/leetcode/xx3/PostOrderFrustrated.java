package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import alite.leetcode.xx3.LeetCode315.TreeNode;

public class PostOrderFrustrated {
    public List postOrder(TreeNode root){
    	List list = new ArrayList();
    	if(root == null ) return list;
    	TreeNode prev = null;
    	
    	Stack<TreeNode> stack = new Stack<>();
    	stack.push(root);
    	while(!stack.isEmpty()){
    		TreeNode current = stack.peek();
    		
    		if(prev == null || prev.left == current || prev.right == current){
        		if(current.left != null){
        			stack.push(current.left);
        		}else if(current.right != null){
        			stack.push(current.right);
        		}else{
        			stack.pop();
        			list.add(current.val);
        		}
        	}else if(current.left == prev){
        		if(current.right != null){
        			stack.push(current.right);
        		}else{
        			stack.pop();
        			list.add(current.val);
        		}
        	}else if(current.right == prev){
        		stack.pop();
    			list.add(current.val);
        	}
    		prev= current;
    	}
    	
    	return list;//////d//
    	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
