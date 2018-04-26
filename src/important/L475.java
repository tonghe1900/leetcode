package important;
/**
 * leetcode 475 - Heaters

https://leetcode.com/problems/heaters/
Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all the houses.
Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that all houses could be covered by those heaters.
So, your input will be the positions of houses and heaters seperately, and your expected output will be the minimum radius standard of heaters.
Note:
Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
As long as a house is in the heaters' warm radius range, it can be warmed.
All the heaters follow your radius standard and the warm radius will the same.
Example 1:
Input: [1,2,3],[2]
Output: 1
Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.
Example 2:
Input: [1,2,3,4],[1,4]
Output: 1
Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.
X. https://discuss.leetcode.com/topic/71429/java-easy-solution
I am sharing my solution. The idea is to find the closest heater to each house and take maximum of the closest distances. 
Thus initially it is necessary to sort both houses and heaters by their coordinates. Then assign two pointers, one for houses 
and another for heaters. Then start traversing the houses. If the ith house is located between j-1th heater and
 jth heater, then take distance to the closest one and check whether it is the maximum radius found so far. 
 The corner cases are when a house is located before the 1st heater, and when a house is located after the last heater.
  At the corner case position, there are only distance to consider. That's it. I think code will clarify the idea more.

    public int findRadius(int[] houses, int[] heaters) {
        if(houses == null || houses.length == 0) return 0;
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int ans = 0;
        int i  = 0;
        int j = 0;
        while(i<houses.length){
            if(houses[i] <= heaters[j]){ //if house is located before heater j.
                if(j == 0){ // corner case when the heater is the first  one
                    ans = Math.max(ans, heaters[j]-houses[i]);
                    i++;
                    continue;
                }
            } else { // if house is located after some heater, 
                while(j!=heaters.length-1 && heaters[j]<houses[i]){ // then find a heater that stands after the house
                    j++;
                }
                if(j == 0 || heaters[j] < houses[i]){ // corner cases if j is 0 or there is no more heaters
                    ans = Math.max(ans, houses[i]-heaters[j]);
                    i++;
                    continue;
                }
            }
            int dist = Math.min(houses[i]-heaters[j-1], heaters[j]-houses[i]); // if house is located between jth and j-1th heaters
            ans = Math.max(ans, dist);
            i++;
        }
        
        return ans;
    }

X. https://discuss.leetcode.com/topic/71460/short-and-clean-java-binary-search-solution
The idea is to leverage decent Arrays.binarySearch() function provided by Java.
For each house, find its position between those heaters (thus we need the heaters array to be sorted).
Calculate the distances between this house and left heater and right heater, get a MIN value of those two values. Corner cases are there is no left or right heater.
Get MAX value among distances in step 2. It's the answer.
Time complexity: max(O(nlogn), O(mlogn)) - m is the length of houses, n is the length of heaters.
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int result = Integer.MIN_VALUE;
        
        for (int house : houses) {
         int index = Arrays.binarySearch(heaters, house);
         if (index < 0) {
          index = -(index + 1);
         }
         int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
         int dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
         
         result = Math.max(result, Math.min(dist1, dist2));
        }
        
        return result;
    }
Just a small variation (we can ignore houses on heaters, and I like ~):
public int findRadius(int[] houses, int[] heaters) {
    Arrays.sort(heaters);
    int result = 0;
    
    for (int house : houses) {
        int index = Arrays.binarySearch(heaters, house);
        if (index < 0) {
            index = ~index;
            int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
            int dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
            
            result = Math.max(result, Math.min(dist1, dist2));
        }
    }
    
    return result;
}
http://bookshadow.com/weblog/2016/12/11/leetcode-heaters/
升序排列加热器的坐标heaters

遍历房屋houses，记当前房屋坐标为house：

    利用二分查找，分别找到不大于house的最大加热器坐标left，以及不小于house的最小加热器坐标right
    
    则当前房屋所需的最小加热器半径radius = min(house - left, right - house)
    
    利用radius更新最终答案ans
    def findRadius(self, houses, heaters):
        """
        :type houses: List[int]
        :type heaters: List[int]
        :rtype: int
        """
        ans = 0
        heaters.sort()
        for house in houses:
            radius = 0x7FFFFFFF
            le = bisect.bisect_right(heaters, house)
            if le > 0:
                radius = min(radius, house - heaters[le - 1])
            ge = bisect.bisect_left(heaters, house)
            if ge < len(heaters):
                radius = min(radius, heaters[ge] - house)
            ans = max(ans, radius)
        return ans
 * @author het
 *
 */
public class L475 {
//	I am sharing my solution. The idea is to find the closest heater to each house and take maximum of the closest distances. 
//	Thus initially it is necessary to sort both houses and heaters by their coordinates. Then assign two pointers, one for houses 
//	and another for heaters. Then start traversing the houses. If the ith house is located between j-1th heater and
//	 jth heater, then take distance to the closest one and check whether it is the maximum radius found so far. 
//	 The corner cases are when a house is located before the 1st heater, and when a house is located after the last heater.
//	  At the corner case position, there are only distance to consider. That's it. I think code will clarify the idea more.

	    public int findRadius(int[] houses, int[] heaters) {
	        if(houses == null || houses.length == 0) return 0;
	        Arrays.sort(houses);
	        Arrays.sort(heaters);
	        int ans = 0;
	        int i  = 0;
	        int j = 0;
	        while(i<houses.length){
	            if(houses[i] <= heaters[j]){ //if house is located before heater j.
	                if(j == 0){ // corner case when the heater is the first  one
	                    ans = Math.max(ans, heaters[j]-houses[i]);
	                    i++;
	                    continue;
	                }
	            } else { // if house is located after some heater, 
	                while(j!=heaters.length-1 && heaters[j]<houses[i]){ // then find a heater that stands after the house
	                    j++;
	                }
	                if(j == 0 || heaters[j] < houses[i]){ // corner cases if j is 0 or there is no more heaters
	                    ans = Math.max(ans, houses[i]-heaters[j]);
	                    i++;
	                    continue;
	                }
	            }
	            int dist = Math.min(houses[i]-heaters[j-1], heaters[j]-houses[i]); // if house is located between jth and j-1th heaters
	            ans = Math.max(ans, dist);
	            i++;
	        }
	        
	        return ans;
	    }
	    
	    
	    
//	    The idea is to leverage decent Arrays.binarySearch() function provided by Java.
//	    For each house, find its position between those heaters (thus we need the heaters array to be sorted).
//	    Calculate the distances between this house and left heater and right heater, get a MIN value of those two values. Corner cases are there is no left or right heater.
//	    Get MAX value among distances in step 2. It's the answer.
//	    Time complexity: max(O(nlogn), O(mlogn)) - m is the length of houses, n is the length of heaters.
	        public int findRadius1(int[] houses, int[] heaters) {
	            Arrays.sort(heaters);
	            int result = Integer.MIN_VALUE;
	            
	            for (int house : houses) {
	             int index = Arrays.binarySearch(heaters, house);
	             if (index < 0) {
	              index = -(index + 1);
	             }
	             int dist1 = index - 1 >= 0 ? house - heaters[index - 1] : Integer.MAX_VALUE;
	             int dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
	             
	             result = Math.max(result, Math.min(dist1, dist2));
	            }
	            
	            return result;
	        }

}
