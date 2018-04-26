package alite.leetcode.xx2.sucess.before;
/**
 * Leetcode 216 Combination Sum III

Leetcode NO.216 Combination Sum III - Programming my life - 博客频道 - CSDN.NET
Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used 
and each combination should be a unique set of numbers.
Ensure that numbers within the set are sorted in ascending order.
Example 1: Input: k = 3, n = 7 Output: [[1,2,4]]
Example 2: Input: k = 3, n = 9 Output: [[1,2,6], [1,3,5], [2,3,4]]

https://discuss.leetcode.com/topic/37962/fast-easy-java-code-with-explanation
https://discuss.leetcode.com/topic/15023/accepted-recursive-java-solution-easy-to-understand
The idea is to choose proper number for 1,2..kth position in ascending order, and for each position, we only iterate through (prev_num, n/k]. Time complexity O(k)
shouldn't the complexity be n ln n? (n/k * logk n)
public List<List<Integer>> combinationSum3(int k, int n) {
    int[] num = {1,2,3,4,5,6,7,8,9};
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, new ArrayList<Integer>(), num, k, n,0);
    return result;
    }

public void helper(List<List<Integer>> result, List<Integer> list, int[] num, int k, int target, int start){
    if (k == 0 && target == 0){
        result.add(new ArrayList<Integer>(list));
    } else {
        for (int i = start; i < num.length && target > 0 && k >0; i++){
            list.add(num[i]);
            helper(result, list, num, k-1,target-num[i],i+1);
            list.remove(list.size()-1);
        }
    }
}

public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new  ArrayList<List<Integer>>();
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        dfs(res, tmp ,k , n, 1);
        return res;
    }
    public void dfs( List<List<Integer>> res, List<Integer> tmp, int k, int n, int pos) {
        if (k == 0) {
            if (n == 0)
                res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = pos; i <= n / k && i < 10; i++) {
            tmp.add(i);
            dfs(res, tmp, k - 1, n - i, i + 1);
            tmp.remove(tmp.size() - 1);
        }
    }

http://buttercola.blogspot.com/2015/08/leetcode-combination-sum-iii.html
https://discuss.leetcode.com/topic/26351/simple-and-clean-java-code-backtracking
 public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> ans = new ArrayList<>();
    combination(ans, new ArrayList<Integer>(), k, 1, n);
    return ans;
}

private void combination(List<List<Integer>> ans, List<Integer> comb, int k,  int start, int n) {
 if (comb.size() == k && n == 0) {
  List<Integer> li = new ArrayList<Integer>(comb);
  ans.add(li);
  return;
 }
 for (int i = start; i <= 9; i++) {
  comb.add(i);
  combination(ans, comb, k, i+1, n-i);
  comb.remove(comb.size() - 1);
 }
}

    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> result = new ArrayList<List<Integer>>();

         

        if (k <= 0 || n <= 0) {

            return result;

        }

         

        List<Integer> curr = new ArrayList<Integer>();

        combinationSum3Helper(1, n, 0, k, 0, curr, result);

         

        return result;

    }

     

    private void combinationSum3Helper(int start, int n, int count, int k, int curSum, List<Integer> curr, 

                                       List<List<Integer>> result) {

        if (count > k) {// || curSum>k return

            return;

        }

         

        if (curSum == n && count == k) {

            result.add(new ArrayList<Integer>(curr));

            return;

        }

         

        for (int i = start; i <= 9; i++) {

            if (curSum + i > n) {

                break;

            }

            curr.add(i);

            combinationSum3Helper(i + 1, n, count + 1, k, curSum + i, curr, result);

            curr.remove(curr.size() - 1);

        }

    }
http://www.jyuan92.com/blog/leetcode-combination-sum-iii/

public List<List<Integer>> combinationSum3(int k, int n) {

    List<List<Integer>> res = new LinkedList<List<Integer>>();

    int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    helper(res, new LinkedList<Integer>(), nums, k, n, 0);

    return res;

}


private void helper(List<List<Integer>> res, List<Integer> list,

                   int[] nums, int k, int n, int index) {

    if (n == 0 && list.size() == k) {

        res.add(new LinkedList<Integer>(list));

        return;

    }

    if (n > 0 && list.size() < k) {

        for (int i = index; i < nums.length; i++) {

            list.add(nums[i]);

            helper(res, list, nums, k, n - nums[i], i + 1);

            list.remove(list.size() - 1);

        }

    }

}
http://www.programcreek.com/2014/05/leetcode-combination-sum-iii-java/
public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    List<Integer> list = new ArrayList<Integer>();
    dfs(result, 1, n, list, k);
    return result;
}
 
public void dfs(List<List<Integer>> result, int start, int sum, List<Integer> list, int k){
    if(sum==0 && list.size()==k){
        List<Integer> temp = new ArrayList<Integer>();
        temp.addAll(list);
        result.add(temp);
    }
 
    for(int i=start; i<=9; i++){
        if(sum-i<0) break;
        if(list.size()>k) break;
 
        list.add(i);
        dfs(result, i+1, sum-i, list, k);
        list.remove(list.size()-1);
    }
}
https://segmentfault.com/a/1190000003743112
    List<List<Integer>> res;
    
    public List<List<Integer>> combinationSum3(int k, int n) {
        res = new LinkedList<List<Integer>>();
        List<Integer> tmp = new LinkedList<Integer>();
        helper(k, n, 1, tmp);
        return res;
    }
    
    private void helper(int k, int target, int i, List<Integer> tmp){
        if(target < 0 || k < 0){
            return;
        } else if (target == 0 && k == 0){
            List<Integer> oneComb = new LinkedList<Integer>(tmp);
            res.add(oneComb);
        } else {
            for(int j = i; j <= 9; j++){
                tmp.add(j);
                helper(k-1, target-j, j+1, tmp);
                tmp.remove(tmp.size() - 1);
            }
        }
    }
http://www.tuicool.com/articles/3eqaAvy
    def combinationSum3(self, k, n):
        ans = []
        def search(start, cnt, sums, nums):
            if cnt > k or sums > n:
                return
            if cnt == k and sums == n:
                ans.append(nums)
                return
            for x in range(start + 1, 10):
                search(x, cnt + 1, sums + x, nums + [x])
        search(0, 0, 0, [])
        return ans

http://blog.csdn.net/xudli/article/details/46224943
注意要新建一个list 放入结果中, 否则放入的reference 会指向原来的不断变化的list,    res.add(new ArrayList(cur));
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(k<1 || n<1) return res;
        List<Integer> cur = new ArrayList<Integer>();
        rec(res, cur, 0, k, n, 1);
        return res;
    }
    
    private void rec(List<List<Integer>> res, List<Integer> cur, int sum, int k, int n, int level) {
        if(sum==n && k==0) {
            res.add(new ArrayList(cur));
            return;
        } else if(sum>n || k<=0) return;
        
        for(int i=level; i<=9; i++) {
            cur.add(i);
            rec(res, cur, sum+i, k-1, n, i+1);
            cur.remove(cur.size() - 1);
        }
    }

X.
https://leetcode.com/discuss/37077/just-iterate-all-combinations-of-c-n-k-no-dfs
    int pop(int n) {
        int res = 0;
        for (int i = 0; i < 32; ++i) {
            if(n & (1<<i)) ++res;
        }
        return res;
    }
    vector<vector<int>> combinationSum3(int k, int n) {
        int num;
        vector<vector<int> > res;
        vector<int> item;
        for (int i = 1; i < (1<<9); ++i) {
            if(pop(i) == k) {
                item.clear();
                num = 0;
                for (int j = 0; j < 9; ++j) {
                    if(i & (1 << j)) num += j + 1;
                }
                if(num == n) {
                    for (int j = 0; j < 9; ++j) {
                        if(i & (1 << j)) item.push_back(j + 1);
                    }
                    res.push_back(item);
                }
            }
        }
        return res;
    }
http://zwzbill8.blogspot.com/2015/05/leetcode-combination-sum-iii.html
There are two cases, depending whether we use i or not, where 1 <= i <= 9. And then we solve the problem recursively using updated values of k, n, and i.
    private List<List<Integer>> combinationSum3(int k, int n, int i) {
        ArrayList<List<Integer>> list = 
            new ArrayList<List<Integer>>();
        if (i > 9 || k < 1 || n < i) {
            return list;
        }
        
        if (k == 1 && n == i) {
            ArrayList<Integer> l = new ArrayList<Integer>();
            l.add(i);
            list.add(l);
            return list;
        }
        
        List<List<Integer>> l1 = combinationSum3(k, n, i + 1);
        list.addAll(l1);
        List<List<Integer>> l2 = combinationSum3(k - 1, n - i, i + 1);
        for (List<Integer> ll : l2) {
            ArrayList<Integer> l = new ArrayList<Integer>();
            l.add(i);
            l.addAll(ll);
            list.add(l);
        }
        return list;
    }
    
    public List<List<Integer>> combinationSum3(int k, int n) {
        return combinationSum3(k, n, 1);
    }
https://leijiangcoding.wordpress.com/2015/05/23/leetcode-q216-combination-sum-iii/
 * @author het
 *
 */
public class Leetcode216 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
