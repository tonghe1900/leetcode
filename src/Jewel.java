import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Problem Statement
    	You have been given a list of jewelry items that must be split amongst two people: Frank and Bob. Frank likes very expensive jewelry. 
    	Bob doesn't care how expensive the jewelry is, as long as he gets a lot of jewelry. Based on these criteria you have devised the
    	 following policy:
1) Each piece of jewelry given to Frank must be valued greater than or equal to each piece of jewelry given to Bob. In other words,
 Frank's least expensive piece of jewelry must be valued greater than or equal to Bob's most expensive piece of jewelry.
2) The total value of the jewelry given to Frank must exactly equal the total value of the jewelry given to Bob.
3) There can be pieces of jewelry given to neither Bob nor Frank.
4) Frank and Bob must each get at least 1 piece of jewelry.
Given the value of each piece, you will determine the number of different ways you can allocate the jewelry to Bob and Frank following the
 above policy. For example:
	values = {1,2,5,3,4,5}
	values={1,1,1,1,1,1,1,1,1}
Valid allocations are:
  Bob       		Frank
  1,2		         3
  1,3        		 4
  1,4		         5  (first 5)
  1,4         		 5  (second 5)
  2,3 		         5  (first 5)
  2,3         		 5  (second 5)
   5  (first 5)		 5  (second 5)
   5  (second 5)	 5  (first 5)
1,2,3,4       		5,5
Note that each '5' is a different piece of jewelry and needs to be accounted for separately. There are 9 legal ways of allocating the jewelry
 to Bob and Frank given the policy, so your method would return 9.
 
 
 values = {1,2,5,3,4,5}
 
 1   2     5 
 base case.   numsOfWay(n, 0) =1
 numsOfWay(-1, k) = 0  k>0
 numsOfWay(n, k) = numsOfWay(n-1, k)+numsOfWay(n-1, k - arr[n]) k >=arr[n]
 
 k   minValueJewel - sum/2
 n*k
Definition
    	
Class:	Jewelry
Method:	howMany
Parameters:	int[]
Returns:	long
Method signature:	long howMany(int[] values)
(be sure your method is public)
    
 
Constraints
-	values will contain between 2 and 30 elements inclusive.
-	Each element of values will be between 1 and 1000 inclusive.
 
Examples
0)	
    	
{1,2,5,3,4,5}
Returns: 9
From above.
1)	
    	
{1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000}
Returns: 18252025766940
2)	
    	
{1,2,3,4,5}
Returns: 4
Valid allocations:
Bob               Frank
1,2                 3
2,3                 5
1,3                 4
1,4                 5
3)	
    	
{7,7,8,9,10,11,1,2,2,3,4,5,6}
Returns: 607
4)	
    	
{123,217,661,678,796,964,54,111,417,526,917,923}
Returns: 0
 * @author het
 *
 */


