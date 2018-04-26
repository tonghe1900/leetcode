package alite.leetcode.xx2.sucess;
/**
 * A long array A[] is given to you. There is a sliding window of size w which is moving from the very left of the array to the very right. You can only see the w numbers in the window. Each time the sliding window moves rightwards by one position. Following is an example:
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Therefore, return the max sliding window as [3,3,5,5,6,7].
 * @author het
 * 
 * 
 * 
 * 
 * X. Double Monotonic  Queue
https://discuss.leetcode.com/topic/19297/this-is-a-typical-monotonic-queue-problem
https://discuss.leetcode.com/topic/19055/java-o-n-solution-using-deque-with-explanation
I think if you could name the deque methods more consistently, with *first or *last, it would be more clear to people that are not very familiar with the Dequeue interface.

We scan the array from 0 to n-1, keep "promising" elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.
At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means
If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array
Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.
As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]
public int[] maxSlidingWindow(int[] a, int k) {  
  if (a == null || k <= 0) {
   return new int[0];
  }
  int n = a.length;
  int[] r = new int[n-k+1];
  int ri = 0;
  // store index
  Deque<Integer> q = new ArrayDeque<>();
  for (int i = 0; i < a.length; i++) {
   // remove numbers out of range k
   while (!q.isEmpty() && q.peek() < i - k + 1) {
    q.poll();
   }
   // remove smaller numbers in k range as they are useless
   while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
    q.pollLast();
   }
   // q contains index... r contains content
   q.offer(i);
   if (i >= k - 1) {
    r[ri++] = a[q.peek()];
   }
  }
  return r;
 }
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return nums;
        }
        int[] result = new int[n - k + 1];
        LinkedList<Integer> dq = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!dq.isEmpty() && dq.peek() < i - k + 1) {
                dq.poll();
            }
            while (!dq.isEmpty() && nums[i] >= nums[dq.peekLast()]) {
                dq.pollLast();
            }
            dq.offer(i);
            if (i - k + 1 >= 0) {
                result[i - k + 1] = nums[dq.peek()];
            }
        }
        return result;
    }
https://segmentfault.com/a/1190000003903509
我们用双向队列可以在O(N)时间内解决这题。当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，则把末尾扔掉，直到该队列的末尾比新数大或者队列为空的时候才住手。这样，我们可以保证队列里的元素是从头到尾降序的，由于队列里只有窗口内的数，所以他们其实就是窗口内第一大，第二大，第三大...的数。保持队列里只有窗口内数的方法和上个解法一样，也是每来一个新的把窗口最左边的扔掉，然后把新的加进去。然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。这里的技巧是，我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。这里为什么时间复杂度是O(N)呢？因为每个数只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，所以被扔掉，或者因为出了窗口而被扔掉。
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) return new int[0];
        LinkedList<Integer> deque = new LinkedList<Integer>();
        int[] res = new int[nums.length + 1 - k];
        for(int i = 0; i < nums.length; i++){
            // 每当新数进来时，如果发现队列头部的数的下标，是窗口最左边数的下标，则扔掉
            if(!deque.isEmpty() && deque.peekFirst() == i - k) deque.poll();
            // 把队列尾部所有比新数小的都扔掉，保证队列是降序的
            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.removeLast();
            // 加入新数
            deque.offerLast(i);
            // 队列头部就是该窗口内第一大的
            if((i + 1) >= k) res[i + 1 - k] = nums[deque.peek()];
        }
        return res;
    }
X. O(n) solution in Java with two simple pass in the array
https://discuss.leetcode.com/topic/26480/o-n-solution-in-java-with-two-simple-pass-in-the-array
For Example: A = [2,1,3,4,6,3,8,9,10,12,56], w=4
partition the array in blocks of size w=4. The last block may have less then w. 2, 1, 3, 4 | 6, 3, 8, 9 | 10, 12, 56|
Traverse the list from start to end and calculate maxsofar. Reset max after each block boundary (of w elements). left_max[] = 2, 2, 3, 4 | 6, 6, 8, 9 | 10, 12, 56
Similarly calculate max in future by traversing from end to start. right_max[] = 4, 4, 4, 4 | 9, 9, 9, 9 | 56, 56, 56
now, sliding max at each position i in current window, sliding-max(i) = max{rightmax(i), leftmax(i+w-1)} sliding_max = 4, 6, 6, 8, 9, 10, 12, 56
order of computing leftmax or right max doesn't matter... the direction of sliding window matter... if we slide from left to right than left_max should contain max we have seen past including current and right should contain max we will see in future (if we had slided the window). So, left or right may be vague term but concept is that left contains max from past window and right contains max from future window.
same logic is applicable if we need to compute sliding min from left to right.. the same code will work with only change of taking min instead of max

 public static int[] slidingWindowMax(final int[] in, final int w) {
    final int[] max_left = new int[in.length];
    final int[] max_right = new int[in.length];

    max_left[0] = in[0];
    max_right[in.length - 1] = in[in.length - 1];

    for (int i = 1; i < in.length; i++) {
        max_left[i] = (i % w == 0) ? in[i] : Math.max(max_left[i - 1], in[i]);

        final int j = in.length - i - 1;
        max_right[j] = (j % w == 0) ? in[j] : Math.max(max_right[j + 1], in[j]);
    }

    final int[] sliding_max = new int[in.length - w + 1];
    for (int i = 0, j = 0; i + w <= in.length; i++) {
        sliding_max[j++] = Math.max(max_right[i], max_left[i + w - 1]);
    }

    return sliding_max;
}

X. PRIORITY QUEUE
PriorityQueue.remove takes linear time rather than log(n) time. So the overall time complexity of priority queue approach should be O(n*k).
http://segmentfault.com/a/1190000003903509
时间 O(NlogK) 空间 O(K)
维护一个大小为K的最大堆，依此维护一个大小为K的窗口，每次读入一个新数，都把堆中窗口最左边的数扔掉，再把新数加入堆中，这样堆顶就是这个窗口内最大的值。
-结果数组的大小是nums.length + 1 - k， 赋值时下标也是i + 1 - k
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) return new int[0];
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
        int[] res = new int[nums.length + 1 - k];
        for(int i = 0; i < nums.length; i++){
            // 把窗口最左边的数去掉
            if(i >= k) pq.remove(nums[i - k]);
            // 把新的数加入窗口的堆中
            pq.offer(nums[i]);
            // 堆顶就是窗口的最大值
            if(i + 1 >= k) res[i + 1 - k] = pq.peek();
        }
        return res;
    }
X. https://leetcode.com/discuss/46578/java-o-n-solution-using-deque-with-explanation
At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means
If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array
Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.
As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]
public int[] maxSlidingWindow(int[] a, int k) {     
        if (a == null || k <= 0) {
            return new int[0];
        }
        int n = a.length;
        int[] r = new int[n-k+1];
        int ri = 0;
        // store index
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            // remove numbers out of range k
            while (!q.isEmpty() && q.peek() < i - k + 1) {
                q.poll();
            }
            // remove smaller numbers in k range as they are useless
            while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
                q.pollLast();
            }
            // q contains index... r contains content
            q.offer(i);
            if (i >= k - 1) {
                r[ri++] = a[q.peek()];
            }
        }
        return r;
    }
Very nice and intuitive solution! Thanks for sharing!
However, the first while loop is unnecessary since we only pop out one out of range element in one round at most. (one round we only accept one element, so we pop at most one element out).
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return nums;
        }
        int[] result = new int[n - k + 1];
        LinkedList<Integer> dq = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!dq.isEmpty() && dq.peek() < i - k + 1) {
                dq.poll();
            }
            while (!dq.isEmpty() && nums[i] >= nums[dq.peekLast()]) {
                dq.pollLast();
            }
            dq.offer(i);
            if (i - k + 1 >= 0) {
                result[i - k + 1] = nums[dq.peek()];
            }
        }
        return result;
    }

双向队列
时间 O(N) 空间 O(K)
我们用双向队列可以在O(N)时间内解决这题。当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，则把末尾扔掉，直到该队列的末尾比新数大或者队列为空的时候才住手。这样，我们可以保证队列里的元素是从头到尾降序的，由于队列里只有窗口内的数，所以他们其实就是窗口内第一大，第二大，第三大...的数。保持队列里只有窗口内数的方法和上个解法一样，也是每来一个新的把窗口最左边的扔掉，然后把新的加进去。然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。这里的技巧是，我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。这里为什么时间复杂度是O(N)呢？因为每个数只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，所以被扔掉，或者因为出了窗口而被扔掉。
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) return new int[0];
        LinkedList<Integer> deque = new LinkedList<Integer>();
        int[] res = new int[nums.length + 1 - k];
        for(int i = 0; i < nums.length; i++){
            // 每当新数进来时，如果发现队列头部的数的下标，是窗口最左边数的下标，则扔掉
            if(!deque.isEmpty() && deque.peekFirst() == i - k) deque.poll();
            // 把队列尾部所有比新数小的都扔掉，保证队列是降序的
            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.removeLast();
            // 加入新数
            deque.offerLast(i);
            // 队列头部就是该窗口内第一大的
            if((i + 1) >= k) res[i + 1 - k] = nums[deque.peek()];
        }
        return res;
    }

http://n00tc0d3r.blogspot.com/2013/04/sliding-window-maximum.html  
https://hellosmallworld123.wordpress.com/2014/05/28/sliding-window-maximum/
用double end queue– Deque.将当前元素加在queue的最后，但是加之前把所有比当前值小的元素从后往前删掉，这样就保证了一个按照index顺序维护的降序queue。非常好的思路.
https://hellosmallworld123.wordpress.com/2014/05/28/sliding-window-maximum/

    public static int[] windowMax(int [] array, int width) {

        Deque<Integer> doubleQueue = new LinkedList<Integer>();

        //calculate the first window max

        int [] maxArray = new int[array.length - width + 1];

        for (int i = 0; i < width; i++) {

            while(!doubleQueue.isEmpty() && array[i] > doubleQueue.peekLast())

                doubleQueue.removeLast();

            doubleQueue.push(array[i]);

        }

        maxArray[0] = doubleQueue.peekFirst();

        //then try to move the window right and pop 

        for (int i = width; i < array.length; i++) {

            if (doubleQueue.size() == width) // if full, remove the first max element

                doubleQueue.removeFirst();

            while(!doubleQueue.isEmpty() && array[i] > doubleQueue.peekLast())

                doubleQueue.removeLast();

            doubleQueue.addLast(array[i]);

            maxArray[i-width+1] = doubleQueue.peekFirst(); // first element will be the max;

        }

        return maxArray;

    }
/* 
  * Given an array of numbers and a sliding window, find out the maximal  
  * number within the window as its moving.  
  * @param nums the array of numbers.  
  * @param window the size of the sliding window.  
  * @return an array of window maximals, i.e. B[i] is the maximal of A[i, i+w).  
  */  
 public int[] windowMax(int[] nums, int window) {  
   int w = (nums.length < window) ? nums.length : window;  
   // A deque allows insertion/deletion on both ends.  
   // Maintain the first as the index of maximal of the window  
   // and elements after it are all smaller and came later than the first.  
   Deque<Integer> que = new ArrayDeque<Integer>();  
   
   // initialize window  
   int i=0;  
   while (i<w) {  
     while (!que.isEmpty() && nums[que.getLast()] <= nums[i]) {  
       que.removeLast();  
     }  
     que.addLast(i++);  
   }  
   
   // sliding window  
   int[] max = new int[nums.length - w + 1];  
   max[i-w] = num[que.getFirst()];  
   while (i<nums.length) {  
     // add new element  
     while (!que.isEmpty() && nums[que.getLast()] <= nums[i]) {  
       que.removeLast();  
     }  
     que.addLast(i);  
     // remove old element if still in que  
     if (!que.isEmpty() && i-w >= que.getFirst()) {  
       que.removeFirst();  
     }  
     // get maximal  
     ++i;  
     max[i-w] = num[que.getFirst()];  
   }  
   
   return max;  
 }  
