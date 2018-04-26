package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 320 - Generalized Abbreviation

Generalized Abbreviation – AlgoBox by dietpepsi
Write a function to generate the generalized abbreviations of a word.
Example:
Given "word"
Return the following list (order does not matter):
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]

X. Bit String
For each character they ends up two ways in the abbreviation, they either appear as it is, or they contribute to the number.
Therefore for a word of length n, there will be 2^n combinations.
Each of these combination can be represented by a binary number of n bit.
Since the problem require to return all the abbreviations, n needs to be relatively small, otherwise the return list can be huge. Therefore we assume n < 32 and use 32 bit int to represent the combination. If it is not enough we can use long instead or we can use backtracking to generate whatever length.
The remaining problem is applying the bit mask and generate the abbreviation. In short, we can scan all the bits of the mask and append the character when corresponding bit is 0 and the count of bit 1 before that.

public List<String> generateAbbreviations(String word) {

    List<String> ans = new ArrayList<>();

    for (int i = 0; i < 1 << word.length(); ++i)

        ans.add(abbr(word, i));

    return ans;

}


private String abbr(String word, int x) {

    StringBuilder builder = new StringBuilder();

    int m = 0;

    for (int i = 0; i < word.length(); ++i, x >>= 1) {

        if ((x & 1) == 0) {

            if (m > 0) builder.append(m);

            m = 0;

            builder.append(word.charAt(i));

        }

        m += x & 1;// change to else m++;

    }

    if (m > 0) builder.append(m);//\\

    return builder.toString();

}
https://discuss.leetcode.com/topic/32188/36ms-o-2-n-n-bitmask-with-explanation
https://discuss.leetcode.com/topic/32173/share-java-backtracking-and-bit-manipulation-solution-with-explanation
public class Solution {
    String word;
    private String generate(int mask) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < this.word.length(); ) {
            if ((mask &(1<<i)) == 0) {
                ans.append(this.word.charAt(i));
                i++;
            }
            else {
                int cur = i;
                while (cur < this.word.length() && ((mask & (1 << cur)) > 0)) {
                    cur++;
                }
                ans.append(cur - i);
                i = cur;
            }
        }
        return ans.toString();
    }
    public List<String> generateAbbreviations(String word) {
        List<String> ans = new ArrayList<>();
        this.word = word;
        for (int mask = 0; mask < (1<<this.word.length()); mask++) {
            ans.add(generate(mask));
        }
        return ans;
    }
}

https://leetcode.com/discuss/75568/o-m-n-bit-manipulation-java
https://discuss.leetcode.com/topic/32154/bit-manipulation-solution-java
running time is indeed O(m * n), where m = 2^n... :)
public List<String> generateAbbreviations(String word) {
    List<String> ret = new ArrayList<>();
    int n = word.length();
    for(int mask = 0;mask < (1 << n);mask++) {
        int count = 0;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i <= n;i++) {
            if(((1 << i) & mask) > 0) {
                count++;
            } else {
                if(count != 0) {
                    sb.append(count);
                    count = 0;
                }
                if(i < n) sb.append(word.charAt(i));
            }
        }
        ret.add(sb.toString()); //\\
    }
    return ret;
}
http://codingmelon.com/2015/12/22/generalized-abbreviation-leetcode-320/
Bit mask:

    vector<string> generateAbbreviations(string word) {

        vector<string> sol;

        int len = word.length();

        for (int i = 0; i < (1 << len); i++) {

            int count = 0;

            string one = "";

            for (int j = 0; j < len; j++) {

                if (i & (1 << j)) {

                    count++;

                } else {

                    if (count) {

                        one += to_string(count);

                        count = 0;

                    }

                    one += word[j];

                }

            }

            if (count) {

                one += to_string(count);

            }

            sol.push_back(one);

        }

        return sol;

    }

