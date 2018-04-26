package alite.leetcode.newExtra;

import java.util.HashMap;
import java.util.Map;

///**
// * Given a task sequence tasks such as ABBABBC, and an integer k, which is the cool down time between two same tasks. 
//Assume the execution for each individual task is 1 unit.
//
//For example, if k = 3, then tasks BB takes a total of 5 unit time to finish, because B takes 1 unit of time to execute, 
//then wait for 3 unit, and execute the second B again for another unit of time. So 1 + 3 + 1 = 5.
//
//Given a task sequence and the cool down time, return the total execution time.
//
//Follow up: Given a task sequence and the cool down time, rearrange the task sequence such that the execution time is minimal.
//
//8 months ago reply quote 
//
//
//
//
//Algorithm for optimizing the order of actions with cooldowns
//
//up vote
//5
//down vote
//favorite
//1
//I can choose from a list of "actions" to perform one once a second. Each action on the list has a numerical value representing how much it's worth, and also a value representing its "cooldown" -- the number of seconds I have to wait before using that action again. The list might look something like this:
//
//Action A has a value of 1 and a cooldown of 2 seconds
//Action B has a value of 1.5 and a cooldown of 3 seconds
//Action C has a value of 2 and a cooldown of 5 seconds
//Action D has a value of 3 and a cooldown of 10 seconds
//So in this situation, the order ABA would have a total value of (1+1.5+1) = 3.5, and it would be acceptable because the first
//use of A happens at 1 second and the final use of A happens at 3 seconds, and then difference between those two is greater
//than or equal to the cooldown of A, 2 seconds. The order AAB would not work because you'd be doing A only a second apart, 
//less than the cooldown.
//
//My problem is trying to optimize the order in which the actions are used, maximizing the total value over a certain number
//of actions. Obviously the optimal order if you're only using one action would be to do Action D, resulting in a total value of 3. The maximum value from two actions would come from doing CD or DC, resulting in a total value of 5. It gets more complicated when you do 10 or 20 or 100 total actions. I can't find a way to optimize the order of actions without brute forcing it, which gives it complexity exponential on the total number of actions you want to optimize the order for. That becomes impossible past about 15 total.
//
//So, is there any way to find the optimal time with less complexity? Has this problem ever been researched? I imagine 
//there could be some kind of weighted-graph type algorithm that works on this, but I have no idea how it would work, 
//let alone how to implement it.
//
//Sorry if this is confusing -- it's kind of weird conceptually and I couldn't find a better way to frame it.
//
//algorithm sorting optimization
//shareimprove this question
//asked Mar 21 '13 at 20:59
//
//user2196830
//262
//1	 	
//How many different types of actions there can be at most? – aram90 Mar 21 '13 at 21:01
//  	 	
//Practically there wouldn't be more than 12. – user2196830 Mar 21 '13 at 21:13
//add a comment
//3 Answers
//active oldest votes
//up vote
//3
//down vote
//EDIT: Here is a proper solution using a highly modified Dijkstra's Algorithm:
//
//Dijkstra's algorithm is used to find the shortest path, given a map (of a Graph Abstract), which is a series of Nodes(usually locations, but for this example let's say they are Actions), which are inter-connected by arcs(in this case, instead of distance, each arc will have a 'value')
//
//Here is the structure in essence.
//
//Graph{//in most implementations these are not Arrays, but Maps. Honestly, for your needs you don't a graph, just nodes and arcs... this is just used to keep track of them.
//node[] nodes;
//arc[] arcs;
//}
//Node{//this represents an action
//arc[] options;//for this implementation, this will always be a list of all possible Actions to use.
//float value;//Action value
//}
//Arc{
//node start;//the last action used
//node end;//the action after that
//dist=1;//1 second
//}
//We can use this datatype to make a map of all of the viable options to take to get the optimal solution, based on looking at the end-total of each path. Therefore, the more seconds ahead you look for a pattern, the more likely you are to find a very-optimal path.
//
//Every segment of a road on the map has a distance, which represents it's value, and every stop on the road is a one-second mark, since that is the time to make the decision of where to go (what action to execute) next. For simplicity's sake, let's say that A and B are the only viable options. na means no action, because no actions are avaliable. If you are travelling for 4 seconds(the higher the amount, the better the results) your choices are...
//
//A->na->A->na->A
//B->na->na->B->na
//A->B->A->na->B
//B->A->na->B->A
//...
//there are more too, but I already know that the optimal path is B->A->na->B->A, because it's value is the highest. So, the established best-pattern for handling this combination of actions is (at least after analyzing it for 4 seconds) B->A->na->B->A This will actually be quite an easy recursive algorithm.
//
//    /*
//     cur is the current action that you are at, it is a Node. In this example, every other action is seen as a viable option, so it's as if every 'place' on the map has a path going to every other path.
//     numLeft is the amount of seconds left to run the simulation. The higher the initial value, the more desirable the results.
//
//This won't work as written, but will give you a good idea of how the algorithm works.
//*/
//function getOptimal(cur,numLeft,path){
//  if(numLeft==0){
//    var emptyNode;//let's say, an empty node wiht a value of 0.
//    return emptyNode;
//  }
//  var best=path;
//  path.add(cur);
//  for(var i=0;i<cur.options.length;i++){
//    var opt=cur.options[i];//this is a COPY
//    if(opt.timeCooled<opt.cooldown){
//      continue;
//    }
//    for(var i2=0;i2<opt.length;i2++){
//      opt[i2].timeCooled+=1;//everything below this in the loop is as if it is one second ahead
//    }
//    var potential=getOptimal(opt[i],numLeft-1,best);
//    if(getTotal(potential)>getTotal(cur)){best.add(potential);}//if it makes it better, use it! getTotal will sum up the values of an array of nodes(actions)
//  }
//  return best;
//}
//function getOptimalExample(){
//  log(getOptimal(someNode,4,someEmptyArrayOfNodes));//someNode will be A or B
//}
//End edit.
//
//I'm a bit confused on the question but...
//
//If you have a limited amount of actions, and that's it, then always pick the action with the most value, unless the cooldown hasn't been met yet.
//
//Sounds like you want something like this (in pseudocode):
//
//function getOptimal(){
//var a=[A,B,C,D];//A,B,C, and D are actions
//a.sort()//(just pseudocode. Sort the array items by how much value they have.)
//var theBest=null;
//for(var i=0;i<a.length;++i){//find which action is the most valuable
//     if(a[i].timeSinceLastUsed<a[i].cooldown){
//        theBest=a[i];
//        for(...){//now just loop through, and add time to each OTHER Action for their timeSinceLastUsed...
//             //...
//         }//That way, some previously used, but more valuable actions will be freed up again.
//        break;
//    }//because a is worth the most, and you can use it now, so why not?
//}
//}
//shareimprove this answer
//edited Mar 22 '13 at 15:54
//answered Mar 21 '13 at 21:57
//
//Ben Yep
//878
//  	 	
//That's originally what I tried, but it seems like the optimal solution will involve using a lot of quick-cooldown actions in between the slower ones, which wouldn't be captured by just going high-to-low. – user2196830 Mar 21 '13 at 22:14
//  	 	
//I think that would be captured, actually; any time none of the slow actions are available, you'll resort to a quicker action. If the high-value actions are slow enough, you'll see lots of these quick actions; otherwise, you won't. – Kyle Strand Mar 22 '13 at 0:29
//  	 	
//When you have a limited number of different actions available, just doing the best through the worst, especially when the best have longer cooldown times, will result in the you ending up with no actions left to do by the end. Take my example in the main post, if you did DCBA in the first four seconds, you'd have none left to do at 5 seconds. The goal is to be able to do an action every single second, so instead something like ABACABADABACB, which fits the cooldown requirement, would be significantly better. Sorry if I'm doing a bad job explaining what I want. – user2196830 Mar 22 '13 at 2:06
//1	 	
//Oh, I finally understand. I'll explain a solution using Dijkstra's algorithm as if this problem were finding the longest possible route on a map. See my edits.. (once I finish) – Ben Yep Mar 22 '13 at 3:43
//  	 	
//I don't completely understand why that returns the optimal solution, but it seems to work. Brilliant. – user2196830 Mar 22 '13 at 15:52
//show 2 more comments
//
//up vote
//1
//down vote
//EDIT: After rereading your problem a bit more, I see that the weighted scheduling algorithm would need to be tweaked to fit your problem statement; in our case we only want to take those overlapping actions out of the set that match the class of the action we selected, and those that start at the same point in time. IE if we select a1, we want to remove a2 and b1 from the set but not b2.
//
//This looks very similar to the weighted scheduling problem which is discussed in depth in this pdf. In essence, the weights are your action's values and the intervals are (starttime,starttime+cooldown). The dynamic programming solution can be memoized which makes it run in O(nlogn) time. The only difficult part will be modifying your problem such that it looks like the weighted interval problem which allows us to then utilize the predetermined solution.
//
//Because your intervals don't have set start and end times (IE you can choose when to start a certain action), I'd suggest enumerating all possible start times for all given actions assuming some set time range, then using these static start/end times with the dynamic programming solution. Assuming you can only start an action on a full second, you could run action A for intervals (0-2,1-3,2-4,...), action B for (0-3,1-4,2-5,...), action C for intervals (0-5,1-6,2-7,...) etc. You can then use union the action's sets to get a problem space that looks like the original weighted interval problem:
//
//|---1---2---3---4---5---6---7---| time
//|{--a1--}-----------------------| v=1
//|---{--a2---}-------------------| v=1
//|-------{--a3---}---------------| v=1
//|{----b1----}-------------------| v=1.5
//|---{----b2-----}---------------| v=1.5
//|-------{----b3-----}-----------| v=1.5
//|{--------c1--------}-----------| v=2
//|---{--------c2---------}-------| v=2
//|-------{-------c3----------}---| v=2
//etc... 
//shareimprove this answer
//edited Mar 21 '13 at 22:55
//answered Mar 21 '13 at 21:05
//
//ryanbwork
//1,847611
//  	 	
//Unfortunately, by forcing enumeration of all possible action start times and end times the solution is no longer O(n lg n). – Gio Borje Mar 21 '13 at 22:03
//  	 	
//You're right, I forgot to add that. Though given a timespan and the number of actions, wouldn't this just be a one-time addition of cn? – ryanbwork Mar 21 '13 at 22:06
//  	 	
//Well, given a maximum burst time of m, it would technically be m/action.cooldown for each action individually. Assuming that all actions have equal cooldown of n, it would be something along the lines of n * m/action.cooldown. – Gio Borje Mar 21 '13 at 22:07
//  	 	
//Yes, I think we're on the same page then as burst time m and action.cooldown don't have to be recalculated for each action in the set, hence c = m/action.cooldown – ryanbwork Mar 21 '13 at 22:10
//  	 	
//If we plug that straight into the current running time, we achieve O((n * m/action.cooldown) log (n * m/action.cooldown)) which doesn't seem to have any obvious reductions into a more simple form. – Gio Borje Mar 21 '13 at 22:13
//show 6 more comments
//up vote
//0
//down vote
//Always choose the available action worth the most points.
//
// * @author het
// *
// */

