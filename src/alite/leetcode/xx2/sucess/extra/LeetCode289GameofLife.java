package alite.leetcode.xx2.sucess.extra;
///**
// * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
//Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
//Any live cell with fewer than two live neighbors dies, as if caused by under-population.
//Any live cell with two or three live neighbors lives on to the next generation.
//Any live cell with more than three live neighbors dies, as if by over-population..
//Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
//Write a function to compute the next state (after one update) of the board given its current state.
//Follow up: 
//Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
//In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?
//根据维基百科的文章："生命游戏，也被简称为生命，是一款由英国数学家约翰・霍顿康威于1970年设计的细胞自动机。"
//给定一个m * n的细胞隔板，每一个细胞拥有一个初始状态：存活(1)或者死亡(0)。每一个细胞与其周围的8个邻居细胞（水平，竖直，对角线）发生交互，依据如下四条规则（摘自维基百科）：
//任何相邻存活细胞数小于2个的存活细胞都会死亡，模拟人口不足。
//任何相邻存活细胞数为2个或者3个的存活细胞会存活到下一代。
//任何相邻存活细胞数大于3个的存活细胞都会死亡，模拟人口过载。
//任何相邻存活细胞数等于3个的死亡细胞都会成为一个存活细胞，模拟繁殖。
//编写函数，根据隔板的当前状态，计算其下一个状态（一次更新之后）
//进一步思考：
//你可以就地完成题目吗？记住隔板需要同时更新：你不能先更新某些细胞然后再以其变更后的值来更新其他细胞。
//在这个问题中，我们使用2维数组表示隔板。原则上，隔板是无穷的，这可能导致一些边界问题。你怎么处理边界问题？
//解题思路：
//https://leetcode.com/discuss/68352/easiest-java-solution-with-explanation
//To solve it in place, we use 2 bits to store 2 states:
//[2nd bit, 1st bit] = [next state, current state]
//
//- 00  dead (next) <- dead (current)
//- 01  dead (next) <- live (current)  
//- 10  live (next) <- dead (current)  
//- 11  live (next) <- live (current) 
//In the beginning, every cell is either 00 or 01.
//Notice that 1st state is independent of 2nd state.
//Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
//Let's count # of neighbors from 1st state and set 2nd state bit.
//Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
//In the end, delete every cell's 1st state by doing >> 1.
//For each cell's 1st bit, check the 8 pixels around itself, and set the cell's 2nd bit.
//Transition 01 -> 11: when board == 1 and lives >= 2 && lives <= 3.
//Transition 00 -> 10: when board == 0 and lives == 3.
//To get the current state, simply do
//board[i][j] & 1
//To get the next state, simply do
//board[i][j] >> 1
//lives -= board[i][j] & 1; why did lives have to minus itself?
//Your solution traversed from top left to bottom right. But might the latter cell live/die affect the previous cell? Why shouldnt we consider this situation?
//(1) This is for conveniency. Notice that it's counting the 8 pixels around itself, not including itself. (2) Notice that bit0 is independent of bit1. Think about the board is changing from state1 "instantly" into state2. So we only retrieve the first state using board[i][j] & 1 and changing only the second state by changing 01 to 11 or 00 to 10. This is same as board[i][j] |= (1<<1).
//public void gameOfLife(int[][] board) {
//    if (board == null || board.length == 0) return;
//    int m = board.length, n = board[0].length;
//
//    for (int i = 0; i < m; i++) {
//        for (int j = 0; j < n; j++) {
//            int lives = liveNeighbors(board, m, n, i, j);
//
//            // In the beginning, every 2nd bit is 0;
//            // So we only need to care about when will the 2nd bit become 1.
//            if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
//                board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
//            }
//            if (board[i][j] == 0 && lives == 3) {
//                board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
//            }
//        }
//    }
//
//    for (int i = 0; i < m; i++) {
//        for (int j = 0; j < n; j++) {
//            board[i][j] >>= 1;  // Get the 2nd state.
//        }
//    }
//}
//
//public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
//    int lives = 0;
//    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
//        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
//            lives += board[x][y] & 1;
//        }
//    }
//    lives -= board[i][j] & 1;
//    return lives;
//}
//https://discuss.leetcode.com/topic/26110/clean-o-1-space-o-mn-time-java-solution
//https://discuss.leetcode.com/topic/26112/c-o-1-space-o-mn-time
//int[][] dir ={{1,-1},{1,0},{1,1},{0,-1},{0,1},{-1,-1},{-1,0},{-1,1}};
//public void gameOfLife(int[][] board) {
//    for(int i=0;i<board.length;i++){
//        for(int j=0;j<board[0].length;j++){
//            int live=0;
//            for(int[] d:dir){
//                if(d[0]+i<0 || d[0]+i>=board.length || d[1]+j<0 || d[1]+j>=board[0].length) continue;
//                if(board[d[0]+i][d[1]+j]==1 || board[d[0]+i][d[1]+j]==2) live++;
//            }
//            if(board[i][j]==0 && live==3) board[i][j]=3;
//            if(board[i][j]==1 && (live<2 || live>3)) board[i][j]=2;
//        }
//    }
//    for(int i=0;i<board.length;i++){
//        for(int j=0;j<board[0].length;j++){
//            board[i][j] %=2;
//        }
//    }
//
//位运算（bit manipulation）
//由于细胞只有两种状态0和1，因此可以使用二进制来表示细胞的生存状态
//更新细胞状态时，将细胞的下一个状态用高位进行存储
//全部更新完毕后，将细胞的状态右移一位
//    def gameOfLife(self, board):
//        """
//        :type board: List[List[int]]
//        :rtype: void Do not return anything, modify board in-place instead.
//        """
//        dx = (1, 1, 1, 0, 0, -1, -1, -1)
//        dy = (1, 0, -1, 1, -1, 1, 0, -1)
//        for x in range(len(board)):
//            for y in range(len(board[0])):
//                lives = 0
//                for z in range(8):
//                    nx, ny = x + dx[z], y + dy[z]
//                    lives += self.getCellStatus(board, nx, ny)
//                if lives + board[x][y] == 3 or lives == 3:
//                    board[x][y] |= 2
//        for x in range(len(board)):
//            for y in range(len(board[0])):
//                board[x][y] >>= 1
//    def getCellStatus(self, board, x, y):
//        if x < 0 or y < 0 or x >= len(board) or y >= len(board[0]):
//            return 0
//        return board[x][y] & 1
//http://segmentfault.com/a/1190000003819277
//最简单的方法是再建一个矩阵保存，不过当inplace解时，如果我们直接根据每个点周围的存活数量来修改当前值，由于矩阵是顺序遍历的，这样会影响到下一个点的计算。如何在修改值的同时又保证下一个点的计算不会被影响呢？实际上我们只要将值稍作编码就行了，因为题目给出的是一个int矩阵，大有空间可以利用。这里我们假设对于某个点，值的含义为
//0 : 上一轮是0，这一轮过后还是0
//1 : 上一轮是1，这一轮过后还是1
//2 : 上一轮是1，这一轮过后变为0
//3 : 上一轮是0，这一轮过后变为1
//这样，对于一个节点来说，如果它周边的点是1或者2，就说明那个点上一轮是活的。最后，在遍历一遍数组，把我们编码再解回去，即0和2都变回0，1和3都变回1，就行了。
//注意编码方式，1和3都是这一轮过后为1，这样就可以用一个模2操作来直接解码了
//我实现的时候并没有预先建立一个对应周围8个点的数组，因为实际复杂度是一样，多加几个数组反而程序可读性下降
//public class Solution {
//    public void gameOfLife(int[][] board) {
//        int m = board.length, n = board[0].length;
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                int lives = 0;
//                // 判断上边
//                if(i > 0){
//                    lives += board[i - 1][j] == 1 || board[i - 1][j] == 2 ? 1 : 0;
//                }
//                // 判断左边
//                if(j > 0){
//                    lives += board[i][j - 1] == 1 || board[i][j - 1] == 2 ? 1 : 0;
//                }
//                // 判断下边
//                if(i < m - 1){
//                    lives += board[i + 1][j] == 1 || board[i + 1][j] == 2 ? 1 : 0;
//                }
//                // 判断右边
//                if(j < n - 1){
//                    lives += board[i][j + 1] == 1 || board[i][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断左上角
//                if(i > 0 && j > 0){
//                    lives += board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 2 ? 1 : 0;
//                }
//                //判断右下角
//                if(i < m - 1 && j < n - 1){
//                    lives += board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断右上角
//                if(i > 0 && j < n - 1){
//                    lives += board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断左下角
//                if(i < m - 1 && j > 0){
//                    lives += board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 2 ? 1 : 0;
//                }
//                // 根据周边存活数量更新当前点，结果是0和1的情况不用更新
//                if(board[i][j] == 0 && lives == 3){
//                    board[i][j] = 3;
//                } else if(board[i][j] == 1){
//                    if(lives < 2 || lives > 3) board[i][j] = 2;
//                }
//            }
//        }
//        // 解码
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                board[i][j] = board[i][j] % 2;
//            }
//        }
//    }
//}
//另一种编码方式是位操作，将下轮该cell要变的值存入bit2中，然后还原的时候右移就行了。
//
//https://leetcode.com/discuss/64053/my-java-solution-for-game-of-life
//
//
//public void gameOfLife(int[][] board) {
//    int m = board.length;
//    int n = board[0].length;
//
//    for (int i = 0; i<m; i++){
//        for (int j = 0; j<n; j++){
//            int sum = 0;
//            int mn = board[i][j];
//            sum = value(board,i-1,j-1) + value(board,i-1,j) + value(board,i-1,j+1) + value(board,i,j-1) + 
//                value(board,i,j+1) + value(board,i+1,j-1) + value(board,i+1,j) + value (board,i+1,j+1);
//            if (mn == 0 && sum == 3){
//                board[i][j] = -1;
//            }else if(mn == 1 && sum < 2){
//                board[i][j] = -2;
//            }else if (mn == 1 && sum > 3){
//                board[i][j] = -2;
//            }
//        }
//    }
//
//    for (int i = 0; i<m; i++){
//        for (int j = 0; j<n; j++){
//              int mn = board[i][j];
//              if (mn == -1 || mn == -2){
//                  board[i][j] = mn + 2;
//              }
//        }
//    }
//}
//
//public int value (int[][] board, int i, int j ){
//    int m = board.length;
//    int n = board[0].length;
//    if (i<0 || j<0 || i>m-1 ||j>n-1){
//        return 0;
//    }else{
//        int cur = board[i][j];
//        if (cur == -2){
//            return 1;
//        }else if(cur == -1){
//            return 0;
//        }else{
//            return cur;
//        }
//    }
//}
//http://blog.csdn.net/xudli/article/details/48896549
//inplace的话,   只要有办法区分 4种状态,   DEAD->LIVE, DEAD->DEAD, LIVE->LIVE, LIVE->DEAD 即可.   int 完全可以找4个数来表示这4种状态.   
//这里我用了   0,1, 10, 11 表示. 
//    public void gameOfLife(int[][] board) {  
//        //check input  
//        if(board==null || board.length==0 || board[0]==null || board[0].length==0) return;  
//          
//        int m = board.length;  
//        int n = board[0].length;  
//          
//        for(int i=0; i<m; i++) {  
//            for(int j=0; j<n; j++) {  
//                int x = getLiveNum(board, i, j);  
//                if(board[i][j] == 0) {  
//                    if(x==3) board[i][j]+=10;  
//                } else {  
//                    if(x==2 || x==3) board[i][j]+=10;       
//                }  
//            }  
//        }  
//          
//        for(int i=0; i<m; i++) {  
//            for(int j=0; j<n; j++) {  
//                board[i][j] /= 10;   
//            }  
//        }  
//    }  
//      
//    private int getLiveNum(int[][] board, int x, int y) {  
//        int c=0;  
//        for(int i=x-1; i<=x+1; i++) {  
//            for(int j=y-1; j<=y+1; j++) {  
//                if(i<0 || j<0 || i>board.length-1 || j>board[0].length-1 || (i==x && j==y)) continue;  
//                if(board[i][j]%10==1) c++;  
//            }  
//        }  
//        return c;  
//    } 
//http://my.oschina.net/Tsybius2014/blog/514447
//在不使用定义新矩阵的情况下，要想解决本问题就需要先定义一组中间状态，中间状态要求能看出一个单元格变化前后两方面的生死情况。
//
//public class Solution {
//
//
//    //死亡单位
//
//    final int DEAD = 0;
//
//    //存活单位
//
//    final int ALIVE = 1;
//
//
//    //变化情况：死亡→死亡
//
//    final int DEAD_TO_DEAD = 0;
//
//    //变化情况：存活→存活
//
//    final int ALIVE_TO_ALIVE = 1;
//
//    //变化情况：存活→死亡
//
//    final int ALIVE_TO_DEAD = 2;
//
//    //变化情况：死亡→存活
//
//    final int DEAD_TO_ALIVE = 3;
//
//     
//
//    /**
//
//     * 判断某点在本轮变化前是否是死亡状态
//
//     * @param obj
//
//     * @return
//
//     */
//
//    private boolean isAliveOld(int obj) {
//
//        if (obj == ALIVE_TO_ALIVE || obj == ALIVE_TO_DEAD) {
//
//            return true;
//
//        }
//
//        else {
//
//            return false;
//
//        }
//
//    }
//
//
//    /**
//
//     * 判断某点在本轮变化后是否是死亡状态
//
//     * @param obj
//
//     * @return
//
//     */
//
//    private boolean isAliveNew(int obj) {
//
//        if (obj % 2 == 1) {
//
//            return true;
//
//        } else {
//
//            return false;
//
//        }
//
//    }
//
//     
//
//    /**
//
//     * 生命游戏
//
//     * @param board
//
//     */
//
//    public void gameOfLife(int[][] board) {
//
//
//        //输入合法性检查
//
//        if (board == null) {
//
//            return;
//
//        }
//
//        int height = board.length;
//
//        if (height == 0) {
//
//            return;
//
//        }
//
//        int width = board[0].length;
//
//        if (width == 0) {
//
//            return;
//
//        }
//
//
//        //考察所有的点，变化其生命状态
//
//        int counter = 0;
//
//        for (int i = 0; i < height; i++) {
//
//            for (int j = 0; j < width; j++) {
//
//                 
//
//                //统计周边生命生存情况
//
//                counter = 0;
//
//                if (i > 0 && j > 0 && isAliveOld(board[i - 1][j - 1])) {
//
//                    counter++;
//
//                }
//
//                if (i > 0 && isAliveOld(board[i - 1][j])) {
//
//                    counter++;
//
//                }
//
//                if (i > 0 && j < width - 1 && isAliveOld(board[i - 1][j + 1])) {
//
//                    counter++;
//
//                }
//
//                if (j > 0 && isAliveOld(board[i][j - 1])) {
//
//                    counter++;
//
//                }
//
//                if (j < width - 1 && isAliveOld(board[i][j + 1])) {
//
//                    counter++;
//
//                }
//
//                if (i < height - 1 && j > 0 && isAliveOld(board[i + 1][j - 1])) {
//
//                    counter++;
//
//                }
//
//                if (i < height - 1 && isAliveOld(board[i + 1][j])) {
//
//                    counter++;
//
//                }
//
//                if (i < height - 1 && j < width - 1 && isAliveOld(board[i + 1][j + 1])) {
//
//                    counter++;
//
//                }
//
//                 
//
//                //根据指定点周边的生命生存情况决定当前点的变化
//
//                if (isAliveOld(board[i][j])) {
//
//                    if (counter < 2) {
//
//                        //1.存活单位周边的存活单位少于2个，该单位死亡
//
//                        board[i][j] = ALIVE_TO_DEAD;
//
//                    } else if (counter == 2 || counter == 3) {
//
//                        //2.存活单位周边的存活单位有2-3个，该单位继续存活
//
//                        board[i][j] = ALIVE_TO_ALIVE;
//
//                    } else {
//
//                        //3.存活单位周边的存活单位多余3个，该单位死亡
//
//                        board[i][j] = ALIVE_TO_DEAD;
//
//                    }
//
//                } else {
//
//                    if (counter == 3) {
//
//                        //4.死亡单位周边的存活单位恰好为3个，该单位变为存活状态
//
//                        board[i][j] = DEAD_TO_ALIVE;                    
//
//                    } else {
//
//                        board[i][j] = DEAD_TO_DEAD;
//
//                    }
//
//                }
//
//            }
//
//        }
//
//
//        //根据变换后的存活状态，重新赋予每个点的生死情况
//
//        for (int i = 0; i < height; i++) {
//
//            for (int j = 0; j < width; j++) {
//
//                if (isAliveNew(board[i][j])) {
//
//                    board[i][j] = ALIVE;
//
//                } else {
//
//                    board[i][j] = DEAD;
//
//                }
//
//            }
//
//        }
//
//    }
//
//}
//https://leetcode.com/discuss/61912/c-o-1-space-o-mn-time?show=61912
//Since the board has ints but only the 1-bit is used, I use the 2-bit to store the new state. At the end, replace the old state with the new state by shifting all values one bit to the right.
//void gameOfLife(vector<vector<int>>& board) {
//    int m = board.size(), n = m ? board[0].size() : 0;
//    for (int i=0; i<m; ++i) {
//        for (int j=0; j<n; ++j) {
//            int count = 0;
//            for (int I=max(i-1, 0); I<min(i+2, m); ++I)
//                for (int J=max(j-1, 0); J<min(j+2, n); ++J)
//                    count += board[I][J] & 1;
//            if (count == 3 || count - board[i][j] == 3)
//                board[i][j] |= 2;
//        }
//    }
//    for (int i=0; i<m; ++i)
//        for (int j=0; j<n; ++j)
//            board[i][j] >>= 1;
//}
//http://www.cnblogs.com/jcliBlogger/p/4854078.html
//3     void gameOfLife(vector<vector<int>>& board) {
// 4         int m = board.size(), n = m ? board[0].size() : 0;
// 5         int di[8] = {-1, -1, -1,  0, 0,  1, 1, 1};
// 6         int dj[8] = {-1,  0,  1, -1, 1, -1, 0, 1};
// 7         for (int i = 0; i < m; i++) {
// 8             for (int j = 0; j < n; j++) {
// 9                 int live = 0;
//10                 for (int k = 0; k < 8; k++) {
//11                     int ii = i + di[k], jj = j + dj[k];
//12                     if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
//13                     if (board[ii][jj] == 1 || board[ii][jj] == 2) live++;
//14                 }
//15                 if (board[i][j] == 1 && (live < 2 || live > 3)) board[i][j] = 2;
//16                 else if (!board[i][j] && live == 3) board[i][j] = 3;
//17             }
//18         }
//19         for (int i = 0; i < m; i++)
//20             for (int j = 0; j < n; j++)
//21                 board[i][j] %= 2;
//22     }
//http://segmentfault.com/a/1190000003819277
//如果循环矩阵如何解决？循环的意思是假设一个3x3的矩阵,则a[0][0]的左边是a[0][1]，其左上是a[2][2]
//这样我们的坐标要多加一个数组长度，使用坐标时还要取模
//   public void solveInplaceCircular(int rounds, int[][] board){
//       for(int round = 0; round < rounds; round++){
//           int m = board.length, n = board[0].length;
//           for(int i = 0; i < m; i++){
//               for(int j = 0; j < n; j++){
//                   int lives = 0;
//                   // 多加一个数组长度
//                   for(int y = i + m - 1; y <= i + m + 1; y++){
//                       for(int x = j + n - 1; x <= j + n + 1; x++){
//                           // 使用的时候要取模
//                           lives += board[y % m][x % n] & 1;
//                       }
//                   }
//                   if(lives == 3 || lives - board[i][j] == 3){
//                       board[i][j] |= 2;
//                   }
//               }
//           }
//           for(int i = 0; i < m; i++){
//               for(int j = 0; j < n; j++){
//                   board[i][j] >>>= 1;
//               }
//           }
//       }
//   }
//如果矩阵很大如何优化？
//我们可以只记录存活节点的信息，存入一个live的list中，这里active代表着存活节点，或者存活节点的邻居。每次只计算这个list中节点和其邻居的情况。进一步优化的话，我们可以用一个active的list，只记录上次更新的节点，或者该节点的邻居。等计算完这个列表后，将产生更新的节点和它的邻居们存入一个新列表中，再用这个新列表里节点的值来更新矩阵。下一轮时，就计算这个新列表，再产生一个新列表。
//因为是多核，我们可以用线程来实现并行计算。如图，将矩阵分块后，每个线程只负责其所在的分块的计算，不过主线程每一轮都要更新一下这些分块的边缘，并提供给相邻分块。所以这里的开销就是主线程和子线程通信这个边缘信息的开销。如果线程变多分块变多，边缘信息也会变多，开销会增大。所以选取线程的数量是这个开销和并行计算能力的折衷。
//如果是多台机器如何优化？
//同样的，我们可以用一个主机器负责处理边缘信息，而多个子机器处理每个分块的信息，因为是分布式的，我们的矩阵可以分块的存储在不同机器的内存中，这样矩阵就可以很大。而主机在每一轮开始时，将边缘信息通过网络发送给哥哥分块机器，然后分块机器计算好自己的分块后，把新自己内边缘信息反馈给主机器。下一轮，等主机器收集齐所有边缘后，就可以继续重复。
//不过多台机器时还有一个更好的方法，就是使用Map Reduce。Map Reduce的简单版本是这样的，首先我们的Mapper读入一个file，这个file中每一行代表一个存活的节点的坐标，然后Mapper做出9个Key-Value对，对这个存活节点的邻居cell，分发出一个1。而对于节点自身，也要分发出一个1。这里Reducer是对应每个cell的，每个reducer累加自己cell得到了多少个1，就知道自己的cell周围有多少存活cell，就能知道该cell下一轮是否可以存活，如果可以存活则分发回mapper的文件中，等待下次读取，如果不能则舍弃。
//如果要进一步优化Map Reduce，那我们主要优化的地方则是mapper和reducer通信的开销，因为对于每个存活节点，mapper都要向9个reducer发一次信息。我们可以在mapper中用一个哈希表，当mapper读取文件的某一行时，先不向9个reducer发送信息，而是以这9个cell作为key，将1累加入哈希表中。这样等mapper读完文件后，再把哈希表中的cell和该cell对应的累加1次数，分发给相应cell的reducer，这样就可以减少一些通信开销。相当于是现在mapper内做了一次累加。这种优化在只有一个mapper是无效的，因为这就等于直接在mapper中统计完了，但是如果多个mapper同时执行时，相当于在每个mapper里先统计一会，再交给reducer一起统计每个mapper的统计结果。
// 1: class Mapper:
//   2: method Map ():
//   3: hash = ∅
//   4: for line ∈ stdin:
//   5:     cell, state = Parse (line)
//   6:     hash[cell] += state
//   7:     for neighbor in Neighborhood (cell):
//   8:         hash[neighbor] += 2*state
//   9: for cell in hash:
//   10:     strip-number = cell.row / strip-length
//   11: Emit (cell, strip-number, hash[cell])
//   
//   1: class Reducer:
//   2: method Reduce ():
//   3: H = 0; last-cell = None
//   4: for line ∈ stdin:
//   5:     strip-number, current-cell, in-value = Parse (line);
//   6:     if current-cell ≠ last-cell :
//   7:         if last-cell ≠ None:
//   8:             Emit (last-cell, state=F(E(H))
//   9:         H = 0; last-cell = current-cell
//   10:     H += in_value
//   11: Emit (last-cell, state=F(E(xi))
//如果整个图都会变，有没有更快的方法？
//参见Hashlife，大意是用哈希记录一下会重复循环的pattern
//
//https://leetcode.com/discuss/62185/infinite-board-solution
//For the second follow-up question, here's a solution for an infinite board. Instead of a two-dimensional array of ones and zeros, I represent the board as a set of live cell coordinates.
//def gameOfLifeInfinite(self, live):
//    ctr = collections.Counter((I, J)
//                              for i, j in live
//                              for I in range(i-1, i+2)
//                              for J in range(j-1, j+2)
//                              if I != i or J != j)
//    return {ij
//            for ij in ctr
//            if ctr[ij] == 3 or ctr[ij] == 2 and ij in live}
//And here's a wrapper that uses the above infinite board solution to solve the problem we have here at the OJ (submitted together, this gets accepted):
//def gameOfLife(self, board):
//    live = {(i, j) for i, row in enumerate(board) for j, live in enumerate(row) if live}
//    live = self.gameOfLifeInfinite(live)
//    for i, row in enumerate(board):
//        for j in range(len(row)):
//            row[j] = int((i, j) in live)
//What I do is I have the coordinates of all living cells in a set. Then I count the living neighbors of all cells by going through the living cells and increasing the counter of their neighbors (thus cells without living neighbor will not be in the counter). Afterwards I just collect the new set of living cells by picking those with the right amount of neighbors. 
//
//Here is (quite verbose) translation to Java:
//    private Set<Coord> gameOfLife(Set<Coord> live) {
//        Map<Coord,Integer> neighbours = new HashMap<>();
//        for (Coord cell : live) {
//            for (int i = cell.i-1; i<cell.i+2; i++) {
//                for (int j = cell.j-1; j<cell.j+2; j++) {
//                    if (i==cell.i && j==cell.j) continue;
//                    Coord c = new Coord(i,j);
//                    if (neighbours.containsKey(c)) {
//                        neighbours.put(c, neighbours.get(c) + 1);
//                    } else {
//                        neighbours.put(c, 1);
//                    }
//                }
//            }
//        }
//        Set<Coord> newLive = new HashSet<>();
//        for (Map.Entry<Coord,Integer> cell : neighbours.entrySet())  {
//            if (cell.getValue() == 3 || cell.getValue() == 2 && live.contains(cell.getKey())) {
//                newLive.add(cell.getKey());
//            }
//        }
//        return newLive;
//    }
//where Coord is:
//    private static class Coord {
//        int i;
//        int j;
//        private Coord(int i, int j) {
//            this.i = i;
//            this.j = j;
//        }
//        public boolean equals(Object o) {
//            return o instanceof Coord && ((Coord)o).i == i && ((Coord)o).j == j;
//        }
//        public int hashCode() {
//            int hashCode = 1;
//            hashCode = 31 * hashCode + i;
//            hashCode = 31 * hashCode + j;
//            return hashCode;
//        }
//    }
//and the wrapper:
//    public void gameOfLife(int[][] board) {
//        Set<Coord> live = new HashSet<>();
//        int m = board.length;
//        int n = board[0].length;
//        for (int i = 0; i<m; i++) {
//            for (int j = 0; j<n; j++) {
//                if (board[i][j] == 1) {
//                    live.add(new Coord(i,j));
//                }
//            }
//        };
//        live = gameOfLife(live);
//        for (int i = 0; i<m; i++) {
//            for (int j = 0; j<n; j++) {
//                board[i][j] = live.contains(new Coord(i,j))?1:0;
//            }
//        };
//
//    }
//
//https://leetcode.com/discuss/62150/what-if-the-input-matrix-is-a-boolean
//It seems that encoding inside original int[][] just utilized spare spaces from matrix. What if the input matrix is a boolean matrix? Is there still a way to solve it without extra space?
//
//It's possible to use O(min(M, N)) extra space since we can do it by row or column, whichever is smaller. Once we finished the current row / column and next row / column, we can update this row / column. It seems to me that O(1) extra space is impossible
//I took a shot at what dasheng2 said. I didn't minimize the space as it would be just a copy-paste or unnecessary complication for sharing.
//public void gameOfLife(int[][] board) {
//    if (board.length == 0 || board[0].length == 0) return;
//    int rows = board.length, cols = board[0].length;
//    // previous row always exists (it's all 0s above board's first row)
//    int[] prevRow = new int[cols];
//    for (int r = 0; r < rows; ++r) {
//        int prev = 0; // fakes a dead cell in front of current row
//        for (int c = 0; c < cols; ++c) {
//            int neigh = neigh(board, r, c, prevRow, prev);
//            if (0 <= c-1) prevRow[c-1] = prev; // only overwrite once not needed
//            prev = board[r][c];
//            board[r][c] =        neigh == 3 /* lives on(3)/reproduction */
//                || board[r][c] + neigh == 3 /* lives on(2) */ ? 1 : 0;
//        }
//        prevRow[cols-1] = prev; // fill in the gap for last item's late write
//    }
//}
///**
// *     prevRow[c-1]*     prevRow[c]      prevRow[c+1]*
// *             prev  board[r  ] [c]  board[r  ] [c+1]*
// * board[r+1]*[c-1]* board[r+1]*[c]* board[r+1]*[c+1]*
// *
// * starred: needs bound check
// */
//int neigh(int[][] board, int r, int c, int[] prevRow, int prev) {
//    int rows = board.length, cols = board[0].length;
//    int neigh = prev; // remember, board[r][c] doesn't count;
//    if (true)          neigh += validSum(prevRow, c); // prevRow always exists
//    if (c+1 <= cols-1) neigh += board[r][c+1]; // only one needs validation from current row
//    if (r+1 <= rows-1) neigh += validSum(board[r+1], c); // only if there's a next row
//    return neigh;
//}
//int validSum(int[] row, int c) {
//    int result = 0;
//    if (0 <= c-1)             result += row[c-1];
//    if (true)                 result += row[c];
//    if (c+1 <= row.length-1)  result += row[c+1];
//    return result;
//}
//https://discuss.leetcode.com/topic/26222/what-if-the-input-matrix-is-a-boolean
//It seems that encoding inside original int[][] just utilized spare spaces from matrix. What if the input matrix is a boolean matrix? Is there still a way to solve it without extra space? 
//
//It's possible to use O(min(M, N)) extra space since we can do it by row or column, whichever is smaller. Once we finished the current row / column and next row / column, we can update this row / column.
//
// public void gameOfLife(int[][] board) {
//    if (board.length == 0 || board[0].length == 0) return;
//    int rows = board.length, cols = board[0].length;
//    // previous row always exists (it's all 0s above board's first row)
//    int[] prevRow = new int[cols];
//    for (int r = 0; r < rows; ++r) {
//        int prev = 0; // fakes a dead cell in front of current row
//        for (int c = 0; c < cols; ++c) {
//            int neigh = neigh(board, r, c, prevRow, prev);
//            if (0 <= c-1) prevRow[c-1] = prev; // only overwrite once not needed
//            prev = board[r][c];
//            board[r][c] =        neigh == 3 /* lives on(3)/reproduction */
//                || board[r][c] + neigh == 3 /* lives on(2) */ ? 1 : 0;
//        }
//        prevRow[cols-1] = prev; // fill in the gap for last item's late write
//    }
//}
///**
// *     prevRow[c-1]*     prevRow[c]      prevRow[c+1]*
// *             prev  board[r  ] [c]  board[r  ] [c+1]*
// * board[r+1]*[c-1]* board[r+1]*[c]* board[r+1]*[c+1]*
// *
// * starred: needs bound check
// */
//int neigh(int[][] board, int r, int c, int[] prevRow, int prev) {
//    int rows = board.length, cols = board[0].length;
//    int neigh = prev; // remember, board[r][c] doesn't count;
//    if (true)          neigh += validSum(prevRow, c); // prevRow always exists
//    if (c+1 <= cols-1) neigh += board[r][c+1]; // only one needs validation from current row
//    if (r+1 <= rows-1) neigh += validSum(board[r+1], c); // only if there's a next row
//    return neigh;
//}
//int validSum(int[] row, int c) {
//    int result = 0;
//    if (0 <= c-1)             result += row[c-1];
//    if (true)                 result += row[c];
//    if (c+1 <= row.length-1)  result += row[c+1];
//    return result;
//}
//
//X. https://discuss.leetcode.com/topic/26236/infinite-board-solution
//For the second follow-up question, here's a solution for an infinite board. Instead of a two-dimensional array of ones and zeros, I represent the board as a set of live cell coordinates.
//def gameOfLifeInfinite(self, live):
//    ctr = collections.Counter((I, J)
//                              for i, j in live
//                              for I in range(i-1, i+2)
//                              for J in range(j-1, j+2)
//                              if I != i or J != j)
//    return {ij
//            for ij in ctr
//            if ctr[ij] == 3 or ctr[ij] == 2 and ij in live}
//And here's a wrapper that uses the above infinite board solution to solve the problem we have here at the OJ (submitted together, this gets accepted):
//def gameOfLife(self, board):
//    live = {(i, j) for i, row in enumerate(board) for j, live in enumerate(row) if live}
//    live = self.gameOfLifeInfinite(live)
//    for i, row in enumerate(board):
//        for j in range(len(row)):
//            row[j] = int((i, j) in live)
//What I do is I have the coordinates of all living cells in a set. Then I count the living neighbors of all cells by going through the living cells and increasing the counter of their neighbors (thus cells without living neighbor will not be in the counter). Afterwards I just collect the new set of living cells by picking those with the right amount of neighbors
//    private Set<Coord> gameOfLife(Set<Coord> live) {
//        Map<Coord,Integer> neighbours = new HashMap<>();
//        for (Coord cell : live) {
//            for (int i = cell.i-1; i<cell.i+2; i++) {
//                for (int j = cell.j-1; j<cell.j+2; j++) {
//                    if (i==cell.i && j==cell.j) continue;
//                    Coord c = new Coord(i,j);
//                    if (neighbours.containsKey(c)) {
//                        neighbours.put(c, neighbours.get(c) + 1);
//                    } else {
//                        neighbours.put(c, 1);
//                    }
//                }
//            }
//        }
//        Set<Coord> newLive = new HashSet<>();
//        for (Map.Entry<Coord,Integer> cell : neighbours.entrySet())  {
//            if (cell.getValue() == 3 || cell.getValue() == 2 && live.contains(cell.getKey())) {
//                newLive.add(cell.getKey());
//            }
//        }
//        return newLive;
//    }
//and the wrapper:
//    public void gameOfLife(int[][] board) {
//        Set<Coord> live = new HashSet<>();
//        int m = board.length;
//        int n = board[0].length;
//        for (int i = 0; i<m; i++) {
//            for (int j = 0; j<n; j++) {
//                if (board[i][j] == 1) {
//                    live.add(new Coord(i,j));
//                }
//            }
//        };
//        live = gameOfLife(live);
//        for (int i = 0; i<m; i++) {
//            for (int j = 0; j<n; j++) {
//                board[i][j] = live.contains(new Coord(i,j))?1:0;
//            }
//        };
//        
//    }
//https://discuss.leetcode.com/topic/37410/the-conway-game-of-life-using-hashtable-in-java-fast-solution-which-only-check-alive-cell-and-its-neighbors
// byte [][]cell =  { 
//     //    0 1 2 3 4 5 6 7 8
//   {0,0,0,0,0,0,0,0,0}, // 0
//   {0,0,0,0,0,0,0,0,0}, // 1
//   {0,0,0,0,1,0,0,0,0}, // 2
//   {0,0,0,0,1,0,0,0,0}, // 3
//   {0,0,0,0,1,0,0,0,0}, // 4
//   {0,0,0,0,0,0,0,0,0} , // 5
//   {0,0,0,0,0,0,0,0,0}  // 6
// };
// 
// public GameOfLife(byte cellArray[][]){
//      cell= cellArray;     
// }
//
//    public GameOfLife(){ }
//
//// Create a hash map, which contain Alive cell and its neighbors 
//Hashtable<String, CELL> ht = new Hashtable<String, CELL>();
//
//    public  void playGOL() {
// if(cell==null) 
// {
//  System.out.println("Error: zero cell exist !!!"); 
//  return; 
// }
// 
// // 1. Check for alive cell.
// // 2. Add alive cell and its neighbors in Hashtable
// initializeHashTable();
// 
// // 1. Print Game Cells 
// // 2. Execute game rule on cells
// // 3. Assign updated cell value in Game cellay.
// 
// do{
//  printGOL();
//  nextStep();
//  if(setCellValues() == 0) // if setCellValues() return zero means, All cell dead by now. Game is Over.
//   break;
// }while(true);
//}
//void initializeHashTable(){
// // Put elements to the map
// for(int i=0; i<cell.length; i++){
//  for(int j=0; j<cell[i].length; j++){
//   if(cell[i][j] == 1)  {
//    ht.put(getKey(i,j), new CELL (i, j, (byte) 1));
//    insertNeighbours(i,j);
//   }
//  }
// }
//}
//
//void nextStep(){
//       Enumeration<String> htElements = ht.keys();      
//
// while(htElements.hasMoreElements()) {
//  final String key = (String) htElements.nextElement();   
//  final CELL c = (CELL)ht.get(key);          
//  
//  // count cell neighbors 
//  final int count = countCellNeghbours (c.r, c.c);
//
//  /* check for Game Of Life rules
//  For a space that is 'populated':
//  - Each cell with one or no neighbors dies, as if by solitude.
//  - Each cell with four or more neighbors dies, as if by overpopulation.
//  - Each cell with two or three neighbors survives.
//  
//  For a space that is 'empty' or 'unpopulated':
//  - Each cell with three neighbors becomes populated.
//   */
//  if(c.cell == 1){
//   switch(count){
//   case 2:
//   case 3:
//    // Do nothing, cell is safe.
//    break;
//   case 0:
//   case 1:
//   default:
//    //kill cell
//    c.cell = 0;
//    break;    
//   }
//  } else{
//   if(count == 3){
//    //activate cell
//    c.cell = 1;
//    insertNeighbours(c.r, c.c);
//   } else if(count==0)
//    //Remove entry from Hashtable, no more needed.
//    ht.remove((String)key);
//  }
// }
//}
//
//int setCellValues(){
// Enumeration<String> htElements = ht.keys();
// int count = 0; // count value to check if we have all dead cell.
// while(htElements.hasMoreElements()) { // Finally initialize with updated cell values.
//  final String key = (String) htElements.nextElement();
//  final CELL c = (CELL)ht.get(key);
//  cell[c.r][c.c] = c.cell;
//  count += c.cell;
// }
// return count;
//}
//
//void printGOL(){
// System.out.println();
// // Display elements
// for(int i=0; i<cell.length; i++){
//  for(int j=0; j<cell[i].length; j++){
//   if(cell[i][j] ==0)
//    System.out.print("0"); // 0 Indicate dead cell.
//   else
//    System.out.print("X"); // X indicate Alive cell
//  }
//  System.out.println();
// }
//}
//
//public static String getKey(final int idx, final int jdx) {
// String key;
//
// if(idx<10 && jdx<10){
//  key = "0"+ idx + "0" + jdx;         
// }else if(idx<10){
//  key = "0"+ idx  + jdx;         
// }else if(jdx<10){
//  key = idx  + "0" + jdx;         
// }else{
//  key = Integer.toString(idx)  + Integer.toString(jdx);         
// }
// //     System.out.print( " key: " +  key);
// return key;
//}
//
//void insertNeighbours(final int i, int j){
// if(ht.get(getKey(i,j-1)) == null) // do not insert if cell already exist in Hashtable 
//  ht.put(getKey(i,j-1), new CELL (i, j-1, (byte) cell[i][j-1]));
//
// if(ht.get(getKey(i,j+1) )== null) // do not insert if cell already exist in Hashtable 
//  ht.put(getKey(i,j+1), new CELL (i, j+1, (byte) cell[i][j+1]));
//
// j--;
//
// for(int n=0; n<3; n++){
//  if(ht.get(getKey(i-1,j) )== null) // do not insert if cell already exist in Hashtable 
//   ht.put(getKey(i-1,j), new CELL (i-1, j, (byte) cell[i-1][j]));
//  if(ht.get(getKey(i+1,j) )== null) // do not insert if cell already exist in Hashtable 
//   ht.put(getKey(i+1,j), new CELL (i+1, j, (byte) cell[i+1][j]));
//  j++;
// }
//}
//
//byte countCellNeghbours( final int i,final int j){
// if(i<1 || j<1 || (i >=cell.length-1) ||  (j>=cell[0].length-1) ) // cellay Index out of bound check
//  return 0;
//
// return (byte) (cell[i][j-1] + cell[i][j+1]  +  
//   cell[i-1][j-1] + cell[i-1][j]  + cell[i-1][j+1] + 
//   cell[i+1][j-1] + cell[i+1][j]  + cell[i+1][j+1]) ;  
//  } 
//https://segmentfault.com/a/1190000003819277
//    public void gameOfLife(int[][] board) {
//        int m = board.length, n = board[0].length;
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                int lives = 0;
//                // 判断上边
//                if(i > 0){
//                    lives += board[i - 1][j] == 1 || board[i - 1][j] == 2 ? 1 : 0;
//                }
//                // 判断左边
//                if(j > 0){
//                    lives += board[i][j - 1] == 1 || board[i][j - 1] == 2 ? 1 : 0;
//                }
//                // 判断下边
//                if(i < m - 1){
//                    lives += board[i + 1][j] == 1 || board[i + 1][j] == 2 ? 1 : 0;
//                }
//                // 判断右边
//                if(j < n - 1){
//                    lives += board[i][j + 1] == 1 || board[i][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断左上角
//                if(i > 0 && j > 0){
//                    lives += board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 2 ? 1 : 0;
//                }
//                //判断右下角
//                if(i < m - 1 && j < n - 1){
//                    lives += board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断右上角
//                if(i > 0 && j < n - 1){
//                    lives += board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 2 ? 1 : 0;
//                }
//                // 判断左下角
//                if(i < m - 1 && j > 0){
//                    lives += board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 2 ? 1 : 0;
//                }
//                // 根据周边存活数量更新当前点，结果是0和1的情况不用更新
//                if(board[i][j] == 0 && lives == 3){
//                    board[i][j] = 3;
//                } else if(board[i][j] == 1){
//                    if(lives < 2 || lives > 3) board[i][j] = 2;
//                }
//            }
//        }
//        // 解码
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                board[i][j] = board[i][j] % 2;
//            }
//        }
//    }
//另一种编码方式是位操作，将下轮该cell要变的值存入bit2中，然后还原的时候右移就行了。
//public void solveInplaceBit(int[][] board){
//        int m = board.length, n = board[0].length;
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                int lives = 0;
//                // 累加上下左右及四个角还有自身的值
//                for(int y = Math.max(i - 1, 0); y <= Math.min(i + 1, m - 1); y++){
//                    for(int x = Math.max(j - 1, 0); x <= Math.min(j + 1, n - 1); x++){
//                        // 累加bit1的值
//                        lives += board[y][x] & 1;
//                    }
//                }
//                // 如果自己是活的，周边有两个活的，lives是3
//                // 如果自己是死的，周边有三个活的，lives是3
//                // 如果自己是活的，周边有三个活的，lives减自己是3
//                if(lives == 3 || lives - board[i][j] == 3){
//                    board[i][j] |= 2;
//                }
//            }
//        }
//        // 右移就是新的值
//        for(int i = 0; i < m; i++){
//            for(int j = 0; j < n; j++){
//                board[i][j] >>>= 1;
//            }
//        }
//}
//如果循环矩阵如何解决？循环的意思是假设一个3x3的矩阵,则a[0][0]的左边是a[0][1]，其左上是a[2][2]
//这样我们的坐标要多加一个数组长度，使用坐标时还要取模
//   public void solveInplaceCircular(int rounds, int[][] board){
//       for(int round = 0; round < rounds; round++){
//           int m = board.length, n = board[0].length;
//           for(int i = 0; i < m; i++){
//               for(int j = 0; j < n; j++){
//                   int lives = 0;
//                   // 多加一个数组长度
//                   for(int y = i + m - 1; y <= i + m + 1; y++){
//                       for(int x = j + n - 1; x <= j + n + 1; x++){
//                           // 使用的时候要取模
//                           lives += board[y % m][x % n] & 1;
//                       }
//                   }
//                   if(lives == 3 || lives - board[i][j] == 3){
//                       board[i][j] |= 2;
//                   }
//               }
//           }
//           for(int i = 0; i < m; i++){
//               for(int j = 0; j < n; j++){
//                   board[i][j] >>>= 1;
//               }
//           }
//       }
//   }
//如果矩阵很大如何优化？我们可以只记录存活节点的信息，存入一个live的list中，这里active代表着存活节点，或者存活节点的邻居。每次只计算这个list中节点和其邻居的情况。进一步优化的话，我们可以用一个active的list，只记录上次更新的节点，或者该节点的邻居。等计算完这个列表后，将产生更新的节点和它的邻居们存入一个新列表中，再用这个新列表里节点的值来更新矩阵。下一轮时，就计算这个新列表，再产生一个新列表。
//如果多核的机器如何优化？
//
//因为是多核，我们可以用线程来实现并行计算。如图，将矩阵分块后，每个线程只负责其所在的分块的计算，不过主线程每一轮都要更新一下这些分块的边缘，并提供给相邻分块。所以这里的开销就是主线程和子线程通信这个边缘信息的开销。如果线程变多分块变多，边缘信息也会变多，开销会增大。所以选取线程的数量是这个开销和并行计算能力的折衷。
//如果是多台机器如何优化？
//同样的，我们可以用一个主机器负责处理边缘信息，而多个子机器处理每个分块的信息，因为是分布式的，我们的矩阵可以分块的存储在不同机器的内存中，这样矩阵就可以很大。而主机在每一轮开始时，将边缘信息通过网络发送给哥哥分块机器，然后分块机器计算好自己的分块后，把新自己内边缘信息反馈给主机器。下一轮，等主机器收集齐所有边缘后，就可以继续重复。
//不过多台机器时还有一个更好的方法，就是使用Map Reduce。Map Reduce的简单版本是这样的，首先我们的Mapper读入一个file，这个file中每一行代表一个存活的节点的坐标，然后Mapper做出9个Key-Value对，对这个存活节点的邻居cell，分发出一个1。而对于节点自身，也要分发出一个1。这里Reducer是对应每个cell的，每个reducer累加自己cell得到了多少个1，就知道自己的cell周围有多少存活cell，就能知道该cell下一轮是否可以存活，如果可以存活则分发回mapper的文件中，等待下次读取，如果不能则舍弃。
//如果要进一步优化Map Reduce，那我们主要优化的地方则是mapper和reducer通信的开销，因为对于每个存活节点，mapper都要向9个reducer发一次信息。我们可以在mapper中用一个哈希表，当mapper读取文件的某一行时，先不向9个reducer发送信息，而是以这9个cell作为key，将1累加入哈希表中。这样等mapper读完文件后，再把哈希表中的cell和该cell对应的累加1次数，分发给相应cell的reducer，这样就可以减少一些通信开销。相当于是现在mapper内做了一次累加。这种优化在只有一个mapper是无效的，因为这就等于直接在mapper中统计完了，但是如果多个mapper同时执行时，相当于在每个mapper里先统计一会，再交给reducer一起统计每个mapper的统计结果。
//如果整个图都会变，有没有更快的方法？
//参见Hashlife，大意是用哈希记录一下会重复循环的pattern
//
//X. Follow up
//http://www.1point3acres.com/bbs/thread-206762-1-1.html
//只有一道题，关灯开灯的问题，给一个n*n 2d array，表示light on/off，求下一个state的状态，规则是8个neighbour里，有两个on的不变，有三个on的变成on，其他变成off。。follow up：
//1. after k steps，求状态2. n is very large-google 1point3acres
//3. k is very large
//
//要求自己选数据结构写方程，我就用的vector。。除了遍历没想出别的办法。。k很大的时候可以用hashmap纪录找pattern。。n很大的时候说了bitmap，然后又说n有million级怎么办
//
//原题确实不难 关键是followUp怎么做呢
//
//我能想到的也是用hash存已经算过的
//
//n很大能不能每次拿出一块 一块一块地算呢？
//n is very large可不可建一个数据结构把每个点做成Node，然后链接它的8个neighbours， 这样就可以分开存了； k很大我也觉得就是把pattern给存起来，在遇到相同的pattern的时候有个hashmap一类的东西可以来lookup
//
//然后如果n很大的话 就存亮的点坐标就好了 不用存整个matrix~ 但是k很大的话。。怎么办 不还是得做k次吗？0.0.。。。
//这个也说了，但是只能针对sparse的。。
//
//follow up答题方向感觉应该是拓展到多台机器并行计算,MPI之类的.
//follow-up map-reduce, 检查boundary
//说了这个，他继续问只有一台machine怎么办。。
//
//我觉得Follow up的问题是要用解决稀疏矩阵点积的方法来解决。
//我想的方法是，把所有亮灯的坐标和状态存到一个hashtable（key(坐标),value(state)），遍历matrix的时候，按坐标查找周围的灯就好了。
//
//检查完当前位置的状态后要更新hashtable。
//
//如果很满的话 下一个状态就会稀疏了啊。。0.0而且如果真的很满  不管什么数据结构都需要存这么多点啊。。
//反正他一直强调n非常大，问我怎么才能fit into memory，我说了map reduce分割文件他也不太满意的样子，最后说的每次只读一部分，他让我把code写出来但是没时间了。。其实我也不会写从特定byte开始读的方法＝＝
//
//那可能意思是让你提出来其实不用将所有的内容都放在内存里,内存里只存三行.
//
//我知道了！！！ 可能就是楼下说的！ 一次只需要读入三行数据
//再用一行的extra memory 存储上一次状态就可以~
//
//
//我觉得k很大的话就用一个hashmap存key：初始状态，value:下一个状态，省去重复计算。
//n很大可不可以对于每个点，只取它周围八个邻居，也就是一次只读9个点，更新中心点的状态，再读下一个点的9个以此类推
// * @author het
// *
// */
public class LeetCode289GameofLife {
//	To solve it in place, we use 2 bits to store 2 states:
//		[2nd bit, 1st bit] = [next state, current state]
//
//		- 00  dead (next) <- dead (current)
//		- 01  dead (next) <- live (current)  
//		- 10  live (next) <- dead (current)  
//		- 11  live (next) <- live (current) 
//		In the beginning, every cell is either 00 or 01.
//		Notice that 1st state is independent of 2nd state.
//		Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
//		Let's count # of neighbors from 1st state and set 2nd state bit.
//		Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
//		In the end, delete every cell's 1st state by doing >> 1.
//		For each cell's 1st bit, check the 8 pixels around itself, and set the cell's 2nd bit.
//		Transition 01 -> 11: when board == 1 and lives >= 2 && lives <= 3.
//		Transition 00 -> 10: when board == 0 and lives == 3.
//		To get the current state, simply do
//		board[i][j] & 1
//		To get the next state, simply do
//		board[i][j] >> 1
//		lives -= board[i][j] & 1; why did lives have to minus itself?
//		Your solution traversed from top left to bottom right. But might the latter cell live/die affect the previous cell? Why shouldnt we consider this situation?
//		(1) This is for conveniency. Notice that it's counting the 8 pixels around itself, not including itself. (2) Notice that bit0 is independent of bit1. Think about the board is changing from state1 "instantly" into state2. So we only retrieve the first state using board[i][j] & 1 and changing only the second state by changing 01 to 11 or 00 to 10. This is same as board[i][j] |= (1<<1).
		public void gameOfLife(int[][] board) {
		    if (board == null || board.length == 0) return;
		    int m = board.length, n = board[0].length;

		    for (int i = 0; i < m; i++) {
		        for (int j = 0; j < n; j++) {
		            int lives = liveNeighbors(board, m, n, i, j);

		            // In the beginning, every 2nd bit is 0;
		            // So we only need to care about when will the 2nd bit become 1.
		            if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
		                board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
		            }
		            if (board[i][j] == 0 && lives == 3) {
		                board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
		            }
		        }
		    }

		    for (int i = 0; i < m; i++) {
		        for (int j = 0; j < n; j++) {
		            board[i][j] >>= 1;  // Get the 2nd state.
		        }
		    }
		}

		public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
		    int lives = 0;
		    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
		        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
		            lives += board[x][y] & 1;
		        }
		    }
		    lives -= board[i][j] & 1;
		    return lives;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
