package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * LeetCode 315 - Count of Smaller Numbers After Self

https://leetcode.com/discuss/73233/complicated-segmentree-solution-hope-to-find-a-better-one
You are given an integer array nums and you have to return a new counts array. 
The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
Example:
Given nums = [5, 2, 6, 1]

To the right of 5 there are 2 smaller elements (2 and 1).
To the right of 2 there is only 1 smaller element (1).
To the right of 6 there is 1 smaller element (1).
To the right of 1 there is 0 smaller element.
Return the array [2, 1, 1, 0].
http://bookshadow.com/weblog/2015/12/06/leetcode-count-of-smaller-numbers-after-self/
X.  二叉搜索树 （Binary Search Tree)
树节点TreeNode记录下列信息：
元素值：val
小于该节点的元素个数：leftCnt
节点自身的元素个数：cnt
左孩子：left
右孩子：right
从右向左遍历nums，在向BST插入节点时顺便做统计
https://kennyzhuang.gitbooks.io/algorithms-collection/content/count_of_smaller_numbers_after_self.html

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums == null || nums.length == 0) return res;
        TreeNode root = new TreeNode(nums[nums.length - 1]);
        res.add(0);
        for(int i = nums.length - 2; i >= 0; i--) {
            int count = insertNode(root, nums[i]);
            res.add(count);
        }
        Collections.reverse(res);
        return res;
    }

    public int insertNode(TreeNode root, int val) {
        int thisCount = 0;
        while(true) {
            if(val <= root.val) {
                root.count++;
                if(root.left == null) {
                    root.left = new TreeNode(val); break;
                } else {
                    root = root.left;
                }
            } else {
                thisCount += root.count;
                if(root.right == null) {
                    root.right = new TreeNode(val); break;
                } else {
                    root = root.right;
                }
            }
        }
        return thisCount;
    }
https://leetcode.com/discuss/73762/9ms-short-java-bst-solution-get-answer-when-building-bst
Every node will maintain a val sum recording the total of number on it's left bottom side, dupcounts the duplication. For example, [3, 2, 2, 6, 1], from back to beginning,we would have:
                1(0, 1)
                     \
                     6(3, 1)
                     /
                   2(0, 2)
                       \
                        3(0, 1)
When we try to insert a number, the total number of smaller number would be adding dup and sum of the nodes where we turn right. for example, if we insert 5, it should be inserted on the way down to the right of 3, the nodes where we turn right is 1(0,1), 2,(0,2), 3(0,1), so the answer should be (0 + 1)+(0 + 2)+ (0 + 1) = 4
if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + (3 + 1) = 5
runtime could be O(n^2) when processing input like n, n-1, ..., 2, 1. I think the test cases are not good enough.
There is no need for considering the duplication elements. See the code below which is referred from: http://www.cnblogs.com/grandyang/p/5078490.html
class TreeNode{
        int smallCount;
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int count, int val){
            this.smallCount = count;
            this.val = val;
        }
    }
    
    public List<Integer> countSmaller(int[] nums) {
        TreeNode root = null;
        Integer[] ret = new Integer[nums.length];//\\
        if(nums == null || nums.length == 0) return Arrays.asList(ret);
        for(int i=nums.length-1; i>=0; i--){
            root = insert(root, nums[i], ret, i, 0);
        }
        return Arrays.asList(ret);//\\
    }
    
    public TreeNode insert(TreeNode root, int val, Integer[] ans, int index, int preSum){
        if(root == null){
            root = new TreeNode(0, val);
            ans[index] = preSum;
        }
        else if(root.val>val){
            root.smallCount++;
            root.left = insert(root.left, val, ans, index, preSum);
        }
        else{
            root.right = insert(root.right, val, ans, index, root.smallCount + preSum + (root.val<val?1:0));//only adding 1 on preSum if root.val is only smaller than val
        }
        return root;
    }

class Node{
    int val, leftSum = 0, count = 0;
    Node left, right;
    public Node(int val){
        this.val = val;
    }
}
public List<Integer> countSmaller(int[] nums) {
    Integer[] count = new Integer[nums.length];
    if(nums.length == 0){
        return Arrays.asList(count);
    }
    Node root = new Node(nums[nums.length - 1]);
    for(int i = nums.length - 1; i >= 0; i--){
        count[i] = insert(root, nums[i]);
    }
    return Arrays.asList(count);
}
private int insert(Node node, int num){
    int sum = 0;
    while(node.val != num){
        if(node.val > num){
            if(node.left == null) node.left = new Node(num);
            node.leftSum++;
            node = node.left;
        }else{
            sum += node.leftSum + node.count;
            if(node.right == null) node.right = new Node(num);
            node = node.right;
        }
    }
    node.count++;
    return sum + node.leftSum;
}
https://discuss.leetcode.com/topic/31422/easiest-java-solution
Traverse from nums[len - 1] to nums[0], and build a binary search tree, which stores:
val: value of nums[i]
count: if val == root.val, there will be count number of smaller numbers on the right
 public List<Integer> countSmaller(int[] nums) {
  List<Integer> res = new ArrayList<>();
  if(nums == null || nums.length == 0) return res;
  TreeNode root = new TreeNode(nums[nums.length - 1]);
  res.add(0);
  for(int i = nums.length - 2; i >= 0; i--) {
   int count = insertNode(root, nums[i]);
   res.add(count);
  }
  Collections.reverse(res);
  return res;
 }

 public int insertNode(TreeNode root, int val) {
  int thisCount = 0;
  while(true) {
   if(val <= root.val) {
    root.count++;
    if(root.left == null) {
     root.left = new TreeNode(val); break;
    } else {
     root = root.left;
    }
   } else {
    thisCount += root.count;
    if(root.right == null) {
     root.right = new TreeNode(val); break;
    } else {
     root = root.right;
    }
   }
  }
  return thisCount;
 }
}

class TreeNode {
 TreeNode left; 
 TreeNode right;
 int val;
 int count = 1;
 public TreeNode(int val) {
  this.val = val;
 }
}
https://discuss.leetcode.com/topic/55730/my-bst-solution-with-detailed-explanation
Basically, the idea is very easy. We traverse the array backwards meanwhile build a BST (AVL, RB-tree... whatever). The key is how to get how many nodes in tree smaller than current node. The code as below says everything.
https://discuss.leetcode.com/topic/31422/easiest-java-solution/11
Here is the solution but getting TLE . You know why, thats because headset.size() is O(N) surprisngly. SO we cant use this Treeset/Treemap
public List<Integer> countSmaller(int[] nums) {
     if(nums==null || nums.length==0)
      return new ArrayList<Integer>();
     List<Integer> l =new ArrayList<Integer>();
     
     TreeSet<Integer> s=new TreeSet<>();
     s.add(nums[nums.length-1]);
     l.add(0);
     for( int i=nums.length-2;i>=0;i--){
      SortedSet<Integer> s1=s.headSet(nums[i]);
      l.add(s1.size());
      s.add(nums[i]);
      
     }
     Collections.reverse(l);
  return l;
}
    public List<Integer> countSmaller(int[] nums) {
        Integer[] counter = new Integer[nums.length];
        TreeSet<Integer> tree = new TreeSet<>(
            (a, b) -> (Integer.compare(a, b) == 0) ? -1 : Integer.compare(a, b));
        for (int i = nums.length - 1; i >= 0; i--) {
            tree.add(nums[i]);
            counter[i] = tree.headSet(nums[i]).size(); // Here is the key!!!
        }
        return Arrays.asList(counter);
    }
    class Node {
        Node left, right;
        int val, sum, dup = 1;
        public Node(int v, int s) {
            val = v;
            sum = s;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        Integer[] ans = new Integer[nums.length];
        Node root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert(nums[i], root, ans, i, 0);
        }
        return Arrays.asList(ans);
    }
    private Node insert(int num, Node node, Integer[] ans, int i, int preSum) {
        if (node == null) {
            node = new Node(num, 0);
            ans[i] = preSum;
        } else if (node.val == num) {
            node.dup++;
            ans[i] = preSum + node.sum;
        } else if (node.val > num) {
            node.sum++;
            node.left = insert(num, node.left, ans, i, preSum);
        } else {
            node.right = insert(num, node.right, ans, i, preSum + node.dup + node.sum);
        }
        return node;
    }
