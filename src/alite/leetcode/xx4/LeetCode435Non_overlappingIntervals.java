package alite.leetcode.xx4;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * http://bookshadow.com/weblog/2016/10/30/leetcode-non-overlapping-intervals/
Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals
non-overlapping.
Note:
You may assume the interval's end point is always bigger than its start point.
Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
Example 1:
Input: [ [1,2], [2,3], [3,4], [1,3] ]

Output: 1

Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
Example 2:
Input: [ [1,2], [1,2], [1,2] ]

Output: 2

Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
Example 3:
Input: [ [1,2], [2,3] ]

Output: 0

Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
http://www.cnblogs.com/grandyang/p/6017505.html
这道题给了我们一堆区间，让我们求需要至少移除多少个区间才能使剩下的区间没有重叠，那么我们首先要给区间排序，
根据每个区间的start来做升序排序，然后我们开始要查找重叠区间，判断方法是看如果前一个区间的end大于后一个区间的start，
那么一定是重复区间，此时我们结果res自增1，我们需要删除一个，那么此时我们究竟该删哪一个呢，为了保证我们总体去掉的区间数最小，
我们去掉那个end值较大的区间，而在代码中，我们并没有真正的删掉某一个区间，而是用一个变量last指向上一个需要比较的区间，
我们将last指向end值较小的那个区间；如果两个区间没有重叠，那么此时last指向当前区间，继续进行下一次遍历
    int eraseOverlapIntervals(vector<Interval>& intervals) {
        int res = 0, n = intervals.size(), last = 0;
        sort(intervals.begin(), intervals.end(), [](Interval& a, Interval& b){return a.start < b.start;});
        for (int i = 1; i < n; ++i) {
            if (intervals[i].start < intervals[last].end) {
                ++res;
                if (intervals[i].end < intervals[last].end) last = i;
            } else {
                last = i;
            }
        }
        return res;
    }
Actually, the problem is the same as "Given a collection of intervals, find the maximum number of intervals that are non-overlapping." (the classic Greedy problem: Interval Scheduling). With the solution to that problem, guess how do we get the minimum number of intervals to remove? : )
Sorting Interval.end in ascending order is O(nlogn), then traverse intervals array to get the maximum number of non-overlapping intervals is O(n). Total is O(nlogn).
https://discuss.leetcode.com/topic/65594/java-least-is-most
Do you know why sort by end time instead of by start time?
[ [1,4], [2,3], [3,4] ], the interval with early start might be very long and incompatible with many intervals. But if we choose the interval that ends early, we'll have more space left to accommodate more intervals.
https://discuss.leetcode.com/topic/65584/java-o-nlogn-very-easy-solution
Sort Intervals by end.
Count valid intervals (non-overlapping).
Answer is len - count
    public int eraseOverlapIntervals(Interval[] intervals) {
        
        if(intervals.length == 0)  return 0;
        
        Arrays.sort(intervals, new myComparator());
        int end = intervals[0].end;
        int count = 1;
        
        for(int i = 0; i < intervals.length; i++) {
            
            if(intervals[i].start >= end) {
                end = intervals[i].end;
                count++;
            }
        }
        
        return intervals.length - count;
    }
    
    class myComparator implements Comparator<Interval> {
        
        public int compare(Interval a, Interval b) {
            return a.end - b.end;
        }
    }
http://blog.csdn.net/mebiuw/article/details/53054380
1、按照起始位置排序 
2、按照顺序，两个指针遍历，一前一后，如果当前位置和上一个位置不冲突就顺序平移两个指针（后指针的值给前指针，然后后指针移动到下一位），如果冲突的话，那么前指针则变成当前两个指针当中覆盖最小的一个（贪心所在），后指针移动到下一个位置就好
    public int eraseOverlapIntervals(Interval[] intervals) {
        // 按照起始位置进行排序
        Arrays.sort(intervals,(x,y)->(x.start)-(y.start));
        int count=0,j=0;
        // 贪心法，如果上一个位置j和当前位置i冲突了，那么进行判断，
如果当前位置的末尾小于上一个边界的末尾，那么删除上一个位置
（因为覆盖的更少，每步选择最有可能不造成重复的），反之如果当前位置尾部覆盖的更多，
那么就删除i的位置。删除的方式通过控制j的取值进行
        for(int i=1;i<intervals.length;i++) {
            if(intervals[j].end>intervals[i].start){
                j=intervals[i].end<intervals[j].end?i:j;
                count++;
            }else
            //没有重复
                j=i;

        }
        return count;

    }

