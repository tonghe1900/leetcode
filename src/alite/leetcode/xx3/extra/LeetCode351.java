package alite.leetcode.xx3.extra;

/**
 * LeetCode 351 - Android Unlock Patterns
 * 
 * Related: Android patterns possible on 3x3 matrix of numbers
 * http://lcoj.tk/problems/android-unlock-patternselmirap/ Given Android 9 key
 * lock screen and numbers m and n, where 1 <= m <= n <= 9 . Count the total
 * number of patterns of Android lock screen, which consist of minimum of m keys
 * and maximum n keys. Rules for valid pattern
 * 
 * Each pattern must connect at least m keys and at most n keys All the keys
 * must be distinct If the line connecting two consecutive keys in the pattern
 * passes through any other keys, the other keys must have previously selected
 * in the pattern. No jumps through non selected key is allowed The order of
 * keys used matters.
 * 
 * Example:
 * 
 * | 1 | 2 | 3 | | 4 | 5 | 6 | | 7 | 8 | 9 | Valid move : 6 - 5 - 4 - 1 - 9 - 2
 * 
 * Line 1 - 9 is valid because it pass through key 5, which has been already
 * selected in the pattern Valid move : 2 - 4 - 1 - 3 - 6
 * 
 * Line 1 - 3 is valid because it pass through key 2, which has been already
 * selected in the pattern Invalid move : 4 - 1 - 3 - 6
 * 
 * Line 1 - 3 pass through key 2 which is not still selected in the pattern
 * Invalid move : 4 - 1 - 9 - 2
 * 
 * Line 1 - 9 pass through key 5 which is not still selected in the pattern
 * https://leetcode.com/discuss/104500/java-solution-with-clear-explanations-and-optimization-81ms
 * The optimization idea is that 1,3,7,9 are symmetric, 2,4,6,8 are also
 * symmetric. Hence we only calculate one among each group and multiply by 4. //
 * cur: the current position // remain: the steps remaining int DFS(boolean
 * vis[], int[][] skip, int cur, int remain) { if(remain < 0) return 0;
 * if(remain == 0) return 1; vis[cur] = true; int rst = 0; for(int i = 1; i <=
 * 9; ++i) { // If vis[i] is not visited and (two numbers are adjacent or skip
 * number is already visited) if(!vis[i] && (skip[i][cur] == 0 ||
 * (vis[skip[i][cur]]))) { rst += DFS(vis, skip, i, remain - 1); } } vis[cur] =
 * false; return rst; }
 * 
 * public int numberOfPatterns(int m, int n) { // Skip array represents number
 * to skip between two pairs int skip[][] = new int[10][10]; skip[1][3] =
 * skip[3][1] = 2; skip[1][7] = skip[7][1] = 4; skip[3][9] = skip[9][3] = 6;
 * skip[7][9] = skip[9][7] = 8; skip[1][9] = skip[9][1] = skip[2][8] =
 * skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5; boolean
 * vis[] = new boolean[10]; int rst = 0; // DFS search each length from m to n
 * for(int i = m; i <= n; ++i) { rst += DFS(vis, skip, 1, i - 1) * 4; // 1, 3,
 * 7, 9 are symmetric rst += DFS(vis, skip, 2, i - 1) * 4; // 2, 4, 6, 8 are
 * symmetric rst += DFS(vis, skip, 5, i - 1); // 5 } return rst; }
 * https://leetcode.com/discuss/104688/simple-and-concise-java-solution-in-69ms
 * The general idea is DFS all the possible combinations from 1 to 9 and skip
 * invalid moves along the way. We can check invalid moves by using a jumping
 * table. e.g. If a move requires a jump and the key that it is crossing is not
 * visited, then the move is invalid. Furthermore, we can utilize symmetry to
 * reduce runtime, in this case it reduced from ~120ms to ~70ms.
 * https://leetcode.com/discuss/104311/java-easy-understand-dfs-solution-72ms If
 * we use the symmetry, we can only start from 1, 2 and 5 then multiply the
 * results of 1 and 2 by 4. (170ms)
 * http://dartmooryao.blogspot.com/2016/05/leetcode-351-android-unlock-patterns.html
 * (1) Create an grid, list all condition paths. For example, if we are
 * currently at node 1, and we want to go to node 3, then we need node 2
 * visited. We use condPaths[1][3] = 2; to represent this relationship. (2) Use
 * an boolean array to keep track of the nodes we have visited before. (3) Count
 * the path number from min to max seperately. (4) Use recursion function, given
 * current position, check all possible paths. If the next node is not visited
 * yet, and it satisfies the constraint, then we can go to this path. (5) Return
 * 1 when we find the end of path. public int numberOfPatterns(int m, int n) {
 * int[][] condPaths = getCondPath(); boolean[] visited = new boolean[10]; int
 * totalCount = 0; for(int i=m; i<=n; i++){ for(int j=1; j<=9; j++){ totalCount
 * += getPathCount(i, j, visited, condPaths); } } return totalCount; }
 * 
 * private int getPathCount(int pathN, int currPosi, boolean[] visited, int[][]
 * condPaths){ if(pathN == 1){ return 1; } int count = 0; visited[currPosi] =
 * true; for(int i=1; i<visited.length; i++){ if(!visited[i] &&
 * (condPaths[currPosi][i] == 0 || visited[condPaths[currPosi][i]])){ count +=
 * getPathCount(pathN-1, i, visited, condPaths); } } visited[currPosi] = false;
 * return count; }
 * 
 * private int[][] getCondPath(){ int[][] condPaths = new int[10][10];
 * condPaths[1][7] = 4; condPaths[1][3] = 2; condPaths[1][9] = 5;
 * condPaths[2][8] = 5; condPaths[3][1] = 2; condPaths[3][9] = 6;
 * condPaths[3][7] = 5; condPaths[4][6] = 5; condPaths[6][4] = 5;
 * condPaths[7][1] = 4; condPaths[7][9] = 8; condPaths[7][3] = 5;
 * condPaths[8][2] = 5; condPaths[9][7] = 8; condPaths[9][3] = 6;
 * condPaths[9][1] = 5; return condPaths; }
 * https://leetcode.com/discuss/104311/java-easy-understand-dfs-solution-72ms If
 * we use the symmetry, we can only start from 1, 2 and 5 then multiply the
 * results of 1 and 2 by 4. (170ms)
 * 
 * http://www.cnblogs.com/grandyang/p/5541012.html
 * 我们建立一个二维数组jumps，用来记录两个数字键之间是否有中间键，然后再用一个一位数组visited来记录某个键是否被访问过，然后我们用递归来解，我们先对1调用递归函数，在递归函数中，我们遍历1到9每个数字next，然后找他们之间是否有jump数字，如果next没被访问过，并且jump为0，或者jump被访问过，我们对next调用递归函数。数字1的模式个数算出来后，由于1,3,7,9是对称的，所以我们乘4即可，然后再对数字2调用递归函数，2,4,6,9也是对称的，再乘4，最后单独对5调用一次，然后把所有的加起来就是最终结果了
 * int numberOfPatterns(int m, int n) { int res = 0; vector<bool> visited(10,
 * false); vector<vector<int>> jumps(10, vector<int>(10, 0)); jumps[1][3] =
 * jumps[3][1] = 2; jumps[4][6] = jumps[6][4] = 5; jumps[7][9] = jumps[9][7] =
 * 8; jumps[1][7] = jumps[7][1] = 4; jumps[2][8] = jumps[8][2] = 5; jumps[3][9]
 * = jumps[9][3] = 6; jumps[1][9] = jumps[9][1] = jumps[3][7] = jumps[7][3] = 5;
 * res += helper(1, 1, 0, m, n, jumps, visited) * 4; res += helper(2, 1, 0, m,
 * n, jumps, visited) * 4; res += helper(5, 1, 0, m, n, jumps, visited); return
 * res; } int helper(int num, int len, int res, int m, int n,
 * vector<vector<int>> &jumps, vector<bool> &visited) { if (len >= m) ++res;
 * ++len; if (len > n) return res; visited[num] = true; for (int next = 1; next
 * <= 9; ++next) { int jump = jumps[num][next]; if (!visited[next] && (jump == 0
 * || visited[jump])) { res = helper(next, len, res, m, n, jumps, visited); } }
 * visited[num] = false; return res; }
 * 
 * X. https://leetcode.com/discuss/104320/short-c-solution
 * 其中used是一个9位的mask，每位对应一个数字，如果为1表示存在，0表示不存在，(i1, j1)是之前的位置，(i,
 * j)是当前的位置，所以滑动是从(i1, j1)到(i, j)，中间点为((i1+i)/2, (j1+j)/2),
 * 这里的I和J分别为i1+i和j1+j，还没有除以2，所以I和J都是整数。如果I%2或者J%2不为0，说明中间点的坐标不是整数，即中间点不存在，如果中间点存在，如果中间点被使用了，则这条线也是成立的，可以调用递归
 * 
 * used is the 9-bit bitmask telling which keys have already been used and
 * (i1,j1) and (i2,j2)are the previous two key coordinates. A step is valid
 * if... I % 2: It goes to a neighbor row or J % 2: It goes to a neighbor column
 * or used2 & (1 << (I/2*3 + J/2))): The key in the middle of the step has
 * already been used. (i2,j2) are the coordinates of the previous key, (i,j) are
 * the coordinates of the new key. So the new line goes from (i2,j2) to (i,j).
 * The middle point of the line is at ((i2+i)/2, (j2+j)/2). My Iand J are those
 * middle coordinates, except I didn't divide by 2 yet, so I can stay in
 * integers. Now if I % 2 isn't zero, then that means I/2 and thus (i2+i)/2 is
 * no integer. Which means the middle point of the line is not a key. Same with
 * the other coordinate. If both those checks fail, then the middle point of the
 * new line is a key, and thus I need to check that that key has been used
 * already. int numberOfPatterns(int m, int n) { return count(m, n, 0, 1, 1, 1,
 * 1); } private: int count(int m, int n, int used, int i1, int j1, int i2, int
 * j2) { int number = m <= 0; if (!n) return 1; for (int i=0; i<3; i++) { for
 * (int j=0; j<3; j++) { int I = i2 + i, J = j2 + j, used2 = used | (1 << (i*3 +
 * j)); if (used2 > used && (I % 2 || J % 2 || used2 & (1 << (I/2*3 + J/2))))
 * number += count(m-1, n-1, used2, i2, j2, i, j); } } return number; }
 * http://www.geeksforgeeks.org/number-of-ways-to-make-mobile-lock-pattern/
 * 
 * @author het
 *
 *
 *         Example:
 * 
 *         | 1 | 2 | 3 | | 4 | 5 | 6 | | 7 | 8 | 9 |
 */
