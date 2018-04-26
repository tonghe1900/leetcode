package alite.leetcode.xx3.extra;
/**
 * LeetCode 373 - Find K Pairs with Smallest Sums

http://bookshadow.com/weblog/2016/07/07/leetcode-find-k-pairs-with-smallest-sums/
You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
Define a pair (u,v) which consists of one element from the first array and one element from the second array.
Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
Example 1:
Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3

Return: [1,2],[1,4],[1,6]

The first 3 pairs are returned from the sequence:
[1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Example 2:
Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2

Return: [1,1],[1,1]

The first 2 pairs are returned from the sequence:
[1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
Example 3:
Given nums1 = [1,2], nums2 = [3],  k = 3 

Return: [1,3],[2,3]

All possible pairs are returned from the sequence:
[1,3],[2,3]
题目大意：
给定两个递增有序的整数数组nums1和nums2，以及一个整数k。
定义一个数对(u, v)，包含第一个数组中的一个元素以及第二个数组中的一个元素。
寻找和最小的k个这样的数对(u1,v1),(u2,v2) ...(uk,vk)。
https://discuss.leetcode.com/topic/50450/slow-1-liner-to-fast-solutions


X. Use Priority Queue
https://discuss.leetcode.com/topic/51163/java-priorityqueue-9ms-without-helper-class
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> res = new LinkedList<>();
        if(nums1==null || nums1.length==0 || nums2==null || nums2.length==0) {
            return res;
        }
        
        // index pair
        PriorityQueue<int[]> minQ = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] pair1, int[] pair2) {
                return (nums1[pair1[0]]+nums2[pair1[1]])-(nums1[pair2[0]]+nums2[pair2[1]]);
            }
            
        });
        
        
        minQ.offer(new int[]{0, 0});
        
        while (k>0 && !minQ.isEmpty()) {
            int[] pair=minQ.poll();
            int i = pair[0];
            int j = pair[1];
            res.add(new int[]{nums1[i], nums2[j]});
            k--;
            
            if(j+1<nums2.length) {
                minQ.offer(new int[]{i, j+1});
            }
            
            if(j==0 && i+1<nums1.length){ 
                minQ.offer(new int[] {i+1, 0}); //\\
            }

        }
        
        
        return res;
    }

https://discuss.leetcode.com/topic/50885/simple-java-o-klogk-solution-with-explanation
The run time complexity is O(kLogk) since que.size <= k and we do at most k loop.
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> que = new PriorityQueue<>((a,b)->a[0]+a[1]-b[0]-b[1]);
        List<int[]> res = new ArrayList<>();
        if(nums1.length==0 || nums2.length==0 || k==0) return res;
        for(int i=0; i<nums1.length && i<k; i++) que.offer(new int[]{nums1[i], nums2[0], 0});
        while(k-- > 0 && !que.isEmpty()){
            int[] cur = que.poll();
            res.add(new int[]{cur[0], cur[1]});
            if(cur[2] == nums2.length-1) continue;
            que.offer(new int[]{cur[0],nums2[cur[2]+1], cur[2]+1});
        }
        return res;
    }

https://discuss.leetcode.com/topic/50647/9ms-java-solution-with-explanation/2
https://discuss.leetcode.com/topic/52953/share-my-solution-which-beat-96-42
the basic idea is using a priority queue to store the candidates and pop them out in ascending order.
But, actually you don't need to store all of the pairs at the beginning, do you?
Imagine a matrix:
a1 a2 a3
a4 a5 a6
a7 a8 a9
The number in m[i][j] refers to the sum of nums1[i] + nums[j].
When you pop the value in m[0][1] (which is "a2"), actually you only need to put candidates m[1][1] ("a3") and m[0][2] ("a5") to the queue. And at this moment you don't need to care any other candidate.
In this way, the priority queue can be quite short comparing to putting all the pairs in the beginning.
Ok, now is there any thing you need to pay attention to? Yes, please notice there might be duplicates.
"a2" will lead to "a3" and "a5", while "a4" will lead to "a5" and "a7", thus "a5" will be a dup.
How to handle this?
I don't have a very good solution. my current one is to add the first column at the beginning, then each time you pop one item, you just add the one on the right and ignore the one below (since it will be someone else's right unless it's in the first column).
public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) 
{
        List<int[]> result = new LinkedList<int[]>();
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0 || k == 0) return result;
        
        PriorityQueue<Triple> queue = new PriorityQueue<Triple>(nums1.length, new Comparator<Triple>() {
            public int compare(Triple a, Triple b)
            {
                return Integer.compare(a.val, b.val);
            }
        });

// add the first column
        for (int i=0; i<nums1.length; i++)
        {
            queue.add(new Triple(nums1[i]+nums2[0], i, 0));
        }
        
        while (k-- > 0 && !queue.isEmpty())
        {
            Triple current = queue.poll();
            result.add(new int[]{nums1[current.one], nums2[current.two]});
// if the current one has a right candidate, add it to the queue. 
            if (current.two+1 < nums2.length)
                queue.add(new Triple(nums1[current.one]+nums2[current.two+1], current.one, current.two+1));
        }
        
        return result;
}
    
// Triple is used to store the sum, the index in nums1 and the index in nums2.
    class Triple
    {
        int val;
        int one;
        int two;
        Triple (int val, int one, int two)
        {
            this.val = val;
            this.one = one;
            this.two = two;
        }
    }
To get the idea clear, you can think that this is the problem to merge k sorted arrays.
array1 = (0,0),(0,1),(0,2),....
array2 = (1,0),(1,1),(1,2),....
....
arrayk = (k-1,0),(k-1,1),(k-1,2),....
So, each time when an array is chosen having the smallest sum, you only move its index to next one of this array.
https://discuss.leetcode.com/topic/50435/java-easy-understandable-bfs-with-priorityqueue
use a matrix to represent all combination of pairs from nums1 and nums2. Do a bfs from [0][0]. However, use a PriorityQueue instead of regular queue.

assuming nums1.length == nums2.length == n. time complexity should be klogn. The PQ's maximum size should be a linear function of n, not n^2. Image it is a regular bfs using a queue. The maximum size of PQ happens when all elements are along the diagonal line of matrix, which is sqrt2*n.

-- Uses too much space: visited[][]

    final int[][] neighbors = {{0, 1}, {1, 0}};
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> list = new ArrayList<>();
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0 || k == 0) {
            return list;
        }
        int m = nums1.length, n = nums2.length;
        boolean[][] visited = new boolean[m][n];
        Queue<Pair> minHeap = new PriorityQueue<>();
        minHeap.offer(new Pair(0, 0, nums1[0] + nums2[0]));
        visited[0][0] = true;
        while (k > 0 && !minHeap.isEmpty()) {
            Pair min = minHeap.poll();
            list.add(new int[] {nums1[min.row], nums2[min.col]});
            k--;
            for (int[] neighbor : neighbors) {
                int row1 = min.row + neighbor[0];
                int col1 = min.col + neighbor[1];
                if (row1 < 0 || row1 == m || col1 < 0 || col1 == n || visited[row1][col1]) {
                    continue;
                }
                visited[row1][col1] = true;
                minHeap.offer(new Pair(row1, col1, nums1[row1] + nums2[col1]));
            }
        }
        return list;
    }
}

class Pair implements Comparable<Pair> {
    int row;
    int col;
    int value;
    
    Pair(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }
    
    public int compareTo(Pair other) {
        return value - other.value;
    } 
}
X. O(k) solution
https://discuss.leetcode.com/topic/53380/o-k-solution
Now that I can find the kth smallest element in a sorted n×n matrix in time O(min(n, k)), I can finally solve this problem in O(k).
The idea:
If nums1 or nums2 are larger than k, shrink them to size k.
Build a virtual matrix of the pair sums, i.e., matrix[i][j] = nums1[i] + nums2[j]. Make it a square matrix by padding with "infinity" if necessary. With "virtual" I mean its entries will be computed on the fly, and only those that are needed. This is necessary to stay within O(k) time.
Find the kth smallest sum kthSum by using that other algorithm.
Use a saddleback search variation to discount the pairs with sum smaller than kthSum. After this, k tells how many pairs we need whose sum equals kthSum.
Collect all pairs with sum smaller than kthSum as well as k pairs whose sum equals kthSum.
Each of those steps only takes O(k) time.

The code (minus the code for kthSmallest, which you can copy verbatim from my solution to the other problem):
Thanks to @zhiqing_xiao for pointing out that my previous way of capping the input lists might not be O(k). It was this:
def kSmallestPairs(self, nums1, nums2, k):
    del nums1[k:]
    del nums2[k:]
    def kSmallestPairs(self, nums1_, nums2_, k):

        # Use at most the first k of each, then get the sizes.
        nums1 = nums1_[:k]
        nums2 = nums2_[:k]
        m, n = len(nums1), len(nums2)

        # Gotta Catch 'Em All?
        if k >= m * n:
            return [[a, b] for a in nums1 for b in nums2]
        
        # Build a virtual matrix.
        N, inf = max(m, n), float('inf')
        class Row:
            def __init__(self, i):
                self.i = i
            def __getitem__(self, j):
                return nums1[self.i] + nums2[j] if self.i < m and j < n else inf
        matrix = map(Row, range(N))

        # Get the k-th sum.
        kthSum = self.kthSmallest(matrix, k)

        # Discount the pairs with sum smaller than the k-th.
        j = min(k, n)
        for a in nums1:
            while j and a + nums2[j-1] >= kthSum:
                j -= 1
            k -= j

        # Collect and return the pairs.
        pairs = []
        for a in nums1:
            for b in nums2:
                if a + b >= kthSum + (k > 0):
                    break
                pairs.append([a, b])
                k -= a + b == kthSum
        return pairs

    def kthSmallest(self, matrix, k):
        
        # copy & paste from https://discuss.leetcode.com/topic/53126/o-n-from-paper-yes-o-rows


X. Don't use PQ
https://discuss.leetcode.com/topic/50527/java-10ms-solution-no-priority-queue
https://discuss.leetcode.com/topic/50632/java-priority-queue-or-without-priority-queue
With Priority Queue. Scan [k+1, 2K] pair. Heap size is [2, k]. O(klogk) time, O(k) space
Without Priority Queue. O(N^2) time, O(1) space
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
      List<int[]> sPairs = new ArrayList<>();
      if (nums1 == null || nums1.length == 0 || nums2 == null
              || nums2.length == 0 || k == 0) return sPairs;

      int len1 = nums1.length, len2 = nums2.length;
      int[] nums2idx = new int[len1]; // map to last used element in nums2
      while (sPairs.size() < k) {
        int minSoFar = Integer.MAX_VALUE;
        int nums1pos = -1;
        // find smallest pair
        for (int i = 0; i < len1; i++) {
          if (nums2idx[i] >= len2) {
            continue;
          }
          if (nums1[i] + nums2[nums2idx[i]] <= minSoFar) {
            minSoFar = nums1[i] + nums2[nums2idx[i]];
            nums1pos = i;
          }
        }

        if (nums1pos == -1) {
          break;
        }

        sPairs.add(new int[]{nums1[nums1pos], nums2[nums2idx[nums1pos]]});
        nums2idx[nums1pos]++;
      }

      return sPairs;
    }

http://blog.csdn.net/yeqiuzs/article/details/51851359
用最小堆保存遍历过程中的pairs。
当遍历到(i,j)时，继续往下遍历，相邻的结点是(i+1,j)，(i,j+1) 
因为是有序数组，这两个pair是较小的，加入堆中。
 public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
  List<int[]> res = new ArrayList<int[]>();
  boolean visit[][] = new boolean[nums1.length][nums2.length];
  Queue<int[]> heap = new PriorityQueue<int[]>(new Comparator<int[]>(){
   public int compare(int[] i, int[] j) {
    return (nums1[i[0]] + nums2[i[1]] -( nums1[j[0]] + nums2[j[1]]));
   }
  });

  heap.add(new int[] { 0, 0 });
  visit[0][0] = true;

  while (!heap.isEmpty() && res.size() < k) {
   int d[] = heap.poll();
   res.add(new int[] { nums1[d[0]], nums2[d[1]] });

   if (d[1] + 1 < nums2.length && visit[d[0]][d[1] + 1] == false) {
    heap.add(new int[] { d[0], d[1] + 1 });
    visit[d[0]][d[1] + 1] = true;
   }
   if (d[0] + 1 < nums1.length && visit[d[0]+1][d[1]] == false) {
    heap.add(new int[] { d[0]+1, d[1]});
    visit[d[0]+1][d[1]] = true;
   }
  }
  return res;
 }
https://www.hrwhisper.me/leetcode-find-k-pairs-smallest-sums/
最直接的思路是把全部的数都组合一下，然后维护一个大小为k的最大堆 这样复杂度O(m*n*logk)
其实还可以维护一个最小堆，每次取出堆顶的元素，然后把该元素相邻结点加入进去。这样能保证最小。

struct Order {

 int sum;

 int index1, index2;

 Order(int a, int b, int sum) : index1(a), index2(b), sum(sum) {}

 bool operator < (const Order& b) const {

  return sum > b.sum;

 }

};

int dx[] = { 1,0 };

int dy[] = { 0,1 };

class Solution {

public:

 vector<pair<int, int>> kSmallestPairs(vector<int>& nums1, vector<int>& nums2, int k) {

  vector<pair<int, int>> ans;

  if (nums1.empty() || nums2.empty()) return ans;


  int n = nums1.size(), m = nums2.size();

  vector<vector<bool>> vis(n, vector<bool>(m, false));

  priority_queue<Order> q;


  q.push(Order(0, 0, nums1[0] + nums1[1]));

  vis[0][0] = true;


  while (k > 0 && !q.empty()) {

   Order cur = q.top();

   q.pop();

   k--;

   int x = cur.index1, y = cur.index2;

   ans.push_back(make_pair(nums1[x], nums2[y]));

   for (int i = 0; i < 2; i++) {

    int nx = x + dx[i], ny = y + dy[i];

    if (nx < n && ny < m && !vis[nx][ny]) {

     q.push(Order(nx, ny, nums1[nx] + nums2[ny]));

     vis[nx][ny] = true;

    }

   }

  }

  return ans;

 }

};
http://www.2cto.com/px/201607/524030.html
 public List kSmallestPairs(int[] nums1, int[] nums2, int k) {
  List res = new ArrayList();
  boolean visit[][] = new boolean[nums1.length][nums2.length];
  Queue heap = new PriorityQueue(new Comparator(){
   public int compare(int[] i, int[] j) {
    return (nums1[i[0]] + nums2[i[1]] -( nums1[j[0]] + nums2[j[1]]));
   }
  });

  heap.add(new int[] { 0, 0 });
  visit[0][0] = true;

  while (!heap.isEmpty() && res.size() < k) {
   int d[] = heap.poll();
   res.add(new int[] { nums1[d[0]], nums2[d[1]] });

   if (d[1] + 1 < nums2.length && visit[d[0]][d[1] + 1] == false) {
    heap.add(new int[] { d[0], d[1] + 1 });
    visit[d[0]][d[1] + 1] = true;
   }
   if (d[0] + 1 < nums1.length && visit[d[0]+1][d[1]] == false) {
    heap.add(new int[] { d[0]+1, d[1]});
    visit[d[0]+1][d[1]] = true;
   }
  }
  return res;
 }


def kSmallestPairs(self, nums1, nums2, k):
    queue = []
    def push(i, j):
        if i < len(nums1) and j < len(nums2):
            heapq.heappush(queue, [nums1[i] + nums2[j], i, j])
    push(0, 0)
    pairs = []
    while queue and len(pairs) < k:
        _, i, j = heapq.heappop(queue)
        pairs.append([nums1[i], nums2[j]])
        push(i, j + 1)
        if j == 0:
            push(i + 1, 0)
    return pairs

http://www.programcreek.com/2015/07/leetcode-find-k-pairs-with-smallest-sums-java/
This problem is similar to Super Ugly Number. The basic idea is using an array to track the index of the next element in the other array.
The best way to understand this solution is using an example such as nums1={1,3,11} and nums2={2,4,8}.
http://blog.csdn.net/qq508618087/article/details/51864835
这种思想有点像寻找第k个丑数, 记录一下nums1中每个数对应nums2中组合的位置. 然后每次找到能够产生最小的组合即可.
public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<int[]> result = new ArrayList<int[]>();
 
    k = Math.min(k, nums1.length*nums2.length);
 
    if(k==0)
        return result;
 
    int[] idx = new int[nums1.length];
 
    while(k>0){
        int min = Integer.MAX_VALUE;
        int t=0;
        for(int i=0; i<nums1.length; i++){
            if(idx[i]<nums2.length && nums1[i]+nums2[idx[i]]<min){
                t=i;
                min = nums1[i]+nums2[idx[i]];
            }
        }
 
        int[] arr = {nums1[t], nums2[idx[t]]};    
        result.add(arr);
 
        idx[t]++;
 
        k--;
    }
 
    return result;
}
X. https://discuss.leetcode.com/topic/50527/java-10ms-solution-no-priority-queue/2 TODO
Because both array are sorted, so we can keep track of the paired index. Therefore, we do not need to go through all combinations when k < nums1.length + num2.length. Time complexity is O(k*m) where m is the length of the shorter array.
public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> ret = new ArrayList<int[]>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return ret;
        }
        
        int[] index = new int[nums1.length];
        while (k-- > 0) {
            int min_val = Integer.MAX_VALUE;
            int in = -1;
            for (int i = 0; i < nums1.length; i++) {
                if (index[i] >= nums2.length) {
                    continue;
                }
                if (nums1[i] + nums2[index[i]] < min_val) {
                    min_val = nums1[i] + nums2[index[i]];
                    in = i;
                }
            }
            if (in == -1) {
                break;
            }
            int[] temp = {nums1[in], nums2[index[in]]};
            ret.add(temp);
            index[in]++;
        }
        return ret;
    }
https://discuss.leetcode.com/topic/51721/anybody-use-this-dp-method
public class Solution
 {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int len1=nums1.length, len2=nums2.length;
        int[] step= new int[len1];
        List<int[]> res= new ArrayList<int[]>();
        
        //记录一下从nums1到nums2的步数
        for(int i=0; i<Math.min(k,len1*len2); i++)
        {
            int min=Integer.MAX_VALUE;
            for(int a=0; a<len1; a++)
            {
                if(step[a]>=len2) continue;
                min=Math.min(min, nums1[a]+nums2[step[a]]);
            }
            for(int a=0; a<len1; a++)
            {
                if(step[a]>=len2) continue;
                if(nums1[a]+nums2[step[a]]==min)
                {
                    res.add(new int[]{nums1[a], nums2[step[a]]});
                    step[a]++;
                    break;
                }
            }
        }
        return res;
    }
}
X. Brute Force
http://www.cnblogs.com/grandyang/p/5653127.html
https://discuss.leetcode.com/topic/50514/java-brute-force-solution-with-priorityqueue-150ms
Time Complexity: O(mnlogK)
Space: O(K)
    public class Pair{
        int i1;
        int i2;
        int sum;
        public Pair(int sum, int i1, int i2){
            this.sum = sum;
            this.i1 = i1;
            this.i2 = i2;
        }
    }
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> result = new ArrayList<>();
        if(nums1 == null || nums2 == null) return result;
        int len1 = nums1.length, len2 = nums2.length;

        PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p2.sum - p1.sum);
        for(int i = 0; i < len1; i++){
            for(int j = 0; j < len2; j++){
                int sum = nums1[i] + nums2[j];
                if(pq.size() < k)
                    pq.offer(new Pair(sum, i, j));
                else if (sum < pq.peek().sum) {
                    pq.poll();
                    pq.offer(new Pair(sum, i, j));
                }
            }
        }
        
        while(!pq.isEmpty()){
            Pair p = pq.poll();
            result.add(0, new int[]{nums1[p.i1], nums2[p.i2]});
        }
        
        return result;
            
    }
}

    vector<pair<int, int>> kSmallestPairs(vector<int>& nums1, vector<int>& nums2, int k) {
        vector<pair<int, int>> res;
        for (int i = 0; i < min((int)nums1.size(), k); ++i) {
            for (int j = 0; j < min((int)nums2.size(), k); ++j) {
                res.push_back({nums1[i], nums2[j]});
            }
        }
        sort(res.begin(), res.end(), [](pair<int, int> &a, pair<int, int> &b){return a.first + a.second < b.first + b.second;});
        if (res.size() > k) res.erase(res.begin() + k, res.end());
        return res;
    }

http://www.voidcn.com/blog/niuooniuoo/article/p-6091497.html
 * @author het
 *
 */
