package alite.leetcode.xx3;
/**
 * LeetCode 323 - Number of Connected Components in an Undirected Graph

http://segmentfault.com/a/1190000004224298
Related: Lintcode 431 - Find the Connected Component in the Undirected Graph
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 write a function to find the number of connected components in an undirected graph.
Example 1:
    0          3
    |          |
    1 --- 2    4
Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
Example 2:
    0           4
    |           |
    1 --- 2 --- 3
Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
http://www.cnblogs.com/EdwardLiu/p/5088502.html
 2     public int countComponents(int n, int[][] edges) {
 3         unionFind uf = new unionFind(n);
 4         for (int[] edge : edges) {
 5             if (!uf.isConnected(edge[0], edge[1])) {
 6                 uf.union(edge[0], edge[1]);
 7             }
 8         }
 9         return uf.findCount();
10     }
11     
12     public class unionFind{
13             int[] ids;
14             int count;
15             
16             public unionFind(int num) {
17                 this.ids = new int[num];
18                 for (int i=0; i<num; i++) {
19                     ids[i] = i;
20                 }
21                 this.count = num;
22             }
23             
24             public int find(int i) {
25                 return ids[i];
26             }
27             
28             public void union(int i1, int i2) {
29                 int id1 = find(i1);
30                 int id2 = find(i2);
31                 if (id1 != id2) {
32                     for (int i=0; i<ids.length; i++) {
33                         if (ids[i] == id2) {
34                             ids[i] = id1;
35                         }
36                     }
37                     count--;
38                 }
39             }
40             
41             public boolean isConnected(int i1, int i2) {
42                 return find(i1)==find(i2);
43             }
44             
45             public int findCount() {
46                 return count;
47             }
48         }

典型且很基础的union find题。用一个数组记录各个数字的父节点，然后遍历图，对edge中两个端点做union。最后扫一遍数组，找到根节点个数即可。
time: O(m*h), space: O(n), m表示edge的数量。
    public int countComponents(int n, int[][] edges) {
        int[] id = new int[n];
        
        // 初始化
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        
        // union
        for (int[] edge : edges) {              
            int i = root(id, edge[0]);
            int j = root(id, edge[1]);
            id[i] = j;
        }
        
        // 统计根节点个数
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (id[i] == i)
                count++;
        }
        return count;
    }
    
    // 找根节点
    public int root(int[] id, int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
https://leetcode.com/discuss/76519/similar-to-number-of-islands-ii-with-a-findroot-function
public int countComponents(int n, int[][] edges) {
    int res = n;

    int[] root = new int[n];
    for (int i = 0; i < n; i++) {
        root[i] = i;
    }
    for (int[] pair : edges) {
        int rootX = findRoot(root, pair[0]);
        int rootY = findRoot(root, pair[1]);
        if (rootX != rootY) {
            root[rootY] = rootX;
            res--;
        }
    }
    return res;
}
public int findRoot(int[] root, int i) {
    while (root[i] != i) i = root[i];
    return i;
}
https://leetcode.com/discuss/93661/java-union-find-%26-dfs-%26-bfs-code-very-clean
    public int countComponents(int n, int[][] edges) {
        if (n <= 1) {
            return n;
        }
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }
        for (int[] edge : edges) {
            int x = find(roots, edge[0]);
            int y = find(roots, edge[1]);
            if (x != y) {
                roots[x] = y;
                n--;
            }
        }
        return n;
    }
    public int find(int[] roots, int id) {
        int x = id;
        while (roots[id] != id) {
            id = roots[id];
        }
        while (roots[x] != id) {
            int fa = roots[x];
            roots[x] = id;
            x = fa;
        }
        return id;
    }

With path compression
https://leetcode.com/discuss/76699/java-union-find-%26-path-compression-with-brief-comments
https://leetcode.com/discuss/78217/easy-java-solution-explanation-beats-submissions-union-find
public int countComponents(int n, int[][] edges) {
    if(n<1) return 0;
    int[] size = new int[n]; // size of components at every node
    int[] root = new int[n]; // root of current node
    for(int i=0; i<n; i++) size[i] = 1; // initialize size to 1
    for(int i=0; i<n; i++) root[i] = i; // initialize root to itself
    for(int[] edge : edges) union(edge[0], edge[1], root, size); // union all edges

    int count = 0;
    for(int i=0; i<n; i++) count += (root[i]==i) ? 1 : 0;

    return count;
}

private int root(int a, int[] root) {
    while(a!=root[a]) a = root(root[a], root); // path compression
    return a;
}

private void union(int a, int b, int[] root, int[] size) {
    int roota = root(a, root);
    int rootb = root(b, root);
    if(roota==rootb) return; // (a,b) already in same set, return immediately
    if(size[roota]>size[rootb]) {
        size[roota] += size[rootb]; // set larger size to sum
        root[rootb] = roota; // set smaller node to larger node's child
    }
    else {
        size[rootb] += size[roota];
        root[roota] = rootb;
    }
}
http://buttercola.blogspot.com/2016/01/leetcode-number-of-connected-components.html

private int[] father;

public int countComponents(int n, int[][] edges) {


    Set<Integer> set = new HashSet<Integer>();

    father = new int[n];

    for (int i = 0; i < n; i++) {

        father[i] = i;

    }

    for (int i = 0; i < edges.length; i++) {

         union(edges[i][0], edges[i][1]);

    }


    for (int i = 0; i < n; i++){ 

        set.add(find(i));

    }

    return set.size();

}


int find(int node) {

    if (father[node] == node) {

        return node;

    }

    father[node] = find(father[node]);

    return father[node];

}


void union(int node1, int node2) {

    father[find(node1)] = find(node2);

}
X. BFS
https://leetcode.com/discuss/93661/java-union-find-%26-dfs-%26-bfs-code-very-clean
    public int countComponents(int n, int[][] edges) {
        if (n <= 1) {
            return n;
        }
        List<List<Integer>> adjList = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<Integer>());
        }
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                Queue<Integer> queue = new LinkedList<Integer>();
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int index = queue.poll();
                    visited[index] = true;
                    for (int next : adjList.get(index)) {
                        if (!visited[next]) {
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        return count;
    }
https://leetcode.com/discuss/85395/standard-bfs-and-dfs-solution-java
public int countComponents(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i=0;i<n;i++){
        graph.add(new ArrayList<>());
    }
    for(int[] item : edges){
        graph.get(item[1]).add(item[0]);
        graph.get(item[0]).add(item[1]);
    }// done with building graph

    HashSet<Integer> visited = new HashSet<>();
    int count = 0;
    for(int i=0;i<n;i++){
        if(!visited.contains(i)){
            count++;
            // dfs(i,graph,visited);  
            bfs(graph,i,visited);
        }
    }
    return count;
}

public void bfs(List<List<Integer>> graph, int i, HashSet<Integer> visited){
    Queue<Integer> q = new LinkedList<>();
    q.offer(i);
    visited.add(i);
    while(!q.isEmpty()){
        int curr = q.poll();
        for(int neighbor : graph.get(curr)){
            if(!visited.contains(neighbor)){
                q.offer(neighbor);
                visited.add(neighbor);
            }
        }
    }
}
public void dfs(int i, List<List<Integer>> graph, HashSet<Integer> visited){
    visited.add(i);
    for(int num : graph.get(i)){
        if(!visited.contains(num)){
            dfs(num,graph,visited);
        }
    }
}
DFS:
https://leetcode.com/discuss/76608/concise-java-dfs-solution
public int countComponents(int n, int[][] edges) {

    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<Integer>());
    }

    for (int i = 0; i < edges.length; i++) {
        graph.get(edges[i][0]).add(edges[i][1]);
        graph.get(edges[i][1]).add(edges[i][0]);
    }

    boolean[] visit = new boolean[n];
    Arrays.fill(visit, false);
    int count = 0;

    for (int i = 0; i < n; i++) {
        if (!visit[i]) {
            bfs(i, graph, visit);
            count++;
        }
    }
    return count;
}
private void bfs(int i, Map<Integer, List<Integer>> graph, boolean[] visit) {
    if (visit[i]) {
        return;
    }
    visit[i] = true;
    List<Integer> neighbors = graph.get(i);
    for (Integer integer: neighbors) {
        bfs((int)integer, graph, visit);
    }
}
https://leetcode.com/discuss/93661/java-union-find-%26-dfs-%26-bfs-code-very-clean
-- previous solution is better
    public int countComponents(int n, int[][] edges) {
        if (n <= 1) {
            return n;
        }
        List<List<Integer>> adjList = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<Integer>());
        }
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfs(visited, i, adjList);
            }
        }

        return count;
    }

    public void dfs(boolean[] visited, int index, List<List<Integer>> adjList) {
        visited[index] = true;
        for (int i : adjList.get(index)) {
            if (!visited[i]) {
                dfs(visited, i, adjList);
            }
        }
    }
https://leetcode.com/discuss/80671/java-concise-dfs
    public int countComponents(int n, int[][] edges) {
        if (n <= 1)
            return n;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        Set<Integer> visited = new HashSet<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (visited.add(i)) {
                dfsVisit(i, map, visited);
                count++;
            }
        }
        return count;
    }
    private void dfsVisit(int i, Map<Integer, List<Integer>> map, Set<Integer> visited) {
        for (int j : map.get(i)) {
            if (visited.add(j))
                dfsVisit(j, map, visited);
        }
    }
https://discuss.leetcode.com/topic/24/friend-circles/
There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature,
 i.e., if A is friend of B and B is friend of C, then A is also friend of C. 
 A friend circle is a group of students who are directly or indirectly friends.
You are given a N×N − matrix M which consists of characters Y or N. If M[i][j]=Y, 
then ith and jth students are friends with each other, otherwise not.
 You have to print the total number of friend circles in the class.
Each element of matrix friends will be Y or N.
Number of rows and columns will be equal in the matrix.
M[i][i]=Y, where 0≤i<N0≤i<N
M[i][j] = M[j][i], where 0≤i<j<N
Example 1
Input:
YYNN
YYYN
NYYN
NNNY
Output: 2
Example 2
Input
YNNNN
NYNNN
NNYNN
NNNYN
NNNNY
Output: 5
Since M[i][j] = M[j][i], it is an undirected graph. Each friend circle can be represented as one connected component in an undirected graph, which translates directly to this problem:
Find the Number of Connected Components in an Undirected Graph
The only difference is in the input format. This is given as a 2D boolean matrix, while the above problem's input is in edge-pairs representation
http://buttercola.blogspot.com/2016/01/leetcode-number-of-connected-components.html
 * @author het
 *
 */
public class LeetCode323 {
	public int countComponents(int n, int[][] edges) {

	    Map<Integer, List<Integer>> graph = new HashMap<>();
	    for (int i = 0; i < n; i++) {
	        graph.put(i, new ArrayList<Integer>());
	    }

	    for (int i = 0; i < edges.length; i++) {
	        graph.get(edges[i][0]).add(edges[i][1]);
	        graph.get(edges[i][1]).add(edges[i][0]);
	    }

	    boolean[] visit = new boolean[n];
	    Arrays.fill(visit, false);
	    int count = 0;

	    for (int i = 0; i < n; i++) {
	        if (!visit[i]) {
	            bfs(i, graph, visit);
	            count++;
	        }
	    }
	    return count;
	}
	private void bfs(int i, Map<Integer, List<Integer>> graph, boolean[] visit) {
	    if (visit[i]) {
	        return;
	    }
	    visit[i] = true;
	    List<Integer> neighbors = graph.get(i);
	    for (Integer integer: neighbors) {
	        bfs((int)integer, graph, visit);
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