/**
 * You asked for a hint so I gave one ;)

And now some more details:
1) Maximum amount one man can get is 15000, so tables as I described will fit in memory/time.
2) Imagine you have the tables, as I defined. Think how to get the final score. Simple loop for all the values in table F, 
multiply the value with corresponding value in table B, and add to result. In example F[10][3]=2, B[10][3]=3. 
That means there are 2 ways to achive amount 10 using gem 3 and gems {0,1,2} and there are 3 ways of geting amount 10 using gems {4.5...}, so it gives you six valid divisions.
3) Computing table F is straightforward DP F[x][k] = sum (F[x-v[k]][i<k])
4) Computing B is almost the same just proces the gems backwards, and ad the end add B[x][k]+=B[x][k-1]

>Disclaimer: I did'n test it, nor participated in TCO online round so it can be wrong

ways[val][g][time] = choose(size(g), time)* ways[val- value(g)* time][gg][size(gg)]

Jewelry    discuss it
Used as: Division One - Level Two:
Value	500
Submission Rate	7 / 50 (14.00%)
Success Rate	5 / 7 (71.43%)
High Score	tomek for 378.97 points (17 mins 15 secs)
Average Score	286.10 (for 5 correct submissions)
At first glance, this problem appears to be a straightforward variation on the knapsack theme. As lars announced in his trademark fashion, 
"It's TRIVIAL!" You would expect a crowd as well-versed in dynamic programming as this one to breeze through the problem. 
Ah, but there was a twist! The treatment of equal elements gave folks fits.

Without equal elements, the problem could be solved as follows:

  sort the elements in increasing order
  count = 0
  for each i from 1 .. # of elements do
    // assume element i is Bob's highest valued item
    waysBelow = ways to make sums using elements below i
    waysAbove = ways to make sums using elements above i
    for each sum s from element i upto max do
      count += waysBelow[s - element i] * waysAbove[s]
  return count
Given K elements 1..K, you can calculate the number of ways to make various sums of those values using a typical knapsack algorithm:
  initialize array ways[0..Max] to all zeros
  ways[0] = 1
  for each i from 1 upto K do
    for each sum s from max downto element i do
      ways[s] += ways[s - element i]
At the end of these loops, ways[s] contains the number of ways to choose elements that sum to s.
Equal elements complicate the picture because we can no longer guarantee that Bob's elements are all 
to the left of Frank's elements 1 the sorted list. 
However, the only exceptions occur when Bob and Frank take elements of equal value. 
In each such case, we need to consider all the different ways that Bob and Frank can exchange
 their equal elements. Fortunately, the changes to the overall algorithm are relatively minor.

We now need to process the elements group by group instead of element by element, 
where each group contains equal elements. 
Suppose there are G equal elements in a group. For each size g between 1 and G, we calculate 
how many distributions there are in which Bob gets g items from this group. Then we multiply
 by Choose(G,g) to account for the different ways to pick g elements out of the group. 
 Altogether, the final algorithm looks like

  sort the elements in increasing order
  count = 0
  for each group of equal elements do
    i = first index of group
    G = size of group
    waysBelow = ways to make sums using elements below i
    for each g from 1 to G do
      waysAbove = ways to make sums using elements above i+g-1
      for each sum s from g * element i upto max do
        count += Choose(G,g) * waysBelow[s - g * element i] * waysAbove[s]
  return count







#include<iostream>
#include<cstdio>
#include<cctype>
#include<cmath>
#include<cstdlib>
#include<algorithm>
#include<vector>
#include<string>
#include<list>
#include<deque>
#include<map>
#include<set>
#include<queue>
#include<stack>
#include<utility>
#include<sstream>
#include<cstring>
using namespace std;

typedef vector<int> VI;

class Jewelry {
    public:
        static const int max_pieces = 35;
        static const int max_value = 1005;

        void calc(VI::iterator a, VI::iterator b, long long t[], long long s) {
            VI::iterator i;
            long long tmp[max_value*max_pieces];

            memset(t, 0, sizeof(long long)*max_value*max_pieces);

//            cout << endl;
//            cout << "uzywam: ";
            for (i = a; i != b; i++) {
                memset(tmp, 0, sizeof(long long)*max_value*max_pieces);
//                cout << *i << " ";
                for (int j = 1; j <= s; ++j) {
                    if (t[j]) {
                        tmp[j+(*i)] += t[j];
                    }
                }
                tmp[*i]++;
                for (int j = 1; j <= s; ++j) {
                    t[j] += tmp[j];
                }
            }
//            cout << endl;
        }

        long long howMany(VI vs) {
            long long ta[max_value*max_pieces];
            long long tb[max_value*max_pieces];
            long long tmp[max_value*max_pieces];
            long long result = 0;
            long long sa = vs[0];
            long long sb = 0;
            VI::iterator ii;

            memset(ta, 0, sizeof(long long)*max_value*max_pieces);

            for (int i = 1; i < vs.size(); ++i)
                sb += vs[i];

            sort(vs.begin(), vs.end());

            for (int i = 1; i < vs.size(); ++i) {
//                cout << "a (" << sa << ") : ";
//                for (int j = 0; j < i; ++j) {
//                    cout << vs[j] << " ";
//                }
//                cout << endl;
//                calc(vs.begin(), vs.begin()+i, ta, min(sa, sb));


                for (int j = 1; j <= sa; ++j) {
                    if (ta[j]) {
                        tmp[j+vs[i-1]] += ta[j];
                    }
                }
                tmp[vs[i-1]]++;
//                cout << endl;

                cout << "tmp: ";
                for (int j = 1; j < 20; ++j) {
                    cout << j << " ";
                }
                cout << endl;
                cout << "tmp: ";
                for (int j = 1; j < 20; ++j) {
                    cout << tmp[j] << " ";
                }
                cout << endl;

                calc(vs.begin()+i, vs.end(), tb, min(sa, sb));
                cout << "b (" << sb << ") : ";
                for (int j = i; j < vs.size(); ++j) {
                    cout << vs[j] << " ";
                }
                cout << endl;
                cout << "b: ";
                for (int j = 1; j < 20; ++j) {
                    cout << j << " ";
                }
                cout << endl;
                cout << "b: ";
                for (int j = 1; j < 20; ++j) {
                    cout << tb[j] << " ";
                }
                cout << endl;

                for (int j = 0; j < max_pieces*max_value; ++j) {
                    if (tmp[j] && tb[j]) {
                        result += tmp[j]*tb[j];
                    }
                }

                for (int j = 1; j <= sa; ++j) {
                    ta[j] += tmp[j];
                }
                memset(tmp, 0, sizeof(long long)*max_value*max_pieces);

                sa += vs[i];
                sb -= vs[i];
            }

            return result;
        }
};


 * @author het
 *
 */
