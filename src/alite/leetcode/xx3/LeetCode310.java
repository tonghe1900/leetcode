package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Leetcode 310 - Minimum Height Trees

leetcode Minimum Height Trees - 细语呢喃
For a undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree.
 Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). 
Given such a graph, write a function to find all the MHTs and return a list of their root labels.
Format
The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).
You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
Example 1:
Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]
        0
        |
        1
       / \
      2   3
return [1]
Example 2:
Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
     0  1  2
      \ | /
        3
        |
        4
        |
        5
return [3, 4]
Hint:
How many MHTs can a graph have at most?
Note:
(1) According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are 
connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
(2) The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
https://leetcode.com/discuss/71763/share-some-thoughts
http://algobox.org/minimum-height-trees/
Our problem want us to find the minimum height trees and return their root labels. First we can think about a simple case
 — a path graph.
For a path graph of n nodes, find the minimum height trees is trivial. Just designate the middle point(s) as roots.
Despite its triviality, let design a algorithm to find them.
Suppose we don’t know n, nor do we have random access of the nodes. We have to traversal. It is very easy to get 
the idea of two pointers. 
One from each end and move at the same speed. When they meet or they are one step away,
 (depends on the parity of n), we have the roots we want.
This gives us a lot of useful ideas to crack our real problem.
For a tree we can do some thing similar. We start from every end, by end we mean vertex of degree 1 (aka leaves). 
We let the pointers move the same speed. When two pointers meet, we keep only one of them, 
until the last two pointers meet or one step away we then find the roots.
It is easy to see that the last two pointers are from the two ends of the longest path in the graph.
The actual implementation is similar to the BFS topological sort.
 Remove the leaves, update the degrees of inner vertexes. 
 Then remove the new leaves. Doing so level by level until there are 2 or 1 nodes left. What’s left is our answer!
The time complexity and space complexity are both O(n).
Note that for a tree we always have V = n, E = n-1
 Using List is faster than my solution using HashMap.
Whenever the Direct Access Table is possible I usually avoid Hash Table. It it better both in time and space.

public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1) return Collections.singletonList(0);

    List<Set<Integer>> adj = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) adj.add(new HashSet<>());
    for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
    }

    List<Integer> leaves = new ArrayList<>();
    for (int i = 0; i < n; ++i)
        if (adj.get(i).size() == 1) leaves.add(i);

    while (n > 2) {
        n -= leaves.size();
        List<Integer> newLeaves = new ArrayList<>();
        for (int i : leaves) {
            int j = adj.get(i).iterator().next();
            adj.get(j).remove(i);
            if (adj.get(j).size() == 1) newLeaves.add(j);
        }
        leaves = newLeaves;
    }
    return leaves;
}
http://buttercola.blogspot.com/2016/01/leetcode-minimum-height-trees.html
1. Note in the implementation, we use List<Set<Integer>> to represent a adj list. That is because the vertex 
Id ranges from 0 to n - 1, we can use the list index to represent the vertex Id. 
2. In the implementation, we don't really delete the leaf nodes, which will result in an O(n) time 
since the adjList is a list. Instead, we find the leaf nodes in each iteration and 
remove it in the neighbor list. Then we find out the new leaf nodes.

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {

        List<Integer> result = new ArrayList<>();

        if (n <= 0) {

            return result;

        }

         

        // Corner case: there is a single node and no edge at all

        if (n == 1 && edges.length == 0) {

            result.add(0);

            return result;

        }

         

        // Step 1: construct the graph

        List<Set<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            adjList.add(new HashSet<>());

        }

         

        for (int[] edge : edges) {

            int from = edge[0];

            int to = edge[1];

            adjList.get(from).add(to);

            adjList.get(to).add(from);

        }

        // Remove leaf nodes

        List<Integer> leaves = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            if (adjList.get(i).size() == 1) {

                leaves.add(i);

            }

        }

        while (n > 2) {

            // identify and remove all leaf nodes

            n -= leaves.size();

            List<Integer> newLeaves = new ArrayList<>();

            for (int leaf : leaves) {

                int neighbor = adjList.get(leaf).iterator().next();

                adjList.get(neighbor).remove(leaf);

                 

                if (adjList.get(neighbor).size() == 1) {

                    newLeaves.add(neighbor);

                }

            }

             

            leaves = newLeaves;

        }

         

        return leaves;

    }
