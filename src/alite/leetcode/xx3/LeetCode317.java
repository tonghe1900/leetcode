package alite.leetcode.xx3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * LeetCode [317] Shortest Distance from All Buildings
 * 
 * http://algobox.org/shortest-distance-from-all-buildings/ You want to build a
 * house on an empty land which reaches all buildings in the shortest amount of
 * distance. You can only move up, down, left and right. You are given a 2D grid
 * of values 0, 1 or 2, where: Each 0 marks an empty land which you can pass by
 * freely. Each 1 marks a building which you cannot pass through. Each 2 marks
 * an obstacle which you cannot pass through. Example: Given three buildings at
 * (0,0), (0,4), (2,2), and an obstacle at (0,2):
 * 
 * 1 2 3 4 5
 * 
 * 
 * 1 - 0 - 2 - 0 - 1
 * 
 * 
 * | | | | |
 * 
 * 
 * 0 - 0 - 0 - 0 - 0
 * 
 * 
 * | | | | |
 * 
 * 
 * 0 - 0 - 1 - 0 - 0
 * 
 * The point (1,2) is an ideal empty land to build a house, as the total travel
 * distance of 3+3+1=7 is minimal. Return 7. Note: There will be at least one
 * building. If it is not possible to build such house according to the above
 * rules, return -1. https://leetcode.com/discuss/76018/share-a-java-implement
 * https://discuss.leetcode.com/topic/31668/my-bfs-java-solution Short version:
 * BFS from every building, calculate the distances and find the minimum
 * distance in the end. Key optimization : we do not go into a land, if it is
 * not accessible by at least one of previous buildings.
 * 
 * The shortest distance from any empty land to a building can be calculated by
 * BFS starting from the building in O(mn)time. Therefore the we can calculate
 * distance from all buildings to empty lands by t rounds of BFS starting from
 * each building. t is the total number of buildings. In each round, we need
 * maintain two values for every empty land: the distance and the accessibility.
 * dist[i][j] is the empty land (i, j) to all the buildings. grid[i][j] is
 * reused as the accessibility. What is accessibility? It is the number of
 * buildings that are accessible from the empty land. In each round of BFS we
 * can maintain these two values. In the end we just need to find the minimum
 * value of dist[i][j] where the accessibility equals t. One interesting point
 * is that the grid[i][j] can also be used to check if (i, j) is visited in this
 * round. At round k(zero based), those has grid[i][j] == k is the empty land
 * unvisited, visited land will have grid[i][j] == k + 1. Here comes the
 * interesting part. One may ask what if grid[i][j] < k? Answer is we do not go
 * into the lands withgrid[i][j] < k as if it is an obstacle. Why can we do
 * that? Because grid[i][j] < k means it is not accessible by at least one of
 * the buildings in previous rounds. Which means not only this land is not our
 * answer, all the lands accessible from (i, j) is also not our answer. This
 * might be why it runs faster than many implements. The Java version runs in 13
 * ms. int[] dx = {1, 0, -1, 0}, dy = {0, 1, 0, -1};
 * 
 * public int shortestDistance(int[][] grid) { int m = grid.length, n =
 * grid[0].length; int[][] dist = new int[m][n]; // Initialize building list and
 * accessibility matrix `grid` List<Tuple> buildings = new ArrayList<>(); for
 * (int i = 0; i < m; ++i) for (int j = 0; j < n; ++j) { if (grid[i][j] == 1)
 * buildings.add(new Tuple(i, j, 0)); grid[i][j] = -grid[i][j]; } // BFS from
 * every building for (int k = 0; k < buildings.size(); ++k)
 * bfs(buildings.get(k), k, dist, grid, m, n); // Find the minimum distance int
 * ans = -1; for (int i = 0; i < m; ++i) for (int j = 0; j < n; ++j) if
 * (grid[i][j] == buildings.size() && (ans < 0 || dist[i][j] < ans)) ans =
 * dist[i][j]; return ans; }
 * 
 * public void bfs(Tuple root, int k, int[][] dist, int[][] grid, int m, int n)
 * { Queue<Tuple> q = new ArrayDeque<>(); q.add(root); while (!q.isEmpty()) {
 * Tuple b = q.poll(); dist[b.y][b.x] += b.dist; for (int i = 0; i < 4; ++i) {
 * int x = b.x + dx[i], y = b.y + dy[i]; if (y >= 0 && x >= 0 && y < m && x < n
 * && grid[y][x] == k) { grid[y][x] = k + 1; q.add(new Tuple(y, x, b.dist + 1));
 * } } } } class Tuple { public int y; public int x; public int dist;
 * 
 * public Tuple(int y, int x, int dist) { this.y = y; this.x = x; this.dist =
 * dist; } }
 * https://leetcode.com/discuss/74999/java-solution-with-explanation-and-time-complexity-analysis
 * Traverse the matrix. For each building, use BFS to compute the shortest
 * distance from each '0' to this building. After we do this for all the
 * buildings, we can get the sum of shortest distance from every '0' to all
 * reachable buildings. This value is stored in 'distance[][]'. For example, if
 * grid[2][2] == 0, distance[2][2] is the sum of shortest distance from this
 * block to all reachable buildings. Time complexity: O(number of 1)O(number of
 * 0) ~ O(m^2n^2) We also count how many building each '0' can be reached. It is
 * stored in reach[][]. This can be done during the BFS. We also need to count
 * how many total buildings are there in the matrix, which is stored in
 * 'buildingNum'. Finally, we can traverse the distance[][] matrix to get the
 * point having shortest distance to all buildings. O(m*n) The total time
 * complexity will be O(m^2*n^2), which is quite high!.
 * 
 * Isn't the time complexity O(#buildings * m^2 * n^2)? BFS's time complexity is
 * O(|V||E|). Here we have mn vertices and the edges are proportional to m*n, so
 * every BFS is O(m^2 * n^2). We do a BFS for every building so the total time
 * complexity is O(#buildings * m^2 * n^2)?
 * 
 * The time complexity for BFS/DFS is O(|V|+|E|), not O(|V||E|). In this
 * problem, every vertex has up to 4 edges (left, right, up, down), so |E| ~
 * 4|V|. Thus, you have overall O(|V|) = O(mn) for a BFS. This has been proven
 * for all sparse graphs like this problem. Now, we do a BFS for each building,
 * so the overall complexity is O(#buildings*(mn)). In worst case, every vertex
 * is a building. So the number of buildings is also upper bounded by O(mn) ,
 * and thus you have O((mn)(mn)) = O(m^2n^2). This is a very loose bound since
 * when every vertex is a building, we don't even need to do a BFS (nowhere to
 * go).
 * 
 * public int shortestDistance(int[][] grid) { if (grid == null ||
 * grid[0].length == 0) return 0; final int[] shift = new int[] {0, 1, 0, -1,
 * 0};
 * 
 * int row = grid.length, col = grid[0].length; int[][] distance = new
 * int[row][col]; int[][] reach = new int[row][col]; int buildingNum = 0;
 * 
 * for (int i = 0; i < row; i++) { for (int j =0; j < col; j++) { if (grid[i][j]
 * == 1) { buildingNum++; Queue<int[]> myQueue = new LinkedList<int[]>();
 * myQueue.offer(new int[] {i,j});
 * 
 * boolean[][] isVisited = new boolean[row][col]; int level = 1;
 * 
 * while (!myQueue.isEmpty()) { int qSize = myQueue.size(); for (int q = 0; q <
 * qSize; q++) { int[] curr = myQueue.poll();
 * 
 * for (int k = 0; k < 4; k++) { int nextRow = curr[0] + shift[k]; int nextCol =
 * curr[1] + shift[k + 1]; // if reach[nextRow][nextCol] < buildingNum, ignore
 * it. if (nextRow >= 0 && nextRow < row && nextCol >= 0 && nextCol < col &&
 * grid[nextRow][nextCol] == 0 && !isVisited[nextRow][nextCol]) { //The shortest
 * distance from [nextRow][nextCol] to thic building // is 'level'.
 * distance[nextRow][nextCol] += level; reach[nextRow][nextCol]++;
 * 
 * isVisited[nextRow][nextCol] = true; myQueue.offer(new int[] {nextRow,
 * nextCol}); } } } level++; } } } } int shortest = Integer.MAX_VALUE; for (int
 * i = 0; i < row; i++) { for (int j = 0; j < col; j++) { if (grid[i][j] == 0 &&
 * reach[i][j] == buildingNum) { shortest = Math.min(shortest, distance[i][j]);
 * } } } return shortest == Integer.MAX_VALUE ? -1 : shortest; }
 * 
 * static final int[] s={-1,0,1,0,-1};
 * 
 * class Move{ int x; int steps; boolean[][] visited; Move(int p, int s){ x=p;
 * steps=s; } }
 * 
 * public int shortestDistance(int[][] grid) { if(grid==null || grid.length==0
 * || grid[0].length==0) return 0; int m = grid.length, n=grid[0].length,
 * total=0, min=Integer.MAX_VALUE; int[][] distance= new int[m][n]; int[][]
 * reach= new int[m][n]; Deque<Move> queue = new ArrayDeque<>(); for(int i=0;
 * i<m; i++){ for(int j=0; j<n; j++){ if(grid[i][j]==1){ Move mv = new
 * Move(i*n+j, 0); mv.visited = new boolean[m][n]; mv.visited[i][j]=true;
 * queue.offer(mv); total++; } } } while(!queue.isEmpty()){ Move mv =
 * queue.poll(); int x = mv.x/n, y=mv.x%n; for(int i=0; i<4; i++){ int p =
 * x+s[i], q=y+s[i+1]; if(p>=0 && p<m && q>=0 && q<n && !mv.visited[p][q] &&
 * grid[p][q]==0){ Move newMv = new Move(p*n+q, mv.steps+1);
 * newMv.visited=mv.visited; newMv.visited[p][q]=true;
 * distance[p][q]+=mv.steps+1; if(distance[p][q]<min) { if(++reach[p][q]==total)
 * min = distance[p][q]; queue.offer(newMv); } } } } return
 * (min==Integer.MAX_VALUE)?-1: min; }
 * http://www.cnblogs.com/yrbbest/p/5068730.html
 * https://leetcode.com/discuss/74380/my-bfs-java-solution private final int[][]
 * directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
 * 
 * public int shortestDistance(int[][] grid) { if(grid == null || grid.length ==
 * 0) { return Integer.MAX_VALUE; } int rowNum = grid.length; int colNum =
 * grid[0].length; int[][] distance = new int[rowNum][colNum]; int[][]
 * canReachBuildings = new int[rowNum][colNum]; int buildingNum = 0;
 * 
 * for(int i = 0; i < rowNum; i++) { for(int j = 0; j < colNum; j++) {
 * if(grid[i][j] != 0) { distance[i][j] = Integer.MAX_VALUE; } if(grid[i][j] ==
 * 1) { // find out all buildings buildingNum++; updateDistance(grid, distance,
 * canReachBuildings, i, j); } } }
 * 
 * int min = Integer.MAX_VALUE; for(int i = 0; i < rowNum; i++) { for(int j = 0;
 * j < colNum; j++) { if(canReachBuildings[i][j] == buildingNum) { min =
 * Math.min(distance[i][j], min); } } }
 * 
 * return min == Integer.MAX_VALUE ? -1 : min; }
 * 
 * private void updateDistance(int[][] grid, int[][] distance, int[][]
 * canReachBuildings, int row, int col) { Queue<int[]> queue = new
 * LinkedList<>(); queue.offer(new int[]{row, col}); boolean[][] visited = new
 * boolean[grid.length][grid[0].length]; visited[row][col] = true; int dist = 0;
 * int curLevel = 1; int nextLevel = 0;
 * 
 * while(!queue.isEmpty()) { int[] position = queue.poll();
 * distance[position[0]][position[1]] += dist; curLevel--; for(int[] direction :
 * directions) { int x = position[0] + direction[0]; int y = position[1] +
 * direction[1]; if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length ||
 * grid[x][y] != 0) { continue; } if(!visited[x][y]) { queue.offer(new int[]{x,
 * y}); nextLevel++; visited[x][y] = true; canReachBuildings[x][y]++; } }
 * if(curLevel == 0) { curLevel = nextLevel; nextLevel = 0; dist++; } } }
 * http://buttercola.blogspot.com/2016/01/leetcode-shortest-distance-from-all.html
 * A BFS problem. Search from each building and calculate the distance to the
 * building. One thing to note is an empty land must be reachable by all
 * buildings. To achieve this, maintain an array of counters. Each time we reach
 * an empty land from a building, increase the counter. Finally, a reachable
 * point must have the counter equaling to the number of buildings.
 * 
 * public int shortestDistance(int[][] grid) {
 * 
 * if (grid == null || grid.length == 0) {
 * 
 * return 0;
 * 
 * }
 * 
 * 
 * 
 * int m = grid.length;
 * 
 * int n = grid[0].length;
 * 
 * 
 * 
 * int[][] dist = new int[m][n];
 * 
 * int[][] reach = new int[m][n];
 * 
 * // step 1: BFS and calcualte the min dist from each building
 * 
 * int numBuildings = 0;
 * 
 * for (int i = 0; i < m; i++) {
 * 
 * for (int j = 0; j < n; j++) {
 * 
 * if (grid[i][j] == 1) {
 * 
 * boolean[][] visited = new boolean[m][n]; // no need declare here
 * 
 * Queue<Integer> queue = new LinkedList<>();
 * 
 * shortestDistanceHelper(i, j, 0, dist, reach, grid, visited, queue);
 * 
 * numBuildings++;
 * 
 * }
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 * // step 2: caluclate the minimum distance
 * 
 * int minDist = Integer.MAX_VALUE;
 * 
 * for (int i = 0; i < m; i++) {
 * 
 * for (int j = 0; j < n; j++) {
 * 
 * if (grid[i][j] == 0 && reach[i][j] == numBuildings) {
 * 
 * minDist = Math.min(minDist, dist[i][j]);
 * 
 * }
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 * return minDist == Integer.MAX_VALUE ? -1 : minDist;
 * 
 * }
 * 
 * 
 * 
 * private void shortestDistanceHelper(int x, int y, int currDist,
 * 
 * int[][] dist, int[][] reach, int[][] grid,
 * 
 * boolean[][] visited, Queue<Integer> queue) {
 * 
 * fill(x, y, x, y, currDist, dist, reach, grid, visited, queue);
 * 
 * 
 * 
 * int m = grid.length;
 * 
 * int n = grid[0].length;
 * 
 * 
 * 
 * while (!queue.isEmpty()) {
 * 
 * int size = queue.size();
 * 
 * currDist++;
 * 
 * for (int sz = 0; sz < size; sz++) {
 * 
 * int cord = queue.poll();
 * 
 * int i = cord / n;
 * 
 * int j = cord % n;
 * 
 * 
 * 
 * fill(x, y, i - 1, j, currDist, dist, reach, grid, visited, queue);
 * 
 * fill(x, y, i + 1, j, currDist, dist, reach, grid, visited, queue);
 * 
 * fill(x, y, i, j - 1, currDist, dist, reach, grid, visited, queue);
 * 
 * fill(x, y, i, j + 1, currDist, dist, reach, grid, visited, queue);
 * 
 * }
 * 
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 * private void fill(int origX, int origY, int x, int y, int currDist,
 * 
 * int[][] dist, int[][] reach,
 * 
 * int[][] grid, boolean[][] visited, Queue<Integer> queue) {
 * 
 * 
 * 
 * int m = dist.length;
 * 
 * int n = dist[0].length;
 * 
 * if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y]) {
 * 
 * return;
 * 
 * }
 * 
 * 
 * 
 * if ((x != origX || y != origY) && grid[x][y] != 0) {
 * 
 * return;
 * 
 * }
 * 
 * 
 * 
 * visited[x][y] = true;
 * 
 * 
 * 
 * dist[x][y] += currDist;
 * 
 * reach[x][y]++;
 * 
 * 
 * 
 * queue.offer(x * n + y);
 * 
 * }
 * https://leetcode.com/discuss/92508/11ms-simple-java-soluton-that-beats-100%25-with-explanation
 * public int shortestDistance(int[][] grid) {
 * 
 * int[][] d = new int[grid.length][grid[0].length];//sum of distance from all
 * buildings int[][] cc = new int[grid.length][grid[0].length];//reachable by
 * how many buildings int count = 0; //find how many buildings for(int i =
 * 0;i<grid.length;i++){ for(int j = 0;j<grid[0].length;j++){ if(grid[i][j] ==
 * 1) { count++; } } }
 * 
 * for(int i = 0;i<grid.length;i++){ for(int j = 0;j<grid[0].length;j++){
 * if(grid[i][j] == 1) { //bfs each building, keep on updating empty spot's
 * distance value boolean x = bfs(grid, i, j, d, new
 * boolean[grid.length][grid[0].length], count, cc); if(!x) return -1;//if this
 * building can not reach all other buildings return -1 } } }
 * 
 * //find the smallest distance, need be an empty spot, and a spot can be
 * reached by all buildings int res = Integer.MAX_VALUE; for(int i =
 * 0;i<grid.length;i++){ for(int j = 0;j<grid[0].length;j++){ if(d[i][j] != 0 &&
 * cc[i][j] == count) res = Math.min(res, d[i][j]); } } return res ==
 * Integer.MAX_VALUE ? -1 : res ; }
 * 
 * //nothing much, just bfs, but updates how many building can an empty spot
 * reach, and //if current building can reach all other buildings, otherwise
 * returns false, solution returns -1 private boolean bfs(int[][]grid, int i,
 * int j, int[][] d, boolean[][] used, int count, int[][] cc) { Queue<Integer>
 * is = new LinkedList<>(); Queue<Integer> js = new LinkedList<>();
 * Queue<Integer> ds = new LinkedList<>(); is.add(i); js.add(j); ds.add(0);
 * used[i][j] = true; int[] xx = new int[]{-1,0,1,0}; int[] yy = new
 * int[]{0,-1,0,1}; int c = 1;//used to check whether this house can reach all
 * houses int neigh = 0; //used to check whether it can reach any empty land
 * while(!is.isEmpty()){ int x = is.poll(); int y = js.poll(); int dis =
 * ds.poll(); for(int k =0 ;k<4;k++){ int newx = x + xx[k]; int newy = y +
 * yy[k]; if(newx >=0 && newx < grid.length && newy >=0 && newy<grid[0].length
 * && !used[newx][newy]) { if(grid[newx][newy] == 0) { is.add(newx);
 * js.add(newy); ds.add(dis+1); used[newx][newy] = true; d[newx][newy] += dis+1;
 * cc[newx][newy]++; neigh++; } else if(grid[newx][newy] == 1) { c++;
 * used[newx][newy] = true; } } } } return c == count && neigh > 0; }
 * https://leetcode.com/discuss/74441/share-my-easy-java-solution-with-comments
 * private class Point { public int i; public int j; public int steps; public
 * Point(int i, int j, int steps){ this.i = i; this.j = j; this.steps = steps; }
 * }
 * 
 * public int shortestDistance(int[][] grid) { int height = grid.length, width =
 * grid[0].length; int min = Integer.MAX_VALUE, houses = 0; // Count all
 * existing houses in grid for(int i=0; i<height; i++){ for(int j=0; j<width;
 * j++){ if(grid[i][j] == 1) houses++; } } // Start from all empty cells one by
 * one for(int i=0; i<height; i++){ for(int j=0; j<width; j++){ if(grid[i][j] ==
 * 0){ int sum = search(i, j, houses, grid); min = Math.min(min, sum); } } } //
 * Can't find all existing houses if(min == Integer.MAX_VALUE) return -1; else
 * return min; }
 * 
 * public int search(int i, int j, int houses, int[][] grid){ int height =
 * grid.length, width = grid[0].length; boolean[][] marked = new
 * boolean[height][width]; int houseCount = 0, sum = 0; Queue<Point> q = new
 * LinkedList<>(); q.add(new Point(i, j, 0)); marked[i][j] = true;
 * while(!q.isEmpty()){ Point point = q.poll(); // Traverse four surrounding
 * cells for(int m=-1; m<=1; m++){ for(int n=-1; n<=1; n++){ // Current point
 * itself or out of boundary if((Math.abs(m) == Math.abs(n)) || !(point.i+m >=0
 * && point.i+m<height && point.j+n>=0 && point.j+n<width) ||
 * marked[point.i+m][point.j+n]) continue; int cell =
 * grid[point.i+m][point.j+n]; // Find a unmarked house if(cell == 1){
 * houseCount++; sum += point.steps+1; marked[point.i+m][point.j+n] = true; }
 * else if(cell == 0){ q.add(new Point(point.i+m, point.j+n, point.steps+1));
 * marked[point.i+m][point.j+n] = true; } } } } // Can't find all existing
 * houses if(houseCount < houses) return Integer.MAX_VALUE; else return sum; } }
 * https://evanyang.gitbooks.io/leetcode/content/LeetCode/shortest_distance_from_all_buildings.html
 * -- good naming
 * https://leetcode.com/discuss/74526/java-and-python-solutions-with-bfs public
 * int shortestDistance(int[][] grid) { if (grid == null || grid.length == 0 ||
 * grid[0] == null || grid[0].length == 0) return -1; List<int[]> emptyLands =
 * new ArrayList<>(); List<int[]> buildings = new ArrayList<>(); int nrow =
 * grid.length; int ncol = grid[0].length; int[][] dists = new int[nrow][ncol];
 * int[][] visitedNums = new int[nrow][ncol]; int minimalDist =
 * Integer.MAX_VALUE;
 * 
 * // Find Buildings and Empty Lands for (int i = 0; i < nrow; i++) { for (int j
 * = 0; j < ncol; j++) { if (grid[i][j] == 0) { emptyLands.add(new int[]{i, j});
 * } else if (grid[i][j] == 1) { buildings.add(new int[]{i, j}); } } } //BFS for
 * each Building for (int[] indices: buildings) { Queue<int[]> queue = new
 * ArrayDeque<>(); boolean[][] visited = new boolean[nrow][ncol];
 * queue.offer(new int[]{indices[0], indices[1], 0}); while (!queue.isEmpty()) {
 * int[] current = queue.poll(); int x = current[0], y = current[1], dist =
 * current[2]; if (x+1 < nrow && grid[x+1][y] == 0 && !visited[x+1][y]) {
 * dists[x+1][y] += dist + 1; queue.offer(new int[]{x+1, y, dist+1});
 * visited[x+1][y] = true; visitedNums[x+1][y]++; } if (x-1 >= 0 && grid[x-1][y]
 * == 0 && !visited[x-1][y]) { dists[x-1][y] += dist + 1; queue.offer(new
 * int[]{x-1, y, dist+1}); visited[x-1][y] = true; visitedNums[x-1][y]++; } if
 * (y+1 < ncol && grid[x][y+1] == 0 && !visited[x][y+1]) { dists[x][y+1] += dist
 * + 1; queue.offer(new int[]{x, y+1, dist+1}); visited[x][y+1] = true;
 * visitedNums[x][y+1]++; } if (y-1 >= 0 && grid[x][y-1] == 0 &&
 * !visited[x][y-1]) { dists[x][y-1] += dist + 1; queue.offer(new int[]{x, y-1,
 * dist+1}); visited[x][y-1] = true; visitedNums[x][y-1]++; } } }
 * 
 * //Find the Empty Land with smallest total dist int size = buildings.size();
 * for (int[] indices: emptyLands) { int x = indices[0], y = indices[1]; if
 * (dists[x][y] < minimalDist && visitedNums[x][y] == size) { minimalDist =
 * dists[x][y]; } } return minimalDist == Integer.MAX_VALUE ? -1 : minimalDist;
 * } http://www.cnblogs.com/icecreamdeqinw/p/5048375.html
 * 
 * @author het
 *
 */
