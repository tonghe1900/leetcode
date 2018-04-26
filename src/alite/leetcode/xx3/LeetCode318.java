package alite.leetcode.xx3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 318 - Maximum Product of Word Lengths

https://leetcode.com/problems/maximum-product-of-word-lengths/
Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share 
common letters.
 You may assume that each word will contain only lower case letters. If no such two words exist, return 0.
Example 1:
Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
Return 16
The two words can be "abcw", "xtfn".
Example 2:
Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
Return 4
The two words can be "ab", "cd".
Example 3:
Given ["a", "aa", "aaa", "aaaa"]
Return 0
No such pair of words.
Follow up:
Could you do better than O(n2), where n is the number of words?
http://traceformula.blogspot.com/2015/12/maximum-product-of-word-lengths.html
https://discuss.leetcode.com/topic/35539/java-easy-version-to-understand
https://discuss.leetcode.com/topic/31769/32ms-java-ac-solution
If you first read the problem, you can think of brute force solution: for each pair of words, check whether they have a common letter, 
if not, get the product of their lengths and compare to max value achieved so far.
The brute force solution leads to another requirement: checking a pair of words if they contain common letters?
 Actually, we can do that with some pre-calculation, and with the understanding that the words contain only lowercase letters.
Since there are only 26 lowercase letters, we can represent a set of letters using an integer. 
So let's say if the word contains 'a', then the integer's 0th bit will be 1.
 If it has 'b', then the 1st is set to 1, so on and so forth.
    public int maxProduct(String[] words) {  
        int n = words.length;  
        int[] dietpepsi = new int[n];  
        for(int i=0; i<n; i++){  
            dietpepsi[i] = getMask(words[i]);  
        }  
        int max = 0; int t;  
        for(int i=0; i<n; i++){  
            t = 0;  
            for(int j=i+1; j<n; j++){  
                if((dietpepsi[i] & dietpepsi[j]) == 0){  
                    t = Math.max(t, words[j].length());  
                }  
            }  
            max = Math.max(max, t*words[i].length());  
        }  
        return max;  
    }  
    private int getMask(String s){  
        int mask = 0;  
        for(char c: s.toCharArray()){  
            mask |= 1 << (c - 'a');  
        }  
        return mask;  
    }  
II. Improvement on (I) 
We can make some improvement by first sorting the words according to their lengths. 
Then for the ith word in the sorted array, we check from i-1 to 0 to see if there is a word that 
shares no common letter with it. Then we calculate the product, compare to the max value so far, 
stop the loop for the ith word, and move on with the (i+1)th word.
    public int maxProduct(String[] words) {  
        int n = words.length;  
          
        Arrays.sort(words, new LengthComparator());  
        int[][] dietpepsi = new int[n][2];  
        int max = 0;  
        for(int i=0; i<n; i++){  
            dietpepsi[i][0] |= getMask(words[i]);   
            dietpepsi[i][1] = words[i].length();  
        }  
          
        int last = 0;  
        for(int i=n-1; i>=1; i--){  
            for(int j=i-1; j>=last; j--){  
                if((dietpepsi[i][0] & dietpepsi[j][0]) == 0){  
                    max = Math.max(dietpepsi[i][1] * dietpepsi[j][1], max);  
                    last = j;  
                    while(last<n && dietpepsi[last][1]==dietpepsi[j][1]) last++;  
                    break;  
                }  
            }  
        }  
        return max;  
    }  
    private int getMask(String s){  
        int mask = 0;  
        for(char c: s.toCharArray()){  
            mask |= 1 << (c - 'a');  
        }  
        return mask;  
    }  
    class LengthComparator implements Comparator<String>{  
        public int compare(String a, String b){  
            return a.length() - b.length();  
        }  
    }  
