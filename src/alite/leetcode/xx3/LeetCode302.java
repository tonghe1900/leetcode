package alite.leetcode.xx3;

/**
 * LeetCode 302 - Smallest Rectangle Enclosing Black Pixels
 * 
 * http://shibaili.blogspot.com/2015/11/day-134-302-smallest-rectangle.html An
 * image is represented by a binary matrix with 0 as a white pixel and 1 as a
 * black pixel. The black pixels are connected, i.e., there is only one black
 * region. Pixels are connected horizontally and vertically.
 * 
 * Given the location (x, y) of one of the black pixels, return the area of the
 * smallest (axis-aligned) rectangle that encloses all black pixels. For
 * example, given the following image: [ "0010", "0110", "0100" ] and x = 0, y =
 * 2, Return 6.X. DFS: - time complexity O(m * n) X. Binary Search:
 * https://discuss.leetcode.com/topic/29006/c-java-python-binary-search-solution-with-explanation
 * Suppose we have a 2D array "000000111000000" "000000101000000"
 * "000000101100000" "000001100100000" Imagine we project the 2D array to the
 * bottom axis with the rule "if a column has any black pixel it's projection is
 * black otherwise white". The projected 1D array is "000001111100000" Theorem
 * If there are only one black pixel region, then in a projected 1D array all
 * the black pixels are connected. Proof by contradiction Assume to the contrary
 * that there are disconnected black pixels at i and j where i < j in the 1D
 * projection array. Thus there exists one column k, k in (i, j) and and the
 * column k in the 2D array has no black pixel. Therefore in the 2D array there
 * exists at least 2 black pixel regions separated by column k which
 * contradicting the condition of "only one black pixel region". Therefore we
 * conclude that all the black pixels in the 1D projection array is connected.
 * This means we can do a binary search in each half to find the boundaries, if
 * we know one black pixel's position. And we do know that. To find the left
 * boundary, do the binary search in the [0, y) range and find the first column
 * vector who has any black pixel. To determine if a column vector has a black
 * pixel is O(m) so the search in total is O(m log n) We can do the same for the
 * other boundaries. The area is then calculated by the boundaries. Thus the
 * algorithm runs in O(m log n + n log m) private char[][] image; public int
 * minArea(char[][] iImage, int x, int y) { image = iImage; int m =
 * image.length, n = image[0].length; int left = searchColumns(0, y, 0, m,
 * true); int right = searchColumns(y + 1, n, 0, m, false); int top =
 * searchRows(0, x, left, right, true); int bottom = searchRows(x + 1, m, left,
 * right, false); return (right - left) * (bottom - top); } private int
 * searchColumns(int i, int j, int top, int bottom, boolean opt) { while (i !=
 * j) { int k = top, mid = (i + j) / 2; while (k < bottom && image[k][mid] ==
 * '0') ++k; if (k < bottom == opt) j = mid; else i = mid + 1; } return i; }
 * private int searchRows(int i, int j, int left, int right, boolean opt) {
 * while (i != j) { int k = left, mid = (i + j) / 2; while (k < right &&
 * image[mid][k] == '0') ++k; if (k < right == opt) j = mid; else i = mid + 1; }
 * return i; }
 * https://discuss.leetcode.com/topic/30621/1ms-java-binary-search-dfs-is-4ms If
 * we don't know programming, how do we get the 4 boundaries given a black
 * pixel? Do we need to scan every cell? Absolutely not. We would expand from a
 * 1 * 1 black square, aggressively expand the 4 boundaries, roughly half of the
 * remaining spaces. if we don't cut any black pixel, we would go back half.
 * This is exactly the process of binary search. The idea is simple but dealing
 * with boundaries can be tricky. For ranges [0, n) and [0, m), the four
 * pointers are starting at 0, n, 0, m initially. This will handle width = 1 or
 * height = 1 gracefully.
 * 
 * public int minArea(char[][] image, int x, int y) { int m = image.length, n =
 * image[0].length; int colMin = binarySearch(image, true, 0, y, 0, m, true);
 * int colMax = binarySearch(image, true, y + 1, n, 0, m, false); int rowMin =
 * binarySearch(image, false, 0, x, colMin, colMax, true); int rowMax =
 * binarySearch(image, false, x + 1, m, colMin, colMax, false); return (rowMax -
 * rowMin) * (colMax - colMin); }
 * 
 * public int binarySearch(char[][] image, boolean horizontal, int lower, int
 * upper, int min, int max, boolean goLower) { while(lower < upper) { int mid =
 * lower + (upper - lower) / 2; boolean inside = false; for(int i = min; i <
 * max; i++) { if((horizontal ? image[i][mid] : image[mid][i]) == '1') { inside
 * = true; break; } } if(inside == goLower) { upper = mid; } else { lower = mid
 * + 1; } } return lower; } http://www.cnblogs.com/yrbbest/p/5050022.html
 * 找到包含所有black pixel的最小矩形。这里我们用二分查找。因为给定black pixel点(x，y)，并且所有black
 * pixel都是联通的，以row search为例， 所有含有black pixel的column，映射到row
 * x上时，必定是连续的。这样我们可以使用binary search，在0到y里面搜索最左边含有black
 * pixel的一列。接下来可以继续搜索上下和右边界。搜索右边界和下边界的时候，其实我们要找的是第一个'0'，所以要传入一个boolean变量searchLo来判断。
 * Time Complexity - O(mlogn + nlogm)， Space Complexity - O(1) public int
 * minArea(char[][] image, int x, int y) { if(image == null || image.length ==
 * 0) { return 0; } int rowNum = image.length, colNum = image[0].length; int
 * left = binarySearch(image, 0, y, 0, rowNum, true, true); int right =
 * binarySearch(image, y + 1, colNum, 0, rowNum, true, false); int top =
 * binarySearch(image, 0, x, left, right, false, true); int bot =
 * binarySearch(image, x + 1, rowNum, left, right, false, false);
 * 
 * return (right - left) * (bot - top); }
 * 
 * private int binarySearch(char[][] image, int lo, int hi, int min, int max,
 * boolean searchHorizontal, boolean searchLo) { while(lo < hi) { int mid = lo +
 * (hi - lo) / 2; boolean hasBlackPixel = false; for(int i = min; i < max; i++)
 * { if((searchHorizontal ? image[i][mid] : image[mid][i]) == '1') {
 * hasBlackPixel = true; break; } } if(hasBlackPixel == searchLo) { hi = mid; }
 * else { lo = mid + 1; } } return lo; }
 * http://www.voidcn.com/blog/bsbcarter/article/p-4886034.html 还有更快的就是binary
 * search 做四次 寻找边界 [0,y) 找left 找的是最左边的1 [y + 1, n) 找right 找的是右边第一个0 top bottom同理
 * 那个boolean只是为了区分这次是往哪边找 不同方向start和end移动方式不一样 最后算面积的时候right － left不用再加1了
 * 
 * public int minArea(char[][] image, int x, int y) { int left =
 * searchColumns(image, 0, y, true); int right = searchColumns(image, y + 1,
 * image[0].length, false); int top = searchRows(image, 0, x, left, right,
 * true); int bottom = searchRows(image, x + 1, image.length, left, right,
 * false); return (right - left) * (bottom - top); } private int
 * searchColumns(char[][] image, int i, int j, boolean opt) { while (i != j) {
 * int k = 0, m = (i + j) / 2; for (; k < image.length; ++k) // extract as a
 * method if (image[k][m] == '1') break; if (k < image.length == opt) j = m;
 * else i = m + 1; } return i; } private int searchRows(char[][] image, int i,
 * int j, int left, int right, boolean opt) { while (i != j) { int k = left, m =
 * (i + j) / 2; for (; k < right; ++k) if (image[m][k] == '1') break; if (k <
 * right == opt) j = m; else i = m + 1; } return i; }
 * https://leetcode.com/discuss/85146/java-binary-search-o-nlogm-mlogn-runtime
 * http://www.cnblogs.com/EdwardLiu/p/5084577.html public int minArea(char[][]
 * image, int x, int y) { int m = image.length; int n = image[0].length; int
 * start = y;// y is column int end = n - 1; int mid; // find last column
 * containing black pixel while (start < end) { mid = start + (end - start) / 2
 * + 1; if (checkColumn(image, mid)) { start = mid; } else { end = mid - 1; } }
 * int right = start;
 * 
 * start = 0; end = y; // find first column containing black pixel while (start
 * < end) { mid = start + (end - start) / 2; if (checkColumn(image, mid)) { end
 * = mid; } else { start = mid + 1; } } int left = start;
 * 
 * start = x; // x is row end = m - 1; // find first row containing black pixel
 * while (start < end) { mid = start + (end - start) / 2 + 1; if
 * (checkRow(image, mid)) { start = mid; } else { end = mid - 1; } } int down =
 * start;
 * 
 * start = 0; end = x; // find first row containing black pixel while (start <
 * end) { mid = start + (end - start) / 2; if (checkRow(image, mid)) { end =
 * mid; } else { start = mid + 1; } } int up = start;
 * 
 * return (right - left + 1) * (down - up + 1); }
 * 
 * private boolean checkColumn(char[][] image, int col) { for (int i = 0; i <
 * image.length; i++) { if (image[i][col] == '1') { return true; } } return
 * false; }
 * 
 * private boolean checkRow(char[][] image, int row) { for (int j = 0; j <
 * image[0].length; j++) { if (image[row][j] == '1') { return true; } } return
 * false;
 * 
 * } Java (DRY) private char[][] image; public int minArea(char[][] iImage, int
 * x, int y) { image = iImage; int m = image.length, n = image[0].length; int
 * top = search(0, x, 0, n, true, true); int bottom = search(x + 1, m, 0, n,
 * false, true); int left = search(0, y, top, bottom, true, false); int right =
 * search(y + 1, n, top, bottom, false, false); return (right - left) * (bottom
 * - top); } private boolean isWhite(int mid, int k, boolean isRow) { return
 * ((isRow) ? image[mid][k] : image[k][mid]) == '0'; } private int search(int i,
 * int j, int low, int high, boolean opt, boolean isRow) { while (i != j) { int
 * k = low, mid = (i + j) / 2; while (k < high && isWhite(mid, k, isRow)) ++k;
 * if (k < high == opt) j = mid; else i = mid + 1; } return i; } // Runtime: 2
 * ms Python (DRY, from Stefan's cool solution) def minArea(self, image, x, y):
 * top = self.search(0, x, lambda mid: '1' in image[mid]) bottom = self.search(x
 * + 1, len(image), lambda mid: '1' not in image[mid]) left = self.search(0, y,
 * lambda mid: any(image[k][mid] == '1' for k in xrange(top, bottom))) right =
 * self.search(y + 1, len(image[0]), lambda mid: all(image[k][mid] == '0' for k
 * in xrange(top, bottom))) return (right - left) * (bottom - top)
 * 
 * def search(self, i, j, check): while i != j: mid = (i + j) / 2 if check(mid):
 * j = mid else: i = mid + 1 return i # Runtime: 56 ms
 * https://lefttree.gitbooks.io/leetcode-categories/content/BinarySearch/SmallestRectangleEnclosingBlackPixels.html
 * 根据已有的black pixel的左边，分别找到上下左右含有1的边界，利用binary search查找。 def minArea(self,
 * image, x, y): if not image: return 0
 * 
 * top = self.searchTop(image, 0, x) bottom = self.searchBottom(image, x,
 * len(image) - 1) left = self.searchLeft(image, 0, y) right =
 * self.searchRight(image, y, len(image[0]) - 1) return (right - left + 1) *
 * (bottom - top + 1)
 * 
 * def searchTop(self, image, start, end): while start + 1 < end: mid = start +
 * (end - start) / 2 if ("1" in image[mid]) == True: end = mid else: start = mid
 * if ("1" in image[start]) == True: return start elif ("1" in image[end]) ==
 * True: return end return end
 * 
 * def searchBottom(self, image, start, end): while start + 1 < end: mid = start
 * + (end - start) / 2 if ("1" in image[mid]) == True: start = mid else: end =
 * mid if ("1" in image[end]) == True: return end elif ("1" in image[start]) ==
 * True: return start return start
 * 
 * def searchLeft(self, image, start, end): while start + 1 < end: mid = start +
 * (end - start) / 2 if any(image[k][mid] == "1" for k in range(len(image))) ==
 * True: end = mid else: start = mid if any(image[k][start] == "1" for k in
 * range(len(image))) == True: return start elif any(image[k][start] == "1" for
 * k in range(len(image))) == True: return end return end
 * 
 * def searchRight(self, image, start, end): while start + 1 < end: mid = start
 * + (end - start) / 2 if any(image[k][mid] == "1" for k in range(len(image)))
 * == True: start = mid else: end = mid if any(image[k][end] == "1" for k in
 * range(len(image))) == True: return end elif any(image[k][start] == "1"for k
 * in range(len(image))) == True: return start return start
 * http://likemyblogger.blogspot.com/2015/11/leetcode-302-smallest-rectangle.html
 * 
 * //C++: 24ms Binary Search
 * 
 * class Solution {
 * 
 * int m, n;
 * 
 * public:
 * 
 * bool allZeros(vector<vector<char>>& image, int index, bool row){
 * 
 * if(index<0) return false;
 * 
 * if(row){
 * 
 * if(index>=m) return false;
 * 
 * for(int k=0; k<n; ++k){
 * 
 * if(image[index][k]=='1') return false;
 * 
 * }
 * 
 * }else{
 * 
 * if(index>=n) return false;
 * 
 * for(int k=0; k<m; ++k){
 * 
 * if(image[k][index]=='1') return false;
 * 
 * }
 * 
 * }
 * 
 * return true;
 * 
 * }
 * 
 * int getBd(vector<vector<char>>& image, int x, int y, bool hrz, bool forward){
 * 
 * int l, r, mid, b;
 * 
 * if(hrz && !forward){l = 0; r = y; b = n;}
 * 
 * if(hrz && forward){l = y; r = n-1; b = n;}
 * 
 * if(!hrz && !forward){l = 0; r = x; b = m;}
 * 
 * if(!hrz && forward){l = x; r = m-1; b = m;}
 * 
 * 
 * while(l<=r){
 * 
 * mid = (l+r)/2;
 * 
 * bool cur = allZeros(image, mid, !hrz);
 * 
 * bool nb = allZeros(image, mid+(forward?1:-1), !hrz);
 * 
 * if(!forward){
 * 
 * if((mid==0&&!cur) || (nb&&!cur)) break;
 * 
 * else if(cur) l = mid+1;
 * 
 * else r = mid-1;
 * 
 * }else{
 * 
 * if((mid==b-1&&!cur) || (nb&&!cur)) break;
 * 
 * else if(!cur) l = mid+1;
 * 
 * else r = mid-1;
 * 
 * }
 * 
 * }
 * 
 * return mid;
 * 
 * }
 * 
 * 
 * int minArea(vector<vector<char>>& image, int x, int y) {
 * 
 * m = image.size();
 * 
 * n = image[0].size();
 * 
 * int L = getBd(image, x, y, true, false);
 * 
 * int R = getBd(image, x, y, true, true);
 * 
 * int T = getBd(image, x, y, false, false);
 * 
 * int B = getBd(image, x, y, false, true);
 * 
 * return (R-L+1)*(B-T+1);
 * 
 * }
 * 
 * }; http://blog.csdn.net/pointbreak1/article/details/49793725 X. DFS
 * http://yuancrackcode.com/2015/11/07/smallest-rectangle-enclosing-black-pixels/
 * https://discuss.leetcode.com/topic/29000/java-dfs-solution-and-seeking-for-a-binary-search-solution
 * DFS or BFS is the intuitive solution for this problem while the problem is
 * with a tag "binary search". So can anyone provide a binary search answer. DFS
 * complexity is O(m * n) and if binary search it would be O(n * lgm + m * lgn)
 * 
 * bfs需要新建一个class存坐标 dfs不用存 更简便一点 记得每次访问过1后要给他设置成0 private int minX =
 * Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0; public int
 * minArea(char[][] image, int x, int y) { if(image == null || image.length == 0
 * || image[0].length == 0) return 0; dfs(image, x, y); return(maxX - minX + 1)
 * * (maxY - minY + 1); } private void dfs(char[][] image, int x, int y){ int m
 * = image.length, n = image[0].length; if(x < 0 || y < 0 || x >= m || y >= n ||
 * image[x][y] == '0') return; image[x][y] = '0'; minX = Math.min(minX, x); maxX
 * = Math.max(maxX, x); minY = Math.min(minY, y); maxY = Math.max(maxY, y);
 * dfs(image, x + 1, y); dfs(image, x - 1, y); dfs(image, x, y - 1); dfs(image,
 * x, y + 1); } X. BFS
 * https://discuss.leetcode.com/topic/49871/10-line-simple-python-solution-based-on-bfs
 * def minArea(self, image, r, c): m, n, queue = len(image), len(image[0]), [(r,
 * c)] visited, minr, minc, maxr, maxc = {(r, c)}, m + 1, n + 1, -1, -1 for r, c
 * in queue: minr, minc, maxr, maxc = min(minr, r), min(minc, c), max(maxr, r),
 * max(maxc, c) for d in {(-1, 0), (1, 0), (0, 1), (0, -1)}: nr, nc = r + d[0],
 * c + d[1] if 0 <= nr < m and 0 <= nc < n and image[nr][nc] != "0" and (nr, nc)
 * not in visited: visited |= {(nr, nc)} queue += (nr, nc), return (maxr - minr
 * + 1) * (maxc - minc + 1)
 * http://likemyblogger.blogspot.com/2015/11/leetcode-302-smallest-rectangle.html
 * 
 * //C++: 52ms BFS
 * 
 * class Solution {
 * 
 * int m, n, L, R, T, B;
 * 
 * vector<vector<bool>> visited;
 * 
 * public:
 * 
 * int minArea(vector<vector<char>>& image, int x, int y) {
 * 
 * m = image.size();
 * 
 * n = image[0].size();
 * 
 * visited.resize(m, vector<bool>(n, false));
 * 
 * queue<pair<int, int>> que;
 * 
 * que.push(pair<int, int>(x,y));
 * 
 * L = y; R = y; T = x; B = x;
 * 
 * while(!que.empty()){
 * 
 * int i = que.front().first;
 * 
 * int j = que.front().second;
 * 
 * que.pop();
 * 
 * if(i<T) T = i;
 * 
 * if(i>B) B = i;
 * 
 * if(j<L) L = j;
 * 
 * if(j>R) R = j;
 * 
 * if(i-1>=0 && !visited[i-1][j] && image[i-1][j]=='1'){visited[i-1][j]=true;
 * que.push(pair<int, int>(i-1, j));}
 * 
 * if(i+1<m && !visited[i+1][j] && image[i+1][j]=='1'){visited[i+1][j]=true;
 * que.push(pair<int, int>(i+1, j));}
 * 
 * if(j-1>=0 && !visited[i][j-1] && image[i][j-1]=='1'){visited[i][j-1]=true;
 * que.push(pair<int, int>(i, j-1));}
 * 
 * if(j+1<n && !visited[i][j+1] && image[i][j+1]=='1'){visited[i][j+1]=true;
 * que.push(pair<int, int>(i, j+1));}
 * 
 * }
 * 
 * return((R-L+1)*(B-T+1));
 * 
 * }
 * 
 * }; http://blog.csdn.net/pointbreak1/article/details/49793725 def
 * minArea(self, image, x, y): if len(image) == 0: return 0 left, right, top,
 * bot = len(image[0]) - 1, 0, len(image) - 1, 0 visited = [[False for i in
 * range(len(image[0]))] for j in range(len(image))] q = [] q.append((x, y))
 * while len(q) != 0: cur = q.pop() m, n = cur[0], cur[1] visited[m][n] = True
 * if n < left: left = n if n > right: right = n if m < top: top = m if m > bot:
 * bot = m if m - 1 >= 0 and image[m-1][n] == "1" and not visited[m-1][n]:
 * q.append((m-1, n)) if m + 1 < len(image) and image[m+1][n] == "1" and not
 * visited[m+1][n]: q.append((m+1, n)) if n - 1 >= 0 and image[m][n-1] == "1"
 * and not visited[m][n-1]: q.append((m, n-1)) if n + 1 < len(image[0]) and
 * image[m][n+1] == "1" and not visited[m][n+1]: q.append((m, n+1)) return
 * (right - left + 1) * (bot - top + 1)
 * 
 * https://discuss.leetcode.com/topic/30122/dfs-bfs-binary-search-and-brute-force-interation
 * Brute Force: int minArea(vector<vector<char>>& image, int x, int y) { int
 * x_min = INT_MAX, y_min = INT_MAX, x_max = INT_MIN, y_max = INT_MIN; for(int i
 * = 0; i < image.size(); ++i){ for(int j = 0; j < image[0].size(); ++j){ if
 * (image[i][j] == '1'){ x_min = min(x_min, i); y_min = min(y_min, j); x_max =
 * max(x_max, i); y_max = max(y_max, j); } } } return (x_max - x_min + 1) *
 * (y_max - y_min + 1); }
 * 
 * @author het
 *
 */
