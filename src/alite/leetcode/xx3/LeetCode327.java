package alite.leetcode.xx3;
/**
 * LeetCode 327 - Count of Range Sum

leetcode Count of Range Sum - 细语呢喃
Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.
Note:
A naive algorithm of O(n2) is trivial. You MUST do better than that.
Example:
Given nums = [-2, 5, -1], lower = -2, upper = 2,
Return 3.
The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.

http://www.1point3acres.com/bbs/thread-165074-1-1.html
题意很简单，就是给你一个数组，求数组的所有子数组和(sub array sum)在区间[lower, upper]之内的个数

题解我blog上有~，用的Merge Sort，边排序边数出来
给定一个整数组成的数组，求它的所有子区间和坐落于[lower, upper] 的个数。
比如样例中[-2, 5, -1]中，这三个区间的和在[-2,2]之间 [0, 0], [2, 2], [0, 2]
http://algobox.org/count-of-range-sum/
let’s look at the naive solution. Preprocess to calculate the prefix sums S_i = S(0, i),
 then S(i, j) = S_j - S_i. Note that here we define S(i, j) as the sum of range [i, j) where j exclusive. 
 With these prefix sums, it is trivial to see that with O(n^2) time we can find all S(i, j) in the range [lower, upper].
Java – Naive Solution

1
2
3
4
5
6
7
8
9
10
11
12


public int countRangeSum(int[] nums, int lower, int upper) {


    int n = nums.length;


    long[] sums = new long[n + 1];


    for (int i = 0; i < n; ++i)


        sums[i + 1] = sums[i] + nums[i];


    int ans = 0;


    for (int i = 0; i < n; ++i)


        for (int j = i + 1; j <= n; ++j)


            if (sums[j] - sums[i] >= lower && sums[j] - sums[i] <= upper)


                ans++;


    return ans;


}

X. Merge Sort
Recall count smaller number after self where we encountered the problem
C_i = \mbox{count of } x_j - x_i < 0, j > i
Here, after we did the preprocess, we need to solve the problem
C_i = \mbox{count of } a <= S_j - S_i <= b, j > i
\mbox{Return }\sum_0^{n-1} C_i
Therefore, the two problems are almost the same. We can use the same technique used in that one to solve this one. 
One solution is merge sort based; another is Balanced BST based. The time complexity are both O(n \log n).
The merge sort based solution counts the answer while doing the merge. 
During the merge stage, we have already sorted the left half [start, mid) and right half [mid, end). 
We then iterate through the left half with index i. For eachi, we need to find two indices k and j in the right half where
j is the first index satisfy S_j - S_i > upper;
k is the first index satisfy S_k - S_i >= lower.
Then the number of sums in [lower, upper] is j-k. We also use another index t to copy the elements satisfy S_t < S_i to a cache in order to complete the merge sort.
Despite the nested loops, the time complexity of the merge & count stage is still linear. Because the indices k, j, twill only increase but not decrease. Therefore, each of them will traversal the right half once at most. The total time complexity of this divide and conquer solution is then O(n \log n).
One other concern is that the sums may overflow the 32bit integer. So we use long instead.
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25


public int countRangeSum(int[] nums, int lower, int upper) {


    int n = nums.length;


    long[] sums = new long[n + 1];


    for (int i = 0; i < n; ++i)


        sums[i + 1] = sums[i] + nums[i];


    return countWhileMergeSort(sums, 0, n + 1, lower, upper);


}


private int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {


    if (end - start <= 1) return 0;


    int mid = (start + end) / 2;


    int count = countWhileMergeSort(sums, start, mid, lower, upper) 


              + countWhileMergeSort(sums, mid, end, lower, upper);


    int j = mid, k = mid, t = mid;


    long[] cache = new long[end - start];


    for (int i = start, r = 0; i < mid; ++i, ++r) {


        while (k < end && sums[k] - sums[i] < lower) k++;


        while (j < end && sums[j] - sums[i] <= upper) j++;


        while (t < end && sums[t] < sums[i]) cache[r++] = sums[t++];


        cache[r] = sums[i];


        count += j - k;


    }


    System.arraycopy(cache, 0, sums, start, t - start);


    return count;


}

http://www.cnblogs.com/EdwardLiu/p/5138198.html
Therefore the two problems are almost the same. We can use the same technique used in that problem to solve this problem. One solution is merge sort based; another one is Balanced BST based. The time complexity are both O(n log n).
The merge sort based solution counts the answer while doing the merge. During the merge stage, we have already sorted the left half [start, mid) and right half [mid, end). We then iterate through the left half with index i. For each i, we need to find two indices k and j in the right half where
j is the first index satisfy sums[j] - sums[i] > upper and
k is the first index satisfy sums[k] - sums[i] >= lower.
Then the number of sums in [lower, upper] is j-k. We also use another index t to copy the elements satisfy sums[t] < sums[i] to a cache in order to complete the merge sort.
Despite the nested loops, the time complexity of the "merge & count" stage is still linear. Because the indices k, j, t will only increase but not decrease, each of them will only traversal the right half once at most. The total time complexity of this divide and conquer solution is then O(n log n).
One other concern is that the sums may overflow integer. So we use long instead.
https://leetcode.com/discuss/79154/short-%26-simple-o-n-log-n
def countRangeSum(self, nums, lower, upper):
    first = [0]
    for num in nums:
        first.append(first[-1] + num)
    def sort(lo, hi):
        mid = (lo + hi) / 2
        if mid == lo:
            return 0
        count = sort(lo, mid) + sort(mid, hi)
        i = j = mid
        for left in first[lo:mid]:
            while i < hi and first[i] - left <  lower: i += 1
            while j < hi and first[j] - left <= upper: j += 1
            count += j - i
        first[lo:hi] = sorted(first[lo:hi])
        return count
    return sort(0, len(first))
First compute the prefix sums: first[m] is the sum of the first m numbers.
Then the sum of any subarray nums[i:k] is simply first[k] - first[i].
So we just need to count those where first[k] - first[i] is in [lower,upper].
To find those pairs, I use mergesort with embedded counting. The pairs in the left half and the pairs in the right half get counted in the recursive calls. We just need to also count the pairs that use both halves.
For each left in first[lo:mid] I find all right in first[mid:hi] so that right - left lies in [lower, upper]. Because the halves are sorted, these fitting right values are a subarrayfirst[i:j]. With increasing left we must also increase right, meaning must we leave outfirst[i] if it's too small and and we must include first[j] if it's small enough.
Besides the counting, I also need to actually merge the halves for the sorting. I let sorted do that, which uses Timsort and takes linear time to recognize and merge the already sorted halves.
https://leetcode.com/discuss/79083/share-my-solution
The key point is that we do not need to count from mid again and again. That is to say, k and j continues to increase is the key. Some simple mathematical property stands behind it. Your solution also represents a general method.

Actually the general method is related to the so called "two pointers" technique, although we do have a lot more than two here :P. The "property" behind two pointers is monotonicity. To utilize monotonicity you have to sort. That is why "merge sort" show up in mind.
The difference between this kind of problems and easy two pointer problems are that they also have relative position constraints which will be destroyed after sorting. Thus, we have to do this during sorting when partially sorted and we still have some relative positions.
http://www.qinshaoxuan.com/articles/930.html
先预处理前缀和 sumi+1=sumi+numsi
s
u
m
i
1
s
u
m
i
n
u
m
s
i
 ，于是区间[i,j]的和等于 sumj−sumi
s
u
m
j
s
u
m
i
 （这里和平时的写法不一样，是方便后面处理归并时的坐标）
巧妙的地方在于，利用归并排序，使左右区间的sum有序，而且不会破坏左区间和右区间的相对位置，这样就可以用二指针了。
枚举左区间的坐标i，找到右区间中：
第一个 sumrl−sumi
s
u
m
r
l
s
u
m
i
 大于等于lower的临界值坐标rl
第一个 sumrr−sumi
s
u
m
r
r
s
u
m
i
 大于upper的临界值坐标rr
则贡献的区间个数为 rr−rl
r
r
r
l
因为左右区间都是单调递增的，当i向右移，rl和rr也只可能向右移，所以合并时复杂度为 O(n)
O
n
计算完后进行归并排序，保证当前区间sum有序
因为只需要计算区间的个数，所以利用前缀和就可以避开不能改变位置的问题，巧妙的利用排序求解。
所以不能因为是统计区间就习惯性的排除排序这种方法啊=。=
还要注意要用long，数据的范围是int，但两个数相加就可能超出int范围
时间复杂度 O(nlog(n))

X. Binary Search Tree
http://www.cnblogs.com/EdwardLiu/p/5138198.html
这个做法是建立BST，把prefix sum作为TreeNode.val存进去，为了避免重复的TreeNode.val处理麻烦，设置一个count记录多少个重复
TreeNode.val, 维护leftSize, 记录比该节点value小的节点个数，rightSize同理

由于RangeSum S(i,j)在[lower,upper]之间的条件是lower<=sums[j+1]-sums[i]<=upper,
所以我们每次insert一个新的PrefixSum sums[k]进这个BST之前，先寻找一下（rangeSize）该BST内已经有多少个PrefixSum（叫它sums[t]吧）
满足lower<=sums[k]-sums[t]<=upper, 即寻找有多少个sums[t]满足： 

sums[k]-upper<=sums[t]<=sums[k]-lower

BST提供了countSmaller和countLarger的功能，计算比sums[k]-upper小的RangeSum数目和比sums[k]-lower大的数目，再从总数里面减去，就是所求

 2     private class TreeNode {
 3         long val = 0;
 4         int count = 1;
 5         int leftSize = 0;
 6         int rightSize = 0;
 7         TreeNode left = null;
 8         TreeNode right = null;
 9         public TreeNode(long v) {
10             this.val = v;
11             this.count = 1;
12             this.leftSize = 0;
13             this.rightSize = 0;
14         }
15     }
16 
17     private TreeNode insert(TreeNode root, long val) {
18         if(root == null) {
19             return new TreeNode(val);
20         } else if(root.val == val) {
21             root.count++;
22         } else if(val < root.val) {
23             root.leftSize++;
24             root.left = insert(root.left, val);
25         } else if(val > root.val) {
26             root.rightSize++;
27             root.right = insert(root.right, val);
28         }
29         return root;
30     }
31 
32     private int countSmaller(TreeNode root, long val) {
33         if(root == null) {
34             return 0;
35         } else if(root.val == val) {
36             return root.leftSize;
37         } else if(root.val > val) {
38             return countSmaller(root.left, val);
39         } else {
40             return root.leftSize + root.count + countSmaller(root.right, val);
41         }
42     }
43 
44     private int countLarger(TreeNode root, long val) {
45         if(root == null) {
46             return 0;
47         } else if(root.val == val) {
48             return root.rightSize;
49         } else if(root.val < val) {
50             return countLarger(root.right, val);
51         } else {
52             return countLarger(root.left, val) + root.count + root.rightSize;
53         }
54     }
55 
56     private int rangeSize(TreeNode root, long lower, long upper) {
57         int total = root.count + root.leftSize + root.rightSize;
58         int smaller = countSmaller(root, lower);    // Exclude everything smaller than lower
59         int larger = countLarger(root, upper);      // Exclude everything larger than upper
60         return total - smaller - larger;
61     }
62 
63     public int countRangeSum(int[] nums, int lower, int upper) {
64         if(nums.length == 0) {
65             return 0;
66         }
67         long[] sums = new long[nums.length + 1];
68         for(int i = 0; i < nums.length; i++) {
69             sums[i + 1] = sums[i] + nums[i];
70         }
71         TreeNode root = new TreeNode(sums[0]);
72         int output = 0;
73         for(int i = 1; i < sums.length; i++) {
74             output += rangeSize(root, sums[i] - upper, sums[i] - lower);
75             insert(root, sums[i]);
76         }
77         return output;
78     }
https://discuss.leetcode.com/topic/34107/java-bst-solution-averagely-o-nlogn
The performance would be bad if all the numbers are positive or negative, where the BST is completely unbalanced.
http://huntzhan.org/leetcode-count-of-range-sum/
    struct TreeNode {
        TreeNode *left = nullptr;
        TreeNode *right = nullptr;
        int tree_size = 1;
        long long value = 0;
    };

    TreeNode *InsertToBST(TreeNode *root, long long value) {
        if (root == nullptr) {
            auto node = new TreeNode;
            node->value = value;
            return node;
        }

        if (value < root->value) {
            root->left = InsertToBST(root->left, value);
        } else {
            root->right = InsertToBST(root->right, value);
        }
        ++root->tree_size;
        return root;
    }

    int CountGEQ(TreeNode *root, long long hint) {
        // recursive version.
        // if (root == nullptr) {
        //     return 0;
        // }
        //
        // if (root->value < hint) {
        //     return CountGEQ(root->right, hint);
        // } else if (root->value == hint) {
        //     return root->tree_size - (root->left? root->left->tree_size : 0);
        // } else {
        //     int right_subtree_size = root->right? root->right->tree_size : 0;
        //     return right_subtree_size + 1 + CountGEQ(root->left, hint);
        // }

        // iterative version, reduce 40ms.
        int count = 0;
        while (root != nullptr) {
            if (root->value < hint) {
                root = root->right;
            } else if (root->value == hint) {
                count += root->tree_size;
                count -= root->left? root->left->tree_size : 0;
                break;
            } else {
                count += root->right? root->right->tree_size : 0;
                count += 1;
                root = root->left;
            }
        }
        return count;
    }

    int CountRange(TreeNode *root, long long lower_bound, long long upper_bound) {
        return CountGEQ(root, lower_bound) - CountGEQ(root, upper_bound);
    }

    vector<long long> BuildPrefixSum(const vector<int> &nums) {
        vector<long long> prefix_sums(nums.size() + 1, 0);
        for (int i = 1; i <= nums.size(); ++i) {
            prefix_sums[i] = prefix_sums[i - 1] + nums[i - 1];
        }
        return prefix_sums;
    }

    int countRangeSum(vector<int>& nums, int lower, int upper) {
        if (nums.empty()) {
            return 0;
        }
        const int n = nums.size();

        auto prefix_sums = BuildPrefixSum(nums);

        TreeNode *root = nullptr;
        int count = 0;
        for (int i = n; i >= 1; --i) {
            root = InsertToBST(root, prefix_sums[i]);
            count += CountRange(root,
                                prefix_sums[i - 1] + lower,
                                prefix_sums[i - 1] + upper + 1);
        }
        return count;
    }
Although this strategy works, but the runtime complexity of BST is highly dependent on the property of dataset, since the cost of searching a BST depends on the height of BST. To solve this problem, we might need to replace BST with some balanced tree, such as red-black tree, but as you know, the cost of implementing a balanced tree is pretty high, especially at the interview.
还记得 Count of Smaller Numbers After Self  么？
那时候，我们用Fenwick树或者线段树，先离散化，然后从右向左扫描，每扫描一个数，对小于它的求和。然后更新…..
这题也差不多，需要找满足条件 lower ≤ sum[j] – sum[i – 1] ≤ upper ，也就是lower + sum[i – 1] ≤ sum[j] ≤ upper + sum[i – 1]
我们同样的求出和，然后离散化，接着从右向左扫描，对每个i 查询满足在[ lower + sum[i – 1], upper + sum[i – 1] ]范围内的个数（用线段树或者Fenwick Tree）
这样复杂度就是O(n log n)

3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47


class FenwickTree(object):


    def __init__(self, n):


        self.sum_array = [0] * (n + 1)


        self.n = n




    def lowbit(self, x):


        return x & -x




    def add(self, x, val):


        while x <= self.n:


            self.sum_array[x] += val


            x += self.lowbit(x)




    def sum(self, x):


        res = 0


        while x > 0:


            res += self.sum_array[x]


            x -= self.lowbit(x)


        return res






class Solution(object):


    def countRangeSum(self, nums, lower, upper):


        """


        :type nums: List[int]


        :type lower: int


        :type upper: int


        :rtype: int


        """


        if not nums: return 0


        sum_array = [upper, lower - 1]


        total = 0


        for num in nums:


            total += num


            sum_array += [total, total + lower - 1, total + upper]




        index = {}


        for i, x in enumerate(sorted(set(sum_array))):


            index[x] = i + 1




        tree = FenwickTree(len(index))


        ans = 0


        for i in xrange(len(nums) - 1, -1, -1):


            tree.add(index[total], 1)


            total -= nums[i]


            ans += tree.sum(index[upper + total]) - tree.sum(index[lower + total - 1])


        return ans

X. TODO
Summary of the Divide and Conquer based and Binary Indexed Tree based solutions
https://discuss.leetcode.com/topic/34108/summary-of-the-divide-and-conquer-based-and-binary-indexed-tree-based-solutions
To start, we already know there is a straightforward solution by computing each range sum and checking whether it lies in [lower, upper] or not. If the number of elements is n, we have n*(n+1)/2 such range sums so the naive solution will end up with O(n^2) time complexity. Now we are asked to do better than that. So what are the targeted time complexities in your mind? When I first looked at the problem, my instinct is that O(n) solution is too ambitious, so I will target at linearithmic-like (O(n*(logn)^b)) solutions. To get the logarithmic part, it's natural to think of breaking down the original array, and that's where the divide-and-conquer idea comes from.
For this problem, we need some array to apply our divide and conquer algorithm. Without much thinking, we can do that directly with the input array (nums) itself. Since our problem also involves range sums and I believe you have the experience of computing range sums from prefix array of the input array, we might as well apply divide and conquer ideas on the prefix array. So I will give both the input-array based and prefix-array based divide&conquer solutions.
Let's first look at input-array based divide&conquer solution. Our original problem is like this: given an input array nums with length n and a range [lower, upper], find the total number of range sums that lie in the given range. Note the range [lower, upper] and the input array are both fixed. Therefore each range sum can be characterized by two indices i1 and i2 (i1 <= i2), such that range sum S(i1, i2) is the summation of input elements with indices going from i1 up to i2 (both inclusive). Then our problem can be redefined in terms of the value ranges of i1 and i2. For example our original problem can be restated as finding the total number of range sums lying in the given range with 0 <= i1 <= i2 <= n - 1, or in a symbolic way T(0, n-1).
Now if we break our original input array into two subarrays, [0, m] and [m+1, n-1] with m = (n-1)/2, our original problem can be divided into three parts, depending on the values of i1 and i2. If i1 and i2 are both from the first subarray [0, m], we have a subproblem T(0, m); if i1 and i2 are both from the second subarray, we have a subproblem T(m+1, n-1); if i1 is from the first subarray and i2 from the second (note we assume i1 <= i2, therefore we don't have the other case with i2 from first subarray and i1 from second), then we have a new problem which I define as C. In summary we should have:
T(0, n-1) = T(0, m) + T(m+1, n-1) + C
Now from the master theorem, the time complexity of the new problem C should be better than O(n^2), otherwise we make no improvement by applying this divide&conquer idea. So again, I will aim at linearithmic-like solutions for the new problem C: find the total number of range sums lying in the given range with each range sum starting from the first subarray and ending at the second subarray.
First let's try to compute all such range sums. The way I did it was first computing the prefix array of the second subarray and the suffix array (or "backward" prefix array if you like) of the first subarray. Then I can naively add each element in the suffix array to all elements in the prefix array to obtain all the possible range sums. Of course you end up with O(n^2) solution, as expected. So how can we approach it with better time complexity?
Here are the facts I observed: for each element e in the suffix array, we need to add it to all elements in the prefix array. But the order in which we add it doesn't matter. This implies that we can sort our prefix array. This can be done in O(nlogn) time. Now we have a sorted prefix array, do we still need to add the element e to all elements in the prefix array? The answer is no. Because our final goal is to compare the resulted range sums with the given range bounds lower and upper. It is equivalent to modifying the range bounds so we have new bounds (lower - e) and (upper - e) and leave the prefix array unchanged. Now we can compare these new bounds with the sorted prefix array, and I'm sure you can write your own binary search algorithm to do that. So for each element e in the suffix array, we can compute the modified range bounds and get the number of range sums in this new range in logn time. Therefore the total time will be O(nlogn). So in summary, our new problem C can be solved in O(nlogn) time and according to the master theorem, our original problem can be solved in O(n(logn)^2) time. The following is the complete java program:
public int countRangeSum(int[] nums, int lower, int upper) {
    if (nums == null || nums.length == 0 || lower > upper) return 0;
    return countRangeSumSub(nums, 0, nums.length - 1, lower, upper);
}

private int countRangeSumSub(int[] nums, int l, int r, int lower, int upper) {
    if (l == r) return nums[l] >= lower && nums[r] <= upper ? 1 : 0;  // base case
 
    int m = l + (r - l) / 2;
    long[] arr = new long[r - m];  // prefix array for the second subarray
    long sum = 0;
    int count = 0;
 
    for (int i = m + 1; i <= r; i++) {
 sum += nums[i];
 arr[i - (m + 1)] = sum; // compute the prefix array
    }
 
    Arrays.sort(arr);  // sort the prefix array
 
    // Here we can compute the suffix array element by element.
    // For each element in the suffix array, we compute the corresponding
    // "insertion" indices of the modified bounds in the sorted prefix array
    // then the number of valid ranges sums will be given by the indices difference.
    // I modified the bounds to be "double" to avoid duplicate elements.
    sum = 0;
    for (int i = m; i >= l; i--) {
 sum += nums[i];  
 count += findIndex(arr, upper - sum + 0.5) - findIndex(arr, lower - sum - 0.5);
     }
 
    return countRangeSumSub(nums, l, m, lower, upper) + countRangeSumSub(nums, m + 1, r, lower, upper) + count;
}

// binary search function
private int findIndex(long[] arr, double val) {
    int l = 0, r = arr.length - 1, m = 0;
 
    while (l <= r) {
 m = l + (r - l) / 2;
  
 if (arr[m] <= val) {
     l = m + 1;
 } else {
     r = m - 1;
        }
    }
 
    return l;
}
We have the prefix array(which I will call prefix) of the input array (nums). The prefix array will have n + 1 elements if nums has n elements. Likewise each range sum can be characterized by two indices i1 and i2 with i1 < i2 (Note that i1 cannot equal i2 now). Again our original problem can be recast in terms of the value ranges of i1 and i2: given the prefix array and a range [lower, upper], find the total number of range sums lying in that range with 0 <= i1 < i2 <= n, or in a symbolic way T(0, n).
Again we will break the prefix array into two subarrays, [0, m] and [m+1, n] with m = n/2, and in a similar fashion we will have subproblem T(0,m) if i1 and i2 are both from [0,m]; subproblem T(m+1, n) if both i1 and i2 are from [m+1, n] and a new problem C if i1 from [0, m] while i2 from [m+1, n]:
T(0, n) = T(0, m) + T(m+1, n) + C
Now we will aim at linearithmic-like solution for the new problem C: find the total number of range sums lying in the given range with each range sum starting from the first subarray and ending at the second subarray. As you expect, naive solution will run in O(n^2) but we have similar observations here: each range sum can be computed by prefix[i2] - prefix[i1] with i2 from [m+1, n] and i1 from [0, m]. For each index i1, the order doesn't matter in which we compute the range sums for i2 running from m+1 to n. Similar for the other way, for each index i2, the order doesn't matter in which we compute the ranges for i1 running from 0 to m. This allows us to sort the two subarrays. Now suppose we have two sorted subarrays, how do we get the total number of valid range sums? @dietpepsi proposed this two-pointer-like method embedded in merge sort, which is very neat and runs in linear time. You can refer to the original post for more details.
Before I post the code for this prefix-array based solution, I'd like to mention one point regarding merge sort. Conventionally when we merge two sorted arrays, we use the following routines:
    while (i < array1.length && j < array2.length) {
     array[k++] = (array1[i] <= array2[j] ? array1[i++] : array2[j++]);
    }

    while (i < array1.length) {
     array[k++] = array1[i++];
    }

    while (j < array2.length) {
     array[k++] = array2[j++];
    }
which is nice for merging purpose. However we also have this modified version of merging:
   while (i < array1.length) {
     while (j < array2.length && array2[j] < array1[i]) {
        array[k++] = array2[j++];
    }
     
     array[k++] = array1[i++];
    }
    
    while (j < array2.length) {
     array[k++] = array2[j++];
    }
which is particularly useful when we'd like to extract information from the two sorted subarrays, such as summation/difference of pairs with elements from each subarray, or reversion counts during the merging process.
Anyway, the following code for prefix-array based solution used this version to compute the range counts:
public int countRangeSumI(int[] nums, int lower, int upper) {
    if (nums == null || nums.length == 0 || lower > upper) return 0;

    long[] prefixArray = new long[nums.length + 1];
 
    for (int i = 1; i < prefixArray.length; i++) {
 prefixArray[i] = prefixArray[i - 1] + nums[i - 1];
    }
 
    return countRangeSumSub(prefixArray, 0, prefixArray.length - 1, lower, upper);
}

private int countRangeSumSub(long[] prefixArray, int l, int r, int lower, int upper) {
    if (l >= r) return 0;
 
    int m = l + (r - l) / 2;
 
    int count = countRangeSumSub(prefixArray, l, m, lower, upper) + countRangeSumSub(prefixArray, m + 1, r, lower, upper);
 
    long[] mergedArray = new long[r - l + 1];
    int i = l, j = m + 1, k = m + 1, p = 0, q = m + 1;
 
    while (i <= m) {
 while (j <= r && prefixArray[j] - prefixArray[i] < lower) j++;
 while (k <= r && prefixArray[k] - prefixArray[i] <= upper) k++;
 count += k - j;

 while (q <= r && prefixArray[q] < prefixArray[i]) mergedArray[p++] = prefixArray[q++];
 mergedArray[p++] = prefixArray[i++];
    }
 
    while (q <= r) mergedArray[p++] = prefixArray[q++];
 
    System.arraycopy(mergedArray, 0, prefixArray, l, mergedArray.length);
 
    return count;
}
Again we will compute the prefix array (which I will call prefix) of the input array (nums). And each range sum will be characterized by two indices i1 and i2 with 0 <= i1 < i2 <= n. Consider only index i1 now. If for each index i1 running from 0 up to n - 1, we can find all the valid range sums starting at i1 in O(logn) time, then the total runtime will be O(nlogn) since we only need to do that n times. Now how can we obtain all the valid range sums starting at each index i1 with time complexity O(logn)? @lixx2100 mentioned that we can take advantage of data structure such as binary indexed tree but offered no further details except for the code. So I will fill in the gaps here. First here is the code (I used an array called "cand" instead of list):
public int countRangeSum(int[] nums, int lower, int upper) {
    long[] sum = new long[nums.length + 1];
    long[] cand = new long[3 * sum.length + 1];
    int index = 0;
    cand[index++] = sum[0];
    cand[index++] = lower + sum[0] - 1;
    cand[index++] = upper + sum[0];

    for (int i = 1; i < sum.length; i++) {
        sum[i] = sum[i - 1] + nums[i - 1];
        cand[index++] = sum[i];
        cand[index++] = lower + sum[i] - 1;
        cand[index++] = upper + sum[i];
    }

    cand[index] = Long.MIN_VALUE; // avoid getting root of the binary indexed tree when doing binary search
    Arrays.sort(cand);

    int[] bit = new int[cand.length];

    // build up the binary indexed tree with only elements from the prefix array "sum"
    for (int i = 0; i < sum.length; i++) {
        addValue(bit, Arrays.binarySearch(cand, sum[i]), 1);
    }

    int count = 0;

    for (int i = 1; i < sum.length; i++) {
        // get rid of visited elements by adding -1 to the corresponding tree nodes
        addValue(bit, Arrays.binarySearch(cand, sum[i - 1]), -1);

        // add the total number of valid elements with upper bound (upper + sum[i - 1])
        count += query(bit, Arrays.binarySearch(cand, upper + sum[i - 1]));

        // minus the total number of valid elements with lower bound (lower + sum[i - 1] - 1)
        count -= query(bit, Arrays.binarySearch(cand, lower + sum[i - 1] - 1));
    }

    return count;
}

private void addValue(int[] bit, int index, int value) {
    while (index < bit.length) {
        bit[index] += value;
        index += index & -index;
    }
}

private int query(int[] bit, int index) {
    int sum = 0;
    
    while (index > 0) {
        sum += bit[index];
        index -= index & -index;
    }
    
    return sum;
}
The "sum" array is the prefix array. What is the purpose of the array "cand"?
Remember that for each i1 in the prefix array, we are looking for all the i2's that satisfy:
lower <= sum[i2] - sum[i1] <= upper (with i2 > i1)
or equivalently:
lower + sum[i1] - 1 < sum[i2] <= upper + sum[i1] (with i2 > i1)
Apparently we'd like to sort the prefix array so we can do binary search. As usual we can get two "insertion points", one for (upper + sum[i1]) and one for (lower + sum[i1] - 1) and the number of valid prefix elements will simply be the indices difference of the two "insertion points", provided there is no restriction on i1 and i2. Unfortunately we do require i1 < i2 to generate a candidate range sum, but sorting the prefix array would break the original index orders and we no longer have guarantee that i1 < i2. Therefore we have to get rid of all those prefix elements between the two "insertion points" with indices up to i1. So it looks like we are in a situation where we want relatively fast operations for both query and updating. Sounds familiar to you? That's where the binary indexed tree comes in, which has O(logn) runtime for both operations.
To build up the binary indexed tree, we have two strategies: either build the tree against the sorted prefix array or against a new array including the modified lower and upper bounds, (upper + sum[i1]) and (lower + sum[i1] - 1). The code given above takes the second strategy. The upside is that we can take advantage of the built-in binary search functions (java's Arrays.binarySearch() for example); the downside is we need more space.
Here I'll show how to build up the binary indexed tree corresponding to the new array "cand". I assume you have basic knowledge of binary indexed tree (if not, refer to Fenwick tree). So we have this "bit" array to represent nodes in the binary indexed tree. For each element with index i in the input array ("cand" in this case), we will store a mapped value of the input element at the corresponding node and all its child nodes in the tree.
Two points here:
The values to store in the tree are not necessarily the elements from the input array itself. You can choose any mapping function you like. (For example, you can choose to return the input elements themselves, or return either 0 or 1 depending on some condition to count the number of elements satisfying that particular condition). The point is that, starting from some given index in the "bit" array, when you traverse toward the root of the tree, the cumulated quantity will correspond to the mapped value. If the mapping function returns the input elements themselves, this cumulated quantity will be the prefix sum of the input array. If the mapping function returns 1 for elements in "cand" that comes from sum[i] and 0 for those from (upper + sum[i1]) or (lower + sum[i1] - 1), then the cumulated quantity will give the total number of elements only from the "sum" array.
For a given index i in the binary index tree, the index of its parent can be obtained by p = i - (i&-i) and its child index can be obtained by c = i + (i&-i). The root of the tree has index 0 and the child index will not go beyond the length of the bit array.
Eventually we'd like to count the number of valid elements from the prefix array "sum", so our mapping function will assign 1 to elements in "cand" that comes from the "sum" array, and 0 to all other elements. If the assigned value is 0, we don't even bother adding it to the tree nodes. Only when the assigned value is 1 will we add it to the corresponding tree nodes. That's what you see in the code above.
After the tree is initialized, we'd like to scan the index i1 from 1 up to n. For each i1, we will first get rid of all elements from "sum" with index from 0 up to i1 - 1 (essentially by adding -1 to corresponding nodes in the tree). Then for the upper bound (upper + sum[i1]), we will compute the corresponding index in the "cand" array using binary search and calculate the total number of valid elements up to that index in the binary indexed tree. We then do the same for the lower bound (lower + sum[i1] - 1). The total number of valid elements from the "sum" array will be given by the difference of these two counts. And that's why you see we add the first count then minus the second count in the code. We proceed in this manner until we reach the end of the "sum" array.
As I mentioned, we can also build the binary indexed tree directly using the prefix array but then you need to come up with your own binary search algorithm instead of the built-in one. Also we do not need to keep a copy of the original prefix array after it is sorted to further save memory space, since the prefix sum array can be computed on the fly as we are scanning the index i1 from 1 to n. If you are interested you can work out those codes very easily.
http://bookshadow.com/weblog/2016/01/11/leetcode-count-of-range-sum/
解法I 树状数组（Fenwick Tree）：
1. 预处理前n项和数组sums

2. 将sums数组离散化（排序+去重）得到数组osums

3. 遍历sums，记sumi = sums[i]
   用二分查找得到[sumi - upper, sumi - lower]的离散化下标[left, right]
   用树状数组统计范围[left, right]内的元素个数，并累加至最终结果ans
   若lower <= sumi <= upper，额外地令ans+1
   将sumi的离散化下标记入树状数组
上述算法将题目转化为下面的问题：
对于数组sums中的每一个元素sumi，统计出现在sumi左侧，并且数值在[sumi - upper, sumi - lower]范围内的元素个数。
这就等价于统计区间和[0, i]，[1, i]... [i - 1, i]当中所有落在范围[lower, upper]之内的区间个数。
    def countRangeSum(self, nums, lower, upper):
        """
        :type nums: List[int]
        :type lower: int
        :type upper: int
        :rtype: int
        """
        size = len(nums)
        sums = [0] * (size + 1)
        for x in range(1, size + 1):
            sums[x] += nums[x - 1] + sums[x - 1]
        INF = max(sums)

        def mergeSort(lo, hi):
            if lo == hi: return 0
            mi = (lo + hi) / 2
            cnt = mergeSort(lo, mi) + mergeSort(mi + 1, hi)
            x = y = lo
            for i in range(mi + 1, hi + 1): 
                while x <= mi and sums[i] - sums[x] >= lower:
                    x += 1
                while y <= mi and sums[i] - sums[y] > upper:
                    y += 1
                cnt += x - y
            part = sums[lo : hi + 1]

            l, h = lo, mi + 1
            for i in range(lo, hi + 1):
                x = part[l - lo] if l <= mi else INF
                y = part[h - lo] if h <= hi else INF
                if x < y: l += 1
                else: h += 1
                sums[i] = min(x, y)
            return cnt

        return mergeSort(0, size)

1


X. Segement tree
https://leetcode.com/discuss/79073/java-segmenttree-solution-36ms
https://www.hrwhisper.me/leetcode-count-of-range-sum/
那时候，我们用Fenwick树或者线段树，先离散化，然后从右向左扫描，每扫描一个数，对小于它的求和。然后更新…..
这题也差不多，需要找满足条件 lower ≤ sum[j] – sum[i – 1] ≤ upper ，也就是lower + sum[i – 1] ≤ sum[j] ≤ upper + sum[i – 1]
我们同样的求出和，然后离散化，接着从右向左扫描，对每个i 查询满足在[ lower + sum[i – 1], upper + sum[i – 1] ]范围内的个数（用线段树或者Fenwick Tree）

struct SegmentTreeNode {

 LL L, R;

 int cnt;

 SegmentTreeNode *left, *right;

 SegmentTreeNode(LL L, LL R) :L(L), R(R), cnt(0), left(NULL), right(NULL) {}

};


class SegmentTree {

 SegmentTreeNode * root;

 SegmentTreeNode * buildTree(vector<LL> &nums, int L, int R) {

  if (L > R) return NULL;

  SegmentTreeNode * root = new SegmentTreeNode(nums[L], nums[R]);

  if (L == R) return root;

  int mid = (L + R) >> 1;

  root->left = buildTree(nums, L, mid);

  root->right = buildTree(nums, mid + 1, R);

  return root;

 }


 void update(SegmentTreeNode * root, LL val) {

  if (root && root->L <= val &&  val <= root->R) {

   root->cnt++;

   update(root->left, val);

   update(root->right, val);

  }

 }


 int sum(SegmentTreeNode * root, LL L, LL R) {

  if (!root || root->R < L ||  R < root->L ) return 0;

  if (L <= root->L  && root->R <= R) return root->cnt;

  return sum(root->left, L, R) + sum(root->right, L, R);

 }


 SegmentTree(vector<LL> &nums, int L, int R) { root = buildTree(nums, L, R); }


 int sum(LL L, LL R) {

  return sum(root, L, R);

 }


 void update(LL val) {

  update(root, val);

 }

};

 int countRangeSum(vector<int>& nums, int lower, int upper) {

  if (nums.size() == 0) return 0;

  vector<LL> sum_array (nums.size(),0);

  sum_array[0] = nums[0];

  for (int i = 1; i < sum_array.size(); i++) {

   sum_array[i] = nums[i] + sum_array[i - 1];

  }

  LL sum = sum_array[sum_array.size() - 1];

  sort(sum_array.begin(), sum_array.end());

  auto t = unique(sum_array.begin(), sum_array.end());

  SegmentTree tree(sum_array, 0, t - sum_array.begin() - 1);

  int ans = 0;

  for (int i = nums.size() - 1; i >= 0; i--) {

   tree.update(sum);

   sum -= nums[i];

   ans += tree.sum(lower + sum,upper + sum);

  }

  return ans;

 }

}

https://leetcode.com/discuss/80232/java-red-black-tree-72-ms-solution
The only trick is that we need to keep track of node counts in subtrees so that we can quickly count the number of elements less than or equal to something without traversing around (likeTreeMap.subMap().size() does). It is also important to update those node counts when performing rotations.
public int countRangeSum(int[] nums, int lower, int upper) {
    long sum = 0;
    RedBlackTree sumTree = new RedBlackTree();
    sumTree.add(sum); // zero-length prefix
    int count = 0;
    for (int i = 0; i < nums.length; ++i) {
        sum += nums[i];
        // we need to count lower <= sums[i] - sums[j] <= upper, j < i, or
        // -lower >= sums[j] - sums[i] >= -upper, or sums[i] - lower >= sums[j] >= sums[i] - upper, or
        // sums[i] - lower >= sums[j] > sums[i] - upper - 1
        count += countLE(sumTree.root, sum - lower) - countLE(sumTree.root, sum - upper - 1);
        sumTree.add(sum);
    }
    return count;
}

private static int countLE(RedBlackTree.Node root, long sum) {
    RedBlackTree.Node current = root;
    int count = current.totalCount;
    while (current != RedBlackTree.Node.NIL) {
        if (current.value == sum) {
            count -= current.right.totalCount;
            break;
        } else if (sum < current.value) {
            count -= current.valueCount + current.right.totalCount;
            current = current.left;
        } else { // we haven't seen anything greater than sum yet
            current = current.right;
        }
    }
    return count;
}

static class RedBlackTree {

    Node root = Node.NIL;

    void add(long value) {
        Node current = root, prev = Node.NIL;
        while (current != Node.NIL && current.value != value) {
            ++current.totalCount;
            prev = current;
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current != Node.NIL) { // Note: can't test for current.value == value here because value can be 0.
            // exact match
            ++current.totalCount;
            ++current.valueCount;
            return;
        }
        Node node = new Node(value);
        if (prev == Node.NIL) {
            root = node;
        } else {
            if (value < prev.value) {
                assert prev.left == Node.NIL;
                prev.left = node;
            } else {
                assert prev.right == Node.NIL && value > prev.value;
                prev.right = node;
            }
            node.parent = prev;
        }
        // fix up the Red-Blackness (CLR, Introduction to Algorithms)
        while (node.parent.color == Node.Color.RED) {
            Node parent = node.parent;
            Node granddad = parent.parent;
            assert granddad.color == Node.Color.BLACK;
            boolean left = granddad.left == parent;
            Node uncle = left ? granddad.right : granddad.left;
            if (uncle.color == Node.Color.RED) { // case 1
                granddad.color = Node.Color.RED;
                parent.color = uncle.color = Node.Color.BLACK;
                node = granddad;
            } else {
                if ((left ? parent.right : parent.left) == node) { // case 2
                    node = parent;
                    rotate(node, left);
                }
                // case 3
                parent.color = Node.Color.BLACK;
                granddad.color = Node.Color.RED;
                rotate(granddad, !left);
            }
        }
        root.color = Node.Color.BLACK;
    }

    void rotate(Node node, boolean left) {
        Node parent = node.parent;
        Node child = left ? node.right : node.left;
        if (left) { node.right = child.left; } else { node.left = child.right; }
        node.totalCount = node.left.totalCount + node.valueCount + node.right.totalCount;
        (left ? child.left : child.right).parent = node;
        child.parent = parent;
        if (parent == Node.NIL) {
            root = child;
        } else {
            if (parent.left == node) {
                parent.left = child;
            } else {
                assert parent.right == node;
                parent.right = child;
            }
        }
        if (left) { child.left = node; } else { child.right = node; }
        child.totalCount = child.left.totalCount + child.valueCount + child.right.totalCount;
        node.parent = child;
        Node.NIL.left = Node.NIL.right = Node.NIL.parent = Node.NIL; // fix it up in case we've messed it up
    }

    static class Node {
        static final Node NIL = new Node();

        static { // need this because we can't initialize fields to NIL until it is created
            NIL.left = NIL.right = NIL.parent = NIL;
        }

        long value;
        int valueCount, totalCount;
        Node parent = NIL, left = NIL, right = NIL;
        Color color;

        private Node() { // NIL constructor
            this.color = Color.BLACK;
        }

        Node(long value) {
            this.value = value;
            this.valueCount = this.totalCount = 1;
            this.color = Color.BLACK;
        }

        enum Color {
            RED, BLACK
        }
    }
}
Read full article from leetcode Count of Range Sum - 细语呢喃
 * @author het
 *
 */
