package alite.leetcode.xx2.sucess.before;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode 253 - Meeting Rooms II

http://sbzhouhao.net/LeetCode/LeetCode-Meeting-Rooms-II.html
LIKE CODING: LeetCode [253] Meeting Rooms II
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
For example,
Given [[0, 30],[5, 10],[15, 20]],

return 2.
X.
https://leetcode.com/discuss/71846/super-easy-java-solution-beats-98-8%25
Very nice, that lazy releasing of rooms
Rather than create or release a room and keep tracking the max value, you can just add room number when start < end else keep tracking the last class that ends. (Like the idea in this post). 
http://happycoding2010.blogspot.com/2015/11/leetcode-253-meeting-rooms-ii.html

   public int minMeetingRooms(Interval[] intervals) {  
     int n=intervals.length;  
     int[] start=new int[n];  
     int[] end=new int[n];  
     for (int i=0; i<n; i++) {  
       start[i]=intervals[i].start;  
       end[i]=intervals[i].end;  
     }  
     Arrays.sort(start);  
     Arrays.sort(end);  
     int i=0, j=0, res=0;  
     while (i<n) {  
       if (start[i]<end[j]) i++;  
       else if (start[i]>end[j]) j++;  
       else {  
         i++;  
         j++;  
       }  
       res=Math.max(res,i-j);  
     }  
     return res;  
   }
https://leetcode.com/discuss/82292/explanation-super-easy-java-solution-beats-from-%40pinkfloyda
To understand why it works, first let’s define two events: Meeting Starts Meeting Ends
Next, we acknowledge three facts: The numbers of the intervals give chronological orders When an ending event occurs, there must be a starting event has happened before that, where “happen before” is defined by the chronological orders given by the intervals Meetings that started which haven’t ended yet have to be put into different meeting rooms, and the number of rooms needed is the number of such meetings
So, what this algorithm works as follows:
for example, we have meetings that span along time as follows:
|_____|
      |______|
|________|
        |_______|
Then, the start time array and end time array after sorting appear like follows:
||    ||
     |   |   |  |
Initially, endsItr points to the first end event, and we move i which is the start event pointer. As we examine the start events, we’ll find the first two start events happen before the end event that endsItr points to, so we need two rooms (we magically created two rooms), as shown by the variable rooms. Then, as i points to the third start event, we’ll find that this event happens after the end event pointed by endsItr, then we increment endsItr so that it points to the next end event. What happens here can be thought of as one of the two previous meetings ended, and we moved the newly started meeting into that vacant room, thus we don’t need to increment rooms at this time and move both of the pointers forward. Next, because endsItr moves to the next end event, we’ll find that the start event pointed by i happens before the end event pointed by endsItr. Thus, now we have 4 meetings started but only one ended, so we need one more room. And it goes on as this.
    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for(int i=0; i<intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0;
        int endsItr = 0;
        for(int i=0; i<starts.length; i++) {
            if(starts[i]<ends[endsItr])
                rooms++;
            else
                endsItr++;
        }
        return rooms;
    }
https://leetcode.com/discuss/74177/elegant-9-line-java-using-heap-%26-6-ms-greedy-java-92-03%25
Greedy way, much faster than min-heap, while loop is the same as the merge operation of merge-sort.

