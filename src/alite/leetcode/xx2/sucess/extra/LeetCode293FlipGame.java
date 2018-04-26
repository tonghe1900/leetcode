package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.List;

/**
 * [LeetCode] Flip Game - jcliBlogger - 博客园
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.
Write a function to compute all possible states of the string after one valid move.
For example, given s = "++++", after one move, it may become one of the following states:
[    "--++",    "+--+",    "++--"  ]  

If there is no valid move, return an empty list [].
The idea is quite straightforward: just traverse s and each time when we see two consecutive+s, convert them to -s and add the resulting string to the final result moves. But remember to recover the string after that.

    vector<string> generatePossibleNextMoves(string s) {
        vector<string> moves;
        int n = s.length();
        for (int i = 0; i < n - 1; i++) {
            if (s[i] == '+' && s[i + 1] == '+') { 
                s[i] = s[i + 1] = '-';
                moves.push_back(s);
                s[i] = s[i + 1] = '+';
            }
        }
        return moves;
    }
http://www.chenguanghe.com/flip-game/

public List<String> generatePossibleNextMoves(String s) {

        List<String> res = new ArrayList<String>();

        for (int i = 0; i < s.length() - 1; i++) {

            if (s.charAt(i) == '+' && s.charAt(i + 1) == '+') {

                res.add(s.substring(0, i) + "--" + s.substring(i + 2, s.length()));

            }

        }

        return res;

    }
https://leetcode.com/discuss/64248/4-lines-in-java
public List<String> generatePossibleNextMoves(String s) {
    List list = new ArrayList();
    for (int i=-1; (i = s.indexOf("++", i+1)) >= 0; ) //\\
        list.add(s.substring(0, i) + "--" + s.substring(i+2));
    return list;
}
Convert String to char array, - avoid use substring
Doesn't this perform better than substring?
public List<String> generatePossibleNextMoves(String s) {
        List<String> answer = new ArrayList<>();
        char[] arr = s.toCharArray();
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i] == '+' && arr[i+1] == '+'){
                arr[i] = '-'; arr[i+1] = '-';
                answer.add(new String(arr));
                arr[i] = '+'; arr[i+1] = '+';
            }
        }
        return answer;
    }
https://leetcode.com/discuss/64145/ac-simple-o-n-java-solution
public List<String> generatePossibleNextMoves(String s) {
    List<String> res = new ArrayList<String>();
    char chs[] = s.toCharArray(); 
    for (int i = 0; i < s.length() - 1; i++) {
        if (chs[i] == chs[i+1] && chs[i] == '+') {
            chs[i] = chs[i+1] = '-';
            res.add(String.valueOf(chs));               
            chs[i] = chs[i+1] = '+';                
        }
    }       
    return res;
}
https://leetcode.com/discuss/70890/simple-java-8-functional-declarative-solution
I noticed that Java 8 streams are much slower than loops. Streams make the code declarative but are a huge hit to the performance, unfortunately.
public List<String> generatePossibleNextMoves(String s) {
    List<String> res = new ArrayList<>();
    IntStream.range(1,s.length()).forEach(i -> 
        {if (s.charAt(i-1) == '+' && s.charAt(i) == '+') 
        res.add(s.substring(0,i-1) + "--" + s.substring(i+1,s.length()));});
    return res;
}
LIKE CODING: LeetCode [293] Flip Game

    vector<string> generatePossibleNextMoves(string s) {

        int n = s.size(), i = 0;

        vector<string> ret;


        while(i<n-1){

            if(s[i]=='+' && s[i+1]=='+'){

                string ss = s;

                ss[i]='-';

                ss[i+1]='-';

                ret.push_back(ss);

            }

            i++;

        }

        return ret;

    }
Read full article from LIKE CODING: LeetCode [293] Flip Game
 * @author het
 *
 */
public class LeetCode293FlipGame {
	public List<String> generatePossibleNextMoves(String s) {
	    List<String> res = new ArrayList<String>();
	    char chs[] = s.toCharArray(); 
	    for (int i = 0; i < s.length() - 1; i++) {
	        if (chs[i] == chs[i+1] && chs[i] == '+') {
	            chs[i] = chs[i+1] = '-';
	            res.add(String.valueOf(chs));               
	            chs[i] = chs[i+1] = '+';                
	        }
	    }       
	    return res;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
