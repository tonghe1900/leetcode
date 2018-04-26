package alite.leetcode.newExtra;
/**
 * Friend Circles

http://www.ideserve.co.in/learn/friend-circles-graph
There are n students in a class. Every student can have 0 or more friends. If A is a friend of B and B is a friend of 
C then A and C are also friends. So we define a friend circle as a group of students who are friends as given by 
above definition. Given an nXn-matrix friends which consists of characters Y or N. If friends[i][j]=Y, 
then ith and jth students are friends, friends[i][j]=N, then i and j are not friends. Find the total number
 of such friend circles in the class.
DFS:
9	    public static int getFriendCircles(char[][] friends) {
10	
11	        if (friends == null || friends.length < 1)
12	            return 0;
13	
14	        int noOfCircles = 0;
15	
16	        boolean visited[] = new boolean[friends.length];
17	
18	        for (int i = 0; i < visited.length; i++)
19	            visited[i] = false;
20	
21	        for (int i = 0; i < friends.length; i++) {
22	            if (!visited[i]) {
23	                noOfCircles++;
24	                visited[i] = true;
25	                findFriends(friends, visited, i);
26	            }
27	        }
28	
29	        return noOfCircles;
30	
31	    }
32	
33	    public static void findFriends(char[][] friends, boolean[] visited, int id) {
34	
35	        for (int i = 0; i < friends.length; i++) {
36	            if (!visited[i] && i != id && 'Y' == friends[id][i]) {
37	                visited[i] = true;
38	                findFriends(friends, visited, i);
39	            }
40	        }
41	
42	    }



You might also like

 * @author het
 *
 */
public class Friend_Circles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