Most greedy solutions start with intuition, and then a few tests that verify that the intuition was correct. I don't have a way to tell right away that a greedy solution will work. I usually start by simply drawing/plotting a sample scenario and observing the behavior. For this problem, I started at putting all the meetings on the same timeline. The idea is that when a new meeting starts, it may require an additional conference room, if one of the running meetings ends we will have a vacant room for the next meeting. So the timeline can look something like this: [s,s,e,s,e,e]: there are three meetings: first meeting starts, then second meeting starts, then first meeting ends, then third meeting starts (it uses the vacant free room from the first meeting that has ended), then second meeting ends, then third meeting ends. The second meeting had no vacant room when it started, so our max # of rooms required increased by 1, the third meeting used one of the vacant rooms that have been added before, so the max # overall is 2. there are 3 things that can happen when meetings overlap (note: I modified my code, the second if-condition didn't make sense and was not necessary): 1. All meetings are already running and no new meetings will start. 2. A new meeting starts before before one of the previous ones ended. 3. A meeting ends. These conditions correspond to the if-statements in the same order. Note, that we don't really care which particular meeting ended or started, we only care that a new meeting has started or any old meeting has ended (look at the timeline array again). In my code, I represent the timeline with two sorted arrays of start times and end times and two pointers. The way we iterate over these two arrays is similar to the merge operation of merge-sort: we advance the pointer with the smaller value (in our case the value represents time). On each iteration of the while loop we simply check whether the current value of rooms required (cur) is the max we've encountered so far. Sorry for the wall of text, I am not very good at explaining :) Let me know if you have any more questions.
public int minMeetingRooms(Interval[] intervals) {
    int[] starts = new int[intervals.length], ends = new int[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i].start;
        ends[i] = intervals[i].end;
    }
    Arrays.sort(starts);
    Arrays.sort(ends);
    int i = 0, j = 0, max = 0, cur = 0;
    while (i < starts.length || j < ends.length) {
        if (i >= starts.length) {
            break;
        } else if (starts[i] < ends[j]) {
            cur += 1; i++;
        } else {
            cur -= 1; j++;
        }
        max = Math.max(cur, max);
    }
    return max;
}
https://leetcode.com/discuss/65801/java-nlog-n-easy-solution-without-heap
public int minMeetingRooms(Interval[] intervals) {
    int res =0;
    int temp =0;
    int[] start = new int[intervals.length];
    int[] end = new int[intervals.length];
    for(int i= 0; i<intervals.length; i++){
        start[i] = intervals[i].start;
        end[i] = intervals[i].end;
    }
    Arrays.sort(start);
    Arrays.sort(end);
    int i=0;
    int j=0;
    while(i<start.length&&j<end.length){
        if(start[i]<end[j]){
            temp++;
            i++;
            res = Math.max(res,temp);
        }
        else{
            temp--;
            j++;
        }
    }
    return res;
}
http://likesky3.iteye.com/blog/2235665
思路2，参考 
https://leetcode.com/discuss/50793/my-python-solution-with-explanation 
原始注解： 
# Very similar with what we do in real life. Whenever you want to start a meeting, 
# you go and check if any empty room available (available > 0) and 
# if so take one of them ( available -=1 ). Otherwise, 
# you need to find a new room someplace else ( numRooms += 1 ).  
# After you finish the meeting, the room becomes available again ( available += 1 ). 
    // Method 2: https://leetcode.com/discuss/50793/my-python-solution-with-explanation  
    public int minMeetingRooms(Interval[] intervals) {  
        if (intervals == null || intervals.length == 0)  
            return 0;  
        int N = intervals.length;  
        int[] starts = new int[N];  
        int[] ends = new int[N];  
        for (int i = 0; i < intervals.length; i++) {  
            starts[i] = intervals[i].start;  
            ends[i] = intervals[i].end;  
        }  
        Arrays.sort(starts);  
        Arrays.sort(ends);  
        int e = 0, rooms = 0, available = 0;  
        for (int start : starts) {  
            while (ends[e] <= start) {  
                available++;  
                e++;  
            }  
            if (available > 0)  
                available--;  
            else  
                rooms++;  
        }  
        return rooms;  
    }  



使用最小堆来维护所有已安排会议的结束时间，来一个新会议，仍然是比较其start 和 当前最早结束会议时间，若 start >= 最早结束时间，说明该会议可安排，就安排在最早结束那个会议的room，需要从最小堆中删除堆顶元素，将新会议的结束时间插入堆中，否则新增会议室。 
    // Method 1  
    public int minMeetingRooms1(Interval[] intervals) {  
        if (intervals == null || intervals.length == 0)  
            return 0;  
        Arrays.sort(intervals, comparator);  
        int N = intervals.length;  
        int rooms = 1;  
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();  
        minHeap.offer(intervals[0].end);  
        for (int i = 1; i < N; i++) {  
            if (intervals[i].start < minHeap.peek()) {  
                rooms++;  
            } else {  
                minHeap.poll();                  
            }  
            minHeap.offer(intervals[i].end);  
        }  
        return rooms;  
    } 

