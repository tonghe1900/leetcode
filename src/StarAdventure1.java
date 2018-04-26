import java.util.Arrays;


public class StarAdventure1 {
	
	
	public int mostStars1(String[] level) {
		
        int row = level.length;

        int col = level[0].length();

 

        int[][] map = new int[row][col];

        int total = 0;

        for (int i=0; i<row; i++) {

            for (int j=0; j<col; j++) {

                map[i][j] = level[i].charAt(j) - '0';

                total += map[i][j];

            }

        }

 

        if (col < 5 || row < 5) {

            return total;

        }

 

        int[][][][][] max = new int[row][col-3][col-2][col-1][col];

 

        for (int r=0; r<row; r++) {

            for(int w = 0; w< col-3; w+=1){

            for (int i=w+1; i<col-2; i++) {

 

                for (int j=i+1; j<col-1; j++) {

 

                    for (int k=j+1; k<col; k++) {

                        if (r == 0) {

                            for (int l=0; l<=k; l++) {

                                max[r][w][i][j][k] += map[0][l];

                            }

                        } else {

                            // Find the max for max[r][w][i][j][k] form max[r-1]...

                            int maxp = 0;
                           for(int ww = 0; ww<=w;ww++){
                            for (int ii=ww+1; ii<=i; ii++) {

                                for (int jj=ii+1; jj<=j; jj++) {

                                    for (int kk=jj+1; kk<=k; kk++) {

                                        int tmp = max[r-1][ww][ii][jj][kk];
                                        if(ww < w){
                                        	for(int x = ww; x< w; x++){
                                        		tmp+= map[r][x];
                                        	}
                                        }
                                        
                                        
                                        tmp += map[r][w];

                                        if (ii < i) {

                                            // Add move

                                            for (int a=ii; a<i; a++) {
                                            	if(a > w) {  // avoid duplicate add

                                                tmp += map[r][a];
                                            	}

                                            }

                                        }

                                        tmp += map[r][i];

                                        if (jj < j) {

                                            for (int b=jj; b<j; b++) {

                                                // Avoid duplicate add

                                                if (b > i) {

                                                    tmp += map[r][b];

                                                }

                                            }

                                        }

                                        tmp += map[r][j];

                                        if (kk < k) {

                                            for (int c=kk; c<k; c++) {

                                                // Avoid duplicate add

                                                if (c > j) {

                                                    tmp += map[r][c];

                                                }

                                            }

                                        }

                                        tmp += map[r][k];

 

                                        if (tmp > maxp) {

                                            maxp = tmp;

                                        }

                                    }

                                }

                            }
                           }

                            max[r][w][i][j][k] = maxp;

                        }

                    }

                }

              }
            }

        }

 

//        // debug
//
//        
//
//        for (int i=0; i<col-2; i++) {
//
//            for (int j=i+1; j<col-1; j++) {
//
//                for (int k=j+1; k<col; k++) {
//
//                    System.out.printf("max[0][%d][%d][%d] = %d%n", i, j, k, max[0][i][j][k]);
//
//                }
//
//            }
//
//        }
//
//        for (int i=0; i<col-2; i++) {
//
//            for (int j=i+1; j<col-1; j++) {
//
//                for (int k=j+1; k<col; k++) {
//
//                    System.out.printf("max[1][%d][%d][%d] = %d%n", i, j, k, max[1][i][j][k]);
//
//                }
//
//            }
//
//        }

       

 

        return max[row-1][col-4][col-3][col-2][col-1];

    }
	public int mostStars(String[] level) {
		
		        int row = level.length;
		
		        int col = level[0].length();
		
		 
		
		        int[][] map = new int[row][col];
		
		        int total = 0;
		
		        for (int i=0; i<row; i++) {
		
		            for (int j=0; j<col; j++) {
		
		                map[i][j] = level[i].charAt(j) - '0';
		
		                total += map[i][j];
		
		            }
		
		        }
		
		 
		
		        if (col < 4 || row < 4) {
		
		            return total;
		
		        }
		
		 
		
		        int[][][][] max = new int[row][col-2][col-1][col];
		
		 
		
		        for (int r=0; r<row; r++) {
		
		 
		
		            for (int i=0; i<col-2; i++) {
		
		 
		
		                for (int j=i+1; j<col-1; j++) {
		
		 
		
		                    for (int k=j+1; k<col; k++) {
		
		                        if (r == 0) {
		
		                            for (int l=0; l<=k; l++) {
		
		                                max[r][i][j][k] += map[0][l];
		
		                            }
		
		                        } else {
		
		                            // Find the max for max[r][i][j][k] form max[r-1]...
		
		                            int maxp = 0;
		
		                            for (int ii=0; ii<=i; ii++) {
		
		                                for (int jj=ii+1; jj<=j; jj++) {
		
		                                    for (int kk=jj+1; kk<=k; kk++) {
		
		                                        int tmp = max[r-1][ii][jj][kk];
		
		                                        if (ii < i) {
		
		                                            // Add move
		
		                                            for (int a=ii; a<i; a++) {
		
		                                                tmp += map[r][a];
		
		                                            }
		
		                                        }
		
		                                        tmp += map[r][i];
		
		                                        if (jj < j) {
		
		                                            for (int b=jj; b<j; b++) {
		
		                                                // Avoid duplicate add
		
		                                                if (b > i) {
		
		                                                    tmp += map[r][b];
		
		                                                }
		
		                                            }
		
		                                        }
		
		                                        tmp += map[r][j];
		
		                                        if (kk < k) {
		
		                                            for (int c=kk; c<k; c++) {
		
		                                                // Avoid duplicate add
		
		                                                if (c > j) {
		
		                                                    tmp += map[r][c];
		
		                                                }
		
		                                            }
		
		                                        }
		
		                                        tmp += map[r][k];
		
		 
		
		                                        if (tmp > maxp) {
		
		                                            maxp = tmp;
		
		                                        }
		///g
		                                    }
		
		                                }
		
		                            }
		
		                            max[r][i][j][k] = maxp;
		
		                        }
		
		                    }
		
		                }
		
		            }
		
		        }
		
		 
		
		        // debug
		
		        
		
		        for (int i=0; i<col-2; i++) {
		
		            for (int j=i+1; j<col-1; j++) {
		
		                for (int k=j+1; k<col; k++) {
		
		                    System.out.printf("max[0][%d][%d][%d] = %d%n", i, j, k, max[0][i][j][k]);
		
		                }
		
		            }
		
		        }
		
		        for (int i=0; i<col-2; i++) {
		
		            for (int j=i+1; j<col-1; j++) {
		
		                for (int k=j+1; k<col; k++) {
		
		                    System.out.printf("max[1][%d][%d][%d] = %d%n", i, j, k, max[1][i][j][k]);
		
		                }
		
		            }
		
		        }
		
		       
		
		 
		
		        return max[row-1][col-3][col-2][col-1];
		
		    }
		
