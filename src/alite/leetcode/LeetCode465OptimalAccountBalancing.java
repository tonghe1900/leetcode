package alite.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * A group of friends went on holiday and sometimes lent each other money. For example, Alice paid for Bill's lunch for $10. Then later Chris gave Alice $5 for a taxi ride. We can model each transaction as a tuple (x, y, z) which means person x gave person y $z. Assuming Alice, Bill, and Chris are person 0, 1, and 2 respectively (0, 1, 2 are the person's ID), the transactions can be represented as [[0, 1, 10], [2, 0, 5]].
Given a list of transactions between a group of people, return the minimum number of transactions required to settle the debt.
Note:
A transaction will be given as a tuple (x, y, z). Note that x ≠ y and z > 0.
Person's IDs may not be linear, e.g. we could have the persons 0, 1, 2 or we could also have the persons 0, 2, 6.
Example 1:
Input:
[[0,1,10], [2,0,5]]

Output:
2

Explanation:
Person #0 gave person #1 $10.
Person #2 gave person #0 $5.

Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
Example 2:
Input:
[[0,1,10], [1,0,1], [1,2,5], [2,0,5]]

Output:
1

Explanation:
Person #0 gave person #1 $10.
Person #1 gave person #0 $1.
Person #1 gave person #2 $5.
Person #2 gave person #0 $5.

Therefore, person #1 only need to give person #0 $4, and all debt is settled.
记忆化搜索（穷举）
统计每个人借出/借入的金钱总数

将借出金钱的人放入集合rich，借入金钱的人放入集合poor

问题转化为计算从rich到poor的最小“债务连线”数

尝试用rich中的每个金额与poor中的每个金额做匹配

若存在差值，则将差值加入相应集合继续搜索
这道题目似乎是NP Hard
参考资料：http://www.mathmeth.com/tom/files/settling-debts.pdf
    def minTransfers(self, transactions):
        """
        :type transactions: List[List[int]]
        :rtype: int
        """
        vdict = collections.defaultdict(dict)

        def solve(rich, poor):
            rlen, plen = len(rich), len(poor)
            if min(rlen, plen) <= 1:
                return max(rlen, plen)
            ri = pi = 0
            rich.sort()
            poor.sort()
            trich, tpoor = tuple(rich), tuple(poor)
            ans = vdict[trich].get(tpoor)
            if ans is not None:
                return ans
            ans = 0x7FFFFFFF
            for ri in range(rlen):
                for pi in range(plen):
                    nrich = rich[:ri] + rich[ri+1:]
                    npoor = poor[:pi] + poor[pi+1:]
                    if rich[ri] == poor[pi]:
                        ans = min(solve(nrich, npoor) + 1, ans)
                    elif rich[ri] > poor[pi]:
                        ans = min(solve(nrich + [rich[ri] - poor[pi]], npoor) + 1, ans)
                    else:
                        ans = min(solve(nrich, npoor + [poor[pi] - rich[ri]]) + 1, ans)
            vdict[trich][tpoor] = ans
            return ans

        loan = collections.defaultdict(int)
        for s, t, v in transactions:
            loan[s] += v
            loan[t] -= v
        rich = [v for k, v in loan.iteritems() if v > 0]
        poor = [-v for k, v in loan.iteritems() if v < 0]
        return solve(rich, poor)

