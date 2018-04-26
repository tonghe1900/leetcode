package alite.leetcode.xx4;
/**
 * LeetCode 467 - Unique Substrings in Wraparound String

https://leetcode.com/problems/unique-substrings-in-wraparound-string/
Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s will look like this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
Now we have another string p. Your job is to find out how many unique non-empty substrings of p are present in s. In particular, your input is the string p and you need to output the number of different non-empty substrings of p in the string s.
Note: p consists of only lowercase English letters and the size of p might be over 10000.
Example 1:
Input: "a"
Output: 1

Explanation: Only the substring "a" of string "a" is in the string  s.
Example 2:
Input: "cac"
Output: 2
Explanation: There are two substrings "a", "c" of string "cac" in the string s.
Example 3:
Input: "zab"
Output: 6
Explanation: There are six substrings "z", "a", "b", "za", "ab", "zab" of string "zab" in the string s.
https://discuss.leetcode.com/topic/70658/concise-java-solution-using-dp
After failed with pure math solution and time out with DFS solution, I finally realized that this is a DP problem...
The idea is, if we know the max number of unique substrings in p ends with 'a', 'b', ..., 'z', then the summary of them is the answer. Why is that?
The max number of unique substring ends with a letter equals to the length of max contiguous substring ends with that letter. Example "abcd", the max number of unique substring ends with 'd' is 4, apparently they are "abcd", "bcd", "cd" and "d".
If there are overlapping, we only need to consider the longest one because it covers all the possible substrings. Example: "abcdbcd", the max number of unique substring ends with 'd' is 4 and all substrings formed by the 2nd "bcd" part are covered in the 4 substrings already.
No matter how long is a contiguous substring in p, it is in s since s has infinite length.
Now we know the max number of unique substrings in p ends with 'a', 'b', ..., 'z' and those substrings are all in s. Summary is the answer, according to the question.
    public int findSubstringInWraproundString(String p) {
        // count[i] is the maximum unique substring end with ith letter.
        // 0 - 'a', 1 - 'b', ..., 25 - 'z'.
        int[] count = new int[26];
        
        // store longest contiguous substring ends at current position.
        int maxLengthCur = 0; 

        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
                maxLengthCur++;
            }
            else {
                maxLengthCur = 1;
            }
            
            int index = p.charAt(i) - 'a';
            count[index] = Math.max(count[index], maxLengthCur);
        }
        
        // Sum to get result
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += count[i];
        }
        return sum;
    }
https://discuss.leetcode.com/topic/70654/c-concise-solution
Letters[i] represents the longest consecutive substring ended with chr(97+i), update res only when current L is bigger than letters[curr].
int findSubstringInWraproundString(string p) {
        vector<int> letters(26, 0);
        int res = 0, len = 0;
        for (int i = 0; i < p.size(); i++) {
            int cur = p[i] - 'a';
            if (i > 0 && p[i - 1] != (cur + 26 - 1) % 26 + 'a') len = 0;
            if (++len > letters[cur]) {
                res += len - letters[cur];
                letters[cur] = len;
            }
        }
        return res;
    }
https://discuss.leetcode.com/topic/70705/java-two-different-solutions-with-explanation

http://bookshadow.com/weblog/2016/12/04/leetcode-unique-substrings-in-wraparound-string/
按照子串的首字母分类计数
用字典cmap记录以某字母开始的子串的最大长度
遍历字符串p，维护区间[start, end]，p[start ... end]是无限循环字符串s的一部分
更新p[start], p[start + 1], ... p[end]在cmap中的长度值
最终结果为cmap中value的和
    def findSubstringInWraproundString(self, p):
        """
        :type p: str
        :rtype: int
        """
        pattern = 'zabcdefghijklmnopqrstuvwxyz'
        cmap = collections.defaultdict(int)
        start = end = 0
        for c in range(len(p)):
            if c and p[c-1:c+1] not in pattern:
                for x in range(start, end):
                    cmap[p[x]] = max(end - x, cmap[p[x]])
                start = c
            end = c + 1
        for x in range(start, end):
            cmap[p[x]] = max(end - x, cmap[p[x]])
        return sum(cmap.values())
将上述代码中cmap的含义变更为记录以某字母结尾的子串的最大长度，可以使代码简化。
    def findSubstringInWraproundString(self, p):
        """
        :type p: str
        :rtype: int
        """
        pattern = 'zabcdefghijklmnopqrstuvwxyz'
        cmap = collections.defaultdict(int)
        clen = 0
        for c in range(len(p)):
            if c and p[c-1:c+1] not in pattern:
                clen = 1
            else:
                clen += 1
            cmap[p[c]] = max(clen, cmap[p[c]])
        return sum(cmap.values())
http://www.itdadao.com/articles/c15a844813p0.html
http://stackoverflow.com/questions/40955470/unique-substrings-in-wrap-around-strings
 * @author het
 *
 */
public class LeetCode467 {
	 public int findSubstringInWraproundString(String p) {
	        // count[i] is the maximum unique substring end with ith letter.
	        // 0 - 'a', 1 - 'b', ..., 25 - 'z'.
	        int[] count = new int[26];
	        
	        // store longest contiguous substring ends at current position.
	        int maxLengthCur = 0; 

	        for (int i = 0; i < p.length(); i++) {
	            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
	                maxLengthCur++;
	            }
	            else {
	                maxLengthCur = 1;
	            }
	            
	            int index = p.charAt(i) - 'a';
	            count[index] = Math.max(count[index], maxLengthCur);
	        }
	        
	        // Sum to get result
	        int sum = 0;
	        for (int i = 0; i < 26; i++) {
	            sum += count[i];
	        }
	        return sum;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
