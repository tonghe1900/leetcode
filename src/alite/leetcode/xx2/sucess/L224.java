package alite.leetcode.xx2.sucess;
/**
 * Basic Calculator

Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

You may assume that the given expression is always valid.

Some examples:

"1 + 1" = 2
" 2-1 + 2 " = 3
"(1+(4+5+2)-3)+(6+8)" = 23
 

Note: Do not use the eval built-in library function.

 

两个要点：

1、无括号时，顺序执行

2、有括号时，先执行括号中的

两个栈：

一个存放操作数，每次进栈要注意，如果操作符栈顶元素为'+'/'-'，则需要立即计算。

一个存放操作符（包括括号），每次出现')'时，不断进行出栈计算再进栈，直到弹出'('，说明当前括号内计算完毕。

复制代码
复制代码
class Solution {
public:
    int calculate(string s) {
        stack<int> num;
        stack<int> op;
        int i = 0;
        while(i < s.size())
        {
            while(i < s.size() && s[i] == ' ')
                i ++;
            if(i == s.size())
                break;
            if(s[i] == '+' || s[i] == '-' || s[i] == '(')
            {
                op.push(s[i]);
                i ++;   
            }
            else if(s[i] == ')')
            {
                while(op.top() != '(')
                {// calculation within parentheses 
                    int n2 = num.top();
                    num.pop();
                    int n1 = num.top();
                    num.pop();
                    if(op.top() == '+')
                        num.push(n1 + n2);
                    else
                        num.push(n1 - n2);
                    op.pop();
                }
                op.pop();
                while(!op.empty() && op.top() != '(')
                {
                    int n2 = num.top();
                    num.pop();
                    int n1 = num.top();
                    num.pop();
                    if(op.top() == '+')
                        num.push(n1 + n2);
                    else
                        num.push(n1 - n2);
                    op.pop();
                }
                i ++;
            }
            else
            {
                int n = 0;
                while(i < s.size() && s[i] >= '0' && s[i] <= '9')
                {
                    n = n*10 + (s[i]-'0');
                    i ++;
                }
                num.push(n);
                while(!op.empty() && op.top() != '(')
                {
                    int n2 = num.top();
                    num.pop();
                    int n1 = num.top();
                    num.pop();
                    if(op.top() == '+')
                        num.push(n1 + n2);
                    else
                        num.push(n1 - n2);
                    op.pop();
                }
            }
        }
        return num.top();
    }
};
 * @author het
 *
 */
public class L224 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
