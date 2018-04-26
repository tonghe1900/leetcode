package alite.leetcode.newExtra.L500;
/**
 * LeetCode 527 - Word Abbreviation

http://bookshadow.com/weblog/2017/03/12/leetcode-word-abbreviation/
Given an array of n distinct non-empty strings, you need to generate minimal possible abbreviations for 
every word following rules below.
Begin with the first character and then the number of characters abbreviated, which followed by the last character.
If there are any conflict, that is more than one words share the same abbreviation, a longer prefix is used 
instead of only the first character until making the map from word to abbreviation become unique. In other words,
 a final abbreviation cannot map to more than one original words.
If the abbreviation doesn't make the word shorter, then keep it as original.
Example:
Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
Note:
Both n and the length of each word will not exceed 400.
The length of each word is greater than 1.
The words consist of lowercase English letters only.
The return answers should be in the same order as the original array.

字典（Map）+递归
利用字典dmap维护原始字符串word到压缩字符串abbr的映射

尝试将所有字符串从最短长度开始进行压缩

若同一个压缩字符串对应多个原串，则将这些串递归求解

否则，将该压缩串的结果加入dmap
https://discuss.leetcode.com/topic/82613/really-simple-and-straightforward-java-solution
Make abbreviation for each word.
Then, check each word, if there are some strings which have same abbreviation with it, increase the prefix.
    public List<String> wordsAbbreviation(List<String> dict) {
        int len=dict.size();
        String[] ans=new String[len];
        int[] prefix=new int[len];
        for (int i=0;i<len;i++) {
            prefix[i]=1;
            ans[i]=makeAbbr(dict.get(i), 1); // make abbreviation for each string
        }
        for (int i=0;i<len;i++) {
            while (true) {
                HashSet<Integer> set=new HashSet<>();
                for (int j=i+1;j<len;j++) {
                    if (ans[j].equals(ans[i])) set.add(j); // check all strings with the same abbreviation
                }
                if (set.isEmpty()) break;
                set.add(i);
                for (int k: set) 
                    ans[k]=makeAbbr(dict.get(k), ++prefix[k]); // increase the prefix
            }
        }
        return Arrays.asList(ans);
    }

    private String makeAbbr(String s, int k) {
        if (k>=s.length()-2) return s;
        StringBuilder builder=new StringBuilder();
        builder.append(s.substring(0, k));
        builder.append(s.length()-1-k);
        builder.append(s.charAt(s.length()-1));
        return builder.toString();
    }
https://discuss.leetcode.com/topic/82613/really-simple-and-straightforward-java-solution/5
public List<String> wordsAbbreviation(List<String> dict) {
  String ans[] = new String[dict.size()];
  abbreviate(ans, dict, IntStream.range(0, ans.length).boxed().collect(Collectors.toList()), 1);
  return Arrays.asList(ans);
}

private void abbreviate(String[] ans, List<String> dict, List<Integer> idxs, int k) {
  Map<String, List<Integer>> map = new HashMap<>();
  idxs.stream().forEach(idx -> map.computeIfAbsent(getAbbr(dict.get(idx), k), key -> new ArrayList<>()).add(idx));
  for (Entry<String, List<Integer>> entry : map.entrySet())
    if (entry.getValue().size() == 1) ans[entry.getValue().get(0)] = entry.getKey();
    else abbreviate(ans, dict, entry.getValue(), k + 1);
}

private String getAbbr(String s, int k) {
  return s.length() - k < 3 ? s : s.substring(0, k) + (s.length() - 1 - k) + s.charAt(s.length() - 1);
}
https://discuss.leetcode.com/topic/82571/verbose-java-solution-hashmap-s

http://www.cnblogs.com/dongling/p/6539600.html
    public List<String> wordsAbbreviation(List<String> dict) {
        Map<String, String>map=new HashMap<>();
        WordMap2Abbreviation(map, 0, dict);
        List<String>result=new ArrayList<>();
        int size=dict.size();
        for(int i=0;i<size;i++){
            result.add(map.get(dict.get(i)));//调整map中Abbreviation的顺序，使result中的Abbreviation与dict中同一位置上的word相对应
        }
        return result;
    }
    
    public String getAbbreviation(String word,int fromIndex){//fromIndex表示从word的第几个字符开始生成缩写词
        int len=word.length();
        if(len-fromIndex<=3){//3个及以下的字符没有缩写的必要
            return word;
        }
        else{
            return word.substring(0, fromIndex+1)+String.valueOf(len-fromIndex-2)+word.charAt(len-1);
        }
    }
    
    public void WordMap2Abbreviation(Map<String, String>map,int fromIndex,List<String>dict){
        Map<String,ArrayList<String>>abbre2Word=new HashMap<>();//以abbreviation做键，value为Abbreviation相同的word组成的ArrayList<String>
        for(String word:dict){
            String abbre=getAbbreviation(word, fromIndex);
            if(abbre2Word.containsKey(abbre)){
                abbre2Word.get(abbre).add(word);
            }
            else{
                ArrayList<String>list=new ArrayList<>();
                list.add(word);
                abbre2Word.put(abbre, list);
            }
        }
        for(String abbre:abbre2Word.keySet()){
            ArrayList<String>words=abbre2Word.get(abbre);
            if(words.size()==1){//说明该Abbreviation是unique的
                map.put(words.get(0), abbre);
            }
            else{
                WordMap2Abbreviation(map, fromIndex+1, words);//对这些Abbreviation相同的word递归调用函数
            }
        }
    }

X. Using Trie
https://www.nowtoshare.com/zh/Article/Index/20484
    public List<String> wordsAbbreviation(List<String> dict) {
        HashMap<Integer,TireTree>[] arr = (HashMap<Integer,TireTree>[])new HashMap[26];
        for(int i=0;i<26;i++)
        {
            arr[i]=new HashMap<Integer,TireTree>();
        }
        for(String word : dict)
        {
            TireTree tt;
            int len=word.length();
            char ch=word.charAt(len-1);
            if(!arr[ch-'a'].containsKey(len))
            {
                tt=new TireTree();
                arr[ch-'a'].put(len,tt);
            }
            else
            {
                tt=arr[ch-'a'].get(len);
            }
            tt.addString(word);
        }
         List<String> ret = new ArrayList<>();
        for(String word : dict)
        {
            if(word.length()<=3) 
            {
                 ret.add(word);
                 continue;
            }
          char[] wArr = word.toCharArray();
          char ch=word.charAt(wArr.length-1);
          TireTree tt=arr[ch-'a'].get(wArr.length);
          StringBuilder sb = new StringBuilder();
          int pos =0;
          while(pos<wArr.length)
          {
              sb.append(wArr[pos++]);
              if(tt.existDupPrefix(word.substring(0,pos))) continue;
              else
              {
                  sb.append(wArr.length-1-pos);
                  sb.append(ch);
                  break;
              }
          }
          if(sb.length()>=word.length())
          ret.add(word);
          else ret.add(sb.toString());
        }
        return ret;
    }
    
    
    
    public class TireNode{
        TireNode[] children;
        boolean isWord;
        int samePrefix;
        public TireNode()
        {
            children = new TireNode[26];
            isWord=false;
            samePrefix=0;
        }
    }
    public class TireTree
    {
        TireNode root;
        public TireTree()
        {
            root = new TireNode();
        }
        
        public void addString(String word)
        {
            TireNode cur =root;
            for(int ch: word.toCharArray())
            {
                if(cur.children[ch-'a']==null)
                {
                    cur.children[ch-'a']=new TireNode();
                }
                cur.samePrefix++;
                cur=cur.children[ch-'a'];
            }
            cur.isWord=true;
        }
        
        public boolean existDupPrefix(String word)
        {
             TireNode cur =root;
            for(int ch: word.toCharArray())
            {
                if(cur.children[ch-'a']==null)
                {
                   return false;
                }
                cur=cur.children[ch-'a'];
            }
            return cur.samePrefix>1;
        }
    }

https://discuss.leetcode.com/topic/82610/hashmap-trie-o-nl-solution
The basic idea is to group all conflicted words, and then resolve the conflicts using Trie. The time complexity will be O(nL) for
 building trie, O(nL) to resolve conflicts, O(n) to group words. So the time complexity will be O(n(2L + 1). n is the number of 
 words, and L is the average length of each words.
    public List<String> wordsAbbreviation(List<String> dict) {
        Map<String, List<Integer>> abbrMap = new HashMap<>();
        // 1) create result set
        List<String> res = new ArrayList<>(Collections.nCopies(dict.size(), null));
        // 2) Group all words with the same shortest abbreviation. For example:
        // "internal", "interval" => grouped by "i6l"
        // "intension", "intrusion" => grouped by "i7n"
        // "god" => grouped by "god"
        // we can notice that only words with the same length and the same start
        // and end letter could be grouped together
        for (int i = 0; i < dict.size(); i ++) {
            String word = dict.get(i);
            String st = getShortestAbbr(word);
            List<Integer> pos = abbrMap.get(st);
            if (pos == null) {
                pos = new ArrayList<>();
                abbrMap.put(st, pos);
            }
            pos.add(i);
        }
        // 3) Resolve conflicts in each group
        for (Map.Entry<String, List<Integer>> entry : abbrMap.entrySet()) {
            String abbr = entry.getKey();
            List<Integer> pos = entry.getValue();
            resolve(dict, res, abbr, pos);
        }
        return res;
    }
    
    /**
     * To resolve conflicts in a group, we could build a trie for all the words
     * in the group. The trie node will contain the count of words that has the
     * same prefix. Then we could use this trie to determine when we could resolve
     * a conflict by identifying that the count of words in that trie node will only
     * have one word has the prefix.
     */
