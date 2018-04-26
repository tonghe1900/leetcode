package alite.leetcode.newExtra;
/**
 * HackerRank: Rectangular Game

HackerRank: Rectangular Game
You are given an infinite 2-d grid with the bottom left cell referenced as (1,1). All the cells contain a value of zero initially.
 Let's play a game?
The game consists of N steps wherein each step you are given two integers a and b. The value of each of the cells in the
 co-ordinate (u, v) satisfying 1 ≤ u ≤ a and 1 ≤ v ≤ b, is increased by 1. After N such steps, if X is the largest number
  amongst all the cells in the rectangular board, can you print the number of X's in the board?
Let's count what will be the number in the cell (x, y) after all operations. Obviously, it would be the number of rectangles
 which contain this cell. After that, one can observe that this number equals N iff 1 <= x <= min(ai), 1 <= y <= min(bi), 
 where min(ai) means the minimum of all a in the input and min(bi) - the minimum of all b in the input.
  So, total answer is min(ai)*min(bi).
  long long a = 1000000000, b = 1000000000;
  for (int i = 0; i < n; ++i) {
    long long x, y;
    cin >> x >> y;
    a = min(a, x);
    b = min(b, y);
  }
HackerRank: Rectangular Game
 * @author het
 *
 */
public class HackerRank_Rectangular_Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