/**
 * ///**
// * Given a task sequence tasks such as ABBABBC, and an integer k, which is the cool down time between two same tasks. 
//Assume the execution for each individual task is 1 unit.
//
//For example, if k = 3, then tasks BB takes a total of 5 unit time to finish, because B takes 1 unit of time to execute, 
//then wait for 3 unit, and execute the second B again for another unit of time. So 1 + 3 + 1 = 5.
//
//Given a task sequence and the cool down time, return the total execution time.
//
//Follow up: Given a task sequence and the cool down time, rearrange the task sequence such that the execution time is minimal.
//
//8 months ago reply quote 
//
 * @author het
 *
 */
public class Minimal_run_time_scheduler {
   public static int totalExecutionTime(String tasks, int k){
	    if(tasks == null || tasks.length() == 0) return 0;
	    int time = tasks.length();
	    for(int i=1;i<tasks.length();i+=1){
	    	if(tasks.charAt(i) == tasks.charAt(i-1)){
	    		time+=k;
	    	}
	    }
	    return time;
   }
   //  greedy or dynamic programming
   //   ABBABBC   A 2  B 4  C 1// BACBABB
   //   BABABCB
   
   //   A 3   B  3    C  3
   //   ABCABCABC
   //  the multiplication of the numbers of each task * (the number of the kinds of tasks)*(the number of the kinds of tasks)
   public static void main(String [] args){
	  // System.out.println(totalExecutionTime("ABBABBC", 3));
	   System.out.println(minTime("ABBABBC", 3));
	   System.out.println(minTime("AAABBBBBBBBBBB", 3));
	  // System.out.println(totalExecutionTime("BB", 3));
   }
   //(k>0)
   public static int minTime(String tasks, int k){
	   if(tasks == null || tasks.length() == 0) return 0;
	   int length = tasks.length();
	   if(length == 1 ) return 1;
	   Map <Character, Integer> map = doStatistics(tasks);
	   return minTime('\0', map, k, new HashMap<String, Integer>());
   }
   
