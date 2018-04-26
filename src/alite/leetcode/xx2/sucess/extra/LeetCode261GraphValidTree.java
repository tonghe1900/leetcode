package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n nodes labeled from 0 to n – 1 and a list of undirected edges (each edge is a pair of nodes), write a function 
 * to check whether these edges make up a valid tree.
For example:
Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
Hint:
Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], what should your return? Is this case a valid tree?
According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
鉴定一个图是否是一个有效的树，既没有回路。
图类的题目基本上都可以用DFS和BFS来解决，这题也不例外。同时，Union and Find- 并查集也可以很好的解决这个问题。
1. DFS
由于是无向图，所以根据给定的edges我们需要造一个双向的图，用List<List<Integer>>即可实现，同时维护一个已访问数组。在深度遍历图的时候，如果当前节点已访问过，则表示存在回路。

public boolean validTree(int n, int[][] edges) {

        // initialize adjacency list

        List<List<Integer>> adjList = new ArrayList<List<Integer>>(n);


        // initialize vertices

        for (int i = 0; i < n; i++)

            adjList.add(i, new ArrayList<Integer>());


        // add edges    

        for (int i = 0; i < edges.length; i++) {

            int u = edges[i][0], v = edges[i][1];

            adjList.get(u).add(v);

            adjList.get(v).add(u);

        }


        boolean[] visited = new boolean[n];


        // make sure there's no cycle

        if (hasCycle(adjList, 0, visited, -1))

            return false;


        // make sure all vertices are connected

        for (int i = 0; i < n; i++) {

            if (!visited[i]) 

                return false;

        }


        return true;

    }


    // check if an undirected graph has cycle started from vertex u

    boolean hasCycle(List<List<Integer>> adjList, int u, boolean[] visited, int parent) {

        visited[u] = true;


        for (int i = 0; i < adjList.get(u).size(); i++) {

            int v = adjList.get(u).get(i);

            //如果是临近的父节点，则跳过。

            if ((visited[v] && parent != v) || (!visited[v] && hasCycle(adjList, v, visited, u)))

                return true;

        }


        return false;

    }
http://buttercola.blogspot.com/2015/08/leetcode-graph-valid-tree.html

    public boolean validTree(int n, int[][] edges) {

         

        // Create an adj list 

        List<List<Integer>> adjList = new ArrayList<List<Integer>>();

        for (int i = 0; i < n; i++) {

            adjList.add(new ArrayList<Integer>());

        }

         

        for (int[] edge : edges) {

            adjList.get(edge[1]).add(edge[0]);

            adjList.get(edge[0]).add(edge[1]);

        }

         

        boolean[] visited = new boolean[n];

         

        if (!validTreeHelper(n, edges, 0, -1, visited, adjList)) {

            return false;

        }

         

        // Check the islands

        for (boolean v : visited) {

            if (!v) {

                return false;

            }

        }

         

        return true;

    }

     

    private boolean validTreeHelper(int n, int[][] edges, int vertexId, int parentId, 

                                    boolean[] visited, List<List<Integer>> adjList) {

        if (visited[vertexId]) {

            return false;

        }

         

        visited[vertexId] = true;

         

        List<Integer> neighbors = adjList.get(vertexId);

        for (Integer neighbor : neighbors) {

            if (neighbor != parentId && !validTreeHelper(n, edges, neighbor, vertexId, visited, adjList)) {

                return false;

            }

        }

         

        return true;

    }
X.BFS

    public boolean validTree(int n, int[][] edges) {

         

        // Create an adj list 

        List<List<Integer>> adjList = new ArrayList<List<Integer>>();

        for (int i = 0; i < n; i++) {

            adjList.add(new ArrayList<Integer>());

        }

         

        for (int[] edge : edges) {

            adjList.get(edge[1]).add(edge[0]);

            adjList.get(edge[0]).add(edge[1]);

        }

         

        boolean[] visited = new boolean[n];

         

        Queue<Integer> queue = new LinkedList<Integer>();

        queue.offer(0);

         

        while (!queue.isEmpty()) {

            int vertexId = queue.poll();

             

            if (visited[vertexId]) {

                return false;

            }

             

            visited[vertexId] = true;

             

            for (int neighbor : adjList.get(vertexId)) {

                if (!visited[neighbor]) {

                    queue.offer(neighbor);

                }

            }

        }

         

        // Check the islands

        for (boolean v : visited) {

            if (!v) {

                return false;

            }

        }

         

        return true;

    }
