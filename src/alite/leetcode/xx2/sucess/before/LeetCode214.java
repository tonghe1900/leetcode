package alite.leetcode.xx2.sucess.before;
///**
// * LeetCode 214 - Shortest Palindrome
//
//LeetCode 214 - Shortest Palindrome - 我的博客 - ITeye技术网站
//Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the 
//shortest palindrome you can find by performing this transformation.
//For example:
//Given "aacecaaa", return "aaacecaaa".
//Given "abcd", return "dcbabcd".
//X. O(N)  TODO
//https://discuss.leetcode.com/topic/21068/my-7-lines-recursive-java-solution
//The idea is to use two anchors j and i to compare the String from beginning and end.
//If j can reach the end, the String itself is Palindrome. Otherwise, we divide the String by j, and get mid = s.substring(0, j) and suffix.
//We reverse suffix as beginning of result and recursively call shortestPalindrome to get result of mid then appedn suffix to get result.
//
//At first I clarify some notation: in the following paragraph mid point of a palindrome "abcdcba" denotes 'd'; when I say charAt(1)=='b' pairs with charAt(5)=='b', I mean they are at symmetric positions.
//Take "gxybakbkabbfmbnnnjjjyxqg" as example. After for loop j stops at j'==10 where charAt(10)=='b'. Then it reaches such a status: To make the final result be a valid palindrome, in the final result this 'b' at index j' neither could be the mid point nor could pair with any other 'b' in the string s.
//Now we prove this by contradiction, in 1st case if charAt(j') is the mid point, then substring(0,2*j'+1) should be palindrome (otherwise the final result was not palindrome), and when substring(0,2*j'+1) is palindrome, using our for loop mentioned before, j would stops with index at least 2*j'+1, which is a contradiction. Similarly in 2nd case if charAt(j') could pair with some charAt(i) in the final result, then substring(0,j'+i+1) should be palindrome, if j'+i+1 is already > s.length(), it's a contradiction(we can't add characters at backside); else when substring(0,j'+i+1) is palindrome, j would stops with index at least j'+i+1, which is a contradiction.
//Then we have the conclusion: to pair the charAt(j)=='b' in the final result, we must add new char 'b' from the front of s, and since they are paired, all chars on the right side of j should be paired by some newly added chars => We MUST add substring(j)'s reverse string in front of s => This is the shortest way to transform s to palindrome. Later we deal with substring(0,j) recursively.
//
//http://www.cnblogs.com/yrbbest/p/4982859.html
//主要使用两个指针从前后对向遍历，就跟我们判断String是否是Palindrome一样，假如s.charAt(i) == s.charAt(j)，则j++。走完之后的结果j所在假如是s.length() - 1，则整个String为Palindrome，返回s，否则，j所在的位置及其以后的部分肯定不是Palindrome，这是我们要把这部分翻转并且加到结果的前面。至于 substring(0, j)这部分，我们仍需要使用递归的方法继续判断。非常非常巧妙。其实一开始思考求以s.charAt(0)为起点的方法时，觉得应该有方法可以不用KMP，Manacher，Rabin-Karp等等String Match的方法
//
//http://www.programcreek.com/2014/06/leetcode-shortest-palindrome-java/
//
//public String shortestPalindrome(String s) {
//    int i=0; 
//    int j=s.length()-1;
// 
//    while(j>=0){
//        if(s.charAt(i)==s.charAt(j)){
//            i++;
//        }
//        j--;
//    }
// 
//    if(i==s.length())
//        return s;
// 
//    String suffix = s.substring(i);
//    String prefix = new StringBuilder(suffix).reverse().toString();
//    String mid = shortestPalindrome(s.substring(0, i));
//    return prefix+mid+suffix;
//}
//Also http://blog.csdn.net/xudli/article/details/45931667
//
//X. http://www.programcreek.com/2014/06/leetcode-shortest-palindrome-java/
//We can solve this problem by using one of the methods which is used to solve the longest palindrome substring problem.
//Specifically, we can start from the center and scan two sides. If read the left boundary, then the shortest palindrome is identified.
//public String shortestPalindrome(String s) {
// if (s == null || s.length() <= 1)
//  return s;
// 
// String result = null;
// 
// int len = s.length();
// int mid = len / 2; 
// 
// for (int i = mid; i >= 1; i--) {
//  if (s.charAt(i) == s.charAt(i - 1)) {
//   if ((result = scanFromCenter(s, i - 1, i)) != null)
//    return result;
//  } else {
//   if ((result = scanFromCenter(s, i - 1, i - 1)) != null)
//    return result;
//  }
// }
// 
// return result;
//}
// 
//private String scanFromCenter(String s, int l, int r) {
// int i = 1;
// 
// //scan from center to both sides
// for (; l - i >= 0; i++) {
//  if (s.charAt(l - i) != s.charAt(r + i))
//   break;
// }
// 
// //if not end at the beginning of s, return null 
// if (l - i >= 0)
//  return null;
// 
// StringBuilder sb = new StringBuilder(s.substring(r + i));
// sb.reverse();
// 
// return sb.append(s).toString();
//}
//
//http://shibaili.blogspot.com/2015/06/day-108-add-and-search-word-data.html
//O(n^2) 找prefix是palindrome。超时。 
//
//    bool isPalindrome(string s) {
//
//        for (int i = 0; i < s.length() / 2; i++) {
//
//            if (s[i] != s[s.length() - 1 - i]) return false;
//
//        }
//
//         
//
//        return true;
//
//    }
//
//     
//
//    int longestPal(string s) {
//
//        for (int i = s.length(); i > 0; i--) {
//
//            if (isPalindrome(s.substr(0,i))) {
//
//                return i;
//
//            }
//
//        }
//
//         
//
//        return 0;
//
//    }
//
//     
//
//    string shortestPalindrome(string s) {
//
//        int length = longestPal(s);
//
//        string copy = s;
//
//        for (int i = 0; i < copy.length() - length; i++) {
//
//            s = copy[length + i] + s;    
//
//        }
//
//         
//
//        return s;
//
//    }
//X. KMP - TODO
//https://discuss.leetcode.com/topic/21713/a-kmp-based-java-solution-with-explanation
//The Idea of using KMP to find the shortest palindrome has been proposed in the previous post but did not have a very clear explanation on how this simple piece of code works. I will post my own understanding of why KMP method works well on this problem followed by my thinking process.
//First of All, My origin thought on this problem is to find the longest palindrome from the starting character of the string. Then we can just adding the reverse of the remaining characters to the front of the origin string to get the required shortest palindrome. However, the time complexity for finding the longest palindrome from front can cost O(n^2) and will cause a TLE when coding in Java.
//Then I found this solution post C++ 8 ms KMP-based O(n) time & O(n) memory solution
//The code and the idea were great but the explanation was somehow lacking some insight of how people construct this solution. After read the code as well as the provided explanation, I became quiet confused with the following questions:
//Why should we have a combined string and why this string should be combined like this. Can we combined the string with the reverse string first and the origin string second?
//What's the use of the array/vector and what value does it store? Is the numeric value represent length or something else?
//What is "recursively" updating the index means?
//In order to answer these questions, we have to look back to my origin idea which is finding the longest palindrome from the front. In fact the KMP solution use a similar idea which is finding the longest prefix which has an identical counter part string that ends at the tail of the combined string(longest suffix) For example, let's say we have a string "abac#caba", then the qualified string will be "aba".
//How can we find this string? The KMP method provide us with a way to accomplish that.The detail implementation is using an array of the length of the combined string to store the index of the character which need to be compared with the next character if current character matches the prefix's last character. If they are the same, the index that need to be stored for the next character will be the previous stored index+1. Otherwise, we will jump further back to the index of the index to perform the same procedure until the index become zero. Then in the end, the value-1 in the last array element will represent the longest common prefix's last index value and we find the longest common prefix. Since these two strings are reversed and identical string, it must be a palindrome which fulfills my origin goal of finding the longest palindrome from the starting character.
//One example for the above process for the string s "abac#caba". we will have the stored indexes be "0,0,1,0,0,1,2,3" .Let's say we are at index 7 of the string s and the character is 'b' and in the array we have 1 for the previous index 6. This means the character at index 1 will need to be compared with character b to decide whether the common prefix from the front can continue to grow or not. Since both characters at index 1 and at index 7 are 'b', the common prefix grows into length 2 which is"ab".
//Back to the first question, since the KMP method will help us find the longest prefix that exist in the body of the new string and we want to find the longest palindrome prefix of the origin string. We have to have the origin string at front and reverse string follows. For the second question, the array store index of the prefix that need to be match for the next character. For the last question, the recursive update happens when the matching failure so we jump to the further back to match early index in the prefix.
//    public String shortestPalindrome(String s) {
//        if(s.length()<=1) return s;
//        String new_s = s+"#"+new StringBuilder(s).reverse().toString();
//        int[] position = new int[new_s.length()];
//        
//        for(int i=1;i<position.length;i++)
//        {
//            int pre_pos = position[i-1];
//            while(pre_pos>0 && new_s.charAt(pre_pos)!=new_s.charAt(i))
//                pre_pos = position[pre_pos-1];
//            position[i] = pre_pos+((new_s.charAt(pre_pos)==new_s.charAt(i))?1:0);
//        }
//        
//        return new StringBuilder(s.substring(position[position.length-1])).reverse().toString()+s;
//    }
//https://discuss.leetcode.com/topic/27261/clean-kmp-solution-with-super-detailed-explanation
//
//看了答案！对KMP要灵活运用
//s + special char + reverse(s) 
//然后运用KMP的failure function计算出s的prefix和revsers(s)的suffix相等的最长长度
//ref: https://leetcode.com/discuss/36807/c-8-ms-kmp-based-o-n-time-%26-o-n-memory-solution 
//
//    vector<int> computePrefixTable(string pattern) {
//
//        int m = pattern.length();
//
//        vector<int> table(m,0);
//
//        int matchedLength = 0;
//
//         
//
//        for (int i = 1; i < m; i++) {
//
//            // until find the next char at matchedLength is equal to char at i
//
//            // or matchedLength is zero
//
//            while (matchedLength > 0 && pattern[matchedLength] != pattern[i]) {
//
//                matchedLength = table[matchedLength - 1];
//
//            }
//
//            if (pattern[matchedLength] == pattern[i]) {
//
//                matchedLength++;
//
//            }
//
//            table[i] = matchedLength;
//
//        }
//
//         
//
//        return table;
//
//    }
//
//    string shortestPalindrome(string s) {
//
//        string rev = s;
//
//        reverse(rev.begin(),rev.end());
//
//        string pattern = s + "*" + rev;
//
//        vector<int> table = computePrefixTable(pattern);
//
//         
//
//        return rev.substr(0,rev.length() - table[table.size() - 1]) + s;
//
//    }
//http://www.jianshu.com/p/989c68079d93
//一开始没有注意是hard难度，以为只需要取得s的逆向字符串reverse_s，将s作为模式串，reverse_s作为主串。即找到reverse_s中能匹配到最后一个字母的前提下的最长的s中的子串。因此就如普通的模式串匹配一样，复杂度是O(n^2)。
//比如：
//reverse_s	d	c	b	a	-	-	-
//s	-	-	-	a	b	c	d 又如：
//reverse_s	a	a	a	c	e	c	a	a	-
//s	-	a	a	c	e	c	a	a	a 后来发现其实用kmp字符串匹配算法就可以完美地完成O(n)复杂度的算法。只需对kmp做一点点调整：
//判断匹配结束的时机：是reverse_s串匹配到了尾部，且匹配成功。
//用mark记录匹配到结尾时s串的下标位置：为了从s串尾部截取子串接到reverse_s后形成回文字符串。
//如何保证最短：由于第一次成功匹配到reverse_s尾部后就结束循环，此时的mark标记的位置所形成的回文段（字符串s从0到mark下标所形成的子串）应该是最长的。
//使用c++自带stl函数：reverse操作，substr操作。
//    string shortestPalindrome(string s) {
//        if (s.empty()) return s;
//        string reverse_s(s);
//        reverse(reverse_s.begin(), reverse_s.end());
//        int mark;
//        vector<int> next(s.size());
//        makeNext(s, next);
//
//        for (int i = 0, j = 0; i < reverse_s.size(); ) {
//            if (j == -1 || reverse_s[i] == s[j]) {
//                ++i; ++j;
//                if (i == reverse_s.size()) {
//                    mark = j;
//                }
//            } else {
//                j = next[j];
//            }
//        }
//
//        return mark == s.size() ? reverse_s : reverse_s + s.substr(mark);
//    }
//
//    void makeNext(string& s, vector<int>& next) {
//        next[0] = -1;
//        for (int i = 0, j = -1; i < s.size(); ) {
//            if (j == -1 || s[i] == s[j]) {
//                ++i; ++j;
//                if (i != s.size()) {
//                    if (s[i] == s[j]) {
//                        next[i] = next[j];
//                    } else {
//                        next[i] = j;
//                    }
//                }
//            } else {
//                j = next[j];
//            }
//        }
//    }
//http://happycoding2010.blogspot.com/2015/10/leetcode-214-shortest-palindrome.html
//Solution 1: use KMP calculate next array. If R is the reverse of s. Make a combo string which is s+'#'+R.
// public class Solution {  
//   public String shortestPalindrome(String s) {  
//     int n=s.length();  
//     if (n==0) return "";  
//     StringBuilder sb=new StringBuilder(s);  
//     String combo=s+'#'+sb.reverse().toString();  
//     int[] next=new int[2*n+2];  
//     next[0]=-1;  
//     int j=0, k=-1;  
//     while (j<2*n+1) {  
//       if (k==-1 || combo.charAt(k)==combo.charAt(j)) {  
//         k++;  
//         j++;  
//         next[j]=k;  
//       }  
//       else k=next[k];  
//     }  
//     StringBuilder res=new StringBuilder(s.substring(next[2*n+1]));  
//     return res.reverse().toString()+s;  
//   }  
// }  
//Solution 2: calculate next array of s, then search in R. O(n)
// public class Solution {  
//   public String shortestPalindrome(String s) {  
//     int n=s.length();  
//     if (n<2) return s;  
//     int[] next=new int[n];  
//     int k=-1,j=0;  
//     next[0]=-1;  
//     while (j<n-1) {  
//       if (k==-1 || s.charAt(k)==s.charAt(j)) {  
//         k++;  
//         j++;  
//         next[j]=s.charAt(k)==s.charAt(j)?next[k]:k;  
//       }  
//       else k=next[k];  
//     }  
//     String r=new StringBuilder(s).reverse().toString();  
//     int i=0;  
//     j=0;  
//     while (i<n && j<n) {  
//       if (j==-1 || r.charAt(i)==s.charAt(j)) {  
//         i++;  
//         j++;  
//       }  
//       else j=next[j];  
//     }  
//     return new StringBuilder(s.substring(j)).reverse().toString()+s;  
//   }  
// }  
//Solution 3: optimize space and time complexity to be half of solution 2.
// public class Solution {  
//   public String shortestPalindrome(String s) {  
//     int n=s.length();  
//     if (n<2) return s;  
//     int[] next=new int[n/2];  
//     int k=-1,j=0;  
//     next[0]=-1;  
//     while (j<n/2-1) {  
//       if (k==-1 || s.charAt(k)==s.charAt(j)) {  
//         k++;  
//         j++;  
//         next[j]=s.charAt(k)==s.charAt(j)?next[k]:k;  
//       }  
//       else k=next[k];  
//     }  
//     String r=new StringBuilder(s).reverse().toString();  
//     int i=0;  
//     j=0;  
//     while (i+j<n) {  
//       if (j==-1 || r.charAt(i)==s.charAt(j)) {  
//         i++;  
//         j++;  
//       }  
//       else j=next[j];  
//     }  
//     return r.substring(0,i-j)+s;  
//   }  
// }  
//http://yuanhsh.iteye.com/blog/2214595
//以上代码再跑很长的字符串时会超时，所以应该用KMP来解决。
//public String shortestPalindrome(String s) {  
//    StringBuilder sb = new StringBuilder(s).reverse();  
//    for(int i=0; i<s.length(); i++) {  
//        if(s.startsWith(sb.substring(i))) {  
//            return sb.substring(0, i)+s;  
//        }  
//    }  
//    return s;  
//} 
//
//https://reeestart.wordpress.com/2016/06/14/google-minimum-add-to-make-a-palindrome/
//Given a string, return the minimum number of characters to add to make the string a Palindrome
//[Analysis]
//和leetcode214 shortest palindrome有点类似，不过这题可以算shortest palindrome的一个follow up。因为这道题可以在任何一个位置插入character，而shortest palindrome只能在两端append。
//[Solution]
//Dynamic Programming. 跟longest palindrome substring的DP解法一样。
//lookup[i][j]表示minimum add to make substring from i to j a palindrome
//if s.charAt(i) == s.charAt(j)
//lookup[i][j] = lookup[i + 1][j – 1]
//else
//lookup[i][j] = Math.min(lookup[i][j – 1], lookup[i + 1][j]) + 1
//
//而对于Leetcode214 shortest palindrome这样在两段append来make palindrome的，除了从mid开始往一边扫的方法之外。还有一种O(n) KMP的做法。比如leetcode这道:
//str = s + ‘#’ + reversed_s
//然后用kmp找longest prefix&suffix的方法找到str的longest prefix & suffix. Reverse一下s的剩余部分就是要往左边append的字符串。
//如果是从右边append，那str = reversed_s + ‘#’ + s就可以了。
//[Note]
//注意中间的’#’必不可少. 不然会出bug。
//比如aabba这个case, 如果不加’#’，str = aabbaabbaa, 这样longest prefix & suffix就会变成aabbaa，超出了原来string的长度。
//加个’#’之后就可以保证longset prefix & suffix的length小于等于s.length()
//
//class Solution {
//
//  public int minimumAdd(String s) {
//
//    if (s == null || s.isEmpty()) {
//
//      return 0;
//
//    }
//
//
//    int n = s.length();
//
//    int[][] lookup = new int[n][n];
//
//    for (int i = 1; i < n; i++) {
//
//      lookup[i - 1][i] = s.charAt(i - 1) == s.charAt(i)? 0 : 1;
//
//    }
//
//
//    for (int i = n - 2; i >= 0; i--) {
//
//      for (int j = i + 1; j < n; j++) {
//
//        if (s.charAt(i) == s.charAt(j)) {
//
//          lookup[i][j] = lookup[i + 1][j - 1];
//
//        }
//
//        else {
//
//          lookup[i][j] = 1 + Math.min(lookup[i + 1][j], lookup[i][j - 1]);
//
//        }
//
//      }
//
//    }
//
//
//    return lookup[0][n - 1];
//
//  }
//
//}
//
//
///*
//
//  P2: Shortest Palindrome, leetcode 214, KMP, O(n) time
//
//*/
//
//class Solution2 {
//
//  public String minimumAdd(String s) {
//
//    if (s == null || s.isEmpty()) {
//
//      return s;
//
//    }
//
//
//    String str = s + "#" + new StringBuilder(s).reverse().toString();
//
//    int[] preSuffix = kmp(str);
//
//
//    int longestLen = preSuffix[str.length() - 1];
//
//    String toAdd = new StringBuilder(s.substring(longestLen, s.length())).reverse()
//
//        .toString();
//
//    return toAdd + s;
//
//  }
//
//
//  private int[] kmp(String s) {
//
//    if (s == null || s.length() == 0) {
//
//      throw new IllegalArgumentException();
//
//    }
//
//    int[] result = new int[s.length()];
//
//
//    int j = 0;
//
//    int i = 1;
//
//    while (i < s.length()) {
//
//      if (s.charAt(i) == s.charAt(j)) {
//
//        result[i] = j + 1;
//
//        i++;
//
//        j++;
//
//      }
//
//      else if (j == 0) {
//
//        i++;
//
//      }
//
//      else {
//
//        j = result[j - 1];
//
//      }
//
//    }
//
//    return result;
//
//  }
//
//}
//
//X. Manacher
//https://discuss.leetcode.com/topic/25051/java-solution-using-manacher-algorithm
//This is my solution using Manacher Algorithm.
//The Manacher Algorithm is used to find the longest palindromic substring, we can modify it to search to obtain the longest palindromic substring starting at index 0.
//Once we have the longest palindrome starting at 0, we insert the remaining characters from the original string 1 by 1 at the head of the original string.
//    public static String manacherize(String s) {
//  StringBuilder ms = new StringBuilder("^");
//  for(int i=0;i<s.length();i++) {
//   ms.append("#"+s.charAt(i));
//  }
//  ms.append("#$");
//  return ms.toString();
// }
// public static String longestPalindromeStartingFrom0(String s) {
//  if(s.length()==0) return "";
//  String T = manacherize(s);
//  int[] P = new int[T.length()];
//  int C = 0;
//  int R = 0;
//  for(int i=1;i<T.length()-1;i++) {
//   int imirror = C-(i-C);
//   P[i] = (R > i) ? Math.min(R-i, P[imirror]) : 0;
//   while(T.charAt(i+1+P[i])==T.charAt(i-1-P[i])) {
//    P[i]++;
//   }
//   if(i+P[i]>R) {
//    C = i;
//    R = i + P[i];
//   }
//  }
//  int maxLen = 0;
//  int maxCenter = 0;
//  for(int i=1;i<P.length-1;i++) {
//   if(P[i]>maxLen && i-P[i]==1) {
//    maxLen = P[i];
//    maxCenter = i;
//   }
//  }
//  int start = (maxCenter-1-maxLen)/2;
//  return s.substring(start,start+maxLen);
// }
// 
// public static String shortestPalindrome(String s) {
//  String pal = longestPalindromeStartingFrom0(s);
//  StringBuilder prefix = new StringBuilder();
//  for(int i=pal.length();i<s.length();i++) {
//   prefix.insert(0, s.charAt(i));
//  }
//  return prefix+s;
// }
//X. Brute Force
//https://discuss.leetcode.com/topic/25860/my-9-lines-three-pointers-java-solution-with-explanation
//The key point is to find the longest palindrome starting from the first character, and then reverse the remaining part as the prefix to s. Any advice will be welcome!
//public String shortestPalindrome(String s) {
//    int i = 0, end = s.length() - 1, j = end; char chs[] = s.toCharArray();
//    while(i < j) {
//         if (chs[i] == chs[j]) {
//             i++; j--;
//         } else { 
//             i = 0; end--; j = end;
//         }
//    }
//    return new StringBuilder(s.substring(end+1)).reverse().toString() + s;
//}
//https://discuss.leetcode.com/topic/14542/ac-in-288-ms-simple-brute-force
//def shortestPalindrome(self, s):
//    r = s[::-1]
//    for i in range(len(s) + 1):
//        if s.startswith(r[i:]):
//            return r[:i] + s
//Example: s = dedcba. Then r = abcded and I try these overlays (the part in (...) is the prefix I cut off, I just include it in the display for better understanding):
//  s          dedcba
//  r[0:]      abcded    Nope...
//  r[1:]   (a)bcded     Nope...
//  r[2:]  (ab)cded      Nope...
//  r[3:] (abc)ded       Yes! Return abc + dedcba
//
//Read full article from LeetCode 214 - Shortest Palindrome - 我的博客 - ITeye技术网站
// * @author het
// *
// */
public class LeetCode214 {
////	The idea is to use two anchors j and i to compare the String from beginning and end.
////	If j can reach the end, the String itself is Palindrome. Otherwise, we divide the String by j, and get mid = 
	//s.substring(0, j) and suffix.
////	We reverse suffix as beginning of result and recursively call shortestPalindrome to get result of mid then appedn suffix to get result.
////
////	At first I clarify some notation: in the following paragraph mid point of a palindrome "abcdcba" denotes 'd'; when I say charAt(1)=='b' pairs with charAt(5)=='b', I mean they are at symmetric positions.
////	Take "gxybakbkabbfmbnnnjjjyxqg" as example. After for loop j stops at j'==10 where charAt(10)=='b'. Then it reaches such a status: To make the final result be a valid palindrome, in the final result this 'b' at index j' neither could be the mid point nor could pair with any other 'b' in the string s.
////	Now we prove this by contradiction, in 1st case if charAt(j') is the mid point, then substring(0,2*j'+1) should be palindrome (otherwise the final result was not palindrome), and when substring(0,2*j'+1) is palindrome, using our for loop mentioned before, j would stops with index at least 2*j'+1, which is a contradiction. Similarly in 2nd case if charAt(j') could pair with some charAt(i) in the final result, then substring(0,j'+i+1) should be palindrome, if j'+i+1 is already > s.length(), it's a contradiction(we can't add characters at backside); else when substring(0,j'+i+1) is palindrome, j would stops with index at least j'+i+1, which is a contradiction.
////	Then we have the conclusion: to pair the charAt(j)=='b' in the final result, we must add new char 'b' from the front of s, and since they are paired, all chars on the right side of j should be paired by some newly added chars => We MUST add substring(j)'s reverse string in front of s => This is the shortest way to transform s to palindrome. Later we deal with substring(0,j) recursively.
////
////	http://www.cnblogs.com/yrbbest/p/4982859.html
////	主要使用两个指针从前后对向遍历，就跟我们判断String是否是Palindrome一样，假如s.charAt(i) == s.charAt(j)，则j++。走完之后的结果j所在假如是s.length() - 1，则整个String为Palindrome，返回s，否则，j所在的位置及其以后的部分肯定不是Palindrome，这是我们要把这部分翻转并且加到结果的前面。至于 substring(0, j)这部分，我们仍需要使用递归的方法继续判断。非常非常巧妙。其实一开始思考求以s.charAt(0)为起点的方法时，觉得应该有方法可以不用KMP，Manacher，Rabin-Karp等等String Match的方法
////
////	http://www.programcreek.com/2014/06/leetcode-shortest-palindrome-java/

	public static String shortestPalindrome(String s) {
	    int i=0; 
	    int j=s.length()-1;
	 
	    while(j>=0){
	        if(s.charAt(i)==s.charAt(j)){
	            i++;
	        }
	        j--;
	    }
	 
	    if(i==s.length())
	        return s;
	 
	    String suffix = s.substring(i);
	    String prefix = new StringBuilder(suffix).reverse().toString();
	    String mid = shortestPalindrome(s.substring(0, i));
	    return prefix+mid+suffix;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//s
		System.out.println(shortestPalindrome("aavytaaefg"));

	}

}
