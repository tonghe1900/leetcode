package Leetcode600x;
/**
 * 
 * @author tonghe
 *题目描述：
LeetCode 604. Design Compressed String Iterator

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
题目大意：
设计和实现压缩字符串的迭代器，支持函数：next和hasNext。

压缩字符串以字母+出现次数的格式给出。

解题思路：
将压缩字符串compressedString拆分成字母数组chars和出现次数数组times。

记原始字符串大小为size

变量idx记录chars的当前下标，cnt记录当前已经遍历过的字符个数

维护idx和cnt即可。

Java代码：
public class StringIterator {

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
}

/**
 * Your StringIterator object will be instantiated and called as such:
 * StringIterator obj = new StringIterator(compressedString);
 * char param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
 */
public class Leetcode604 {

}