X. DFS
https://discuss.leetcode.com/topic/32765/java-14ms-beats-100
For each char c[i], either abbreviate it or not.
Abbreviate: count accumulate num of abbreviating chars, but don't append it yet.
Not Abbreviate: append accumulated num as well as current char c[i].
In the end append remaining num.
Using StringBuilder can decrease 36.4% time.
This comes to the pattern I find powerful:
int len = sb.length(); // decision point
... backtracking logic ...
sb.setLength(len);     // reset to decision point
Similarly, check out remove parentheses and add operators.
For fun you can go even faster if you use a char[] sb + an int pos:
sb's size cannot be bigger than word.length()
you don't need setLength because pos is a local variable,
down the stack it has smaller values
append(char) becomes sb[pos++] = c[i];
and you could write your own "append(int)" which is much simpler because 0 < num < 32
(List cannot hold more than 2^31 elements):
 if (num >= 10) sb[pos++] = '0' + num / 10;
 if (num > 0) sb[pos++] = '0' + num % 10;
public List<String> generateAbbreviations(String word) {
    List<String> res = new ArrayList<>();
    DFS(res, new StringBuilder(), word.toCharArray(), 0, 0);
    return res;
}

public void DFS(List<String> res, StringBuilder sb, char[] c, int i, int num) {
    int len = sb.length();  
    if(i == c.length) {
        if(num != 0) sb.append(num);
        res.add(sb.toString());
    } else {
        DFS(res, sb, c, i + 1, num + 1);               // abbr c[i]

        if(num != 0) sb.append(num);                   // not abbr c[i]
        DFS(res, sb.append(c[i]), c, i + 1, 0);        
    }
    sb.setLength(len); 
}
http://www.cnblogs.com/EdwardLiu/p/5092886.html
这道题肯定是DFS/Backtracking, 但是怎么DFS不好想，跟Leetcode: Remove Invalid Parentheses的backtracking很像。
Generalized Abbreviation这道题是当前这个字母要不要abbreviate，要或者不要两种选择，Parentheses那道题是当前括号要不要keep在StringBuffer里，要或不要同样是两种选择。
 Syntax：注意27行使用StringBuffer.setLength(), 因为count一直累加可能变成两位数三位数，delete stringbuffer最后一个字母可能不行，所以干脆设置为最初进recursion的长度
参考了：https://leetcode.com/discuss/76783/easiest-14ms-java-solution-beats-100%25
 2     public List<String> generateAbbreviations(String word) {
 3         List<String> res = new ArrayList<String>();
 4         dfs(0, word.toCharArray(), new StringBuffer(), 0, res);
 5         return res;
 6     }
 7     
 8     public void dfs(int pos, char[] word, StringBuffer sb, int count, List<String> res) {
 9         int len = word.length;
10         int sbOriginSize = sb.length();
11         if (pos == len) {
12             if (count > 0) {
13                 sb.append(count);
14             }
15             res.add(sb.toString());
16         }
17         else {
18             //choose to abbr word[pos]
19             dfs(pos+1, word, sb, count+1, res);
20             
21             //choose not to abbr word[pos]
22             //first append previous count to sb if count>0
23             if (count > 0) sb.append(count);
24             sb.append(word[pos]);
25             dfs(pos+1, word, sb, 0, res);
26         }
27         sb.setLength(sbOriginSize);
28     }
https://leetcode.com/discuss/76783/easiest-14ms-java-solution-beats-100%25
For each char c[i], either abbreviate it or not.
Abbreviate: count accumulate num of abbreviating chars, but don't append it yet.
Not Abbreviate: append accumulated num as well as current char c[i].
In the end append remaining num.
Using StringBuilder can decrease 36.4% time.
This comes to the pattern I find powerful:
int len = sb.length(); // decision point
... backtracking logic ...
sb.setLength(len);     // reset to decision point
Similarly, check out remove parentheses and add operators.
public List<String> generateAbbreviations(String word) {
    List<String> res = new ArrayList<>();
    DFS(res, new StringBuilder(), word.toCharArray(), 0, 0);
    return res;
}
public void DFS(List<String> res, StringBuilder sb, char[] c, int i, int num) {
    int len = sb.length();  
    if(i == c.length) {
        if(num != 0) sb.append(num);
        res.add(sb.toString());
    } else {
        DFS(res, sb, c, i + 1, num + 1);               // abbr c[i]

        if(num != 0) sb.append(num);                   // not abbr c[i]
        DFS(res, sb.append(c[i]), c, i + 1, 0);        
    }
    sb.setLength(len); 
}
For fun you can go even faster if you use a char[] sb + an int pos:
sb's size cannot be bigger than word.length()
you don't need setLength because pos is a local variable,
down the stack it has smaller values
append(char) becomes sb[pos++] = c[i];
and you could write your own "append(int)" which is much simpler because 0 < num < 32
(List cannot hold more than 2^31 elements):
if (num >= 10) sb[pos++] = '0' + num / 10;
if (num > 0) sb[pos++] = '0' + num % 10;
@TWiStErRob, very clever solution! It looks like faster than mine. It's convenient since 0 < num < 32 or0 < num < 64.
https://leetcode.com/discuss/75443/straight-forward-methods-with-backtracking-divide-conquer
        public List<String> generateAbbreviations(String word) {
            List<String> res = new ArrayList<String>();
            helper(word, 0, "", res, false);
            return res;
        }
        // isAbbrPrev: false, we can add alphabet or abbreviation(number) next round
        // isAbbrPrev: true,  we can add alphabet ONLY next round
        public void helper(String word, int start, String cur, List<String> res, 
                boolean isAbbrPrev) {
            if (start == word.length()) {
                res.add(cur);
                return;
            }
            if (isAbbrPrev == false) { // we can add abbreviation (num)
                for(int end=start+1; end<=word.length(); end++) { 
             // iterate all abbreviations from 'start'
                    helper(word, end, cur + Integer.toString(end-start), res, true);
                }
            }
            helper(word, start+1, cur + word.charAt(start), res, false); 
            // adding one word each time
        }