Java Using Heap:
https://codesolutiony.wordpress.com/2015/05/22/lintcode-sliding-window-maximum/
-- inefficient

    public ArrayList<Integer> maxSlidingWindow(int[] nums, int k) {

        ArrayList<Integer> res = new ArrayList<Integer>();

        if (nums == null || nums.length == 0 || k <= 0) {

            return res;

        }

        PriorityQueue<Pair> heap = new PriorityQueue<Pair>();

        for (int i = 0; i < k; i++) {

            heap.add(new Pair(nums[i], i));

            //res.add(heap.peek().val);

        }

        for (int i = k; i < nums.length; i++) {

            res.add(heap.peek().val);

            Pair toRemove = heap.peek();

            while (!heap.isEmpty() && heap.peek().index <= i - k) {

                heap.remove();

            }

            heap.add(new Pair(nums[i], i));

        }

        res.add(heap.peek().val);

        return res;

    }

    class Pair implements Comparable<Pair> {

        int val;

        int index;

        public Pair(int val, int index) {

            this.val = val;

            this.index = index;

        }

        @Override

        public int compareTo(Pair p) {

            return Integer.compare(p.val, val);

        }

    }
http://blog.csdn.net/zyfo2/article/details/8752689
    public static void maxSlidingWindow(int a[],int n,int w,int b[]){
        if(n<=0||n<w) return;
        LinkedList<Integer> l=new LinkedList<Integer>();
        for(int i=0;i<w;i++){
            while(l.size()>0&&a[l.peekLast()]<=a[i]) l.pollLast();
            l.add(i);
        }
        b[0]=a[l.peekFirst()];
        for(int i=w;i<n;i++){
            while(l.size()>0&&l.peekFirst()<=i-w) l.removeFirst();
            while(l.size()>0&&a[l.peekLast()]<=a[i]) l.pollLast();
            l.add(i);
            b[i-w+1]=a[l.peekFirst()];
        }   
    }