http://www.cnblogs.com/grandyang/p/5000291.html
这道题虽然是树的题目，但是跟其最接近的题目是Course Schedule 课程清单和Course Schedule II 课程清单之二。
由于LeetCode中的树的题目主要都是针对于二叉树的，而这道题虽说是树但其实本质是想考察图的知识，这道题刚开始在拿到的时候，
我最先想到的解法是遍历的点，以每个点都当做根节点，算出高度，然后找出最小的，但是一时半会又写不出程序来，于是上网看看大家的解法，
发现大家推崇的方法是一个类似剥洋葱的方法，就是一层一层的褪去叶节点，最后剩下的一个或两个节点就是我们要求的最小高度树的根节点，
这种思路非常的巧妙，而且实现起来也不难，跟之前那到课程清单的题一样，我们需要建立一个图g，是一个二维数组，
其中g[i]是一个一维数组，保存了i节点可以到达的所有节点。还需要一个一维数组d用来保存各个节点的入度信息，
其中d[i]表示i节点的入度数。我们的目标是删除所有的叶节点，叶节点的入度为1，所以我们开始将所有入度为1的节点(叶节点)
都存入到一个队列queue中，然后我们遍历每一个叶节点，通过图来找到和其相连的节点，将该节点的入度减1，
如果该节点的入度减到1了，说明此节点也也变成一个叶节点了，加入队列中，再下一轮删除。那么我们删到什么时候呢，
当节点数小于等于2时候停止，此时剩下的一个或两个节点就是我们要求的最小高度树的根节点啦
    vector<int> findMinHeightTrees(int n, vector<pair<int, int> >& edges) {
        if (n == 1) return {0};
        vector<int> res, d(n, 0);
        vector<vector<int> > g(n, vector<int>());
        queue<int> q;
        for (auto a : edges) {
            g[a.first].push_back(a.second);
            ++d[a.first];
            g[a.second].push_back(a.first);
            ++d[a.second];
        }
        for (int i = 0; i < n; ++i) {
            if (d[i] == 1) q.push(i);
        }
        while (n > 2) {
            int sz = q.size();
            for (int i = 0; i < sz; ++i) {
                int t = q.front(); q.pop();
                --n;
                for (int i : g[t]) {
                    --d[i];
                    if (d[i] == 1) q.push(i);
                }
            }
        }
        while (!q.empty()) {
            res.push_back(q.front()); q.pop();
        }
        return res;
    }

基本思路是“逐层删去叶子节点，直到剩下根节点为止”

有点类似于拓扑排序

最终剩下的节点个数可能为1或者2

时间复杂度：O(n)，其中n为顶点的个数。
答案一定是最长距离的中间结点位置上。
我们要的是中间结点，沿着树的外围每次把叶子结点砍掉，那么，最后剩下的不就是中间结点了么？

    def findMinHeightTrees(self, n, edges):

        """

        :type n: int

        :type edges: List[List[int]]

        :rtype: List[int]

        """

        if n==1: return [0]


        digree = [0 for i in xrange(n)]

        g = [[] for i in xrange(n)]

        for x,y in edges:

            digree[x] += 1

            digree[y] += 1

            g[x].append(y) #add_edge

            g[y].append(x)


        leaves = [i for i in xrange(n) if digree[i]==1]

        nodes = n

        while nodes > 2:

            temp = []

            for i in leaves:

                digree[i] = 0

                nodes -= 1

                for j in g[i]:

                    digree[j] -= 1

                    if digree[j] == 1:

                        temp.append(j)

            leaves = temp

        return leaves
http://bookshadow.com/weblog/2015/11/26/leetcode-minimum-height-trees/
    def findMinHeightTrees(self, n, edges):
        """
        :type n: int
        :type edges: List[List[int]]
        :rtype: List[int]
        """
        children = collections.defaultdict(set)
        for e in edges:
            children[e[0]].add(e[1])
            children[e[1]].add(e[0])
        vertices = set(children.keys())
        while len(vertices) > 2:
            leaves = [x for x in children if len(children[x]) == 1] // previous solution better
            for x in leaves:
                for y in children[x]:
                    children[y].remove(x)
                del children[x]
                vertices.remove(x)
        return list(vertices) if n != 1 else [0]