http://traceformula.blogspot.com/2015/12/count-of-smaller-numbers-after-self.html
https://leetcode.com/discuss/73803/easiest-java-solution
Traverse from nums[len - 1] to nums[0], and build a binary search tree, which stores:
val: value of nums[i]
count: if val == root.val, there will be count number of smaller numbers on the right
But can we really use balanced tree instead of this one? I mean we need keep the structure of the tree in the inserted order. If we convert it into balance tree, we could get wrong answer.
We don't need to keep the inserted order. Just build the tree from back of the array and sum up all passing node's count.
Also https://leetcode.com/discuss/73280/my-simple-ac-java-binary-search-code
public List<Integer> countSmaller(int[] nums) {
    List<Integer> res = new ArrayList<>();
    if(nums == null || nums.length == 0) return res;
    TreeNode root = new TreeNode(nums[nums.length - 1]);
    res.add(0);
    for(int i = nums.length - 2; i >= 0; i--) {
        int count = insertNode(root, nums[i]);
        res.add(count);
    }
    Collections.reverse(res);
    return res;
}
public int insertNode(TreeNode root, int val) {
    int thisCount = 0;
    while(true) {
        if(val <= root.val) {
            root.count++;
            if(root.left == null) {
                root.left = new TreeNode(val); break;
            } else {
                root = root.left;
            }
        } else {
            thisCount += root.count;
            if(root.right == null) {
                root.right = new TreeNode(val); break;
            } else {
                root = root.right;
            }
        }
    }
    return thisCount;
}
class TreeNode {
TreeNode left; 
TreeNode right;
int val;
int count = 1;
public TreeNode(int val) {
    this.val = val;
}
}


Here is the solution but getting TLE . You know why, thats because headset.size() is O(N) surprisngly. SO we cant use this Treeset/Treemap
public List<Integer> countSmaller(int[] nums) {
        if(nums==null || nums.length==0)
            return new ArrayList<Integer>();
        List<Integer> l =new ArrayList<Integer>();

        TreeSet<Integer> s=new TreeSet<>();
        s.add(nums[nums.length-1]);
        l.add(0);
        for( int i=nums.length-2;i>=0;i--){
            SortedSet<Integer> s1=s.headSet(nums[i]);
            l.add(s1.size());
            s.add(nums[i]);

        }
        Collections.reverse(l);
        return l;
}


    class Node {  
        Node left, right;  
        int val, sum, dup = 1;  
        public Node(int v, int s) {  
            val = v;  
            sum = s;  
        }  
    }  
    public List<Integer> countSmaller(int[] nums) {  
        Integer[] ans = new Integer[nums.length];  
        Node root = null;  
        for (int i = nums.length - 1; i >= 0; i--) {  
            root = insert(nums[i], root, ans, i, 0);  
        }  
        return Arrays.asList(ans);  
    }  
    private Node insert(int num, Node node, Integer[] ans, int i, int preSum) {  
        if (node == null) {  
            node = new Node(num, 0);  
            ans[i] = preSum;  
        } else if (node.val == num) {  
            node.dup++;  
            ans[i] = preSum + node.sum;  
        } else if (node.val > num) {  
            node.sum++;  
            node.left = insert(num, node.left, ans, i, preSum);  
        } else {  
            node.right = insert(num, node.right, ans, i, preSum + node.dup + node.sum);  
        }  
        return node;  
    } 

class Node{
    int val, leftSum = 0, count = 0;
    Node left, right;
    public Node(int val){
        this.val = val;
    }
}
public List<Integer> countSmaller(int[] nums) {
    Integer[] count = new Integer[nums.length];
    if(nums.length == 0){
        return Arrays.asList(count);
    }
    Node root = new Node(nums[nums.length - 1]);
    for(int i = nums.length - 1; i >= 0; i--){
        count[i] = insert(root, nums[i]);
    }
    return Arrays.asList(count);
}
private int insert(Node node, int num){
    int sum = 0;
    while(node.val != num){
        if(node.val > num){
            if(node.left == null) node.left = new Node(num);
            node.leftSum++;
            node = node.left;
        }else{
            sum += node.leftSum + node.count;
            if(node.right == null) node.right = new Node(num);
            node = node.right;
        }
    }
    node.count++;
    return sum + node.leftSum;
}

X. Merge Sort
http://www.voidcn.com/blog/smileyk/article/p-5747482.html
归并排序后，虽然数组有序的，但是原始顺序变化了，计算每个元素数量需要找到他们的位置，因此需要记录每个元素的index。


