package alite.leetcode.xx3.extra;
///**
// * LeetCode 362 - Design Hit Counter
//
//
//Design a hit counter which counts the number of hits received in the past 5 minutes.
//Each function accepts a timestamp parameter (in seconds granularity) and you may assume that calls are 
//being made to the system in chronological order (ie, the timestamp is monotonically increasing). You may assume that the earliest timestamp starts at 1.
//It is possible that several hits arrive roughly at the same time.
//Example:
//HitCounter counter = new HitCounter();
//
//// hit at timestamp 1.
//counter.hit(1);
//
//// hit at timestamp 2.
//counter.hit(2);
//
//// hit at timestamp 3.
//counter.hit(3);
//
//// get hits at timestamp 4, should return 3.
//counter.getHits(4);
//
//// hit at timestamp 300.
//counter.hit(300);
//
//// get hits at timestamp 300, should return 4.
//counter.getHits(300);
//
//// get hits at timestamp 301, should return 3.
//counter.getHits(301);
//http://www.cnblogs.com/grandyang/p/5605552.html
//这道题让我们设计一个点击计数器，能够返回五分钟内的点击数，提示了有可能同一时间内有多次点击。由于操作都是按时间顺序的，下一次的时间戳都会大于等于本次的时间戳，那么最直接的方法就是用一个队列queue，每次点击时都将当前时间戳加入queue中，然后在需要获取点击数时，我们从队列开头开始看，如果开头的时间戳在5分钟以外了，就删掉，直到开头的时间戳在5分钟以内停止，然后返回queue的元素个数即为所求的点击数
//
//    void hit(int timestamp) {
//        q.push(timestamp);
//    }
//    
//    /** Return the number of hits in the past 5 minutes.
//        @param timestamp - The current timestamp (in seconds granularity). */
//    int getHits(int timestamp) {
//        while (!q.empty() && timestamp - q.front() >= 300) {
//            q.pop();
//        }
//        return q.size();
//    }
//
//private:
//    queue<int> q;
//
//X. https://discuss.leetcode.com/topic/48758/super-easy-design-o-1-hit-o-s-gethits-no-fancy-data-structure-is-needed
//O(s) s is total seconds in given time interval, in this case 300.
//basic ideal is using buckets. 1 bucket for every second because we only need to keep the recent hits info for 300 seconds. hit[] array is wrapped around by mod operation. Each hit bucket is associated with times[] bucket which record current time. If it is not current time, it means it is 300s or 600s... ago and need to reset to 1.
//public class HitCounter {
//    private int[] times;
//    private int[] hits;
//    /** Initialize your data structure here. */
//    public HitCounter() {
//        times = new int[300];
//        hits = new int[300];
//    }
//    
//    /** Record a hit.
//        @param timestamp - The current timestamp (in seconds granularity). */
//    public void hit(int timestamp) {
//        int index = timestamp % 300;
//        if (times[index] != timestamp) {
//            times[index] = timestamp;
//            hits[index] = 1;
//        } else {
//            hits[index]++;
//        }
//    }
//    
//    /** Return the number of hits in the past 5 minutes.
//        @param timestamp - The current timestamp (in seconds granularity). */
//    public int getHits(int timestamp) {
//        int total = 0;
//        for (int i = 0; i < 300; i++) {
//            if (timestamp - times[i] < 300) {
//                total += hits[i];
//            }
//        }
//        return total;
//    }
//}
//
//由于Follow up中说每秒中会有很多点击，下面这种方法就比较巧妙了，定义了两个大小为300的一维数组times和hits，分别用来保存时间戳和点击数，在点击函数中，将时间戳对300取余，然后看此位置中之前保存的时间戳和当前的时间戳是否一样，一样说明是同一个时间戳，那么对应的点击数自增1，如果不一样，说明已经过了五分钟了，那么将对应的点击数重置为1。那么在返回点击数时，我们需要遍历times数组，找出所有在5分中内的位置，然后把hits中对应位置的点击数都加起来即可
//
//https://discuss.leetcode.com/topic/48762/linkedhashmap-solution
//final int  fiveMin = 300;
//LinkedHashMap<Integer,Integer> map;
///** Initialize your data structure here. */
//public HitCounter() {
//    map = new LinkedHashMap<Integer,Integer>(){
//        @Override
//        protected boolean removeEldestEntry(Map.Entry<Integer,Integer> eldest){
//            return map.size() > fiveMin;
//        }
//        };
//}
//
///** Record a hit.
//    @param timestamp - The current timestamp (in seconds granularity). */
//public void hit(int timestamp) {
//    map.put(timestamp,map.getOrDefault(timestamp,0)+1);
//}
//
///** Return the number of hits in the past 5 minutes.
//    @param timestamp - The current timestamp (in seconds granularity). */
//public int getHits(int timestamp) {
//    int start = timestamp - fiveMin;
//    int sum =0;
//    for(int tsp:map.keySet()){
//        if(tsp>start)
//            sum += map.get(tsp);
//    }
//    return sum;
//}
//https://discuss.leetcode.com/topic/48879/java-sliding-window-approach-o-1-for-both-space-and-each-method-call
//public class HitCounter 
//{
//    int winLo = 0, winHi = 0; 
//    LinkedList<int[]> win = new LinkedList<int[]>();
//
//    public HitCounter() {
//    }
//    
//    public void hit(int timestamp) 
//    {
//        winHi++;
//        
//        if(win.size() == 0 || win.getLast()[0] != timestamp)
//            win.add(new int[] { timestamp, winHi});
//        else
//            win.getLast()[1] = winHi;
//            
//        ShrinkWin(timestamp);
//    }
//    
//    public int getHits(int timestamp) 
//    {
//        ShrinkWin(timestamp);
//        return (int)(winHi - winLo);
//    }
//    
//    private void ShrinkWin(int timestamp)
//    {
//        while (win.size() > 0 && timestamp - win.getFirst()[0] >= 300) 
//        {
//            winLo = win.getFirst()[1];
//            win.removeFirst();
//        }        
//    }
//}
//X. https://discuss.leetcode.com/topic/48752/simple-java-solution-with-explanation
//-- use too much space
//In this problem, I use a queue to record the information of all the hits. Each time we call the function getHits( ), we have to delete the elements which hits beyond 5 mins (300). The result would be the length of the queue : )
//public class HitCounter {
//        Queue<Integer> q = null;
//        /** Initialize your data structure here. */
//        public HitCounter() {
//            q = new LinkedList<Integer>();
//        }
//        
//        /** Record a hit.
//            @param timestamp - The current timestamp (in seconds granularity). */
//        public void hit(int timestamp) {
//            q.offer(timestamp);
//        }
//        
//        /** Return the number of hits in the past 5 minutes.
//            @param timestamp - The current timestamp (in seconds granularity). */
//        public int getHits(int timestamp) {
//            while(!q.isEmpty() && timestamp - q.peek() >= 300) {
//                q.poll();
//            }
//            return q.size();
//        }
//    }
//
//X. Extended
//https://reeestart.wordpress.com/2016/06/12/google-last-request-number/
//Interview中的一道常见题。就是要求设计数据结构来返回lastSecond(), lastMinute(), lastHour()的request数量。
//因为之前太想当然的假设在多线程环境下执行，完全忽略了在sequential环境下很多的edge case.
//Circular Buffer的确是这道题的考点，但是如果call getHits()的时间距离上一次hit()的时间早就超过了5分钟，那circular buffer里的旧数据要怎么更新是个大问题。
//一开始想着记录最近一次hit的时间，但是不够。如果当前时间距离lastHit超过5分钟了，那好办，整个circular buffer清零就好。但是如果没有超过5min，circular buffer依然要update，这样的话只有lastHit是不够的，因为没有办法找到距离当前时间5min以前的hits，也就根本没法知道circular buffer里哪些需要update，哪些不需要。
//这道题只有一个circular buffer是不够的。
//1. 要么用Deque做并wrap一个class包含时间和hit count (不能用queue是因为要获取最近一次hit的时间，而queue应该只能peek head)
//2. 要么在circular buffer的基础上再加一个time[] 数组记录circular buffer里每个cell的时间。
//[Time & Space]
//两种方法的query time都为O(n)，hit()为O(1).
//space也都为O(n)
//
//// circular buffer
//
//class HitCounter {
//
// 
//
//  int[] buffer;
//
//  int[] time;
//
// 
//
//  public HitCounter() {
//
//    this.buffer = new int[5 * 60];
//
//    this.time = new int[5 * 60];
//
//  }
//
// 
//
//  public void hit(int timestamp) {
//
//    int idx = timestamp % 300;
//
//    if (timestamp == time[idx]) {
//
//      buffer[idx]++;
//
//    }
//
//    else {
//
//      buffer[idx] = 1;
//
//      time[idx] = timestamp;
//
//    }
//
//  }
//
// 
//
//  public int getHits(int timestamp) {
//
//    int result = 0;
//
//    for (int i = 0; i < 300; i++) {
//
//      if (timestamp - time[i] < 300) {
//
//        result += buffer[i];
//
//      }
//
//    }
//
// 
//
//    return result;
//
//  }
//
//}
//
// 
//
//// Queue + Wrapper class
//
//class HitCounter2 {
//
// 
//
//  Deque<Hit> deque;
//
//  int hits = 0;
//
// 
//
//  public HitCounter2() {
//
//    this.deque = new LinkedList<>();
//
//  }
//
// 
//
//  public void hit(int timestamp) {
//
//    if (!deque.isEmpty() && timestamp == deque.getLast().time) {
//
//      deque.getLast().cnt++;
//
//    }
//
//    else {
//
//      deque.offerLast(new Hit(timestamp));
//
//    }
//
//    hits++;
//
//  }
//
// 
//
//  public int getHits(int timestamp) {
//
//    while (!deque.isEmpty() && timestamp - deque.getFirst().time >= 300) {
//
//      hits -= deque.pollFirst().cnt;
//
//    }
//
// 
//
//    return hits;
//
//  }
//
// 
//
//  private class Hit {
//
//    int time;
//
//    int cnt;
//
// 
//
//    public Hit(int time) {
//
//      this.time = time;
//
//      this.cnt = 1;
//
//    }
//
//  }
//
//}
//http://stackoverflow.com/questions/17562089/how-to-count-number-of-requests-in-last-second-minute-and-hour
// * @author het
// *
// */
public class LeetCode362 {
//	(s) s is total seconds in given time interval, in this case 300.
//	basic ideal is using buckets. 1 bucket for every second because we only need to keep the recent hits
	//info for 300 seconds. hit[] array is wrapped around by mod operation. Each hit bucket is associated with times[] 
	//bucket which record current time. If it is not current time, it means it is 300s or 600s... ago and need to reset to 1.
	public class HitCounter {
	    private int[] times;
	    private int[] hits;
	    /** Initialize your data structure here. */
	    public HitCounter() {
	        times = new int[300];
	        hits = new int[300];
	    }
	    
