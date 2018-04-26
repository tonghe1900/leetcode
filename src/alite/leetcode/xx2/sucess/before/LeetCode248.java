package alite.leetcode.xx2.sucess.before;

import java.util.ArrayList;
import java.util.List;
/**
 * LeetCode 248 - Strobogrammatic Number III

LeetCode: Strobogrammatic Number III | CrazyEgg
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
For example,
Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.
Note:
Because the range might be a large number, the low and high numbers are represented as string.
X.
https://discuss.leetcode.com/topic/44718/java-1ms-solution-with-comments-in-the-code
Generating the numbers in ascending order so it can be teminated when the number is greater than high.
Only need to actually generate the numbers having the same length of low and high for range checking. The total nubmers of other lengths can be counted with a dp function.
Using char arrays and placing char on both ends is faster than using adding String operation. In addtion, comparing char arrays directly should be faster than calling String.compareTo() function.
 char[] singles = new char[]{'0','1','8'};
 // Sorted by the first char in ascending order
 char[][] pairs = new char[][]{ {'0','0'},{'1','1'},{'6','9'},{'8','8'},{'9','6'} };
 char[] ans; // buffer of storing the number
 int count = 0; // total count
 char[] l; // char array of low
 char[] h; // char array of high
 
 // Compare two numbers' char array. Longer one is the larger one.
 // If have same length then compare each char from left to right
 // return positive when s2 > s1, 0 when s2==s1, nagetive when s2 < s1
 int comp(char[] s1, char[] s2) {
     int len1 = s1.length;
     int len2 = s2.length;
     if (len1 != len2) return len2-len1;
     else {
         for (int i=0; i < len1; i++) {
             if (s1[i] != s2[i]) return s2[i]-s1[i];
         }
         return 0;
     }
 }
 // Recursion for generating Strobogrammatic numbers of length n 
 // starting from both ends. As a result, the numbers are 
 // generate in ascending order.
 // Therefore, when when a number is greater than high it returns false 
 // and then terminate the loop. 
 boolean helper(int n, int s, int e, int len) {
     if (n==0) return false;
     if (len==n) { // a resulting number 
         // checking the range
         if ( comp(l, ans) >=0  && comp(ans, h) >=0 ) count++;
         if ( comp(ans, h) < 0) return false; // the nubmer is greater than high
         return true;
     } else if (s==e) { // odd length at the middle position, apply single digit
         for (int i =0 ; i< singles.length; i++) {
             if ( ! ( s == 0 && i == 0 && n > 1)  ) { // first digit can't be 0
                 ans[s] = singles[i];
                 if ( ! helper( n, s+1, e-1, len+1) ) return false;
             }
         }
     } else { // placing two digits at both ends
         for (int i =0 ; i< pairs.length; i++) {
             char[] pair = pairs[i];
             if ( ! ( s == 0 && i == 0)  ) { // first digit can't be 0
                 ans[s] = pair[0];
                 ans[e] = pair[1];
                 if ( ! helper( n, s+1, e-1, len+2 ) ) return false;
             }
         }
     }
     return true;
 }
 // counting the total Strobogrammatic numbers of lengh n 
 // without considering the range
 int counts(int n, int next) {
     if (next<=0) return 0;
     if (next==1) return 3;
     if (next==2) return n==next? 4 :5; // first digit can't be 0
     return n==next? 4 * counts(n, next-2) : 5 * counts(n, next-2);
 }
 
 public int strobogrammaticInRange(String low, String high) {
     int low_len = low.length();
     int high_len = high.length();
     l = low.toCharArray();
     h = high.toCharArray();
     for (int i=low_len; i <= high_len; i++) {
         // generating the numbers only when the length is equal to low or high
         if (i== low_len || i== high_len) {
             ans = new char[i];
             helper(i, 0, i-1, 0);
         } else { 
             // counting the total numbers without acctualy generating them
             count+=counts(i,i);
         }
     }
     return count;
 }
https://discuss.leetcode.com/topic/39448/java-0ms-solution-99-5
return all valid nums under upper (inclusive) - all valid nums under low (exclusive).
Suppose upper has length len. The numbers of valid nums of len_i's < len, can be very efficiently computed using recursion or Math.pow();.
For valid nums with len, construct them all and aggressively discard them if they are higher than upper (pruning). After all, char array comparison is cheap : if(compareCharArray(chs, upper, i) > 0) break;
    private static char[][] pairs = new char[][]{{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
    public int strobogrammaticInRange(String low, String high) {
        if(low.length()>high.length() || low.length()==high.length() && high.compareTo(low)<0) return 0;
        return strobogrammaticInRangeFrom0(high, true) - strobogrammaticInRangeFrom0(low, false);
    }
    private int strobogrammaticInRangeFrom0(String num, boolean inclusive){
        int len = num.length();
        if(len == 1){
            if(num.charAt(0) == '0')        return inclusive ? 1 : 0;       // 0?
            else if(num.charAt(0) == '1')   return inclusive ? 2 : 1;       // 0,1?
            else if(num.charAt(0) < '8')    return 2;                       // 0,1
            else if(num.charAt(0) == '8')   return inclusive ? 3 : 2;       // 0,1,8?
            else                            return 3;                       // 0,1,8
        }
        int sum = 0;
        for(int i = 1; i < len; i++)
            sum += strobogrammaticDigit(i, true);
        sum += strobogrammaticInRangeSameDigits(new char[len], 0, len - 1, num.toCharArray(),inclusive);
        return sum;
    }
    private int strobogrammaticInRangeSameDigits(char[] chs, int i, int j, char[] upper, boolean inclusive){
        int sum = 0;
        if(i > j){
            if( inclusive && compareCharArray(upper, chs, chs.length-1 ) >= 0 || !inclusive && compareCharArray(upper, chs, chs.length-1) > 0 )    return 1;
            else    return 0;
        }
        for(char[] pair: pairs){
            if(i == 0 && pair[0] == '0' || i==j && (pair[0] == '6' || pair[0] == '9') )     continue;
            chs[i] = pair[0];
            chs[j] = pair[1];
            if(compareCharArray(chs, upper, i) > 0)     break;
            sum += strobogrammaticInRangeSameDigits(chs, i+1, j-1, upper, inclusive);
        }
        return sum;
    }
    private int strobogrammaticDigit(int digit, boolean outside){
        if(digit == 0)      return 1;
        if(digit == 1)      return 3;
        return outside? strobogrammaticDigit(digit-2, false)*4: strobogrammaticDigit(digit-2, false)*5;
    }
    private int compareCharArray(char[] arr1, char[] arr2, int idx){
        for(int i = 0; i <= idx; i++)
            if(arr1[i] == arr2[i])          continue;
            else if(arr1[i] > arr2[i])      return 1;
            else                            return -1;
        return 0;
    }
https://discuss.leetcode.com/topic/27065/java-solution-using-dp-and-strobogrammatic-number-ii

Number of Strobogrammatic numbers follows:
f(2*n) = f(2*n-1) + f(2*n-2)*2;
f(2*n+1) = f(2*n) * 3;
n = 1,2,3...   |   f(0) = 1, f(1) = 2;
f(1) is a special case because of 0;
public int strobogrammaticInRange(String low, String high) {
 int h = high.length(), l = low.length();
 if (l > h || h == l && low.compareTo(high) > 0)
  return 0;
 int[] dp = new int[h + 1];
 dp[0] = 1; dp[1] = 2;
 for (int i = 2; i <= h; i++) {
  dp[i] = i % 2 == 0 ? dp[i - 1] + 2 * dp[i - 2] : dp[i - 1] * 3;
 }
 dp[1] = 3;
 long count = findStrobogrammatic(l).stream().filter(j -> j.compareTo(low) >= 0)
               .count() - 
                 findStrobogrammatic(h).stream().filter(j -> j.compareTo(high) > 0)
                        .count();
 for (int i = l + 1; i <= h; i++) {
  count += dp[i];
 }
 return (int) count;
}

public List<String> findStrobogrammatic(int n) {
    List<String> ret = new ArrayList<>();
    helper(ret, new char[n], 0, n-1);
    return ret;
}

private final char[] ipick = {'0', '1', '8', '6', '9'};
private final char[] jpick = {'0', '1', '8', '9', '6'};
private void helper(List<String> ret, char[] tmp, int i, int j) {
    if (i > j) {
        ret.add(new String(tmp)); return;
    } 
    if (i == j){
        for (int k = 0; k < 3; k++) {
            tmp[i] = ipick[k];
            helper(ret, tmp, i+1, j-1);
        }
    } else {
        for (int k = i == 0 ? 1 : 0; k < 5; k++) {
            tmp[i] = ipick[k];
            tmp[j] = jpick[k];
            helper(ret, tmp, i+1, j-1);
        }
    }
}

X.
https://discuss.leetcode.com/topic/31386/concise-java-solution
Construct char arrays from low.length() to high.length()
Add stro pairs from outside
When left > right, add eligible count
private static final char[][] pairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

public int strobogrammaticInRange(String low, String high) {
    int[] count = {0};
    for (int len = low.length(); len <= high.length(); len++) {
        char[] c = new char[len];
        dfs(low, high, c, 0, len - 1, count);
    }
    return count[0];
}

public void dfs(String low, String high , char[] c, int left, int right, int[] count) {
    if (left > right) {
        String s = new String(c);
        if ((s.length() == low.length() && s.compareTo(low) < 0) || 
            (s.length() == high.length() && s.compareTo(high) > 0)) {
            return;
        }
        count[0]++;
        return;
    }
    for (char[] p : pairs) {
        c[left] = p[0];
        c[right] = p[1];
        if (c.length != 1 && c[0] == '0') {
            continue;
        }
        if (left == right && p[0] != p[1]) {
            continue;
        }
        dfs(low, high, c, left + 1, right - 1, count);
    }
}

http://buttercola.blogspot.com/2015/09/leetcode-strobogrammatic-number-iii.html
The idea would be very close to the previous problem. So we find all the strobogrammatic numbers between the length of low and high. Note that when the n == low or n == high, we need to compare and make sure the strobogrammatic number we find is within the range.

public class Solution {

    private int count = 0;

    private Map<Character, Character> map = new HashMap<>();

     

    public int strobogrammaticInRange(String low, String high) {

        if (low == null || low.length() == 0 || high == null || high.length() == 0) {

            return 0;

        }

         

        fillMap();

         

        for (int n = low.length(); n <= high.length(); n++) {

            char[] arr = new char[n];

            getStrobogrammaticNumbers(arr, 0, n - 1, low, high);

        }

         

        return count;

    }

     

    private void getStrobogrammaticNumbers(char[] arr, int start, int end, String low, String high) {

        if (start > end) {

            String s = new String(arr);

            if ((s.length() == 1 || s.charAt(0) != '0') && compare(low, s) && compare(s, high)) {

                count++;

            }

            return;

        }

             

        for (char c : map.keySet()) {

            arr[start] = c;

            arr[end] = map.get(c);

                 

            if ((start < end) || (start == end && map.get(c) == c)) {

                getStrobogrammaticNumbers(arr, start + 1, end - 1, low, high);

            }

        }

    }

     

    // Return true if s1 <= s2

    private boolean compare(String s1, String s2) {

        if (s1.length() == s2.length()) {

            if (s1.compareTo(s2) <= 0) {

                return true;

            } else {

                return false;

            }

        }

         

        return true;

    }

     

    private void fillMap() {

        map.put('0', '0');

        map.put('1', '1');

        map.put('8', '8');

        map.put('6', '9');

        map.put('9', '6');

    }

}
https://leetcode.com/discuss/50628/ac-java-solution-with-explanation
public class Solution {

  int count = 0;
  String[][] pairs = {{"0", "0"}, {"1", "1"}, {"6", "9"}, {"8", "8"}, {"9", "6"}};

  public int strobogrammaticInRange(String low, String high) {
    // use a look-up table to return the strobogrammatic list of length n
    Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
    map.put(0, new ArrayList<String>(Arrays.asList("")));
    map.put(1, new ArrayList<String>(Arrays.asList("0", "1", "8")));

    // loop through all possible lengths
    for (int len = low.length(); len <= high.length(); len++)
      helper(len, map, low, high);

    return count;
  }

  // return the strobogrammatic list of length n 
  List<String> helper(int n, Map<Integer, List<String>> map, String low, String high) {
    List<String> res = new ArrayList<String>();

    if (map.containsKey(n)) {
      res = map.get(n);
    } else {
      // found in look-up table? return it, otherwise do the recursion by n - 2
      List<String> list = map.containsKey(n - 2) ? map.get(n - 2) : helper(n - 2, map, low, high);

      for (int i = 0; i < list.size(); i++) {
        String s = list.get(i);

        for (int j = 0; j < pairs.length; j++) {
          // form the new strobogrammatic number
          String v = pairs[j][0] + s + pairs[j][1];

          // if it's larger than high already, no need to proceed
          if (v.length() == high.length() && v.compareTo(high) > 0)
            break;

          res.add(v);
        }
      }

      // put the new list to look-up table
      map.put(n, res);
    }

    // if current length is longer than low
    // we start to count
    if (n >= low.length()) {
      count += res.size();

      for (String s : res) {
        // eliminate the number that is outside [low, high] range
        if ((s.length() > 1 && s.charAt(0) == '0') || 
            (s.length() == low.length() && s.compareTo(low) < 0) || 
            (s.length() == high.length() && s.compareTo(high) > 0))
          count--;
      }
    }

    return res;
  }

}
http://likesky3.iteye.com/blog/2240441
思路1：比较容易想到的思路，利用题II的思路构造出low.length 和high.length之间的所有strobogrammatic number, 然后计数在low-high范围内的那些数字个数。 

思路2：参考https://leetcode.com/discuss/50624/clean-and-easy-understanding-java-solution 在构造过程中判断并计数，且构造方式是从两边往中间，而思路1是从中间忘两边。在leetcode的运行时间优于思路1 

https://leetcode.com/discuss/50624/clean-and-easy-understanding-java-solution
    public class Solution {
Map<Character, Character> map = new HashMap<>();
{
    map.put('1', '1');
    map.put('8', '8');
    map.put('6', '9');
    map.put('9', '6');
    map.put('0', '0');
}
String low = "", high = "";
public int strobogrammaticInRange(String low, String high) {
    this.low = low;
    this.high = high;
    int result = 0;
    for(int n = low.length(); n <= high.length(); n++){
        int[] count = new int[1];
        strobogrammaticInRange(new char[n], count, 0, n-1);
        result += count[0];
    }
    return result;
}
private void strobogrammaticInRange(char[] arr, int[] count, int lo, int hi){
    if(lo > hi){
        String s = new String(arr);
        if((arr[0] != '0' || arr.length == 1) && compare(low, s) && compare(s, high)){
            count[0]++;
        }
        return;
    }
    for(Character c: map.keySet()){
        arr[lo] = c;
        arr[hi] = map.get(c);
        if((lo == hi && c == map.get(c)) || lo < hi)
            strobogrammaticInRange(arr, count, lo+1, hi-1);
    }
}
private boolean compare(String a, String b){
    if(a.length() != b.length())
        return a.length() < b.length();
    int i = 0;
    while(i < a.length() &&a.charAt(i) == b.charAt(i))
        i++;
    return i == a.length() ? true: a.charAt(i) <= b.charAt(i);
}
http://likemyblogger.blogspot.com/2015/08/leetcode-248-strobogrammatic-number-iii.html


    int compareStr(string s1, string s2){

        int n1 = s1.size();

        int n2 = s2.size();

        if(n1<n2){

            for(int i=0; i<n2-n1; ++i) s1 = "0"+s1;

        }else if(n1>n2){

            for(int i=0; i<n1-n2; ++i) s2 = "0"+s2;

        }

        return s1.compare(s2);

    }

    void findStrobogrammatic(int n, int nn, vector<string> &res) {

        if(n==0) return;

        else if(n==1){

            res.push_back("0");

            res.push_back("1");

            res.push_back("8");

        }else if(n==2){

            findStrobogrammatic(1, nn, res);

            res.push_back("11");

            res.push_back("69");

            res.push_back("88");

            res.push_back("96");

            if(n<nn) res.push_back("00");

        }else{

            findStrobogrammatic(n-1, nn, res);

            vector<string> tmp = res;

            for(auto s:tmp){

                if(s.size()!=n-2) continue;

                if(n<nn){

                    string s0 = "0"+s+"0";

                    res.push_back(s0);

                }

                string s1 = "1"+s+"1";

                res.push_back(s1);

                string s2 = "8"+s+"8";

                res.push_back(s2);

                string s3 = "6"+s+"9";

                res.push_back(s3);

                string s4 = "9"+s+"6";

                res.push_back(s4);

            }

        }

    }



    int strobogrammaticInRange(string low, string high) {

        int n = high.size();

        vector<string> res;

        findStrobogrammatic(n, n, res);

        int cnt = 0;

        for(auto s:res){

            if(s.size()>1 && s[0]=='0') continue;

            if(compareStr(s, low)>=0 && compareStr(s, high)<=0){

                cnt++;

            }

        }

        return cnt;

    }


    private char[] validNumbers = new char[]{'0', '1', '6', '8', '9'};
    private char[] singleable = new char[]{'0', '1', '8'};

    public int strobogrammaticInRange(String low, String high) {
        assert low != null && high != null;

        int ll = low.length();
        int hl = high.length();
        int result = 0;
        
        if(ll > hl || (ll == hl&&low.compareTo(high) > 0)) {
            return 0;
        }

        List<String> list = findStrobogrammatic(ll);

        if (ll == hl) {
            for (String s : list) {
                if (s.compareTo(low) >= 0 && s.compareTo(high) <= 0) {
                    result++;
                }
                if (s.compareTo(high) > 0) {
                    break;
                }
            }
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                String s = list.get(i);
                if (s.compareTo(low) >= 0) {
                    result++;
                }
                if (s.compareTo(low) < 0) {
                    break;
                }
            }
            list = findStrobogrammatic(hl);
            for (String s : list) {
                if (s.compareTo(high) <= 0) {
                    result++;
                }
                if (s.compareTo(high) > 0) {
                    break;
                }
            }

            for (int i = ll + 1; i < hl; i++) {
                result += findStrobogrammatic(i).size();
            }
        }
        return result;
    }

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
Read full article from LeetCode: Strobogrammatic Number III | CrazyEgg
 * @author het
 *
 */

