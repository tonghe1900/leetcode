package alite.leetcode.newExtra.L500;
/**
 * LeetCode 564 - Find the Closest Palindrome

https://leetcode.com/problems/find-the-closest-palindrome
Given an integer n, find the closest integer (not including itself), which is a palindrome.
The 'closest' is defined as absolute difference minimized between two integers.
Example 1:
Input: "123"
Output: "121"
Note:
The input n is a positive integer represented by string, whose length will not exceed 18.
If there is a tie, return the smaller one as answer.
https://discuss.leetcode.com/topic/87220/python-simple-with-explanation
Let's build a list of candidate answers for which the final answer must be one of those candidates. Afterwards,
 choosing from these candidates is straightforward.
If the final answer has the same number of digits as the input string S, then the answer must be the middle 
digits + (-1, 0, or 1) flipped into a palindrome. For example, 23456 had middle part 234, and 233, 234, 235
 flipped into a palindrome yields 23332, 23432, 23532. Given that we know the number of digits, the prefix 235
  (for example) uniquely determines the corresponding palindrome 23532, so all palindromes with larger prefix like 23732 are strictly 
  farther away from S than 23532 >= S.
If the final answer has a different number of digits, it must be of the form 999....999 or 1000...0001,
 as any palindrome smaller than 99....99 or bigger than 100....001 will be farther away from S.
def nearestPalindromic(self, S):
    K = len(S)
    candidates = [str(10**k + d) for k in (K-1, K) for d in (-1, 1)]
    prefix = S[:(K+1)/2]
    P = int(prefix)
    for start in map(str, (P-1, P, P+1)):
        candidates.append(start + (start[:-1] if K%2 else start)[::-1])
    
    def delta(x):
        return abs(int(S) - int(x))
    
    ans = None
    for cand in candidates:
        if cand != S and not cand.startswith('00'):
            if (ans is None or delta(cand) < delta(ans) or
                    delta(cand) == delta(ans) and int(cand) < int(ans)):
                ans = cand
    return ans
http://bookshadow.com/weblog/2017/04/23/leetcode-find-the-closest-palindrome/
记n的前半部分为p，分别用p，p - 1，p + 1及其逆序串拼接成长度为奇数或者偶数的回文串。
假设n的长度为m， p的长度应分别取m / 2，m / 2 + 1。
另外需要考虑进位时的边界情况，比如测试用例：11, 1001, 999
    def nearestPalindromic(self, n):
        """
        :type n: str
        :rtype: str
        """
        evenPal = lambda sp : int(sp + sp[::-1])
        oddPal = lambda sp : int(sp + sp[::-1][1:])
        sn, n = n, int(n)
        if len(sn) == 1: return str(n - 1)
        ans = -999999999999999999
        mid = len(sn) / 2
        for sp in sn[:mid], sn[:mid + 1], str(int(sn[:mid]) * 10):
            p = int(sp)
            for pal in evenPal, oddPal:
                for d in -1, 0, 1:
                    val = pal(str(p + d))
                    if val == n: continue
                    ans = min(ans, val, key = lambda x : (abs(x - n), x))
        return str(ans)

https://discuss.leetcode.com/topic/87200/java-solution
    public String nearestPalindromic(String n) {
        if (n.length() >= 2 && allNine(n)) {
            String s = "1";
            for (int i = 0; i < n.length() - 1; i++) {
                s += "0";
            }
            s += "1";
            return s;
        }
        boolean isOdd = (n.length() % 2 != 0);
        String left = n.substring(0, (n.length() + 1) / 2);
        long[] increment = {-1, 0, +1};
        String ret = n;
        long minDiff = Long.MAX_VALUE;
        for (long i : increment) {
            String s = getPalindrom(Long.toString(Long.parseLong(left) + i), isOdd);
            if (n.length() >= 2 && (s.length() != n.length() || Long.parseLong(s) == 0)) {
                s = "";
                for (int j = 0; j < n.length() - 1; j++) {
                    s += "9";
                }
            }
            long diff = s.equals(n) ? Long.MAX_VALUE : Math.abs(Long.parseLong(s) - Long.parseLong(n));
            if (diff < minDiff) {
                minDiff = diff;
                ret = s;
            }
        }
        return ret;
    }
    private String getPalindrom(String s, boolean isOdd) {
        String right = new StringBuilder(s).reverse().toString();
        return isOdd ? s.substring(0, s.length() - 1) + right : s + right;
    }
    private boolean allNine(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '9') {
                return false;
            }
        }
        return true;
    }
 * @author het
 *
 */
public class L564 {
	
	 public String nearestPalindromic(String n) {
	        if (n.length() >= 2 && allNine(n)) {
	            String s = "1";
	            for (int i = 0; i < n.length() - 1; i++) {
	                s += "0";
	            }
	            s += "1";
	            return s;
	        }
	        boolean isOdd = (n.length() % 2 != 0);
	        String left = n.substring(0, (n.length() + 1) / 2);
	        long[] increment = {-1, 0, +1};
	        String ret = n;
	        long minDiff = Long.MAX_VALUE;
	        for (long i : increment) {
	            String s = getPalindrom(Long.toString(Long.parseLong(left) + i), isOdd);
	            if (n.length() >= 2 && (s.length() != n.length() || Long.parseLong(s) == 0)) {
	                s = "";
	                for (int j = 0; j < n.length() - 1; j++) {
	                    s += "9";
	                }
	            }
	            long diff = s.equals(n) ? Long.MAX_VALUE : Math.abs(Long.parseLong(s) - Long.parseLong(n));
	            if (diff < minDiff) {
	                minDiff = diff;
	                ret = s;
	            }
	        }
	        return ret;
	    }
	    private String getPalindrom(String s, boolean isOdd) {
	        String right = new StringBuilder(s).reverse().toString();
	        return isOdd ? s.substring(0, s.length() - 1) + right : s + right;
	    }
	    private boolean allNine(String s) {
	        for (int i = 0; i < s.length(); i++) {
	            if (s.charAt(i) != '9') {
	                return false;
	            }
	        }
	        return true;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
