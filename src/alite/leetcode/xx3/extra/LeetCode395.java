package alite.leetcode.xx3.extra;

import java.util.HashMap;
import java.util.HashSet;

/**
 * LeetCode 395 - Longest Substring with At Least K Repeating Characters

https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
Find the length of the longest substring T of a given string (consists of lowercase letters only) 
such that every character in T appears no less than k times.
Example 1:
Input:
s = "aaabb", k = 3

Output:
3

The longest substring is "aaa", as 'a' is repeated 3 times.
Example 2:
Input:
s = "ababbc", k = 2

Output:
5

The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
X.
https://discuss.leetcode.com/topic/57501/java-20-lines-very-easy-solution-7ms-with-explanation/2
https://tech.liuchao.me/2016/09/leetcode-solution-395/

    public int longestSubstring(String s, int k) {

        if (s.length() < k) return 0;


        int[] count = new int[26];

        for (char c: s.toCharArray()) {

            count++;

        }


        List<String> badChars = new ArrayList<>();

        for (int i = 0; i < count.length; i++) {

            if (count[i] < k && count[i] != 0) badChars.add(String.valueOf((char)(i + 'a')));

        }

        if (badChars.isEmpty()) return s.length();


        int result = 0;

        String[] targets = s.split(String.join("|", badChars));

        for (String tmpString: targets) {

            result = Math.max(result, longestSubstring(tmpString, k));

        }

        return result;

    }
http://www.voidcn.com/blog/niuooniuoo/article/p-6199475.html


 public int longestSubstring(String s, int k) {
  if (s == null || s.length() == 0) return 0;
  int[] nums = new int[26];
  char[] chars = s.toCharArray();
  for (char c : chars) nums[c - 'a']++;
  boolean valid = false, flag = false;
  for (int num : nums) {
   if (num >= k) valid = true;
   if (num > 0 && num < k) flag = true;
   if (valid && flag) break;
  }
  if (!valid) return 0;// invalid: no char in s appears >= k times.
  if (!flag) return s.length();//!flag: every char appears >= k times.
  int max = 0, start = 0, cur = 0;
  for (int i = 0; i < s.length(); i++) {
   if (nums[chars[i] - 'a'] < k) {
    max = Math.max(max, longestSubstring(s.substring(start, i), k));
    start = i + 1;
   }
  }
  max = Math.max(max, longestSubstring(s.substring(start), k));
  return max;
 }

https://discuss.leetcode.com/topic/57265/java-d-c-solution
https://discuss.leetcode.com/topic/57372/java-3ms-divide-and-conquer-recursion-solution
The idea is pretty basic, find the point where we should split the string, eg, the position of character which total count is <k, then dfs it then find the max.
For Example: bbcddefegaghfh and 2, so we shall dfs on "bb", "ddefeg", "ghfh", since a , c only appears1 for once.
public int longestSubstring(String s, int k) {
       if (s == null || s.length() < k || k == 0 ) return 0;
       int max = 0;
       int[] count = new int[26];
       int res = 0;
       for (int i = 0; i < s.length(); i++) {
           count[s.charAt(i) - 'a']++;
       }
       List<Integer> pos = new ArrayList<Integer>();
       for (int i = 0; i < s.length(); i++) {
           if (count[s.charAt(i) - 'a'] < k) pos.add(i);
       }
       if (pos.size() == 0) return s.length();
       pos.add(0, -1);
       pos.add(s.length());
       for (int i = 1; i < pos.size(); i++) {
           int start = pos.get(i-1) + 1;
           int end = pos.get(i);
           int next = longestSubstring(s.substring(start, end), k);
           res = Math.max(res, next);
       }
       return res;
   }
https://discuss.leetcode.com/topic/57092/4-lines-python/
Only 7ms for Java solution without looking for the least occurred character, i.e., split the string by any character that doesn't appear for k times.
    public int longestSubstring(String s, int k) {
        int n = s.length();
        if (n==0 || n<k) return 0;
        if (k==1) return n;
        int[] counts = new int[26];
        for (char c: s.toCharArray()) counts[c-'a']++;
        boolean valid = true;
        char badchar = 0;
        for (int i=0; i<26; i++) {
            if (counts[i]>0 && counts[i]<k) {
                badchar = (char)(i+'a');
                break;
            }
        }
        if (badchar==0) return n;
        String[] subs = s.split(badchar+"");
        int res = 0;
        for (String sub:subs) res = Math.max(res, longestSubstring(sub,k));
        return res;
    }

public int longestSubstring(String s, int k) {
    if (s.length() == 0 || k == 1) return s.length();
    if (s.length() < k) return 0;
    
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c-'a'] ++;
    boolean eligible = true;
    for (int i = 0; i < 26; i ++) {
        if (count[i] != 0 && count[i] < k) {eligible = false;break;}
    }
    if (eligible) return s.length();
    char least = 0;
    for (int i = 0; i < 26; i ++) {
        if (count[i] != 0 && least == 0) {least = (char)(i+'a');continue;} 
        if (count[i]!=0 && count[i] < count[least - 'a']) least = (char)(i+'a');
    }
    String[] sub = s.split(least+"");
    int res = 0;
    for (String str : sub) {
        res = Math.max(res, longestSubstring(str, k));
    }
    return res;
}

As pointed out by @hayleyhu, I can just take the first too rare character instead of a rarest. Submitted once, accepted in 48 ms.
def longestSubstring(self, s, k):
    for c in set(s):
        if s.count(c) < k:
            return max(self.longestSubstring(t, k) for t in s.split(c))
    return len(s)
Original:

def longestSubstring(self, s, k):
    if len(s) < k:
        return 0
    c = min(set(s), key=s.count)
    if s.count(c) >= k:
        return len(s)
    return max(self.longestSubstring(t, k) for t in s.split(c))
If every character appears at least k times, the whole string is ok. Otherwise split by a least frequent character
 (because it will always be too infrequent and thus can't be part of any ok substring) and make the most out of the splits.
https://discuss.leetcode.com/topic/57134/two-short-c-solutions-3ms-and-6ms
Sol1: a simple improvement on the naive quaratic solution. The idea is that if a locally longest substr is found, there's no need to check substrs overlapping it.
Sol1 can run O(n) times in some cases, but worst case is O(n2).
int longestSubstring(string s, int k) {
   int max_len = 0;
   for (int first = 0; first+k < s.size();) {
       int count[26] = {0};
       int mask = 0;
       int max_last = first;
       for (int last = first; last < s.size(); ++last) {
           int i = s[last] - 'a';
           count[i]++;
           if (count[i]<k) mask |= (1 << i);
           else   mask &= (~(1 << i));
           
           if (mask == 0) {
               max_len = max(max_len, last-first+1);
               max_last = last;
           }
       }
       first = max_last + 1;
   }
   return max_len;
}
Sol2: recursive: split the string into substrs by characters of occurrence less than k. Then recursively apply the problem to each substr.
Worst case of Sol2 is O(n), because there are at most 26 levels of recursions. The C++ impl. runs 6ms. I suspect this is because the current test cases does not cover enough cases in favor of this solution in run time.
int longestSubstring(string s, int k) {
    return longestSubstring_recur(s, k, 0, s.size());
}

int longestSubstring_recur(const string& s, int k, int first, int last) {
    int count[26] = {0};
    for (int j = first; j < last; ++j) ++count[s[j] - 'a'];
    
    int max_len = 0;
    for (int j = first; j < last;) {
        while (j < last && count[s[j]-'a']<k) ++j;
        if (j == last) break;
        int l = j;
        while (l < last && count[s[l]-'a']>=k) ++l;
        //all chars appear more than k times
        if (j == first && l == last) return last-first; 
        max_len = max(max_len, longestSubstring_recur(s, k, j, l));
        j = l;
    }
    return max_len;
}
http://www.cnblogs.com/grandyang/p/5852352.html
发现我当时的没做出来的原因主要是卡在了如何快速的判断某一个字符串是否所有的元素都已经满足了至少出现k次这个条件，虽然我也用哈希表建立了字符和其出现次数之间的映射，但是如果每一次都要遍历哈希表中的所有字符看其出现次数是否大于k，未免有些不高效。而用mask就很好的解决了这个问题，由于字母只有26个，而整型mask有32位，足够用了，每一位代表一个字母，如果为1，表示该字母不够k次，如果为0就表示已经出现了k次，这种思路真是太聪明了，隐约记得这种用法在之前的题目中也用过，但是博主并不能举一反三(沮丧脸:()，还得继续努力啊。我们遍历字符串，对于每一个字符，我们都将其视为起点，然后遍历到末尾，我们增加哈希表中字母的出现次数，如果其小于k，我们将mask的对应位改为1，如果大于等于k，将mask对应位改为0。然后看mask是否为0，是的话就更新res结果，然后把当前满足要求的子字符串的起始位置j保存到max_idx中，等内层循环结束后，将外层循环变量i赋值为max_idx+1，继续循环直至结束
    int longestSubstring(string s, int k) {
        int res = 0, i = 0, n = s.size();
        while (i + k < n) {
            int m[26] = {0}, mask = 0, max_idx = i;
            for (int j = i; j < n; ++j) {
                int t = s[j] - 'a';
                ++m[t];
                if (m[t] < k) mask |= (1 << t);
                else mask &= (~(1 << t));
                if (mask == 0) {
                    res = max(res, j - i + 1);
                    max_idx = j;
                }
            }
            i = max_idx + 1;
        }
        return res;
    }


X.
http://www.guoting.org/leetcode/leetcode-395-longest-substring-with-at-least-k-repeating-characters/
首先,跟通常的类似题目的处理方式一样,我们先统计出字符串中每个字符出现的次数,如果统计出来的每个字符出现的次数都不小于k,说明原字符串是符合条件的,直接返回原字符串的长度即可;如果统计出来的字符次数有小于k的,说明直接返回原字符串的长度已经不合适了,因为有的字符不符合条件,那么这时候我们应该怎么做呢?
我们想,既然我们已经知道了原字符串中有些字符出现的次数是小于k的,那么我们要求的满足题目条件的最长子串中肯定是不能包含这些字符的,那么我们应该怎么做呢?
我们这样做,我们记录下这些字符在原字符串中出现的位置,这些字符会把原字符串分成一段一段的,但是分割之后的字符子串也不一定肯定符合条件,仍需要对每段进行判断,接下来我们对分割之后的每一段继续判断,找出其中的最大值即可。
    public int longestSubstring(String s, int k) {
        if(s==null||s.length()==0||s.length()<k) return 0;
        if(k<=1) return s.length();
        int n=s.length();
        int[] count=new int[26];
        int max=0;
        for(int i=0;i<n;i++){
            count[s.charAt(i)-'a']++;
        }
        List<Integer> pos=new ArrayList<>();
        for(int i=0;i<n;i++){
            if(count[s.charAt(i)-'a']<k) pos.add(i);
        }
        if(pos.size()==0) return n;
        pos.add(0,-1);
        pos.add(n);
        for(int i=1;i<pos.size();i++){
            int start=pos.get(i-1)+1;
            int end=pos.get(i);
            max=Math.max(max,longestSubstring(s.substring(start,end),k));
        }
        return max;
    }
http://www.programcreek.com/2014/09/leetcode-longest-substring-with-at-least-k-repeating-characters-java/
public int longestSubstring(String s, int k) {
    HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
 
    for(int i=0; i<s.length(); i++){
 
        char c = s.charAt(i);
        if(counter.containsKey(c)){
            counter.put(c, counter.get(c)+1);
        }else{
            counter.put(c, 1);
        }
 
    }
 
    HashSet<Character> splitSet = new HashSet<Character>();
    for(char c: counter.keySet()){
        if(counter.get(c)<k){
            splitSet.add(c);
        }
    }
 
    if(splitSet.isEmpty()){
        return s.length();
    }
 
    int max = 0;
    int i=0, j=0;
    while(j<s.length()){
        char c = s.charAt(j);
        if(splitSet.contains(c)){
            if(j!=i){
                max = Math.max(max, longestSubstring(s.substring(i, j), k));
            }
            i=j+1;
        }
        j++;
    }
 
    if(i!=j)
         max = Math.max(max, longestSubstring(s.substring(i, j), k));
 
    return max;
}
https://all4win78.wordpress.com/2016/09/08/leetcode-395-longest-substring-with-at-least-k-repeating-characters/comment-page-1/
算法基本原理是，先遍历整个string，并记录每个不同的character的出现次数。如果所有character出现次数都不小于k，那么说明整个string就是满足条件的longest substring，返回原string的长度即可；如果有character的出现次数小于k，假设这个character是c，因为满足条件的substring永远不会包含c，所以满足条件的substring一定是在以c为分割参考下的某个substring中。所以我们需要做的就是把c当做是split的参考，在得到的String[]中再次调用我们的method，找到最大的返回值即可。
当然，这样每次都需要统计character的次数，不是特别高效，优化的话可以保存一个全局的数组来保存统计的次数，每一行是每个不同的character，而每一列是每个character在原始string中到某个位置时出现了多少次，所以大小应该是(# of different characters) x (length of original string)。当然这里具体细节还是有点复杂，需要的空间可能也会比较大（最差情况n*n）。我这里就偷懒没有实现，有兴趣的朋友可以自己试试看
http://blog.csdn.net/mebiuw/article/details/52449892
    public int longestSubstring(String s, int k) {
        //System.out.println(s);
        int n = s.length();
        if(n < k) return 0;
        int counter[] = new int[26];
        boolean valid[] = new boolean[26];
        char ss[] = s.toCharArray();
        //统计每个字符的长度
        for(int i=0;i<n;i++) 
            counter[ss[i] - 'a']++;
        //检查当前字符串是否是完全满足的
        boolean fullValid = true;
        //判断每个字符的出现条件是否合适，即，要么不出现，要么出现了不少于k
        for(int i=0;i<26;i++){
            if(counter[i]>0 && counter[i]<k){
                valid[i] = false;
                fullValid = false;
            }
            else valid[i] = true;
        }
        if(fullValid) return s.length();
        int max = 0;
        int lastStart=0;
        //把不符合要求的断开，然后依次检查 取最大
        for(int i=0;i<n;i++){
            if(valid[ss[i] - 'a'] == false){
               // System.out.println(lastStart+"  "+i);
                max = Math.max(max,longestSubstring(s.substring(lastStart,i),k));
                lastStart = i + 1;
            }
        }
        max = Math.max(max,longestSubstring(s.substring(lastStart,n),k));
        return max;

    }

http://xiadong.info/2016/09/leetcode-395-longest-substring-with-at-least-k-repeating-characters/
先遍历一遍字符串, 记录每个字符的出现次数, 在这一步中同时记录出现大于等于k次的字母个数, 如果根本就没有大于等于k次的字母, 那么可以直接返回0.
通过出现次数不足k次的字母来把字符串分割成多个子串, 因为在原字符串中出现次数不足k次的字母必然不会出现在结果串中.
递归的处理每个子串. 递归结束条件为字符串长度不足k.
http://www.cnblogs.com/dongling/p/5843874.html

X.
https://scottduan.gitbooks.io/leetcode-review/content/longest_substring_with_at_least_k_repeating_charac.html
The idea is to keep finding the character which does not satisfy the requirement. (repeat less than k times). And divide the problem into 2 parts, to find the longest substring on the lhs and rhs of the breaking point. For divide and conquer, it needs O(log(n)) calls of helper function to check the entire string and in the helper function, it costs O(n) to find such breaking point. In total, this algorithm is O(nlog(n)).
public int longestSubstring(String s, int k) {
    char[] str = s.toCharArray();
    return helper(str,0,s.length(),k);
}
private int helper(char[] str, int start, int end,  int k){
    if(end<start) return 0;
    if(end-start<k) return 0;//substring length shorter than k.
    int[] count = new int[26];
    for(int i = start;i<end;i++){
        int idx = str[i]-'a';
        count[idx]++;
    }
    for(int i = 0;i<26;i++){
        if(count[i]==0)continue;//i+'a' does not exist in the string, skip it.
        if(count[i]<k){
            for(int j = start;j<end;j++){
                if(str[j]==i+'a'){
                    int left = helper(str,start,j,k);
                    int right = helper(str,j+1,end,k);
                    return Math.max(left,right);
                }
            }
        }
    }
    return end-start;
}
http://bookshadow.com/weblog/2016/09/04/leetcode-longest-substring-with-at-least-k-repeating-characters/
递归（Recursion）/ 分治法（Divide and Conquer）
统计原始字符串s中各字符的出现次数，统计其中出现次数少于k次的字符，得到数组filters。
若filters为空数组，则直接返回s的长度。
以filters为分隔符，将s拆分为若干子串，分别递归计算各子串的结果，返回最大值。
    def longestSubstring(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        cnt = collections.Counter(s)
        filters = [x for x in cnt if cnt[x] < k]
        if not filters: return len(s)
        tokens = re.split('|'.join(filters), s)
        return max(self.longestSubstring(t, k) for t in tokens)

http://www.itdadao.com/articles/c15a340739p0.html
先统计出字符串中每个字符出现的次数，对于出现次数少于k的字符，任何一个包含该字符的字符串都不是符合要求的子串，因此这样的字符就是分隔符，应该以这些出现次数少于k次的字符做分隔符打断原字符串，然后对各个打断得到的字符串进行递归统计，得到最长的符合要求的字符串。如果一个字符串中不包含分隔符(即每个字符出现的次数都达到了k次及以上次数)，那么这个字符串就是符合要求的子串。
    public int longestSubstring(String s, int k) {
        if(k<=1){
            return s.length();
        }
        
        int[] repeat=new int[128];
        for(int i=0;i<s.length();i++){
            repeat[s.charAt(i)]++;
        }
        StringBuilder reg=new StringBuilder("");
        boolean firstSplit=true;
        for(int i='a';i<='z';i++){
            if(repeat[i]>0&&repeat[i]<k){
                if(firstSplit){
                    reg.append((char)i);
                    firstSplit=false;
                }
                else{
                    reg.append("|"+(char)i);
                }
            }
        }
        if(reg.length()>0){
            //说明有分隔符
            String[] strs=s.split(reg.toString());
            int max=0;
            int tmpAns=0;
            for(String str:strs){ // if str.length< max, ignore it
                tmpAns=longestSubstring(str, k);
                if(tmpAns>max){
                    max=tmpAns;
                }
            }
            return max;
        }
        else{
            //没有分隔符,说明s中的每一个字符出现的次数都大于k
            return s.length();
        }
    }
https://discuss.leetcode.com/topic/57699/simple-java-o-n-solution
The exact time complexity is O(26n) = O(n), where n is the length of the input string.
The proof is simply that every time, we remove a letter (all of that one) from string (or sub-string). So the maximum level of recursion tree is 26, since there are only 26 letters in this case. And at each level, the total characters we need process are no more than n. Thus, the time complexity is O(n).
Visualization:
[00000000000000000000000000] number of kinds of letters remained: no more than L (L<=26);
[000000000].....[000000]...[00000] number of kinds of letters remained no more than L-1;

https://discuss.leetcode.com/topic/57092/6-lines-python
def longestSubstring(self, s, k):
    if len(s) < k:
        return 0
    c = min(set(s), key=s.count)
    if s.count(c) >= k:
        return len(s)
    return max(self.longestSubstring(t, k) for t in s.split(c))
If every character appears at least k times, the whole string is ok. Otherwise split by a least frequent character (because it will always be too infrequent and thus can't be part of any ok substring) and make the most out of the splits.

https://discuss.leetcode.com/topic/57343/share-java-ac-solution-3ms
public int longestSubstring(String s, int k) {

    return divide(s,0,s.length()-1,k);        
}

private int divide(String s, int start, int end, int k){
    // pass impossible cases
    if(end<start) return 0;
    if(end-start+1<k) return 0;
    
    char[] schar = s.toCharArray();
    // count the occurence of each letter in the string from start and end;
    int[] cnt = new int[26];

    int max = 0;
    // count frequence of each character
    for(int i=start;i<=end;i++){
        int index = schar[i]-'a';
        cnt[index]++;
    }
    
    // check if there is a letter which occurence is smaller than k
    // if there it is, divide it and rerun.
    for(int i=0;i<26;i++){
        if(cnt[i]==0) continue;
        if(cnt[i]<k){
            char c = (char) (i+'a');
            int smaller = s.indexOf(c,start);
            if(smaller>=0){
                int left = divide(s,start,smaller-1,k);
                int right = divide(s,smaller+1,end, k);
                return Math.max(left, right);
            }else{
                return 0;
            }
        }
    }
    return end-start+1;  // if every letter comes along more than k times
}
https://discuss.leetcode.com/topic/57265/java-d-c-solution
The idea is pretty basic, find the point where we should split the string, eg, the position of character which total count is <k, then dfs it then find the max.
For Example: bbcddefegaghfh and 2, so we shall dfs on "bb", "ddefeg", "ghfh", since a , c only appears1 for once.
// use substring, not efficient, use char[] instead
public int longestSubstring(String s, int k) {
       if (s == null || s.length() == 0 || k == 0) return 0;
       int max = 0;
       int[] count = new int[26];
       int res = 0;
       for (int i = 0; i < s.length(); i++) {
           count[s.charAt(i) - 'a']++;
       }
       List<Integer> pos = new ArrayList<Integer>();
       for (int i = 0; i < s.length(); i++) {
           if (count[s.charAt(i) - 'a'] < k) pos.add(i);
       }
       if (pos.size() == 0) return s.length();
       pos.add(0, -1);
       pos.add(s.length());
       for (int i = 1; i < pos.size(); i++) {
           int start = pos.get(i-1) + 1;
           int end = pos.get(i);
           int next = longestSubstring(s.substring(start, end), k);
           res = Math.max(res, next);
       }
       return res;
   }
X. Iterative Version
https://discuss.leetcode.com/topic/57165/java-o-n-2-iterator-and-backtracking-solution
The first one is a simple solution of O(n^2), we find the max length starting at each character in s. The three if statement in for loop is to check if the string is satisfied, I use math methods instead of iterator the map each time to save time.
0_1473033447412_Screen Shot 2016-09-04 at 4.21.35 PM.png

http://edlinlink.github.io/Leetcode_Longest_Substring_With_At_Least_K_Repeating_Characters.html
2. MLE Code

    int longestSubstring(string s, int k )
    {
        int ans = 0;
        vector<int> count(26,0);                    
        for( int i=0; i<s.size(); i++ )
            count[s[i]-'a']++;

        for( int i=0; i<s.size(); i++ )
            if( count[s[i]-'a']<k )
                return max( longestSubstring(s.substr(0,i),k), longestSubstring(s.substr(i+1),k) );
        return s.size();
    }
Using recursion will lead to Memory Limit Exceeded. We do not need to calculate longestSubstring if the length of the string is less than current answer;
3. TLE Code

    int longestSubstring(string s, int k )
    {
        int ans = 0;
        vector<int> count(26,0);                    
        for( int i=0; i<s.size(); i++ )
            count[s[i]-'a']++;

        for( int i=0; i<s.size(); i++ ){
            if( count[s[i]-'a']<k ){
                int left = 0, right = 0;
                left = longestSubstring(s.substr(0,i),k);
                if( s.substr(i+1).size() > left)
                    right = longestSubstring(s.substr(i+1),k);
                return max( left, right );
            }
        }
        return s.size();
    }
Opps, recursion solution still Time Limit Exceeded. We now just split the substring into two parts, but actually after one statistic we can split the string into several parts.
4. Code

class Solution {
public:
    int longestSubstring(string s, int k )
    {
        int ans = 0;
        vector<int> count(26,0);                    
        for( int i=0; i<s.size(); i++ )
            count[s[i]-'a']++;

        int len = -1;
        for( int i=0; i<s.size(); i++ ){
            int index = 0;
            if( count[s[i]-'a']<k ){
                len = max( len, longestSubstring( s.substr(index, i-index), k ) );
                index = i+1;
            }
        }
        return ( -1 == len ? s.size() : len );
    }
https://all4win78.wordpress.com/2016/09/08/leetcode-395-longest-substring-with-at-least-k-repeating-characters/
算法基本原理是，先遍历整个string，并记录每个不同的character的出现次数。如果所有character出现次数都不小于k，那么说明整个string就是满足条件的longest substring，返回原string的长度即可；如果有character的出现次数小于k，假设这个character是c，因为满足条件的substring永远不会包含c，所以满足条件的substring一定是在以c为分割参考下的某个substring中。所以我们需要做的就是把c当做是split的参考，在得到的String[]中再次调用我们的method，找到最大的返回值即可。
当然，这样每次都需要统计character的次数，不是特别高效，优化的话可以保存一个全局的数组来保存统计的次数，每一行是每个不同的character，而每一列是每个character在原始string中到某个位置时出现了多少次，所以大小应该是(# of different characters) x (length of original string)。当然这里具体细节还是有点复杂，需要的空间可能也会比较大（最差情况n*n)
X.
https://discuss.leetcode.com/topic/57158/java-hashmap-solution-worst-case-o-n-2-26
These commented blocks are for speeding up the code, in case of Time Limit Exceed.Basically,denote index as the position of each character in String s,I use a HashMap<index,int[]> to store the occurence of all characters in input s[0,index],index inclusively,which, ranges from 0 to s.length()-1;every substring then can be expressed as map.get(i)-map.get(j), 0<=j<i, we can just check whether if it's valid,update the maxLen and also the hashmap.
I was totally inspired by a guy and he gave an idea of solving subarray/substring problem, and it's stunning!
https://discuss.leetcode.com/topic/33537/java-o-n-explain-how-i-come-up-with-this-idea
    public int longestSubstring(String s, int k) {
        HashMap<Integer,int[]> map=new HashMap<>();
        int maxLen=0;
        map.put(-1,new int[26]);
        for(int i=0;i<s.length();i++){
            int[] curr=Arrays.copyOf(map.get(i-1),26);
            curr[s.charAt(i)-'a']++;
            // if(i+1<k){
            //     map.put(i,curr);
            //     continue;
            // }
            for(int j=-1;j<i;j++){
                // if(i-j<k){
                //     continue;
                // }
                int[] tmp=map.get(j);
                boolean flag=true;
                for(int m=0;m<26;m++){
                    if(curr[m]!=tmp[m]&&curr[m]-tmp[m]<k){
                        flag=false;
                        break;
                    }
                }
                
                if(flag){
      maxLen=Math.max(maxLen,i-j);
                    break;
                }
            }
            map.put(i,curr);
       }
       return maxLen;
    }
TODO
http://www.2cto.com/px/201609/545934.html
 * @author het
 *
 */
public class LeetCode395 {
	public int longestSubstring(String s, int k) {
	    HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
	 
	    for(int i=0; i<s.length(); i++){
	 
	        char c = s.charAt(i);
	        if(counter.containsKey(c)){
	            counter.put(c, counter.get(c)+1);
	        }else{
	            counter.put(c, 1);
	        }
	 
	    }
	 
	    HashSet<Character> splitSet = new HashSet<Character>();
	    for(char c: counter.keySet()){
	        if(counter.get(c)<k){
	            splitSet.add(c);
	        }
	    }
	 
	    if(splitSet.isEmpty()){
	        return s.length();
	    }
	 
	    int max = 0;
	    int i=0, j=0;
	    while(j<s.length()){
	        char c = s.charAt(j);
	        if(splitSet.contains(c)){
	            if(j!=i){
	                max = Math.max(max, longestSubstring(s.substring(i, j), k));
	            }
	            i=j+1;
	        }
	        j++;
	    }
	 
	    if(i!=j)
	         max = Math.max(max, longestSubstring(s.substring(i, j), k));
	 
	    return max;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
