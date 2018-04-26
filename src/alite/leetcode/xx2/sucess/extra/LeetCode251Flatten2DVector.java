package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implement an iterator to flatten a 2d vector.
For example,
Given 2d vector =
[
  [1,2],
  [3],
  [4,5,6]
]
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].
Hint:
How many variables do you need to keep track?
Two variables is all you need. Try with x and y.
Beware of empty rows. It could be the first few rows.
To write correct code, think about the invariant to maintain. What is it?
The invariant is x and y must always point to a valid point in the 2d vector. Should you maintain your invariant ahead of time or right when you need it?
Not sure? Think about how you would implement hasNext(). Which is more complex?
Common logic in two different places should be refactored into a common method.
 * @author het
 *
 */
 class Vector2D {
    List<Iterator<Integer>> its;
    int curr = 0;
    public Vector2D(List<List<Integer>> vec2d) {
        this.its = new ArrayList<Iterator<Integer>>();
        for(List<Integer> l : vec2d){
            // 只将非空的迭代器加入数组
            if(l != null && l.size() > 0){
               this.its.add(l.iterator()); 
            }
        }
    }
    public int next() {
        Integer res = its.get(curr).next();
        // 如果该迭代器用完了，换到下一个
        if(!its.get(curr).hasNext()){
            curr++;
        }
        return res;
    }
    public boolean hasNext() {
        return curr < its.size() && its.get(curr).hasNext();
    }
}

public class LeetCode251Flatten2DVector {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
