package alite.leetcode.xx3.extra;
/**
 * LeetCode 394 - Decode String

https://leetcode.com/problems/decode-string/
Given an encoded string, return it's decoded string.
The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that kis guaranteed to be a positive integer.
You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
Examples:
s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".

X.DFS
http://blog.csdn.net/qq508618087/article/details/52439114
思路: 一个DFS的题目, 给定的字符串可能会有嵌套很多层, 在每一层我们只要在碰到正常的字符就保存到当前层的结果中, 如果碰到数字就另外保存起来作为倍数, 碰到'[' 就进入下层递归, 碰到']' 就将当前层结果返回, 这样在返回给上层之后就可以用倍数加入到上层结果字符串中. 最终当所有层都完成之后就可以得到结果. 在不同层次的递归中, 我们可以维护一个共同的位置索引, 这样在下层递归完成之后上层可以知道已经运算到哪里了.
    string DFS(string s, int &k)  
    {  
        string ans;  
        int cnt = 0;  
        while(k < s.size())  
        {  
            if(isdigit(s[k])) cnt = cnt*10 + (s[k++]-'0');  
            else if(s[k]=='[')  
            {  
                string tem = DFS(s, ++k);  
                for(int i = 0; i < cnt; i++) ans += tem;  
                cnt = 0;  
            }  
            else if(s[k]==']')  
            {  
                k++;  
                return ans;  
            }  
            else ans += s[k++];  
        }  
        return ans;  
    }  
      
    string decodeString(string s) {  
        int k = 0;  
        return DFS(s, k);  
    }  
https://discuss.leetcode.com/topic/57318/java-simple-recursive-solution/3
    StringBuilder cur = new StringBuilder();
    int num = 0;
    for (int i = 0; i < s.length(); i++) {
        if (Character.isDigit(s.charAt(i))) {
            num = num * 10 + (s.charAt(i) - '0');
        } else if (s.charAt(i) == '[') {
            //find the matching ]
            int begin = i;
            i++;
            int count = 1; 
            while (count != 0) {
                if (s.charAt(i) == '[') {
                    count++;
                } else if (s.charAt(i) == ']') {
                    count--;
                }
                i++;
            }
            i--;
            
            String substr = decodeString(s.substring(begin + 1, i));
            for (int k = 0; k < num; k++) {
                cur.append(substr);
            }
            num = 0;
        } else { 
            cur.append(s.charAt(i));
        }
    }
    return cur.toString();
X. Iterative
https://all4win78.wordpress.com/2016/09/07/leetcode-394-decode-string/
http://www.guoting.org/leetcode/leetcode-394-decode-string/

    public String decodeString(String s) {

        if (s == null) {

            return null;

        }

         

        Stack<StringBuilder> sbStack = new Stack<>();

        Stack<Integer> intStack = new Stack<>();

        StringBuilder sb = new StringBuilder();

        int repeat = 0;

         

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '[') {

                sbStack.push(sb);

                intStack.push(repeat);

                sb = new StringBuilder();

                repeat = 0;

            } else if (c == ']') {

                StringBuilder temp = sb;

                sb = sbStack.pop();

                repeat = intStack.pop();

                while (repeat > 0) {

                    sb.append(temp);

                    repeat -= 1;

                }

            } else if (c >= '0' && c <= '9') {

                repeat *= 10;

                repeat += c - '0';

            } else {

                sb.append(c);

            }

        }

         

        return sb.toString();

    }

X. Use stack
http://blog.csdn.net/mebiuw/article/details/52448807
http://blog.csdn.net/yeqiuzs/article/details/52435359
http://www.guoting.org/leetcode/leetcode-394-decode-string/
意思是在字符串当中，有一个特殊的格式 — k[S]，遇到这种状况，需要把S重复k次，注意是可以嵌套的
在这次解题当中，我是使用了栈的方式，去解决这个问题。分别使用了一个全局的已解码的字符串Builder，另外对于为解码的，使用栈来暂存。
符号’[‘控制进栈，分别进入计数数字和之前尚未解码的字符串
符号’]’控制出站，出栈当前计数，并且将未解码的字符串进行重复，再链接上一个未解码的字符串
注意栈空的时候证明当前嵌套解码完毕，需要添加到全局当中，反之基于暂存。
    public String decodeString(String s) {
        int n = s.length();
        char[] ss = s.toCharArray();
        Stack<Integer> counts = new Stack<Integer>();
        Stack<String> strings = new Stack<String>();
        StringBuilder result = new StringBuilder();
        int count = 0;
        //当前需要解码，但是还没有遇到]暂存的
        String currentString = "";
        for(int i=0;i<n;i++){
            if(ss[i]>='0' && ss[i]<='9'){
                count = count * 10;
                count = count + ss[i] -'0';
            } else if(ss[i] == '['){
                counts.push(count);
                count = 0;
                strings.push(currentString);
                currentString = "";
            }else if(ss[i] >='a' && ss[i]<='z'){
                //注意栈空与否很重要
                if(!counts.isEmpty())
                    currentString += ss[i];
                else result.append(ss[i]);
            } else if(ss[i] == ']'){
                int times = counts.pop();
                if(counts.isEmpty()){
                    for(int j=0;j<times;j++)
                        result.append(currentString);
                     currentString=strings.pop();
                } else {
                    String tmp = "";
                    for(int j=0;j<times;j++)
                        tmp+=currentString;
                    currentString = strings.pop()+tmp;
                }

            }
        }
        return result.toString();
    }
https://discuss.leetcode.com/topic/57121/share-my-python-stack-simple-solution-easy-to-understand
https://discuss.leetcode.com/topic/57159/simple-java-solution-using-stack/2
-- Use StringBuilder
    public String decodeString(String s) {
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    count = 10 * count + (s.charAt(idx) - '0');
                    idx++;
                }
                countStack.push(count);
            }
            else if (s.charAt(idx) == '[') {
                resStack.push(res);
                res = "";
                idx++;
            }
            else if (s.charAt(idx) == ']') {
                StringBuilder temp = new StringBuilder (resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res);
                }
                res = temp.toString();
                idx++;
            }
            else {
                res += s.charAt(idx++);
            }
        }
        return res;
    }
https://discuss.leetcode.com/topic/57356/java-2-stacks-solution-reference-basic-calculator
public String decodeString(String s) {
        if (s.length() == 0) return "";
        Stack<String> letter = new Stack<>();
        Stack<Integer> times = new Stack<>();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                sb.append(s.charAt(i));
            }
            else if (Character.isDigit(s.charAt(i))) {
                int time = s.charAt(i) - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    time = time * 10 + s.charAt(i + 1) - '0';
                    i++;
                } 
                times.push(time);
                letter.push(sb.toString());
                sb = new StringBuilder();
            }
            else if (s.charAt(i) == ']') {
                sb = constructStringBuidler(sb, letter, times);
            }
        }
        
        return sb.toString();
    }
    
    private StringBuilder constructStringBuidler(StringBuilder sb, Stack<String> letter, Stack<Integer> times) {
        StringBuilder res = new StringBuilder();
        int time = times.pop();
        String prev = letter.pop(), curr = sb.toString();
        res.append(prev);
        
        for (int i = 0; i < time; i++) {
            res.append(sb);
        }
        
        return res;
    }
https://discuss.leetcode.com/topic/57250/java-short-and-easy-understanding-solution-using-stack
    public String decodeString(String s) {
        Stack<Integer> count = new Stack<>();
        Stack<String> result = new Stack<>();
        int i = 0;
        result.push("");
        while (i < s.length()) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int start = i;
                while (s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') i++;
                count.push(Integer.parseInt(s.substring(start, i + 1)));
            } else if (ch == '[') {
                result.push("");//\\??
            } else if (ch == ']') {
                String str = result.pop();
                StringBuilder sb = new StringBuilder();
                int times = count.pop();
                for (int j = 0; j < times; j += 1) {
                    sb.append(str);
                }
                result.push(result.pop() + sb.toString());
            } else {
                result.push(result.pop() + ch);//\\
            }
            i += 1;
        }
        return result.pop();
    }

X. Use Recursion
https://discuss.leetcode.com/topic/57612/java-one-pass-recursive-solution
    int idx;
    public String decodeString(String s) {
        idx = 0;
        return helper(s);
    }
    String helper(String s) {
        StringBuilder ans = new StringBuilder();
        for (int k = 0; idx < s.length(); ++idx) {
            char ch = s.charAt(idx);
            if (ch == '[') {
                ++idx;
                String str = helper(s);
                while (k > 0) {
                    ans.append(str);
                    --k;
                }
            } else if (ch == ']') {
                break;
            } else if (Character.isDigit(ch)) {
                k = k * 10 + ch - '0';
            } else ans.append(ch);
        }
        return ans.toString();
    }
https://discuss.leetcode.com/topic/57527/3ms-simple-java-recursive-solution
The idea is to use DFS to find the solution. Each time we find a '[' we solve it recursively and when we encounter ']' the call is end.
So to make sure that we're gonna encounter '[' and ']' both one time in each recursion, I have do the following thing:
Create a global variable strIndex;
Adjust the input, surround it with '1[' and ']'.
    private int strIndex;
    
    public String decodeString(String s) {
        // Adapt the input to my algorithm
        return dfs("1[" + s + "]").toString(); //no need to add 1[]
    }
    
    private StringBuilder dfs(String s) {
        StringBuilder cur = new StringBuilder();

        for (int k = 0; strIndex < s.length(); ++strIndex) {
            char c = s.charAt(strIndex);
            if (c >= '0' && c <= '9') { // Calculate the number K
                k = k * 10 + c - '0';
            } else if (c == '[') { // Recursive step
                ++strIndex;
                StringBuilder sb = dfs(s);

                for (; k > 0; k--) cur.append(sb);
            } else if (c == ']') { // Exit the loop and return the result.
                break;
            } else {
                cur.append(c);
            }
        }
        
        return cur;
    }
X.
http://xiadong.info/2016/09/leetcode-394-decode-string/
对一个字符串进行解码, 该字符串的编码规则是这样的重复次数[重复内容], 由于有可能出现嵌套, 所以我使用递归来处理这个字符串.
对于括号匹配来说, 使用栈来确定与相应左括号匹配的右括号. 要注意可能会出现不重复的串, 这时要直接把它加到返回串的后面而不处理重复次数.

    string decodeString(string str) {

        s = str;

        return decodeStringImpl(0, s.length());

    }


    string decodeStringImpl(int start, int end) {

        string ret;

        int index = start;

        while(index < end && !isDigit(s[index]) && s[index] != ']'){

            index++;

        }

        ret += s.substr(start, index - start);

        while (index < end) {

            if(!isDigit(s[index])){

                int j = index;

                while(j < end && !isDigit(s[j])) j++;

                ret += s.substr(index, j - index);

                index = j;

            }

            else{

                int leftBracket = getInt(index);

                int repeat = stoi(s.substr(index, leftBracket - index));

                int rightBracket = findRightBracket(leftBracket);

                string s = decodeStringImpl(leftBracket + 1, rightBracket);

                for (int i = 0; i < repeat; i++) {

                    ret += s;

                }

                index = rightBracket + 1;

            }

        }

        return ret;

    }


    int findRightBracket(int index) {

        vector<int> st;

        st.push_back(index);

        int i = index;

        while (!st.empty() && i < s.length() - 1) {

            i++;

            if (s[i] == '[') st.push_back(i);

            else if (s[i] == ']') st.pop_back();

        }

        return i;

    }


    int getInt(int index) {

        while (index < s.length() && isDigit(s[index])) {

            index++;

        }

        return index;

    }

    

    bool isDigit(char ch){

        return ch <= '9' && ch >= '0';

    }
http://www.cnblogs.com/dongling/p/5843795.html
    public String decodeString(String s) {
        if(s==null||s.length()==0)
            return s;
        char ch;
        int index=0;
        int repeat=0;
        StringBuilder head=new StringBuilder(""),body=new StringBuilder("");
        while(index<s.length()&&!Character.isDigit(ch=s.charAt(index))){
            head.append(ch);
            index++;
        }
        if(index<s.length()){
            //indicating that next character is Digit
            while(index<s.length()&&Character.isDigit(ch=s.charAt(index))){
                repeat=repeat*10+ch-'0';
                index++;
            }//got the leading num
            //now index is pointing to '[';
            
            //next, to get the index of ']';
            int rightBracket=index+1;
            int leftBracketNum=1;
            while(leftBracketNum>0){
                ch=s.charAt(rightBracket);
                if(ch==']'){
                    leftBracketNum--;
                }
                else if(ch=='['){
                    leftBracketNum++;
                }
                else{
                    
                }
                rightBracket++;
            }
            rightBracket--;//now rightBracket is pointing to the right position of the ']';
            String res1=decodeString(s.substring(index+1,rightBracket));
            String tail=decodeString(s.substring(rightBracket+1));
            
            for(int i=1;i<=repeat;i++){
                body.append(res1);
            }
            body.append(tail);
        }
        
        return head.toString()+body.toString();
    }

X. Follow up
http://www.1point3acres.com/bbs/thread-211139-1-1.html
第三轮：LC394，剩十分钟follow up是反过来encode，求最短encode（跟我说前面那个是warm up，这才是真题）。
懵逼，给了个贪心+memory的算法（代码非常乱），也不知道能不能得到最优解。没做出来。。。
最后结束的时候她说这题很难很难，让我don't worry，用DP做（分析题目的时候我试了一下，她没有hint所以我没继续下去），
然后我才想到用dp[k][i][j]做应该可以。。。但也没时间写了。。

https://discuss.leetcode.com/topic/68275/can-we-do-the-encode-way-i-post-one-the-return-is-the-shortest-length-of-encoded-string
Suppose we have a string with only lowercase letters and the problem asked us to encode it and return the shortest one.
How can we do that? Does anyone have the idea?
update: I write a solution for the problem. Does anyone have some suggestion?
11/18 update: This solution return the shortest encode string, this means if the string is "aaa", it will return "aaa".
if the string is "aaaaa", it will return "5[a]"
https://discuss.leetcode.com/topic/68511/we-have-394-decode-string-people-were-often-asked-the-shortest-encode-as-a-followup/
Today I saw some people were asked the encoding as a follow up for leetcode #394 decode string. Most of the them only gave a brief direction during the interview and didn't write any code. But I would like to ask for more thoughts.
I may think about using dynamic programming. For string s, the shortest encoding from the ith character to the jth character is denoted by dp[i][j], then we have:
If i+2 > j, dp[i][j] = s[i-1:j ], i.e. just keep those characters.
else If s[i-1:j ] can be represented as repeating patterns, then encode it as repeating_times[repeating_pattern], using the finest repeating pattern.
otherwise dp[i][j] = min_length(dp[i][k] + dp[k+1][j]) where i < k < j
It runs in O(n3) time however, where n is the length of the string to be encoded.
case- aaaabb public static boolean checkRepeating(String s, int l, int r, int start, int end){
  if( (end-start) % (r-l) != 0 ){
   return false;
  }
  int len = r-l;
  String pattern = s.substring(l, r);
  for(int i = start; i +len <= end; i+=len){
   if(!pattern.equals(s.substring(i, i+len))){
    return false;
   }
  }
  return true;
 }

 public static int getLength(int plen, int slen){
  return (int)(Math.log10(slen/plen)+1);
 }

 public static String sortestEncodeString(String s){
  int len = s.length();
  int[][] dp = new int[len+1][len+1];
  for(int i = 1; i <= len; ++i){
   for(int j = 0; j < i; ++j){
    dp[j][i] = i-j;
   }
  }

  Map<String, String> dpMap = new HashMap<>();

  for(int i = 1; i <= len; ++i){
   for(int j = i-1; j >= 0; --j){
    String temp = s.substring(j, i);
    if(dpMap.containsKey(temp)){
     dp[j][i] = dpMap.get(temp).length();
     continue;
    }
    String ans = temp;
    for(int k = j+1; k <= i; ++k){
     String leftStr = s.substring(j, k);
     String rightStr = s.substring(k, i);
     if(dp[j][i] > dp[j][k] + dp[k][i]){
      dp[j][i] = dp[j][k] + dp[k][i];
      ans = dpMap.get(leftStr) + dpMap.get(rightStr);
     }
     if( checkRepeating(s, j, k, k, i) 
      && ( 2 + getLength(k-j, i-j) + dp[j][k] < dp[j][i]) ){
      dp[j][i] = 2 + getLength(k-j, i-j) + dp[j][k];
      ans = String.valueOf((i-j)/(k-j)) +"["+ dpMap.get(leftStr) +"]";
     }
    }
    dpMap.put(temp,ans);
   }
  }
  return dpMap.get(s);
 }
I wrote a similar top-down approach combining KMP.
    def check_repeating(s):
        sz = len(s)
        lps = [0] * sz
        for i in range(1, sz):
            l = lps[i-1]
            while l and s[l] != s[i]:
                l = lps[l-1]
            lps[i] = l + (s[l] == s[i])
        if lps[-1] and sz % (sz-lps[-1]) == 0:
            p = sz - lps[-1]
            return sz/p, s[:p]
        return 1, s

    memo = {}

    def _encode(s):
        if s not in memo:
            if len(s) <= 2:
                return s
            can = s
            for m in range(1, len(s)-1):
                l_cnt, l_str = check_repeating(s[:m])
                r_cnt, r_str = check_repeating(s[m:])
                if l_str == r_str:
                    comb = '{}[{}]'.format(l_cnt+r_cnt, _encode(l_str))
                else:
                    comb = _encode(s[:m])+_encode(s[m:])
                can = min(can, comb, key=len)
            memo[s] = can
        return memo[s]

    return _encode(s)
http://blog.gainlo.co/index.php/2016/09/30/uber-interview-question-delimiter-matching/
Write an algorithm to determine if all of the delimiters in an expression are matched and closed.
For example, “{ac[bb]}”, “[dklf(df(kl))d]{}” and “{[[[]]]}” are matched. But “{3234[fd” and {df][d} are not. The question has been asked by Uber recently and is expected to be solved quickly.
When I was working on this problem for the first time, what I thought first is to keep separate counters for each delimiter and if all of them are 0 after processing the string, all of the delimiters are balanced. However, this doesn’t work. Consider the case “([)]”, we have an equal number of left and right delimiters but they are incorrectly matched.
Therefore, only keeping the number of unmatched delimiters is not enough, we also need to keep track of all the previous delimiters. Since each time when we see a new delimiter, we need to compare this one with the last unmatched delimiter, stack is the data structure we should consider.
Let’s finalize the solution as follows:
Start with an empty stack to store any delimiter we are processing
Go over the string, for each delimiter we find:
If it’s a left parenthesis, push it to the stack
If it’s a right parenthesis, pop the top element of the stack and compare with the right parenthesis. If they are matched, keep processing the string. Otherwise, delimiters are not balanced.
 * @author het
 *
 */
public class LeetCode394 {
    public static String decodeString(String s){
    	 StringBuilder cur = new StringBuilder();
    	    int num = 0;
    	    for (int i = 0; i < s.length(); i++) {
    	        if (Character.isDigit(s.charAt(i))) {
    	            num = num * 10 + (s.charAt(i) - '0');
    	        } else if (s.charAt(i) == '[') {
    	            //find the matching ]
    	            int begin = i;
    	            i++;
    	            int count = 1; 
    	            while (count != 0) {
    	                if (s.charAt(i) == '[') {
    	                    count++;
    	                } else if (s.charAt(i) == ']') {
    	                    count--;
    	                }
    	                i++;
    	            }
    	            i--;
    	            
    	            String substr = decodeString(s.substring(begin + 1, i));
    	            for (int k = 0; k < num; k++) {
    	                cur.append(substr);
    	            }
    	            num = 0;
    	        } else { 
    	            cur.append(s.charAt(i));
    	        }
    	    }
    	    return cur.toString();
    }
//    s = "3[a]2[bc]", return "aaabcbc".
//    		s = "3[a2[c]]", return "accaccacc".
//    		s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(decodeString("2[abc]3[cd]ef"));

	}

}