public class LeetCode317 {
	// The shortest distance from any empty land to a building can be calculated by
	// BFS starting from the building in O(mn)time. Therefore the we can calculate
	// distance
	// from all buildings to empty lands by t rounds of BFS starting from each
	// building. t is the total number of buildings.
	// In each round, we need maintain two values for every empty land: the distance
	// and the accessibility.
	// dist[i][j] is the empty land (i, j) to all the buildings.
	// grid[i][j] is reused as the accessibility.
	// What is accessibility? It is the number of buildings that are accessible from
	// the empty land.
	// In each round of BFS we can maintain these two values. In the end we just
	// need to find the minimum value of dist[i][j]
	// where the accessibility equals t.
	// One interesting point is that the grid[i][j] can also be used to check if (i,
	// j) is visited in this round.
	// At round k(zero based), those has grid[i][j] == k is the empty land
	// unvisited,
	// visited land will have grid[i][j] == k + 1. Here comes the interesting part.
	// One may ask what if grid[i][j] < k?
	// Answer is we do not go into the lands withgrid[i][j] < k as if it is an
	// obstacle. Why can we do that?
	// Because grid[i][j] < k means it is not accessible by at least one of the
	// buildings in previous rounds.
	// Which means not only this land is not our answer, all the lands accessible
	// from (i, j) is also not our answer.
	// This might be why it runs faster than many implements. The Java version runs
	// in 13 ms.
	int[] dx = { 1, 0, -1, 0 }, dy = { 0, 1, 0, -1 };

