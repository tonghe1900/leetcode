package alite.leetcode;

import java.util.List;

/**
 * Given a list of points that form a polygon when joined sequentially, find if this polygon is convex (Convex polygon definition).
Note:
There are at least 3 and at most 10,000 points.
Coordinates are in the range -10,000 to 10,000.
You may assume the polygon formed by given points is always a simple polygon (Simple polygon definition). In other words, we ensure that exactly two edges intersect at each vertex, and that edges otherwise don't intersect each other.
Example 1:
[[0,0],[0,1],[1,1],[1,0]]

Answer: True

Explanation:
Example 2:
[[0,0],[0,10],[10,10],[10,0],[5,5]]

Answer: False

Explanation:
题目大意：
给定一组点，顺序相连可以组成一个多边形。判断多边形是否是凸包。
注意：
最少3个，最多10000个点
坐标在-10,000 到 10,000之间。
你可以假设组成的多边形总是简单多边形。换言之，我们确保每个顶点都是两条边的交点，其他边不会相互交叉。
解题思路：
遍历顶点，判断相邻三个顶点A、B、C组成的两个向量(AB, AC)的叉积是否同负同正。
http://blog.csdn.net/liuchenjane/article/details/53455874

只需按逆时针一次判断一边和一点的关系，若叉积>0，则表示存在大于180的内角，即为凹多边形。
对于任意两点组成的直线，第3点相对于这条直线都有3种关系，在线上，在线的左边，在线的右边。如果一个多边形是凸的，则对于多边形的任意一条边，其他的n-2个点必定都在边的同一侧。
假设有两点A(a[0],a[1])，B(b[0],b[1])，则直线AB的表示：(y-a[1])/(x-a[0])=(b[1]-a[1])/(b[0]-a[0])。对于任意点C(c[0],c[1]),要判断点C相对于直线AB的关系，将x=c[0]带入，y=(b[1]-a[1])/(b[0]-a[0])*(c[0]-a[0])+a[1]；则y-c[1]=(b[1]-a[1])/(b[0]-a[0])*(c[0]-a[0])+a[1]-c[1]；化简，判断符号。如果为正，说明，点C在直线AB的上边，为负在直线的下边，=0表示在直线上。

https://discuss.leetcode.com/topic/70643/i-believe-this-time-it-s-far-beyond-my-ability-to-get-a-good-grade-of-the-contest
Line tmp = (p1[0]-p0[0])*(p2[1]-p0[1])-(p2[0]-p0[0])*(p1[1]-p0[1]) is to calculate the determinant of 2x2 matrix det([p1-p0,p2-p0]).
Note that for any two 2D vectors v1=(x1,y1), v2=(x2,y2), the cross product is calculated by the determinant of 2x2 matrix [v1,v2]:
v1 x v2 = det([v1, v2]),
where the sign represents the positive direction of z-axis determined by right-hand system from v1 to v2. So det([v1, v2]) >= 0if and only if v1 can be rotated at most 180 degrees counterclockwise to v2.

get the cross product of the sequential input edge a, b as tmp, then:
if tmp == 0, a -> b 180° on the same line;
elif tmp > 0, a -> b clockwise;
else tmp < 0, a -> anticlockwise;
tmp = (p1[0]-p0[0])(p2[1]-p0[1])-(p2[0]-p0[0])(p1[1]-p0[1])
Update instead of just maintaining the sequential cross product result, any of the two cross product shouldn't multiplies to minus:
 * @author het
 *  m [j] = new int []{points.get((i+j+1)%n).get(0) - points.get(i).get(0), 
 *         points.get((i+j+1)%n).get(1) - points.get(i).get(1)}   j = 0 - 1
 *         
 *         long cur = det(m)       det(m) = m[0][0]*m[1][1 ]  - m[1][0]*m[0][1]
 *         if(cur*prev <  0) return false;
 *         prev = cur;
  */
public class LeetCode469ConvexPolygon_to_be_reviewed {
	public boolean isConvex(List<List<Integer>> points) {  
        long prev = 0;  
        int n = points.size();  
        for(int i = 0; i < n; i++) {  
            int[][] m = new int[2][2];  
            for(int j = 0; j < 2; j++) {  
                m[j] = new int[] {points.get((i+j+1)%n).get(0) - points.get(i).get(0),  
                        points.get((i+j+1)%n).get(1) - points.get(i).get(1)};  
            }  
            long cur = det(m);  
            if (cur * prev < 0) return false;  
            prev = cur;  
        }  
        return true;  
    }  
    private long det(int[][] m) {  
        return m[0][0] * m[1][1] - m[0][1] * m[1][0];  
    } 
    // x1*y2 - x2*y1
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
