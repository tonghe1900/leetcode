package alite.leetcode.xx3;

import java.util.Arrays;

/**
 * LeetCode 324 - Wiggle Sort II

https://leetcode.com/problems/wiggle-sort-ii/
Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
Example:
(1) Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
(2) Given nums = [1, 3, 2, 2, 3, 1], one possible answer is [2, 3, 1, 3, 1, 2].
Note:
You may assume all input has valid answer.
Follow Up:
Can you do it in O(n) time and/or in-place with O(1) extra space?
http://bookshadow.com/weblog/2015/12/31/leetcode-wiggle-sort-ii/
O(nlogn)时间排序+O(n)空间辅助数组解法：
1. 对原数组排序，得到排序后的辅助数组snums
2. 对原数组的偶数位下标填充snums的末尾元素
3. 对原数组的奇数位下标填充snums的末尾元素
    def wiggleSort(self, nums):
        """
        :type nums: List[int]
        :rtype: void Do not return anything, modify nums in-place instead.
        """
        size = len(nums)
        snums = sorted(nums)
        for x in range(1, size, 2) + range(0, size, 2):
            nums[x] = snums.pop()
http://www.cnblogs.com/Liok3187/p/5091648.html
 5 var wiggleSort = function(nums) {
 6     var sorted = nums.slice(0).sort(sorting);
 7     var i = 0, p = 0, q = parseInt((sorted.length - 1) / 2 + 1);
 8     for(; i < sorted.length; i++){
 9         if(i % 2 === 0) nums[i] = sorted[p++];
10         else nums[i] = sorted[q++];
11     }
12     return;
13 
14     function sorting(a, b){
15         return a - b;
16     }
17 };
https://leetcode.com/discuss/76965/3-lines-python-with-explanation-proof
def wiggleSort(self, nums):
    nums.sort()
    half = len(nums[::2])
    nums[::2], nums[1::2] = nums[:half][::-1], nums[half:][::-1]
Alternative, maybe nicer, maybe not:

def wiggleSort(self, nums):
    nums.sort()
    half = len(nums[::2]) - 1
    nums[::2], nums[1::2] = nums[half::-1], nums[:half:-1]

Explanation / Proof
I put the smaller half of the numbers on the even indexes and the larger half on the odd indexes, both from right to left:
Example nums = [1,2,...,7]      Example nums = [1,2,...,8] 

Small half:  4 . 3 . 2 . 1      Small half:  4 . 3 . 2 . 1 .
Large half:  . 7 . 6 . 5 .      Large half:  . 8 . 7 . 6 . 5
--------------------------      --------------------------
Together:    4 7 3 6 2 5 1      Together:    4 8 3 7 2 6 1 5
I want:
Odd-index numbers are larger than their neighbors.
Since I put the larger numbers on the odd indexes, clearly I already have:
Odd-index numbers are larger than or equal to their neighbors.
Could they be "equal to"? That would require some number M to appear both in the smaller and the larger half. It would be the largest in the smaller half and the smallest in the larger half. Examples again, where S means some number smaller than M and L means some number larger than M.
Small half:  M . S . S . S      Small half:  M . S . S . S .
Large half:  . L . L . M .      Large half:  . L . L . L . M
--------------------------      --------------------------
Together:    M L S L S M S      Together:    M L S L S L S M
You can see the two M are quite far apart. Of course M could appear more than just twice, for example:
Small half:  M . M . S . S      Small half:  M . S . S . S .
Large half:  . L . L . M .      Large half:  . L . M . M . M
--------------------------      --------------------------
Together:    M L M L S M S      Together:    M L S M S M S M
You can see that with seven numbers, three M are no problem. And with eight numbers, four M are no problem. Should be easy to see that in general, with n numbers, floor(n/2) times M is no problem. Now, if there were more M than that, then my method would fail. But... it would also be impossible:
If n is even, then having more than n/2 times the same number clearly is unsolvable, because you'd have to put two of them next to each other, no matter how you arrange them.
If n is odd, then the only way to successfully arrange a number appearing more than floor(n/2) times is if it appears exactly floor(n/2)+1 times and you put them on all the even indexes. And to have the wiggle-property, all the other numbers would have to be larger. But then we wouldn't have an M in both the smaller and the larger half.
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int[] copy = Arrays.copyOf(nums, nums.length);
        int smallEndIndex = (nums.length + 1) / 2 - 1;
        int largeEndIndex = nums.length - 1;
        for (int i = 0; i < nums.length; i++)
            nums[i] = i % 2 == 0 ? copy[smallEndIndex - i / 2] : copy[largeEndIndex - i / 2];

    }
http://www.hrwhisper.me/leetcode-wiggle-sort-ii/
给定一个数组，要求进行如下排序： 奇数的位置要大于两边。 如 nums[1] > nums[0]  ，nums[1] > nums[2]
思路
排序，然后两边分别取，复杂度O(nlogn)

    public void wiggleSort(int[] nums) {

        Arrays.sort(nums);

        int[] temp = new int[nums.length];

        int s = (nums.length + 1) >> 1, t = nums.length;

        for (int i = 0; i < nums.length; i++) {

            temp[i] = (i & 1) == 0 ?  nums[--s] : nums[--t] ;

        }


        for (int i = 0; i < nums.length; i++)

            nums[i] = temp[i];

    }


    def wiggleSort(self, nums):

        """

        :type nums: List[int]

        :rtype: void Do not return anything, modify nums in-place instead.

        """

        temp = sorted(nums)

        s, t = (len(nums) + 1) >> 1, len(nums)

        for i in xrange(len(nums)):

            if i & 1 == 0:

                s -= 1

                nums[i] = temp[s]

            else:

                t -= 1

                nums[i] = temp[t]

X. O(n)
https://discuss.leetcode.com/topic/41464/step-by-step-explanation-of-index-mapping-in-java
There is no 'nth_element' in Java, but you can use 'findKthLargest' function from "https://leetcode.com/problems/kth-largest-element-in-an-array/" to get the median element in average O(n) time and O(1) space.

Assume your original array is {6,13,5,4,5,2}. After you get median element, the 'nums' is partially sorted such that the first half is larger or equal to the median, the second half is smaller or equal to the median, i.e
13   6   5   5   4   2

         M
In the post https://leetcode.com/discuss/76965/3-lines-python-with-explanation-proof, we have learned that , to get wiggle sort, you want to put the number in the following way such that
(1) elements smaller than the 'median' are put into the last even slots
(2) elements larger than the 'median' are put into the first odd slots
(3) the medians are put into the remaining slots.
Index :       0   1   2   3   4   5
Small half:   M       S       S    
Large half:       L       L       M
M - Median, S-Small, L-Large. In this example, we want to put {13, 6, 5} in index 1,3,5 and {5,4,2} in index {0,2,4}
The index mapping, (1 + 2*index) % (n | 1) combined with 'Color sort', will do the job.
After selecting the median element, which is 5 in this example, we continue as the following
Mapped_idx[Left] denotes the position where the next smaller-than median element  will be inserted.
Mapped_idx[Right] denotes the position where the next larger-than median element  will be inserted.


Step 1: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    5    5    4    2 
             Left
              i
                                      Right
 nums[Mapped_idx[i]] = nums[1] = 6 > 5, so it is ok to put 6 in the first odd index 1. We increment i and left.


Step 2: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    5    5    4    2 
                  Left
                   i
                                      Right
 nums[3] = 5 = 5, so it is ok to put 6 in the index 3. We increment i.


Step 3: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    5    5    4    2 
                  Left
                        i
                                     Right
 nums[5] = 2 < 5, so we want to put it to the last even index 4 (pointed by Right). So, we swap nums[Mapped_idx[i]] with nums[Mapped_idx[Right]], i.e. nums[5] with nums[4], and decrement Right. 




Step 4: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    5    5    2    4 
                  Left
                        i
                               Right
 nums[5] = 4 < 5, so we want to put it to the second last even index 2. So, we swap nums[5] with nums[2], and decrement Right. 




Step 5: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    4    5    2    5 
                  Left
                        i
                            Right
 nums[5] = 5 < 5, it is ok to put it there, we increment i.


Step 6: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        13   6    4    5    2    5 
                  Left
                             i
                            Right
 nums[0] = 13 > 5, so, we want to put it to the next odd index which is 3 (pointed by 'Left'). So, we swap nums[0] with nums[3], and increment 'Left' and 'i'.


Step Final: 
Original idx: 0    1    2    3    4    5  
Mapped idx:   1    3    5    0    2    4 
Array:        5    6    4    13   2    5 
                      Left
                                  i
                            Right
i > Right, we get the final wiggle array 5 6 4 13 2 5 !
   public void wiggleSort(int[] nums) {
        int median = findKthLargest(nums, (nums.length + 1) / 2);
        int n = nums.length;

        int left = 0, i = 0, right = n - 1;

        while (i <= right) {

            if (nums[newIndex(i,n)] > median) {
                swap(nums, newIndex(left++,n), newIndex(i++,n));
            }
            else if (nums[newIndex(i,n)] < median) {
                swap(nums, newIndex(right--,n), newIndex(i,n));
            }
            else {
                i++;
            }
        }


    }

    private int newIndex(int index, int n) {
        return (1 + 2*index) % (n | 1);
    }

public void wiggleSort(int[] nums) {
    int median=findKthLargest(nums,(nums.length+1)/2);
    int odd=1;
    int even=nums.length%2==0?nums.length-2:nums.length-1;
    int[] tmpArr=new int[nums.length];
    for(int i=0;i<nums.length;i++){
        if(nums[i]>median){
            tmpArr[odd]=nums[i];
            odd+=2;
            continue;
        }
        if(nums[i]<median){
            tmpArr[even]=nums[i];
            even-=2;
            continue;
        }
    }
    while(odd<nums.length){
        tmpArr[odd]=median;
        odd+=2;
    }
    while(even>=0){
        tmpArr[even]=median;
        even-=2;
    }
    for(int i=0;i<nums.length;i++){
        nums[i]=tmpArr[i];
    }
}

    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0)   return;
        int len = nums.length;
        int median = findMedian(0, len-1, len/2, nums);
        int left = 0, right = len-1, i = 0;
        // map current index firstly
        while (i <= right) {
            int mappedCurIndex = newIndex(i, len);
            if (nums[mappedCurIndex] > median) {
                int mappedLeftIndex = newIndex(left, len);
                swap(mappedLeftIndex, mappedCurIndex, nums);
                left++; i++;
            } else if (nums[mappedCurIndex] < median) {
                int mappedRightIndex = newIndex(right, len);
                swap(mappedCurIndex, mappedRightIndex, nums);
                right--;
            } else {
                i++;
            }
        }
    }
    // {0,1,2,3,4,5} -> {1,3,5,0,2,4}
    // find mapped new inde
    public int newIndex(int index, int len) {
        return (1+2*index) % (len|1);
    }
    // use quicksort, average O(n) time
    public int findMedian(int start, int end, int k, int[] nums) {
        if (start > end)   return Integer.MAX_VALUE;
        int pivot = nums[end];
        int indexOfWall = start;
        for (int i = start; i < end; i++) {
            if (nums[i] <= pivot) {
                swap(i, indexOfWall, nums);
                indexOfWall++;
            }
        }
        swap(indexOfWall, end, nums);
        if (indexOfWall == k) {
            return nums[indexOfWall];
        }
        else if (indexOfWall < k) {
            return findMedian(indexOfWall+1, end, k, nums);
        } else {
            return findMedian(start, indexOfWall-1, k, nums);
        }
    }
    public void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

This is to explain why mapped index formula is (1 + 2*index) % (n | 1)
Notice that by placing the median in it's place in the array we divided the array in 3 chunks: all numbers less than median are in one side, all numbers larger than median are on the other side, median is in the dead center of the array.
We want to place any a group of numbers (larger than median) in odd slots, and another group of numbers (smaller than median) in even slots. So all numbers on left of the median < n / 2 should be in odd slots, all numbers on right of the median > n / 2 should go into even slots (remember that median is its correct place at n / 2)
PS: I'm ignoring the discussion of odd/even array length for simplicity.
So let's think about the first group in the odd slots, all numbers is the left side of the array should go into these odd slots. What's the formula for it? Naturally it would be:
(1 + 2 x index) % n
All these indexes are less than n / 2 so multiplying by 2 and add 1 (to make them go to odd place) and then mod by n will always guarantee that they are less than n.
Original Index => Mapped Index
0 => (1 + 2 x 0) % 6 = 1 % 6 = 1
1 => (1 + 2 x 1) % 6 = 3 % 6 = 3
2 => (1 + 2 x 2) % 6 = 5 % 6 = 5
These are what's less than median, if we continue this with indexes 3, 4, 5 we will cycle again:
3 => (1 + 2 x 3) % 6 = 7 % 6 = 1
4 => (1 + 2 x 4) % 6 = 9 % 6 = 3
5 => (1 + 2 x 5) % 6 = 11 % 6 = 5
and we don't want that, so for indexes larger than n/2 we want them to be even, (n|1) does that exactly. What n|1 does it that it gets the next odd number to n if it was even
if n = 6 for example 110 | 1 = 111 = 7
if n = 7 for example 111 | 1 = 111 = 7
and this is what we want, instead of cycling the odd numbers again we want them to be even, and odd % odd number is even so updating the formula to :
(1 + 2*index) % (n | 1)
Then we have:
3 => (1 + 2 x 3) % 7 = 7 % 7 = 0
4 => (1 + 2 x 4) % 7 = 9 % 7 = 2
5 => (1 + 2 x 5) % 7 = 11 % 7 = 4
http://bookshadow.com/weblog/2015/12/31/leetcode-wiggle-sort-ii/
解法II O(n)时间复杂度+O(1)空间复杂度解法：
1. 使用O(n)时间复杂度的quickSelect算法，从未经排序的数组nums中选出中位数mid
2. 参照解法I的思路，将nums数组的下标x通过函数idx()从[0, 1, 2, ... , n - 1, n] 映射到 [1, 3, 5, ... , 0, 2, 4, ...]，得到新下标ix
3. 以中位数mid为界，将大于mid的元素排列在ix的较小部分，而将小于mid的元素排列在ix的较大部分。
https://leetcode.com/discuss/77133/o-n-o-1-after-median-virtual-indexing
O(n)+O(1) after median --- Virtual Indexing
First I find a median using nth_element. That only guarantees O(n) average time complexity and I don't know about space complexity. I might write this myself using O(n) time and O(1) space, but that's not what I want to show here.
This post is about what comes after that. We can use three-way partitioning to arrange the numbers so that those larger than the median come first, then those equal to the median come next, and then those smaller than the median come last.
Ordinarily, you'd then use one more phase to bring the numbers to their final positions to reach the overall wiggle-property. But I don't know a nice O(1) space way for this. Instead, I embed this right into the partitioning algorithm. That algorithm simply works with indexes 0 to n-1 as usual, but sneaky as I am, I rewire those indexes where I want the numbers to actually end up. The partitioning-algorithm doesn't even know that I'm doing that, it just works like normal (it just usesA(x) instead of nums[x]).
Let's say nums is [10,11,...,19]. Then after nth_element and ordinary partitioning, we might have this (15 is my median):
index:     0  1  2  3   4   5  6  7  8  9
number:   18 17 19 16  15  11 14 10 13 12
I rewire it so that the first spot has index 5, the second spot has index 0, etc, so that I might get this instead:
index:     5  0  6  1  7  2  8  3  9  4
number:   11 18 14 17 10 19 13 16 12 15
And 11 18 14 17 10 19 13 16 12 15 is perfectly wiggly. And the whole partitioning-to-wiggly-arrangement (everything after finding the median) only takes O(n) time and O(1) space.
#define A(i) nums[(1+2*(i)) % (n|1)]

Brilliant work! The idea is similar to the two-pointer one-pass solution to Sort Colors, where 0, 1, 2 are replaced by numbers greater than median, median, numbers smaller than median. And the mapping function redirects the indexing to realize wiggle positioning.
https://leetcode.com/discuss/77115/o-n-time-o-1-space-solution-with-detail-explanations
As @StefanPochmann pointed out in this thread, we can arrange the elements in the three categories in a deterministic way.
(1) Elements that are larger than the median: we can put them in the first few odd slots;
(2) Elements that are smaller than the median: we can put them in the last few even slots;
(3) Elements that equal the median: we can put them in the remaining slots.
Update: According to @StefanPochmann's thread, we can use a one-pass three-way partition to rearrange all elements. His idea is to re-map the indices into its destined indices, odd indices first and even indices follow.
Example:
Original Indices:    0  1  2  3  4  5  6  7  8  9 10 11
Mapped Indices:      1  3  5  7  9 11  0  2  4  6  8 10
(its reverse mapping is)
Mapped Indices:      0  1  2  3  4  5  6  7  8  9 10 11
Original Indices:    6  0  7  1  8  2  9  3 10  4 11  5   (wiggled)
In order to achieve this, we can use a function alike
int map_index(int idx, int n) {
    return (2 * idx + 1) % (n | 1);
}
where (n | 1) calculates the nearest odd that is not less than n.
void wiggleSort(vector<int>& nums) {
    int n = nums.size();

    // Find a median.
    auto midptr = nums.begin() + n / 2;
    nth_element(nums.begin(), midptr, nums.end());
    int mid = *midptr;

    // Index-rewiring.
    #define A(i) nums[(1+2*(i)) % (n|1)]

    // 3-way-partition-to-wiggly in O(n) time with O(1) space.
    int i = 0, j = 0, k = n - 1;
    while (j <= k) {
        if (A(j) > mid)
            swap(A(i++), A(j++));
        else if (A(j) < mid)
            swap(A(j), A(k--));
        else
            j++;
    }
}
https://www.hrwhisper.me/leetcode-wiggle-sort-ii/

    public void wiggleSort(int[] nums) {

        int medium = findMedium(nums, 0, nums.length - 1, (nums.length + 1) >> 1);

        int s = 0, t = nums.length - 1 , mid_index = (nums.length + 1) >> 1;

        int[] temp = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < medium)

                temp[s++] = nums[i];

            else if (nums[i] > medium)

                temp[t--] = nums[i];

        }


        while (s < mid_index) temp[s++] = medium;

        while (t >= mid_index) temp[t--] = medium;


        t = nums.length;

        for (int i = 0; i < nums.length; i++)

            nums[i] = (i & 1) == 0 ? temp[--s] : temp[--t];

    }


    private int findMedium(int[] nums, int L, int R, int k) {

        if (L >= R) return nums[R];

        int i = partition(nums, L, R);

        int cnt = i - L + 1;

        if (cnt == k) return nums[i];

        return cnt > k ? findMedium(nums, L, i - 1, k) : findMedium(nums, i + 1, R, k - cnt);

    }


    private int partition(int[] nums, int L, int R) {

        int val = nums[L];

        int i = L, j = R + 1;

        while (true) {

            while (++i < R && nums[i] < val) ;

            while (--j > L && nums[j] > val) ;

            if (i >= j) break;

            swap(nums, i, j);

        }

        swap(nums, L, j);

        return j;

    }
http://buttercola.blogspot.com/2016/01/leetcode-wiggle-sort-ii.html

    public void wiggleSort(int[] nums) {

        if (nums == null || nums.length <= 1) {

            return;

        }

         

        int n = nums.length;

         

        // Step 1: Find median of the array, return the index of the median

        int median = findMedian(nums, 0, n - 1, (n - 1) / 2);

         

        // Step 2: 3-way partition, put median in the middle, 

        // numbers less than median on the left, 

        // numbers greater than median on the right

        int left = 0;

        int right = n - 1;

        int j = 0;

        while (j <= right) {

            if (nums[j] < nums[median]) {

                swap(nums, j, left);

                j++;

                left++;

            } else if (nums[j] > nums[median]) {

                swap(nums, j, right);

                right--;

            } else {

                j++;

            }

        }

         

        int[] temp = new int[n];

        System.arraycopy(nums, 0, temp, 0, n);

         

        // Step 3: wiggle sort

        left = (n - 1) / 2;

        right = n - 1;

         

        for (int i = 0; i < n; i++) {

            if ((i & 1) == 0) {

                nums[i] = temp[left];

                left--;

            } else {

                nums[i] = temp[right];

                right--;

            }

        }

    }

     

    private int findMedian(int[] nums, int lo, int hi, int k) {

        if (lo >= hi) {

            return lo;

        }

         

        int pivot = partition(nums, lo, hi);

        if (pivot == k) {

            return pivot;

        }

         

        if (pivot > k) {

            return findMedian(nums, lo, pivot - 1, k);

        } else {

            return findMedian(nums, pivot + 1, hi, k);

        }

    }

     

    private int partition(int[] nums, int lo, int hi) {

        int pivot = nums[lo];

        int i = lo + 1;

        int j = hi;

         

        while (i <= j) {

            while (i <= j && nums[i] < pivot) {

                i++;

            }

             

            while (i <= j && nums[j] >= pivot) {

                j--;

            }

             

            if (i <= j) {

                swap(nums, i, j);

            }

        }

         

        swap(nums, lo, j);

         

        return j;

    }


    public void wiggleSort(int[] nums) {

        if (nums == null || nums.length <= 1) {

            return;

        }

         

        int n = nums.length;

         

        // Step 1: Find median of the array, return the index of the median

        int median = findMedian(nums, 0, n - 1, (n - 1) / 2);

         

        // Step 2: 3-way sort, put median in the middle, 

        // numbers less than median on the left, 

        // numbers greater than median on the right

        int[] temp = new int[n];

        int left = 0;

        int right = n - 1;

         

        for (int i = 0; i < n; i++) {

            if (nums[i] < nums[median]) {

                temp[left] = nums[i];

                left++;

            } else if (nums[i] > nums[median]) {

                temp[right] = nums[i];

                right--;

            }

        }

         

        // add median into the middle

        for (int i = left; i <= right; i++) {

            temp[i] = nums[median];

        }

         

        // Step 3: wiggle sort

        left = (n - 1) / 2;

        right = n - 1;

         

        for (int i = 0; i < n; i++) {

            if ((i & 1) == 0) {

                nums[i] = temp[left];

                left--;

            } else {

                nums[i] = temp[right];

                right--;

            }

        }

    }
http://liveasatree.blogspot.com/2016/01/leetcode-wiggle-sort-ii.html

    public void wiggleSort(int[] nums) {

        if (nums == null || nums.length <= 1) return;

        int median = getMedian(nums);


        int higher = 0, lower = nums.length - 1, current = 0;

        while (current <= lower) {

            if (nums[reIndex(current, nums.length)] == median) {

                current++;

            }


            else if (nums[reIndex(current, nums.length)] < median) {

                swap(nums, reIndex(current, nums.length), reIndex(lower--, nums.length));

            }

            else swap(nums, reIndex(current++, nums.length), reIndex(higher++, nums.length));

        }

    }

    

    private int reIndex(int index, int n) {

        return (2*index + 1) % (n | 1);

    }


    private int getMedian(int[] nums) {

        int start = 0, end = nums.length - 1, target = nums.length / 2;

        while (true) {

            swap(nums, start, (start + end) / 2);

            int swapIndex = start, current = start + 1;

            while (current <= end) {

                if (nums[current] >= nums[start]) swap(nums, ++swapIndex, current);

                current++;

            }

            swap(nums, start, swapIndex);

            if (swapIndex - start == target) return nums[swapIndex];

            else if (swapIndex - start > target) end = swapIndex - 1;

            else {

                target -= (swapIndex - start + 1);

                start = swapIndex + 1;

            }

        }

    }


    private void swap(int[] nums, int index1, int index2) {

        int temp = nums[index1];

        nums[index1] = nums[index2];

        nums[index2] = temp;

    }


https://leetcode.com/discuss/76939/o-n-time-solution-with-median-found-with-quick-selection
public void wiggleSort(int[] nums) {

    if(nums == null || nums.length == 0) return;
    randomShuffle(nums);

    double median = findMedian(nums);
    int firstHalfLen, secondHalfLen;
    if(nums.length % 2 == 0) {
        firstHalfLen = nums.length/2;
    } else {
        firstHalfLen = nums.length/2+1;
    }
    secondHalfLen = nums.length/2;

    List<Integer> firstHalf  = new ArrayList<Integer>();
    List<Integer> secondHalf = new ArrayList<Integer>();

    for(int i = 0; i < nums.length; i++) {
        if((double) nums[i] < median) firstHalf.add(nums[i]);
        else if((double) nums[i] > median) secondHalf.add(nums[i]);
    }

    while(firstHalf.size() < firstHalfLen) {
        firstHalf.add((int) median);
    }
    while(secondHalf.size() < secondHalfLen) {
        secondHalf.add((int) median);
    }

    for(int i = 0; i < firstHalf.size(); i++) {
        nums[i*2] = firstHalf.get(firstHalf.size()-1-i);
    }
    for(int i = 0; i < secondHalf.size(); i++) {
        nums[i*2+1] = secondHalf.get(i);
    }
}

private void randomShuffle(int[] nums) {
    Random rm = new Random();
    for(int i = 0; i < nums.length-1; i++) {
        int j = i + rm.nextInt(nums.length-i);
        swap(nums, i, j);
    }
}

private double findMedian(int[] nums) {
    if(nums.length % 2 == 1) return (double) findKth(nums, 0, nums.length-1, nums.length/2);
    else return ( (double) findKth(nums, 0, nums.length-1, nums.length/2 - 1) + (double) findKth(nums, 0, nums.length-1, nums.length/2) ) / 2;
}

private int findKth(int[] nums, int low, int high, int k) {
    int pivot = nums[low];
    int lb = low, hb = high, pt = low+1;;
    while(pt <= hb) {
        if(nums[pt] < pivot) swap(nums, lb++, pt++);
        else if(nums[pt] > pivot) swap(nums, pt, hb--);
        else pt++;
    }
    if(k < lb) return findKth(nums, low, lb-1, k);
    else if(k > hb) return findKth(nums, hb+1, high, k);
    else return pivot;
}
http://www.jiuzhang.com/solutions/wiggle-sort-ii/
    public static void wiggleSort(int[] nums) {
        int[] tem = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            tem[i] = nums[i];
        }
        int mid = partition(tem, 0, nums.length-1, nums.length/2);
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = mid;
        }
        int l, r;
        if (nums.length % 2 == 0) {
            l = nums.length - 2;
            r = 1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < mid) {
                    ans[l] = nums[i];
                    l -= 2;
                } else if (nums[i] > mid) {
                    ans[r] = nums[i];
                    r += 2;
                }
            }
        } else {
            l = 0;
            r = nums.length - 2;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < mid) {
                    ans[l] = nums[i];
                    l += 2;
                } else if (nums[i] > mid) {
                    ans[r] = nums[i];
                    r -= 2;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = ans[i];
        }
    }
    public static int partition(int[] nums, int l, int r, int rank) {
        int left = l, right = r;
        int now = nums[left];
        while (left < right) {
            while (left < right && nums[right] >= now) {
                right--;
            }
            nums[left] = nums[right];
            while (left < right && nums[left] <= now) {
                left++;
            }
            nums[right] = nums[left];
        }
        if (left - l == rank) {
            return now;
        } else if (left - l < rank) {
            return partition(nums, left + 1, r, rank - (left - l + 1));
        } else {
            return partition(nums, l, right - 1, rank);
        }
    }
http://algobox.org/wiggle-sort-ii/
Method 2 (In place solution)

Sort then apply the in place perfect shuffle algorithm.
See here for a reference
Time is still O(nlogn). Space is O(1).
https://discuss.leetcode.com/topic/30566/what-about-this-follow-up-question/
 * @author het
 *
 */
