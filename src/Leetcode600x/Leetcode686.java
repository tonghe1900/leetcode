package Leetcode600x;
/**
 * 686. Repeated String Match
DescriptionHintsSubmissionsDiscussSolution
Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it. If no such solution, return -1.

For example, with A = "abcd" and B = "cdabcdab".

Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of A repeated two times ("abcdabcd").

Note:
The length of A and B will be between 1 and 10000.

Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode686 {
//https://leetcode.com/problems/repeated-string-match/solution/
	
	class Solution {
	    public int repeatedStringMatch(String A, String B) {
	        int q = 1;
	        StringBuilder S = new StringBuilder(A);
	        for (; S.length() < B.length(); q++) S.append(A);
	        if (S.indexOf(B) >= 0) return q;
	        if (S.append(A).indexOf(B) >= 0) return q+1;
	        return -1;
	    }
	}
	
	
	
	
	import java.math.BigInteger;

	class Solution {
	    public boolean check(int index, String A, String B) {
	        for (int i = 0; i < B.length(); i++) {
	            if (A.charAt((i + index) % A.length()) != B.charAt(i)) {
	                return false;
	            }
	        }
	        return true;
	    }
	    public int repeatedStringMatch(String A, String B) {
	        int q = (B.length() - 1) / A.length() + 1;
	        int p = 113, MOD = 1_000_000_007;
	        int pInv = BigInteger.valueOf(p).modInverse(BigInteger.valueOf(MOD)).intValue();

	        long bHash = 0, power = 1;
	        for (int i = 0; i < B.length(); i++) {
	            bHash += power * B.codePointAt(i);
	            bHash %= MOD;
	            power = (power * p) % MOD;
	        }

	        long aHash = 0; power = 1;
	        for (int i = 0; i < B.length(); i++) {
	            aHash += power * A.codePointAt(i % A.length());
	            aHash %= MOD;
	            power = (power * p) % MOD;
	        }

	        if (aHash == bHash && check(0, A, B)) return q;
	        power = (power * pInv) % MOD;

	        for (int i = B.length(); i < (q + 1) * A.length(); i++) {
	            aHash -= A.codePointAt((i - B.length()) % A.length());
	            aHash *= pInv;
	            aHash += power * A.codePointAt(i % A.length());
	            aHash %= MOD;
	            if (aHash == bHash && check(i - B.length() + 1, A, B)) {
	                return i < q * A.length() ? q : q + 1;
	            }
	        }
	        return -1;
	    }
	}
}
