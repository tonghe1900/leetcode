package alite.leetcode.xx2.sucess.before;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 212 - Word Search II

LeetCode Q212 Word Search II | Lei Jiang Coding
Given a 2D board and a list of words from the dictionary, find all words in the board.
Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally 
or 
vertically neighboring. The same letter cell may not be used more than once in a word.
For example, given words = ["oath","pea","eat","rain"] and board =
[
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
Return ["eat","oath"].
You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?
If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. 
What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? How about a Trie?
 If you would like to learn how to implement a basic trie, please work on this problem: Implement Trie (Prefix Tree) first.
X. Use Trie
Time complexity:
http://buttercola.blogspot.com/2015/09/word-search-ii.html
https://leetcode.com/discuss/51505/my-java-solution-using-trie
Let's think about the naive solution first. The naive solution is we search the board for each board. 
So for the dict with n words, and assume the ave. length of each word has length of m. Then without using a Trie,
 the time complexity would be O(n * rows * cols  * 4^m). 

Now let's analyze the time complexity of using a Trie. We put each word into the trie. Search in the Trie takes 
O(m) time, so the time complexity would be O(rows * cols * m * 4^m). Since mostly m << n, using a trie would save 
lots of time. 
http://algobox.org/word-search-ii/
https://leetcode.com/discuss/77851/java-15ms-easiest-solution-100-00%25
https://leetcode.com/discuss/71402/java-dfs-trie-20ms-solution
Some key points:
reuse board to check visited
store word in TrieNode instead of boolean.
remove the word from the trie once it is found.   remove the word from the trie once it is found
Some explanations:
For point 1, we always restore the board to it’s original state after the recursive call. 
So the board remain the same after the search.
For point 2, although the TrieNode stores a String, it just stores a reference to the immutable string 
already in memory (since the string is already in words). So it doesn’t cost much more space than store boolean.
For point 3, we just need to set the node’s value to null which is a O(1) time operation. 
This does not only save time on hashing and convert set to list, it also prune some branch in the search. 
One may also implement a proper deleteoperation to actually delete the nodes in the path. 
Which one is better depends on the input.
Combing them, Trie is the natural choice. Notice that:
TrieNode is all we need. search and startsWith are useless.
No need to store character at TrieNode. c.next[i] != null is enough.
Never use c1 + c2 + c3. Use StringBuilder.
No need to use O(n^2) extra space visited[m][n].
No need to use StringBuilder. Storing word itself at leaf node is enough.
No need to use HashSet to de-duplicate. Use "one time search" trie.
UPDATE: Thanks to @dietpepsi we further improved from 17ms to 15ms.
59ms: Use search and startsWith in Trie class like this popular solution.
33ms: Remove Trie class which unnecessarily starts from root in every dfs call.
30ms: Use w.toCharArray() instead of w.charAt(i).
22ms: Use StringBuilder instead of c1 + c2 + c3.
20ms: Remove StringBuilder completely by storing word instead of boolean in TrieNode.
20ms: Remove visited[m][n] completely by modifying board[i][j] = '#' directly.
18ms: check validity, e.g., if(i > 0) dfs(...), before going to the next dfs.
17ms: De-duplicate c - a with one variable i.
15ms: Remove HashSet completely. dietpepsi's idea is awesome.
 It seems the total time complexity is O(n * len)， where n is the number of words, len is the average length of a word.
public List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    TrieNode root = buildTrie(words);
    for(int i = 0; i < board.length; i++) {
        for(int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, res);
        }
    }
    return res;
}

public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
    char c = board[i][j];
    if(c == '#' || p.next[c - 'a'] == null) return;
    p = p.next[c - 'a'];
    if(p.word != null) {   // found one
        res.add(p.word);
        p.word = null;     // de-duplicate
    }

    board[i][j] = '#';
    if(i > 0) dfs(board, i - 1, j ,p, res); 
    if(j > 0) dfs(board, i, j - 1, p, res);
    if(i < board.length - 1) dfs(board, i + 1, j, p, res); 
    if(j < board[0].length - 1) dfs(board, i, j + 1, p, res); 
    board[i][j] = c;
}

