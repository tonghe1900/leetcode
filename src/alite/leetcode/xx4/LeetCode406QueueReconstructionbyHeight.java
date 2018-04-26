package alite.leetcode.xx4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * LeetCode 406 - Queue Reconstruction by Height

https://leetcode.com/problems/queue-reconstruction-by-height/
Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), 
where h is the height of the person and k is the number of people in front of this person who have a height greater
 than or equal to h. Write an algorithm to reconstruct the queue.
 
 //k is the number of people in front of this person who have a height greater
// than or equal to h
Note:
The number of people is less than 1,100.
Example

Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

https://discuss.leetcode.com/topic/60394/easy-concept-with-python-c-java-solution
Pick out tallest group of people and sort them in a subarray (S). Since there's no other groups of 
people taller than them, therefore each guy's index will be just as same as his k value.
For 2nd tallest group (and the rest), insert each one of them into (S) by k value. So on and so forth.
E.g.
input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
subarray after step 1: [[7,0], [7,1]]
subarray after step 2: [[7,0], [6,1], [7,1]]

    public int[][] reconstructQueue(int[][] people) {
        //pick up the tallest guy first
        //when insert the next tall guy, just need to insert him into kth position
        //repeat until all people are inserted into list
        Arrays.sort(people,new Comparator<int[]>(){
           @Override
           public int compare(int[] o1, int[] o2){
               return o1[0]!=o2[0]?-o1[0]+o2[0]:o1[1]-o2[1];
           }
        });
        List<int[]> res = new LinkedList<>();
        for(int[] cur : people){
            if(cur[1]>=res.size())
                res.add(cur);
            else
                res.add(cur[1],cur);       
        }
        return res.toArray(new int[people.length][]);
    }
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
        List<int[]> list = new LinkedList<>();
        for (int[] p : people) {
            list.add(p[1], p);
        }
        return list.toArray(new int[list.size()][]);
    }
https://discuss.leetcode.com/topic/60981/explanation-of-the-neat-sort-insert-solution
Below is my explanation of the following neat solution where we sort people from tall to short (and by increasing k-value) and then just insert them into the queue using their k-value as the queue index:
def reconstructQueue(self, people):
    people.sort(key=lambda (h, k): (-h, k))
    queue = []
    for p in people:
        queue.insert(p[1], p)
    return queue
I didn't come up with that myself, but here's my own explanation of it, as I haven't seen anybody explain it (and was asked to explain it):
People are only counting (in their k-value) taller or equal-height others standing in front of them. So a smallest person is completely irrelevant for all taller ones. And of all smallest people, the one standing most in the back is even completely irrelevant for everybodyelse. Nobody is counting that person. So we can first arrange everybody else, ignoring that one person. And then just insert that person appropriately. Now note that while this person is irrelevant for everybody else, everybody else is relevant for this person - this person counts exactly everybody in front of them. So their count-value tells you exactly the index they must be standing.
So you can first solve the sub-problem with all but that one person and then just insert that person appropriately. And you can solve that sub-problem the same way, first solving the sub-sub-problem with all but the last-smallest person of the subproblem. And so on. The base case is when you have the sub-...-sub-problem of zero people. You're then inserting the people in the reverse order, i.e., that overall last-smallest person in the very end and thus the first-tallest person in the very beginning. That's what the above solution does, Sorting the people from the first-tallest to the last-smallest, and inserting them one by one as appropriate.

Nice explanation. I actually came to this solution myself, and pretty quickly, only to be completely baffled by the next rain problem. My code was a bit different, though, because I totally forgot about the key parameter:
        res = []
        for p in sorted((-x[0], x[1]) for x in people):
            res.insert(p[1], [-p[0], p[1]])
        return res
My thinking process, though, was completely backwards. At first I remembered the Russian Doll Envelopes problem, which had me baffled for a while, and this awesome solution to it. So I immediately thought about sorting in various orders by various keys. It became instantly obvious that persons with the same height should be sorted by the second value: if they have the same height, then they have the same selection criteria for counting, and therefore the one with more selected persons in front of them should be behind.
Then I thought about height. Suppose I take only the tallest persons, all having the same maximum height. Their second values must be 0, 1, 2, 3... with no gaps at all, because they only count each other. Therefore, if there were no other persons at all, their second value must be their final index. What about the persons with second maximum height then? Suppose there are only tallest persons and just one more person who has slightly smaller height. What would be his position? Well, since he obviously only count tallest persons, his position would still be his second value. The next person of the same height counts only the previous person and all the tallest ones, but since they are all already in the queue, his second value would also be his index.
Then I realized that I could go on forever like that because each time I put a person in the queue and go to the next person, all persons counted by the next one are already there, so I instantly know the right index and I know that the person I put in the queue doesn't really care about where I put all subsequent persons because they are outside of his selection criteria.
https://discuss.leetcode.com/topic/60423/java-o-n-2-greedy-solution
We always choose the current shortest height (so we need to sort input first), and then try to put it into the right position. We simply scan from the left and count how many persons are really >= its own height. Then we put the person into the empty slot.
https://discuss.leetcode.com/topic/60437/java-solution-using-priorityqueue-and-linkedlist
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people,new Comparator<int[]>(){
           public int compare(int[] p1, int[] p2){
               return p1[0]!=p2[0]?Integer.compare(p2[0],p1[0]): Integer.compare(p1[1],p2[1]);
           }
        });
        List<int[]> list = new LinkedList(); // use arraylist or array
        for (int[] ppl: people) list.add(ppl[1], ppl);
        return list.toArray(new int[people.length][] );
    }
