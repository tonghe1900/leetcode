package alite.leetcode.xx2.sucess.before;

import java.util.ArrayList;
import java.util.List;
/**
 * LeetCode 247 - Strobogrammatic Number II | CrazyEgg

LeetCode: Strobogrammatic Number II | CrazyEgg
Related: LeetCode 246 - Strobogrammatic Number
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
Find all strobogrammatic numbers that are of length = n.
For example,
Given n = 2, return ["11","69","88","96"].

Iterative:
https://leetcode.com/discuss/53144/my-concise-iterative-java-code
http://www.cnblogs.com/jcliBlogger/p/4713146.html
    public List<String> findStrobogrammatic(int n) {
        List<String> one = Arrays.asList("0", "1", "8"), two = Arrays.asList(""), r = two;
        if(n%2 == 1)
            r = one;
        for(int i=(n%2)+2; i<=n; i+=2){
            List<String> newList = new ArrayList<>();
            for(String str : r){
                if(i != n)
                    newList.add("0" + str + "0");
                newList.add("1" + str + "1");
                newList.add("6" + str + "9");
                newList.add("8" + str + "8");
                newList.add("9" + str + "6");
            }
            r = newList;
        }
        return r;   
    }
https://discuss.leetcode.com/topic/21981/my-concise-iterative-java-code
public List<String> findStrobogrammatic(int n) {
 Map<Character, Character> map = buildMap();
 List<String> ret = n % 2 == 0 ? Arrays.asList("") : Arrays.asList("1", "8", "0");

 for (int i = n % 2 == 0 ? 1 : 2; i < n; i += 2) {
  List<String> cur = new ArrayList<>();
  for (char c : map.keySet()) {
   for (String s : ret) {
                // don't add leading 0s!
    if (i != n - 1 || c != '0')
     cur.add(c + s + map.get(c));
   }
  }
  ret = cur;
 }

 return ret;
}

private Map<Character, Character> buildMap() {
 Map<Character, Character> map = new HashMap<>();
 map.put('1', '1');
 map.put('6', '9');
 map.put('8', '8');
 map.put('9', '6');
 map.put('0', '0');
 return map;
}
https://discuss.leetcode.com/topic/28359/iterative-clean-java-solution
    public List<String> findStrobogrammatic(int n) {
        
        List<String> prev = new ArrayList<String>();
        int start = 1;
        if(n % 2 == 0){
            prev.add("");
        } else{
            prev.add("0");
            prev.add("1");
            prev.add("8");
            start = 2;
        }
        
        for(int i = start; i <= n; i+=2){
            List<String> res = new ArrayList<String>();
            for(int j = 0; j < prev.size(); j++){
                String cur = prev.get(j);
                if(i+1 != n){
                    res.add("0" + cur + "0");
                }
                res.add("1" + cur + "1");
                res.add("6" + cur + "9");
                res.add("8" + cur + "8");
                res.add("9" + cur + "6");
            }
            prev = res;
        }
        return prev;
        
    }
X. DFS
https://discuss.leetcode.com/topic/20753/ac-clean-java-solution
The mothed to tackle "00" case is really smart.
created too many list and temp string
public List<String> findStrobogrammatic(int n) {
    return helper(n, n);
}

List<String> helper(int n, int m) {
    if (n == 0) return new ArrayList<String>(Arrays.asList(""));
    if (n == 1) return new ArrayList<String>(Arrays.asList("0", "1", "8"));
    
    List<String> list = helper(n - 2, m);
    
    List<String> res = new ArrayList<String>();
    
    for (int i = 0; i < list.size(); i++) {
        String s = list.get(i);
        
        if (n != m) res.add("0" + s + "0");
        
        res.add("1" + s + "1");
        res.add("6" + s + "9");
        res.add("8" + s + "8");
        res.add("9" + s + "6");
    }
    
    return res;
}
https://discuss.leetcode.com/topic/30897/java-solution-with-recursion
https://discuss.leetcode.com/topic/21579/accepted-java-solution-using-recursion
public List<String> findStrobogrammatic(int n) {
    findStrobogrammaticHelper(new char[n], 0, n - 1);
    return res;
}

List<String> res = new ArrayList<String>();

public void findStrobogrammaticHelper(char[] a, int l, int r) {
    if (l > r) {
        res.add(new String(a));
        return;
    }
    if (l == r) {
        a[l] = '0'; res.add(new String(a));
        a[l] = '1'; res.add(new String(a));
        a[l] = '8'; res.add(new String(a));
        return;
    }
    
    if (l != 0) {
        a[l] = '0'; a[r] = '0';
        findStrobogrammaticHelper(a, l+1, r-1);
    }
    a[l] = '1'; a[r] = '1';
    findStrobogrammaticHelper(a, l+1, r-1);
    a[l] = '8'; a[r] = '8';
    findStrobogrammaticHelper(a, l+1, r-1);
    a[l] = '6'; a[r] = '9';
    findStrobogrammaticHelper(a, l+1, r-1);
    a[l] = '9'; a[r] = '6';
    findStrobogrammaticHelper(a, l+1, r-1);
}
https://discuss.leetcode.com/topic/20733/my-concise-java-solution-using-dfs
why it does not need backtracking after dfs(n, index + 1, buffer, result, map);
like remove a char in the buffer??
Because he noticed that the buffer would be fixed size in n. So he use an array instead. The old value would got overwritten at next time.
First I was trying to use StringBuilder. But it is hard to append on both sides. char[] is much more better.
    public List<String> findStrobogrammatic(int n) {
        Map<Character, Character> map = new HashMap<Character, Character>();
        map.put('0', '0');
        map.put('1', '1');
        map.put('6', '9');
        map.put('8', '8');
        map.put('9', '6');
        List<String> result = new ArrayList<String>();
        char[] buffer = new char[n];
        dfs(n, 0, buffer, result, map);
        return result;
    }
    
    private void dfs(int n, int index, char[] buffer, List<String> result, Map<Character, Character> map) {
        if (n == 0) {
            return;
        }
        if (index == (n + 1) / 2) {
            result.add(String.valueOf(buffer));
            return;
        }
        for (Character c : map.keySet()) {
            if (index == 0 && n > 1 && c == '0') {  // first digit cannot be '0' when n > 1
                continue;
            }
            if (index == n / 2 && (c == '6' || c == '9')) {   // mid digit cannot be '6' or '9' when n is odd
                continue;
            }
            buffer[index] = c;
            buffer[n - 1 - index] = map.get(c);
            dfs(n, index + 1, buffer, result, map);
        }
    }

http://happycoding2010.blogspot.com/2015/11/leetcode-247-strobogrammatic-number-ii.html
   public List<String> findStrobogrammatic(int n) {  
     List<String> res=new ArrayList<>();  
     if (n<=0) return res;  
     char[] c=new char[n];  
     dfs(c,0,n-1,res);  
     return res;  
   }  
   private void dfs(char[] c, int i, int j, List<String> res) {  
     if (i>j) res.add(new String(c));  
     else {  
       if (i>0 || (i==0 && j==0)) {  
         c[i]='0';c[j]='0';  
         dfs(c,i+1,j-1,res);  
       }  
       if (i!=j) {  
         c[i]='6';c[j]='9';  
         dfs(c,i+1,j-1,res);  
         c[i]='9';c[j]='6';  
         dfs(c,i+1,j-1,res);  
       }  
       c[i]='1';c[j]='1';  
       dfs(c,i+1,j-1,res);  
       c[i]='8';c[j]='8';  
       dfs(c,i+1,j-1,res);  
     }  
   }  

    private char[] validNumbers = new char[]{'0', '1', '6', '8', '9'};
    private char[] singleable = new char[]{'0', '1', '8'};

    public List<String> findStrobogrammatic(int n) {
        assert n > 0;
        List<String> result = new ArrayList<>();

        if (n == 1) {
            for (char c : singleable) {
                result.add(String.valueOf(c));
            }
            return result;
        }

        if (n % 2 == 0) {
            helper(n, new StringBuilder(), result);
        } else {
            helper(n - 1, new StringBuilder(), result);
            List<String> tmp = new ArrayList<>();
            for (String s : result) {
                for (char c : singleable) {
                    tmp.add(new StringBuilder(s).insert(s.length() / 2, c).toString());
                }
            }
            result = tmp;
        }
        return result;
    }

    private void helper(int n, StringBuilder sb, List<String> result) {
        if (sb.length() > n) return;

        if (sb.length() == n) {
            if (sb.length() > 0 && sb.charAt(0) != '0') {
                result.add(sb.toString());
            }
            return;
        }

        for (char c : validNumbers) {
            StringBuilder tmp = new StringBuilder(sb);
            String s = "" + c + findMatch(c);
            tmp.insert(tmp.length() / 2, s);
            helper(n, tmp, result);
        }
    }

    private char findMatch(char c) {
        switch (c) {
            case '1':
                return '1';
            case '6':
                return '9';
            case '9':
                return '6';
            case '8':
                return '8';
            case '0':
                return '0';
            default:
                return 0;
        }
    }
http://buttercola.blogspot.com/2015/08/leetcode-strobogrammatic-number-ii.html


public class Solution {

    private List<String> result = new ArrayList<String>();

    private Map<Character, Character> hashMap = new HashMap<>();

     

    public List<String> findStrobogrammatic(int n) {

        result.clear();

        hashMap.clear();

        fillHashMap(hashMap);

         

        char[] arr = new char[n];

        findStrobogrammaticHelper(arr, 0, n - 1);

         

        return result;

    }

     

    private void findStrobogrammaticHelper(char[] arr, int lo, int hi) {

        if (lo > hi) {

            if (arr.length == 1 || (arr.length > 1 && arr[0] != '0')) {

                result.add(new String(arr));

            }

            return;

        }

         

        for (Character c : hashMap.keySet()) {

            arr[lo] = c;

            arr[hi] = hashMap.get(c);

             

            if (lo < hi || (lo == hi && hashMap.get(c) == c)) {

                findStrobogrammaticHelper(arr, lo + 1, hi - 1);

            }

        }

    }

     

    private void fillHashMap(Map<Character, Character> hashMap) {

        hashMap.put('0', '0');

        hashMap.put('1', '1');

        hashMap.put('8', '8');

        hashMap.put('6', '9');

        hashMap.put('9', '6');

    }

}
https://github.com/kamyu104/LeetCode/blob/master/C++/strobogrammatic-number-ii.cpp
http://shibaili.blogspot.com/2015/07/google-interview-questions-3.html
1. find all rotation symmetric numbers less than N digits,  16891 -> 16891,  
类似LC #246 Strobogrammatic Number 
对称性，11,00,88,69,96. 当n为奇数时，中间为1，8，或0 
假设n为位数，k为n位数时rotation symmetric numbers的数量，则：
n = 1, k = 3 
n = 2, k = 5
n = 3, k = 3 * 5
n = 4, k = 5 * 5
n = 5, k = 3 * 5 * 5
n = 6, k = 5 * 5 * 5
....

void insertToReturn(vector<string> &rt, vector<string> candidates) {

    for (int i = 0; i < candidates.size(); i++) {

        rt.push_back(candidates[i]);

    }  

}


vector<string> pad(vector<string> candidates) {

    vector<string> rt;

    for (int i = 0; i < candidates.size(); i++) {

        rt.push_back("1" + candidates[i] + "1");

        rt.push_back("8" + candidates[i] + "8");

        rt.push_back("0" + candidates[i] + "0");

        rt.push_back("6" + candidates[i] + "9");

        rt.push_back("9" + candidates[i] + "6");

    }

   

    return rt;

}


vector<string> findAllNumber(int n) {

    vector<string> odd;

    vector<string> even;

    odd.push_back("1");

    odd.push_back("8");

    odd.push_back("0");

    even.push_back("11");

    even.push_back("88");

    even.push_back("00");

    even.push_back("69");

    even.push_back("96");

     

    if (n == 1) return odd;

    if (n == 2) {

        insertToReturn(odd,even);

        return odd;

    }

     

    vector<string> rt;

    insertToReturn(rt,odd);

    insertToReturn(rt,even);

     

    for (int i = 3; i <= n; i++) {

        if (i % 2 == 1) {

            odd = pad(odd);

            insertToReturn(rt,odd);

             

        }else {

            even = pad(even);

            insertToReturn(rt,even);

        }

    }

     

    return rt;

}
 * @author het
 *
 */

