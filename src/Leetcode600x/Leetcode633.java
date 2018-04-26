package Leetcode600x;
/**
 * Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.

Example 1:
Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5
Example 2:
Input: 3
Output: False
Seen this question in a real interview before? 
 * @author tonghe
 *
 */
public class Leetcode633 {
//https://leetcode.com/problems/sum-of-square-numbers/solution/
	
	public class Solution {
	    public boolean judgeSquareSum(int c) {
	        for (long a = 0; a * a <= c; a++) {
	            for (long b = 0; b * b <= c; b++) {
	                if (a * a + b * b == c)
	                    return true;
	            }
	        }
	        return false;
	    }
	}
	
	
	
	
	public class Solution {
	    public boolean judgeSquareSum(int c) {
	        for (long a = 0; a * a <= c; a++) {
	            int b =  c - (int)(a * a);
	            int i = 1, sum = 0;
	            while (sum < b) {
	                sum += i;
	                i += 2;
	            }
	            if (sum == b)
	                return true;
	        }
	        return false;
	    }
	}
	
	
	
	

public class Solution {
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            double b = Math.sqrt(c - a * a);
            if (b == (int) b)
                return true;
        }
        return false;
    }
}




public class Solution {
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a * a <= c; a++) {
            int b = c - (int)(a * a);
            if (binary_search(0, b, b))
                return true;
        }
        return false;
    }
    public boolean binary_search(long s, long e, int n) {
        if (s > e)
            return false;
        long mid = s + (e - s) / 2;
        if (mid * mid == n)
            return true;
        if (mid * mid > n)
            return binary_search(s, mid - 1, n);
        return binary_search(mid + 1, e, n);
    }
}



public class Solution {
    public boolean judgeSquareSum(int c) {
        for (int i = 2; i * i <= c; i++) {
            int count = 0;
            if (c % i == 0) {
                while (c % i == 0) {
                    count++;
                    c /= i;
                }
                if (i % 4 == 3 && count % 2 != 0)
                    return false;
            }
        }
        return c % 4 != 3;
    }
}



}