https://discuss.leetcode.com/topic/31554/11ms-java-solution-using-merge-sort-with-explanation
https://discuss.leetcode.com/topic/55751/3-ways-segment-tree-binary-indexed-tree-merge-sort-clean-java-code
The basic idea is to do merge sort to nums[]. To record the result, we need to keep the index of each number in the original array. So instead of sort the number in nums, we sort the indexes of each number. Example: nums = [5,2,6,1], indexes = [0,1,2,3] After sort: indexes = [3,1,0,2]
While doing the merge part, say that we are merging left[] and right[], left[] and right[] are already sorted.
We keep a rightcount to record how many numbers from right[] we have added and keep an array count[] to record the result.
When we move a number from right[] into the new sorted array, we increase rightcount by 1.
When we move a number from left[] into the new sorted array, we increase count[ index of the number ] by rightcount.
indexes is the array @lzyfriday wants to sort.
new_indexes is a temporary subarray that was created for doing the merge. After the merge was done successfully on the temporary subarray new_indexes, he is overwriting elements of indexes array with the appropriate elements from the temporary subarray new_indexes;
int[] count;
public List<Integer> countSmaller(int[] nums) {
    List<Integer> res = new ArrayList<Integer>();     

    count = new int[nums.length];
    int[] indexes = new int[nums.length];
    for(int i = 0; i < nums.length; i++){
     indexes[i] = i;
    }
    mergesort(nums, indexes, 0, nums.length - 1);
    for(int i = 0; i < count.length; i++){
     res.add(count[i]);
    }
    return res;
}
private void mergesort(int[] nums, int[] indexes, int start, int end){
 if(end <= start){
  return;
 }
 int mid = (start + end) / 2;
 mergesort(nums, indexes, start, mid);
 mergesort(nums, indexes, mid + 1, end);
 
 merge(nums, indexes, start, end);
}
private void merge(int[] nums, int[] indexes, int start, int end){
 int mid = (start + end) / 2;
 int left_index = start;
 int right_index = mid+1;
 int rightcount = 0;     
 int[] new_indexes = new int[end - start + 1];

 int sort_index = 0;
 while(left_index <= mid && right_index <= end){
  if(nums[indexes[right_index]] < nums[indexes[left_index]]){
   new_indexes[sort_index] = indexes[right_index];
   rightcount++;
   right_index++;
  }else{
   new_indexes[sort_index] = indexes[left_index];
   count[indexes[left_index]] += rightcount;
   left_index++;
  }
  sort_index++;
 }
 while(left_index <= mid){
  new_indexes[sort_index] = indexes[left_index];
  count[indexes[left_index]] += rightcount;
  left_index++;
  sort_index++;
 }
 while(right_index <= end){
  new_indexes[sort_index++] = indexes[right_index++];
 }
 for(int i = start; i <= end; i++){
  indexes[i] = new_indexes[i - start];
 }
}
https://discuss.leetcode.com/topic/31162/mergesort-solution/12
The smaller numbers on the right of a number are exactly those that jump from its right to its left during a stable sort. So I do mergesort with added tracking of those right-to-left jumps.
    class Pair {
        int index;
        int val;
        public Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        Pair[] arr = new Pair[nums.length];
        Integer[] smaller = new Integer[nums.length];
        Arrays.fill(smaller, 0);
        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Pair(i, nums[i]);
        }
        mergeSort(arr, smaller);
        res.addAll(Arrays.asList(smaller));
        return res;
    }
    private Pair[] mergeSort(Pair[] arr, Integer[] smaller) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid), smaller);
        Pair[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length), smaller);
        for (int i = 0, j = 0; i < left.length || j < right.length;) {
            if (j == right.length || i < left.length && left[i].val <= right[j].val) {
                arr[i + j] = left[i];
                smaller[left[i].index] += j;
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        return arr;
    }

class NumberIndex {
 int number;
 int index;

 NumberIndex(int number, int index) {
  this.number = number;
  this.index = index;
 }

 NumberIndex(NumberIndex another) {
  this.number = another.number;
  this.index = another.index;
 }
}

public List<Integer> countSmaller(int[] nums) {
 NumberIndex[] cnums = new NumberIndex[nums.length];
 for (int i = 0; i < nums.length; i++) {
  cnums[i] = new NumberIndex(nums[i], i);
 }
 int[] smaller = new int[nums.length];
 cnums = sort(cnums, smaller);
 List<Integer> res = new ArrayList<>();
 for (int i : smaller) {
  res.add(i);
 }
 return res;
}

private NumberIndex[] sort(NumberIndex[] nums, int[] smaller) {
 int half = nums.length / 2;
 if (half > 0) {
  NumberIndex[] rightPart = new NumberIndex[nums.length - half];
  NumberIndex[] leftPart = new NumberIndex[half];
  for (int i = 0; i < leftPart.length; i++) {
   leftPart[i] = new NumberIndex(nums[i]);
  }
  for (int i = 0; i < rightPart.length; i++) {
   rightPart[i] = new NumberIndex(nums[half + i]);
  }
  NumberIndex[] left = sort(leftPart, smaller), right = sort(
    rightPart, smaller);
  int m = left.length, n = right.length, i = 0, j = 0;
  while (i < m || j < n) {
   if (j == n || i < m && left[i].number <= right[j].number) {
    nums[i + j] = left[i];
    smaller[left[i].index] += j;
    i++;
   } else {
    nums[i + j] = right[j];
    j++;
   }
  }
 }
 return nums;
}

http://algobox.org/count-of-smaller-numbers-after-self/

Like the merge sort with one exception: we count the number of elements smaller when we merge.

public List<Integer> countSmaller(int[] nums) {

    int n = nums.length;

    int[] ans = new int[n];

    Tuple[] arr = new Tuple[n];

    for (int i = 0; i < n; ++i) arr[i] = new Tuple(nums[i], i);

    sort(ans, arr, n);

    List<Integer> list = new ArrayList<>(n);

    for (int x : ans) list.add(x);

    return list;

}


private Tuple[] sort(int[] ans, Tuple[] a, int n) {

    if (n > 1) {

        int i = n / 2, j = n - i;

        Tuple[] left = sort(ans, Arrays.copyOfRange(a, 0, i), i);

        Tuple[] right = sort(ans, Arrays.copyOfRange(a, i, n), j);

        for (int k = n - 1; k >= 0; --k)

            if (j == 0 || (i > 0 && left[i - 1].val > right[j - 1].val)) {

                ans[left[i - 1].idx] += j;

                a[k] = left[--i];

            } else

                a[k] = right[--j];

    }

    return a;

}


class Tuple {

    public int val;

    public int idx;


    public Tuple(int val, int idx) {

        this.val = val;

        this.idx = idx;

    }

}

https://discuss.leetcode.com/topic/31162/mergesort-solution/26
The smaller numbers on the right of a number are exactly those that jump from its right to its left during a stable sort. So I do mergesort with added tracking of those right-to-left jumps.
Use the idea of merge sort. Integer algorithm:
ex:
index: 0, 1
left: 2, 5
right: 1, 6
Each time we choose a left to the merged array. We want to know how many numbers from array right are moved before this number.
For example we take 1 from right array and merge sort it first. Then it’s 2 from left array. We find that there are j numbers moved before this left[i], in this case j == 1.
So the array smaller[original index of 2] += j.
Then we take 5 from array left. We also know that j numbers moved before this 5.
smaller[original index of 6] += j.
ex:
index: 0, 1, 2
left: 4, 5, 6
right: 1, 2, 3
when we take 4 for merge sort. We add j (j == 3) because we already take j numbers before take this 4.
During the merge sort, we have to know number and it’s original index. We use a class called Pair to encapsulate them together.
We need to pass the array smaller to merge sort method call because it might be changed during any level of merge sort. And the final smaller number is add up of all the numbers moved before this value.
def countSmaller(self, nums):
    def sort(enum):
        half = len(enum) / 2
        if half:
            left, right = sort(enum[:half]), sort(enum[half:])
            for i in range(len(enum))[::-1]:
                if not right or left and left[-1][1] > right[-1][1]:
                    smaller[left[-1][0]] += len(right)
                    enum[i] = left.pop()
                else:
                    enum[i] = right.pop()
        return enum
    smaller = [0] * len(nums)
    sort(list(enumerate(nums)))
    return smaller

https://leetcode.com/discuss/73256/mergesort-solution
The smaller numbers on the right of a number are exactly those that jump from its right to its left during a stable sort. So I do mergesort with added tracking of those right-to-left jumps.
def countSmaller(self, nums):  
    def sort(enum):  
        half = len(enum) / 2  
        if half:  
            left, right = sort(enum[:half]), sort(enum[half:])  
            for i in range(len(enum))[::-1]:  
                if not right or left and left[-1][1] > right[-1][1]:  
                    smaller[left[-1][0]] += len(right)  
                    enum[i] = left.pop()  
                else:  
                    enum[i] = right.pop()  
        return enum  
    smaller = [0] * len(nums)  
    sort(list(enumerate(nums)))  
    return smaller  
Merge sort (Java)
//not easy to understand: what's the indexes[]?
int[] count;  
public List<Integer> countSmaller(int[] nums) {  
    List<Integer> res = new ArrayList<Integer>();       
  
    count = new int[nums.length];  
    int[] indexes = new int[nums.length];  
    for(int i = 0; i < nums.length; i++){  
        indexes[i] = i;  
    }  
    mergesort(nums, indexes, 0, nums.length - 1);  
    for(int i = 0; i < count.length; i++){  
        res.add(count[i]);  
    }  
    return res;  
}  
private void mergesort(int[] nums, int[] indexes, int start, int end){  
    if(end <= start){  
        return;  
    }  
    int mid = (start + end) / 2;  
    mergesort(nums, indexes, start, mid);  
    mergesort(nums, indexes, mid + 1, end);  
  
    merge(nums, indexes, start, end);  
}  
private void merge(int[] nums, int[] indexes, int start, int end){  
    int mid = (start + end) / 2;  
    int left_index = start;  
    int right_index = mid+1;  
    int rightcount = 0;       
    int[] new_indexes = new int[end - start + 1];  
  
    int sort_index = 0;  
    while(left_index <= mid && right_index <= end){  
        if(nums[indexes[right_index]] < nums[indexes[left_index]]){  
            new_indexes[sort_index] = indexes[right_index];  
            rightcount++;  
            right_index++;  
        }else{  
            new_indexes[sort_index] = indexes[left_index];  
            count[indexes[left_index]] += rightcount;  
            left_index++;  
        }  
        sort_index++;  
    }  
    while(left_index <= mid){  
        new_indexes[sort_index] = indexes[left_index];  
        count[indexes[left_index]] += rightcount;  
        left_index++;  
        sort_index++;  
    }  
    while(right_index <= end){  
        new_indexes[sort_index++] = indexes[right_index++];  
    }  
    for(int i = start; i <= end; i++){  
        indexes[i] = new_indexes[i - start];  
    }  
}  
http://www.hrwhisper.me/leetcode-count-of-smaller-numbers-after-self/
简单的说就是求逆序数。
使用逆序数有经典的解法为合并排序。

struct Node {

 int val;

 int index;

 int cnt;

 Node(int val, int index) : val(val), index(index), cnt(0) {}

 bool operator <= (const Node &node2)const {

 return val <= node2.val;

 }

};


class Solution {

public:

 void combine(vector<Node> &nums, int Lpos, int Lend, int Rend, vector<Node> &temp) {

 int Rpos = Lend + 1;

 int Tpos = Lpos;

 int n = Rend - Lpos + 1;

 int t = Rpos;

 while (Lpos <= Lend && Rpos <= Rend) {

 if (nums[Lpos] <= nums[Rpos]) {

 temp[Tpos] = nums[Lpos];

 temp[Tpos].cnt += Rpos - t ;

 Tpos++; Lpos++;

 }

 else {

 temp[Tpos++] = nums[Rpos++];

 }

 }


 while (Lpos <= Lend) {

 temp[Tpos] = nums[Lpos];

 temp[Tpos].cnt += Rpos - t;

 Tpos++; Lpos++;

 }


 while (Rpos <= Rend) 

 temp[Tpos++] = nums[Rpos++];


 for (int i = 0; i< n; i++, Rend--) 

 nums[Rend] = temp[Rend];

 }


 void merge_sort(vector<Node> & nums, int L, int R, vector<Node> &temp) {

 if (L < R) {

 int m = (L + R) >> 1;

 merge_sort(nums, L, m, temp);

 merge_sort(nums, m + 1, R, temp);

 combine(nums, L, m, R, temp);

 }

 }


 vector<int> countSmaller(vector<int>& nums) {

 vector<Node> mynums;

 vector<Node> temp(nums.size(), Node(0, 0));

 for (int i = 0; i < nums.size(); i++) 

 mynums.push_back(Node(nums[i], i));

 

 vector<int> ans(nums.size(), 0);

 merge_sort(mynums, 0, nums.size() - 1, temp);


 for (int i = 0; i < nums.size(); i++) 

 ans[mynums[i].index] = mynums[i].cnt;

 

 return ans;

 }
https://leetcode.com/discuss/73233/complicated-segmentree-solution-hope-to-find-a-better-one
Using a Binary Indexed Tree (Fenwick tree) can shorten the code a lot. :P
    private void add(int[] bit, int i, int val) {
        for (; i < bit.length; i += i & -i) bit[i] += val;
    }

    private int query(int[] bit, int i) {
        int ans = 0;
        for (; i > 0; i -= i & -i) ans += bit[i];
        return ans;
    }

    public List<Integer> countSmaller(int[] nums) {
        int[] tmp = nums.clone();
        Arrays.sort(tmp);
        for (int i = 0; i < nums.length; i++) nums[i] = Arrays.binarySearch(tmp, nums[i]) + 1;
        int[] bit = new int[nums.length + 1];
        Integer[] ans = new Integer[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            ans[i] = query(bit, nums[i] - 1);
            add(bit, nums[i], 1);
        }
        return Arrays.asList(ans);
    }
Segment tree:
TODO
http://www.jiuzhang.com/solutions/count-of-smaller-number/
    static class segmentTreeNode {
        int start, end, count;
        segmentTreeNode left, right;
        segmentTreeNode(int start, int end, int count) {
            this.start = start;
            this.end = end;
            this.count = count;
            left = null;
            right = null;
        }
    }
    public static List<Integer> countSmaller(int[] nums) {
        // write your code here
        List<Integer> result = new ArrayList<Integer>();

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i : nums) {
            min = Math.min(min, i);

        }
        if (min < 0) {
            for (int i = 0; i < nums.length; i++) {
                nums[i] -= min;//deal with negative numbers, seems a dummy way
            }
        }
        for (int i : nums) {
            max = Math.max(max, i);
        }
        segmentTreeNode root = build(0, max);
        for (int i = 0; i < nums.length; i++) {
            updateAdd(root, nums[i]);
        }
        for (int i = 0; i < nums.length; i++) {
            updateDel(root, nums[i]);
            result.add(query(root, 0, nums[i] - 1));
        }
        return result;
    }
    public static segmentTreeNode build(int start, int end) {
        if (start > end) return null;
        if (start == end) return new segmentTreeNode(start, end, 0);
        int mid = (start + end) / 2;
        segmentTreeNode root = new segmentTreeNode(start, end, 0);
        root.left = build(start, mid);
        root.right = build(mid + 1, end);
        root.count = root.left.count + root.right.count;
        return root;
    }

    public static int query(segmentTreeNode root, int start, int end) {
        if (root == null) return 0;
        if (root.start == start && root.end == end) return root.count;
        int mid = (root.start + root.end) / 2;
        if (end < mid) {
            return query(root.left, start, end);
        } else if (start > end) {
            return query(root.right, start, end);
        } else {
            return query(root.left, start, mid) + query(root.right, mid + 1, end);
        }
    }

    public static void updateAdd(segmentTreeNode root, int val) {
        if (root == null || root.start > val || root.end < val) return;
        if (root.start == val && root.end == val) {
            root.count ++;
            return;
        }
        int mid = (root.start + root.end) / 2;
        if (val <= mid) {
            updateAdd(root.left, val);
        } else {
            updateAdd(root.right, val);
        }
        root.count = root.left.count + root.right.count;
    }

    public static void updateDel(segmentTreeNode root, int val) {
        if (root == null || root.start > val || root.end < val) return;
        if (root.start == val && root.end == val) {
            root.count --;
            return;
        }
        int mid = (root.start + root.end) / 2;
        if (val <= mid) {
            updateDel(root.left, val);
        } else {
            updateDel(root.right, val);
        }
        root.count = root.left.count + root.right.count;
    }
http://bookshadow.com/weblog/2015/12/06/leetcode-count-of-smaller-numbers-after-self/
    def countSmaller(self, nums):
        ans = [0] * len(nums)
        bst = BinarySearchTree()
        for i in range(len(nums) - 1, -1, -1):
            ans[i] = bst.insert(nums[i])
        return ans

class TreeNode(object):
    def __init__(self, val):
        self.leftCnt = 0
        self.val = val
        self.cnt = 1
        self.left = None
        self.right = None

class BinarySearchTree(object):
    def __init__(self):
        self.root = None

    def insert(self, val):
        if self.root is None:
            self.root = TreeNode(val)
            return 0
        root = self.root
        cnt = 0
        while root:
            if val < root.val:
                root.leftCnt += 1
                if root.left is None:
                    root.left = TreeNode(val)
                    break
                root = root.left
            elif val > root.val:
                cnt += root.leftCnt + root.cnt
                if root.right is None:
                    root.right = TreeNode(val)
                    break
                root = root.right
            else:
                cnt += root.leftCnt
                root.cnt += 1
                break
        return cnt
http://traceformula.blogspot.com/2015/12/count-of-smaller-numbers-after-self.html

X. bit trick
https://discuss.leetcode.com/topic/31924/o-nlogn-divide-and-conquer-java-solution-based-on-bit-by-bit-comparison
given two integers, how would you determine which one is larger and which is smaller?
But now imagine you were the computer, how would you actually do the comparison? Remember now all you can see are two series of binary bits representing the two integers. You have no better ways but start from the most significant bit and check bit by bit towards the least significant bit. The first bit will tell you the sign of the two numbers and you know positive numbers (with sign bit value of 0) will be greater than negative numbers (with sign bit value 1). If they have the same sign, then you continue to the next bit with the idea in mind that numbers with bit value 1 will be greater than those with bit value 0. You compare the two series of binary bits in this manner until at some point you can distinguish them or end up with no more bits to check(means the two integers are equal).
What if we have an array of integers? It doesn't matter. We can proceed in the same way. First check the sign of each integer and partition them into two groups: positive integers and negative ones. Then for each group, we partition the integers further into two groups depending on the next-bit value: those with bit value 1 and those with bit value 0 (to unify sign partition and other bits partitions, we will call the two groups after each partition as highGroup and lowGroup to indicate that all the integers in the highGroup will be greater than those in the lowGroup). And so on until we either have no numbers to partition or no bits to check. (Here we assume the integers have finite length of bits)
Now let's turn to our problem: for each integer in an array, count the number of integers to its right that are smaller than it. Based on the analysis above, we will have a group of integers as the input (initially the group will be the same as the input array). we then divide the integers into highGroup and lowGroup according to either the sign bit or other-bit values. Since for each integer in the group, we only care about integers to its right, it would be better if we scan the group from right to left. For each integer currently being checked, if it belongs to the highGroup, we know it will be greater than all those in the lowGroup accumulated so far. We proceed in this fashion until either the group is empty or have no more bits to check.
Notes:
To unify sign bit partition and other-bit partition, we used a variable called highBit to distinguish these two cases.
we store the indices of the elements instead of the elements themselves as it would be easier to set the results.
I used a customized ListNode instead of the Java built-in lists to avoid reallocation of space for each partition. So after each partition, all elements belonging to the lowGroup will be detached from the input group (which is assumed to be highGroup by default) and form the lowGroup while the remaining elements will be the highGroup.
I reversed the order of the elements when adding them to the list due to list performance consideration.
The time complexity can be analyzed as follows:
Let T(n) be the total run time and after the partition, we have b*n number in one group and (1-b)n in the other with 0 <= b <= 1. Now the original problem is divided into two smaller subproblems with size given by bn and (1-b)*n. And the solution to the original problem can be obtained by combining the solutions to the two subproblems in O(n) time, then we have:
T(n) = T(b*n) + T((1-b)*n) + O(n)
The solution to the characteristic equation b^x + (1-b)^x = 1 is x = 1 so the runtime complexity will be O(nlogn) according to the master theorem (one tricky scenario is b = 0 or b = 1. In this case the runtime is essentially linear provided the total number of bits in each integer is constant). By the way the space complexity is apparently O(n) due to the partition.
Update: The time complexity analysis above is based on the assumption that the number of partition is logarithmically dependent on the input array size, which is not the case for this problem. The number of partition is constant provided the integer bit size is constant (See @StefanPochmann's answer below).
Therefore O(nlogn) is a relaxed upper bound while the algorithm can run in linear time.
The runtime complexity is indeed linear, provided the bit size for an integer is constant. The nlogn runtime is based on the assumption that the number of partitions is logarithmically dependent on the input size, which is not in this case. By the way, nice job to unify the sign bit and other bits.

class ListNode {
    int val, size;
    ListNode next;
     
    ListNode(int val) {
     this.val = val;
    }
}
    
public List<Integer> countSmaller(int[] nums) {
    Integer[] res = new Integer[nums.length];
    Arrays.fill(res, 0);
        
    ListNode highGroup = new ListNode(-1), itr = highGroup;
    
    for (int i = nums.length - 1; i >= 0; i--) {
        itr.next = new ListNode(i);
        itr = itr.next;
        highGroup.size++;
    }
        
    countSmallerSub(nums, highGroup, 1 << 31, res);
    return Arrays.asList(res);
}
    
private void countSmallerSub(int[] nums, ListNode highGroup, int mask, Integer[] res) {
    if (mask != 0 && highGroup.size > 1) {
     ListNode lowGroup = new ListNode(-1);
     
     int highBit = (mask < 0 ? 0 : mask);
         
     for (ListNode i1 = highGroup, i2 = lowGroup; i1.next != null;) {
         if ((nums[i1.next.val] & mask) == highBit) {
      res[i1.next.val] += lowGroup.size;
      i1 = i1.next;
              
         } else {
      i2.next = i1.next;
      i1.next = i1.next.next;
      i2 = i2.next;
      i2.next = null;
      lowGroup.size++;
      highGroup.size--;
         }
     }
         
     countSmallerSub(nums, highGroup, mask >>> 1, res);
     countSmallerSub(nums, lowGroup, mask >>> 1, res);
    }
}

one tricky scenario is b = 0 or b = 1. In this case the runtime is essentially linear provided the total number of bits in each integer is constant
I think it's linear for any b (if bit size is constant), no? During the whole process, each number is handled at most 32 times.
Nice one. I tried to deal with the first bit differently, here's the nicest I found:
public List<Integer> countSmaller(int[] nums) {
    List<Integer> res = new ArrayList<>(nums.length);
    List<Integer> index = new ArrayList<>(nums.length);

    for (int i = nums.length - 1; i >= 0; i--) {
        res.add(0);
        index.add(i);
    }

    countSmallerSub(nums, index, 1 << 31, res);

    return res;
}

private void countSmallerSub(int[] nums, List<Integer> index, int mask, List<Integer> res) {
    if (mask != 0 && index.size() > 1) {
        List<Integer> lowGroup = new ArrayList<>(index.size());
        List<Integer> highGroup = new ArrayList<>(index.size());

        int high = mask < 0 ? 0 : mask;
        for (int i = 0; i < index.size(); i++) {
            if ((nums[index.get(i)] & mask) == high) {
                res.set(index.get(i), res.get(index.get(i)) + lowGroup.size());
                highGroup.add(index.get(i));
            } else {
                lowGroup.add(index.get(i));
            }
        }

        countSmallerSub(nums, lowGroup, mask >>> 1, res);
        countSmallerSub(nums, highGroup, mask >>> 1, res);
    }
}
X. Using Binary Search
https://discuss.leetcode.com/topic/31173/my-simple-ac-java-binary-search-code
http://www.cnblogs.com/grandyang/p/5078490.html
思路是将给定数组从最后一个开始，用二分法插入到一个新的数组，这样新数组就是有序的，那么此时该数字在新数组中的坐标就是原数组中其右边所有较小数字的个数
Traverse from the back to the beginning of the array, maintain an sorted array of numbers have been visited. Use findIndex() to find the first element in the sorted array which is larger or equal to target number. For example, [5,2,3,6,1], when we reach 2, we have a sorted array[1,3,6], findIndex() returns 1, which is the index where 2 should be inserted and is also the number smaller than 2. Then we insert 2 into the sorted array to form [1,2,3,6].
Due to the O(n) complexity of ArrayList insertion, the total runtime complexity is not very fast, but anyway it got AC for around 53ms.
- maybe we can use a wrapper data structure: hashmap+ linkedlist so O(1) to add, O(1) to get
public List<Integer> countSmaller(int[] nums) {
    Integer[] ans = new Integer[nums.length];
    List<Integer> sorted = new ArrayList<Integer>();
    for (int i = nums.length - 1; i >= 0; i--) {
        int index = findIndex(sorted, nums[i]);
        ans[i] = index;
        sorted.add(index, nums[i]);
    }
    return Arrays.asList(ans);
}
private int findIndex(List<Integer> sorted, int target) {
    if (sorted.size() == 0) return 0;
    int start = 0;
    int end = sorted.size() - 1;
    if (sorted.get(end) < target) return end + 1;
    if (sorted.get(start) >= target) return 0;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (sorted.get(mid) < target) {
            start = mid + 1;
        } else {
            end = mid;
        }
    }
    if (sorted.get(start) >= target) return start;
    return end;
}
Due to the O(n) complexity of ArrayList insertion, the total runtime complexity is not very fast, but anyway it got AC for around 53ms.
Very nice solution. A small suggestion about the binary search part. If you have (start < end), then start index will be the solution.
while (start < end) {
    int mid = start + (end - start) / 2;
    if (sorted.get(mid) < target) {
        start = mid + 1;
    } else {
        end = mid;
    }
}
X. brute force
http://www.cnblogs.com/yrbbest/p/5068550.html
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums == null || nums.length == 0) {
            return res;
        } 
        for(int i = 0; i < nums.length; i++) {
            int count = 0;
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[j] < nums[i]) {
                    count++;
                }
            }
            res.add(count);
        }
        
        return res;
    }