public TrieNode buildTrie(String[] words) {
    TrieNode root = new TrieNode();
    for(String w : words) {
        TrieNode p = root;
        for(char c : w.toCharArray()) {
            int i = c - 'a';
            if(p.next[i] == null) p.next[i] = new TrieNode();
            p = p.next[i];
       }
       p.word = w;
    }
    return root;
}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    String word;
}
https://discuss.leetcode.com/topic/14256/my-simple-and-clean-java-code-using-dfs-and-trie/

https://codesolutiony.wordpress.com/2015/05/20/leetcode-word-search-ii/
https://leetcode.com/discuss/51505/my-java-solution-using-trie
Use Hashset to remove duplication.
另外如果搜索的路径已经不是字典树是的前缀了就可以直接剪枝返回了。下面是AC代码。因为要避免重复，我先用了一个set存结果，然后再转存到result中。
因此得用trie来保存input的所有string，然后dfs遍历board来看当前的string是否存在于trie中。如果当前string不是trie中任意string的prefix，可以剪枝，不需要继续dfs下去。很好的题目，需要加强

    public List<String> findWords(char[][] board, String[] words) {

        List<String> res = new ArrayList<String>();

        if (board == null || board.length == 0 || board[0].length == 0

         || words == null || words.length == 0) {

            return res;

        }

         

        boolean[][] visited = new boolean[board.length][board[0].length];

        Set<String> noDuplicateInput = new HashSet<String>(Arrays.asList(words));

        Trie trie = new Trie(noDuplicateInput);

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[0].length; j++) {

                search(board, i, j, "", visited, trie, res);

            }

        }

        return res;

    }

    private void search(char[][] board, int i, int j,

            String str, boolean[][] visited, Trie trie, List<String> res) {

        if (i < board.length && i >= 0 && j < board[0].length && j >= 0 && visited[i][j] == false) {

            String newStr = str + board[i][j];

            TrieNode endNode = trie.startWith(newStr);//

            if (endNode != null) {

                if (endNode.leaf == true) {

                    res.add(newStr);

                    endNode.leaf = false; //avoid duplicate in result

                }

                visited[i][j] = true;

                search(board, i+1, j, newStr, visited, trie, res);

                search(board, i-1, j, newStr, visited, trie, res);

                search(board, i, j+1, newStr, visited, trie, res);

                search(board, i, j-1, newStr, visited, trie, res);

                visited[i][j] = false;

            }

        }

    }

    class Trie {

        TrieNode root;

        public Trie(Set<String> strs) {

            root = new TrieNode();

            for (String str : strs) {

                add(str);

            }

        }

        //gets the last node in the tree that matches the str, return null if not match

        public TrieNode startWith(String str) {

            TrieNode res = null;

            TrieNode curRoot = root;

            for (int i = 0; i < str.length(); i++) {

                if (curRoot.children != null && curRoot.children.get(str.charAt(i)) != null) {

                    if (i == str.length() - 1) {

                        res = curRoot.children.get(str.charAt(i));

                    }

                    curRoot = curRoot.children.get(str.charAt(i));

                } else {

                    break;

                }

            }

            return res;

        }

        public void add(String str) {

            TrieNode curRoot = root;

            for (int i = 0; i < str.length(); i++) {

                if (curRoot.children == null) {

                    curRoot.children = new HashMap<Character, TrieNode>();

                }

                if (curRoot.children.get(str.charAt(i)) == null) {

                    curRoot.children.put(str.charAt(i), new TrieNode(str.charAt(i)));

                }

                if (i == str.length() - 1) {

                    curRoot.children.get(str.charAt(i)).leaf = true;

                }

                curRoot = curRoot.children.get(str.charAt(i));

            }

        }

        public boolean contains(String str) {

            TrieNode lastNode = startWith(str);

            if (lastNode == null || lastNode.leaf == false) {

                return false;

            }

            return true;

        }

    }

    class TrieNode {

        boolean leaf;

        char ch;

        Map<Character, TrieNode> children;

        public TrieNode() { }

        public TrieNode(char ch) {

            this.ch = ch;

        }

    }
