package alite.leetcode.newExtra.L500;
/**
 * LeetCode 555 - Split Assembled Strings

http://bookshadow.com/weblog/2017/04/16/leetcode-split-assembled-strings/
Given a list of strings, you could assemble these strings together into a loop. Among all the possible loops, 
you need to find the lexicographically biggest string after cutting and making one breakpoint of the loop, which 
will make a looped string into a regular one.
So, to find the lexicographically biggest string, you need to experience two phases:
Assemble all the strings into a loop, where you can reverse some strings or not and connect them in the same order as given.
Cut and make one breakpoint in any place of the loop, which will make a looped string into a regular string starting from the character at the cutting point.
And your job is to find the lexicographically biggest one among all the regular strings.
Example:
Input: "abc", "xyz"
Output: "zyxcba"
Explanation: You can get the looped string "-abcxyz-", "-abczyx-", "-cbaxyz-", "-cbazyx-", 
where '-' represents the looped status. 
The answer string came from the third looped one, 
where you could cut from the middle and get "zyxcba".
Note:
The input strings will only contain lowercase letters.
The total length of all the strings will not over 1000.
给定一组字符串，其中每个字符串可以逆置，将字符串按照原始顺序拼接组成一个环，从中选择一个位置切开。
求得到的字符串中字典序最大的字符串。

给定一组字符串，其中每个字符串可以逆置，将字符串按照原始顺序拼接组成一个环，从中选择一个位置切开。
求得到的字符串中字典序最大的字符串。
https://discuss.leetcode.com/topic/86477/neat-java-solution
    public String splitLoopedString(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            String rev = new StringBuilder(strs[i]).reverse().toString();
            if (strs[i].compareTo(rev) < 0)
                strs[i] = rev;
        }
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            String rev = new StringBuilder(strs[i]).reverse().toString();
            for (String st: new String[] {strs[i], rev}) {
                for (int k = 0; k < st.length(); k++) {
                    StringBuilder t = new StringBuilder(st.substring(k));
                    for (int j = i + 1; j < strs.length; j++)
                        t.append(strs[j]);
                    for (int j = 0; j < i; j++)
                        t.append(strs[j]);
                    t.append(st.substring(0, k));
                    if (t.toString().compareTo(res) > 0)
                        res = t.toString();
                }
            }
        }
        return res;
    }
 * @author het
 *
 */
public class L555 {
	
	 public String splitLoopedString(String[] strs) {
	        for (int i = 0; i < strs.length; i++) {
	            String rev = new StringBuilder(strs[i]).reverse().toString();
	            if (strs[i].compareTo(rev) < 0)
	                strs[i] = rev;
	        }
	        String res = "";
	        for (int i = 0; i < strs.length; i++) {
	            String rev = new StringBuilder(strs[i]).reverse().toString();
	            for (String st: new String[] {strs[i], rev}) {
	                for (int k = 0; k < st.length(); k++) {
	                    StringBuilder t = new StringBuilder(st.substring(k));
	                    for (int j = i + 1; j < strs.length; j++)
	                        t.append(strs[j]);
	                    for (int j = 0; j < i; j++)
	                        t.append(strs[j]);
	                    t.append(st.substring(0, k));
	                    if (t.toString().compareTo(res) > 0)
	                        res = t.toString();
	                }
	            }
	        }
	        return res;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
