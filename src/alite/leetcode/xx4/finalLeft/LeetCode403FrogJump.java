package alite.leetcode.xx4.finalLeft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/frog-jump/
A frog is crossing a river. The river is divided into x units and at each unit there may or 
may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able 
to cross the river by landing on the last stone. Initially, the frog is on the first stone and
 assume the first jump must be 1 unit.
If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units.
 Note that the frog can only jump in the forward direction.
Note:
The number of stones is ≥ 2 and is < 1,100.
Each stone's position will be a non-negative integer < 231.
The first stone's position is always 0.
Example 1:
[0,1,3,5,6,8,12,17]

There are a total of 8 stones.
The first stone at the 0th unit, second stone at the 1st unit,
third stone at the 3rd unit, and so on...
The last stone at the 17th unit.

Return true. The frog can jump to the last stone by jumping 
1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
2 units to the 4th stone, then 3 units to the 6th stone, 
4 units to the 7th stone, and 5 units to the 8th stone.
Example 2:
[0,1,2,3,4,8,9,11]

Return false. There is no way to jump to the last stone as 
the gap between the 5th and 6th stone is too large.

X. DP
http://www.cnblogs.com/grandyang/p/5888439.html
我们也可以用迭代的方法来解，用一个哈希表来建立每个石头和在该位置上能跳的距离之间的映射，建立一个一维dp数组，其中dp[i]表示在位置为i的石头青
蛙的弹跳力(只有青蛙能跳到该石头上，dp[i]才大于0)，由于题目中规定了第一个石头上青蛙跳的距离必须是1，为了跟后面的统一，我们对青蛙
在第一块石头上的弹跳力初始化为0(虽然为0，但是由于题目上说青蛙最远能到其弹跳力+1的距离，所以仍然可以到达第二块石头)。我们用变量k
表示当前石头，然后开始遍历剩余的石头，对于遍历到的石头i，我们来找到刚好能跳到i上的石头k，如果i和k的距离大于青蛙在k上的弹跳力+1，则说明
青蛙在k上到不了i，则k自增1。我们从k遍历到i，如果青蛙能从中间某个石头上跳到i上，我们更新石头i上的弹跳力和最大弹跳力。这样当循
环完成后，我们只要检查最后一个石头上青蛙的最大弹跳力是否大于0即可
    bool canCross(vector<int>& stones) {
        unordered_map<int, unordered_set<int>> m;
        vector<int> dp(stones.size(), 0);
        m[0].insert(0);
        int k = 0;
        for (int i = 1; i < stones.size(); ++i) {
            while (dp[k] + 1 < stones[i] - stones[k]) ++k;
            for (int j = k; j < i; ++j) {
                int t = stones[i] - stones[j];
                if (m[j].count(t - 1) || m[j].count(t) || m[j].count(t + 1)) {
                    m[i].insert(t);
                    dp[i] = max(dp[i], t);
                    break;
                }
            }
        }
        return dp.back() > 0;
    }



更好地做法是对于每个位置记录当前位置能向前走的距离，用一个map套set实现，90ms+
    public boolean canCross(int[] stones) {
        int len = stones.length;
        Map<Integer, HashSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < len; i ++) {
            map.put(stones[i], new HashSet<>());
        }
        map.get(0).add(1);
        for (int i = 0; i < len - 1; i ++) {
            for (int step : map.get(stones[i])) {
                int to = step + stones[i];
                if (to == stones[len - 1]) {
                    return true;
                }
                HashSet<Integer> tmp = map.get(to);
                if (tmp != null) {
                    tmp.add(step);
                    if (step > 1) {
                        tmp.add(step - 1);
                    }
                    tmp.add(step + 1);
                }
            }
        }
        return false;
    }
