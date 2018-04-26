package alite.leetcode.xx4.left;
///**
// * https://leetcode.com/problems/add-two-numbers-ii/
//You are given two linked lists representing two non-negative numbers. The most significant digit comes first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//Follow up:
//What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
//Example:
//Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
//Output: 7 -> 8 -> 0 -> 7
//https://discuss.leetcode.com/topic/65279/easy-o-n-java-solution-using-stack
//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        Stack<Integer> s1 = new Stack<Integer>();
//        Stack<Integer> s2 = new Stack<Integer>();
//        
//        while(l1 != null) {
//            s1.push(l1.val);
//            l1 = l1.next;
//        };
//        while(l2 != null) {
//            s2.push(l2.val);
//            l2 = l2.next;
//        }
//        
//        int sum = 0;
//        ListNode list = new ListNode(0);
//        while (!s1.empty() || !s2.empty()) {
//            if (!s1.empty()) sum += s1.pop();
//            if (!s2.empty()) sum += s2.pop();
//            list.val = sum % 10;
//            ListNode head = new ListNode(sum / 10);
//            head.next = list;
//            list = head;
//            sum /= 10;
//        }
//        
//        return list.val == 0 ? list.next : list;
//    }
//https://aaronice.gitbooks.io/lintcode/content/linked_list/add_two_numbers_ii.html
//    public ListNode reverse(ListNode head) {
//        ListNode prev = null;
//        while (head != null) {
//            ListNode next = head.next;
//            head.next = prev;
//            prev = head;
//            head = next;
//        }
//        return prev;
//    }
//
//    public ListNode addLists(ListNode l1, ListNode l2) {
//        if (l1 == null && l2 == null) {
//            return null;
//        }
//
//        ListNode head = new ListNode(0);
//        ListNode pointer = head;
//
//        int carry = 0;
//
//        while (l1 != null && l2 != null) {
//
//            int sum = l1.val + l2.val + carry;
//            carry = sum / 10;
//
//            pointer.next = new ListNode(sum % 10);
//            pointer = pointer.next;
//            l1 = l1.next;
//            l2 = l2.next;
//        }
//
//        while (l1 != null) {
//            int sum = l1.val + carry;
//            carry = sum / 10;
//            pointer.next = new ListNode(sum % 10);
//            pointer = pointer.next;
//            l1 = l1.next;
//        }
//
//        while (l2 != null) {
//            int sum = l2.val + carry;
//            carry = sum / 10;
//            pointer.next = new ListNode(sum = sum % 10);
//            pointer = pointer.next;
//            l2 = l2.next;
//        }
//
//        if (carry != 0) {
//            pointer.next = new ListNode(carry);
//        }
//
//        return head.next;
//    }
//    /**
//     * @param l1: the first list
//     * @param l2: the second list
//     * @return: the sum list of l1 and l2
//     */
//    public ListNode addLists2(ListNode l1, ListNode l2) {
//        l1 = reverse(l1);
//        l2 = reverse(l2);
//
//        return reverse(addLists(l1, l2));
//    }
//http://www.itdadao.com/articles/c15a668623p0.html
//This changed the input
//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {                
//         if(l1 == null) return l2;
//         if(l2 == null) return l1;
//         
//         int flag = 0;
//         ListNode res = new ListNode(0);
//         ListNode tmp = new ListNode(0);
//         res = tmp;
//           
//         l1 = reverse(l1);
//         l2 = reverse(l2);
//           
//         while(l1 != null || l2 != null){
//             int a = l1 == null?0:l1.val;
//             int b = l2 == null?0:l2.val;
//             ListNode ss = new ListNode((a+b+flag)%10);
//             flag = (a+b+flag) /10;
//             tmp.next = ss;
//             tmp = ss;
//               
//             l1 = l1 == null? null:l1.next;
//             l2 = l2 == null? null:l2.next;
//             
//         }
//        
//         if(flag == 1){
//             
//             ListNode ss = new ListNode(1);
//             tmp.next = ss;
//             
//         }                       
//           return reverse(res.next);            
//    }
//    
//    
//    public ListNode reverse(ListNode l1){
//        
//        if(l1.next == null) return l1;
//        
//        ListNode p = new ListNode(0);
//        ListNode q = new ListNode(0);
//        
//        p = l1;
//        q = l1.next;
//        p.next = null;
//        
//        while(q != null){            
//            p = q;
//            q = q.next;
//            p.next = l1;
//            l1 = p;            
//        }        
//       return l1;
//    }
//http://bookshadow.com/weblog/2016/10/29/leetcode-add-two-numbers-ii/
//双指针（Two Pointers）
//时间复杂度O(n)，空间复杂度O(n)，所用空间除保存最终结果外，没有额外开销
//具体步骤如下：
//1. 统计两链表长度s1, s2；最终结果链表长度s = max(s1, s2) （若有进位，则为s+1）
//
//2. 将两链表对齐并逐节点求和，记头节点为h（头节点为dummy node，最高位从h.next开始）
//
//3. 初始令指针p指向头节点h，执行循环：
//
//    令指针q = p.next，重复向其下一节点移动，直到q为空或者q.val ≠ 9
//    
//    如果q.val ＞ 9，说明p与q之间的所有节点需要进位，令p向q移动同时修改p.val
//    
//    否则，令p = q
//    def addTwoNumbers(self, l1, l2):
//        """
//        :type l1: ListNode
//        :type l2: ListNode
//        :rtype: ListNode
//        """
//        s1 = self.getSize(l1)
//        s2 = self.getSize(l2)
//        s = max(s1, s2)
//
//        p = h = ListNode(0)
//        while s:
//            p.next = ListNode(0)
//            p = p.next
//            if s <= s1:
//                p.val += l1.val
//                l1 = l1.next
//            if s <= s2:
//                p.val += l2.val
//                l2 = l2.next
//            s -= 1
//
//        p = h
//        while p:
//            q = p.next
//            while q and q.val == 9:
//                q = q.next
//            if q and q.val > 9:
//                while p != q:
//                    p.val += 1
//                    p = p.next
//                    p.val -= 10
//            else: p = q
//        return h if h.val else h.next
//    
//    def getSize(self, h):
//        c = 0
//        while h:
//            c += 1
//            h = h.next
//        return c
//
//X. Use hashmap
//https://discuss.leetcode.com/topic/65271/straightforward-o-n-java-solution-without-modifying-input-lists
//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        
//        HashMap<Integer, Integer> hm1 = new HashMap<>(); //Store the 'index' and the value of List1
//        HashMap<Integer, Integer> hm2 = new HashMap<>(); //Store the 'index' and the value of List2
//        int i = 1, j = 1;
//        
//        while(l1 != null){
//            hm1.put(i, l1.val);
//            l1 = l1.next;
//            i++;
//        }
//        while(l2 != null){
//            hm2.put(j, l2.val);
//            l2 = l2.next;
//            j++;
//        }
//        
//        int carry = 0;
//        i--; j--;
//        ListNode head = null;
//        
//      //Create new nodes to the front of a new LinkedList
//        while(i > 0 || j > 0 || carry > 0){
//
//            int a = i > 0 ? hm1.get(i) : 0;
//            int b = j > 0 ? hm2.get(j) : 0;
//            int res = (a + b + carry) % 10;
//            
//            ListNode newNode = new ListNode(res);
//            newNode.next = head;
//            head = newNode;
//            
//            carry = (a + b + carry) / 10;
//            i--; j--;
//        }
//        return head;
//    }
// * @author het
// *
// */
public class LeetCode445AddTwoNumbersII {
	//https://discuss.leetcode.com/topic/65279/easy-o-n-java-solution-using-stack
	    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
	        Stack<Integer> s1 = new Stack<Integer>();
	        Stack<Integer> s2 = new Stack<Integer>();
	        
	        while(l1 != null) {
	            s1.push(l1.val);
	            l1 = l1.next;
	        };
	        while(l2 != null) {
	            s2.push(l2.val);
	            l2 = l2.next;
	        }
	        
	        int sum = 0;
	        ListNode list = new ListNode(0);
	        while (!s1.empty() || !s2.empty()) {
	            if (!s1.empty()) sum += s1.pop();
	            if (!s2.empty()) sum += s2.pop();
	            list.val = sum % 10;
	            ListNode head = new ListNode(sum / 10);
	            head.next = list;
	            list = head;
	            sum /= 10;
	        }
	        
	        return list.val == 0 ? list.next : list;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
