package alite.leetcode.xx2.sucess.before;
/**
 * [LeetCode]Different Ways to Add Parentheses | 书影博客
Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and *.
Example 1
Input: "2-1-1".
((2-1)-1) = 0  (2-(1-1)) = 2
Output: [0, 2]
X. 分治法（Divide and conquer），递归地将表达式按照运算符拆分为左右两半
http://www.cnblogs.com/yrbbest/p/5006196.html
Time Complexity - O(3n)， Space Complexity - O(3n)
http://likesky3.iteye.com/blog/2231382
思路类似Unique Binary Search Trees II，以某个运算符为分界线，递归计算左右两边可能的值，然后根据当前运算符归并结果。纯粹的递归含有冗余计算，可同时保留中间结果来提高效率。 
http://codechen.blogspot.com/2015/07/leetcode-different-ways-to-add.html
https://leetcode.com/discuss/48477/a-recursive-java-solution-284-ms

直接使用递归, 通过operator作为分割点, 对左右expr进行recursion得到的list来for loop得到当前expression的解并放入到结果list.
需要注意边界条件.

This is a nice and clean solution. However, note that there are many overlapping sub-problems, dynamic programming should be a potential way to improve the performance.
Using a map to memorize would be better.


    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<Integer>();
        if (input == null || input.length() == 0) {
            return result;
        }
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '+' && c != '-' && c != '*') {
                continue;
            }
            
            List<Integer> part1Result = 
                diffWaysToCompute(input.substring(0, i));
            List<Integer> part2Result = 
                diffWaysToCompute(input.substring(i + 1, input.length()));
            
            for (Integer m : part1Result) {
                for (Integer n : part2Result) {
                    if (c == '+') {
                        result.add(m + n);
                    } else if (c == '-') {
                        result.add(m - n);
                    } else if (c == '*') {
                        result.add(m * n);
                    }
                }
            }
        }
        
        if (result.size() == 0) {
            result.add(Integer.parseInt(input));
        }
        
        return result;
    }
https://discuss.leetcode.com/topic/19901/a-recursive-java-solution-284-ms/12
    Map<String, List<Integer>> map = new HashMap<>();
    
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '-' || c == '*') {
                String p1 = input.substring(0, i);
                String p2 = input.substring(i + 1);
                List<Integer> l1 = map.getOrDefault(p1, diffWaysToCompute(p1));
                List<Integer> l2 = map.getOrDefault(p2, diffWaysToCompute(p2));
                for (Integer i1 : l1) {
                    for (Integer i2 : l2) {
                        int r = 0;
                        switch (c) {
                            case '+':
                                r = i1 + i2;
                                break;
                            case '-':
                                r = i1 - i2;
                                break;
                            case '*':
                                r = i1 * i2;
                                break;
                        }
                        res.add(r);
                    }
                }
            }
        }
        if (res.size() == 0) {
            res.add(Integer.valueOf(input));
        }
        map.put(input, res);
        return res;
    }
https://leetcode.com/discuss/74276/my-recursive-java-solution
-- better to use expression to detect there is no operator.
    List<Integer> list=new ArrayList();
    if(input==null||input.length()==0) return list;
    if(!input.contains("+")&&!input.contains("-")&&!input.contains("*")) {//\\
        list.add(Integer.valueOf(input));//\\
        return list;//\\
    }
    for(int i=0;i<input.length();i++){
         char ops=input.charAt(i);
         if(ops=='+'||ops=='-'||ops=='*'){
            List<Integer> leftList=diffWaysToCompute(input.substring(0,i));
            List<Integer> rightList=diffWaysToCompute(input.substring(i+1,input.length()));
            for(int leftValue:leftList){
                for(int rightValue:rightList){
                    switch(ops){
                        case '+': list.add(leftValue+rightValue); break;
                        case '-': list.add(leftValue-rightValue); break;
                        case '*': list.add(leftValue*rightValue); break;
                    }
                }
            }
         }
      }
    return list;
}
https://leetcode.com/discuss/61840/java-recursive-9ms-and-dp-4ms-solution
I think it's more efficient to pre-parse the string because String.substring() is costly. I store the parsed string in a list, for example, if the string is 1+2+3+4, then the list will contain:
"1", "+", "2", "+", "3", "+", "4"
Personally I feel this is also more convenient because all integers occurs at even indices (0, 2, 4, 6) and all operators are at odd indices (1, 3, 5).
Then the problem is very similar to "Unique Binary Search Trees II". For each operator in the list, we compute all possible results for entries to the left of that operator, which is List<Integer> left, and also all possible results for entries to the right of that operator, namely List<Integer> right, and combine the results. It can be achieved by recursion or more efficiently by dp.
public List<Integer> diffWaysToCompute(String input) {
    List<Integer> result=new ArrayList<>();
    if(input==null||input.length()==0)  return result;
    List<String> ops=new ArrayList<>();
    for(int i=0; i<input.length(); i++){
        int j=i;
        while(j<input.length()&&Character.isDigit(input.charAt(j)))
            j++;
        String num=input.substring(i, j);
        ops.add(num);
        if(j!=input.length())   ops.add(input.substring(j, j+1));
        i=j;
    }
    result=compute(ops, 0, ops.size()-1);
    return result;
}
private List<Integer> compute(List<String> ops, int lo, int hi){
    List<Integer> result=new ArrayList<>();
    if(lo==hi){
        Integer num=Integer.valueOf(ops.get(lo));
        result.add(num);
        return result;
    }
    for(int i=lo+1; i<=hi-1; i=i+2){
        String operator=ops.get(i);
        List<Integer> left=compute(ops,lo, i-1), right=compute(ops, i+1, hi);
        for(int leftNum:left)
            for(int rightNum: right){
                if(operator.equals("+"))
                    result.add(leftNum+rightNum);
                else if(operator.equals("-"))
                    result.add(leftNum-rightNum);
                else
                    result.add(leftNum*rightNum);
            }
    }
    return result;
}
X. 使用memorization的recursion
https://leetcode.com/discuss/48490/java-recursive-solution-with-memorization
public List<Integer> diffWaysToCompute(String input) {
    //cache for memorization
    HashMap<String,List<Integer>> cache = new HashMap<String,List<Integer>>();
    return this.helper(input,cache);
}

List<Integer>helper(String s, HashMap<String,List<Integer>> cache) {
    if (cache.get(s)!=null) {
        return cache.get(s);
    }
    boolean expression = false; // this is clearer
    ArrayList<Integer> result = new ArrayList<Integer>();
    for(int i=0; i<s.length(); i++) {
        if("+-*".indexOf(s.charAt(i))!=-1) {
            List<Integer> left = helper(s.substring(0,i),cache);
            List<Integer> right = helper(s.substring(i+1),cache);
            for(Integer l: left) {
                for(Integer r: right) {
                    result.add(cal(l,r,s.charAt(i)));
                }
            }
            expression = true;
        }
    }
    if (!expression) { // base case, there is only number no operator.
        result.add(Integer.parseInt(s));
    }
    cache.put(s, result);
    return result;
}
int cal(int l, int r, char op) {
    int result = 0;
    switch (op) {
        case '+': result= l+r; break;
        case '-': result = l-r; break;
        case '*': result= l*r; break;
        default: break;
    }
    return result;
}



https://fxrcode.gitbooks.io/leetcodenotebook/content/Leetcode_Medium/different_ways_to_add_parentheses.html
很明显: 每一个表达式很有可能在左右都遇到, 如果左边的同一个表达式已经计算过了, 那么右边出现的同样表示就直接返回结果了. 即memorization的recursion写法. 可以加速一点.
-- too many substring
public List<Integer> diffWaysToCompute(String input) {
    Map<String, List<Integer>> cache = new HashMap<>();
    return helper(input, cache);
  }

private List<Integer> helper(String s, Map<String, List<Integer>> cache) {
    if (cache.get(s) != null) {
      return cache.get(s);  // 这里就发现为什么map定义成List而不是Integer了: 因为可以直接return了.
    }
    boolean expr = false;
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < s.length(); ++i) {
      if ("+-*".indexOf(s.charAt(i)) != -1) {
        for (int left : helper(s.substring(0, i), cache)) {
        for (int right : helper(s.substring(i + 1), cache)) {
            result.add(s.charAt(i) == '+' ? left + right
                : s.charAt(i) == '-' ? left - right : left * right);
        }
        }
        expr = true;
      }
    }
    if (!expr) result.add(Integer.parseInt(s));
    cache.put(s, result);
    return result;
}
http://likesky3.iteye.com/blog/2231382
    public List<Integer> diffWaysToCompute(String input) {  
        HashMap<String, List<Integer>> dpMap = new HashMap<String, List<Integer>>();  
        return computeWithDP(input, dpMap);  
    }  
    public List<Integer> computeWithDP(String input, HashMap<String, List<Integer>> dpMap) {  
        List<Integer> result = new ArrayList<Integer>();  
        if (input == null || input.length() == 0) return result;  
        int N = input.length();  
        for (int i = 0; i < N; i++) {  
            char c = input.charAt(i);  
            if (c == '+' || c == '-' || c == '*') {  
                String leftSubStr = input.substring(0, i);  
                String rightSubStr = input.substring(i + 1, N);  
                List<Integer> left = dpMap.get(leftSubStr);  
                if (left == null)  
                    left = computeWithDP(leftSubStr, dpMap);  
                List<Integer> right = dpMap.get(rightSubStr);  
                if (right == null)  
                    right = computeWithDP(rightSubStr, dpMap);  
                for (int op1: left) {  
                    for (int op2: right) {  
                        if (c == '+')  
                            result.add(op1 + op2);  
                        else if (c == '-')  
                            result.add(op1 - op2);  
                        else  
                            result.add(op1 * op2);  
                    }  
                }  
            }  
        }  
        //if the string contains only number  
        if (result.isEmpty())  
            result.add(Integer.parseInt(input));  
        dpMap.put(input, result);  
        return result;  
    }  

http://taoalpha.me/blog/2016/01/31/oj-oj-parentheses-problems/
https://leetcode.com/discuss/48488/c-4ms-recursive-%26-dp-solution-with-brief-explanation
https://leetcode.com/discuss/64418/5ms-java-recursive-with-cache-hashmap
public List<Integer> diffWaysToCompute(String input) {
    Map<String, List<Integer>> cache = new HashMap<>();
    return diffWaysToCompute(input, cache);
}

private List<Integer> diffWaysToCompute(String input, Map<String, List<Integer>> cache) {
    if(cache.containsKey(input)) return cache.get(input);

    List<Integer> vals = new ArrayList<Integer>();
    for(int i=0; i<input.length(); i++) {
        if(!operators.contains(input.charAt(i))) continue;

        char operator = input.charAt(i);
        List<Integer> leftVals = diffWaysToCompute(input.substring(0,i), cache);
        List<Integer> rightVals = diffWaysToCompute(input.substring(i+1), cache);

        for(Integer left : leftVals) {
            for(Integer right : rightVals) {
                if(operator == '+') {
                    vals.add(left + right);
                } else if(operator == '-') {
                    vals.add(left - right);
                } else if(operator == '*') {
                    vals.add(left*right);
                }
            }
        }
    }

    // number only
    if(vals.size() == 0) {
        vals.add(Integer.parseInt(input));
    }

    cache.put(input, vals);
    return vals;
}
X. DP
https://discuss.leetcode.com/topic/26076/java-recursive-9ms-and-dp-4ms-solution
I think it's more efficient to pre-parse the string because String.substring() is costly. I store the parsed string in a list, for example, if the string is 1+2+3+4, then the list will contain:
"1", "+", "2", "+", "3", "+", "4"
Personally I feel this is also more convenient because all integers occurs at even indices (0, 2, 4, 6) and all operators are at odd indices (1, 3, 5).
Then the problem is very similar to "Unique Binary Search Trees II". For each operator in the list, we compute all possible results for entries to the left of that operator, which is List<Integer> left, and also all possible results for entries to the right of that operator, namely List<Integer> right, and combine the results. It can be achieved by recursion or more efficiently by dp.
And DP, where dp[i][j] stores all possible results from the i-th integer to the j-th integer (inclusive) in the list.
public List<Integer> diffWaysToCompute(String input) {
    List<Integer> result=new ArrayList<>();
    if(input==null||input.length()==0)  return result;
    List<String> ops=new ArrayList<>();
    for(int i=0; i<input.length(); i++){
        int j=i;
        while(j<input.length()&&Character.isDigit(input.charAt(j)))
            j++;
        ops.add(input.substring(i, j));
        if(j!=input.length())   ops.add(input.substring(j, j+1));
        i=j;
    }
    int N=(ops.size()+1)/2; //num of integers
    ArrayList<Integer>[][] dp=(ArrayList<Integer>[][]) new ArrayList[N][N];
    for(int d=0; d<N; d++){
        if(d==0){
            for(int i=0; i<N; i++){
                dp[i][i]=new ArrayList<>();
                dp[i][i].add(Integer.valueOf(ops.get(i*2)));//\\
            }
            continue;
        }
        for(int i=0; i<N-d; i++){
            dp[i][i+d]=new ArrayList<>();
            for(int j=i; j<i+d; j++){
                ArrayList<Integer> left=dp[i][j], right=dp[j+1][i+d];
                String operator=ops.get(j*2+1);
                for(int leftNum:left)
                    for(int rightNum:right){
                        if(operator.equals("+"))
                            dp[i][i+d].add(leftNum+rightNum);
                        else if(operator.equals("-"))
                            dp[i][i+d].add(leftNum-rightNum);
                        else
                            dp[i][i+d].add(leftNum*rightNum);
                    }
            }
        }
    }
    return dp[0][N-1];
}

既然上面已经写出了recursion with memorization了. 那么DP怎么写呢?
定义state: 即dp[i][j], 表示[i...j]之间所能得到的所有可能值.
这种分段型的dp都是外层loop循环段的长度, 然后i,j分别表示起点,终点. 这里的细节要熟练.
public List<Integer> diffWaysToComputeBest(String input) {
    List<Integer> result = new ArrayList<>();
    if (input == null || input.length() == 0) return result;
    List<String> ops = new ArrayList<>();
    // 先是将所有数字和'+-*'放到list里
    for (int i = 0; i < input.length(); ++i) {
      int j = i;
      while (j < input.length() && Character.isDigit(input.charAt(j))) {
        j++;
      }
      ops.add(input.substring(i, j));
      if (j != input.length()) ops.add(input.substring(j, j + 1));
      i = j;
    }
    int N = (ops.size() + 1) >> 1;//??
    List<Integer>[][] dp = (ArrayList<Integer>[][])new ArrayList[N][N];
    // 按照state的长度打表
    for (int len = 0; len < N; ++len) {
      if (len == 0) {
        for (int i = 0; i < N; ++i) {
          dp[i][i] = new ArrayList<>();
          dp[i][i].add(Integer.valueOf(ops.get(i * 2)));
        }
        continue; // 计算完len = 0的之后就update len, 所以要continue
      }
      for (int i = 0; i < N - len; ++i) {
        dp[i][i + len] = new ArrayList<>();
        for (int j = i; j < i + len; ++j) {
          // 由于是按照state的长度来loop, 所以此时已经计算完所有len = 0到len-1的了!
          List<Integer> left = dp[i][j], right = dp[j + 1][i + len];  
          for (int l : left) {
            for (int r : right) {
              String op = ops.get(j * 2 + 1);
              dp[i][i + len].add(op.equals("+") ? l + r : op.equals("-") ? l - r : l * r);
            }
          }
        }
      }
    }
    return dp[0][N - 1];
}

And DP, where dp[i][j] stores all possible results from the i-th integer to the j-th integer (inclusive) in the list.
public List<Integer> diffWaysToCompute(String input) {
    List<Integer> result=new ArrayList<>();
    if(input==null||input.length()==0)  return result;
    List<String> ops=new ArrayList<>();
    for(int i=0; i<input.length(); i++){
        int j=i;
        while(j<input.length()&&Character.isDigit(input.charAt(j)))
            j++;
        ops.add(input.substring(i, j));
        if(j!=input.length())   ops.add(input.substring(j, j+1));
        i=j;
    }
    int N=(ops.size()+1)/2; //num of integers
    ArrayList<Integer>[][] dp=(ArrayList<Integer>[][]) new ArrayList[N][N];
    for(int d=0; d<N; d++){
        if(d==0){
            for(int i=0; i<N; i++){
                dp[i][i]=new ArrayList<>();
                dp[i][i].add(Integer.valueOf(ops.get(i*2)));
            }
            continue;
        }
        for(int i=0; i<N-d; i++){
            dp[i][i+d]=new ArrayList<>();
            for(int j=i; j<i+d; j++){
                ArrayList<Integer> left=dp[i][j], right=dp[j+1][i+d];
                String operator=ops.get(j*2+1);
                for(int leftNum:left)
                    for(int rightNum:right){
                        if(operator.equals("+"))
                            dp[i][i+d].add(leftNum+rightNum);
                        else if(operator.equals("-"))
                            dp[i][i+d].add(leftNum-rightNum);
                        else
                            dp[i][i+d].add(leftNum*rightNum);
                    }
            }
        }
    }
    return dp[0][N-1];
}

https://leetcode.com/discuss/51448/java-dp-solution
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> nums = new ArrayList<Integer>();
        List<Character> ops = new ArrayList<Character>();
        decode(input,nums,ops);
        List<Integer> ans = new ArrayList<Integer>();
        int len = nums.size();
        List<List<List<Integer>>> dp = new ArrayList<List<List<Integer>>>();
        for(int i=0;i<len;i++) dp.add(new ArrayList<List<Integer>>());
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                dp.get(i).add(new ArrayList<Integer>());
        for(int i=0;i<len;i++) dp.get(i).get(i).add(nums.get(i));//dp[i][i]=nums[i]
        for(int dist=1;dist<len;dist++){
            //dp[begin][end] = dp[begin][k] ops[k] dp[k+1[end];(begin<=k<end)
            for(int begin=0;begin<len-dist;begin++){
                int end = begin+dist;
                for(int k=begin;k<end;k++){
                    for(int i=0;i<dp.get(begin).get(k).size();i++)
                        for(int j=0;j<dp.get(k+1).get(end).size();j++)
                            dp.get(begin).get(end).add(compute(dp.get(begin).get(k).get(i),ops.get(k),dp.get(k+1).get(end).get(j)));
                }
            }
        }
        return dp.get(0).get(len-1);
    }

public List<Integer> diffWaysToCompute(String input) {
    List<Integer> nums = new ArrayList<>();
    List<Character> ops = new ArrayList<>();
    decode(input, nums, ops);
    int len = nums.size();
    List<Integer>[][] dp = new ArrayList[len][len];
    for (int i = 0; i < len; i++) {
        for (int j = 0; j < len; j++) {
            dp[i][j] = new ArrayList<>();
        }
    }
    for (int i = 0; i < len; i++) {
        dp[i][i].add(nums.get(i)); // dp[i][i] = nums[i]
    }
    for (int dist = 1; dist < len; dist++) {
        for (int begin = 0; begin < len - dist; begin++) {
            int end = begin + dist;
            // dp[begin][end] = dp[begin][k] ops[k] dp[k+1][end] (begin <= k < end)
            for (int k = begin; k < end; k++) {
                for (int left : dp[begin][k]) {
                    for (int right : dp[k + 1][end]) {
                        dp[begin][end].add(compute(left, ops.get(k), right));
                    }
                }
            }
        }
    }
    return dp[0][len - 1];
}
private void decode(String input, List<Integer> nums, List<Character> ops) {
    int i = 0, len = input.length();
    while (i < len) {
        int num = 0;
        while (i < len) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                break;
            }
            num = num * 10 + c - '0';
            i++;
        }
        nums.add(num);
        if (i < len) {
            ops.add(input.charAt(i));
            i++;
        }
    }
}
http://yuanhsh.iteye.com/blog/2230557
public List<Integer> diffWaysToCompute(String s) {  
    String[] arr = s.split("[\\+\\-\\*\\/]");  
    String[] ops = s.split("\\d+"); // Note: the 1st item is a space  
    int n = arr.length;  
    int[] nums = new int[n];  
    for(int i=0; i<n; i++) {  
        nums[i] = Integer.parseInt(arr[i].trim());  
    }  
    return diffWays(nums, ops, 0, n-1);  
}  
  
public List<Integer> diffWays(int[] nums, String[] ops, int left, int right) {  
    List<Integer> list  = new ArrayList<>();  
    if(left == right) {  
        list.add(nums[left]);  
        return list;  
    }  
    for(int i=left+1; i<=right; i++) {  
        List<Integer> list1 = diffWays(nums, ops, left, i-1);  
        List<Integer> list2 = diffWays(nums, ops, i, right);  
        for(int num1:list1) {  
            for(int num2:list2) {  
                switch(ops[i].charAt(0)) {  
                    case '+': list.add(num1+num2); break;  
                    case '-': list.add(num1-num2); break;  
                    case '*': list.add(num1*num2); break;  
                    case '/': list.add(num1/num2); break;  
                }  
            }  
        }  
    }  
    return list;  
}  
http://my.oschina.net/u/922297/blog/484437

private final static long base = 100001;

private Map<Long, List<Integer>> cache = new HashMap<>();


private List<Integer> diffWaysToCompute(char[] cs, int start, int end) {

    long key = base * start + end;

    if (cache.containsKey(key)) {

        return cache.get(key);

    }


    boolean isNumber = true;


    for (int i = start; i < end; i++) {

        if (isOperator(cs[i])) {

            isNumber = false;

            break;

        }

    }


    List<Integer> result = new ArrayList<>();

    if (isNumber) {

        result.addAll(toNum(cs, start, end));

    } else {


        for (int i = start; i < end; i++) {

            if (isOperator(cs[i])) {

                List<Integer> prev = diffWaysToCompute(cs, start, i);

                List<Integer> suff = diffWaysToCompute(cs, i + 1, end);

                result.addAll(combineResult(prev, suff, cs[i]));

            }

        }


        return result;

    }


    cache.put(key, result);

    return result;

}


private List<Integer> combineResult(List<Integer> prev, List<Integer> suff, char op) {

    List<Integer> result = new ArrayList<>();


    for (int x : prev) {

        for (int y : suff) {

            result.add(calculate(x, y, op));

        }

    }


    return result;

}


private int calculate(int x, int y, char op) {

    switch (op) {

        case '+':

            return x + y;

        case '-':

            return x - y;

        case '*':

            return x * y;

    }

    return 0;

}


private List<Integer> toNum(char[] cs, int start, int end) {

    int num = 0;


    for (int i = start; i < end; i++) {

        if (cs[i] == ' ') {

            continue;

        }

        num = num * 10 + (cs[i] - '0');

    }

    List<Integer> result = new ArrayList<>(1);

    result.add(num);

    return result;

}


private boolean isOperator(char c) {

    return c == '+' || c == '-' || c == '*';

}


public List<Integer> diffWaysToCompute(String input) {

    return diffWaysToCompute(input.toCharArray(), 0, input.length());

}

class Solution:
    # @param {string} input
    # @return {integer[]}
    def diffWaysToCompute(self, input):
        def calc(a, b, o):
            return {'+':lambda x,y:x+y, '-':lambda x,y:x-y, '*':lambda x,y:x*y}[o](a, b)
        def dfs(nums, ops):
            if not ops:
                return [nums[0]]
            ans = []
            for x in range(len(ops)):
                left = dfs(nums[:x+1], ops[:x])
                right = dfs(nums[x+1:], ops[x+1:])
                for l in left:
                    for r in right:
                        ans.append(calc(l, r, ops[x]))
            return ans
        nums, ops = [], []
        input = re.split(r'(\D)', input)
        for x in input:
            if x.isdigit():
                nums.append(int(x))
            else:
                ops.append(x)
        return dfs(nums, ops)
http://shibaili.blogspot.com/2015/08/day-119-239-sliding-window-maximum.html
X. DFS

    vector<int> diffWaysToCompute(string s) {

        vector<int> rt;

         

        for (int i = 0; i < s.length(); i++) {

            if (s[i] == '-' || s[i] == '+' || s[i] == '*') {

                vector<int> first = diffWaysToCompute(s.substr(0,i));

                vector<int> second = diffWaysToCompute(s.substr(i + 1));

                for (int j = 0; j < first.size(); j++) {

                    for (int k = 0; k < second.size(); k++) {

                        if (s[i] == '-') {

                            rt.push_back(first[j] - second[k]);

                        }

                        if (s[i] == '+') {

                            rt.push_back(first[j] + second[k]);

                        }

                        if (s[i] == '*') {

                            rt.push_back(first[j] * second[k]);

                        }

                    }

                }

            }

        }

        if (rt.size() == 0) rt.push_back(stoi(s));

        return rt;
DP优化

    vector<int> helper(string s, int start, int end,unordered_map<string,vector<int>> &dic) {

        vector<int> rt;

         

        for (int i = start; i <= end; i++) {

            if (s[i] == '-' || s[i] == '+' || s[i] == '*') {

                vector<int> first;

                vector<int> second;

                if (dic.find(s.substr(start,i)) == dic.end()) {

                    first = helper(s,start,i - 1,dic);

                }else first = dic[s.substr(start,i)];

                 

                if (dic.find(s.substr(i + 1)) == dic.end()) {

                    second = helper(s,i + 1,end,dic);

                }else second = dic[s.substr(i + 1)];

                 

                for (int j = 0; j < first.size(); j++) {

                    for (int k = 0; k < second.size(); k++) {

                        if (s[i] == '-') {

                            rt.push_back(first[j] - second[k]);

                        }

                        if (s[i] == '+') {

                            rt.push_back(first[j] + second[k]);

                        }

                        if (s[i] == '*') {

                            rt.push_back(first[j] * second[k]);

                        }

                    }

                }

            }

        }

        if (rt.size() == 0) rt.push_back(stoi(s.substr(start,end - start + 1)));

        dic[s] = rt;

        return rt;

    }

     

    vector<int> diffWaysToCompute(string input) {

        unordered_map<string,vector<int>> dic;

        return helper(input,0,input.length() - 1,dic);

    }

回溯法（Backtracking），时间复杂度O(n!)，n为运算符的个数
通过dict保存有效的加括号方案，使用内置函数expr计算结果
class Solution:
    # @param {string} input
    # @return {integer[]}
    def diffWaysToCompute(self, input):
        exprDict = dict()
        def dfs(nums, ops):
            if ops:
                for x in range(len(ops)):
                    dfs(nums[:x]+['('+nums[x]+ops[x]+nums[x+1]+')']+nums[x+2:],ops[:x]+ops[x+1:])
            elif nums[0] not in exprDict:
                exprDict[nums[0]] = eval(nums[0])
        nums, ops = [], []
        input = re.split(r'(\D)', input)
        for x in input:
            if x.isdigit():
                nums.append(x)
            else:
                ops.append(x)
        dfs(nums, ops)
        return exprDict.values()

count how many ways there are
http://blog.welkinlan.com/2015/09/11/different-ways-to-add-parentheses-leetcode-java/

    public static int diffWaysToCompute(String input) {

        int countOp = getCountOperators(input);

        int[] dp = new int[countOp + 1];

        dp[1] = 1;

        dp[2] = 2;

        dp[3] = 5;

        return helper(countOp, dp);

    }

    

    private static int helper(int numOp, int[] dp) {

        if (dp[numOp] != 0) {

            return dp[numOp];

        }

        int result = 0;

        for (int i = 1; i < numOp; i++) {

            result += helper(i, dp) * helper(numOp - i, dp);

        }

        return result;

    }

    

    private static int getCountOperators(String input) {

        int r = 0;

        for (int i = 0; i < input.length(); i++) {

            if (!Character.isDigit(input.charAt(i))) {

                r++;

            }

        }

        return r;

    }


    public static void main(String[] args){

     String a = "1+2+3+4+4+5";

     System.out.print(diffWaysToCompute(a));

    }
Read full article from [LeetCode]Different Ways to Add Parentheses | 书影博客
 * @author het
 *
 */

/**
 * Input: "2-1-1".
((2-1)-1) = 0  (2-(1-1)) = 2
Output: [0, 2]
 * @author het
 *
 */
public class LeetCode241 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
