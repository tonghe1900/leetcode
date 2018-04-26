package alite.leetcode.xx4.select;
/**
 * https://leetcode.com/problems/battleships-in-a-board
Given an 2D board, count how many different battleships are in it. The battleships are represented with 'X's, 
empty slots are represented with '.'s. You may assume the following rules:
You receive a valid board, made of only battleships or empty slots.
Battleships can only be placed horizontally or vertically. In other words,
 they can only be made of the shape 1xN (1 row, N columns) or Nx1 (N rows, 1 column), 
 where N can be of any size.
At least one horizontal or vertical cell separates between two battleships - there are no adjacent battleships.
Example:
X..X
...X
...X
In the above board there are 2 battleships.
Invalid Example:
...X
XXXX
...X
This is an invalid board that you will not receive - as battleships will always have a cell separating between them.
Follow up:
Could you do it in one-pass, using only O(1) extra memory and without modifying the value of the board?
X.
https://discuss.leetcode.com/topic/62970/simple-java-solution
Going over all cells, marking only those that are the "first" cell of the battleship. First cell will be defined as the most top-left cell. We can check for first cells by only counting cells that do not have an 'X' to the left and do not have an 'X' above them.

    public int countBattleships(char[][] board) {
        int m = board.length;
        if (m==0) return 0;
        int n = board[0].length;
        
        int count=0;
        
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == '.') continue;
                if (i > 0 && board[i-1][j] == 'X') continue;
                if (j > 0 && board[i][j-1] == 'X') continue;
                count++;
            }
        }
        
        return count;
    }
https://discuss.leetcode.com/topic/64027/share-my-7-line-code-1-line-core-code-3ms-super-easy
https://discuss.leetcode.com/topic/65418/o-n-2-time-and-o-1-space-without-modifying-the-board
    public int countBattleships(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'X' && (i == 0 || board[i-1][j] == '.') && (j == 0 || board[i][j-1] == '.')) {
                    count++;
                }
            }
        }
        return count;
    }
https://discuss.leetcode.com/topic/65025/4ms-java-optimized-code
A head of a battleship means the top most or left most cell with value 'X'.
Thus, we only need to count those heads.
There are three rules to tell if a cell is a 'head':
The cell is a 'X' (board[i][j] == 'X')
No left side neighbor, or the left neighbor is a '.' (i == 0 || board[i - 1][j] == '.')
No right side neighbor, or the right neighbor is a '.' (j == 0 || board[i][j - 1] == '.')
Code:
public int countBattleships(char[][] board) {
 if (board == null || board.length == 0 || board[0].length == 0) return 0;
 int R = board.length, C = board[0].length, cnt = 0;
 for (int i = 0; i < R; i++) {
  for (int j = 0; j < C; j++) {
   if (board[i][j] == 'X' && (i == 0 || board[i - 1][j] == '.') && (j == 0 || board[i][j - 1] == '.'))
    cnt++;
  }
 }
 
 return cnt;
}
...Note...
For a statement like if (A && B && C), when A is false, the program will not compute B and C.
So, for the best performance, we write the program in this way, instead of using a lot of if statements.

http://bookshadow.com/weblog/2016/10/13/leetcode-battleships-in-a-board/
由于board中的战舰之间确保有'.'隔开，因此遍历board，若某单元格为'X'，只需判断其左边和上边的相邻单元格是否也是'X'。
如果左邻居或者上邻居单元格是'X'，则说明当前单元格是左边或者上边战舰的一部分；
否则，令计数器+1

X.
https://discuss.leetcode.com/topic/63940/java-dfs-solution-6ms
Basic idea: If any 'X' has more than one neighbors is 'X' then these connected 'X' cannot form a valid Battleship.

    public int countBattleships(char[][] board) {
        int M = board.length;
        int N = board[0].length;
        boolean[][] marked = new boolean[M][N];
        int cnt = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 'X' && !marked[i][j] && dfs(board, i, j, marked)) cnt++; 
            }
        }
        return cnt;
    }
    
    public boolean dfs(char[][] board, int r, int c, boolean[][] marked) {
        marked[r][c] = true;
        int[] direct = {1, 0, -1, 0, 1};
        int cnt = 0;
        boolean res = true;
        for (int i = 0; i < 4; i++) {
            int newR = r + direct[i];
            int newC = c + direct[i + 1];
            if (newR >= 0 && newR < board.length && newC >= 0 && newC < board[0].length && !marked[newR][newC] && board[newR][newC] == 'X') {
                cnt++;
                if (!dfs(board, newR, newC, marked)) res = false;
            }
        }
        return cnt > 1 || res;
    }

解法II FloodFill
遍历board，用DFS（深度优先搜索）对每一个'X'位置进行探索与标记，同时进行计数。
    def countBattleships(self, board):
        """
        :type board: List[List[str]]
        :rtype: int
        """
        vs = set()
        h = len(board)
        w = len(board[0]) if h else 0

        def dfs(x, y):
            for dx, dy in zip((1, 0, -1, 0), (0, 1, 0, -1)):
                nx, ny = x + dx, y + dy
                if 0 <= nx < h and 0 <= ny < w:
                    if (nx, ny) not in vs and board[nx][ny] == 'X':
                        vs.add((nx, ny))
                        dfs(nx, ny)

        ans = 0
        for x in range(h):
            for y in range(w):
                if (x, y) not in vs and board[x][y] == 'X':
                    ans += 1
                    vs.add((x, y))
                    dfs(x, y)
        return ans
 * @author het
 *
 */
public class LeetCode419BattleshipsinaBoard {
//	A head of a battleship means the top most or left most cell with value 'X'.
//	Thus, we only need to count those heads.
//	There are three rules to tell if a cell is a 'head':
//	The cell is a 'X' (board[i][j] == 'X')
//	No left side neighbor, or the left neighbor is a '.' (i == 0 || board[i - 1][j] == '.')
//	No right side neighbor, or the right neighbor is a '.' (j == 0 || board[i][j - 1] == '.')
//	Code:
	public int countBattleships(char[][] board) {
	 if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return 0;
	 int R = board.length, C = board[0].length, cnt = 0;
	 for (int i = 0; i < R; i++) {
	  for (int j = 0; j < C; j++) {
	   if (board[i][j] == 'X' && (i == 0 || board[i - 1][j] == '.') && (j == 0 || board[i][j - 1] == '.'))
	    cnt++;
	  }
	 }
	 
	 return cnt;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
