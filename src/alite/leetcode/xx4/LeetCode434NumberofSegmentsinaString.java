package alite.leetcode.xx4;
/**
 * LeetCode 434 - Number of Segments in a String

http://bookshadow.com/weblog/2016/12/04/leetcode-number-of-words-in-a-string/
Count the number of segments in a string, where a segment is defined to be a contiguous sequence of non-space characters.
For example,
Input: "Hello, my name is John"
Output: 5
https://discuss.leetcode.com/topic/70642/clean-java-solution-o-n
public int countSegments(String s) {
    int res=0;
    for(int i=0; i<s.length(); i++)
        if(s.charAt(i)!=' ' && (i==0 || s.charAt(i-1)==' '))
            res++;        
    return res;
}

https://discuss.leetcode.com/topic/70656/ac-solution-java-with-trim-and-split
public int countSegments(String s) {
    String trimmed = s.trim();
    if (trimmed.length() == 0) return 0;
    else return trimmed.split("\\s+").length;
    \\s+
}
 * @author het
 *
 */
public class LeetCode434NumberofSegmentsinaString {
	public int countSegments(String s) {
	    int res=0;
	    for(int i=0; i<s.length(); i++)
	        if(s.charAt(i)!=' ' && (i==0 || s.charAt(i-1)==' '))
	            res++;        
	    return res;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
