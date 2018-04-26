package alite.leetcode.newExtra;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Rooms:	06-6C005/901/11501 Burnet Rd/TX-Austin@IBMUS

前15分钟先聊人生，然后就出了一题，之前没见过：
Define amazing number as: its value is less than or equal to its index. Given a circular array, find the starting position, 
such that the total number of amazing numbers in the array is maximized.. 鐣欏鐢宠璁哄潧-涓€浜╀笁鍒嗗湴
Example 1: 0, 1, 2, 3
Ouptut: 0.    When starting point at position 0, all the elements in the array are equal to its index. So all the numbers are amazing number.. more info on 1point3acres.com
Example 2: 1, 0 , 0
Output: 1.    When starting point at position 1, the array becomes 0, 0, 1. All the elements are amazing number.
If there are multiple positions, return the smallest one.
 * @author het
 *
 */

//  5   2   3  5   6    7
// the difference between its value to its index
//  5 the starting position      5     2-1     3 - 2   5-3    6-4   7-5
//  2 the starting position      5-(length-1)=0     1+1       1+1            <=0
//  3 the starting position      1                  2-(length-1))=-3   3

//  5   1   1  2   2    2       amazing number  0
//  0   2   2  3   3    3                        1
//  1   -3  3  4   4    4                        1
//  2   -2  -2 5   5    5                       2
//  3   -1   -1  0  6   6                        3
//  4    0    0  1  1   7                        2


//i 




// 
public class AmazingNumber {
	
	public static void main(String []args){
		System.out.println(new AmazingNumber().getStartingPosition(new int[]{ 5 ,  2 ,  3 , 5 ,  6 ,   7}));
	}
	 private Map<Integer, Integer> numbersOfAmazingNumberForEachIndex = new HashMap<>();
	 public int getStartingPosition(int [] arr){
	    	if(arr == null || arr.length == 0) return -1;
	    	if(arr.length == 1) return 0;
	    	int length = arr.length;
	    	int[] differenceForAmazingNumbers = getDifferences(arr);
	    	if(allDiffNoMoreThanZero(differenceForAmazingNumbers)) return 0;
	    	for(int i =0 ;i<length;i+=1){
	    		int val = differenceForAmazingNumbers[i];
	    		if(val + i - (length-1) > 0) {
	    			continue;
	    		}
	    		
	    		for(int j=1;j <= length;j+=1){
	    			if(j < i+2){
	    				int newValue = val+ (j-1);
	    				if(newValue <=0 ){
	    					increment(numbersOfAmazingNumberForEachIndex, j-1 );
	    				}
	    				
	    			}else {
	    				int newValue = val+ (j-1) - length;
	    				if(newValue <=0 ){
	    					increment(numbersOfAmazingNumberForEachIndex, j-1 );
	    				}
	    				if(newValue == 0) {
	    					break;
	    				}
	    			
	    			}
	    		}
            
	    		
	    		
	    		
	    	}
	    	int max = 0;
	    	int keyForMax = 0;
	    	for(Integer key: numbersOfAmazingNumberForEachIndex.keySet()){
	    		int val = numbersOfAmazingNumberForEachIndex.get(key);
	    		if(max< val){
	    			max = val;
	    			keyForMax = key;
	    		}else if(max ==val){
	    			keyForMax = Math.min(keyForMax, key);
	    		}
	    	}
	    	
	    	return keyForMax;
	    }
	 
private boolean allDiffNoMoreThanZero(int[] differenceForAmazingNumbers) {
		boolean result = true;
		for(int diff: differenceForAmazingNumbers){
			result =result &&  (diff<=0);
		}
		return result;
	}

private void increment(
			Map<Integer, Integer> numbersOfAmazingNumberForEachIndex, int i) {
	   
		if(numbersOfAmazingNumberForEachIndex.containsKey(i)){
			int val = numbersOfAmazingNumberForEachIndex.get(i);
			numbersOfAmazingNumberForEachIndex.put(i, val+1);
		}else{
			numbersOfAmazingNumberForEachIndex.put(i, 1);
		}
		
	}

//    public int getStartingPosition(int [] arr){
//    	if(arr == null || arr.length == 0) return -1;
//    	if(arr.length == 1) return 0;
//    	int[] differenceForAmazingNumbers = getDifferences(arr);
//    }
//
	private int[] getDifferences(int[] arr) {
		int[] differenceForAmazingNumbers = new int[arr.length];
		for(int i=0;i<arr.length;i+=1){
			differenceForAmazingNumbers[i] = arr[i] - i;
		}
		return differenceForAmazingNumbers;
	}

	
}