https://discuss.leetcode.com/topic/68755/share-my-o-n-npc-solution-tle-for-large-case/2
public int minTransfers(int[][] transactions) {
    Map<Integer, Integer> net = new HashMap<>();
    for (int[] t : transactions) {
        net.put(t[0], net.getOrDefault(t[0], 0) - t[2]);
        net.put(t[1], net.getOrDefault(t[1], 0) + t[2]);
    }
    int[] a = new int[net.size()];
    int cnt = 0;
    for (int v : net.values()) {
        if (v != 0) a[cnt++] = v;
    }
    return helper(a, 0, cnt, 0);
}
int helper(int[] a, int start, int n, int num) {
    int ans = Integer.MAX_VALUE;
    while(start < n && a[start] == 0) start++;
    for (int i = start + 1; i < n; ++i) {
        if (a[i] < 0 && a[start] > 0 || a[i] > 0 && a[start] < 0) {
            a[i] += a[start];
            ans = Math.min(ans, helper(a, start + 1, n, num + 1));
            a[i] -= a[start];
        }
    }
    return ans == Integer.MAX_VALUE ? num : ans;
}
X.
https://discuss.leetcode.com/topic/68740/looks-that-this-is-a-wrong-question-the-expected-solution-didn-t-consider-this-case/14
I have found a paper talking about this.
The question can be transferred to a 3-partition problem, which is NP-hard.
http://www.mathmeth.com/tom/files/settling-debts.pdf
http://blog.csdn.net/jmspan/article/details/53280225
https://discuss.leetcode.com/topic/68948/easy-java-solution-with-explanation
方法：用银行结算的思路，首先计算出每个人的最后盈亏，然后用随机的算法找到比较可能的最小值。
in the problem, construct an isolated system. It mean the total amount of money in the system keeps constant. Thus, what matters is the amount of extra money each person have after all transactions complete. For example, if id1 gave id2 5$, then after that transaction id1's money decreased to 5$, on the contrary id2's money increased to 5$. That way, we know how did change account of each person. For imagination let's consider the following input [[1,2,3],[2,3,5], [4,1,6]]:
           id|  trans |  total |
          ---------------------
           1 | -3 + 6 |   +3   |
          ---------------------
           2 | +3 - 5 |   -2   |
         ----------------------
           3 |    +5  |   +5   |
         ----------------------
           4 |    -6  |   -6   |
         ----------------------
Now, we have some negative account changes and positive account changes. By the way it is not hard to see that they compensate each other. Now, our task is to balance the accounts, by performing minimal amount of transactions. For instance we can balance these accounts, by performing the following transactions: [1,2,2], [3,4,5], [1,4,1]. After that, all accounts become balanced, i.e 0 extra money in total. But we have performed 3 transactions. Can we do better? May be. The number of transactions depend on the order of pairs taking part in each transaction. Consequently, the next question is, 'how to know which set of pairs give minimum number of transactions?'. One solution idea is just, brute force through all pairs and just take the minimum number of transactions. Another idea is just take some random combinations of pairs and take the minimum number of trans so far.
P.S: May be there are other elegant and exact solutions and this solution doesn't pretend to the best one, but it is quite reasonable. The more random shuffles you do, the more probability of hitting the answer. For that test cases 1000 is enough, may be less...
From your code here:
                if(n == p) continue;
                if(n>p){
                    stNeg.push(n-p);
                } else {
                    stPos.push(p-n);
                }
