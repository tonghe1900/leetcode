package alite.leetcode.xx4.select;
/**
 * http://bookshadow.com/weblog/2016/10/09/leetcode-sentence-screen-fitting/
Given a rows x cols screen and a sentence represented by a list of words, find how many times 
the given sentence can be fitted on the screen.
Note:
A word cannot be split into two lines.
The order of words in the sentence must remain unchanged.
Two consecutive words in a line must be separated by a single space.
Total words in the sentence won't exceed 100.
Length of each word won't exceed 10.
1 ≤ rows, cols ≤ 20,000.
Example 1:
Input:
rows = 2, cols = 8, sentence = ["hello", "world"]

Output: 
1

Explanation:
hello---
world---

The character '-' signifies an empty space on the screen.
Example 2:
Input:
rows = 3, cols = 6, sentence = ["a", "bcd", "e"]

Output: 
2

Explanation:
a-bcd- 
e-a---
bcd-e-

The character '-' signifies an empty space on the screen.
Example 3:
Input:
rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]

Output: 
1

Explanation:
I-had
apple
pie-I
had--

The character '-' signifies an empty space on the screen.

https://discuss.leetcode.com/topic/62455/21ms-18-lines-java-solution
String s = String.join(" ", sentence) + " " ;. This line gives us a formatted sentence to be put to our screen.
start is the counter for how many valid characters from s have been put to our screen.
if (s.charAt(start % l) == ' ') is the situation that we don't need an extra space for current row. The current row 
could be successfully fitted. So that we need to increase our counter by using start++.
The else is the situation, which the next word can't fit to current row. So that we need to remove extra characters from next word.
start / s.length() is (# of valid characters) / our formatted sentence.
    public int wordsTyping(String[] sentence, int rows, int cols) {
        String s = String.join(" ", sentence) + " ";
        int start = 0, l = s.length();
        for (int i = 0; i < rows; i++) {
            start += cols;
            if (s.charAt(start % l) == ' ') {
                start++;
            } else {
                while (start > 0 && s.charAt((start-1) % l) != ' ') {
                    start--;
                }
            }
        }
        
        return start / s.length();
    }

https://discuss.leetcode.com/topic/62364/java-optimized-solution-17ms
public int wordsTyping(String[] sentence, int rows, int cols) {
        int[] nextIndex = new int[sentence.length];
        int[] times = new int[sentence.length];
        for(int i=0;i<sentence.length;i++) {
            int curLen = 0;
            int cur = i;
            int time = 0;
            while(curLen + sentence[cur].length() <= cols) {
                curLen += sentence[cur++].length()+1;
                if(cur==sentence.length) {
                    cur = 0;
                    time ++;
                }
            }
            nextIndex[i] = cur;
            times[i] = time;
        }
        int ans = 0;
        int cur = 0;
        for(int i=0; i<rows; i++) {
            ans += times[cur];
            cur = nextIndex[cur];
        }
        return ans;
    }

https://discuss.leetcode.com/topic/62297/a-simple-simulation
http://bookshadow.com/weblog/2016/10/09/leetcode-sentence-screen-fitting/
上例中apple单词的相对位置从第二行开始循环，因此只需要找到单词相对位置的“循环节”，即可将问题简化。
利用字典dp记录循环节的起始位置，具体记录方式为：dp[(pc, pw)] = pr, ans
以数对(pc, pw)为键，其中pw为单词在句子中出现时的下标，pc为单词出现在屏幕上的列数

以数对(pr, ans)为值，其中pr为单词出现在屏幕上的行数，ans为此时已经出现过的完整句子数

    def wordsTyping(self, sentence, rows, cols):
        """
        :type sentence: List[str]
        :type rows: int
        :type cols: int
        :rtype: int
        """
        wcount = len(sentence)
        wlens = map(len, sentence)
        slen = sum(wlens) + wcount
        dp = dict()
        pr = pc = pw = ans = 0
        while pr < rows:
            if (pc, pw) in dp:
                pr0, ans0 = dp[(pc, pw)]
                loop = (rows - pr0) / (pr - pr0 + 1)
                ans = ans0 + loop * (ans - ans0)
                pr = pr0 + loop * (pr - pr0)
            else:
                dp[(pc, pw)] = pr, ans
            scount = (cols - pc) / slen
            ans += scount
            pc += scount * slen + wlens[pw]
            if pc <= cols:
                pw += 1
                pc += 1
                if pw == wcount:
                    pw = 0
                    ans += 1
            if pc >= cols:
                pc = 0
                pr += 1
        return ans

Brute Force
http://www.itdadao.com/articles/c15a559599p0.html
    public int wordsTyping(String[] sentence, int rows, int cols) 
    {
        int m = sentence.length;
        int res = 0;
        int c = 0;
        int left = cols;
        for(int i = 0; i < rows;)
        {
            if(sentence[c%m].length() <= left)
            {
                if(c%m == m-1) res++;
                c++;
                left = left - sentence[(c-1)%m].length() - 1;
                
            }
            else
            {
                i++;
                left = cols;
            }
        }
        return res;        
    }
 * @author het
 *
 */
public class LeetCode418SentenceScreenFitting {
//	The character '-' signifies an empty space on the screen.
//
//	https://discuss.leetcode.com/topic/62455/21ms-18-lines-java-solution
//	String s = String.join(" ", sentence) + " " ;. This line gives us a formatted sentence to be put to our screen.
//	start is the counter for how many valid characters from s have been put to our screen.
//	if (s.charAt(start % l) == ' ') is the situation that we don't need an extra space for current row. 
	//The current row could be successfully fitted. So that we need to increase our counter by using start++.
//	The else is the situation, which the next word can't fit to current row. So that we need to remove extra characters from next word.
//	start / s.length() is (# of valid characters) / our formatted sentence.
	public static int wordsTyping(String[] sentence, int rows, int cols) {
        String s = joinStr(sentence) + " ";
        int start = 0, l = s.length();
        for (int i = 0; i < rows; i++) {
            start += cols;
            if (s.charAt(start % l) == ' ') {
                start++;
            } else {
                while (start > 0 && s.charAt((start-1) % l) != ' ') {
                    start--;
                }
            }
        }
        
        return start / s.length();
    }
	private static String joinStr(String[] sentence) {
		StringBuilder sb = new StringBuilder();
		for(String word:sentence){
			sb.append(word).append(" ");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//rows = 3, cols = 6, sentence = ["a", "bcd", "e"]
		System.out.println(wordsTyping(new String[]{"a", "bcd", "e"}, 78, 3));
		

	}

}