/**
 *  * LeetCode 248 - Strobogrammatic Number III

LeetCode: Strobogrammatic Number III | CrazyEgg
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
For example,
Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.
Note:
Because the range might be a large number, the low and high numbers are represented as string.
 * @author het
 *
 */
public class LeetCode248 {
	// low   355  3    high   777777 6
	 // low  99999999        10000000000000000 --
    public int getNumberOfStrobogrammaticNumbers(String low, String high){
    	if(low == null || high == null || low.length() == 0 || high.length() == 0) return 0;
    	int lowDigits = low.length();
    	int highDigits = high.length();
    	
    	if((lowDigits ==highDigits && low.compareTo(high) > 0) || lowDigits > highDigits) return 0;
    	if(lowDigits == highDigits){
    		int digits = low.length();
    		int []  result = new int[1];
    		findStrobogrammaticNumber(result, low, high,  new char [digits], 0, digits-1, digits);
    		return result[0];
    	}else{
    		
        	int result = 0;
        	for(int i= lowDigits+1; i<highDigits;i+=1){
        		result+=findStrobogrammaticNumber(i);
        	}
        	int []  resultForStrobogrammaticNumberNoLessThan = new int[1];
        	int []  resultForStrobogrammaticNumberNoMoreThan = new int[1];
        	findStrobogrammaticNumberNoLessThan(resultForStrobogrammaticNumberNoLessThan, low,  new char [lowDigits], 0, lowDigits-1, lowDigits, false);
        	result+=resultForStrobogrammaticNumberNoLessThan[0];
        	findStrobogrammaticNumberNoMoreThan(resultForStrobogrammaticNumberNoMoreThan, high, new char [highDigits], 0, highDigits-1, highDigits, false);
        	result+=resultForStrobogrammaticNumberNoMoreThan[0];
        	return result;
    	}
    	
    	
    	
    }
	private void findStrobogrammaticNumber(int[] result, String low,
			String high, char[] arr, int start, int end, int n) {
		String str = new String(arr);
		if((start == end+1 || start == end +2) ){
			if(low.compareTo(str)<=0 && high.compareTo(str) >=0){
				result[0]+=1;
				return;
			}else{
				return;
			}
			
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
	private void findStrobogrammaticNumberNoMoreThan( int [] result, String high, char[] arr,
			int start,int end,  int n, boolean alreadySmaller) {
		
		if((start == end+1 || start == end +2) ){
			if(high.compareTo(new String(arr)) >=0){
				result[0]+=1;
				return;
			}else{
				return;
			}
			
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
			if(!alreadySmaller){
				arr[start] = choicesForStartDigit[i];
				
				arr[end] = choicesForEndDigit[i];
				if(arr[start] > high.charAt(start)){
					continue;
				}else{
					arr[start] = choicesForStartDigit[i];
					
					arr[end] = choicesForEndDigit[i];
					if(arr[start] < high.charAt(start)){
						findStrobogrammaticNumberNoMoreThan(result,high, arr, start+1, end -1, n, true);
					}else{
						findStrobogrammaticNumberNoMoreThan(result,high, arr, start+1, end -1, n, false);
					}
				}
			}else{
				findStrobogrammaticNumberNoMoreThan(result,high, arr, start+1, end -1, n, alreadySmaller);
			}
			
			
			//revert 
			
			
		}
		
	}
	private void findStrobogrammaticNumberNoLessThan(int [] result, String low,char[] arr,
			int start,int end,  int n, boolean alreadyBigger) {
		if((start == end+1 || start == end +2 )){
			if(low.compareTo(new String(arr)) <=0){
				result[0]+=1;
				return;
			}else{
				return;
			}
			
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
			if(!alreadyBigger){
				arr[start] = choicesForStartDigit[i];
				
				arr[end] = choicesForEndDigit[i];
				if(arr[start] < low.charAt(start)){
					continue;
				}else{
					arr[start] = choicesForStartDigit[i];
					
					arr[end] = choicesForEndDigit[i];
					if(arr[start] > low.charAt(start)){
						findStrobogrammaticNumberNoLessThan(result,low, arr, start+1, end -1, n, true);
					}else{
						findStrobogrammaticNumberNoLessThan(result,low, arr, start+1, end -1, n, false);
					}
				}
			}else{
				findStrobogrammaticNumberNoLessThan(result,low, arr, start+1, end -1, n, alreadyBigger);
			}
			
			
			//revert 
			
			
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode248().getNumberOfStrobogrammaticNumbers("50", "100"));

	}
	
	
	public static  int findStrobogrammaticNumber(int n){
		
		if(n <= 0 ) return 0;
		// even or odd   69 69       6[108]9     1 0 8 
		if(n == 1) {
			
			return 3;
		}
		int [] result = new int[1];
		findStrobogrammaticNumber(result, new char [n], 0, n-1, n);
		return result[0];
	}
	
	
	private static void findStrobogrammaticNumber(int [] result, char[] arr,
			int start,int end,  int n) {
		// even or odd
		//  even   start = end+1
		// odd     start = end +2
		// start == end   0  8 1
		// other case     0  8 1 6 9
		if(start == end+1 || start == end +2){
			result[0]+=1;
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

}