http://www.jiuzhang.com/solutions/word-search-ii/
Where is the visited hashset to avoid re-computation?
    class TrieNode {
        String s;
         boolean isString;
         HashMap<Character, TrieNode> subtree;
         public TrieNode() {
            // TODO Auto-generated constructor stub
             isString = false;
             subtree = new HashMap<Character, TrieNode>();
             s = "";
         }
    };


    class TrieTree{
        TrieNode root ;
        public TrieTree(TrieNode TrieNode) {
            root = TrieNode;
        }
        public void insert(String s) {
            TrieNode now = root;
            for (int i = 0; i < s.length(); i++) {
                if (!now.subtree.containsKey(s.charAt(i))) {
                    now.subtree.put(s.charAt(i), new TrieNode());
                }
                now  =  now.subtree.get(s.charAt(i));
            }
            now.s = s;
            now.isString  = true;
        }
        public boolean find(String s){
            TrieNode now = root;
            for (int i = 0; i < s.length(); i++) {
                if (!now.subtree.containsKey(s.charAt(i))) {
                    return false;
                }
                now  =  now.subtree.get(s.charAt(i));
            }
            return now.isString ;
        }
    };

    public int []dx = {1, 0, -1, 0};
    public int []dy = {0, 1, 0, -1};
    
    public void search(char[][] board, int x, int y, TrieNode root, ArrayList<String> ans, String res) {    
        if(root.isString == true)
        {
            if(!ans.contains(root.s)){
                ans.add(root.s);
            }
        }
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y]==0 || root == null)
            return ;
        if(root.subtree.containsKey(board[x][y])){
            for(int i = 0; i < 4; i++){
                char now = board[x][y];
                board[x][y] = 0;
                search(board, x+dx[i], y+dy[i], root.subtree.get(now), ans, res);
                board[x][y] = now;
            }
        }
        
    }
    
    public ArrayList<String> wordSearchII(char[][] board, ArrayList<String> words) {
        ArrayList<String> ans = new ArrayList<String>();
        
        TrieTree tree = new TrieTree(new TrieNode());
        for(String word : words){
            tree.insert(word);
        }
        String res = ""; 
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                search(board, i, j, tree.root, ans, res);
            }
        }
        return ans;        
    }


If the current candidate does not exist in all words' prefix, we can stop backtracking immediately. This can be done by using a trie structure.
public class Solution {
    Set<String> result = new HashSet<String>(); 
 
    public List<String> findWords(char[][] board, String[] words) {
        //HashSet<String> result = new HashSet<String>();
 
        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }
 
        int m=board.length;
        int n=board[0].length;
 
        boolean[][] visited = new boolean[m][n];
 
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
               dfs(board, visited, "", i, j, trie);
            }
        }
 
        return new ArrayList<String>(result);
    }
 
    public void dfs(char[][] board, boolean[][] visited, String str, int i, int j, Trie trie){
        int m=board.length;
        int n=board[0].length;
 
        if(i<0 || j<0||i>=m||j>=n){
            return;
        }
 
        if(visited[i][j])
            return;
 
        str = str + board[i][j];
 
        if(!trie.startsWith(str))
            return;
 
        if(trie.search(str)){
            result.add(str);
        }
 
        visited[i][j]=true;
        dfs(board, visited, str, i-1, j, trie);
        dfs(board, visited, str, i+1, j, trie);
        dfs(board, visited, str, i, j-1, trie);
        dfs(board, visited, str, i, j+1, trie);
        visited[i][j]=false;
    }
}
//Trie Node
class TrieNode{
    public TrieNode[] children = new TrieNode[26];
    public String item = "";
}
 
//Trie
class Trie{
    public TrieNode root = new TrieNode();
 
