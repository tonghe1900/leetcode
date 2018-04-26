package alite.leetcode.xx4;
/**
 * http://bookshadow.com/weblog/2016/10/02/leetcode-valid-word-abbreviation/
Given a non-empty string s and an abbreviation abbr, return whether the string matches with the given abbreviation.
A string such as "word" contains only the following valid abbreviations:
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
Notice that only the above abbreviations are valid abbreviations of the string "word". Any other string is not a valid abbreviation of "word".
Note:
Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.
Example 1:
Given s = "internationalization", abbr = "i12iz4n":

Return true.
Example 2:
Given s = "apple", abbr = "a2e":

Return false.
https://discuss.leetcode.com/topic/61348/short-and-easy-to-understand-java-solution
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                ++i;++j;
                continue;
            }
            if (abbr.charAt(j) <= '0' || abbr.charAt(j) > '9') {
                return false;
            }
            int start = j;
            while (j < abbr.length() && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
                ++j;
            }
            int num = Integer.valueOf(abbr.substring(start, j));
            i += num;
        }
        return i == word.length() && j == abbr.length();
    }
https://discuss.leetcode.com/topic/61362/simple-java-code-with-two-pointers
Use two pointers to track the start of the word and abbr. Move abbr pointer, change word pointer accordingly, check whether we can get to the end of word

模拟题，遍历word和abbr即可
    def validWordAbbreviation(self, word, abbr):
        """
        :type word: str
        :type abbr: str
        :rtype: bool
        """
        size = len(word)
        cnt = loc = 0
        for w in abbr:
            if w.isdigit():
                if w == '0' and cnt == 0:
                    return False
                cnt = cnt * 10 + int(w)
            else:
                loc += cnt
                cnt = 0
                if loc >= size or word[loc] != w:
                    return False
                loc += 1
        return loc + cnt == size


X. Use Regular Expression
https://discuss.leetcode.com/topic/61353/simple-regex-one-liner-java-python
I just turn an abbreviation like "i12iz4n" into a regular expression like "i.{12}iz.{4}n"
public boolean validWordAbbreviation(String word, String abbr) {
    return word.matches(abbr.replaceAll("[1-9]\\d*", ".{$0}"));
}
 * @author het
 *
 */
public class LeetCode408ValidWordAbbreviation {
	 public boolean validWordAbbreviation(String word, String abbr) {
	        int i = 0, j = 0;
	        while (i < word.length() && j < abbr.length()) {
	            if (word.charAt(i) == abbr.charAt(j)) {
	                ++i;++j;
	                continue;
	            }
	            if (abbr.charAt(j) <= '0' || abbr.charAt(j) > '9') {
	                return false;
	            }
	            int start = j;
	            while (j < abbr.length() && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
	                ++j;
	            }
	            int num = Integer.valueOf(abbr.substring(start, j));
	            i += num;
	        }
	        return i == word.length() && j == abbr.length();
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
