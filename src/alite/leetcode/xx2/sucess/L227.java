package alite.leetcode.xx2.sucess;
/**
 * Basic Calculator II

Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.

You may assume that the given expression is always valid.

Some examples:

"3+2*2" = 7
" 3/2 " = 1
" 3+5 / 2 " = 5
 

Note: Do not use the eval built-in library function.

Credits:
Special thanks to @ts for adding this problem and creating all test cases.

 

与Basic Calculator对照，

乘除法优先级高，因此一旦出现就立即计算，

到最后栈中剩下的就是顺序执行的加减计算。

先将栈逆序，再出栈计算。注意，此时的操作数先后顺序反过来。

复制代码
复制代码
class Solution {
public:
    int calculate(string s) {
        stack<int> num;
        stack<char> op;
        int i = 0;
        while(i < s.size())
        {
            while(i < s.size() && s[i] == ' ')
                i ++;
            if(i == s.size())
                break;
            if(s[i] == '+' || s[i] == '-' || s[i] == '*' || s[i] == '/')
            {
                op.push(s[i]);
                i ++;
            }
            else
            {
                int n = 0;
                while(i < s.size() && s[i] >= '0' && s[i] <= '9')
                {
                    n = n * 10 + (s[i]-'0');
                    i ++;
                }
                num.push(n);
                if(!op.empty() && (op.top() == '*' || op.top() == '/'))
                {
                    int n2 = num.top();
                    num.pop();
                    int n1 = num.top();
                    num.pop();
                    if(op.top() == '*')
                        num.push(n1 * n2);
                    else
                        num.push(n1 / n2);
                    op.pop();
                }
            }
        }
        // '+'/'-' in order
        if(!op.empty())
        {
            // reverse num and op
            stack<int> num2;
            while(!num.empty())
            {
                num2.push(num.top());
                num.pop();
            }
            num = num2;
            stack<char> op2;
            while(!op.empty())
            {
                op2.push(op.top());
                op.pop();
            }
            op = op2;
            
            while(!op.empty())
            {
                // pay attention to the operands order!
                int n1 = num.top();
                num.pop();
                int n2 = num.top();
                num.pop();
                if(op.top() == '+')
                    num.push(n1 + n2);
                else
                    num.push(n1 - n2);
                op.pop();
            }
        }
        return num.top();
    }
};
 * @author het
 *
 */
public class L227 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