   private static String createKey(Map<Character, Integer> map, char prevChar){
	   StringBuilder sb = new StringBuilder(prevChar+"|");
	   for(Character key: map.keySet()){
		   sb.append(key+","+map.get(key)+",");
	   }
	   return sb.toString();
   }

private static int minTime(char prevChar, Map<Character, Integer> map, int k , Map<String,Integer> cache) {
	if(map.isEmpty()){
		return 0;
	}
	String key = createKey(map, prevChar);
	if(cache.containsKey(key)) return cache.get(key);
	int minTime = Integer.MAX_VALUE;
	for(Character ch: map.keySet()){
		int extraTime = (prevChar == ch) ? k:0;
		//Map newMap = ;
		minTime = Math.min(minTime, 1+ extraTime+ minTime(ch, decrement(map, ch), k, cache));
		//increment(map, ch);
	}
	cache.put(key, minTime);
	return minTime;
}


//private static Map<Character, Integer> increment(Map<Character, Integer> map, Character ch) {
//	if(map.containsKey(ch)){
//		int number =  map.get(ch);
//		map.put(ch, number+1);
//	}else{
//		map.put(ch, 1);
//	}
//	
//}

private static Map<Character, Integer> decrement(Map<Character, Integer> map, Character ch) {
	Map<Character, Integer> result = new HashMap<>(map);
	int number = result.get(ch);
	if(number>0){
		number -=1;
	}
	if(number == 0) {
		result.remove(ch);
	}else{
		result.put(ch, number);
	}
	return result;
	
}
private static Map<Character, Integer> doStatistics(String tasks) {
	Map<Character, Integer> result = new HashMap<>();
	for(int i=0;i<tasks.length();i+=1){
		char c = tasks.charAt(i);
		if(result.containsKey(c)){
			int num = result.get(c);
			result.put(c, num+1);
		}else{
			result.put(c, 1);
		}
	}
	return result;
}
}