https://discuss.leetcode.com/topic/60417/java-solution-using-arrays-sort-and-insert-sorting-idea


X. Using PriorityQueue

首先选出k值为0且身高最低的人，记为hi, ki，将其加入结果集。
然后更新队列，若队列中人员的身高≤hi，则令其k值 - 1（需要记录原始的k值）。
循环直到队列为空。
    public int[][] reconstructQueue(int[][] people) {
        int size = people.length;
        LinkedList<int[]> list = new LinkedList<int[]>();
        for (int i = 0; i < size; i++) {
            list.add(new int[]{people[i][0], people[i][1], 0});
        }
        int ans[][] = new int[size][];
        for (int i = 0; i < size; i++) {
            Collections.sort(list, new Comparator<int[]>() {
                public int compare (int[] a, int[] b) {
                    if (a[1] == b[1])
                        return a[0] - b[0];
                    return a[1] - b[1];
                }
            });
            int[] head = list.removeFirst();
            ans[i] = new int[]{head[0], head[1] + head[2]};
            for (int[] p : list) {
                if (p[0] <= head[0]) {
                    p[1] -= 1;
                    p[2] += 1;
                }
            }
        }
        return ans;
    }
X. https://discuss.leetcode.com/topic/60550/o-n-sqrt-n-solution/13
https://discuss.leetcode.com/topic/60437/java-solution-using-priorityqueue-and-linkedlist
Instead of using ArrayList for insertion, using LinkedList is more efficient. Here is the discussion of when to use LinkedList over ArrayList on StackOverFlow.
    class PairComp implements Comparator<int[]> {
        public int compare(int[] p1, int[] p2){
            int comp_h = Integer.compare(p2[0], p1[0]);
            return comp_h == 0 ? Integer.compare(p1[1], p2[1]): comp_h;
        }
    }
    public int[][] reconstructQueue(int[][] people) {
        LinkedList<int[]> list = new LinkedList();
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(1, new PairComp() );
        for (int[] ppl: people){
            queue.offer( ppl );
        }
        while ( ! queue.isEmpty() ) {
            int[] pair = queue.poll();
            list.add(pair[1], pair);
        }
        int[][] ret = new int[people.length][];
        for (int i=0; i<list.size(); i++){
            ret[i] = list.get(i);
        }
        return ret;
    }
Shorter version without using PriorityQueue:
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people,new Comparator<int[]>(){
           public int compare(int[] p1, int[] p2){
               return p1[0]!=p2[0]?Integer.compare(p2[0],p1[0]): Integer.compare(p1[1],p2[1]);
           }
        });
        List<int[]> list = new LinkedList();
        for (int[] ppl: people) list.add(ppl[1], ppl);
        return list.toArray(new int[people.length][] );
    }
 * @author het
 *
 */
public class LeetCode406QueueReconstructionbyHeight {
//	Pick out tallest group of people and sort them in a subarray (S). Since there's no other groups of people 
	//taller than them, therefore each guy's index will be just as same as his k value.
//	For 2nd tallest group (and the rest), insert each one of them into (S) by k value. So on and so forth.
//	E.g.
//	input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
//	subarray after step 1: [[7,0], [7,1]]
//	subarray after step 2: [[5,0] [7,0], [6,1], [7,1]]
//   [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
	//  [5,0] [7,0]  [5,2] [6,1]  [4,4] [7,1]

	    public static int[][] reconstructQueue(int[][] people) {
	        //pick up the tallest guy first
	        //when insert the next tall guy, just need to insert him into kth position
	        //repeat until all people are inserted into list
	        Arrays.sort(people,new Comparator<int[]>(){
	           @Override // o1.compareTo(o2)   from small to large
	           //  o2.compareTo(o1)   from large to small
	           public int compare(int[] o1, int[] o2){
	               return o1[0]!=o2[0]?-o1[0]+o2[0]:o1[1]-o2[1];// tall guy first,  and then 
	           }
	        });
	        List<int[]> res = new LinkedList<>();
	        for(int[] cur : people){
	            if(cur[1]>=res.size())
	                res.add(cur);
	            else
	                res.add(cur[1],cur);       
	        }
	        return res.toArray(new int[people.length][]);
	    }
//	    public int[][] reconstructQueue(int[][] people) {
//	        Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
//	        List<int[]> list = new LinkedList<>();
//	        for (int[] p : people) {
//	            list.add(p[1], p);
//	        }
//	        return list.toArray(new int[list.size()][]);
//	    }
	public static void main(String[] args) {
		int[][] people = {};
		// [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
		
		// [[7,0], [7,1], [6,1],  [5,0],, [5,2]   [4,4]
		
		//  [5,0]  [7,0]    [5,2]  [6,1]  [4,4] [7,1]
//		[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
//         [5,0], [7,0]   [5,2]  [6,1] [4,4] [7,1] 
//				Output:
//				[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
		// TODO Auto-generated method stub
		System.out.println(reconstructQueue(people ));

	}

}
