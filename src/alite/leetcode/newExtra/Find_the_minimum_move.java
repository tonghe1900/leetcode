package alite.leetcode.newExtra;
/**
 * Find the minimum move | Yaozong's Blog
Given a m*n grid starting from (1, 1). At any point (x, y), you has two choices for the next move:
 1) move to (x+y, y); 2) move to (x, y+x);  From point (1, 1), how to move to (m, n) in least moves? (or there's no such a path)

Solution: Breath-first search.

int least_move(int tx, int ty)

{

 if (tx == 1 && ty == 1)

  return 0;

 

 vector<pair<int, int>> front;

 front.push_back({1, 1});

 

 int step = 0;

 while (front.size() > 0)

 {

  vector<pair<int, int>> new_front;

  for (int i = 0; i < front.size(); ++i) {

   int nx = front[i].first, ny = front[i].first + front[i].second;

   if (nx == tx && ny == ty)

    return step + 1;

   else if (nx <= tx && ny <= ty)

    new_front.push_back({nx, ny});

 

   nx = ny; ny = front[i].second;

   if (nx == tx && ny == ty)

    return step + 1;

   else if (nx <= tx && ny <= ty)

    new_front.push_back({nx, ny});

  }

 

  ++step;

  swap(front, new_front);

 }

 

 return -1;

}
http://shirleyisnotageek.blogspot.com/2015/02/least-moves.html
If m = n and both m and n are greater than 1, then there is no solution. This is because at any time we will move to x+ y, y or x, y + x then there are only two possibilities for the last move:

x + y = m
y = n

or
x = m
y + x = n

if m = n, either of these will lead to x = 0 or y = 0, which is impossible since the start point is (1, 1), thus at anytime when m = n and m > 1, there is no solution.

Then the next thing is to start from m and n, goes back to 1, 1.

public static int leastMoves(int m, int n) {

  if (m == n && m == 1)

   return 0;

  if (m == n && m > 1)

   return -1;

  int count = 0;

  while (m > 1 || n > 1) {

   if (m == n)

    return -1;

   else if (m > n) {

    m -= n;

    count++;

   }

   else {

    n -= m;

    count++;

   }

  }

  if (m != 1 || n != 1)

   return -1;

  return count;

 }
https://www.glassdoor.com/Interview/Given-a-m-n-grid-starting-from-1-1-At-any-point-x-y-you-has-two-choices-for-the-next-move-1-move-to-x-y-y-QTN_256921.htm
Read full article from Find the minimum move | Yaozong's Blog
 * @author het
 *
 */
public class Find_the_minimum_move {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
