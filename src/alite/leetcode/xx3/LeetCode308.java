package alite.leetcode.xx3;
/**
 * LeetCode - 308 Range Sum Query 2D - Mutable

http://bookshadow.com/weblog/2015/11/18/leetcode-range-sum-query-mutable/
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
The update(i, val) function modifies nums by updating the element at index i to val.
Example:
Given nums = [1, 3, 5]

sumRange(0, 2) -> 9
update(1, 2)
sumRange(0, 2) -> 8
Note:
The array is only modifiable by the update function.
You may assume the number of calls to update and sumRange function is distributed evenly.
解法I：线段树（Segment Tree）的基础应用
class NumArray(object):
    def __init__(self, nums):
        self.nums = nums
        self.size = size = len(nums)
        h = int(math.ceil(math.log(size, 2))) if size else 0
        maxSize = 2 ** (h + 1) - 1
        self.st = [0] * maxSize
        if size:
            self.initST(0, size - 1, 0)

    def update(self, i, val):
        if i < 0 or i >= self.size:
            return
        diff = val - self.nums[i]
        self.nums[i] = val
        self.updateST(0, self.size - 1, i, diff, 0)

    def sumRange(self, i, j):
        """
        if i < 0 or j < 0 or i >= self.size or j >= self.size:
            return 0
        return self.sumRangeST(0, self.size - 1, i, j, 0)

    def initST(self, ss, se, si):
        if ss == se:
            self.st[si] = self.nums[ss]
        else:
            mid = (ss + se) / 2
            self.st[si] = self.initST(ss, mid, si * 2 + 1) + \
                          self.initST(mid + 1, se, si * 2 + 2)
        return self.st[si]

    def updateST(self, ss, se, i, diff, si):
        if i < ss or i > se:
            return
        self.st[si] += diff
        if ss != se:
            mid = (ss + se) / 2
            self.updateST(ss, mid, i, diff, si * 2 + 1)
            self.updateST(mid + 1, se, i, diff, si * 2 + 2)

    def sumRangeST(self, ss, se, qs, qe, si):
        if qs <= ss and qe >= se:
            return self.st[si]
        if se < qs or ss > qe:
            return 0
        mid = (ss + se) / 2
        return self.sumRangeST(ss, mid, qs, qe, si * 2 + 1) + \
                self.sumRangeST(mid + 1, se, qs, qe, si * 2 + 2)
解法II：树状数组（Binary Indexed Tree / Fenwick Tree）
class NumArray(object):
    def __init__(self, nums):
        self.sums = [0] * (len(nums) + 1)
        self.nums = nums
        self.n = len(nums)
        for i in xrange(len(nums)):
            self.add(i + 1,nums[i])

    def add(self,x,val):
        while x <= self.n:
            self.sums[x] += val
            x += self.lowbit(x)

    def lowbit(self,x):
        return x & -x

    def sum(self,x):
        res = 0
        while x > 0:
            res += self.sums[x]
            x -= self.lowbit(x)
        return res

    def update(self, i, val):
        self.add(i + 1, val - self.nums[i])
        self.nums[i] = val

    def sumRange(self, i, j):
        if not self.nums:
            return  0
        return self.sum(j+1) - self.sum(i)

X.
https://leetcode.com/discuss/70948/15ms-easy-to-understand-java-solution
We use colSums[j][i] = the sum of ( matrix[0][j], matrix[1][j], matrix[2][j],......,matrix[i - 1][j] ).
private int[][] colSums;
private int[][] matrix;

public NumMatrix(int[][] matrix) {
    if(   matrix           == null
       || matrix.length    == 0
       || matrix[0].length == 0   ){
        return;   
     }

     this.matrix = matrix;

     int m   = matrix.length;
     int n   = matrix[0].length;
     colSums = new int[n][m + 1];
     for(int i = 0; i < n; i++){
        for(int j = 1; j <= m; j++){
            colSums[i][j] = colSums[i][j - 1] + matrix[j - 1][i];
        } 
     }
}
//time complexity for the worst case scenario: O(m)
public void update(int row, int col, int val) {
    for(int i = row + 1; i < colSums[0].length; i++){
        colSums[col][i] = colSums[col][i] - matrix[row][col] + val;
    }

    matrix[row][col] = val;
}
//time complexity for the worst case scenario: O(n)
public int sumRegion(int row1, int col1, int row2, int col2) {
    int ret = 0;

    for(int j = col1; j <= col2; j++){
        ret += colSums[j][row2 + 1] - colSums[j][row1];
    }

    return ret;
}
https://discuss.leetcode.com/topic/30250/15ms-easy-to-understand-java-solution/12
This can be improved to O(1) space. I changed it to row sum version. BTW I think this solution is better than the BIT version since it's quite hard to give a BIT solution during an interview (unless you have seen BIT before).
    // O(1) space
    int[][] mMatrix = null;
    int m = 0;
    int n = 0;
    
    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
        mMatrix = matrix;
        m = matrix.length;
        n = matrix[0].length;
        // rowSums[i][j] = rowSums[i][0] + rowSums[i][1] + ... + rowSums[i][j]
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                matrix[i][j] = matrix[i][j] + matrix[i][j - 1];
            }
        }
    }
    // O(n)
    public void update(int row, int col, int val) {
        // handle col = 0 differently
        int originalValue = col == 0 ? mMatrix[row][0] : mMatrix[row][col] - mMatrix[row][col - 1];
        int diff = val - originalValue;
        for (int j = col; j < n; j++) {
            mMatrix[row][j] += diff;
        }
    }
    // O(m)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int result = 0;
        for (int i = row1; i <= row2; i++) {
            // handle col = 0 differently
            result += col1 == 0 ? mMatrix[i][col2] : mMatrix[i][col2] - mMatrix[i][col1 - 1];
        }
        return result;
    }
https://kennyzhuang.gitbooks.io/leetcode-lock/content/308_range_sum_query_2d_-_mutable.html
public class NumMatrix {

    int[][] tree;
    int[][] nums;
    int m;
    int n;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        m = matrix.length;
        n = matrix[0].length;
        tree = new int[m+1][n+1];
        nums = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (m == 0 || n == 0) return;
        int delta = val - nums[row][col];
        nums[row][col] = val;
        for (int i = row + 1; i <= m; i += i & (-i)) {
            for (int j = col + 1; j <= n; j += j & (-j)) {
                tree[i][j] += delta;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (m == 0 || n == 0) return 0;
        return sum(row2+1, col2+1) + sum(row1, col1) - sum(row1, col2+1) - sum(row2+1, col1);
    }

    public int sum(int row, int col) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = col; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }
}

http://www.1point3acres.com/bbs/thread-210173-1-1.html
其实后来发现我给的解法 好像就是quad tree。
对于quad tree每一个region，存总的sum， 和所有row的 和 所有column 的sum。. visit 1point3acres.com for more.
递归过程t(n) = t(n/2) +O(n) 因为只有一个quadrant 需要 recursive call， 其他的可以直接根据region 存的值算出来 所以是o（n）
 * @author het
 *
 */


