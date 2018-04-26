package alite.leetcode.xx3.extra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

///**
// * LeetCode 353 - Design Snake Game
//
//http://www.cnblogs.com/grandyang/p/5558033.html
//Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.
//The snake is initially positioned at the top left corner (0,0) with length = 1 unit.
//You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.
//Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.
//When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.
//Example:
//Given width = 3, height = 2, and food = [[1,2],[0,1]].
//
//Snake snake = new Snake(width, height, food);
//
//Initially the snake appears at position (0,0) and the food at (1,2).
//
//|S| | |
//| | |F|
//
//snake.move("R"); -> Returns 0
//
//| |S| |
//| | |F|
//
//snake.move("D"); -> Returns 0
//
//| | | |
//| |S|F|
//
//snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )
//
//| |F| |
//| |S|S|
//
//snake.move("U"); -> Returns 1
//
//| |F|S|
//| | |S|
//
//snake.move("L"); -> Returns 2 (Snake eats the second food)
//
//| |S|S|
//| | |S|
//
//snake.move("U"); -> Returns -1 (Game over because snake collides with border)
//这道题让我们设计一个贪吃蛇的游戏，这是个简化版的，但是游戏规则还是保持不变，蛇可以往上下左右四个方向走，吃到食物就会变长1个，如果碰到墙壁或者自己的躯体，
//游戏就会结束。我们需要一个一维数组来保存蛇身的位置，由于蛇移动的过程的蛇头向前走一步，蛇尾也跟着往前，中间的躯体还在原来的位置，
//所以移动的结果就是，蛇头变到新位置，去掉蛇尾的位置即可。需要注意的是去掉蛇尾的位置是在检测和蛇身的碰撞之前还是之后，如果是之后则无法通过这个test case：[[3,3,[[2,0],[0,0]]],["D"],["D"],["U"]]，如果是之前就没有问题了.
//    SnakeGame(int width, int height, vector<pair<int, int>> food) {
//        this->width = width;
//        this->height = height;
//        this->food = food;
//        score = 0;
//        pos.push_back({0, 0});
//    }
//    
//    /** Moves the snake.
//        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
//        @return The game's score after the move. Return -1 if game over. 
//        Game over when snake crosses the screen boundary or bites its body. */
//    int move(string direction) {
//        auto head = pos.front(), tail = pos.back();
//        pos.pop_back();
//        if (direction == "U") --head.first;
//        else if (direction == "L") --head.second;
//        else if (direction == "R") ++head.second;
//        else if (direction == "D") ++head.first;
//        if (count(pos.begin(), pos.end(), head) || head.first < 0 || head.first >= height || head.second < 0 || head.second >= width) {
//            return -1;
//        }
//        pos.insert(pos.begin(), head);
//        if (!food.empty() && head == food.front()) {
//            food.erase(food.begin());
//            pos.push_back(tail);
//            ++score;
//        }
//        return score;
//    }
//
//private:
//    int width, height, score;
//    vector<pair<int, int>> food, pos;
//X.
//http://dartmooryao.blogspot.com/2016/06/leetcode-353-design-snake-game.html
//In the first try, I used a char matrix, and get memory space exceed error.
//So I changed it to a linkedlist.
//
//The idea is to use a linkedList to represents the body of snake, and use a hashset to represents the position of snake's body. So we can get constant time to check whether the next position is valid.
//
//Also note that the snake can move to it's own tail. So it means that we have to delete the tail position from the hashset before we check the valid.
//public class SnakeGame {
//   
//    Map<String, int[]> map;
//    int idx = 0;
//    int[][] food;
//    LinkedList<int[]> snake;
//    int width;
//    int height;
//    Set<String> posi = new HashSet<>();
//
//    /** Initialize your data structure here.
//        @param width - screen width
//        @param height - screen height
//        @param food - A list of food positions
//        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
//    public SnakeGame(int width, int height, int[][] food) {
//        this.food = food;
//        this.width = width;
//        this.height = height;
//        map = createMap();
//        snake = new LinkedList<>();
//        snake.add(new int[]{0,0});
//        posi.add(0+"_"+0);
//    }
//   
//    /** Moves the snake.
//        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
//        @return The game's score after the move. Return -1 if game over.
//        Game over when snake crosses the screen boundary or bites its body. */
//    public int move(String direction) {
//        int[] dir = map.get(direction);
//        int[] head = snake.get(0);
//        int[] tail = snake.get(snake.size()-1);
//        posi.remove(tail[0]+"_"+tail[1]);
//       
//        int nextR = head[0]+dir[0];
//        int nextC = head[1]+dir[1];
//        String posiKey = nextR+"_"+nextC;
//        if(nextR<0 || nextC<0 || nextR>=height || nextC>=width || posi.contains(posiKey)){ return -1; }
//       
//        if(idx<food.length && nextR==food[idx][0] && nextC==food[idx][1]){
//            idx++;
//        }else{
//            snake.removeLast(); // posi.remove(tail)?
//        }
//        snake.addFirst(new int[]{nextR, nextC});
//        posi.add(posiKey);
//        return snake.size()-1;
//    }
//   
//    private Map<String, int[]> createMap(){
//        Map<String, int[]> map = new HashMap<>();
//        map.put("U", new int[]{-1, 0});
//        map.put("L", new int[]{0, -1});
//        map.put("R", new int[]{0, 1});
//        map.put("D", new int[]{1, 0});
//        return map;
//    }
//}
//https://leetcode.com/discuss/106258/accepted-java-solution
//private int width, height;
//int[][] food;
//private int score, idx;
//private Deque<Integer> snake;
//private Set<Integer> body;
//public SnakeGame(int width, int height, int[][] food) {
//    this.width = width;
//    this.height = height;
//    this.food = food;
//    score = 0;
//    idx = 0;
//    snake = new LinkedList<>();
//    snake.addLast(0);
//    body = new HashSet<>();
//    body.add(0);
//}
//
//public int move(String direction) {
//    // first of all, get head
//    int h = snake.getFirst() / width, w = snake.getFirst() % width;
//    if (direction.equals("U")) h--;
//    else if (direction.equals("D")) h++;
//    else if (direction.equals("L")) w--;
//    else w++;
//    if (w < 0 || w == width || h < 0 || h == height) return -1;
//    int head = h*width + w;
//    snake.addFirst(head); // move head
//    if (idx < food.length && h == food[idx][0] && w == food[idx][1]) {
//        score++; // and still keep the tail
//        idx++;
//    } else
//        body.remove(snake.removeLast()); // move tail
//    if (!body.add(head)) return -1;
//    return score;
//}
//http://blog.csdn.net/jmspan/article/details/51688859
//https://leetcode.com/discuss/106217/java-solution-of-snakegame-276ms
//// use another hashmap to speed the contains
////Here we use int to keep the location of snake body, value = x*width + y
////And LinkedList has the method boolean contains, to check if collide with snake body
//Deque<Integer> snakeBody = new LinkedList();
//int width = 0;
//int height = 0;
//int[][] food;
//int count = 0;
//boolean gameOver = false;
//
//public SnakeGame(int width, int height, int[][] food) {
//    this.width = width;
//    this.height = height;
//    this.food = food;
//    snakeBody.addLast(0);
//}
//
///** Moves the snake.
//    @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
//    @return The game's score after the move. Return -1 if game over. 
//    Game over when snake crosses the screen boundary or bites its body. */
//public int move(String direction) {
//    if(gameOver) return -1;
//    int curValue = snakeBody.getFirst();
//    int lastValue = snakeBody.removeLast();
//    int[] head = new int[2];
//    head[0] = curValue/width;
//    head[1] = curValue%width;
//    switch(direction) {
//        case "U": head[0]--;break;
//        case "L": head[1]--;break;
//        case "R": head[1]++;break;
//        case "D": head[0]++;break;
//    }
//    if(head[0]<0||head[1]<0||head[0]>=height||head[1]>=width||snakeBody.contains(valueOf(head)))//slow
//    {
//        gameOver = true;
//        return -1;
//    }
//    snakeBody.addFirst(valueOf(head));
//    if(count<food.length&&food[count][0]==head[0]&&food[count][1]==head[1]) {
//        snakeBody.addLast(lastValue);
//        count++;
//    }
//    return snakeBody.size()-1;
//}
//
//public int valueOf(int[] head) {
//    return head[0]*width+head[1];
//}
// * @author het
// *
// */
public class LeetCode353 {
//	 X.
//	 http://dartmooryao.blogspot.com/2016/06/leetcode-353-design-snake-game.html
//	 In the first try, I used a char matrix, and get memory space exceed error.
//	 So I changed it to a linkedlist.
//
//	 The idea is to use a linkedList to represents the body of snake, and use a hashset to represents the position of snake's body. So we can get constant time to check whether the next position is valid.
//
//	 Also note that the snake can move to it's own tail. So it means that we have to delete the tail position from the hashset before we check the valid.
	 public class SnakeGame {
	    
