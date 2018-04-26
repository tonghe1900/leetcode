package alite.leetcode.xx3.extra;

/**
 * LeetCode 364 - Nested List Weight Sum II
 * 
 * http://blog.csdn.net/qq508618087/article/details/51743408 Given a nested list
 * of integers, return the sum of all integers in the list weighted by their
 * depth. Each element is either an integer, or a list -- whose elements may
 * also be integers or other lists. Different from the previous question where
 * weight is increasing from root to leaf, now the weight is defined from bottom
 * up. i.e., the leaf level integers have weight 1, and the root level integers
 * have the largest weight. Example 1: Given the list [[1,1],2,[1,1]], return 8.
 * (four 1's at depth 1, one 2 at depth 2) Example 2: Given the list
 * [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2, and one 6 at
 * depth 1; 1*3 + 4*2 + 6*1 = 17)
 * https://discuss.leetcode.com/topic/49041/no-depth-variable-no-multiplication/
 * Inspired by lzb700m's solution and one of mine. Instead of multiplying by
 * depth, add integers multiple times (by going level by level and adding the
 * unweighted sum to the weighted sum after each level). public int
 * depthSumInverse(List<NestedInteger> nestedList) { int unweighted = 0,
 * weighted = 0; while (!nestedList.isEmpty()) { List<NestedInteger> nextLevel =
 * new ArrayList<>(); for (NestedInteger ni : nestedList) { if (ni.isInteger())
 * unweighted += ni.getInteger(); else nextLevel.addAll(ni.getList()); }
 * weighted += unweighted; nestedList = nextLevel; } return weighted; }
 * http://www.cnblogs.com/grandyang/p/5615583.html
 * 下面这个方法就比较巧妙了，由史蒂芬大神提出来的，这个方法用了两个变量unweighted和weighted，非权重和跟权重和，初始化均为0，然后如果nestedList不为空开始循环，先声明一个空数组nextLevel，遍历nestedList中的元素，如果是数字，则非权重和加上这个数字，如果是数组，就加入nextLevel，这样遍历完成后，第一层的数字和保存在非权重和unweighted中了，其余元素都存入了nextLevel中，此时我们将unweighted加到weighted中，将nextLevel赋给nestedList，这样再进入下一层计算，由于上一层的值还在unweighted中，所以第二层计算完将unweighted加入weighted中时，相当于第一层的数字和被加了两次，这样就完美的符合要求了
 * 
 * int depthSumInverse(vector<NestedInteger>& nestedList) { int unweighted = 0,
 * weighted = 0; while (!nestedList.empty()) { vector<NestedInteger> nextLevel;
 * for (auto a : nestedList) { if (a.isInteger()) { unweighted +=
 * a.getInteger(); } else { nextLevel.insert(nextLevel.end(),
 * a.getList().begin(), a.getList().end()); } } weighted += unweighted;
 * nestedList = nextLevel; } return weighted; }
 * https://discuss.leetcode.com/topic/49023/share-my-2ms-intuitive-one-pass-no-multiplication-solution
 * The idea is to pass the current found integer sum into the next level of
 * recursion, and return it back again. So that we don't have to count the
 * number of levels in the nested list. public int
 * depthSumInverse(List<NestedInteger> nestedList) { return helper(nestedList,
 * 0); }
 * 
 * private int helper(List<NestedInteger> niList, int prev) { int intSum = prev;
 * List<NestedInteger> levelBreak = new ArrayList<>();
 * 
 * for (NestedInteger ni : niList) { if (ni.isInteger()) { intSum +=
 * ni.getInteger(); } else { levelBreak.addAll(ni.getList()); } }
 * 
 * int listSum = levelBreak.isEmpty()? 0 : helper(levelBreak, intSum);
 * 
 * return listSum + intSum; } https://segmentfault.com/a/1190000005937820
 * 如果说第一道题的关键是记录层次，那么这一题的关键是把这一层的integer sum传到下一层去 public int
 * DFS(List<NestedInteger> nestedList, int intSum) { //关键点在于把上一层的integer
 * sum传到下一层去，这样的话，接下来还有几层，每一层都会加上这个integer sum,也就等于乘以了它的层数 List<NestedInteger>
 * nextLevel = new ArrayList<>(); int listSum = 0; for (NestedInteger list :
 * nestedList) { if (list.isInteger()) { intSum += list.getInteger(); } else {
 * nextLevel.addAll(list.getList()); } } listSum = nextLevel.isEmpty() ? 0 :
 * DFS(nextLevel, intSum); return listSum + intSum; }
 * 
 * public int depthSumInverse(List<NestedInteger> nestedList) { return
 * DFS(nestedList, 0); } 思路: 和之前的一题不同在于这题结点的权值是越靠近根部越高, 而在叶子结点则越低.
 * 所以在找到最大深度之前你是无法计算的, 也就是说我们可以将每个值和他的深度边搜索边存起来, 并且计算最大深度是多少.
 * 最后将所有的点遍历完之后就得到了所有需要的信息. 这时就可以根据最大深度和每一个点的深度来计算加权值了. void
 * DFS(vector<NestedInteger>& nestedList, int depth) { maxDepth = max(maxDepth,
 * depth); for(auto val: nestedList) { if(!val.isInteger()) DFS(val.getList(),
 * depth+1); else nums.push_back(make_pair(val.getInteger(), depth)); } }
 * 
 * int depthSumInverse(vector<NestedInteger>& nestedList) { if(nestedList.size()
 * ==0) return 0; DFS(nestedList, 1); for(auto val: nums) result+=
 * (maxDepth-val.second+1)*val.first; return result; } private: vector<pair<int,
 * int>> nums; int maxDepth = 0, result = 0;
 * http://blog.csdn.net/jmspan/article/details/51747784 方法：先检查最大深度，再计算。 private
 * int maxDepth(List<NestedInteger> nestedList, int depth) { int max = depth;
 * for(NestedInteger ni : nestedList) { if (!ni.isInteger()) { max =
 * Math.max(max, maxDepth(ni.getList(), depth + 1)); } } return max; } private
 * int sum(List<NestedInteger> nestedList, int depth) { int sum = 0;
 * for(NestedInteger ni : nestedList) { if (ni.isInteger()) { sum +=
 * ni.getInteger() * depth; } else { sum += sum(ni.getList(), depth - 1); } }
 * return sum; } public int depthSumInverse(List<NestedInteger> nestedList) { if
 * (nestedList == null || nestedList.isEmpty()) return 0; int max =
 * maxDepth(nestedList, 1); return sum(nestedList, max); } X. BFS
 * https://discuss.leetcode.com/topic/49488/java-ac-bfs-solution public int
 * depthSumInverse(List<NestedInteger> nestedList) { if (nestedList == null)
 * return 0; Queue<NestedInteger> queue = new LinkedList<NestedInteger>(); int
 * prev = 0; int total = 0; for (NestedInteger next: nestedList) {
 * queue.offer(next); }
 * 
 * while (!queue.isEmpty()) { int size = queue.size(); int levelSum = 0; for
 * (int i = 0; i < size; i++) { NestedInteger current = queue.poll(); if
 * (current.isInteger()) levelSum += current.getInteger(); List<NestedInteger>
 * nextList = current.getList(); if (nextList != null) { for (NestedInteger
 * next: nextList) { queue.offer(next); } } } prev += levelSum; total += prev; }
 * return total; } http://www.cnblogs.com/grandyang/p/5615583.html
 * 下面这种算法是常规的BFS解法，利用上面的建立两个变量unweighted和weighted的思路，大体上没什么区别: int
 * depthSumInverse(vector<NestedInteger>& nestedList) { int unweighted = 0,
 * weighted = 0; queue<vector<NestedInteger>> q; q.push(nestedList); while
 * (!q.empty()) { int size = q.size(); for (int i = 0; i < size; ++i) {
 * vector<NestedInteger> t = q.front(); q.pop(); for (auto a : t) { if
 * (a.isInteger()) unweighted += a.getInteger(); else q.push(a.getList()); } }
 * weighted += unweighted; } return weighted; }
 * https://discuss.leetcode.com/topic/49008/java-easy-to-understand-solution
 * http://fangde2.blogspot.com/2016/07/leetcode-q364-nested-list-weight-sum-ii.html
 * public class Solution { Map<Integer, Integer> map = new HashMap<>();
 * 
 * public int depthSumInverse(List<NestedInteger> nestedList) { int ret = 0;
 * 
 * //use dfs to travese the List and put (depth, number) to the map
 * dfs(nestedList, 1);
 * 
 * int maxDepth = Integer.MIN_VALUE;
 * 
 * //get the max depth for (int n : map.keySet()) { if (n>maxDepth) maxDepth =
 * n; }
 * 
 * //get the sum for (int n : map.keySet()) { ret += (maxDepth-n+1)*map.get(n);
 * }
 * 
 * return ret; }
 * 
 * void dfs(List<NestedInteger> nestedList, int depth) {
 * 
 * for (NestedInteger item : nestedList) { if (item.isInteger()) { Integer num =
 * map.get(depth); if (num == null) { map.put(depth, item.getInteger()); } else
 * { map.put(depth, map.get(depth) + item.getInteger()); } } else {
 * dfs(item.getList(), depth+1); } } } }
 * 我最开始的想法是在遍历的过程中建立一个二维数组，把每层的数字都保存起来，然后最后知道了depth后，再来计算权重和，比如题目中给的两个例子，建立的二维数组分别为：
 * [[1,1],2,[1,1]]： 1 1 1 1 2 [1,[4,[6]]]： 1 4 6 这样我们就能算出权重和了 int
 * depthSumInverse(vector<NestedInteger>& nestedList) { int res = 0;
 * vector<vector<int>> v; for (auto a : nestedList) { helper(a, 0, v); } for
 * (int i = v.size() - 1; i >= 0; --i) { for (int j = 0; j < v[i].size(); ++j) {
 * res += v[i][j] * (v.size() - i); } } return res; } void helper(NestedInteger
 * &ni, int depth, vector<vector<int>> &v) { vector<int> t; if (depth <
 * v.size()) t = v[depth]; else v.push_back(t); if (ni.isInteger()) {
 * t.push_back(ni.getInteger()); if (depth < v.size()) v[depth] = t; else
 * v.push_back(t); } else { for (auto a : ni.getList()) { helper(a, depth + 1,
 * v); } } }
 * 其实上面的方法可以简化，由于每一层的数字不用分别保存，每个数字分别乘以深度再相加，跟每层数字先相加起来再乘以深度是一样的，这样我们只需要一个一维数组就可以了，只要把各层的数字和保存起来，最后再计算权重和即可：
 * int depthSumInverse(vector<NestedInteger>& nestedList) { int res = 0;
 * vector<int> v; for (auto a : nestedList) { helper(a, 0, v); } for (int i =
 * v.size() - 1; i >= 0; --i) { res += v[i] * (v.size() - i); } return res; }
 * void helper(NestedInteger ni, int depth, vector<int> &v) { if (depth >=
 * v.size()) v.resize(depth + 1); if (ni.isInteger()) { v[depth] +=
 * ni.getInteger(); } else { for (auto a : ni.getList()) { helper(a, depth + 1,
 * v); } } }
 * 
 * @author het
 *
 */
// 1+4 //1 +(1+4)+(1+4+6)
// [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2, and one 6 at
// depth 1; 1*3 + 4*2 + 6*1 = 17)
public class LeetCode364 {
	// https://discuss.leetcode.com/topic/49041/no-depth-variable-no-multiplication/
	// Inspired by lzb700m's solution and one of mine. Instead of multiplying by
	// depth, add integers multiple times
	// (by going level by level and adding the unweighted sum to the weighted sum
	// after each level).
	public int depthSumInverse(List<NestedInteger> nestedList) {
		int unweighted = 0, weighted = 0;
		while (!nestedList.isEmpty()) {
			List<NestedInteger> nextLevel = new ArrayList<>();
			for (NestedInteger ni : nestedList) {
				if (ni.isInteger())
					unweighted += ni.getInteger();
				else
					nextLevel.addAll(ni.getList());
			}
			weighted += unweighted;
			nestedList = nextLevel;
		}
		return weighted;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
