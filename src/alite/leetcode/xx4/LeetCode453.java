package alite.leetcode.xx4;
/**
 * https://leetcode.com/problems/minimum-moves-to-equal-array-elements/
Given a non-empty integer array of size n, find the minimum number of moves 
required to make all array elements equal, where a move is incrementing n - 1 elements by 1.
Example:
Input:
[1,2,3]

Output:
3

Explanation:
Only three moves are needed (remember each move increments two elements):

[1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]

https://discuss.leetcode.com/topic/66737/it-is-a-math-question
let's define sum as the sum of all the numbers, before any moves;
 minNum as the min number int the list; n is the length of the list;
After, say m moves, we get all the numbers as x , and we will get the following equation
 sum + m * (n - 1) = x * n
and actually,
  x = minNum + m
and finally, we will get
  sum - minNum * n = m
before all elements reach to the same value, every time (n-1) elements add one meaning 
only one element remains the same, which of cause should be the max value
( should be different from min value, otherwise they have reached the same value) of the array.
 So, with that being said, every time doing add one for (n-1) operation, the min value +1. 
 If it takes m moves to reach x, then x=minNum+m.

As long as min not equals to max, you keep doing add 1 to (n-1) elements, then min value for sure +1, I believe you understand this part already. So, I suppose your question is what if m moves means min value once equals to max and somehow this array adds 1 to (n-1) elements which makes it to go for another round to have all equal elements. If you think carefully, you will find out that by doing this, there exists a single add-1-operation that min value doesn't get to +1.
So, if you want to get the second minimum moves to reach to all equal state, then you can have these equations instead:(I am using OP's equations so I think you can better understand and compare the differences)
for second minimum m moves:
sum+m*(n-1)=x * n
x=minNum+m-1 (there is a single time min value doesn't +1)
sum-minNum * n+n=m (m gets n moves more compared to the minimum value, because it also means you need to decrease every element by 1 after it reaches to the first equality, easy to understand, right?)
Let me explain why x = minNum + m
our goal is :increment minNum to be equal to maxNum
No matter how many add operations are executed,the goal won't change.
Every time we do the add operation,the min number in the array must participate in.
After an add operation,the minNum is still the min number
So the minNum participate in every add operation
So x = minNum + m
https://discuss.leetcode.com/topic/66771/what-if-we-are-not-smart-enough-to-come-up-with-decrease-1-here-is-how-we-do-it/

https://discuss.leetcode.com/topic/66557/java-o-n-solution-short
Adding 1 to n - 1 elements is the same as subtracting 1 from one element, w.r.t goal of making the elements in the array equal.
So, best way to do this is make all the elements in the array equal to the min element.
sum(array) - n * minimum
    public int minMoves(int[] nums) {
        if (nums.length == 0) return 0;
        int min = nums[0];
        for (int n : nums) min = Math.min(min, n);
        int res = 0;
        for (int n : nums) res += n - min;
        return res;
    }
https://discuss.leetcode.com/topic/66562/simple-one-liners
public int minMoves(int[] nums) {
    return IntStream.of(nums).sum() - nums.length * IntStream.of(nums).min().getAsInt();
}
https://tech.liuchao.me/2016/11/leetcode-solution-453/

    public int minMoves(int[] nums) {

        int sum = 0, min = nums[0];


        for (int num : nums) {

            sum += num;

            min = Math.min(min, num);

        }


        return sum - nums.length * min;

    }
http://bookshadow.com/weblog/2016/11/06/leetcode-minimum-moves-to-equal-array-elements/
一次移动将n - 1个元素加1，等价于将剩下的1个元素减1。
因此累加数组中各元素与最小值之差即可。
    def minMoves(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        return sum(nums) - min(nums) * len(nums)

https://discuss.leetcode.com/topic/66575/thinking-process-of-solving-problems-use-java-37ms
I try to find the max num every time, and +1 to rest num, code like below:
    public int minMoves(int[] nums) {
        return helper(nums, 0);
    }

    private int helper(int[] nums, int count) {
        int max = 0;
        int total = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[max]) max = i;
            else if (nums[i] == nums[max]) total++;
        }
        if (total == nums.length) return count;

        for (int i = 0; i < nums.length; i++) {
            if (i != max) nums[i]++;
        }
        return helper(nums, ++count);
    }
but when the nums is [1, 1, 2147483647], it will be java.lang.StackOverflowError.
So I try to improve in this way that find the max and min num every time, and +(max - min) to rest num, code like below:
    public int minMoves(int[] nums) {
        return helper(nums, 0);
    }

    private int helper(int[] nums, int count) {
        int max = 0, min = 0;
        int total = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[max]) max = i;
            else if (nums[i] < nums[min]) min = i;
            else if (nums[i] == nums[max]) total++;
        }
        if (total == nums.length) return count;

        int dis = nums[max] - nums[min];
        for (int i = 0; i < nums.length; i++) {
            if (i != max) nums[i] += dis;
        }
        return helper(nums, count + dis);
    }
But when the num length is bigger to 10000, it wil be Time Limit Exceeded.
Then, I want to implements it by no recursive way and use insert sort every time in order to reduce unnecessary traversal operation. code like below:
    public int minMoves(int[] nums) {
        int res = 0;
        int n = nums.length;
        Arrays.sort(nums);

        while (nums[n - 1] != nums[0]) {
            int dis = nums[n - 1] - nums[0];
            for (int i = 0; i < n - 1; i++) {
                nums[i] += dis;
            }
            res += dis;

            //insert sort
            int max = nums[n - 1];
            int i = n - 2;
            while (i >= 0) {
                if (nums[i] > max) nums[i + 1] = nums[i--];
                else break;
            }
            nums[i + 1] = max;
        }

        return res;
    }
But it still Time Limit Exceeded.
============== The final solution is as follows ==============
The final flash, I though that should we use dynamic programming?
[step] is The number of steps arrive at the state of [all equal]
[finalNum] is The value of the state of [all equal]
we can know that
step[i] = (step[i-1] + num[i]) - finalNum[i-1] + step[i-1]
finalNum[i] = num[i] + step[i-1]
    public int minMoves(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int step = 0;
        int finalNum = nums[0];

        for (int i = 1; i < n; i++) {
            int tmp = finalNum;
            finalNum = nums[i] + step;
            if (finalNum == tmp) continue;   //attention!!
            step = finalNum - tmp + step;
        }

        return step;
    }
 * @author het
 *
 */
public class LeetCode453 {
//	Adding 1 to n - 1 elements is the same as subtracting 1 from one element, w.r.t goal of making the elements in the array equal.
//	So, best way to do this is make all the elements in the array equal to the min element.
//	sum(array) - n * minimum
	    public int minMoves(int[] nums) {
	        if (nums.length == 0) return 0;
	        int min = nums[0];
	        for (int n : nums) min = Math.min(min, n);
	        int res = 0;
	        for (int n : nums) res += n - min;
	        return res;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
