package alite.leetcode.xx2.sucess.extra;

import java.util.LinkedList;
import java.util.Queue;

/**
 * [LeetCode] Walls and Gates - jcliBlogger - 博客园
You are given a m x n 2D grid initialized with these three possible values.
-1 - A wall or an obstacle.
0 - A gate.
INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF 
as you may assume that the distance to a gate is less than2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, i
t should be filled with INF.

For example, given the 2D grid:
INF  -1  0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF INF
 After running your function, the 2D grid should be:
  3  -1   0   1
  2   2   1  -1
  1  -1   2  -1
  0  -1   3   4
An application of BFS. The key is to apply appropriate pruning
http://buttercola.blogspot.com/2015/09/leetcode-walls-and-gates.html
It is very classic backtracking problem. We can start from each gate (0 point), and searching for its neighbors. We can either use DFS or BFS solution.
http://shibaili.blogspot.com/2015/09/day-130-286-expression-add-operators.html
Interview - What may change, explain and
Make code readable, extendable.
Pass a direction array. {-1, 1} etc
http://algobox.org/walls-and-gates/
The performances of MultiEnd and Naive BFS are very stable and have time complexities of O(n^2) for a n x n square matrix.
The performance of recursive DFS is very unstable. It is much slower than BFS if the rooms are interconnected. It is only faster than BFS when small groups of rooms are isolated.
Thus, for this problem we should prefer BFS over DFS. And the best Solution is Multi End BFS.
DFS is much slower than BFS. DFS is repeatedly updating the cell distance. Since there is only one gate in this case, NaiveBFS is expected to perform just like the MultiEndBFS.

X. BFS
http://segmentfault.com/a/1190000003906674
实际上就是找每个房间到最近的门的距离，我们从每个门开始，广度优先搜索并记录层数就行了。如果某个房间之前被标记过距离，那就选择这个距离和当前距离中较小的那个。这题要注意剪枝，如果下一步是门或者下一步是墙或者下一步已经访问过了，就不要加入队列中。否则会超时。

Don't need the visisted hashset.
    public void wallsAndGates(int[][] rooms) {
        if(rooms.length == 0) return;
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                // 如果遇到一个门，从门开始广度优先搜索，标记连通的节点到自己的距离
                if(rooms[i][j] == 0) bfs(rooms, i, j);
            }
        }
    }
    
    public void bfs(int[][] rooms, int i, int j){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(i * rooms[0].length + j);
        int dist = 0;
        // 用一个集合记录已经访问过的点
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(i * rooms[0].length + j);
        while(!queue.isEmpty()){
            int size = queue.size();
            // 记录深度的搜索
            for(int k = 0; k < size; k++){
                Integer curr = queue.poll();
                int row = curr / rooms[0].length;
                int col = curr % rooms[0].length;
                // 选取之前标记的值和当前的距离的较小值
                rooms[row][col] = Math.min(rooms[row][col], dist);
                int up = (row - 1) * rooms[0].length + col;
                int down = (row + 1) * rooms[0].length + col;
                int left = row * rooms[0].length + col - 1;
                int right = row * rooms[0].length + col + 1;
                if(row > 0 && rooms[row - 1][col] > 0 && !visited.contains(up)){
                    queue.offer(up);
                    visited.add(up);
                }
                if(col > 0 && rooms[row][col - 1] > 0 && !visited.contains(left)){
                    queue.offer(left);
                    visited.add(left);
                }
                if(row < rooms.length - 1 && rooms[row + 1][col] > 0 && !visited.contains(down)){
                    queue.offer(down);
                    visited.add(down);
                }
                if(col < rooms[0].length - 1 && rooms[row][col + 1] > 0 && !visited.contains(right)){
                    queue.offer(right);
                    visited.add(right);
                }
            }
            dist++;
        }
    }
http://algobox.org/walls-and-gates/
https://leetcode.com/discuss/82264/benchmarks-of-dfs-and-bfs
The performances of MultiEnd is very stable and have time complexities of O(n^2) for a n x nsquare matrix.
The time complexity for NaiveBFS should be O(n^4) in the worst case. However is not shown in our tests.
The performance of recursive DFS is very unstable. It is much slower than BFS if the rooms are interconnected. It is only faster than BFS when small groups of rooms are isolated. In the worst case the time complexity is also O(n^4).
Thus, for this problem we should prefer BFS over DFS. And the best Solution is Multi End BFS.

Because in the worst case, we can have O(n^2) gates. For example 1/4n^2. For each gate, DFS will need to travel n^2 to 1 rooms, thus total O(n^4).
A visited matrix is not necessary. Firstly, because rooms matrix contains this information; Secondly, and mainly, because DFS need to revisit a cell even if it is already visited by other gates to update the cell to the minimum distance.
And that is exactly why the time complexity of DFS in the worst case can be O(n^4).


    public static final int[] d = {0, 1, 0, -1, 0};


    public void wallsAndGates(int[][] rooms) {

        if (rooms.length == 0) return;

        for (int i = 0; i < rooms.length; ++i)

            for (int j = 0; j < rooms[0].length; ++j)

                if (rooms[i][j] == 0) bfs(rooms, i, j);

    }


    private void bfs(int[][] rooms, int i, int j) {

        int m = rooms.length, n = rooms[0].length;

        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(i * n + j); // Put gate in the queue

        while (!queue.isEmpty()) {

            int x = queue.poll();

            i = x / n; j = x % n;

            for (int k = 0; k < 4; ++k) {

                int p = i + d[k], q = j + d[k + 1];

                if (0 <= p && p < m && 0 <= q && q < n && rooms[p][q] > rooms[i][j] + 1) {

                    rooms[p][q] = rooms[i][j] + 1;

                    queue.offer(p * n + q);

                }

            }

        }

    }
X. Java(Multi End BFS)
https://leetcode.com/discuss/60179/java-bfs-solution-o-mn-time
Push all gates into queue first. Then for each gate update its neighbor cells and push them to the queue.
Repeating above steps until there is nothing left in the queue.
public class Solution {
    public void wallsAndGates(int[][] rooms) {
        LinkedList<int[]> list = new LinkedList<int[]>();
        for(int i = 0; i < rooms.length; i++) {
            for(int j = 0; j < rooms[0].length; j++) {
                if(rooms[i][j] == 0) 
                    list.add(new int[]{i,j});
            }
        }
        int[][] diff = new int[][]{{-1,0,1,0},{0,1,0,-1}};
        while(!list.isEmpty()) {
            int[] pop = list.remove();
            for(int i = 0; i < diff[0].length; i++) {
                int newR = pop[0] + diff[0][i];
                int newC = pop[1] + diff[1][i];
                if(newR < 0 || newR >= rooms.length || newC < 0 || newC >= rooms[0].length || 
                    rooms[newR][newC] != Integer.MAX_VALUE) 
                    continue;
                rooms[newR][newC] = rooms[pop[0]][pop[1]] + 1;
                list.add(new int[]{newR, newC});
            }
        }
    }



    public static final int[] d = {0, 1, 0, -1, 0};


    public void wallsAndGates(int[][] rooms) {

        if (rooms.length == 0) return;

        int m = rooms.length, n = rooms[0].length;


        Deque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < m; ++i)

            for (int j = 0; j < n; ++j)

                if (rooms[i][j] == 0) queue.offer(i * n + j); // Put gates in the queue


        while (!queue.isEmpty()) {

            int x = queue.poll();

            int i = x / n, j = x % n;

            for (int k = 0; k < 4; ++k) {

                int p = i + d[k], q = j + d[k + 1]; // empty room

                if (0 <= p && p < m && 0 <= q && q < n &&

                    rooms[p][q] == Integer.MAX_VALUE) {

                    rooms[p][q] = rooms[i][j] + 1;

                    queue.offer(p * n + q);

                }

            }

        }


    }

找出所有的gate，然后同时bfs
http://buttercola.blogspot.com/2015/09/leetcode-walls-and-gates.html

    public void wallsAndGates(int[][] rooms) {

        if (rooms == null || rooms.length == 0) {

            return;

        }

         

        int m = rooms.length;

        int n = rooms[0].length;

         

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                if (rooms[i][j] == 0) {

                    wallsAndGatesHelper(i, j, 0, rooms, queue);

                }

            }

        }

    }

     

    private void wallsAndGatesHelper(int row, int col, int distance, int[][] rooms, Queue<Integer> queue) {

        fill(row, col, distance, rooms, queue);

         

        int m = rooms.length;

        int n = rooms[0].length;

         

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                int cord = queue.poll();

                int x = cord / n;

                int y = cord % n;

             

                fill(x - 1, y, distance + 1, rooms, queue);

                fill(x + 1, y, distance + 1, rooms, queue);

                fill(x, y - 1, distance + 1, rooms, queue);

                fill(x, y + 1, distance + 1, rooms, queue);

             

            }

            distance++;

        }

    }

     

    private void fill (int row, int col, int distance, int[][] rooms, Queue<Integer> queue) {

        int m = rooms.length;

        int n = rooms[0].length;

         

        if (row < 0 || row >= m || col < 0 || col >= n) {

            return;

        }

         

        if (rooms[row][col] == -1) {

            return;

        }

         

        if (distance > rooms[row][col]) {

            return;

        }

         

        if (distance < rooms[row][col]) {

            rooms[row][col] = distance;

        }

         

        int cord = row * n + col;

        queue.offer(cord);

    }




    void wallsAndGates(vector<vector<int>>& rooms) {

        int m = rooms.size();

        if (m == 0) return;

        int n = rooms[0].size();

        queue<pair<int,int>> que;

         

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                if (rooms[i][j] == 0) {

                    que.push(make_pair(i,j));

                }

            }

        }

         

        int size = que.size();

        int dis = 1;

        while (!que.empty()) {

            int row = que.front().first;

            int col = que.front().second;

            size--;

            que.pop();

            

            if (row + 1 < m && rooms[row + 1][col] > dis) {

                rooms[row + 1][col] = dis;

                que.push(make_pair(row + 1,col));

            }

            if (row - 1 >= 0 && rooms[row - 1][col] > dis) {

                rooms[row - 1][col] = dis;

                que.push(make_pair(row - 1,col));

            }

            if (col + 1 < n && rooms[row][col + 1] > dis) {

                rooms[row][col + 1] = dis;

                que.push(make_pair(row,col + 1));

            }

            if (col - 1 >= 0 && rooms[row][col - 1] > dis) {

                rooms[row][col - 1] = dis;

                que.push(make_pair(row,col - 1));

            }

             

            if (size == 0) {

                size = que.size();

                dis++;

            }

        }

    }
X. BFS + Pruning
http://tiancao.me/Leetcode-Unlocked/LeetCode%20Locked/c1.24.html
    void wallsAndGates(vector<vector<int>>& rooms) {
        if (rooms.empty()) return;
        int m = rooms.size(), n = rooms[0].size();
        queue<pair<int, int>> q;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!rooms[i][j]) q.push({i, j});
            }
        }

        for (int d = 1; !q.empty(); d++) {
            for (int k = q.size(); k; k--) {
                int i = q.front().first, j = q.front().second;
                q.pop();
                add(rooms, q, i - 1, j, m, n, d);
                add(rooms, q, i + 1, j, m, n, d);
                add(rooms, q, i, j - 1, m, n, d);
                add(rooms, q, i, j + 1, m, n, d);
            }
        }
    }
private:
    void add(vector<vector<int>> &rooms, queue<pair<int, int>> &q, int i, int j, int m, int n, int d) {
        if (i >= 0 && i < m && j >= 0 && j < n && rooms[i][j] > d) {
            q.push({i, j});
            rooms[i][j] = d;
        }
    }
2 public:
 3     void wallsAndGates(vector<vector<int>>& rooms) {
 4         if (rooms.empty()) return;
 5         int m = rooms.size(), n = rooms[0].size();
 6         for (int i = 0; i < m; i++) {
 7             for (int j = 0; j < n; j++) {
 8                 if (!rooms[i][j]) {
 9                     queue<Grid> grids;
10                     grids.push(Grid(i + 1, j, 1));
11                     grids.push(Grid(i - 1, j, 1));
12                     grids.push(Grid(i, j + 1, 1));
13                     grids.push(Grid(i, j - 1, 1));
14                     while (!grids.empty()) {
15                         Grid grid = grids.front();
16                         grids.pop();
17                         int r = grid.r, c = grid.c, d = grid.d;
18                         if (r < 0 || r >= m || c < 0 || c >= n || rooms[r][c] < d)
19                             continue;
20                         rooms[r][c] = d;
21                         grids.push(Grid(r + 1, c, d + 1));
22                         grids.push(Grid(r - 1, c, d + 1));
23                         grids.push(Grid(r, c + 1, d + 1));
24                         grids.push(Grid(r, c - 1, d + 1));
25                     }
26                 }
27             }
28         }
29     }
30 private:
31     struct Grid {
32         int r, c, d;
33         Grid(int _r, int _c, int _d) : r(_r), c(_c), d(_d) {}
34     };

3     void wallsAndGates(vector<vector<int>>& rooms) {
 4         int m = rooms.size(), n = m ? rooms[0].size() : 0;
 5         queue<pair<int, int>> q;
 6         for (int i = 0; i < m; i++)
 7             for (int j = 0; j < n; j++)
 8                 if (!rooms[i][j]) q.push({i, j});
 9         for (int d = 1; !q.empty(); d++) {
10             for (int k = q.size(); k; k--) {
11                 int i = q.front().first, j = q.front().second;
12                 q.pop();
13                 add(rooms, q, i - 1, j, m, n, d);
14                 add(rooms, q, i + 1, j, m, n, d);
15                 add(rooms, q, i, j - 1, m, n, d);
16                 add(rooms, q, i, j + 1, m, n, d); 
17             }
18         }
19     }
20 private:
21     void add(vector<vector<int>>& rooms, queue<pair<int, int>>& q, int i, int j, int m, int n, int d) {
22         if (i >= 0 && i < m && j >= 0 && j < n && rooms[i][j] > d) {
23             q.push({i, j});
24             rooms[i][j] = d;
25         }
26     }

X. DFS  
https://leetcode.com/discuss/78333/my-short-java-solution-very-easy-to-understand
Use origin input as visited[][]
I think it should be O(kmn) where k is the number of 0.
I think time complexity should be O(mn) since each entry will be visited at most 4 times which means O(4mn)
public void wallsAndGates(int[][] rooms) {
    for (int i = 0; i < rooms.length; i++)
        for (int j = 0; j < rooms[0].length; j++)
            if (rooms[i][j] == 0) helper(rooms, i, j, 0);
}

private void helper(int[][] rooms, int i, int j, int d) {
    if (i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length || rooms[i][j] < d) return;
    rooms[i][j] = d;
    helper(rooms, i - 1, j, d + 1);
    helper(rooms, i + 1, j, d + 1);
    helper(rooms, i, j - 1, d + 1);
    helper(rooms, i, j + 1, d + 1);
}
http://blog.csdn.net/xudli/article/details/48748547
    public void wallsAndGates(int[][] rooms) {
        if(rooms==null || rooms.length==0 || rooms[0]==null || rooms[0].length==0) return;
        int m = rooms.length;
        int n =rooms[0].length;        
        boolean[][] visited = new boolean[m][n];
        
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(rooms[i][j] == Integer.MAX_VALUE) {
                    rooms[i][j] = search(rooms, visited, i, j, m, n);
                }
            }
        }
        return;
    }
    
    private int search(int[][] rooms, boolean[][] visited, int i, int j, int m, int n) {
        if(i<0 || i>m-1 || j<0 || j>n-1 || rooms[i][j] == -1) return Integer.MAX_VALUE;
        if(rooms[i][j]==0) return 0;
        if(visited[i][j]) return rooms[i][j];
        visited[i][j] = true;
        
        if(rooms[i][j]>0 && rooms[i][j]<Integer.MAX_VALUE) return rooms[i][j];        
        int up = search(rooms, visited, i-1, j, m, n);
        int down = search(rooms, visited, i+1, j, m, n);
        int left = search(rooms, visited, i, j-1, m, n);
        int right = search(rooms, visited, i, j+1, m, n);
        
        visited[i][j] = false;
        
        int min = Math.min( Math.min(up, down), Math.min(left, right) );
        return min==Integer.MAX_VALUE ? min : min+1;
    }
http://buttercola.blogspot.com/2015/09/leetcode-walls-and-gates.html

    public void wallsAndGates(int[][] rooms) {

        if (rooms == null || rooms.length == 0) {

            return;

        }

         

        int m = rooms.length;

        int n = rooms[0].length;

         

        boolean[][] visited = new boolean[m][n];

         

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                if (rooms[i][j] == 0) {

                    wallsAndGatesHelper(i, j, 0, visited, rooms);

                }

            }

        }

    }

     

    private void wallsAndGatesHelper(int row, int col, int distance, boolean[][] visited, int[][] rooms) {

        int rows = rooms.length;

        int cols = rooms[0].length;

         

        if (row < 0 || row >= rows || col < 0 || col >= cols) {

            return;

        }

         

        // visited

        if (visited[row][col]) {

            return;

        }

         

        // Is wall?

        if (rooms[row][col] == -1) {

            return;

        }

         

        // Distance greater than current

        if (distance > rooms[row][col]) {

            return;

        }

         

         

        // Mark as visited

        visited[row][col] = true;

         

        if (distance < rooms[row][col]) {

            rooms[row][col] = distance;

        }

         

        // go up, down, left and right

        wallsAndGatesHelper(row - 1, col, distance + 1, visited, rooms);

        wallsAndGatesHelper(row + 1, col, distance + 1, visited, rooms);

        wallsAndGatesHelper(row, col - 1, distance + 1, visited, rooms);

        wallsAndGatesHelper(row, col + 1, distance + 1, visited, rooms);

         

        // Mark as unvisited

        visited[row][col] = false;

    }
http://likemyblogger.blogspot.com/2015/09/leetcode-286-walls-and-gates.html

    void wallsAndGates(vector<vector<int>>& rooms) {

        int m = rooms.size();

        if(m==0) return;

        int n = rooms[0].size();

        if(n==0) return;

         

        for(int i=0; i<m; ++i){

            for(int j=0; j<n; ++j){

                stack<pair<int, int>> stk;

                if(rooms[i][j]==0){

                    stk.push(pair<int, int>(i,j));

                    while(!stk.empty()){

                        int x = stk.top().first, y = stk.top().second;

                        stk.pop();

                        if(x-1>=0 && rooms[x-1][y]>rooms[x][y]+1){rooms[x-1][y] = rooms[x][y]+1; stk.push(pair<int, int>(x-1,y));}

                        if(x+1<m  && rooms[x+1][y]>rooms[x][y]+1){rooms[x+1][y] = rooms[x][y]+1; stk.push(pair<int, int>(x+1,y));}

                        if(y-1>=0 && rooms[x][y-1]>rooms[x][y]+1){rooms[x][y-1] = rooms[x][y]+1; stk.push(pair<int, int>(x,y-1));}

                        if(y+1<n  && rooms[x][y+1]>rooms[x][y]+1){rooms[x][y+1] = rooms[x][y]+1; stk.push(pair<int, int>(x,y+1));}

                    }

                }

            }

        }

    }

Time complexity:
https://leetcode.com/discuss/61429/little-bit-confused-about-the-time-complexity-here
It is really O(mn). Image each gate as the root of a tree, the queue could magically guarantee we traverse level 
by level of each trees.
step 1: add each root node into the queue.
step 2: pop a node(root) out, visit it is 1st level children(if has not been visited), add all 1st level 
children into the queue. then pop second node(root) out ...


https://discuss.leetcode.com/topic/176/calculate-minimum-path-from-a-given-point-i-j-to-point-x-y-in-a-martix/8
with BFS complexity is O(E+V), we have mn4 E maximum (in case there are 0 blocks) and m*n vertexes.
Why is complexity exponential? We can also run two BFS simultaneosuly from both point and the first point 
they meet ,we stop

with BFS you find the minimum path without trying all possibilities, as you do with DFS. I don't see any sense to traverse deep in the graph when you need to find minimum distance to the point

Read full article from [LeetCode] Walls and Gates - jcliBlogger - 博客园
 * @author het
 *
 */
