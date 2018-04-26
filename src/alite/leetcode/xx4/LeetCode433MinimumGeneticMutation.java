package alite.leetcode.xx4;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * https://leetcode.com/problems/minimum-genetic-mutation/
A gene string can be represented by an 8-character long string, with choices from "A","C","G","T". 
Suppose we need to investigate about a mutation (mutation from "start" to "end"), 
where ONE mutation is defined as ONE single character changed in the gene string. 
For example, "AACCGGTT" -> "AACCGGTA" is 1 mutation. 
Also, there is a given gene "bank", which records all the valid gene mutations.
 A gene must be in the bank to make it a valid gene string. 

Now, given 3 things - start, end, bank, your task is to determine what is the minimum number of mutations needed to mutate from "start" to "end". If there is no such a mutation, return -1. 

NOTE: 1. Starting point is assumed to be valid, so it might not be included in the bank. 2. If multiple mutations are needed, all mutations during in the sequence must be valid. For example, 
bank: "AACCGGTA" 
start: "AACCGGTT" 
end: "AACCGGTA" 
return: 1

bank: "AACCGGTA", "AACCGCTA", "AAACGGTA"
start: "AACCGGTT"
end: "AAACGGTA"
return: 2
bank: "AAAACCCC", "AAACCCCC", "AACCCCCC"
start: "AAAAACCC"
end: "AACCCCCC"
return: 3

https://discuss.leetcode.com/topic/63954/java-bfs-solution
Explain: Every time we find a mismatch letter then check if it is in the bank, if so, then we add it to the queue for bfs traverse. "#" is used to mark end of each level. time complexity should be o(length of bank)
    public int minMutation(String start, String end, String[] bank) {
        Set<String> set=new HashSet<String>();//\\
        char[] chars={'A','C','G','T'};
        for(String b:bank)set.add(b);
        
        if(!set.contains(end))return -1;
        Queue<Container> q=new LinkedList<Container>();
        q.add(new Container(start,0));
        
        while(!q.isEmpty()) {
            Container con=q.remove();
            char[] str=con.word.toCharArray();
            
            for(int i=0;i<str.length;i++) {
                char tmpChar=str[i];
                for(char c:chars) {
                    if(c==str[i]) continue;
                    str[i]=c;
                    String tmpStr=new String(str);
                    if(tmpStr.equals(end))
                        return con.steps+1;
                    if(set.contains(tmpStr))
                        q.add(new Container(tmpStr,con.steps+1));
                    
                }
                str[i]=tmpChar;
            }
        }
        return -1;
    }
class Container {
    String word;
    int steps;
    public Container(String word,int steps) {
        this.word=word;
        this.steps=steps;
    }
}
https://discuss.leetcode.com/topic/65780/java-solution-using-bfs
    public int minMutation(String start, String end, String[] bank) {
        if(start.equals(end)) return 0;
        
        Set<String> bankSet = new HashSet<>();
        for(String b: bank) bankSet.add(b);
        
        char[] charSet = new char[]{'A', 'C', 'G', 'T'};
        
        int level = 0;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0) {
                String curr = queue.poll();
                if(curr.equals(end)) return level;
                
                char[] currArray = curr.toCharArray();
                for(int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for(char c: charSet) {
                        currArray[i] = c;
                        String next = new String(currArray);
                        if(!visited.contains(next) && bankSet.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    currArray[i] = old;
                }
            }
            level++;
        }
        return -1;
    }

http://bookshadow.com/weblog/2016/10/20/leetcode-minimum-genetic-mutation/
进制转换+位运算+广度优先搜索（BFS）
用4进制将基因字符串转化为整数，通过BFS可以求出最少突变次数，每一次突变的转换可以用异或运算完成。
    def minMutation(self, start, end, bank):
        """
        :type start: str
        :type end: str
        :type bank: List[str]
        :rtype: int
        """
        def toNumber(gene):
            table = {v : i for i, v in enumerate('ATGC')}
            return sum([table[g] * 1 << (2 * i) for i, g in enumerate(gene)])

        bank = set(map(toNumber, bank))
        start = toNumber(start)
        end = toNumber(end)
        queue = [(start, 0)]
        viset = set([start])
        while queue:
            gene, step = queue.pop(0)
            if gene == end:
                return step
            for x in range(8):
                for y in range(4):
                    next = gene ^ (y << (x * 2))
                    if next in bank and next not in viset:
                        viset.add(next)
                        queue.append((next, step + 1))
        return -1
 * @author het
 *
 */
