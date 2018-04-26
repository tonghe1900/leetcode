package alite.leetcode.conquerLeft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * LeetCode 332 - Reconstruct Itinerary

http://bookshadow.com/weblog/2016/02/05/leetcode-reconstruct-itinerary/
Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], 
reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK.
 Thus, the itinerary must begin with JFK.
Note:
If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order 
when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
All airports are represented by three capital letters (IATA code).
You may assume all tickets may form at least one valid itinerary.
Example 1:
tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
Example 2:
tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
题目大意：
给定一组机票，用出发机场和到达机场[from, to]来表示，重建行程的顺序。所有的机票都属于一个从JFK（肯尼迪国际机场）出发的旅客。因此，行程必须从JFK开始。
注意：
如果存在多重有效的行程，你应当返回字典序最小的那个。例如，行程["JFK", "LGA"]的字典序比["JFK", "LGB"]要小。
所有的机场用3个大写字母表示（IATA编码）。
你可以假设所有的机票均至少包含一条有效的行程。
X. DFS
解法I 深度优先搜索（DFS）：
从出发机场开始，按照到达机场的字典序递归搜索
在搜索过程中删除已经访问过的机票
将到达机场分为两类：子行程中包含出发机场的记为left，不含出发机场的记为right
返回时left排在right之前
http://bookshadow.com/weblog/2016/02/05/leetcode-reconstruct-itinerary/

    def findItinerary(self, tickets):
        routes = collections.defaultdict(list)
        for s, e in tickets:
            routes[s].append(e)
        def solve(start):
            left, right = [], []
            for end in sorted(routes[start]):
                if end not in routes[start]:
                    continue
                routes[start].remove(end)
                subroutes = solve(end)
                if start in subroutes:
                    left += subroutes
                else:
                    right += subroutes
            return [start] + left + right
        return solve("JFK")

解法II 欧拉通路（Eulerian path）：
https://en.wikipedia.org/wiki/Eulerian_path
an Eulerian trail (or Eulerian path) is a trail in a graph which visits every edge exactly once. Similarly, an Eulerian circuit or Eulerian cycle is an Eulerian trail which starts and ends on the same vertex. 
TODO: https://www.scribd.com/doc/296907217/Hierholzer-s-Algorithm
Choose any starting vertex v, and follow a trail of edges from that vertex until returning to v. It is not possible to get stuck at any vertex other than v, because the even degree of all vertices ensures that, when the trail enters another vertex w there must be an unused edge leaving w. The tour formed in this way is a closed tour, but may not cover all the vertices and edges of the initial graph.
As long as there exists a vertex u that belongs to the current tour but that has adjacent edges not part of the tour, start another trail from u, following unused edges until returning to u, and join the tour formed in this way to the previous tour.
https://leetcode.com/discuss/84659/short-ruby-python-java-c
Just Eulerian path. Greedy DFS, building the route backwards when retreating.
some observations:
The nodes which have odd degrees (int and out) are the entrance or exit. In your example it's JFK and A.
If there are no nodes have odd degrees, we could follow any path without stuck until hit the last exit node
The reason we got stuck is because that we hit the exit
In your given example, nodes A is the exit node, we hit it and it's the exit. So we put it to the result as the last node.
First keep going forward until you get stuck. That's a good main path already. Remaining tickets form cycles which are found on the way back and get merged into that main path. By writing down the path backwards when retreating from recursion, merging the cycles into the main path is easy - the end part of the path has already been written, the start part of the path hasn't been written yet, so just write down the cycle now and then keep backwards-writing the path.
Example:
enter image description here
From JFK we first visit JFK -> A -> C -> D -> A. There we're stuck, so we write down A as the end of the route and retreat back to D. There we see the unused ticket to B and follow it: D -> B -> C -> JFK -> D. Then we're stuck again, retreat and write down the airports while doing so: Write down D before B, then JFK before D, etc. When we're back from our cycle at D, the written route is D -> B -> C -> JFK -> D -> A. Then we retreat further along the original path, prepending C, A and finally JFK to the route, ending up with the route JFK -> A -> C -> D -> B -> C -> JFK -> D -> A.
A->B, A-C, C->A.
Why A->B is the last trip if there is no way to go from B and the trips pool is not empty
Because I build the route backwards. From A the solution first visits B but didn't put anything into the route yet. Then when it's stuck at B, it puts B at the end of the route. The recursion retreats to A, where the A->C->A cycle will be found and inserted into the route before the B.
public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    visit("JFK");
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();
List<String> route = new LinkedList();