https://discuss.leetcode.com/topic/59903/very-easy-to-understand-java-solution-with-explanations
Use map to represent a mapping from the stone (not index) to the steps that can be taken from this stone.
so this will be
[0,1,3,5,6,8,12,17]
{17=[], 0=[1], 1=[1, 2], 3=[1, 2, 3], 5=[1, 2, 3], 6=[1, 2, 3, 4], 8=[1, 2, 3, 4], 12=[3, 4, 5]}
Notice that no need to calculate the last stone.
On each step, we look if any other stone can be reached from it, if so, we update that stone's steps by adding step, step + 1, step - 1. If we can reach the final stone, we return true. No need to calculate to the last stone.
    public boolean canCross(int[] stones) {
        if (stones.length == 0) {
         return true;
        }
        
        HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(stones.length);
        map.put(0, new HashSet<Integer>());
        map.get(0).add(1);
        for (int i = 1; i < stones.length; i++) {
         map.put(stones[i], new HashSet<Integer>() );
        }
        
        for (int i = 0; i < stones.length - 1; i++) {
         int stone = stones[i];
         for (int step : map.get(stone)) {
          int reach = step + stone;
          if (reach == stones[stones.length - 1]) {
           return true;
          }
          HashSet<Integer> set = map.get(reach);
          if (set != null) {
              set.add(step);
              if (step - 1 > 0) set.add(step - 1);
              set.add(step + 1);
          }
         }
        }
        
        return false;
    } 
I chose arraylist instead of map (map is better i tihnk), also I think we don't need loop to the stones.length, when current is already bigger than the reach, can prune mostly but still same in worst case.
public boolean canCross(int[] stones) {
    if(stones.length==2) return stones[1]-stones[0]==1;
    List<HashSet<Integer>> dp = new ArrayList<>(stones.length);
    for(int i=0;i<stones.length;i++)
        dp.add(new HashSet<Integer>());

    Iterator<Integer> kSet;
    dp.add(1, new HashSet<>(Arrays.asList(1)));

    for(int i=2;i<stones.length;i++){
        kSet = dp.get(i-1).iterator();
        int j=i, k=-1;
        while((k!=-1||kSet.hasNext())&&j<stones.length){

            if(k==-1) k = kSet.next();
            if(Math.abs(stones[j]-stones[i-1]-k)<=1)  //k reach point
            {
                if(j == stones.length-1) return true;
                HashSet<Integer> temp = dp.get(j);
                temp.add(stones[j]-stones[i-1]);
            }
            else if (j == stones.length-1||stones[j]-stones[i-1]-1>k)
                {k=-1;j=i-1;}
            j++;

        }

    }
    return false;
}
https://discuss.leetcode.com/topic/59423/simple-easy-to-understand-java-solution
public static boolean canCross(int[] stones) {
        Map<Integer, Set<Integer>> stoneMap = new HashMap<>();
        for (int i = 1; i < stones.length; i++) {
            stoneMap.put(stones[i], new HashSet<Integer>());
        }
        if(stones[0]+1 == stones[1]) {
            stoneMap.get(stones[1]).add(1);
        }
        for(int i = 1; i < stones.length; i++) {
            int eachStone = stones[i];
            for(Integer K: stoneMap.get(eachStone)) {
                if(K != 1 &&  stoneMap.containsKey(eachStone + K - 1)) {
                    stoneMap.get(eachStone + K - 1).add(K - 1);
                }
                if(stoneMap.containsKey(eachStone + K)) {
                    stoneMap.get(eachStone + K).add(K);
                }
                if(stoneMap.containsKey(eachStone + K + 1)) {
                    stoneMap.get(eachStone + K + 1).add(K + 1);
                }
            }
        }
        return stoneMap.get(stones[stones.length - 1]).size() >= 1;
    }
https://discuss.leetcode.com/topic/61561/simple-and-easy-understand-java-solution
http://www.voidcn.com/blog/tc_to_top/article/p-6237336.html


裸的n^2做法400ms+，lastPos[i]存到到i位置前可能的步长
    public boolean canCross(int[] stones) {
        int len = stones.length;
        if (len == 2 && stones[1] - stones[0] > 1) {
            return false;
        }
        Set[] lastPos = new Set[len + 1];
        for (int i = 1; i < len; i ++) {
            lastPos[i] = new HashSet<>();
        }
        lastPos[1].add(1);
        for (int i = 2; i < len; i ++) {
            for (int j = 1; j < i; j ++) {
                if (lastPos[j].size() > 0) {
                    int dist = stones[i] - stones[j];
                    if (lastPos[j].contains(dist) || lastPos[j].contains(dist - 1) || lastPos[j].contains(dist + 1)) {
                        lastPos[i].add(dist);
                    }
                }
            }
        }
        return lastPos[len - 1].size() > 0;
    }