X. http://sbzhouhao.net/LeetCode/LeetCode-Meeting-Rooms-II.html
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, (o1, o2) -> {
            int r = o1.start - o2.start;
            return r == 0 ? o1.end - o2.end : r;
        });

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        queue.add(intervals[0].end);

        for (int i = 1; i < intervals.length; i++) {
            int val = queue.peek();
            Interval in = intervals[i];
            if (in.start >= val) {
                queue.remove(val);
            }
            queue.add(in.end);
        }
        return queue.size();
    }

X.  http://www.cnblogs.com/jcliBlogger/p/4713099.html
https://leetcode.com/discuss/50783/java-ac-code-using-comparator
Update: for each group of non-overlapping intervals, we just need to store the last added one instead of the full list. So we could use a vector<Interval> instead of vector<vector<Interval>> in C++. The code is now as follows.
 3     int minMeetingRooms(vector<Interval>& intervals) {
 4         sort(intervals.begin(), intervals.end(), compare);
 5         vector<Interval> rooms;
 6         int n = intervals.size();
 7         for (int i = 0; i < n; i++) {
 8             int idx = findNonOverlapping(rooms, intervals[i]);
 9             if (rooms.empty() || idx == -1)
10                 rooms.push_back(intervals[i]);
11             else rooms[idx] = intervals[i];
12         }
13         return (int)rooms.size();
14     } 
15 private:
16     static bool compare(Interval& interval1, Interval& interval2) {
17         return interval1.start < interval2.start;
18     }
19     int findNonOverlapping(vector<Interval>& rooms, Interval& interval) {
20         int n = rooms.size();
21         for (int i = 0; i < n; i++)
22             if (interval.start >= rooms[i].end)
23                 return i;
24         return -1;
25     }


This also group meetings in same meeting room.
The idea is to group those non-overlapping meetings in the same room and then count how many rooms we need. You may refer to this link.

2 public:
 3     int minMeetingRooms(vector<Interval>& intervals) {
 4         sort(intervals.begin(), intervals.end(), compare);
 5         vector<vector<Interval>> rooms;
 6         int n = intervals.size();
 7         for (int i = 0; i < n; i++) {
 8             int idx = findNonOverlapping(rooms, intervals[i]);
 9             if (rooms.empty() || idx == -1)
10                 rooms.push_back({intervals[i]});
11             else rooms[idx].push_back(intervals[i]);
12         }
13         return (int)rooms.size();
14     }
15 private:
16     static bool compare(Interval& interval1, Interval& interval2) {
17         return interval1.start < interval2.start;
18     }
19     int findNonOverlapping(vector<vector<Interval>>& rooms, Interval& interval) {
20         int n = rooms.size();
21         for (int i = 0; i < n; i++)
22             if (interval.start >= rooms[i].back().end)
23                 return i;
24         return -1;
25     }

