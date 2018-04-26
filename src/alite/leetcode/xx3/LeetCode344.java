package alite.leetcode.xx3;
/**
 * LeetCode 344 - Reverse String

https://leetcode.com/problems/reverse-string/
Write a function that takes a string as input and returns the string reversed.
https://leetcode.com/discuss/98932/java-solution-using-stringbuilders-reverse-function
public String reverseString(String s) {
    return new StringBuilder(s).reverse().toString();
}
https://leetcode.com/discuss/98786/java-swapping-char-array
public String reverseString(String s) {
        char[] c = s.toCharArray();
        for (int i=0,j=c.length-1;i<j;i++,j--){
            char temp = c[i];
            c[i]=c[j];
            c[j]=temp;
        }
        return new String(c);
    }
https://leetcode.com/discuss/98774/many-acceptable-answers
Same as previous but using byte instead
public class Solution {
    public String reverseString(String s) {
        byte[] bytes = s.getBytes();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            byte temp = bytes[i];
            bytes[i] = bytes[j];
            bytes[j] = temp;
            i++;
            j--;
        }
        return new String(bytes);
    }
}
Classic Method by swapping first and last
If you don't like temp variable
public class Solution {
    public String reverseString(String s) {
        byte[] bytes = s.getBytes();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            bytes[i] = (byte)(bytes[i] ^ bytes[j]);
            bytes[j] = (byte)(bytes[i] ^ bytes[j]);
            bytes[i] = (byte)(bytes[i] ^ bytes[j]);
            i++;
            j--;
        }
        return new String(bytes);
    }
}
a = a^b
b = a^b
a = a^b
Using recursion
public class Solution {
    public String reverseString(String s) {
        int length = s.length();
        if (length <= 1) return s;
        String leftStr = s.substring(0, length / 2);
        String rightStr = s.substring(length / 2, length);
        return reverseString(rightStr) + reverseString(leftStr);
    }
}

https://discuss.leetcode.com/topic/43997/using-recursion-only-1-line-but-time-exceeded
    public String reverseString(String s) {
        return s.length()<=1?s:(reverseString(s.substring(1))+s.charAt(0));
    }
Let's see what you crammed into that one line.
(This is an excellent example that short code could be more inefficient than pages of code.)
public String reverseString(String s) {
 if (s.length() <= 1) {
  return s; // base case, no op
 } else {
  StringBuilder builder = new StringBuilder();
  // linear operation: copies String.value into String.value
  String rest = s.substring(1); // skip first char
  String reversedRest = reverseString(rest); // recursive call
  // linear operation: copies String.value into StringBuilder.value
  builder.append(reversedRest);
  builder.append(s.charAt(0)); // append first char
  // linear operation: copies StringBuilder.value into String.value
  return builder.toString();
 }
}
Let's see how many times the recursion is called: you start with a long string and do those linear operations. To get the reversed tail of the string you do a recursion with one shorter string which is still pretty long, but luckily it's decreasing. You'll end up with linear operations being executed n + n-1 + n-2 + n-3 + ... + 3 + 2 + 1 which is (from lower education) = n * (n-1) / 2 and you have a char-char copy 3 times, so you end up with O(n^2).
 * @author het
 *
 */
public class LeetCode344 {
	
//	a = a^b
//			b = a^b
//			a = a^b
	
	
//	a = a^b
//	b = a^b
//	a = a^b
	
	
//	a = a^b
//	b = a^b
//	a = a^b
	
	
//	a = a^b
//	b = a^b
//	a = a^b

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
