package alite.leetcode.xx2.sucess;
/**
 * Implement Stack using Queues

Implement the following operations of a stack using queues.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
empty() -- Return whether the stack is empty.
Notes:

You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size, and is empty operations are valid.
Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or deque (double-ended queue), as long as you use only standard operations of a queue.
You may assume that all operations are valid (for example, no pop or top operations will be called on an empty stack).
 

Update (2015-06-11):
The class name of the Java function had been updated to MyStack instead of Stack.

Credits:
Special thanks to @jianchao.li.fighter for adding this problem and all test cases.

 

两个队列实现栈：

一个队列存放当前所有元素，另一个队列为空。

push: 元素进非空队列，并更新top值为当前进队元素。

pop: 将非空队列的值，除了最后一个(待出队)，其余的装入空队列。注意更新top值为最后第二个进队元素。

top: 直接返回实时更新的top值

empty: 判断两个栈是否均为空

复制代码
复制代码
class Stack {
public:
    queue<int> q1;
    queue<int> q2;
    int n;
    
    // Push element x onto stack.
    void push(int x) {
        if(q2.empty())
            q1.push(x);
        else
            q2.push(x);
        n = x;
    }

    // Removes the element on top of the stack.
    void pop() {
        if(!q1.empty())
        {
            while(q1.size() > 1)
            {
                int front = q1.front();
                if(q1.size() == 2)
                    n = front;
                q1.pop();
                q2.push(front);
            }
            q1.pop();
        }
        else
        {
            while(q2.size() > 1)
            {
                int front = q2.front();
                if(q2.size() == 2)
                    n = front;
                q2.pop();
                q1.push(front);
            }
            q2.pop();
        }
    }

    // Get the top element.
    int top() {
        return n;
    }

    // Return whether the stack is empty.
    bool empty() {
        return q1.empty() && q2.empty();
    }
};
 * @author het
 *
 */
public class L225 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
