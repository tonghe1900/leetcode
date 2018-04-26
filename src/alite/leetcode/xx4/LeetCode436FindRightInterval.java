package alite.leetcode.xx4;

import java.util.Map;
import java.util.TreeMap;

/**
 * http://bookshadow.com/weblog/2016/10/30/leetcode-find-right-interval/
Given a set of intervals, for each of the interval i, check if there exists an interval j 
whose start point is bigger than or equal to the end point of the interval i, which can be called that j is on the "right" of i.
For any interval i, you need to store the minimum interval j's index, 
which means that the interval j has the minimum start point to build the "right" relationship for interval i. 
If the interval j doesn't exist, store -1 for the interval i. Finally, you need output the stored value of each interval as an array.
Note:
You may assume the interval's end point is always bigger than its start point.
You may assume none of these intervals have the same start point.
Example 1:
Input: [ [1,2] ]

Output: [-1]

Explanation: There is only one interval in the collection, so it outputs -1.
Example 2:
Input: [ [3,4], [2,3], [1,2] ]

Output: [-1, 0, 1]

Explanation: There is no satisfied "right" interval for [3,4].
For [2,3], the interval [3,4] has minimum-"right" start point;
For [1,2], the interval [2,3] has minimum-"right" start point.
Example 3:
Input: [ [1,4], [2,3], [3,4] ]
1   2  3
Output: [-1, 2, -1]

Explanation: There is no satisfied "right" interval for [1,4] and [3,4].
For [2,3], the interval [3,4] has minimum-"right" start point.
排序（Sort）+ 二分查找（Binary Search）
按照区间起点排序，然后二分查找即可。
    def findRightInterval(self, intervals):
        """
        :type intervals: List[Interval]
        :rtype: List[int]
        """
        invs = sorted((x.start, i) for i, x in enumerate(intervals))
        ans = []
        for x in intervals:
            idx = bisect.bisect_right( invs, (x.end,) )
            ans.append(invs[idx][1] if idx < len(intervals) else -1)
        return ans
https://discuss.leetcode.com/topic/65817/java-clear-o-n-logn-solution-based-on-treemap
    public int[] findRightInterval(Interval[] intervals) {
        int[] result = new int[intervals.length];
        java.util.NavigableMap<Integer, Integer> intervalMap = new TreeMap<>();
        
        for (int i = 0; i < intervals.length; ++i) {
            intervalMap.put(intervals[i].start, i);    
        }
        
        for (int i = 0; i < intervals.length; ++i) {
            Map.Entry<Integer, Integer> entry = intervalMap.ceilingEntry(intervals[i].end);
            result[i] = (entry != null) ? entry.getValue() : -1;
        }
        
        return result;
    }
https://discuss.leetcode.com/topic/65641/commented-java-o-n-logn-solution-sort-binary-search
Time compexity: n*log(n)
n*log(n) for sorting
log(n) for binary search X n times is n*log(n)
Space complexity: n
n for auxilliary array
Algorithm:
Clone intervals and update end with index.
Sort clone-intervals by start
Iterate over each interval and find the right by binary searching the clone-intervals.
If found, shove the end i.e., the original index of the right interval from clone-intervals into the output array.
public int[] findRightInterval(Interval[] intervals) {
        
        int n;
        // boundary case
        if (intervals == null || (n = intervals.length) == 0) return new int[]{};
        
        // output
        int[] res = new int[intervals.length];
        // auxilliary array to store sorted intervals
        Interval[] sintervals = new Interval[n];
        
        // sintervals don't have any use of 'end', so let's use it for tracking original index
        for (int i = 0; i < n; ++i) {
            sintervals[i] = new Interval(intervals[i].start, i);
        }
        
        // sort
        Arrays.sort(sintervals, (a, b)->a.start-b.start);
        
        int i = 0;
        for (; i < n; ++i) {
            int key = intervals[i].end;
            // binary search in sintervals for key
            int l = 0, r = n - 1;
            int right = -1;
            while (l <= r) {
                int m = l + (r - l) / 2;
                if (sintervals[m].start == key) {
                    right = sintervals[m].end; // original index is stored in end
                    break;
                } else if (sintervals[m].start < key) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            
            // if we haven't found the key, try looking for 'start' that's just greater
            if ((right == -1) && (l < n) && (sintervals[l].start > key)) {
                right = sintervals[l].end; // original index is stored in end
            }
            
            res[i] = right;
        }
        
        return res;
    }
https://discuss.leetcode.com/topic/65585/java-sweep-line-solution-o-nlogn
wrapper class: Point
value
flag: 1 indicates start, 2 indicates end
index: original pos in intervals array
Comparable: sort by value ascending, end in front of start if they have same value.
Iterate intervals array and fill a points list, then sort it
Iterate points list, since the sequence will be "order by position, and end will come before start".
whenever meet a end point, keep a list(prevIdxs) before next start, save original index of curr interval to the list.
whenever meet a start point, this start point is the right interval to the intervals in the list (prevIdxs). Take out each index in it and update to result.
class Point implements Comparable<Point>{
    int val;
    int flag; //1 start, 0 end
    int index;
    public Point(int val, int flag, int index) {
        this.val = val;
        this.flag = flag;
        this.index = index;
    }
    public int compareTo(Point o) {
        if (this.val == o.val) return this.flag - o.flag; //end in front of start
        return this.val - o.val;
    }
}
public int[] findRightInterval(Interval[] intervals) {
    if (intervals == null || intervals.length == 0) return new int[]{};
    
    int[] res = new int[intervals.length];
    Arrays.fill(res, -1);
    
    List<Point> points = new ArrayList<>();
    for (int i = 0; i < intervals.length; i++) {
        points.add(new Point(intervals[i].start, 1, i));
        points.add(new Point(intervals[i].end, 0, i));
    }
    
    Collections.sort(points);
    
    int prevEnd = 0;
    List<Integer> prevIdxs = new ArrayList<>();
    
    for (Point point: points) {
        if (point.flag == 1) {
                for (Integer prevIdx: prevIdxs) {
                   res[prevIdx] = point.index; 
                }
                prevIdxs = new ArrayList<>();
        } else {
            prevEnd = point.val;
            prevIdxs.add(point.index);
        }
    }
    
    return res;
}
http://www.cnblogs.com/grandyang/p/6018581.html
Non-overlapping Intervals
Data Stream as Disjoint Intervals 
Insert Interval
Merge Intervals
 * @author het
 *
 */
public class LeetCode436FindRightInterval {
	public int[] findRightInterval(Interval[] intervals) {
        int[] result = new int[intervals.length];
        java.util.NavigableMap<Integer, Integer> intervalMap = new TreeMap<>();
        
        for (int i = 0; i < intervals.length; ++i) {
            intervalMap.put(intervals[i].start, i);    
        }
        
        for (int i = 0; i < intervals.length; ++i) {
            Map.Entry<Integer, Integer> entry = intervalMap.ceilingEntry(intervals[i].end);
            result[i] = (entry != null) ? entry.getValue() : -1;
        }
        
        return result;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