void visit(String airport) {
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());
    route.add(0, airport);
}
Iterative version:
public List<String> findItinerary(String[][] tickets) {
    Map<String, PriorityQueue<String>> targets = new HashMap<>();
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    List<String> route = new LinkedList();
    Stack<String> stack = new Stack<>();
    stack.push("JFK");
    while (!stack.empty()) {
        while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
            stack.push(targets.get(stack.peek()).poll());
        route.add(0, stack.pop());
    }
    return route;
}
https://discuss.leetcode.com/topic/36385/share-solution-java-greedy-stack-15ms-with-explanation
Noticed some folks are using Hierholzer's algorithm to find a Eulerian path.
My solution is similar, considering this passenger has to be physically in one place before move to another airport, we are considering using up all tickets and choose lexicographically smaller solution if in tie as two constraints.
Thinking as that passenger, the passenger choose his/her flight greedy as the lexicographical order, once he/she figures out go to an airport without departure with more tickets at hand. the passenger will push current ticket in a stack and look at whether it is possible for him/her to travel to other places from the airport on his/her way.
https://discuss.leetcode.com/topic/36383/share-my-solution
All the airports are vertices and tickets are directed edges. Then all these tickets form a directed graph.
The graph must be Eulerian since we know that a Eulerian path exists.
Thus, start from "JFK", we can apply the Hierholzer's algorithm to find a Eulerian path in the graph which is a valid reconstruction.
Since the problem asks for lexical order smallest solution, we can put the neighbors in a min-heap. In this way, we always visit the smallest possible neighbor first in our trip.

From this link http://www.geeksforgeeks.org/euler-circuit-directed-graph/, I found the following definition of a Eulerian graph.
Eulerian Cycle
A directed graph has an eulerian cycle if following conditions are true (Source: Wiki)
All vertices with nonzero degree belong to a single strongly connected component.
In degree and out degree of every vertex is same.

    Map<String, PriorityQueue<String>> flights;
    LinkedList<String> path;

    public List<String> findItinerary(String[][] tickets) {
        flights = new HashMap<>();
        path = new LinkedList<>();
        for (String[] ticket : tickets) {
            flights.putIfAbsent(ticket[0], new PriorityQueue<>());
            flights.get(ticket[0]).add(ticket[1]);
        }
        dfs("JFK");
        return path;
    }

    public void dfs(String departure) {
        PriorityQueue<String> arrivals = flights.get(departure);
        while (arrivals != null && !arrivals.isEmpty())
            dfs(arrivals.poll());
        path.addFirst(departure);
    }
http://www.cnblogs.com/EdwardLiu/p/5184961.html
First keep going forward until you get stuck. Put the stuck element always at the front of the result list. Try if it is possible to travel to other places from the airport on the way.
From JFK we first visit JFK -> A -> C -> D -> A. There we're stuck, so we write down A as the end of the route and retreat back to D. There we see the unused ticket to B and follow it: D -> B -> C -> JFK -> D. Then we're stuck again, retreat and write down the airports while doing so: Write down D before A, then JFK before D, the c before JFK, etc. When we're back from our cycle at D, the written route is D -> B -> C -> JFK -> D -> A. Then we retreat further along the original path, prepending C, A and finally JFK to the route, ending up with the route JFK -> A -> C -> D -> B -> C -> JFK -> D -> A.
Since the problem asks for lexical order smallest solution, we can put the neighbors in a min-heap. In this way, we always visit the smallest possible neighbor first in our trip.
Use LinkedList as the result type because we always add at the front of the list

public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    visit("JFK");
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();
List<String> route = new LinkedList();

void visit(String airport) {
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());
    route.add(0, airport);
}

Iterative version:
public List<String> findItinerary(String[][] tickets) {
    Map<String, PriorityQueue<String>> targets = new HashMap<>();
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    List<String> route = new LinkedList();
    Stack<String> stack = new Stack<>();
    stack.push("JFK");
    while (!stack.empty()) {
        while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
            stack.push(targets.get(stack.peek()).poll());
        route.add(0, stack.pop());
    }
    return route;
}
https://leetcode.com/discuss/84702/share-my-solution
    Map<String, PriorityQueue<String>> flights;
    LinkedList<String> path;

    public List<String> findItinerary(String[][] tickets) {
        flights = new HashMap<>();
        path = new LinkedList<>();
        for (String[] ticket : tickets) {
            flights.putIfAbsent(ticket[0], new PriorityQueue<>());//create pq everytime,inefficient
            flights.get(ticket[0]).add(ticket[1]);
        }
        dfs("JFK");
        return path;
    }

    public void dfs(String departure) {
        PriorityQueue<String> arrivals = flights.get(departure);
        while (arrivals != null && !arrivals.isEmpty())
            dfs(arrivals.poll());
        path.addFirst(departure);
    }