http://codercareer.blogspot.com/2012/02/no-33-maximums-in-sliding-windows.html
Another solution to get the maximum of a queue: QueueWithMax
template<typename T> class QueueWithMax
{
public:
    QueueWithMax(): currentIndex(0)
    {
    }

    void push_back(T number)
    {
        while(!maximums.empty() && number >= maximums.back().number)
            maximums.pop_back();

        InternalData internalData = {number, currentIndex};
        data.push_back(internalData);
        maximums.push_back(internalData);

        ++currentIndex;
    }

    void pop_front()
    {
        if(maximums.empty())
            throw new exception("queue is empty");

        if(maximums.front().index == data.front().index)
            maximums.pop_front();

        data.pop_front();
    }

    T max() const
    {
        if(maximums.empty())
            throw new exception("queue is empty");

        return maximums.front().number;
    }

private:
    struct InternalData
    {
        T number;
        int index;
    };

    deque<InternalData> data;
    deque<InternalData> maximums;
    int currentIndex;
};
http://sudhansu-codezone.blogspot.com/2011/12/sliding-window-maximum.html
Brute Forces:
https://discuss.leetcode.com/topic/45770/important-to-talk-about-the-solution-brute-force-vs-deque-method-in-java
public int[] maxSlidingWindow(int[] nums, int k) {
    if(nums == null || k <= 0) return new int [0];
    int [] arr = new int[nums.length - k + 1];
    for(int i = 0; i < nums.length - k + 1; i++){
        int max = Integer.MIN_VALUE;
        for(int j = i; j < i + k; j++)
           max = Math.max(max, nums[j]);
        arr[i] = max;
    }
    return arr;
}

