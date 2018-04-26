package alite.leetcode.newExtra.L500.extra.finish;
/**
 * The problem here can be stated pretty simply. Imagine that you need a function that can take two inputs: 1) a list of integers and 2)
 *  another list of integers. The first list could be thousands of integers long. It can contain positive and negative numbers. It’s not sorted in any way.
 *   Any integer could be at any position. The second list is similar except that it’s equal in length to the first list 
 *   or shorter.

What we want to do with these lists is find where in the first list we could substitute the second list, integer for
 integer, that would create the least amount of change in each integer from the original list. For this problem, 
 we consider change to be measured in number line distance (i.e. absolute value). So, you can’t use some negative 
 distance to offset some positive distance. If you substitute -2 for 2, that’s a change of 4.

An example would be something like this:

original =    [1, 2, 3, 4, 5]
replacement = [3, 5, 3] 
In this example, the “disruption” created by each possible substitution looks like this:

0th position swapping
 0  1  2  3  4
---------------
[1, 2, 3, 4, 5]
[3, 5, 3] 
 2, 3, 0 -- total disruption of 5

1st position swapping
 0  1  2  3  4
---------------
[1, 2, 3, 4, 5]
   [3, 5, 3] 
    1, 2, 1 -- total disruption of 4

2nd position swapping
 0  1  2  3  4
---------------
[1, 2, 3, 4, 5]
      [3, 5, 3] 
       0, 1, 2 -- total disruption of 3
You can see from this, that the best replacement choice here would be the 2nd index, which would create a subrange disruption of just 3, compared to all the other options.

So how might you solve this? Well, here’s a bit of JavaScript that aims to tackle the problem. This algorithm runs in O(n*m) time, where n refers to the length of the first input and m is the length of the second input. Interestingly, the longer the second input is, the shorter the run of the algorithm will be. So, the worst case is something like the length of replacement being half the length of original. In that case, the algorithm will do something along the lines of m*m work. It’s a constant memory problem in that it uses no additional arrays or objects to store state. I guess you could achieve this with a hash table if you just felt like wasting RAM. 😀

 function findBestFittingSubrange(		  original, replacement) {		
  var ii;		  var jj;		  var distance;		  var distanceSum;		  var smallestDistanceSum = Infinity;		  var smallestDistanceSumIndex;		

  //   0  1  2  3  4  5  6		  // [ 0, 1, 2, 3, 4, 5, 6 ] 		  //             [ 4, 5, 6 ]		  //   7 - 3 = 4		  // Need one more than that to make sure we 		  // compare the last set of digits		
  for (ii = 0; ii <= original.length - replacement.length; ii++) {		    distanceSum = 0;		    console.log('Logging for pass #' + ii);		    for (jj=0; jj<replacement.length; jj++) {		      distance = 				Math.abs(original[ii + jj] - replacement[jj]);		      console.log(		        '  ' + original[ii + jj] + ' is ' + distance + 				' off from ' + replacement[jj]		      );		      distanceSum += distance;		    }		
    console.log(			  '  The total distance on this pass is ' + distanceSum			);					    if (distanceSum < smallestDistanceSum) {		      console.log('  >>> Found a new low with that.');		      smallestDistanceSum = distanceSum;		      smallestDistanceSumIndex = ii;		    }		  }		  return smallestDistanceSumIndex;		}		
console.log('---Test Run---');		console.log(		  findBestFittingSubrange(		    [2,5,9,1,-3,40,2,19],		    [10,-3,39]		  )		);		
console.log('---Test Run---');		console.log(		  findBestFittingSubrange(		    [2,5,9,1,-3,10,-3,39],		    [10,-3,39]		  )		);
view rawBest-Fitting-Subrange.js hosted with ❤ by GitHub
While this algorithm (as far as I know) is correct, it does leave many details unserved. For instance, I don’t address the potential to integer underflow by subtracting from the minimum integer value in JavaScript. Likewise, I could integer overflow by adding to distance until it bubbles over the maximum integer value in JavaScript. My test cases are also fairly limited and don’t test for cases like empty list inputs. Also, because JavaScript, I should be checking for null inputs and handling that case reasonably. I’m also not checking for the case where replacement could be longer than original. I’m sure there are like a dozen other defects here.

The point is not to create a bullet-proof library function. Rather, I was aiming to simulate a real coding interview focusing on what you’d have time to accomplish. Let me know what you think. Especially let me know if I got it all wrong!
Categories: Algorithms, Coding Interview Problems, JavaScript, Unqualified Engineer Tags: coding, coding interview, interview prep, javascript, programming
« Thinking About Knee Jerk Reactions Dinner Party Coding Interview Problem »
2 Comments


Ben
26TH APRIL 2016 AT 4:17 AM
The algorithm you wrote isn’t O(n), it’s O(n^2). The worst-case run time (when the second array is exactly half the length of the first) is (n^2)/2. The average run time is approximately (n^2)/3.

LOG IN TO REPLY

jackson (Post author)
26TH APRIL 2016 AT 11:36 AM
So, you’re totally right that I got it wrong at first. I had O(n*m) originally but then someone convinced me I was wrong so I changed it. I’ve now changed it back to O(n*m), which is correct. So, I’m afraid that you’re not right about the O(n^2) here. As far as I can tell, the algorithm for this question is identical in complexity and structure to the naive string search algorithm — https://en.wikipedia.org/wiki/String_searching_algorithm#Na.C3.AFve_string_search.

LOG IN TO REPLY
 * @author het
 *
 */

/**
 * original =    [1, 2, 3, 4, 5]
replacement = [3, 5, 3]    
 * @author het
 *O(m +n)
 */
public class Least_Disruptive_Subrange {
     public int leastDisruptive(int[] original , int[] replacement){
    	 if(original == null || original.length ==0 || replacement== null || replacement.length ==0) return -1;
    	 int originalLength = original.length;
    	 int replacementLength = replacement.length;
    	 if(originalLength < replacementLength) return -1;
    	 if(originalLength ==  replacementLength) return 0;
    	 int leastDisruptiveIndex = 0;
    	 int leastDisruptiveDiff = 0;
    	 
    	 for(int i=0;i<original.length;i+=1){
    		 
    	 }
     }
}
