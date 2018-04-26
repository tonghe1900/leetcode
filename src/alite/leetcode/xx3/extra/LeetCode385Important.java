package alite.leetcode.xx3.extra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 385 - Mini Parser

https://www.hrwhisper.me/leetcode-mini-parser/
Given a nested list of integers represented as a string, implement a parser to deserialize it.
Each element is either an integer, or a list -- whose elements may also be integers or other lists.
Note: You may assume that the string is well-formed:
String is non-empty.
String does not contain white spaces.
String contains only digits 0-9, [, - ,, ].
Example 1:
Given s = "324",

You should return a NestedInteger object which contains a single integer 324.
Example 2:

Given s = "[123,[456,[789]]]",

Return a NestedInteger object containing a nested list with 2 elements:

1. An integer containing value 123.
2. A nested list containing two elements:
    i.  An integer containing value 456.
    ii. A nested list with one element:
         a. An integer containing value 789.
X. Using Stack
http://www.programcreek.com/2014/08/leetcode-mini-parser-java/
http://blog.csdn.net/yeqiuzs/article/details/52208388
public NestedInteger deserialize(String s) {
 
    Stack<NestedInteger> stack = new Stack<NestedInteger>();
    String temp = "";
 
    for(char c: s.toCharArray()){
        switch(c){
            case '[':
                stack.push(new NestedInteger()); //start a new NI
                break;
 
            case ']':
                if(!temp.equals("")){
                    stack.peek().add(new NestedInteger(Integer.parseInt(temp))); //add NI to parent
                    temp="";
                }
 
                NestedInteger top = stack.pop();
                if(!stack.empty()){
                    stack.peek().add(top);
                }else{
                    return top;
                }
 
                break;
 
            case ',':
                if(!temp.equals("")){
                    stack.peek().add(new NestedInteger(Integer.parseInt(temp)));//add NI to parent
                    temp="";
                }
 
                break;
 
            default:
                temp += c;
        }
    }
 
    if(!temp.equals("")){
        return new NestedInteger(Integer.parseInt(temp));
    }
 
    return null;
}

由于没有空格，所以第一个字母不是'[‘的说明只有一个数字。直接返回NestedInteger(int(s))
接下来，用一个栈来维护，对于左括号[的，当前的NestedInteger 进栈，对于右括号，当前数字放入NestedInteger 栈不为空则把栈顶的NestedInteger添加当前的NestedInteger，并且出栈。
http://bookshadow.com/weblog/2016/08/15/leetcode-mini-parser/
http://wisim.me/leetcode/2016/08/18/LeetCode_MinParser.html

Use StringBuilder
http://www.jianshu.com/p/4578be7c2270
    public NestedInteger deserialize(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        if (s.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(s));
        }

        Stack<NestedInteger> st = new Stack<NestedInteger>();
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            switch (curr) {
                case '[':
                    st.push(new NestedInteger());
                    break;
                case ']':
                    if (temp.length() != 0) {
                        st.peek().add(new NestedInteger(Integer.parseInt(temp)));
                        temp = "";
                    }               
                    if (st.size() > 1) {
                        NestedInteger node = st.pop();
                        st.peek().add(node);
                    }
                    break;
                case ',':
                    if (temp.length() != 0) {
                        st.peek().add(new NestedInteger(Integer.parseInt(temp)));
                        temp = "";
                    }
                    break;
                default:
                    temp = temp + curr;
                    break;
            }
        }

        return st.pop();
    }