Optimised Naive Approach :void printKMax(int arr[], int n, int k)
{
    int j, max;
     int i=0;
     if (arr[i]>arr[i+1] && arr[i] > arr[i+2])
          max=arr[i];
     else if (arr[i+1]> arr[i+2])
        max=arr[i+1];
     else 
        max=arr[i+2];
     printf("%d  ",max);

    for (i = 1; i <= n-k; i++)
    {
        if (arr[i+2]>max)
            {printf("%d  ",arr[i+2]);
             continue;}
        else
          max=arr[i];
        for (j = 1; j < k; j++)
        {   
            if (arr[i+j] > max)
               max = arr[i+j];
        }
        printf("%d ", max);
    }
}
Method 2 (Use Self-Balancing BST)
1) Pick first k elements and create a Self-Balancing Binary Search Tree (BST) of size k.
2) Run a loop for i = 0 to n – k
…..a) Get the maximum element from the BST, and print it.
…..b) Search for arr[i] in the BST and delete it from the BST.
…..c) Insert arr[i+k] into the BST.
Time Complexity: Time Complexity of step 1 is O(kLogk). Time Complexity of steps 2(a), 2(b) and 2(c) is O(Logk). Since steps 2(a), 2(b) and 2(c) are in a loop that runs n-k+1 times, time complexity of the complete algorithm is O(kLogk + (n-k+1)*Logk) which can also be written as O(nLogk).
Method 3:Using Heap
void maxSlidingWindow(int A[], int n, int w, int B[]) {
  priority_queue<Pair> Q;
  for (int i = 0; i < w; i++)
    Q.push(Pair(A[i], i));
  for (int i = w; i < n; i++) {
    Pair p = Q.top();
    B[i-w] = p.first;
    while (p.second <= i-w) {
      Q.pop();
      p = Q.top();
    }
    Q.push(Pair(A[i], i));
  }
  B[n-w] = Q.top().first;
}
Method 4:Double Ended Queue
http://www.zrzahid.com/sliding-window-minmax/
For Example: A = [2,1,3,4,6,3,8,9,10,12,56],  w=4
partition the array in blocks of size w=4. The last block may have less then w.
2, 1, 3, 4 | 6, 3, 8, 9 | 10, 12, 56|
Traverse the list from start to end and calculate min_so_far. Reset min to 0 after each block (of w elements).
left_min[] = 2, 1, 1, 1 | 6, 3, 3, 3 | 10, 10, 10
Similarly calculate min_in_future by traversing from end to start.
right_min[] = 1, 1, 3, 4 | 3, 3, 8, 9 | 10, 12, 56
now, min at each position i in current window, sliding_min(i) = min {right_min[i], left_min[i+w-1]}
sliding_min = 1, 1, 3, 3, 3, 3, 8, ….
public static int[] slidingWindowMin(final int[] in, final int w) {
    final int[] min_left = new int[in.length];
    final int[] min_right = new int[in.length];

    min_left[0] = in[0];
    min_right[in.length - 1] = in[in.length - 1];

    for (int i = 1; i < in.length; i++) {
        min_left[i] = (i % w == 0) ? in[i] : Math.min(min_left[i - 1], in[i]);

        final int j = in.length - i - 1;
        min_right[j] = (j % w == 0) ? in[j] : Math.min(min_right[j + 1], in[j]);
    }

    final int[] sliding_min = new int[in.length - w + 1];
    for (int i = 0, j = 0; i + w <= in.length; i++) {
        sliding_min[j++] = Math.min(min_right[i], min_left[i + w - 1]);
    }

    return sliding_min;
}
Similarly for sliding window max for each position i, we will take the max of left_max(i+w-1) and right_max(i) .
sliding-max(i) = max{right_max(i), left_max(i+w-1)}
public static int[] slidingWindowMax(final int[] in, final int w) {
    final int[] max_left = new int[in.length];
    final int[] max_right = new int[in.length];

    max_left[0] = in[0];
    max_right[in.length - 1] = in[in.length - 1];

    for (int i = 1; i < in.length; i++) {
        max_left[i] = (i % w == 0) ? in[i] : Math.max(max_left[i - 1], in[i]);

        final int j = in.length - i - 1;
        max_right[j] = (j % w == 0) ? in[j] : Math.max(max_right[j + 1], in[j]);
    }

    final int[] sliding_max = new int[in.length - w + 1];
    for (int i = 0, j = 0; i + w <= in.length; i++) {
        sliding_max[j++] = Math.max(max_right[i], max_left[i + w - 1]);
    }

    return sliding_max;
}