public class LeetCode351 {
	// cur: the current position
	// remain: the steps remaining
	int DFS(boolean vis[], int[][] skip, int cur, int remain) {
		if (remain < 0)
			return 0;
		if (remain == 0)
			return 1;
		vis[cur] = true;
		int rst = 0;
		for (int i = 1; i <= 9; ++i) {
			// If vis[i] is not visited and (two numbers are adjacent or skip number is
			// already visited)
			if (!vis[i] && (skip[i][cur] == 0 || (vis[skip[i][cur]]))) {
				rst += DFS(vis, skip, i, remain - 1);
			}
		}
		vis[cur] = false;
		return rst;
	}

	public int numberOfPatterns(int m, int n) {
		// Skip array represents number to skip between two pairs
		int skip[][] = new int[10][10];
		skip[1][3] = skip[3][1] = 2;
		skip[1][7] = skip[7][1] = 4;
		skip[3][9] = skip[9][3] = 6;
		skip[7][9] = skip[9][7] = 8;
		skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
		boolean vis[] = new boolean[10];
		int rst = 0;
		// DFS search each length from m to n
		for (int i = m; i <= n; ++i) {
			rst += DFS(vis, skip, 1, i - 1) * 4; // 1, 3, 7, 9 are symmetric
			rst += DFS(vis, skip, 2, i - 1) * 4; // 2, 4, 6, 8 are symmetric
			rst += DFS(vis, skip, 5, i - 1); // 5
		}
		return rst;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
