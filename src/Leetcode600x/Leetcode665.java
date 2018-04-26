package Leetcode600x;
/**
 * Given an array with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

Example 1:
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:
Input: [4,2,1]
Output: False
Explanation: You can't get a non-decreasing array by modify at most one element.
Note: The n belongs to [1, 10,000].

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode665 {
//https://leetcode.com/problems/non-decreasing-array/solution/
	
	class Solution(object):
	    def checkPossibility(self, A):
	        def monotone_increasing(arr):
	            for i in range(len(arr) - 1):
	                if arr[i] > arr[i+1]:
	                    return False
	            return True

	        new = A[:]
	        for i in xrange(len(A)):
	            old_ai = A[i]
	            new[i] = new[i-1] if i > 0 else float('-inf')
	            if monotone_increasing(new):
	                return True
	            new[i] = old_ai

	        return False
	        		
	        		
	        		
	        		class Solution(object):
	        		    def checkPossibility(self, A):
	        		        def brute_force(A):
	        		            #Same as in approach 1

	        		        i, j = 0, len(A) - 1
	        		        while i+2 < len(A) and A[i] <= A[i+1] <= A[i+2]:
	        		            i += 1
	        		        while j-2 >= 0 and A[j-2] <= A[j-1] <= A[j]:
	        		            j -= 1

	        		        if j - i + 1 <= 2:
	        		            return True
	        		        if j - i + 1 >= 5:
	        		            return False

	        		        return brute_force(A[i: j+1])
	        		        		
	        		        		
	        		        		
	        		        		
	        		        		
	        		        		
	        		        		class Solution(object):
	        		        		    def checkPossibility(self, A):
	        		        		        p = None
	        		        		        for i in xrange(len(A) - 1):
	        		        		            if A[i] > A[i+1]:
	        		        		                if p is not None:
	        		        		                    return False
	        		        		                p = i

	        		        		        return (p is None or p == 0 or p == len(A)-2 or
	        		        		                A[p-1] <= A[p+1] or A[p] <= A[p+2])
}
