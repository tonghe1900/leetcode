package alite.leetcode.newExtra.L500;
/**
 * LeetCode 539 - Minimum Time Difference

https://leetcode.com/problems/minimum-time-difference
Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes difference between any two time points in the list.
Example 1:
Input: ["23:59","00:00"]
Output: 1
Note:
The number of time points in the given list is at least 2 and won't exceed 20000.
The input time is legal and ranges from 00:00 to 23:59.
X.
将时间从小到大排序，然后将最小的时间小时+24后加入数组末尾。
两两做差，求最小值即可
https://discuss.leetcode.com/topic/82582/java-sorting-with-a-sentinel-node
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        List<Time> times = new ArrayList<>();
        for (String tp : timePoints) {
            String[] strs = tp.split(":");
            times.add(new Time(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
        }
        Collections.sort(times);
        Time earlist = times.get(0);
        times.add(new Time(earlist.h + 24, earlist.m));
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int diff = (int) Math.abs(times.get(i).getDiff(times.get(i + 1)));
            minDiff = Math.min(minDiff, diff);
        }
        return minDiff;
    }
class Time implements Comparable<Time> {
    int h;
    int m;
    public Time(int h, int m) {
        this.h = h;
        this.m = m;
    }
    
    public int compareTo(Time other) {
        if (this.h == other.h) {
            return this.m - other.m;
        }
        return this.h - other.h;
    }
    
    public int getDiff(Time other) {
        return (this.h - other.h) * 60 + (this.m - other.m);            
    }
}
https://discuss.leetcode.com/topic/83019/java-10-liner-solution-simplest-so-far
    public int findMinDifference(List<String> timePoints) {
        int mm = Integer.MAX_VALUE;
        List<Integer> time = new ArrayList<>();
        
        for(int i = 0; i < timePoints.size(); i++){
            Integer h = Integer.valueOf(timePoints.get(i).substring(0, 2));
            time.add(60 * h + Integer.valueOf(timePoints.get(i).substring(3, 5)));
        }
        
        Collections.sort(time, (Integer a, Integer b) -> a - b);
        
        for(int i = 1; i < time.size(); i++){
            System.out.println(time.get(i));
            mm = Math.min(mm, time.get(i) - time.get(i-1));
        }
        
        int corner = time.get(0) + (1440 - time.get(time.size()-1));
        return Math.min(mm, corner);
    }
X.
https://discuss.leetcode.com/topic/82573/verbose-java-solution-bucket
There is only 24 * 60 = 1440 possible time points. Just create a boolean array, each element stands for if we see that time point or not. Then things become simple...
same idea here but you can improve your time a little if you avoid string split and int parse. You know the input format so you can leverage that directly.
    public int findMinDifference(List<String> timePoints) {
        boolean[] mark = new boolean[24 * 60];
        for (String time : timePoints) {
            String[] t = time.split(":");
            int h = Integer.parseInt(t[0]);
            int m = Integer.parseInt(t[1]);
            if (mark[h * 60 + m]) return 0;
            mark[h * 60 + m] = true;
        }
        
        int prev = 0, min = Integer.MAX_VALUE;
        int first = Integer.MAX_VALUE, last = Integer.MIN_VALUE;
        for (int i = 0; i < 24 * 60; i++) {
            if (mark[i]) {
                if (first != Integer.MAX_VALUE) {
                    min = Math.min(min, i - prev);
                }
                first = Math.min(first, i);
                last = Math.max(last, i);
                prev = i;
            }
        }
        
        min = Math.min(min, (24 * 60 - last + first));
        
        return min;
    }

https://discuss.leetcode.com/topic/82575/java-o-nlog-n-o-n-time-o-1-space-solutions
O(n) Time O(1) Space. Note that, more accurately, this is O(1) time as the number of iterations of the first loop is limited to 1440 due to the pigeonhole principle.
public int findMinDifference(List<String> timePoints) {

    boolean[] timeSeen = new boolean[1440];
    for (String s : timePoints) {
        int mins = Integer.parseInt(s.split(":")[0])*60 + Integer.parseInt(s.split(":")[1]);
        if (timeSeen[mins]) return 0;
        timeSeen[mins] = true;
    }
    
    Integer firstTimeSeen = null, prevTimeSeen = null, minDiff = Integer.MAX_VALUE;
    for (int i=0;i<1440;i++) {
        if (!timeSeen[i]) continue;
        if (firstTimeSeen == null) {firstTimeSeen = i; prevTimeSeen = i;}
        else {
          minDiff = Math.min(minDiff, Math.min(i - prevTimeSeen, 1440 - i + prevTimeSeen));
          prevTimeSeen = i;
        }
    }
    
    minDiff = Math.min(minDiff, Math.min(prevTimeSeen - firstTimeSeen, 1440 - prevTimeSeen + firstTimeSeen));
    return minDiff;
}
 * @author het
 *
 */


//https://discuss.leetcode.com/topic/82582/java-sorting-with-a-sentinel-node
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        List<Time> times = new ArrayList<>();
        for (String tp : timePoints) {
            String[] strs = tp.split(":");
            times.add(new Time(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
        }
        Collections.sort(times);
        Time earlist = times.get(0);
        times.add(new Time(earlist.h + 24, earlist.m));   //times.add(new Time(earlist.h + 24, earlist.m));
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int diff = (int) Math.abs(times.get(i).getDiff(times.get(i + 1)));
            minDiff = Math.min(minDiff, diff);
        }
        return minDiff;
    }
class Time implements Comparable<Time> {
    int h;
    int m;
    public Time(int h, int m) {
        this.h = h;
        this.m = m;
    }
    
    public int compareTo(Time other) {
        if (this.h == other.h) {
            return this.m - other.m;
        }
        return this.h - other.h;
    }
    
    public int getDiff(Time other) {
        return (this.h - other.h) * 60 + (this.m - other.m);            
    }
}
public class LeetCode539_Minimum_Time_Difference {

}
