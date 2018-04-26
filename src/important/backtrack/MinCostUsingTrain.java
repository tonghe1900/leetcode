package important.backtrack;
/**
 * / This function returns the smallest possible cost to
// reach station N-1 from station 0.
int minCost(int cost[][N])
{
    // dist[i] stores minimum cost to reach station i
    // from station 0.
    int dist[N];
    for (int i=0; i<N; i++)
       dist[i] = INF;
    dist[0] = 0;
 
    // Go through every station and check if using it
    // as an intermediate station gives better path
    for (int i=0; i<N; i++)
       for (int j=i+1; j<N; j++)
          if (dist[j] > dist[i] + cost[i][j])
             dist[j] = dist[i] + cost[i][j];
 
    return dist[N-1];
}
 
// Driver program to test above function
int main()
{
    int cost[N][N] = { {0, 15, 80, 90},
                      {INF, 0, 40, 50},
                      {INF, INF, 0, 70},
                      {INF, INF, INF, 0}
                    };
    cout << "The Minimum cost to reach station "
          << N << " is " << minCost(cost);
    return 0;
}

 * @author het
 *
 */
public class MinCostUsingTrain {
	private int cost[][] = { {0, 15, 80, 90},{-1, 0, 40, 50},{-1, -1, 0, 70},{-1, -1, -1, 0}};
   public int minCost(){
	   int stationLength = cost.length;
	   int [] dist = new int[stationLength];
	   for(int i = 0;i<stationLength;i+=1){
		   dist[i] = Integer.MAX_VALUE;
	   }
	   dist[0] = 0;
	   for(int i = 1;i<stationLength;i+=1){
		   for(int j = 0; j<i;j+=1){
			   dist[i]  = Math.min(dist[i], dist[j]+cost[j][i]);
		   }
	   }
	   return dist[stationLength-1];
   }
	public static void main(String[] args) {
		System.out.println(new MinCostUsingTrain().minCost());
		System.out.println(new MinCostUsingTrain().maxCost());
		System.out.println(new MinCostUsingTrain().count(3, 6));

	}
	
	public static int count(int n, int sum){
        if(sum < 0 || n <=0) return 0;
        int [] cache = new int [sum+1];
        for(int i=0;i<=sum&& i<=9;i+=1){
                  cache[i] = 1;
        }
        if(n == 1){
                 if(sum >=0 && sum<=9) {
                        return 1;
                }else{
                        return 0;
                }
         }
         int [] current = new int[sum+1];
         for( int i=1;i<n;i+=1){
                    
                    for(int j=0;j<=sum;j+=1){
                    	current[j] = 0;
                           int digit = (i== n-1)? 1: 0;
                           for(;digit <10;digit+=1){
      
                                   current[j]+= j-digit <0 ? 0: cache[j-digit];
                             }
                           
                   }
                    for(int index=0;index<=sum;index++){
                           cache[index] =current[index];
                   } 
         }
         return cache[sum];
         

}
	
	
	public int maxCost(){
		   int stationLength = cost.length;
		   int [] dist = new int[stationLength];
		   for(int i = 0;i<stationLength;i+=1){
			   dist[i] = Integer.MIN_VALUE;
		   }
		   dist[0] = 0;
		   for(int i = 1;i<stationLength;i+=1){
			   for(int j = 0; j<i;j+=1){
				   dist[i]  = Math.max(dist[i], dist[j]+cost[j][i]);
			   }
		   }
		   return dist[stationLength-1];
	   }
		

}