X. Using TreeMap
https://discuss.leetcode.com/topic/49348/java-o-nlogk-solution-using-treemap
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length==0)
            return new int[0];
        int[] res = new int[nums.length-k+1];
        TreeMap<Integer,Integer> tree = new TreeMap<>();
        for(int i=0; i<k; i++)
            add(tree,nums[i]);
        res[0] = tree.lastKey();
        for(int i=k, j=1; i<nums.length; i++, j++) {
            remove(tree, nums[i-k]);
            add(tree, nums[i]);
            res[j] = tree.lastKey();
        }
        return res;
    }
    
    public void add(TreeMap<Integer,Integer> tree, int val) {
        if(tree.containsKey(val))
            tree.put(val, tree.get(val)+1);
        else
            tree.put(val,1);
    }
    
    public void remove(TreeMap<Integer,Integer> tree, int val) {
        if(tree.get(val)>1)
            tree.put(val, tree.get(val)-1);
        else
            tree.remove(val);
    }

https://discuss.leetcode.com/topic/19074/treemap-solution-o-nlogk-and-deque-solution-o-n
public int[] maxSlidingWindow(int[] nums, int k) {
    if(nums.length == 0)
        return nums;
    int[] res = new int[nums.length - k + 1];
    TreeMap<Integer, Set<Integer>> memo = new TreeMap<>();
    for(int i = 0 ; i < k ; i++){
        if(memo.containsKey(nums[i])){
            memo.get(nums[i]).add(i);
        }else{
            Set<Integer> temp = new HashSet<>();
            temp.add(i);
            memo.put(nums[i], temp);
        }
    }
    res[0] = memo.lastKey();
    for(int i = k ; i < nums.length ; i++){
        if(memo.get(nums[i - k]).size() == 1){
            memo.remove(nums[i - k]);
        }else{
            memo.get(nums[i - k]).remove(i - k);
        }
        if(memo.containsKey(nums[i]))
            memo.get(nums[i]).add(i);
        else{
            Set<Integer> temp = new HashSet<>();
            temp.add(i);
            memo.put(nums[i], temp);
        }
        res[i - k + 1] = memo.lastKey();
    }
    return res;
}