Maximal Surpasser Count Problem
http://www.fgdsb.com/2015/01/11/maximal-surpasser-count/
输入一个数组，返回数组元素的surpasser个数的最大值。
数组元素a[i] 的surpasser是指元素a[j], j > i， a[j] > a[i]。
比如[10, 3, 7, 1, 23, 14, 6, 9] 这个数组中10的surpasser是23,14，个数是2。而3的surpasser是7,23,14,6,9，个数为5，并且是最多的一个。所以返回5。
利用特殊数据结构的话，本题有很多种做法，比如BST，线段树，点树，树状数组。下面给出一种归并排序思路的解法。实际上跟Inverse Pairs基本是完全一样的。这里关心的是顺序而不是逆序。
大体思路就是在降序归并排序的过程中每次遇到顺序对就记录下来，比如merge的过程中两个元素分别是3和7，3 < 7，所以3的顺序数+1。最后merge sort完毕后，每个顺序数的个数我们都知道，返回最大值即可。
思路很简单，唯一tricky的地方就是每次遇到顺序的时候，这个顺序数的个数到底加多少。这个需要注意一下。

用BST的话也很简单，注意每个节点要存surpasser的数量，O(NlogN)建树，然后O(N)遍历一遍找到最大值即可。注意tricky的地方是BST的定义是不允许有重复的，而这题的输入数组是有重复的，要处理一下这个情况。






