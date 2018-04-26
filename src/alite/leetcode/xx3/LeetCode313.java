package alite.leetcode.xx3;

import java.util.PriorityQueue;



/**
 * LeetCode 313 - Super Ugly Number

Related: LeetCode 263 - Ugly Number
LeetCode 263 + 264 Ugly Number I II
[LeetCode]Super Ugly Number | 书影博客
Write a program to find the nth super ugly number.
Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.
Note:
(1) 1 is a super ugly number for any given primes.
(2) The given numbers in primes are in ascending order.
(3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
题目大意：
编写程序寻找第n个"超级丑陋数"
超级丑陋数是指只包含给定的k个质因子的正数。例如，给定长度为4的质数序列primes = [2, 7, 13, 19]，前12个超级丑陋数序列为：[1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32]
注意：
(1) 1 被认为是超级丑陋数，无论给定怎样的质数列表.
(2) 给定的质数列表以升序排列.
(3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
https://leetcode.com/discuss/81411/java-three-methods-23ms-58ms-with-heap-performance-explained
https://discuss.leetcode.com/topic/30999/108ms-easy-to-understand-java-solution
public int nthSuperUglyNumber(int n, int[] primes) {
    int [] res = new int[n];
    res[0] = 1;
    int [] cur = new int[primes.length];
    
    for(int i = 1; i < n; i++){
        res[i] = Integer.MAX_VALUE;
        for(int j = 0; j < primes.length; j++){
            if (primes[j] * res[cur[j]] == res[i-1]) {
                cur[j]++;
            }
            res[i] = Math.min(res[i], primes[j]*res[cur[j]]);
        }
    }
    return res[n-1];
}

Basic idea is same as ugly number II, new ugly number is generated by multiplying a prime with previous generated ugly number. One catch is need to remove duplicate
Let's start with the common solution from ugly number II 36 ms, Theoretically O(kN)
the time complexity would be O(m*n) here where m stands for the length of primes.
public int nthSuperUglyNumberI(int n, int[] primes) {
    int[] ugly = new int[n];
    int[] idx = new int[primes.length];

    ugly[0] = 1;
    for (int i = 1; i < n; i++) {
        //find next
        ugly[i] = Integer.MAX_VALUE;
        for (int j = 0; j < primes.length; j++)
            ugly[i] = Math.min(ugly[i], primes[j] * ugly[idx[j]]);
        
        //slip duplicate
        for (int j = 0; j < primes.length; j++) {
            while (primes[j] * ugly[idx[j]] <= ugly[i]) idx[j]++;
        }
    }

    return ugly[n - 1];
}
If you look at the above solution, it has redundant multiplication can be avoided, and also two for loops can be consolidated into one. This trade-off space for speed. 23 ms, Theoretically O(kN)
public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] idx = new int[primes.length];
        int[] val = new int[primes.length];
        Arrays.fill(val, 1);

        int next = 1;
        for (int i = 0; i < n; i++) {
            ugly[i] = next;
            
            next = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++) {
                //skip duplicate and avoid extra multiplication
                if (val[j] == ugly[i]) val[j] = ugly[idx[j]++] * primes[j];
                //find next ugly number
                next = Math.min(next, val[j]);
            }
        }

        return ugly[n - 1];
    }
Can we do better? Theoretically yes, by keep the one candidates for each prime in a heap, it can improve the theoretical bound to O( log(k)N ), but in reality it's 58 ms. I think it's the result of using higher level object instead of primitive. Can be improved by writing an index heap (http://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html)
public int nthSuperUglyNumberHeap(int n, int[] primes) {
    int[] ugly = new int[n];

    PriorityQueue<Num> pq = new PriorityQueue<>();
    for (int i = 0; i < primes.length; i++) pq.add(new Num(primes[i], 1, primes[i]));
    ugly[0] = 1;

    for (int i = 1; i < n; i++) {
        ugly[i] = pq.peek().val;
        while (pq.peek().val == ugly[i]) {
            Num nxt = pq.poll();
            pq.add(new Num(nxt.p * ugly[nxt.idx], nxt.idx + 1, nxt.p));
        }
    }

    return ugly[n - 1];
}

private class Num implements Comparable<Num> {
    int val;
    int idx;
    int p;

    public Num(int val, int idx, int p) {
        this.val = val;
        this.idx = idx;
        this.p = p;
    }

    @Override
    public int compareTo(Num that) {
        return this.val - that.val;
    }
}

http://www.cnblogs.com/Liok3187/p/5016076.html
和Ugly Number II一样的思路。
动态规划，ugly number肯定是之前的ugly number乘以primes中的其中一个数得到的。
结果存在dp数组中，每一个dp中的数都要乘以primes中的数，寻找所有的结果，也就是说primes中的每一个数都需要一个变量记录位置。
举例来说primes : [2, 5, 7]。
一开始2, 5, 7都指向0的位置。
每一轮循环都用2, 5, 7与当前位置的数相乘，把最小的放进dp数组中。


 1 public static int nthSuperUglyNumber(int n, int[] primes) {
 2     int[] dp = new int[n + 1], prime = new int[primes.length];
 3     int dpIndex = 1, minIndex = -1;
 4     dp[0] = 1;
 5     while(dpIndex <= n){
 6         int min = Integer.MAX_VALUE;
 7         for(int i = 0; i < primes.length; i++){
 8             int tmp = dp[prime[i]] * primes[i];
 9             if(tmp < min){
10                 min = tmp;
11                 minIndex = i;
12             }
13         }
14         prime[minIndex]++;
15         if(min != dp[dpIndex - 1]){
16             dp[dpIndex] = min;
17             dpIndex++;
18         }
19     }
20     return dp[n - 1];
21 }
http://leetcode0.blogspot.com/2015/12/super-ugly-number_10.html
动态规划，ugly number一定是由一个较小的ugly number乘以2，3或5得到的。
先开一个数组记录所有的ugly number。
然后开一个变量数组A，一开始都是零，指向数组的第0位。    A[i]是ugly number的index，
A[i] * primes[i] > current ugly number

每一次取Min(A[i] * primes[i])，找到这个数之后，对应的A[i]加一，指向数组的下一位。

    public int nthSuperUglyNumber(int n, int[] primes) {

        int res[]  = new int[n];

        int A[] = new int[primes.length];// the index that A[j] * prime[j] > current Ugly number

        res[0] = 1;

        for(int i =1; i<n;i++){

            int lastUgly = res[i-1];

            int minProduct = Integer.MAX_VALUE;// one possible value

            for(int j = 0 ; j< primes.length; j++){// for each prime number

                while(res[A[j]] * primes[j] <= lastUgly)// update its preceding index

                    A[j]++;

                if(res[A[j]] * primes[j] < minProduct)// find the min product

                    minProduct = res[A[j]] * primes[j];

            }

            res[i] = minProduct;

        }

        return res[n-1];


    }
http://my.oschina.net/Tsybius2014/blog/547766

找出第n个超级丑数，思路与找出第n个丑数是一样的。区别仅在与找出第n个丑数时，用三个数字记录中间变量，而在找第n个超级丑数时，用一个数组记录。

    public int nthSuperUglyNumber(int n, int[] primes) {

         

        int[] superUglyNumbers = new int[n];

        superUglyNumbers[0] = 1;

        int[] idxPrimes = new int[primes.length];

        for (int i = 0; i < idxPrimes.length; i++) {

            idxPrimes[i] = 0;

        }

         

        int counter = 1;

        while (counter < n) {

             

            int min = Integer.MAX_VALUE;

            for (int i = 0; i < primes.length; i++) {

                int temp = superUglyNumbers[idxPrimes[i]] * primes[i];

                min = min < temp ? min : temp;

            }


            for (int i = 0; i < primes.length; i++) {

                if (min == superUglyNumbers[idxPrimes[i]] * primes[i]) {

                    idxPrimes[i]++;

                }

            }

             

            superUglyNumbers[counter] = min;

            counter++;

        }

         

        return superUglyNumbers[n - 1];

    }
http://bookshadow.com/weblog/2015/12/03/leetcode-super-ugly-number/
时间复杂度O(n * k)，k为primes的长度

    public int nthSuperUglyNumber(int n, int[] primes) {
        int size = primes.length;
        int q[] = new int[n];
        int idxes[] = new int[size];
        int vals[] = new int[size];
        q[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < size; j++) {
                vals[j] = q[idxes[j]] * primes[j];
            }
            int min = findMin(vals);
            q[i] = min;
            for (int j = 0; j < size; j++) {
                if (vals[j] == min) {
                    idxes[j] += 1;
                }
            }
        }
        return q[n - 1];
    }

    public int findMin(int[] nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
        }
        return min;
X. 使用堆（优先队列）O(nlogk)
维护一个小顶堆，弹出元素时，将其乘以primes中的每一个元素然后加入堆
参考StefanPochmann的答案：https://leetcode.com/discuss/72763/python-generators-on-a-heap
https://discuss.leetcode.com/topic/30946/using-min-heap-accepted-java-and-python-code
The idea is similar to 264 Ugly Number II. The insight is that each new ugly number is generated from the previous ugly number by multiplying one of the prime. Thus we can maintain a pointer for each prime which indicates the current position of the generated ugly number list. Then there is a new ugly number from each prime, then we find the minimum one from them. Naturally the minimum one can be found by min-heap.
public int nthSuperUglyNumber(int n, int[] primes) {
 Comparator<Number> comparator = new NumberCompare();
 PriorityQueue<Number> queue = 
            new PriorityQueue<Number>(primes.length, comparator);
 for(int i = 0; i < primes.length; i ++) 
  queue.add(new Number(primes[i], 0, primes[i]));
 int[] uglyNums = new int[n];
 uglyNums[0] = 1;
 for(int i = 1; i < n; i++){
  Number min = queue.peek();
  uglyNums[i] = min.un;
  while(queue.peek().un == min.un){
   Number tmp = queue.poll();
   queue.add(new Number(uglyNums[tmp.pos + 1] * tmp.prime, tmp.pos+1, tmp.prime)); 
  }
 }
 
 return uglyNums[n-1];
}

public class Number{
 int un;
 int pos;
 int prime;
 Number(int un, int pos, int prime){
  this.un = un;
  this.pos = pos;
  this.prime = prime;
 }
}

public class NumberCompare implements Comparator<Number>{

 @Override
 public int compare(Number x, Number y) {
  // TODO Auto-generated method stub
  if (x.un > y.un)
   return 1;
  else if (x.un < y.un)
   return -1;
  else
   return 0;
 }
}


public int nthSuperUglyNumber(int n, int[] primes){
    PriorityQueue<Number> queue =
            new PriorityQueue<Number>();
    Set<Integer> uglySet = new HashSet<Integer>();
    for(int i = 0; i < primes.length; i ++) {
        queue.add(new Number(primes[i], 0, primes[i]));
        uglySet.add(primes[i]);
    }
    int[] uglyNums = new int[n];
    uglyNums[0] = 1;
    for(int i = 1; i < n; i++){
        Number min = queue.poll();
        uglyNums[i] = min.un;
        int j = min.pos+1;
        int newNum = min.prime * uglyNums[j];
        while (uglySet.contains(newNum))
             newNum = min.prime * uglyNums[++j];
        queue.add(new Number(newNum, j, min.prime));
        uglySet.add(newNum);
    }
    return uglyNums[n-1];
}

public class Number implements Comparable<Number>{
    int un;
    int pos;
    int prime;
    Number(int un, int pos, int prime){
        this.un = un;
        this.pos = pos;
        this.prime = prime;
    }
    @Override
    public int compareTo(Number x) {
        // TODO Auto-generated method stub
        return this.un > x.un ? 1 : -1;
    }
}
Are you sure this is nlog(k)? Your outer loop runs n times and your inner loop may run multiple times each time, causing several log(k) instead of just one.
Yes, you are right Stefan. The number of log(k) in the inner loop depends on the duplicates of the numbers when generating the kth number. Previously I thought the duplicate number would be negligible. But it turns out not. I checked that there are about 20M duplicates when n = 500K.
I then made this change: use a hashset to store all the used numbers in the heap, and also add the one which has not appeared in the hashset.
public int nthSuperUglyNumber(int n, int[] primes) {
    Comparator<Number> comparator = new NumberCompare();
    PriorityQueue<Number> queue =
            new PriorityQueue<Number>(primes.length, comparator);
    for(int i = 0; i < primes.length; i ++)
        queue.add(new Number(primes[i], 0, primes[i]));
    int[] uglyNums = new int[n];
    uglyNums[0] = 1;
    for(int i = 1; i < n; i++){
        Number min = queue.peek();
        uglyNums[i] = min.un;
        while(queue.peek().un == min.un){
            Number tmp = queue.poll();
            queue.add(new Number(uglyNums[tmp.pos + 1] * tmp.prime, tmp.pos+1, tmp.prime));
        }
    }

    return uglyNums[n-1];
}
https://www.hrwhisper.me/leetcode-super-ugly-number/
和 leetcode Ugly Number  II 思路一样，要使得super ugly number 不漏掉，那么用每个因子去乘第一个，当前因子乘积是最小后，乘以下一个…..以此类推。

    public int nthSuperUglyNumber(int n, int[] primes) {

        int[] ugly_number = new int[n];

        ugly_number[0] = 1;

        PriorityQueue<Node> q = new PriorityQueue<Node>();

        for (int i = 0; i < primes.length; i++)

            q.add(new Node(0, primes[i], primes[i]));

        for (int i = 1; i < n; i++) {

            Node cur = q.peek();

            ugly_number[i] = cur.val;

            do {

                cur = q.poll();

                cur.val = ugly_number[++cur.index] * cur.prime;

                q.add(cur);

            } while (!q.isEmpty() && q.peek().val == ugly_number[i]);

        }

        return ugly_number[n - 1];

    }

}

class Node implements Comparable<Node> {

    int index;

    int val;

    int prime;

    Node(int index, int val, int prime) {

        this.val = val;

        this.index = index;

        this.prime = prime;

    } 

    public int compareTo(Node x) {

        return this.val - x.val ;

    }

}
X. You get faster performance for custom heap, if use PriorityQueue, the performance is much more poor
https://discuss.leetcode.com/topic/35149/java-31ms-o-nlgk-solution-with-heap
public int nthSuperUglyNumber(int n, int[] primes){
    int[] index = new int[1000];
    int[] res = new int[n];
    int[] heap = new int[primes.length];
    for(int i = 0;i<primes.length; i++)heap[i] = primes[i];
    res[0] = 1;
    for(int i = 1; i<n;)
    {
        if(res[i-1] != heap[0]){         
         res[i] = heap[0];
         System.out.print(res[i]+" ");
         i++;
        }
        updateHeap(heap,primes,index,res);
    }
    return res[n-1];
}
public void heapify(int[] heap, int[] primes, int i){
    int index = i;
    int left = 2*i+1; int right = left+1;
    if(heap.length>left && heap[i] > heap[left]) index = left;
    if(heap.length>right && heap[index] > heap[right]) index = right;
    if(i!=index){
        int temp = heap[i];
        heap[i] = heap[index];
        heap[index] = temp;
        int tempPri = primes[i];
        primes[i] = primes[index];
        primes[index] = tempPri;
        heapify(heap,primes,index);
    }
}
public void updateHeap(int[] heap, int[] primes, int[] index, int[] res)
{
    index[primes[0]]++;
    heap[0] = res[index[primes[0]]] * primes[0];
    heapify(heap,primes,0);
}
https://discuss.leetcode.com/topic/31957/heap-is-slower-than-array-possible-explanation
Read full article from [LeetCode]Super Ugly Number | 书影博客
 * @author het
 *
 */