class Point implements Comparable<Point>{
	  int x, y, max;
	  Point(){}
	  Point(int x, int y, int max){
		  this.x  =x;
		  this.y = y;
		  this.max = max;
	  }
	@Override
	public int compareTo(Point p) {
		
		return new Integer(this.max).compareTo(p.max);
	}
	  
}

class Segtree2d{
	
}
//using namespace std;
//
//#define Max 501
//#define INF (1 << 30)
//int P[Max][Max]; // container for 2D grid
//
///* 2D Segment Tree node */
//struct Point {
//    int x, y, mx;
//    Point() {}
//    Point(int x, int y, int mx) : x(x), y(y), mx(mx) {}
//
//    bool operator < (const Point& other) const {
//        return mx < other.mx;
//    }
//};
//
//struct Segtree2d {
//
//    // I didn't calculate the exact size needed in terms of 2D container size.
//    // If anyone, please edit the answer.
//    // It's just a safe size to store nodes for MAX * MAX 2D grids which won't cause stack overflow :)
//    Point T[500000]; // TODO: calculate the accurate space needed
//
//    int n, m;
//
//    // initialize and construct segment tree
//    void init(int n, int m) {
//        this -> n = n;
//        this -> m = m;
//        build(1, 1, 1, n, m);
//    }
//
//    // build a 2D segment tree from data [ (a1, b1), (a2, b2) ]
//    // Time: O(n logn)
//    Point build(int node, int a1, int b1, int a2, int b2) {
//        // out of range
//        if (a1 > a2 or b1 > b2)
//            return def();
//
//        // if it is only a single index, assign value to node
//        if (a1 == a2 and b1 == b2)
//            return T[node] = Point(a1, b1, P[a1][b1]);
//
//        // split the tree into four segments
//        T[node] = def();
//        T[node] = maxNode(T[node], build(4 * node - 2, a1, b1, (a1 + a2) / 2, (b1 + b2) / 2 ) );
//        T[node] = maxNode(T[node], build(4 * node - 1, (a1 + a2) / 2 + 1, b1, a2, (b1 + b2) / 2 ));
//        T[node] = maxNode(T[node], build(4 * node + 0, a1, (b1 + b2) / 2 + 1, (a1 + a2) / 2, b2) );
//        T[node] = maxNode(T[node], build(4 * node + 1, (a1 + a2) / 2 + 1, (b1 + b2) / 2 + 1, a2, b2) );
//        return T[node];
//    }
//
//    // helper function for query(int, int, int, int);
//    Point query(int node, int a1, int b1, int a2, int b2, int x1, int y1, int x2, int y2) {
//        // if we out of range, return dummy
//        if (x1 > a2 or y1 > b2 or x2 < a1 or y2 < b1 or a1 > a2 or b1 > b2)
//            return def();
//
//        // if it is within range, return the node
//        if (x1 <= a1 and y1 <= b1 and a2 <= x2 and b2 <= y2)
//            return T[node];
//
//        // split into four segments
//        Point mx = def();
//        mx = maxNode(mx, query(4 * node - 2, a1, b1, (a1 + a2) / 2, (b1 + b2) / 2, x1, y1, x2, y2) );
//        mx = maxNode(mx, query(4 * node - 1, (a1 + a2) / 2 + 1, b1, a2, (b1 + b2) / 2, x1, y1, x2, y2) );
//        mx = maxNode(mx, query(4 * node + 0, a1, (b1 + b2) / 2 + 1, (a1 + a2) / 2, b2, x1, y1, x2, y2) );
//        mx = maxNode(mx, query(4 * node + 1, (a1 + a2) / 2 + 1, (b1 + b2) / 2 + 1, a2, b2, x1, y1, x2, y2));
//
//        // return the maximum value
//        return mx;
//    }
//
//    // query from range [ (x1, y1), (x2, y2) ]
//    // Time: O(logn)
//    Point query(int x1, int y1, int x2, int y2) {
//        return query(1, 1, 1, n, m, x1, y1, x2, y2);
//    }
//
//    // helper function for update(int, int, int);
//    Point update(int node, int a1, int b1, int a2, int b2, int x, int y, int value) {
//        if (a1 > a2 or b1 > b2)
//            return def();
//
//        if (x > a2 or y > b2 or x < a1 or y < b1)
//            return T[node];
//
//        if (x == a1 and y == b1 and x == a2 and y == b2)
//            return T[node] = Point(x, y, value);
//
//        Point mx = def();
//        mx = maxNode(mx, update(4 * node - 2, a1, b1, (a1 + a2) / 2, (b1 + b2) / 2, x, y, value) );
//        mx = maxNode(mx, update(4 * node - 1, (a1 + a2) / 2 + 1, b1, a2, (b1 + b2) / 2, x, y, value));
//        mx = maxNode(mx, update(4 * node + 0, a1, (b1 + b2) / 2 + 1, (a1 + a2) / 2, b2, x, y, value));
//        mx = maxNode(mx, update(4 * node + 1, (a1 + a2) / 2 + 1, (b1 + b2) / 2 + 1, a2, b2, x, y, value) );
//        return T[node] = mx;
//    }
//
//    // update the value of (x, y) index to 'value'
//    // Time: O(logn)
//    Point update(int x, int y, int value) {
//        return update(1, 1, 1, n, m, x, y, value);
//    }
//
//    // utility functions; these functions are virtual because they will be overridden in child class
//    virtual Point maxNode(Point a, Point b) {
//        return max(a, b);
//    }
//
//    // dummy node
//    virtual Point def() {
//        return Point(0, 0, -INF);
//    }
//};
//
///* 2D Segment Tree for range minimum query; a override of Segtree2d class */
//struct Segtree2dMin : Segtree2d {
//    // overload maxNode() function to return minimum value
//    Point maxNode(Point a, Point b) {
//        return min(a, b);
//    }
//
//    Point def() {
//        return Point(0, 0, INF);
//    }
//};
//
//// initialize class objects
//Segtree2d Tmax;
//Segtree2dMin Tmin;
//
//
///* Drier program */
//int main(void) {
//    int n, m;
//    // input
//    scanf("%d %d", &n, &m);
//    for(int i = 1; i <= n; i++)
//        for(int j = 1; j <= m; j++)
//            scanf("%d", &P[i][j]);
//
//    // initialize
//    Tmax.init(n, m);
//    Tmin.init(n, m);
//
//    // query
//    int x1, y1, x2, y2;
//    scanf("%d %d %d %d", &x1, &y1, &x2, &y2);
//
//    Tmax.query(x1, y1, x2, y2).mx;
//    Tmin.query(x1, y1, x2, y2).mx;
//
//    // update
//    int x, y, v;
//    scanf("%d %d %d", &x, &y, &v);
//    Tmax.update(x, y, v);
//    Tmin.update(x, y, v);
//
//    return 0;
//}
public class LeetCode308 {
//	public class NumMatrix {
//
//	    int[][] tree;
//	    int[][] nums;
//	    int m;
//	    int n;
//
//	    public NumMatrix(int[][] matrix) {
//	        if (matrix.length == 0 || matrix[0].length == 0) return;
//	        m = matrix.length;
//	        n = matrix[0].length;
//	        tree = new int[m+1][n+1];
//	        nums = new int[m][n];
//	        for (int i = 0; i < m; i++) {
//	            for (int j = 0; j < n; j++) {
//	                update(i, j, matrix[i][j]);
//	            }
//	        }
//	    }
//
//	    public void update(int row, int col, int val) {
//	        if (m == 0 || n == 0) return;
//	        int delta = val - nums[row][col];
//	        nums[row][col] = val;
//	        for (int i = row + 1; i <= m; i += i & (-i)) {  //i & (-i)  i & (-i)
//	            for (int j = col + 1; j <= n; j += j & (-j)) {
//	                tree[i][j] += delta;
//	            }
//	        }
//	    }
	
