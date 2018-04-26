package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 496 - Next Greater Element I

https://leetcode.com/problems/next-greater-element-i/
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. 
Find all the next greater numbers for nums1's elements in the corresponding places of nums2.
The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. 
If it does not exist, output -1 for this number.
Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
Note:
All elements in nums1 and nums2 are unique.
The length of both nums1 and nums2 would not exceed 1000.
https://discuss.leetcode.com/topic/77916/java-10-lines-linear-time-complexity-o-n-with-explanation
Suppose we have a decreasing sequence followed by a greater number
For example [5, 4, 3, 2, 1, 6] then the greater number 6 is the next greater element for all previous numbers in the sequence
We use a stack to keep a decreasing sub-sequence, whenever we see a number x greater than stack.peek() we pop all elements less than x and for all the popped ones, their next greater element is x
For example [9, 8, 7, 3, 2, 1, 6]
The stack will first contain [9, 8, 7, 3, 2, 1] and then we see 6 which is greater than 1 so we pop 1 2 3 whose next greater element should be 6
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
        Stack<Integer> stack = new Stack<>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() < num)
                map.put(stack.pop(), num);
            stack.push(num);
        }   
        for (int i = 0; i < findNums.length; i++)
            findNums[i] = map.getOrDefault(findNums[i], -1);
        return findNums;
    }
https://discuss.leetcode.com/topic/77880/simple-o-m-n-java-solution-using-stack
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        int[] ret = new int[findNums.length];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = nums.length - 1; i >= 0; i--) {
            while(!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            if(stack.isEmpty()) map.put(nums[i], -1);
            else map.put(nums[i], stack.peek());
            stack.push(nums[i]);
        }
        for(int i = 0; i < findNums.length; i++) {
            ret[i] = map.get(findNums[i]);
        }
        return ret;
    }

http://bookshadow.com/weblog/2017/02/05/leetcode-next-greater-element-i/
栈stack维护nums的递减子集，记nums的当前元素为n，栈顶元素为top

重复弹出栈顶，直到stack为空，或者top大于n为止

将所有被弹出元素的next greater element置为n
X.
时间复杂度O(n * m) 其中n为nums的长度，m为findNums的长度
https://discuss.leetcode.com/topic/77904/easy-to-understand-o-mn-java-solution
public int[] nextGreaterElement(int[] findNums, int[] nums) {
    if(findNums == null ||  nums == null || 
       findNums.length == 0 || nums.length == 0 || 
       findNums.length > nums.length) return new int[0];
    
    int m = findNums.length;
    int n = nums.length;
    int[] result = new int[m];
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    
    for(int j = 0; j < n; ++j){
        map.put(nums[j], j);
    }
    for(int i = 0; i < m; ++i){
        int j = map.get(findNums[i]);
        for(; j < n; ++j){
            if(nums[j] > findNums[i]) break;
        }
        result[i] = j < n ? nums[j] : -1;
    }
    return result;
}
https://discuss.leetcode.com/topic/77868/my-concise-short-solution
public int[] nextGreaterElement(int[] findNums, int[] nums) {
 int n1 = findNums.length, n2 = nums.length;
 List<Integer> list = new ArrayList<>();
 for (int i : nums) list.add(i);
 int[] res = new int[n1];
 for (int i = 0; i < n1; i++) {
  int cur = findNums[i];
  res[i] = -1;
  for (int k = list.indexOf(cur); k < n2; k++) {
   if (nums[k] > cur){
    res[i] = nums[k];
    break;
   }
  }
 }
 return res;
}
 * @author het
 *
 */
public class L496_Next_Greater_Element_I {
	 public int[] nextGreaterElement(int[] findNums, int[] nums) {
	        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
	        Stack<Integer> stack = new Stack<>();
	        for (int num : nums) {
	            while (!stack.isEmpty() && stack.peek() < num)   //  while (!stack.isEmpty() && stack.peek() < num)
	                map.put(stack.pop(), num);
	            stack.push(num);
	        }   
	        for (int i = 0; i < findNums.length; i++)
	            findNums[i] = map.getOrDefault(findNums[i], -1);
	        return findNums;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
