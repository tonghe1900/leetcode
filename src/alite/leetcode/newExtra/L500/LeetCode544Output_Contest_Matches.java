package alite.leetcode.newExtra.L500;
/**
 * LeetCode 544 - Output Contest Matches

http://bookshadow.com/weblog/2017/03/19/leetcode-output-contest-matches/
During the NBA playoffs, we always arrange the rather strong team to play with the rather weak team, 
like make the rank 1 team play with the rank nth team, which is a good strategy to make the contest more interesting. 
Now, you're given n teams, you need to output their final contest matches in the form of a string.
The n teams are given in the form of positive integers from 1 to n, which represents their initial rank.
 (Rank 1 is the strongest team and Rank n is the weakest team.) We'll use parentheses('(', ')') and commas(',') 
 to represent the contest team pairing - parentheses('(' , ')') for pairing and commas(',') for partition. 
 During the pairing process in each round, you always need to follow the strategy of making the rather strong 
 one pair with the rather weak one.
Example 1:
Input: 2
Output: (1,2)
Explanation: 
Initially, we have the team 1 and the team 2, placed like: 1,2.
Then we pair the team (1,2) together with '(', ')' and ',', which is the final answer.
Example 2:
Input: 4
Output: ((1,4),(2,3))
Explanation: 
In the first round, we pair the team 1 and 4, the team 2 and 3 together, as we need to make the strong team and weak team together.
And we got (1,4),(2,3).
In the second round, the winners of (1,4) and (2,3) need to play again to generate the final winner, so you need to add the paratheses outside them.
And we got the final answer ((1,4),(2,3)).
Example 3:
Input: 8
Output: (((1,8),(4,5)),((2,7),(3,6)))
Explanation: 
First round: (1,8),(2,7),(3,6),(4,5)
Second round: ((1,8),(4,5)),((2,7),(3,6))
Third round: (((1,8),(4,5)),((2,7),(3,6)))
Since the third round will generate the final winner, you need to output the answer (((1,8),(4,5)),((2,7),(3,6))).
Note:
The n is in range [2, 212].
We ensure that the input n can be converted into the form 2k, where k is a positive integer.
https://discuss.leetcode.com/topic/83454/java-10-lines
    public String findContestMatch(int n) {
        List<String> matches = new ArrayList<>();
        for(int i = 1; i <= n; i++) matches.add(String.valueOf(i));
        
        while(matches.size() != 1){
            List<String> newRound = new ArrayList<>();
            for(int i = 0; i < matches.size()/2; i++)   
                newRound.add("(" + matches.get(i) + "," + matches.get(matches.size() - i - 1) + ")");
            matches = newRound;
        }
        return matches.get(0);
    }
Same idea. Indeed, we can even do this in an in-place fashion.
public String findContestMatch(int n) {
    String[] res = new String[n];
    for (int i = 1; i <= n; i++) res[i - 1] = "" + i;
    for (; n > 1; n /= 2)
        for (int i = 0; i < n / 2; i++)
            res[i] = "(" + res[i] + "," + res[n - 1 - i] + ")";
    return res[0];
}
Furthermore, if one uses a LinkedList to implement the string, the code can run in O(n) time.
Update: The (long) implementation is shown as follows, which uses the ListNode that is pre-defined by LeetCode.

    public string FindContestMatch(int n) {
        string[] arr = new string[n];
        for (int i = 0; i < n; i++) arr[i] = (i + 1).ToString();
        
        int left = 0;
        int right = n - 1;
        while (left < right)
        {
            while (left < right)
            {
                arr[left] = "(" + arr[left] + "," + arr[right] + ")";
                left++;
                right--;
            }
            left = 0;
        }
        
        return arr[0];
    }
https://discuss.leetcode.com/topic/83457/c-java-clean-code
    public String findContestMatch(int n) {
        String[] m = new String[n];
        for (int i = 0; i < n; i++) {
            m[i] = String.valueOf(i + 1);
        }

        while (n > 1) {
            for (int i = 0; i < n / 2; i++) {
                m[i] = "(" + m[i] + "," + m[n - 1 - i] + ")";
            }
            n /= 2;
        }
        
        return m[0];
    }

https://discuss.leetcode.com/topic/83463/java-8-line-solution-two-iterative-linkedlist
    public String findContestMatch(int n) {
        LinkedList<String> res = new LinkedList<>();
        
        for (int i = 1; i <= n; i++) res.add(i + "");
        
        while (res.size() > 1) {
            LinkedList<String> tmp = new LinkedList<>();
            
            while (!res.isEmpty()) {
                tmp.add("(" + res.remove(0) + "," + res.remove(res.size() - 1) + ")");
            }
            
            res = tmp;
        }
        
        return res.get(0);
    }
 * @author het
 * 
 * 
 * 
 * HackerRank: Missing Numbers

HackerRank: Missing Numbers

Numeros, The Artist, had two lists A and B, such that, B was a permutation of A. Numeros was very proud of these lists. 
Unfortunately, while transporting them from one exhibition to another, some numbers from List A got left out. Can you find out the numbers missing from A?

Notes

If a number occurs multiple times in the lists, you must ensure that the frequency of that number in both the lists is the same. If that is not the case, then it is also a missing number.
You have to print all the missing numbers in ascending order.
Print each missing number once, even if it is missing multiple times.
The difference between maximum and minimum number in the list B is less than or equal to 100.

https://sisijava.wordpress.com/2014/07/23/hackerrank-missing-numbers/
Using hashmap.
Actually we just need one hashmap.

http://aruncyberspace.blogspot.com/2014/04/algorithms-search-missing-numbers.html
// No point to sort a,b first

https://codepair.hackerrank.com/paper/keonZBPs?b=eyJyb2xlIjoiY2FuZGlkYXRlIiwibmFtZSI6ImplZmZlcnl5dWFuIiwiZW1haWwiOiJ5dWFueXVuLmtlbm55QGdtYWlsLmNvbSJ9

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        final int XMAX = 10000;
  final int RANGE_SIZE = 101;

  Scanner sc = new Scanner(System.in);
  int list1Size = sc.nextInt();
  int xMin = XMAX + 1;
  int[] list = new int[RANGE_SIZE];
  int[] list1 = new int[list1Size];
  for (int i = 0; i < list1Size; i++) {
   list1[i] = sc.nextInt();
  }
  int list2Size = sc.nextInt();
  int[] list2 = new int[list2Size];
  for (int i = 0; i < list2Size; i++) {
   int num = sc.nextInt();
   if (num < xMin) {
    xMin = num;
   }
   list2[i] = num;
  }
  for (int i = 0; i < list2Size; i++) {

   list[list2[i] - xMin]++;
  }
  for (int i = 0; i < list1Size; i++) {
   list[list1[i] - xMin]--;
  }
  for (int i = 0; i < RANGE_SIZE; i++) {
   if (list[i] > 0) {
    System.out.print((i + xMin) + " ");
   }
  }
 }


You might also like

 *
 */
