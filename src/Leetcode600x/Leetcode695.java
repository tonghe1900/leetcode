package Leetcode600x;
/**
 * 695. Max Area of Island
DescriptionHintsSubmissionsDiscussSolution
Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

Example 1:
[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
Example 2:
[[0,0,0,0,0,0,0,0]]
Given the above grid, return 0.
Note: The length of each dimension in the given grid does not exceed 50.

Seen this question in a real interview before?


Approach #1: Depth-First Search (Recursive) [Accepted]
Intuition and Algorithm

We want to know the area of each connected shape in the grid, then take the maximum of these.

If we are on a land square and explore every square connected to it 4-directionally (and recursively squares connected to those squares, and so on), then the total number of squares explored will be the area of that connected shape.

To ensure we don't count squares in a shape more than once, let's use seen to keep track of squares we haven't visited before. It will also prevent us from counting the same shape more than once.


Complexity Analysis

Time Complexity: O(R*C)O(R∗C), where RR is the number of rows in the given grid, and CC is the number of columns. We visit every square once.

Space complexity: O(R*C)O(R∗C), the space used by seen to keep track of visited squares, and the space used by the call stack during our recursion.

Approach #2: Depth-First Search (Iterative) [Accepted]
Intuition and Algorithm

We can try the same approach using a stack based, (or "iterative") depth-first search.

Here, seen will represent squares that have either been visited or are added to our list of squares to visit (stack). For every starting land square that hasn't been visited, we will explore 4-directionally around it, adding land squares that haven't been added to seen to our stack.

On the side, we'll keep a count shape of the total number of squares seen during the exploration of this shape. We'll want the running max of these counts.


Complexity Analysis

Time Complexity: O(R*C)O(R∗C), where RR is the number of rows in the given grid, and CC is the number of columns. We visit every square once.

Space complexity: O(R*C)O(R∗C), the space used by seen to keep track of visited squares, and the space used by stack.

Analysis written by: @awice
 * @author tonghe
 *
 */
public class Leetcode695 {
	class Solution {
	    int[][] grid;
	    boolean[][] seen;

	    public int area(int r, int c) {
	        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length ||
	                seen[r][c] || grid[r][c] == 0)
	            return 0;
	        seen[r][c] = true;
	        return (1 + area(r+1, c) + area(r-1, c)
	                  + area(r, c-1) + area(r, c+1));
	    }

	    public int maxAreaOfIsland(int[][] grid) {
	        this.grid = grid;
	        seen = new boolean[grid.length][grid[0].length];
	        int ans = 0;
	        for (int r = 0; r < grid.length; r++) {
	            for (int c = 0; c < grid[0].length; c++) {
	                ans = Math.max(ans, area(r, c));
	            }
	        }
	        return ans;
	    }
	}
	
	
	
	
	class Solution {
	    public int maxAreaOfIsland(int[][] grid) {
	        boolean[][] seen = new boolean[grid.length][grid[0].length];
	        int[] dr = new int[]{1, -1, 0, 0};
	        int[] dc = new int[]{0, 0, 1, -1};

	        int ans = 0;
	        for (int r0 = 0; r0 < grid.length; r0++) {
	            for (int c0 = 0; c0 < grid[0].length; c0++) {
	                if (grid[r0][c0] == 1 && !seen[r0][c0]) {
	                    int shape = 0;
	                    Stack<int[]> stack = new Stack();
	                    stack.push(new int[]{r0, c0});
	                    seen[r0][c0] = true;
	                    while (!stack.empty()) {
	                        int[] node = stack.pop();
	                        int r = node[0], c = node[1];
	                        shape++;
	                        for (int k = 0; k < 4; k++) {
	                            int nr = r + dr[k];
	                            int nc = c + dc[k];
	                            if (0 <= nr && nr < grid.length &&
	                                    0 <= nc && nc < grid[0].length &&
	                                    grid[nr][nc] == 1 && !seen[nr][nc]) {
	                                stack.push(new int[]{nr, nc});
	                                seen[nr][nc] = true;
	                            }
	                        }
	                    }
	                    ans = Math.max(ans, shape);
	                }
	            }
	        }
	        return ans;
	    }
	}
}
