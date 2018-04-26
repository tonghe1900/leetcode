package alite.leetcode.xx4.select;

import java.util.ArrayList;
import java.util.List;

/**
 * http://bookshadow.com/weblog/2016/10/02/leetcode-minimum-unique-word-abbreviation/
A string such as "word" contains the following abbreviations:
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
Given a target string and a set of strings in a dictionary, find an abbreviation of this target string 
with the smallest possible length such that it does not conflict with abbreviations of the strings in the dictionary.
Each number or letter in the abbreviation is considered length = 1. For example, the abbreviation "a32bc" has length = 4.
Note:
In the case of multiple answers as shown in the second example below, you may return any one of them.
Assume length of target string = m, and dictionary size = n. You may assume that m ≤ 21, n ≤ 1000, and log2(n) + m ≤ 20.
Examples:
"apple", ["blade"] -> "a4" (because "5" or "4e" conflicts with "blade")
"apple", ["plain", "amber", "blade"] -> "1p3" (other valid answers include "ap3", "a3e", "2p2", "3le", "3l1").

深度优先搜索（DFS） + 剪枝
将单词缩写abbr利用二进制转化为数字形式，例如"w3d"可以视为二进制数10001，即十进制17。
检测单词缩写abbr与字典dictionary中的单词d是否存在冲突，可以通过如下方式判断：
for d in dictionary:
    abbr & d == abbr
若循环中存在上式为真的情形，说明存在冲突。
DFS从target出发，逐一去除字母，检测冲突，若不存在冲突，则递归，并更新最优解。
剪枝策略：
利用变量len记录当前时刻的最优的单词缩写长度，若DFS分支的长度大于len，则可剪枝
    def minAbbreviation(self, target, dictionary):
        """
        :type target: str
        :type dictionary: List[str]
        :rtype: str
        """
        self.size = len(target)
        self.wlist = [self.toNumber(target, d) \
                      for d in dictionary \
                      if len(d) == self.size]
        self.ans = (1 << self.size) - 1
        self.len = self.size + 1
        self.vset = set([self.ans])
        self.dfs(self.ans, self.size)
        return self.toWord(self.ans)
    def dfs(self, number, depth):
        if depth >= self.len: return
        if not self.checkNumber(number): return
        self.ans = number
        self.len = depth
        for x in range(self.size):
            if (number >> x) & 1:
                next = number ^ (1 << x)
                if next not in self.vset:
                    self.vset.add(next)
                    self.dfs(next, depth - 1)
    def toNumber(self, target, word):
        ans = 0
        for x in range(self.size - 1, -1, -1):
            ans <<= 1
            ans += target[x] == word[x]
        return ans
    def toWord(self, number):
        ans = ''
        cnt = 0
        for x in range(self.size):
            if number & 1:
                if cnt:
                    ans += str(cnt)
                    cnt = 0
                ans += target[x]
            else:
                cnt += 1
            number >>= 1
        if cnt:
            ans += str(cnt)
        return ans
    def checkNumber(self, number):
        for w in self.wlist:
            if number & w == number:
                return False
        return True
X. Use Trie
https://discuss.leetcode.com/topic/61982/java-dfs-trie-binary-search-90ms
https://scottduan.gitbooks.io/leetcode-review/content/minimum_unique_word_abbreviation.html
https://discuss.leetcode.com/topic/61346/trie-bruteforce
Use Trie to build a dictionary with a function to check abbreviation.
Use DFS with backtracking to generate the abbreviations of a given length.
Use binary search to find the smallest possible length.
-- binary search(bisection) seems good idea
-- seem not really good to use trie to check whether it's abbr.
    class Node{ // Trie Node
        Node[] nodes;
        boolean isWord;
        Node(){
            nodes = new Node[26];
            isWord = false;
        }
        void add(String str){ // add a word to Trie
            if (str.length() == 0) isWord=true; // end of a word
            else {
                int idx = str.charAt(0)-'a'; // insert a new node
                if (nodes[idx] == null) nodes[idx] = new Node();
                nodes[idx].add(str.substring(1));
            }
        }
        boolean isAbbr(String abbr, int num){
            if ( num > 0){ // number of '*'
                for (Node node : nodes){ 
                    if (node != null && node.isAbbr(abbr, num-1)) return true; 
                }
                return false; // not exist in the dictionary
            } else {
                if (abbr.length()==0) return isWord; // at the end of the addr
                int idx=0; // get the number of '*' at the start of the abbr
                while (idx < abbr.length() && abbr.charAt(idx) >='0' && abbr.charAt(idx) <='9' ) {
                    num = (num*10) + (abbr.charAt(idx++)-'0'); 
                }
                if (num>0) return isAbbr(abbr.substring(idx),num); // start with number
                else { // start with non-number
                    if (nodes[abbr.charAt(0)-'a'] != null )   
                        return nodes[abbr.charAt(0)-'a'].isAbbr(abbr.substring(1), 0);
                    else return false; // not exist in the dictionary 
                }
            }
        }
    }
    
    void getAbbs(char[] cc, int s, int len, StringBuilder sb, List<String> abbs){ //DFS with backtracking
        boolean preNum = (sb.length() > 0 ) && (sb.charAt(sb.length()-1) >= '0') && (sb.charAt(sb.length()-1) <= '9');
        if (len == 1)  { 
            if ( s  < cc.length) {
                if (s==cc.length-1) abbs.add(sb.toString() + cc[s]); // add one char
                if (! preNum ) abbs.add(sb.toString() + (cc.length-s) ); // add a number
            }
        } else if (len > 1 ) {
            int last = sb.length();
            for (int i=s+1; i < cc.length; i++ ){
                if (! preNum) { // add a number
                    sb.append(i-s);
                    getAbbs(cc, i, len-1, sb, abbs);
                    sb.delete(last, sb.length());
                }
                if (i==s+1) { // add one char
                    sb.append(cc[s]);
                    getAbbs(cc, i, len-1, sb, abbs);
                    sb.delete(last, sb.length());
                }
            }
        }
    }
    
    public String minAbbreviation(String target, String[] dictionary) {
        List<String> dict = new ArrayList();
        int len = target.length();
        for (String str : dictionary) if (str.length() == len ) dict.add(str);
        if (dict.isEmpty()) return ""+len;
        Node root = new Node();
        for (String str : dict) root.add(str);
        char[] cc = target.toCharArray();
        String ret = null;

        int min = 1, max = len; 
        while (max >= min) {
            int mid = min+( (max-min)/2 );
            List<String> abbs = new ArrayList();
            getAbbs(cc, 0, mid, new StringBuilder(), abbs);
            boolean conflict = true;
            for (String abbr: abbs){
                if ( ! root.isAbbr(abbr,0) ) {
                    conflict = false;
                    ret = abbr;
                    break;
                } 
            }
            if (conflict) {
                min = mid+1;
            } else {
                max = mid-1;
            }
        }
        return ret;
    }

X. Brute Force
http://www.cnblogs.com/grandyang/p/5935836.html
这道题实际上是之前那两道Valid Word Abbreviation和Generalized Abbreviation的合体，我们的思路其实很简单，首先找出target的所有的单词缩写的形式，然后按照长度来排序，小的排前面，我们用优先队列来自动排序，里面存一个pair，保存单词缩写及其长度，然后我们从最短的单词缩写开始，跟dictionary中所有的单词一一进行验证，利用Valid Word Abbreviation中的方法，看其是否是合法的单词的缩写，如果是，说明有冲突，直接break，进行下一个单词缩写的验证
https://discuss.leetcode.com/topic/61327/brute-force-solution-just-check-every-possible-abbreviation/3
Use the approach of “320. Generalized Abbreviation” to generate all abbreviations of “target”;
Put all the abbreviations into a PriorityQueue according to the length of the abbreviations;
With each abbreviation, check whether it’s an abbreviation of any word in the dictionary using the approach of “408. Valid Word Abbreviation”.
    public class Abbreviation{
        public String abbr;
        public int len;
        
        public Abbreviation(String abbr, int len){
            this.abbr = abbr;
            this.len = len;
        }
    }
    
    public String minAbbreviation(String target, String[] dictionary) {
        if(dictionary.length == 0) return Integer.toString(target.length());
        PriorityQueue<Abbreviation> q = new PriorityQueue<Abbreviation>(new Comparator<Abbreviation>(){
           public int compare(Abbreviation a1, Abbreviation a2){
               return a1.len - a2.len;
           } 
        });
        generateAbbrs(q, target, "", 0, 0, false);
        System.out.println(q.size());
        while(!q.isEmpty()){
            String abbr = q.poll().abbr;
            boolean notMatch = true;
            for(int i=0; i<dictionary.length; i++){
                if(isValidAbbr(dictionary[i], abbr)){
                    notMatch = false;
                    break;
                }
            }
            if(notMatch) return abbr;
        }
        return "";
    }
    
    private void generateAbbrs(PriorityQueue<Abbreviation> q, String target, String path, int start, int len, boolean prevAbbr){
        if(start == target.length()){
            q.offer(new Abbreviation(path, len));
            return;
        }
        generateAbbrs(q, target, path+target.charAt(start), start+1, len+1, false);
        if(!prevAbbr){
            for(int i=start; i<target.length(); i++){
                String str = target.substring(start, i+1);
                generateAbbrs(q, target, path+Integer.toString(str.length()), i+1, len+1, true);
            }
        }
    }
    
    private boolean isValidAbbr(String word, String abbr){
        int index = 0, i = 0;
        while(i < abbr.length()){
            if(!Character.isDigit(abbr.charAt(i))){
                if(index >= word.length() || word.charAt(index) != abbr.charAt(i)) return false;
                index++; i++;
            }else{
                int start = i;
                while(i < abbr.length() && Character.isDigit(abbr.charAt(i))) i++;
                int number = Integer.parseInt(abbr.substring(start, i));
                index += number;
            }
        }
        return index == word.length();
    }
https://discuss.leetcode.com/topic/61346/trie-bruteforce
Abbreviation number is pretty like wild card and it can match all the characters appearing in the trie.
There's 3 functions:
addTrie: add string to the trie
search: search a string to determine if that's the one in the trie (wild card mode)
abbrGenerator: generate all the possible abbreviations given certain length (which is num parameter).
class Trie{
        Trie[] next = new Trie[26];
        boolean isEnd = false;
    }
    Trie root = new Trie();
    List<String> abbrs;
    public String minAbbreviation(String target, String[] dictionary) {
        for(String s:dictionary) {
            addTrie(s);
        }
        for(int i=0; i<target.length(); i++) {
            abbrs = new ArrayList<>();
            abbrGenerator(target, 0, "", 0, i+1);
            for(String s:abbrs) {
                if(search(s, root, 0, 0)==false) return s;
            }
        }
        return "";
    }
    public void addTrie(String s) {
        Trie cur = root;
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(cur.next[c-'a']==null) {
                cur.next[c-'a']=new Trie();
            }
            cur = cur.next[c-'a'];
        }
        cur.isEnd = true;
    }
    public boolean search(String target, Trie root, int i, int loop) {
        if(root==null) return false;

        if(loop!=0) {
            for(int a=0; a<26; a++) {
                if(search(target, root.next[a], i, loop-1)) return true;
            }
            return false;
        }
        if(i==target.length()) {
            if(root.isEnd) return true;
            return false;
        }
        if(Character.isDigit(target.charAt(i))) {
            int tmp = 0;
            while(i<target.length()&&Character.isDigit(target.charAt(i))) {
                tmp = tmp*10 + target.charAt(i)-'0';
                i++;
            }
            return search(target, root, i, tmp);
        } else {
            return search(target, root.next[target.charAt(i)-'a'], i+1, 0);
        }
    }
    public void abbrGenerator(String target, int i, String tmp, int abbr, int num) {
        if(i==target.length()) {
            if(num==0&&abbr==0) abbrs.add(tmp);
            if(num==1&&abbr!=0) abbrs.add(tmp+abbr);
            return;
        }
        if(num<=0) return;
        char cur = target.charAt(i);
        abbrGenerator(target, i+1, abbr==0?tmp+cur:tmp+abbr+cur, 0, abbr==0?num-1:num-2);
        abbrGenerator(target, i+1, tmp, abbr+1, num);
    }
 * @author het
 *
 */
