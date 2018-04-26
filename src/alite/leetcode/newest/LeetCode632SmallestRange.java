package alite.leetcode.newest;
/**
 * LeetCode 632 - Smallest Range

https://leetcode.com/problems/smallest-range
You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the klists.
We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
Example 1:
Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
Output: [20,24]
Explanation: 
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].
Note:
The given list may contain duplicates, so ascending order means >= here.
1 <= k <= 3500
-105 <= value of elements <= 105.
For Java users, please note that the input type has been changed to List<List<Integer>>. And after you reset the code template, you'll see this point.


https://leetcode.com/articles/smallest-range/
X.
https://discuss.leetcode.com/topic/94445/java-code-using-priorityqueue-similar-to-merge-k-array
Image you are merging k sorted array using a heap. Then everytime you pop the smallest element out and add the next element of that array to the heap. By keep doing this, you will have the smallest range.
public int[] smallestRange(int[][] nums) {
  PriorityQueue<Element> pq = new PriorityQueue<Element>(new Comparator<Element>() {
   public int compare(Element a, Element b) {
    return a.val - b.val;
   }
  });
  int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
  for (int i = 0; i < nums.length; i++) {
   Element e = new Element(i, 0, nums[i][0]);
   pq.offer(e);
   max = Math.max(max, nums[i][0]);
  }
  int range = Integer.MAX_VALUE;
  int start = -1, end = -1;
  while (pq.size() == nums.length) {

   Element curr = pq.poll();
   if (max - curr.val < range) {
    range = max - curr.val;
    start = curr.val;
    end = max;
   }
   if (curr.idx + 1 < nums[curr.row].length) {
    curr.idx = curr.idx + 1;
    curr.val = nums[curr.row][curr.idx];
    pq.offer(curr);
    if (curr.val > max) {
     max = curr.val;
    }
   }
  }

  return new int[] { start, end };
 }

 class Element {
  int val;
  int idx;
  int row;

  public Element(int r, int i, int v) {
   val = v;
   idx = i;
   row = r;
  }
 }
http://www.geeksforgeeks.org/find-smallest-range-containing-elements-from-k-lists/
Create a min heap of size k and insert first elements of all k lists into the heap.
Maintain two variables min and max to store minimum and maximum values present in the heap at any point. Note min will always contain value of the root of the heap.
Repeat following steps
Get minimum element from heap (minimum is always at root) and compute the range.
Replace heap root with next element of the list from which the min element is extracted. After replacing the root, heapify the tree. Update max if next element is greater. If the list doesn’t have any more elements, break the loop.
https://leetcode.com/articles/smallest-range/
    public int[] smallestRange(int[][] nums) {
        int minx = 0, miny = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        PriorityQueue < Integer > min_queue = new PriorityQueue < Integer > ((i, j) -> nums[i][next[i]] - nums[j][next[j]]);
        for (int i = 0; i < nums.length; i++) {
            min_queue.offer(i);
            max = Math.max(max, nums[i][0]);
        }
        for (int i = 0; i < nums.length && flag; i++) {
            for (int j = 0; j < nums[i].length && flag; j++) {
                int min_i = min_queue.poll();
                if (miny - minx > max - nums[min_i][next[min_i]]) {
                    minx = nums[min_i][next[min_i]];
                    miny = max;
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length) {
                    flag = false;
                    break;
                }
                min_queue.offer(min_i);
                max = Math.max(max, nums[min_i][next[min_i]]);
            }
        }
        return new int[] { minx, miny};
    }
X. Using minQueue and maxQueue
http://prismoskills.appspot.com/lessons/Algorithms/Chapter_07_-_Shortest_range_with_all_lists.jsp
DateLineComparator cmp = new DateLineComparator ();
DateLineReverseComparator revCmp = new DateLineReverseComparator();

// Create min-Heap and max-Heap by using PriorityQueue
PriorityQueue <DataAndLineNum> minPQ = new PriorityQueue<DataAndLineNum>(numLists, cmp);
PriorityQueue <DataAndLineNum> maxPQ = new PriorityQueue<DataAndLineNum>(numLists, revCmp);

// Put first element of each list into min-Heap and max-Heap
// Each element is converted from normal integer to wrapper class DataAndLineNum before inserting    
for (int i=0; i<numLists; i++)
{
  ArrayList<Integer> lst = (ArrayList<Integer>) lists[i];
  
  DataAndLineNum info = new DataAndLineNum();
  info.data = lst.get(0);
  info.lineNum = i;
  
  minPQ.add(info);
  maxPQ.add(info);
}


// Heaps are initialized with first element of each list.
// Now, remove one element from min-Heap and remove corresponding element from max-Heap
// From the removed element, get the line number
// From the line-number, go directly to the list and take the next element from it.

int minDiff = 0;

while (minPQ.size() > 0)
{
  if (minPQ.size() == numLists)
  {
    int diff = maxPQ.peek().data - minPQ.peek().data;
    if (minDiff == 0 || diff < minDiff)
    {
      minDiff = diff;
      printCurrentPQ (minPQ, minDiff);
    }
  }
    
  DataAndLineNum smallest = minPQ.poll(); // remove smallest from min-Heap
  maxPQ.remove(smallest);                 // remove same element from max-Heap
  
  
  // get next number from the line whose element is removed above
  int lineNum = smallest.lineNum;
  ArrayList<Integer> list = (ArrayList<Integer>) lists[lineNum];
  list.remove(0);
  
  if (list.size() > 0)
  {
    smallest.data = list.get(0); // re-use the wrapper object smallest
    minPQ.add(smallest);
    maxPQ.add(smallest);
  }
}

X.
https://discuss.leetcode.com/topic/94589/java-8-sliding-window
The idea is to sort all the elements in the k lists and run a sliding window over the sorted list, to find the minimum window that satisfies the criteria of having atleast one element from each list.
public static int[] smallestRange(List<List<Integer>> nums) {
        List<int[]> list = IntStream.range(0, nums.size())
                .mapToObj( i -> nums.get(i).stream().map(x -> new int[]{x, i}))
                .flatMap(y -> y)
                .sorted(Comparator.comparingInt(p -> p[0])).collect(toList());
        int[] counts = new int[nums.size()];
        BitSet set = new BitSet(nums.size());
        int start = -1;
        int[] res = new int[2];
        for(int i = 0; i < list.size(); i++) {
            int[] p = list.get(i);
            set.set(p[1]);
            counts[p[1]] += 1;
            if(start == -1) { start = 0; }
            while(start < i && counts[list.get(start)[1]] > 1) {
                counts[list.get(start)[1]]--;
                start++;
            }
            if(set.cardinality() == nums.size()) {
                if( (res[0] == 0 && res[1] == 0) || (list.get(i)[0] - list.get(start)[0]) < res[1] - res[0]) {
                    res[0] = list.get(start)[0];
                    res[1] = list.get(i)[0];
                }
            }
        }
        return res;
    }



X.
http://www.geeksforgeeks.org/find-smallest-range-containing-elements-from-k-lists/
The idea is to maintain pointers to every list using array ptr[k].Below are the steps :
Initially the index of every list is 0,therefore initialize every element of ptr[0..k] to 0;
Repeat the following steps until atleast one list exhausts :
Now find the minimum and maximum value among the current elements of all the list pointed by the ptr[0…k] array.
Now update the minrange if current (max-min) is less than minrange.
increment the pointer pointing to current minimum element.
Time complexity : O(n2 k)

// array for storing the current index of list i

int ptr[501];

 

// This function takes an k sorted lists in the form of

// 2D array as an argument. It finds out smallest range

// that includes elements from each of the k lists.

void findSmallestRange(int arr[][N], int n, int k)

{

      int i,minval,maxval,minrange,minel,maxel,flag,minind;

       

      //initializing to 0 index;

      for(i = 0;i <= k;i++) 

        ptr[i] = 0;

 

      minrange = INT_MAX;

       

      while(1)       

      {

          // for mainting the index of list containing the minimum element

          minind = -1; 

          minval = INT_MAX;

          maxval = INT_MIN;

          flag = 0;

 

          //iterating over all the list

          for(i = 0;i < k;i++)   

          {    

              // if every element of list[i] is traversed then break the loop

              if(ptr[i] == n)   

              {

                flag = 1;

                break;

              }

              // find minimum value among all the list elements pointing by the ptr[] array 

              if(ptr[i] < n && arr[i][ptr[i]] < minval)  

              {

                  minind=i;  // update the index of the list

                  minval=arr[i][ptr[i]];

              }

              // find maximum value among all the list elements pointing by the ptr[] array

              if(ptr[i] < n && arr[i][ptr[i]] > maxval)    

              {

                  maxval = arr[i][ptr[i]];

              }

          }

 

          //if any list exhaust we will not get any better answer ,so break the while loop

          if(flag) 

            break;

 

          ptr[minind]++;

 

          //updating the minrange

          if((maxval-minval) < minrange)  

          {

              minel = minval;

              maxel = maxval;

              minrange = maxel - minel;

          }

      }

       

      printf("The smallest range is [%d , %d]\n",minel,maxel);

}


The naive approach is to consider every pair of elements, nums[i][j]nums[i][j] and nums[k][l]nums[k][l] from amongst the given lists and consider the range formed by these elements. For every range currently considered, we can traverse over all the lists to find if atleast one element from these lists can be included in the current range. If so, we store the end-points of the current range and compare it with the previous minimum range found, if any, satisfying the required criteria, to find the smaller range from among them.
Time complexity : O(n^3)O(n
​3
​​ ). Considering every possible range(element pair) requires O(n^2)O(n
​2
​​ ) time. For each range considered, we need to traverse over all the elements of the given lists in the worst case requiring another O(n)O(n) time. Here, nn refers to the total number of elements in the given lists.



http://algorithms.tutorialhorizon.com/shortest-range-in-k-sorted-lists/
 * @author het
 *
 */
public class LeetCode632SmallestRange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
