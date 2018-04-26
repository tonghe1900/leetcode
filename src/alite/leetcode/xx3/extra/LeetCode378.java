package alite.leetcode.xx3.extra;
/**
 * LeetCode 378 - Kth Smallest Element in a Sorted Matrix

https://www.hrwhisper.me/leetcode-kth-smallest-element-sorted-matrix/
Given a n x n matrix where each of the rows and columns are sorted in ascending order, 
find the kth smallest element in the matrix.
Note that it is the kth smallest element in the sorted order, not the kth distinct element.
Example:
matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note: 
You may assume k is always valid, 1 ≤ k ≤ n2.
X.  Binary search
https://discuss.leetcode.com/topic/53041/java-1ms-nlog-max-min-solution
Main loop is binary search of max - min.
Swap from left-bottom to right-top can get count <= mid in O(n) time instead of O(nlogn), total complexity will be O(nlogm) while m = max - min.
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int lo = matrix[0][0], hi = matrix[n - 1][n - 1];
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int count = getLessEqual(matrix, mid);
            if (count < k) lo = mid + 1;
            else hi = mid - 1;
        }
        return lo;
    }
    
    private int getLessEqual(int[][] matrix, int val) {
        int res = 0;
        int n = matrix.length, i = n - 1, j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] > val) i--;
            else {
                res += i + 1;
                j++;
            }
        }
        return res;
    }
而下面的解法利用了列有序的性质，并将复杂度降到了O(nlogX)   其中X = max – min
我们仍采用猜测法，设L = min(matrix) R= max(matrix) , mid =( L + R ) / 2 ，mid为我们猜测的答案。
对于mid，我们不必再所有的行或列种执行二分查找，我们可以从左下角出发，若matrix[i][j] <= mid，则下一次查询在右边（j++），并且，该列的所有元素均比mid小，因此可以cnt += (i+1)
对于matrix[i][j] > mid，则 i – – 。 

    public int kthSmallest(int[][] matrix, int k) {

  int n = matrix.length;

  int L = matrix[0][0], R = matrix[n - 1][n - 1];

  while (L < R) {

   int mid = L + ((R - L) >> 1);

   int temp = search_lower_than_mid(matrix, n, mid);

   if (temp < k) L = mid + 1;

   else R = mid;

  }

  return L;

 }

 

 private int search_lower_than_mid(int[][] matrix,int n,int x) {

  int i = n - 1, j = 0, cnt = 0;

  while (i >= 0 && j < n) {

   if (matrix[i][j] <= x) {

    j++;

    cnt += i + 1;

   }

   else i--;

  }

  return cnt;

 }

https://discuss.leetcode.com/topic/52865/my-solution-using-binary-search-in-c
The time complexity is O(n * log(n) * log(N)), where N is the search space that ranges from the smallest element to the biggest element.
You can argue that int implies N = 2^32, so log(N) is constant. I guess this is where O(n * log(n)) comes from.
I thought this idea was weird for a while. Then I noticed the previous problem 377. Combination Sum IV is pretty much doing the same thing, so this idea may actually be intended.


    int kthSmallest(vector<vector<int>>& matrix, int k) {

        int n = matrix.size();

        long long l = INT_MIN, r = INT_MAX, mid;

        while(l < r){

            mid = (l + r) >> 1;

            int kth = 0;

            for(int i = 0; i < n; i++){

                for(int j = 0; j < n && matrix[i][j] <= mid; j++){

                    kth++;

                }

            }

            if(kth < k) l = mid + 1;

            else r = mid;

        }

        return l;

    }
X. Priority Queue
https://discuss.leetcode.com/topic/52948/share-my-thoughts-and-clean-java-code
https://discuss.leetcode.com/topic/52868/java-heap-klog-k
https://discuss.leetcode.com/topic/52859/java-heap-solution-time-complexity-klog-k
    public int kthSmallest(final int[][] matrix, int k) {
        int c = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(
            k, (o1, o2) -> matrix[o1[0]][o1[1]] - matrix[o2[0]][o2[1]]);
        queue.offer(new int[] {0, 0});
        while (true) {
            int[] pair = queue.poll();
            if (++c == k) {
                return matrix[pair[0]][pair[1]];
            }
            if (pair[0] == 0 && pair[1] + 1 < matrix[0].length) {
                queue.offer(new int[] {0, pair[1] + 1});
            }
            if (pair[0] + 1 < matrix.length) {
                queue.offer(new int[] {pair[0] + 1, pair[1]});
            }
        }
https://discuss.leetcode.com/topic/52871/java-priorityqueue-solution/5
    private class Value implements Comparable<Value> {
        int val;
        int row;
        int col;
        public Value(int val, int row, int col) {
            this.val = val;
            this.row = row;
            this.col = col;
        }
        public int compareTo(Value other) {
            return this.val - other.val;
        }
    }
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Value> minHeap = new PriorityQueue<Value>();
        minHeap.add(new Value(matrix[0][0], 0, 0));
        for(int x = 1; x < k; x++) {
            Value val = minHeap.poll();
            if(val.row + 1 < matrix.length) {
                minHeap.add(new Value(matrix[val.row + 1][val.col], val.row + 1, val.col));
            }
            // avoid duplicates
            if(val.row == 0 && val.col + 1 < matrix[0].length) {
                minHeap.add(new Value(matrix[0][val.col + 1], 0, val.col + 1));
            }
        }
        return minHeap.peek().val;
    }
 先按从上往下, 从左往右的顺序将k个元素放入堆中. 对于剩下的元素, 每一行从头开始与堆顶比较, 如果小于堆顶, 就把它放入堆中, 把原堆顶弹出. 该行中出现>=堆顶的元素时即可停止对这一行的处理. 运行时间112ms.

    int kthSmallest(vector<vector<int>>& matrix, int k) {

        int n = matrix.size();

        priority_queue<int> heap;

        for(int i = 0; i < n; i++){

            if(heap.size() < k){

                int j;

                for(j = 0; j < n && heap.size() < k; j++){

                    heap.push(matrix[i][j]);

                }

                for(; j < n && heap.top() > matrix[i][j]; j++){

                    heap.pop();

                    heap.push(matrix[i][j]);

                }

            }

            else{

                for(int j = 0; j < n && heap.top() > matrix[i][j]; j++){

                    heap.pop();

                    heap.push(matrix[i][j]);

                }

            }

        }

        return heap.top();

    }
http://www.jiuzhang.com/solutions/kth-smallest-number-in-sorted-matrix/
    public class Point {
        public int x, y, val;
        public Point(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    } 
    
    Comparator<Point> comp = new Comparator<Point>() {
        public int compare(Point left, Point right) {
            return left.val - right.val;
        }
    };
    
    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        if (k > matrix.length * matrix[0].length) {
            return 0;
        }
        return horizontal(matrix, k);
    }
    
    private int horizontal(int[][] matrix, int k) {
        Queue<Point> heap = new PriorityQueue<Point>(k, comp);
        for (int i = 0; i < Math.min(matrix.length, k); i++) {
            heap.offer(new Point(i, 0, matrix[i][0]));
        }
        for (int i = 0; i < k - 1; i++) {
            Point curr = heap.poll();
            if (curr.y + 1 < matrix[0].length) {
                heap.offer(new Point(curr.x, curr.y + 1, matrix[curr.x][curr.y + 1]));
            }
        }
        return heap.peek().val;
    }
https://all4win78.wordpress.com/2016/08/01/leetcode-378-kth-smallest-element-in-a-sorted-matrix/
http://blog.csdn.net/yeqiuzs/article/details/52089480
http://www.voidcn.com/blog/corpsepiges/article/p-6132483.html
public int kthSmallest(int[][] matrix, int k) {  
    PriorityQueue<Integer> heap = new PriorityQueue<Integer>(new Comparator<Integer>() {  
        public int compare(Integer a0, Integer a1) {  
            if(a0>a1){  
                return -1;  
            }else if(a0<a1){  
                return 1;  
            }  
            return 0;  
        }  
    });// 最大堆  
    for (int i = 0; i < matrix.length; i++) {  
        for (int j = 0; j < matrix.length; j++) {  
            if (i * matrix.length + j + 1 > k) {  
                if (matrix[i][j] < heap.peek()) {  
                    heap.poll();  
                    heap.offer(matrix[i][j]);  
                }  
            } else {  
                heap.offer(matrix[i][j]);  
            }  
        }  
  
    }  
    return heap.peek();  
}  

https://discuss.leetcode.com/topic/52924/java-klog-k-37ms-solution-with-heap
-- not that efficient
public int kthSmallest(int[][] matrix, int k) {
  if(matrix == null || matrix.length == 0) return 0;
  PriorityQueue<int[]> pq = new PriorityQueue< >(3, new Comparator<int[]>(){
   public int compare(int[] a1, int[] a2){
    return  (a1[0]  - a2[0]);
   }
  });
  int[] cur = new int[3]; // cur[0] : matrix[rindex][cindex], cur[1]: rowIndex, cur[2]: colIndex
  for (int j = 0; j < matrix[0].length && j < k; j++)  {
   pq.offer(new int[]{matrix[0][j], 0, j});
  }
  while (k > 0 && !pq.isEmpty()) {
   cur = pq.poll();
   int rindex = cur[1];
   int cindex = cur[2];
   if (rindex < matrix.length - 1) {
    pq.offer(new int[]{matrix[rindex+1][cindex], rindex + 1, cindex});
   }
   k--;
  }
  return cur[0];
 }
http://xiadong.info/2016/08/leetcode-378-kth-smallest-element-in-a-sorted-matrix/
首先的想法是每次从n行中取最前端的n个值中的最小值, 然后这个值从该行删除, 重复k次. 时间复杂度O(nk). 实际运行时间280ms.
    int kthSmallest(vector<vector<int>>& matrix, int k) {
        int n = matrix.size();
        vector<int> matrixPos(n, 0);
        int ret;
        for(int i = 0; i < k; i++){
            int minNum = INT_MAX, minPos = 0;
            for(int j = 0; j < n; j++){
                if(matrixPos[j] == n) continue;
                if(matrix[j][matrixPos[j]] < minNum){
                    minNum = matrix[j][matrixPos[j]];
                    minPos = j;
                }
            }
            matrixPos[minPos]++;
            ret = minNum;
        }
        return ret;
    }

https://discuss.leetcode.com/topic/53161/hashmap-treeset-approach-java-o-n-2-logn-n-time-and-o-n-2-space-complexity
-- then why not use treemap

X. https://discuss.leetcode.com/topic/53126/o-n-from-paper-yes-o-rows
It's O(n) where n is the number of rows (and columns), not the number of elements. So it's very efficient. The algorithm is from the paper Selection in X + Y and matrices with sorted rows and columns, which I first saw mentioned by @elmirap (thanks).
The basic idea: Consider the submatrix you get by removing every second row and every second column. This has about a quarter of the elements of the original matrix. And the k-th element (k-th smallest I mean) of the original matrix is roughly the (k/4)-th element of the submatrix. So roughly get the (k/4)-th element of the submatrix and then use that to find the k-th element of the original matrix in O(n) time. It's recursive, going down to smaller and smaller submatrices until a trivial 2×2 matrix. For more details I suggest checking out the paper, the first half is easy to read and explains things well. Or @zhiqing_xiao's solution+explanation.
Cool: It uses variants of saddleback search that you might know for example from the Search a 2D Matrix II problem. And it uses the median of medians algorithm for linear-time selection.
Optimization: If k is less than n, we only need to consider the top-left k×k matrix. Similar if k is almost n2. So it's even O(min(n, k, n^2-k)), I just didn't mention that in the title because I wanted to keep it simple and because those few very small or very large k are unlikely, most of the time k will be "medium" (and average n2/2).
Implementation: I implemented the submatrix by using an index list through which the actual matrix data gets accessed. If [0, 1, 2, ..., n-1] is the index list of the original matrix, then [0, 2, 4, ...] is the index list of the submatrix and [0, 4, 8, ...] is the index list of the subsubmatrix and so on. This also covers the above optimization by starting with [0, 1, 2, ..., k-1] when applicable.
Application: I believe it can be used to easily solve the Find K Pairs with Smallest Sums problem in time O(k) instead of O(k log n), which I think is the best posted so far. I might try that later if nobody beats me to it (if you do, let me know :-). Update: I did that now.
    def kthSmallest(self, matrix, k):

        # The median-of-medians selection function.
        def pick(a, k):
            if k == 1:
                return min(a)
            groups = (a[i:i+5] for i in range(0, len(a), 5))
            medians = [sorted(group)[len(group) / 2] for group in groups]
            pivot = pick(medians, len(medians) / 2 + 1)
            smaller = [x for x in a if x < pivot]
            if k <= len(smaller):
                return pick(smaller, k)
            k -= len(smaller) + a.count(pivot)
            return pivot if k < 1 else pick([x for x in a if x > pivot], k)

        # Find the k1-th and k2th smallest entries in the submatrix.
        def biselect(index, k1, k2):

            # Provide the submatrix.
            n = len(index)
            def A(i, j):
                return matrix[index[i]][index[j]]
            
            # Base case.
            if n <= 2:
                nums = sorted(A(i, j) for i in range(n) for j in range(n))
                return nums[k1-1], nums[k2-1]

            # Solve the subproblem.
            index_ = index[::2] + index[n-1+n%2:]
            k1_ = (k1 + 2*n) / 4 + 1 if n % 2 else n + 1 + (k1 + 3) / 4
            k2_ = (k2 + 3) / 4
            a, b = biselect(index_, k1_, k2_)

            # Prepare ra_less, rb_more and L with saddleback search variants.
            ra_less = rb_more = 0
            L = []
            jb = n   # jb is the first where A(i, jb) is larger than b.
            ja = n   # ja is the first where A(i, ja) is larger than or equal to a.
            for i in range(n):
                while jb and A(i, jb - 1) > b:
                    jb -= 1
                while ja and A(i, ja - 1) >= a:
                    ja -= 1
                ra_less += ja
                rb_more += n - jb
                L.extend(A(i, j) for j in range(jb, ja))
                
            # Compute and return x and y.
            x = a if ra_less <= k1 - 1 else \
                b if k1 + rb_more - n*n <= 0 else \
                pick(L, k1 + rb_more - n*n)
            y = a if ra_less <= k2 - 1 else \
                b if k2 + rb_more - n*n <= 0 else \
                pick(L, k2 + rb_more - n*n)
            return x, y

        # Set up and run the search.
        n = len(matrix)
        start = max(k - n*n + n-1, 0)
        k -= n*n - (n - start)**2
        return biselect(range(start, min(n, start+k)), k, k)[0]
 * @author het
 *
 */
public class LeetCode378 {
	 public int kthSmallest(int[][] matrix, int k) {
	        int n = matrix.length;
	        int lo = matrix[0][0], hi = matrix[n - 1][n - 1];
	        while (lo <= hi) {
	            int mid = lo + (hi - lo) / 2;   // int mid = lo + (hi - lo) / 2;
	            int count = getLessEqual(matrix, mid);
	            if (count < k) lo = mid + 1;
	            else hi = mid - 1;
	        }
	        return lo;
	    }
	    
	    private int getLessEqual(int[][] matrix, int val) {
	        int res = 0;
	        int n = matrix.length, i = n - 1, j = 0;
	        while (i >= 0 && j < n) {
	            if (matrix[i][j] > val) i--;
	            else {
	                res += i + 1;
	                j++;
	            }
	        }
	        return res;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