there are three cases (let's assume the persons are Person N and Person P):
n == p: P pays N $p
n > p: P pays N $p
n < p: P pays N $n
Basically, P pays N with all money he owns (n >= p), or P pays N in full (n < p).
However, this missed the case that P pays N $x, where $x < p and $x < n.
For example, the first person of (2, 50, 50, ..., 50) could pay $1 to both the first and last person of (51, 50, 50, ..., 50, 51), and get the minimal number of transactions. (This may not be a good example, but you know what I mean.)
I considered only the cases when some of the pairs pays off to close others account. Now, question is what if a pair makes transaction with some x$, where x<p and x< n and achieve minimal amount of transactions? Intuitively, such kind of transaction cannot decrease the total amount of transactions. Because, it doesn't change the state of the isolated system (total money flow doesn't change). But i think it is provable too.
    public int minTransfers(int[][] transactions) {
        if(transactions == null || transactions.length == 0) return 0;
        Map<Integer, Integer> acc = new HashMap<>();
        for(int i = 0;i<transactions.length;i++){
            int id1 = transactions[i][0];
            int id2 = transactions[i][1];
            int m = transactions[i][2];
            acc.put(id1, acc.getOrDefault(id1, 0)-m);
            acc.put(id2, acc.getOrDefault(id2, 0)+m);
        }
        List<Integer> negs = new ArrayList<>();
        List<Integer> poss = new ArrayList<>();
        for(Integer key:acc.keySet()){
            int m = acc.get(key);
            if(m == 0) continue;
            if(m<0) negs.add(-m);
            else poss.add(m);
        }
        int ans = Integer.MAX_VALUE;
        Stack<Integer> stNeg = new Stack<>(), stPos = new Stack<>();
        for(int i =0;i<1000;i++){
            for(Integer num:negs) stNeg.push(num);
            for(Integer num:poss) stPos.push(num);
            int cur = 0;
            while(!stNeg.isEmpty()){
                int n = stNeg.pop();
                int p = stPos.pop();
                cur++;
                if(n == p) continue;
                if(n>p){
                    stNeg.push(n-p);
                } else {
                    stPos.push(p-n);
                }
            }
            ans = Math.min(ans, cur);
            Collections.shuffle(negs);
            Collections.shuffle(poss);
        }
        return ans;
    }
{{1, 8, 1}, {1, 9, 1}, {2, 8, 10}, {3, 9, 20}, {4, 10, 30}, {5, 11, 40}, {6, 12, 50}, {7, 13, 60}, {20, 80, 10}, {30, 90, 20}, {40, 100, 30}, {50, 110, 40}, {60, 120, 50}, {70, 130, 60}}
Yes, I see. Thank you for pointing out! But I think the reason is randomization, not that type of transactions you mentioned. My solution takes 1000 random permutations. But actually, it worth considering all permutations. So in that case the algorithm would run O(n!) time. After i increased the number of iterations up 100000, My solution started to churning out answers from 18 - 14. Therefore I decided to add some simple correction to perform obvious transactions first:
        int base = 0;
        for(Integer key:negInts.keySet()){
            
            if(!posInts.containsKey(key) ||  posInts.get(key) == 0 || negInts.get(key) == 0) continue;
            int p = posInts.get(key);
            int n = negInts.get(key);
            base += Math.min(n,p);
            if(p == n){
                posInts.put(key, 0);
                negInts.put(key, 0);
                continue;
            }
            if(p>n){
                posInts.put(key, p-n);
                negInts.put(key, 0);
                continue;
            }
            if(n>p){
                posInts.put(key, 0);
                negInts.put(key, n-p);
                continue;
            }
        }
        for(Integer key:negInts.keySet()){
            if(negInts.get(key) == 0) continue;
            for(int i = 0;i<negInts.get(key);i++) negs.add(key);
        }
        for(Integer key:posInts.keySet()){
            if(posInts.get(key) == 0) continue;
            for(int i = 0;i<posInts.get(key);i++) poss.add(key);
        }  
The code above first makes transactions where we can balance two accounts by one transaction. It means where n == p. So the rest of the accounts are balanced randomly. Now the code gives correct answers with higher probability.
public class Solution {
    public int minTransfers(int[][] transactions) {
        if(transactions == null || transactions.length == 0) return 0;
        Map<Integer, Integer> acc = new HashMap<>();
        for(int i = 0;i<transactions.length;i++){
            int id1 = transactions[i][0];
            int id2 = transactions[i][1];
            int m = transactions[i][2];
            acc.put(id1, acc.getOrDefault(id1, 0)-m);
            acc.put(id2, acc.getOrDefault(id2, 0)+m);
        }
        // int neg = 0, pos = 0;
        List<Integer> negs = new ArrayList<>();
        List<Integer> poss = new ArrayList<>();
        HashMap<Integer, Integer> negInts = new HashMap<>();
        HashMap<Integer, Integer> posInts = new HashMap<>();
        for(Integer key:acc.keySet()){
            int m = acc.get(key);
            if(m == 0) continue;
            if(m<0) negInts.put(-m, negInts.getOrDefault(-m,0)+1);
            else posInts.put(m, posInts.getOrDefault(m,0)+1);
        }
        int base = 0;
        for(Integer key:negInts.keySet()){
            
            if(!posInts.containsKey(key) ||  posInts.get(key) == 0 || negInts.get(key) == 0) continue;
            int p = posInts.get(key);
            int n = negInts.get(key);
            base += Math.min(n,p);
            if(p == n){
                posInts.put(key, 0);
                negInts.put(key, 0);
                continue;
            }
            if(p>n){
                posInts.put(key, p-n);
                negInts.put(key, 0);
                continue;
            }
            if(n>p){
                posInts.put(key, 0);
                negInts.put(key, n-p);
                continue;
            }
        }
        for(Integer key:negInts.keySet()){
            if(negInts.get(key) == 0) continue;
            for(int i = 0;i<negInts.get(key);i++) negs.add(key);
        }
        for(Integer key:posInts.keySet()){
            if(posInts.get(key) == 0) continue;
            for(int i = 0;i<posInts.get(key);i++) poss.add(key);
        }
        int ans = Integer.MAX_VALUE;
        Stack<Integer> stNeg = new Stack<>(), stPos = new Stack<>();        
        for(int i =0;i<=1000;i++){
            for(Integer num:negs) stNeg.push(num);
            for(Integer num:poss) stPos.push(num);
            int cur = base;
            while(!stNeg.isEmpty()){
                int n = stNeg.pop();
                int p = stPos.pop();
                cur++;
                if(n == p) continue;
                if(n>p){
                    stNeg.push(n-p);
                } else {
                    stPos.push(p-n);
                }
            }
            ans = Math.min(ans, cur);
            Collections.shuffle(negs);
            Collections.shuffle(poss);
        }
        return ans;
    }
}
    public int minTransfers(int[][] transactions) {  
        Map<Integer, Integer> balances = new HashMap<>();  
        for(int[] tran: transactions) {  
            balances.put(tran[0], balances.getOrDefault(tran[0], 0) - tran[2]);  
            balances.put(tran[1], balances.getOrDefault(tran[1], 0) + tran[2]);  
        }  
        List<Integer> poss = new ArrayList<>();  
        List<Integer> negs = new ArrayList<>();  
        for(Map.Entry<Integer, Integer> balance : balances.entrySet()) {  
            int val = balance.getValue();  
            if (val > 0) poss.add(val);  
            else if (val < 0) negs.add(-val);  
        }  
        int min = Integer.MAX_VALUE;  
        Stack<Integer> ps = new Stack<>();  
        Stack<Integer> ns = new Stack<>();  
        for(int i = 0; i < 10; i++) {  
            for(int pos: poss) {  
                ps.push(pos);  
            }  
            for(int neg: negs) {  
                ns.push(neg);  
            }  
            int count = 0;  
            while (!ps.isEmpty()) {  
                int p = ps.pop();  
                int n = ns.pop();  
                if (p > n) {  
                    ps.push(p - n);  
                } else if (p < n) {  
                    ns.push(n - p);  
                }  
                count++;  
            }  
            min = Math.min(min, count);  
            Collections.shuffle(poss);  
            Collections.shuffle(negs);  
        }  
        return min;  
    }  