https://leetcode.com/discuss/71676/share-my-accepted-solution-java-o-n-time-o-n-space
https://leetcode.com/discuss/71692/java-and-python-solution-o-n-time-o-n-space
It's easy to know there are most two nodes to return, which are the medium nodes in the longest path in the tree. level travels, remove the leafs level by level
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    //check for the single node situation
    List<Integer> queue = new LinkedList<Integer>();
    if (n < 2) {
        queue.add(0);
        return queue;
    }
    //initiate map, indegree, and queue
    int count = n;
    int[] indegree = new int[n];
    Map<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>();
    for (int i = 0; i < count; i++) {
        map.put(i, new HashSet<Integer>());
    }
    for (int[] edge: edges) {
        map.get(edge[0]).add(edge[1]);
        map.get(edge[1]).add(edge[0]);
        indegree[edge[0]]++;
        indegree[edge[1]]++;
    }
    for (int i = 0; i < count; i++){
        if (indegree[i] == 1)
            queue.add(i);
    }
    //check from the outer layer to inner layer, stop when count == 1 or 2
    //which means we arrive at the center
    while (count > 2) {
        List<Integer> newqueue = new LinkedList<Integer>();
        for (int cur: queue) {
            count--;
            for (int next: map.get(cur)) {
                map.get(next).remove(cur);
                if (--indegree[next] == 1)
                    newqueue.add(next);
            }
        }
        queue = newqueue;
    }
    return queue;

}
https://leetcode.com/discuss/71738/easiest-75-ms-java-solution
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    List<Integer> leaves = new ArrayList<>(); 
    if(n <= 1) {leaves.add(0); return leaves;}

    // Construct adjencent graph
    Map<Integer, Set<Integer>> graph = new HashMap<>();   // use list<set<>>
    for(int i = 0; i < n; i++) graph.put(i, new HashSet<Integer>());
    for(int[] e : edges) {
        graph.get(e[0]).add(e[1]);
        graph.get(e[1]).add(e[0]);
    }

    // Add leaves which have one leaf
    for(int i = 0; i < n; i++) {
        if(graph.get(i).size() == 1) leaves.add(i);
    }

    // Remove leaves level by level
    while(n > 2) {
        List<Integer> newLeaves = new ArrayList<>();
        for(int leaf : leaves) {
            for(int nb : graph.get(leaf)) {
                // Remove connection
                graph.get(leaf).remove(nb);
                graph.get(nb).remove(leaf);
                n--;
                if(graph.get(nb).size() == 1) {
                    newLeaves.add(nb);
                }
            }
        }
        leaves = newLeaves;
    }
    return leaves;
}
X. TODO
https://discuss.leetcode.com/topic/30956/two-o-n-solutions
Longest Path
It is easy to see that the root of an MHT has to be the middle point (or two middle points) of the longest path of the tree. Though multiple longest paths can appear in an unrooted tree, they must share the same middle point(s).
Computing the longest path of a unrooted tree can be done, in O(n) time, by tree dp, or simply 2 tree traversals (dfs or bfs). The following is some thought of the latter.
Randomly select a node x as the root, do a dfs/bfs to find the node y that has the longest distance from x. Then y must be one of the endpoints on some longest path. Let y the new root, and do another dfs/bfs. Find the node z that has the longest distance from y.
Now, the path from y to z is the longest one, and thus its middle point(s) is the answer.
https://github.com/lydxlx1/LeetCode/blob/master/src/_310.java
int n;
List<Integer>[] e;

private void bfs(int start, int[] dist, int[] pre) {
    boolean[] visited = new boolean[n];
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(start);
    dist[start] = 0;
    visited[start] = true;
    pre[start] = -1;
    while (!queue.isEmpty()) {
        int u = queue.poll();
        for (int v : e[u])
            if (!visited[v]) {
                visited[v] = true;
                dist[v] = dist[u] + 1;
                queue.add(v);
                pre[v] = u;
            }
    }
}

public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n <= 0) return new ArrayList<>();
    this.n = n;
    e = new List[n];
    for (int i = 0; i < n; i++)
        e[i] = new ArrayList<>();
    for (int[] pair : edges) {
        int u = pair[0];
        int v = pair[1];
        e[u].add(v);
        e[v].add(u);
    }

    int[] d1 = new int[n];
    int[] d2 = new int[n];
    int[] pre = new int[n];
    bfs(0, d1, pre);
    int u = 0;
    for (int i = 0; i < n; i++)
        if (d1[i] > d1[u]) u = i;

    bfs(u, d2, pre);
    int v = 0;
    for (int i = 0; i < n; i++)
        if (d2[i] > d2[v]) v = i;

    List<Integer> list = new ArrayList<>();
    while (v != -1) {
        list.add(v);
        v = pre[v];
    }

    if (list.size() % 2 == 1) return Arrays.asList(list.get(list.size() / 2));
    else return Arrays.asList(list.get(list.size() / 2 - 1), list.get(list.size() / 2));
}

