package important.backtrack;
/**
 * Minimum number of jumps to reach end
Given an array of integers where each element represents the max number of steps that can be made forward from that element. 
Write a function to return the minimum number of jumps to reach the end of the array (starting from the first element). 
If an element is 0, then cannot move through that element.
Example:
Input: arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}
Output: 3 (1-> 3 -> 8 ->9)
First element is 1, so can only go to 3. Second element is 3, so can make at most 3 steps eg to 5 or 8 or 9.
 * @author het
 * 
 * 
 *
 */
public class MinJumpToEnd {
    public static int minJump(int [] arr){
    	if(arr == null || arr.length == 0) return 0;
    	if(arr[0] <=0) return -1;
    	int jumpLength = arr[0];
    	int maxHeap = arr[0];
    	int jump=1;
    	for(int i=1;i<arr.length-1;i+=1){
    		maxHeap = Math.max(maxHeap-1, arr[i]);
    		jumpLength-=1;
    		if(jumpLength == 0){
    			if(maxHeap <=0 ) throw new  RuntimeException("could not go through the bridge!!");
    			jumpLength = maxHeap;
    			jump+=1;
    		}
    		
    		
    	}
    	return jump;
    	
    }
    
    public boolean canJump(int[] A) {
        if(A.length <= 1)
            return true;
     
        int max = A[0]; //max stands for the largest index that can be reached.
     
        for(int i=0; i<A.length; i++){
            //if not enough to go to next
            if(max <= i && A[i] == 0) 
                return false;
     
            //update max    
            if(i + A[i] > max){
                max = i + A[i];
            }
     
            //max is enough to reach the end
            if(max >= A.length-1) 
                return true;
        }
     
        return false;    
    }
    
    
    public int jump(int[] nums) {
    	if (nums == null || nums.length == 0)
    		return 0;
     
    	int lastReach = 0;
    	int reach = 0;
    	int step = 0;
     
    	for (int i = 0; i <= reach && i < nums.length; i++) {
    		//when last jump can not read current i, increase the step by 1
    		if (i > lastReach) {
    			step++;
    			lastReach = reach;
    		}
    		//update the maximal jump 
    		reach = Math.max(reach, nums[i] + i);
    	}
     
    	if (reach < nums.length - 1)
    		return 0;
     
    	return step;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	System.out.println(minJump(new int[]{1, 3, 2, 2, 0, 5, 3, 7, 6, 8, 9}));
		

	}

}