X.  Use PriorityQueue + sweep line
https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap/11
public int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0)
        return 0;
        
    // Sort the intervals by start time
    Arrays.sort(intervals, new Comparator<Interval>() {
        public int compare(Interval a, Interval b) { return a.start - b.start; }
    });
    
    // Use a min heap to track the minimum end time of merged intervals
    PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
        public int compare(Interval a, Interval b) { return a.end - b.end; }
    });
    
    // start with the first meeting, put it to a meeting room
    heap.offer(intervals[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        // get the meeting room that finishes earliest
        Interval interval = heap.poll();
        
        if (intervals[i].start >= interval.end) {
            // if the current meeting starts right after 
            // there's no need for a new room, merge the interval
            interval.end = intervals[i].end;
        } else {
            // otherwise, this meeting needs a new room
            heap.offer(intervals[i]);
        }
        
        // don't forget to put the meeting room back
        heap.offer(interval);
    }
    
    return heap.size();
}
public int minMeetingRooms(Interval[] intervals) {
 if (intervals.length == 0) {
  return 0;
 }
 // sort
 Arrays.sort(intervals, new Comparator<Interval>() {
  @Override
  public int compare(Interval a, Interval b) {
   return a.start - b.start;
  }
 });
 // PriorityQueue
 PriorityQueue<Integer> ends = new PriorityQueue<Integer>();
 ends.offer(intervals[0].end);
 for (int i = 1; i < intervals.length; i++) {
  if (intervals[i].start >= ends.peek()) { // no overlap, then should update smallest end.
   ends.poll();
  } 
  ends.offer(intervals[i].end);
 }
 return ends.size();
}
http://www.cnblogs.com/yrbbest/p/5012534.html
给定一个interval数组，求最少需要多少间教室。初始想法是扫描线算法sweeping-line algorithm，先把数组排序，之后维护一个min-oriented heap。遍历排序后的数组，每次把interval[i].end加入到heap中，然后比较interval.start与pq.peek()，假如interval[i].start >= pq.peek()，说明pq.peek()所代表的这个meeting已经结束，我们可以从heap中把这个meeting的end time移除，继续比较下一个pq.peek()。比较完毕之后我们尝试更新maxOverlappingMeetings。 像扫描线算法和heap还需要好好复习， 直线，矩阵的相交也可以用扫描线算法。
Time Complexity - O(nlogn)， Space Complexity - O(n)
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals == null || intervals.length == 0)
            return 0;
        
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval t1, Interval t2) {
                if(t1.start != t2.start)
                    return t1.start - t2.start;
                else
                    return t1.end - t2.end;
            }
        });
        
        int maxOverlappingMeetings = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();      // min oriented priority queue
        
        for(int i = 0; i < intervals.length; i++) {         // sweeping-line algorithms
            pq.add(intervals[i].end);
            while(pq.size() > 0 && intervals[i].start >= pq.peek())
                pq.remove();
                
            maxOverlappingMeetings = Math.max(maxOverlappingMeetings, pq.size());
        }
        
        return maxOverlappingMeetings;
    }
Use min PQ to store the meeting rooms end time. If new meeting start time greater or equal than least element, update it. If not open a new meeting room. Report the pq size at the end. O(nlogn) complexity.
http://segmentfault.com/a/1190000003894670
An alternative solution is to use a priority queue to store the end times. Then we sort the intervals according to its start time. We iterate through the intervals. If the current start time is less than the earliest end time in the pq, numRooms++. Else, remove the earliest time from the pq. For each iteration, we also need to offer the current ending time into the pq. 
这题的思路和Rearrange array to certain distance很像，我们要用贪心法，即从第一个时间段开始，选择下一个最近不冲突的时间段，再选择下一个最近不冲突的时间段，直到没有更多。然后如果有剩余时间段，开始为第二个房间安排，选择最早的时间段，再选择下一个最近不冲突的时间段，直到没有更多，如果还有剩余时间段，则开辟第三个房间，以此类推。这里的技巧是我们不一定要遍历这么多遍，我们实际上可以一次遍历的时候就记录下，比如第一个时间段我们放入房间1，然后第二个时间段，如果和房间1的结束时间不冲突，就放入房间1，否则开辟一个房间2。然后第三个时间段，如果和房间1或者房间2的结束时间不冲突，就放入房间1或者2，否则开辟一个房间3，依次类推，最后统计开辟了多少房间。对于每个房间，我们只要记录其结束时间就行了，这里我们查找不冲突房间时，只要找结束时间最早的那个房间。
这里还有一个技巧，如果我们把这些房间当作List来管理，每次查询需要O(N)时间，如果我们用堆来管理，可以用logN时间找到时间最早结束的房间。
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });
        // 用堆来管理房间的结束时间
        PriorityQueue<Integer> endTimes = new PriorityQueue<Integer>();
        endTimes.offer(intervals[0].end);
        for(int i = 1; i < intervals.length; i++){
            // 如果当前时间段的开始时间大于最早结束的时间，则可以更新这个最早的结束时间为当前时间段的结束时间，如果小于的话，就加入一个新的结束时间，表示新的房间
            if(intervals[i].start >= endTimes.peek()){
                endTimes.poll();
            }
            endTimes.offer(intervals[i].end);
        }
        // 有多少结束时间就有多少房间
        return endTimes.size();
    }