public class LeetCode286WallsandGates {

	
	 public void wallsAndGates(int[][] rooms) {

	        if (rooms == null || rooms.length == 0) {

	            return;

	        }

	         

	        int m = rooms.length;

	        int n = rooms[0].length;

	         

	        Queue<Integer> queue = new LinkedList<>();

	        for (int i = 0; i < m; i++) {

	            for (int j = 0; j < n; j++) {

	                if (rooms[i][j] == 0) {

	                    wallsAndGatesHelper(i, j, 0, rooms, queue);

	                }

	            }

	        }

	    }

	     

	    private void wallsAndGatesHelper(int row, int col, int distance, int[][] rooms, Queue<Integer> queue) {

	        fill(row, col, distance, rooms, queue);

	         

	        int m = rooms.length;

	        int n = rooms[0].length;

	         

	        while (!queue.isEmpty()) {

	            int size = queue.size();

	            for (int i = 0; i < size; i++) {

	                int cord = queue.poll();

	                int x = cord / n;

	                int y = cord % n;

	             

	                fill(x - 1, y, distance + 1, rooms, queue);

	                fill(x + 1, y, distance + 1, rooms, queue);

	                fill(x, y - 1, distance + 1, rooms, queue);

	                fill(x, y + 1, distance + 1, rooms, queue);

	             

	            }

	            distance++;

	        }

	    }

	     

	    private void fill (int row, int col, int distance, int[][] rooms, Queue<Integer> queue) {

	        int m = rooms.length;

	        int n = rooms[0].length;

	         

	        if (row < 0 || row >= m || col < 0 || col >= n) {

	            return;

	        }

	         

	        if (rooms[row][col] == -1) {

	            return;

	        }

	         

	        if (distance > rooms[row][col]) {

	            return;

	        }

	         

	        if (distance < rooms[row][col]) {

	            rooms[row][col] = distance;

	        }

	         

	        int cord = row * n + col;

	        queue.offer(cord);

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
