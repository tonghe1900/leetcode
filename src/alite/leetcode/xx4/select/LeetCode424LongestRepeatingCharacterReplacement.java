package alite.leetcode.xx4.select;
/**
 * https://leetcode.com/problems/longest-repeating-character-replacement/
Given a string that consists of only uppercase English letters, 
you can replace any letter in the string with another letter at most k times. 
Find the length of a longest substring containing all repeating letters you can get after performing the above operations.
Note:
Both the string's length and k will not exceed 104.
Example 1:
Input:
s = "ABAB", k = 2

Output:
4

Explanation:
Replace the two 'A's with two 'B's or vice versa.
Example 2:
Input:
s = "AABABBA", k = 1

Output:
4

Explanation:
Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.

https://discuss.leetcode.com/topic/63471/java-sliding-window-easy-to-understand
The problem is similar to longest substring with most K distinct characte. But this time, the constraint is we can
 only have most K characters that is different with the most frequent character in the substring. 
 For example in the sliding window:
"ABBBAC" most frequent character is B with count 3, all other character is count as different to B, 
    which is A and C, and the result is 2 + 1 = 3. 
Each time we count the different characters. If it is not bigger than k we extend the sliding window.
Since we only have 26 characters, keep the count in a integer array is good enough.
    public int characterReplacement(String s, int k) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int max = 0;
        int[] ch = new int[26];
        char[] str = s.toCharArray();
        for(int i=0, j=0; i<s.length(); i++){
            while(j < s.length()){
                ch[str[j] - 'A']++;
                if(count(ch) > k){  //If exceed k, break
                    ch[str[j] - 'A']--;
                    break;
                }
                j++;
            }
            max = Math.max(max, j-i);
            ch[str[i] - 'A']--;
        }
        return max;
    }
    //Count the number of character that is different to the longest character
    public int count(int[] ch){
        int max = 0;
        int sum = 0;
        for(int val:ch){
            sum += val;
            max = Math.max(max, val);
        }
        return sum - max;
    }
https://discuss.leetcode.com/topic/63456/7-lines-c
The true meaning of his max_same is the window_size. (i.e. windows_size = max_same + k).
So it is all right that window_size never decreases since we are only interested in finding the biggest window.

https://discuss.leetcode.com/topic/63416/sliding-window-similar-to-finding-longest-substring-with-k-distinct-characters/5
The problem says that we can make at most k changes to the string (any character can be replaced with any other character). So, let's say there were no constraints like the k. Given a string convert it to a string with all same characters with minimal changes. The answer to this is
length of the entire string - number of times of the maximum occurring character in the string
Given this, we can apply the at most k changes constraint and maintain a sliding window such that
(length of substring - number of times of the maximum occurring character in the substring) <= k
    int characterReplacement(string s, int k) {
        vector<int> counts(26, 0);
        int start = 0;
        int maxCharCount = 0;
        int n = s.length();
        int result = 0;
        for(int end = 0; end < n; end++){
            counts[s[end]-'A']++;
            if(maxCharCount < counts[s[end]-'A']){
                maxCharCount = counts[s[end]-'A'];
            }
            while(end-start-maxCharCount+1 > k){
                counts[s[start]-'A']--;
                start++;
                for(int i = 0; i < 26; i++){
                    if(maxCharCount < counts[i]){
                        maxCharCount = counts[i];
                    }
                }
            }
            result = max(result, end-start+1);
        }
        return result;
    }

X.
http://bookshadow.com/weblog/2016/10/16/leetcode-longest-repeating-character-replacement/
定义一段区间内出现次数最多的字符为“优势字符”
维护有效区间[p1, p2]，使得区间内除“优势字符”外的其余字符个数不大于k
时间复杂度O(m * n)，其中m为字母个数, n为字符串长度
https://scottduan.gitbooks.io/leetcode-review/content/longest_repeating_character_replacement.html
Since we are only interested in the longest valid substring, our sliding windows need not shrink, even if a window may cover an invalid substring. We either grow the window by appending one char on the right, or shift the whole window to the right by one. And we only grow the window when the count of the new char exceeds the historical max count (from a previous window that covers a valid substring).
That is, we do not need the accurate max count of the current window; we only care if the max count exceeds the historical max count; and that can only happen because of the new char.
https://discuss.leetcode.com/topic/63494/java-12-lines-o-n-sliding-window-solution-with-explanation
There's no edge case for this question. The initial step is to extend the window to its limit, that is, the longest we can get to with maximum number of modifications. Until then the variable start will remain at 0.
Then as end increase, the whole substring from 0 to end will violate the rule, so we need to update start accordingly (slide the window). We move start to the right until the whole string satisfy the constraint again. Then each time we reach such situation, we update our max length.
The solution is great, but I have a small question here :
when you move the start pointer, why we the max count may change:
for example :
s = "bbcdef", k = 2.
when move end to 'e', we need to remove first 'b' so that maxcount will change to 1.
But for s = "bbcdd", it wont change when we move end to second 'd' and remove first 'b'.
In this question we may don't need to worry about that because we just need to get maxlength of substring. But what if the question is to find all the strategy, can we try to use similar method ?

when move the left begin of b, maxcount will not be change to 1.It will always the biggest one unless another one is more than it. That is why explain that
our sliding windows need not shrink, even if a window may cover an invalid substring
this situation it will shift the whole window to the right by one

We use a window.
The window starts at the left side of the string and the length of the window is initialized to zero.
The window only stays the same or grows in length as the algorithm proceeds.
The algorithm grows the window to the maximum valid length according to the rules of the game.
The algorithm returns the length of the window upon completion.
Setup:
The length of the window is defined by the following equation: end - start + 1.
The values of both start and end are subject to change during the execution of the algorithm.
The value of end starts at 0 and gets incremented by 1 with each execution of the loop.
But unless a certain condition is met, the value of start will also gets incremented with each execution of the loop, keeping the length of the window unchanged.
That condition is met when the number of the most commonly occuring character in the window + k is at least as large as the length of the window (the value of k determines how many of the less commonly occurring characters there can be). This condition would be required to create a string containing all the same characters from the characters contained within the window.
Execution:
Right in the beginning, the length of the window is going to be able to grow to at least the value of k.
After that initial growth, the algorithm becomes a search to find the window that contains the greatest number of reoccurring characters.
Whenever including the character at end results in an increase in the greatest number of reoccurring characters ever encountered in a window tested so far, the window grows by one (by not incrementing start).
When determining whether or not including another character at the end of the window results in the increase described above, only the occurrence of the newly included character in the window and the running all-time max need to be taken into account (after all, only the frequency of the newly included character is increasing).
Even if/when the value of start is incremented (i.e. the left side of the window is moved to the right), the all-time max doesn't need to be reset to reflect what's currently in the window because 1) at this point in the algorithm, the all-time maximum number of reoccurring characters in a window is what we're using to determine the all-time longest window; 

2) we only care about positive developments in our search (i.e. we find a window that contains an even greater number of reoccurring characters than any other window we have tested so far and therefore is longer than any window we have tested so far). The algorithm becomes a search for the max and we only need to set the max when we have a new max.
public int characterReplacement(String s, int k)
{
    int[] count = new int[128];
    int max=0;
    int start=0;
    for(int end=0; end<s.length(); end++)
    {
        max = Math.max(max, ++count[s.charAt(end)]);
        if(max+k<=end-start)
            count[s.charAt(start++)]--;
    }
    return s.length()-start;//??
}
    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }
X.  https://discuss.leetcode.com/topic/63465/java-o-n-solution-using-sliding-window
http://blog.csdn.net/MebiuW/article/details/52830364
The idea is to find maximum valid substring with repeated character 'A' to 'Z' respectively. For each case, use sliding window to determine its maximum length, update the global maximum length if needed.
    public int characterReplacement(String s, int k) {
        int maxLen = 0;
        for(int l = 0 ; l<26;l++){
            char c = (char)('A' + l); //repeated char we are looking for
            int i = 0, j = 0, count = 0;
            while(j<s.length()){
                char cur = s.charAt(j);
                if(cur != c) count++;
                
                //make the substring valid again
                while(count > k){
                    if(s.charAt(i) != c) count--;
                    i++;
                }
                
                //update maximun len
                maxLen = Math.max(maxLen,j-i+1);
                j++;
            }
        }
        return maxLen;
    }
X. DFS http://brookebian.blogspot.com/2016/10/424-longest-repeating-character.html
   Set<Character> dict = new HashSet<>();  
   int max = 0;  
   public int characterReplacement(String s, int k) {  
     if(s.length() == 0) return 0;  
     for(int i = 0; i < s.length(); i++)  
       dict.add(s.charAt(i));  
     dfs(s.toCharArray(),k,new HashSet<>());  
     return max;  
   }  
   public void dfs(char[] chars,int k,Set<Integer> visited){  
     int[] count = count(String.valueOf(chars));  
     max = Math.max(count[count.length-1],max);  
     if(k == 0)  
       return;  
     for(int i = 0; i < chars.length; i++){  
       if(visited.contains(i))  
         continue;  
       char tmp = chars[i];  
       for(char c: dict){  
         if(c==tmp )  
           continue;  
         chars[i] = c;  
         visited.add(i);  
         dfs(chars,k-1,visited);  
         visited.remove(i);  
       }  
       chars[i] = tmp;  
     }  
     return ;  
   }  
   public int[] count(String s){  
     int[] count = new int[s.length()+1];  
     count[0]=1;  
     count[s.length()-1]=1;  
     int max = 1;  
     for(int i = 1; i < s.length(); i++){  
       count[i] = s.charAt(i) != s.charAt(i-1)? 1:count[i-1] + 1;  
       max = Math.max(max,count[i]);  
     }  
     for(int i = s.length()-2; i >= 0; i--){  
       if(s.charAt(i) == s.charAt(i+1))  
         count[i] = count[i+1];  
     }  
     count[s.length()]=max;  
     return count;  
   }  
   public int[] copy(int[] nums){  
     int n = nums.length;  
     int[] copy = new int[n];  
     for(int i = 0; i < n; i++){  
       copy[i] = nums[i];  
     }  
     return copy;  
   } 

http://bookshadow.com/weblog/2016/10/16/leetcode-longest-repeating-character-replacement/
解法II 统计单词连续区间 + 枚举字母 + 枚举区间起止点
时间复杂度O(m * n)，其中m为字母个数, n为区间数
首先统计出字符串内各单词所在的连续区间，记为cdict，例如AABABBA得到的区间为{A : [(0, 1), (3, 3), (6, 6)], B : [(2, 2), (4, 5)]}
然后枚举需要保留的字母
尝试用k值填补区间之间的间隙，并更新最优答案。
    def characterReplacement(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        sizes = len(s)
        letters = set(s)

        cdict = collections.defaultdict(list)
        li, lc = 0, (s[0] if s else None)
        for i, c in enumerate(s):
            if c != lc:
                cdict[lc].append( (li, i - 1) )
                li, lc = i, c
        cdict[lc].append( (li, sizes - 1) )

        ans = 0
        for c in letters:
            invs = cdict[c]
            ans = max(ans, max(y - x + 1 + min(k, x + sizes - 1 - y) for x, y in invs))
            sizec = len(invs)
            cnt = k
            sp = 0
            ep = 1
            while sp < sizec and ep < sizec:
                if cnt >= invs[ep][0] - invs[ep - 1][1] - 1:
                    cnt -= invs[ep][0] - invs[ep - 1][1] - 1
                    lenc = invs[ep][1] - invs[sp][0] + 1 + min(cnt, invs[sp][0] + sizes - 1 - invs[ep][1])
                    ans = max(ans, lenc)
                    ep += 1
                else:
                    sp += 1
                    cnt += invs[sp][0] - invs[sp - 1][1] - 1
        return ans
 * @author het
 *
 */
public class LeetCode424LongestRepeatingCharacterReplacement {
	  public int characterReplacement(String s, int k) {
	        if(s == null || s.length() == 0){
	            return 0;
	        }
	        int max = 0;
	        int[] ch = new int[26];
	        char[] str = s.toCharArray();
	        for(int i=0, j=0; i<s.length(); i++){
	            while(j < s.length()){
	                ch[str[j] - 'A']++;
	                if(count(ch) > k){  //If exceed k, break
	                    ch[str[j] - 'A']--;
	                    break;
	                }
	                j++;
	            }
	            max = Math.max(max, j-i);
	            ch[str[i] - 'A']--;
	        }
	        return max;
	    }
	    //Count the number of character that is different to the longest character
	    public int count(int[] ch){
	        int max = 0;
	        int sum = 0;
	        for(int val:ch){
	            sum += val;
	            max = Math.max(max, val);
	        }
	        return sum - max;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//s = "AABABBA", k = 1
		System.out.println(new LeetCode424LongestRepeatingCharacterReplacement().characterReplacement("AABABBA", 1));

	}

}
