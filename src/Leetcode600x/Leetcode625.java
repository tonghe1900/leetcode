package Leetcode600x;
/**
 * 625. Minimum Factorization 
 
 
 
 
 
 
 
 
Average Rating: 5 (9 votes)

June 17, 2017  |  2.3K views
Given a positive integer a, find the smallest positive integer b whose multiplication of each digit equals to a.

If there is no answer or the answer is not fit in 32-bit signed integer, then return 0.

Example 1
Input:

48 
Output:
68
Example 2
Input:

15
Output:
35
 * @author tonghe
 *
 */
public class Leetcode625 {
//https://leetcode.com/articles/minimum-factorization/
	public class Solution {
	    public int smallestFactorization(int a) {
	        for (int i = 1; i < 999999999; i++) {
	            long mul = 1, t = i;
	            while (t != 0) {
	                mul *= t % 10;
	                t /= 10;
	            }
	            if (mul == a && mul <= Integer.MAX_VALUE)
	                return i;
	        }
	        return 0;
	    }
	}

	
	
	
	
	public class Solution {
	    long ans;
	    public int smallestFactorization(int a) {
	        if(a < 2)
	            return a;
	        int[] dig=new int[]{9, 8, 7, 6, 5, 4, 3, 2};
	        if (search(dig, 0, a, 1, "") && ans <= Integer.MAX_VALUE)
	            return (int)ans;
	        return 0;
	    }
	    public boolean search(int[] dig, int i, int a, long mul, String res) {
	        if (mul > a || i == dig.length )
	            return false;
	        if (mul == a) {
	            ans = Long.parseLong(res);
	            return true;
	        }
	        return search(dig, i, a, mul * dig[i], dig[i] + res) || search(dig, i + 1, a, mul, res);
	    }
	}

	
	
	public class Solution {
	    public int smallestFactorization(int a) {
	        if (a < 2)
	            return a;
	        long res = 0, mul = 1;
	        for (int i = 9; i >= 2; i--) {
	            while (a % i == 0) {
	                a /= i;
	                res = mul * i + res;
	                mul *= 10;
	            }
	        }
	        return a < 2 && res <= Integer.MAX_VALUE ? (int)res : 0;
	    }
	}
}