https://leetcode.com/discuss/51402/java-greedy-algorithm-with-priority-queue
   public int minMeetingRooms(Interval[] intervals) {
    if (intervals == null || intervals.length == 0)
        return 0;

    Comparator<Interval> comp = new Comparator<Interval>() {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    };
    Arrays.sort(intervals, comp);

    PriorityQueue<Interval> queue = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.end - o2.end;
        }
    }
    );

    for (int i = 0; i < intervals.length; i++) {
        if (queue.isEmpty()) {
            queue.offer(intervals[i]); //start the first meeting in a new room.
        } else {
            Interval finishingMeeting = queue.poll(); // get the previous meeting with earliest finishing time.
            if (intervals[i].start < finishingMeeting.end) {
                queue.offer(intervals[i]); //the meeting isn't finished yet, start meeting in a new room.
            } else {
                finishingMeeting.end = intervals[i].end; // using the room by the previous meeting.
            }
            queue.offer(finishingMeeting);
        }
    }
    return queue.size();  
}
https://leetcode.com/discuss/50911/ac-java-solution-using-min-heap
A different version of code with similar thought. Every time the new interval start is larger than the minimum end, pop the interval in the queue. In addition, really enjoy the java 8 lambda style comparator : )
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> (a.start - b.start));
        int max = 0;
        PriorityQueue<Interval> queue = new PriorityQueue<>(intervals.length, (a, b) -> (a.end - b.end));
        for(int i = 0; i < intervals.length; i++){
            while(!queue.isEmpty() && intervals[i].start >= queue.peek().end)
                queue.poll();
            queue.offer(intervals[i]);
            max = Math.max(max, queue.size());
        }
        return max;
    }
http://dananqi.blog.163.com/blog/static/23066615020157104293164/
这题就是首先按照start time 排序，然后遍历排序后的数组.
如果当前会议的开始时间早于之前所有会议的结束时间(即最早结束的会议），则要增加一个房间。
如果之前所有会议的最早结束时间早于当前会议，那么不需要增加会议室，只需要重新记下最早结束的会议就好了。
所以关键在要记住当前所有会议的最早结束时间，需要借助一个min heap，比较的是结束时间。
而之前排序的时候要比较的是开始时间。这个算法的时间复杂度为O(NlogN)。
如果不用heap，也可以用一个List<Interval> 来记录下每个会议室的结束时间，每次遇到一个新的会议，则要寻找是否有会议室已经结束了，如果找到，不用增加会议室，如果没找到，则要增加。这种解法每次都要遍历一遍所有会议室，最坏情况下为O(N*N)。不如上面用heap的时间复杂度为O(NlogN)。
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        int len = intervals.length;
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i, Interval j) { return i.start - j.start; } 
        });
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>(len, new Comparator<Interval>() {
            public int compare(Interval i, Interval j) { return i.end - j.end; }
        });
        heap.offer(intervals[0]);
        for(int i = 1; i < len; i++) {
            Interval min = heap.poll();
            if(intervals[i].start >= min.end) {
                min.end = intervals[i].end; //更新当前最早结束
            } else {
                heap.offer(intervals[i]); //增加一个会议室
            }
            heap.offer(min); //当前最早结束有可能已经更新了哦。
        }
        return heap.size();
    }

X. PriorityQueue + Event
https://discuss.leetcode.com/topic/25503/java-another-thinking-process-event-queue/2
Simulate event queue procession. Create event for each start and end of intervals. Then for start event, open one more room; for end event, close one meeting room. At the same time, update the most rooms that is required.
Be careful of events like [(end at 11), (start at 11)]. Put end before start event when they share the same happening time, so that two events can share one meeting room.
    private static final int START = 1;

    private static final int END = 0;
    
    private class Event {
        int time;
        int type; // end event is 0; start event is 1

        public Event(int time, int type) {
            this.time = time;
            this.type = type;
        }
    }
    
    public int minMeetingRooms(Interval[] intervals) {
        int rooms = 0; // occupied meeting rooms
        int res = 0;

        // initialize an event queue based on event's happening time
        Queue<Event> events = new PriorityQueue<>(new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // for same time, let END event happens first to save rooms
                return e1.time != e2.time ? 
                       e1.time - e2.time : e1.type - e2.type;
            }
        });

        // create event and push into event queue
        for (Interval interval : intervals) {
            events.offer(new Event(interval.start, START));
            events.offer(new Event(interval.end, END));
        }
        
        // process events
        while (!events.isEmpty()) {
            Event event = events.poll();
            if (event.type == START) {
                rooms++;
                res = Math.max(res, rooms);
            } else {
                rooms--; 
            }
        }
        
        return res;
    }

