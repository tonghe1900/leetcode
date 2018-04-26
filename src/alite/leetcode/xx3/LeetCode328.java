package alite.leetcode.xx3;
/**
 * LeetCode 328 - Odd Even Linked List

http://www.hrwhisper.me/leetcode-odd-even-linked-list/
Given a singly linked list, group all odd nodes together followed by the even nodes. Please note
 here we are talking about the node number and not the value in the nodes.
You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
Example:
Given 1->2->3->4->5->NULL,
return 1->3->5->2->4->NULL.
Note:
The relative order inside both the even and odd groups should remain as it was in the input.
The first node is considered odd, the second node even and so on ...
http://algobox.org/odd-even-linked-list/
this version odd and even is decoupled and easier to understand.


public ListNode oddEvenList(ListNode head) {


    if (head == null) return null;


    ListNode odd = head, even = head.next, evenHead = even;


    while (even != null && even.next != null) {


        odd.next = odd.next.next;        


        even.next = even.next.next;


        odd = odd.next;


        even = even.next;


    }


    odd.next = evenHead;


    return head;


}

Share this:
很简单的，用两个指针即可，一个指针p指向当前遍历的奇数节点的最后一个位置，另一个指针q指向待提取的奇数节点的前一个位置。
然后把q.next 的节点删除，插入到p.next的位置即可
Better variable names:

    public ListNode oddEvenList(ListNode head) {

        if (head == null) return head;

        ListNode p = head, q =head;

        while (q != null) {

            q = q.next;

            if (q==null || q.next==null) break;

            ListNode next_p = p.next, next_q = q.next;

            q.next = next_q.next;

            p.next = next_q;

            next_q.next = next_p;

            p = p.next;

        }

        return head;

    }
http://bookshadow.com/weblog/2016/01/16/leetcode-odd-even-linked-list/
偶数与奇数节点分组内部的相对顺序应当与输入保持一致。
第一个节点为奇数节点，第二个节点为偶数节点，以此类推。
    def oddEvenList(self, head):
        if head is None: return head
        odd = oddHead = head
        even = evenHead = head.next
        while even and even.next:
            odd.next = even.next
            odd = odd.next
            even.next = odd.next
            even = even.next
        odd.next = evenHead
        return oddHead
 * @author het
 *Given 1->2->3->4->5->NULL,
return 1->3->5->2->4->NULL.
 */
public class LeetCode328 {
	public ListNode oddEvenList(ListNode head) {


	    if (head == null) return null;


	    ListNode odd = head, even = head.next, evenHead = even;


	    while (even != null && even.next != null) {


	        odd.next = odd.next.next;        


	        even.next = even.next.next;


	        odd = odd.next;


	        even = even.next;


	    }


	    odd.next = evenHead;


	    return head;


	}

//	Share this:
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
