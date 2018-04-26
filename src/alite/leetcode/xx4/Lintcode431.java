package alite.leetcode.xx4;
/**
 * Lintcode 431 - Find the Connected Component in the Undirected Graph

http://algorithm.yuanbin.me/zh-cn/graph/find_the_connected_component_in_the_undirected_graph.html
Find the number connected component in the undirected graph. Each node in the graph contains a label and a list of its neighbors. (a connected component (or just component) of an undirected graph is a subgraph in which any two vertices are connected to each other by paths, and which is connected to no additional vertices in the supergraph.)
Example

Given graph:
A------B  C
 \     |  |
  \    |  |
   \   |  |
    \  |  |
      D   E
Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}
    public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes) {
        if (nodes == null || nodes.size() == 0) return null;

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Set<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
        for (UndirectedGraphNode node : nodes) {
            if (visited.contains(node)) continue;
            List<Integer> temp = new ArrayList<Integer>();
            dfs(node, visited, temp);
            Collections.sort(temp);
            result.add(temp);
        }

        return result;
    }

    private void dfs(UndirectedGraphNode node,
                     Set<UndirectedGraphNode> visited,
                     List<Integer> result) {

        // add node into result
        result.add(node.label);
        visited.add(node);
        // node is not connected, exclude by for iteration
        // if (node.neighbors.size() == 0 ) return;
        for (UndirectedGraphNode neighbor : node.neighbors) {
            if (visited.contains(neighbor)) continue;
            dfs(neighbor, visited, result);
        }
    }
}
遍历所有节点和边一次，时间复杂度 
O(V+E)
O(V+E), 记录节点是否被访问，空间复杂度 
O(V)
O(V).

深搜容易爆栈，采用 BFS 较为安全。BFS 中记录已经访问的节点在入队前判断，可有效防止不重不漏。
    public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes) {
        if (nodes == null || nodes.size() == 0) return null;

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // log visited node before push into queue
        Set<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
        for (UndirectedGraphNode node : nodes) {
            if (visited.contains(node)) continue;
            List<Integer> row = bfs(node, visited);
            result.add(row);
        }

        return result;
    }

    private List<Integer> bfs(UndirectedGraphNode node,
                              Set<UndirectedGraphNode> visited) {

        List<Integer> row = new ArrayList<Integer>();
        Queue<UndirectedGraphNode> q = new LinkedList<UndirectedGraphNode>();
        q.offer(node);
        visited.add(node);

        while (!q.isEmpty()) {
            UndirectedGraphNode qNode = q.poll();
            row.add(qNode.label);
            for (UndirectedGraphNode neighbor : qNode.neighbors) {
                if (visited.contains(neighbor)) continue;
                q.offer(neighbor);
                visited.add(neighbor);
            }
        }

        Collections.sort(row);
        return row;
    }
http://www.jiuzhang.com/solutions/find-the-connected-component-in-the-undirected-graph/

http://www.jiuzhang.com/solutions/find-the-connected-component-in-the-undirected-graph/
 * class UndirectedGraphNode {
 *     int label;
 *     ArrayList<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
    public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes) {
        // Write your code here
        
        int m = nodes.size();
        Map<UndirectedGraphNode, Boolean> visited = new HashMap<>();
        
       for (UndirectedGraphNode node : nodes){
            visited.put(node, false);
       }
        
        List<List<Integer>> result = new ArrayList<>();
        
        for (UndirectedGraphNode node : nodes){
            if (visited.get(node) == false){
                bfs(node, visited, result);
            }
        }
        
        return result;
    }
    
    public void bfs(UndirectedGraphNode node, Map<UndirectedGraphNode, Boolean> visited, List<List<Integer>> result){
        List<Integer>row = new ArrayList<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        visited.put(node, true);
        queue.offer(node);
        while (!queue.isEmpty()){
            UndirectedGraphNode u = queue.poll();
            row.add(u.label);    
            for (UndirectedGraphNode v : u.neighbors){
                if (visited.get(v) == false){
                    visited.put(v, true);
                    queue.offer(v);
                }
            }
        }
        Collections.sort(row);
        result.add(row);
        
    }
  class UnionFind{
    HashMap<Integer, Integer> father = new HashMap<Integer, Integer>();
    UnionFind(HashSet<Integer> hashSet){
      for(Integer now : hashSet) {
        father.put(now, now);
      }
    }
  int find(int x){
    int parent =  father.get(x);
    while(parent!=father.get(parent)) {
      parent = father.get(parent);
    }
    return parent;
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
    int fa_x = find(x);
    int fa_y = find(y);
    if(fa_x != fa_y)
      father.put(fa_x, fa_y);
  }
  }
  List<List<Integer> >  print(HashSet<Integer> hashSet, UnionFind uf, int n) {
    List<List <Integer> > ans = new ArrayList<List<Integer>>();
  HashMap<Integer, List <Integer>> hashMap = new HashMap<Integer, List <Integer>>();
  for(int i : hashSet){
    int fa = uf.find(i);
    if(!hashMap.containsKey(fa)) {
      hashMap.put(fa,  new ArrayList<Integer>() );
    }
    List <Integer> now =  hashMap.get(fa);
    now.add(i);
    hashMap.put(fa, now);
  }
  for( List <Integer> now: hashMap.values()) {
  Collections.sort(now);
    ans.add(now);
  }
    return ans;
  }
  
  public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes){
  // Write your code here
  
    HashSet<Integer> hashSet = new HashSet<Integer>(); 
    for(UndirectedGraphNode now : nodes){
      hashSet.add(now.label);
      for(UndirectedGraphNode neighbour : now.neighbors) {
        hashSet.add(neighbour.label);
      }
    }
    UnionFind uf = new UnionFind(hashSet);

  
    for(UndirectedGraphNode now : nodes){
      
      for(UndirectedGraphNode neighbour : now.neighbors) {
        int fnow = uf.find(now.label);
      int fneighbour = uf.find(neighbour.label);
      if(fnow!=fneighbour) {
        uf.union(now.label, neighbour.label);
      }
      }
    }
  
  
    return print(hashSet , uf, nodes.size());
  }
 * @author het
 *
 */
public class Lintcode431 {
	
	 public List<List<Integer>> connectedSet(ArrayList<UndirectedGraphNode> nodes) {
	        if (nodes == null || nodes.size() == 0) return null;

	        List<List<Integer>> result = new ArrayList<List<Integer>>();
	        Set<UndirectedGraphNode> visited = new HashSet<UndirectedGraphNode>();
	        for (UndirectedGraphNode node : nodes) {
	            if (visited.contains(node)) continue;
	            List<Integer> temp = new ArrayList<Integer>();
	            dfs(node, visited, temp);
	            Collections.sort(temp);
	            result.add(temp);
	        }

	        return result;
	    }

	    private void dfs(UndirectedGraphNode node,
	                     Set<UndirectedGraphNode> visited,
	                     List<Integer> result) {

	        // add node into result
	        result.add(node.label);
	        visited.add(node);
	        // node is not connected, exclude by for iteration
	        // if (node.neighbors.size() == 0 ) return;
	        for (UndirectedGraphNode neighbor : node.neighbors) {
	            if (visited.contains(neighbor)) continue;
	            dfs(neighbor, visited, result);
	        }
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
