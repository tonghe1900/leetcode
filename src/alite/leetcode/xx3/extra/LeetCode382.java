package alite.leetcode.xx3.extra;

import java.util.Random;

///**
// * LeetCode 382 - Linked List Random Node
//
//https://discuss.leetcode.com/topic/53738/o-n-time-o-1-space-java-solution
//Given a singly linked list, return a random node's value from the linked list. 
//Each node must have the same probability of being chosen.
//Follow up:
//What if the linked list is extremely large and its length is unknown to you? 
//Could you solve this efficiently without using extra space?
//Example:
//
//
//// Init a singly linked list [1,2,3].
//ListNode head = new ListNode(1);
//head.next = new ListNode(2);
//head.next.next = new ListNode(3);
//Solution solution = new Solution(head);
//
//// getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
//solution.getRandom();
//
//https://discuss.leetcode.com/topic/53740/reservoir-sampling-java-solution
//    ListNode head;
//    Random random;
//    /** @param head The linked list's head. Note that the head is guanranteed to be not null, so it contains at least one node. */
//    public Solution(ListNode head) {
//        this.head = head;
//        random = new Random();
//    }
//    
//    /** Returns a random node's value. */
//    public int getRandom() {
//        ListNode result = head;
//        ListNode cur = head;
//        int size = 1;
//        while (cur != null) {
//            if (random.nextInt(size) == 0) {
//                result = cur;
//            }
//            size++;
//            cur = cur.next;
//        }
//        
//        return result.val;
//    }
//https://discuss.leetcode.com/topic/53738/o-n-time-o-1-space-java-solution
//    ListNode head = null;
//    Random randomGenerator = null;
//    public Solution(ListNode head) {
//        this.head = head;
//        this.randomGenerator = new Random();
//
//    }
//    
//    /** Returns a random node's value. */
//    public int getRandom() {
//        ListNode result = null;
//        ListNode current = head;
//        
//        for(int n = 1; current!=null; n++) {
//            if (randomGenerator.nextInt(n) == 0) {
//                result = current;
//            }
//            current = current.next;
//        }
//        
//        return result.val;
//        
//    }
//
//https://discuss.leetcode.com/topic/53739/straight-forward-java-solution
// * @author het
// *
// */
public class LeetCode382 {
    	ListNode head = null;
        Random randomGenerator = null;
        public LeetCode382(ListNode head) {
            this.head = head;
            this.randomGenerator = new Random();

        }
        
        /** Returns a random node's value. */
        public int getRandom() {
            ListNode result = null;
            ListNode current = head;
//            for(int n = 1 ; current != null; n+=1){
//            	 if(randomGenerator.nextInt(n) == 0){
//            		 result = current;
//            	 }
//            	 current = current.next;
//            	
//            }
//            return result.val;
            for(int n = 1; current!=null; n++) {
                if (randomGenerator.nextInt(n) == 0) {
                    result = current;
                }
                current = current.next;
            }
            
            return result.val;
            
        }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
