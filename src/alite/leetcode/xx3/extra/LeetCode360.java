package alite.leetcode.xx3.extra;
/**'
 * LeetCode 360 - Sort Transformed Array

http://www.cnblogs.com/grandyang/p/5595614.html
Given a sorted array of integers nums and integer values a, b and c. Apply a function of the form f(x) = ax2 +bx + c to each element x
 in the array.
The returned array must be in sorted order.
Expected time complexity: O(n)
Example:
nums = [-4, -2, 2, 4], a = 1, b = 3, c = 5,

Result: [3, 9, 15, 33]

nums = [-4, -2, 2, 4], a = -1, b = 3, c = 5

Result: [-23, -5, 1, 7]

这道题给了我们一个数组，又给了我们一个抛物线的三个系数，让我们求带入抛物线方程后求出的数组成的有序数组。那么我们首先来看O(nlgn)的解法，这个解法没啥可说的，
就是每个算出来再排序，这里我们用了最小堆来帮助我们排序
    vector<int> sortTransformedArray(vector<int>& nums, int a, int b, int c) {
        vector<int> res;
        priority_queue<int, vector<int>, greater<int>> q;
        for (auto d : nums) {
            q.push(a * d * d + b * d + c);
        }
        while (!q.empty()) {
            res.push_back(q.top()); q.pop();
        }
        return res;
    }

https://discuss.leetcode.com/topic/50373/simple-java-solution-using-priority-queue

    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i = 0;i<nums.length;i++){
            nums[i] = (a*nums[i]*nums[i]) + (b*nums[i]) + c;
            q.offer(nums[i]);
        }
        int i = 0;
        while(!q.isEmpty()){
            nums[i++] = q.poll();
        }
        return nums;
    }
但是题目中的要求让我们在O(n)中实现，那么我们只能另辟蹊径。其实这道题用到了大量的高中所学的关于抛物线的数学知识，我们知道，
对于一个方程f(x) = ax2 + bx + c 来说，如果a>0，则抛物线开口朝上，那么两端的值比中间的大，而如果a<0，则抛物线开口朝下，则两端的值比中间的小。而当a=0时，
则为直线方法，是单调递增或递减的。那么我们可以利用这个性质来解题，题目中说明了给定数组nums是有序的，如果不是有序的，我想很难有O(n)的解法。
正因为输入数组是有序的，我们可以根据a来分情况讨论：
当a>0，说明两端的值比中间的值大，那么此时我们从结果res后往前填数，用两个指针分别指向nums数组的开头和结尾，指向的两个数就是抛物线两端的数，
将它们之中较大的数先存入res的末尾，然后指针向中间移，重复比较过程，直到把res都填满。
当a<0，说明两端的值比中间的小，那么我们从res的前面往后填，用两个指针分别指向nums数组的开头和结尾，指向的两个数就是抛物线两端的数，
将它们之中较小的数先存入res的开头，然后指针向中间移，重复比较过程，直到把res都填满。
当a=0，函数是单调递增或递减的，那么从前往后填和从后往前填都可以，我们可以将这种情况和a>0合并。
https://segmentfault.com/a/1190000005771017
抛物线中点: -b/2a
分这么几种情况：
a > 0，
a < 0, 
a == 0 && b >= 0, 
a == 0 && b < 0
给的x数组是排序的，搞两个指针从两边比较
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int[] result = new int[nums.length];
        int start = 0, end = nums.length - 1;
        int nextIndex = 0;
        if (a > 0 || (a == 0 && b >= 0)) 
            nextIndex = nums.length - 1;
        if (a < 0 || (a == 0 && b < 0))
            nextIndex = 0;
        double mid = -1 * ((b * 1.0)  / (2.0 * a));
        while (start <= end) {
            if (a > 0 || (a == 0 && b >= 0)) {
                if (Math.abs(mid - nums[start]) > Math.abs(nums[end] - mid)) {
                    int x = nums[start++];
                    result[nextIndex--] = a * x * x + b * x + c;
                }
                else {
                    int x = nums[end--];
                    result[nextIndex--] = a * x * x + b * x + c;
                }
            }
            else if (a < 0 || (a == 0 && b < 0)){
                if (Math.abs(mid - nums[start]) > Math.abs(nums[end] - mid)) {
                    int x = nums[start++];
                    result[nextIndex++] = a * x * x + b * x + c;
                }
                else {
                    int x = nums[end--];
                    result[nextIndex++] = a * x * x + b * x + c;
                }
            }
        }
        return result;
    }
https://discuss.leetcode.com/topic/50497/my-easy-to-understand-java-ac-solution-using-two-pointers
For a parabola,
if a >= 0, the min value is at its vertex. So our our sorting should goes from two end points towards the vertex, high to low.
if a < 0, the max value is at its vertex. So our sort goes the opposite way.
http://juneend.blogspot.com/2016/07/360-sort-transformed-array.html
If a >=0, the minimum value is at the array's vertex. So we need to move the two end pointers toward the vertex and output from right to left.
If a <0, the maximum value is at the array's vertex. So we need to move the two end pointers toward the vertex but output from left to right.
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int[] res = new int[nums.length];
        int start = 0;
        int end = nums.length - 1;
        int i = a >= 0 ? nums.length - 1 : 0;
        while(start <= end) {
            int startNum = getNum(nums[start], a, b, c);
            int endNum = getNum(nums[end], a, b, c);
            if(a >= 0) {
                if(startNum >= endNum) {
                    res[i--] = startNum;
                    start++;
                }
                else{
                    res[i--] = endNum;
                    end--;
                }
            }
            else{
                if(startNum <= endNum) {
                    res[i++] = startNum;
                    start++;
                }
                else{
                    res[i++] = endNum;
                    end--;
                }
            }
        }
        return res;
    }
    public int getNum(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }
https://leetcode.com/discuss/108831/java-o-n-incredibly-short-yet-easy-to-understand-ac-solution
the problem seems to have many cases a>0, a=0,a<0, (when a=0, b>0, b<0). However, they can be combined into just 2 cases: a>0 or a<0
1.a>0, two ends in original array are bigger than center if you learned middle school math before.
2.a<0, center is bigger than two ends.
so use two pointers i, j and do a merge-sort like process. depending on sign of a, you may want to start from the beginning or end of the transformed array. For a==0 case, it does not matter what b's sign is. The function is monotonically increasing or decreasing. you can start with either beginning or end.
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int n = nums.length;
        int[] sorted = new int[n];
        int i = 0, j = n - 1;
        int index = a >= 0 ? n - 1 : 0;
        while (i <= j) {
            if (a >= 0) {
                sorted[index--] = quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[i++], a, b, c) : quad(nums[j--], a, b, c);
            } else {
                sorted[index++] = quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[j--], a, b, c) : quad(nums[i++], a, b, c);
            }
        }
        return sorted;
    }

    private int quad(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }

X. Merge sort
https://discuss.leetcode.com/topic/48487/share-my-simple-java-solution-similar-to-merge-sort
public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
    int result[] = new int[nums.length];
    int i = 0, j = nums.length - 1;
    if(a > 0){
        int index = result.length - 1;
        while(i <= j){
            int left = a*nums[i]*nums[i] + b*nums[i] + c;
            int right = a*nums[j]*nums[j] + b*nums[j] + c;
            if(left < right){
                result[index--] = right;
                j--;
            }
            else{
                result[index--] = left;
                i++;
            }
        }
    }
    else{
        int index = 0;
        while(i <= j){
            int left = a*nums[i]*nums[i] + b*nums[i] + c;
            int right = a*nums[j]*nums[j] + b*nums[j] + c;
            if(left < right){
                result[index++] = left;
                i++;
            }
            else{
                result[index++] = right;
                j--;
            }
        }
    }
    return result;
}
https://discuss.leetcode.com/topic/49462/7-line-and-9-line-concise-solutions-with-explanation
    vector<int> sortTransformedArray(vector<int>& nums, int a, int b, int c) {
        vector<int> res(nums.size()), y;
        for (auto &x: nums) y.emplace_back(a*x*x+b*x+c);

        int i = 0, j = nums.size()-1, k = a<0? 0: nums.size()-1;
        for(; i <= j; k += a<0? 1: -1) {
            if (y[i] < y[j] == a < 0) res[k] = y[i++];
            else                      res[k] = y[j--];
        }
        return res;
    }

https://discuss.leetcode.com/topic/48436/simple-and-concise-o-n-solution
ax2+bx+c = a(x + b/2a)2 + c - b2/4a
So use offset as c - b2/4a
ax2+bx+c - offset will be always positive or negative
Then iteratively pop max (or min if a is negative) from both ends and push it into final array
function sortTransformedArray(nums, a, b, c) {
    var arr = nums.map(n => a * n * n + b * n + c);
    var offset = a ? c - (b * b) / (4 * a) : Math.min(arr[0], arr.slice(-1)[0]);
    var res = [];

    for (var l = 0, r = arr.length - 1; l <= r;) {
        if (Math.abs(arr[l] - offset) >= Math.abs(arr[r] - offset)) {
            res.push(arr[l++]);
        } else {
            res.push(arr[r--]);
        }
    }

    return res[0] > res[res.length - 1] ? res.reverse() : res;
}
 * @author het
 *
 */