public class Jewel {
	static class Range{
		int start;
		int end;
		int size;
		public Range(int start, int end) {
			super();
			this.start = start;
			this.end = end;
			this.size = end - start+1;
		}
		
		public void setEnd(int end){
			this.end = end;
			this.size = end - start +1;
		}
		public Range() {
			super();
		}
		
		public String toString(){
			return start+","+end;
		}
		
		
	}
	
	public static int numOfWays1(int [] arr){
		//\
		if(arr == null || arr.length <= 1) return 0;
		Arrays.sort(arr);
		int length = arr.length;
		int sum = 0;
		
		Map<Integer, Range> map = new HashMap<>();
		for(int i=0;i<arr.length;i+=1){
			int e = arr[i];
			sum+=e;
			if(!map.containsKey(e)){
				Range range = new Range();
				range.start = i;
				range.end = i;
				map.put(e, range);
			}else{
				Range range = map.get(e);
				range.end = i;
				
			}
		}
		for(Integer key: map.keySet()){
			System.out.println(key+","+ map.get(key));
		}
		int half = sum/2;
		

		int [][] left = new int[length][half+1];
		int [][] right = new int[length][half+1];
		
		//compute left and right
		left[0][arr[0]] = 1;
		for(int i=0;i<length;i+=1){
			left[i][0] = 1;
		}
		for(int i = 1; i<length;i+=1){
			for(int j=1;j<=half;j+=1){
				for(int k=0;k<i;k+=1){
					if(j >arr[i]){
						if(left[k][j- arr[i]] > 0){
							Range range = map.get(arr[i]);
							
							left[i][j] += left[k][j- arr[i]];
						}
						
					}else if(j == arr[i]){
						left[i][j] = 1;
					}
					
				}
				
			}
		}
		
		for(int i=0; i<length;i+=1){
			for(int j=0;j<=half;j+=1){
				System.out.print(left[i][j] + ",");
			}
			System.out.println();
		}
		right[length-1][arr[length-1]] = 1;
		for(int i = 0;i<length;i+=1){
			right[i][0] = 1;
		}
		for(int i = length-2; i>=0;i-=1){
			for(int j=1;j<=half;j+=1){
				for(int k=i+1;k<length;k+=1){
					if(j > arr[i]){
						right[i][j] += right[k][j- arr[i]];
					}else if(j == arr[i]){
						right[i][j] = 1;
					}
					
				}
				
			}
		}
		System.out.println();
		for(int i = 0; i<length;i+=1){
			for(int j=0;j<=half;j+=1){
				System.out.print(right[i][j]);
				
			}
			System.out.println();
		}
		
		
		
		int result = 0;
		for(int i=0;i<length-1;i+=1){
			for(int j = 1; j<=half;j+=1){
				for(int k=i+1;k<length;k+=1){
					result+=left[i][j]*right[k][j];
				}
				
			}
		}
		return result;
		
		
		//return 0;
		
		
	}
	public static int numOfWays(int [] arr){
		if(arr == null || arr.length <= 1) return 0;
		Arrays.sort(arr);
		//left[i][sum]    // the right boundary is i index and the total sum is sum= the value of ways
		//right[i][sum]   // the left boundary is i index and the total sum is sum = the value of ways
		int length = arr.length;
		int sum = 0;
		
		Map<Integer, Range> map = new HashMap<>();
		for(int i=0;i<arr.length;i+=1){
			int e = arr[i];
			sum+=e;
			if(!map.containsKey(e)){
				Range range = new Range();
				range.start = i;
				range.end = i;
				map.put(e, range);
			}else{
				Range range = map.get(e);
				range.end = i;
				
			}
		}
		for(Integer key: map.keySet()){
			System.out.println(key+","+ map.get(key));
		}
		int half = sum/2;
		int [][] left = new int[length][half+1];
		int [][] right = new int[length][half+1];
		//compute left and right
		left[0][arr[0]] = 1;
		for(int i=0;i<length;i+=1){
			left[i][0] = 1;
		}
		for(int i = 1; i<length;i+=1){
			for(int j=1;j<=half;j+=1){
				for(int k=0;k<i;k+=1){
					if(j >arr[i]){
						if(left[k][j- arr[i]] > 0){
							Range range = map.get(arr[i]);
							
							left[i][j] += left[k][j- arr[i]];
						}
						
					}else if(j == arr[i]){
						left[i][j] = 1;
					}
					
				}
				
			}
		}
		
		for(int i=0; i<length;i+=1){
			for(int j=0;j<=half;j+=1){
				System.out.print(left[i][j] + ",");
			}
			System.out.println();
		}
		right[length-1][arr[length-1]] = 1;
		for(int i = 0;i<length;i+=1){
			right[i][0] = 1;
		}
		for(int i = length-2; i>=0;i-=1){
			for(int j=1;j<=half;j+=1){
				for(int k=i+1;k<length;k+=1){
					if(j > arr[i]){
						right[i][j] += right[k][j- arr[i]];
					}else if(j == arr[i]){
						right[i][j] = 1;
					}
					
				}
				
			}
		}
		System.out.println();
		for(int i = 0; i<length;i+=1){
			for(int j=0;j<=half;j+=1){
				System.out.print(right[i][j]);
				
			}
			System.out.println();
		}
		
		
		
		int result = 0;
		for(int i=0;i<length-1;i+=1){
			for(int j = 1; j<=half;j+=1){
				for(int k=i+1;k<length;k+=1){
					result+=left[i][j]*right[k][j];
				}
				
			}
		}
		return result;
		
	}
	
