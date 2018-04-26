package alite.leetcode.newest;
/**
 * LeetCode 616 - Add Bold Tag in String

https://leetcode.com/articles/add-bold-tag-in-a-string/
Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist
 in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped
  by bold tags are consecutive, you need to combine them.
Example 1:
Input: 
s = "abcxyz123"
dict = ["abc","123"]
Output:
"<b>abc</b>xyz<b>123</b>"
Example 2:
Input: 
s = "aaabbcc"
dict = ["aaa","aab","bc"]
Output:
"<b>aaabbc</b>c"
Note:
The given dict won't contain duplicates, and its length won't exceed 100.
All the strings in input have length in range [1, 1000].
Approach #3 Using Boolean(Marking) Array
Time complexity : O(l*s*x)O(l∗s∗x). Three nested loops are there to fill boldbold array.
Space complexity : O(s)O(s). resres and boldbold size grows upto O(s)O(s).
Another idea could be to merge the process of identification of the substrings in ss matching with the words in dictdict. To do so, we make use of an array boldboldfor marking the positions of the substrings in ss which are present in dictdict. A True value at bold[i]bold[i] indicates that the current character is a part of the substring which is present in dictdict.
We identify the substrings in ss which are present in dictdict similar to the last approach, by considering only substrings of length length_dlength
​d
​​  for a dictionary word dd. Whenver such a substring is found with its beginning index as ii(and end index (i + length_d -1)(i+length
​d
​​ −1)), we mark all such positions in boldbold as True.
Thus, in this way, whenever a overlapping or consecutive matching substrings exist in ss, a continuous sequence of True values is present in boldbold. Keeping this idea in mind, we traverse over the string ss and keep on putting the current character in the resultant string resres. At every step, we also check if the boldboldarray contains the beginning or end of a continuous sequence of True values. At the beginnning of such a sequence, we put an opening bold tag and then keep on putting the characters of ss till we find a position corresponding to which the last sequence of continuous True values breaks(the first False value is found). We put a closing bold tag at such a position. After this, we again keep on putting the characters of ss in resres till we find the next True value and we keep on continuing the process in the same manner.
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        for (String d: dict) {
            for (int i = 0; i <= s.length() - d.length(); i++) {
                if (s.substring(i, i + d.length()).equals(d)) {
                    for (int j = i; j < i + d.length(); j++)
                        bold[j] = true;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length();) {
            if (bold[i]) {
                res.append("<b>");
                while (i < s.length() && bold[i])
                    res.append(s.charAt(i++));
                res.append("</b>");
            } else
                res.append(s.charAt(i++));
        }
        return res.toString();
    }
http://www.cnblogs.com/grandyang/p/7043394.html
思路是建一个和字符串s等长的bold布尔型数组，表示如果该字符在单词里面就为true，那么最后我们就可以根据bold数组的真假值来添加标签了。我们遍历字符串s中的每一个字符，把遍历到的每一个字符当作起始位置，我们都匹配一遍字典中的所有单词，如果能匹配上，我们就用i + len来更新end，len是当前单词的长度，end表示字典中的单词在字符串s中结束的位置，那么如果i小于end，bold[i]就要赋值为true了。最后我们更新完bold数组了，就再遍历一遍字符串s，如果bold[i]为false，直接将s[i]加入结果res中；如果bold[i]为true，那么我们用while循环来找出所有连续为true的个数，然后在左右两端加上标签
https://discuss.leetcode.com/topic/92112/java-solution-boolean-array
Use a boolean array to mark if character at each position is bold or not. After that, things will become simple.
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        for (int i = 0, end = 0; i < s.length(); i++) {
            for (String word : dict) {
                if (s.startsWith(word, i)) {
                    end = Math.max(end, i + word.length());
                }
            }
            bold[i] = end > i;
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!bold[i]) {
                result.append(s.charAt(i));
                continue;
            }
            int j = i;
            while (j < s.length() && bold[j]) j++;
            result.append("<b>" + s.substring(i, j) + "</b>");
            i = j - 1;
        }
        
        return result.toString();
    }
    for (int i = 0, end = 0; i < s.length(); i++) { // For every character in `s`,
        for (String word : dict) { // For every `word` in `dict`, we test:
            // If substring s[i, i + word.length()] == word, meaning characters between i, 
            // i + word.length() should be `bold`.
            if (s.startsWith(word, i)) {
                // Use variable `end` to store known longest end of current continuous `bold` characters
                end = Math.max(end, i + word.length());
            }
        }
        // If `end` > `i`, meaning character at position `i` is within the current continuous `bold`
        // characters, we mark it as `bold`.
        bold[i] = end > i;
    }

Approach #2 Similar to Merge Interval Problem [Accepted]
We can pick up every word of the dictionary. For every word dd of the dictionary chosen currently, say of length length_dlength
​d
​​ , it is obvious that the substrings in ss only with length length_dlength
​d
​​ , can match with the dd. Thus, instead of blindly checking for dd's match with every substring in ss, we check only the substrings with length length_dlength
​d
​​ . The matching substrings' indices are again added to the listlist similar to the last approach.
Time complexity : O(l*s*x)O(l∗s∗x). Generating list will take O(l*s*x)O(l∗s∗x), where xx is the average string length of dictdict.
Space complexity : O(s+s*l)O(s+s∗l). resres size grows upto O(s)O(s) and listlist size can grow upto O(s*l)O(s∗l) in worst case.
    public String addBoldTag(String s, String[] dict) {
        List < int[] > list = new ArrayList < > ();
        for (String d: dict) {
            for (int i = 0; i <= s.length() - d.length(); i++) {
                if (s.substring(i, i + d.length()).equals(d))
                    list.add(new int[] {i, i + d.length() - 1});
            }
        }
        if (list.size() == 0)
            return s;
        Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int start, prev = 0, end = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            res.append(s.substring(prev, list.get(i)[0]));
            start = i;
            end = list.get(i)[1];
            while (i < list.size() - 1 && list.get(i + 1)[0] <= end + 1) {
                end = Math.max(end, list.get(i + 1)[1]);
                i++;
            }
            res.append("<b>" + s.substring(list.get(start)[0], end + 1) + "</b>");
            prev = end + 1;
        }
        res.append(s.substring(end + 1, s.length()));
        return res.toString();
    }

Approach #1 Brute Force [Time Limit Exceeded]
Time complexity : O(s^3)O(s
​3
​​ ). Generating list of intervals will take O(s^3)O(s
​3
​​ ), where ss represents string length.
Space complexity : O(s+d+s*l)O(s+d+s∗l). resres size grows upto ss and setset size will be equal to the size of dictdict. Here, dd refers to the size of dictdict.And listlistsize can grow upto O(s*l)O(s∗l) in worst case, where ll refers to dictdict size.
    public String addBoldTag(String s, String[] dict) {
        List < int[] > list = new ArrayList < > ();
        Set < String > set = new HashSet < > (Arrays.asList(dict));
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (set.contains(s.substring(i, j + 1)))
                    list.add(new int[] {i, j});
            }
        }
        if (list.size() == 0)
            return s;
        Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int start, prev = 0, end = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            res.append(s.substring(prev, list.get(i)[0]));
            start = i;
            end = list.get(i)[1];
            while (i < list.size() - 1 && list.get(i + 1)[0] <= end + 1) {
                end = Math.max(end, list.get(i + 1)[1]);
                i++;
            }
            res.append("<b>" + s.substring(list.get(start)[0], end + 1) + "</b>");
            prev = end + 1;
        }
        res.append(s.substring(end + 1, s.length()));
        return res.toString();
    }

https://discuss.leetcode.com/topic/92130/short-java-solution
    public String addBoldTag(String s, String[] dict) {
        int n = s.length();
        int[] mark = new int[n+1];
        for(String d : dict) {
            int i = -1;
            while((i = s.indexOf(d, i+1)) >= 0) {
                mark[i]++;
                mark[i + d.length()]--;
            }
        }
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for(int i = 0; i <= n; i++) {
            int cur = sum + mark[i];
            if (cur > 0 && sum == 0) sb.append("<b>");
            if (cur == 0 && sum > 0) sb.append("</b>");
            if (i == n) break;
            sb.append(s.charAt(i));
            sum = cur;
        }
        return sb.toString();
    }
https://www.nowtoshare.com/en/Article/Index/70576
 * @author het
 *
 */
public class LeetCode616 {

}