    public void insert(String word){
        TrieNode node = root;
        for(char c: word.toCharArray()){
            if(node.children[c-'a']==null){
                node.children[c-'a']= new TrieNode();
            }
            node = node.children[c-'a'];
        }
        node.item = word;
    }
 
    public boolean search(String word){
        TrieNode node = root;
        for(char c: word.toCharArray()){
            if(node.children[c-'a']==null)
                return false;
            node = node.children[c-'a'];
        }
        if(node.item.equals(word)){
            return true;
        }else{
            return false;
        }
    }
 
    public boolean startsWith(String prefix){
        TrieNode node = root;
        for(char c: prefix.toCharArray()){
            if(node.children[c-'a']==null)
                return false;
            node = node.children[c-'a'];
        }
        return true;
    }
}
http://buttercola.blogspot.com/2015/09/word-search-ii.html

    private int rows;

    private int cols;

    public List<String> findWords(char[][] board, String[] words) {

        List<String> result = new ArrayList<String>();

        if (board == null || board.length == 0 || words == null || words.length == 0) {

            return result;

        } 

         

        Set<String> set = new HashSet<String>();

         

        Trie trie = new Trie();

         

        this.rows = board.length;

        this.cols = board[0].length;

         

        // Step 1: insert all words into a trie

        for (String word : words) {

            trie.insert(word);

        }

         

        // Step 2: search the board

        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                searchBoardHelper(board, i, j, "", visited, trie, set);

            }

        }

         

        return new ArrayList<String>(set);

    }

     

     

    private void searchBoardHelper(char[][] board, int row, int col, String word, 

                                      boolean[][] visited, Trie trie, Set<String> set) {

        if (row < 0 || row >= rows || col < 0 || col >= cols) {

            return;

        }

         

        if (visited[row][col]) {

            return;

        }

         

        word += board[row][col];

         

        if (!trie.searchPrefix(word)) {

            return;

        }

         

        if (trie.search(word)) { //\\ no need to search again, just check whether the node is a leaf

            set.add(word);

        }         

        visited[row][col] = true;

         

        searchBoardHelper(board, row - 1, col, word, visited, trie, set);

        searchBoardHelper(board, row + 1, col, word, visited, trie, set);

        searchBoardHelper(board, row, col - 1, word, visited, trie, set);

        searchBoardHelper(board, row, col + 1, word, visited, trie, set);

         

        visited[row][col] = false;

    }

     

    class Trie {

        private TrieNode root = new TrieNode();

         

        // Insert a word

        void insert(String s) {

            TrieNode curr = root;

            int offset = 'a';

            for (int i = 0; i < s.length(); i++) {

                char character = s.charAt(i);

                if (curr.children[character - offset] == null) {

                    curr.children[character - offset] = 

                        new TrieNode(character, i == s.length() - 1 ? true : false);

                } else {

                    if (i == s.length() - 1) {

                        curr.children[character - offset].leaf = true;

                    }

                }

                curr = curr.children[character - offset];

            }

        }

         

         

        // Search a word

        boolean search(String s) {

            TrieNode curr = root;

            int offset = 'a';

            for (int i = 0; i < s.length(); i++) {

                char character = s.charAt(i);

                if (curr.children[character - offset] == null) {

                    return false;

                }

                curr = curr.children[character - offset];

            }

             

            if (curr != null && !curr.leaf) {

                return false;

            }

             

            return true;

        }

         

        // Search for prefix

        boolean searchPrefix(String s) {

            TrieNode curr = root;

            int offset = 'a';

            for (int i = 0; i < s.length(); i++) {

                char character = s.charAt(i);

                if (curr.children[character - offset] == null) {

                    return false;

                } 

                 

                curr = curr.children[character - offset];

            }

             

            return true;

        }

    }

     

    class TrieNode {

        char val;

        boolean leaf;

        TrieNode[] children;

         

        public TrieNode() {

            this.val = '\0';

            this.children = new TrieNode[26];

        } 

         

        public TrieNode(char val, boolean leaf) {

            this.val = val;

            this.leaf = leaf;

            this.children = new TrieNode[26];

        }

    }