https://leetcode.com/discuss/54211/ac-java-solutions-union-find-bfs-dfs
    // BFS, using queue
    private boolean valid(int n, int[][] edges)
    {
        // build the graph using adjacent list
        List<Set<Integer>> graph = new ArrayList<Set<Integer>>();
        for(int i = 0; i < n; i++)
            graph.add(new HashSet<Integer>());
        for(int[] edge : edges)
        {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // no cycle
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0);
        while(!queue.isEmpty())
        {
            int node = queue.poll();
            if(visited[node])
                return false;
            visited[node] = true;
            for(int neighbor : graph.get(node))
            {
                queue.offer(neighbor);
                graph.get(neighbor).remove((Integer)node);
            }
        }

        // fully connected
        for(boolean result : visited)
        {
            if(!result)
                return false;
        }

        return true;
    }
In the BFS one, while loop can't not detect loop, try{ [1, 2] [2, 3] [1, 3]}:
boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0);
        while(!queue.isEmpty())
        {
            int node = queue.poll();
            if(visited[node])
                return false;
            visited[node] = true;
            for(int neighbor : graph.get(node))
            {
                queue.offer(neighbor);
                graph.get(neighbor).remove((Integer)node);
            }
        }
Should be:
boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0);
        while(!queue.isEmpty())
        {
            int node = queue.poll();
            for(int neighbor : graph.get(node))
            {
                if(visited[neighbor])
                    return false;
                visited[neighbor] = true;                
                queue.offer(neighbor);
                graph.get(neighbor).remove((Integer)node);
            }
        }
https://leetcode.com/discuss/52555/java-bfs-solution
    public boolean validTree(int n, int[][] edges) {
        // n must be at least 1
        if (n < 1) return false;

        // create hashmap to store info of edges
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(i, new HashSet<>());
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        // bfs starts with node in label "0"
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int top = queue.remove();
            // if set already contains top, then the graph has cycle
            // hence return false
            if (set.contains(top)) return false;

            for (int node : map.get(top)) {
                queue.add(node);
                // we should remove the edge: node -> top
                // after adding a node into set to avoid duplicate
                // since we already consider top -> node
                map.get(node).remove(top);
            }
            set.add(top);
        }
        return set.size() == n;
    }
X. 并查集 Union-Find
https://discuss.leetcode.com/topic/21712/ac-java-union-find-solution
let's say the graph has V vertices and E edges, the find( ) function takes O(V) time because in the worst case it has to go through all the vertices, and from outside we loop through all the edges, so the time complexity should be O(V*E).

https://segmentfault.com/a/1190000003791051
就是先构建一个数组，节点0到节点n-1，刚开始都各自独立的属于自己的集合。这时集合的编号是节点号。然后，每次union操作时，我们把整个并查集中，所有和第一个节点所属集合号相同的节点的集合号，都改成第二个节点的集合号。这样就将一个集合的节点归属到同一个集合号下了。我们遍历一遍输入，把所有边加入我们的并查集中，加的同时判断是否有环路。最后如果并查集中只有一个集合，则说明可以构建树。
    public boolean validTree(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for(int i = 0; i < edges.length; i++){
            // 如果两个节点已经在同一集合中，说明新的边将产生环路
            if(!uf.union(edges[i][0], edges[i][1])){
                return false;
            }
        }
        return uf.count() == 1;
    }
    
    public class UnionFind {
        
        int[] ids;
        int cnt;
        
        public UnionFind(int size){
            this.ids = new int[size];
            //初始化并查集，每个节点对应自己的集合号
            for(int i = 0; i < this.ids.length; i++){
                this.ids[i] = i;
            }
            this.cnt = size;
        }
        public boolean union(int m, int n){
            int src = find(m);
            int dst = find(n);
            //如果两个节点不在同一集合中，将两个集合合并为一个
            if(src != dst){
                for(int i = 0; i < ids.length; i++){
                    if(ids[i] == src){
                        ids[i] = dst;
                    }
                }
                // 合并完集合后，集合数减一
                cnt--;
                return true;
            } else {
                return false;
            }
        }
        public int find(int m){
            return ids[m];
        }
        public boolean areConnected(int m, int n){
            return find(m) == find(n);
        }
        public int count(){
            return cnt;
        }
    }

Union and Find。先附上一个很经典的图。 并查集可以很简单的查询到一个图的联通情况，或者返回最少路径数。

