package alite.leetcode.xx3;
/**
 * LeetCode 345 - Reverse Vowels of a String

http://blog.csdn.net/ebowtang/article/details/51228095
Write a function that takes a string as input and reverse only the vowels of a string.
Example 1:
Given s = "hello", return "holle".
Example 2:
Given s = "leetcode", return "leotcede".
https://leetcode.com/discuss/98987/java-standard-two-pointer-solution
In the inner while loop, don't forget the condition "start less than end" while incrementing start and decrementing end. This is my friend's google phone interview question. Cheers! // update! May use a HashSet to reduce the look up time to O(1)
public String reverseVowels(String s) {
    if(s == null || s.length()==0) return s;
    String vowels = "aeiouAEIOU";
    char[] chars = s.toCharArray();
    int start = 0;
    int end = s.length()-1;
    while(start<end){

        while(start<end && !vowels.contains(chars[start]+"")){
            start++;
        }

        while(start<end && !vowels.contains(chars[end]+"")){
            end--;
        }

        char temp = chars[start];
        chars[start] = chars[end];
        chars[end] = temp;

        start++;
        end--;
    }
    return new String(chars);
}
http://www.programcreek.com/2015/04/leetcode-reverse-vowels-of-a-string-java/
Use hashset
public String reverseVowels(String s) {
    ArrayList<Character> vowList = new ArrayList<Character>();
    vowList.add('a');
    vowList.add('e');
    vowList.add('i');
    vowList.add('o');
    vowList.add('u');
    vowList.add('A');
    vowList.add('E');
    vowList.add('I');
    vowList.add('O');
    vowList.add('U');
 
    char[] arr = s.toCharArray();
 
    int i=0; 
    int j=s.length()-1;
 
    while(i<j){
        if(!vowList.contains(arr[i])){
            i++;
            continue;
        }
 
        if(!vowList.contains(arr[j])){
            j--;
            continue;
        }
 
        char t = arr[i];
        arr[i]=arr[j];
        arr[j]=t;
 
        i++;
        j--; 
    }
 
    return new String(arr);
}
https://leetcode.com/discuss/99000/4ms-java-assume-ascii-input
    private final char[] vowels = new char[]{'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};

    public String reverseVowels(String s) {
        boolean[] map = new boolean[256];
        for (char c : vowels) map[c] = true;
        int i = 0;
        int j = s.length() - 1;
        char[] word = s.toCharArray();
        while (i < j) {
            while (i < j && !map[word[i]]) i++;
            while (i < j && !map[word[j]]) j--;
            char temp = word[i];
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return new String(word);
    }

典型的双指针问题，注意边界条件即可！
    void swapchar(string &str,int pos1,int pos2)  
    {  
        char tmpch=str[pos2];  
        str[pos2]=str[pos1];  
        str[pos1]=tmpch;  
    }  
    string reverseVowels(string s) {
        char base[10]={'a','e','i','o','u','A','E','I','O','U'};
        set<char> seting(base,base+10);
        int startpos=0,endpos=s.size()-1;
        while(startpos < endpos )
        {
            while( startpos < endpos && seting.find(s[startpos]) == seting.end())
                startpos++;
            while( startpos < endpos && seting.find(s[endpos]) == seting.end())
                endpos--;
            if(startpos < endpos)    
                swapchar(s,startpos++,endpos--);    
        }
        return s;
    }
 * @author het
 *
 */
public class LeetCode345 {
	//https://leetcode.com/discuss/98987/java-standard-two-pointer-solution
	//	In the inner while loop, don't forget the condition "start less than end" while incrementing start 
	//and decrementing end. This is my friend's google phone interview question. 
	//Cheers! // update! May use a HashSet to reduce the look up time to O(1)
		public String reverseVowels(String s) {
		    if(s == null || s.length()==0) return s;
		    String vowels = "aeiouAEIOU";
		    char[] chars = s.toCharArray();
		    int start = 0;
		    int end = s.length()-1;
		    while(start<end){

		        while(start<end && !vowels.contains(chars[start]+"")){
		            start++;
		        }

		        while(start<end && !vowels.contains(chars[end]+"")){
		            end--;
		        }

		        char temp = chars[start];
		        chars[start] = chars[end];
		        chars[end] = temp;

		        start++;
		        end--;
		    }
		    return new String(chars);
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