public class LeetCode411MinimumUniqueWordAbbreviation {
	static class Node{ // Trie Node
	        Node[] nodes;
	        boolean isWord;
	        Node(){
	            nodes = new Node[26];
	            isWord = false;
	        }
	        void add(String str){ // add a word to Trie
	            if (str.length() == 0) isWord=true; // end of a word
	            else {
	                int idx = str.charAt(0)-'a'; // insert a new node
	                if (nodes[idx] == null) nodes[idx] = new Node();
	                nodes[idx].add(str.substring(1));
	            }
	        }
	        boolean isAbbr(String abbr, int num){
	            if ( num > 0){ // number of '*'
	                for (Node node : nodes){ 
	                    if (node != null && node.isAbbr(abbr, num-1)) return true; 
	                }
	                return false; // not exist in the dictionary
	            } else {
	                if (abbr.length()==0) return isWord; // at the end of the addr
	                int idx=0; // get the number of '*' at the start of the abbr
	                while (idx < abbr.length() && abbr.charAt(idx) >='0' && abbr.charAt(idx) <='9' ) {
	                    num = (num*10) + (abbr.charAt(idx++)-'0'); 
	                }
	                if (num>0) return isAbbr(abbr.substring(idx),num); // start with number
	                else { // start with non-number
	                    if (nodes[abbr.charAt(0)-'a'] != null )   
	                        return nodes[abbr.charAt(0)-'a'].isAbbr(abbr.substring(1), 0);
	                    else return false; // not exist in the dictionary 
	                }
	            }
	        }
	    }
	    