	    /** Record a hit.
	        @param timestamp - The current timestamp (in seconds granularity). */
	    public void hit(int timestamp) {
	        int index = timestamp % 300;
	        if (times[index] != timestamp) {
	            times[index] = timestamp;
	            hits[index] = 1;
	        } else {
	            hits[index]++;
	        }
	    }
	    
	    /** Return the number of hits in the past 5 minutes.
	        @param timestamp - The current timestamp (in seconds granularity). */
	    public int getHits(int timestamp) {
	        int total = 0;
	        for (int i = 0; i < 300; i++) {
	            if (timestamp - times[i] < 300) {
	                total += hits[i];
	            }
	        }
	        return total;
	    }
	}
	
	
//	public class HitCounter {
//	    private int[] times;
//	    private int[] hits;
//	    /** Initialize your data structure here. */
//	    public HitCounter() {
//	        times = new int[300];
//	        hits = new int[300];
//	    }
//	    
//	    /** Record a hit.
//	        @param timestamp - The current timestamp (in seconds granularity). */
//	    public void hit(int timestamp) {
//	        int index = timestamp % 300;
//	        if (times[index] != timestamp) {
//	            times[index] = timestamp;
//	            hits[index] = 1;
//	        } else {
//	            hits[index]++;
//	        }
//	    }
//	    
//	    /** Return the number of hits in the past 5 minutes.
//	        @param timestamp - The current timestamp (in seconds granularity). */
//	    public int getHits(int timestamp) {
//	        int total = 0;
//	        for (int i = 0; i < 300; i++) {
//	            if (timestamp - times[i] < 300 ||timestamp - times[i] >= 0) {
//	                total += hits[i];
//	            }
//	        }
//	        return total;
//	    }
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
