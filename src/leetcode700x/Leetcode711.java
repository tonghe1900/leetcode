package leetcode700x;
/**
 * [Leetcode] 711. Number of Distinct Islands II 解题报告
标签： Leetcode 解题报告 DFS
2018年02月07日 18:29:27415人阅读 评论(0) 收藏  举报
 分类： IT公司面试习题（727）  
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/magicbean2/article/details/79282937

题目：

Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

Count the number of distinct islands. An island is considered to be the same as another if they have the same shape, or have the same shape after rotation (90, 180, or 270 degrees only) or reflection (left/right direction or up/down direction).

Example 1:

11000
10000
00001
00011
Given the above grid map, return 1. 

Notice that:
11
1
and
 1
11
are considered same island shapes. Because if we make a 180 degrees clockwise rotation on the first island, then two islands will have the same shapes.
Example 2:

11100
10001
01001
01110
Given the above grid map, return 2.

Here are the two distinct islands:
111
1
and
1
1

Notice that:
111
1
and
1
111
are considered same island shapes. Because if we flip the first array in the up/down direction, then they have the same shapes.
Note: The length of each dimension in the given grid does not exceed 50.

思路：

DFS的套路不难，这里主要是如何处理小岛的旋转问题。我们的做法是：每次找到一个小岛之后，我们生成其8个对应的变形（包括本身，翻转以及旋转等）。然后返回其排序最小的那个，作为这种类型的小岛的代表。这样其余的思路就都可以照搬 [Leetcode] 694. Number of Distinct Islands 解题报告中的思路了。

代码：

[cpp] view plain copy
class Solution {  
public:  
    int numDistinctIslands2(vector<vector<int>>& grid) {  
        int row_num = grid.size(), col_num = grid[0].size();  
        set<vector<pair<int, int>>> islands;  
        for (int r = 0; r < row_num; ++r) {  
            for (int c = 0; c < col_num; ++c) {  
                if (grid[r][c] == 1) {  
                    vector<pair<int, int>> island;  
                    DFS(grid, r, c, r, c, island);  
                    islands.insert(normalize(island));  
                }  
            }  
        }  
        return islands.size();  
    }  
private:  
    void DFS(vector<vector<int>> &grid, int r0, int c0, int r, int c, vector<pair<int, int>> &island) {  
        int row_num = grid.size(), col_num = grid[0].size();  
        if (r < 0 || r >= row_num || c < 0 || c >= col_num || grid[r][c] <= 0) {  
            return;  
        }  
        grid[r][c] = -1;  
        island.push_back(make_pair(r - r0, c - c0));  
        for (int d = 0; d < 4; ++d) {  
            DFS(grid, r0, c0, r + delta[d][0], c + delta[d][1], island);  
        }  
    }  
    vector<pair<int, int>> normalize(vector<pair<int, int>> &a) {  
        vector<vector<pair<int, int>>> ret(8, vector<pair<int, int>>());  
        for (auto &p : a) {  
            int x = p.first, y = p.second;  
            ret[0].push_back(make_pair(x, y));  
            ret[1].push_back(make_pair(x, -y));  
            ret[2].push_back(make_pair(-x, y));  
            ret[3].push_back(make_pair(-x, -y));  
            ret[4].push_back(make_pair(y, x));  
            ret[5].push_back(make_pair(y, -x));  
            ret[6].push_back(make_pair(-y, x));  
            ret[7].push_back(make_pair(-y, -x));  
        }  
        for (int i = 0; i < 8; ++i) {  
            sort(ret[i].begin(), ret[i].end());  
            int r_offset = 0 - ret[i][0].first, c_offset = 0 - ret[i][0].second;  
            for (int j = 0; j < ret[i].size(); ++j) {  
                ret[i][j].first += r_offset;  
                ret[i][j].second += c_offset;  
            }  
        }  
        sort(ret.begin(), ret.end());  
        return ret[0];  
    }   
    int delta[4][2] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  
};  
 * @author tonghe
 *
 */
public class Leetcode711 {

}
