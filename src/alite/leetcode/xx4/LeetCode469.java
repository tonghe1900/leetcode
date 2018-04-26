package alite.leetcode.xx4;



import java.util.Arrays;
import java.util.Comparator;
import java.util.List;





/**
 * 
 * LeetCode 469 - Convex Polygon
 * http://bookshadow.com/weblog/2016/12/04/leetcode-convex-polygon/
Given a list of points that form a polygon when joined sequentially, 
find if this polygon is convex (Convex polygon definition).
Note:
There are at least 3 and at most 10,000 points.
Coordinates are in the range -10,000 to 10,000.
You may assume the polygon formed by given points is always a simple polygon
 (Simple polygon definition). In other words,
  we ensure that exactly two edges intersect at each vertex, and that edges otherwise don't intersect each other.
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
    def isConvex(self, points):
        last, tmp = 0, 0
        for i in xrange(2, len(points) + 3):
            p0, p1, p2 = points[(i - 2) % len(points)], points[(i - 1) % len(points)], points[i % len(points)]
            tmp = (p1[0]-p0[0])*(p2[1]-p0[1])-(p2[0]-p0[0])*(p1[1]-p0[1])
            if tmp:
                if last * tmp < 0:
                    return False
                last = tmp
        return True
http://blog.csdn.net/jmspan/article/details/53460969
    public boolean isConvex(List<List<Integer>> points) {  
        long prev = 0;  
        int n = points.size();  
        for(int i = 0; i < n; i++) {  
            int[][] m = new int[2][];  
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



https://discuss.leetcode.com/topic/70664/c-7-line-o-n-solution-to-check-convexity-with-cross-product-of-adajcent-vectors-detailed-explanation
The key observation for convexity is that vector p[i+1]-p[i] always turns to the same direction to p[i+2]-p[i] formed by any 3 sequentially adjacent vertices, i.e., cross product (p[i+1]-p[i]) x (p[i+2]-p[i]) does not change sign when traversing sequentially along polygon vertices.
Note that for any vectors v1, v2:
v1 x v2 = det([v1, v2]),
which is the determinant of 2*2 matrix [v1,v2]. And the sign of det([v1, v2]) represents the positive z-direction of right-hand system from v1 to v2. So det([v1, v2]) >= 0 if and only if v1 turns at most 180 degrees counterclockwise to v2.


https://en.wikipedia.org/wiki/Complex_polygon
a complex polygon is a polygon which has a boundary comprising discrete circuits, such as a polygon with a hole in it.[2]
Self-intersecting polygons are also sometimes included among the complex polygons.[3] Vertices are only counted at the ends of edges, not where edges intersect in space.
https://en.wikipedia.org/wiki/Simple_polygon
 * @author het
 *
 */

class Point2D implements Comparable<Point2D> {

    /**
     * Compares two points by x-coordinate.
     */
    public static final Comparator<Point2D> X_ORDER = new XOrder();

    /**
     * Compares two points by y-coordinate.
     */
    public static final Comparator<Point2D> Y_ORDER = new YOrder();

    /**
     * Compares two points by polar radius.
     */
    public static final Comparator<Point2D> R_ORDER = new ROrder();

    /**
     * Compares two points by polar angle (between 0 and 2pi) with respect to this point.
     */
    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();

    /**
     * Compares two points by atan2() angle (between -pi and pi) with respect to this point.
     */
    public final Comparator<Point2D> ATAN2_ORDER = new Atan2Order();

    /**
     * Compares two points by distance to this point.
     */
    public final Comparator<Point2D> DISTANCE_TO_ORDER = new DistanceToOrder();

    private final double x;    // x coordinate
    private final double y;    // y coordinate

    /**
     * Initializes a new point (x, y).
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @throws IllegalArgumentException if either <tt>x</tt> or <tt>y</tt>
     *    is <tt>Double.NaN</tt>, <tt>Double.POSITIVE_INFINITY</tt> or
     *    <tt>Double.NEGATIVE_INFINITY</tt>
     */
    public Point2D(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) x = 0.0;  // convert -0.0 to +0.0
        if (y == 0.0) y = 0.0;  // convert -0.0 to +0.0
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate
     */
    public double x() {
        return x;
    }
    
    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public double y() {
        return y;
    }

    /**
     * Returns the polar radius of this point.
     * @return the polar radius of this point in polar coordiantes: sqrt(x*x + y*y)
     */
    public double r() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Returns the angle of this point in polar coordinates.
     * @return the angle (in radians) of this point in polar coordiantes (between -pi/2 and pi/2)
     */
    public double theta() {
        return Math.atan2(y, x);
    }

    /**
     * Returns the angle between this point and that point.
     * @return the angle in radians (between -pi and pi) between this point and that point (0 if equal)
     */
    private double angleTo(Point2D that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        return Math.atan2(dy, dx);
    }