X.
https://leetcode.com/discuss/50783/java-ac-code-using-comparator
Nice idea to just group those non-overlapping meetings together :-)
A little improvement: for each group of non-overlapping intervals, we just need to store the last added one instead of the full list. So we could use a vector<Interval> instead ofvector<vector<Interval>> in C++ or a List<Interval> instead of List<List<Interval>>in Java.
class intervalComparator implements Comparator<Interval>{
    public int compare(Interval o1, Interval o2){
        return o1.start-o2.start;
    }
}
public int minMeetingRooms(Interval[] intervals) {
    Arrays.sort(intervals, new intervalComparator());
    List<List<Interval>> list = new ArrayList<>();
    for(int i=0; i<intervals.length; i++){
        int idx = findIdx(list, intervals[i]);
        if(list.size()==0 || idx==-1){
            List<Interval> tmp = new ArrayList<>();
            tmp.add(intervals[i]);
            list.add(tmp);
        }else{
            list.get(idx).add(intervals[i]);
        }
    }
    return list.size();
}
public int findIdx(List<List<Interval>> list, Interval interval){
    int idx = -1;
    int min=Integer.MAX_VALUE;
    for(int i=0; i<list.size(); i++){
        if(interval.start>=list.get(i).get(list.get(i).size()-1).end){
            return i;
        }
    }
    return idx;
}
 it depends on number of rooms. The worst case is O(NlogN + NN) --> O(N^2). Normal case is O(NlogN + Nk), while k is the room number
    int minMeetingRooms(vector<Interval>& intervals) {
        sort(intervals.begin(), intervals.end(), compare);
        vector<Interval> rooms;
        int n = intervals.size();
        for (int i = 0; i < n; i++) {
            int idx = findNonOverlapping(rooms, intervals[i]);
            if (rooms.empty() || idx == -1)
                rooms.push_back(intervals[i]);
            else rooms[idx] = intervals[i];
        }
        return (int)rooms.size();
    } 
private:
    static bool compare(Interval& interval1, Interval& interval2) {
        return interval1.start < interval2.start;
    }
    int findNonOverlapping(vector<Interval>& rooms, Interval& interval) {
        int n = rooms.size();
        for (int i = 0; i < n; i++)
            if (interval.start >= rooms[i].end)
                return i;
        return -1;
    }

X. https://leetcode.com/discuss/60659/java-another-thinking-process-event-queue
Simulate event queue procession. Create event for each start and end of intervals. Then forstart event, open one more room; for end event, close one meeting room. At the same time, update the most rooms that is required.
Be careful of events like [(end at 11), (start at 11)]. Put end before start event when they share the same happening time, so that two events can share one meeting room.
public class Solution {
    private static final int START = 1;
    private static final int END = 0;

    private class Event {
        int time;
        int type; // end event is 0; start event is 1

        public Event(int time, int type) {
            this.time = time;
            this.type = type;
        }
    }

    public int minMeetingRooms(Interval[] intervals) {
        int rooms = 0; // occupied meeting rooms
        int res = 0;
        // initialize an event queue based on event's happening time
        Queue<Event> events = new PriorityQueue<>(new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // for same time, let END event happens first to save rooms
                return e1.time != e2.time ? 
                       e1.time - e2.time : e1.type - e2.type;
            }
        });
        // create event and push into event queue
        for (Interval interval : intervals) {
            events.offer(new Event(interval.start, START));
            events.offer(new Event(interval.end, END));
        }

        // process events
        while (!events.isEmpty()) {
            Event event = events.poll();
            if (event.type == START) {
                rooms++;
                res = Math.max(res, rooms);
            } else {
                rooms--; 
            }
        }
        return res;
    }
}