http://www.huhaoyu.com/leetcode-word-search-2/
这种基于哈希表的算法，每次只查询一个单词，显然这会导致重复工作。所以正确的做法是构造一个字典树（Trie Tree），将字典树作为一个待查询的字符串集，在board中进行查找。
毋庸置疑，基于字典树的算法每次都查询整个字符串集，避免了相同前缀多次搜索的问题。
http://shibaili.blogspot.com/2015/06day-108-add-and-search-word-data.html
You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?
If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? How about a Trie? If you would like to learn how to implement a basic trie, please work on this problem: Implement Trie (Prefix Tree) first.

    void search(vector<string> &rt, vector<vector<char>>& board, 

            vector<vector<bool> > &visit, Trie &trie, string word, int row, int col, unordered_set<string> &hadIt) {

        if (row < 0 || row >= board.size() || col < 0 || col >= board[0].size() || visit[row][col]) {

            return;

        }

        word += board[row][col];

        visit[row][col] = true;

        if (hadIt.find(word) == hadIt.end() && trie.search(word)) {

            hadIt.insert(word);

            rt.push_back(word);

        }

         

        if (trie.startsWith(word)) {

            search(rt,board,visit,trie,word,row + 1,col,hadIt);

            search(rt,board,visit,trie,word,row - 1,col,hadIt);

            search(rt,board,visit,trie,word,row,col + 1,hadIt);

            search(rt,board,visit,trie,word,row,col - 1,hadIt);

        }

        visit[row][col] = false;

    }


    vector<string> findWords(vector<vector<char>>& board, vector<string>& words) {

        Trie trie;

        for (int i = 0; i < words.size(); i++) {

            trie.insert(words[i]);

        }

         

        vector<string> rt;

        vector<vector<bool> > visit(board.size(),vector<bool>(board[0].size(),false));

        unordered_set<string> hadIt;

        for (int i = 0; i < board.size(); i++) {

            for (int j = 0; j <board[0].size(); j++) {

                search(rt,board,visit,trie,"",i,j,hadIt);

            }

        }

         

        return rt;

    }

http://www.cnblogs.com/linxiong/p/4518192.html
Word Search Problem - Non-recursive Solution
http://www.bo-yang.net/2014/07/28/word-search/
X. Simple DFS
http://blog.csdn.net/xudli/article/details/45864915
    public List<String> findWords(char[][] board, String[] words) {  
        List<String> res = new ArrayList<String>();  
        if(board==null || words==null || board.length==0 || words.length==0) return res;  
        boolean[][] visited = new boolean[board.length][board[0].length];   
          
        Set<String> dict = new HashSet<String>(Arrays.asList(words));  
          
        for(int i=0; i<board.length; i++) {  
            for(int j=0; j<board[0].length; j++) {  
                search(board, visited, dict, i, j, new StringBuilder(), res);  
            }  
        }  
        return res;  
    }  
      
    private void search(char[][] board,boolean[][] visited,Set<String> dict,int i,int j, StringBuilder sb, List<String> res) {  
        if(i<0 || i>board.length-1 || j<0 || j>board[0].length-1 || visited[i][j])  return;  
        sb.append(board[i][j]);  
        visited[i][j] = true;  
        if(dict.contains(sb.toString()))   
            res.add(sb.toString());  
          
        search(board, visited, dict, i-1, j, sb, res);  
        search(board, visited, dict, i+1, j, sb, res);  
        search(board, visited, dict, i, j-1, sb, res);  
        search(board, visited, dict, i, j+1, sb, res);  
          
        sb.deleteCharAt(sb.length() - 1);  
        visited[i][j] = false;  
    }  