public class LeetCode373 {
//	// continue at study it 
//	 public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
//	        List<int[]> res = new LinkedList<>();
//	        if(nums1==null || nums1.length==0 || nums2==null || nums2.length==0) {
//	            return res;
//	        }
//	        
//	        // index pair
//	        PriorityQueue<int[]> minQ = new PriorityQueue<>(new Comparator<int[]>(){
//	            public int compare(int[] pair1, int[] pair2) {
//	                return (nums1[pair1[0]]+nums2[pair1[1]])-(nums1[pair2[0]]+nums2[pair2[1]]);
//	            }
//	            
//	        });
//	        
//	        
//	        minQ.offer(new int[]{0, 0});
//	        
//	        while (k>0 && !minQ.isEmpty()) {
//	            int[] pair=minQ.poll();
//	            int i = pair[0];
//	            int j = pair[1];
//	            res.add(new int[]{nums1[i], nums2[j]});
//	            k--;
//	            
//	            if(j+1<nums2.length) {
//	                minQ.offer(new int[]{i, j+1});
//	            }
//	            
//	            if(j==0 && i+1<nums1.length){ 
//	                minQ.offer(new int[] {i+1, 0}); //\\
//	            }
//
//	        }
//	        
//	        
//	        return res;
//	    }
	
	
//	the basic idea is using a priority queue to store the candidates and pop them out in ascending order.
//	But, actually you don't need to store all of the pairs at the beginning, do you?
//	Imagine a matrix:
//	a1 a2 a3
//	a4 a5 a6
//	a7 a8 a9
//	The number in m[i][j] refers to the sum of nums1[i] + nums[j].
//	When you pop the value in m[0][1] (which is "a2"), actually you only need to put candidates m[1][1] ("a3") and m[0][2] ("a5") to the queue. And at this moment you don't need to care any other candidate.
//	In this way, the priority queue can be quite short comparing to putting all the pairs in the beginning.
//	Ok, now is there any thing you need to pay attention to? Yes, please notice there might be duplicates.
//	"a2" will lead to "a3" and "a5", while "a4" will lead to "a5" and "a7", thus "a5" will be a dup.
//	How to handle this?
//	I don't have a very good solution. my current one is to add the first column at the beginning, then each time you pop one item, you just add the one on the right and ignore the one below (since it will be someone else's right unless it's in the first column).
	public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) 
	{
	        List<int[]> result = new LinkedList<int[]>();
	        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0 || k == 0) return result;
	        
	        PriorityQueue<Triple> queue = new PriorityQueue<Triple>(nums1.length, new Comparator<Triple>() {
	            public int compare(Triple a, Triple b)
	            {
	                return Integer.compare(a.val, b.val);
	            }
	        });

	// add the first column
	        for (int i=0; i<nums1.length; i++)
	        {
	            queue.add(new Triple(nums1[i]+nums2[0], i, 0));
	        }
	        
	        while (k-- > 0 && !queue.isEmpty())
	        {
	            Triple current = queue.poll();
	            result.add(new int[]{nums1[current.one], nums2[current.two]});
	// if the current one has a right candidate, add it to the queue. 
	            if (current.two+1 < nums2.length)
	                queue.add(new Triple(nums1[current.one]+nums2[current.two+1], current.one, current.two+1));
	        }
	        
	        return result;
	}
	    
	// Triple is used to store the sum, the index in nums1 and the index in nums2.
	    class Triple
	    {
	        int val;
	        int one;
	        int two;
	        Triple (int val, int one, int two)
	        {
	            this.val = val;
	            this.one = one;
	            this.two = two;
	        }
	    }