http://buttercola.blogspot.com/2015/11/airbnb-mini-parser.html
http://yuanhsh.iteye.com/blog/2230314

  private static class NestedIntList {

    private int value;

    private boolean isNumber;

    private List<NestedIntList> intList;

     

    // Constructor to construct a number 

    public NestedIntList(int value) {

      this.value = value;

      isNumber = true;

    }

     

    // Constructor to construct a list

    public NestedIntList() {

      intList = new ArrayList<>();

      isNumber = false;

    }

     

    public void add(NestedIntList num) {

      this.intList.add(num);

    }

     

    public NestedIntList miniParser(String s) {

      if (s == null || s.length() == 0) {

        return null;

      }

       

      // Corner case "123"

      if (s.charAt(0) != '[') {

        int num = Integer.parseInt(s);

        return new NestedIntList(num);

      }

       

      int i = 0;

      int left = 1;

      Stack<NestedIntList> stack = new Stack<>();

      NestedIntList result = null;

       

      while (i < s.length()) {

        char c = s.charAt(i);

        if (c == '[') {

          NestedIntList num = new NestedIntList();

          if (!stack.isEmpty()) {

            stack.peek().add(num);

          }

          stack.push(num);

          left = i + 1;

        } else if (c == ',' || c == ']') {

          if (left != i) {

            int value = Integer.parseInt(s.substring(left, i));

            NestedIntList num = new NestedIntList(value);

            stack.peek().add(num);

          }

          left = i + 1;

           

          if (c == ']') {

            result = stack.pop();

          }

        }

         

        i++;

      }

       

      return result;

    }

     

    public String toString() {

      if (this.isNumber) {

        return this.value + "";

      } else {

        return this.intList.toString();

      }

    }

  }