https://discuss.leetcode.com/topic/65828/java-solution-with-clear-explain
First we sort the array by below rules
1) sort by end, smaller end in front
2) if end is same, sort by start, bigger start in front
Then, visited array by end. If we visited next closest end interval, access the bigger start priority.
when end is same, the bigger start one has greater possibility of convergence. So we can put those front. Actually, we only sort by end is ok, like below
Arrays.sort(intervals, new Comparator<Interval>() {
    @Override
    public int compare(Interval o1, Interval o2) {
        return o1.end - o2.end;  //only sort by end
    }
});
public int eraseOverlapIntervals(Interval[] intervals) {
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                if (o1.end != o2.end) return o1.end - o2.end;  //first sort by end
                return o2.start - o1.start;  //second sort by start
            }
        });

        int end = Integer.MIN_VALUE;
        int count = 0;
        for (Interval interval : intervals) {
            if (interval.start >= end) end = interval.end;
            else count++;
        }

        return count;
    }
贪心算法（Greedy Algorithm）
优先按照终点，然后按照起点从小到大排序
利用变量end记录当前的区间终点，end初始化为负无穷
遍历排序后的区间，若当前区间的起点≥end，则更新end为当前区间的终点，并将计数器ans+1
ans为可以两两互不相交的最大区间数，len(intervals) - ans即为答案
    def eraseOverlapIntervals(self, intervals):
        """
        :type intervals: List[Interval]
        :rtype: int
        """
        invs = sorted((x.end, x.start) for x in intervals)
        end = -0x7FFFFFFF
        ans = 0
        for e, s in invs:
            if s >= end:
                end = e
                ans += 1
        return len(intervals) - ans

https://hintpine.wordpress.com/2016/10/31/leetcode-non-overlapping-intervals/
First, let’s define solution to be a group who has maximum non-overlapping intervals, and the intervals are sorted by end values. There might be multiple solutions to the input. For example, if the input is [[1,2], [1,3], [5,6]], there are two solutions, [[1,2], [5,6]] and [[1,3], [5,6]].
Now, sort the intervals by end values. Then, we can say the first interval must belong to some solution.
Proof:
Suppose the first interval, name it A, does not belong to any solution. Then, use B to denote the first interval of solution. It must be either one of following cases:
B is non-overlapping with A. In this case, A can be safely inserted before B to form a new solution.
B is overlapping with A. Because all other intervals of the solution don’t overlap with B, we can safely replace B with A to form a new solution.
So, both cases contradict the assumption. Thus, A must belong to some solution. Q.E.D.
Since A is fixed, in that solution all those intervals who overlap with A definitely don’t belong to the solution. So, we can safely remove those intervals until an interval whose start value is greater than or equal to A’s end value. Then, as proven, we can add that interval into the solution.
Repeat the above way, we can generate a solution, and the count of those removed intervals is the final return value.
 * @author het
 *
 */
class Interval{
	int start;
	int end;
}
public class LeetCode435Non_overlappingIntervals {
	 int eraseOverlapIntervals(List<Interval> intervals) {
	        int res = 0, n = intervals.size(), last = 0;  // start>=0 && end >=0
	       Collections.sort(intervals, new Comparator<Interval>(){

			@Override
			public int compare(Interval o1, Interval o2) {
				
				return new Integer(o1.start).compareTo(new Integer(o2.start));
			}
	    	   
	       });
	        for (int i = 1; i < n; ++i) {
	            if (intervals.get(i).start < intervals.get(last).end) {
	                ++res;
	                if (intervals.get(i).end <  intervals.get(last).end) last = i;
	            } else {
	                last = i;
	            }
	        }
	        return res;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