http://segmentfault.com/a/1190000004186943
然而我们可以简化对于两个单词求product这一步，方法是我们做个预处理，遍历一遍单词，对于每个单词，我们用一个Integer表示其含有的字母情况。预处理之后，对于两个单词我们只需要对两个int做个和运算便可以知道两个单词是否存在相同字母。这种方法，我们可以将时间复杂度降到O(n^2)。
当然，在以上方法的基础上，我们可以做一些小优化，比如事先对单词根据长度依照长度从大到小排序，这样在两个for loop过程中我们可以根据具体情况直接跳出，不用再继续遍历
    public int maxProduct(String[] words) {
        int[] maps = new int[words.length];
        
        // 将单词按照长度从长到短排序
        Arrays.sort(words, new Comparator<String>() {
           public int compare(String s1, String s2) {
               return s2.length() - s1.length();
           } 
        });
        
        // 对于每个单词，算出其对应的int来表示所含字母情况
        for (int i = 0; i < words.length; i++) {
            int bits = 0;
            for (int j = 0; j < words[i].length(); j++) {
                char c = words[i].charAt(j);
                // 注意bit运算优先级
                bits = bits | (1 << (c - 'a'));
            }
            maps[i] = bits;
        }
        
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            // 提前结束，没必要继续loop
            if (words[i].length() * words[i].length() <= max)
                break;
            for (int j = i + 1; j < words.length; j++) {
                if ((maps[i] & maps[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                    // 下面的结果只会更小，没必要继续loop
                    break;
                }
            }
        }
        return max;
    }
https://leetcode.com/discuss/74589/32ms-java-ac-solution

http://www.hrwhisper.me/leetcode-maximum-product-of-word-lengths/
直接看看每个字符串都包括了哪个字符，然后一一枚举是否有交集：
有交集，则乘积为0
无交集，乘积为 words[i].length() * words[j].length()

 int maxProduct(vector<string>& words) {

 int n = words.size();

 vector<vector<int> > elements(n, vector<int>(26, 0));

 for (int i = 0; i < n; i++) {

 for (int j = 0; j < words[i].length(); j++)

 elements[i][words[i][j] - 'a'] ++;

 }

 int ans = 0;

 for (int i = 0; i < n; i++) {

 for (int j = i + 1; j < n; j++) {

 bool flag = true;

 for (int k = 0; k < 26; k++) {

 if (elements[i][k] != 0 && elements[j][k] != 0) {

 flag = false;

 break;

 }

 }

 if (flag && words[i].length() * words[j].length()  > ans)

 ans = words[i].length() * words[j].length();

 }

 }

 return ans;

 }
https://leetcode.com/discuss/74528/bit-manipulation-java-o-n-2
Pre-process the word, use bit to represent the words. We can do this because we only need to compare if two words contains the same characters.
public int maxProduct(String[] words) {
    int max = 0;
    int[] bytes = new int[words.length];
    for(int i=0;i<words.length;i++){
        int val = 0;
        for(int j=0;j<words[i].length();j++){
            val |= 1<<(words[i].charAt(j)-'a');
        }
        bytes[i] = val;
    }
    for(int i=0; i<bytes.length; i++){
        for(int j=i+1; j<bytes.length; j++){
            if((bytes[i] & bytes[j])==0)max = Math.max(max,words[i].length()*words[j].length());
        }
    }
    return max;
}
https://leetcode.com/discuss/74519/straightforward-o-n-2-solution-by-comparing-each-word
http://buttercola.blogspot.com/2016/01/leetcode-maximum-product-of-word-lengths.html
The most straight-forward way to solve this problem is to pick up any two words, and check if they have common characters. If not, then calculate the maximum product of the length. 

Now let's analyze the complexity in time. Suppose the number of words is n, and average word length is m. So the time complexity for the  brute-force solution is O(n^2 * m). 
Straightforward O(n^2) solution by comparing each word
    public int maxProduct(String[] words) {
        if (words == null || words.length == 0) return 0;

        int result = 0;
        for (int i = 0; i < words.length; i++) {
            int[] letters = new int[26];
            for (char c : words[i].toCharArray()) {
                letters[c - 'a'] ++;
            }
            for (int j = 0; j < words.length; j++) {
                if (j == i) continue;
                int k = 0;
                for (; k < words[j].length(); k++) {
                    if (letters[words[j].charAt(k) - 'a'] != 0) {
                        break;
                    }
                }
                if (k == words[j].length()) {
                    result = Math.max(result, words[i].length() * words[j].length());
                }
            }
        }
        return result;
    }
http://bookshadow.com/weblog/2015/12/16/leetcode-maximum-product-word-lengths/

class Solution(object):
    def maxProduct(self, words):
        """
        :type words: List[str]
        :rtype: int
        """
        nums = []
        size = len(words)
        for w in words:
            nums += sum(1 << (ord(x) - ord('a')) for x in set(w)),
        ans = 0
        for x in range(size):
            for y in range(size):
                if not (nums[x] & nums[y]):
                    ans = max(len(words[x]) * len(words[y]), ans)
        return ans
另一种解法：
引入辅助数组es和字典ml。
es[x]记录所有不包含字母x的单词（单词转化成的数字）
ml[x]记录所有单词对应数字的最大长度
首次遍历words，计算words[i]对应的数字num，并找出words[i]中所有未出现的字母，将num加入这些字母在es数组中对应的位置。
二次遍历words，计算words[i]中未出现过字母所对应的数字集合的交集，从字典ml中取长度的最大值进行计算。
    def maxProduct(self, words):
        """
        :type words: List[str]
        :rtype: int
        """
        es = [set() for x in range(26)]
        ml = collections.defaultdict(int)
        for w in words:
            num = sum(1 << (ord(x) - ord('a')) for x in set(w))
            ml[num] = max(ml[num], len(w))
            for x in set(string.lowercase) - set(w):
                es[ord(x) - ord('a')].add(num)
        ans = 0
        for w in words:
            r = [es[ord(x) - ord('a')] for x in w]
            if not r: continue
            r = set.intersection(*r)
            for x in r:
                ans = max(ans, len(w) * ml[x])
        return ans
https://www.hrwhisper.me/leetcode-maximum-product-of-word-lengths/
 * @author het
 *
 */
public class LeetCode318 {
	 public int maxProduct1(String[] words) {  
	        int n = words.length;  
	        int[] dietpepsi = new int[n];  
	        for(int i=0; i<n; i++){  
	            dietpepsi[i] = getMask1(words[i]);  
	        }  
	        int max = 0; int t;  
	        for(int i=0; i<n; i++){  
	            t = 0;  
	            for(int j=i+1; j<n; j++){  
	                if((dietpepsi[i] & dietpepsi[j]) == 0){  
	                    t = Math.max(t, words[j].length());  
	                }  
	            }  
	            max = Math.max(max, t*words[i].length());  
	        }  
	        return max;  
	    }  
	    private int getMask1(String s){  
	        int mask = 0;  
	        for(char c: s.toCharArray()){  
	            mask |= 1 << (c - 'a');  
	        }  
	        return mask;  
	    }  
//	II. Improvement on (I) 
//	We can make some improvement by first sorting the words according to their lengths. 
//	Then for the ith word in the sorted array, we check from i-1 to 0 to see if there is a word that 
//	shares no common letter with it. Then we calculate the product, compare to the max value so far, 
//	stop the loop for the ith word, and move on with the (i+1)th word.
	    public static int maxProduct(String[] words) {  
	        int n = words.length;  
	          
	        Arrays.sort(words, new LengthComparator());  
	        int[][] dietpepsi = new int[n][2];  
	        int max = 0;  
	        for(int i=0; i<n; i++){  
	            dietpepsi[i][0] |= getMask(words[i]);   
	            dietpepsi[i][1] = words[i].length();  
	        }  
	          
	        int last = 0;  
	        for(int i=n-1; i>=1; i--){  
	            for(int j=i-1; j>=last; j--){  
	                if((dietpepsi[i][0] & dietpepsi[j][0]) == 0){  
	                    max = Math.max(dietpepsi[i][1] * dietpepsi[j][1], max);  
	                    last = j;  
	                    while(last<n && dietpepsi[last][1]==dietpepsi[j][1]) last++;  
	                    break;  
	                }  
	            }  
	        }  
	        return max;  
	    }  
	    private static int getMask(String s){  
	        int mask = 0;  
	        for(char c: s.toCharArray()){  
	            mask |= 1 << (c - 'a');  
	        }  
	        return mask;  
	    }  
	   static class LengthComparator implements Comparator<String>{  
	        public int compare(String a, String b){  
	            return a.length() - b.length();  
	        }  
	    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(maxProduct(new String[]{"abcw", "baz", "foo", "bar", "xtfn", "abcdef"}));

	}

}
