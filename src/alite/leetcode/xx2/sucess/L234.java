package alite.leetcode.xx2.sucess;
/**
 * Palindrome Linked List

Given a singly linked list, determine if it is a palindrome.

Follow up:
Could you do it in O(n) time and O(1) space?

 

解法一：

一次遍历，装入vector，然后再一次遍历判断回文。

时间复杂度O(n)，空间复杂度O(n)

复制代码
复制代码
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    bool isPalindrome(ListNode* head) {
        vector<int> v;
        while(head)
        {
            v.push_back(head->val);
            head = head->next;
        }
        for(int i = 0, j = v.size()-1; i < j; i ++, j --)
        {
            if(v[i] != v[j])
                return false;
        }
        return true;
    }
};
复制代码
复制代码


 

解法二：

找到链表中点，拆分后，逆转后半个链表，然后两个链表同时顺序遍历一次。

若链表长度为奇数，最末尾的元素可以忽略。

时间复杂度O(n)，空间复杂度O(1)

复制代码
复制代码
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    bool isPalindrome(ListNode* head) {
        if(head == NULL || head->next == NULL)
            return true;
        ListNode* mid = getMid(head);
        ListNode* head2 = reverse(mid);
        while(head && head2)
        {
            if(head->val != head2->val)
                return false;
            head = head->next;
            head2 = head2->next;
        }
        return true;
    }
    ListNode* getMid(ListNode* head)
    {// at least two nodes
        ListNode* slow = head;
        ListNode* fast = head;
        ListNode* preslow = NULL;
        do
        {
            fast = fast->next;
            if(fast)
            {
                fast = fast->next;
                preslow = slow;
                slow = slow->next;
            }
        }while(fast != NULL);
        preslow->next = NULL;
        return slow;
    }
    ListNode* reverse(ListNode* head)
    {
        if(head == NULL || head->next == NULL)
            return head;
        else if(head->next->next == NULL)
        {// two nodes
            ListNode* tail = head->next;
            head->next = NULL;
            tail->next = head;
            return tail;
        }
        else
        {
            ListNode* pre = head;
            ListNode* cur = pre->next;
            pre->next = NULL;   // set tail
            ListNode* post = cur->next;
            while(post)
            {
                cur->next = pre;
                pre = cur;
                cur = post;
                post = post->next;
            }
            cur->next = pre;
            return cur;
        }
    }
};
 * @author het
 *
 */
public class L234 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
