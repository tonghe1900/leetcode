package alite.leetcode.newExtra.L500;
/**
 * LeetCode 520 - Detect Capital

https://leetcode.com/problems/detect-capital/?tab=Description
Given a word, you need to judge whether the usage of capitals in it is right or not.
We define the usage of capitals in a word to be right when one of the following cases holds:
All letters in this word are capitals, like "USA".
All letters in this word are not capitals, like "leetcode".
Only the first letter in this word is capital if it has more than one letter, like "Google".
Otherwise, we define that this word doesn't use capitals in a right way.
Example 1:
Input: "USA"
Output: True
Example 2:
Input: "FlaG"
Output: False

boolean 
Note: The input will be a non-empty word consisting of uppercase and lowercase latin letters.
X. https://discuss.leetcode.com/topic/79912/3-lines/
    public boolean detectCapitalUse(String word) {
        int cnt = 0;
        for(char c: word.toCharArray()) if('Z' - c >= 0) cnt++;
        return ((cnt==0 || cnt==word.length()) || (cnt==1 && 'Z' - word.charAt(0)>=0));
    }
    bool detectCapitalUse(string word) {
        int cnt = 0;
        for (char c : word) if (isupper(c)) ++cnt;
        return !cnt || cnt == word.size() || cnt == 1 && isupper(word[0]);
    }
https://discuss.leetcode.com/topic/79911/simple-java-solution-o-n-time-o-1-space
    public boolean detectCapitalUse(String word) {
        int numUpper = 0;
        for (int i=0;i<word.length();i++) {
            if (Character.isUpperCase(word.charAt(i))) numUpper++;
        }
        if (numUpper == 0 || numUpper == word.length()) return true;
        if (numUpper == 1) return Character.isUpperCase(word.charAt(0));
        return false;
    }
-- inefficient
    public boolean detectCapitalUse(String word) {
        return word.equals(word.toUpperCase()) || 
               word.equals(word.toLowerCase()) ||
               Character.isUpperCase(word.charAt(0)) && 
               word.substring(1).equals(word.substring(1).toLowerCase());
    }
https://discuss.leetcode.com/topic/79913/java-short-solution-using-built-in-string-methods
The string can be correctly capitalized if either: it's shorter than 2 characters, or if it's all lower case, or if it's all upper case, or if from position 1 onward there are only lowercase letters.
public boolean detectCapitalUse(String word) {
        if (word.length() < 2) return true;
        if (word.toUpperCase().equals(word)) return true;
        if (word.substring(1).toLowerCase().equals(word.substring(1))) return true;
        return false;
}

http://bookshadow.com/weblog/2017/02/19/leetcode-detect-capital/
    def detectCapitalUse(self, word):
        """
        :type word: str
        :rtype: bool
        """
        return word[1:].islower() or word.islower() or word.isupper()

https://discuss.leetcode.com/topic/79930/java-1-liner
public boolean detectCapitalUse(String word) {
    return word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]+");
}
https://discuss.leetcode.com/topic/79951/in-python-these-are-called
def detectCapitalUse(self, word):
    return word.isupper() or word.islower() or word.istitle()



You might also like

 * @author het
 *
 */
public class L520 {
	 public boolean detectCapitalUse(String word) {
	        int numUpper = 0;
	        for (int i=0;i<word.length();i++) {
	            if (Character.isUpperCase(word.charAt(i))) numUpper++;
	        }
	        if (numUpper == 0 || numUpper == word.length()) return true;
	        if (numUpper == 1) return Character.isUpperCase(word.charAt(0));
	        return false;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