将机场视为顶点，机票看做有向边，可以构成一个有向图。
通过图（无向图或有向图）中所有边且每边仅通过一次的通路称为欧拉通路，相应的回路称为欧拉回路。具有欧拉回路的图称为欧拉图（Euler Graph），
具有欧拉通路而无欧拉回路的图称为半欧拉图。
因此题目的实质就是从JFK顶点出发寻找欧拉通路，可以利用Hierholzer算法。
    def findItinerary(self, tickets):
        targets = collections.defaultdict(list)
        for a, b in sorted(tickets)[::-1]:
            targets[a] += b,
        route = []
        def visit(airport):
            while targets[airport]:
                visit(targets[airport].pop())
            route.append(airport)
        visit('JFK')
        return route[::-1]

This is an application of Hierholzer’s algorithm to find a Eulerian path.
PriorityQueue should be used instead of TreeSet, because there are duplicate entries.
http://algobox.org/reconstruct-itinerary/
All the airports are vertices and tickets are directed edges. Then all these tickets form a directed graph.
The graph must be Eulerian since we know that a Eulerian path exists: the man’s real itinerary.
Thus, start from “JFK”, we can apply the Hierholzer’s algorithm to find a Eulerian path in the graph which is a valid reconstruction.
Since the problem asks for lexical order smallest solution, we can put the neighbors in a min-heap. In this way, we always visit the smallest possible neighbor first in our trip.

    Map<String, PriorityQueue<String>> flights;

    LinkedList<String> path;

    public List<String> findItinerary(String[][] tickets) {

        flights = new HashMap<>();

        path = new LinkedList<>();

        for (String[] ticket : tickets) {

            flights.putIfAbsent(ticket[0], new PriorityQueue<>());

            flights.get(ticket[0]).add(ticket[1]);

        }

        dfs("JFK");

        return path;

    }

    public void dfs(String departure) {

        PriorityQueue<String> arrivals = flights.get(departure);

        while (arrivals != null && !arrivals.isEmpty())

            dfs(arrivals.poll());

        path.addFirst(departure);

    }
https://leetcode.com/discuss/84706/share-solution-java-greedy-stack-15ms-with-explanation
https://asanchina.wordpress.com/2016/02/04/332-reconstruct-itinerary/
我刚开始以为建图后用拓扑排序来做，结果是错的，这道题并不是拓扑排序。以前做拓扑排序要求访问每个点一次且仅一次，这道题是要求访问每条边一次且仅一次，所以只能用DFS搜索。
本来就是普通的dfs，但是这道题各种要求使得题目复杂了不少：
输入是string，不是int
要求输出最小序列
所以针对上面两个要求，我首先将每个string转换成对应的int型，因为每个string只有3个letter，这些letter我们可以看成是26进制的letter，所以3个letter可以对应一个26进制的数字。假设strings = [‘AAA’, ‘BBB’, ‘CCC’, ‘DDD’, ‘ZZZ’], 对应的int型数值是[0, 703, 1406, 2109, 17575]。接着就是dfs这个邻接表了，由于要求访问每条边，所以不用设visited数组，只要访问的过程中标记边就够了。
1, 用hash表存储路径
上面谈到string需要转换成int型再存图，这样比较好处理，其实我们可以利用hash来存储路径(hash的本质就是用int型来代表某一个object)：
2, 转换成int型(不进行离散化)
上面的代码运行的很慢，要368ms，究其原因是申请的空间太多，导致没必要的for循环。显而易见，上面26*26*26不一定都用到了，我们可以尝试离散化。
假设strings = [‘AAA’, ‘BBB’, ‘CCC’, ‘DDD’, ‘ZZZ’], 对应的int型数值是[0, 703, 1406, 2109, 17575]，进行离散化后对应的label就是[0,1,2,3,4]。
我们用label值(即离散化后的值)建图，图用邻接表表示，接着就是和上面一样的做法了。
    vector<string> findItinerary(vector<pair<string, string> > tickets) {
        int size = tickets.size();
        
        int map[26*26*26];
        memset(map, 0xff, sizeof(map));
        int mini = 26*26*26, maxi = -1;
        for(int i = 0; i < size; ++i) 
        { 
            int num = getNumber(tickets[i].first); 
            mini = mini>num?num:mini;
            maxi = maxi<num?num:maxi; 
            map[num] = 0; 
            num = getNumber(tickets[i].second); 
            mini = mini>num?num:mini;
            maxi = maxi<num?num:maxi;
            map[num] = 0;
        }
        
        vector<int> label;
        int pos = 0;
        for(int i = mini; i <= maxi; ++i)
            if(map[i] == 0)
            {
                label.push_back(i);
                map[i] = pos++;
            }
            
        vector<vector<int> > adj(pos);
        for(int i = 0; i < size; ++i)
        {
            int from = getNumber(tickets[i].first);
            int to = getNumber(tickets[i].second);
            int fromLabel = map[from];
            int toLabel = map[to];
            
            adj[fromLabel].push_back(toLabel);
        }
        
        for(int i = 0; i < pos; ++i)
            sort(adj[i].begin(), adj[i].end());
        
        int start = map[getNumber("JFK")];
        vector<int> order(size+1);
        order[0] = start;
        bool flag = false;
        dfs(adj, order, start, 1, flag);
        
        return reconstruct(label, order);
    }
