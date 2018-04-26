package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//the target number can be calculated by applying "+-*/" operations to the number list?
/**
 * Get Target Number Using Number List
and Arithmetic Operations
Given a list of numbers and a target number, write a program to determine whether

You can assume () is automatically added when necessary.
For example, given 1,2,3,4 and 21, return true. Because (1+2)*(3+4)=21
 * @author het
 *
 */
public class GetTargetNumberUsingNumberList {
	public static boolean isReachable(List<Integer> list, int target){
		if(list == null || list.size() == 0) return false;
		return isReachable(list, 0, list.size() -1, new Set[list.size()][list.size()]).contains(target);
		
		
	}
	
	private static Set<Integer> isReachable(List<Integer> list, int start, int end, Set<Integer> [][] cache){
		if(cache[start][end] != null){
			return cache[start][end];
		}
		if(start == end){
			Set<Integer> set = new HashSet<>();
			set.add(list.get(start));
			return set;
		}
		 Set<Integer> set = new HashSet<>();
		for(int i= start; i< end ;i+=1){
			Set<Integer> front = isReachable(list, start, i, cache);
			Set<Integer> back = isReachable(list, i+1, end, cache);
			for(int frontVal: front){
				for(int backVal: back){
					set.add(frontVal + backVal);
					set.add(frontVal - backVal);
					set.add(frontVal * backVal);
					if(backVal != 0){
						set.add(frontVal / backVal);
					}
					
					
				}
			}
		}
		cache[start][end]=set;
		return set;
	}
 	public static void main(String[] args) {
		// TODO Auto-generated method stub
 		System.out.println(isReachable(Arrays.asList(1,2,3,4), 1));

	}

}