public class LeetCode360 {
//	但是题目中的要求让我们在O(n)中实现，那么我们只能另辟蹊径。其实这道题用到了大量的高中所学的关于抛物线的数学知识，我们知道，
//	对于一个方程f(x) = ax2 + bx + c 来说，如果a>0，则抛物线开口朝上，那么两端的值比中间的大，而如果a<0，则抛物线开口朝下，则两端的值比中间的小。而当a=0时，
//	则为直线方法，是单调递增或递减的。那么我们可以利用这个性质来解题，题目中说明了给定数组nums是有序的，如果不是有序的，我想很难有O(n)的解法。
//	正因为输入数组是有序的，我们可以根据a来分情况讨论：
//	当a>0，说明两端的值比中间的值大，那么此时我们从结果res后往前填数，用两个指针分别指向nums数组的开头和结尾，指向的两个数就是抛物线两端的数，
//	将它们之中较大的数先存入res的末尾，然后指针向中间移，重复比较过程，直到把res都填满。
//	当a<0，说明两端的值比中间的小，那么我们从res的前面往后填，用两个指针分别指向nums数组的开头和结尾，指向的两个数就是抛物线两端的数，
//	将它们之中较小的数先存入res的开头，然后指针向中间移，重复比较过程，直到把res都填满。
//	当a=0，函数是单调递增或递减的，那么从前往后填和从后往前填都可以，我们可以将这种情况和a>0合并。
//	https://segmentfault.com/a/1190000005771017
//	抛物线中点: -b/2a
//	分这么几种情况：
//	a > 0，
//	a < 0, 
//	a == 0 && b >= 0, 
//	a == 0 && b < 0
//	给的x数组是排序的，搞两个指针从两边比较
	    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
	        int[] result = new int[nums.length];
	        int start = 0, end = nums.length - 1;
	        int nextIndex = 0;
	        if (a > 0 || (a == 0 && b >= 0)) 
	            nextIndex = nums.length - 1;
	        if (a < 0 || (a == 0 && b < 0))
	            nextIndex = 0;
	        double mid = -1 * ((b * 1.0)  / (2.0 * a));
	        while (start <= end) {
	            if (a > 0 || (a == 0 && b >= 0)) {
	                if (Math.abs(mid - nums[start]) > Math.abs(nums[end] - mid)) {
	                    int x = nums[start++];
	                    result[nextIndex--] = a * x * x + b * x + c;
	                }
	                else {
	                    int x = nums[end--];
	                    result[nextIndex--] = a * x * x + b * x + c;
	                }
	            }
	            else if (a < 0 || (a == 0 && b < 0)){
	                if (Math.abs(mid - nums[start]) > Math.abs(nums[end] - mid)) {
	                    int x = nums[start++];
	                    result[nextIndex++] = a * x * x + b * x + c;
	                }
	                else {
	                    int x = nums[end--];
	                    result[nextIndex++] = a * x * x + b * x + c;
	                }
	            }
	        }
	        return result;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