	     Map<String, int[]> map;
	     int idx = 0;
	     int[][] food;
	     LinkedList<int[]> snake;
	     int width;
	     int height;
	     Set<String> posi = new HashSet<>();

	     /** Initialize your data structure here.
	         @param width - screen width
	         @param height - screen height
	         @param food - A list of food positions
	         E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
	     public SnakeGame(int width, int height, int[][] food) {
	         this.food = food;
	         this.width = width;
	         this.height = height;
	         map = createMap();
	         snake = new LinkedList<>();
	         snake.add(new int[]{0,0});
	         posi.add(0+"_"+0);
	     }
	    
	     /** Moves the snake.
	         @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
	         @return The game's score after the move. Return -1 if game over.
	         Game over when snake crosses the screen boundary or bites its body. */
	     public int move(String direction) {
	         int[] dir = map.get(direction);
	         int[] head = snake.get(0);
	         int[] tail = snake.get(snake.size()-1);
	         posi.remove(tail[0]+"_"+tail[1]);
	        
	         int nextR = head[0]+dir[0];
	         int nextC = head[1]+dir[1];
	         String posiKey = nextR+"_"+nextC;
	         if(nextR<0 || nextC<0 || nextR>=height || nextC>=width || posi.contains(posiKey)){ return -1; }
	        
	         if(idx<food.length && nextR==food[idx][0] && nextC==food[idx][1]){
	             idx++;
	         }else{
	             snake.removeLast(); // posi.remove(tail)?
	         }
	         snake.addFirst(new int[]{nextR, nextC});
	         posi.add(posiKey);
	         return snake.size()-1;
	     }
	    
	     private Map<String, int[]> createMap(){
	         Map<String, int[]> map = new HashMap<>();
	         map.put("U", new int[]{-1, 0});
	         map.put("L", new int[]{0, -1});
	         map.put("R", new int[]{0, 1});
	         map.put("D", new int[]{1, 0});
	         return map;
	     }
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
