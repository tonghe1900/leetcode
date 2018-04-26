package alite.leetcode.xx3.extra;
/**
 * LeetCode 383 - Ransom Note

https://www.hrwhisper.me/leetcode-ransom-note/
 Given  an  arbitrary  ransom  note  string  and  another  string  containing  letters from  all  the  magazines,  write  a  function  that  will  return  true  if  the  ransom   note  can  be  constructed  from  the  magazines ;  otherwise,  it  will  return  false.   
Each  letter  in  the  magazine  string  can  only  be  used  once  in  your  ransom  note.
Note:
You may assume that both strings contain only lowercase letters.
canConstruct("a", "b") -> false
canConstruct("aa", "ab") -> false
canConstruct("aa", "aab") -> true


    public boolean canConstruct(String ransomNote, String magazine) {

        int[] cnt = new int[26];

        for (int i = 0; i < magazine.length(); i++) cnt[magazine.charAt(i) - 97]++;

  for (int i = 0; i < ransomNote.length(); i++) if (--cnt[ransomNote.charAt(i) - 97] < 0) return false;

  return true;

    }
http://thealphaking01.blogspot.com/2016/08/leetcode-solution-383-ransom-note.html


http://www.jianshu.com/p/5950dc7c75a5
Use hashmap - code is much complex
   public boolean canConstruct(String ransomNote, String magazine) {
    Map<Character,Integer> charmap = new HashMap<>();
    //循环把杂志中出现的单个字符计数，确定每个字符的出现频数~~
    for(int i = 0; i<magazine.length(); i++) {
        char c = magazine.charAt(i);
        if (charmap.containsKey(c)) {
            int count = (int)charmap.get(c);
            count++;
            charmap.put(c, count);
        }else {
            charmap.put(c, 1);
        }
    }
    //循环勒索信字符，每出现杂志中存在的字符，就把杂志中此字符频数减一。循环中，如果没得减了，就确定其无法构成信件！
    for(int i = 0; i<ransomNote.length() ;i++) {
        char c = ransomNote.charAt(i);
        if (charmap.containsKey(c)) {
            int count = (int)charmap.get(c);
            count--;
            if (count<0) {
                return false;
            }else {
                 charmap.put(c, count);
            }
        }else {
            return false;
        }
    }
    return true;
}

http://thealphaking01.blogspot.com/2016/08/leetcode-solution-383-ransom-note.html
    def canConstruct(self, ransomNote, magazine):
        """
        :type ransomNote: str
        :type magazine: str
        :rtype: bool
        """
        a = {}
        b = {}
        for i in magazine:
            a[i] = a.get(i,0)+1
        for i in ransomNote:
            b[i]=b.get(i,0)+1
        val=True
        for i in b.keys():
            if(a.get(i,0)<b[i]):
                val=False
                break
        return val
 * @author het
 *
 */
public class LeetCode383 {
	
	  public boolean canConstruct(String ransomNote, String magazine) {

	        int[] cnt = new int[26];

	        for (int i = 0; i < magazine.length(); i++) cnt[magazine.charAt(i) - 97]++;

	  for (int i = 0; i < ransomNote.length(); i++) if (--cnt[ransomNote.charAt(i) - 97] < 0) return false;

	  return true;

	    }
	 public boolean canConstruct(String ransomNote, String magazine) {
		    Map<Character,Integer> charmap = new HashMap<>();
		    //循环把杂志中出现的单个字符计数，确定每个字符的出现频数~~
		    for(int i = 0; i<magazine.length(); i++) {
		        char c = magazine.charAt(i);
		        if (charmap.containsKey(c)) {
		            int count = (int)charmap.get(c);
		            count++;
		            charmap.put(c, count);
		        }else {
		            charmap.put(c, 1);
		        }
		    }
		    //循环勒索信字符，每出现杂志中存在的字符，就把杂志中此字符频数减一。循环中，如果没得减了，就确定其无法构成信件！
		    for(int i = 0; i<ransomNote.length() ;i++) {
		        char c = ransomNote.charAt(i);
		        if (charmap.containsKey(c)) {
		            int count = (int)charmap.get(c);
		            count--;
		            if (count<0) {
		                return false;
		            }else {
		                 charmap.put(c, count);
		            }
		        }else {
		            return false;
		        }
		    }
		    return true;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
