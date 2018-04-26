package alite.leetcode.newExtra.L500;
/**
 * LeetCode 522 - Longest Uncommon Subsequence II

https://leetcode.com/problems/longest-uncommon-subsequence-ii
Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon subsequence
 is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of
  the other strings.
A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order
 of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.
The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.
Example 1:
Input: "aba", "cdc", "eae"
Output: 3
Note:
All the given strings' lengths will not exceed 10.
The length of the given list will be in the range of [2, 50].
https://discuss.leetcode.com/topic/85044/python-simple-explanation
When we add a letter Y to our candidate longest uncommon subsequence answer of X, it only makes it strictly harder to find a common subsequence. Thus our candidate longest uncommon subsequences will be chosen from the group of words itself.
Suppose we have some candidate X. We only need to check whether X is not a subsequence of any of the other words Y. To save some time, we could have quickly ruled out Y when len(Y) < len(X), either by adding "if len(w1) > len(w2): return False" or enumerating over A[:i] (and checking neighbors for equality.) However, the problem has such small input constraints that this is not required.
We want the max length of all candidates with the desired property, so we check candidates in descending order of length. When we find a suitable one, we know it must be the best global answer.
def subseq(w1, w2):
    #True iff word1 is a subsequence of word2.
    i = 0
    for c in w2:
        if i < len(w1) and w1[i] == c:
            i += 1
    return i == len(w1)
    
A.sort(key = len, reverse = True)
for i, word1 in enumerate(A):
    if all(not subseq(word1, word2) 
            for j, word2 in enumerate(A) if i != j):
        return len(word1)
return -1

public int findLUSlength(String[] strs) {
        int len = strs.length;
        
        // reverse sorting array with length 
        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        
        for(int i=0; i<len; i++){
            int missMatchCount = strs.length - 1;
            for(int j=0; j<len; j++){
                if(i != j && !isSubSequence(strs[i], strs[j])){
                    missMatchCount --;
                }
            }
            
            // strs[i] is not a sub sequence of any other entry
            if(missMatchCount == 0){
                return strs[i].length();
            }
        }
        
        return -1;
    }
    
    private boolean isSubSequence(String s1, String s2){
        int idx = 0;
        for(char ch : s2.toCharArray()){
            if(idx < s1.length() && ch == s1.charAt(idx)){
                idx++;
            }
        }
        
        return idx == s1.length();
    }

https://discuss.leetcode.com/topic/85027/java-hashing-solution
We simply maintain a map of all subsequence frequencies and get the subsequence with frequency 1 that has longest length.
NOTE: This solution does not take advantage of the fact that the optimal length subsequence (if it exists) is always going to be the length of some string in the array. Thus, the time complexity of this solution is non-optimal. See https://discuss.leetcode.com/topic/85044/python-simple-explanation for optimal solution.
public int findLUSlength(String[] strs) {
    Map<String, Integer> subseqFreq = new HashMap<>();
    for (String s : strs) 
        for (String subSeq : getSubseqs(s))
            subseqFreq.put(subSeq, subseqFreq.getOrDefault(subSeq, 0) + 1);
    int longest = -1;
    for (Map.Entry<String, Integer> entry : subseqFreq.entrySet()) 
        if (entry.getValue() == 1) longest = Math.max(longest, entry.getKey().length());
    return longest;
}

public static Set<String> getSubseqs(String s) {
    Set<String> res = new HashSet<>();
    if (s.length() == 0) {
         res.add("");
         return res;
    }
    Set<String> subRes = getSubseqs(s.substring(1));
    res.addAll(subRes);
    for (String seq : subRes) res.add(s.charAt(0) + seq);
    return res;
}
 * @author het
 *
 */
public class L522 {
	public int findLUSlength(String[] strs) {
        int len = strs.length;
        
        // reverse sorting array with length 
        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        
        for(int i=0; i<len; i++){
            int missMatchCount = strs.length - 1;
            for(int j=0; j<len; j++){
                if(i != j && !isSubSequence(strs[i], strs[j])){
                    missMatchCount --;
                }
            }
            
            // strs[i] is not a sub sequence of any other entry
            if(missMatchCount == 0){
                return strs[i].length();
            }
        }
        
        return -1;
    }
    
    private boolean isSubSequence(String s1, String s2){
        int idx = 0;
        for(char ch : s2.toCharArray()){
            if(idx < s1.length() && ch == s1.charAt(idx)){
                idx++;
            }
        }
        
        return idx == s1.length();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
