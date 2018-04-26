import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class Test {
	class Boundary{
		int start;
		int end;
		int sum;
		public Boundary(int start, int end){
			super();
			this.start = start;
			this.end = end;
		}
		public Boundary(int start, int end, int sum) {
			super();
			this.start = start;
			this.end = end;
			this.sum = sum;
		}
		public int length(){
			return end-start+1;
		}
		
	}
	
	
	public int []  getMaxIntervalDP2(int a[]){
		int start = 0; int end = 0;
		int prevIntervalSum = a[0];
		int max = a[0];
		int startForMax = 0;
		int endForMax = 0;
		for(int i=1;i<a.length;i+=1){
	
			if(prevIntervalSum + a[i] >= a[i]){
				end+=1;
				prevIntervalSum += a[i];
			}else{
				start = i;
				end = i;
				prevIntervalSum = a[i];
			}
			
			if(max < prevIntervalSum){
				startForMax = start;
				endForMax = end;
				max = prevIntervalSum;
			}
			
		}
		Boundary boundary = new Boundary(startForMax, endForMax);
    	int [] result = new int[boundary.length()];
    	for(int i=0; i<result.length; i+=1){
    		result[i] = a[boundary.start+i];
    	}
    	return result;
	}
	//  getLongestSubarray(array, index, k) =  getLongestSubarray(array, index -1, k) + index  if <=k
	                                         //   index    if array[index] <=k
	public int []  getMaxIntervalDP(int a[]){
		int start = 0; int end = 0; int minSum = 0; int minBeginIndex = -1;
		int maxSum = Integer.MIN_VALUE;
		int sum = 0;
    	for(int i=0; i< a.length; i+=1){
    		sum+= a[i];
    		if(sum < minSum){
    			minSum  = sum; 
    			minBeginIndex = i;
    		}
    		
    		if(sum - minSum > maxSum){
    			maxSum = sum - minSum;
    			start = minBeginIndex + 1;
    			end = i;
    		}
    		
    		
    	}
    	
    	
    	Boundary boundary = new Boundary(start, end);
    	if(boundary == null) return new int[]{};
    	int [] result = new int[boundary.length()];
    	for(int i=0; i<result.length; i+=1){
    		result[i] = a[boundary.start+i];
    	}
    	return result;
	}
	
	public int []  getMaxInterval(int a[]){
		int sum = 0;
    	for(int i=0; i< a.length; i+=1){
    		sum += a[i];
    	}
    	
    	Boundary boundary =  getMaxInterval(a, 0, a.length-1,sum, new HashMap<String, Boundary>());
    	if(boundary == null) return new int[]{};
    	int [] result = new int[boundary.length()];
    	for(int i=0; i<result.length; i+=1){
    		result[i] = a[boundary.start+i];
    	}
    	return result;
	}
    public int  [] longestSubarray(int [] a, int k){
    	int sum = 0;
    	for(int i=0; i< a.length; i+=1){
    		sum += a[i];
    	}
    	Boundary boundary =  longestSubarrayHelper(a, k, 0, a.length-1, sum, new HashMap<String, Boundary>());
    	if(boundary == null) return new int[]{};
    	int [] result = new int[boundary.length()];
    	for(int i=0; i<result.length; i+=1){
    		result[i] = a[boundary.start+i];
    	}
    	return result;
    	
    }
    private String createKey(int start, int end){
    	return start+","+end;
    }
    
    
    private Boundary getMaxInterval(int [] a, int start, int end, int currentSum,  Map<String, Boundary> cache){
    	if(start > end) return null;
    	String key = createKey(start, end);
    	if(cache.containsKey(key)) return cache.get(key);
    	if(start  == end) return new Boundary(start, end, currentSum);
    	
    	Boundary first = getMaxInterval(a,start, end-1, currentSum - a[end], cache);
    	Boundary second = getMaxInterval(a,start+1, end, currentSum - a[start],  cache);
    	Boundary current = new Boundary(start, end , currentSum);
    	Boundary bigger = current.sum > first.sum? current: first;
    	return bigger.sum > second.sum? bigger: second;
    	
    	
    }
    
    private Boundary longestSubarrayHelper(int [] a, int k, int start, int end, int currentSum, Map<String, Boundary> cache){
    	if(start > end) return null;
    	if(currentSum <=k) return new Boundary(start, end);
    	String key = createKey(start, end);
    	if(cache.containsKey(key)) return cache.get(key);
    	Boundary first = longestSubarrayHelper(a, k, start, end-1, currentSum - a[end], cache);
    	Boundary second = longestSubarrayHelper(a, k, start+1, end, currentSum - a[start], cache);
    	Boundary result = null;
    	if(first != null || second != null) {
    	 result = (first == null ? 0: first.length()) > (second == null ? 0 :second.length())? first: second;
    	}
    	 cache.put(key, result);
    	return result;
    	
    }
    
 // @include
    public static int maxSubarraySumInCircular(List<Integer> A) {
      return Math.max(findMaxSubarray(A), findCircularMaxSubarray(A));
    }

    // Calculates the non-circular solution.
    private static int findMaxSubarray(List<Integer> A) {
      int maximumTill = 0, maximum = 0;
      for (Integer a : A) {
        maximumTill = Math.max(a, a + maximumTill);
        maximum = Math.max(maximum, maximumTill);
      }
      return maximum;
    }

    // Calculates the solution which is circular.
    private static int findCircularMaxSubarray(List<Integer> A) {
      // Maximum subarray sum starts at index 0 and ends at or before index i.
      ArrayList<Integer> maximumBegin = new ArrayList<>();
      int sum = A.get(0);
      maximumBegin.add(sum);
      for (int i = 1; i < A.size(); ++i) {
        sum += A.get(i);
        maximumBegin.add(
            Math.max(maximumBegin.get(maximumBegin.size() - 1), sum));
      }

      // Maximum subarray sum starts at index i + 1 and ends at the last element.
      List<Integer> maximumEnd
          = new ArrayList<>(Collections.nCopies(A.size(), 0));
      sum = 0;
      for (int i = A.size() - 2; i >= 0; --i) {
        sum += A.get(i + 1);
        maximumEnd.set(i, Math.max(maximumEnd.get(i + 1), sum));
      }

      // Calculates the maximum subarray which is circular.
      int circularMax = 0;
      for (int i = 0; i < A.size(); ++i) {
        circularMax
            = Math.max(circularMax, maximumBegin.get(i) + maximumEnd.get(i));
      }
      return circularMax;
    }
    
    
    
 // @include
    private interface IntegerComparator {
      Integer compare(Integer o1, Integer o2);
    }

    private static class MaxComparator implements IntegerComparator {
      @Override
      public Integer compare(Integer o1, Integer o2) {
        return o1 > o2 ? o1 : o2;
      }
    }

    private static class MinComparator implements IntegerComparator {
      @Override
      public Integer compare(Integer o1, Integer o2) {
        return o1 > o2 ? o2 : o1;
      }
    }

    public static int maxSubarraySumInCircular2(List<Integer> A) {
      // Finds the max in non-circular case and circular case.
      int accumulate = 0;
      for (int a : A) {
        accumulate += a;
      }
      return Math.max(
          findOptimumSubarrayUsingComp(A, new MaxComparator()),
          accumulate - findOptimumSubarrayUsingComp(A, new MinComparator()));
    }

    private static int findOptimumSubarrayUsingComp(List<Integer> A,
                                                    IntegerComparator comp) {
      int till = 0, overall = 0;
      for (int a : A) {
        till = comp.compare(a, a + till);
        overall = comp.compare(overall, till);
      }
      return overall;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//{1,2,3, -2,-9,5,6,7,-9,5}
//		int[] result  = (new Test().longestSubarray(new int[]{431,-15,639, 342,-14,565,-924,635,167,-70}, 184));
//		int sum = 0;
//		for(int element: result){
//			sum +=element;
//			System.out.print(element + ",");
//			
//		}
//		System.out.println();
//		System.out.println(sum);
		
		int[] array = new int[]{904,40,523, 12,-335,-385,-124,481,-31};
		List<Integer> list = new ArrayList<>();
		for(int i: array){
			list.add(i);
		}
		int result  = (new Solution().maxSubarraySumInCircular2(list));
//		int sum = 0;
//		for(int element: result){
//			sum +=element;
//			System.out.print(element + ",");
//			
//		}
		System.out.println(904+40+523+12+481-31);
		System.out.println(result);

	}

}
