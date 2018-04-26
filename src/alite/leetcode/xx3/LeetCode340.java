package alite.leetcode.xx3;
/**
 * LeetCode 340 - Longest Substring with At Most K Distinct Characters

http://www.cnblogs.com/grandyang/p/5351347.html
Given a string, find the length of the longest substring T that contains at most k distinct characters.
For example, Given s = “eceba” and k = 2,
T is "ece" which its length is 3.
https://leetcode.com/discuss/95558/15-lines-java-solution-using-slide-window
One optimization, update the res only when valid substring beginning at i reaches the longest:
Put update of res, i.e. res = Math.max(res, j - i + 1), into the if statement,
 just above the while loop. Replace it by res = Math.max(res, j - i). 
 This is the place where a valid substring grows to the greatest.
Put another update for res before return statement, i.e. res = Math.max(s.length() - i, res).
This optimization is very general, it avoids unnecessary updates.

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int[] count = new int[256];
        int num = 0, i = 0, res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)]++ == 0) num++;
            if (num > k) { 
                while (--count[s.charAt(i++)] > 0);//??
                num--;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

https://leetcode.com/discuss/95698/generic-solution-in-java-that-can-be-used-for-unicode
This problem can be solved using two pointers. The important part is while (map.size() > k), we move left pointer to make sure the map size is less or equal to k. This can be easily extended to any number of unique characters.
A more generic solution as follows, can be solution for Unicode string:

public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0;
    int best = 0;
    for(int i = 0; i < s.length(); i++) {
        // character at the right pointer
        char c = s.charAt(i);
        map.put(c, map.getOrDefault(c, 0) + 1);
        // make sure map size is valid, no need to check left pointer less than s.length()
        while (map.size() > k) {
            char leftChar = s.charAt(left);
            if (map.containsKey(leftChar)) {
                map.put(leftChar, map.get(leftChar) - 1);                     
                if (map.get(leftChar) == 0) { 
                    map.remove(leftChar);
                }
            }
            left++;
        }
        best = Math.max(best, i - left + 1);
    }
    return best;
}
http://www.guoting.org/leetcode/leetcode-340-longest-substring-with-at-most-k-distinct-characters/
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        int[] count = new int[256];
        int num = 0, i = 0, res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (count[s.charAt(j)]++ == 0) num++;
            if (num > k) {
                while (--count[s.charAt(i++)] > 0);
                num--;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int res = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (map.size() > k) {
                char leftChar = s.charAt(left);
                left+=map.get(leftChar);
                map.remove(leftChar);
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }
https://sherry1992.gitbooks.io/leetcode/content/lc340_longest_substring_with_at_most_k_distinct_ch.html
Follow up
https://discuss.leetcode.com/topic/48827/java-o-nlogk-using-treemap-to-keep-last-occurrence-interview-follow-up-question/3
The interviewer may say that the string is given as a steam. In this situation, we can't maintain a "left pointer" as the classical O(n) hashmap solution.
Solving the problem with O(n) time is not enough, some interviewer may require this solution as a followup. Instead of recording each char's count, we keep track of char's last occurrence. If you consider k as constant, it is also a O(n) algorithm.
inWindow keeps track of each char in window and its last occurrence position
lastOccurrence is used to find the char in window with left most last occurrence. A better idea is to use a PriorityQueue, as it takes O(1) to getMin, However Java's PQ does not support O(logn) update a internal node, it takes O(n). TreeMap takes O(logn) to do both getMin and update.
Every time when the window is full of k distinct chars, we lookup TreeMap to find the one with leftmost last occurrence and set left bound j to be 1 + first to exclude the char to allow new char coming into window.
        public int lengthOfLongestSubstringKDistinct(String str, int k) {
            if (str == null || str.isEmpty() || k == 0) {
                return 0;
            }
            TreeMap<Integer, Character> lastOccurrence = new TreeMap<>();
            Map<Character, Integer> inWindow = new HashMap<>();
            int j = 0;
            int max = 1;
            for (int i = 0; i < str.length(); i++) {
                char in = str.charAt(i);
                while (inWindow.size() == k && !inWindow.containsKey(in)) {
                    int first = lastOccurrence.firstKey();
                    char out = lastOccurrence.get(first);
                    inWindow.remove(out);
                    lastOccurrence.remove(first);
                    j = first + 1;
                }
                //update or add in's position in both maps
                if (inWindow.containsKey(in)) {
                    lastOccurrence.remove(inWindow.get(in));
                }
                inWindow.put(in, i);
                lastOccurrence.put(i, in);
                max = Math.max(max, i - j + 1);
            }
            return max;
        }
http://www.1point3acres.com/bbs/thread-209202-1-1.html
第一轮，Longest Substring with At Most K Distinct Characters变形题，输入是stream而不是string，所以无法知道输入的长度，而且内存不够，无法存入整个stream，所以维持sliding window来更新最大长度的算法无法解决该问题。最后是通过一个map纪录每个字符上次出现的index来实现。提供了函数streamreader.reader()来返回下一个字符

第一轮，Longest Substring with At Most K Distinct Characters变形题: 假设API streamreader.read()返回'\0' 若stream已读完（具体的要求不影响实现）。因为内存有限制，那么这个题还有一个细节就是所给的k大小能不能认为是有界的，一般若假设是26或256个字符的话那么用一个unordered_map<char, int> lastInx记录当前最后k个distinct char出现的最后index 就可以了。但是这样实际会造成O(Nk)时间复杂度，因为在前进substring front的时候还会涉及判断front index是不是某个char的最后index。我是再定义一个reverse mapping: unordered_map<int, char> chars来记录每个最后index所对应的char. 这样用O(2k)的空间就可以实现O(N)的时间。
当然假设内存虽然有限制，但至少是O(k)的 （即使不是O(N)的）。
int maxLength(StreamReader& streamreader, int k) {.1point3acres缃�
  unordered_map<char, int> lastInx; // last index of distinct char, space O(k)
  unordered_map<int, char> chars;   // distinct char of last index, space O(k)
  char c = '\0'; // current char
  int front = 0; // first char's index of valid substring
  int cur = -1;  // current char's index of valid substring
  int maxLen = 0; // max length of valid substring
  while ((c = streamreader.read()) != '\0') { // time: O(N)
    // update maps of last index and char
    if (lastInx.count(c)) chars.erase(lastInx[c]);
    lastInx[c] = ++cur; chars[cur] = c;
    // advance front index of substring if invalid
    while (chars.size() > k) {
      if (chars.count(front)) { lastInx.erase(chars[front]); chars.erase(front); }
      ++front;. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
    }
    // update max length
    maxLen = max(maxLen, cur - front + 1);. more info on 1point3acres.com
  }
  return maxLen;
}
关键看你的sliding window在advance front index的时候需要记录什么。
在无内存限制的时候，advance front index需要判断删除char s[front_index]后是否减少了某种字符的出现，若是这样实现必然要存储substring从front_index一直到cur_index的字符，这需要O(N)空间，也就是这道题不允许的。所以按照Leetcode原题只记录char frequency是不行的。.鏈枃鍘熷垱鑷�1point3acres璁哄潧
实际上每次front_index++时并不需要知道s[front_index]本身是什么，只需要知道该字符是否是最后出现的instance。这个改进应该就是这个题的考点吧。
当然，前提是内存虽然没有O(N)，但至少有O(k). 注意O(k)可以远远小于O(charset).

Unicode allows for 17 planes, each of 65,536 possible characters (or 'code points'). This gives a total of 1,114,112 possible characters. At present, only about 10% of this space has been allocated.
 * @author het
 *
 */