private:
    void dfs(vector<vector<int> > &adj, vector<int> &order, int label, int pos, bool &flag)
    {
        if(flag) return;
        int size = order.size();
        if(pos == size)
        {
            flag = true;
            return;
        }
        
        int neighborSize = adj[label].size();
        for(int i = 0; i < neighborSize; ++i)
            if(adj[label][i] != -1)
            {
                order[pos] = adj[label][i];
                int prev = adj[label][i];
                adj[label][i] = -1;
                dfs(adj, order, prev, pos+1, flag);
                if(flag) return;
                adj[label][i] = prev;
            }
    }
    
    vector<string> reconstruct(vector<int> &label, vector<int> &order)
    {
        int size = order.size();
        vector<string> result(size);
        for(int i = 0; i < size; ++i)
        {
            int num = label[order[i]];
            result[i] = getString(num);
        }
        return result;
    }
    
    
    int getNumber(string& s)
    {
        int sum = 0;
        for(int i = 0; i < 3; ++i) 
        { 
            sum *= 26; 
            sum += s[i]-'A'; 
        } 
        return sum; 
    } 
    string getString(int number) 
    { 
        string ret(3, ' '); 
        for(int i = 2; i >= 0; --i)
        {
            ret[i] = number%26+'A';
            number /= 26;
        }
        return ret;
    }
https://leetcode.com/discuss/85213/java-11ms-solution-hashmap-%26-sorted-list
https://leetcode.com/discuss/86611/java-14ms-dfs-backtrack
    public List<String> findItinerary(String[][] tickets) {
        ArrayList<String> result = new ArrayList<String>();

        if(tickets == null || tickets.length == 0){
            return result;
        }

        int total = tickets.length + 1;

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

        for(int i = 0; i < tickets.length; i++){
            if(map.containsKey(tickets[i][0])){
                ArrayList<String> tmp = map.get(tickets[i][0]);
                listAdd(tickets[i][1], tmp);
            }
            else{
                ArrayList<String> tmp = new ArrayList<String>();
                tmp.add(tickets[i][1]);
                map.put(tickets[i][0], tmp);
            }
        }

        result.add("JFK");

        itineraryHelper("JFK", map, result, total, 1);

        return result;
    }

    public boolean itineraryHelper(String current, HashMap<String, ArrayList<String>> map, ArrayList<String> result, int total, int num){

        if(num >= total){
            return true;
        }

        if(!map.containsKey(current) || map.get(current).size() == 0){
            return false;
        }

        ArrayList<String> curList = map.get(current);
        int i = 0;

        while(i < curList.size()){
            String next = curList.remove(i);
            result.add(next);

            if(itineraryHelper(next, map, result, total, num + 1)){
                return true;
            }

            result.remove(result.size() - 1);
            listAdd(next, curList);
            i++;
        }

        return false;
    }


    public void listAdd(String value, ArrayList<String> list){
        if(list.size() == 0){
            list.add(value);
            return;
        }
        else{
            int i = 0;
            while(i < list.size()){
                if(value.compareTo(list.get(i)) <= 0){
                    list.add(i, value);
                    return;
                }
                i++;
            }
            list.add(value);
            return;
        }
    }



