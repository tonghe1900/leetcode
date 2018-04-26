package alite.leetcode.xx2;
/**
 * Implement Queue using Stacks

Implement the following operations of a queue using stacks.

push(x) -- Push element x to the back of queue.
pop() -- Removes the element from in front of queue.
peek() -- Get the front element.
empty() -- Return whether the queue is empty.
Notes:

You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 

两个栈实现堆是经典算法。

进队时，压入stk1。

出队时，stk2弹出。

stk2为空时，stk1倒入stk2。两次逆序恢复了原序。

复制代码
复制代码
class Queue {
public:
    stack<int> stk1;    // push
    stack<int> stk2;    // pop
    
    // Push element x to the back of queue.
    void push(int x) {
        stk1.push(x);
    }

    // Removes the element from in front of queue.
    void pop(void) {
        if(stk2.empty())
        {
            while(!stk1.empty())
            {
                int top = stk1.top();
                stk1.pop();
                stk2.push(top);
            }
        }
        stk2.pop();
    }

    // Get the front element.
    int peek(void) {
        if(stk2.empty())
        {
            while(!stk1.empty())
            {
                int top = stk1.top();
                stk1.pop();
                stk2.push(top);
            }
        }
        return stk2.top();
    }

    // Return whether the queue is empty.
    bool empty(void) {
        return stk1.empty()&&stk2.empty();
    }
};
 * @author het
 *
 */
public class L232 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
