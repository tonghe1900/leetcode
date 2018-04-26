package alite.leetcode.xx4.select;

import java.util.ArrayList;
import java.util.List;

/**
 * 
http://bookshadow.com/weblog/2016/10/16/leetcode-word-squares/
Given a set of words (without duplicates), find all word squares you can build from them.
A sequence of words forms a valid word square if the kth row and column read the exact same string,
 where 0 ≤ k < max(numRows, numColumns).
For example, the word sequence ["ball","area","lead","lady"] forms a word square 
because each word reads the same both horizontally and vertically.
b a l l
a r e a
l e y d
l a d y



Note:
There are at least 1 and at most 1000 words.
All words will have the exact same length.
Word length is at least 1 and at most 5.
Each word contains only lowercase English alphabet a-z.
Example 1:
Input:
["area","lead","wall","lady","ball"]

Output:
[
  [ "wall",
    "area",
    "lead",
    "lady"
  ],
  [ "ball",
    "area",
    "lead",
    "lady"
  ]
]

Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
Example 2:
Input:
["abat","baba","atan","atal"]

Output:
[
  [ "baba",
    "abat",
    "baba",
    "atan"
  ],
  [ "baba",
    "abat",
    "baba",
    "atal"
  ]
]

Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
X. DFS
https://discuss.leetcode.com/topic/63516/explained-my-java-solution-using-trie-126ms-16-16
My first approach is brute-force, try every possible word sequences, and use the solution of Problem 422 (https://leetcode.com/problems/valid-word-square/) to check each sequence. This solution is straightforward, but too slow (TLE).
A better approach is to check the validity of the word square while we build it.
Example: ["area","lead","wall","lady","ball"]
wall
area
lead
lad
We know that the sequence contains 4 words because the length of each word is 4.
Every word can be the first word of the sequence, let's take "wall" for example.
Which word could be the second word? Must be a word start with "a" (therefore "area"), because it has to match the second letter of word "wall".
Which word could be the third word? Must be a word start with "le" (therefore "lead"), because it has to match the third letter of word "wall" and the third letter of word "area".
What about the last word? Must be a word start with "lad" (therefore "lady"). For the same reason above.
The picture below shows how the prefix are matched while building the sequence.
0_1476809138708_wordsquare.png
In order for this to work, we need to fast retrieve all the words with a given prefix. There could be 2 ways doing this:
Using a hashtable, key is prefix, value is a list of words with that prefix.
Trie, we store a list of words with the prefix on each trie node.
One pic to help understand the Trie structure.
The only difference between the trie here and the normal trie is that we hold one more list of all the words which have the prefix(from the root char to the current node char).
alt text

    class TrieNode {
        List<String> startWith;
        TrieNode[] children;

        TrieNode() {
            startWith = new ArrayList<>();
            children = new TrieNode[26];
        }
    }

    class Trie {
        TrieNode root;

        Trie(String[] words) {
            root = new TrieNode();
            for (String w : words) {
                TrieNode cur = root;
                for (char ch : w.toCharArray()) {
                    int idx = ch - 'a';
                    if (cur.children[idx] == null)
                        cur.children[idx] = new TrieNode();
                    cur.children[idx].startWith.add(w);
                    cur = cur.children[idx];
                }
            }
        }

        List<String> findByPrefix(String prefix) {
            List<String> ans = new ArrayList<>();
            TrieNode cur = root;
            for (char ch : prefix.toCharArray()) {
                int idx = ch - 'a';
                if (cur.children[idx] == null)
                    return ans;

                cur = cur.children[idx];
            }
            ans.addAll(cur.startWith);
            return ans;
        }
    }

    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> ans = new ArrayList<>();
        if (words == null || words.length == 0)
            return ans;
        int len = words[0].length();
        Trie trie = new Trie(words);
        List<String> ansBuilder = new ArrayList<>();
        for (String w : words) {
            ansBuilder.add(w);
            search(len, trie, ans, ansBuilder);
            ansBuilder.remove(ansBuilder.size() - 1);
        }

        return ans;
    }

    private void search(int len, Trie tr, List<List<String>> ans,
            List<String> ansBuilder) {
        if (ansBuilder.size() == len) {
            ans.add(new ArrayList<>(ansBuilder));
            return;
        }

        int idx = ansBuilder.size();
        StringBuilder prefixBuilder = new StringBuilder();
        for (String s : ansBuilder)
            prefixBuilder.append(s.charAt(idx));
        List<String> startWith = tr.findByPrefix(prefixBuilder.toString());
        for (String sw : startWith) {
            ansBuilder.add(sw);
            search(len, tr, ans, ansBuilder);
            ansBuilder.remove(ansBuilder.size() - 1);
        }
    }
https://discuss.leetcode.com/topic/63532/121ms-java-solution-using-trie-and-backtracking
    TrieNode root = new TrieNode();
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> ans = new ArrayList<>();
        if(words.length == 0) return ans;
        buildTrie(words);
        int length = words[0].length();
        findSquare(ans, length, new ArrayList<>());
        return ans;
    }
    
    private void findSquare(List<List<String>> ans, int length, List<String> temp) {
        if(temp.size() == length) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        int index = temp.size();
        StringBuilder sb = new StringBuilder();
        for(String s : temp) {
            sb.append(s.charAt(index));
        }
        String s = sb.toString();
        TrieNode node = root;
        for(int i = 0; i < s.length(); i++) {
            if(node.next[s.charAt(i) - 'a'] != null) {
                node = node.next[s.charAt(i) - 'a'];
            } else {
                node = null;
                break;
            }
        }
        if(node != null) {
            for(String next : node.words) {
                temp.add(next);
                findSquare(ans, length, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }
    
    private void buildTrie(String[] words) {
        for(String word : words) {
            TrieNode node = root;
            char[] array = word.toCharArray();
            for(char c : array) {
                node.words.add(word);
                if(node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.words.add(word);
        }
    }
    
    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        List<String> words = new ArrayList<>();
    }

https://discuss.leetcode.com/topic/63417/181-ms-java-solution-intuitive-backtracking
http://bookshadow.com/weblog/2016/10/16/leetcode-word-squares/
深度优先搜索（DFS）+ 剪枝（Pruning）
首先构建一个单词前缀prefix->单词word的字典mdict

深度优先搜索search(word, line)，其中word是当前单词，line是行数

利用变量matrix记录当前已经生成的单词

前缀prefix = matrix[0..line][line]，如果prefix对应单词不存在，则可以剪枝

否则枚举mdict[prefix]，并递归搜索

X. Using Trie
https://discuss.leetcode.com/topic/63516/my-java-solution-using-trie
 class TrieNode {
  List<String> startWith;
  TrieNode[] children;

  TrieNode() {
   startWith = new ArrayList<>();
   children = new TrieNode[26];
  }
 }

 class Trie {
  TrieNode root;

  Trie(String[] words) {
   root = new TrieNode();
   for (String w : words) {
    TrieNode cur = root;
    for (char ch : w.toCharArray()) {
     int idx = ch - 'a';
     if (cur.children[idx] == null)
      cur.children[idx] = new TrieNode();
     cur.children[idx].startWith.add(w);
     cur = cur.children[idx];
    }
   }
  }

  List<String> findPrefix(String prefix) {
   List<String> ans = new ArrayList<>();
   TrieNode cur = root;
   for (char ch : prefix.toCharArray()) {
    int idx = ch - 'a';
    if (cur.children[idx] == null)
     return ans;

    cur = cur.children[idx];
   }
   ans.addAll(cur.startWith);
   return ans;
  }
 }

 public List<List<String>> wordSquares(String[] words) {
  List<List<String>> ans = new ArrayList<>();
  if (words == null || words.length == 0)
   return ans;
  int n = words.length;
  int len = words[0].length();
  Trie trie = new Trie(words);
  List<String> ansBuilder = new ArrayList<>();
  for (String w : words) {
   ansBuilder.add(w);
   search(words, n, len, trie, ans, ansBuilder);
   ansBuilder.remove(ansBuilder.size() - 1);
  }

  return ans;
 }

 private void search(String[] ws, int n, int len, Trie tr,
   List<List<String>> ans, List<String> ansBuilder) {
  if (ansBuilder.size() == len) {
   ans.add(new ArrayList<>(ansBuilder));
   return;
  }

  int idx = ansBuilder.size();
  StringBuilder prefix = new StringBuilder();
  for (String s : ansBuilder)
   prefix.append(s.charAt(idx));
  List<String> startWith = tr.findPrefix(prefix.toString());
  for (String sw : startWith) {
   ansBuilder.add(sw);
   search(ws, n, len, tr, ans, ansBuilder);
   ansBuilder.remove(ansBuilder.size() - 1);
  }
 }
https://discuss.leetcode.com/topic/64770/java-dfs-trie-54-ms-98-so-far/2
 * @author het
 *
 */
