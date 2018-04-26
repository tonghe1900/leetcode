package alite.leetcode.newExtra.L500.extra.finish;

import java.util.Arrays;
import java.util.Stack;

/**
 * LeetCode 503 - Next Greater Element II

https://leetcode.com/problems/next-greater-element-ii/
Given a circular array (the next element of the last element is the first element of the array),
 print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number
  to its traversing-order next in the array, which means you could search circularly to find its next greater number.
   If it doesn't exist, output -1 for this number.
Example 1:
Input: [1,2,1]
Output: [2,-1,2]
Explanation: The first 1's next greater number is 2; 
The number 2 can't find next greater number; 
The second 1's next greater number needs to search circularly, which is also 2.
Note: The length of given array won't exceed 10000.

https://discuss.leetcode.com/topic/77923/java-10-lines-and-c-12-lines-linear-time-complexity-o-n-with-explanation
The only difference here is that we use stack to keep the indexes of the decreasing subsequence
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length, next[] = new int[n];
        Arrays.fill(next, -1);
        Stack<Integer> stack = new Stack<>(); // index stack
        for (int i = 0; i < n * 2; i++) {
            int num = nums[i % n]; 
            while (!stack.isEmpty() && nums[stack.peek()] < num)
                next[stack.pop()] = num;
            if (i < n) stack.push(i);
        }   
        return next;
    }
    
    
     public int[] nextGreaterElements(int[] nums) {
        int n = nums.length, next[] = new int[n];
        Arrays.fill(next, -1);
        Stack<Integer> stack = new Stack<>(); // index stack
        for (int i = 0; i < n * 2; i++) {
            int num = nums[i % n]; 
            while (!stack.isEmpty() && nums[stack.peek()] < num)
                next[stack.pop()] = num;
            if (i < n) stack.push(i);
        }   
        return next;
    }
https://discuss.leetcode.com/topic/77881/typical-ways-to-solve-circular-array-problems-java-solution
The first typical way to solve circular array problems is to extend the original array to twice length, 2nd half has the same element as first half. Then everything become simple.
The second way is to use a stack to facilitate the look up. First we put all indexes into the stack, smaller index on the top. Then we start from end of the array look for the first element (index) in the stack which is greater than the current one. That one is guaranteed to be the Next Greater Element. Then put the current element (index) into the stack.

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            stack.push(i);
        }
        
        for (int i = n - 1; i >= 0; i--) {
            result[i] = -1;
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()){
                result[i] = nums[stack.peek()];
            }
            stack.add(i);
        }
        
        return result;
    }
X.
https://discuss.leetcode.com/topic/77871/short-ac-solution-and-fast-dp-solution-45ms
public int[] nextGreaterElements2(int[] nums) {
 int  n = nums.length;
 int[] res = new int[n];
 for (int i = 0; i < n; i++) {
  res[i] = -1;
  for (int k = i+1; k < i + n; k++) {
   if (nums[k%n] > nums[i]){
    res[i] = nums[k%n];
    break;
   }
  }
 }
 return res;
}
https://discuss.leetcode.com/topic/77881/typical-ways-to-solve-circular-array-problems-java-solution
    public int[] nextGreaterElements(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        
        int n = nums.length;
        int[] result = new int[n];
        int[] temp = new int[n * 2];
        
        for (int i = 0; i < n * 2; i++) {
            temp[i] = nums[i % n];
        }
        
        for (int i = 0; i < n; i++) {
            result[i] = -1;
            if (nums[i] == max) continue;
            
            for (int j = i + 1; j < n * 2; j++) {
                if (temp[j] > nums[i]) {
                    result[i] = temp[j];
                    break;
                }
            }
        }
        
        return result;
    }
 * @author het
 *
 */

//  3  4  5   1  0   -1   -2     //    x y   x < y    x >=y   //  3   1     2
public class L503_Next_Greater_ElementII {
//	public int[] nextGreaterElements(int[] nums) {
//        int max = Integer.MIN_VALUE;
//        for (int num : nums) {
//            max = Math.max(max, num);
//        }
//        
//        int n = nums.length;
//        int[] result = new int[n];
//        int[] temp = new int[n * 2];
//        
//        for (int i = 0; i < n * 2; i++) {
//            temp[i] = nums[i % n];
//        }
//        
//        for (int i = 0; i < n; i++) {
//            result[i] = -1;
//            if (nums[i] == max) continue;
//            
//            for (int j = i + 1; j < n * 2; j++) {
//                if (temp[j] > nums[i]) {
//                    result[i] = temp[j];
//                    break;
//                }
//            }
//        }
//        
//        return result;
//    }
//	
//	
//	
//	public int[] nextGreaterElements2(int[] nums) {
//		 int  n = nums.length;
//		 int[] res = new int[n];
//		 for (int i = 0; i < n; i++) {
//		  res[i] = -1;
//		  for (int k = i+1; k < i + n; k++) {
//		   if (nums[k%n] > nums[i]){
//		    res[i] = nums[k%n];
//		    break;
//		   }
//		  }
//		 }
//		 return res;
//		}
//	
//	
//	
//	
//	https://discuss.leetcode.com/topic/77923/java-10-lines-and-c-12-lines-linear-time-complexity-o-n-with-explanation
//		The only difference here is that we use stack to keep the indexes of the decreasing subsequence
//		    public int[] nextGreaterElements(int[] nums) {
//		        int n = nums.length, next[] = new int[n];
//		        Arrays.fill(next, -1);
//		        Stack<Integer> stack = new Stack<>(); // index stack
//		        for (int i = 0; i < n * 2; i++) {
//		            int num = nums[i % n]; 
//		            while (!stack.isEmpty() && nums[stack.peek()] < num)
//		                next[stack.pop()] = num;
//		            if (i < n) stack.push(i);
//		        }   
//		        return next;
//		    }
//		https://discuss.leetcode.com/topic/77881/typical-ways-to-solve-circular-array-problems-java-solution
//		The first typical way to solve circular array problems is to extend the original array to twice length, 2nd half has the same element as first half. Then everything become simple.
//		The second way is to use a stack to facilitate the look up. First we put all indexes into the stack, smaller index on the top. Then we start from end of the array look for the first element (index) in the stack which is greater than the current one. That one is guaranteed to be the Next Greater Element. Then put the current element (index) into the stack.
//
//		    public int[] nextGreaterElements(int[] nums) {
//		        int n = nums.length;
//		        int[] result = new int[n];
//		        
//		        Stack<Integer> stack = new Stack<>();
//		        for (int i = n - 1; i >= 0; i--) {
//		            stack.push(i);
//		        }
//		        
//		        for (int i = n - 1; i >= 0; i--) {
//		            result[i] = -1;
//		            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
//		                stack.pop();
//		            }
//		            if (!stack.isEmpty()){
//		                result[i] = nums[stack.peek()];
//		            }
//		            stack.add(i);
//		        }
//		        
//		        return result;
//		    }
		    
		    
		    public static int[] nextGreaterElements(int[] nums) {
		        int n = nums.length, next[] = new int[n];
		        Arrays.fill(next, -1);
		        Stack<Integer> stack = new Stack<>(); // index stack
		        for (int i = 0; i < n * 2; i++) {
		            int num = nums[i % n]; 
		            while (!stack.isEmpty() && nums[stack.peek()] < num)
		                next[stack.pop()] = num;
		            if (i < n) stack.push(i);
		        }   
		        return next;
		    }
	public static void main(String[] args) {
///////////		 TODO Auto-generated method stub
		int [] r = nextGreaterElements(new int[]{5,4,3,2,1});
		for(int element:r){
			System.out.println(element);
		}

	}

}
