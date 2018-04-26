package alite.leetcode.xx2.sucess.extra;

import java.util.Arrays;

///**
// * Given a sorted dictionary (array of words) of an alien language, find order of characters in the language.
//Input:  words[] = {"baa", "abcd", "abca", "cab", "cad"}
//Output: Order of characters is 'b', 'd', 'a', 'c'
//Note that words are sorted and in the given language "baa" 
//comes before "abcd", therefore 'b' is before 'a' in output.
//Similarly we can find other orders.
//
//Input:  words[] = {"caa", "aaa", "aab"}
//Output: Order of characters is 'c', 'a', 'b'
//http://www.cnblogs.com/jcliBlogger/p/4758761.html
//you need to understand graph representation, graph traversal and specifically, topological sort, which are all needed to solve this problem cleanly.
//DFS
//https://leetcode.com/discuss/78602/3ms-clean-java-solution-dfs
//A topological ordering is possible if and only if the graph has no directed cycles
//Let's build a graph and perform a DFS. The following states made things easier.
//visited[i] = -1. Not even exist.
//visited[i] = 0. Exist. Non-visited.
//visited[i] = 1. Visiting.
//visited[i] = 2. Visited.
//private final int N = 26;
//public String alienOrder(String[] words) {
//    boolean[][] adj = new boolean[N][N];
//    int[] visited = new int[N];
//    buildGraph(words, adj, visited);
//
//    StringBuilder sb = new StringBuilder();
//    for(int i = 0; i < N; i++) {
//        if(visited[i] == 0) {                 // unvisited
//            if(!dfs(adj, visited, sb, i)) return "";
//        }
//    }
//    return sb.reverse().toString();
//}
//
//public boolean dfs(boolean[][] adj, int[] visited, StringBuilder sb, int i) {
//    visited[i] = 1;                            // 1 = visiting
//    for(int j = 0; j < N; j++) {
//        if(adj[i][j]) {                        // connected
//            if(visited[j] == 1) return false;  // 1 => 1, cycle   
//            if(visited[j] == 0) {              // 0 = unvisited
//                if(!dfs(adj, visited, sb, j)) return false;
//            }
//        }
//    }
//    visited[i] = 2;                           // 2 = visited
//    sb.append((char) (i + 'a'));
//    return true;
//}
//
//public void buildGraph(String[] words, boolean[][] adj, int[] visited) {
//    Arrays.fill(visited, -1);                 // -1 = not even existed
//    for(int i = 0; i < words.length; i++) {
//        for(char c : words[i].toCharArray()) visited[c - 'a'] = 0;
//        if(i > 0) {
//            String w1 = words[i - 1], w2 = words[i];
//            int len = Math.min(w1.length(), w2.length());
//            for(int j = 0; j < len; j++) {
//                char c1 = w1.charAt(j), c2 = w2.charAt(j);
//                if(c1 != c2) {
//                    adj[c1 - 'a'][c2 - 'a'] = true;
//                    break;
//                }
//            }
//        }
//    }
//}
//https://leetcode.com/discuss/72402/fast-java-solution-3ms-dfs-topological-sort
//    public String alienOrder(String[] words) {
//        if (words == null || words.length == 0) return "";
//        if (words.length == 1) return words[0];
//        
//        boolean[][] graph = new boolean[26][26];
//        // mark existing letters
//        for (String w : words) {
//            for (char c : w.toCharArray()) {
//                graph[c - 'a'][c - 'a'] = true;
//            }
//        }
//        // build adjacent matrix
//        int first = 0;
//        int second = 1;
//        while (second < words.length) {
//            String s1 = words[first];
//            String s2 = words[second];
//            int minLen = Math.min(s1.length(), s2.length());
//            for (int i = 0; i < minLen; i++) {
//                if (s1.charAt(i) != s2.charAt(i)) {
//                    graph[s1.charAt(i) - 'a'][s2.charAt(i) - 'a'] = true;
//                    break;
//                }
//            }
//            first++;
//            second++;
//        }
//        
//        // Do topologic sort
//        StringBuilder sb = new StringBuilder();
//        boolean[] path = new boolean[26];
//        for (int i = 0; i < 26; i++) {
//            if (!dfs(graph, sb, i, path)) return "";
//        }
//        
//        for (int i = 0; i < 26; i++) {
//            if (graph[i][i]) sb.append((char)(i + 'a'));
//        }
//        return sb.reverse().toString();
//    }
//    
//    /** Do DFS to do topological sort. Return false for invalid input. */
//    boolean dfs(boolean[][] graph, StringBuilder sb, int index, boolean[] path) {
//        if (!graph[index][index]) return true; // visited or non-existing letter
//        path[index] = true; // track letters in the dfs path for detecting if DAG or not
//        for (int i = 0; i < 26; i++) {
//            if (i == index || !graph[index][i]) continue;
//            if (path[i]) return false; // cyclic path (non-DAG)
//            if (!dfs(graph, sb, i, path)) return false;
//        }
//        path[index] = false;
//        graph[index][index] = false;
//        sb.append((char)(index + 'a'));
//        return true;
//    }
//
//Fortunately, jaewoo posts a nice solution in this post, whose code is rewritten as follows by decomposing the code into two parts:
//make_graph: Build the graph as a list of adjacency lists;
//toposort and acyclic: Traverse the graph in DFS manner to check for cycle and generate the topological sort.
//3     string alienOrder(vector<string>& words) {
// 4         if (words.size() == 1) return words[0];
// 5         graph g = make_graph(words);
// 6         return toposort(g);
// 7     }
// 8 private:
// 9     typedef unordered_map<char, unordered_set<char>> graph;
//10     
//11     graph make_graph(vector<string>& words) {
//12         graph g;
//13         int n = words.size();
//14         for (int i = 1; i < n; i++) {
//15             bool found = false;
//16             string word1 = words[i - 1], word2 = words[i];
//17             int m = word1.length(), n = word2.length(), l = max(m, n);
//18             for (int j = 0; j < l; j++) {
//19                 if (j < m && g.find(word1[j]) == g.end())
//20                     g[word1[j]] = unordered_set<char>();
//21                 if (j < n && g.find(word2[j]) == g.end())
//22                     g[word2[j]] = unordered_set<char>();
//23                 if (j < m && j < n && word1[j] != word2[j] && !found) {
//24                     g[word1[j]].insert(word2[j]);
//25                     found = true;
//26                 }
//27             }
//28         }
//29         return g;
//30     }
//31     
//32     string toposort(graph& g) {
//33         vector<bool> path(256, false), visited(256, false);
//34         string topo;
//35         for (auto adj : g)
//36             if (!acyclic(g, path, visited, topo, adj.first))
//37                 return "";
//38         reverse(topo.begin(), topo.end());
//39         return topo;
//40     }
//41     
//42     bool acyclic(graph& g, vector<bool>& path, vector<bool>& visited, string& topo, char node) {
//43         if (path[node]) return false;
//44         if (visited[node]) return true;
//45         path[node] = visited[node] = true;
//46         for (auto neigh : g[node])
//47             if (!acyclic(g, path, visited, topo, neigh))
//48                 return false;
//49         path[node] = false;
//50         topo += node;
//51         return true;
//52     }
//Topological sort - BFS
//https://leetcode.com/discuss/77078/easiest-java-bfs-solution
//Build Graph
//Init indegree[26] for number of links pointing to w[i], adj[26] for neighbors of w[i].
//For each first seeing character w[i] with indegree initially-1, mark it as indegree = 0.
//For each adjacent words w1 and w2, if w1[i] != w2[i], insert w1[i] -> w2[i] into graph. Increase indegree[w2[i]] by 1.
//Topological Sort
//Start from queue filled with indegree = 0 characters (lexicographically earliest).
//poll queue, append these 0 indegree guys, and reduce indegree of their neighbors by1.
//If neighbors reduced to indegree = 0, add them back to queue.
//Peel level by level until queue is empty.
//private final int N = 26;
//public String alienOrder(String[] words) {
//    List<Set<Integer>> adj = new ArrayList<>(N);
//    int[] degree = new int[N];
//    buildGraph(words, adj, degree);
//
//    StringBuilder sb = new StringBuilder();
//    Queue<Integer> q = new LinkedList<>();
//    for(int i = 0; i < N; i++) {
//        if(degree[i] == 0) q.add(i);
//    }
//    // peeling 0 indegree nodes
//    while(!q.isEmpty()) {
//        int i = q.poll();
//        sb.append((char) ('a' + i));
//        for(int j : adj.get(i)) {
//            if(--degree[j] == 0) {
//                q.add(j);
//            }
//        }
//    }
//    for(int d : degree) if(d > 0) return "";  // invalid
//    return sb.toString();
//}
//
//public void buildGraph(String[] words, List<Set<Integer>> adj, int[] degree) {
//    for(int i = 0; i < N; i++) adj.add(new HashSet<Integer>());
//    Arrays.fill(degree, -1);
//
//    for(int i = 0; i < words.length; i++) {
//        for(char c : words[i].toCharArray()) { 
//            if(degree[c - 'a'] < 0) degree[c - 'a'] = 0;
//        }
//        if(i > 0) {
//            String w1 = words[i - 1], w2 = words[i];
//            int len = Math.min(w1.length(), w2.length());
//            for(int j = 0; j < len; j++) {
//                if(w1.charAt(j) != w2.charAt(j)) {
//                    int c1 = w1.charAt(j) - 'a', c2 = w2.charAt(j) - 'a';
//                    // c1 -> c2, c1 is lexically earlier to c2.
//                    if(!adj.get(c1).contains(c2)) {
//                        adj.get(c1).add(c2);
//                        degree[c2]++;
//                        break;
//                    }
//                }
//            }
//        }
//    }
//}
//http://likesky3.iteye.com/blog/2240572
//https://discuss.leetcode.com/topic/28308/java-ac-solution-using-bfs
//先由输入构造出一张有向图，图的节点是字符，边指示字符顺序。考察相邻两字符串的每个字符来构造全图。假设当前考察的字符是c1, c2（c1是前面单词某位置的字符，c2是后面单词同位置处的字符），若c1和c2不等，则在图中加入一条c1指向c2的边。 
//然后进行拓扑排序，并记录拓扑排序依次遍历到的字符，若排序后所得字符和图中节点数一致说明该图无圈，也即给定的字典合法，拓扑排序所得结果即为字典规则。 
//    public String alienOrder(String[] words) {  
//        if (words == null || words.length == 0)   
//            return "";  
//        if (words.length == 1)  
//            return words[0];  
//        Map<Character, Set<Character>> graph = buildGraph(words);  
//        Map<Character, Integer> indegree = computeIndegree(graph);  
//        StringBuilder order = new StringBuilder();  
//        LinkedList<Character> queue = new LinkedList<Character>();  
//        for (Character c : indegree.keySet()) {  
//            if (indegree.get(c) == 0)  
//                queue.offer(c);  
//        }  
//        while (!queue.isEmpty()) {  
//            char c = queue.poll();  
//            order.append(c);  
//            for (Character adj : graph.get(c)) {  
//                if (indegree.get(adj) - 1 == 0)  
//                    queue.offer(adj);  
//                else  
//                    indegree.put(adj, indegree.get(adj) - 1);  
//            }  
//        }  
//        return order.length() == indegree.size() ? order.toString() : "";  
//    }  
//    public Map<Character, Set<Character>> buildGraph(String[] words) {  
//        Map<Character, Set<Character>> map = new HashMap<Character, Set<Character>>();  
//        int N = words.length;  
//        for (int i = 1; i < N; i++) {  
//            String word1 = words[i - 1];  
//            String word2 = words[i];  
//            int len1 = word1.length(), len2 = word2.length(), maxLen = Math.max(len1, len2);  
//            boolean found = false;  
//            for (int j = 0; j < maxLen; j++) {  
//                char c1 = j < len1 ? word1.charAt(j) : ' ';  
//                char c2 = j < len2 ? word2.charAt(j) : ' ';  
//                if (c1 != ' ' && !map.containsKey(c1))   
//                    map.put(c1, new HashSet<Character>());  
//                if (c2 != ' ' && !map.containsKey(c2))  
//                    map.put(c2, new HashSet<Character>());  
//                if (c1 != ' ' && c2 != ' ' && c1 != c2 && !found) {  
//                    map.get(c1).add(c2);  
//                    found = true;  
//                }  
//            }  
//        }  
//        return map;  
//    }  
//    public Map<Character, Integer> computeIndegree(Map<Character, Set<Character>> graph) {  
//        Map<Character, Integer> indegree = new HashMap<Character, Integer>();  
//        for (Character prev : graph.keySet()) {  
//            if (!indegree.containsKey(prev))  
//                indegree.put(prev, 0);  
//            for (Character succ : graph.get(prev)) {  
//                if (!indegree.containsKey(succ))  
//                    indegree.put(succ, 1);  
//                else  
//                    indegree.put(succ, indegree.get(succ) + 1);   
//            }  
//        }  
//        return indegree;  
//    }  
//https://leetcode.com/discuss/66716/java-ac-solution-using-bfs
//Well, given the graph well represented, the BFS solution is also not that hard :-)
//    string alienOrder(vector<string>& words) {
// 4         if (words.size() == 1) return words[0];
// 5         graph g = make_graph(words);
// 6         unordered_map<char, int> degrees = compute_indegree(g);
// 7         int numNodes = degrees.size();
// 8         string order;
// 9         queue<char> toVisit;
//10         for (auto node : degrees)
//11             if (!node.second)
//12                 toVisit.push(node.first);
//13         for (int i = 0; i < numNodes; i++) {
//14             if (toVisit.empty()) return "";
//15             char c = toVisit.front();
//16             toVisit.pop();
//17             order += c;
//18             for (char neigh : g[c])
//19                 if (!--degrees[neigh])
//20                     toVisit.push(neigh);
//21         }
//22         return order;
//23     }
//24 private:
//25     typedef unordered_map<char, unordered_set<char>> graph;
//26 
//27     graph make_graph(vector<string>& words) {
//28         graph g;
//29         int n = words.size();
//30         for (int i = 1; i < n; i++) {
//31             bool found = false;
//32             string word1 = words[i - 1], word2 = words[i];
//33             int l1 = word1.length(), l2 = word2.length(), l = max(l1, l2);
//34             for (int j = 0; j < l; j++) {
//35                 if (j < l1 && g.find(word1[j]) == g.end())
//36                     g[word1[j]] = unordered_set<char>();
//37                 if (j < l2 && g.find(word2[j]) == g.end())
//38                     g[word2[j]] = unordered_set<char>();
//39                 if (j < l1 && j < l2 && word1[j] != word2[j] && !found) {
//40                     g[word1[j]].insert(word2[j]);
//41                     found = true;
//42                 }
//43             }
//44         }
//45         return g; 
//46     }
//47 
//48     unordered_map<char, int> compute_indegree(graph& g) {
//49         unordered_map<char, int> degrees;
//50         for (auto adj : g) {
//51             if (!degrees[adj.first]);
//52             for (char neigh : adj.second)
//53                 degrees[neigh]++;
//54         }
//55         return degrees;
//56     }
//BTW, if (!degrees[adj.first]); in compute_indegree is to make sure that a node with 0indegree will not be left out. For this part, something about the default constructor ofunordered_map is useful: each time when we try to access a key k which is still not inunordered_map by [k], the default constructor of unordered_map will set its value to 0.
//
//X. Topological sort 
//http://beyondcoder.blogspot.com/2015/09/alien-dictionary.html
//Graph, Topological sort, use HashMap to save vertex and its adjancecy list with char as hashkey.
//public String alienOrder(String[] words) {
//if (words == null) return null;
//Map<Character, Set<Character>> graph_hm = new HashMap<>(); 
//for (int i = 0; i < words.length; i++) {
//for (int j = 0; j < words[i].length(); j++) {
//char c = words[i].charAt(j);
//if (!graph_hm.containsKey(c)) {
//graph_hm.put(c,  new HashSet<Character>());
//}
//}
//if (i > 0) { // order every two words
//getOrder(words[i-1], words[i], graph_hm);
//}
//}
//return topSort(graph_hm);
//}
//public void getOrder(String s, String t, Map<Character, Set<Character>> graph_hm) {
//for(int i = 0; i < Math.min(s.length(), t.length()); i++) {
//char c1 = s.charAt(i), c2 = t.charAt(i);
//if (c1 != c2) {
//if (!graph_hm.get(c1).contains(c2)) {
//graph_hm.get(c1).add(c2);
//}
//break;  // stop here because after one char different, remaining chars doesn't matter
//}
//}
//}
//// standard top sort algorithm
//public String topSort(Map<Character, Set<Character>> graph_hm) {
//StringBuilder sb = new StringBuilder();
//// count initial indegree for every char vertex
//Map<Character, Integer> indegree = new HashMap<>();
//for(char c : graph_hm.keySet()) {
//for(char a : graph_hm.get(c)) {
//int count = indegree.containsKey(a) ? indegree.get(a) + 1 : 1;
//indegree.put(a, count);
//}
//}
//// find indegree==0, initialize the queue
//Queue<Character> queue = new LinkedList<>();
//for(char c : graph_hm.keySet()) {
//if(!indegree.containsKey(c)) {
//queue.offer(c);
//}
//}
//// topological sort
//while(!queue.isEmpty()) {
//char c = queue.poll();
//sb.append(c);
//for(char a : graph_hm.get(c)) {
//indegree.put(a, indegree.get(a) - 1);
//if(indegree.get(a) == 0) {
//queue.offer(a);
//}
//}
//}
//for (int a : indegree.values()) { // if there is any non sorted, this is not a DAG, return false
//if (a != 0) return "";
//}
//return sb.toString();
//}
//public static void main(String [] args) {
//AlienDictionary_graph outer = new AlienDictionary_graph();
//String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
//System.out.println(outer.alienOrder(words));
//}
//
//http://segmentfault.com/a/1190000003795463
//首先简单介绍一下拓扑排序，这是一个能够找出有向无环图顺序的一个方法
//假设我们有3条边：A->C, B->C, C->D，先将每个节点的计数器初始化为0。
//然后我们对遍历边时，每遇到一个边，把目的节点的计数器都加1。然后，我们再遍历一遍，
//找出所有计数器值还是0的节点，这些节点就是有向无环图的“根”。然后我们从根开始广度优先搜索。
//具体来说，搜索到某个节点时，将该节点加入结果中，然后所有被该节点指向的节点的计数器减1，
//在减1之后，如果某个被指向节点的计数器变成0了，那这个被指向的节点就是该节点下轮搜索的子节点。
//在实现的角度来看，我们可以用一个队列，这样每次从队列头拿出来一个加入结果中，同时把被这个节点指向的节点中计数器值减到0的节点也都加入队列尾中。需要注意的是，如果图是有环的，则计数器会产生断层，即某个节点的计数器永远无法清零（有环意味着有的节点被多加了1，然而遍历的时候一次只减一个1，所以导致无法归零），这样该节点也无法加入到结果中。所以我们只要判断这个结果的节点数和实际图中节点数相等，就代表无环，不相等，则代表有环。
//对于这题来说，我们首先要初始化所有节点（即字母），一个是该字母指向的字母的集合（被指向的字母在字母表中处于较后的位置），一个是该字母的计数器。然后我们根据字典开始建图，但是字典中并没有显示给出边的情况，如何根据字典建图呢？其实边都暗藏在相邻两个词之间，比如abc和abd，我们比较两个词的每一位，直到第一个不一样的字母c和d，因为abd这个词在后面，所以d在字母表中应该是在c的后面。所以每两个相邻的词都能蕴含一条边的信息。在建图的同时，实际上我们也可以计数了，对于每条边，将较后的字母的计数器加1。计数时需要注意的是，我们不能将同样一条边计数两次，所以要用一个集合来排除已经计数过的边。最后，我们开始拓扑排序，从计数器为0的字母开始广度优先搜索。为了找到这些计数器为0的字母，我们还需要先遍历一遍所有的计数器。
//最后，根据结果的字母个数和图中所有字母的个数，判断时候有环即可。无环直接返回结果。
//因为这题代码很冗长，面试的时候最好都把几个大步骤都写成子函数，先完成主函数，再实现各个子函数，比如初始化图，建图，加边，排序，都可以分开
//要先对字典里所有存在的字母初始化入度为0，否则之后建图可能会漏掉一些没有入度的字母
//'a'+'b'+""和'a'+""+'b'是不一样的，前者先算数字和，后者则是字符串拼接
//因为字典里有重复的边，所有要先判断，已经添加过的边不要重复添加
//public class Solution {
//    public String alienOrder(String[] words) {
//        // 节点构成的图
//        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
//        // 节点的计数器
//        Map<Character, Integer> indegree = new HashMap<Character, Integer>();
//        // 结果存在这个里面
//        StringBuilder order = new StringBuilder();
//        // 初始化图和计数器
//        initialize(words, graph, indegree);
//        // 建图并计数
//        buildGraphAndGetIndegree(words, graph, indegree);
//        // 拓扑排序的最后一步，根据计数器值广度优先搜索
//        topologicalSort(order, graph, indegree);
//        // 如果大小相等说明无环
//        return order.length() == indegree.size() ? order.toString() : "";
//    }
//    
//    private void initialize(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> indegree){
//        for(String word : words){
//            for(int i = 0; i < word.length(); i++){
//                char curr = word.charAt(i);
//                // 对每个单词的每个字母初始化计数器和图节点
//                if(graph.get(curr) == null){
//                    graph.put(curr, new HashSet<Character>());
//                }
//                if(indegree.get(curr) == null){
//                    indegree.put(curr, 0);
//                }
//            }
//        }
//    }
//    
//    private void buildGraphAndGetIndegree(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> indegree){
//        Set<String> edges = new HashSet<String>();
//        for(int i = 0; i < words.length - 1; i++){
//        // 每两个相邻的词进行比较
//            String word1 = words[i];
//            String word2 = words[i + 1];
//            for(int j = 0; j < word1.length() && j < word2.length(); j++){
//                char from = word1.charAt(j);
//                char to = word2.charAt(j);
//                // 如果相同则继续，找到两个单词第一个不相同的字母
//                if(from == to) continue;
//                // 如果这两个字母构成的边还没有使用过，则
//                if(!edges.contains(from+""+to)){
//                    Set<Character> set = graph.get(from);
//                    set.add(to);
//                    // 将后面的字母加入前面字母的Set中
//                    graph.put(from, set);
//                    Integer toin = indegree.get(to);
//                    toin++;
//                    // 更新后面字母的计数器，+1
//                    indegree.put(to, toin);
//                    // 记录这条边已经处理过了
//                    edges.add(from+""+to);
//                    break;
//                }
//            }
//        }
//    }
//    
//    private void topologicalSort(StringBuilder order, Map<Character, Set<Character>> graph, Map<Character, Integer> indegree){
//        // 广度优先搜索的队列
//        Queue<Character> queue = new LinkedList<Character>();
//        // 将有向图的根，即计数器为0的节点加入队列中
//        for(Character key : indegree.keySet()){
//            if(indegree.get(key) == 0){
//                queue.offer(key);
//            }
//        }
//        // 搜索
//        while(!queue.isEmpty()){
//            Character curr = queue.poll();
//            // 将队头节点加入结果中
//            order.append(curr);
//            Set<Character> set = graph.get(curr);
//            if(set != null){
//                // 对所有该节点指向的节点，更新其计数器，-1
//                for(Character c : set){
//                    Integer val = indegree.get(c);
//                    val--;
//                    // 如果计数器归零，则加入队列中待处理
//                    if(val == 0){
//                        queue.offer(c);
//                    }
//                    indegree.put(c, val);
//                }
//            }
//        }
//    }
//}
//新建一个AlienChar数据结构重写，只用一个Map作为Graph自身
//http://blueocean-penn.blogspot.com/2015/01/given-sorted-dictionary-find-precedence.html
//public static class CNode {
//  char c;
//  Set<CNode> outgoing = new HashSet<CNode>();
//  CNode(char i){this.c=i;}
//  //these two methods are important for hash
//  //but we are not using them in this solution
//  public int hasCode() {return this.c;}
//  public boolean equals(Object c) {
//        if(c == this)
//            return true;
//        if(c instanceof CNode){
//          return this.c == ((CNode)c).c;
//        }
//        return false;
//  }
//}
// 
// public static List<Character> findLanguageOrderDFS(String[] words){
//
//  Set<CNode> vertices = new HashSet<CNode>();
//  createGraph(words, vertices);
//  
//  List<Character> result = new ArrayList<Character>();  
//  //add those vertices without any incoming edges
//  Set<CNode> visited = new HashSet<CNode>();
//  Set<CNode> processed = new HashSet<CNode>();
//  Stack<CNode> stack = new Stack<CNode>();
//  for(CNode n : vertices){
//   if(visited.contains(n))
//        continue;
//   if(processed.contains(n))
//        throw new IllegalArgumentException("cycle found");
//   DFS(n, visited, processed, stack);    
//   visited.add(n);
//   stack.add(n);
//   
//  }
//  
//  while(!stack.isEmpty()){
//   result.add(stack.pop().c);
//  }
//  
//  return result;
// }
// 
// 
// public static void DFS(CNode v, Set<CNode> visited,  Set<CNode> processed, Stack<CNode> s){
//  if(visited.contains(v))
//   return;
//  if(processed.contains(v))
//   throw new IllegalArgumentException("cycle found");
//  processed.add(v);
//  for(CNode n : v.outgoing){
//   if(!visited.contains(n)){
//    DFS(n, visited, processed, s);
//   }
//  }
//  visited.add(v);
//  s.push(v);
// }
//
//
//private static void createGraph(String[] words,
//   Set<CNode> vertices) {
////we may not need this hash map if we can trust the hashcode() written in CNode class
//Map<Character, CNode> nodes = new HashMap<Character, CNode>();
//  for(int i=0; i<words.length-1; i++){
//   String current = words[i], next = words[i+1];
//   int j = 0;
//   for(j=0; j<current.length() && j<next.length() && current.charAt(j) == next.charAt(j); j++){}
//
//   char c1=current.charAt(j), c2=next.charAt(j);
//   CNode start = null, end = null;
//   
//   if(!nodes.containsKey(c1)){
//    start = new CNode(c1);
//    nodes.put(c1, start);
//    vertices.add(start);
//   }
//   if(!nodes.containsKey(c2)){
//    end = new CNode(c2);
//    nodes.put(c2, end);
//    vertices.add(end);
//   }
//   start = nodes.get(c1);
//   end = nodes.get(c2);   
//   start.outgoing.add(end);   
//  }
// }
//More solution at: http://massivealgorithms.blogspot.com/2014/07/given-sorted-dictionary-of-alien.html
//Read full article from LIKE CODING: LeetCode [269] Alien Dictionary
// * @author het
// *
// */
public class LeetCode269AlienDictionary {
	private final int N = 26;
	public String alienOrder(String[] words) {
	    boolean[][] adj = new boolean[N][N];
	    int[] visited = new int[N];
	    buildGraph(words, adj, visited);

	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < N; i++) {
	        if(visited[i] == 0) {                 // unvisited
	            if(!dfs(adj, visited, sb, i)) return "";
	        }
	    }
	    return sb.reverse().toString();
	}

	public boolean dfs(boolean[][] adj, int[] visited, StringBuilder sb, int i) {
	    visited[i] = 1;                            // 1 = visiting
	    for(int j = 0; j < N; j++) {
	        if(adj[i][j]) {                        // connected
	            if(visited[j] == 1) return false;  // 1 => 1, cycle   
	            if(visited[j] == 0) {              // 0 = unvisited
	                if(!dfs(adj, visited, sb, j)) return false;
	            }
	        }
	    }
	    visited[i] = 2;                           // 2 = visited
	    sb.append((char) (i + 'a'));
	    return true;
	}

	public void buildGraph(String[] words, boolean[][] adj, int[] visited) {
	    Arrays.fill(visited, -1);                 // -1 = not even existed
	    for(int i = 0; i < words.length; i++) {
	        for(char c : words[i].toCharArray()) visited[c - 'a'] = 0;
	        if(i > 0) {
	            String w1 = words[i - 1], w2 = words[i];
	            int len = Math.min(w1.length(), w2.length());
	            for(int j = 0; j < len; j++) {
	                char c1 = w1.charAt(j), c2 = w2.charAt(j);
	                if(c1 != c2) {
	                    adj[c1 - 'a'][c2 - 'a'] = true;
	                    break;
	                }
	            }
	        }
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