public boolean validTree(int n, int[][] edges) {

        // initialize n isolated islands

        int[] nums = new int[n];

        Arrays.fill(nums, -1);


        // perform union find

        for (int i = 0; i < edges.length; i++) {

            int x = find(nums, edges[i][0]);

            int y = find(nums, edges[i][1]);


            // if two vertices happen to be in the same set

            // then there's a cycle

            if (x == y) return false;


            // union

            nums[x] = y;

        }


        return edges.length == n - 1;

    }


    int find(int nums[], int i) {

        if (nums[i] == -1) return i;

        //反复寻找父节点。 

        return find(nums, nums[i]);

    }
http://likesky3.iteye.com/blog/2240154
    int count = 0;  
    int[] id = null;  
    int[] size = null;  
    public boolean validTree(int n, int[][] edges) {  
        initUnionFind(n);  
        for (int i = 0; i < edges.length; i++) {  
            int p = edges[i][0], q = edges[i][1];  
            if (find(p) == find(q))  
                return false;  
            union(p, q);  
        }  
        return count == 1 ? true : false;  
    }  
    public void initUnionFind(int n) {  
        id = new int[n];  
        size = new int[n];  
        for (int i = 0; i < n; i++) {  
            id[i] = i;  
            size[i] = 1;  
        }  
        count = n;  
    }  
    //version 4: weighted quick union with path compression, find & union, very close to O(1)  
    public int find(int p) {  
        while (p != id[p]) {  
            id[p] = id[id[p]];  
            p = id[p];  
        }  
        return p;  
    }  
    public void union(int p, int q) { //same with version 3  
        int pRoot = find(p), qRoot = find(q);  
        if (size[pRoot] < size[qRoot]) {  
            id[pRoot] = qRoot;  
            size[qRoot] += size[pRoot];  
        } else {  
            id[qRoot] = pRoot;  
            size[pRoot] += size[qRoot];  
        }  
        count--;  
    }  
    //version 3: weighted quick union, find & union, O(logn)  
    public int find3(int p) { // same with version 2  
        while (p != id[p]) p = id[p];  
        return p;  
    }  
    public void union3(int p, int q) {  
        int pRoot = find(p), qRoot = find(q);  
        if (size[pRoot] < size[qRoot]) {  
            id[pRoot] = qRoot;  
            size[qRoot] += size[pRoot];  
        } else {  
            id[qRoot] = pRoot;  
            size[pRoot] += size[qRoot];  
        }  
        count--;  
    }  
    // version 2: quick union, find & union, O(tree height)  
    public int find2(int p) {  
        while (p != id[p]) p = id[p];  
        return p;  
    }  
    public void union2(int p, int q) {  
        int pRoot = find(p), qRoot = find(q);  
        id[pRoot] = qRoot;  
        count--;  
    }  
    // version 1: quick find, find O(1), union O(n)  
    public int find1(int p) {  
        return id[p];  
    }  
    public void union1(int p, int q) {  
        int pId = find(p), qId = find(q);// 特别注意进入循环先保存原始值，循环过程id[p]会被更改  
        for (int i = 0; i < id.length; i++) {  
            if (id[i] == pId)  
                id[i] = qId;  
        }  
        count--;  
    } 
    // union by rank  
    public boolean union(int p, int q) {  
        int pRoot = find(p), qRoot = find(q);  
        if (pRoot == qRoot)  
            return false;  
        if (s[pRoot] < s[qRoot]) {  
            s[qRoot] = pRoot;  
        } else {  
            if (s[pRoot] == s[qRoot])  
                s[qRoot]--;  
            s[pRoot] = qRoot;  
        }  
        return true;  
    }  
    // union by size  
    public boolean union1(int p, int q) {  
        int pRoot = find(p), qRoot = find(q);  
        if (pRoot == qRoot)  
            return false;  
        if (s[pRoot] < s[qRoot]) {  
            s[pRoot] += s[qRoot];  
            s[qRoot] = pRoot;  
        } else {  
            s[qRoot] += s[pRoot];  
            s[pRoot] = qRoot;  
        }  
        return true;  
    } 
