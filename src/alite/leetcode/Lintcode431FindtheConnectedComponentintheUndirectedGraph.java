package alite.leetcode;
/**
 * Find the number connected component in the undirected graph. Each node in the graph contains a label and a list of its neighbors. (a connected component (or just component) of an undirected graph is a subgraph in which any two vertices are connected to each other by paths, and which is connected to no additional vertices in the supergraph.)
Example

Given graph:
A------B  C
 \     |  |
  \    |  |
   \   |  |
    \  |  |
      D   E
Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}
 * @author het
 *
 */
public class Lintcode431FindtheConnectedComponentintheUndirectedGraph {
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