https://discuss.leetcode.com/topic/36332/ac-java-solution-dfs-with-treemap
https://discuss.leetcode.com/topic/36836/java-map-with-treeset-based-solution
public List<String> findItinerary(String[][] tickets) {
  Map<String,Set<String>> cityToDests = new HashMap<String,Set<String>>();
  Map<String, Integer> avlTickets = new HashMap<String, Integer>();
  for (String[] str: tickets){
   String ticket = str[0]+"_"+str[1];
            avlTickets.put(ticket,avlTickets.get(ticket)!=null?avlTickets.get(ticket)+1:1);
            cityToDests.put(str[0],cityToDests.get(str[0])!=null?
                    cityToDests.get(str[0]):new TreeSet<String>());
            Set<String> set = cityToDests.get(str[0]);
            set.add(str[1]);
        }
  List<String> itinerary = new ArrayList<String>();
  itinerary.add("JFK");
  return findItineraryHelper("JFK", avlTickets, cityToDests, itinerary);
 }
 private List<String> findItineraryHelper(String fromCity, Map<String,Integer> avlTickets,
       Map<String, Set<String>> cityToDests, List<String> itinerary){
  if (cityToDests.get(fromCity)!=null && !avlTickets.isEmpty()){
      for (String toCity:cityToDests.get(fromCity)){
       String ticket = fromCity+"_"+toCity;
       if (avlTickets.get(ticket) != null){
           int newCnt = avlTickets.get(ticket)-1;
           int dc=newCnt==0?avlTickets.remove(ticket):avlTickets.put(ticket,newCnt);
           itinerary.add(toCity);
           findItineraryHelper(toCity, avlTickets, cityToDests, itinerary);
              if (avlTickets.isEmpty()) 
            break;
           avlTickets.put(ticket, ++newCnt);
           itinerary.remove(itinerary.size()-1);
       }
      }
  }
  return itinerary;
 }

https://asanchina.wordpress.com/2016/02/04/332-reconstruct-itinerary/

题目其实可以增加难度的，比如，有的飞机票就是用不到啊[a,b ] [b,c] [f,g]最后一张用不到，让你求最多跑多少，多解输出最小序列。
https://discuss.leetcode.com/topic/36385/share-solution-java-greedy-stack-15ms-with-explanation
 * @author het
 *
 */
public class LeetCode332 {
	public static List<String> findItinerary(String[][] tickets) {
        ArrayList<String> result = new ArrayList<String>();

        if(tickets == null || tickets.length == 0){
            return result;
        }

        int total = tickets.length + 1;

        Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        for(int i = 0; i < tickets.length; i++){
            if(map.containsKey(tickets[i][0])){
            	Set<String> tmp = map.get(tickets[i][0]);
                tmp.add(tickets[i][1]);
            }
            else{
                Set<String> tmp = new TreeSet<String>();
                tmp.add(tickets[i][1]);
                map.put(tickets[i][0], tmp);
            }
        }

        result.add("JFK");

        itineraryHelper("JFK", map, result, total, 1);

        return result;
    }

    public static boolean itineraryHelper(String current, Map<String, Set<String>> map, ArrayList<String> result, int total, int num){

        if(num >= total){
            return true;
        }

        if(!map.containsKey(current) || map.get(current).size() == 0){
            return false;
        }

        Set<String> curList = map.get(current);
        Set<String> copy = new TreeSet<String>(curList);
        for(String next: copy){
        	 curList.remove(next);
             result.add(next);
             if(itineraryHelper(next, map, result, total, num + 1)){
                 return true;
             }
             result.remove(result.size() - 1);
             curList.add(next);
             
        }
        
//        while(i < curList.size()){
//            String next = curList.remove(i);
//            result.add(next);
//
//            if(itineraryHelper(next, map, result, total, num + 1)){
//                return true;
//            }
//
//            result.remove(result.size() - 1);
//            listAdd(next, curList);
//            i++;
//        }

        return false;
    }


//    public static void listAdd(String value, ArrayList<String> list){
//        if(list.size() == 0){
//            list.add(value);
//            return;
//        }
//        else{
//            int i = 0;
//            while(i < list.size()){
//                if(value.compareTo(list.get(i)) <= 0){
//                    list.add(i, value);
//                    return;
//                }
//                i++;
//            }
//            list.add(value);
//            return;
//        }
//    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(findItinerary(new String[][]{{"JFK","SFO"},
				{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}}));
		
		//[JFK, ATL, JFK, SFO, ATL, SFO]
		
		//[JFK, ATL, JFK, SFO, ATL, SFO]
		
//		tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
//				Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
//				Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.

	}

}
