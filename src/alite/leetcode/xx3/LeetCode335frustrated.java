package alite.leetcode.xx3;
/**
 * leetcode 335 - Self Crossing

leetcode Self Crossing - 细语呢喃
You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to 
the north, then x[1] metres to the west,x[2] metres to the south, x[3] metres to the east and so on. In other words, after each move your direction changes counter-clockwise.
Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.
Example 1:
Given x = [2, 1, 1, 2]
Return true (self crossing)
Example 2:
Given x = [1, 2, 3, 4]
Return false (not self crossing)
Example 3:
Given x = [1, 1, 1, 1]
Return true (self crossing)

题意：给定一个数组x，代表行走的距离，最初的方向是北，每走一次就按逆时针顺序变化方向（北、西、南、东）
要求判断走过的路径是否交叉
思路：
There are only 3 scenarios where it won’t cross itself.
The distances of the moves parallel to each other keeps going up (growing spiral).
The distances of the moves parallel to each other keeps going down (shrinking spiral).
The distances of the moves parallel to each other first keeps going up, then keeps going down
 (shrinking spiral inside of the growing spiral), and never goes up.
from  https://leetcode.com/discuss/88038/java-o-n-o-1-0ms-solution-with-explanation
http://www.cnblogs.com/tonix/p/5272052.html

https://leetcode.com/discuss/89336/best-submission-searching-for-the-crossing-patterns-the-key
After drawing a few crossing cases ourselves, we can simply find out there are two basic patterns:
x[i-1]<=x[i-3] && x[i]>=x[i-2] the ending circle line cross the beginning circle line in one circle;
i>=5 && x[i-1]<=x[i-3] && x[i]>=x[i-2]-x[i-4] the second line of the next circle cross the the beginning of the previous circle between two adjacent circles;
But still that is not over yet, how about some special cases? How about the first line of the next circle and the previous circle? Yeah, the beginning line of the next circle can overlap the the first line of the previous circle - another two adjacent circles case:
i>=4 && x[i-1]==x[i-3] && x[i]>=x[i-2]-x[i-4]
Quite straightforward. Then we can test our patterns now, however soon we will find out that the second cases is not strong enough to cover all possible situations - the second line of the next circle crossing the previous circle at the its first line
[3,3,3,2,1,1] is an example here, so x[i-2]>=x[i-4] then must be added to our conditions;
[3,3,4,4,10,4,4,,3,3] is another typical example for x[i-3]<=x[i-1]+x[i-5] condition, which also should be added to make the constrained conditions stronger;
bool isSelfCrossing(int* x, int size)
{
    for(int i = 3; i < size; i++)
    {
        if(x[i]>=x[i-2] && x[i-1]<=x[i-3]) return true;
        if(i>=4 && x[i-1]==x[i-3] && x[i]+x[i-4]>=x[i-2]) return true;
        if(i>=5 && x[i-2]-x[i-4]>=0 && x[i]>=x[i-2]-x[i-4] && x[i-1]>=x[i-3]-x[i-5] && x[i-1]<=x[i-3]) return true;
    }
    return false;
}
https://leetcode.com/discuss/88054/java-oms-with-explanation
// Categorize the self-crossing scenarios, there are 3 of them: 
// 1. Fourth line crosses first line and works for fifth line crosses second line and so on...
// 2. Fifth line meets first line and works for the lines after
// 3. Sixth line crosses first line and works for the lines after
public class Solution {
    public boolean isSelfCrossing(int[] x) {
        int l = x.length;
        if(l <= 3) return false;

        for(int i = 3; i < l; i++){
            if(x[i] >= x[i-2] && x[i-1] <= x[i-3]) return true;  //Fourth line crosses first line and onward
            if(i >=4)
            {
                if(x[i-1] == x[i-3] && x[i] + x[i-4] >= x[i-2]) return true; // Fifth line meets first line and onward
            }
            if(i >=5)
            {
                if(x[i-2] - x[i-4] >= 0 && x[i] >= x[i-2] - x[i-4] && x[i-1] >= x[i-3] - x[i-5] && x[i-1] <= x[i-3]) return true;  // Sixth line crosses first line and onward
            }
        }
        return false;
    }
}

不交叉的有以下三种情况
平行移动的距离是不断的增加的（螺旋形上升）
平行移动的距离是不断的减少的（螺旋形下降）
平行移动的距离先增加后减少，并且再也不增加。

    public boolean isSelfCrossing(int[] x) {

        int n = x.length;

  if (n < 4) return false;

  int t1 = 0, t2 = x[0], t3 = x[1], t4 = x[2], t5;

  boolean increase = t4 > t2 ? true : false;

  for (int i = 3; i < n; i++) {

   t5 = x[i];

   if (increase && t3 >= t5) {

    if (t5 + t1 < t3 || i + 1 < n && x[i + 1] + t2 < t4)

     increase = false;

    else if (i + 1 < n)

     return true;

   }

   else if (!increase && t3 <= t5)

    return true;

   t1 = t2;

   t2 = t3;

   t3 = t4;

   t4 = t5;

  }

  return false;

    }
https://leetcode.com/discuss/88038/java-o-n-o-1-0ms-solution-with-explanation
    public boolean isSelfCrossing(int[] x) {
        int a1, a2, a3, a4, a5;

        // if it's increasing
        boolean up = false;

        if (x.length < 4) {
            return false;
        }

        a1 = 0;
        a2 = x[0];
        a3 = x[1];
        a4 = x[2];

        if (a2 < a4) {
            up = true;
        }
        else {
            up = false;
        }

        for (int i = 3; i < x.length; i++) {
            a5 = x[i];

            if (!up && a5 >= a3) {
                return true;
            }
            else if (up && a5 <= a3) {
                // succeeded in turning into decreasing
                if (a5 + a1 < a3 || (i + 1 < x.length && x[i + 1] + a2 < a4)) {
                    up = false;
                }
                // not end yet
                else if (i + 1 < x.length) {
                    return true;
                }
            }

            a1 = a2;
            a2 = a3;
            a3 = a4;
            a4 = a5;
        }

        return false;
    }
https://leetcode.com/discuss/91094/simple-java-solution
    public boolean isSelfCrossing(int[] x) {
        if (x.length <= 3) {
            return false;
        }
        int i = 2;
        // keep spiraling outward
        while (i < x.length && x[i] > x[i - 2]) {
            i++;
        }
        if (i >= x.length) {
            return false;
        }
        // transition from spiraling outward to spiraling inward
        if ((i >= 4 && x[i] >= x[i - 2] - x[i - 4]) ||
                (i == 3 && x[i] == x[i - 2])) {
            x[i - 1] -= x[i - 3];
        }
        i++;
        // keep spiraling inward
        while (i < x.length) {
            if (x[i] >= x[i - 2]) {
                return true;
            }
            i++;
        }
        return false;
    }
https://leetcode.com/discuss/88888/java-solution-based-on-spiral-direction-status
public static boolean isSelfCrossing(int[] x) {
    if (x.length < 4)
        return false;

    boolean inside = false;
    for (int i = 3; i < x.length; i++) {
        if(inside) {
            if (x[i] >= x[i - 2])
                return true;
            continue;
        }

        if(x[i-1] > x[i-3])
            continue;

        int x5 = i>=5 ? x[i-5] : 0;
        int x4 = i>=4 ? x[i-4] : 0;
        if(x[i-1] >= x[i-3] - x5) {
            if(x[i] >= x[i-2] - x4)
                return true;
        }else {
            if(x[i] >= x[i-2])
                return true;
        }
        inside=true;
    }
    return false;
}
For this question, to keep the line not crossed, it can be in following conditions:
Keep spiraling outside.
Keep spiraling inside.
Not crossing during transition from outside spiral into inside spiral.
And one observation is once it starts to spiral inside, it will never spiral outside.
Based on this observation, we keep one flag: inside which is initialized to false,
During spiraling outside, and inside, the check is very simple: just check x[i] < x[i-2] for inside spiral. In outside spiral phase, as long as x[i-1] > x[i-3], it's not possible to cross in this step.
Once x[i-1] > x[i-3] condition is broken, we will trigger the transition period: In this period, it has two conditions,
If this turn back line is towards line x[i-5] (possible cross x[i-5])
If this turn back line is not towards line x[i-5]. in that case, it will go towards x[i-3] instead.
We need to calculate the max line for x[i] for the two cases.
When i<4 and i<5 corner case, to avoid many if/else we just prepend two additional steps as if they are moving 0 length. So assign x4 and x5 to 0 respectively.
This solution compare to other solution based on 3 different crossing condition, it's slight better as it will only look back x[i-4] and x[i-4] during transition period (once only). In other two phases, it will only compare two edges.

Read full article from leetcode Self Crossing - 细语呢喃
 * @author het
 *
 */
public class LeetCode335frustrated {
//	不交叉的有以下三种情况
//	平行移动的距离是不断的增加的（螺旋形上升）
//	平行移动的距离是不断的减少的（螺旋形下降）
//	平行移动的距离先增加后减少，并且再也不增加。

	    public boolean isSelfCrossing(int[] x) {

	        int n = x.length;

	  if (n < 4) return false;

	  int t1 = 0, t2 = x[0], t3 = x[1], t4 = x[2], t5;

	  boolean increase = t4 > t2 ? true : false;

	  for (int i = 3; i < n; i++) {

	   t5 = x[i];

	   if (increase && t3 >= t5) {

	    if (t5 + t1 < t3 || i + 1 < n && x[i + 1] + t2 < t4)

	     increase = false;

	    else if (i + 1 < n)

	     return true;

	   }

	   else if (!increase && t3 <= t5)

	    return true;

	   t1 = t2;

	   t2 = t3;

	   t3 = t4;

	   t4 = t5;

	  }

	  return false;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