https://leetcode.com/discuss/75754/java-backtracking-solution
The idea is: for every character, we can keep it or abbreviate it. To keep it, we add it to the current solution and carry on backtracking. To abbreviate it, we omit it in the current solution, but increment the count, which indicates how many characters have we abbreviated. When we reach the end or need to put a character in the current solution, and count is bigger than zero, we add the number into the solution.
This is definitely O(2^n). It is easy to explain. Every char has two possibilities, either abbreviate it or omit it. So there are total 2^n possibilities.
  public List<String> generateAbbreviations(String word){
        List<String> ret = new ArrayList<String>();
        backtrack(ret, word, 0, "", 0);

        return ret;
    }

    private void backtrack(List<String> ret, String word, int pos, String cur, int count){
        if(pos==word.length()){
            if(count > 0) cur += count;
            ret.add(cur);
        }
        else{
            backtrack(ret, word, pos + 1, cur, count + 1);
            backtrack(ret, word, pos+1, cur + (count>0 ? count : "") + word.charAt(pos), 0);
        }
    }

https://discuss.leetcode.com/topic/32270/java-backtracking-solution/11
I made two improvements in your code:
(1) use StringBuilder rather than the concatenation of String.
(2) use char[] to represent String rather than the original String.
these two improvements make run time from 17ms to 14ms.
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        StringBuilder tmpRes = new StringBuilder();
        char[] wordArray = word.toCharArray();
        dfs(res, tmpRes, wordArray, 0, 0);
        return res;
    }
    
    private void dfs(List<String> res, StringBuilder tmpRes, char[] wordArray, int pos, int numCount) {
        if(pos == wordArray.length) {
            if(numCount > 0) tmpRes.append(numCount);
            res.add(tmpRes.toString());
            return;
        }
        
        // use number
        int len = tmpRes.length();
        dfs(res, tmpRes, wordArray, pos + 1, numCount + 1);
        tmpRes.setLength(len);              // backtracking
        
        // use character
        len = tmpRes.length();
        if(numCount > 0) {
            tmpRes.append(numCount).append(wordArray[pos]);
            dfs(res, tmpRes, wordArray, pos + 1, 0);    
        } else {
            tmpRes.append(wordArray[pos]);
            dfs(res, tmpRes, wordArray, pos + 1, 0);
        }
        tmpRes.setLength(len);              // backtracking
    }