int max_surpasser(vector<int> nums) {


vector<int> temp(nums.size());


int ret = 0;


unordered_map<int, int> counts;




auto merge = [&](int left, int mid, int right) {


if(left >= right) return;


int l = mid, r = right, cur = right;


while (l >= left && r > mid) {


if(nums[l] < nums[r]) {


counts[nums[l]] += r - mid;


ret = max(ret, counts[nums[l]]);


temp[cur--] = nums[l--];


} else {


temp[cur--] = nums[r--];


}


}


while(l >= left) temp[cur--] = nums[l--];


while(r > mid) temp[cur--] = nums[r--];       


copy(temp.begin()+left, temp.begin()+right+1, nums.begin()+left);


};


function<void(int,int)> sort = [&](int left, int right) {


if(left >= right) return;


int mid = left + (right - left) / 2;


sort(left, mid);


sort(mid + 1, right);


merge(left, mid, right);


};


sort(0, (int)nums.size() - 1);


return ret;


}

http://www.shadabahmed.com/blog/2013/03/18/puzzle-a-surpassing-problem/
Related: [LintCode] Count of Smaller Number before itself
 * @author het
 *
 */
public class LeetCode315 {
//	public List<Integer> countSmaller(int[] nums) {
//        List<Integer> res = new ArrayList<>();
//        if(nums == null || nums.length == 0) return res;
//        TreeNode root = new TreeNode(nums[nums.length - 1]);
//        res.add(0);
//        for(int i = nums.length - 2; i >= 0; i--) {
//            int count = insertNode(root, nums[i]);
//            res.add(count);
//        }
//        Collections.reverse(res);
//        return res;
//    }
//
//    public int insertNode(TreeNode root, int val) {
//        int thisCount = 0;
//        while(true) {
//            if(val <= root.val) {
//                root.count++;
//                if(root.left == null) {
//                    root.left = new TreeNode(val); break;
//                } else {
//                    root = root.left;
//                }
//            } else {
//                thisCount += root.count;
//                if(root.right == null) {
//                    root.right = new TreeNode(val); break;
//                } else {
//                    root = root.right;
//                }
//            }
//        }
//        return thisCount;
//    }
	
//	https://leetcode.com/discuss/73762/9ms-short-java-bst-solution-get-answer-when-building-bst
//		Every node will maintain a val sum recording the total of number on it's left bottom side, dupcounts the duplication.
	//For example, [3, 2, 2, 6, 1], from back to beginning,we would have:
//		                1(0, 1)
//		                     \
//		                     6(3, 1)
//		                     /
//		                   2(0, 2)
//		                       \
//		                        3(0, 1)
//		When we try to insert a number, the total number of smaller number would be adding dup and sum of the nodes where we turn right.
	//for example, if we insert 5, it should be inserted on the way down to the right of 3, the nodes where we turn 
	//right is 1(0,1), 2,(0,2), 3(0,1), so the answer should be (0 + 1)+(0 + 2)+ (0 + 1) = 4
//		if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + (3 + 1) = 5
//		runtime could be O(n^2) when processing input like n, n-1, ..., 2, 1. I think the test cases are not good enough.
//		There is no need for considering the duplication elements. See the code below which is referred from: http://www.cnblogs.com/grandyang/p/5078490.html
		class TreeNode{
		        int smallCount;
		        int val;
		        TreeNode left;
		        TreeNode right;
		        public TreeNode(int count, int val){
		            this.smallCount = count;
		            this.val = val;
		        }
		    }
		    
