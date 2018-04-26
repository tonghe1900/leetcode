package alite.leetcode.xx4;

import java.util.Arrays;

/**
 * LettCode 452 - Minimum Number of Arrows to Burst Balloons

https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
There are a number of spherical balloons spread in two-dimensional space. 
For each balloon, provided input is the start and end coordinates of the horizontal diameter. 
Since it's horizontal, y-coordinates don't matter and hence the x-coordinates of start and end of 
the diameter suffice. Start is always smaller than end. There will be at most 104 balloons.
An arrow can be shot up exactly vertically from different points along the x-axis.
 A balloon with xstart and xend bursts by an arrow shot at x if xstart ≤ x ≤ xend.
  There is no limit to the number of arrows that can be shot. 
  An arrow once shot keeps travelling up infinitely. The problem is to find the minimum number 
  of arrows that must be shot to burst all balloons.
Example:
Input:
[[10,16], [2,8], [1,6], [7,12]]

Output:
2

Explanation:
One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two balloons).
https://discuss.leetcode.com/topic/66709/c-easy-understood-solution-sort
First, we sort balloons by increasing points.end (if ends are the same, then by increasing of points.start). Every time arrow shot points.end, say, points[i].second. If next balloon.start <= points[i].second, it is also shot, thus we continue.
http://xiadong.info/2016/11/leetcode-452-minimum-number-of-arrows-to-burst-balloons/
基本思路是每次尽可能多的刺破气球, 所以一开始从最左边的气球的右边缘发射一支箭, 否则最左边的气球就无法刺破了, 这样也能保证刺破尽可能多的气球.
但是问题在于如何判断哪个气球在"最左边", 不能以左边缘来进行判断, 因为有这种情况: (1,10),(2,5), 第一支箭应该从x=5发出而不是x=10, 所以排序时应该使用每个气球的右边缘来进行排序.
    int findMinArrowShots(vector<pair<int, int>>& points) {
        int count = 0, arrow = INT_MIN;
        sort(points.begin(), points.end(), mysort);
        for(int i = 0; i<points.size(); i++){
            if(arrow!=INT_MIN && points[i].first<=arrow){continue;} //former arrow shot points[i] 
            arrow = points[i].second; // new arrow shot the end of points[i]
            count++;
        }
        return count;
    }
    static bool mysort(pair<int, int>& a, pair<int, int>& b){
        return a.second==b.second?a.first<b.first:a.second<b.second;
    }
http://blog.jerkybible.com/2016/11/11/LeetCode-452-Minimum-Number-of-Arrows-to-Burst-Balloons/
这道题目是活动选择问题(Activity-Selection Problem)的变形。活动选择问题是《算法导论》里面关于贪心算法的第一个问题。这个问题是这样的。有一组活动，每个活动都有一个开始时间S和结束时间F，假设一个人在同一时间只能参加一个活动，找出出一个人可以参加的最多的活动数量。例如。


1


2


3


4


5


假设现在有6个活动，下面是6个活动的起始和结束时间。 


start[]  =  {1, 3, 0, 5, 8, 5};


finish[] =  {2, 4, 6, 7, 9, 9};


一个人一天最多能参见的活动为


{0, 1, 3, 4}


关于活动选择问题就不在详细解说了。这道题非常算是变形。将所有的气球按照终止位置排序，开始从前向后扫描。以第一个气球的终止位置为准，只要出现的气球起始位置小于这个气球的终止位置，代表可以一箭使这些气球全部爆炸；当出现一个气球的起始位置大于第一个气球的终止位置时再以这个气球的终止位置为准，找出所有可以再一箭爆炸的所有气球；以此类推。。。

public int findMinArrowShots(int[][] points) {


if (points == null || points.length == 0 || points[0].length == 0) {


return 0;


}


Arrays.sort(points, new Comparator<int[]>() {


public int compare(int[] a, int[] b) {


return a[1] - b[1];


}


});




long lastEnd = Long.MIN_VALUE;


int minArrows = 0;


for (int i = 0; i < points.length; i++) {


if (lastEnd < points[i][0]) {


lastEnd = points[i][1];


minArrows++;


}


}


return minArrows;


}

https://discuss.leetcode.com/topic/66548/concise-java-solution-tracking-the-end-of-overlapping-intervals
    public int findMinArrowShots(int[][] points) {
        if(points == null || points.length < 1) return 0;
        Arrays.sort(points, (a, b)->(a[0]-b[0]));
        int result = 1;
        int end = points[0][1];
        for(int i = 1; i < points.length; i ++) {
            if(points[i][0] > end) {
                result ++;
                end = points[i][1];
            } else {
                end = Math.min(end, points[i][1]);
            }
        }
        return result;
    }
https://discuss.leetcode.com/topic/66579/java-greedy-soution
He is actually sorting by start in ascending order, note that if(a[0]==b[0]) return a[1]-b[1]; means only when starts of a and b are equal we then look at ends.

when sorted by end value, you will always need a shot for the first balloon(at its end) because if it's a stand alone balloon you obviously have to otherwise it has other balloons overlapping, in which case you also have to use an arrow at its end otherwise you will miss this balloon. 2, If you have overlapping balloon, it's good, your arrow will destroy them, and this way, you are creating a sub-problem, which start with a new set of balloons with these characteristics. You keep doing until no balloon is left.
public int findMinArrowShots(int[][] points) {
 if(points==null || points.length==0 || points[0].length==0) return 0;
 Arrays.sort(points, new Comparator<int[]>() {
  public int compare(int[] a, int[] b) {
   if(a[0]==b[0]) return a[1]-b[1];
   else return a[0]-b[0];
  }
 });
 
 int minArrows = 1;
 int arrowLimit = points[0][1];
 for(int i=1;i<points.length;i++) {
  int[] baloon = points[i];
  if(baloon[0]<=arrowLimit) {
   arrowLimit=Math.min(arrowLimit, baloon[1]);
  } else {
   minArrows++;
   arrowLimit=baloon[1];
  }
 }
 return minArrows;
}
http://bookshadow.com/weblog/2016/11/06/leetcode-minimum-number-of-arrows-to-burst-balloons/
二维空间中有一组气球。对于每一个气球，输入其水平直径的起止点坐标。由于是水平的，不需要考虑y坐标，因而用x坐标表示起止点即可。起点总是小于终点。最多10^4个气球。
一支箭从x轴的不同点竖直射出。某气球的起止点坐标为xstart和xend，当xstart ≤ x ≤ xend时，该气球会被射中。射出的弓箭数目没有限制。弓箭可以保持无限移动。计算最少需要多少弓箭可以将所有气球射中
贪心算法（Greedy Algorithm）
按照气球的起点排序

变量emin记录当前可以一箭命中的气球终点坐标的最小值，初始化为+∞

遍历排序后的气球起始点坐标s, e

若emin < s，说明当前气球无法用一支箭射中，则令最终结果ans + 1，令emin=+∞

更新emin = min(emin, e)
    def findMinArrowShots(self, points):
        """
        :type points: List[List[int]]
        :rtype: int
        """
        ans = 0
        emin = MAXINT = 0x7FFFFFFF
        for s, e in sorted(points):
            if emin < s:
                ans += 1
                emin = MAXINT
            emin = min(emin, e)
        return ans + bool(points)

http://www.cnblogs.com/grandyang/p/6050562.html
http://blog.csdn.net/mebiuw/article/details/53096708
http://brookebian.blogspot.com/2016/11/452-minimum-number-of-arrows-to-burst.html
这个题需要考虑多种情况才能做


上边儿两种情况都需要两个arrow。
需要知道什么时候new 一个新的arrow：
如果按照start来排序，我们需要新arrow就是当前面的min(end) < cur.start
图一的a.end < c.start
图二的b.end < c.start

 public int findMinArrowShots(int[][] points) {  
     if(points.length == 0) return 0;  
     int n = points.length;  
     Arrays.sort(points, (a, b)->{  
       if(a[0] == b[0]) return Integer.compare(a[1],b[1]);  
       return Integer.compare(a[0],b[0]);  
     });  
     int p1 = 0;  
     int ans = 1;  
     // point 0's end    
     int end = points[0][1];  
     for(int i = 1; i < n; i++){  
       //when end is smaller than next start, we need another arrow  
       //we need to update end, if end is smaller than previous ones  
       end = Math.min(end,points[i][1]);  
       if( end < points[i][0]){  
         end = points[i][1];  
         ans++;  
       }  
     }  
     return ans;  
   }  
 * @author het
 *
 */
public class LeetCode452 {
	public int findMinArrowShots(int[][] points) {


		if (points == null || points.length == 0 || points[0].length == 0) {


		return 0;


		}


		Arrays.sort(points, new Comparator<int[]>() {


		public int compare(int[] a, int[] b) {


		return a[1] - b[1];


		}


		});




		long lastEnd = Long.MIN_VALUE;


		int minArrows = 0;


		for (int i = 0; i < points.length; i++) {


		if (lastEnd < points[i][0]) {


		lastEnd = points[i][1];


		minArrows++;


		}


		}


		return minArrows;


		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