https://leetcode.com/discuss/75528/9-line-easy-java-solution
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        int len = word.length();
        res.add(len==0 ? "" : String.valueOf(len));
        for(int i = 0 ; i < len ; i++)
            for(String right : generateAbbreviations(word.substring(i+1))){
                String leftNum = i > 0 ? String.valueOf(i) : "";
                res.add( leftNum + word.substring(i,i + 1) + right );
            }
        return res;
    }

X. DFS 2
https://discuss.leetcode.com/topic/32171/9-line-easy-java-solution
Would you mind telling me why my memorization code below doesn't speed up the program? - the cache is never used.
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        int len = word.length();
        res.add(len==0 ? "" : String.valueOf(len));
        for(int i = 0 ; i < len ; i++)
            for(String right : generateAbbreviations(word.substring(i+1))){
                String leftNum = i > 0 ? String.valueOf(i) : "";
                res.add( leftNum + word.substring(i,i + 1) + right );
            }
        return res;
    }

X. Divide &Conquer:
    public List<String> generateAbbreviations(String word) {
        Set<String> s = helper(word);
        List<String> res = new ArrayList<String>(s);
        return res;
    }
    public Set<String> helper(String word) {
        int length = word.length();
        Set<String> res = new HashSet<String>();
        if (length == 0) {
            res.add("");
            return res;
        }
        res.add(Integer.toString(length));
        for(int i=0; i<length; i++) {    
            // we separate String into two parts with word.charAt(i)
            Set<String> left = helper(word.substring(0,i));
            Set<String> right = helper(word.substring(i+1, length));
            for(String strLeft : left) {
                for(String strRight : right) {
                    res.add(strLeft + word.charAt(i) + strRight);
                }
            }
        }
        return res;
    }
Your D&C solution seems to have LTE problem for test case "segmentation". One way to possibly shorten the time is to use subfix only instead of both prefix and subfix. See my code here: https://leetcode.com/discuss/76010/straightforward-recursive-solution
The idea is kind of straightforward: for a given string, think of all possible first substrings that are converted to an integer. For example, for
word
we have the following possibilities:
w --> 1 (rem: ord)
wo --> 2 (rem: rd)
wor --> 3 (rem: d)
word --> 4 (rem: null)
For each case, the remainder (subfix) is going through exactly the same procedure as word, which is obviously a recursion.

https://leetcode.com/discuss/75528/9-line-easy-java-solution
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        int len = word.length();
        res.add(len==0 ? "" : String.valueOf(len));
        for(int i = 0 ; i < len ; i++)
            for(String right : generateAbbreviations(word.substring(i+1))){
                String leftNum = i > 0 ? String.valueOf(i) : "";
                res.add( leftNum + word.substring(i,i + 1) + right );
            }
        return res;
    }
https://segmentfault.com/a/1190000004187690
首先要考虑的是会有多少种结果。仔细观察会发现，最终会有Cn0 + Cn1 + Cn2 + ... + Cnn = 2^n种结果。
然后就很显然应该用DFS, 每次recursion存下当前结果，然后继续DFS。要注意下一步DFS的起始位置要与当前结束位置隔一个，否则就会出现有连续数字的结果。
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        dfs(res, "", 0, word);
        return res;
    }
    
    public void dfs(List<String> res, String curr, int start, String s) {
        
        // 表示数字替换已经越界，recursion终止 
        if (start > s.length()) 
            return;
            
        // 用之前结束位置存入当前符合条件的结果
        res.add(curr + s.substring(start));
        
        // 定义新的起始位置
        int i = 0;
        
        // 除了最开始，起始位置都要与之前结尾位置隔一个
        if (start > 0) {
            i = start + 1;
        }
        
        for (; i < s.length(); i++) {
            for (int j = 1; j <= s.length(); j++) {
                // 数字之前的字符串要append上
                dfs(res, curr + s.substring(start, i) + j, i + j, s);
            }
        }
    }