	private int end;
	private int k;
	private Integer[][] cache;
     //values = {1,2,5,3,4,5}
	
	// values = {1,2,3,4,5,5}
	// case one : each of two both has only one 
	// case two: Frank  1     Bob  2
	//                    1          2.... n-1
	//  case three      2          2     all fours  same value
	//                  2         3....n-1
	
	//                  3          3
	//                  3          4.. n-1
	
	
	//                  4          4
	//                  4          5.. n-1
	
	//                  N/2  
	//                  
	/**
	 *   Bob       		Frank
  1,2		         3
  1,3        		 4
  1,4		         5  (first 5)
  1,4         		 5  (second 5)
  2,3 		         5  (first 5)
  2,3         		 5  (second 5)
   5  (first 5)		 5  (second 5)
   5  (second 5)	 5  (first 5)
1,2,3,4       		5,5
	 * @param args
	 * 
	 * 
	 * {1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000,
 1000,1000,1000,1000,1000}
Returns: 18252025766940
2)	
    	
{1,2,3,4,5}
Returns: 4
Valid allocations:
Bob               Frank
1,2                 3
2,3                 5
1,3                 4
1,4                 5
3)	
    	
{7,7,8,9,10,11,1,2,2,3,4,5,6}
Returns: 607
4)	
    	
{123,217,661,678,796,964,54,111,417,526,917,923}
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(new Jewel().numberOfWays(new int[]{1,2,3,4,5}));
		//System.out.println(new Jewel().numberOfWays(new int[]{1,1,1,1}));
		System.out.println(new Jewel().numberOfWays(new int[]{1,1,1,1}));
		//System.out.println(new Jewel().numberOfWays(new int[]{123,217,661,678,796,964,54,111,417,526,917,923}));
	//	System.out.println(new Jewel().numOfWays1(new int[]{7,7,8,9,10,11,1,2,2,3,4,5,6}));
//		System.out.println(new Jewel().numWays1(new int[]{1,2,5,3,4,5}));
//		
//		System.out.println(new Jewel().numWays1(new int[]{1,2,3,4,5}));
		
		
		//{7,7,8,9,10,11,1,2,2,3,4,5,6}
//		Returns: 607
//		4)	
//		    	
//		{123,217,661,678,796,964,54,111,417,526,917,923}
		

	}
	
	
	public List<int[]> chooose(int [] arr, int num){  // return the miminum index choosen and the sum of all the number
		List<int[]> result = new ArrayList<>();
		choose(arr, result, num, arr.length, 0, arr.length);
		return result;
	}
	
	private void choose(int [] arr, List<int[]> result, int num, int lastChoosenIndex, int sum, int lastConsideredIndex){
	
		if(num == 0) {
			result.add(new int[]{lastChoosenIndex , sum});
			return;
		}
		
		if(lastConsideredIndex <=0 )return;
		
		choose(arr, result, num-1, lastConsideredIndex-1, sum+arr[lastConsideredIndex-1] , lastConsideredIndex-1);
		choose(arr, result, num, lastChoosenIndex, sum , lastConsideredIndex-1);
	}
	
	public int numWays(int [] arr){
		if(arr == null || arr.length == 0) return 0;
		Arrays.sort(arr);
		int number = 0;
		int length = arr.length;
		for(int i=1;i<=length;i+=1){
			List<int[]> list = chooose(arr, i);
			for(int[] element: list){
				number+= numWays(arr, element[0]-1, element[1]);
			}
			
			
			
		}
		return number;
	}
	
	private int numWays(int [] arr, int end, int k){
		if(arr == null || arr.length == 0) return 0;
		 this.end = end;
		 this.k = k;
		 cache =  new Integer[end+1][k+1];
		return numWaysHelper(arr,  end,  k, cache);
	}
	
//	private int numWaysHelper(int[] arr, int end, int k){
//		
//	}

	private int numWaysHelper(int[] arr, int end, int k, Integer[][] cache) {
		if(k == 0) return 1;
		if(end == -1) return 0;
		
		if(k < 0)  return 0;
		if(cache[end][k] != null) return cache[end][k];
		int result = numWaysHelper(arr, end-1, k, cache)+numWaysHelper(arr, end-1, k-arr[end], cache);
		cache[end][k] = result;
		return result;
	}
	
	
	public int numberOfWays(int [] arr){
		if(arr == null || arr.length <= 1) return 0;
		Arrays.sort(arr);
		int length = arr.length;
		int sum = 0;
		Map<Integer, Range> map = new TreeMap<>();
		for(int i=0;i<arr.length;i+=1){
			int e = arr[i];
			sum+=e;
			if(!map.containsKey(e)){
				Range range = new Range(i, i);
				
				map.put(e, range);
			}else{
				Range range = map.get(e);
				range.setEnd(i);
				
			}
		}
		int maxSize = 0;
		Set<Integer> keys = map.keySet();
		int []allValues = new int[keys.size()];
		int index = 0;
		for(Integer key:keys){
			allValues[index ++] = key;
		}
		for(Integer key: keys){
			if(maxSize < map.get(key).size){
				maxSize = map.get(key).size;
			}
			//System.out.println(key+","+ map.get(key));
		}
		int groupNum = map.size();
		int half = sum/2;
		
		
		int [][] right = new int[length][half+1];

		
		
		right[length-1][arr[length-1]] = 1;
		for(int i = 0;i<length;i+=1){
			right[i][0] = 1;
		}
		for(int i = length-2; i>=0;i-=1){
			for(int j=1;j<=half;j+=1){
				for(int k=i+1;k<length;k+=1){
					if(j > arr[i]){
						right[i][j] += right[k][j- arr[i]];
					}else if(j == arr[i]){
						right[i][j] = 1;
					}
					
				}
				
			}
		}
		
		int [][][] left = new int [groupNum+1][maxSize+1][half+1];
	    for(int i=0;i<=maxSize;i+=1){
	    	for(int j=0;j<=half;j+=1){
	    		left[0][i][j] = 0;
	    	}
	    }
	    
	    for(int i=0;i<=groupNum;i+=1){
	    	for(int j=0;j<=maxSize;j+=1){
	    		left[i][j][0] = 1;
	    	}
	    }
	    int totalSum = 0;
	    for(int i=1;i<=groupNum;i+=1){
	    	for(int j=0;j<=map.get(allValues[i-1]).size;j+=1){
	    		for(int k= 1;k<=half;k+=1){
	    			if(allValues[i-1]*j > k) {
	    				left[i][j][k]  = 0;
	    			}else if(allValues[i-1]*j == k){
	    				left[i][j][k]  = 1;
	    			}else{
	    				if(i > 1){
	    					for(int jj = 0;jj<= map.get(allValues[i-2]).size;jj+=1){
	    						left[i][j][k] +=left[i-1][jj][k-allValues[i-1]*j];
		    				}
	    					left[i][j][k] = left[i][j][k]* choose(map.get(allValues[i-1]).size, j);
		    				
	    				}
	    				
	    			}
	    			int beginNext = (i>1?map.get(allValues[i-2]).end: -1)+j+1;
	    			if(beginNext >0 && beginNext < arr.length){
	    				for(int w = beginNext;w<arr.length;w+=1){
	    					totalSum+=left[i][j][k]* right[w][k];
	    				}
	    				
	    			}
	    			
	    			
	    			
	    			
	    		}
	    	}
	    }
	    
	    return totalSum;
	    
	    
	    
		

//		int [][] left = new int[length][half+1];
//		int [][] right = new int[length][half+1];
//		int count = 0;
//		int []waysBelow = new int[half+1];
//		int []waysAbove = new int[half+1];
//		for(int i=0;i<length;i+=1){
//			
//		}
	}
	/**
	 * Without equal elements, the problem could be solved as follows:

  sort the elements in increasing order
  count = 0
  for each i from 1 .. # of elements do
    // assume element i is Bob's highest valued item
    waysBelow = ways to make sums using elements below i
    waysAbove = ways to make sums using elements above i
    for each sum s from element i upto max do
      count += waysBelow[s - element i] * waysAbove[s]
  return count
Given K elements 1..K, you can calculate the number of ways to make various sums of those values using a typical knapsack algorithm:
  initialize array ways[0..Max] to all zeros
  ways[0] = 1
  for each i from 1 upto K do
    for each sum s from max downto element i do
      ways[s] += ways[s - element i]
At the end of these loops, ways[s] contains the number of ways to choose elements that sum to s.
Equal elements complicate the picture because we can no longer guarantee that Bob's elements are all 
to the left of Frank's elements in the sorted list. 
However, the only exceptions occur when Bob and Frank take elements of equal value. 
In each such case, we need to consider all the different ways that Bob and Frank can exchange
 their equal elements. Fortunately, the changes to the overall algorithm are relatively minor.

We now need to process the elements group by group instead of element by element, 
where each group contains equal elements. 
Suppose there are G equal elements in a group. For each size g between 1 and G, we calculate 
how many distributions there are in which Bob gets g items from this group. Then we multiply
 by Choose(G,g) to account for the different ways to pick g elements out of the group. 
 Altogether, the final algorithm looks like

  sort the elements in increasing order
  count = 0
  for each group of equal elements do
    i = first index of group
    G = size of group
    waysBelow = ways to make sums using elements below i
    for each g from 1 to G do
      waysAbove = ways to make sums using elements above i+g-1
      for each sum s from g * element i upto max do
        count += Choose(G,g) * waysBelow[s - g * element i] * waysAbove[s]
  return count
	 **/
	private int choose(int size, int j) {
		
		return factorial(size)/ (factorial(j)* factorial(size-j));
	}
	private int factorial(int i) {
		if(i ==0) return 1;
		if(i == 1) return 1;
		return i* factorial(i-1);
	}
	
	

}