    /**
     * Is a->b->c a counterclockwise turn?
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclocwise } turn.
     */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }

    /**
     * Returns twice the signed area of the triangle a-b-c.
     * @param a first point
     * @param b second point
     * @param c third point
     * @return twice the signed area of the triangle a-b-c
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        return (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
    }

    /**
     * Returns the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the Euclidean distance between this point and that point
     */
    public double distanceTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Returns the square of the Euclidean distance between this point and that point.
     * @param that the other point
     * @return the square of the Euclidean distance between this point and that point
     */
    public double distanceSquaredTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx*dx + dy*dy;
    }

    /**
     * Compares this point to that point by y-coordinate, breaking ties by x-coordinate.
     * @param that the other point
     * @return { a negative integer, zero, a positive integer } if this point is
     *    { less than, equal to, greater than } that point
     */
    public int compareTo(Point2D that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }

    // compare points according to their polar radius
    private static class ROrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double delta = (p.x*p.x + p.y*p.y) - (q.x*q.x + q.y*q.y);
            if (delta < 0) return -1;
            if (delta > 0) return +1;
            return 0;
        }
    }
 
    // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
    private class Atan2Order implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double angle1 = angleTo(q1);
            double angle2 = angleTo(q2);
            if      (angle1 < angle2) return -1;
            else if (angle1 > angle2) return +1;
            else                      return  0;
        }
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                if      (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else                          return  0;
            }
            else return -ccw(Point2D.this, q1, q2);     // both above or below

            // Note: ccw() recomputes dx1, dy1, dx2, and dy2
        }
    }

    // compare points according to their distance to this point
    private class DistanceToOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            double dist1 = distanceSquaredTo(p);
            double dist2 = distanceSquaredTo(q);
            if      (dist1 < dist2) return -1;
            else if (dist1 > dist2) return +1;
            else                    return  0;
        }
    }


    /**
     * Does this point equal y?
     * @param other the other point
     * @return true if this point equals the other point; false otherwise
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Point2D that = (Point2D) other;
        return this.x == that.x && this.y == that.y;
    }

    /**
     * Return a string representation of this point.
     * @return a string representation of this point in the format (x, y)
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns an integer hash code for this point.
     * @return an integer hash code for this point
     */
    public int hashCode() {
        int hashX = ((Double) x).hashCode();
        int hashY = ((Double) y).hashCode();
        return 31*hashX + hashY;
    }

   

   
public static class LeetCode469 {
	
	
	 private  boolean isConvex(Point2D[] points) {
		 
		 int N = points.length;
	        if (N <= 2) return true;

	        // preprocess so that points[0] has lowest y-coordinate; break ties by x-coordinate
	        // points[0] is an extreme point of the convex hull
	        // (alternatively, could do easily in linear time)
	        Arrays.sort(points);

	        // sort by polar angle with respect to base point points[0],
	        // breaking ties by distance to points[0]
	        Arrays.sort(points, 1, N, points[0].polarOrder());

	        
	        

	        for (int i = 0; i < N; i++) {
	            if (Point2D.ccw(points[i], points[(i+1) % N], points[(i+2) % N]) <= 0) {
	                return false;
	            }
	        }
	        return true;
	    }
//	 if tmp:
////         if last * tmp < 0:
////             return False
////         last = tmp
	 // my version
	 public boolean isConvex1(Point2D[] points) {  
	        long prev = 0;  
	        int N = points.length;  
	        for(int i = 0; i < N; i++) {  
	        	int cur = Point2D.ccw(points[i], points[(i+1) % N], points[(i+2) % N]);
	        		
	           
	            if (cur * prev < 0) return false;  
	            prev = cur;  
	        }  
	        return true;  
	    }  
//	    private long det(int[][] m) {  
//	        return m[0][0] * m[1][1] - m[0][1] * m[1][0];  
//	    } 
	 
	 
	 public boolean isConvex(List<List<Integer>> points) {  
	        long prev = 0;  
	        int n = points.size();  
	        for(int i = 0; i < n; i++) {  
	            int[][] m = new int[2][];  
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
	 
	 //m[0][0] * m[1][1] - m[0][1] * m[1][0]; 
	    private long det(int[][] m) {  
	        return m[0][0] * m[1][1] - m[0][1] * m[1][0];  //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	        
	    } 
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	    //m[0][0] * m[1][1] - m[0][1] * m[1][0];  
	 
	 
	
}

public static void main(String[] args) {
	
//	Example 1:
//		[[0,0],[0,1],[1,1],[1,0]]
//
//		Answer: True
//
//		Explanation:
//		Example 2:
//		[[0,0],[0,10],[10,10],[10,0],[5,5]]
	Point2D[] points = new Point2D[]{new Point2D(0,0),new Point2D(0,10),new Point2D(10,10),new Point2D(10,0),new Point2D(5,5)};
//	get the cross product of the sequential input edge a, b as tmp, then:
//		if tmp == 0, a -> b 180° on the same line;
//		elif tmp > 0, a -> b clockwise;
//		else tmp < 0, a -> anticlockwise;
//		tmp = (p1[0]-p0[0])(p2[1]-p0[1])-(p2[0]-p0[0])(p1[1]-p0[1])
//		Update instead of just maintaining the sequential cross product result, any of the two cross product shouldn't multiplies to minus:
//		    def isConvex(self, points):
//		        last, tmp = 0, 0
//		        for i in xrange(2, len(points) + 3):
//		            p0, p1, p2 = points[(i - 2) % len(points)], points[(i - 1) % len(points)], points[i % len(points)]
//		            tmp = (p1[0]-p0[0])*(p2[1]-p0[1])-(p2[0]-p0[0])*(p1[1]-p0[1])
//		            if tmp:
//		                if last * tmp < 0:
//		                    return False
//		                last = tmp
//		        return True
// TODO Auto-generated method stub
 System.out.println(new LeetCode469().isConvex(points));
 
// Example 1:
//	 [[0,0],[0,1],[1,1],[1,0]]
//
//	 Answer: True
//
//	 Explanation:
//	 Example 2:
//	 [[0,0],[0,10],[10,10],[10,0],[5,5]]
//
//	 Answer: False

}
}
