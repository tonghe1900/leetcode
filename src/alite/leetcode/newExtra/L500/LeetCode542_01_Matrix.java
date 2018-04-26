package alite.leetcode.newExtra.L500;
/**
 * LeetCode 542 - 01 Matrix

https://leetcode.com/problems/01-matrix/
Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
The distance between two adjacent cells is 1.
Example 2: 
Input:
0 0 0
0 1 0
1 1 1
Output:
0 0 0
0 1 0
1 2 1
Note:
The number of elements of the given matrix will not exceed 10,000.
There are at least one 0 in the given matrix.
The cells are adjacent in only four directions: up, down, left and right.
X. BFS
https://discuss.leetcode.com/topic/83453/java-solution-bfs
General idea is BFS. Some small tricks:
At beginning, set cell value to Integer.MAX_VALUE if it is not 0.
If newly calculated distance >= current distance, then we don't need to explore that cell again.
    public List<List<Integer>> updateMatrix(List<List<Integer>> matrix) {
        int m = matrix.size();
        int n = matrix.get(0).size();
        
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix.get(i).get(j) == 0) {
                    queue.offer(new int[] {i, j});
                }
                else {
                    matrix.get(i).set(j, Integer.MAX_VALUE);
                }
            }
        }
        
        
//         if (r < 0 || r >= m || c < 0 || c >= n || 
//                    matrix.get(r).get(c) <= matrix.get(cell[0]).get(cell[1]) + 1) continue;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int r = cell[0] + d[0];
                int c = cell[1] + d[1];
                if (r < 0 || r >= m || c < 0 || c >= n || 
                    matrix.get(r).get(c) <= matrix.get(cell[0]).get(cell[1]) + 1) continue;
                queue.add(new int[] {r, c});
                matrix.get(r).set(c, matrix.get(cell[0]).get(cell[1]) + 1);
            }
        }
        
        return matrix;
    }

X. https://discuss.leetcode.com/topic/83558/java-33ms-solution-with-two-sweeps-in-o-n
In the first sweep, we visit each entry in natural order and answer[i][j] = min(Integer.MAX_VALUE, min(answer[i - 1][j], answer[i][j - 1]) + 1).
in the second sweep, we visit each entry in reverse order and answer[i][j] = min(answer[i][j], min(answer[i + 1][j], answer[i][j + 1]) + 1).
    public List<List<Integer>> updateMatrix(List<List<Integer>> matrix) {
        if (matrix.size() == 0 || matrix.get(0).size() == 0)
            return matrix;
        int M = matrix.size(), N = matrix.get(0).size();
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
                if (matrix.get(i).get(j) == 0)
                    continue;
                int val = Integer.MAX_VALUE - 1;
                if (i > 0)
                    val = Math.min(val, matrix.get(i - 1).get(j) + 1);
                if (j > 0)
                    val = Math.min(val, matrix.get(i).get(j - 1) + 1);
                matrix.get(i).set(j, val);
            }

        for (int i = M - 1; i >= 0; i--)
            for (int j = N - 1; j >= 0; j--) {
                if (matrix.get(i).get(j) == 0)
                    continue;
                int val = matrix.get(i).get(j);
                if (i < M - 1)
                    val = Math.min(val, matrix.get(i + 1).get(j) + 1);
                if (j < N - 1)
                    val = Math.min(val, matrix.get(i).get(j + 1) + 1);
                matrix.get(i).set(j, val);
            }
        return matrix;
    }
Just another variation, first doing rows and then doing columns. This way I don't need the "if valid neighbor" checks, I can just start the loops from 1 instead of 0.
    public List<List<Integer>> updateMatrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
        m = matrix.size();
        n = matrix.get(0).size();
        for (List<Integer> row : matrix)
            for (int j = 0; j < n; j++)
                row.set(j, row.get(j) * 10000);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                relax(i, j, i, j-1);
                relax(i, n-1-j, i, n-j);
                relax(i, j, i-1, j);
                relax(m-1-i, j, m-i, j);
            }
        }
        return matrix;
    }
    void relax(int i, int j, int i0, int j0) {
        if (i0 >= 0 && i0 < m && j0 >= 0 && j0 < n)
            matrix.get(i).set(j, Math.min(matrix.get(i).get(j), matrix.get(i0).get(j0) + 1));
    }
    List<List<Integer>> matrix;
    int m, n;

https://discuss.leetcode.com/topic/83574/short-solution-each-path-needs-at-most-one-turn
def updateMatrix(self, matrix):
    answer = [[10000 * x for x in row] for row in matrix]
    for _ in range(4):
        for row in answer:
            for j in range(1, len(row)):
                row[j] = min(row[j], row[j-1] + 1)
        answer = map(list, zip(*answer[::-1]))
    return answer
Based on @qswawrq's solution which only considers down/right paths (meaning a combination of only down and right moves, from some 0 to some 1) and up/left paths. When I realized why that works, I realized that we don't even need paths like down,right,down,right. We can instead go just down,down,right,right or right,right,down,down. Just one turn (change of direction). It's the same length, and all of the intermediate cells must be 1 because otherwise down,right,down,right wouldn't have been an optimal path in the first place.
So in my solution I simply optimize in each direction, one after the other. For this I "optimize rightwards" and "rotate the matrix by 90 degrees" four times. Then I have covered every pair of directions, which is enough to cover every straight path and every single-turn path.
 * @author het
 *
 */
public class LeetCode542_01_Matrix {
//	X. BFS
//	https://discuss.leetcode.com/topic/83453/java-solution-bfs
//	General idea is BFS. Some small tricks:
//	At beginning, set cell value to Integer.MAX_VALUE if it is not 0.
//	If newly calculated distance >= current distance, then we don't need to explore that cell again.
	    public List<List<Integer>> updateMatrix(List<List<Integer>> matrix) {
	        int m = matrix.size();
	        int n = matrix.get(0).size();
	        
	        Queue<int[]> queue = new LinkedList<>();
	        for (int i = 0; i < m; i++) {
	            for (int j = 0; j < n; j++) {
	                if (matrix.get(i).get(j) == 0) {
	                    queue.offer(new int[] {i, j});
	                }
	                else {
	                    matrix.get(i).set(j, Integer.MAX_VALUE);
	                }
	            }
	        }
	        
	        
//	         if (r < 0 || r >= m || c < 0 || c >= n || 
//	                    matrix.get(r).get(c) <= matrix.get(cell[0]).get(cell[1]) + 1) continue;
	        
	        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	        
	        while (!queue.isEmpty()) {
	            int[] cell = queue.poll();
	            for (int[] d : dirs) {
	                int r = cell[0] + d[0];
	                int c = cell[1] + d[1];
	                if (r < 0 || r >= m || c < 0 || c >= n || 
	                    matrix.get(r).get(c) <= matrix.get(cell[0]).get(cell[1]) + 1) continue;
	                queue.add(new int[] {r, c});
	                matrix.get(r).set(c, matrix.get(cell[0]).get(cell[1]) + 1);
	            }
	        }
	        
	        return matrix;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
