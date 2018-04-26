
public class Longest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] roads={{0, 0, 0}, 
				{1, 1 ,1},
				{1, 0, 0}};
		System.out.println(new Longest().longest(roads));

	}
	class Statistics{
		int left; // the number of 1 from the most left boundary to the current position
		int right; // the number of 1 from the most right boundary to the current position
		int up;  // the number of 1 from the most up boundary to the current position
		int down; // the number of 1 from the most down boundary to the current position
		
		int length(){
			return (left>0 ? left-1: 0)+(right>0? right -1: 0)+(up>0? up-1: 0)+(down>0? down-1:0)+1;
		}
		public String toString(){
			return left+","+right+","+up+","+down;
		}
	}
	public int longest(int [][] roads){
		if(roads == null || roads.length == 0 || roads[0] == null || roads[0].length == 0){
			return 0;
		}
		int height = roads.length;
		int length = roads[0].length;
		Statistics [][] statistics = new Statistics[height+2][length+2];
		for(int i=0; i< height+2;i+=1){
			for(int j=0; j< length+2; j+=1){
				statistics[i][j] = new Statistics();
			}
		}
		computeLeftAndUp(statistics, roads);
		int result = computeDownAndRight(statistics, roads);
		return result;
		
	}
	private int computeDownAndRight(Statistics[][] statistics, int[][] roads) {
		int result = 0;
		for(int i= roads.length-1; i>=0; i-=1){
			for(int j= roads[0].length-1; j>=0; j-=1){
				Statistics  current = statistics[i+1][j+1];
				if(roads[i][j] == 0){
					current.down = 0;
					current.right = 0;
				}else{
				    
					
					current.down  = statistics[i+2][j].down+1;
					current.right = statistics[i+1][j+2].right+1;
				}
				System.out.println(current);
				if(result < current.length()){
					
					 result  = current.length();
				}
				
			}
		}
		return result;
	}
	private void computeLeftAndUp(Statistics[][] statistics, int[][] roads) {
		
		for(int i=0; i< roads.length;i+=1){
			for(int j=0; j< roads[0].length; j+=1){
				Statistics  current = statistics[i+1][j+1];
				if(roads[i][j] == 0){
					current.left = 0;
					current.up = 0;
				}else{
				    
					
					current.left  = statistics[i+1][j].left+1;
					current.up = statistics[i][j+1].up+1;
				}
				
				
			}
		}
		
	}

}