http://www.programcreek.com/2014/06/leetcode-word-search-ii-java/
public List<String> findWords(char[][] board, String[] words) {
 ArrayList<String> result = new ArrayList<String>();
 
 int m = board.length;
 int n = board[0].length;
 
 for (String word : words) {
  boolean flag = false;
  for (int i = 0; i < m; i++) {
   for (int j = 0; j < n; j++) {
    char[][] newBoard = new char[m][n];
    for (int x = 0; x < m; x++)
     for (int y = 0; y < n; y++)
      newBoard[x][y] = board[x][y];
 
    if (dfs(newBoard, word, i, j, 0)) {
     flag = true;
    }
   }
  }
  if (flag) {
   result.add(word);
  }
 }
 
 return result;
}
 
public boolean dfs(char[][] board, String word, int i, int j, int k) {
 int m = board.length;
 int n = board[0].length;
 
 if (i < 0 || j < 0 || i >= m || j >= n || k > word.length() - 1) {
  return false;
 }
 
 if (board[i][j] == word.charAt(k)) {
  char temp = board[i][j];
  board[i][j] = '#';
 
  if (k == word.length() - 1) {
   return true;
  } else if (dfs(board, word, i - 1, j, k + 1)
    || dfs(board, word, i + 1, j, k + 1)
    || dfs(board, word, i, j - 1, k + 1)
    || dfs(board, word, i, j + 1, k + 1)) {
   board[i][j] = temp;
   return true;
  }
 
 } else {
  return false;
 }
 
 return false;
}
Read full article from LeetCode Q212 Word Search II | Lei Jiang Coding
 * @author het
 *
 *
 *Let's think about the naive solution first. The naive solution is we search the board for each board. 
So for the dict with n words, and assume the ave. length of each word has length of m. Then without using a Trie,
 the time complexity would be O(n * rows * cols  * 4^m). 

Now let's analyze the time complexity of using a Trie. We put each word into the trie. Search in the Trie takes 
O(m) time, so the time complexity would be O(rows * cols * m * 4^m). Since mostly m << n, using a trie would save 
lots of time. 
 */

//So for the dict with n words, and assume the ave. length of each word has length of m. Then without using a Trie,
//the time complexity would be O(n * rows * cols  * 4^m). 
//
//Now let's analyze the time complexity of using a Trie. We put each word into the trie. Search in the Trie takes 
//O(m) time, so the time complexity would be O(rows * cols * m * 4^m). Since mostly m << n, using a trie would save 
//lots of time. 
public class LeetCode212 {
	// It seems the total time complexity is O(n * len)， where n is the number of words, len is the average length of a word.
	 public List<String> findWords(char[][] board, String[] words) {
	     List<String> res = new ArrayList<>();
	     TrieNode root = buildTrie(words);
	     for(int i = 0; i < board.length; i++) {
	         for(int j = 0; j < board[0].length; j++) {
	             dfs(board, i, j, root, res);
	         }
	     }
	     return res;
	 }

	 public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
	     char c = board[i][j];
	     if(c == '#' || p.next[c - 'a'] == null) return;
	     p = p.next[c - 'a'];
	     if(p.word != null) {   // found one
	         res.add(p.word);
	         p.word = null;     // de-duplicate
	     }

	     board[i][j] = '#';
	     if(i > 0) dfs(board, i - 1, j ,p, res); 
	     if(j > 0) dfs(board, i, j - 1, p, res);
	     if(i < board.length - 1) dfs(board, i + 1, j, p, res); 
	     if(j < board[0].length - 1) dfs(board, i, j + 1, p, res); 
	     board[i][j] = c;
	 }

	 public TrieNode buildTrie(String[] words) {
	     TrieNode root = new TrieNode();
	     for(String w : words) {
	         TrieNode p = root;
	         for(char c : w.toCharArray()) {
	             int i = c - 'a';
	             if(p.next[i] == null) p.next[i] = new TrieNode();
	             p = p.next[i];
	        }
	        p.word = w;
	     }
	     return root;
	 }

	 class TrieNode {
	     TrieNode[] next = new TrieNode[26];
	     String word;
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
