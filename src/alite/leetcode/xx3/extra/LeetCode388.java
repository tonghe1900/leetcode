package alite.leetcode.xx3.extra;
/**
 * LeetCode 388 - Longest Absolute File Path

http://bookshadow.com/weblog/2016/08/21/leetcode-longest-absolute-file-path/
Suppose we abstract our file system by a string in the following manner:
The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
dir
    subdir1
    subdir2
        file.ext
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.
The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext 
and an empty second-level sub-directory subsubdir1. subdir2contains a second-level sub-directory 
subsubdir2 containing a file file2.ext.
We are interested in finding the longest (number of characters) absolute path to a file within
 our file system. For example, in the second example above, the longest absolute path
  is"dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).
Given a string representing the file system in the above format, return the length of the longest absolute path 
to file in the abstracted file system. If there is no file in the system, return0.
Note:
The name of a file contains at least a . and an extension.
The name of a directory or sub-directory will not contain a ..
Time complexity required: O(n) where n is the size of the input string.


Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.

利用栈（Stack）数据结构。
首先将字符串以'\n'进行分割，得到目录/文件的列表，记为parts
然后统计各目录/文件中'\t'的个数，表示当前目录/文件的深度
遍历parts，若栈顶元素的深度不小于parts的深度，则弹出栈顶元素，重复此过程。
然后将新的深度压入栈中，顺便统计当前目录的总长度。
https://discuss.leetcode.com/topic/55247/9-lines-4ms-java-solution
public int lengthLongestPath(String input) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // "dummy" length
        int maxLen = 0;
        for(String s:input.split("\n")){
            int lev = s.lastIndexOf("\t")+1; // number of "\t"
            while(lev+1<stack.size()) stack.pop(); // find parent
            int len = stack.peek()+s.length()-lev+1; // remove "/t", add"/"
            stack.push(len);
            // check if it is file
            if(s.contains(".")) maxLen = Math.max(maxLen, len-1); 
        }
        return maxLen;
    }
An even shorter and faster solution using array instead of stack:
public int lengthLongestPath(String input) {
    String[] paths = input.split("\n");
    int[] stack = new int[paths.length+1];
    int maxLen = 0;
    for(String s:paths){
        int lev = s.lastIndexOf("\t")+1, curLen = stack[lev+1] = stack[lev]+s.length()-lev+1;
        if(s.contains(".")) maxLen = Math.max(maxLen, curLen-1);
    }
    return maxLen;
}
https://discuss.leetcode.com/topic/55561/two-different-solutions-in-java-using-stack-and-hashmap
    public int lengthLongestPath(String input) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        for (String s : input.split("\n")) {
            int level = s.lastIndexOf("\t") + 1;
            while (stack.size() != level) {
                stack.pop();
            }
            int len = s.length() - level; // s.substring(level).length();
            if (stack.isEmpty()) {
                stack.push(len);
            } else {
                stack.push(stack.peek() + len + 1);
            }
            if (s.contains(".")) {
                result = Math.max(result, stack.peek());
            }
        }
        return result;
    }
https://discuss.leetcode.com/topic/55088/java-o-n-solution-using-stack
The depth of the directory/file is calculated by counting how many "\t"s are there.
The time complexity is O(n) because each substring in the input string only goes into the stack once, and pops out from the stack once.
public class Solution {
    public int lengthLongestPath(String input) {
        String[] tokens = input.split("\n");
        int result = 0;
        int curLen = 0;
        Stack<Integer> stack = new Stack<>();

        for (String s : tokens) {
            int level = countLevel(s);

            // if current directory/file depth is lower that the top directory/file on the stack, pop from stack 
            while (stack.size() > level) {
                curLen -= stack.pop();
            }

            // +1 here because a "/" needs to be counted following each diretory
            int len = s.replaceAll("\t", "").length() + 1;
            curLen += len;

            // if s contains ".", we have found a file!
            if (s.contains(".")) {
                result = curLen - 1 > result ? curLen - 1 : result;
            }
            stack.add(len);
        }
        return result;
    }
    
    private int countLevel(String s) {
        String cur = s.replaceAll("\t", "");
        return s.length() - cur.length();
    }
}
http://blog.csdn.net/qq508618087/article/details/52296490
思路: 这是google的OA题目, 改了一些, 原来是求图片文件的路径和, 这里只求最大的文件数, 但是思路还是一样的.
可以借助一个栈来保存当前层的路径, 层数可以利用tab字符的个数来确定, 如果当前行的层数大于栈顶元素并且非文件, 就可以让其进入栈, 否则如果当前行是文件就可以比较大小, 或者如果当前行是目录但是深度小于等于栈顶元素, 就可以将栈顶元素出栈, 直到为空或者小于当前行的深度.
https://all4win78.wordpress.com/2016/08/22/leetcode-388-longest-absolute-file-path/
这是Google的OA题目，改来改去也都是差不多的解法。这道题首先考察的是对于String的的一些method的用法的熟悉程度，比如split()，charAt()，indexOf()等等。
其次，这里有hierarchy的结构，需要我们去找出每一个文件所处的层次，这里自然可以想到使用stack，因为stack可以帮助我们记录控制我们current directory和所有parent directories录直至root directory，与此同时，我们可以知道当前的path length（由各级parent dir构成）。每次我们发现我们进入了某个sub dir，我们就需要往stack上push这么一个目录，并在path length上添加长度；当我们跳出某个目录dir1，并进入到某个新的目录dir2时，我们就要利用stack逐级pop之前的parent dir，直到我们找到dir2的父目录（当我们pop出dir2的同级目录的时候，在stack顶端的就是dir2的父目录），然后更新path length。想清楚整个过程和所需要的数据结构之后，这道题就比较容易了。

    public int lengthLongestPath(String s) {

        if (s == null || s.length() == 0) {

            return 0;

        }

        String[] files = s.split("\n");

        int maxLen = 0;

        int pathLen = 0;

        Stack<Integer> dirLen = new Stack<>();

        for (int i = 0; i < files.length; i++) {

            String curLine = files[i];

            int curLvl = findLvl(curLine);

            int lvDiff = dirLen.size() - curLvl;

            while (lvDiff > 0) {

                pathLen -= dirLen.pop();

                lvDiff -= 1;

            }

            int dotPos = curLine.indexOf('.');

            if (dotPos > -1) {

                maxLen = Math.max(maxLen, pathLen + curLine.length() - curLvl);

            } else {

                pathLen += curLine.length() - curLvl + 1;

                dirLen.push(curLine.length() - curLvl + 1);

            }

        }

        return maxLen;

    }


    private int findLvl(String s) {

        int count = 0;

        while (s.charAt(count) == '\t') {

            count += 1;

        }

        return count;

    }
http://ninefu.github.io/blog/388-Longest-Absolute-File-Path/
http://codechen.blogspot.com/2016/08/leetcode388-longest-absolute-file-path.html
    public int lengthLongestPath(String input) {
        String[] lines = input.split("\n");
        Stack<Integer> path = new Stack<>();
        path.push(0);
        int max = 0;
        
        for (String line : lines) {
            int tabIndex = line.lastIndexOf("\t") + 1;
            while (path.size() - 1 > tabIndex) {
                path.pop();
            }
            int length = path.peek() + 1 + line.length() - tabIndex;
            path.push(length);
            if (line.indexOf(".") != -1)
                max = Math.max(max, length - 1);
        }
        
        return max;
    }
https://discuss.leetcode.com/topic/55129/a-simple-4ms-java-solution-with-explanation
use stack to maintain each layer's length by far
add up chars between '\n\t' to get file or direction's length (use '.' to distinguish them)
you either output a candidate pathLen if you got a file, or refresh your stack with the direction
to prepare for the next round, you have to count what's next file/direction's depth
    public int lengthLongestPath(String input) {
        
        int max = 0, len = input.length(), idx = 0, pathLen = 0, curDepth = 0;
        Stack<Integer> stk = new Stack<>();  //store diretion length in order, Notice stk.size is associate with curDepth
        while(idx < len){
            //first modify pathLen by popping out diretion_len that has greater depth
            while(stk.size() > curDepth) pathLen -= stk.pop();
            
            int curLen = 0; curDepth = 0;
            boolean isFile = false;
            //find the next dir or file length, check if it is a File
            for(;idx < len && input.charAt(idx)!='\n';idx++, curLen++) 
             if(input.charAt(idx)=='.') isFile = true;
            
            if(isFile) max = Math.max(max, curLen+pathLen); // isFile, then output cur total pathLen
            else pathLen += stk.push(curLen+1);  // else, add it to stack & refresh pathLen
            
            idx++; // idx now points to the char next to '\n'
            for(;idx < len && input.charAt(idx)=='\t'; idx++) curDepth++; // find curDepth for the next round 
        }
        return max;
        
    }

https://discuss.leetcode.com/topic/55088/java-o-n-solution-using-stack
The depth of the directory/file is calculated by counting how many "\t"s are there.
The time complexity is O(n) because each substring in the input string only goes into the stack once, and pops out from the stack once.
    public int lengthLongestPath(String input) {
        String[] tokens = input.split("\n");
        int result = 0;
        int curLen = 0;
        Stack<Integer> stack = new Stack<>();

        for (String s : tokens) {
            int level = countLevel(s);

            // if current directory/file depth is lower that the top directory/file on the stack, pop from stack 
            while (stack.size() > level) {
                curLen -= stack.pop();
            }

            // +1 here because a "/" needs to be counted following each diretory
            int len = s.replaceAll("\t", "").length() + 1;
            curLen += len;

            // if s contains ".", we have found a file!
            if (s.contains(".")) {
                result = curLen - 1 > result ? curLen - 1 : result;
            }
            stack.add(len);
        }
        return result;
    }
    
    private int countLevel(String s) {
        String cur = s.replaceAll("\t", "");
        return s.length() - cur.length();
    }
X. Use HashMap
https://discuss.leetcode.com/topic/55245/concise-java-o-n-solution-use-map
Map to store the level and the length sum from root to current level.
level is calculated by how many 't' there.
length is preLevelLengthSum + current path length and ( + 1"/" if level bigger than 0)
public int lengthLongestPath(String input) {
    String[] strs = input.split("\n");
    int max = 0;
    Map<Integer,Integer> map = new HashMap<>();
    map.put(-1, 0);
    for(int i = 0; i < strs.length; i++){
        int level =  strs[i].lastIndexOf('\t')  + 1;
        int length = map.get(level - 1) + strs[i].length() - level + (level > 0 ? 1 : 0);
        if(strs[i].indexOf('.') == -1){
            map.put(level, length);
        }else{
            max = Math.max(length, max);
        }
    }
    return max;
}
https://discuss.leetcode.com/topic/55561/two-different-solutions-in-java-using-stack-and-hashmap
    public int lengthLongestPath2(String input) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, 0);
        int result = 0;
        for (String s : input.split("\n")) {
            int level = s.lastIndexOf("\t") + 1;
            int len = s.substring(level).length();
            if (s.contains(".")) {
                result = Math.max(result, hashMap.get(level) + len);
            } else {
                hashMap.put(level + 1, hashMap.get(level) + len + 1);
            }
        }
        return result;
    }
https://discuss.leetcode.com/topic/56437/java-simple-solution
public int lengthLongestPath(String input) {
    int longest = 0;
    String[] lines = input.split("\n");
    int[] lens = new int[lines.length+1];
    for(String line: lines) {
        String[] subs = line.split("\t");
        String cur = subs[subs.length-1];
        int len = lens[subs.length-1] + cur.length() + 1;
        if(cur.contains(".")) longest = Math.max(longest, len-1);
        else lens[subs.length] = len;
    }
    return longest;
}
X. http://ninefu.github.io/blog/388-Longest-Absolute-File-Path/
    public int lengthLongestPath(String input) {
        String[] lines = input.split("\n");
        int[] length = new int[lines.length + 1];
        int max = 0;
        
        for (String line : lines) {
            int depth = line.lastIndexOf("\t") + 1;
            length[depth + 1] = length[depth] + 1 + line.length() - depth;
            if (line.indexOf(".") != -1) {
                max = Math.max(max, length[depth + 1] - 1);
            }
        }
        return max;
    }

http://glassjar-blog.appspot.com/Article?aid=ag9zfmdsYXNzamFyLWJsb2dyFAsSB0FydGljbGUYgICAgLyfmwoM
 * @author het
 *
 */
public class LeetCode388 {
	//An even shorter and faster solution using array instead of stack:
		public static int lengthLongestPath(String input) {
		    String[] paths = input.split("\n");
		    int[] stack = new int[paths.length+1];
		    int maxLen = 0;
		    for(String s:paths){
		        int lev = s.lastIndexOf("\t")+1, curLen = stack[lev+1] = stack[lev]+s.length()-lev+1;
		        if(s.contains(".")) maxLen = Math.max(maxLen, curLen-1);
		    }
		    return maxLen;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));

	}

}
