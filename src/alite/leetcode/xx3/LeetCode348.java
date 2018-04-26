package alite.leetcode.xx3;
///**
// * LeetCode 348 - Design Tic-Tac-Toe
//
//http://www.cnblogs.com/grandyang/p/5467118.html
//Design a Tic-tac-toe game that is played between two players on a n x n grid.
//You may assume the following rules:
//A move is guaranteed to be valid and is placed on an empty block.
//Once a winning condition is reached, no more moves is allowed.
//A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
//Example:
//Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
//TicTacToe toe = new TicTacToe(3);
//toe.move(0, 0, 1); -> Returns 0 (no one wins)
//|X| | |
//| | | | // Player 1 makes a move at (0, 0).
//| | | |
//toe.move(0, 2, 2); -> Returns 0 (no one wins)
//|X| |O|
//| | | | // Player 2 makes a move at (0, 2).
//| | | |
//toe.move(2, 2, 1); -> Returns 0 (no one wins)
//|X| |O|
//| | | | // Player 1 makes a move at (2, 2).
//| | |X|
//toe.move(1, 1, 2); -> Returns 0 (no one wins)
//|X| |O|
//| |O| | // Player 2 makes a move at (1, 1).
//| | |X|
//toe.move(2, 0, 1); -> Returns 0 (no one wins)
//|X| |O|
//| |O| | // Player 1 makes a move at (2, 0).
//|X| |X|
//toe.move(1, 0, 2); -> Returns 0 (no one wins)
//|X| |O|
//|O|O| | // Player 2 makes a move at (1, 0).
//|X| |X|
//toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
//|X| |O|
//|O|O| | // Player 1 makes a move at (2, 1).
//|X|X|X|
//Follow up:
//Could you do better than O(n2) per move() operation?
//Hint:
//Could you trade extra space such that move() operation can be done in O(1)?
//You need two arrays: int rows[n], int cols[n], plus two variables: diagonal, anti_diagonal.
//
//
//Follow up中让我们用更高效的方法，那么根据提示中的，我们建立一个大小为n的一维数组rows和cols，还有变量对角线diag和逆对角线rev_diag，这种方法的思路是，
//如果玩家1在第一行某一列放了一个子，那么rows[0]自增1，如果玩家2在第一行某一列放了一个子，
//则rows[0]自减1，那么只有当rows[0]等于n或者-n的时候，表示第一行的子都是tne一个玩家放的，
//则游戏结束返回该玩家即可，其他各行各列，对角线和逆对角线都是这种思路，参见代码如下：
//https://leetcode.com/discuss/101219/7-8-lines-o-1-java-python
//
//public class TicTacToe {
//
//    public TicTacToe(int n) {
//        count = new int[6*n][3];
//    }
//
//    public int move(int row, int col, int player) {
//        int n = count.length / 6;
//        for (int x : new int[]{row, n+col, 2*n+row+col, 5*n+row-col})
//            if (++count[x][player] == n)
//                return player;
//        return 0;
//    }
//
//    int[][] count;
//}
//https://leetcode.com/discuss/101717/java-o-1-short-and-clean
//https://leetcode.com/discuss/101144/java-o-1-solution-easy-to-understand
//In the previous solution, we allocate two arrays for player 1 and 2, respectively. Actually we can use only one array for both of the players. Say, if it is player 1 put one chess, add that location by 1. If it is player 2, deduce it by one. Finally, if either player 1 or player 2 win, that location must be equal to n or -n. 
//
//The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column. Thus, we don't need to keep track of an entire n^2 board. We only need to keep a count for each row and column. If at any time a row or column matches the size of the board then that player has won.
//To keep track of which player, I add one for Player1 and -1 for Player2. There are two additional variables to keep track of the count of the diagonals. Each time a player places a piece we just need to check the count of that row, column, diagonal and anti-diagonal.
//private int[] rows;
//private int[] cols;
//private int diagonal;
//private int antiDiagonal;
//
///** Initialize your data structure here. */
//public TicTacToe(int n) {
//    rows = new int[n];
//    cols = new int[n];
//}
//
///** Player {player} makes a move at ({row}, {col}).
//    @param row The row of the board.
//    @param col The column of the board.
//    @param player The player, can be either 1 or 2.
//    @return The current winning condition, can be either:
//            0: No one wins.
//            1: Player 1 wins.
//            2: Player 2 wins. */
//public int move(int row, int col, int player) {
//    int toAdd = player == 1 ? 1 : -1;
//
//    rows[row] += toAdd;
//    cols[col] += toAdd;
//    if (row == col)
//    {
//        diagonal += toAdd;
//    }
//
//    if (col == (cols.length - row - 1))
//    {
//        antiDiagonal += toAdd;
//    }
//
//    int size = rows.length;
//    if (Math.abs(rows[row]) == size ||
//        Math.abs(cols[col]) == size ||
//        Math.abs(diagonal) == size  ||
//        Math.abs(antiDiagonal) == size)
//    {
//        return player;
//    }
//
//    return 0;
//}
//https://leetcode.com/discuss/101143/13-lines-simple-and-clean-o-1-java-solution
//int[] rows, cols;
//int n, diagonal = 0, antiDiagonal = 0;
//public TicTacToe(int n) {
//    this.n = n;
//    rows = new int[n];
//    cols = new int[n];
//}
//public int move(int row, int col, int player) {
//    if(player == 1){
//        if(++rows[row] == n || ++cols[col] == n) return 1;
//        if(row == col && ++diagonal == n) return 1;
//        if(row + col == n - 1 && ++antiDiagonal == n) return 1;
//
//    }else{
//        if(--rows[row] == -n || --cols[col] == -n) return 2;
//        if(row == col && --diagonal == -n) return 2;
//        if(row + col == n - 1 && --antiDiagonal == -n) return 2;
//    }
//    return 0;
//}
//
//http://buttercola.blogspot.com/2016/06/leetcode-348-design-tic-tac-toe.html
//
//public class TicTacToe {
//
//    private int[][] rows;
//
//    private int[][] cols;
//
//    private int[] diag;
//
//    private int[] xdiag;
//
//    private int n;
//
//
//    /** Initialize your data structure here. */
//
//    public TicTacToe(int n) {
//
//        this.n = n;
//
//        rows = new int[2][n];
//
//        cols = new int[2][n];
//
//        diag = new int[2];
//
//        xdiag = new int[2];
//
//    }
//
//     
//
//    /** Player {player} makes a move at ({row}, {col}).
//
//        @param row The row of the board.
//
//        @param col The column of the board.
//
//        @param player The player, can be either 1 or 2.
//
//        @return The current winning condition, can be either:
//
//                0: No one wins.
//
//                1: Player 1 wins.
//
//                2: Player 2 wins. */
//
//    public int move(int row, int col, int player) {
//
//        int p = player == 1 ? 0 : 1;
//
//         
//
//        rows[p][row]++;
//
//        cols[p][col]++;
//
//         
//
//        if (row == col) {
//
//            diag[p]++;
//
//        }
//
//             
//
//        // X-diagonal
//
//        if (row + col == n - 1) {
//
//            xdiag[p]++;
//
//        }
//
//             
//
//        // If any of them equals to n, return 1
//
//        if (rows[p][row] == n || cols[p][col] == n || 
//
//            diag[p] == n || xdiag[p] == n) {
//
//            return p + 1;
//
//        }
//
//         
//
//        return 0;
//
//    }
//
//}
//
//Not good - just different
//http://dartmooryao.blogspot.com/2016/05/leetcode-348-design-tic-tac-toe.html
//    Map<String, Integer> map;
//    int n;
//
//    /** Initialize your data structure here. */
//    public TicTacToe(int n) {
//        this.n = n;
//        this.map = new HashMap<>();
//    }
//   
//    /** Player {player} makes a move at ({row}, {col}).
//        @param row The row of the board.
//        @param col The column of the board.
//        @param player The player, can be either 1 or 2.
//        @return The current winning condition, can be either:
//                0: No one wins.
//                1: Player 1 wins.
//                2: Player 2 wins. */
//    public int move(int row, int col, int player) {
//        String rowKey = "R"+row+"_"+player;
//        int countR = map.getOrDefault(rowKey, 0)+1;
//        map.put(rowKey, countR);
//       
//        String colKey = "C"+col+"_"+player;
//        int countC = map.getOrDefault(colKey, 0)+1;
//        map.put(colKey, countC);
//       
//        int countD1 = 0;
//        if(row == col){
//            String d1Key = "D1_"+player;
//            countD1 = map.getOrDefault(d1Key, 0)+1;
//            map.put(d1Key, countD1);
//        }
//       
//        int countD2 = 0;
//        if(row + col == n-1){
//            String d2Key = "D2_"+player;
//            countD2 = map.getOrDefault(d2Key, 0)+1;
//            map.put(d2Key, countD2);
//        }
//
//        if(countR == n || countC == n || countD1 == n || countD2 == n){
//            return player;
//        }else{
//            return 0;
//        }
//    }
//
//O(n2)的解法，这种方法的思路很straightforward，就是建立一个nxn大小的board，其中0表示该位置没有棋子，1表示玩家1放的子，2表示玩家2。那么棋盘上每增加一个子，我们都每行每列，对角线和逆对角线来扫描一遍棋盘，看看是否有三子相连的情况，有的话则返回对应的玩家，没有则返回0 
//    TicTacToe(int n) {
//        board.resize(n, vector<int>(n, 0));   
//    }
//
//    int move(int row, int col, int player) {
//        board[row][col] = player;
//        int i = 0, j = 0, N = board.size();
//        for (i = 0; i < N; ++i) {
//            if (board[i][0] != 0) {
//                for (j = 1; j < N; ++j) {
//                    if (board[i][j] != board[i][j - 1]) break;
//                }
//                if (j == N) return board[i][0];
//            }
//        }
//        for (j = 0; j < N; ++j) {
//            if (board[0][j] != 0) {
//                for (i = 1; i < N; ++i) {
//                    if (board[i][j] != board[i - 1][j]) break;
//                }
//                if (i == N) return board[0][j];
//            }
//        }
//        if (board[0][0] != 0) {
//            for (i = 1; i < N; ++i) {
//                if (board[i][i] != board[i - 1][i - 1]) break;
//            }
//            if (i == N) return board[0][0];
//        }
//        if (board[N - 1][0] != 0) {
//            for (i = 1; i < N; ++i) {
//                if (board[N - i - 1][i] != board[N - i][i - 1]) break;
//            }
//            if (i == N) return board[N - 1][0];
//        }
//        return 0;
//    }
//    vector<vector<int>> board;
//http://stackoverflow.com/questions/22087006/using-arrays-to-detect-a-win-in-a-gomoku-game
//https://github.com/javierchavez/Gomoku/blob/master/Gomoku.java
// * @author het
// *
// */
public class LeetCode348 {
	//The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column. Thus, we don't need to keep
	//track of an entire n^2 board. We only need to keep a count for each row and column. If at any time a row or column matches
	//the size of the board then that player has won.
	//To keep track of which player, I add one for Player1 and -1 for Player2. There are two additional 
	//variables to keep track of the count of the diagonals. Each time a player places a piece we just need to
	//check the count of that row, column, diagonal and anti-diagonal.
	//private int[] rows;
	//private int[] cols;
	//private int diagonal;
	//private int antiDiagonal;
	//
	///** Initialize your data structure here. */
	//public TicTacToe(int n) {
//	    rows = new int[n];
//	    cols = new int[n];
	//}
	//
	///** Player {player} makes a move at ({row}, {col}).
//	    @param row The row of the board.
//	    @param col The column of the board.
//	    @param player The player, can be either 1 or 2.
//	    @return The current winning condition, can be either:
//	            0: No one wins.
//	            1: Player 1 wins.
//	            2: Player 2 wins. */
	//public int move(int row, int col, int player) {
//	    int toAdd = player == 1 ? 1 : -1;
	//
//	    rows[row] += toAdd;
//	    cols[col] += toAdd;
//	    if (row == col)
//	    {
//	        diagonal += toAdd;
//	    }
	//
//	    if (col == (cols.length - row - 1))
//	    {
//	        antiDiagonal += toAdd;
//	    }
	//
//	    int size = rows.length;
//	    if (Math.abs(rows[row]) == size ||
//	        Math.abs(cols[col]) == size ||
//	        Math.abs(diagonal) == size  ||
//	        Math.abs(antiDiagonal) == size)
//	    {
//	        return player;
//	    }
	//
//	    return 0;
	//}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