public class LeetCode302 {
	// Imagine we project the 2D array to the bottom axis with the rule "if a column
	// has any black pixel it's projection
	// is black otherwise white". The projected 1D array is
	// "000001111100000"
	// Theorem
	// If there are only one black pixel region, then in a projected 1D array all
	// the black pixels are connected.
	// Proof by contradiction
	// Assume to the contrary that there are disconnected black pixels at i
	// and j where i < j in the 1D projection array. Thus there exists one
	// column k, k in (i, j) and and the column k in the 2D array has no
	// black pixel. Therefore in the 2D array there exists at least 2 black
	// pixel regions separated by column k which contradicting the condition
	// of "only one black pixel region".
	// Therefore we conclude that all the black pixels in the 1D projection
	// array is connected.
	// This means we can do a binary search in each half to find the boundaries, if
	// we know one black pixel's position. And we do know that.
	// To find the left boundary, do the binary search in the [0, y) range and find
	// the first column vector who has any black pixel.
	// To determine if a column vector has a black pixel is O(m) so the search in
	// total is O(m log n)
	// We can do the same for the other boundaries. The area is then calculated by
	// the boundaries.
	// Thus the algorithm runs in O(m log n + n log m)
	private char[][] image;

	public int minArea(char[][] iImage, int x, int y) {
		image = iImage;
		int m = image.length, n = image[0].length;
		int left = searchColumns(0, y, 0, m, true);
		int right = searchColumns(y + 1, n, 0, m, false);
		int top = searchRows(0, x, left, right, true);
		int bottom = searchRows(x + 1, m, left, right, false);
		return (right - left) * (bottom - top);
	}

