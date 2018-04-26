package alite.leetcode.xx4.select;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/pacific-atlantic-water-flow/
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent
 the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.
Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.
Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
Note:
The order of returned grid coordinates does not matter.
Both m and n are less than 150.
Example:


Given the following 5x5 matrix:

  Pacific ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
X. BFS
https://discuss.leetcode.com/topic/62379/java-bfs-dfs-from-ocean
    int[][]dir = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new LinkedList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return res;
        }
        int n = matrix.length, m = matrix[0].length;
        //One visited map for each ocean
        boolean[][] pacific = new boolean[n][m];
        boolean[][] atlantic = new boolean[n][m];
        Queue<int[]> pQueue = new LinkedList<>();
        Queue<int[]> aQueue = new LinkedList<>();
        for(int i=0; i<n; i++){ //Vertical border
            pQueue.offer(new int[]{i, 0});
            aQueue.offer(new int[]{i, m-1});
            pacific[i][0] = true;
            atlantic[i][m-1] = true;
        }
        for(int i=0; i<m; i++){ //Horizontal border
            pQueue.offer(new int[]{0, i});
            aQueue.offer(new int[]{n-1, i});
            pacific[0][i] = true;
            atlantic[n-1][i] = true;
        }
        bfs(matrix, pQueue, pacific);
        bfs(matrix, aQueue, atlantic);
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(pacific[i][j] && atlantic[i][j])
                    res.add(new int[]{i,j});
            }
        }
        return res;
    }
    public void bfs(int[][]matrix, Queue<int[]> queue, boolean[][]visited){
        int n = matrix.length, m = matrix[0].length;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            for(int[] d:dir){
                int x = cur[0]+d[0];
                int y = cur[1]+d[1];
                if(x<0 || x>=n || y<0 || y>=m || visited[x][y] || matrix[x][y] < matrix[cur[0]][cur[1]]){
                    continue;
                }
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            } 
        }
    }

http://bookshadow.com/weblog/2016/10/09/leetcode-pacific-atlantic-water-flow/
BFS（广度优先搜索）
将矩形的左边和上边各点加入“太平洋”点集pacific
将矩形的右边和下边各点加入“大西洋”点集atlantic
分别对pacific与atlantic执行BFS
两者的交集即为答案。
    def pacificAtlantic(self, matrix):
        """
        :type matrix: List[List[int]]
        :rtype: List[List[int]]
        """
        m = len(matrix)
        n = len(matrix[0]) if m else 0
        if m * n == 0: return []
        topEdge = [(0, y) for y in range(n)]
        leftEdge = [(x, 0) for x in range(m)]
        pacific = set(topEdge + leftEdge)
        bottomEdge = [(m - 1, y) for y in range(n)]
        rightEdge = [(x, n - 1) for x in range(m)]
        atlantic = set(bottomEdge + rightEdge)
        def bfs(vset):
            dz = zip((1, 0, -1, 0), (0, 1, 0, -1))
            queue = list(vset)
            while queue:
                hx, hy = queue.pop(0)
                for dx, dy in dz:
                    nx, ny = hx + dx, hy + dy
                    if 0 <= nx < m and 0 <= ny < n:
                        if matrix[nx][ny] >= matrix[hx][hy]:
                            if (nx, ny) not in vset:
                                queue.append((nx, ny))
                                vset.add((nx, ny))
        bfs(pacific)
        bfs(atlantic)
        result = pacific & atlantic
        return map(list, result)


X. DFS
https://discuss.leetcode.com/topic/62379/java-bfs-dfs-from-ocean
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new LinkedList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return res;
        }
        int n = matrix.length, m = matrix[0].length;
        boolean[][]pacific = new boolean[n][m];
        boolean[][]atlantic = new boolean[n][m];
        for(int i=0; i<n; i++){
            dfs(matrix, pacific, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlantic, Integer.MIN_VALUE, i, m-1);
        }
        for(int i=0; i<m; i++){
            dfs(matrix, pacific, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlantic, Integer.MIN_VALUE, n-1, i);
        }
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) 
                if (pacific[i][j] && atlantic[i][j]) 
                    res.add(new int[] {i, j});
        return res;
    }
    
    int[][]dir = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    
    public void dfs(int[][]matrix, boolean[][]visited, int height, int x, int y){
        int n = matrix.length, m = matrix[0].length;
        if(x<0 || x>=n || y<0 || y>=m || visited[x][y] || matrix[x][y] < height)
            return;
        visited[x][y] = true;
        for(int[]d:dir){
            dfs(matrix, visited, matrix[x][y], x+d[0], y+d[1]);
        }
    }
