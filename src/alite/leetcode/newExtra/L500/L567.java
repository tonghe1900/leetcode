package alite.leetcode.newExtra.L500;
///**
// * LeetCode 567 - Permutation in String
//
//https://leetcode.com/problems/permutation-in-string
//Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, 
//one of the first string's permutations is the substring of the second string.
//Example 1:
//Input:s1 = "ab" s2 = "eidbaooo"
//Output:True
//Explanation: s2 contains one permutation of s1 ("ba").
//Example 2:
//Input:s1= "ab" s2 = "eidboaoo"
//Output: False
//Note:
//The input strings only contain lower case letters.
//The length of both given strings is in range [1, 10,000].
//https://discuss.leetcode.com/topic/87845/java-solution-sliding-window
//How do we know string p is a permutation of string s? Easy, each character in p is in s too. So we can abstract all permutation strings of s to a map (Character -> Count). i.e. abba -> {a:2, b:2}. Since there are only 26 lower case letters in this problem, we can just use an array to represent the map.
//How do we know string s2 contains a permutation of s1? We just need to create a sliding window with length of s1, move from beginning to the end of s2. When a character moves in from right of the window, we subtract 1 to that character count from the map. When a character moves out from left of the window, we add 1 to that character count. So once we see all zeros in the map, meaning equal numbers of every characters between s1 and the substring in the sliding window, we know the answer is true.
//    public boolean checkInclusion(String s1, String s2) {
//        int len1 = s1.length(), len2 = s2.length();
//        if (len1 > len2) return false;
//        
//        int[] count = new int[26];
//        for (int i = 0; i < len1; i++) {
//            count[s1.charAt(i) - 'a']++;
//            count[s2.charAt(i) - 'a']--;
//        }
//        if (allZero(count)) return true;
//        
//        for (int i = len1; i < len2; i++) {
//            count[s2.charAt(i) - 'a']--;
//            count[s2.charAt(i - len1) - 'a']++;
//            if (allZero(count)) return true;
//        }
//        
//        return false;
//    }
//    
//    private boolean allZero(int[] count) {
//        for (int i = 0; i < 26; i++) {
//            if (count[i] != 0) return false;
//        }
//        return true;
//    }
//https://discuss.leetcode.com/topic/87861/c-java-clean-code-with-explanation
//    public boolean checkInclusion(String s1, String s2) {
//        char[] ca1 = s1.toCharArray(), ca2 = s2.toCharArray();
//        int[] cnts = new int[256];
//        for (char ch : ca1) cnts[ch]++;
//
//        int left = ca1.length;
//        for (int i = 0, j = 0; j < ca2.length; j++) {
//            if (cnts[ca2[j]]-- > 0) left--;
//
//            while (left == 0) {
//                if (j + 1 - i == ca1.length) return true;
//                if (++cnts[ca2[i++]] > 0) left++;
//            }
//        }
//
//        return false;
//    }
//https://discuss.leetcode.com/topic/87884/8-lines-slide-window-solution-in-java
//    public boolean checkInclusion(String s1, String s2) {
//        int[] count = new int[128];
//        for(int i = 0; i < s1.length(); i++) count[s1.charAt(i)]--;
//        for(int l = 0, r = 0; r < s2.length(); r++) {
//            if (++count[s2.charAt(r)] > 0)
//                while(--count[s2.charAt(l++)] != 0) { /* do nothing */}
//            else if ((r - l + 1) == s1.length()) return true;
//        }
//        return false;
//    }
//http://bookshadow.com/weblog/2017/04/30/leetcode-permutation-in-string/
//滑动窗口（Sliding Window） 时间复杂度O(n)
//由于输入只包含小写字母，因此可以通过统计字母个数判断字符串是否互为对方的排列，其时间复杂度为O(1)。
// * @author het
// *
// */
public class L567 {
	public boolean checkInclusion(String s1, String s2) {
//      int len1 = s1.length(), len2 = s2.length();
//      if (len1 > len2) return false;
//      
//      int[] count = new int[26];
//      for (int i = 0; i < len1; i++) {
//          count[s1.charAt(i) - 'a']++;
//          count[s2.charAt(i) - 'a']--;
//      }
//      if (allZero(count)) return true;
//      
//      for (int i = len1; i < len2; i++) {
//          count[s2.charAt(i) - 'a']--;
//          count[s2.charAt(i - len1) - 'a']++;
//          if (allZero(count)) return true;
//      }
//      
//      return false;
//  }
//  
//  private boolean allZero(int[] count) {
//      for (int i = 0; i < 26; i++) {
//          if (count[i] != 0) return false;
//      }
//      return true;
//  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