public boolean canCross(int[] stones) 
        if (stones == null || stones.length == 0) {
            return false;
        }
        if (stones[1] > 1) {
            return false;
        }

        Set[] lastJump = new Set[stones.length];
        for (int i = 1; i < stones.length; i++) {
            lastJump[i] = new HashSet<Integer>();
        }
        lastJump[1].add(1);
        
        for (int i = 2; i < stones.length; i++) {
            for (int j = 1; j < i; j++) {
                //cell j can be reached
                if (lastJump[j].size() > 0) {
                    int currJump = stones[i] - stones[j];
                    if (lastJump[j].contains(currJump) || 
                        lastJump[j].contains(currJump + 1) ||
                        lastJump[j].contains(currJump - 1)) {
                        lastJump[i].add(currJump);
                    }
                }
            }
        }
        return lastJump[stones.length - 1].size() > 0;
    }

X. DFS
https://discuss.leetcode.com/topic/59439/straight-forward-9ms-7-line-c-solution-with-explanation
Search for the last stone in a depth-first way, prune those exceeding the [k-1,k+1] range. Well, I think the code is simple enough and need no more explanation.
bool canCross(vector<int>& stones, int pos = 0, int k = 0) {
    for (int i = pos + 1; i < stones.size(); i++) {
        int gap = stones[i] - stones[pos];
        if (gap < k - 1) continue;
        if (gap > k + 1) return false;
        if (canCross(stones, i, gap)) return true;
    }
    return pos == stones.size() - 1;
}
This can pass OJ at 9ms but is inefficient for extreme cases. (update: new test cases are added and the solution above no longer passes OJ, please see the solution below which takes 62ms) We can memorize the returns with minimum effort:
unordered_map<int, bool> dp;

bool canCross(vector<int>& stones, int pos = 0, int k = 0) {
    int key = pos | k << 11;

    if (dp.count(key) > 0)
        return dp[key];

    for (int i = pos + 1; i < stones.size(); i++) {
        int gap = stones[i] - stones[pos];
        if (gap < k - 1)
            continue;
        if (gap > k + 1)
            return dp[key] = false;
        if (canCross(stones, i, gap))
            return dp[key] = true;
    }

    return dp[key] = (pos == stones.size() - 1);
}
The number of stones is less than 1100 so pos will always be less than 2^11 (2048).
Stone positions could be theoretically up to 2^31 but k is practically not possible to be that big for the parameter as the steps must start from 0 and 1 and at the 1100th step the greatest valid k would be 1100. So combining pos and k is safe here.


https://discuss.leetcode.com/topic/59337/easy-version-java
public boolean canCross(int[] stones) {
        if(stones[1] > 1) return false;
        if(stones.length == 2) return true;
        return helper(stones, 1, 1);
    }
    private boolean helper(int[] arr, int i, int step){
        boolean pass = false;
        if(i == arr.length-1) return true;
        for(int j = i+1; j < arr.length; j++){
            if(arr[j] <= arr[i] + step + 1 && arr[j] >= arr[i]+step-1){
                pass = pass || helper(arr, j, arr[j] - arr[i]);
            }
        }
        return pass;
    }
http://blog.csdn.net/mebiuw/article/details/52577052
    public boolean canCross(int[] stones) {
        int k = 0;
        return helper(stones, 0, k);
    }

    private boolean helper(int[] stones, int index, int k) {
        //目前已经达到了
        if (index == stones.length - 1) return true;
        //选择k的步伐，范围k-1到k
        for (int i = k - 1; i <= k + 1; i++) {
            int nextJump = stones[index] + i;
            //看接下来有没有合适的石头可以跳过去，从接下来的位置中查找有没有nextJump的位置，有的话返回石头的编号
            int nextPosition = Arrays.binarySearch(stones, index + 1, stones.length, nextJump);
            if (nextPosition > 0) {
                if (helper(stones, nextPosition, i)) return true;
        }
    }

    return false;
}
http://xiadong.info/2016/09/leetcode-403-frog-jump/
虽然tag里说这是个DP题, 但是我觉得更像个图论题. 每个stone是一个node, 根据能否到达来判断有没有边相连, 最终要判断第一个节点与最后一个节点是否连通.
那么既然是这样, 就有DFS和BFS两派了. 用BFS的话要记录抵达每个node的上一跳可能有多远, 同时要记录一个node有没有访问过, 所以比较复杂.

    bool canCross(vector<int>& stones) {

        return canCrossImpl(stones, 0, 0);

    }

    

    bool canCrossImpl(vector<int>& stones, int index, int lastStep){

        for(int i = index + 1; i < stones.size(); i++){

            if(stones[i] - stones[index] < lastStep - 1) continue;

            if(stones[i] - stones[index] > lastStep + 1) return false;

            if(canCrossImpl(stones, i, stones[i] - stones[index])) return true;

        }

        return index == stones.size() - 1;

    }

