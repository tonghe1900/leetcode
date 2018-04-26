package alite.leetcode.xx3.extra;

import java.util.HashSet;

///**
// * LeetCode 356 - Line Reflection
//
//[LeetCode] Line Reflection 直线对称 - Grandyang - 博客园
//Given n points on a 2D plane, find if there is such a line parallel to y-axis that reflect the given set of points.
//Example 1:
//
//Given points = [[1,1],[-1,1]], return true. 
//Example 2:
//
//Given points = [[1,1],[-1,-1]], return false. 
//Follow up:
//Could you do better than O(n2)? 
//Hint:
//Find the smallest and largest x-value for all points.
//If there is a line then it should be at y = (minX + maxX) / 2.
//For each point, make sure that it has a reflected point in the opposite side.
//http://blog.csdn.net/jmspan/article/details/51688862
//
//http://dartmooryao.blogspot.com/2016/06/leetcode-356-line-reflection.html
//https://leetcode.com/discuss/108319/simple-java-hashset-solution
//The idea is that all symmetry pair of number's x value will add to the same number.
//So we want to find out this number by finding the min and max, then add them
//
//In addition, we use a string to represent the points.
//For each point, we calculate the counter part of this point, and check whether this string exists in the set. If not, return false. If all points pass the check, return true.
//
//My idea is similar to it. However, in my solution, I have to either add or subtract the number. Also, I have to deal with double value because I use average of the two value. Which is more difficult to handle.
//
//Very important:
//(1) Use sum over average!
//(2) Consider using min/max value when I need to find a particular value from a group of numbers.
//(3) String matching is magic!
//   public boolean isReflected(int[][] points) {
//    int max = Integer.MIN_VALUE;
//    int min = Integer.MAX_VALUE;
//    HashSet<String> set = new HashSet<>();
//    for(int[] p:points){
//        max = Math.max(max,p[0]);
//        min = Math.min(min,p[0]);
//        String str = p[0] + "a" + p[1];
//        set.add(str);
//    }
//    int sum = max+min;
//    for(int[] p:points){
//        //int[] arr = {sum-p[0],p[1]};
//        String str = (sum-p[0]) + "a" + p[1];
//        if( !set.contains(str))
//            return false;
//
//    }
//    return true;
//}    
//
//https://discuss.leetcode.com/topic/48172/simple-java-hashset-solution/5
//    public boolean isReflected(int[][] points) {
//        int max, min, sum;
//        HashSet<Point> set = new HashSet<>();
//        if(points.length == 0) return true;
//        max = points[0][0]; min = max;
//        for(int[] point: points) {
//            int x = point[0];
//            if(x > max) max = x;
//            if(x < min) min = x;
//            set.add(new Point(point[0], point[1]));
//        }
//        sum = (max + min);
//        for(int[] point: points) {
//            Point ref = new Point(sum - point[0], point[1]);
//            if(set.contains(ref)) set.remove(ref);
//        }
//        return set.isEmpty();
//        
//    }
//    private class Point {
//        int x;
//        int y;
//        Point(int xx, int yy) {x = xx; y = yy;}
//        @Override
//        public boolean equals(Object obj){
//            Point p = (Point) obj;
//            return (this.x == p.x && this.y == p.y);
//        }
//        @Override
//        public int hashCode(){
//            return x * 31 + y * 17;
//        }
//    }
//
//方法：使用哈希映射来快速查找反射点。
//https://leetcode.com/discuss/107725/8ms-java-no-hash-just-sort-o-1-space
//https://leetcode.com/discuss/107625/ac-java-with-explain-from-hint-not-using-average
//Thanks for StefanPochmann's test case. I found that average solution does not work for odd points. As the example StefanPochmann provided [[0,0],[1,0],[3,0]]. There is no reflection line. With average code, it returns true.
//So after read the hint and StefanPochmann's solution, I made it with Java.
//Just sort the points with x. Then "reflect" it by assume there is a x-axis formed by minx and maxx. Then resorted the array. All the points should be same as the first sort.
//    public boolean isReflected(int[][] points) {
//    if (points == null) {
//        return false;
//    }
//
//    Arrays.sort(points, new ArrComparator());
//
//    int[][] reflectedPoints = new int[points.length][2];
//    for (int i = 0; i < reflectedPoints.length; i++) {
//        reflectedPoints[i][0] = points[0][0] + points[reflectedPoints.length-1][0] - points[i][0];
//        reflectedPoints[i][1] = points[i][1];
//    }
//
//    Arrays.sort(reflectedPoints, new ArrComparator());
//
//    for (int i = 0; i < reflectedPoints.length; i++) {
//        if (reflectedPoints[i][0] != points[i][0] ||
//            reflectedPoints[i][1] != points[i][1]) {
//                return false;
//            }
//    }
//    return true;
//}
//public class ArrComparator implements Comparator<int[]>{
//    @Override
//    public int compare(int[] a, int b[]) {
//        if (a[0] == b[0]) {
//            return Integer.compare(a[1], b[1]);
//        }
//        return Integer.compare(a[0], b[0]);
//    }
//}
//https://leetcode.com/discuss/107665/solution-and-explanation
//My first approach to solve this problem was to sort all points by its x-coordinates, find the mid point of all points and then use 2-pointer to iterate through the sorted points from both side, and check if they are symmetric.
//It turn out this approach is a pain in the neck. Because sorting all points is not easy. The compare function used in sorting depends on where the middle line is. If you are not convinced, please think about how to compare 2 points that have the same x-coordinate.
//
//这道题给了我们一堆点，问我们存不存在一条平行于y轴的直线，使得所有的点关于该直线对称。题目中的提示给的相当充分，我们只要按照提示的步骤来做就可以解题了。首先我们找到所有点的横坐标的最大值和最小值，那么二者的平均值就是中间直线的横坐标，然后我们遍历每个点，如果都能找到直线对称的另一个点，则返回true，反之返回false.
//    bool isReflected(vector<pair<int, int>>& points) {
//        unordered_map<int, set<int>> m;
//        int mx = INT_MIN, mn = INT_MAX;
//        for (auto a : points) {
//            mx = max(mx, a.first);
//            mn = min(mn, a.first);
//            m[a.first].insert(a.second);
//        }
//        double y = (double)(mx + mn) / 2;
//        for (auto a : points) {
//            int t = 2 * y - a.first;
//            if (!m.count(t) || !m[t].count(a.second)) {
//                return false;
//            }
//        }
//        return true;
//    }
//下面这种解法没有求最大值和最小值，而是把所有的横坐标累加起来，然后求平均数，基本思路都相同
//    bool isReflected(vector<pair<int, int>>& points) {
//        if (points.empty()) return true;
//        set<pair<int, int>> pts;
//        double y = 0;
//        for (auto a : points) {
//            pts.insert(a);
//            y += a.first;
//        }
//        y /= points.size();
//        for (auto a : pts) {
//            if (!pts.count({y * 2 - a.first, a.second})) {
//                return false;
//            }
//        }
//        return true;
//    }
//http://blog.csdn.net/github_34333284/article/details/51697208
//  Think that, if all points are reflect each other. We need to first find the line. The line should be in the middle of (min, max).
//  For other points, if two point reflect, the y-ith should be same, (min, max) / 2 - x1 = (min, max) / 2 - x2.
//*/
//bool isReflected(vector< pair<int, int> >& points) {
//  unordered_map< int, set<int> > m;
//  int minX = INT_MAX, maxX = INT_MIN;
//  for(int i = 0; i < points.size(); ++i) {
//    minX = min(points[i].first, minX);
//    maxX = max(points[i].first, maxX);
//    m[points[i].first].insert(points[i].second);
//  }
//  double y = double(maxX + minX) / 2;
//  for(int i = 0; i < points.size(); ++i) {
//    int t = 2*y - points[i].first;
//    if(!m.count(t) || !m[t].count(points[i].second)) return false;
//  }
//  return true;
//}
//
//// second method
//bool isReflectedII(vector<pair<int, int> >& points) {
//  if(point.size() == 0) return true;
//  set< pair<int, int> > res;
//  double y = 0;
//  for(auto a : points) {
//    res.insert(a);
//    y += a.first;
//  }
//  y = y / points.size();
//  for(auto a : res) {
//    if(!res.count((2 * y - a.first), a.second)) return false;
//  }
//  return true;
//}
//https://reeestart.wordpress.com/2016/06/06/google-vertical-symmetric-line/
//
//[Solution]
//如果存在，这条线肯定是所有x的平均值或者中值(Avg or Median).
//先算出所有x的平均值作为candidate，然后对于所有在同一条y上的点，算对称轴，只要有一条对称轴和candidate不同，就返回false, 否则返回true.
//[Mistake]
//上面的Solution，如果input的点都在同一条水平线上，比如[-2, 1], [-1, 1], [1, 1], [3, 1]这4个点，很明显没有对称轴，但还是会返回true。
//[Solution]
//无论是Average还是Median都是不靠谱的。因为就算对于两行点来说有相同的average或者median x coordinator，也没法保证每行内的点都关于这个x对称。
//[Solution #1]
//一种解决方法是仍然找Average, 只要有一行点的average和其他不同就返回false，但是除此之后，对于同一水平线上的点，仍然需要判断它们是否关于candidate对称，判断方法就是对每一个点，看对称轴另一边的点是否存在。
//Cons: 由于ConcurrentModificationException, 使得实现起来稍微麻烦一点。
//[Solution #2]
//第二种解决方法是leetcode上给的hint，如果存在一条对称轴，那么对于所有对称点，两个x的和必然为一个固定值。
//A.x + B.x === xMin + xMax, if A and B are symmetrical to each other.
//那么和第一种方法一样，在first pass的时候一边 group y，一边找所有点的xMin和xMAX。最终如果存在对称轴，所有对称点的sum of x一定等于xMin + xMax。于是可以把问题简化为对于每一行的点，做two sum。
//[Solution #3]
//还有一种方法思路和solution #2一样，不过可以换种方法来解决。
//idea就是把所有点表示成一个string, 比如x#y，然后把所有点的string加入到HashSet里，在找对称点的时候只要找set里是否存在(sum – x)#y这样的string就可以了。
//[Time & Space]
//O(n) time, O(n) space
//Code is for Solution #2
//
//  public boolean isReflected(int[][] points) {
//
//    if (points.length == 0) {
//
//      return true;
//
//    }
//
//
//    int xMin = Integer.MAX_VALUE;
//
//    int xMax = Integer.MIN_VALUE;
//
//    Map<Integer, Set<Integer>> yGroup = new HashMap<>();
//
//    for (int i = 0; i < points.length; i++) {
//
//      xMin = Math.min(xMin, points[i][0]);
//
//      xMax = Math.max(xMax, points[i][0]);
//
//      yGroup.putIfAbsent(points[i][1], new HashSet<>());
//
//      yGroup.get(points[i][1]).add(points[i][0]);
//
//    }
//
//
//    int sum = xMax + xMin;
//
//
//    for (int y : yGroup.keySet()) {
//
//      Set<Integer> set = yGroup.get(y);
//
//      for (int x : set) {
//
//        if (!set.contains(sum - x)) {
//
//          return false;
//
//        }
//
//      }
//
//    }
//
//
//    return true;
//
//  }
//
//
//
//
//http://chuansong.me/n/465211849627
//Follow up: 如果对称轴是任意直线呢
//
//2. LC 356, 但是直线可以是任意直线
//http://www.1point3acres.com/bbs/thread-210040-1-1.html
//Read full article from [LeetCode] Line Reflection 直线对称 - Grandyang - 博客园
// * @author het
// *
// */
public class LeetCode356 {
	 public boolean isReflected(int[][] points) {
		    int max = Integer.MIN_VALUE;
		    int min = Integer.MAX_VALUE;
		    HashSet<String> set = new HashSet<>();
		    for(int[] p:points){
		        max = Math.max(max,p[0]);
		        min = Math.min(min,p[0]);
		        String str = p[0] + "a" + p[1];
		        set.add(str);
		    }
		    int sum = max+min;
		    for(int[] p:points){
		        //int[] arr = {sum-p[0],p[1]};
		        String str = (sum-p[0]) + "a" + p[1];
		        if( !set.contains(str))
		            return false;

		    }
		    return true;
		}    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