//	To get the idea clear, you can think that this is the problem to merge k sorted arrays.
//	array1 = (0,0),(0,1),(0,2),....
//	array2 = (1,0),(1,1),(1,2),....
//	....
//	arrayk = (k-1,0),(k-1,1),(k-1,2),....
//	So, each time when an array is chosen having the smallest sum, you only move its index to next one of this array.
//	https://discuss.leetcode.com/topic/50435/java-easy-understandable-bfs-with-priorityqueue
//	use a matrix to represent all combination of pairs from nums1 and nums2. Do a bfs from [0][0]. However, use a PriorityQueue instead of regular queue.

	//assuming nums1.length == nums2.length == n. time complexity should be klogn. The PQ's maximum size should be a linear function of n, not n^2. Image it is a regular bfs using a queue. The maximum size of PQ happens when all elements are along the diagonal line of matrix, which is sqrt2*n.

	//-- Uses too much space: visited[][]

	    final int[][] neighbors = {{0, 1}, {1, 0}};
	    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
	        List<int[]> list = new ArrayList<>();
	        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0 || k == 0) {
	            return list;
	        }
	        int m = nums1.length, n = nums2.length;
	        boolean[][] visited = new boolean[m][n];
	        Queue<Pair> minHeap = new PriorityQueue<>();
	        minHeap.offer(new Pair(0, 0, nums1[0] + nums2[0]));
	        visited[0][0] = true;
	        while (k > 0 && !minHeap.isEmpty()) {
	            Pair min = minHeap.poll();
	            list.add(new int[] {nums1[min.row], nums2[min.col]});
	            k--;
	            for (int[] neighbor : neighbors) {
	                int row1 = min.row + neighbor[0];
	                int col1 = min.col + neighbor[1];
	                if (row1 < 0 || row1 == m || col1 < 0 || col1 == n || visited[row1][col1]) {
	                    continue;
	                }
	                visited[row1][col1] = true;
	                minHeap.offer(new Pair(row1, col1, nums1[row1] + nums2[col1]));
	            }
	        }
	        return list;
	    }
	}

	class Pair implements Comparable<Pair> {
	    int row;
	    int col;
	    int value;
	    
	    Pair(int row, int col, int value) {
	        this.row = row;
	        this.col = col;
	        this.value = value;
	    }
	    
	    public int compareTo(Pair other) {
	        return value - other.value;
	    } 
	}
	
	
	
	
//	X. https://discuss.leetcode.com/topic/50527/java-10ms-solution-no-priority-queue/2 TODO
	//	Because both array are sorted, so we can keep track of the paired index. Therefore, we do not need to go through all combinations when k < nums1.length + num2.length. Time complexity is O(k*m) where m is the length of the shorter array.
		public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		        List<int[]> ret = new ArrayList<int[]>();
		        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
		            return ret;
		        }
		        
		        int[] index = new int[nums1.length];
		        while (k-- > 0) {
		            int min_val = Integer.MAX_VALUE;
		            int in = -1;
		            for (int i = 0; i < nums1.length; i++) {
		                if (index[i] >= nums2.length) {
		                    continue;
		                }
		                if (nums1[i] + nums2[index[i]] < min_val) {
		                    min_val = nums1[i] + nums2[index[i]];
		                    in = i;
		                }
		            }
		            if (in == -1) {
		                break;
		            }
		            int[] temp = {nums1[in], nums2[index[in]]};
		            ret.add(temp);
		            index[in]++;
		        }
		        return ret;
		    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