Java 8:
https://leetcode.com/discuss/46744/java8-functional-style-solution
    if(nums.length == 0) return nums;

    int[] arr = IntStream.range(0, nums.length - k + 1).map(i ->
        Arrays.stream(Arrays.copyOfRange(nums, i, i + k)).reduce(Integer.MIN_VALUE, Math::max)
    ).toArray();

    return arr;
Read full article from Sliding Window Maximum | LeetCode
 *
 */
public class L239SlidingWindowMaximum {
//	https://segmentfault.com/a/1190000003903509
//		我们用双向队列可以在O(N)时间内解决这题。当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，
	//则把末尾扔掉，直到该队列的末尾比新数大或者队列为空的时候才住手。这样，我们可以保证队列里的元素是从头到尾降序的，由于队列里
	//只有窗口内的数，所以他们其实就是窗口内第一大，第二大，第三大...的数。保持队列里只有窗口内数的方法和上个解法一样，也是每来一个新的把窗
	//口最左边的扔掉，然后把新的加进去。然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。这里的技巧是，
	//我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。这里为什么时间复杂度是O(N)呢？
	//因为每个数只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，所以被扔掉，或者因为出了窗口而被扔掉。
	
	//这里为什么时间复杂度是O(N)呢？
	//因为每个数只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，所以被扔掉，或者因为出了窗口而被扔掉。
		    public int[] maxSlidingWindow(int[] nums, int k) {
		        if(nums == null || nums.length == 0) return new int[0];
		        LinkedList<Integer> deque = new LinkedList<Integer>();
		        int[] res = new int[nums.length + 1 - k];
		        for(int i = 0; i < nums.length; i++){
		            // 每当新数进来时，如果发现队列头部的数的下标，是窗口最左边数的下标，则扔掉
		            if(!deque.isEmpty() && deque.peekFirst() == i - k) deque.poll();
		            // 把队列尾部所有比新数小的都扔掉，保证队列是降序的
		            // while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.removeLast();
		            while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.removeLast();
		            // 加入新数
		            deque.offerLast(i);
		            // 队列头部就是该窗口内第一大的
		            if((i + 1) >= k) res[i + 1 - k] = nums[deque.peek()];
		        }
		        return res;
		    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