public class LeetCode425WordSquares {
	 class TrieNode {
	        List<String> startWith;
	        TrieNode[] children;

	        TrieNode() {
	            startWith = new ArrayList<>();
	            children = new TrieNode[26];
	        }
	    }

	    class Trie {
	        TrieNode root;

	        Trie(String[] words) {
	            root = new TrieNode();
	            for (String w : words) {
	                TrieNode cur = root;
	                for (char ch : w.toCharArray()) {
	                    int idx = ch - 'a';
	                    if (cur.children[idx] == null)
	                        cur.children[idx] = new TrieNode();
	                    cur.children[idx].startWith.add(w);
	                    cur = cur.children[idx];
	                }
	            }
	        }

	        List<String> findByPrefix(String prefix) {
	            List<String> ans = new ArrayList<>();
	            TrieNode cur = root;
	            for (char ch : prefix.toCharArray()) {
	                int idx = ch - 'a';
	                if (cur.children[idx] == null)
	                    return ans;

	                cur = cur.children[idx];
	            }
	            ans.addAll(cur.startWith);
	            return ans;
	        }
	    }

	    public List<List<String>> wordSquares(String[] words) {
	        List<List<String>> ans = new ArrayList<>();
	        if (words == null || words.length == 0)
	            return ans;
	        int len = words[0].length();
	        Trie trie = new Trie(words);
	        List<String> ansBuilder = new ArrayList<>();
	        for (String w : words) {
	            ansBuilder.add(w);
	            search(len, trie, ans, ansBuilder);
	            ansBuilder.remove(ansBuilder.size() - 1);
	        }

	        return ans;
	    }