http://www.jiuzhang.com/solutions/graph-valid-tree/
      class UnionFind{
        HashMap<Integer, Integer> father = new HashMap<Integer, Integer>();
        UnionFind(int n){
            for(int i = 0 ; i < n; i++) {
                father.put(i, i); 
            }
        }
        int compressed_find(int x){
            int parent =  father.get(x);
            while(parent!=father.get(parent)) {
                parent = father.get(parent);
            }
            int temp = -1;
            int fa = father.get(x);
            while(fa!=father.get(fa)) {
                temp = father.get(fa);
                father.put(fa, parent) ;
                fa = temp;
            }
            return parent;
                
        }
        
        void union(int x, int y){
            int fa_x = compressed_find(x);
            int fa_y = compressed_find(y);
            if(fa_x != fa_y)
                father.put(fa_x, fa_y);
        }
    }
    public boolean validTree(int n, int[][] edges) {
        // tree should have n nodes with n-1 edges
        if (n - 1 != edges.length) {
            return false;
        }
        
        UnionFind uf = new UnionFind(n);
        
        for (int i = 0; i < edges.length; i++) {
            if (uf.compressed_find(edges[i][0]) == uf.compressed_find(edges[i][1])) {
                return false;
            }
            uf.union(edges[i][0], edges[i][1]);
        }
        return true;
    }

并查集可以解决的问题很多，比如：
Example1：
在某个城市里住着n个人，任何两个认识的人不是朋友就是敌人，而且满足：
n    我朋友的朋友是我的朋友；
n    我敌人的敌人是我的朋友；
已知关于 n个人的m条信息（即某2个人是朋友或者敌人），假设所有是朋友的人一定属于同一个团伙，请计算该城市最多有多少团伙？
         分析：要知道有多少个团伙，就要知道每个人属于哪个团伙？还有做到的是若A属于Team1同时也属于Team2那么就要合并Team1和Team2。这就是并查集的“并”和“查”了。显然天生就要用到并查集解决这个题了。

Example2：
某省调查城镇交通状况，得到现有城镇道路统计表，表中列出了每条道路直接连通的城镇。省政府“畅通工程”的目标是使全省任何两个城镇间都可以实现交通（但不一定有直接的道路相连，只要互相间接通过道路可达即可）。问最少还需要建设多少条道路？请给出一个可行的规划。
Input
测试输入包含若干测试用例。每个测试用例的第1行给出两个正整数，分别是城镇数目N ( < 1000 )和道路数目M；随后的M行对应M条道路，每行给出一对正整数，分别是该条道路直接连通的两个城镇的编号。为简单起见，城镇从1到N编号。
注意:两个城市之间可以有多条道路相通,也就是说
3 3
1 2
1 2
2 1
这种输入也是合法的
当N为0时，输入结束，该用例不被处理。
Output
对每个测试用例，在1行里输出最少还需要建设的道路数目。
 * @author het
 *
 */
public class LeetCode261GraphValidTree {
	 public boolean validTree(int n, int[][] edges) {

         

	        // Create an adj list 

	        List<List<Integer>> adjList = new ArrayList<List<Integer>>();

	        for (int i = 0; i < n; i++) {

	            adjList.add(new ArrayList<Integer>());

	        }

	         

	        for (int[] edge : edges) {

	            adjList.get(edge[1]).add(edge[0]);

	            adjList.get(edge[0]).add(edge[1]);

	        }

	         

	        boolean[] visited = new boolean[n];

	         

	        if (!validTreeHelper(n, edges, 0, -1, visited, adjList)) {

	            return false;

	        }

	         

	        // Check the islands

	        for (boolean v : visited) {

	            if (!v) {

	                return false;

	            }

	        }

	         

	        return true;

	    }

	     

	    private boolean validTreeHelper(int n, int[][] edges, int vertexId, int parentId, 

	                                    boolean[] visited, List<List<Integer>> adjList) {

	        if (visited[vertexId]) {

	            return false;

	        }

	         

	        visited[vertexId] = true;

	         

	        List<Integer> neighbors = adjList.get(vertexId);

	        for (Integer neighbor : neighbors) {

	            if (neighbor != parentId && !validTreeHelper(n, edges, neighbor, vertexId, visited, adjList)) {

	                return false;

	            }

	        }

	         

	        return true;

	    }
	    
	    // union find 
	    
	    public boolean validTree1(int n, int[][] edges) {
	        UnionFind uf = new UnionFind(n);
	        for(int i = 0; i < edges.length; i++){
	            // 如果两个节点已经在同一集合中，说明新的边将产生环路
	            if(!uf.union(edges[i][0], edges[i][1])){
	                return false;
	            }
	        }
	        return uf.count() == 1;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
