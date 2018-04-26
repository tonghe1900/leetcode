package alite.leetcode.xx4.left;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/number-of-boomerangs/
Given n points in the plane that are all pairwise distinct, 
a "boomerang" is a tuple of points (i, j, k) such that the distance between iand j equals the distance between
 i and k (the order of the tuple matters).
Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points
 are all in the range [-10000, 10000](inclusive).
Example:
Input:
[[0,0],[1,0],[2,0]]

Output:
2

Explanation:
The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
https://discuss.leetcode.com/topic/66587/clean-java-solution-o-n-2-166ms
For every i, we capture the number of points equidistant from i. Now for this i, we have to calculate all possible permutations of (j,k) from these equidistant points.
Total number of permutations of size 2 from n different points is nP2 = n!/(n-2)! = n * (n-1). hope this helps.
public int numberOfBoomerangs(int[][] points) {
    int res = 0;        
    Map<Integer, Integer> map = new HashMap<>();

    for(int i=0; i<points.length; i++) {
        for(int j=0; j<points.length; j++) {
            if(i == j)
                continue;
            
            int d = getDistance(points[i], points[j]);                
            map.put(d, map.getOrDefault(d, 0) + 1);
        }
        
        for(int val : map.values()) {
            res += val * (val-1);
        }            
        map.clear();
    }
    
    return res;
}

private int getDistance(int[] a, int[] b) {
    int dx = a[0] - b[0];
    int dy = a[1] - b[1];
    
    return dx*dx + dy*dy;
}

Time complexity:  O(n^2)
Space complexity: O(n)
https://discuss.leetcode.com/topic/66722/very-simple-java-answer-on2
http://blog.csdn.net/mebiuw/article/details/53096120
    public int numberOfBoomerangs(int[][] points) {
        int count = 0;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int j = 0; j < n; j++) {
                int dis = (points[i][0] - points[j][0]) * (points[i][0] - points[j][0]) + (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);
                if (!map.containsKey(dis)) {
                    map.put(dis, 0);
                }
                //两个位置可以j k可以颠倒
                count += map.get(dis) * 2;
                map.put(dis, map.get(dis) + 1);
            }
        }
        return count;
    }
https://tech.liuchao.me/2016/11/leetcode-solution-447/

    public int numberOfBoomerangs(int[][] points) {

        int result = 0, cache[][] = new int[points.length][points.length];


        HashMap<Integer, Integer> tmp = new HashMap<>();

        for (int i = 0, L1 = points.length; i < L1; i++) {

            for (int j = 0, L2 = points.length; j < L2; j++) {

                int distance = i >= j ? cache[j][i]

                        : (points[i][0] - points[j][0]) * (points[i][0] - points[j][0])

                        + (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);

                if (i < j) cache[i][j] = distance;

                int last = tmp.getOrDefault(distance, 0);

                result += 2 * last;

                tmp.put(distance, last + 1);

            }

            tmp.clear();

        }


        return result;

    }
http://bookshadow.com/weblog/2016/11/06/leetcode-number-of-boomerangs/
枚举点i(x1, y1)，计算点i到各点j(x2, y2)的距离，并分类计数
利用排列组合知识，从每一类距离中挑选2个点的排列数 A(n, 2) = n * (n - 1)
将上述结果累加即为最终答案
    def numberOfBoomerangs(self, points):
        """
        :type points: List[List[int]]
        :rtype: int
        """
        ans = 0
        for x1, y1 in points:
            dmap = collections.defaultdict(int)
            for x2, y2 in points:
                dmap[(x1 - x2) ** 2 + (y1 - y2) ** 2] += 1
            for d in dmap:
                ans += dmap[d] * (dmap[d] - 1)
        return ans
 * @author het
 *
 */
public class LeetCode447NumberofBoomerangs {
	public int numberOfBoomerangs(int[][] points) {
	    int res = 0;        
	    Map<Integer, Integer> map = new HashMap<>();

	    for(int i=0; i<points.length; i++) {
	        for(int j=0; j<points.length; j++) {
	            if(i == j)
	                continue;
	            
	            int d = getDistance(points[i], points[j]);                
	            map.put(d, map.getOrDefault(d, 0) + 1);
	        }
	        
	        for(int val : map.values()) {
	            res += val * (val-1);
	        }            
	        map.clear();
	    }
	    
	    return res;
	}

	private int getDistance(int[] a, int[] b) {
	    int dx = a[0] - b[0];
	    int dy = a[1] - b[1];
	    
	    return dx*dx + dy*dy;
	}

//	Time complexity:  O(n^2)
//	Space complexity: O(n)
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