public class LeetCode313 {
	  static class Node implements Comparable<Node> {
	        int indexPointingToWhichPrimeNumber;
	        int indexPointingToUglyNumber;
	        int val;
	        Node (int indexPointingToWhichPrimeNumber, int indexPointingToUglyNumber, int val) {
	            this.indexPointingToUglyNumber = indexPointingToUglyNumber;
	            this.val = val;
	            this.indexPointingToWhichPrimeNumber = indexPointingToWhichPrimeNumber;
	        }

	        @Override
	        public int compareTo(Node other) {
	            return this.val - other.val;
	        }
	    }

	    public int nthSuperUglyNumber1(int n, int[] primes) {

	        PriorityQueue<Node> pq = new PriorityQueue<>();
	        for (int i = 0; i < primes.length; i++) {
	            pq.offer(new Node(i, 0, primes[i]));
	        }
	        int[] val = new int[n];
	        val[0] = 1;
	        for (int i = 1; i < n; ) {														`
	            Node node = pq.poll();
	            if (val[i-1] != node.val) {
	                val[i] = node.val;
	                i++;
	            }
	            node.indexPointingToUglyNumber = node.indexPointingToUglyNumber + 1;
	            node.val = primes[node.indexPointingToWhichPrimeNumber]*val[node.indexPointingToUglyNumber];
	            pq.offer(node);
	        }
	        return val[n - 1];
	    }
	    
	    int ugly1(int n, int[] primes){
	        int arr[] = new int[n];
	        int count = 1;
	        arr[0] = 1;
	        int i2 = 0;
	        int i3 = 0;
	        int i5 = 0;
	        while(count < n){
	            int minNumber = min(arr[i2] * 2, arr[i3] * 3, arr[i5] * 5);
	            if(minNumber == arr[i2]*2){
	                i2++;
	            }
	            if(minNumber == arr[i3]*3){
	                i3++;
	            }
	            if(minNumber == arr[i5]*5){
	                i5++;
	            }
	            arr[count++] = minNumber;
	        }
	        
	        return arr[n-1];
	    }
	    int ugly(int n, int[] primes){
	        int arr[] = new int[n];
	        int count = 1;
	        arr[0] = 1;
	        int [] pointers= new int[primes.length];
	        PriorityQueue<Entry> pq = new PriorityQueue<>();
	        for(int i=0;i<primes.length;i+=1){
				int value = arr[pointers[i]]*primes[i];
				pq.add(new Entry(value, i));
			}
	        while(count < n){
	          //  int minNumber = min(arr[i2] * 2, arr[i3] * 3, arr[i5] * 5);
	        	int minNumber ;
	        	while(true){
	        		 minNumber = getMinNumber1(arr, primes, pointers, count);
	        		if(minNumber != arr[count-1]) break;
	        		//2, 7, 13, 19
	        	}
	            
	            
	            arr[count++] = minNumber;
	        }
	        for(int i=0;i<arr.length;i+=1){
	        	System.out.print(arr[i] + ",");
	        }
	        return arr[n-1];
	    }
	    
	    static class Entry implements Comparable<Entry>{
	    	public Entry(int value, int i) {
				this.val = value;
				this.pointerIndex = i;
			}
			int val;
	    	int pointerIndex;
			@Override
			public int compareTo(Entry that) {
				
				return this.val -  that.val;
			}
	    	
	    	
	    }
	    private int getMinNumber(PriorityQueue<Entry> pq, int [] arr, int[] primes, int[] pointers, int count) {
		    
	    	Entry entry = pq.poll();
			pointers[entry.pointerIndex]+=1;
			int value = arr[pointers[entry.pointerIndex]]*primes[entry.pointerIndex];
			pq.add(new Entry(value, entry.pointerIndex));
			return entry.val;
		}
	    
	    private int getMinNumber1(int [] arr, int[] primes, int[] pointers, int count) {
			int min = Integer.MAX_VALUE;
			int pointerForMin = -1;
			for(int i=0;i<primes.length;i+=1){
				int value = arr[pointers[i]]*primes[i];
				if(min > value){
					min = value;
					pointerForMin = i;
				}
			}
			pointers[pointerForMin]+=1;
			return min;
		}

		private int min(int a,int b, int c){
	        int l = Math.min(a, b);
	        return Math.min(l, c);
	    }
	    
	    public static void main(String args[]) {
	    	LeetCode313 ugly = new LeetCode313();
//	        int result = ugly.ugly(150);
//	        System.out.println(result);
	        int[] primes = {2, 7, 13, 19};
	        System.out.print(ugly.ugly(12, primes));
	    }
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
}