public class LeetCode340 {
	 public int lengthOfLongestSubstringKDistinct(String s, int k) {
	        int[] count = new int[256];
	        int num = 0, i = 0, res = 0;
	        for (int j = 0; j < s.length(); j++) {
	            if (count[s.charAt(j)]++ == 0) num++;
	            if (num > k) { 
	                while (--count[s.charAt(i++)] > 0);//??
	                num--;
	            }
	            res = Math.max(res, j - i + 1);
	        }
	        return res;
	    }
	 
	 
//	 public int lengthOfLongestSubstringKDistinct(String s, int k) {
//		    Map<Character, Integer> map = new HashMap<>();
//		    int left = 0;
//		    int best = 0;
//		    for(int i = 0; i < s.length(); i++) {
//		        // character at the right pointer
//		        char c = s.charAt(i);
//		        map.put(c, map.getOrDefault(c, 0) + 1);
//		        // make sure map size is valid, no need to check left pointer less than s.length()
//		        while (map.size() > k) {
//		            char leftChar = s.charAt(left);
//		            if (map.containsKey(leftChar)) {
//		                map.put(leftChar, map.get(leftChar) - 1);                     
//		                if (map.get(leftChar) == 0) { 
//		                    map.remove(leftChar);
//		                }
//		            }
//		            left++;
//		        }
//		        best = Math.max(best, i - left + 1);
//		    }
//		    return best;
//		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
