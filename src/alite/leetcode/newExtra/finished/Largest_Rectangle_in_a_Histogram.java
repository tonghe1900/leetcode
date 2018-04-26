package alite.leetcode.newExtra.finished;

import java.util.Deque;
import java.util.LinkedList;

import com.interview.stackqueue.MaximumHistogram;

/**
 * Here’s the full text of the code from Episode 05. This is coded in JavaScript and uses the common approach of using a stack to keep track of the open rectangles.

 function findLargestRectangle(hist) {		  var h, pos;		  var tempH, tempPos;		  var hStack = [];		  var posStack = [];		  var maxSize = -Infinity;		  var tempSize = 0;		
  function popThatShit() {		    tempH = hStack.pop();		    tempPos = posStack.pop();		    tempSize = tempH * (pos - tempPos);		    maxSize = Math.max(tempSize, maxSize);		  }		
  for (pos = 0; pos < hist.length; pos++) {		    h = hist[pos];		    if (hStack.length === 0 || h > hStack[hStack.length - 1]) {		      hStack.push(h);		      posStack.push(pos);		    } else if (h < hStack[hStack.length - 1]) {		      while (hStack.length && h < hStack[hStack.length - 1]) {		        popThatShit();		      }		      hStack.push(h);		      posStack.push(tempPos);		    }		  }		  while (hStack.length) {		    popThatShit();		  }		  return maxSize;		}		
function test(hist, answer) {		  var size = findLargestRectangle(hist);		  console.log(		    'Testing ' + hist.join(',') + 		    ' - Answer should be: ' + answer + 		    ', Discovered Answer: ' + size		  );		  if (size !== answer) {		    console.log('!!! shit is fucked !!!');		  }		  console.log("\n");		}		
test([ 1, 2, 3, 4, 2, 3, 5, 2, 1, 0, 8 ], 14);		test([ 5, 5, 1, 0, 1, 0, 1], 10);		test([ 6, 5, 4, 0, 1, 0, 1], 12);		test([ 0, 1, 0, 1, 0, 5, 6], 10);		test([ 5, 1, 1, 1, 1, 1, 0], 6);		test([ 5, 0, 3, 3, 3], 9);		test([ 0, 1, 0], 1);		test([ 1, 2, 3, 4, 5], 9);		test([ 5, 4, 3, 2, 1], 9);		test([ 0, 1, 0, 1, 3, 2, 0, 1, 0, 1], 4);		test([], -Infinity);		test([ 1 ], 1);
 * @author het
 *
 */
public class Largest_Rectangle_in_a_Histogram {

}


/**
 * 12/23/2014
 * @author tusroy
 * 
 * Video link https://youtu.be/ZmnqCZp9bBs
 * 
 * Given an array representing height of bar in bar graph, find max histogram
 * area in the bar graph. Max histogram will be max rectangular area in the
 * graph.
 * 
 * Maintain a stack
 * 
 * If stack is empty or value at index of stack is less than or equal to value at current 
 * index, push this into stack.
 * Otherwise keep removing values from stack till value at index at top of stack is 
 * less than value at current index.
 * While removing value from stack calculate area
 * if stack is empty 
 * it means that till this point value just removed has to be smallest element
 * so area = input[top] * i
 * if stack is not empty then this value at index top is less than or equal to 
 * everything from stack top + 1 till i. So area will
 * area = input[top] * (i - stack.peek() - 1);
 * Finally maxArea is area if area is greater than maxArea.
 * 
 * 
 * Time complexity is O(n)
 * Space complexity is O(n)
 * 
 * References:
 * http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
 */
public class MaximumHistogram {

    public int maxHistogram(int input[]){
        Deque<Integer> stack = new LinkedList<Integer>();
        int maxArea = 0;
        int area = 0;
        int i;
        for(i=0; i < input.length;){
            if(stack.isEmpty() || input[stack.peekFirst()] <= input[i]){
                stack.offerFirst(i++);
            }else{
                int top = stack.pollFirst();
                //if stack is empty means everything till i has to be
                //greater or equal to input[top] so get area by
                //input[top] * i;
                if(stack.isEmpty()){
                    area = input[top] * i;
                }
                //if stack is not empty then everythin from i-1 to input.peek() + 1
                //has to be greater or equal to input[top]
                //so area = input[top]*(i - stack.peek() - 1);
                else{
                    area = input[top] * (i - stack.peekFirst() - 1);
                }
                if(area > maxArea){
                    maxArea = area;
                }
            }
        }
        while(!stack.isEmpty()){
            int top = stack.pollFirst();
            //if stack is empty means everything till i has to be
            //greater or equal to input[top] so get area by
            //input[top] * i;
            if(stack.isEmpty()){
                area = input[top] * i;
            }
            //if stack is not empty then everything from i-1 to input.peek() + 1
            //has to be greater or equal to input[top]
            //so area = input[top]*(i - stack.peek() - 1);
            else{
                area = input[top] * (i - stack.peekFirst() - 1);
            }
        if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
    
    public static void main(String args[]){
        MaximumHistogram mh = new MaximumHistogram();
        int input[] = {2,2,2,6,1,5,4,2,2,2,2};
        int maxArea = mh.maxHistogram(input);
        //System.out.println(maxArea);
        assert maxArea == 12;
    }
}
