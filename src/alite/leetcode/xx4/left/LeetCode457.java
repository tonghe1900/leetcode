package alite.leetcode.xx4.left;
/**
 * LeetCode 457 - Circular Array Loop

https://leetcode.com/problems/circular-array-loop/
You are given an array of positive and negative integers. 
If a number n at an index is positive, then move forward n steps. 
Conversely, if it's negative (-n), move backward n steps.
 Assume the first element of the array is forward next to the last element, 
 and the last element is backward next to the first element.
  Determine if there is a loop in this array. 
  A loop starts and ends at a particular index with more than 1 element along the loop. 
  The loop must be "forward" or "backward'.
Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.
Example 2: Given the array [-1, 2], there is no loop.
Note: The given array is guaranteed to contain no element "0".
Can you do it in O(n) time complexity and O(1) space complexity?

https://discuss.leetcode.com/topic/66862/two-pass-o-n-solution-by-marking-failed-loop-by-zero
The basic idea is to detect a loop by maintaining a one-step and a two-step pointers, 
just like an old problem from leetcode. And each time a possible attempt failed
 we mark every index on the path by zero, since zero is guaranteed to fail. 
 Since the problem asks only forward of backward solution we simply run it for positive indices and negative indices twice.
By the way, the problem states that the array has only pos and neg numbers, which is apparently a little inaccurate. The presence of zero though doesn't seem to cause much problem.
https://discuss.leetcode.com/topic/66894/java-slow-fast-pointer-solution
Just think it as finding a loop in Linkedlist, except that loops with only 1 element do not count. Use a slow and fast pointer, slow pointer moves 1 step a time while fast pointer 
moves 2 steps a time. If there is a loop (fast == slow), we return true, else if we meet element with different
 directions, then the search fail, we set all elements along the way to 0. 
 Because 0 is fail for sure so when later search meet 0 we know the search will fail.
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            // slow/fast pointer
            int j = i, k = getIndex(i, nums);
            while (nums[k] * nums[i] > 0 && nums[getIndex(k, nums)] * nums[i] > 0) {
                if (j == k) {
                    // check for loop with only one element
                    if (j == getIndex(j, nums)) {
                        break;
                    }
                    return true;
                }
                j = getIndex(j, nums);
                k = getIndex(getIndex(k, nums), nums);
            }
            // loop not found, set all element along the way to 0
            j = i;
            int val = nums[i];
            while (nums[j] * val > 0) {
                int next = getIndex(j, nums);
                nums[j] = 0;
                j = next;
            }
        }
        return false;
    }
    
    public int getIndex(int i, int[] nums) {
        int n = nums.length;
        return i + nums[i] >= 0? (i + nums[i]) % n: n + ((i + nums[i]) % n);
    }
getIndex() could also be:
public int getIndex(int i, int[] nums) {
        int n = nums.length;
        return (i + nums[i]) % n + (i + nums[i] < 0)*n;
    }
https://discuss.leetcode.com/topic/66962/java-solution-similar-to-finding-linkedlist-cycle
Start from any index, move to the next index by function f(i)=((i+nums[i])%len+len)%len where len is the length of array nums. and follow the chain. We are guaranteed there is a cycle in this chaining process. check if all the numbers in the cycle are backward or forward. Remember to exclude the one element cycle cases.
    public boolean circularArrayLoop(int[] nums) {
        if(nums==null||nums.length==0) return false;
        for(int a:nums){
            if(a==0) return false;
        }
        int len=nums.length;
        for(int i=0;i<len;i++){
            if(checkCycle(nums,i)) return true;
        }
        return false;
    }
    public boolean checkCycle(int[] nums, int start){
        int len=nums.length;
        int slow=((start+nums[start])%len+len)%len;
        int fast=((slow+nums[slow])%len+len)%len;
        while(slow!=fast){
            slow=((slow+nums[slow])%len+len)%len;
            fast=((fast+nums[fast])%len+len)%len;
            fast=((fast+nums[fast])%len+len)%len;
        }
        if(slow==((slow+nums[slow])%len+len)%len) return false;//one element loop
        boolean forward_backward=nums[slow]>0;//forward or backword
        int ptr=((slow+nums[slow])%len+len)%len;
        while(ptr!=slow){
            if(nums[ptr]>0!=forward_backward) return false;
            ptr=((ptr+nums[ptr])%len+len)%len;
        }
        return true;
    }
http://bookshadow.com/weblog/2016/11/09/leetcode-circular-array-loop/
三次遍历数组：
第一次遍历，将所有指向自己的元素置0

第二次遍历，从0到n（循环一周），将指向非负数的负数置0

第三次遍历，从n到0（循环一周），将指向非正数的正数置0
遍历结束后，如果数组中存在非零元素，则返回True；否则返回False
    def circularArrayLoop(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        if not nums or not all(nums): return False
        size = len(nums)
        next = lambda x : (x + size + nums[x]) % size
        for x in range(size):
            if next(x) == x:
                nums[x] = 0
        for x in range(size + 1):
            x = x % size
            if nums[x] < 0 and nums[next(x)] >= 0:
                nums[x] = 0
        for x in range(size, -1, -1):
            x = x % size
            if nums[x] > 0 and nums[next(x)] <= 0:
                nums[x] = 0
        return any(nums)
 * @author het
 *
 */
public class LeetCode457 {
	public static boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            // slow/fast pointer
            int j = i, k = getIndex(i, nums);
            while (nums[k] * nums[i] > 0 && nums[getIndex(k, nums)] * nums[i] > 0) {
                if (j == k) {
                    // check for loop with only one element
                    if (j == getIndex(j, nums)) {
                        break;
                    }
                    return true;
                }
                j = getIndex(j, nums);
                k = getIndex(getIndex(k, nums), nums);
            }
            // loop not found, set all element along the way to 0
            j = i;
            int val = nums[i];
            while (nums[j] * val > 0) {
                int next = getIndex(j, nums);
                nums[j] = 0;
                j = next;
            }
        }
        return false;
    }
    
    public static int getIndex(int i, int[] nums) {
        int n = nums.length;
        return i + nums[i] >= 0? (i + nums[i]) % n: n + ((i + nums[i]) % n);
    }
//getIndex() could also be:
//public int getIndex(int i, int[] nums) {
//        int n = nums.length;
//        return (i + nums[i]) % n + (i + nums[i] < 0)*n;
//    }
    
//    Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.
//    Example 2: Given the array [-1, 2], there is no loop.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(circularArrayLoop(new int[]{2, -1, 1, 2, 2}));

	}

}