	    static void getAbbs(char[] cc, int s, int len, StringBuilder sb, List<String> abbs){ //DFS with backtracking
	        boolean preNum = (sb.length() > 0 ) && (sb.charAt(sb.length()-1) >= '0') && (sb.charAt(sb.length()-1) <= '9');
	        if (len == 1)  { 
	            if ( s  < cc.length) {
	                if (s==cc.length-1) abbs.add(sb.toString() + cc[s]); // add one char
	                if (! preNum ) abbs.add(sb.toString() + (cc.length-s) ); // add a number
	            }
	        } else if (len > 1 ) {
	            int last = sb.length();
	            for (int i=s+1; i < cc.length; i++ ){
	                if (! preNum) { // add a number
	                    sb.append(i-s);
	                    getAbbs(cc, i, len-1, sb, abbs);
	                    sb.delete(last, sb.length());  //sb.delete(last, sb.length());
	                    // sb.delete(last, sb.length());  //sb.delete(last, sb.length());
	                }
	                if (i==s+1) { // add one char
	                    sb.append(cc[s]);
	                    getAbbs(cc, i, len-1, sb, abbs);
	                    sb.delete(last, sb.length());
	                }
	            }
	        }
	    }
	    
	    public static String minAbbreviation(String target, String[] dictionary) {
	        List<String> dict = new ArrayList();
	        int len = target.length();
	        for (String str : dictionary) if (str.length() == len ) dict.add(str);
	        if (dict.isEmpty()) return ""+len;
	        Node root = new Node();
	        for (String str : dict) root.add(str);
	        char[] cc = target.toCharArray();
	        String ret = null;

	        int min = 1, max = len; 
	        while (max >= min) {
	            int mid = min+( (max-min)/2 );
	            List<String> abbs = new ArrayList();
	            getAbbs(cc, 0, mid, new StringBuilder(), abbs);
	            boolean conflict = true;
	            for (String abbr: abbs){
	                if ( ! root.isAbbr(abbr,0) ) {
	                    conflict = false;
	                    ret = abbr;
	                    break;
	                } 
	            }
	            if (conflict) {
	                min = mid+1;
	            } else {
	                max = mid-1;
	            }
	        }
	        return ret;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(minAbbreviation("apple", new String[]{"plain", "amber", "blade"}));
		
		

	}

}