	    private void search(int len, Trie tr, List<List<String>> ans,
	            List<String> ansBuilder) {
	        if (ansBuilder.size() == len) {
	            ans.add(new ArrayList<>(ansBuilder));
	            return;
	        }

	        int idx = ansBuilder.size();
	        StringBuilder prefixBuilder = new StringBuilder();
	        for (String s : ansBuilder)
	            prefixBuilder.append(s.charAt(idx));
	        List<String> startWith = tr.findByPrefix(prefixBuilder.toString());
	        for (String sw : startWith) {
	            ansBuilder.add(sw);
	            search(len, tr, ans, ansBuilder);
	            ansBuilder.remove(ansBuilder.size() - 1);
	        }
	    }
//	https://discuss.leetcode.com/topic/63532/121ms-java-solution-using-trie-and-backtracking
//	    TrieNode root = new TrieNode();
//	    public List<List<String>> wordSquares(String[] words) {
//	        List<List<String>> ans = new ArrayList<>();
//	        if(words.length == 0) return ans;
//	        buildTrie(words);
//	        int length = words[0].length();
//	        findSquare(ans, length, new ArrayList<>());
//	        return ans;
//	    }
//	    
//	    private void findSquare(List<List<String>> ans, int length, List<String> temp) {
//	        if(temp.size() == length) {
//	            ans.add(new ArrayList<>(temp));
//	            return;
//	        }
//	        int index = temp.size();
//	        StringBuilder sb = new StringBuilder();
//	        for(String s : temp) {
//	            sb.append(s.charAt(index));
//	        }
//	        String s = sb.toString();
//	        TrieNode node = root;
//	        for(int i = 0; i < s.length(); i++) {
//	            if(node.next[s.charAt(i) - 'a'] != null) {
//	                node = node.next[s.charAt(i) - 'a'];
//	            } else {
//	                node = null;
//	                break;
//	            }
//	        }
//	        if(node != null) {
//	            for(String next : node.words) {
//	                temp.add(next);
//	                findSquare(ans, length, temp);
//	                temp.remove(temp.size() - 1);
//	            }
//	        }
//	    }
//	    
//	    private void buildTrie(String[] words) {
//	        for(String word : words) {
//	            TrieNode node = root;
//	            char[] array = word.toCharArray();
//	            for(char c : array) {
//	                node.words.add(word);
//	                if(node.next[c - 'a'] == null) {
//	                    node.next[c - 'a'] = new TrieNode();
//	                }
//	                node = node.next[c - 'a'];
//	            }
//	            node.words.add(word);
//	        }
//	    }
//	    
//	    class TrieNode {
//	        TrieNode[] next = new TrieNode[26];
//	        List<String> words = new ArrayList<>();
//	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] words = {"area","lead","wall","lady","ball", "leyd"};
		System.out.println(new LeetCode425WordSquares().wordSquares(words));
		

	}

}
