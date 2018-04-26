package alite.leetcode.xx3.extra;

import java.util.Arrays;

/**
 * LeetCode 387 - First Unique Character in a String

http://bookshadow.com/weblog/2016/08/21/leetcode-first-unique-character-in-a-string/
Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
Examples:
s = "leetcode"
return 0.

s = "loveleetcode",
return 2.
Note: You may assume the string contain only lowercase letters.
首先统计每个字符的出现次数，然后遍历一次原字符串。

    def firstUniqChar(self, s):
        """
        :type s: str
        :rtype: int
        """
        d = collections.Counter(s)
        ans = -1
        for x, c in enumerate(s):
            if d[c] == 1:
                ans = x
                break
        return ans

https://discuss.leetcode.com/topic/56055/simpe-java-solution
public int firstUniqChar(String s) 
        int[] cache = new int[26];
        for (char c : s.toCharArray()) cache[c - 'a']++;
        for (int i = 0; i < s.length(); i++) {
            if (cache[s.charAt(i) - 'a'] == 1) return i;
        }
        return -1;
    }
https://discuss.leetcode.com/topic/55890/java-o-n-easy-one-pass-solution-20ms
    public int firstUniqChar(String s) {
        char[] schar = s.toCharArray();
        int result = schar.length;
        int[] seen = new int[26];
        //-1 => not visited
        //-2 => repeating
        //>=0 => appear once, value is index
        Arrays.fill(seen, -1);
        for(int i = 0; i < schar.length; i++) {
            int index = schar[i]-'a';
            if(seen[index] == -1) seen[index] = i;
            else if(seen[index] >= 0) seen[index] = -2;
        }
        for(int i = 0; i < 26; i++) {
            if(seen[i] >= 0) result = Math.min(result, seen[i]);
        }
        return result == schar.length?-1:result;
    }

X. Two pointers
https://discuss.leetcode.com/topic/55230/java-two-pointers-slow-and-fast-solution-18-ms
The idea is to use a slow pointer to point to the current unique character and a fast pointer to scan the string. The fast pointer not only just add the count of the character. Meanwhile, when fast pointer finds the identical character of the character at the current slow pointer, we move the slow pointer to the next unique character or not visited character. (20 ms)
    public int firstUniqChar(String s) {
        if (s==null || s.length()==0) return -1;
        int len = s.length();
        if (len==1) return 0;
        char[] cc = s.toCharArray();
        int slow =0, fast=1;
        int[] count = new int[256];
        count[cc[slow]]++;
        while (fast < len) {
            count[cc[fast]]++;
            // if slow pointer is not a unique character anymore, move to the next unique one
            while (slow < len && count[cc[slow]] > 1) slow++;  
            if (slow >= len) return -1; // no unique character exist
            if (count[cc[slow]]==0) { // not yet visited by the fast pointer
                count[cc[slow]]++; 
                fast=slow; // reset the fast pointer
            }
            fast++;
        }
        return slow;
    }

Improved version, which checks early termination when all characters from 'a'~'z' appear more than twice. (18 ms)
    public int firstUniqChar(String s) {
        if (s==null || s.length()==0) return -1;
        int len = s.length();
        char[] cc = s.toCharArray();
        int slow =0, fast=1;
        int[] count = new int[256];
        int total = 0;
        count[cc[slow]]++;
        while (fast < len) {
            count[cc[fast]]++;
            if (cc[fast] == cc[slow]) { 
                total++;
                if (total==26) return -1;
                while (slow < len && count[cc[slow]] > 1) slow++;
                if (slow >= len) return -1;
            }
            if (count[cc[slow]]==0) count[cc[slow]]++;
            if (slow > fast) fast=slow;
            fast++;
        }
        return slow;
    }

X. https://discuss.leetcode.com/topic/55488/java-one-pass-solution-with-linkedhashmap
LinkedHashMap will not be the fastest answer for this question because the input characters are just from 'a' to 'z', but in other situations it might be faster than two pass solutions. I post this just for inspiration.
public int firstUniqChar(String s) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                if (map.get(s.charAt(i)) != null) {// no need to check
                    map.remove(s.charAt(i));
                }
            } else {
                map.put(s.charAt(i), i);
                set.add(s.charAt(i));
            }
        }
        return map.size() == 0 ? -1 : map.entrySet().iterator().next().getValue();
    }
 * @author het
 *
 */
public class LeetCode387 {
	public int firstUniqChar1(String s) {
    int[] cache = new int[26];
    for (char c : s.toCharArray()) cache[c - 'a']++;
    for (int i = 0; i < s.length(); i++) {
        if (cache[s.charAt(i) - 'a'] == 1) return i;
    }
    return -1;
}



public int firstUniqChar(String s) {
    char[] schar = s.toCharArray();
    int result = schar.length;
    int[] seen = new int[26];
    //-1 => not visited
    //-2 => repeating
    //>=0 => appear once, value is index
    Arrays.fill(seen, -1);
    for(int i = 0; i < schar.length; i++) {
        int index = schar[i]-'a';
        if(seen[index] == -1) seen[index] = i;
        else if(seen[index] >= 0) seen[index] = -2;
    }
    for(int i = 0; i < 26; i++) {
        if(seen[i] >= 0) result = Math.min(result, seen[i]);
    }
    return result == schar.length?-1:result;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