https://discuss.leetcode.com/topic/62306/java-dfs-solution
http://www.itdadao.com/articles/c15a557475p0.html
1、分别处理每个海洋，从海洋边缘（每个海洋两条边）开始，一步步的搜索，即从连接海洋的地方还是搜索，哪些节点的高度高于等于自身（反过来就是可以从那里流到自己），如果是，就标记为true，就这样不断搜索下去。最后所有标记为true的位置就是可以流到对应的海洋。 
2、找这两个矩阵，同为true的输出，就是都能流到两个海洋的位置
    int dx[] = {0,0,-1,1};
    int dy[] = {1,-1,0,0}; 
    //判断一个节点能否流通到海洋
    private void flow(boolean visited[][],int matrix[][],int x,int y,int n,int m ){
        visited[x][y] = true;
        for(int i=0;i<4;i++){
            int nx=x+dx[i];
            int ny=y+dy[i];
            if(nx>=0 && nx<n && ny>=0 && ny<m){
                //一个节点是只能留到不高于自己高度的节点，但是我们这里是反过来找能从nxny留到xy的节点，所以这里注意下
                if(visited[nx][ny]==false && matrix[nx][ny]>=matrix[x][y])
                    flow(visited,matrix,nx,ny,n,m);
            }
        }
    }
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList();
        int n = matrix.length;
        if(n==0) return res;
        int m = matrix[0].length;
        boolean PV[][] = new boolean[n][m];
        boolean AV[][] = new boolean[n][m];
        // 这里从所有临海的地方到这回去判断某个节点是否能流到对应的地方
        for(int i=0;i<n;i++){
            flow(PV,matrix,i,0,n,m);
            flow(AV,matrix,i,m-1,n,m);
        }
        for(int i=0;i<m;i++){
            flow(PV,matrix,0,i,n,m);
            flow(AV,matrix,n-1,i,n,m);
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(PV[i][j] && AV[i][j])
                    res.add(new int[] {i,j});
            }
        }
        return res;

    }
https://discuss.leetcode.com/topic/62415/reverse-flow-technique-clean-java-solution-using-dfs-and-state-machine
DFS from first row and first column and mark all the reachable nodes (reverse comparison) as 1.
If cell is marked as 1 or 3 already, break DFS
If cell is marked as 2 already, mark it 3
DFS from last row and last column and mark all the reachable nodes (reverse comparison) as 2.
If cell is marked as 2 or 3 already, breakDFS
If cell is marked as 1 already, mark it 3 
Space complexity: O(n)
Time complexity: O(n^3)
I guess we can argue that it is O(n^2) since we don't visit all the n^2cells for the 4n DFSes
    int m;
    int n;
    
    public List<int[]> pacificAtlantic(int[][] matrix) {
        
        List<int[]> res = new ArrayList<>();
        m = matrix.length;
        
        if (m == 0) return res;
        
        n = matrix[0].length;
        
        if (n == 0) return res;
        
        // 0 not visited
        // 1 pacific
        // 2 atlantic
        // 3 both
        int[][] touchdown = new int[m][n];
        
        for (int i = 0; i < m; ++i) {
            dfs(matrix, touchdown, i, 0, 1, res);
            dfs(matrix, touchdown, i, n - 1, 2, res);
        }
        
        for (int j = 0; j < n; ++j) {
            dfs(matrix, touchdown, 0, j, 1, res);
            dfs(matrix, touchdown, m - 1, j, 2, res);
        }
        
        return res;
    }
    
    private void dfs(int[][] matrix, int[][] touchdown, int i, int j, int toState, List<int[]> res) {
        
        if (i < 0 || j < 0 || i >= m || j >= n) return;
        
        if (!updateState(touchdown, i, j, toState, res)) {
            return;
        }
        
        if (i + 1 < m && matrix[i][j] <= matrix[i + 1][j]) {
            dfs(matrix, touchdown, i + 1, j, toState, res);
        }
        if (j + 1 < n && matrix[i][j] <= matrix[i][j + 1]) {
            dfs(matrix, touchdown, i, j + 1, toState, res);
        }
        if (i - 1 >= 0 && matrix[i][j] <= matrix[i - 1][j]) {
            dfs(matrix, touchdown, i - 1, j, toState, res);
        }
        if (j - 1 >= 0 && matrix[i][j] <= matrix[i][j - 1]) {
            dfs(matrix, touchdown, i, j - 1, toState, res);
        }
    }
    
    private boolean updateState(int[][] touchdown, int i, int j, int toState, List<int[]> res) {
        int currState = touchdown[i][j];
        if (currState == 3 || currState == toState) return false;
        if (currState == 0) {
            touchdown[i][j] = toState;
        } else {
            touchdown[i][j] = 3;
            res.add(new int[]{i, j});
        }
        return true;
    }
 * @author het
 *
 */