//    private void resolve(List<String> dict, List<String> res, String abbr, List<Integer> pos) {
//        if (pos.size() == 1) {
//            res.set(pos.get(0), abbr);
//        } else {
//            Trie trie = buildTrie(dict, pos);
//            for (int p : pos) {
//                String w = dict.get(p);
//                Trie cur = trie;
//                int i = 0;
//                int n = w.length();
//                // while loop to find the trie node which only has the 1 word which has
//                // the prefix. That means in that position, only current word has that
//                // specific character.
//                while (i < n && cur.next.get(w.charAt(i)).cnt > 1) {
//                    cur = cur.next.get(w.charAt(i));
//                    i ++;
//                }
//                if (i >= n - 3) {
//                    res.set(p, w);
//                } else {
//                    String pre = w.substring(0, i+1);
//                    String st = pre + (n - i - 2) + "" + w.charAt(n - 1);
//                    res.set(p, st);
//                }
//            }
//        }
//    }
//    
//    /**
//     * Get the shortest abbreviation for a word
//     */ 
//    private String getShortestAbbr(String s) {
//        if (s.length() <= 3) {
//            return s;
//        } else {
//            return s.charAt(0) + "" + (s.length() - 2) + "" + s.charAt(s.length() - 1);
//        }
//    }
//    
//    /**
//     * Standard way to build the trie, but we record each trie node with the information
//     * of the count of words with the same prefix.
//     */
//    private Trie buildTrie(List<String> dict, List<Integer> pos) {
//        Trie root = new Trie();
//        for (int p : pos) {
//            String w = dict.get(p);
//            Trie cur = root;
//            for (int i = 0; i < w.length(); i ++) {
//                char c = w.charAt(i);
//                if (cur.next.containsKey(c)) {
//                    cur = cur.next.get(c);
//                } else {
//                    Trie next = new Trie();
//                    cur.next.put(c, next);
//                    cur = next;
//                }
//                cur.cnt ++;
//            }
//        }
//        return root;
//    }
//    
//    private class Trie {
//        int cnt = 0;
//        Map<Character, Trie> next = new HashMap<>();
//    }
//
//X. https://discuss.leetcode.com/topic/82671/just-brute-force
//    public List<String> wordsAbbreviation(List<String> dict) {
//        int n = dict.size();
//        List<String> res = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            int prefix = 1;
//            String abbr = abbrPrefix(dict.get(i), prefix);
//            while (conflict(abbr, dict, i)) {
//                prefix++;
//                abbr = abbrPrefix(dict.get(i), prefix);
//            }
//            res.add(abbr);
//        }
//        return res;
//    }
//    
//    private String abbrPrefix(String s, int prefix) {
//        if (prefix + 2 >= s.length()) {
//            return s;
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(s.substring(0, prefix));
//        sb.append(s.length() - prefix - 1);
//        sb.append(s.charAt(s.length() - 1));
//        return sb.toString();
//    }
//    
//    private boolean conflict(String abbr, List<String> dict, int except) {
//        for (int i = 0; i < dict.size(); i++) {
//            if (i != except && isValidAbbreviation(dict.get(i), abbr)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    private boolean isValidAbbreviation(String word, String abbr) {
//        int i = 0, j = 0;
//        int num = 0;
//        while (i < word.length() && j < abbr.length()) {
//            char a = abbr.charAt(j);
//            if (Character.isDigit(a)) {
//                num = num * 10 + a - '0';
//                j++;
//            } else {
//                i += num;
//                num = 0;
//                if (i >= word.length() || word.charAt(i) != a) {
//                    return false;
//                }
//                i++;
//                j++;
//            }
//        }
//        i += num;
//        return i == word.length() && j == abbr.length();
//    }
// * @author het
// *
// */
public class LeetCode527_Word_Abbreviation {
	public List<String> wordsAbbreviation(List<String> dict) {
        HashMap<Integer,TireTree>[] arr = (HashMap<Integer,TireTree>[])new HashMap[26];
        for(int i=0;i<26;i++)
        {
            arr[i]=new HashMap<Integer,TireTree>();
        }
        for(String word : dict)
        {
            TireTree tt;
            int len=word.length();
            char ch=word.charAt(len-1);
            if(!arr[ch-'a'].containsKey(len))
            {
                tt=new TireTree();
                arr[ch-'a'].put(len,tt);
            }
            else
            {
                tt=arr[ch-'a'].get(len);
            }
            tt.addString(word);
        }
         List<String> ret = new ArrayList<>();
        for(String word : dict)
        {
            if(word.length()<=3) 
            {
                 ret.add(word);
                 continue;
            }
          char[] wArr = word.toCharArray();
          char ch=word.charAt(wArr.length-1);
          TireTree tt=arr[ch-'a'].get(wArr.length);
          StringBuilder sb = new StringBuilder();
          int pos =0;
          while(pos<wArr.length)
          {
              sb.append(wArr[pos++]);
              if(tt.existDupPrefix(word.substring(0,pos))) continue;
              else
              {
                  sb.append(wArr.length-1-pos);
                  sb.append(ch);
                  break;
              }
          }
          if(sb.length()>=word.length())
          ret.add(word);
          else ret.add(sb.toString());
        }
        return ret;
    }
    
    
    
    public class TireNode{
        TireNode[] children;
        boolean isWord;
        int samePrefix;
        public TireNode()
        {
            children = new TireNode[26];
            isWord=false;
            samePrefix=0;
        }
    }
    public class TireTree
    {
        TireNode root;
        public TireTree()
        {
            root = new TireNode();
        }
        
        public void addString(String word)
        {
            TireNode cur =root;
            for(int ch: word.toCharArray())
            {
                if(cur.children[ch-'a']==null)
                {
                    cur.children[ch-'a']=new TireNode();
                }
                cur.samePrefix++;
                cur=cur.children[ch-'a'];
            }
            cur.isWord=true;
        }
        
        public boolean existDupPrefix(String word)
        {
             TireNode cur =root;
            for(int ch: word.toCharArray())
            {
                if(cur.children[ch-'a']==null)
                {
                   return false;
                }
                cur=cur.children[ch-'a'];
            }
            return cur.samePrefix>1;
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
