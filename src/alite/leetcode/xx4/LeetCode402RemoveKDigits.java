package alite.leetcode.xx4;

import java.util.Stack;

///**
// * https://leetcode.com/problems/remove-k-digits/
//Given a non-negative integer num represented as a string, remove k digits from the number 

//、、so that the new number is the smallest possible.
//Note:
//The length of num is less than 10002 and will be ≥ k.
//The given num does not contain any leading zero.
//Example 1:
//Input: num = "1432219", k = 3
//Output: "1219"

//"1432219", k = 3


//Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
//Example 2:
//Input: num = "10200", k = 1
//Output: "200"
//Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
//Example 3:
//Input: num = "10", k = 2
//Output: "0"
//Explanation: Remove all the digits from the number and it is left with nothing which is 0.
//X. Using Ordered Stack
//Use StringBuilder as stack
//https://discuss.leetcode.com/topic/59580/short-10-lines-o-n-java-code
//    public static String removeKdigits(String num, int k) {
//        StringBuilder sb = new StringBuilder();
//        for(char c : num.toCharArray()) {
//            while(k > 0 && sb.length() != 0 && sb.charAt(sb.length() - 1) > c) {
//                sb.setLength(sb.length() - 1);
//                k--;
//            }
//            if(sb.length() != 0 || c != '0') sb.append(c);  // Only append when it is not leading zero
//        }
//        if(k >= sb.length()) return "0";
//        sb.setLength(sb.length() - k);  // use all remaining k
//        return sb.toString();  
//    }
//setLength(len -1) O(1)
//public void setLength(int newLength) {
//    if (newLength < 0)
//        throw new StringIndexOutOfBoundsException(newLength);    ensureCapacityInternal(newLength);
//    if (count < newLength) {
//        Arrays.fill(value, count, newLength, '\0');    }
//
//    count = newLength;}
//Use array as stack
//https://discuss.leetcode.com/topic/59412/a-greedy-method-using-stack-o-n-time-and-o-n-space
//https://discuss.leetcode.com/topic/59501/6ms-java-solution-with-detailed-comment
//    public String removeKdigits(String num, int k) {
//        int digits = num.length() - k;
//        char[] stk = new char[num.length()];
//        int top = 0;
//        // k keeps track of how many characters we can remove
//        // if the previous character in stk is larger than the current one
//        // then removing it will get a smaller number
//        // but we can only do so when k is larger than 0
//        for (int i = 0; i < num.length(); ++i) {
//            char c = num.charAt(i);
//            while (top > 0 && stk[top-1] > c && k > 0) {
//                top -= 1;
//                k -= 1;
//            }
//            stk[top++] = c;
//        }
//        // find the index of first non-zero digit
//        int idx = 0;
//        while (idx < digits && stk[idx] == '0') idx++;
//        return idx == digits? "0": new String(stk, idx, digits - idx);
//    }
//http://www.cnblogs.com/grandyang/p/5883736.html
//http://blog.csdn.net/qq508618087/article/details/52584133
//思路：其基本思想是利用栈尽量维持一个递增的序列，也就是说将字符串中字符依次入栈，如果当前字符串比栈顶元素小，并且还可以继续删除元素，那么就将栈顶元素删掉，这样可以保证将当前元素加进去一定可以得到一个较小的序列．也可以算是一个贪心思想．最后我们只取前len-k个元素构成一个序列即可，如果这样得到的是一个空串那就手动返回０．还有一个需要注意的是字符串首字符不为０
//https://discuss.leetcode.com/topic/59327/o-n-solution/4
//使得栈中的数字尽可能保持递增顺序。
//http://www.voidcn.com/blog/yeqiuzs/article/p-6204756.html
//    public String removeKdigits(String num, int k) {
//        int len = num.length();
//        //corner case
//        if(k==len)        
//            return "0";
//            
//        Stack<Character> stack = new Stack<>();
//        int i =0;
//        while(i<num.length()){
//            //whenever meet a digit which is less than the previous digit, discard the previous one
//            while(k>0 && !stack.isEmpty() && stack.peek()>num.charAt(i)){
//                stack.pop();
//                k--;
//            }
//            stack.push(num.charAt(i));
//            i++;
//        }
//        
//        // corner case like "1111"
//        while(k>0){
//            stack.pop();
//            k--;            
//        }
//        
//        //construct the number from the stack
//        StringBuilder sb = new StringBuilder();
//        while(!stack.isEmpty())
//            sb.append(stack.pop());
//        sb.reverse();
//        
//        //remove all the 0 at the head
//        while(sb.length()>1 && sb.charAt(0)=='0')
//            sb.deleteCharAt(0);
//        return sb.toString();
//    }
//https://discuss.leetcode.com/topic/59646/straightforward-java-solution-using-stack
//public String removeKdigits(String num, int k) {
//        Stack<Integer> stack = new Stack<Integer>();
//        if (num.length() == 0 || num.length() <= k)
//            return "0";
//
//        for (int i = 0; i < num.length(); i++) {
//            int cur = num.charAt(i) - '0';
//            while (!stack.isEmpty() && cur < stack.peek()
//                    && num.length() - i - 1 >= (num.length() - k) - stack.size()) {
//                stack.pop();
//            }
//            if (stack.size()<num.length()-k)
//                stack.push(cur);
//        }
//
//        StringBuilder res = new StringBuilder();
//        while (!stack.isEmpty())
//            res.insert(0, stack.pop());
//
//        while (res.length() > 0 && res.charAt(0) == '0')
//            res.deleteCharAt(0);
//
//        if (res.length() == 0)
//            return "0";
//        return res.toString();
//    }
//http://www.cnblogs.com/grandyang/p/5883736.html
//这道题让我们将给定的数字去掉k位，要使得留下来的数字最小，这题跟LeetCode上之前那道Create Maximum Number有些类似，可以借鉴其中的思路，如果n是num的长度，我们要去除k个，那么需要剩下n-k个，我们开始遍历给定数字num的每一位，对于当前遍历到的数字c，进行如下while循环，如果res不为空，且k大于0，且res的最后一位大于c，那么我们应该将res的最后一位移去，且k自减1。当跳出while循环后，我们将c加入res中，最后我们将res的大小重设为n-k。根据题目中的描述，可能会出现"0200"这样不符合要求的情况，所以我们用一个while循环来去掉前面的所有0，然后返回时判断是否为空，为空则返回“0”
//    string removeKdigits(string num, int k) {
//        string res = "";
//        int n = num.size(), keep = n - k;
//        for (char c : num) {
//            while (k && res.size() && res.back() > c) {
//                res.pop_back();
//                --k;
//            }
//            res.push_back(c);
//        }
//        res.resize(keep);
//        while (!res.empty() && res[0] == '0') res.erase(res.begin());
//        return res.empty() ? "0" : res;
//    }
//http://blog.csdn.net/mebiuw/article/details/52576884
//     * 这是一个非常简单的问题，贪心解决法
//     * 即 removeKdigits(num,k) = removeKdigits(removeKdigits(num,1),k-1)
//     * 进行最多K轮的删除，每次从头开始寻找一位删除：
//     * 1、要么第二位是0，这样相当于至少删了两位，很值得，必须这么做
//     * 2、不然，找到第一个出现下降转折的位置 删除
//     * */
//    public String removeKdigits(String num, int k) {
//        int n;
//        while(true){
//            n = num.length();
//            if(n <= k || n == 0) return "0";
//            if(k-- == 0) return num;
//            if(num.charAt(1) == '0'){
//                int firstNotZero = 1;
//                while(firstNotZero < num.length()  && num.charAt(firstNotZero) == '0') firstNotZero ++;
//                num=num.substring(firstNotZero);
//            } else{
//                int startIndex = 0;
//                while(startIndex < num.length() - 1  && num.charAt(startIndex) <= num.charAt(startIndex + 1)) startIndex ++;
//                num=num.substring(0,startIndex)+num.substring(startIndex+1);
//            }
//        }
//    }
//
//X.
//http://bookshadow.com/weblog/2016/09/18/leetcode-remove-k-digits/
//解法II 问题可以转化为对偶问题：从字符串num中选取size - k个字符，使得选出的数字最小化。
//首先利用字典numd保存数字'0' - '9'在原始字符串num中的出现位置。
//然后循环size - k次：
//按照'0' - '9'的顺序从numd中选取位置合法的数字
//位置合法包含2条原则：1) 候选数字之后剩余的数字要足够多，2) 候选数字要处在已选出的数字之后
//    def removeKdigits(self, num, k):
//        """
//        :type num: str
//        :type k: int
//        :rtype: str
//        """
//        size = len(num)
//        ans = '0'
//        numd = collections.defaultdict(collections.deque)
//        for i, n in enumerate(num):
//            numd[n].append(i)
//        p = 0
//        for x in range(size - k):
//            for y in '0123456789':
//                while numd[y] and numd[y][0] < p:
//                    numd[y].popleft()
//                if numd[y] and numd[y][0] <= k + x:
//                    p = numd[y][0]
//                    ans += y
//                    numd[y].popleft()
//                    break
//        return str(int(ans))
//
//X.  https://discuss.leetcode.com/topic/59871/two-algorithms-with-detailed-explaination
//The first algorithm is straight-forward. Let's think about the simplest case: how to remove 1 digit from the number so that the new number is the smallest possible？ Well, one can simply scan from left to right, and remove the first "peak" digit; the peak digit is larger than its right neighbor. One can repeat this procedure k times, and obtain the first algorithm:
//string removeKdigits(string num, int k) {
//        while (k > 0) {
//            int n = num.size();
//            int i = 0;
//            while (i+1<n && num[i]<=num[i+1])  i++;
//            num.erase(i, 1);
//            k--;
//        }
//        // trim leading zeros
//        int s = 0;
//        while (s<(int)num.size()-1 && num[s]=='0')  s++;
//        num.erase(0, s);
//        
//        return num=="" ? "0" : num;
//    }
//
//http://www.1point3acres.com/bbs/thread-202732-1-1.html
//给一个string, 找出lexical order 最小的， size==k的， subsequence, (note, not substring)
//String findMin(String s, k){} e.g.
//input
//s=pineapple, k==3, 
//
//output: ale
//ale is the lexical order smallest subsequnce of length 3. 
//我是暴力求解的： 
//1. find the first occur position of distinct char. 
//2. then start from that position. 
//3. dfs to find lenght==3, subsequence(dfs, combination way); . visit 1point3acres.com for more.
//4. find the one with smallest lexical order. 
//
//我就是说了个大概.
//pop的时候需要看后面还剩几个元素了
//元素不够的时候就含泪不pop了，直接push进去
//比如你说的例子，其实是
//f->e->d->c->cb->cba
//public class Solution {
//        鏉ユ簮涓€浜�.涓夊垎鍦拌鍧�. 
//        public String removeKdigits(String num, int k) {. 1point3acres.com/bbs
//        if(num == null || num.length() == 0) return num;
//        int len = num.length();. 1point3acres.com/bbs
//        if(len == k) return "";
//        Stack<Character> stack = new Stack<Character>();
//        char []ch = num.toCharArray();
//        int i = 0, n = num.length();
//        while(i < len){
//
//            while(!stack.isEmpty() && stack.size() + n-i > k &&  stack.peek() > ch[i]){
//                stack.pop();
//            }.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
//            stack.push(ch[i++]);
//        }. more info on 1point3acres.com
//        
//        // handle corner case 1111 or 1234, when k = 2. 涓€浜�-涓夊垎-鍦帮紝鐙鍙戝竷
//        while(stack.size() > k){-google 1point3acres
//            stack.pop();
//        }
//        
//        StringBuilder sb = new StringBuilder();
//        while(!stack.isEmpty()). Waral 鍗氬鏈夋洿澶氭枃绔�,
//          sb.insert(0, stack.pop());
//        
//        return sb.toString();
//        
//    }
//
//        public static void main(String[] args) {.鏈枃鍘熷垱鑷�1point3acres璁哄潧
//                Solution s = new Solution();
//                String str="xyzabc";
//                int k=3;
//                System.out.println(s.removeKdigits(str, k));
//        }. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
//}
// * @author het
// *
// */
public class LeetCode402RemoveKDigits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//num = "1432219", k = 3
		System.out.println();

	}
	
	
	//思路：其基本思想是利用栈尽量维持一个递增的序列，也就是说将字符串中字符依次入栈，如果当前字符串比栈顶元素小，并且还可以继续删除元素，那么就将栈顶元素删掉，
	//这样可以保证将当前元素加进去一定可以得到一个较小的序列．也可以算是一个贪心思想．最后我们只取前len-k个元素构成一个序列即可，如果这样得到的是一个空串那
	//就手动返回０．还有一个需要注意的是字符串首字符不为０
	//https://discuss.leetcode.com/topic/59327/o-n-solution/4
	//使得栈中的数字尽可能保持递增顺序。
	//http://www.voidcn.com/blog/yeqiuzs/article/p-6204756.html
	    public static String removeKdigits(String num, int k) {
	        int len = num.length();
	        //corner case
	        if(k==len)        
	            return "0";
	            
	        Stack<Character> stack = new Stack<>();
	        int i =0;
	        while(i<num.length()){
	            //whenever meet a digit which is less than the previous digit, discard the previous one
	            while(k>0 && !stack.isEmpty() && stack.peek()>num.charAt(i)){
	                stack.pop();
	                k--;
	            }
	            if(!stack.isEmpty() || num.charAt(i)!= '0') {
	            	stack.push(num.charAt(i));
		           
	            }else {
	            	   k--;
	            }
	            i++;
	            
	        }
	        //Margarita
	        // corner case like "1111"
	        while(k>0){
	            stack.pop();
	            k--;            
	        }
	        
	        //construct the number from the stack
	        StringBuilder sb = new StringBuilder();
	        while(!stack.isEmpty())
	            sb.append(stack.pop());
	        sb.reverse();
	        
	        //remove all the 0 at the head
	        while(sb.length()>1 && sb.charAt(0)=='0')
	            sb.deleteCharAt(0);
	        return sb.toString();
	    }
	    
	    //'' sb.setLength(sb.length() - 1);
	    public static String removeKdigits1(String num, int k) {
	        StringBuilder sb = new StringBuilder();
	        for(char c : num.toCharArray()) {
	            while(k > 0 && sb.length() != 0 && sb.charAt(sb.length() - 1) > c) {
	                sb.setLength(sb.length() - 1);   // sb.setLength(sb.length() - 1);
	                k--;
	            }
	            if(sb.length() != 0 || c != '0') sb.append(c);  // Only append when it is not leading zero
	        }
	        if(k >= sb.length()) return "0";
	        sb.setLength(sb.length() - k);  // use all remaining k
	        return sb.toString();  
	    }

}