https://leetcode.com/discuss/62683/simple-o-nlgn-java-solution-with-explanation
The basic idea is referred from StefanPochmann's C++ version: Create a map to store room amount changes for each meeting time point, if it's start time, room + 1; if it's end time, room - 1. Then sort the keys of the map, iterate the room changes, eventually output the maximum room amount as the result.
public int minMeetingRooms(Interval[] intervals) {    
        HashMap<Integer, Integer> changes= new HashMap<Integer, Integer>();//Room changes at the specific time.
        for (Interval i : intervals) {
            Integer start = changes.get(i.start) == null ? 0 : changes.get(i.start);
            changes.put(i.start, start+1);

            Integer end = changes.get(i.end) == null ? 0 : changes.get(i.end);
            changes.put(i.end, end-1);
        }
        int rooms = 0, maxrooms = 0;
        Object array[] = changes.keySet().toArray();
        Arrays.sort(array);

        for (Object i : array) {
            rooms += changes.get(i);
            maxrooms = Math.max(maxrooms, rooms);
        }
        return maxrooms;
    }
Below code can be avoid if you use TreeMap in java, where key is sorted
    Object array[] = changes.keySet().toArray();
    Arrays.sort(array);
Below is the code with similar algorithm but use TreeMap:
public int minMeetingRooms(Interval[] intervals) {
    //we must sort the timestamp, otherwise we may incorrectly use offset and skip the max room usage
    //key is timeStamp, value is num of room that will be occupied start from this moment. 
    //If a room will be cleared from this moment, then we simply let value--       
    TreeMap<Integer, Integer> hs = new TreeMap<Integer, Integer>();

    for(Interval temp : intervals){
        //put timestamp in map
        if(!hs.containsKey(temp.start)) hs.put(temp.start, 0);
        if(!hs.containsKey(temp.end)) hs.put(temp.end, 0);

        //based on timestamp to mark the usage of rooms
        hs.put(temp.start, hs.get(temp.start) + 1);//add one room
        hs.put(temp.end, hs.get(temp.end) - 1);//remove one room
    }

    int rooms = 0, maxRoom = 0;
    for(int temp : hs.keySet()){
        //update room availability
        rooms += hs.get(temp);
        maxRoom = Math.max(rooms, maxRoom);
    }

    return maxRoom;
}