Tree DP
Alternatively, one can solve this problem directly by tree dp. Let dp[i] be the height of the tree when the tree root is i. We compute dp[0] ... dp[n - 1] by tree dp in a dfs manner.
Arbitrarily pick a node, say node 0, as the root, and do a dfs. When we reach a node u, and let T be the subtree by removing all u's descendant (see the right figure below). We maintain a variable acc that keeps track of the length of the longest path in T with one endpoint being u. Then dp[u] = max(height[u], acc) Note, acc is 0 for the root of the tree.
             |                 |
             .                 .
            /|\               /|\
           * u *             * u *
            /|\
           / | \
          *  v  *
. denotes a single node, and * denotes a subtree (possibly empty).
Now it remains to calculate the new acc for any of u's child, v. It is easy to see that the new acc is the max of the following
acc + 1 --- extend the previous path by edge uv;
max(height[v'] + 2), where v != v' --- see below for an example.
         u
        /|
       / |
      v' v
      |
      .
      .
      .
      |
      .
In fact, the second case can be computed in O(1) time instead of spending a time proportional to the degree of u. Otherwise, the runtime can be quadratic when the degree of some node is Omega(n). The trick here is to maintain two heights of each node, the largest height (the conventional height), and the second largest height (the height of the node after removing the branch w.r.t. the largest height).
Therefore, after the dfs, all dp[i]'s are computed, and the problem can be answered trivially. 
int n;
List<Integer>[] e;
int[] height1;
int[] height2;
int[] dp;

private void dfs(int u, int parent) {
    height1[u] = height2[u] = -Integer.MIN_VALUE / 10;
    for (int v : e[u])
        if (v != parent) {
            dfs(v, u);
            int tmp = height1[v] + 1;
            if (tmp > height1[u]) {
                height2[u] = height1[u];
                height1[u] = tmp;
            } else if (tmp > height2[u]) {
                height2[u] = tmp;
            }
        }
    height1[u] = Math.max(height1[u], 0); // in case u is a leaf.
}

private void dfs(int u, int parent, int acc) {
    dp[u] = Math.max(height1[u], acc);
    for (int v : e[u])
        if (v != parent) {
            int newAcc = Math.max(acc + 1, (height1[v] + 1 == height1[u] ? height2[u] : height1[u]) + 1);
            dfs(v, u, newAcc);
        }
}

public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n <= 0) return new ArrayList<>();
    if (n == 1) return Arrays.asList(0);

    this.n = n;
    e = new List[n];
    for (int i = 0; i < n; i++)
        e[i] = new ArrayList<>();
    for (int[] pair : edges) {
        int u = pair[0];
        int v = pair[1];
        e[u].add(v);
        e[v].add(u);
    }

    height1 = new int[n];
    height2 = new int[n];
    dp = new int[n];

    dfs(0, -1);
    dfs(0, -1, 0);

    int min = dp[0];
    for (int i : dp)
        if (i < min) min = i;

    List<Integer> ans = new ArrayList<>();
    for (int i = 0; i < n; i++)
        if (dp[i] == min) ans.add(i);
    return ans;
}
https://apolloydy.wordpress.com/2015/11/26/minimum-height-trees/
1 Find the longest path in the tree, start from node T1 to node T2; (first bfs find T1 and the second bfs find T2 and remember the parent path. O(n))

2 Find the middle node/nodes for T2 to T1 (Get from parent path, need to take care of odd even cases, O(n))


    vector<int> findMinHeightTrees(int n, vector<pair<int, int>>& edges) {

        vector<int> res;

        if (n<=0) {

            return res;

        }

        if (n==1) {

            res.push_back(0);

            return res;

        }

        vector<unordered_set<int>> G(n);

        for (const pair<int,int> &edge: edges) {

            G[edge.first].insert(edge.second);

            G[edge.second].insert(edge.first);

        }

        queue<int> Q;

        for (int i=0; i<n; ++i) {

            // n>0 all tree node must have a degree >= 1;

            if (G[i].size() ==1) {

                Q.push(i);

            }

        }

        int remain = n;

        while (remain>2) {

            int size = Q.size();

            remain -= size;

            for (int j = 0; j<size; ++j) {

                // Q should never be zero for a valid tree input;

                int cur = Q.front();

                Q.pop();

                for (const int &next : G[cur]) {

                    G[next].erase(cur);

                    if (G[next].size() == 1) {

                        Q.push(next);

                    }

                }

            }

        }

        while (!Q.empty()) {

            res.push_back(Q.front());

            Q.pop();

        }

        return res;

    }
