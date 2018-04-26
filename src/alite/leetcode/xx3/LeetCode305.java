package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode [305] Number of Islands II
 * 
 * http://www.cnblogs.com/jcliBlogger/p/4965051.html A 2d grid map of m rows and
 * n columns is initially filled with water. We may perform an addLand operation
 * which turns the water at position (row, col) into a land. Given a list of
 * positions to operate, count the number of islands after each addLand
 * operation. An island is surrounded by water and is formed by connecting
 * adjacent lands horizontally or vertically. You may assume all four edges of
 * the grid are all surrounded by water. Example: Given m = 3, n = 3, positions
 * = [[0,0], [0,1], [1,2], [2,1]]. Initially, the 2d grid grid is filled with
 * water. (Assume 0 represents water and 1 represents land). 0 0 0 0 0 0 0 0 0
 * Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. 1 0 0
 * 0 0 0 Number of islands = 1 0 0 0 Operation #2: addLand(0, 1) turns the water
 * at grid[0][1] into a land. 1 1 0 0 0 0 Number of islands = 1 0 0 0 Operation
 * #3: addLand(1, 2) turns the water at grid[1][2] into a land. 1 1 0 0 0 1
 * Number of islands = 2 0 0 0 Operation #4: addLand(2, 1) turns the water at
 * grid[2][1] into a land. 1 1 0 0 0 1 Number of islands = 3 0 1 0 We return the
 * result as an array: [1, 1, 2, 3] This problem requires a classic data
 * structure called UnionFind. Take some efforts to learn it at first, like
 * using this Princeton's notes offered by peisi.
 * https://leetcode.com/discuss/69392/python-clear-solution-unionfind2d-weighting-compression
 * The following algorithm is derived from Princeton's lecture note on Union
 * Find in Algorithms and Data Structures It is a well organized note with clear
 * illustration describing from the naive QuickFind to the one with Weighting
 * and Path compression. With Weighting and Path compression, The algorithm runs
 * in O((M+N) log* N) where M is the number of operations ( unite and find ), N
 * is the number of objects, log* is iterated logarithm while the naive runs
 * inO(MN). For our problem, If there are N positions, then there are O(N)
 * operations and N objects then total is O(N log*N), when we don't consider the
 * O(mn) for array initialization. Note that log*N is almost constant (for N =
 * 265536, log*N = 5) in this universe, so the algorithm is almost linear with
 * N. However, if the map is very big, then the initialization of the arrays can
 * cost a lot of time when mnis much larger than N. In this case we should
 * consider using a hashmap/dictionary for the underlying data structure to
 * avoid this overhead. Of course, we can put all the functionality into the
 * Solution class which will make the code a lot shorter. But from a design
 * point of view a separate class dedicated to the data sturcture is more
 * readable and reusable. I implemented the idea with 2D interface to better fit
 * the problem. private int[][] dir = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
 * 
 * public List<Integer> numIslands2(int m, int n, int[][] positions) {
 * UnionFind2D islands = new UnionFind2D(m, n); List<Integer> ans = new
 * ArrayList<>(); for (int[] position : positions) { int x = position[0], y =
 * position[1]; int p = islands.add(x, y); for (int[] d : dir) { int q =
 * islands.getID(x + d[0], y + d[1]); if (q > 0 && !islands.find(p, q))
 * islands.unite(p, q); } ans.add(islands.size()); } return ans; } class
 * UnionFind2D { private int[] id; private int[] sz; private int m, n, count;
 * 
 * public UnionFind2D(int m, int n) { this.count = 0; this.n = n; this.m = m;
 * this.id = new int[m * n + 1]; this.sz = new int[m * n + 1]; }
 * 
 * public int index(int x, int y) { return x * n + y + 1; }
 * 
 * public int size() { return this.count; }
 * 
 * public int getID(int x, int y) { if (0 <= x && x < m && 0<= y && y < n)
 * return id[index(x, y)]; return 0; }
 * 
 * public int add(int x, int y) { int i = index(x, y); id[i] = i; sz[i] = 1;
 * ++count; return i; }
 * 
 * public boolean find(int p, int q) { return root(p) == root(q); }
 * 
 * public void unite(int p, int q) { int i = root(p), j = root(q); if (sz[i] <
 * sz[j]) { //weighted quick union id[i] = j; sz[j] += sz[i]; } else { id[j] =
 * i; sz[i] += sz[j]; } --count; }
 * 
 * private int root(int i) { for (;i != id[i]; i = id[i]) id[i] = id[id[i]];
 * //path compression return i; }
 * 
 * private static final int[][] dir = {{0, 1},{1, 0},{0, -1},{-1, 0}};
 * 
 * public List<Integer> numIslands2(int n, int m, int[][] positions) { int[][]
 * map = new int[n + 2][m + 2]; List<Integer> ans = new ArrayList(); int islandN
 * = 0; UnionSet us = new UnionSet(n, m);
 * 
 * for (int[] p : positions) { map[p[0] + 1][p[1] + 1] = 1; islandN++; for
 * (int[] d : dir) if (map[p[0] + d[0] + 1][p[1] + d[1] + 1] > 0 &&
 * us.union(p[0], p[1], p[0] + d[0], p[1] + d[1])) islandN--; ans.add(islandN);
 * } return ans; }
 * 
 * private class UnionSet { int n, m; int[] p, size;
 * 
 * public UnionSet(int a, int b) { n = a; m = b; p = new int[getID(n, m)]; size
 * = new int[getID(n, m)]; }
 * 
 * private int getID(int i, int j) { return i * m + j + 1; // ensure no id == 0;
 * }
 * 
 * private int find(int i) { if (p[i] == 0) { // == 0 means not yet initialized
 * p[i] = i; size[i] = 1; } p[i] = (p[i] == i) ? i : find(p[i]); return p[i]; }
 * 
 * private boolean union(int i1, int j1, int i2, int j2) { // true if combines
 * two element of two different sets int s1 = find(getID(i1, j1)), s2 =
 * find(getID(i2, j2)); if (s1 == s2) return false; if (size[s1] > size[s2]) {
 * p[s2] = s1; size[s1] += size[s2]; } else { p[s1] = s2; size[s2] += size[s1];
 * } return true; } }
 * 
 * 
 * http://www.cnblogs.com/yrbbest/p/5050749.html 加入了Path compression以及Weight，
 * 速度快了不少。 Time Complexity - (k * logmn) Space Complexity - O(mn),
 * 这里k是positions的长度 public class Solution { private int[] id; private int[] sz;
 * private int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; public
 * List<Integer> numIslands2(int m, int n, int[][] positions) { List<Integer>
 * res = new ArrayList<>(); if (positions == null || positions.length == 0 || m
 * < 0 || n < 0) { return res; } id = new int[m * n]; sz = new int[m * n]; for
 * (int i = 0; i < id.length; i++) { id[i] = i; }
 * 
 * int count = 0; for (int[] position : positions) { int p = position[0] * n +
 * position[1]; sz[p]++; count++; for (int[] direction : directions) { int
 * newRow = position[0] + direction[0]; int newCol = position[1] + direction[1];
 * if (newRow < 0 || newCol < 0 || newRow > m - 1 || newCol > n - 1) { continue;
 * } int q = newRow * n + newCol; if (sz[q] > 0) { if (isConnected(p, q)) {
 * continue; } else { union(p, q); count--; } } } res.add(count); } return res;
 * }
 * 
 * private int getRoot(int p) { while (p != id[p]) { id[p] = id[id[p]]; p =
 * id[p]; } return p; }
 * 
 * private boolean isConnected(int p, int q) { return getRoot(p) == getRoot(q);
 * }
 * 
 * private void union(int p, int q) { int rootP = getRoot(p); int rootQ =
 * getRoot(q); if (rootP == rootQ) { return; } else { if (sz[p] < sz[q]) {
 * id[rootP] = rootQ; sz[q] += sz[p]; } else { id[rootQ] = rootP; sz[p] +=
 * sz[q]; } } } } 又是一道Union Find的经典题。这道题代码主要参考了yavinci大神。风格还是princeton
 * Sedgewick的那一套。这里我们可以把二维的Union-Find映射为一维的Union
 * Find。使用Quick-Union就可以完成。但这样的话Time Complexity是O(kmn)。
 * 想要达到O(klogmn)的话可能还需要使用Weighted-Quick Union配合path compression。 Time Complexity
 * - O(mn * k)， Space Complexity - O(mn) public class Solution { int[][]
 * directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
 * 
 * public List<Integer> numIslands2(int m, int n, int[][] positions) {
 * List<Integer> res = new ArrayList<>(); if(m < 0 || n < 0 || positions ==
 * null) { return res; } int[] id = new int[m * n]; // union find array int
 * count = 0; Arrays.fill(id, -1);
 * 
 * for(int i = 0; i < positions.length; i++) { int index = n * positions[i][0] +
 * positions[i][1]; if(id[index] != -1) { res.add(count); continue; }
 * 
 * id[index] = index; count++;
 * 
 * for(int[] direction : directions) { int x = positions[i][0] + direction[0];
 * int y = positions[i][1] + direction[1]; int neighborIndex = n * x + y; if(x <
 * 0 || x >= m || y < 0 || y >= n || id[neighborIndex] == -1) { continue; }
 * if(!connected(id, index, neighborIndex)) { union(id, neighborIndex, index);
 * count--; } }
 * 
 * res.add(count); } return res; } private boolean connected(int[] id, int p,
 * int q) { return id[p] == id[q]; }
 * 
 * private void union(int[] id, int p, int q) { int pid = id[p]; int qid =
 * id[q]; for(int i = 0; i < id.length; i++) { if(id[i] == pid) { id[i] = qid; }
 * } } }
 * https://leetcode.com/discuss/69572/easiest-java-solution-with-explanations
 * public int findIsland(int[] roots, int id) { while(id != roots[id]) id =
 * roots[id]; return id; }
 * 
 * If you have time, add one line to shorten the tree. The new runtime becomes:
 * 19ms (95.94%). public int findIsland(int[] roots, int id) { while(id !=
 * roots[id]) { roots[id] = roots[roots[id]]; // only one line added id =
 * roots[id]; } return id; } http://www.cnblogs.com/jcliBlogger/p/4965051.html
 * LIKE CODING: LeetCode [305] Number of Islands II
 * 
 * http://www.1point3acres.com/bbs/thread-148670-1-1.html 第一题类似于孤岛问题，follow
 * up是如果在来个stream，每次输入一个点是岛点，问你怎么update已经有的数组并返回新的岛的个数。 Read full article from
 * LIKE CODING: LeetCode [305] Number of Islands II
 * 
 * @author het
 *
 */
public class LeetCode305 {
	private int[][] dir = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

	public List<Integer> numIslands2(int m, int n, int[][] positions) {
		UnionFind2D islands = new UnionFind2D(m, n);
		List<Integer> ans = new ArrayList<>();
		for (int[] position : positions) {
			int x = position[0], y = position[1];
			int p = islands.add(x, y);
			for (int[] d : dir) {
				int q = islands.getID(x + d[0], y + d[1]);
				if (q > 0 && !islands.find(p, q))
					islands.unite(p, q);
			}
			ans.add(islands.size());
		}
		return ans;
	}

class UnionFind2D {
    private int[] id;
    private int[] sz;
    private int m, n, count;

    public UnionFind2D(int m, int n) {
        this.count = 0;
        this.n = n;
        this.m = m;
        this.id = new int[m * n + 1];
        this.sz = new int[m * n + 1];
    }

    public int index(int x, int y) { return x * n + y + 1; }

    public int size() { return this.count; }

    public int getID(int x, int y) {
        if (0 <= x && x < m && 0<= y && y < n)
            return id[index(x, y)];
        return 0;
    }

    public int add(int x, int y) {
        int i = index(x, y);
        id[i] = i; sz[i] = 1;
        ++count;
        return i;
    }

    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    public void unite(int p, int q) {
        int i = root(p), j = root(q);
        if (sz[i] < sz[j]) { //weighted quick union
            id[i] = j; sz[j] += sz[i];
        } else {
            id[j] = i; sz[i] += sz[j];
        }
        --count;
    }

    private int root(int i) {
        for (;i != id[i]; i = id[i])
            id[i] = id[id[i]]; //path compression
        return i;
    }

    private static final int[][] dir = {{0, 1},{1, 0},{0, -1},{-1, 0}};

    public List<Integer> numIslands2(int n, int m, int[][] positions) {
        int[][] map = new int[n + 2][m + 2];
        List<Integer> ans = new ArrayList();
        int islandN = 0;
        UnionSet us = new UnionSet(n, m);

        for (int[] p : positions) {
            map[p[0] + 1][p[1] + 1] = 1;
            islandN++;
            for (int[] d : dir)
                if (map[p[0] + d[0] + 1][p[1] + d[1] + 1] > 0 && us.union(p[0], p[1], p[0] + d[0], p[1] + d[1])) 
                    islandN--;
            ans.add(islandN);
        }
        return ans;
    }

    private class UnionSet {
        int n, m;
        int[] p, size;

        public UnionSet(int a, int b) {
            n = a; m = b;
            p = new int[getID(n, m)];
            size = new int[getID(n, m)];
        }

        private int getID(int i, int j) {
            return i * m + j + 1; // ensure no id == 0;
        }

        private int find(int i) {
            if (p[i] == 0) { // == 0 means not yet initialized
                p[i] = i;
                size[i] = 1;
            }
            p[i] = (p[i] == i) ? i : find(p[i]);
            return p[i];
        }

        private boolean union(int i1, int j1, int i2, int j2) { // true if combines two element of two different sets
            int s1 = find(getID(i1, j1)), s2 = find(getID(i2, j2));
            if (s1 == s2) return false;
            if (size[s1] > size[s2]) {
                p[s2] = s1;
                size[s1] += size[s2];
            } else {
                p[s1] = s2;
                size[s2] += size[s1];
            }
            return true;
        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
