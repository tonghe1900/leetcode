
public class Practice {
	
	
	
	 static long getWays(long n, long[] c){
	        // Complete this function
	        // ways[n+1][i]  =  the ways generating n using index 0 ..... i-1
	        // ways[n+1][0]    the ways generating n without using any coins
	        if(n <0)  return 0;
	        if(n ==0) return 1;
	        if((c == null || c.length == 0) && n > 0) return 0;
	        int [][] ways = new int[n+1][c.length+1];
	        for(int i=0;i<=c.length;i+=1){
	            ways[0][i] = 1;
	        }
	        
	        for(int i=1;i<=n;i+=1){
	            ways[i][0] = -1; 
	            
	        }
	        for(int i = 0;i< ways.length;i+=1){
	            for(int j=0;j<ways[0].length;j+=1){
	                for(int k=0; k<=j;k+=1){
	                    if(i >= c[j] && ways[i-c[j]][k] >=0) {
	                        ways[i][j] += ways[i][k];
	                    }
	                }
	            }
	            
	        }
	        return ways[n][c.length];
	        
	        
	        
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