附上一开始TLE的版本
思路就是类似于RIP算法，通过相邻的结点更新当前结点距离。
比如说有x ,y 一条边（以x=>y举例，y=>x同样进行更新）
我们可以通过y到各个结点的距离dis[y]来更新，就是说，让x走y这条边，然后到其他结点的距离(比如i)是不是比当前不走y到i的距离小。
于是有：dis[x][i] = dis[i][x] = min(dis[i][x], dis[i][y] + 1)  (无向图对称性：dis[i][y] = dis[y][i] , dis[x][i] = dis[i][x])
最后找最小的即可。
总复杂度：O(n^2)

    def findMinHeightTrees(self, n, edges):

        INF = 0x7ffffffe

        dis = [[INF for j in xrange(n)] for i in xrange(n)]

        for i in xrange(n): dis[i][i] = 0

        g = [[0 for j in xrange(n)] for i in xrange(n)]

        for x,y in edges:

            g[x][y] = g[y][x] = 1


        for x,y in edges:

            dis[x][y] = dis[y][x] = 1

            for i in xrange(n):

                dis[y][i] = dis[i][y] = min(dis[i][y], dis[i][x] + 1)

                dis[x][i] = dis[i][x] = min(dis[i][x], dis[i][y] + 1)


        res  = []

        min_dis = INF

        for i in xrange(n):

            temp = max(dis[i])

            if temp < min_dis:

                res = []

                res.append(i)

                min_dis = temp

            elif temp == min_dis:

                res.append(i)

        return res
http://buttercola.blogspot.com/2016/01/leetcode-minimum-height-trees.html
A brute-force solution is we can construct the graph first, then for each vertex as a root of the tree, we calculate the height, and then compare the height with the minimum height we got so far. Update the minimum if necessary. Since getting the height of a tree takes O(n) time, and we need to traverse each vertex of the graph, the total time complexity is O(n^2). 

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {

        List<Integer> result = new ArrayList<>();

        if (n <= 0 || edges == null || edges.length == 0) {

            return result;

        }

         

        // Step 1: construct the adjList

        Map<Integer, List<Integer>> adjList = new HashMap<>();

         

        for (int[] edge : edges) {

            // add forward edge

            int from = edge[0];

            int to = edge[1];

             

            if (!adjList.containsKey(from)) {

                List<Integer> neighbors = new ArrayList<>();

                neighbors.add(to);

                adjList.put(from, neighbors); 

            } else {

                List<Integer> neighbors = adjList.get(from);

                neighbors.add(to);

                adjList.put(from, neighbors);

            }

             

            // Add the reverse edge

            if (!adjList.containsKey(to)) {

                List<Integer> neighbors = new ArrayList<>();

                neighbors.add(from);

                adjList.put(to, neighbors);

            } else {

                List<Integer> neighbors = adjList.get(to);

                neighbors.add(from);

                adjList.put(to, neighbors);

            }

        }

         

        // Step 2: iterate each vertex as the root and get the height

        boolean[] visited = new boolean[n];

        int minHeight = Integer.MAX_VALUE;

         

        for (int i = 0; i < n; i++) {

            int height = getHeightOfTree(i, adjList, visited);

            if (height < minHeight) {

                result.clear();

                result.add(i);

                minHeight = height;

            } else if (height == minHeight) {

                result.add(i);

            }

        }

         

        return result;

    }

     

    private int getHeightOfTree(int root, Map<Integer, List<Integer>> adjList, 

                                boolean[] visited) {

        List<Integer> neighbors = adjList.get(root);

        visited[root] = true;

         

        int maxHeight = 0;

         

        for (Integer neighbor : neighbors) {

            if (!visited[neighbor]) {

                maxHeight = Math.max(maxHeight, 

                  getHeightOfTree(neighbor, adjList, visited));

            }

        }

         

        visited[root] = false;

         

        return maxHeight + 1;

    }
