package alite.leetcode.newExtra.L500;
/**
 * LeetCode 557 - Reverse Words in a String III

https://leetcode.com/problems/reverse-words-in-a-string-iii
Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
Example 1:
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
Note: In the string, each word is separated by single space and there will not be any extra space in the string.
https://discuss.leetcode.com/topic/85784/c-java-clean-code
    public String reverseWords(String s) {
        char[] ca = s.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] != ' ') {   // when i is a non-space
                int j = i;
                while (j + 1 < ca.length && ca[j + 1] != ' ') { j++; } // move j to the end of the word
                reverse(ca, i, j);
                i = j;
            }
        }
        return new String(ca);
    }

    private void reverse(char[] ca, int i, int j) {
        for (; i < j; i++, j--) {
            char tmp = ca[i];
            ca[i] = ca[j];
            ca[j] = tmp;
        }
    }

https://discuss.leetcode.com/topic/85744/java-solution
public String reverseWords(String s) {
    String[] strs = s.split(" ");
    StringBuffer sb = new StringBuffer();
    for(String str: strs){
        StringBuffer temp = new StringBuffer(str);
        sb.append(temp.reverse());
        sb.append(" ");
    }
    sb.setLength(sb.length()-1);
    return sb.toString();
}
 * @author het
 *
 */
public class L557 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