X. greedy - which not get best solution
https://discuss.leetcode.com/topic/68733/java-recursion
    public int minTransfers(int[][] transactions) {
        //I thought it's kind of network flow problem
        Map<Integer,Integer> map = new HashMap<>();
        for(int[] t : transactions){
            map.put(t[1],map.getOrDefault(t[1],0)-t[2]);
            map.put(t[0],map.getOrDefault(t[0],0)+t[2]);
        }
        int[] count = new int[1];
        helper(map,count);
        return count[0];
    }
//this getMax() and getMin() function is to get the index(key) of the MaxValue and MinValue
    private int getMax(Map<Integer,Integer> map){
        int max = -1;
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(max == -1) max = entry.getKey();
            else if(entry.getValue() > map.get(max)){
                max = entry.getKey();
            }
        }
        return max;
    }
    private int getMin(Map<Integer,Integer> map){
        int min = -1;
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(min == -1) min = entry.getKey();
            else if(entry.getValue() < map.get(min)){
                min = entry.getKey();
            }
        }
        return min;
    }

    private void helper(Map<Integer,Integer> map, int[] count){
        int max = getMax(map);
        int min = getMin(map);
//This means richest one and poorest one are both $0, means balance.
        if(map.get(max) == 0 && map.get(min) == 0) return;
        //Or we get the min abs value of max and min
        int minValue = Math.min(map.get(max),-map.get(min));
       //let the richest give the poorest the as much as possible money
        map.put(max, map.get(max)-minValue);
        map.put(min, map.get(min)+minValue);
      //after done this, add the count
        count[0]++;
        helper(map,count);
    }
http://www.1point3acres.com/bbs/thread-202132-1-1.html
这题是简化债务关系，就是给一堆人的账面交易记录，求最少交易次数使得账面平衡。

这题一般有两个思路，一个是把一个人当做中转站，所有人都跟他交易；第二个思路是把所有人的待结款项算出来，然后排序，用two pointer做。

然而这两个方法都不能保证所有情况下交易次数最少，这题实际上是一个NPC问题，可以归结为，在当前集合待结款项正数和负数两个集合中，找到两个不相交的最小子集，使得二者刚好能够结余。不停地寻找这样的子集，删掉，就能够得到最优。然而 subset sum 是NPC，我没想到这一层，结果跪了。