//Given the following 5x5 matrix:
//
//	  Pacific ~   ~   ~   ~   ~ 
//	       ~  1   2   2   3  (5) *
//	       ~  3   2   3  (4) (4) *
//	       ~  2   4  (5)  3   1  *
//	       ~ (6) (7)  1   4   5  *
//	       ~ (5)  1   1   2   4  *
//	          *   *   *   *   * Atlantic
//
//	Return:
//
//	[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
public class LeetCode417PacificAtlanticWaterFlow {
//	DFS from first row and first column and mark all the reachable nodes (reverse comparison) as 1.
//	If cell is marked as 1 or 3 already, break DFS
//	If cell is marked as 2 already, mark it 3
//	DFS from last row and last column and mark all the reachable nodes (reverse comparison) as 2.
//	If cell is marked as 2 or 3 already, breakDFS
//	If cell is marked as 1 already, mark it 3 
//	Space complexity: O(n)
//	Time complexity: O(n^3)
//	I guess we can argue that it is O(n^2) since we don't visit all the n^2cells for the 4n DFSes
	    int m;
	    int n;
	    
	    public List<int[]> pacificAtlantic(int[][] matrix) {
	        
	        List<int[]> res = new ArrayList<>();
	        m = matrix.length;
	        
	        if (m == 0) return res;
	        
	        n = matrix[0].length;
	        
	        if (n == 0) return res;
	        
	        // 0 not visited
	        // 1 pacific
	        // 2 atlantic
	        // 3 both
	        int[][] touchdown = new int[m][n];
	        
	        for (int i = 0; i < m; ++i) {
	            dfs(matrix, touchdown, i, 0, 1, res);
	            dfs(matrix, touchdown, i, n - 1, 2, res);
	        }
	        
	        for (int j = 0; j < n; ++j) {
	            dfs(matrix, touchdown, 0, j, 1, res);
	            dfs(matrix, touchdown, m - 1, j, 2, res);
	        }
	        
	        return res;
	    }
	    
	    private void dfs(int[][] matrix, int[][] touchdown, int i, int j, int toState, List<int[]> res) {
	        
	        if (i < 0 || j < 0 || i >= m || j >= n) return;
	        
	        if (!updateState(touchdown, i, j, toState, res)) {
	            return;
	        }
	        
	        if (i + 1 < m && matrix[i][j] <= matrix[i + 1][j]) {
	            dfs(matrix, touchdown, i + 1, j, toState, res);
	        }
	        if (j + 1 < n && matrix[i][j] <= matrix[i][j + 1]) {
	            dfs(matrix, touchdown, i, j + 1, toState, res);
	        }
	        if (i - 1 >= 0 && matrix[i][j] <= matrix[i - 1][j]) {
	            dfs(matrix, touchdown, i - 1, j, toState, res);
	        }
	        if (j - 1 >= 0 && matrix[i][j] <= matrix[i][j - 1]) {
	            dfs(matrix, touchdown, i, j - 1, toState, res);
	        }
	    }
	    
	    private boolean updateState(int[][] touchdown, int i, int j, int toState, List<int[]> res) {
	        int currState = touchdown[i][j];
	        if (currState == 3 || currState == toState) return false;
	        if (currState == 0) {
	            touchdown[i][j] = toState;
	        } else {
	            touchdown[i][j] = 3;
	            res.add(new int[]{i, j});
	        }
	        return true;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
