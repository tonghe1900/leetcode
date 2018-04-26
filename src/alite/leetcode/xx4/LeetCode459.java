package alite.leetcode.xx4;
/**
 * LeetCode 459 - Repeated Substring Pattern

http://bookshadow.com/weblog/2016/11/13/leetcode-repeated-substring-pattern/
Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple 
copies of the substring together. You may assume the given string consists of lowercase English letters only
 and its length will not exceed 10000.
Example 1:
Input: "abab"

Output: True

Explanation: It's the substring "ab" twice.
Example 2:
Input: "aba"
abaaba
Output: False
Example 3:
Input: "abcabcabcabc"

Output: True

Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
蛮力法（Brute Force）
时间复杂度 O(k * n)，其中n是字符串长度，k是n的约数个数
若字符串可以由其子串重复若干次构成，则子串的起点一定从原串的下标0开始

并且子串的长度一定是原串长度的约数

整数约数的个数可以通过统计其质因子的幂得到，而输入规模10000以内整数的约数个数很少

因此通过蛮力法，枚举子串长度即可
    def repeatedSubstringPattern(self, str):
        """
        :type str: str
        :rtype: bool
        """
        size = len(str)
        for x in range(1, size / 2 + 1):
            if size % x:
                continue
            if str[:x] * (size / x) == str:
                return True
        return False
 * @author het
 *
 */
public class LeetCode459 {
	  public boolean  repeatedSubstringPattern(String str){
		 int  size = str.length();
		 for(int x = 1; x<= size /2 +1; x+=1){
			 if(size % x == 0 ){
				 if(str.equals(copy(str.substring(0, x), size/x))){
					 return true;
				 }
			 }
		 }
		 return false;
			       
	  }
	       
	        
	private String  copy(String substring, int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