另外大家平时在做简单题的时候能够注意一下常数优化，比如减少不必要地循环次数，以及内外层循环计算等问题，我面试的时候这个被问了很多次。

假设有A, B, C D, E五个人，债务情况是
A, B, 2
A, C, 4
B, D, 5
C, D, 4
D, E, 7

那么债务结清之后每个人的结余应该是
A, -6
B, -3
C, 0
D, 2
E, 7
. 1point 3acres 璁哄潧
这时候C不用参与支付，省下A, B, D, E需要找到最少的支付次数来达到这个状态。最简单的情况是 A ->B -> D -> E (6, 9, 7)，需要3次支付。但是如果最终的状态是. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
A -3,
B -2,
C, 0
D, 2
E, 3.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
那么只需要两次支付就能达到这个状态A -> E (3), B -> D (2)。

类似的，我们可以把所有结余为负的人看成一个集合，例如{A, B}，同样的，所有结余为正的人看成一个集合{D, E}。对于正的集合，假设我们能找的一个划分，使得划分后的每一个小集合都能对应到负的集合的一个划分中的一个小集合，那么就能减少需要支付的次数。那么此题就转为求两个集合的最大数量的划分，使得划分中的所有子集都能找到另一个集合的划分里的一个子集。

例如集合{A, B}可以划分为{{A}, {B}}。如果能分别对应到{D, E}到划分{{D},{E}}中的两个子集，即A + E = 0 并且B + D = 0，那总的支付次数会少一次。到这里应该就是楼主说的NPC问题了。. 鐣欏鐢宠璁哄潧-涓€浜╀笁鍒嗗湴
回到这道题，我能想到的一个搜索方法的方式是，对于正负两个集合，找到所有的子集的和，然后在这些和中寻找相等的一对子集。比如我们找到A+E = 0和B＋D ＝ 0。那么就可以递归的去求最多的划分次数。这里涉及到重复计算的子问题，可能自底向上更好解决。不知道有没有高手能想出更好的解法

这题的做法就是，算清各自应得/应付账款之后，分为正数负数两个集合，0扔掉，然后在正数里面找最小的子集，负数里面找另一个子集，使得存在两个不等于全集的子集，他们的和是相反数，然后合并这两个集合，这样一定是最优的。而找子集的过程就是subset sum，目前看只能穷举，要是你多项式时间做出来，那就图灵奖了。
    private List<int[]> transactions = new ArrayList<>();
    private List<int[]> finaltransactions = new ArrayList<>();
    private int number = Integer.MAX_VALUE;

    private void mintran(int[] a, int start){

        if(a.length < 2). 1point 3acres 璁哄潧
            return;
        else if(a.length == 2) {
            if(a[0] != 0){
                finaltransactions.add(a);
            }
            return;
        }else{
            int ind = -1;. From 1point 3acres bbs
            int max = Integer.MIN_VALUE;
            int i = start;
            for(; i < a.length; i++){.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
                if(Math.abs(a[i]) > max){. 鍥磋鎴戜滑@1point 3 acres
                    max = Math.abs(a[i]);.1point3acres缃�
                    ind = i;
                }.鏈枃鍘熷垱鑷�1point3acres璁哄潧
            }

            if(max == 0 || start == a.length){
                if(transactions.size() < number){. visit 1point3acres.com for more.
                    finaltransactions = new ArrayList<>(transactions);
                    number = transactions.size();
                }
                return;
            }

            int temp = a[ind];.鏈枃鍘熷垱鑷�1point3acres璁哄潧
            a[ind] = a[start];
            a[start] = temp;

            for(i = start + 1; i < a.length; i++){
                if(a[i] * a[start] < 0) {. 1point 3acres 璁哄潧
                    transactions.add(new int[]{a[i], a[start]});
                    temp = a[i];. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
                    a[i] += a[start];
                    mintran(a, start + 1);. 1point 3acres 璁哄潧
                    a[i] = temp;
                    transactions.remove(transactions.size()-1);
                }
            }

            temp = a[ind];. 1point 3acres 璁哄潧
            a[ind] = a[start];
            a[start] = temp;

        }
    }
