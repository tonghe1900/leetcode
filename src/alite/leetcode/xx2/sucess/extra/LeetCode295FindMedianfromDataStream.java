package alite.leetcode.xx2.sucess.extra;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

///**
// * LeetCode 295 - Find Median from Data Stream
// * 
// * 
//Find Median from Data Stream | LeetCode OJ
//Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
//Examples: 
//[2,3,4] , the median is 3
//[2,3], the median is (2 + 3) / 2 = 2.5 
//Design a data structure that supports the following two operations: 
//void addNum(int num) - Add a integer number from the data stream to the data structure.
//double findMedian() - Return the median of all elements so far.
//For example:
//add(1)  add(2)  findMedian() -> 1.5  add(3)   findMedian() -> 2
//https://leetcode.com/discuss/64852/java-python-two-heap-solution-o-log-n-add-o-1-find
//The invariant of the algorithm is two heaps, small and large, each represent half of the current list. The length of smaller half is kept to be n / 2 at all time and the length of the larger half is either n / 2 or n / 2 + 1 depend on n's parity.
//This way we only need to peek the two heaps' top number to calculate median.
//Any time before we add a new number, there are two scenarios, (total n numbers, k = n / 2):
//(1) length of (small, large) == (k, k)
//(2) length of (small, large) == (k, k + 1)
//After adding the number, total (n + 1) numbers, they will become:
//(1) length of (small, large) == (k, k + 1)
//(2) length of (small, large) == (k + 1, k + 1)
//Here we take the first scenario for example, we know the large will gain one more item and small will 
//remain the same size, but we cannot just push the item into large. 
//What we should do is we push the new number into small and 
//pop the maximum item from small then push it into large 
//(all the pop and push here are heappop and heappush). 
//By doing this kind of operations for the two scenarios we can keep our invariant.
//Therefore to add a number, we have 3 O(log n) heap operations. Luckily the heapq provided us a function "heappushpop" which saves some time by combine two into one. The document says:
//Push item on the heap, then pop and return the smallest item from the heap. The combined action runs more efficiently than heappush() followed by a separate call to heappop().
//Alltogether, the add operation is O(logn), The findMedian operation is O(1).
//Note that the heapq in python is a min heap, thus we need to invert the values in the smaller half to mimic a "max heap".
//A further observation is that the two scenarios take turns when adding numbers, thus it is possible to combine the two into one. 
//For this please see stefan's post
//private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
//private PriorityQueue<Integer> large = new PriorityQueue<>();
//private boolean even = true;
//
//public double findMedian() {
//    if (even)
//        return (small.peek() + large.peek()) / 2.0;
//    else
//        return small.peek();
//}
//
//public void addNum(int num) {
//    if (even) {
//        large.offer(num);
//        small.offer(large.poll());
//    } else {
//        small.offer(num);
//        large.offer(small.poll());
//    }
//    even = !even;
//}
//http://www.programcreek.com/2015/01/leetcode-find-median-from-data-stream-java/
//    PriorityQueue<Integer> maxHeap;//lower half
//    PriorityQueue<Integer> minHeap;//higher half
// 
//    public MedianFinder(){
//        maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
//        minHeap = new PriorityQueue<Integer>();
//    }
// 
//    // Adds a number into the data structure.
//    public void addNum(int num) {
//        maxHeap.offer(num);
//        minHeap.offer(maxHeap.poll());
// 
//        if(maxHeap.size() < minHeap.size()){
//            maxHeap.offer(minHeap.poll());
//        }
//    }
// 
//    // Returns the median of current data stream
//    public double findMedian() {
//        if(maxHeap.size()==minHeap.size()){
//            return (double)(maxHeap.peek()+(minHeap.peek()))/2;
//        }else{
//            return maxHeap.peek();
//        }
//    }
//http://buttercola.blogspot.com/2015/12/leetcode-find-median-from-data-stream.html
//Use two heaps. The maxHeap stores the number which is less than the current number. The minHeap stores the number which is greter than the current number. 
//We also need to keep the two heaps balanced in size. 
//
//For the method findMedian(), we need to check if the two heaps have the same size. If yes, there must be even number of elements so far, so the median is the average of the top of the minHeap and the maxHeap. If not, i.e. odd number of elements so far, the median is the top of the heap which one more element. 
//
//    private PriorityQueue<Integer> leftPQ = 
//
//        new PriorityQueue<>(Collections.reverseOrder());
//
//    private PriorityQueue<Integer> rightPQ = new PriorityQueue<>();
//
//     
//
//    // Adds a number into the data structure.
//
//    public void addNum(int num) {
//
//        if (leftPQ.isEmpty() || num <= leftPQ.peek()) {
//
//            leftPQ.offer(num);
//
//        } else {
//
//            rightPQ.offer(num);
//
//        }
//
//         
//
//        // Rebalance the pqs
//
//        if (leftPQ.size() - rightPQ.size() > 1) {
//
//            rightPQ.offer(leftPQ.poll());
//
//        } else if (rightPQ.size() - leftPQ.size() > 1) {
//
//            leftPQ.offer(rightPQ.poll());
//
//        }
//
//    }
//
//
//    // Returns the median of current data stream
//
//    public double findMedian() {
//
//        if (leftPQ.isEmpty() && rightPQ.isEmpty()) {
//
//            throw new NoSuchElementException(); // if the queue is empty
//
//        }
//
//         
//
//        if(leftPQ.isEmpty()) {
//
//            return (double) rightPQ.peek();
//
//        } else if (rightPQ.isEmpty()) {
//
//            return (double) leftPQ.peek();
//
//        } else if (leftPQ.size() > rightPQ.size()) {
//
//            return (double) leftPQ.peek();
//
//        } else if (rightPQ.size() > leftPQ.size()) {
//
//            return (double) rightPQ.peek();
//
//        } else {
//
//            return (double) (leftPQ.peek() + rightPQ.peek()) / 2;
//
//        }
//
//    }
//https://segmentfault.com/a/1190000003709954
//维护一个最大堆，一个最小堆。最大堆存的是到目前为止较小的那一半数，最小堆存的是到目前为止较大的那一半数，这样中位数只有可能是堆顶或者堆顶两个数的均值。而维护两个堆的技巧在于判断堆顶数和新来的数的大小关系，还有两个堆的大小关系。我们将新数加入堆后，要保证两个堆的大小之差不超过1。先判断堆顶数和新数的大小关系，有如下三种情况：最小堆堆顶小于新数时，说明新数在所有数的上半部分。最小堆堆顶大于新数，但最大堆堆顶小于新数时，说明新数将处在最小堆堆顶或最大堆堆顶，也就是一半的位置。最大堆堆顶大于新数时，说明新数将处在所有数的下半部分。再判断两个堆的大小关系，如果新数不在中间，那目标堆不大于另一个堆时，将新数加入目标堆，否则将目标堆的堆顶加入另一个堆，再把新数加入目标堆。如果新数在中间，那加到大小较小的那个堆就行了（一样大的话随便，代码中是加入最大堆）。这样，每次新加进来一个数以后，如果两个堆一样大，则中位数是两个堆顶的均值，否则中位数是较大的那个堆的堆顶。
//Java中实现最大堆是在初始化优先队列时加入一个自定义的Comparator，默认初始堆大小是11。Comparator实现compare方法时，用arg1 - arg0来表示大的值在前面
//    
//    PriorityQueue<Integer> maxheap;
//    PriorityQueue<Integer> minheap;
//    
//    public MedianFinder(){
//        // 新建最大堆
//        maxheap = new PriorityQueue<Integer>(11, new Comparator<Integer>(){
//            public int compare(Integer i1, Integer i2){
//                return i2 - i1;
//            }
//        });
//        // 新建最小堆
//        minheap = new PriorityQueue<Integer>();
//    }
//
//    // Adds a number into the data structure.
//    public void addNum(int num) {
//        // 如果最大堆为空，或者该数小于最大堆堆顶，则加入最大堆
//        if(maxheap.size() == 0 || num <= maxheap.peek()){
//            // 如果最大堆大小超过最小堆，则要平衡一下
//            if(maxheap.size() > minheap.size()){
//                minheap.offer(maxheap.poll());
//            }
//            maxheap.offer(num);
//        // 数字大于最小堆堆顶，加入最小堆的情况
//        } else if (minheap.size() == 0 || num > minheap.peek()){
//            if(minheap.size() > maxheap.size()){
//                maxheap.offer(minheap.poll());
//            }
//            minheap.offer(num);
//        // 数字在两个堆顶之间的情况
//        } else {
//            if(maxheap.size() <= minheap.size()){
//                maxheap.offer(num);
//            } else {
//                minheap.offer(num);
//            }
//        }
//    }
//
//    // Returns the median of current data stream
//    public double findMedian() {
//        // 返回大小较大的那个堆堆顶，如果大小一样说明是偶数个，则返回堆顶均值
//        if(maxheap.size() > minheap.size()){
//            return maxheap.peek();
//        } else if (maxheap.size() < minheap.size()){
//            return minheap.peek();
//        } else if (maxheap.isEmpty() && minheap.isEmpty()){
//            return 0;
//        } else {
//            return (maxheap.peek() + minheap.peek()) / 2.0;
//        }
//    }
//https://leetcode.com/discuss/65107/share-my-java-solution-logn-to-insert-o-1-to-query
//class MedianFinder {
//    // max queue is always larger or equal to min queue
//    PriorityQueue<Integer> min = new PriorityQueue();
//    PriorityQueue<Integer> max = new PriorityQueue(1000, Collections.reverseOrder());
//    // Adds a number into the data structure.
//    public void addNum(int num) {
//        max.offer(num);
//        min.offer(max.poll());
//        if (max.size() < min.size()){
//            max.offer(min.poll());
//        }
//    }
//
//    // Returns the median of current data stream
//    public double findMedian() {
//        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
//        else return max.peek();
//    }
//};
//http://www.zrzahid.com/median-of-a-stream-of-integer/
//So, the idea is to use some data structure that will maintain two lists of elements such that first list is less then or equal to current median and the second list is greater then or equal to the current median. If both list are of same size then the average of the top of the two lists is the median. Otherwise, median is top of the bigger list. Question is what data structure to use?
//Using self-balancing BST
//At every node of a AVL BST, maintain number of elements in the subtree rooted at that node. We can use a node as root of simple binary tree, whose left child is self balancing BST with elements less than root and right child is self balancing BST with elements greater than root. The root always holds current median. I discussed the implementation of an AVL tree in my previous post here.
//Using Heap
//public static int getMedian(final int current, final int med, final PriorityQueue<Integer> left, final PriorityQueue<Integer> right) {
// final int balance = left.size() - right.size();
//    int median = med;
//
//    // both heaps are of equal size.
//    if (balance == 0) {
//        // need to insert in left
//        if (current < median) {
//            left.offer(current);
//            median = left.peek();
//        }
//        // need to insert in right
//        else {
//            right.offer(current);
//            median = right.peek();
//        }
//    }
//    // left heap is larger
//    else if (balance > 0) {
//        // need to insert in left
//        if (current < median) {
//            right.offer(left.poll());
//            left.offer(current);
//        }
//        // need to insert in right
//        else {
//            right.offer(current);
//        }
//
//        median = (left.peek() + right.peek()) / 2;
//    }
//    // right heap is larger
//    else if (balance < 0) {
//        // need to insert in left
//        if (current < median) {
//            left.offer(current);
//        }
//        // need to insert in right
//        else {
//            left.offer(right.poll());
//            right.offer(current);
//        }
//
//        median = (left.peek() + right.peek()) / 2;
//    }
//
//    return median;
//}
//
//public static int getStreamMedian(final int[] stream) {
//    int median = 0;
//    final PriorityQueue<Integer> left = new PriorityQueue<Integer>(16, Collections.reverseOrder());
//    final PriorityQueue<Integer> right = new PriorityQueue<Integer>(16);
//
//    for (int i = 0; i < stream.length; i++) {
//        median = getMedian(stream[i], median, left, right);
//    }
//    return median;
//}
//
//Java code: http://blog.csdn.net/xudli/article/details/46389077
//http://blog.welkinlan.com/2015/06/26/data-stream-median-lintcode-java/
//Find median by using two heaps
//
//LintCode
//
//   c
//X. Use Binary Search Tree
//https://leetcode.com/discuss/65631/just-user-binary-sort-tree-java-20ms
//private Node root=null;
//// Adds a number into the data structure.
//public void addNum(int num) {
//    if(root==null){
//        root=new Node(num);
//    }else{
//        addNum(root,num);
//    }
//}
//private void addNum(Node node,int value){
//    node.nodeCnt++;
//    if(value<=node.value){
//        if(node.leftTree==null){
//            Node t=new Node(value);
//            node.leftTree=t;
//        }else{
//            addNum(node.leftTree,value);
//        }
//    }else{
//        if(node.rightTree==null){
//            Node t=new Node(value);
//            node.rightTree=t;
//        }else{
//            addNum(node.rightTree,value);
//        }
//    }
//}
//
//// Returns the median of current data stream
//public double findMedian() {
//    return findMedian(root,0,0);
//}
//
//public double findMedian(Node node ,int leftCnt,int rightCnt){
//    int nodeLeftCnt=node.leftTree==null?0:node.leftTree.nodeCnt;
//    int nodeRightCnt=node.rightTree==null?0:node.rightTree.nodeCnt;
//    int diff=nodeLeftCnt+leftCnt-nodeRightCnt-rightCnt;
//    if(diff==0) return node.value;
//    if(diff==-1) return (node.value+min(node.rightTree)+0.0)/2;
//    if(diff==1) return (node.value+max(node.leftTree)+0.0)/2;
//    if(diff<-1) return findMedian(node.rightTree,leftCnt+node.nodeCnt-node.rightTree.nodeCnt,rightCnt);
//    return findMedian(node.leftTree,leftCnt,rightCnt+node.nodeCnt-node.leftTree.nodeCnt);
//}
//
//private int min(Node node){
//    while (node.leftTree!=null) node=node.leftTree;
//    return node.value;
//}
//private int max(Node node){
//    while (node.rightTree!=null) node=node.rightTree;
//    return node.value;
//}
//
//private static class Node{
//    public Node(int value) {
//        this.value = value;
//        this.nodeCnt=1;
//    }
//    public int value;
//    public int nodeCnt;
//    public Node leftTree;
//    public Node rightTree;
//}
//
//Extended
//Q：如果要求第n/10个数字该怎么做？
//A：改变两个堆的大小比例，当求n/2即中位数时，两个堆是一样大的。而n/10时，说明有n/10个数小于目标数，9n/10个数大于目标数。所以我们保证最小堆是最大堆的9倍大小就行了。
//https://github.com/careercup/CtCI-6th-Edition/tree/master/Java/Ch%2017.%20Hard/Q17_20_Continuous_Median
//private static Comparator<Integer> maxHeapComparator;
//private static Comparator<Integer> minHeapComparator;
//private static PriorityQueue<Integer> maxHeap;
//private static PriorityQueue<Integer> minHeap;
//
//public static void addNewNumber(int randomNumber) {
// /* Note: addNewNumber maintains a condition that maxHeap.size() >= minHeap.size() */
// if (maxHeap.size() == minHeap.size()) {
//  if ((minHeap.peek() != null) &&
//    randomNumber > minHeap.peek()) {
//   maxHeap.offer(minHeap.poll());
//   minHeap.offer(randomNumber);
//  } else {
//   maxHeap.offer(randomNumber);
//  }
// }
// else {
//  if(randomNumber < maxHeap.peek()){
//   minHeap.offer(maxHeap.poll());
//   maxHeap.offer(randomNumber);
//  }
//  else {
//   minHeap.offer(randomNumber);
//  }
// }
//}
//
//public static double getMedian() {
// /* maxHeap is always at least as big as minHeap. So if maxHeap is empty, then minHeap is also. */  
// if (maxHeap.isEmpty()) {
//  return 0;
// }
// if (maxHeap.size() == minHeap.size()) {
//  return ((double)minHeap.peek() + (double) maxHeap.peek()) / 2;
// } else {
//  /* If maxHeap and minHeap are of different sizes, then maxHeap must have one extra element. Return maxHeap�s top element.*/   
//  return maxHeap.peek();
// }
//}
//
//public static void addNewNumberAndPrintMedian(int randomNumber) {
// addNewNumber(randomNumber);
// System.out.println("Random Number = " + randomNumber);
// printMinHeapAndMaxHeap();
// System.out.println("\nMedian = " + getMedian() + "\n");
//}
//
//public static void printMinHeapAndMaxHeap(){
// Integer[] minHeapArray = minHeap.toArray(
//   new Integer[minHeap.size()]);
// Integer[] maxHeapArray = maxHeap.toArray(
//   new Integer[maxHeap.size()]);
//
// Arrays.sort(minHeapArray, maxHeapComparator);
// Arrays.sort(maxHeapArray, maxHeapComparator);
// System.out.print("MinHeap =");
// for (int i = minHeapArray.length - 1; i >= 0 ; i--){
//  System.out.print(" " + minHeapArray[i]);
// }
// System.out.print("\nMaxHeap =");
// for (int i = 0; i < maxHeapArray.length; i++){
//  System.out.print(" " + maxHeapArray[i]);
// }
//}
// * 
// * 
// * @author het
// *
// */
public class LeetCode295FindMedianfromDataStream {
	 public int[] medianII(int[] nums) {
       // write your code here
       if(nums.length <= 1) return nums;
       int[] res = new int[nums.length];
       PriorityQueue<Integer> minheap = new PriorityQueue<Integer>();
       PriorityQueue<Integer> maxheap = new PriorityQueue<Integer>(11, new Comparator<Integer>(){
           public int compare(Integer arg0, Integer arg1) {
               return arg1 - arg0;
           }
       });
       // 将前两个元素先加入堆中
       minheap.offer(Math.max(nums[0], nums[1]));
       maxheap.offer(Math.min(nums[0], nums[1]));
       res[0] = res[1] = Math.min(nums[0], nums[1]);
       for(int i = 2; i < nums.length; i++){
           int mintop = minheap.peek();
           int maxtop = maxheap.peek();
           int curr = nums[i];
           // 新数在较小的一半中
           if (curr < maxtop){
               if (maxheap.size() <= minheap.size()){
                   maxheap.offer(curr);
               } else {
                   minheap.offer(maxheap.poll());
                   maxheap.offer(curr);
               }
           // 新数在中间
           } else if (curr >= maxtop && curr <= mintop){
               if (maxheap.size() <= minheap.size()){
                   maxheap.offer(curr);
               } else {
                   minheap.offer(curr);
               }
           // 新数在较大的一半中
           } else {
               if(minheap.size() <= maxheap.size()){
                   minheap.offer(curr);
               } else {
                   maxheap.offer(minheap.poll());
                   minheap.offer(curr);
               }
           }
           if (maxheap.size() == minheap.size()){
               res[i] = (maxheap.peek() + minheap.peek()) / 2;
           } else if (maxheap.size() > minheap.size()){
               res[i] = maxheap.peek();
           } else {
               res[i] = minheap.peek();
           }
       }
       return res;
   }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
