package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import finish.TrieST;

/**
 * LeetCode 336 - Palindrome Pairs

http://bookshadow.com/weblog/2016/03/10/leetcode-palindrome-pairs/
Given a list of unique words. Find all pairs of indices (i, j) in the given list, so that the concatenation of the two words,
 i.e. words[i] + words[j] is a palindrome.
Example 1:
Given words = ["bat", "tab", "cat"]
Return [[0, 1], [1, 0]]
The palindromes are ["battab", "tabbat"]
Example 2:
Given words = ["abcd", "dcba", "lls", "s", "sssll"]
Return [[0, 1], [1, 0], [3, 2], [2, 4]]
The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
O(k * n ^2)解法 其中k为单词个数，n为单词的长度：
利用字典wmap保存单词 -> 下标的键值对

遍历单词列表words，记当前单词为word，下标为idx：

1). 若当前单词word本身为回文，且words中存在空串，则将空串下标bidx与idx加入答案

2). 若当前单词的逆序串在words中，则将逆序串下标ridx与idx加入答案

3). 将当前单词word拆分为左右两半left，right。

     3.1) 若left为回文，并且right的逆序串在words中，则将right的逆序串下标rridx与idx加入答案
     3.2) 若right为回文，并且left的逆序串在words中，则将left的逆序串下标idx与rlidx加入答案
    def palindromePairs(self, words):
        """
        :type words: List[str]
        :rtype: List[List[int]]
        """
        wmap = {y : x for x, y in enumerate(words)}
        
        def isPalindrome(word):
            size = len(word)
            for x in range(size / 2):
                if word[x] != word[size - x - 1]:
                    return False
            return True

        ans = set()
        for idx, word in enumerate(words):
            if "" in wmap and word != "" and isPalindrome(word):
                bidx = wmap[""]
                ans.add((bidx, idx))
                ans.add((idx, bidx))

            rword = word[::-1]
            if rword in wmap:
                ridx = wmap[rword]
                if idx != ridx:
                    ans.add((idx, ridx))
                    ans.add((ridx, idx))

            for x in range(1, len(word)):
                left, right = word[:x], word[x:]
                rleft, rright = left[::-1], right[::-1]
                if isPalindrome(left) and rright in wmap:
                    ans.add((wmap[rright], idx))
                if isPalindrome(right) and rleft in wmap:
                    ans.add((idx, wmap[rleft]))
        return list(ans)
http://unknown66.blogspot.com/2016/04/leetcode-336-palindrome-pairs.html
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> ret = new ArrayList<>();
        
        if (words == null || words.length < 2) return ret;
        
        // word to index map (lookup table)
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) map.put(words[i], i);
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            
            int l = 0, r = 0;
            while (l <= r) {
                // Instead of blindly test every word in the list, we first check if there
                // is a candidate to make a possible palindrom pair
                String key = new StringBuilder(word.substring(l, r)).reverse().toString();
                
                Integer j = map.get(key); // return null if not exists
                
                if (j != null && j != i && isPalindrome(word.substring(l == 0 ? r : 0, l == 0 ? word.length() : l)))
                  ret.add(Arrays.asList(l == 0 ? new Integer[]{i, j} : new Integer[]{j, i}));
                
                if (r < word.length()) r++;
                else l++;
            }
            
        }
        
        return ret;
    }
https://leetcode.com/discuss/93603/150-ms-45-lines-java-solution
Is the time complexity O(m * n ^ 2) where m is the length of the list and the n is the length of the word.
The <= in for (int j=0; j<=words[i].length(); j++) is aimed to handle empty string in the input. Consider the test case of ["a", ""];
Since we now use <= in for (int j=0; j<=words[i].length(); j++) instead of <. There may be duplicates in the output (consider test case ["abcd", "dcba"]). Therefore I put a str2.length()!=0 to avoid duplicates.
Another way to avoid duplicates is to use Set<List<Integer>> ret = new HashSet<>(); and return new ArrayList<>(ret);
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ret = new ArrayList<>(); 
    if (words == null || words.length < 2) return ret;
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i=0; i<words.length; i++) map.put(words[i], i);
    for (int i=0; i<words.length; i++) {
        for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
            String str1 = words[i].substring(0, j);
            String str2 = words[i].substring(j);
            if (isPalindrome(str1)) {
                String str2rvs = new StringBuilder(str2).reverse().toString();
                if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(map.get(str2rvs));
                    list.add(i);
                    ret.add(list);
                    // System.out.printf("isPal(str1): %s\n", list.toString());
                }
            }
            if (isPalindrome(str2)) {
                String str1rvs = new StringBuilder(str1).reverse().toString();
                // check "str.length() != 0" to avoid duplicates
                if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length()!=0) { 
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    list.add(map.get(str1rvs));
                    ret.add(list);
                    // System.out.printf("isPal(str2): %s\n", list.toString());
                }
            }
        }
    }
    return ret;
}
Very clean solution overall. But I found your two if statements repeating themselves. Here is some modification suggestions. We can use another function, I call it addPair here and pass in necessary info. if(str2.length() != 0) is used to avoid duplicates. And we reverse the order of str1 and str2 so that both of the cases will be considered.
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>(); 
    if (words == null || words.length < 2) {
        return res;
    }
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (int i = 0; i < words.length; i++) {
        map.put(words[i], i);
    }
    for (int i = 0; i < words.length; i++) {
        for (int j = 0; j <= words[i].length(); j++) {
            String str1 = words[i].substring(0, j);
            String str2 = words[i].substring(j);
            addPair(map, res, str1, str2, i, false);
            if(str2.length() != 0) {
                addPair(map, res, str2, str1, i, true);
            }
        }
    }
    return res;
}
private void addPair(Map<String, Integer> map, List<List<Integer>> res, String str1, String str2, int index, boolean reverse) {
    if (isPalindrome(str1)) {
        String str2rev = new StringBuilder(str2).reverse().toString();
        if (map.containsKey(str2rev) && map.get(str2rev) != index) {
            List<Integer> list = new ArrayList<>();
            if(!reverse) {
                list.add(map.get(str2rev));
                list.add(index);
            } else {
                list.add(index);
                list.add(map.get(str2rev));
            }
            res.add(list);
        }
    }
}
https://leetcode.com/discuss/91254/java-naive-165-ms-nk-and-126-ms-nk-manacher-suffixes-prefixes
If two concatenated words form a palindrome, then there are three cases to consider:
+---s1---+---s2--+     +---s1---+-s2-+    +-s1-+---s2---+
|abcdefgh|hgfedcba|    |abcdxyyx|dcba|    |abcd|xyyxdcba|
Case 1 is when one string is a mirror image of another. Case 2 is when the first string is longer than the other and consists of the mirror image of the other (prefix) and a palindrome (suffix). Case 3 is a mirror image of case 2. Case 1 can also be considered a special subcase of either case 2 or case 3 with an empty palindrome suffix/prefix.
Of these three, case 1 is definitely the easiest because we just need to look up a word in a reverse string-to-index map (words are unique, so no multimaps needed). If we iterate over the list with s1 as the current string, then case 2 is also much easier than case 3 because when we locate a prefix/palindrome split inside s1 we just need to look up for the reversed prefix in the map.
Case 3 is trickier, but we can get rid of case 3 altogether if we just make another run with the reversed words! This way case 3 turns into case 2. We only need to consider case 1 in one of these runs in order to avoid duplicate combinations. With that in mind, I present the following 165 ms solution:
public List<List<Integer>> palindromePairs(String[] words) {
    Map<String, Integer> index = new HashMap<>();
    Map<String, Integer> revIndex = new HashMap<>();
    String[] revWords = new String[words.length];
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        String r = new StringBuilder(s).reverse().toString();
        index.put(s, i);
        revIndex.put(r, i);
        revWords[i] = r;
    }
    List<List<Integer>> result = new ArrayList<>();
    result.addAll(findPairs(words, revWords, revIndex, false));
    result.addAll(findPairs(revWords, words, index, true));
    return result;
}

private static List<List<Integer>> findPairs(String[] words, String[] revWords, Map<String, Integer> revIndex, boolean reverse) {
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < words.length; ++i) {
        String s = words[i];
        for (int k = reverse ? 1 : 0; k <= s.length(); ++k) { // check suffixes, <= because we allow empty words
            Integer j = revIndex.get(s.substring(k));
            if (j != null && j != i) { // reversed suffix is present in the words list
                String prefix = s.substring(0, k); // check whether the prefix is a palindrome
                String revPrefix = revWords[i].substring(s.length() - k);
                if (prefix.equals(revPrefix)) {
                    result.add(reverse ? Arrays.asList(i, j) : Arrays.asList(j, i));
                }
            }
        }
    }
    return result;
}

I used Manacher's algorithm to quickly determine whether some part of a string is a palindrome or not. That gets rid of one O(nk^2) part.
Another part is numerous calls to substring, so my idea is to avoid copying a substring unless there's a good chance that it's actually present in the list. I do this by creating a kind of ad-hoc hash tables for both reversed and non-reversed words.
We start by iterating over the list of the words and compute hashes for both reversed and non-reversed words. However, because I later calculate hashes of suffixes on the fly, which means that I calculate them right-to-left, so it is kind of mixed up which hash is reversed and which is not.
Then we just compute every suffix's hash and look up the matching words. Updating the hash as we go, we avoid O(nk^2) complexity. To consider all cases, we do it twice, for reversed and non-reversed words.

http://www.cnblogs.com/grandyang/p/5272039.html
要用到哈希表来建立每个单词和其位置的映射，然后需要一个set来保存出现过的单词的长度，算法的思想是，遍历单词集，对于遍历到的单词，我们对其翻转一下，然后在哈希表查找翻转后的字符串是否存在，注意不能和原字符串的坐标位置相同，因为有可能一个单词翻转后和原单词相等，现在我们只是处理了bat和tab的情况，还存在abcd和cba，dcb和abcd这些情况需要考虑，这就是我们为啥需要用set，由于set是自动排序的，我们可以找到当前单词长度在set中的iterator，然后从开头开始遍历set，遍历比当前单词小的长度，比如abcdd翻转后为ddcba，我们发现set中有长度为3的单词，然后我们dd是否为回文串，若是，再看cba是否存在于哈希表，若存在，则说明abcdd和cba是回文对，存入结果中，对于dcb和aabcd这类的情况也是同样处理，我们要在set里找的字符串要在遍历到的字符串的左边和右边分别尝试，看是否是回文对，这样遍历完单词集，就能得到所有的回文对
    vector<vector<int>> palindromePairs(vector<string>& words) {
        vector<vector<int>> res;
        unordered_map<string, int> m;
        set<int> s;
        for (int i = 0; i < words.size(); ++i) {
            m[words[i]] = i;
            s.insert(words[i].size());
        }
        for (int i = 0; i < words.size(); ++i) {
            string t = words[i];
            int len = t.size();
            reverse(t.begin(), t.end());
            if (m.count(t) && m[t] != i) {
                res.push_back({i, m[t]});
            }
            auto a = s.find(len);
            for (auto it = s.begin(); it != a; ++it) {
                int d = *it;
                if (isValid(t, 0, len - d - 1) && m.count(t.substr(len - d))) {
                    res.push_back({i, m[t.substr(len - d)]});
                }
                if (isValid(t, d, len - 1) && m.count(t.substr(0, d))) {
                    res.push_back({m[t.substr(0, d)], i});
                }
            }
        }
        return res;
    }
    bool isValid(string t, int left, int right) {
        while (left < right) {
            if (t[left++] != t[right--]) return false;
        }
        return true;
    }
下面这种方法没有用到set，但实际上循环的次数要比上面多，因为这种方法对于遍历到的字符串，要验证其所有可能的子串，看其是否在哈希表里存在，并且能否组成回文对
https://leetcode.com/discuss/91531/accepted-short-java-solution-using-hashmap
Good idea. I hadn't thought about using two pointers to simplify the suffix/prefix stuff. Although I'm not sure if it counts as a simplification—it definitely makes code shorter, but it also makes it a bit harder to read.
One minor note, though: you shouldn't ever use LinkedList except when you need fast insertions/deletions, which is not the case here. An ArrayList would do just fine, use less memory and provide better performance too.


public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> pairs = new LinkedList<>();
    if (words == null) return pairs;
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < words.length; ++ i) map.put(words[i], i);
    for (int i = 0; i < words.length; ++ i) {
        int l = 0, r = 0;
        while (l <= r) {
            String s = words[i].substring(l, r);
            Integer j = map.get(new StringBuilder(s).reverse().toString());
            if (j != null && i != j && isPalindrome(words[i].substring(l == 0 ? r : 0, l == 0 ? words[i].length() : l)))
                pairs.add(Arrays.asList(l == 0 ? new Integer[]{i, j} : new Integer[]{j, i}));
            if (r < words[i].length()) ++r;
            else ++l;
        }
    }
    return pairs;
}

private boolean isPalindrome(String s) {
    for (int i = 0; i < s.length()/2; ++ i)
        if (s.charAt(i) != s.charAt(s.length()-1-i))
            return false;
    return true;
}
X. Trie
https://discuss.leetcode.com/topic/39585/o-n-k-2-java-solution-with-trie-structure-n-total-number-of-words-k-average-length-of-each-word
Apparently there is a O(n^2*k) naive solution for this problem, with n the total number of words in the "words" array and k the average length of each word:
For each word, we simply go through the words array and check whether the concatenated string is a palindrome or not.
Of course this will result in TLE as expected. To improve the algorithm, we need to reduce the number of words that is needed to check for each word, instead of iterating through the whole array. This prompted me to think if I can extract any useful information out of the process of checking whether the concatenated string is a palindrome, so that it can help eliminate as many words as possible for the rest of the words array.
To begin, here is the technique I employed to check for palindromes: maintain two pointers i and j, with i pointing to the start of the string and j to the end of the string. Characters pointed by i and j are compared. If at any time the characters pointed by them are not the same, we conclude the string is not a palindrome. Otherwise we move the two pointers towards each other until they meet in the middle and the string is a palindrome.
By examining the process above, I do find something that we may take advantage of to get rid of words that need to be checked otherwise. For example, let's say we want to append words to w0, which starts with character 'a'. Then we only need to consider words ending with character 'a', i.e., this will single out all words ending with character 'a'. If the second character of w0 is 'b'for instance, we can further reduce our candidate set to words ending with string "ba", etc. Our naive solution throws away all this "useful" information and repeats the comparison, which leads to the undesired O(n^2*k) time complexity.
In order to exploit the information gathered so far, we obviously need to restructure all the words in the words array. If you are familiar with Trie structure (I believe you are, since LeetCode has problems for it. In case you are not, see Trie), it will come to mind as we need to deal with words with common suffixes. The next step is to design the structure for each Trie node. There are at least two fields that should be covered for each TrieNode: a TrieNode array denoting the next layer of nodes and a boolean (or integer) to signify the end of a word. So our tentative TrieNode will look like this:
class TrieNode {
    TrieNode[] next;
    boolean isWord;
}
One point here is that we assume all the words contain lowercase letters only. This is not specified in the problem statement so you probably need to confirm with the interviewer (here I assume it is the case)
Now we will rearrange each word into this Trie structure: for each word, simply starting from its last character and identify the node at the next layer by indexing into root's next array with index given by the difference between the ending character and character 'a'. If the indexed node is null, create a new node. Continue to the next layer and towards the beginning of the word in this manner until we are done with the word, at which point we will label the isWord field of the final node as true.
After building up the Trie structure, we can proceed to search for pairs of palindromes for each word in the words array. I will use the following example to explain how it works and make possible modifications of the TrieNode we proposed above.
Let's say we have these words: ["ba", "a", "aaa"], the Trie structure will be as follows:
        root (f)
           | 'a'
          n1 (t)
     ------------
 'b' |          | 'a'
    n2 (t)    n3 (f)
                | 'a'
              n4 (t)
The letter in parentheses indicates the value of isWord for each node: f ==> false and t ==> true. The letter beside each vertical line denotes the index into the next array of the corresponding node. For example, for the first vertical line, 'a' means root.next[0] is not null. Similarly 'b' means n1.next[1] is not null, and so on.
Here is the searching process:
For word "ba", starting from the first character 'b', index into the root.next array with index given by 'b' - 'a' = 1. The corresponding node is null, then we know there are no words ending at this character, so the searching process is terminated;
For word "a", again indexing into array root.next at index given by 'a' - 'a' = 0 will yield node n1, which is not null. We then check the value of n1.isWord. If it is true, then it is possible to obtain a palindrome by appending this word to the one currently being examined (a.k.a word "a"). Also note that the two words should be distinct, but the n1.isWord field provides no information about the word itself, which makes it impossible to distinguish the two words. So it is necessary to modify the structure of the TrieNode so that we can identify the word it represents. One easy way is to have an integer field to remember the index of the word in the words array. For non-word nodes, this integer will take negative values (-1 for example) while for those representing a word, it will be non-negative values. Suppose we have made this modification, then the two words will be identified to be the same, so we discard this pair combination. Since the word "a" has only one letter, it seems we are done with it. Or do we? Not really. What if there are words with suffix "a" ("aaa" in this case)? We need to continue to check the rest part of these words (such as "aa" for the word "aaa") and see if the rest forms a palindrome. If it is, then appending this word ("aaa" in this case) to the original word ("a") will also form a palindrome ("aaaa"). Here I take another strategy: add an integer list to each TrieNode; the list will record the indices of all words satisfying the following two conditions: each word has a suffix represented by the current Trie node; the rest of the word forms a palindrome.
Before I get to the third word "aaa", let me spell out the new TrieNode and the corresponding Trie structure for the above array.
TrieNode:
class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;
            
    TrieNode() {
        next = new TrieNode[26];
        index = -1;
        list = new ArrayList<>();
    }
}
Trie structure:
          root (-1,[1,2])
            | 'a'
          n1 (1,[0,1,2])
    ---------------------
'b' |                 | 'a'
  n2 (0,[0])    n3 (-1,[2])
                      | 'a'
                 n4 (2,[2])
The first integer in the parentheses is the index of the word in the words" array (defaulted to -1). The integers in the square bracket are the indices of words satisfying the two conditions mentioned above.
Let's continue with the third word "aaa" with this new structure. Indexing into array root.next at index given by 'a' - 'a' = 0will yield node n1 and n1.index = 1 >= 0, which means we have a valid word now. The index of this word (which is 1) is also different from the index of the word currently being visited, a.k.a "aaa" (which is 2). So pair (2,1) is a possible concatenation to form a palindrome. But still we need to check the rest of "aaa" (excluding the substring represented by current node n1 which is "a" from the beginning of "aaa") to see if it is a palindrome. If so, (2,1) will be a valid combination. We continue in this fashion until we reach the end of "aaa". Lastly we will check n4.list to see if there are any words satisfying the two conditions specified in step 2 which are different from current word, and add the corresponding valid pairs.
Both building and searching the Trie structure take O(n*k^2), which set the total time complexity of the solution. See the complete Java program in the answer.
We have the TrieNode structure at the top. In the "palindromePairs" function, we build up the Trie by adding each word, then searching for valid pairs for each word and record the results in the "res" list. The last "isPalindrome" function checks if the substring [i, j] (both inclusive) of the given word is a palindrome.
class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;
     
    TrieNode() {
     next = new TrieNode[26];
     index = -1;
     list = new ArrayList<>();
    }
}
    
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();

    TrieNode root = new TrieNode();
    for (int i = 0; i < words.length; i++) addWord(root, words[i], i);
    for (int i = 0; i < words.length; i++) search(words, i, root, res);
    
    return res;
}
    
private void addWord(TrieNode root, String word, int index) {
    for (int i = word.length() - 1; i >= 0; i--) {
        int j = word.charAt(i) - 'a';
     if (root.next[j] == null) root.next[j] = new TrieNode();
     if (isPalindrome(word, 0, i)) root.list.add(index);
     root = root.next[j];
    }
     
    root.list.add(index);
    root.index = index;
}
    
private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
    for (int j = 0; j < words[i].length(); j++) { 
     if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
         res.add(Arrays.asList(i, root.index));
     }
      
     root = root.next[words[i].charAt(j) - 'a'];
       if (root == null) return;
    }
     
    for (int j : root.list) {
     if (i == j) continue;
     res.add(Arrays.asList(i, j));
    }
}
    
private boolean isPalindrome(String word, int i, int j) {
    while (i < j) {
     if (word.charAt(i++) != word.charAt(j--)) return false;
    }
     
    return true;
}
https://leetcode.com/discuss/91429/solution-with-structure-total-number-words-average-length
O(n*k^2) java solution with Trie structure (n: total number of words; k: average length of each word)
class TrieNode {
    TrieNode[] next;
    int index;
    List<Integer> list;

    TrieNode() {
        next = new TrieNode[26];
        index = -1;
        list = new ArrayList<>();
    }
}

public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();

    TrieNode root = new TrieNode();

    for (int i = 0; i < words.length; i++) {
        addWord(root, words[i], i);
    }

    for (int i = 0; i < words.length; i++) {
        search(words, i, root, res);
    }

    return res;
}

private void addWord(TrieNode root, String word, int index) {
    for (int i = word.length() - 1; i >= 0; i--) {
        if (root.next[word.charAt(i) - 'a'] == null) {
            root.next[word.charAt(i) - 'a'] = new TrieNode();
        }

        if (isPalindrome(word, 0, i)) {
            root.list.add(index);
        }

        root = root.next[word.charAt(i) - 'a'];
    }

    root.list.add(index);
    root.index = index;
}

private void search(String[] words, int i, TrieNode root, List<List<Integer>> list) {
    for (int j = 0; j < words[i].length(); j++) {   
        if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
            list.add(Arrays.asList(i, root.index));
        }

        root = root.next[words[i].charAt(j) - 'a'];
        if (root == null) return;
    }

    for (int j : root.list) {
        if (i == j) continue;
        list.add(Arrays.asList(i, j));
    }
}
http://www.allenlipeng47.com/blog/index.php/2016/03/15/palindrome-pairs/
https://leetcode.com/discuss/91429/o-n-k-2-java-solution-with-trie-structure
We uses trie. For each string, we save it trie in reverse order. For example, if it is abc. The trie will be like below. a has value pos. This tells that trie cba is a word.
palinpair1
When we use cbaxyx to match the trie, it will finally goes to trie a. And when it sees a has pos, we check if xyx is a palindrome. If it is, then we know cbaxyx and abc is a pair of palindrome.
Think about words[] = {“cbaaa”, “bc”, “abc”}. The trie will be like:
palinpair2
When it tries to match cbaaa, when it arrives cb, its pos is 1, then we should check if aaa is a palindrome. When it arrives cba, we should check if rest of string aa is a palindrome. In this way, we can know that [“cbaaa”, “bc”], [“cbaaa”, “abc”] are pairs.
How about [“abc”, “xyxcba”, “xcba”]?
palinpair3
When we check abc and it arrives c in trie. We should know that the rest of string after abc has palindrome. So we modify trie like this:
palinpair4
c has a list palins. It tells string below c, what rest of word is palindrome. When we scan abc, it will finally stops at c in xyxabc. When it ends, check if palins list of trie c is not empty. If not, it may be able to concatenate into palindrome.
We can do it in another way by rolling hash which I wrote a post before. But the code is too much overhead.
public static class Trie {
    int pos;
    Trie[] nodes;   // consider xyxabc. if current trie is 'a'. Then a.nodes has information. It means string after a is palindrome
    List<Integer> palins;
    public Trie() {
        pos = -1;
        nodes = new Trie[26];
        palins = new ArrayList<>();
    }
}

public static void add(Trie root, String word, int pos) {
    for (int i = word.length() - 1; i >= 0; i--) {
        char ch = word.charAt(i);
        if (isPalindrome(word, 0, i)) { // check if substring(0, i) is palindrome.
            root.palins.add(pos);
        }
        if (root.nodes[ch - 'a'] == null) {
            root.nodes[ch - 'a'] = new Trie();
        }
        root = root.nodes[ch - 'a'];
    }
    root.pos = pos; // if it is xyxcba. Until now, the node should be at x.
    root.palins.add(pos);
}

public static void search(Trie root, String[] words, int i, List<List<Integer>> ans) {
    int len = words[i].length();
    for (int j = 0; j < len && root != null; j++) {
        if (root.pos >= 0 && i != root.pos && isPalindrome(words[i], j, len - 1)) {
            ans.add(Arrays.asList(new Integer[] {i, root.pos}));
        }
        char ch = words[i].charAt(j);
        root = root.nodes[ch - 'a'];
    }
    if (root != null && root.palins.size() > 0) { // assume 'xyxabc' is in trie, now try 'cba'
        for (int j : root.palins) {
            if (j != i) {
                ans.add(Arrays.asList(new Integer[] {i, j}));
            }
        }
    }
}

public static List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> ans = new ArrayList<>();
    Trie trie = new Trie();
    for (int i = 0; i < words.length; i++) {
        add(trie, words[i], i);
    }
    for (int i = 0; i < words.length; i++) {
        search(trie, words, i, ans);
    }
    return ans;
}

public static boolean isPalindrome(String str, int i, int j) {
    while (i < j) {
        if (str.charAt(i++) != str.charAt(j--)) {
            return false;
        }
    }
    return true;
}
https://leetcode.com/discuss/91433/easy-to-understand-java-code-using-hashmap
I believe this could be generalized a little. So you don't really need the special case of empty string, and also you don't need the postfix case, if you store the reversed string. 
public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        HashMap<String, Integer> hm = new HashMap<>();

        // put all the words in HashMap for easy look-up
        for (int i = 0; i < words.length; i++) {
            hm.put(words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            String reverse = new StringBuilder(words[i]).reverse().toString();

            // check if there is reverse word present
            if (hm.containsKey(reverse) && hm.get(reverse) != i) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(hm.get(reverse));
                result.add(list);
            }

            // check if there is empty string
            // in that case if word is palindrome, empty string can be added
            // before and after the word.
            if (hm.containsKey("")
                    && hm.get("") != i
                    && new StringBuilder(words[i]).reverse().toString()
                            .equals(words[i])) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(hm.get(""));
                result.add(list);
                list = new ArrayList<>();
                list.add(hm.get(""));
                list.add(i);
                result.add(list);
            }

            // check if some other word can be added as prefix
            int curReverse = 0;
            while (curReverse < reverse.length() - 1) {
                if (hm.containsKey(reverse.substring(0, curReverse + 1))) {
                    String rem = words[i].substring(0, words[i].length()
                            - curReverse - 1);
                    if (new StringBuilder(rem).reverse().toString().equals(rem)) {
                        List<Integer> list = new ArrayList<>();
                        list.add(hm.get(reverse.substring(0, curReverse + 1)));
                        list.add(i);
                        result.add(list);
                    }
                }
                curReverse++;
            }

            // check if some other word can be added as suffix
            int curForward = 0;
            while (curForward < words[i].length() - 1) {
                String rev = new StringBuilder(words[i].substring(0,
                        curForward + 1)).reverse().toString();
                if (hm.containsKey(rev)) {
                    String rem = words[i].substring(curForward + 1);
                    if (new StringBuilder(rem).reverse().toString().equals(rem)) {
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        list.add(hm.get(rev));
                        result.add(list);
                    }
                }
                curForward++;
            }
        }
        return result;
    }
http://m.blog.csdn.net/article/details?id=51056177
    private String strrev(String s)
    {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    private boolean palindrome(String s)
    {
        int len = s.length();
        for (int i = 0; i < len / 2; i++)
        {
            if (s.charAt(i) != s.charAt(len - 1 - i))
            {
                return false;
            }
        }

        return true;
    }

    public List<List<Integer>> palindromePairs(String[] words)
    {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();

        if (null == words || 0 == words.length)
        {
            return ret;
        }

        Map<String, Integer> hs = new HashMap<String, Integer>();
        for (int i = 0; i < words.length; i++)
        {
            hs.put(words[i], i);
        }

        if (hs.containsKey(""))
        {
            int index = hs.get("");
            for (int i = 0; i < words.length; i++)
            {
                if (palindrome(words[i]))
                {
                    if (i == index) continue;
                    ret.add(Arrays.asList(i, index));
                    ret.add(Arrays.asList(index, i));
                }
            }
        }

        for (int i = 0; i < words.length; i++)
        {
            String rev = strrev(words[i]);
            if (hs.containsKey(rev))
            {
                int index = hs.get(rev);
                if (index == i) continue;
                ret.add(Arrays.asList(i, index));
            }
        }

        for (int i = 0; i < words.length; i++)
        {
            for (int len = 1; len < words[i].length(); len++)
            {
                if (palindrome(words[i].substring(0, len)))
                {
                    String rev = strrev(words[i].substring(len));
                    if (hs.containsKey(rev))
                    {
                        int index = hs.get(rev);
                        if (i == index) continue;
                        ret.add(Arrays.asList(index, i));
                    }
                }

                if (palindrome(words[i].substring(len)))
                {
                    String rev = strrev(words[i].substring(0, len));
                    if (hs.containsKey(rev))
                    {
                        int index = hs.get(rev);
                        if (i == index) continue;
                        ret.add(Arrays.asList(i, index));
                    }
                }
            }
        }
        return ret;
    }
BRUTE FORCE:时间复杂度=O(n^2 * k)
http://nahan.github.io/2016/03/11/leetcode-336-palindrome-pairs/
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i != j && isPalindrome(words[i], words[j])) {
                    List<Integer> item = new ArrayList<Integer>();
                    item.add(i);
                    item.add(j);
                    result.add(item);
                }
            }
        }
        return result;
    }
    public boolean isPalindrome(String word1, String word2) {
        int i = 0;
        int j = word2.length() - 1;
        while (i < word1.length() && j >= 0) {
            if (word1.charAt(i) != word2.charAt(j)) {
                return false;
            }
            i += 1;
            j -= 1;
        }
        if (word1.length() < word2.length()) {
            i = 0;
            while (i < j) {
                if (word2.charAt(i) != word2.charAt(j)) {
                    return false;
                }
                i += 1;
                j -= 1;
            }
        } else if (word1.length() > word2.length()) {
            j = word1.length() - 1;
            while (i < j) {
                if (word1.charAt(i) != word1.charAt(j)) {
                    return false;
                }
                i += 1;
                j -= 1;
            }
        }
        return true;
    }
}

http://www.allenlipeng47.com/blog/index.php/2015/06/06/find-two-strings-which-can-group-into-palindrome/
Problem: Given n strings, check if exist 2 of strings which can group a palindrome.
For example, for strings ‘aaa’, ‘aac’, ‘ab’, ‘acblb’, ‘ac’, ‘ddcaa’.
‘aac’ and ‘ddcaa’ can group into palindrome; ‘acblb’ and ‘ac’ can group into palindrome.
We can solve this problem using trie and rolling hash queue. For example, below is when first 4 words in trie:

Next, check word ‘ca’ in reverse sequence. Search it in trie. And it ends in string first. Then, we check any descendant of last node contains a palindrome. And we found b-l-b is a palindrome. So, we conclude word ‘acblb’ and ‘ca’ can group to a palindrome string.

Next, check word ‘ddcaa’ in reverse sequence. Search it in trie. And it ends in trie first. Then, we check if rest string in ‘ddcaa’ is palindrome, which is ‘dd’. And we found ‘dd’ is palindrome. So, we conclude word ‘aac’ and ‘ddcaa’ can group to a palindrome string.

I’m a bit lazy to write the trie code. It would a kinda long code. So, I summarized the basic idea of the solution. But this requires how to find a palindrome in tree, which is talked in my last post http://allenlipeng47.com/PersonalPage/index/view/165/nkey
https://segmentfault.com/a/1190000005605192
    class TrieNode {
        TrieNode[] next;
        int index;
        List<Integer> list;
        TrieNode() {
            next = new TrieNode[26];
            index = -1;
            list = new ArrayList<>();
        }
    }
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) addWord(root, words[i], i);
        for (int i = 0; i < words.length; i++) search(words, i, root, res);
        return res;
    }
    private void addWord(TrieNode root, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            if (root.next[word.charAt(i) - 'a'] == null) root.next[word.charAt(i) - 'a'] = new TrieNode();
            if (isPalindrome(word, 0, i)) root.list.add(index);
            root = root.next[word.charAt(i) - 'a'];
        }
        root.list.add(index);
        root.index = index;
    }
    private void search(String[] words, int i, TrieNode root, List<List<Integer>> list) {
        for (int j = 0; j < words[i].length(); j++) {   
            if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) list.add(Arrays.asList(i, root.index));
            root = root.next[words[i].charAt(j) - 'a'];
            if (root == null) return;
        }
        for (int j : root.list) {
            if (i == j) continue;
            list.add(Arrays.asList(i, j));
        }
    }
    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) return false;
        }
        return true;
    }
https://leetcode.com/discuss/92451/trie-solution-in-time-n-k-2


https://shawnlincoding.wordpress.com/2015/04/09/pair-palindrome/

    public List<List<String>> pairPalindrome(List<String> words){

        List<List<String>> res = new ArrayList<List<String>>();

        if(words == null || words.size() == 0){

            return res;

        }

        HashSet<String> hashset = new HashSet<String>();

        for(String word : words){

            hashset.add(word);

        }

        for(String word : words){

            int N = word.length();

            for(int i = 0; i < N; i++){

                String prefix = word.substring(0, i);

                String suffix = word.substring(i, N);

                String reverseSuffix = reverse(suffix);

                if(isPalindrome(prefix) && hashset.contains(reverseSuffix)){

                     

                    List<String> sol = new ArrayList<String>();

                    sol.add(reverseSuffix);

                    sol.add(word);

                    res.add(sol);

                }

            }

        }

        return res;

    }

     

    private boolean isPalindrome(String word){

        int start = 0, end = word.length() - 1;

        while(start < end){

            if(word.charAt(start) != word.charAt(end)){

                return false;

            }

            start++;

            end--;

        }

        return true;

    }

     

    private String reverse(String word){

        char[] chars = word.toCharArray();

        int start = 0, end = chars.length - 1;

        while(start < end){

            char temp = chars[start];

            chars[start] = chars[end];

            chars[end] = temp;

            start++;

            end--;

        }

        return new String(chars);

    }
http://buttercola.blogspot.com/2015/11/airbnb-palindromic-pair-of-list-of.html
 * @author het
 *
 */