public class LeetCode544Output_Contest_Matches {

}



Castle On The Grid - HackerRank

https://www.hackerrank.com/challenges/castle-on-the-grid


You are given a grid with both sides equal to NN. Rows and columns are numbered from 00 to N−1N−1. There is a castle on the intersection of the aath row and the bbth column.
Your task is to calculate the minimum number of steps it would take to move the castle from its initial position to the goal position (c,dc,d).
It is guaranteed that it is possible to reach the goal position from the initial position.
Note: You can move the castle from cell (a,b)(a,b) to any (x,y)(x,y) in a single step if there is a straight line between (a,b)(a,b) and (x,y)(x,y) that does not contain any forbidden cell. Here, "X" denotes a forbidden cell.
3
.X.
.X.
...
0 0 0 2

3
Explanation
Here is a path that one could follow in order to reach the destination in 33 steps:
(0,0)−>(2,0)−>(2,2)−>(0,2)(0,0)−>(2,0)−>(2,2)−>(0,2).

https://www.hackerrank.com/rest/contests/master/challenges/castle-on-the-grid/hackers/phishman3579/download_solution
    private static final int INVALID    = Integer.MAX_VALUE;
    private static final int UNVISITED  = Integer.MAX_VALUE-1;

    private static final class Board {

        final int N;
        final int[][] board;

        private Board(int N, char[][] chars) {
            this.N = N;

            this.board = new int[N][N];
            for (int i=0; i<N; i++)
                for (int j=0; j<N; j++) {
                    if (chars[i][j]=='X')
                        board[i][j] = INVALID;
                    else
                        board[i][j] = UNVISITED;
                }
        }
    }

    private static final Board populateBoard(int N, int a, int b, int c, int d, char[][] chars) {
        final Board visited = new Board(N,chars);

        final Deque<Integer[]> queue = new ArrayDeque<Integer[]>();
        queue.add(new Integer[]{a,b});
        visited.board[a][b] = 0;

        while (!queue.isEmpty()) {
            final Integer[] next = queue.removeFirst();
            final int ta = next[0];
            final int tb = next[1];
            final int nextValue = visited.board[ta][tb]+1;

            //System.out.println(visited.toString());
            if (ta==c && tb==d) {
                break;
            }

            for (int i=ta-1; i>=0; i--) {
                int na = i;
                int nb = tb;
                if (visited.board[na][nb] == UNVISITED) {
                    visited.board[na][nb] = nextValue;
                    queue.addLast(new Integer[]{na,nb});
                } else {
                    break;
                }
            }
            for (int i=tb-1; i>=0; i--) {
                int na = ta;
                int nb = i;
                if (visited.board[na][nb] == UNVISITED) {
                    visited.board[na][nb] = nextValue;
                    queue.addLast(new Integer[]{na,nb});
                } else {
                    break;
                }
            }
            for (int i=ta+1; i<N; i++) {
                int na = i;
                int nb = tb;
                if (visited.board[na][nb] == UNVISITED) {
                    visited.board[na][nb] = nextValue;
                    queue.addLast(new Integer[]{na,nb});
                } else {
                    break;
                }
            }
            for (int i=tb+1; i<N; i++) {
                int na = ta;
                int nb = i;
                if (visited.board[na][nb] == UNVISITED) {
                    visited.board[na][nb] = nextValue;
                    queue.addLast(new Integer[]{na,nb});
                } else {
                    break;
                }
            }
        }

        return visited;
    }
