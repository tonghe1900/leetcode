package alite.leetcode.xx3;

import java.util.ArrayDeque;
import java.util.Queue;

///**
// * LeetCode 346 - Moving Average from Data Stream
//
//http://www.bubufx.com/detail-1431945.html
//Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
//
//For example,
//MovingAverage m = new MovingAverage(3);
//m.next(1) = 1
//m.next(10) = (1 + 10) / 2
//m.next(3) = (1 + 10 + 3) / 3
//m.next(5) = (10 + 3 + 5) / 3
//https://discuss.leetcode.com/topic/6/moving-average-from-data-stream/4
//public class MovingAverage {
//    private int size;
//    private int sum;
//    private Queue<Integer> window;
//
//    public MovingAverage(int size) {
//        this.size = size;
//        this.sum = 0;
//        this.window = new ArrayDeque<>();
//    }
//
//    public double next(int val) {
//        window.offer(val);
//        if (window.size() > size) {
//            sum -= window.poll();
//        }
//        sum += val;
//        return (double) sum / window.size();
//    }
//}
//https://thorcsblog.wordpress.com/2016/05/01/leetcode-moving-average-from-data-stream/
//
//public class MovingAverage {
//
//    private int count;
//
//    private int size;
//
//    private int sum;
//
//    private Queue<Integer> queue;
//
//     
//
//    /** Initialize your data structure here. */
//
//    public MovingAverage(int size) {
//
//        this.count = 0;
//
//        this.size = size;
//
//        this.sum = 0;
//
//        this.queue = new LinkedList<>();
//
//    }
//
//     
//
//    public double next(int val) {
//
//        count++;
//
//        if (count > size) {
//
//            sum -= queue.poll();
//
//        }
//
//         
//
//        queue.offer(val);
//
//        sum += val;
//
//         
//
//        return count > size ? sum * 1.0 / size : sum * 1.0 / count;
//
//    }
//
//}
//这道题定义了一个MovingAverage类，里面可以存固定个数字，然后我们每次读入一个数字，如果加上这个数字后总个数大于限制的个数，那么我们移除最早进入的数字，然后返回更新后的平均数，这种先进先出的特性最适合使用队列queue来做，而且我们还需要一个double型的变量sum来记录当前所有数字之和，这样有新数字进入后，如果没有超出限制个数，则sum加上这个数字，如果超出了，那么sum先减去最早的数字，再加上这个数字，然后返回sum除以queue的个数即可：
//    MovingAverage(int size) {
//        this->size = size;
//        sum = 0;
//    }
//    
//    double next(int val) {
//        if (q.size() >= size) {
//            sum -= q.front(); q.pop();
//        }
//        q.push(val);
//        sum += val;
//        return sum / q.size();
//    }
//https://leetcode.com/discuss/100369/java-o-1-using-deque
//    Deque<Integer> dq;
//    int size;
//    int sum;
//    public MovingAverage(int size) {
//        dq = new LinkedList<>();
//        this.size = size;
//        this.sum = 0;
//    }
//
//    public double next(int val) {
//        if (dq.size() < size) {
//            sum += val;
//            dq.addLast(val);
//            return (double) (sum / dq.size());
//        } else {
//            int temp = dq.pollFirst();
//            sum -= temp;
//            dq.addLast(val);
//            sum += val;
//            return (double) (sum / size);
//        }
//    }
//X. 
//https://leetcode.com/discuss/100361/java-o-1-time-solution
//    private int [] window;
//    private int n, insert;
//    private long sum;
//
//    /** Initialize your data structure here. */
//    public MovingAverage(int size) {
//        window = new int[size];
//        insert = 0;
//        sum = 0;
//    }
//
//    public double next(int val) {
//        if (n < window.length)  n++;
//        sum -= window[insert];
//        sum += val;
//        window[insert] = val;
//        insert = (insert + 1) % window.length;
//
//        return (double)sum / n;
//    }
//
//http://www.programcreek.com/2014/05/leetcode-moving-average-from-data-stream-java/
//public class MovingAverage {
// 
//    LinkedList<Integer> queue;
//    int size;
// 
//    /** Initialize your data structure here. */
//    public MovingAverage(int size) {
//        this.queue = new LinkedList<Integer>();
//        this.size = size;
//    }
// 
//    public double next(int val) {
//        queue.offer(val);
//        if(queue.size()>this.size){
//            queue.poll();
//        }
//        int sum=0; // not efficient
//        for(int i: queue){
//            sum=sum+i;
//        }
// 
//        return (double)sum/queue.size();
//    }
//}
//
//Use ArrayDeque as Circular Array
//
//    transient Object[] elements; // non-private to simplify nested class access
//    public void addLast(E e) {
//        if (e == null)
//            throw new NullPointerException();
//        elements[tail] = e;
//        if ( (tail = (tail + 1) & (elements.length - 1)) == head)
//            doubleCapacity();
//    }
//    public E pollLast() {
//        int t = (tail - 1) & (elements.length - 1);
//        @SuppressWarnings("unchecked")
//        E result = (E) elements[t];
//        if (result == null)
//            return null;
//        elements[t] = null;
//        tail = t;
//        return result;
//    }
//
//    public void addFirst(E e) {
//        if (e == null)
//            throw new NullPointerException();
//        elements[head = (head - 1) & (elements.length - 1)] = e;
//        if (head == tail)
//            doubleCapacity();
//    }
//
//    public E pollFirst() {
//        int h = head;
//        @SuppressWarnings("unchecked")
//        E result = (E) elements[h];
//        // Element is null if deque empty
//        if (result == null)
//            return null;
//        elements[h] = null;     // Must null out slot
//        head = (h + 1) & (elements.length - 1);
//        return result;
//    }
//
//    private void doubleCapacity() {
//        assert head == tail;
//        int p = head;
//        int n = elements.length;
//        int r = n - p; // number of elements to the right of p
//        int newCapacity = n << 1;
//        if (newCapacity < 0)
//            throw new IllegalStateException("Sorry, deque too big");
//        Object[] a = new Object[newCapacity];
//        System.arraycopy(elements, p, a, 0, r);
//        System.arraycopy(elements, 0, a, r, p);
//        elements = a;
//        head = 0;
//        tail = n;
//    }
// * @author het
// *
// */
public class LeetCode346 {
	public class MovingAverage {
	    private int size;
	    private int sum;
	    private Queue<Integer> window;
	
	    public MovingAverage(int size) {
	        this.size = size;
	        this.sum = 0;
	        this.window = new ArrayDeque<>();
	    }
	
	    public double next(int val) {
	        window.offer(val);
	        if (window.size() > size) {
	            sum -= window.poll();
	        }
	        sum += val;
	        return (double) sum / window.size();
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
