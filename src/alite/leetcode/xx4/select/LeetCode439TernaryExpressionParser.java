package alite.leetcode.xx4.select;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given a string representing arbitrarily nested ternary expressions,
 *  calculate the result of the expression. You can always assume that the given expression 
 *  is valid and only consists of digits 0-9, ?, :, T and F (T and F represent True and False respectively).
Note:
The length of the given string is ≤ 10000.
Each number will contain only one digit.
The conditional expressions group right-to-left (as usual in most languages).
The condition will always be either T or F. That is, the condition will never be a digit.
The result of the expression will always evaluate to either a digit 0-9, T or F.
Example 1:
Input: "T?2:3"

Output: "2"

Explanation: If true, then result is 2; otherwise result is 3.
Example 2:
Input: "F?1:T?4:5"

Output: "4"

Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

             "(F ? 1 : (T ? 4 : 5))"                   "(F ? 1 : (T ? 4 : 5))"
          -> "(F ? 1 : 4)"                 or       -> "(T ? 4 : 5)"
          -> "4"                                    -> "4"
Example 3:
Input: "T?T?F:5:3"

Output: "F"

Explanation: The conditional expressions group right-to-left. Using parenthesis, it is read/evaluated as:

             "(T ? (T ? F : 5) : 3)"                   "(T ? (T ? F : 5) : 3)"
          -> "(T ? F : 3)"                 or       -> "(T ? F : 5)"
          -> "F"                                    -> "F"
https://discuss.leetcode.com/topic/64409/very-easy-1-pass-stack-solution-in-java-no-string-concat
Iterate the expression from tail, whenever encounter a character before '?', calculate the right value and push back to stack.
P.S. this code is guaranteed only if "the given expression is valid" base on the requirement.
public String parseTernary(String expression) {
    if (expression == null || expression.length() == 0) return "";
    Deque<Character> stack = new LinkedList<>();

    for (int i = expression.length() - 1; i >= 0; i--) {
        char c = expression.charAt(i);
        if (!stack.isEmpty() && stack.peek() == '?') {

            stack.pop(); //pop '?'
            char first = stack.pop();
            stack.pop(); //pop ':'
            char second = stack.pop();

            if (c == 'T') stack.push(first);
            else stack.push(second);
        } else {
            stack.push(c);
        }
    }

    return String.valueOf(stack.peek());
}
https://discuss.leetcode.com/topic/64512/java-short-clean-code-no-stack

https://discuss.leetcode.com/topic/64524/short-python-solutions-one-o-n
栈（Stack）数据结构
循环直到栈中元素为1并且表达式非空：
取栈顶的5个元素，判断是否为一个可以解析的表达式。若是，则解析后压栈
否则从右向左将expression中的字符压入栈stack
Collect chars from back to front on a stack, evaluate ternary sub-expressions as soon as possible:
def parseTernary(self, expression):
    stack = []
    for c in reversed(expression):
        stack.append(c)
        if stack[-2:-1] == ['?']:
            stack[-5:] = stack[-3 if stack[-1] == 'T' else -5]
    return stack[0]
Originally my check was stack[-4::2] == [':', '?'], but @YJL1228's is right, looking for ? is enough.
https://discuss.leetcode.com/topic/64389/easy-and-concise-5-lines-python-java-solution
    public String parseTernary(String expression) {
        while (expression.length() != 1) {
            int i = expression.lastIndexOf("?");    // get the last shown '?'
            char tmp;
            if (expression.charAt(i-1) == 'T') { tmp = expression.charAt(i+1); }
            else { tmp = expression.charAt(i+3); }
            expression = expression.substring(0, i-1) + tmp + expression.substring(i+4);
        }
        return expression;
    }

X. Recursion
http://www.voidcn.com/blog/zqh_1991/article/p-6244665.html
    string parseTernary(string expression) {
        int len=expression.size();
        if(len==1) return expression;
        int count1=0,count2=0;
        for(int i=0;i<len;i++) {
            if(expression[i]=='?') {
                count1++;
            }
            else if(expression[i]==':') {
                count2++;
                if(count1==count2) {
                    if(expression[0]=='T') {
                        return parseTernary(expression.substr(2,i-2));  //表达式的左边
                    }
                    else return parseTernary(expression.substr(i+1));   //表达式的右边
                }
            }
        }
        return "";
    }

https://discuss.leetcode.com/topic/64397/java-o-n-using-binary-tree
    public String parseTernary(String expression) {
        if(expression == null || expression.length() == 0) return "";
        Node root = buildTree(expression.toCharArray());
        return evaluate(root) + "";
    }
    static class Node {
        char val;
        Node left;
        Node right;
        Node parent;
        
        public Node(char c) {
            val = c;
            left = null;
            right = null;
            parent = null;
        }
    }
    private static Node buildTree(char[] ch) {
        Node root = new Node(ch[0]);
        Node node = root;
        for(int i = 1; i < ch.length; i++) {
            if(ch[i] == '?') {
                Node left = new Node(ch[i + 1]);
                node.left = left;
                left.parent = node;
                node = node.left;
            }
            else if(ch[i] == ':') {
                node = node.parent;
                while(node.right != null && node.parent != null) {
                    node = node.parent;
                }
                Node right = new Node(ch[i + 1]);
                node.right = right;
                right.parent = node;
                node = node.right;
            }
        }
        return root;
    }
    private static char evaluate(Node root) {
        while(root.val == 'T' || root.val == 'F') {
            if(root.left == null && root.right == null) break;
            if(root.val == 'T') root = root.left;
            else root = root.right;
        }
        return root.val;
    }
 * @author het
 *
 */
public class LeetCode439TernaryExpressionParser {
	public static String parseTernary(String expression) {
	    if (expression == null || expression.length() == 0) return "";
	    Deque<Character> stack = new LinkedList<>();

	    for (int i = expression.length() - 1; i >= 0; i--) {
	        char c = expression.charAt(i);
	        if (!stack.isEmpty() && stack.peek() == '?') {

	            stack.pop(); //pop '?'
	            char first = stack.pop();
	            stack.pop(); //pop ':'
	            char second = stack.pop();

	            if (c == 'T') stack.push(first);
	            else stack.push(second);
	        } else {
	            stack.push(c);
	        }
	    }

	    return String.valueOf(stack.peek());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(parseTernary("T?T?F:5:3"));

	}

}
