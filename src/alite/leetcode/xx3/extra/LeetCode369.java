package alite.leetcode.xx3.extra;
/**
 * LeetCode 369 - Plus One Linked List

http://www.cnblogs.com/grandyang/p/5626389.html
Given a non-negative number represented as a singly linked list of digits, plus one to the number.
The digits are stored such that the most significant digit is at the head of the list.
Example:
Input:
1->2->3

Output:
1->2->4
X. http://blog.csdn.net/jmspan/article/details/51780132
    private ListNode reverse(ListNode head) {  
        ListNode prev = null;  
        ListNode current = head;  
        while (current != null) {  
            ListNode next = current.next;  
            current.next = prev;  
            prev = current;  
            current = next;  
        }  
        return prev;  
    }  
    public ListNode plusOne(ListNode head) {  
        if (head == null) return null;  
        ListNode reversed = reverse(head);  
        reversed.val ++;  
        ListNode current = reversed;  
        while (current != null && current.val >= 10) {  
            current.val -= 10;  
            if (current.next == null) {  
                current.next = new ListNode(1);  
            } else {  
                current.next.val ++;  
            }  
            current = current.next;  
        }  
        reversed = reverse(reversed);  
        return reversed;  
    }  
这道题给了我们一个链表，用来模拟一个三位数，表头是高位，现在让我们进行加1运算，这道题的难点在于链表无法通过坐标来访问元素，只能通过遍历的方式进行，而这题刚好让我们从链尾开始操作，从后往前，遇到进位也要正确的处理，最后还有可能要在开头补上一位。那么我们反过来想，如果链尾是高位，那么进行加1运算就方便多了，直接就可以边遍历边进行运算处理，那么我们可以做的就是先把链表翻转一下，然后现在就是链尾是高位了，我们进行加1处理运算结束后，再把链表翻转回来即可.
    ListNode* plusOne(ListNode* head) {
        if (!head) return head;
        ListNode *rev_head = reverse(head), *cur = rev_head, *pre = cur;
        int carry = 1;
        while (cur) {
            pre = cur;
            int t = cur->val + carry;
            cur->val = t % 10;
            carry = t / 10;
            if (carry == 0) break;
            cur = cur->next;
        }
        if (carry) pre->next = new ListNode(1);
        return reverse(rev_head);
    }
    ListNode* reverse(ListNode *head) {
        if (!head) return head;
        ListNode *dummy = new ListNode(-1), *cur = head;
        dummy->next = head;
        while (cur->next) {
            ListNode *t = cur->next;
            cur->next = t->next;
            t->next = dummy->next;
            dummy->next = t;
        }
        return dummy->next;
    }
X.  
https://discuss.leetcode.com/topic/49551/java-elegant-backtracking-o-n-time-o-n-stack-space-with-comments
        public ListNode plusOne(ListNode head) {
            if (plusOneHelper(head) == 0) {
                return head;
            }
            //need addtional node
            ListNode newHead = new ListNode(1);
            newHead.next = head;
            return newHead;
        }
        
        // plus one for the rest of the list starting from node and return carry
     //because last node.next is null, let null return 1 and it is equivalent to  "plus one" to the least significant digit
   
        private int plusOneHelper(ListNode node) {
            if (node == null) {
                return 1;
            }
            int sum = node.val + plusOneHelper(node.next);
            node.val = sum % 10;
            return sum / 10;
        }
https://leetcode.com/discuss/111104/java-recursive-solution
Recursion! With recursion, we can visit list in reverse way!
public ListNode plusOne(ListNode head) {
    if( DFS(head) == 0){
        return head;
    }else{
        ListNode newHead = new ListNode(1);
        newHead.next = head;
        return newHead;
    }
}

public int DFS(ListNode head){
    if(head == null) return 1;

    int carry = DFS(head.next);

    if(carry == 0) return 0;

    int val = head.val + 1;
    head.val = val%10;
    return val/10;
}


public ListNode plusOne(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    helper(dummy);
    return dummy.val == 0 ? head : dummy;
}

private int helper(ListNode node){
    if(node == null) return 1;
    node.val += helper(node.next);
    if(node.val <= 9) return 0;
    node.val %= 10;
    return 1;
}
我们也可以通过递归来实现，这样我们就不用翻转链表了，通过递归一层一层的调用，最先处理的是链尾元素，我们将其加1，然后看是否有进位，返回进位，然后回溯到表头，加完进位，如果发现又差生了新的进位，那么我们在最开头加上一个新节点即可
    ListNode* plusOne(ListNode* head) {
        if (!head) return head;
        int carry = helper(head);
        if (carry == 1) {
            ListNode *res = new ListNode(1);
            res->next = head;
            return res;
        }
        return head;
    }
    int helper(ListNode *node) {
        if (!node) return 1;
        int carry = helper(node->next);
        int sum = node->val + carry;
        node->val = sum % 10;
        return
https://leetcode.com/discuss/111157/9-lines-recursive-without-helper
If the +1 was already handled without further carry, then the result is the given head node. Otherwise it's a new node (with carry value 1). In other words, a carry-node is created at the end and gets carried towards the front until it has been fully integrated.
public ListNode plusOne(ListNode head) {
    if (head == null)
        return new ListNode(1);
    ListNode plused = plusOne(head.next);
    if (plused != head.next)
        head.val++;
    if (head.val <= 9)
        return head;
    head.val = 0;
    plused.next = head;
    return plused;
}
X. https://leetcode.com/discuss/111127/iterative-two-pointers-with-dummy-node-java-o-n-time-o-1-space

i stands for the most significant digit that is going to be incremented if there exists a carry
dummy node can handle cases such as "9->9>-9" automatically
    public ListNode plusOne(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode i = dummy;
        ListNode j = dummy;

        while (j.next != null) {
            j = j.next;
            if (j.val != 9) {
                i = j;
            }
        }

        if (j.val != 9) {
            j.val++;
        } else {
            i.val++;
            i = i.next;
            while (i != null) {
                i.val = 0;
                i = i.next;
            }
        }

        if (dummy.val == 0) {
            return dummy.next;
        }

        return dummy;
    }
https://leetcode.com/discuss/111212/two-pointers-java-solution-o-n-time-o-1-space
    public ListNode plusOne(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode i = dummy;
        ListNode j = dummy;

        while (j.next != null) {
            j = j.next;
            if (j.val != 9) {
                i = j;
            }
        }
        // i = index of last non-9 digit

        i.val++;
        i = i.next;
        while (i != null) {
            i.val = 0;
            i = i.next;
        }

        if (dummy.val == 0) return dummy.next;
        return dummy;
    }
下面这种方法比较巧妙了，思路是遍历链表，找到第一个不为9的数字，如果找不这样的数字，说明所有数字均为9，那么在表头新建一个值为0的新节点，进行加1处理，然后把右边所有的数字都置为0即可。举例来说：
比如1->2->3，那么第一个不为9的数字为3，对3进行加1，变成4，右边没有节点了，所以不做处理，返回1->2->4。
再比如说8->9->9，找第一个不为9的数字为8，进行加1处理变成了9，然后把后面的数字都置0，得到结果9->0->0。
再来看9->9->9的情况，找不到不为9的数字，那么再前面新建一个值为0的节点，进行加1处理变成了1，把后面的数字都置0，得到1->0->0->0。
    ListNode* plusOne(ListNode* head) {
        ListNode *cur = head, *right = NULL;
        while (cur) {
            if (cur->val != 9) right = cur;
            cur = cur->next;
        }
        if (!right) {
            right = new ListNode(0);
            right->next = head;
            head = right;
        }
        ++right->val;
        cur = right->next;
        while (cur) {
            cur->val = 0;
            cur = cur->next;
        }
        return head;
    }
最后这种解法是解法二的迭代写法，我们用到栈，利用栈的先进后出机制，就可以实现从后往前的处理节点
    ListNode* plusOne(ListNode* head) {
        stack<ListNode*> s;
        ListNode *cur = head;
        while (cur) {
            s.push(cur);
            cur = cur->next;
        }
        int carry = 1;
        while (!s.empty() && carry) {
            ListNode *t = s.top(); s.pop();
            int sum = t->val + carry;
            t->val = sum % 10;
            carry = sum / 10;
        }
        if (carry) {
            ListNode *new_head = new ListNode(1);
            new_head->next = head;
            head = new_head;
        }
        return head;
    }

https://discuss.leetcode.com/topic/52174/java-o-n-time-o-1-space-short-solution
The idea is to find the first non-nine node from the right.
Increase its val by 1 and change everything behind it to zero.
public ListNode plusOne(ListNode head) {
    ListNode notNine = new ListNode(0);
    notNine.next = head;
    head = notNine;
    for (ListNode node = head; node != null; node = node.next)
        if (node.val != 9) notNine = node;
    notNine.val += 1;
    for (ListNode node = notNine.next; node != null; node = node.next)
        node.val = 0;
    return head.val > 0 ? head : head.next;
}

You might also like

 * @author het
 *
 */
public class LeetCode369 {
//	//Recursion! With recursion, we can visit list in reverse way!
//	public ListNode plusOne(ListNode head) {
//	    if( DFS(head) == 0){
//	        return head;
//	    }else{
//	        ListNode newHead = new ListNode(1);
//	        newHead.next = head;
//	        return newHead;
//	    }
//	}
//
//	public int DFS(ListNode head){
//	    if(head == null) return 1;
//
//	    int carry = DFS(head.next);
//
//	    if(carry == 0) return 0;
//
//	    int val = head.val + 1;
//	    head.val = val%10;
//	    return val/10;
//	}
	
	public ListNode plusOne(ListNode head) {
	    ListNode dummy = new ListNode(0);
	    dummy.next = head;
	    helper(dummy);
	    return dummy.val == 0 ? head : dummy;
	}

	private int helper(ListNode node){
	    if(node == null) return 1;
	    node.val += helper(node.next);
	    if(node.val <= 9) return 0;
	    node.val %= 10;
	    return 1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