public class LeetCode324 {
	  public void wiggleSort(int[] nums) {
	        Arrays.sort(nums);
	        int[] copy = Arrays.copyOf(nums, nums.length);   //
	        int smallEndIndex = (nums.length + 1) / 2 - 1;    // 1  2  3  4  5  6   7
	        int largeEndIndex = nums.length - 1;   //  3  6   2  5  1  4  
	        for (int i = 0; i < nums.length; i++)    // 4  7  3  6  2  5  1 
	            nums[i] = i % 2 == 0 ? copy[smallEndIndex - i / 2] : copy[largeEndIndex - i / 2];

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	  public void wiggleSort(int[] nums) {

	        if (nums == null || nums.length <= 1) return;

	        int median = getMedian(nums);


	        int higher = 0, lower = nums.length - 1, current = 0;

	        while (current <= lower) {

	            if (nums[reIndex(current, nums.length)] == median) {

	                current++;

	            }


	            else if (nums[reIndex(current, nums.length)] < median) {

	                swap(nums, reIndex(current, nums.length), reIndex(lower--, nums.length));

	            }

	            else swap(nums, reIndex(current++, nums.length), reIndex(higher++, nums.length));

	        }

	    }

	    

	    private int reIndex(int index, int n) {

	        return (2*index + 1) % (n | 1);

	    }


	    private int getMedian(int[] nums) {

	        int start = 0, end = nums.length - 1, target = nums.length / 2;

	        while (true) {

	            swap(nums, start, (start + end) / 2);

	            int swapIndex = start, current = start + 1;

	            while (current <= end) {

	                if (nums[current] >= nums[start]) swap(nums, ++swapIndex, current);

	                current++;

	            }

	            swap(nums, start, swapIndex);

	            if (swapIndex - start == target) return nums[swapIndex];

	            else if (swapIndex - start > target) end = swapIndex - 1;

	            else {

	                target -= (swapIndex - start + 1);

	                start = swapIndex + 1;

	            }

	        }

	    }


	    private void swap(int[] nums, int index1, int index2) {

	        int temp = nums[index1];

	        nums[index1] = nums[index2];

	        nums[index2] = temp;

	    }


	https://leetcode.com/discuss/76939/o-n-time-solution-with-median-found-with-quick-selection
	public void wiggleSort(int[] nums) {

	    if(nums == null || nums.length == 0) return;
	    randomShuffle(nums);

	    double median = findMedian(nums);
	    int firstHalfLen, secondHalfLen;
	    if(nums.length % 2 == 0) {
	        firstHalfLen = nums.length/2;
	    } else {
	        firstHalfLen = nums.length/2+1;
	    }
	    secondHalfLen = nums.length/2;

	    List<Integer> firstHalf  = new ArrayList<Integer>();
	    List<Integer> secondHalf = new ArrayList<Integer>();

	    for(int i = 0; i < nums.length; i++) {
	        if((double) nums[i] < median) firstHalf.add(nums[i]);
	        else if((double) nums[i] > median) secondHalf.add(nums[i]);
	    }

	    while(firstHalf.size() < firstHalfLen) {
	        firstHalf.add((int) median);
	    }
	    while(secondHalf.size() < secondHalfLen) {
	        secondHalf.add((int) median);
	    }

	    for(int i = 0; i < firstHalf.size(); i++) {
	        nums[i*2] = firstHalf.get(firstHalf.size()-1-i);
	    }
	    for(int i = 0; i < secondHalf.size(); i++) {
	        nums[i*2+1] = secondHalf.get(i);
	    }
	}

	private void randomShuffle(int[] nums) {
	    Random rm = new Random();
	    for(int i = 0; i < nums.length-1; i++) {
	        int j = i + rm.nextInt(nums.length-i);
	        swap(nums, i, j);
	    }
	}

	private double findMedian(int[] nums) {
	    if(nums.length % 2 == 1) return (double) findKth(nums, 0, nums.length-1, nums.length/2);
	    else return ( (double) findKth(nums, 0, nums.length-1, nums.length/2 - 1) + (double) findKth(nums, 0, nums.length-1, nums.length/2) ) / 2;
	}

	private int findKth(int[] nums, int low, int high, int k) {
	    int pivot = nums[low];
	    int lb = low, hb = high, pt = low+1;;
	    while(pt <= hb) {
	        if(nums[pt] < pivot) swap(nums, lb++, pt++);
	        else if(nums[pt] > pivot) swap(nums, pt, hb--);
	        else pt++;
	    }
	    if(k < lb) return findKth(nums, low, lb-1, k);
	    else if(k > hb) return findKth(nums, hb+1, high, k);
	    else return pivot;
	}
	//http://www.jiuzhang.com/solutions/wiggle-sort-ii/
	    public static void wiggleSort(int[] nums) {
	        int[] tem = new int[nums.length];
	        for (int i = 0; i < nums.length; i++) {
	            tem[i] = nums[i];
	        }
	        int mid = partition(tem, 0, nums.length-1, nums.length/2);
	        int[] ans = new int[nums.length];
	        for (int i = 0; i < nums.length; i++) {
	            ans[i] = mid;
	        }
	        int l, r;
	        if (nums.length % 2 == 0) {
	            l = nums.length - 2;
	            r = 1;
	            for (int i = 0; i < nums.length; i++) {
	                if (nums[i] < mid) {
	                    ans[l] = nums[i];
	                    l -= 2;
	                } else if (nums[i] > mid) {
	                    ans[r] = nums[i];
	                    r += 2;
	                }
	            }
	        } else {
	            l = 0;
	            r = nums.length - 2;
	            for (int i = 0; i < nums.length; i++) {
	                if (nums[i] < mid) {
	                    ans[l] = nums[i];
	                    l += 2;
	                } else if (nums[i] > mid) {
	                    ans[r] = nums[i];
	                    r -= 2;
	                }
	            }
	        }
	        for (int i = 0; i < nums.length; i++) {
	            nums[i] = ans[i];
	        }
	    }
	    public static int partition(int[] nums, int l, int r, int rank) {
	        int left = l, right = r;
	        int now = nums[left];
	        while (left < right) {
	            while (left < right && nums[right] >= now) {
	                right--;
	            }
	            nums[left] = nums[right];
	            while (left < right && nums[left] <= now) {
	                left++;
	            }
	            nums[right] = nums[left];
	        }
	        if (left - l == rank) {
	            return now;
	        } else if (left - l < rank) {
	            return partition(nums, left + 1, r, rank - (left - l + 1));
	        } else {
	            return partition(nums, l, right - 1, rank);
	        }
	    }
//	http://algobox.org/wiggle-sort-ii/
//	Method 2 (In place solution)
//
//	Sort then apply the in place perfect shuffle algorithm.
//	See here for a reference
//	Time is still O(nlogn). Space is O(1).
//	https://discuss.leetcode.com/topic/30566/what-about-this-follow-up-question/
//	 * @author het
//	 *
//	 */

}
