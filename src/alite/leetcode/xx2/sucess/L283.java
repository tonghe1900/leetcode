package alite.leetcode.xx2.sucess;
/**
 * LEETCODE 283. GAME OF LIFE
LC address: Game of Life

According to the Wikipedia’s article: “The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.”

Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

Any live cell with fewer than two live neighbors dies, as if caused by under-population.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by over-population..
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
Write a function to compute the next state (after one update) of the board given its current state.

Follow up:

Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?
Analysis:

这道题其实没有什么特别的难点，按照要求in-place的做法需要对信息进行编码，因为在更新当前格子的时候不可以把当前格子的信息丢失。如果题目是把细胞的life用boolean来表示的话，是不可能做到的，不过这道题是用int，所以我们可以利用这个储存信息。我用了two-pass的做法，第一遍是统计当前格子经过当前turn之后会是live(1)还是die(0)，并且把结果放在十位，比如10表示当前是0，下一轮是1；01表示当前是1，下一轮是0等等。第二遍就根据十位是0还是1更新格子的数字就可以了。当然如果用别的编码方式也可以做到one-pass，也就是把每个格子被当成neighbor考虑的次数也编入其中，那么当次数达到8的时候（也可能是别的值，取决于所处位置），也就不会有新的细胞需要考虑这个格子了，这个格子也就可以直接变成0或者1了。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
public class Solution {
    public void gameOfLife(int[][] board) {
        int[][] directions = {{1, 1}, {1, 0}, {1, -1}, {0, 1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                calculate(board, i, j, directions, m, n);
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                update(board, i, j);
            }
        }
    }
     
    private void calculate(int[][] board, int i, int j,
        int[][] directions, int m, int n) {
        int count = 0;
        for (int[] dir : directions) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n) {
                if (board[x][y] % 10 == 1) {
                    count += 1;
                }
            }
        }
        if ((board[i][j] == 1 && (count == 2 || count == 3))
            || (board[i][j] == 0 && count == 3)) {
            board[i][j] += 10;
        }
    }
     
    private void update(int[][] board, int i, int j) {
        board[i][j] /= 10;
    }
}
 * @author het
 *
 */
public class L283 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