payOnce每次产生的list长度应该是lender的个数*borrower的个数，即为a*b, a+b=n (总人数)。在pay函数里，queue的长度应该按a*b为等比数列增加吗？那最后不就是(a*b)^n? 
        class Balance {
                int level;
                List<Integer> lender;
                List<Integer> borrower;
. Waral 鍗氬鏈夋洿澶氭枃绔�,
                Balance(int level, List<Integer> posVals, List<Integer> negVals) {. 1point3acres.com/bbs
                        this.level = level;
                        lender = posVals;
                        borrower = negVals;
                }

                public List<Balance> payOnce() {.1point3acres缃�
                        List<Balance> ans = new ArrayList<>();
                        for (int i = 0; i < lender.size(); i++)
                                for (int j = 0; j < borrower.size(); j++) {. From 1point 3acres bbs
                                        int pos = lender.get(i);
                                        int neg = borrower.get(j);
                                        List<Integer> newLender = new ArrayList<>(lender);
                                        List<Integer> newBorrower = new ArrayList<>(borrower);
                                        newLender.remove(i);
                                        newBorrower.remove(j);. 1point 3acres 璁哄潧

                                        int diff = pos + neg;
                                        if (diff > 0)
                                                newLender.add(pos + neg);
                                        else if (diff < 0)
                                                newBorrower.add(pos + neg);

                                        ans.add(new Balance(level + 1, newLender, newBorrower));
                                }. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
                        return ans;. Waral 鍗氬鏈夋洿澶氭枃绔�,
                }. 鐣欏鐢宠璁哄潧-涓€浜╀笁鍒嗗湴
.鏈枃鍘熷垱鑷�1point3acres璁哄潧
                public boolean balanced() {
                        return lender.isEmpty() && borrower.isEmpty();
                }
.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
                public String toString() {
                        return borrower.toString() + ", " + lender.toString()
                                        + ", # of payments: " + level;
                }
        }
. 涓€浜�-涓夊垎-鍦帮紝鐙鍙戝竷
        public int pay(int[] paid) {
                int n = paid.length;
                long total = 0;.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
                for (int amount : paid) {
                        total += amount;
                }
                if (total % n != 0).1point3acres缃�
                        throw new IllegalArgumentException(
                                        "Total amount can not be evenly divided.");. 鐣欏鐢宠璁哄潧-涓€浜╀笁鍒嗗湴
                int avg = (int) total / n;

                List<Integer> pos = new ArrayList<>();
                List<Integer> neg = new ArrayList<>();
                for (int amount : paid) {
                        int diff = amount - avg;
                        if (diff > 0)
                                pos.add(diff);
                        else if (diff < 0)
                                neg.add(diff);
                }
. From 1point 3acres bbs
                Balance initialBalance = new Balance(0, pos, neg);
                Queue<Balance> queue = new LinkedList<>();
                queue.offer(initialBalance);
                int ans = Integer.MAX_VALUE; 鏉ユ簮涓€浜�.涓夊垎鍦拌鍧�. 
                while (true) {
                        Balance cur = queue.poll();
                        System.out.println(cur);
                        if (cur.balanced()) {. 1point 3acres 璁哄潧
                                ans = cur.level;
                                break;
                        }
                        List<Balance> nextLevel = cur.payOnce();
                        for (Balance newBalance : nextLevel)
                                queue.offer(newBalance);. Waral 鍗氬鏈夋洿澶氭枃绔�,
                }

                return ans;
        }
 * @author het
 *
 */
public class LeetCode465OptimalAccountBalancing {
	public int minTransfers(int[][] transactions) {
	    Map<Integer, Integer> net = new HashMap<>();
	    for (int[] t : transactions) {
	        net.put(t[0], net.getOrDefault(t[0], 0) - t[2]);
	        net.put(t[1], net.getOrDefault(t[1], 0) + t[2]);
	    }
	    int[] a = new int[net.size()];
	    int cnt = 0;
	    for (int v : net.values()) {
	        if (v != 0) a[cnt++] = v;
	    }
	    return helper(a, 0, cnt, 0);
	}
	int helper(int[] a, int start, int n, int num) {
	    int ans = Integer.MAX_VALUE;
	    while(start < n && a[start] == 0) start++;
	    for (int i = start + 1; i < n; ++i) {
	        if (a[i] < 0 && a[start] > 0 || a[i] > 0 && a[start] < 0) {
	            a[i] += a[start];
	            ans = Math.min(ans, helper(a, start + 1, n, num + 1));
	            a[i] -= a[start];
	        }
	    }
	    return ans == Integer.MAX_VALUE ? num : ans;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