https://www.hackerrank.com/rest/contests/master/challenges/castle-on-the-grid/hackers/acetseng/download_solution
    public static int[][] steps=null;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        steps=new int[N][N];
        char[][] board=new char[N][N];
        for(int i=0; i<N; i++) {
            String s=sc.next();
            board[i]=s.toCharArray();
            Arrays.fill(steps[i], -1);
        }
        int a=sc.nextInt(), b=sc.nextInt(), c=sc.nextInt(), d=sc.nextInt();
        System.out.println(getSteps(board, a, b, c, d));
    }
    public static int getSteps(char[][] board, int a, int b, int c, int d) {
        // a,b  --> c,d
        Queue<D> que=new LinkedList<D>();
        Queue<D> nextlevel;
        que.offer(new D(c, d));
        int s=0, n=board.length;
        while(!que.isEmpty()) {
            nextlevel=new LinkedList<D>();
            while(!que.isEmpty()) {
                D cur=que.poll();
                int x=cur.x, y=cur.y;
                if(a==x && b==y)
                    return s;
                steps[x][y]=s;
                for(int j=y-1; j>=0; j--) { //left
                    if(board[x][j]=='X')
                        break;
                    nextlevel.offer(new D(x, j));
                    board[x][j]='X';
                }
                for(int j=y+1; j<n; j++) { //right
                    if(board[x][j]=='X')
                        break;
                    nextlevel.offer(new D(x, j));
                    board[x][j]='X';
                }
                for(int i=x-1; i>=0; i--) { //up
                    if(board[i][y]=='X')
                        break;
                    nextlevel.offer(new D(i, y));
                    board[i][y]='X';
                }
                for(int i=x+1; i<n; i++) {
                    if(board[i][y]=='X')
                        break;
                    nextlevel.offer(new D(i, y));
                    board[i][y]='X';
                }
            }
            s+=1;
            que=nextlevel;
        }
        return s;
    }