X. BFS
http://bookshadow.com/weblog/2016/09/18/leetcode-frog-jump/
利用元组(x, y)表示青蛙跳跃的状态：x表示位置, y表示上一跳的单元数。
初始将(0, 0)加入队列q，利用二维数组v记录元组(x, y)是否被访问过。
循环遍历队列q，根据队头状态扩展队列，直到队列为空。
    def canCross(self, stones):
        """
        :type stones: List[int]
        :rtype: bool
        """
        q = collections.deque()
        v = collections.defaultdict(lambda : collections.defaultdict(bool))
        stoneSet = set(stones)
        q.append((0, 0))
        v[0][0] = True
        while q:
            x, y = q.popleft()
            if x == stones[-1]: return True
            for z in (y - 1, y, y + 1):
                if z > 0 and not v[x + z][z] and x + z in stoneSet:
                    v[x + z][z] = True
                    q.append((x + z, z))
        return False


由于这道题只是让我们判断青蛙是否能跳到最后一个石头上，并没有让我们返回所有的路径，这样就降低了一些难度

http://www.georgelyu.me/2016/09/18/7/


at 6:22 PM 
Email This
BlogThis!
Share to Twitter
Share to Facebook
Share to Pinterest

Labels: BFS, DFS, Dynamic Programming, LeetCode, LeetCode - Review, Review, to-do
 * @author het
 *
 */
public class LeetCode403FrogJump {
//	Example 1:
//		[0,1,3,5,6,8,12,17]
//
//		There are a total of 8 stones.
//		The first stone at the 0th unit, second stone at the 1st unit,
//		third stone at the 3rd unit, and so on...
//		The last stone at the 17th unit.
//
//		Return true. The frog can jump to the last stone by jumping 
//		1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
//		2 units to the 4th stone, then 3 units to the 6th stone, 
//		4 units to the 7th stone, and 5 units to the 8th stone.
//		Example 2:
//		[0,1,2,3,4,8,9,11]
	//我们也可以用迭代的方法来解，用一个哈希表来建立每个石头和在该位置上能跳的距离之间的映射，建立一个一维dp数组，
	//其中dp[i]表示在位置为i的石头青蛙的弹跳力(只有青蛙能跳到该石头上，dp[i]才大于0)，由于题目中规定了第一个石头上青蛙跳的距离必须是1，为了跟后面的统一，我们对青蛙在第一块石头上的弹跳力初始化为0(虽然为0，但是由于题目上说青蛙最远能到其弹跳力+1的距离，所以仍然可以到达第二块石头)。我们用变量k表示当前石头，然后开始遍历剩余的石头，对于遍历到的石头i，我们来找到刚好能跳到i上的石头k，如果i和k的距离大于青蛙在k上的弹跳力+1，则说明青蛙在k上到不了i，则k自增1。我们从k遍历到i，如果青蛙能从中间某个石头上跳到i上，我们更新石头i上的弹跳力和最大弹跳力。这样当循环完成后，我们只要检查最后一个石头上青蛙的最大弹跳力是否大于0即可
//	 boolean canCross(vector<int>& stones) {
//	        Map<Integer, unordered_set<int>> m;
//	        vector<int> dp(stones.size(), 0);
//	        m[0].insert(0);
//	        int k = 0;
//	        for (int i = 1; i < stones.size(); ++i) {
//	            while (dp[k] + 1 < stones[i] - stones[k]) ++k;
//	            for (int j = k; j < i; ++j) {
//	                int t = stones[i] - stones[j];
//	                if (m[j].count(t - 1) || m[j].count(t) || m[j].count(t + 1)) {
//	                    m[i].insert(t);
//	                    dp[i] = max(dp[i], t);
//	                    break;
//	                }
//	            }
//	        }
//	        return dp.back() > 0;
//	    }
//	
//	bool canCross(vector<int>& stones) {
//        unordered_map<int, unordered_set<int>> m;
//        vector<int> dp(stones.size(), 0);
//        m[0].insert(0);
//        int k = 0;
//        for (int i = 1; i < stones.size(); ++i) {
//            while (dp[k] + 1 < stones[i] - stones[k]) ++k;
//            for (int j = k; j < i; ++j) {
//                int t = stones[i] - stones[j];
//                if (m[j].count(t - 1) || m[j].count(t) || m[j].count(t + 1)) {
//                    m[i].insert(t);
//                    dp[i] = max(dp[i], t);
//                    break;
//                }
//            }
//        }
//        return dp.back() > 0;
//    }
	