	public int shortestDistance(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		int[][] dist = new int[m][n];
		// Initialize building list and accessibility matrix `grid`
		List<Tuple> buildings = new ArrayList<>();
		for (int i = 0; i < m; ++i)
			for (int j = 0; j < n; ++j) {
				if (grid[i][j] == 1)
					buildings.add(new Tuple(i, j, 0));
				grid[i][j] = -grid[i][j];
			}
		// BFS from every building
		for (int k = 0; k < buildings.size(); ++k)
			bfs(buildings.get(k), k, dist, grid, m, n);
		// Find the minimum distance
		int ans = -1;
		for (int i = 0; i < m; ++i)
			for (int j = 0; j < n; ++j)
				if (grid[i][j] == buildings.size() && (ans < 0 || dist[i][j] < ans))
					ans = dist[i][j];
		return ans;
	}

	public void bfs(Tuple root, int k, int[][] dist, int[][] grid, int m, int n) {
		Queue<Tuple> q = new ArrayDeque<>(); // ..、、、、ArrayDeque
		q.add(root);
		while (!q.isEmpty()) {
			Tuple b = q.poll();
			dist[b.y][b.x] += b.dist;
			for (int i = 0; i < 4; ++i) {
				int x = b.x + dx[i], y = b.y + dy[i];
				if (y >= 0 && x >= 0 && y < m && x < n && grid[y][x] == k) {
					grid[y][x] = k + 1;
					q.add(new Tuple(y, x, b.dist + 1));
				}
			}
		}
	}

	class Tuple {
		public int y;
		public int x;
		public int dist;

		public Tuple(int y, int x, int dist) {
			this.y = y;
			this.x = x;
			this.dist = dist;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