http://blog.csdn.net/yeqiuzs/article/details/52208388
用栈维护一个包含关系，类似于用栈维护带 '(' 的表达式。
思路不难想到，有个坑爹的细节要注意：添加一个数到list type的nestedInteger时 要将该数封装为integer type的NestedInteger，然后用add方法添加该nestedInteger，不能直接用setInteger，否则会把list type的nestedInteger定义为一个integer type，会出错。
还可以用递归来做，思路大致是：没处理完一个token，遇到 [  递归处理 返回该层list结尾下标。好像有点麻烦。。留坑日后待填。
public NestedInteger deserialize(String s) {  
    Stack<NestedInteger> stack = new Stack<NestedInteger>();  
    String tokenNum = "";  
  
    for (char c : s.toCharArray()) {  
        switch (c) {  
        case '['://[代表一个list  
            stack.push(new NestedInteger());  
            break;  
        case ']'://list结尾  
            if (!tokenNum.equals(""))//前面token为数字   
                stack.peek().add(new NestedInteger(Integer.parseInt(tokenNum)));//将数字加入到本层list中  
            NestedInteger ni = stack.pop();//本层list结束  
            tokenNum = "";  
            if (!stack.isEmpty()) {//栈内有更高层次的list  
                stack.peek().add(ni);  
            } else {//栈为空，遍历到字符串结尾  
                return ni;  
            }  
            break;  
        case ',':  
            if (!tokenNum.equals("")) //将数字加入到本层list中  
                stack.peek().add(new NestedInteger(Integer.parseInt(tokenNum)));  
            tokenNum = "";  
            break;  
        default:  
            tokenNum += c;  
        }  
    }  
    if (!tokenNum.equals(""))//特殊case: 如果字符串只包含数字的情况  
        return new NestedInteger(Integer.parseInt(tokenNum));  
    return null;  
}  

我们也可以使用迭代的方法来做，这样就需要使用栈来辅助，变量start记录起始位置，我们遍历字符串，如果遇到'['，我们给栈中加上一个空的NestInteger，如果遇到的字符数逗号或者']'，如果i>start，那么我们给栈顶元素调用add来新加一个NestInteger，初始化参数传入start到i之间的子字符串转为的整数，然后更新start=i+1，当遇到的']'时，如果此时栈中元素多于1个，那么我们将栈顶元素取出，加入新的栈顶元素中通过调用add函数
    NestedInteger deserialize(string s) {
        if (s.empty()) return NestedInteger();
        if (s[0] != '[') return NestedInteger(stoi(s));
        stack<NestedInteger> st;
        int start = 1;
        for (int i = 0; i < s.size(); ++i) {
            if (s[i] == '[') {
                st.push(NestedInteger());
                start = i + 1;
            } else if (s[i] == ',' || s[i] == ']') {
                if (i > start) {
                    st.top().add(NestedInteger(stoi(s.substr(start, i - start))));
                }
                start = i + 1;
                if (s[i] == ']') {
                    if (st.size() > 1) {
                        NestedInteger t = st.top(); st.pop();
                        st.top().add(t);
                    }
                }
            }
        }
        return st.top();
    }
X. Recusive Version
https://discuss.leetcode.com/topic/54407/java-recursive-solution-one-scan-clean-easy-to-understand
if '[', construct a new NestedInteger;
if ']', add the number before ']' if it exists, the character before ']' must be a number or ']';
if ',', add the number before ',' if it exists, the character before ',' must be a number or ']';
if a number, just add the right pointer.
    public NestedInteger deserialize(String s) {
        if (s.charAt(0) != '[') return new NestedInteger(Integer.parseInt(s));
        NestedInteger result = new NestedInteger();
        helper(s, 1, result);
        return result;
    }

    private int helper(String s, int idx, NestedInteger parent) {
        int l = idx, r = idx;
        while (r < s.length()) {
            String num = s.substring(l, r);//don't do here
            if (s.charAt(r) == '[') {
                NestedInteger child = new NestedInteger();
                parent.add(child);
                r = helper(s, r + 1, child);
                l = r;
            } else if (s.charAt(r) == ']') { // get num here
                if (!num.isEmpty()) parent.add(new NestedInteger(Integer.valueOf(num)));
                return r + 1;
            } else if (s.charAt(r) == ',') {//get num here
                if (!num.isEmpty()) parent.add(new NestedInteger(Integer.valueOf(num)));
                r++;
                l = r;
            } else {
                r++;
            }
        }
        return s.length();
    }
http://www.cnblogs.com/grandyang/p/5771434.html
我们可以先用递归来做，思路是，首先判断s是否为空，为空直接返回，不为空的话看首字符是否为'['，是的话说明s为一个整数，我们直接返回结果。如果首字符是'['，且s长度小于等于2，说明没有内容，直接返回结果。反之如果s长度大于2，我们从i=1开始遍历，我们需要一个变量start来记录某一层的其实位置，用cnt来记录跟其实位置是否为同一深度，cnt=0表示同一深度，由于中间每段都是由逗号隔开，所以当我们判断当cnt为0，且当前字符是逗号或者已经到字符串末尾了，我们把start到当前位置之间的字符串取出来递归调用函数，把返回结果加入res中，然后start更新为i+1。如果遇到'['，计数器cnt自增1，若遇到']'，计数器cnt自减1
    NestedInteger deserialize(string s) {
        if (s.empty()) return NestedInteger();
        if (s[0] != '[') return NestedInteger(stoi(s));
        if (s.size() <= 2) return NestedInteger();
        NestedInteger res;
        int start = 1, cnt = 0;
        for (int i = 1; i < s.size(); ++i) {
            if (cnt == 0 && (s[i] == ',' || i == s.size() - 1)) {
                res.add(deserialize(s.substr(start, i - start)));
                start = i + 1;
            } else if (s[i] == '[') ++cnt;
            else if (s[i] == ']') --cnt;
        }
        return res;
    }

X. Recursive version 2
https://discuss.leetcode.com/topic/59029/short-and-clean-java-recursive-solution-with-explanation
Using the "lvl" variable to track if we are inside an inner integer.
Using lIndex to track the leftmost start position.
Every time the program hit the "[" increase lvl, and decrease lvl when hit "]"
When the program meets ","
If lvl != 0, ignore the "," since we are inside a nested integer
else do recursive call ,add the result to the current list and move lIndex.
[ [abc, [xy]] , def, [qqq] ]
ni.add(myDeserialize("[abc, [xy]]"));
ni.add(myDeserialize("def");
ni.add(myDeserialize("[qqq]");
public NestedInteger deserialize(String s) {
    if (s.length() == 0)    return new NestedInteger();
    return myDeserialize(s, 0, s.length()-1);
}

private NestedInteger myDeserialize(String s, int start, int end) {
    if (s.charAt(start) != '[') 
        return new NestedInteger(Integer.valueOf(s.substring(start, end+1)));

    NestedInteger ni = new NestedInteger();
    int lvl = 0, lIndex = start+1;

    for (int i=start+1 ; i<=end-1 ; ++i) {
        char ch = s.charAt(i);
        if (ch == '[')  ++lvl;
        else if (ch == ']') --lvl; 
        else if (ch == ',' && lvl == 0) {
            ni.add(myDeserialize(s, lIndex, i-1));
            lIndex = i + 1;
        }
    }
    if (lIndex <= end-1) {
        ni.add(myDeserialize(s, lIndex, end-1));
    }
    return ni;        
}
https://discuss.leetcode.com/topic/54277/short-java-recursive-solution
Just for people who try this approach instead of stack. This takes much more time than stack as the depth of elements grow. If you would like to reduce time complexity, use stack as a space trade-off for time.
    public NestedInteger deserialize(String s) {
        NestedInteger ret = new NestedInteger();
        if (s == null || s.length() == 0) return ret;
        if (s.charAt(0) != '[') {
            ret.setInteger(Integer.parseInt(s));
        }
        else if (s.length() > 2) {
            int start = 1, count = 0;
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (count == 0 && (c == ',' || i == s.length() - 1)) {
                    ret.add(deserialize(s.substring(start, i)));
                    start = i + 1;
                }
                else if (c == '[') count++;
                else if (c == ']') count--;
            }
        }
        return ret;
    }
https://discuss.leetcode.com/topic/55872/very-short-recursive-solution
    NestedInteger parse(string& s, int& i) {
      if(s[i]=='[') {
          ++i;
          NestedInteger list;
          while(s[i] != ']') {
              list.add(parse(s,i));
              if(s[i] ==',') ++i;
          }
          ++i;
          return list;
      } else {                       
        int sum = 0;
        int sign=1;
        if(s[i] == '-'){ sign = -1; ++i;}
        while(isdigit(s[i])) { sum *= 10; sum+= s[i]-'0'; ++i;}
          return NestedInteger(sum*sign);
      }
    }
    NestedInteger deserialize(string s) {
        int i = 0;
        return parse(s, i);
    }
https://discuss.leetcode.com/topic/54274/java-10-ms-while-loop-recursion-one-scan

https://discuss.leetcode.com/topic/54268/straightforward-java-solution-with-explanation-and-a-simple-implementation-of-nestedinteger-for-your-ease-of-testing
class NestedInteger {
    private List<NestedInteger> list;
    private Integer integer;
    
    public NestedInteger(List<NestedInteger> list){
        this.list = list;
    }
    
    public void add(NestedInteger nestedInteger) {
        if(this.list != null){
            this.list.add(nestedInteger);
        } else {
            this.list = new ArrayList();
            this.list.add(nestedInteger);
        }
    }

    public void setInteger(int num) {
        this.integer = num;
    }

    public NestedInteger(Integer integer){
        this.integer = integer;
    }

    public NestedInteger() {
        this.list = new ArrayList();
    }

    public boolean isInteger() {
        return integer != null;
    }

    public Integer getInteger() {
        return integer;
    }

    public List<NestedInteger> getList() {
        return list;
    }
    
    public String printNi(NestedInteger thisNi, StringBuilder sb){
        if(thisNi.isInteger()) {
            sb.append(thisNi.integer);
            sb.append(",");
        }
        sb.append("[");
        for(NestedInteger ni : thisNi.list){
            if(ni.isInteger()) {
                sb.append(ni.integer);
                sb.append(",");
            }
            else {
                printNi(ni, sb);
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
 * @author het
 *
 */
public class LeetCode385Important {
//	我们也可以使用迭代的方法来做，这样就需要使用栈来辅助，变量start记录起始位置，我们遍历字符串，如果遇到'['，我们给栈中加上一个空的NestInteger，如果遇到的字符数逗号或者']'，如果i>start，那么我们给栈顶元素调用add来新加一个NestInteger，初始化参数传入start到i之间的子字符串转为的整数，然后更新start=i+1，当遇到的']'时，如果此时栈中元素多于1个，那么我们将栈顶元素取出，加入新的栈顶元素中通过调用add函数
//		    NestedInteger deserialize(string s) {
//		        if (s.empty()) return NestedInteger();
//		        if (s[0] != '[') return NestedInteger(stoi(s));
//		        stack<NestedInteger> st;
//		        int start = 1;
//		        for (int i = 0; i < s.size(); ++i) {
//		            if (s[i] == '[') {
//		                st.push(NestedInteger());
//		                start = i + 1;
//		            } else if (s[i] == ',' || s[i] == ']') {
//		                if (i > start) {
//		                    st.top().add(NestedInteger(stoi(s.substr(start, i - start))));
//		                }
//		                start = i + 1;
//		                if (s[i] == ']') {
//		                    if (st.size() > 1) {
//		                        NestedInteger t = st.top(); st.pop();
//		                        st.top().add(t);
//		                    }
//		                }
//		            }
//		        }
//		        return st.top();
//		    }
//		X. Recusive Version
//		https://discuss.leetcode.com/topic/54407/java-recursive-solution-one-scan-clean-easy-to-understand
//		if '[', construct a new NestedInteger;
//		if ']', add the number before ']' if it exists, the character before ']' must be a number or ']';
//		if ',', add the number before ',' if it exists, the character before ',' must be a number or ']';
//		if a number, just add the right pointer.
//		    public NestedInteger deserialize(String s) {
//		        if (s.charAt(0) != '[') return new NestedInteger(Integer.parseInt(s));
//		        NestedInteger result = new NestedInteger();
//		        helper(s, 1, result);
//		        return result;
//		    }
//
//		    private int helper(String s, int idx, NestedInteger parent) {
//		        int l = idx, r = idx;
//		        while (r < s.length()) {
//		            String num = s.substring(l, r);//don't do here
//		            if (s.charAt(r) == '[') {
//		                NestedInteger child = new NestedInteger();
//		                parent.add(child);
//		                r = helper(s, r + 1, child);
//		                l = r;
//		            } else if (s.charAt(r) == ']') { // get num here
//		                if (!num.isEmpty()) parent.add(new NestedInteger(Integer.valueOf(num)));
//		                return r + 1;
//		            } else if (s.charAt(r) == ',') {//get num here
//		                if (!num.isEmpty()) parent.add(new NestedInteger(Integer.valueOf(num)));
//		                r++;
//		                l = r;
//		            } else {
//		                r++;
//		            }
//		        }
//		        return s.length();
//		    }
//		http://www.cnblogs.com/grandyang/p/5771434.html
		//我们可以先用递归来做，思路是，首先判断s是否为空，为空直接返回，不为空的话看首字符是否为'['，是的话说明s为一个整数，
	//我们直接返回结果。如果首字符是'['，且s长度小于等于2，说明没有内容，直接返回结果。反之如果s长度大于2，
	//我们从i=1开始遍历，我们需要一个变量start来记录某一层的其实位置，用cnt来记录跟其实位置是否为同一深度，
	//cnt=0表示同一深度，由于中间每段都是由逗号隔开，所以当我们判断当cnt为0，且当前字符是逗号或者已经到字符串末尾了，
	//我们把start到当前位置之间的字符串取出来递归调用函数，把返回结果加入res中，然后start更新为i+1。如果遇到'['，
	//计数器cnt自增1，若遇到']'，计数器cnt自减1
//		    NestedInteger deserialize(string s) {
//		        if (s.empty()) return NestedInteger();
//		        if (s[0] != '[') return NestedInteger(stoi(s));
//		        if (s.size() <= 2) return NestedInteger();
//		        NestedInteger res;
//		        int start = 1, cnt = 0;
//		        for (int i = 1; i < s.size(); ++i) {
//		            if (cnt == 0 && (s[i] == ',' || i == s.size() - 1)) {
//		                res.add(deserialize(s.substr(start, i - start)));
//		                start = i + 1;
//		            } else if (s[i] == '[') ++cnt;
//		            else if (s[i] == ']') --cnt;
//		        }
//		        return res;
//		    }
//	Example 1:
//		Given s = "324",
//
//		You should return a NestedInteger object which contains a single integer 324.
//		Example 2:
//
//		Given s = "[123,[456,[789]]]",
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//、System.out.println(deserialize1("[123,[456,[789]]]"));

	}
	
	
	
	
//	//System.out.println(deserialize("[123,[456,[789]]]"));
//	 Stack<NestedInteger> stack = new Stack<NestedInteger>();
//	    String temp = "";
//	 
//	    for(char c: s.toCharArray()){
//	        switch(c){
//	            case '[':
//	                stack.push(new NestedInteger()); //start a new NI
//	                break;
//	 
//	            case ']':
//	                if(!temp.equals("")){
//	                    stack.peek().add(new NestedInteger(Integer.parseInt(temp))); //add NI to parent
//	                    temp="";
//	                }
//	 
//	                NestedInteger top = stack.pop();
//	                if(!stack.empty()){
//	                    stack.peek().add(top);
//	                }else{
//	                    return top;
//	                }
//	 
//	                break;
//	 
//	            case ',':
//	                if(!temp.equals("")){
//	                    stack.peek().add(new NestedInteger(Integer.parseInt(temp)));//add NI to parent
//	                    temp="";
//	                }
//	 
//	                break;
//	 
//	            default:
//	                temp += c;
//	        }
//	    }
//	 
//	    if(!temp.equals("")){
//	        return new NestedInteger(Integer.parseInt(temp));
//	    }
//	 
//	    return null;
//	}

////	由于没有空格，所以第一个字母不是'[‘的说明只有一个数字。直接返回NestedInteger(int(s))
////	接下来，用一个栈来维护，对于左括号[的，当前的NestedInteger 进栈，对于右括号，当前数字
////放入NestedInteger 栈不为空则把栈顶的NestedInteger添加当前的NestedInteger，并且出栈。
////	http://bookshadow.com/weblog/2016/08/15/leetcode-mini-parser/
////	http://wisim.me/leetcode/2016/08/18/LeetCode_MinParser.html
//
////	Use StringBuilder
////	http://www.jianshu.com/p/4578be7c2270
////System.out.println(deserialize("[123,[456,[789]]]"));
//	    public NestedInteger deserialize(String s) {
//	        if (s == null || s.length() == 0) {
//	            return null;
//	        }
//
//	        if (s.charAt(0) != '[') {
//	            return new NestedInteger(Integer.parseInt(s));
//	        }
////System.out.println(deserialize("[123,[456,[789]]]"));
//	        Stack<NestedInteger> st = new Stack<NestedInteger>();
//	        String temp = "";
//	        for (int i = 0; i < s.length(); i++) {
//	            char curr = s.charAt(i);
//	            switch (curr) {
//	                case '[':
//	                    st.push(new NestedInteger());
//	                    break;
//	                case ']':
//	                    if (temp.length() != 0) {
//	                        st.peek().add(new NestedInteger(Integer.parseInt(temp)));
//	                        temp = "";
//	                    }               
//	                    if (st.size() > 1) {
//	                        NestedInteger node = st.pop();
//	                        st.peek().add(node);
//	                    }
//	                    break;
//	                case ',':
//	                    if (temp.length() != 0) {
//	                        st.peek().add(new NestedInteger(Integer.parseInt(temp)));
//	                        temp = "";
//	                    }
//	                    break;
//	                default:
//	                    temp = temp + curr;
//	                    break;
//	            }
//	        }
//
//	        return st.pop();
//	    }

}