public int minMeetingRooms(Interval[] intervals) {
    PriorityQueue<int[]> q = new PriorityQueue<>((a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
    for (Interval i : intervals) {
        q.offer(new int[] {i.start, 1});
        q.offer(new int[] {i.end, -1});
    }
    int max = 0, cur = 0;
    while(!q.isEmpty())
        max = Math.max(cur += q.poll()[1], max);
    return max;
} 
http://buttercola.blogspot.com/2015/08/leetcode-meeting-rooms-ii.html

    public int minMeetingRooms(Interval[] intervals) {

        if (intervals == null || intervals.length == 0) {

            return 0;

        }

         

        int len = intervals.length;

        int[] startTime = new int[len];

        int[] endTime = new int[len];

         

        for (int i = 0; i < len; i++) {

            Interval curr = intervals[i];

            startTime[i] = curr.start;

            endTime[i] = curr.end;

        }

         

        // Sort the start and end time

        Arrays.sort(startTime);

        Arrays.sort(endTime);

         

        int activeMeetings = 0;

        int numMeetingRooms = 0;

         

        int i = 0;

        int j = 0;

         

        while (i < len && j < len) {

            if (startTime[i] < endTime[j]) {

                activeMeetings++;

                numMeetingRooms = Math.max(numMeetingRooms, activeMeetings);

                i++;

            } else {

                activeMeetings--;

                j++;

            }

        }

         

        return numMeetingRooms;

    }

X. Using TreeMap
https://leetcode.com/discuss/68125/super-easy-java-solution-using-treemap
    public int minMeetingRooms(Interval[] intervals) {
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>(); // Sort Key based on nature order
        for (Interval i : intervals) {
            if (map.containsKey(i.start)) {
                map.put(i.start, map.get(i.start)+1);
            } else {
                map.put(i.start, 1);
            }
            if (map.containsKey(i.end)) {
                map.put(i.end, map.get(i.end)-1);
            } else {
                map.put(i.end, -1);
            }
        }
        int maxRoom = 0; int curRoom = 0;
        for (int i : map.keySet()) {
            maxRoom = Math.max(maxRoom, curRoom += map.get(i));
        }
        return maxRoom;
    }
https://leetcode.com/discuss/70998/java-ac-solution-greedy-beats-92-03%25
According to greedy, you get one interval, then add the one right behind it. Then recursively deal with the rest.
    public int minMeetingRooms(Interval[] intervals) {
        Arrays.sort(intervals, new Comparator<Interval>(){
           public int compare(Interval o1, Interval o2){
               return o1.start - o2.start;
           } 
        });
        return helper(new ArrayList(Arrays.asList(intervals)));
    }

    private int helper(List<Interval> li){
        if(li.size() == 0)
            return 0;
        Interval pre = li.get(0);
        List<Interval> nextLi = new ArrayList();
        for(int i=1;i<li.size();i++){
            Interval inter = li.get(i);
            if(inter.start < pre.end){
                nextLi.add(inter);
            }else{
                pre = inter;
            }
        }
        return 1 + helper(nextLi);
    }
http://www.jyuan92.com/blog/leetcode-meeting-rooms-ii/

Follow up
http://www.1point3acres.com/bbs/thread-210807-1-1.html
一大堆Task有开始时间和结束时间，要把这些工作派给不同的工人
每个工人有一个list存放自己要做的Task
要求把这些工作尽可能的安排给少的工人，存在他们的工作List里面
最后返回这些工人
我一上来就是Meeting Room 2 的思路
在meeting room2 里， 最常规的思路就是分开看，开始时间和结束时间，然后做完了就可以做新的，有会还在开的时候就得加一个会议室，这里跟这个很相似，我每次都找到所有现有worker中工作最早做完的那一个，然后跟最近开始的工作的开始时间作比较，如果来的记做就安排给他，来不及就加个新的。 当时时间紧也没有细想对不对。。。
Read full article from LIKE CODING: LeetCode [253] Meeting Rooms II
 * @author het
 *
 */

/**
 *  [[0, 30],[5, 10],[15, 20]],
 *  
 *  [5, 6]  [6, 7]  
 *  
 *  
 * @author het
 *
 */
public class LeetCode253 {
//    public int numOfRooms(List<int []> meetings){
//    	if(meetings == null || meetings.size() == 0) return 0;
//    	Collections.sort(meetings, new Comparator<int []>(){
//
//			@Override
//			public int compare(int[] o1, int[] o2) {
//				// TODO Auto-generated method stub
//				return o1[0] - o2[0];
//			}
//    		
//    	});
//    	int end = meetings.get(0)[1];
//    	int result = 1;
//    	for(int i=1;i<meetings.size();i+=1){
//    		int [] meeting = meetings.get(i);
//    		if(meeting[0] < end){
//    			result+=1;
//    			end = Math.min(end, meeting[1]);
//    		}else{
//    			end = Math.max(end, meeting[1]);
//    		}
//    	}
//    	return result;
//    }
	
	// [[0, 5],[4, 10],[5, 8]  [11, 15],
	public int numMeetingRooms(List<int []> intervals) {
		if(intervals == null || intervals.size() == 0) return 0;
		Collections.sort(intervals, new Comparator<int []>(){
			public int compare(int [] first, int [] second){
				return new Integer(first[0]).compareTo(new Integer(second[0]));
				
			}
			
		});
		
		int count= 1;
		//int end = intervals.get(0)[1];
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		pq.add(intervals.get(0)[1]);   //5
		for(int i=1;i<intervals.size();i+=1){
			if(intervals.get(i)[0] < pq.peek()){
				count+=1;
			}else{
				pq.poll();//  10
			}
			pq.add(intervals.get(i)[1]);// 8  10
		}
		return count;
	}
    
	
	
	
	
    public int minMeetingRooms(List<int []> intervals) {
        if(intervals==null||intervals.size()==0)
            return 0;
     
        Collections.sort(intervals, new Comparator<int[]>(){
            public int compare(int [] i1, int [] i2){
                return i1[0]-i2[0];
            }
        });
     
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        int count=1;
        queue.offer(intervals.get(0)[1]);
     
        for(int i=1; i<intervals.size(); i++){
            if(intervals.get(i)[0]<queue.peek()){
                count++;
     
            }else{
                queue.poll();
            }
     
            queue.offer(intervals.get(i)[1]);
        }
     
        return count;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode253().minMeetingRooms(Arrays.asList(new int[]{0, 5},new int[]{4, 10},new int[]{5, 20},new int[]{10, 20})));
   
	}

}
