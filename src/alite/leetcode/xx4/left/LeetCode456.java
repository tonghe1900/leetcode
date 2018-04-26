package alite.leetcode.xx4.left;
/**
 * http://bookshadow.com/weblog/2016/11/13/leetcode-132-pattern/
Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak 
such that i < j < k and ai < ak < aj. Design an algorithm that 
takes a list of n numbers as input and checks whether there is a 132 pattern in the list.
Note: n will be less than 15,000.
Example 1:
Input: [1, 2, 3, 4]
1  4  
Output: False

Explanation: There is no 132 pattern in the sequence.
Example 2:
Input: [3, 1, 4, 2]
1  4 2
Output: True

Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
Example 3:
Input: [-1, 3, 2, 0]
-1 3  
Output: True

Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
BST（Binary Search Tree 二叉查找树）
首先利用TreeMap（采用红黑树实现）统计nums中所有元素的个数，记为tm

变量min记录访问过元素的最小值

遍历数组nums，记当前数字为num

  将num在tm中的计数-1，计数为0时将num从tm中删去

  如果num < min，则更新min值为num

  否则，若tm中大于min的最小元素<num，则返回true

遍历结束，返回false
    public boolean find132pattern(int[] nums) {
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for (int num : nums) {
            tm.put(num, tm.getOrDefault(num, 0) + 1);
        }
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            int cnt = tm.get(num);
            if (cnt > 1) {
                tm.put(num, cnt - 1);
            } else {
                tm.remove(num);
            }
            if (num <= min) {
                min = num;
            } else {
                Integer target = tm.higherKey(min);
                if (target != null && target < num) {
                    return true;
                }
            }
        }
        return false;
    }

X. Use Stack
http://blog.csdn.net/mebiuw/article/details/53193012
public boolean find132pattern(int[] nums) {
    Stack<Range> stack = new Stack<>();
    for(int num : nums) {
        Range cur = new Range(num, num);
        while(!stack.isEmpty() && cur.max > stack.peek().min) {
            cur.min = Math.min(stack.peek().min, cur.min);
            cur.max = Math.max(stack.peek().max, cur.max);
            stack.pop();
        }
        stack.push(cur);

        if(stack.peek().min < num && num < stack.peek().max)
            return true;
    }

    return false;
}
https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation
https://discuss.leetcode.com/topic/67816/o-n-time-o-n-space-java-solution-using-stack-13ms
The idea is that we can use a stack to keep track of previous min-max intervals.
Here is the principle to maintain the stack:
For each number num in the array
If stack is empty:
push a new Pair of num into stack
If stack is not empty:
if num < stack.peek().min, push a new Pair of num into stack
if num > stack.peek().min, we first pop() out the peek element, denoted as last
if num < last.max, we are done, return true;
if num > last.max, we merge num into last, which means last.max = num.
Once we update last, if stack is empty, we just push back last.
However, the crucial part is:
If stack is not empty, the updated last might:
Entirely covered stack.peek(), i.e. last.min < stack.peek().min (which is always true) && last.max > stack.peek().max, in which case we keep popping out stack.peek().
Form a 1-3-2 pattern, we are done ,return true
So at any time in the stack, non-overlapping Pairs are formed in descending order by their min value, which means the min value of peek element in the stack is always the min value globally.
i = 6, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 9, S3 candidate = None, Stack = Empty
i = 5, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 7, S3 candidate = None, Stack = [9]
i = 4, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 10, S3 candidate = None, Stack = [9,7]
i = 3, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 9, S3 candidate = 9, Stack = [10]
i = 2, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 8, S3 candidate = 9, Stack = [10,9] We have 8<9, sequence found!
   class Pair{
        int min, max;
        public Pair(int min, int max){
            this.min = min;
            this.max = max;
        }
    }
    public boolean find132pattern(int[] nums) {
        Stack<Pair> stack = new Stack();
        for(int n: nums){
            if(stack.isEmpty() || n <stack.peek().min ) stack.push(new Pair(n,n));
            else if(n > stack.peek().min){ 
                Pair last = stack.pop();
                if(n < last.max) return true;
                else {
                    last.max = n;
                    while(!stack.isEmpty() && n >= stack.peek().max) stack.pop();
                    // At this time, n < stack.peek().max (if stack not empty)
                    if(!stack.isEmpty() && stack.peek().min < n) return true;
                    stack.push(last);
                }
                
            }
        }
        return false;
    }
https://discuss.leetcode.com/topic/67881/single-pass-c-o-n-space-and-time-solution-8-lines-with-detailed-explanation
We want to search for a sub sequence (s1,s2,s3)
INTUITION: The problem would be simple if we want to find sequence with s1 > s2 > s3, we just need to find s1, followed by s2 and s3. Now if we want to find a 132 sequence, we need to switch up the order of searching. we want to first find s2, followed by s3, then s1.
IDEA: We can start from either side but I think starting from the end allow us to finish in a single pass. The idea is to start from end and search for a candidate for s2 and s3. A number becomes a candidate for s3 if there is any number on the left (s2) that is bigger than it.
DETECTION: Keep track of the largest candidate of s3 and once we encounter any number smaller than s3, we know we found a valid sequence since s1 < s3 implies s1 < s2.
IMPLEMENTATION:
Have a stack, each time we store a new number, we first pop out all numbers that are smaller than that number. The numbers that are popped out becomes candidate for s3.
We keep track of the maximum of such s3.
Once we encounter any number smaller than s3, we know we found a valid sequence since s1 < s3 implies s1 < s2.
RUNTIME: Each item is pushed and popped once at most, the time complexity is therefore O(n).
    bool find132pattern(vector<int>& nums) {
        int s3 = INT_MIN;
        stack<int> st;
        for( int i = nums.size()-1; i >= 0; i -- ){
            if( nums[i] < s3 ) return true;
            else while( !st.empty() && nums[i] > st.top() ){ 
              s3 = max( s3, st.top() ); st.pop(); 
            }
            st.push(nums[i]);
        }
        return false;
    }
public boolean find132pattern(int[] nums) {
    Stack<Integer> stack = new Stack<>();
    for (int i = nums.length - 1, two = Integer.MIN_VALUE; i >= 0; stack.push(nums[i--]))
        if (nums[i] < two) return true;
        else for (; !stack.empty() && nums[i] > stack.peek(); two = Math.max(two, stack.pop()));
    return false;
}
https://segmentfault.com/a/1190000007507137
维护一个pair, 里面有最大值和最小值。如果当前值小于pair的最小值，那么就将原来的pair压进去栈，然后在用这个新的pair的值再进行更新。如果当前值大于pair的最大值，首先这个值和原来在stack里面的那些pair进行比较，如果这个值比stack里面的值的max要大，就需要pop掉这个pair。如果没有适合返回的值，就重新更新当前的pair。
Class Pair {
    int min;
    int max;
    public Pair(int min, int max) {
        this.min = min;
        this.max = max;
    }
}

public boolean find123Pattern(int[] nums) {
    if(nums == null || nums.length < 3) return false;
    Pair cur = new Pair(nums[0], nums[0]);
    Stack<Pair> stack = new Stack<>();
    for(int i = 1; i < nums.length; i ++) {
        if(nums[i] < cur.min) {
            stack.push(cur);
            cur = new Pair(nums[i], nums[i]);
        }
        else if(nums[i] > cur.max) {
            while(!stack.isEmpty() && stack.peek().max <= nums[i]) {
                stack.pop();
            }
            if(!stack.isEmpty() && stack.peek.max > nums[i]) {
                return true;
            }
            cur.max = nums[i];
        }
        else if(nums[i] > cur.min && nums[i] < cur.max) {
            return true;
        }
    }
    return false;
}
X.
https://discuss.leetcode.com/topic/67615/share-my-easy-and-simple-solution
the worst case time complexity for this one is O(n^2) when the array is in ascending order, right?
Idea: Find peak and bottom
For every [bottom, peak], find if there is one number bottom<number<peak.
    public boolean find132pattern(int[] nums) {
        if(nums.length<3) return false;
        Integer low = null, high = null;
        int start = 0, end = 0;
        while(start<nums.length-1){
            while(start<nums.length-1 && nums[start]>=nums[start+1]) start++;
            // start is lowest now
            int m = start+1; //no need to use m - use end instead
            while(m<nums.length-1 && nums[m]<=nums[m+1]) m++;
            // m is highest now
            int j = m+1;
            while(j<nums.length){
                if(nums[j]>nums[start] && nums[j]<nums[m]) return true;
                j++;
            }
            start = m+1;
        }
        return false;
    }
X.
https://discuss.leetcode.com/topic/67592/java-straightforward
public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if(len < 3) return false;
        int[] max_cache = new int[len];
        int[] min_cache = new int[len];
        
        min_cache[0] = nums[0];
        for(int j = 1; j < len ; j++){
            min_cache[j] = Math.min(min_cache[j-1], nums[j]);
        }
        max_cache[len-1] = nums[len-1];
        for(int j = len-2; j >= 0; j--){
            max_cache[j] = Math.max(max_cache[j+1], nums[j]);
        }
        for(int i = 1; i < len-1; i++){
            int val = nums[i];
            int left = min_cache[i-1];
            if(max_cache[i+1] > left && val > max_cache[i+1]) return true;
            for(int j = i+1; j < len; j++){
                if(nums[j] > left && val > nums[j]) return true;
            }
        }
        return false;
    }
X. brute force
https://discuss.leetcode.com/topic/68242/java-solutions-from-o-n-3-to-o-n-for-132-pattern
I. Naive O(n^3) solution
simply check every (i, j, k) combination to see if there is any 132 pattern.
public boolean find132pattern(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            for (int k = j + 1; k < nums.length; k++) {
                if (nums[i] < nums[k] && nums[k] < nums[j]) return true;
            }
        }
    }
    return false;
}
II. Improved O(n^2) solution
To reduce the time complexity down to O(n^2), we need to do some observations. In the naive solution above, let's assume we have index j fixed, what should index i be so that it is most probable we will have a 132 pattern? Or in other words, what should i be so that we will be certain there is no such 132 pattern for combination (*, j, *) whenever there is no 132 pattern for combination of (i, j, *)? (Here * means any index before or after index j.)
The answer lies in the fact that once the first two numbers nums[i] and nums[j] are fixed, we are up to find the third number nums[k] which will be within the range (nums[i], nums[j]) (the two bounds are exclusive). Intuitively the larger the range is, the more likely there will be a number "falling into" it. Therefore we need to choose index i which will maximize the range (nums[i], nums[j]). Since the upper bound nums[j] is fixed, this is equivalent to minimizing the lower bound nums[i]. Thus it is clear ishould be the index of the minimum element of the subarray nums[0, j) (left inclusive, right exclusive).
Since we are scanning index j from the beginning of the input array nums, we can keep track of the minimum element of the subarray from index 0 up to j - 1 without rescanning it. Therefore the first two loops in the naive solution can be combined into one and leads to the following O(n^2) solution:
public boolean find132pattern(int[] nums) {
    for (int j = 0, min = Integer.MAX_VALUE; j < nums.length; j++) {
         min = Math.min(nums[j], min);
         if (min == nums[j]) continue;
         
         for (int k = nums.length - 1; k > j; k--) {
             if (min < nums[k] && nums[k] < nums[j]) return true;
         }
     }
     
     return false;
}
While this solution can be accepted, it runs slow. One obvious drawback is that in the second loop we are throwing away information about elements in the right part of nums that may be "useful" for later combinations. It turns out we can retain this "useful" information by applying the classic space-time tradeoff, which leads to the following O(n) time and O(n) space solution.
III. Optimized O(n) solution
As I mentioned, to further reduce the time complexity, we need to record the "useful" information about elements in the right part of 
the input array nums. Since these elements are located at the right part of nums, it will be hard to do so if we are 
scanning the array from the beginning. So the idea is to scan it from the end while in the meantime 
keep track of the "useful" information. But still at each index j, we need to know the minimum 
element for subarray nums[0, j). This can be done by doing a pre-scan in the forward direction 
and memorize the results for each index in an auxiliary array (we will call the array as arr whose element arr[j] 
will denote the minimum element in the subarray nums[0, j)).
Until now we are kinda vague about the exact meaning of "useful" information, so let's try to be more specific.
 Assume we're currently scanning (from the end) the element with index j, our task is to find two elements nums[i] and nums[k]
  to determine if there exists a 132 pattern, with i < j < k. The left element nums[i], as it has been shown in part II, 
  will be chosen as arr[j], the minimum element of subarray nums[0, j). What about the right element nums[k]?
The answer to that will address the meaning of "useful" information. First note we are only interested in elements 
that are greater than arr[j], so it is sensible to maintain only those elements. Second, among all these qualified
 elements, which one will be the most probable to fall into the range (nums[i], nums[j])? I would say it is the smallest one
  (i.e., if the smallest one is out of the range, all others will also be out of range). So to sum up, the "useful" information
   for current index j will be a collection of scanned elements that are greater than arr[j], and nums[k] will be chosen as the smallest
    one if the collection is not empty.
From the analyses above, it looks like we have to do some sorting stuff for the retained elements (or at least find a way 
to figure out its smallest element). Well, it turns out these elements will be sorted automatically due to the fact that 
arr[j'] >= arr[j] as long as j' < j. Here is how it goes, which is a proof by induction.
At the beginning we have an empty collection and of course it is sorted. Now suppose we are at index j and the corresponding collection is still sorted, let's see if it remains so at index j - 1. First we will check if nums[j] is greater than arr[j]. If not, we simply continue to j - 1. Since the collection is intact so it will be sorted at j - 1. Otherwise, we need to remove elements in the collection that are no greater than arr[j] (this is necessary because some smaller elements may be left over in the collection from previous steps). After removal, we then compare the first element in the collection with nums[j] to see if a 132 pattern has been found, provided the collection is not empty. If so, return true. Otherwise one of the following must be true: the collection is empty or nums[j] is no greater than the first element in the collection. In either case the collection is sorted. Now if we have arr[j - 1] < nums[j], we need to add nums[j] to the collection since it is a qualified number for arr[j - 1]. Again in either case the collection will remain sorted after addition (if it is empty, after addition there is only one element; otherwise since the added element is no greater than the first element in the collection before addition, it will become the new first element after addition and the collection stays sorted).
Here is the program with O(n) time and space complexity. There is one minor optimization based on the observation that the total number of elements in the collection will never exceed the total number of elements scanned so far. Therefore the right part of the arrarray can be used to serve as the collection. For time complexity, each element in the input array nums will be pushed into and popped out from the collection (or stack to be exact) at most once, the time complexity will be O(n) despite of the nested loop.
public boolean find132pattern(int[] nums) {
    int[] arr = Arrays.copyOf(nums, nums.length);

    for (int i = 1; i < nums.length; i++) {
        arr[i] = Math.min(nums[i - 1], arr[i - 1]);
    }
    
    for (int j = nums.length - 1, top = nums.length; j >= 0; j--) {
        if (nums[j] <= arr[j]) continue;
        while (top < nums.length && arr[top] <= arr[j]) top++;
        if (top < nums.length && nums[j] > arr[top]) return true;
        arr[--top] = nums[j];
    }
        
    return false;
}
https://t.du9l.com/2016/11/leetcode-456-132-pattern/
思路是遍历每一个可能的k值，然后找到它之前第一个（也就是距离最近的）比它大的数作为aj，然后再判断a0~aj-1之间的最小值是否小于ak。
之所以要找到“第一个”比它大的数，理由也很明显：要尽量扩大找ai的范围，否则可能会有false negative的情况。
思路很简单，关键是如何做到O(n)。因为遍历每一个k的过程就是O(n)的，所以要求后面两个步骤都是O(1)，也就是要预处理。预处理最小值很简单，开一个数组记录一下即可，空间复杂度O(n)。预处理“之前第一个比ak大的数（的位置）”就比较麻烦了，放在子算法里说，空间复杂度也是O(n)。
因此最终算法分三步：预处理a0~aj的最小值，预处理ak之前第一个大的数，遍历每一个可能的k。
要预处理每个数之前第一个比它大的数，用到了之前一个stack的奇技淫巧。方法是维护一个pair<数, 位置>的stack，其中数是递减的；对于数组从头到尾遍历，每个数先将栈顶比它小的数pop出去，如果pop完了栈空了，说明前面没有比它大的数，此时预处理结果为-1；否则预处理结果为栈顶数的位置。处理完之后将当前pair<数, 位置>也push进栈。
原理：因为要找到之前第一个比它大的数，如果栈顶的数比当前数还小，那对于它们之后的数来说，当前数更可能是比后面数大的结果，所以将栈顶pop出去。因为每个数最多进出栈一次，时间复杂度O(n)。

    bool find132pattern(vector<int>& nums) {

        if (nums.empty()) return false;

        int n = nums.size();

        vector<int> min1(n);

        min1[0] = nums[0];

        for (int i = 1; i < n; ++i) {

            min1[i] = min(min1[i-1], nums[i]);

        }

        typedef pair<int, int> pii;

        stack<pii> s;

        vector<int> max1;

        for (int i = 0; i < n; ++i) {

            int I = nums[i];

            while (!s.empty() && s.top().first <= I) s.pop();

            if (s.empty()) {

                max1.push_back(-1);

            } else {

                max1.push_back(s.top().second);

            }

            s.push(make_pair(I, i));

        }

        for (int i = n-1; i >= 0; --i) {

            int I = nums[i];

            int j = max1[i];

            if (j == -1 || j == 0) continue;

            if (min1[j-1] < I) return true;

        }

        return false;

    }
 * @author het
 *
 */