	// for (int i = row + 1; i <= m; i += i & (-i)) {
//         for (int j = col + 1; j <= n; j += j & (-j)) {
//             tree[i][j] += delta;
//         }
//     }
//
//	    public int sumRegion(int row1, int col1, int row2, int col2) {
//	        if (m == 0 || n == 0) return 0;
//	        return sum(row2+1, col2+1) + sum(row1, col1) - sum(row1, col2+1) - sum(row2+1, col1);
//	    }
//
//	    public int sum(int row, int col) {
//	        int sum = 0;
//	        for (int i = row; i > 0; i -= i & (-i)) {  //i -= i & (-i)
//	            for (int j = col; j > 0; j -= j & (-j)) {
//	                sum += tree[i][j];
//	            }
//	        }
//	        return sum;
//	    }
	}



public class NumMatrix {

    int[][] tree;
    int[][] nums;
    int m;
    int n;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        m = matrix.length;
        n = matrix[0].length;
        tree = new int[m+1][n+1];
        nums = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (m == 0 || n == 0) return;
        int delta = val - nums[row][col];
        nums[row][col] = val;
        for (int i = row + 1; i <= m; i += i & (-i)) {
            for (int j = col + 1; j <= n; j += j & (-j)) {
                tree[i][j] += delta;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (m == 0 || n == 0) return 0;
        return sum(row2+1, col2+1) + sum(row1, col1) - sum(row1, col2+1) - sum(row2+1, col1);
    }

    public int sum(int row, int col) {
        int sum = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = col; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
