package leetcode700x;
/**
 *  [Leetcode] 723. Candy Crush 解题报告
标签： Leetcode 解题报告 DFS
2018年02月17日 08:39:0896人阅读 评论(0) 收藏  举报
 分类： IT公司面试习题（727）  
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/magicbean2/article/details/79331607

题目：

This question is about implementing a basic elimination algorithm for Candy Crush.

Given a 2D integer array board representing the grid of candy, different positive integers board[i][j] represent different types of candies. A value of board[i][j] = 0 represents that the cell at position (i, j) is empty. The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:

If three or more candies of the same type are adjacent vertically or horizontally, "crush" them all at the same time - these positions become empty.
After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. (No new candies will drop outside the top boundary.)
After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
If there does not exist more candies that can be crushed (ie. the board is stable), then return the current board.
You need to perform the above rules until the board becomes stable, then return the current board.

Example 1:

Input:
board = 
[[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
Output:
[[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
Explanation: 
Note:

The length of board will be in the range [3, 50].
The length of board[i] will be in the range [3, 50].
Each board[i][j] will initially start as an integer in the range [1, 2000].
思路：

这道题目本身并不难，算法框架分为两步：1）标记出所有需要被crash掉的元素；2）将这些元素置为0，并且crash。但是我感觉难的地方是如何确定哪些元素需要被crash。例如在题目中给出的例子中，5个“2”需要被crash，但是只有4个“1”需要被crash，而board[8][0]位置上的1不可以被crash（这也是我开始写的程序怎么都跑不对的原因）。为了解决这一问题，我的思路是：如果一个元素可以被crash，则我们首先将其置为负数。这样在判断某个元素是否可以被crash的时候，我们就判断它的纵向或者横向是否存在三个连续的位置，其值不为0，并且绝对值相同。如果可以被crash，则将其置为原数对应的负数。这样在后面crash的时候，我们只需要保留正数，而将所有的非正数都置为0。

代码：

[cpp] view plain copy
class Solution {  
public:  
    vector<vector<int>> candyCrush(vector<vector<int>>& board) {  
        int row_num = board.size(), col_num = board[0].size();  
        while (true) {  
            // step 1: mark the elements that will be crashed  
            bool changed = false;  
            for (int r = 0; r < row_num; ++r) {  
                for (int c = 0; c < col_num; ++c) {  
                    if (mark(board, r, c)) {  
                        changed = true;  
                    }  
                }  
            }  
            // step 2: crash elements  
            if (changed) {  
                for (int c = 0; c < col_num; ++c) {  
                    vector<int> positives;  
                    for (int r = 0; r < row_num; ++r) {  
                        if (board[r][c] > 0) {  
                            positives.push_back(board[r][c]);  
                        }  
                    }  
                    int size = positives.size();  
                    for (int r = 0; r < row_num - size; ++r) {  
                        board[r][c] = 0;  
                    }  
                    for (int r = row_num - size; r < row_num; ++r) {  
                        board[r][c] = positives[r - row_num + size];  
                    }  
                }  
            }  
            else {  
                break;  
            }  
        }  
        return board;  
    }  
private:  
    bool mark(vector<vector<int>> &board, int row, int col) {  
        int row_num = board.size(), col_num = board[0].size();  
        if (row < 0 || row >= row_num || col < 0 || col >= col_num || board[row][col] <= 0) {  
            return false;  
        }  
        int num = board[row][col];  
        int left = col, right = col, up = row, down = row;  
        while (left - 1 >= 0 && abs(board[row][left - 1]) == num) {  
            --left;  
        }  
        while (right + 1 < col_num && abs(board[row][right + 1]) == num) {  
            ++right;  
        }  
        if (right - left + 1 >= 3) {  
            board[row][col] = -board[row][col];  
            return true;  
        }  
        while (up - 1 >= 0 && abs(board[up - 1][col]) == num) {  
            --up;  
        }  
        while (down + 1 < row_num && abs(board[down + 1][col]) == num) {  
            ++down;  
        }  
        if (down - up + 1 >= 3) {  
            board[row][col] = -board[row][col];  
            return true;  
        }  
        return false;  
    }  
};  
 * @author tonghe
 *
 */
public class Leetcode723 {

}