		    public List<Integer> countSmaller(int[] nums) {
		        TreeNode root = null;
		        Integer[] ret = new Integer[nums.length];//\\
		        if(nums == null || nums.length == 0) return Arrays.asList(ret);
		        for(int i=nums.length-1; i>=0; i--){
		            root = insert(root, nums[i], ret, i, 0);
		        }
		        return Arrays.asList(ret);//\\
		    }
		    
		    public TreeNode insert(TreeNode root, int val, Integer[] ans, int index, int preSum){
		        if(root == null){
		            root = new TreeNode(0, val);
		            ans[index] = preSum;
		        }
		        else if(root.val>val){
		            root.smallCount++;
		            root.left = insert(root.left, val, ans, index, preSum);
		        }
		        else{
		            root.right = insert(root.right, val, ans, index, root.smallCount + preSum + (root.val<val?1:0));//only adding 1 on preSum if root.val is only smaller than val
		        }
		        return root;
		    }

//		class Node{
//		    int val, leftSum = 0, count = 0;
//		    Node left, right;
//		    public Node(int val){
//		        this.val = val;
//		    }
//		}
//		public List<Integer> countSmaller(int[] nums) {
//		    Integer[] count = new Integer[nums.length];
//		    if(nums.length == 0){
//		        return Arrays.asList(count);
//		    }
//		    Node root = new Node(nums[nums.length - 1]);
//		    for(int i = nums.length - 1; i >= 0; i--){
//		        count[i] = insert(root, nums[i]);
//		    }
//		    return Arrays.asList(count);
//		}
//		private int insert(Node node, int num){
//		    int sum = 0;
//		    while(node.val != num){
//		        if(node.val > num){
//		            if(node.left == null) node.left = new Node(num);
//		            node.leftSum++;
//		            node = node.left;
//		        }else{
//		            sum += node.leftSum + node.count;
//		            if(node.right == null) node.right = new Node(num);
//		            node = node.right;
//		        }
//		    }
//		    node.count++;
//		    return sum + node.leftSum;
//		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeetCode315 leetCode315 = new LeetCode315();
		LeetCode315.RedBlackBST tree = new LeetCode315.RedBlackBST();
		//;
		System.out.println(tree.countSmaller(new int[]{3, 5, 2, 6, 1, 3}));
		System.out.println(leetCode315.countSmaller(new int[]{3, 5, 2, 6, 1, 3}));
		System.out.println(tree.countSmaller(new int[]{2,2,2,2,2,2,2,2,2,2}));
		System.out.println(tree.countSmaller(new int[]{2,2,2,2,2,2,2,1,1,1,1,1}));

	}
	
	
	
	static class RedBlackBST {

	    private static final boolean RED   = true;
	    private static final boolean BLACK = false;

	    private Node root;     // root of the BST

	    // BST helper node data type
	    private class Node {
	        private Integer key;           // key
	         int dup = 1;
	        private Node left, right;  // links to left and right subtrees
	        private boolean color;     // color of parent link
	        private int N;             // subtree count

	        public Node(Integer key, boolean color, int N) {
	            this.key = key;
	           
	            this.color = color;
	            this.N = N;
	        }
	    }

	    /**
	     * Initializes an empty symbol table.
	     */
	    public RedBlackBST() {
	    }

	   /***************************************************************************
	    *  Node helper methods.
	    ***************************************************************************/
	    // is node x red; false if x is null ?
	    private boolean isRed(Node x) {
	        if (x == null) return false;
	        return x.color == RED;
	    }

	    // number of node in subtree rooted at x; 0 if x is null
	    private int size(Node x) {
	        if (x == null) return 0;
	        return x.N;
	    } 


	    /**
	     * Returns the number of key-value pairs in this symbol table.
	     * @return the number of key-value pairs in this symbol table
	     */
	    public int size() {
	        return size(root);
	    }

	   /**
	     * Is this symbol table empty?
	     * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
	     */
	    public boolean isEmpty() {
	        return root == null;
	    }


	   /***************************************************************************
	    *  Standard BST search.
	    ***************************************************************************/

	    /**
	     * Returns the value associated with the given key.
	     * @param key the key
	     * @return the value associated with the given key if the key is in the symbol table
	     *     and <tt>null</tt> if the key is not in the symbol table
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public Node get(Integer key) {
	        if (key == null) throw new NullPointerException("argument to get() is null");
	        return get(root, key);
	    }

	    // value associated with the given key in subtree rooted at x; null if no such key
	    private Node get(Node x, Integer key) {
	        while (x != null) {
	            int cmp = key.compareTo(x.key);
	            if      (cmp < 0) x = x.left;
	            else if (cmp > 0) x = x.right;
	            else              return x;
	        }
	        return null;
	    }

	    /**
	     * Does this symbol table contain the given key?
	     * @param key the key
	     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
	     *     <tt>false</tt> otherwise
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public boolean contains(Integer key) {
	        return get(key) != null;
	    }

	   /***************************************************************************
	    *  Red-black tree insertion.
	    ***************************************************************************/

	    /**
	     * Inserts the specified key-value pair into the symbol table, overwriting the old 
	     * value with the new value if the symbol table already contains the specified key.
	     * Deletes the specified key (and its associated value) from this symbol table
	     * if the specified value is <tt>null</tt>.
	     *
	     * @param key the key
	     * @param val the value
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public void put(Integer key) {
	        if (key == null) throw new NullPointerException("first argument to put() is null");
	        

	        root = put(root, key);
	        root.color = BLACK;
	        // assert check();
	    }
	    
	    
	    public List<Integer> countSmaller(int[] nums) {
	    	Node root = null;
	        Integer[] ret = new Integer[nums.length];//\\
	        if(nums == null || nums.length == 0) return Arrays.asList(ret);
	        for(int i=nums.length-1; i>=0; i--){
	            root = insert(root, new Integer(nums[i]), ret, i, 0);
	        }
	        return Arrays.asList(ret);//\\
	    }
	    
	    public Node insert(Node h, Integer key, Integer[] ans, int index, int preSum){
	    	
	    	if (h == null) {
	    		ans[index ] = preSum;
	    		return new Node(key, RED, 1);
	    	}

	        int cmp = key.compareTo(h.key);
	        if      (cmp < 0) h.left  = insert(h.left,  key, ans, index, preSum); 
	        else  if (cmp > 0 )h.right = insert(h.right, key, ans, index, preSum + getSmaller(h)); 
	        else   {
	        	h.dup+=1;
	        	 h.N = size(h.left) + size(h.right) + h.dup;
	        	ans[index ] = preSum +(h.left == null? 0 : h.left.N);
	        	
	        }
	       

	        // fix-up any right-leaning links
	        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
	        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
	        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
	        h.N = size(h.left) + size(h.right) + h.dup;

	        return h;
	        
	        
	    }

	    private int getSmaller(Node h) {
			Node left = h.left;
			return left == null? h.dup : h.dup+ left.N;
		}

		// insert the key-value pair in the subtree rooted at h
	    private Node put(Node h, Integer key) { 
	        if (h == null) return new Node(key, RED, 1);

	        int cmp = key.compareTo(h.key);
	        if      (cmp < 0) h.left  = put(h.left,  key); 
	        else if (cmp > 0) h.right = put(h.right, key); 
	       

	        // fix-up any right-leaning links
	        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
	        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
	        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
	        h.N = size(h.left) + size(h.right) + 1;

	        return h;
	    }

	   /***************************************************************************
	    *  Red-black tree deletion.
	    ***************************************************************************/

	    /**
	     * Removes the smallest key and associated value from the symbol table.
	     * @throws NoSuchElementException if the symbol table is empty
	     */
	    public void deleteMin() {
	        if (isEmpty()) throw new NoSuchElementException("BST underflow");

	        // if both children of root are black, set root to red
	        if (!isRed(root.left) && !isRed(root.right))
	            root.color = RED;

	        root = deleteMin(root);
	        if (!isEmpty()) root.color = BLACK;
	        // assert check();
	    }

	    // delete the key-value pair with the minimum key rooted at h
	    private Node deleteMin(Node h) { 
	        if (h.left == null)
	            return null;

	        if (!isRed(h.left) && !isRed(h.left.left))
	            h = moveRedLeft(h);

	        h.left = deleteMin(h.left);
	        return balance(h);
	    }


	    /**
	     * Removes the largest key and associated value from the symbol table.
	     * @throws NoSuchElementException if the symbol table is empty
	     */
	    public void deleteMax() {
	        if (isEmpty()) throw new NoSuchElementException("BST underflow");

	        // if both children of root are black, set root to red
	        if (!isRed(root.left) && !isRed(root.right))
	            root.color = RED;

	        root = deleteMax(root);
	        if (!isEmpty()) root.color = BLACK;
	        // assert check();
	    }

	    // delete the key-value pair with the maximum key rooted at h
	    private Node deleteMax(Node h) { 
	        if (isRed(h.left))
	            h = rotateRight(h);

	        if (h.right == null)
	            return null;

	        if (!isRed(h.right) && !isRed(h.right.left))
	            h = moveRedRight(h);

	        h.right = deleteMax(h.right);

	        return balance(h);
	    }

	    /**
	     * Removes the specified key and its associated value from this symbol table     
	     * (if the key is in this symbol table).    
	     *
	     * @param  key the key
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
//	    public void delete(Integer key) { 
//	        if (key == null) throw new NullPointerException("argument to delete() is null");
//	        if (!contains(key)) return;
//
//	        // if both children of root are black, set root to red
//	        if (!isRed(root.left) && !isRed(root.right))
//	            root.color = RED;
//
//	        root = delete(root, key);
//	        if (!isEmpty()) root.color = BLACK;
//	        // assert check();
//	    }

//	    // delete the key-value pair with the given key rooted at h
//	    private Node delete(Node h, Integer key) { 
//	        // assert get(h, key) != null;
//
//	        if (key.compareTo(h.key) < 0)  {
//	            if (!isRed(h.left) && !isRed(h.left.left))
//	                h = moveRedLeft(h);
//	            h.left = delete(h.left, key);
//	        }
//	        else {
//	            if (isRed(h.left))
//	                h = rotateRight(h);
//	            if (key.compareTo(h.key) == 0 && (h.right == null))
//	                return null;
//	            if (!isRed(h.right) && !isRed(h.right.left))
//	                h = moveRedRight(h);
//	            if (key.compareTo(h.key) == 0) {
//	                Node x = min(h.right);
//	                h.key = x.key;
//	                h.val = x.val;
//	                // h.val = get(h.right, min(h.right).key);
//	                // h.key = min(h.right).key;
//	                h.right = deleteMin(h.right);
//	            }
//	            else h.right = delete(h.right, key);
//	        }
//	        return balance(h);
//	    }

	   /***************************************************************************
	    *  Red-black tree helper functions.
	    ***************************************************************************/

	    // make a left-leaning link lean to the right
	    private Node rotateRight(Node h) {
	        // assert (h != null) && isRed(h.left);
	        Node x = h.left;
	        h.left = x.right;
	        x.right = h;
	        x.color = x.right.color;
	        x.right.color = RED;
	        x.N = h.N;
	        h.N = size(h.left) + size(h.right) + 1;
	        return x;
	    }

	    // make a right-leaning link lean to the left
	    private Node rotateLeft(Node h) {
	        // assert (h != null) && isRed(h.right);
	        Node x = h.right;
	        h.right = x.left;
	        x.left = h;
	        x.color = x.left.color;
	        x.left.color = RED;
	        x.N = h.N;
	        h.N = size(h.left) + size(h.right) + 1;
	        return x;
	    }

	    // flip the colors of a node and its two children
	    private void flipColors(Node h) {
	        // h must have opposite color of its two children
	        // assert (h != null) && (h.left != null) && (h.right != null);
	        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
	        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
	        h.color = !h.color;
	        h.left.color = !h.left.color;
	        h.right.color = !h.right.color;
	    }

	    // Assuming that h is red and both h.left and h.left.left
	    // are black, make h.left or one of its children red.
	    private Node moveRedLeft(Node h) {
	        // assert (h != null);
	        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

	        flipColors(h);
	        if (isRed(h.right.left)) { 
	            h.right = rotateRight(h.right);
	            h = rotateLeft(h);
	            flipColors(h);
	        }
	        return h;
	    }

	    // Assuming that h is red and both h.right and h.right.left
	    // are black, make h.right or one of its children red.
	    private Node moveRedRight(Node h) {
	        // assert (h != null);
	        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
	        flipColors(h);
	        if (isRed(h.left.left)) { 
	            h = rotateRight(h);
	            flipColors(h);
	        }
	        return h;
	    }

	    // restore red-black tree invariant
	    private Node balance(Node h) {
	        // assert (h != null);

	        if (isRed(h.right))                      h = rotateLeft(h);
	        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
	        if (isRed(h.left) && isRed(h.right))     flipColors(h);

	        h.N = size(h.left) + size(h.right) + 1;
	        return h;
	    }


	   /***************************************************************************
	    *  Utility functions.
	    ***************************************************************************/

	    /**
	     * Returns the height of the BST (for debugging).
	     * @return the height of the BST (a 1-node tree has height 0)
	     */
	    public int height() {
	        return height(root);
	    }
	    private int height(Node x) {
	        if (x == null) return -1;
	        return 1 + Math.max(height(x.left), height(x.right));
	    }

	   /***************************************************************************
	    *  Ordered symbol table methods.
	    ***************************************************************************/

	    /**
	     * Returns the smallest key in the symbol table.
	     * @return the smallest key in the symbol table
	     * @throws NoSuchElementException if the symbol table is empty
	     */
	    public Integer min() {
	        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
	        return min(root).key;
	    } 

	    // the smallest key in subtree rooted at x; null if no such key
	    private Node min(Node x) { 
	        // assert x != null;
	        if (x.left == null) return x; 
	        else                return min(x.left); 
	    } 

	    /**
	     * Returns the largest key in the symbol table.
	     * @return the largest key in the symbol table
	     * @throws NoSuchElementException if the symbol table is empty
	     */
	    public Integer max() {
	        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
	        return max(root).key;
	    } 

	    // the largest key in the subtree rooted at x; null if no such key
	    private Node max(Node x) { 
	        // assert x != null;
	        if (x.right == null) return x; 
	        else                 return max(x.right); 
	    } 


	    /**
	     * Returns the largest key in the symbol table less than or equal to <tt>key</tt>.
	     * @param key the key
	     * @return the largest key in the symbol table less than or equal to <tt>key</tt>
	     * @throws NoSuchElementException if there is no such key
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public Integer floor(Integer key) {
	        if (key == null) throw new NullPointerException("argument to floor() is null");
	        if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
	        Node x = floor(root, key);
	        if (x == null) return null;
	        else           return x.key;
	    }    

	    // the largest key in the subtree rooted at x less than or equal to the given key
	    private Node floor(Node x, Integer key) {
	        if (x == null) return null;
	        int cmp = key.compareTo(x.key);
	        if (cmp == 0) return x;
	        if (cmp < 0)  return floor(x.left, key);
	        Node t = floor(x.right, key);
	        if (t != null) return t; 
	        else           return x;
	    }

	    /**
	     * Returns the smallest key in the symbol table greater than or equal to <tt>key</tt>.
	     * @param key the key
	     * @return the smallest key in the symbol table greater than or equal to <tt>key</tt>
	     * @throws NoSuchElementException if there is no such key
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public Integer ceiling(Integer key) {
	        if (key == null) throw new NullPointerException("argument to ceiling() is null");
	        if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
	        Node x = ceiling(root, key);
	        if (x == null) return null;
	        else           return x.key;  
	    }

	    // the smallest key in the subtree rooted at x greater than or equal to the given key
	    private Node ceiling(Node x, Integer key) {  
	        if (x == null) return null;
	        int cmp = key.compareTo(x.key);
	        if (cmp == 0) return x;
	        if (cmp > 0)  return ceiling(x.right, key);
	        Node t = ceiling(x.left, key);
	        if (t != null) return t; 
	        else           return x;
	    }

	    /**
	     * Return the kth smallest key in the symbol table.
	     * @param k the order statistic
	     * @return the kth smallest key in the symbol table
	     * @throws IllegalArgumentException unless <tt>k</tt> is between 0 and
	     *     <em>N</em> &minus; 1
	     */
	    public Integer select(int k) {
	        if (k < 0 || k >= size()) throw new IllegalArgumentException();
	        Node x = select(root, k);
	        return x.key;
	    }

	    // the key of rank k in the subtree rooted at x
	    private Node select(Node x, int k) {
	        // assert x != null;
	        // assert k >= 0 && k < size(x);
	        int t = size(x.left); 
	        if      (t > k) return select(x.left,  k); 
	        else if (t < k) return select(x.right, k-t-1); 
	        else            return x; 
	    } 

	    /**
	     * Return the number of keys in the symbol table strictly less than <tt>key</tt>.
	     * @param key the key
	     * @return the number of keys in the symbol table strictly less than <tt>key</tt>
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public int rank(Integer key) {
	        if (key == null) throw new NullPointerException("argument to rank() is null");
	        return rank(key, root);
	    } 

	    // number of keys less than key in the subtree rooted at x
	    private int rank(Integer key, Node x) {
	        if (x == null) return 0; 
	        int cmp = key.compareTo(x.key); 
	        if      (cmp < 0) return rank(key, x.left); 
	        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
	        else              return size(x.left); 
	    } 

	   /***************************************************************************
	    *  Range count and range search.
	    ***************************************************************************/

//	    /**
//	     * Returns all keys in the symbol table as an <tt>Iterable</tt>.
//	     * To iterate over all of the keys in the symbol table named <tt>st</tt>,
//	     * use the foreach notation: <tt>for (Integer key : st.keys())</tt>.
//	     * @return all keys in the sybol table as an <tt>Iterable</tt>
//	     */
//	    public Iterable<Integer> keys() {
//	        if (isEmpty()) return new Queue<Integer>();
//	        return keys(min(), max());
//	    }
//
//	    /**
//	     * Returns all keys in the symbol table in the given range,
//	     * as an <tt>Iterable</tt>.
//	     * @return all keys in the sybol table between <tt>lo</tt> 
//	     *    (inclusive) and <tt>hi</tt> (exclusive) as an <tt>Iterable</tt>
//	     * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
//	     *    is <tt>null</tt>
//	     */
//	    public Iterable<Integer> keys(Integer lo, Integer hi) {
//	        if (lo == null) throw new NullPointerException("first argument to keys() is null");
//	        if (hi == null) throw new NullPointerException("second argument to keys() is null");
//
//	        Queue<Integer> queue = new Queue<Integer>();
//	        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
//	        keys(root, queue, lo, hi);
//	        return queue;
//	    } 
//
//	    // add the keys between lo and hi in the subtree rooted at x
//	    // to the queue
//	    private void keys(Node x, Queue<Integer> queue, Integer lo, Integer hi) { 
//	        if (x == null) return; 
//	        int cmplo = lo.compareTo(x.key); 
//	        int cmphi = hi.compareTo(x.key); 
//	        if (cmplo < 0) keys(x.left, queue, lo, hi); 
//	        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
//	        if (cmphi > 0) keys(x.right, queue, lo, hi); 
//	    } 

	    /**
	     * Returns the number of keys in the symbol table in the given range.
	     * @return the number of keys in the sybol table between <tt>lo</tt> 
	     *    (inclusive) and <tt>hi</tt> (exclusive)
	     * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
	     *    is <tt>null</tt>
	     */
	    public int size(Integer lo, Integer hi) {
	        if (lo == null) throw new NullPointerException("first argument to size() is null");
	        if (hi == null) throw new NullPointerException("second argument to size() is null");

	        if (lo.compareTo(hi) > 0) return 0;
	        if (contains(hi)) return rank(hi) - rank(lo) + 1;
	        else              return rank(hi) - rank(lo);
	    }


	   
	    // does this binary tree satisfy symmetric order?
	    // Note: this test also ensures that data structure is a binary tree since order is strict
	    private boolean isBST() {
	        return isBST(root, null, null);
	    }

	    // is the tree rooted at x a BST with all keys strictly between min and max
	    // (if min or max is null, treat as empty constraint)
	    // Credit: Bob Dondero's elegant solution
	    private boolean isBST(Node x, Integer min, Integer max) {
	        if (x == null) return true;
	        if (min != null && x.key.compareTo(min) <= 0) return false;
	        if (max != null && x.key.compareTo(max) >= 0) return false;
	        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
	    } 

	    // are the size fields correct?
	    private boolean isSizeConsistent() { return isSizeConsistent(root); }
	    private boolean isSizeConsistent(Node x) {
	        if (x == null) return true;
	        if (x.N != size(x.left) + size(x.right) + 1) return false;
	        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	    } 

//	    // check that ranks are consistent
//	    private boolean isRankConsistent() {
//	        for (int i = 0; i < size(); i++)
//	            if (i != rank(select(i))) return false;
//	        for (Integer key : keys())
//	            if (key.compareTo(select(rank(key))) != 0) return false;
//	        return true;
//	    }

	    // Does the tree have no red right links, and at most one (left)
	    // red links in a row on any path?
	    private boolean is23() { return is23(root); }
	    private boolean is23(Node x) {
	        if (x == null) return true;
	        if (isRed(x.right)) return false;
	        if (x != root && isRed(x) && isRed(x.left))
	            return false;
	        return is23(x.left) && is23(x.right);
	    } 

	    // do all paths from root to leaf have same number of black edges?
	    private boolean isBalanced() { 
	        int black = 0;     // number of black links on path from root to min
	        Node x = root;
	        while (x != null) {
	            if (!isRed(x)) black++;
	            x = x.left;
	        }
	        return isBalanced(root, black);
	    }

	    // does every path from the root to a leaf have the given number of black links?
	    private boolean isBalanced(Node x, int black) {
	        if (x == null) return black == 0;
	        if (!isRed(x)) black--;
	        return isBalanced(x.left, black) && isBalanced(x.right, black);
	    } 


	   
	}

}