public class LeetCode456 {
//	X. brute force
//	https://discuss.leetcode.com/topic/68242/java-solutions-from-o-n-3-to-o-n-for-132-pattern
//	I. Naive O(n^3) solution
//	simply check every (i, j, k) combination to see if there is any 132 pattern.
//	public boolean find132pattern(int[] nums) {
//	    for (int i = 0; i < nums.length; i++) {
//	        for (int j = i + 1; j < nums.length; j++) {
//	            for (int k = j + 1; k < nums.length; k++) {
//	                if (nums[i] < nums[k] && nums[k] < nums[j]) return true;
//	            }
//	        }
//	    }
//	    return false;
//	}
//	II. Improved O(n^2) solution
//	To reduce the time complexity down to O(n^2), we need to do some observations. In the naive solution above, 
	//let's assume we have index j fixed, what should index i be so that it is most probable we will have a 132 pattern?
	//Or in other words, what should i be so that we will be certain there is no such 132 pattern 
	//for combination (*, j, *) whenever there is no 132 pattern for combination of (i, j, *)?
	//(Here * means any index before or after index j.)
//	The answer lies in the fact that once the first two numbers nums[i] and nums[j] are fixed, we are up to find the third number nums[k] which will be within the range (nums[i], nums[j]) (the two bounds are exclusive). Intuitively the larger the range is, the more likely there will be a number "falling into" it. Therefore we need to choose index i which will maximize the range (nums[i], nums[j]). Since the upper bound nums[j] is fixed, this is equivalent to minimizing the lower bound nums[i]. Thus it is clear ishould be the index of the minimum element of the subarray nums[0, j) (left inclusive, right exclusive).
//	Since we are scanning index j from the beginning of the input array nums, we can keep track of the minimum element of the subarray from index 0 up to j - 1 without rescanning it. Therefore the first two loops in the naive solution can be combined into one and leads to the following O(n^2) solution:
//	public boolean find132pattern(int[] nums) {
//	    for (int j = 0, min = Integer.MAX_VALUE; j < nums.length; j++) {
//	         min = Math.min(nums[j], min);
//	         if (min == nums[j]) continue;
//	         
//	         for (int k = nums.length - 1; k > j; k--) {
//	             if (min < nums[k] && nums[k] < nums[j]) return true;
//	         }
//	     }
//	     
//	     return false;
//	}
//	While this solution can be accepted, it runs slow. One obvious drawback is that in the second loop we are throwing away information about elements in the right part of nums that may be "useful" for later combinations. It turns out we can retain this "useful" information by applying the classic space-time tradeoff, which leads to the following O(n) time and O(n) space solution.
//	III. Optimized O(n) solution
//	As I mentioned, to further reduce the time complexity, we need to record the "useful" information about elements in the right part of the input array nums. Since these elements are located at the right part of nums, it will be hard to do so if we are scanning the array from the beginning. So the idea is to scan it from the end while in the meantime keep track of the "useful" information. But still at each index j, we need to know the minimum element for subarray nums[0, j). This can be done by doing a pre-scan in the forward direction and memorize the results for each index in an auxiliary array (we will call the array as arr whose element arr[j] will denote the minimum element in the subarray nums[0, j)).
//	Until now we are kinda vague about the exact meaning of "useful" information, so let's try to be more specific. Assume we're currently scanning (from the end) the element with index j, our task is to find two elements nums[i] and nums[k] to determine if there exists a 132 pattern, with i < j < k. The left element nums[i], as it has been shown in part II, will be chosen as arr[j], the minimum element of subarray nums[0, j). What about the right element nums[k]?
//	The answer to that will address the meaning of "useful" information. First note we are only interested in elements that are greater than arr[j], so it is sensible to maintain only those elements. Second, among all these qualified elements, which one will be the most probable to fall into the range (nums[i], nums[j])? I would say it is the smallest one (i.e., if the smallest one is out of the range, all others will also be out of range). So to sum up, the "useful" information for current index j will be a collection of scanned elements that are greater than arr[j], and nums[k] will be chosen as the smallest one if the collection is not empty.
//	From the analyses above, it looks like we have to do some sorting stuff for the retained elements (or at least find a way to figure out its smallest element). Well, it turns out these elements will be sorted automatically due to the fact that arr[j'] >= arr[j] as long as j' < j. Here is how it goes, which is a proof by induction.
//	At the beginning we have an empty collection and of course it is sorted. Now suppose we are at index j and the corresponding collection is still sorted, let's see if it remains so at index j - 1. First we will check if nums[j] is greater than arr[j]. If not, we simply continue to j - 1. Since the collection is intact so it will be sorted at j - 1. Otherwise, we need to remove elements in the collection that are no greater than arr[j] (this is necessary because some smaller elements may be left over in the collection from previous steps). After removal, we then compare the first element in the collection with nums[j] to see if a 132 pattern has been found, provided the collection is not empty. If so, return true. Otherwise one of the following must be true: the collection is empty or nums[j] is no greater than the first element in the collection. In either case the collection is sorted. Now if we have arr[j - 1] < nums[j], we need to add nums[j] to the collection since it is a qualified number for arr[j - 1]. Again in either case the collection will remain sorted after addition (if it is empty, after addition there is only one element; otherwise since the added element is no greater than the first element in the collection before addition, it will become the new first element after addition and the collection stays sorted).
//	Here is the program with O(n) time and space complexity. There is one minor optimization based on the observation that the total number of elements in the collection will never exceed the total number of elements scanned so far. Therefore the right part of the arrarray can be used to serve as the collection. For time complexity, each element in the input array nums will be pushed into and popped out from the collection (or stack to be exact) at most once, the time complexity will be O(n) despite of the nested loop.
//	public boolean find132pattern(int[] nums) {
//	    int[] arr = Arrays.copyOf(nums, nums.length);
//
//	    for (int i = 1; i < nums.length; i++) {
//	        arr[i] = Math.min(nums[i - 1], arr[i - 1]);
//	    }
//	    
//	    for (int j = nums.length - 1, top = nums.length; j >= 0; j--) {
//	        if (nums[j] <= arr[j]) continue;
//	        while (top < nums.length && arr[top] <= arr[j]) top++;
//	        if (top < nums.length && nums[j] > arr[top]) return true;
//	        arr[--top] = nums[j];
//	    }
//	        
//	    return false;
//	}
	// 1  3   2
	public boolean find132pattern(int[] nums) {
		if(nums == null ||  nums.length <3) return false;
		Integer [] range = new Integer[2];
		range[0] = nums[0];
		for(int i=1 ; i< nums.length; i+=1){
			if(range[1] == null){
				if(nums[i] > range[0]){
					range[1] = nums[i];
				}else{
					range[0] = nums[i];
				}
			}else{
				if(nums[i] > range[0] && nums[i] < range[1]){
					return true;
				}else if(nums[i] )
			}
		}
	}
//	Example 1:
//		Input: [1, 2, 3, 4]
//		1  4  
//		Output: False
//
//		Explanation: There is no 132 pattern in the sequence.
//		Example 2:
//		Input: [3, 1, 4, 2]
//		1  4 2
//		Output: True
//
//		Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
//		Example 3:
//		Input: [-1, 3, 2, 0]
//		-1 3  
//		Output: True
//	public boolean find132pattern(int[] nums) {
//	    Stack<Range> stack = new Stack<>();
//	    for(int num : nums) {
//	        Range cur = new Range(num, num);
//	        while(!stack.isEmpty() && cur.max > stack.peek().min) {
//	            cur.min = Math.min(stack.peek().min, cur.min);
//	            cur.max = Math.max(stack.peek().max, cur.max);
//	            stack.pop();
//	        }
//	        stack.push(cur);
//// 1  3
//	        if(stack.peek().min < num && num < stack.peek().max)
//	            return true;
//	    }
//
//	    return false;
//	}
	
	 public boolean find132pattern(int[] nums) {
	        TreeMap<Integer, Integer> tm = new TreeMap<>();
	        for (int num : nums) {
	            tm.put(num, tm.getOrDefault(num, 0) + 1);
	        }
	        int min = Integer.MAX_VALUE;
	        for (int num : nums) {
	            int cnt = tm.get(num);
	            if (cnt > 1) {
	                tm.put(num, cnt - 1);
	            } else {
	                tm.remove(num);
	            }
	            if (num <= min) {
	                min = num;
	            } else {
	                Integer target = tm.higherKey(min);
	                if (target != null && target < num) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub

	}

}