/**
 * LeetCode 247 - Strobogrammatic Number II | CrazyEgg

LeetCode: Strobogrammatic Number II | CrazyEgg
Related: LeetCode 246 - Strobogrammatic Number
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
Find all strobogrammatic numbers that are of length = n.
For example,
Given n = 2, return ["11","69","88","96"].
0  1  8     6  9
**/
public class LeetCode247 {

	
	public static  List<String> findStrobogrammaticNumber(int n){
		List<String> result = new ArrayList<>();
		if(n <= 0 ) return result;
		// even or odd   69 69       6[108]9     1 0 8 
		if(n == 1) {
			result.add("0");
			result.add("8");
			result.add("1");
			return result;
		}
		findStrobogrammaticNumber(result, new char [n], 0, n-1, n);
		return result;
	}
	
	
	private static void findStrobogrammaticNumber(List<String> result, char[] arr,
			int start,int end,  int n) {
		// even or odd
		//  even   start = end+1
		// odd     start = end +2
		// start == end   0  8 1
		// other case     0  8 1 6 9
		if(start == end+1 || start == end +2){
			result.add(new String(arr));
			return;
		}
		char[] choicesForStartDigit = null;
		char[] choicesForEndDigit = null;
		if(start == 0)  {
			//8 1 6 9
			 choicesForStartDigit = new char[]{'8', '1' ,'6',  '9'};
			 choicesForEndDigit = new char[]{'8', '1' ,'9',  '6'};
			
			
		}else{
			if(start == end){
				choicesForStartDigit = new char[]{'8', '0', '1'};
				choicesForEndDigit = new char[]{'8', '0', '1'};
				
				
			}else{
				 choicesForStartDigit = new char[]{'0', '8', '1' ,'6',  '9'};
				 choicesForEndDigit =new char[] {'0','8', '1' ,'9',  '6'};
			}
		}
		
		for(int i=0 ; i<choicesForStartDigit.length;i+=1){
			arr[start] = choicesForStartDigit[i];
			arr[end] = choicesForEndDigit[i];
			findStrobogrammaticNumber(result, arr, start+1, end -1, n);
			//revert 
			
			
		}
		
		
		
		
		
	}
	
//	public int  getNumber(int n){
//		int result = 3;
//		
//	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(findStrobogrammaticNumber(5).size());

	}

}