public class LeetCode336 {
	
	 public List<List<String>> pairPalindrome(List<String> words){

	        List<List<String>> res = new ArrayList<List<String>>();

	        if(words == null || words.size() == 0){

	            return res;

	        }

	        HashSet<String> hashset = new HashSet<String>();

	        for(String word : words){

	            hashset.add(word);

	        }

	        for(String word : words){

	            int N = word.length();

	            for(int i = 0; i < N; i++){

	                String prefix = word.substring(0, i);

	                String suffix = word.substring(i, N);

	                String reverseSuffix = reverse(suffix);

	                if(isPalindrome(prefix) && hashset.contains(reverseSuffix)){

	                     

	                    List<String> sol = new ArrayList<String>();

	                    sol.add(reverseSuffix);

	                    sol.add(word);

	                    res.add(sol);

	                }

	            }

	        }

	        return res;

	    }

	     

	    private boolean isPalindrome(String word){

	        int start = 0, end = word.length() - 1;

	        while(start < end){

	            if(word.charAt(start) != word.charAt(end)){

	                return false;

	            }

	            start++;

	            end--;

	        }

	        return true;

	    }

	     

	    private String reverse(String word){

	        char[] chars = word.toCharArray();

	        int start = 0, end = chars.length - 1;

	        while(start < end){

	            char temp = chars[start];

	            chars[start] = chars[end];

	            chars[end] = temp;

	            start++;

	            end--;

	        }

	        return new String(chars);

	    }
//	Example 1:
//		Given words = ["bat", "tab", "cat"]
//		Return [[0, 1], [1, 0]]
//		The palindromes are ["battab", "tabbat"]
//		Example 2:
//		Given words = ["abcd", "dcba", "lls", "s", "sssll"]
//		Return [[0, 1], [1, 0], [3, 2], [2, 4]]
//		The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
	
	public static List<int[]> getPalindromesPair(String[] input){
		 
		TrieST<Integer> trie = new TrieST<>();
		return trie.getPalindromePair(input);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<int[]> palindromesPair = getPalindromesPair(new String[]{"acbab", "ca", "acddcdd", "babcafgf", "babcaf", "wrw"});
		for(int[] pair: palindromesPair){
			System.out.println(pair[0]+","+pair[1]);
		}
		System.out.println(palindromesPair);
         
	}

}
