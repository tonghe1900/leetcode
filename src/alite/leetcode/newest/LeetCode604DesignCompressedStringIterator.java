package alite.leetcode.newest;
/**
 * LeetCode 604 - Design Compressed String Iterator

http://bookshadow.com/weblog/2017/06/11/leetcode-design-compressed-string-iterator/
Design and implement a data structure for a compressed string iterator. It should support the following operations: next and hasNext.
The given compressed string will be in the form of each letter followed by a positive integer representing the number of this letter existing in the original uncompressed string.
next() - if the original string still has uncompressed characters, return the next letter; Otherwise return a white space.
hasNext() - Judge whether there is any letter needs to be uncompressed.
Note:
Please remember to RESET your class variables declared in StringIterator, as static/class variables are persisted across multiple test cases. Please see here for more details.
Example:
StringIterator iterator = new StringIterator("L1e2t1C1o1d1e1");

iterator.next(); // return 'L'
iterator.next(); // return 'e'
iterator.next(); // return 'e'
iterator.next(); // return 't'
iterator.next(); // return 'C'
iterator.next(); // return 'o'
iterator.next(); // return 'd'
iterator.hasNext(); // return true
iterator.next(); // return 'e'
iterator.hasNext(); // return false
iterator.next(); // return ' '
https://discuss.leetcode.com/topic/92098/java-concise-single-queue-solution
   Queue<int[]> queue = new LinkedList<>();
    
    public StringIterator(String s) {
        int i = 0, n = s.length();
        while (i < n) {
            int j = i+1;
            while (j < n && s.charAt(j) - 'A' < 0) j++;
            queue.add(new int[]{s.charAt(i) - 'A',  Integer.parseInt(s.substring(i+1, j))});
            i = j;
        }
    }
    
    public char next() {
        if (queue.isEmpty()) return ' ';
        int[] top = queue.peek();
        if (--top[1] == 0) queue.poll();
        return (char) ('A' + top[0]);
    }
    
    public boolean hasNext() {
        return !queue.isEmpty();
    }
https://www.nowtoshare.com/en/Article/Index/70578
public class StringIterator {
    Deque<Character> q;
    char cur;
    int count;
    public StringIterator(String compressedString) {
        q = new LinkedList<>();
        cur ='#';
        count=0;
        for(char ch : compressedString.toCharArray()){
            q.offer(ch);
        }
    }
    
    public char next() {
        if(!hasNext()) return ' ';
        count--;
        return cur;
    }
    
    public boolean hasNext() {
        if(count>0) return true;
        else{
           // System.out.println(q);
            if(q.size()==0) return false;
            else{
                cur =q.pollFirst();
                count=0;
                while(!q.isEmpty() && Character.isDigit(q.peekFirst())){
                    count*=10;
                    count+=q.pollFirst()-'0';
                }
                return true;
            }
        }
    }
}

设计和实现压缩字符串的迭代器，支持函数：next和hasNext。
压缩字符串以字母+出现次数的格式给出。
将压缩字符串compressedString拆分成字母数组chars和出现次数数组times。
记原始字符串大小为size
变量idx记录chars的当前下标，cnt记录当前已经遍历过的字符个数
维护idx和cnt即可。
    private ArrayList<Character> chars = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();
    private long size, cnt;
    private int idx;
    
    public StringIterator(String compressedString) {
        StringBuilder s = new StringBuilder();
        for (char c : (compressedString + "#").toCharArray()) {
            if (c >= '0' && c <= '9') {
                s.append(c);
                continue;
            }
            if (s.length() > 0) {
                size += Integer.parseInt(s.toString());
                times.add(this.size);
                s = new StringBuilder();
            }
            if (c != '#') chars.add(c);
        }
    }
    
    public char next() {
        if (!hasNext()) return ' ';
        if (times.get(idx) < ++cnt) idx++;
        return chars.get(idx);
    }
    
    public boolean hasNext() {
        return cnt < size;
    }

X.
http://blog.csdn.net/kangbin825/article/details/73044743
public class StringIterator {  
  
    private String compressedString;  
    int c = 0;  
    private int pos = 0;  
    private char ch;  
  
    public StringIterator(String compressedString) {  
        this.compressedString = compressedString;  
    }  
  
    public char next() {  
        if (c > 0) {  
            c--;  
            return ch;  
        }  
  
        if (pos >= compressedString.length()) {  
            return ' ';  
        }  
  
        ch = compressedString.charAt(pos++);  
        while (pos < compressedString.length()  
                && isDigit(compressedString.charAt(pos))) {  
            c = c * 10 + compressedString.charAt(pos++) - '0';  
        }  
        c--;  
        return ch;  
    }  
  
    private boolean isDigit(char charAt) {  
        return Character.isDigit(charAt);  
    }  
  
    public boolean hasNext() {  
        return c > 0 || pos < compressedString.length();  
    }  
}  
https://discuss.leetcode.com/topic/92107/java-solution-two-lists
public class StringIterator {
    List<Character> chars = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();
    int ptr = 0;
    
    public StringIterator(String str) {
        int i = 0;
        while (i < str.length()) {
            chars.add(str.charAt(i));
            int j = i + 1;
            while (j < str.length() && Character.isDigit(str.charAt(j))) j++;
            counts.add(Integer.parseInt(str.substring(i + 1, j)));
            i = j;
        }
    }
    
    public char next() {
        if (!hasNext()) return ' ';
        
        char result = chars.get(ptr);
        counts.set(ptr, counts.get(ptr) - 1);
        if (counts.get(ptr) == 0) ptr++;
        return result;
    }
    
    public boolean hasNext() {
        return ptr < chars.size();
    }
}

https://discuss.leetcode.com/topic/92108/c-java-clean-code


https://discuss.leetcode.com/topic/92202/a-bit-of-java8-regex-and-very-short-solution/2
public class StringIterator {
    int i;
    String[] arr;
    int[] counts;

    public StringIterator(String str) {
        arr = str.split("\\d+");
        counts = Arrays.stream(str.substring(1).split("[a-zA-Z]+")).mapToInt(Integer::parseInt).toArray();
    }
    
    public char next() {
        if(!hasNext()) return ' ';
        char ch = arr[i].charAt(0);
        if(--counts[i] == 0) ++i;
        return ch;
    }

    public boolean hasNext() {
        if(i == arr.length) return false;
        return true;
    }
}
 * @author het
 *
 */
public class LeetCode604DesignCompressedStringIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