	private int searchColumns(int i, int j, int top, int bottom, boolean opt) {
		while (i != j) {
			int k = top, mid = (i + j) / 2;
			while (k < bottom && image[k][mid] == '0')
				++k;
			if (k < bottom == opt)
				j = mid;
			else
				i = mid + 1;
		}
		return i;
	}

	private int searchRows(int i, int j, int left, int right, boolean opt) {
		while (i != j) {
			int k = left, mid = (i + j) / 2;
			while (k < right && image[mid][k] == '0')
				++k;
			if (k < right == opt)
				j = mid;
			else
				i = mid + 1;
		}
		return i;
	}

	// 找到包含所有black pixel的最小矩形。这里我们用二分查找。因为给定black pixel点(x，y)，并且所有black
	// pixel都是联通的，以row search为例， 所有含有black pixel的column，映射到row
	// x上时，必定是连续的。这样我们可以使用binary search，在0到y里面搜索最左边含有black
	// pixel的一列。接下来可以继续搜索上下和右边界。搜索右边界和下边界的时候，其实我们要找的是第一个'0'，所以要传入一个boolean变量searchLo来判断。
	// Time Complexity - O(mlogn + nlogm)， Space Complexity - O(1)
	public int minArea1(char[][] image, int x, int y) {
		if (image == null || image.length == 0) {
			return 0;
		}
		int rowNum = image.length, colNum = image[0].length;
		int left = binarySearch(image, 0, y, 0, rowNum, true, true);
		int right = binarySearch(image, y + 1, colNum, 0, rowNum, true, false);
		int top = binarySearch(image, 0, x, left, right, false, true);
		int bot = binarySearch(image, x + 1, rowNum, left, right, false, false);

		return (right - left) * (bot - top);
	}

	private int binarySearch(char[][] image, int lo, int hi, int min, int max, boolean searchHorizontal,
			boolean searchLo) {
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			boolean hasBlackPixel = false;
			for (int i = min; i < max; i++) {
				if ((searchHorizontal ? image[i][mid] : image[mid][i]) == '1') {
					hasBlackPixel = true;
					break;
				}
			}
			if (hasBlackPixel == searchLo) {
				hi = mid;
			} else {
				lo = mid + 1;
			}
		}
		return lo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