public class LeetCode327 {
	public static int countRangeSum(int[] nums, int lower, int upper) {


	    int n = nums.length;


	    long[] sums = new long[n + 1];


	    for (int i = 0; i < n; ++i)


	        sums[i + 1] = sums[i] + nums[i];


	    return countWhileMergeSort(sums, 0, n + 1, lower, upper);


	}


	private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {


	    if (end - start <= 1) return 0;


	    int mid = (start + end) / 2;


	    int count = countWhileMergeSort(sums, start, mid, lower, upper) 


	              + countWhileMergeSort(sums, mid, end, lower, upper);


	    int j = mid, k = mid, t = mid;


	    long[] cache = new long[end - start];


	    for (int i = start, r = 0; i < mid; ++i, ++r) {


	        while (k < end && sums[k] - sums[i] < lower) k++;


	        while (j < end && sums[j] - sums[i] <= upper) j++;


	        while (t < end && sums[t] < sums[i]) cache[r++] = sums[t++];


	        cache[r] = sums[i];


	        count += j - k;


	    }


	    System.arraycopy(cache, 0, sums, start, t - start);


	    return count;


	}
	
	
//	X. Binary Search Tree
//	http://www.cnblogs.com/EdwardLiu/p/5138198.html
//	这个做法是建立BST，把prefix sum作为TreeNode.val存进去，为了避免重复的TreeNode.val处理麻烦，设置一个count记录多少个重复
//	TreeNode.val, 维护leftSize, 记录比该节点value小的节点个数，rightSize同理
//
//	由于RangeSum S(i,j)在[lower,upper]之间的条件是lower<=sums[j+1]-sums[i]<=upper,
//	所以我们每次insert一个新的PrefixSum sums[k]进这个BST之前，先寻找一下（rangeSize）该BST内已经有多少个PrefixSum（叫它sums[t]吧）
//	满足lower<=sums[k]-sums[t]<=upper, 即寻找有多少个sums[t]满足： 
//
//	sums[k]-upper<=sums[t]<=sums[k]-lower
//
//	BST提供了countSmaller和countLarger的功能，计算比sums[k]-upper小的RangeSum数目和比sums[k]-lower大的数目，再从总数里面减去，就是所求
//
//	 2     private class TreeNode {
//	 3         long val = 0;
//	 4         int count = 1;
//	 5         int leftSize = 0;
//	 6         int rightSize = 0;
//	 7         TreeNode left = null;
//	 8         TreeNode right = null;
//	 9         public TreeNode(long v) {
//	10             this.val = v;
//	11             this.count = 1;
//	12             this.leftSize = 0;
//	13             this.rightSize = 0;
//	14         }
//	15     }
//	16 
//	17     private TreeNode insert(TreeNode root, long val) {
//	18         if(root == null) {
//	19             return new TreeNode(val);
//	20         } else if(root.val == val) {
//	21             root.count++;
//	22         } else if(val < root.val) {
//	23             root.leftSize++;
//	24             root.left = insert(root.left, val);
//	25         } else if(val > root.val) {
//	26             root.rightSize++;
//	27             root.right = insert(root.right, val);
//	28         }
//	29         return root;
//	30     }
//	31 
//	32     private int countSmaller(TreeNode root, long val) {
//	33         if(root == null) {
//	34             return 0;
//	35         } else if(root.val == val) {
//	36             return root.leftSize;
//	37         } else if(root.val > val) {
//	38             return countSmaller(root.left, val);
//	39         } else {
//	40             return root.leftSize + root.count + countSmaller(root.right, val);
//	41         }
//	42     }
//	43 
//	44     private int countLarger(TreeNode root, long val) {
//	45         if(root == null) {
//	46             return 0;
//	47         } else if(root.val == val) {
//	48             return root.rightSize;
//	49         } else if(root.val < val) {
//	50             return countLarger(root.right, val);
//	51         } else {
//	52             return countLarger(root.left, val) + root.count + root.rightSize;
//	53         }
//	54     }
//	55 
//	56     private int rangeSize(TreeNode root, long lower, long upper) {
//	57         int total = root.count + root.leftSize + root.rightSize;
//	58         int smaller = countSmaller(root, lower);    // Exclude everything smaller than lower
//	59         int larger = countLarger(root, upper);      // Exclude everything larger than upper
//	60         return total - smaller - larger;
//	61     }
//	62 
//	63     public int countRangeSum(int[] nums, int lower, int upper) {
//	64         if(nums.length == 0) {
//	65             return 0;
//	66         }
//	67         long[] sums = new long[nums.length + 1];
//	68         for(int i = 0; i < nums.length; i++) {
//	69             sums[i + 1] = sums[i] + nums[i];
//	70         }
//	71         TreeNode root = new TreeNode(sums[0]);
//	72         int output = 0;
//	73         for(int i = 1; i < sums.length; i++) {
//	74             output += rangeSize(root, sums[i] - upper, sums[i] - lower);
//	75             insert(root, sums[i]);
//	76         }
//	77         return output;
//	78     }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//、、 [-2, 5, -1], lower = -2, upper = 2,
		System.out.println(countRangeSum(new int[]{-2, 5, -1}, -2, 2));

	}

}
