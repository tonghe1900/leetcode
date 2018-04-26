package alite.leetcode.xx4.select;

import java.util.List;

/**
 * https://leetcode.com/contest/9/problems/valid-word-square/
Given a sequence of words, check whether it forms a valid word square.

A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 ≤ k < max(numRows, numColumns).

Note: 
The number of words given is at least 1 and does not exceed 500. 
Word length will be at least 1 and does not exceed 500. 
Each word contains only lowercase English alphabet a-z.
https://discuss.leetcode.com/topic/63387/java-ac-solution-easy-to-understand
    public boolean validWordSquare(List<String> words) {
        if(words == null || words.size() == 0){
            return true;
        }
        int n = words.size();
        for(int i=0; i<n; i++){
            for(int j=0; j<words.get(i).length(); j++){
                if(j >= n || words.get(j).length() <= i || words.get(j).charAt(i) != words.get(i).charAt(j))
                    return false;
            }
        }
        return true;
    }
https://discuss.leetcode.com/topic/63398/simple-java-solution
 * @author het
 *b a l l
  a r e a
  l e a d
  l a d y
 */
public class LeetCode422ValidWordSquare {
	 public boolean validWordSquare(List<String> words) {
	        if(words == null || words.size() == 0){
	            return true;
	        }
	        int n = words.size();
	        for(int i=0; i<n; i++){
	            for(int j=0; j<words.get(i).length(); j++){
	                if(j >= n || words.get(j).length() <= i || words.get(j).charAt(i) != words.get(i).charAt(j))
	                    return false;
	            }
	        }
	        return true;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