	public static boolean  canCross(int[] stones) {
    if (stones == null || stones.length == 0) {
        return false;
    }
    if (stones[1] > 1) {
        return false;
    }

    Set[] lastJump = new Set[stones.length];
    for (int i = 1; i < stones.length; i++) {
        lastJump[i] = new HashSet<Integer>();
    }
    lastJump[1].add(1);
    
    for (int i = 2; i < stones.length; i++) {
        for (int j = 1; j < i; j++) {
            //cell j can be reached
            if (lastJump[j].size() > 0) {
                int currJump = stones[i] - stones[j];
                if (lastJump[j].contains(currJump) || 
                    lastJump[j].contains(currJump + 1) ||
                    lastJump[j].contains(currJump - 1)) {
                    lastJump[i].add(currJump);
                }
            }
        }
    }
    return lastJump[stones.length - 1].size() > 0;
	}
	
	
//	A frog is crossing a river. The river is divided into x units and at each unit there may or 
//	may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
//	Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able 
//	to cross the river by landing on the last stone. Initially, the frog is on the first stone and
//	 assume the first jump must be 1 unit.
//	If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units.
//	 Note that the frog can only jump in the forward direction.
//	Note:
//	The number of stones is ≥ 2 and is < 1,100.
//	Each stone's position will be a non-negative integer < 231.
//	The first stone's position is always 0.
//	Example 1:
//	[0,1,3,5,6,8,12,17]
//
//	There are a total of 8 stones.
//	The first stone at the 0th unit, second stone at the 1st unit,
//	third stone at the 3rd unit, and so on...
//	The last stone at the 17th unit.
//
//	Return true. The frog can jump to the last stone by jumping 
//	1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
//	2 units to the 4th stone, then 3 units to the 6th stone, 
//	4 units to the 7th stone, and 5 units to the 8th stone.
//	Example 2:
//	[0,1,2,3,4,8,9,11]   ascending order   Initially, the frog is on the first stone and
//	 assume the first jump must be 1 unit.
//
//	Return false. There is no way to jump to the last stone as 
//	the gap between the 5th and 6th stone is too large.
	public static boolean couldCross(int [] positions){
		if(positions == null || positions.length == 0) return true;
		if(positions[1] >1) return false;
		Map<Integer, Integer> map = new HashMap<>();
		for(int i=2;i<positions.length;i+=1){
			map.put(positions[i], i);
		}
		int largestJump = positions[positions.length -1];
		return couldCross(1, 1, positions, new Boolean[largestJump+1][positions.length], map);
	}
	
	
	
	private static  boolean couldCross(int current, int previousStep, int [] positions, Boolean[][] cache, Map<Integer, Integer> map ){
		
		if(current == positions.length - 1) return true;
		
		if(cache[previousStep][current] != null) return cache[previousStep][current] ;
		boolean result = false;
		if(map.containsKey(positions[current]+ previousStep)){
			result |= couldCross(map.get(positions[current]+ previousStep), previousStep, positions, cache, map);
		}
		
		if(!result){
			if(previousStep>1 && map.containsKey(positions[current]+ previousStep-1)){
				result |= couldCross(map.get(positions[current]+ previousStep-1), previousStep-1, positions, cache, map);
			}
		}
		
		
		if(!result){
			if( map.containsKey(positions[current]+ previousStep+1)){
				result |= couldCross(map.get(positions[current]+ previousStep+1), previousStep+1, positions, cache, map);
			}
		}
		
		cache[previousStep][current]  = result;
		return result;
		
		
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       System.out.println(couldCross(new int[]{0,1,3,5,6,8,12,17}));
		//[0,1,3,5,6,8,12,17]
        System.out.println(couldCross(new int[]{ 0,1,2,3,4,8,9,11}));
       
	}

}