Read full article from leetcode Minimum Height Trees - 细语呢喃
 * @author het
 *
 */
public class LeetCode310 {
	static class UF {

	    private int[] parent;  // parent[i] = parent of i
	    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
	    private int count;     // number of components

	    /**
	     * Initializes an empty union-find data structure with <tt>N</tt> sites
	     * <tt>0</tt> through <tt>N-1</tt>. Each site is initially in its own 
	     * component.
	     *
	     * @param  N the number of sites
	     * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
	     */
	    public UF(int N) {
	        if (N < 0) throw new IllegalArgumentException();
	        count = N;
	        parent = new int[N];
	        rank = new byte[N];
	        for (int i = 0; i < N; i++) {
	            parent[i] = i;
	            rank[i] = 0;
	        }
	    }

	    /**
	     * Returns the component identifier for the component containing site <tt>p</tt>.
	     *
	     * @param  p the integer representing one site
	     * @return the component identifier for the component containing site <tt>p</tt>
	     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
	     */
	    public int find(int p) {
	        validate(p);
	        while (p != parent[p]) {
	            parent[p] = parent[parent[p]];    // path compression by halving
	            p = parent[p];
	        }
	        return p;
	    }

	    /**
	     * Returns the number of components.
	     *
	     * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
	     */
	    public int count() {
	        return count;
	    }
	  
	    /**
	     * Returns true if the the two sites are in the same component.
	     *
	     * @param  p the integer representing one site
	     * @param  q the integer representing the other site
	     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
	     *         <tt>false</tt> otherwise
	     * @throws IndexOutOfBoundsException unless
	     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
	     */
	    public boolean connected(int p, int q) {
	        return find(p) == find(q);
	    }
	  
	    /**
	     * Merges the component containing site <tt>p</tt> with the 
	     * the component containing site <tt>q</tt>.
	     *
	     * @param  p the integer representing one site
	     * @param  q the integer representing the other site
	     * @throws IndexOutOfBoundsException unless
	     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
	     */
	    public int[] union(int p, int q) {
	        int rootP = find(p);
	        int rootQ = find(q);
	        if (rootP == rootQ)  throw new RuntimeException();
	        count--;
	        // make root of smaller rank point to root of larger rank
	        if      (rank[rootP] < rank[rootQ]) {
	        	parent[rootP] = rootQ;
	        	return new int[]{rootQ};
	        }
	        else if (rank[rootP] > rank[rootQ]) {
	        	parent[rootQ] = rootP;
	        	return new int[]{rootP};
	        }
	        else {
	            parent[rootQ] = rootP;
	            rank[rootP]++;
	            return new int[]{rootP, rootQ};
	        }
	        
			
	    }

	    // validate that p is a valid index
	    private void validate(int p) {
	        int N = parent.length;
	        if (p < 0 || p >= N) {
	            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
	        }
	    }
	    
	    public static int[]  mht(int n, int [][] edges){
	    	UF uf = new UF(n);
	    	int [] result = null ;
	    	for(int [] edge: edges){
	    		result = uf.union(edge[0], edge[1]);
	    	}
			return result;
	    }
	    
	    
	    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
	        if (n == 1) return Collections.singletonList(0);

	        List<Set<Integer>> adj = new ArrayList<>(n);
	        for (int i = 0; i < n; ++i) adj.add(new HashSet<Integer>());
	        for (int[] edge : edges) {
	            adj.get(edge[0]).add(edge[1]);
	            adj.get(edge[1]).add(edge[0]);
	        }

	        List<Integer> leaves = new ArrayList<>();
	        for (int i = 0; i < n; ++i)
	            if (adj.get(i).size() == 1) leaves.add(i);

	        while (n > 2) {
	            n -= leaves.size();
	            List<Integer> newLeaves = new ArrayList<>();
	            for (int i : leaves) {
	                int j = adj.get(i).iterator().next();
	                adj.get(j).remove(i);
	                if (adj.get(j).size() == 1) newLeaves.add(j);
	            }
	            leaves = newLeaves;
	        }
	        return leaves;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]
//		        0
//		        |
//		        1
//		       / \
//		      2   3
//		return [1]
//		Example 2:
//		Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
		List<Integer> result = findMinHeightTrees(6, new int[][]{{0, 3},{1, 3}, {2, 3}, {4, 3}, {5, 4}});
		
		System.out.println(result);
		UF uf = new UF(4);
		uf.union(0, 3);
	}
	}
}