class D{
    int x;
    int y;
    public D(int x, int y) {
        this.x=x; 
        this.y=y;
    }
}
https://www.hackerrank.com/rest/contests/master/challenges/castle-on-the-grid/hackers/rixiac/download_solution
Use recursive to implement BFS
    static void go(List<int[]> l , int[][] map ){
        int[] xy = l.get(0);
        l.remove(0);
        int x = xy[0];
        int y = xy[1];
        int temp = x ;
        int n = map[x][y] + 1 ;
        while(--temp >= 0 && (map[temp][y] == 0 || map[temp][y] >= n)){
            map[temp][y] = n;
            l.add(new int[]{temp,y});
        }
        temp = x ;
        while(++temp < map.length && (map[temp][y] == 0 || map[temp][y] >= n)){
            map[temp][y] = n;
            l.add(new int[]{temp,y});
        }
        temp = y ;
        while(--temp >= 0 && (map[x][temp] == 0 || map[x][temp] >= n)){
            map[x][temp] = n;
            l.add(new int[]{x,temp});
        }
        temp = y ;
        while(++temp < map.length && (map[x][temp] == 0 || map[x][temp] >= n)){
            map[x][temp] = n;
            l.add(new int[]{x,temp});
        }
        if(l.size() > 0){
            go(l , map);
        }
    }
http://www.martinkysel.com/hackerrank-castle-on-the-grid-solution/
This solution works with the task as a 2D array. There are options available where you treat the task as a graph problem. In both cases each node it visited exactly once using BFS. On each node, I generate all nodes that are connected to this node in a straight line that is not broken by a ‘X’. I keep the distance data (integer) in the array itself. I store the nodes that have to be visited in a FIFO queue. Once the top element in the queue is the end, I terminate the algorithm. The data stored in the array are these:
X        – blocked
.          – not visited yet
(int)   – already visited. Value is the number of steps from the beginning.

from collections import deque

 

class Point:

    def __init__(self, x, y):

        self.x = x

        self.y = y

     

    def __str__(self):

        return "X=%d,Y=%d" % (self.x, self.y)

 

def getPointsFromPoint(N, arr, point):

    x = point.x

    y = point.y

    points = []

     

    while x > 0:

        x -= 1

        if arr[x][y] == 'X':

            break

        points.append(Point(x,y))

     

    x = point.x

    while x < N-1: 

        x += 1

        if arr[x][y] == 'X': 

            break

        points.append(Point(x,y)) 

     

    x = point.x 

    while y > 0:

        y -= 1

        if arr[x][y] == 'X':

            break

        points.append(Point(x,y))

     

    y = point.y

    while y < N-1:

        y += 1

        if arr[x][y] == 'X':

            break

        points.append(Point(x,y))

         

    return points

     

def solveCastleGrid(N, arr, start, end):

    q = deque([start])

    arr[start.x][start.y] = 0

     

    while q:

        current_point = q.pop()

        current_distance = arr[current_point.x][current_point.y]

         

        points = getPointsFromPoint(N, arr, current_point)

        for p in points:

            if arr[p.x][p.y] == '.':

                arr[p.x][p.y] = current_distance + 1

                q.appendleft(p)

                if p.x == end.x and p.y == end.y:

                    return current_distance + 1

    return -1



https://d396qusza40orc.cloudfront.net/algo1/slides/algo-graphs-bfs_typed.pdf
	
	
	
	
	
	 public String findContestMatch(int n) {
    List<String> matches = new ArrayList<>();
    for(int i = 1; i <= n; i++) matches.add(String.valueOf(i));
    
    while(matches.size() != 1){
        List<String> newRound = new ArrayList<>();
        for(int i = 0; i < matches.size()/2; i++)   
            newRound.add("(" + matches.get(i) + "," + matches.get(matches.size() - i - 1) + ")");
        matches = newRound;
    }
    return matches.get(0);
}
