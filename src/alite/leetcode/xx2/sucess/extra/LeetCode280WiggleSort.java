package alite.leetcode.xx2.sucess.extra;
/**
 * Leetcode 280 Wiggle Sort
Given an array of integers, sort the array into a wave like array, namely
a1 >= a2 <= a3 >= a4 <= a5.....
X. https://segmentfault.com/a/1190000003783283
题目对摇摆排序的定义有两部分：
如果i是奇数，nums[i] >= nums[i - 1]
如果i是偶数，nums[i] <= nums[i - 1]
所以我们只要遍历一遍数组，把不符合的情况交换一下就行了。具体来说，如果nums[i] > nums[i - 1]， 则交换以后肯定有nums[i] <= nums[i - 1]。
    public void wiggleSort(int[] nums) {
        for(int i = 1; i < nums.length; i++){
            // 需要交换的情况：奇数时nums[i] < nums[i - 1]或偶数时nums[i] > nums[i - 1]
            if((i % 2 == 1 && nums[i] < nums[i-1]) || (i % 2 == 0 && nums[i] > nums[i-1])){
                int tmp = nums[i-1];
                nums[i-1] = nums[i];
                nums[i] = tmp;
            }
        }
    }
http://algobox.org/wiggle-sort/

    public void wiggleSort(int[] nums) {

        for (int i = 1; i < nums.length; ++i) {

            if ((i % 2 == 1) != (nums[i] > nums[i - 1])) {

                int cache = nums[i];

                nums[i] = nums[i - 1];

                nums[i - 1] = cache;

            }

        }

    }
http://www.geeksforgeeks.org/sort-array-wave-form-2/
This can be done in O(n) time by doing a single traversal of given array. The idea is based on the fact that if we make sure that all even positioned (at index 0, 2, 4, ..) elements are greater than their adjacent odd elements, we don’t need to worry about odd positioned element. Following are simple steps.
1) Traverse all even positioned elements of input array, and do following.
….a) If current element is smaller than previous odd element, swap previous and current.
….b) If current element is smaller than next odd element, swap next and current.

// This function sorts arr[0..n-1] in wave form, i.e., arr[0] >= 

// arr[1] <= arr[2] >= arr[3] <= arr[4] >= arr[5] ....

void sortInWave(int arr[], int n)

{

    // Traverse all even elements

    for (int i = 0; i < n; i+=2)

    {

        // If current even element is smaller than previous

        if (i>0 && arr[i-1] > arr[i] )

            swap(&arr[i], &arr[i-1]);


        // If current even element is smaller than next

        if (i<n-1 && arr[i] < arr[i+1] )

            swap(&arr[i], &arr[i + 1]);

    }

}
http://sidbai.github.io/2015/07/06/Wiggle-Sort/
void wiggle_sort(vector<int>& arr) {
    for (int i = 1; i < arr.size(); i++) {
        if ((i % 2 == 1 && arr[i] < arr[i - 1]) ||
            (i % 2 == 0 && arr[i] > arr[i - 1])) {
            swap(arr[i], arr[i - 1]);
        }
    }
}
http://shibaili.blogspot.com/2015/09/day-125-279-perfect-squares.html

    void wiggleSort(vector<int>& nums) {

        for (int i = 0; i < (int)nums.size() - 1; i++) {

            if (i % 2 == 0 && nums[i] > nums[i + 1]) {

                swap(nums[i],nums[i + 1]);

            }else if (i % 2 == 1 && nums[i] < nums[i + 1]) {

                swap(nums[i],nums[i + 1]);

            }

        }
There are two O(n) solutions for this problem that does not require ordering. 
1. You can find the median in O(n) and then rearrange the elements around the median 
float median = find_median(a); 
int even = 0;
int odd = 1;
for(int i = 0; i < n ; i++)
{
if(a[i] < median)
{
  b[odd] = a[i];
  odd = odd + 2;
}
else
{
  b[even] = a[i];
  even = even + 2;  
}
}
2. (Better Solution) If you notice the desired ordering, the even numbered elements are bigger (or equal) than the next element, and the odd numbered elements are less than (or equal) than the next element, of course I am assuming the array is 0 offset. 

So, you can iterate the array and swap the elements that doesn't match this arrangements,e.g., swap A[i] and A[i+1], when i is even and A[i] < A[i + 1].

public static int[] createWave(int[] arr){

  for(int i=0; i<arr.length-1;i++){ 

   if(i%2 == 0){
    if(arr[i] < arr[i+1]){
     arr = swap(arr,i,i+1);
    }
   }else{
    if(arr[i] > arr[i+1]){
     arr = swap(arr,i,i+1);
    }
   }
  }
  
  return arr;
 }

 private static int[] swap(int[] arr,int i,int j){

  int temp = arr[i];
  arr[i] = arr[j];
  arr[j] = temp;
  return arr;
 }
http://www.fgdsb.com/2015/01/20/special-sorting/
http://yuanhsh.iteye.com/blog/2206429
public void wiggle_sort(int[] arr) {  
    int n = arr.length;  
    if(n <= 1) return;  
    boolean inc = true;  
    int prev = arr[0];  
    for(int i=1; i<n; i++) {  
        if((inc && prev <= arr[i]) || (!inc && prev >= arr[i])) {  
            arr[i-1] = prev;  
            prev = arr[i];  
        } else {  
            arr[i-1] = arr[i];  
        }  
        inc = !inc;  
    }  
} 
http://zwzbill8.blogspot.com/2015/05/wiggle-sort.html

void wiggle_sort(vector<int>& arr) {    
    if(arr.size() < 2) return;    
    int flag = 1;    
    int prev = arr[0];    
    for(int i=1; i<arr.size(); i++) {    
        if(prev*flag <= arr[i]*flag) {    
            arr[i-1] = prev;    
            prev = arr[i];    
        } else {    
            // arr[i-1] = arr[i];    
            swap(arr[i-1], arr[i]);    
        }    
        flag = -flag;    
    }    
}
http://hehejun.blogspot.com/2015/01/algorithmreorder-array.html
Given an array with distinct elements a1, a2, a3...., reorder it as a1 < a2 > a3 < a4 >.......

对于这一题我们可以先分析array是sorted的话，我们的解法应该是什么样
如果array是偶数长度的话，我们只要从(a2，an-1）这一对开始每隔一对交换一对直到我们到达中间一对即可，这样可以的原因是比如我们交换a2这一对的时候换回来的值可以保证a1 < a2 > a3，对于a4那一对是一样的，注意只有长度为偶数的时候这个方法才work，是奇数的话中间总会三个是递增或者递减的，这是因为交换后中间元素的两边同时被交换的元素影响了。
如果array是奇数的话，我们把它转化成偶数即可，我们除掉第一个元素，剩下的元素是长度为偶数的数组，同时a1满足a1< a2（交换后的a2）的条件
所以，如果数组是sorted的话，我们用n / 2次交换就可以完成，如果数组不是sorted的话，我们进行n次交换也可以完成，具体的思想是如果A[k]和A[k+ 1]如果不满足他们应该有的关系，就交换，下面我们证明这样是可行的：
如果实际上A[k] < A[k + 1]，但他们的关系应该是A[k] > A[k + 1]。因为A[k]之前的部分是reordered的，我们可以得知A[k - 1] < A[k]，那么实际上A[k - 1] < A[k + 1]，那么交换A[k]，A[k + 1]后A[k - 1] < A[k] > A[k + 1]也成立
如果实际上A[k] > A[k + 1]，但他们的关系应该是A[k] < A[k + 1]。根据reorder的结果我们可以得知A[k - 1] >A[k]，那么实际上A[k - 1] > A[k + 1]，那么交换A[k]，A[k + 1]后A[k - 1] > A[k] < A[k + 1]也成立

public void reorderSortedArray(int[] arr) {  if (arr == null)
return;
int len = arr.length;
for (int i = 1; i <= (len - 1) / 2; i += 2) {
if (len % 2 == 0)
swap(arr, i, len - 1 - i);
else 
swap(arr, i, len - i);
}
}
public void reorderArray(int[] arr) {
if (arr == null)
return;
int len = arr.length;
for (int i = 0; i < len - 1; i++) {
if (i % 2 == 0 && arr[i] > arr[i + 1])
swap(arr, i, i + 1);
if (i % 2 == 1 && arr[i] < arr[i + 1])
swap(arr, i, i + 1);
}
}
private void swap(int[] arr, int i, int j) {
int temp = arr[i];
arr[i] = arr[j];
arr[j] = temp;
}
private boolean isReordered(int[] arr) {
int len = arr.length;
for (int i = 0; i < len - 1; i++) {
if (i % 2 == 0 && arr[i] > arr[i + 1])
return false;
if (i % 2 == 1 && arr[i] < arr[i + 1])
return false;
}
return true;
}
https://gist.github.com/gcrfelix/bded01c86330ac38a561
// Solution 1: O(n)
  public void sort(int[] nums) {
    int n = nums.length;
    for(int i=1; i<n; i++) {
      if((i%2==0 && nums[i] > nums[i-1]) || (i%2!=0 && nums[i] < nums[i-1])) {
        swap(nums, i, i-1);
      }
    }
  }
  // Soluton 2: O(nlogn)
  public void sort(int[] nums) {
    int n = nums.length;
    Arrays.sort(nums);
    int index = 1;
    while(index < n-1) {
      swap(nums, index, index+1);
      index += 2;
    }
  }
http://www.shuatiblog.com/blog/2015/11/21/swizzle-sort/
public int[] solve(int[] input) {
    boolean incr = true;
    int len = input.length;
    int p = 1;
    while (p < len) {
        if (incr ^ (input[p - 1] < input[p])) {
            Common.swap(input, p - 1, p);
        }
        p++;
        incr = !incr;
    }
    return input;
}
https://leetcode.com/discuss/71750/what-about-this-follow-up-question
Given an unsorted array nums, reorder it in-place such that nums[0] < nums[1] > nums[2] < nums[3]... For example, given nums = [1, 1, 1, 3, 3, 3, 2, 2, 2], one possible answer is [1, 3, 2, 3, 2, 3, 1, 2, 1].
Note: the difference between this and the former question is that two adjacent number cannot be the same.
O(n) is possible.
First find the median
count the number of elements smaller than, larger than and equal to median
put the ones equal to median in the correct place in wiggle result, so that they do not adjacent and they leave enough even slots for smaller and odd slots for larger.
put all the other number smaller than median in any of the rest even slots
put all the other number larger than median in any of the rest odd slots
All step can be done in O(n) and in place. Therefore it can be done in O(n) time O(1) space
For example 1 3 3 1 2 2
median is 2
there are two 2s, two 1s and two 3s
we need leave 2 even slots for 1s and 2 odd slots for 3s. So we take one even and one odd for 2s.
To make sure they are not adjacent, we can be greedy and take 0 and 5.
put the 1s in 4 and 2
put the 3s in 3 and 1
2 3 1 3 1 2
Correct place means leave exactly correct even and odd slots for the small and large, yet they dont meet each other.
For example. for n numbers, suppose we have n/3 numbers smaller than median and n/3 numbers larger than median and n/3 numbers equal median.
You can put the median anywhere you want but they may not adjacent and when they settled the odd and even slots left should be n/3
My code did need O(n) space, yes. Apparently I didn't save it, but it was something like this:
m = median(nums)
ordered = [x for x in nums if x<m] + [m]*nums.count(m) + [x for x in nums if x>m]
half = (len(nums) + 1) / 2
nums[::2], nums[1::2] = ordered[:half], ordered[half:]
I guess it can be done in-place, think of (unstable) partitioning just with unusual indexing. Not like usual to indexes 0,1,2,3,4,5 but directly to indexes 0,2,4,1,3,5 (evens followed by odds). No idea how nice/ugly it would be.
https://segmentfault.com/a/1190000003783283
根据题目的定义，摇摆排序的方法将会很多种。我们可以先将数组排序，这时候从第3个元素开始，将第3个元素和第2个元素交换。然后再从第5个元素开始，将第5个元素和第4个元素交换，以此类推。就能满足题目要求。
    public void wiggleSort(int[] nums) {
        // 先将数组排序
        Arrays.sort(nums);
        // 将数组中一对一对交换
        for(int i = 2; i < nums.length; i+=2){
            int tmp = nums[i-1];
            nums[i-1] = nums[i];
            nums[i] = tmp;
        }
    }
Peaks and Valleys: In an array of integers, a "peak" is an element which is greater than or equal
to the adjacent integers and a "valley" is an element which is less than or equal to the adjacent
integers. For example, in the array {5, 8, 6, 2, 3, 4, 6}, {8, 6} are peaks and {5, 2} are valleys. Given an array of integers, sort the array into an alternating sequence of peaks and valleys.
EXAMPLE
Input: {5, 3, 1, 2, 3}

Output: {5, 1, 3, 2, 3}

We can try is doing a normal sort and then "fixing" the array into an alternating sequence of peaks and valleys.
void sortValleyPeak(int[] array) {
        for (int i= 1; i < array.length; i += 2) {
                int biggestindex = maxindex(array, i - 1, i, i + 1);
                if (i != biggestindex) {
                        swap(array, i, biggestindex);
                }
        }
}
int maxindex(int[] array, int a, int b, int c) {
        int len = array.length;
        int aValue = a > = 0 && a < len ? array[a] : Integer.MIN_VALUE;
        int bValue = b >= 0 && b < len ? array[b] : Integer.MIN_VALUE;
        int cValue = c >= 0 && c < len ? array[c] : Integer.MIN_VALUE;
        int max= Math.max(aValue, Math.max(bValue, cValue));
        if (aValue == max) return a;
        else if (bValue == max) return b;
        else return c;
}

void sortValleyPeak(int[] array) {
        Arrays.sort(array);
        for (int i= 1; i < array.length; i += 2) {
                swap(array, i - 1, i);
        }
}
void swap(int[] array, int left,int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
}
 * @author het
 *
 */
public class LeetCode280WiggleSort {
//	题目对摇摆排序的定义有两部分：
//	如果i是奇数，nums[i] >= nums[i - 1]
//	如果i是偶数，nums[i] <= nums[i - 1]
//	所以我们只要遍历一遍数组，把不符合的情况交换一下就行了。具体来说，如果nums[i] > nums[i - 1]， 则交换以后肯定有nums[i] <= nums[i - 1]。
	    public void wiggleSort(int[] nums) {
	        for(int i = 1; i < nums.length; i++){
	            // 需要交换的情况：奇数时nums[i] < nums[i - 1]或偶数时nums[i] > nums[i - 1]
	            if((i % 2 == 1 && nums[i] < nums[i-1]) || (i % 2 == 0 && nums[i] > nums[i-1])){
	                int tmp = nums[i-1];
	                nums[i-1] = nums[i];
	                nums[i] = tmp;
	            }
	        }
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
