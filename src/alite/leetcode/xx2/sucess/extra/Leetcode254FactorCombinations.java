package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.List;

/**
 * Numbers can be regarded as product of its factors. For example,
8 = 2 x 2 x 2;
  = 2 x 4.
Write a function that takes an integer n and return all possible combinations of its factors.
Note:

Each combination’s factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
You may assume that n is always positive.
Factors should be greater than 1 and less than n.
For example:Input: 12
Output: [[2, 2, 3], [2, 6], [3, 4]]
Input: 15
Output: [[3, 5]]
Input: 28
Output: [[2, 2, 7], [2, 14], [4, 7]]

X. 典型DFS做法，注意避免重复。
截止到一半，不然的话就会重复，例如12*2 |   2*12
go to depth=》 number/factor
不能除尽就立刻添加到res里面去，注意6*4  6*2*2怎么实现的。这个是很有意思的地方。不停地迭代factor的大小。
http://www.cnblogs.com/airwindow/p/4822572.html
This problem is not hard, could be easily solved through DFS method. However, I came up with a very complicated method initially.
Initial Idea: each factor must be consisted through multi prime numbers, thus we could dissect the "n" into a collection of prime numbers. 
Step 1: compute the collection of prime numbers for n. (which must be unique).
Step 2: use the elements in the collection to generate various combinations.

The above idea is right, but it is too complex. According to problem, as long as the factor is not 1, we could include it. Thus, we not we do this?
Considering the process of dissecting a number:
(((2) * 3 ) * 5 )* 6 = 180

Suppose our target is 180, we can first chop out factor 2. then our target becomes 90.
Then we can continue the process, until our target reachs 1. (which means we fully factorize it). Since this problem asks us to compute all such combinations, we should also try to chop out factor "((2) * 3 )" "(((2) * 3 ) * 5 )". This process could be elegantly achieved through: 
<At present, we are in "helper(ret, item, n, i)">
for (int i = start; i <= n; i++) {
    if (n % i == 0) {
        ...
        helper(ret, item, n/i, i);
        ...
    }
}
So elegantly, right?
a. for (int i = start; i <= n; i++), searches through all possible numbers through start to n.
Note: you may ask since "1" is not allowed in the combination, why we allow i <= n. Even n*1 is allowed, but in the recursive process, it no longer the n as the initial one. 2(current n) of 4(inital n), apparently we should allow i to be n, otherwise, we would never reach the base case.
-------------------------------------------------------
if (n == 1) {
    if (item.size() > 1) {
        ret.add(new ArrayList<Integer> (item));
    }
    return;
}
-------------------------------------------------------
Note the case of "n * 1" would never include in to the ret set, since we require "item.size()" must larger than 1. 
So elegant and smart? Right!!!! 
Also take advantage of item's size!!!! Great Programming skill!


b. (n % i == 0), guarantees the current i is a factor. 

c. helper(ret, item, n/i, i), makes us to continue the search with updated target "n/i".
Note: since the same factor could appears repeatedlly, we should start from "i" rather than "i+1".
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> ret = new ArrayList<List<Integer>> ();
        helper(ret, new ArrayList<Integer> (), n, 2);
        return ret;
    }
    
    private void helper(List<List<Integer>> ret, List<Integer> item, int n, int start) {
        if (n == 1) {
            if (item.size() > 1) {
                ret.add(new ArrayList<Integer> (item));
            }
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                item.add(i);
                helper(ret, item, n/i, i);
                item.remove(item.size()-1);
            }
        }
    }

http://www.cnblogs.com/yrbbest/p/5014873.html
Update: 使用@yuhangjiang的方法，只用计算 2到sqrt(n)的这么多因子，大大提高了速度。
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res =  new ArrayList<>();
        if (n <= 1) return res;
        getFactors(res, new ArrayList<>(), n, 2);
        return res;
    }
    
    private void getFactors(List<List<Integer>> res, List<Integer> list, int n, int pos) {
        for (int i = pos; i <= Math.sqrt(n); i++) {
            if (n % i == 0 && n / i >= i) {
                list.add(i);
                list.add(n / i);
                res.add(new ArrayList<>(list));
                list.remove(list.size() - 1);
                getFactors(res, list, n / i, i);
                list.remove(list.size() - 1);
            }
        }
    }
https://discuss.leetcode.com/topic/24603/a-simple-java-solution
great solution! Math.sqrt() check saves a lot of time. I used to have n/2 check, with 22ms, now I have running time down to 3ms. 
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    if (n <= 3) return result;
    helper(n, -1, result, new ArrayList<Integer>());
    return result; 
}

public void helper(int n, int lower, List<List<Integer>> result, List<Integer> cur) {
    if (lower != -1) {
        cur.add(n);
        result.add(new ArrayList<Integer>(cur));
        cur.remove(cur.size() - 1);
    }
    int upper = (int) Math.sqrt(n);
    for (int i = Math.max(2, lower); i <= upper; ++i) {
        if (n % i == 0) {
            cur.add(i);
            helper(n / i, i, result, cur);
            cur.remove(cur.size() - 1);
        }
    }
}

https://leetcode.com/discuss/51250/my-recursive-dfs-java-solution
var i can go up to n/2 not n.

public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, new ArrayList<Integer>(), n, 2);
    return result;
}

public void helper(List<List<Integer>> result, List<Integer> item, int n, int start){
    if (n <= 1) {
        if (item.size() > 1) {
            result.add(new ArrayList<Integer>(item));
        }
        return;
    }

    for (int i = start; i <= n; ++i) {
        if (n % i == 0) {
            item.add(i);
            helper(result, item, n/i, i);
            item.remove(item.size()-1);
        }
    }
}
https://hzhou.me/LeetCode/LeetCode-Factor-Combinations.html
 public List<List<Integer>> getFactors(int n) {
      Set<List<Integer>> result = new HashSet<>();

      int dist = (int) Math.sqrt(n);

      for (int i = 2; i <= dist; i++) {
          if (n % i == 0) {
              List<List<Integer>> tmp = helper(n / i);
              for (List<Integer> l : tmp) {
                  l.add(i);
                  Collections.sort(l);
                  result.add(l);
              }
          }
      }
      return new ArrayList<>(result);
  }

  public List<List<Integer>> helper(int n) {
      List<List<Integer>> result = new ArrayList<>();

      List<Integer> t = new ArrayList<>();
      t.add(n);
      result.add(t);

      int dist = (int) Math.sqrt(n);

      for (int i = 2; i <= dist; i++) {
          if (n % i == 0) {
              List<List<Integer>> tmp = helper(n / i);
              for (List<Integer> l : tmp) {
                  l.add(i);
                  result.add(l);
              }
          }
      }
      return result;
  }

https://github.com/kamyu104/LeetCode/blob/master/C++/factor-combinations.cpp
http://shibaili.blogspot.com/2015/08/notes-from-others.html
https://github.com/shifuxu/leetcode/blob/master/Factor-Combinations.java
    public List<List<Integer>> getFactors(int n) {
        // classic backtracking strategy
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> sol = new ArrayList<Integer>();
        // the reason why we need the start here is to keep the order of the sequence
        helper(res, sol, n, 2);
        return res;
    }
  
    private static void helper(List<List<Integer>> res, List<Integer> sol, int n, int start) {
        if (n == 1) {
            if (sol.size() > 1) {
                res.add(new ArrayList<Integer>(sol));
            }
            return ;
        }
      
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                sol.add(i);
                helper(res, sol, n / i, i);
                sol.remove(sol.size() - 1);
            }
        }
    }
http://leetcode.tgic.me/factor-combinations/index.html
    List<List<Integer>> getFactors(int n, int low, int high) {
        List<List<Integer>> found = new ArrayList<>();

        if(low <= n && n < high){
            found.add(Arrays.asList(n));
        }

        for(int i = low; n / i >= low; i++){
            if(n % i == 0){
                for(List<Integer> sub : getFactors(n / i, i, n)){
                    List<Integer> l = new ArrayList<>();
                    l.add(i);
                    l.addAll(sub);
                    found.add(l);
                }
            }
        }

        return found;
    }

    public List<List<Integer>> getFactors(int n) {
        return getFactors(n, 2, n);
    }
https://leetcode.com/discuss/51250/my-recursive-dfs-java-solution
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, new ArrayList<Integer>(), n, 2);
    return result;
}

public void helper(List<List<Integer>> result, List<Integer> item, int n, int start){
    if (n <= 1) {
        if (item.size() > 1) {
            result.add(new ArrayList<Integer>(item));
        }
        return;
    }

    for (int i = start; i <= n; ++i) {
        if (n % i == 0) {
            item.add(i);
            helper(result, item, n/i, i);
            item.remove(item.size()-1);
        }
    }
}
http://likesky3.iteye.com/blog/2238597
依次判断2到n的平方根是否能被n整除，如果可以整除则当前 i 和 n / i是一个可行解，然后递归获取 n / i的所有因子组合，它们将与 i 一起组合成 n 的因子组合。 
为避免得到重复解并满足因子组合各项从小到大排列，有两个注意点： 
1）如果 i * i > n / i，无需递归，因为 n / i分解所得因子必然小于i，不符合要求。 
2）递归 n / i时最小因子要从 i开始 
    public List<List<Integer>> getFactors(int n) {  
        return getFactorsWithStartParam(n, 2);  
    }  
    public List<List<Integer>> getFactorsWithStartParam(int n, int start) {  
        List<List<Integer>> ret = new ArrayList<List<Integer>>();  
        if (n < 4) return ret;  
        int sqrt = (int)Math.sqrt(n);  
        for (int i = start; i <= sqrt; i++) {  
            if (n % i == 0) {  
                int factor2 = n / i;  
                List<Integer> item = new ArrayList<Integer>();  
                item.add(i);  
                item.add(factor2);  
                ret.add(item);  
                if (i * i <= factor2) {// avoid get smaller factor than i  
                    for (List<Integer> subitem : getFactorsWithStartParam(factor2, i)) {// avoid get smaller factor than i  
                        subitem.add(0, i);  
                        ret.add(subitem);  
                    }  
                }  
            }  
        }  
        return ret;  
    }  


vector<vector<int>> factors_comb(int n) {


vector<vector<int>> ret = {};    


function<void(int, vector<int>&)> dfs = [&](int num, vector<int>& cur) {


int last = cur.empty() ? 2 : cur.back();


for(int f = last; f < num; ++f) {


if(num % f != 0) continue;


cur.push_back(f);


dfs(num / f, cur);


cur.pop_back();


}


if(!cur.empty() && num >= last) {


cur.push_back(num);


ret.push_back(cur);


cur.pop_back();


}


};    


vector<int> cur = {};


dfs(n, cur);


return ret;


}

X. http://sbzhouhao.net/LeetCode/LeetCode-Factor-Combinations.html
   public List<List<Integer>> getFactors(int n) {
        Set<List<Integer>> result = new HashSet<>();

        int dist = (int) Math.sqrt(n);

        for (int i = 2; i <= dist; i++) {
            if (n % i == 0) {
                List<List<Integer>> tmp = helper(n / i);
                for (List<Integer> l : tmp) {
                    l.add(i);
                    Collections.sort(l);
                    result.add(l);
                }
            }
        }
        return new ArrayList<>(result);
    }

    public List<List<Integer>> helper(int n) {
        List<List<Integer>> result = new ArrayList<>();

        List<Integer> t = new ArrayList<>();
        t.add(n);
        result.add(t);

        int dist = (int) Math.sqrt(n);

        for (int i = 2; i <= dist; i++) {
            if (n % i == 0) {
                List<List<Integer>> tmp = helper(n / i);
                for (List<Integer> l : tmp) {
                    l.add(i);
                    result.add(l);
                }
            }
        }
        return result;
    }

Not good: https://hzhou.me/LeetCode/LeetCode-Factor-Combinations.html
Related - variant:
http://www.shuatiblog.com/blog/2015/02/13/combination-of-factors/
打印一个数的所有乘数组合，从大到小，不要有重复
Print all unique combinations of factors of a positive integer. For example given 24:
24*1
12*2
8*3
6*4
6*2*2
4*3*2
3*2*2*2

public List<List<Integer>> factorCombinations(int n) {
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    helper(ans, n, n / 2, new ArrayList<Integer>()); //\\
    return ans;
}

private void helper(List<List<Integer>> ans, int num, int largestFactor,
        List<Integer> path) {
    if (num == 1) {
        ans.add(new ArrayList<Integer>(path));
        return;
    }
    for (int i = largestFactor; i > 1; i--) {
        if (num % i == 0) {
            path.add(i);
            helper(ans, num / i, i, path);
            path.remove(path.size() - 1);
        }
    }
}
http://leetcode0.blogspot.com/2015/12/254-factor-combinations.html

https://discuss.leetcode.com/topic/21086/iterative-and-recursive-python
Iterative:
def getFactors(self, n):
    todo, combis = [(n, 2, [])], []
    while todo:
        n, i, combi = todo.pop()
        while i * i <= n:
            if n % i == 0:
                combis += combi + [i, n/i],
                todo += (n/i, i, combi+[i]),
            i += 1
    return combis
Recursive:
def getFactors(self, n):
    def factor(n, i, combi, combis):
        while i * i <= n:
            if n % i == 0:
                combis += combi + [i, n/i],
                factor(n/i, i, combi+[i], combis)
            i += 1
        return combis
    return factor(n, 2, [], [])
Read full article from Factor Combinations | tech::interview
 * @author het
 *
 */
public class Leetcode254FactorCombinations {
	
	public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> ret = new ArrayList<List<Integer>> ();
        helper(ret, new ArrayList<Integer> (), n, 2);
        return ret;
    }
    
    private void helper(List<List<Integer>> ret, List<Integer> item, int n, int start) {
        if (n == 1) {
            if (item.size() > 1) {
                ret.add(new ArrayList<Integer> (item));
            }
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                item.add(i);
                helper(ret, item, n/i, i);
                item.remove(item.size()-1);
            }
        }
    }
    
    // 2
    
    public List<List<Integer>> getFactors1(int n) {
        List<List<Integer>> res =  new ArrayList<>();
        if (n <= 1) return res;
        getFactors1(res, new ArrayList<Integer>(), n, 2);
        return res;
    }
    
    private void getFactors1(List<List<Integer>> res, List<Integer> list, int n, int pos) {
        for (int i = pos; i <= Math.sqrt(n); i++) {
            if (n % i == 0 && n / i >= i) {
                list.add(i);
                list.add(n / i);
                res.add(new ArrayList<>(list));
                list.remove(list.size() - 1);
                getFactors1(res, list, n / i, i);
                list.remove(list.size() - 1);
            }
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