public class LeetCode433MinimumGeneticMutation {
//	A gene string can be represented by an 8-character long string, with choices from "A","C","G","T". 
//	Suppose we need to investigate about a mutation (mutation from "start" to "end"), 
//	where ONE mutation is defined as ONE single character changed in the gene string. 
//	For example, "AACCGGTT" -> "AACCGGTA" is 1 mutation. 
//	Also, there is a given gene "bank", which records all the valid gene mutations.
//	 A gene must be in the bank to make it a valid gene string. 
//
//	Now, given 3 things - start, end, bank, your task is to determine what is the minimum number of mutations needed to mutate from "start" to "end". If there is no such a mutation, return -1. 
//
//	NOTE: 1. Starting point is assumed to be valid, so it might not be included in the bank. 
//	、、2. If multiple mutations are needed, all mutations during in the sequence must be valid. For example, 
//	bank: "AACCGGTA" 
//	start: "AACCGGTT" 
//	end: "AACCGGTA" 
//	return: 1
//
//	bank: "AACCGGTA", "AACCGCTA", "AAACGGTA"
//	start: "AACCGGTT"
//	end: "AAACGGTA"
//	return: 2
//	bank: "AAAACCCC", "AAACCCCC", "AACCCCCC"
//	start: "AAAAACCC"
//	end: "AACCCCCC"
//	return: 3
//
//	https://discuss.leetcode.com/topic/63954/java-bfs-solution
//	Explain: Every time we find a mismatch letter then check if it is in the bank, if so, then we add it to the queue for bfs traverse. "#" is used to mark end of each level. time complexity should be o(length of bank)
	    public int minMutation(String start, String end, String[] bank) {
	        Set<String> set=new HashSet<String>();//\\
	        char[] chars={'A','C','G','T'};
	        for(String b:bank)set.add(b);
	       // if(!set.contains(end))
	        if(!set.contains(end))return -1;
	        Deque<Container> q=new LinkedList<Container>();
	        Set<String> visited=new HashSet<String>();
	        q.add(new Container(start,0));
	        visited.add(start);
	        while(!q.isEmpty()) {
	            Container con=q.remove();
	            char[] str=con.word.toCharArray();
	            
	            for(int i=0;i<str.length;i++) {
	                char tmpChar=str[i];
	                for(char c:chars) {
	                    if(c==str[i]) continue;
	                    str[i]=c;
	                    String tmpStr=new String(str);
	                    if(tmpStr.equals(end))
	                        return con.steps+1;
	                    if(!visited.contains(tmpStr)&& set.contains(tmpStr)) {
	                     	visited.add(tmpStr);
	                        q.add(new Container(tmpStr,con.steps+1));
	                    }
	                   
	                    
	                }
	                str[i]=tmpChar;
	            }
	        }
	        return -1;
	    }
	class Container {
	    String word;
	    int steps;
	    public Container(String word,int steps) {
	        this.word=word;
	        this.steps=steps;
	    }
	}
	//https://discuss.leetcode.com/topic/65780/java-solution-using-bfs
	    public int minMutation1(String start, String end, String[] bank) {
	        if(start.equals(end)) return 0;
	        
	        Set<String> bankSet = new HashSet<>();
	        for(String b: bank) bankSet.add(b);
	        
	        char[] charSet = new char[]{'A', 'C', 'G', 'T'};
	        
	        int level = 0;
	        Set<String> visited = new HashSet<>();
	        Deque<String> queue = new LinkedList<String>();
	        queue.offer(start);
	        visited.add(start);
//	        int size = queue.size();
//            while(size-- > 0) {
	        while(!queue.isEmpty()) {
	            int size = queue.size();
	            while(size-- > 0) {
	                String curr = queue.poll();
	                if(curr.equals(end)) return level;
	                
	                char[] currArray = curr.toCharArray();
	                for(int i = 0; i < currArray.length; i++) {
	                    char old = currArray[i];
	                    for(char c: charSet) {
	                        currArray[i] = c;
	                        String next = new String(currArray);
	                        if(!visited.contains(next) && bankSet.contains(next)) {
	                            visited.add(next);
	                            queue.offer(next);
	                        }
	                    }
	                    currArray[i] = old;
	                }
	            }
	            level++;
	        }
	        return -1;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
