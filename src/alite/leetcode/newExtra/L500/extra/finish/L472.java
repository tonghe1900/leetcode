package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 472 - Concatenated Words

http://bookshadow.com/weblog/2016/12/18/leetcode-concatenated-words/
Given a list of words, please write a program that returns all concatenated words in the given list of words.
A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
Example:
Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]


public List<String> getConcatWords(List<String> input){
     Node root = createTrie(input);
     List<String> result  = new ArrayList<>();
     getConcatWords(result, root, 0, new StringBuilder(),List<String> input);
     // filter out the longest length words from input 
     return result;
}

public void getConcatWords( List<String> result, Node root, int wordsUsed, StringBuilder sb,List<String> input){
   
   if(wordsUsed > 1 && root.isWord){
           result.add(sb.toString());
           return;
   }
   
   if(wordsUsed ==1 && root.isWord){
           
           return;
   }
   if(root == null) return;
   for(int i=0;i<input.length;i+=1){
      String word  =input.get(i);
      Node node = move(root, word);
      if(node != null){
             int length = sb.length();
             getConcatWords(result, node, wordsUsed+1, sb.append(word));
             sb.setLength(length);
              
      }
      
   }
   
   
   
}





Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
 "dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
Note:
The number of elements of the given array will not exceed 10,000
The length sum of elements in the given array will not exceed 600,000.
The returned elements order does not matter.

解法I 深度优先搜索（Depth First Search）
    def findAllConcatenatedWordsInADict(self, words):
        """
        :type words: List[str]
        :rtype: List[str]
        """
        ans = []
        self.wordSet = set(words)
        for word in words:
            self.wordSet.remove(word)
            if self.search(word):
                ans.append(word)
            self.wordSet.add(word)
        return ans

    def search(self, word):
        if word in self.wordSet:
            return True
        for idx in range(1, len(word)):
            if word[:idx] in self.wordSet and self.search(word[idx:]):
                return True
        return False

解法II 字典树（Trie）
思路同前，使用字典树Trie替换Set，理论上可以提升查找效率。
然而，下面的代码返回ML
    def findAllConcatenatedWordsInADict(self, words):
        """
        :type words: List[str]
        :rtype: List[str]
        """
        self.trie = Trie()
        ans = []
        for word in words:
            self.trie.insert(word)
        for word in words:
            if self.search(word):
                ans.append(word)
        return ans

    def search(self, word):
        node = self.trie.root
        for idx, letter in enumerate(word):
            node = node.children.get(letter)
            if node is None:
                return False
            suffix = word[idx+1:]
            if node.isWord and (self.trie.search(suffix) or self.search(suffix)):
                return True
        return False

class TrieNode:
    def __init__(self):
        self.children = dict()
        self.isWord = False

class Trie:
    def __init__(self):
        self.root = TrieNode()

    def insert(self, word):
        node = self.root
        for letter in word:
            child = node.children.get(letter)
            if child is None:
                child = TrieNode()
                node.children[letter] = child
            node = child
        node.isWord = True

    def search(self, word):
        node = self.root
        for letter in word:
            node = node.children.get(letter)
            if node is None:
                return False
        return node.isWord
 * @author het
 *
 */
public class L472 {
	public List<String> getConcatWords(List<String> input){
	     Node root = createTrie(input);
	     List<String> result  = new ArrayList<>();
	     getConcatWords(result, root, 0, new StringBuilder(),List<String> input);
	     // filter out the longest length words from input 
	     return result;
	}

	public void getConcatWords( List<String> result, Node root, int wordsUsed, StringBuilder sb,List<String> input){
	   
	   if(wordsUsed > 1 && root.isWord){
	           result.add(sb.toString());
	           return;
	   }
	   
	   if(wordsUsed ==1 && root.isWord){
	           
	           return;
	   }
	   if(root == null) return;
	   for(int i=0;i<input.length;i+=1){
	      String word  =input.get(i);
	      Node node = move(root, word);
	      if(node != null){
	             int length = sb.length();
	             getConcatWords(result, node, wordsUsed+1, sb.append(word));
	             sb.setLength(length);
	              
	      }
	      
	   }
	   
	   
	   
	}
}