		// SRM 208 Division I Level Three - 1000
		// dynamic programming, recursion
		// http://community.topcoder.com/stat?c=problem_statement&pm=2940&rd=5854
		
		/**
		 * Examples
		0)	
		    	
		{"01",
		 "11"}
		Returns: 3
		1)	
		    	
		{"0999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"
		,"9999999999"}
		Returns: 450
		2)	
		    	
		{"012"
		,"012"
		,"012"
		,"012"
		,"012"
		,"012"
		,"012"}
		Returns: 21
		3)	
		    	
		{"0123456789",
		 "1123456789",
		 "2223456789",
		 "3333456789",
		 "4444456789",
		 "5555556789",
		 "6666666789",
		 "7777777789",
		 "8888888889",
		 "9999999999"}
		Returns: 335
		 * @author het
		 *
		 */
		public class StarAdventure {
		  int N;
		  int M;
		  int[][] data;
		  int[][][] best;
		  int[][][] sums;
		
		  public int mostStars(String[] level) {
		    N = level.length;
		    M = level[0].length();
		    data = new int[N][M];
		    sums = new int[N][M][M];
		
		    for (int i = 0; i < N; i++) {
		      char[] chars = level[i].toCharArray();
		      for (int j = 0; j < M; j++) {
		        data[i][j] = chars[j] - '0';
		      }
		    }
		
		    computeSums();
		
		    if (M <= 3) { return sumAll(); }
		
		    best = new int[M][M][M];
		    for (int y = 0; y < N; y++) {
		      int[][][] curr = new int[M][M][M];
		      for (int i = 0; i < M - 2; i++) {
		        for (int j = i + 1; j < M - 1; j++) {
		          for (int k = j + 1; k < M; k++) {
		            curr[i][j][k] = findBest(y, i, j, k);
		          }
		        }
		      }
		      best = curr;
		    }
		
		    return best[M - 3][M - 2][M - 1];
		  }
		
		  int findBest(int row, int i, int j, int k) {
		    int curr = 0;
		
		    for (int ip = 0; ip < i + 1; ip++) {
		      for (int jp = i + 1; jp < j + 1; jp++) {
		        for (int kp = j + 1; kp < k + 1; kp++) {
		          curr = Math.max(curr, best[ip][jp][kp] + sums[row][ip][i] + sums[row][jp][j] + sums[row][kp][k]);
		        }
		      }
		    }
		
		    return curr;
		  }
		
		  void computeSums() {
		    for (int y = 0; y < N; y++) {
		      for (int i = 0; i < M; i++) {
		        for (int j = i; j < M; j++) {
		          sums[y][i][j] = sum(y, i, j);
		        }
		      }
		    }
		  }
		
		  int sum(int row, int start, int end) {
		    int res = 0;
		    for (int i = start; i < end + 1; i++) { res += data[row][i]; }
		    return res;
		  }
		
		  int sumAll() {
		    int res = 0;
		    for (int i = 0; i < N; i++) { res += sums[i][0][M - 1]; }
		    return res;
		  }
		  
		 
	
		 }
		 
		 public static void main(String [] args){
			  String [] level = {"0123456789",
					  "1123456789",
					  "2223456789",
					  "3333456789",
					  "4444456789",
					  "5555556789",
					  "6666666789",
					  "7777777789",
					  "8888888889",
					  "9999999999"};
			  System.out.println(new StarAdventure1().mostStars(level));
			 // System.out.println(new StarAdventure1().mostStars1(level));
			  
		  }
		
		  private void debug(Object... os) {
		    System.out.println(Arrays.deepToString(os));
		  }
}