https://segmentfault.com/a/1190000004187690

    public List<String> generateAbbreviations(String word) {

        List<String> list = new LinkedList<String>();

        list.add("");

        for(int i =0;i<word.length();i++){

            char ch = word.charAt(i);

            List<String> newList = new LinkedList<String>();

            for(String tmp : list){

                if(tmp.length() == 0){

                    newList.add(tmp+"1");

                }else{ // extract the last number in ,note that there might be no last digit

                    int val = 0;

                    int endIndex = tmp.length()-1;

                    while(endIndex >=0 && isDigit(tmp.charAt(endIndex))){

                        endIndex--;

                    }

                    if(endIndex!= tmp.length()-1){

                        val = Integer.parseInt(tmp.substring(endIndex+1));

                    }

                    newList.add(tmp.substring(0,endIndex+1)+ (val+1));

                }

                newList.add(tmp+ch);

            }

            list = newList;

        }

        return list;

    }

    private boolean isDigit(char ch){

        return ch>='0' && ch <= '9';

    }

Read full article from Generalized Abbreviation – AlgoBox by dietpepsi
 * @author het
 *
 */
public class LeetCode320 {
	
	// word
	
	public static List<String> allAbres(String input){
		List<String> result = new ArrayList<>();
		if(input== null || input.length() == 0) return result;
		allAbresHelper(result, input, 0, 0, new StringBuilder());
		return result;
	}
private static void allAbresHelper(List<String> result, String input, int currentIndex,
			int num, StringBuilder sb) {
		if(currentIndex == input.length()){
			if(num != 0){
				result.add(sb.toString()+num  );
			}else{
				result.add(sb.toString());
			}
			return;
		}
		allAbresHelper(result, input, currentIndex+1, num+1, sb);
		int length = sb.length();
		if(num !=0){
		sb.append(num);
		}
		allAbresHelper(result, input, currentIndex+1, 0, sb.append(input.charAt(currentIndex)));
		sb.setLength(length);
		
		
		
	}
		//	https://discuss.leetcode.com/topic/32765/java-14ms-beats-100
//		For each char c[i], either abbreviate it or not.
//		Abbreviate: count accumulate num of abbreviating chars, but don't append it yet.
//		Not Abbreviate: append accumulated num as well as current char c[i].
//		In the end append remaining num.
//		Using StringBuilder can decrease 36.4% time.
//		This comes to the pattern I find powerful:
//		int len = sb.length(); // decision point
//		... backtracking logic ...
//		sb.setLength(len);     // reset to decision point
//		Similarly, check out remove parentheses and add operators.
//		For fun you can go even faster if you use a char[] sb + an int pos:
//		sb's size cannot be bigger than word.length()
//		you don't need setLength because pos is a local variable,
//		down the stack it has smaller values
//		append(char) becomes sb[pos++] = c[i];
//		and you could write your own "append(int)" which is much simpler because 0 < num < 32
//		(List cannot hold more than 2^31 elements):
//		 if (num >= 10) sb[pos++] = '0' + num / 10;
//		 if (num > 0) sb[pos++] = '0' + num % 10;
		public static List<String> generateAbbreviations(String word) {
		    List<String> res = new ArrayList<>();
		    DFS(res, new StringBuilder(), word.toCharArray(), 0, 0);
		    return res;
		}
        //use StringBuilder's setLength method to remove/revoke the previous update
		public static void DFS(List<String> res, StringBuilder sb, char[] c, int i, int num) {
		    int len = sb.length();  
		    if(i == c.length) {
		        if(num != 0) sb.append(num);
		        res.add(sb.toString());
		    } else {
		        DFS(res, sb, c, i + 1, num + 1);               // abbr c[i]

		        if(num != 0) sb.append(num);                   // not abbr c[i]
		        DFS(res, sb.append(c[i]), c, i + 1, 0);        
		    }
		    sb.setLength(len); 
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//word
		System.out.println(allAbres("word"));

	}

}
