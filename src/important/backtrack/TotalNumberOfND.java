package important.backtrack;
/**
 * Total number of non-decreasing numbers with n digits
A number is non-decreasing if every digit (except the first one) is greater than or equal to previous digit. For example, 223, 4455567, 899, are non-decreasing numbers.
So, given the number of digits n, you are required to find the count of total non-decreasing numbers with n digits.
Examples:
Input:  n = 1
Output: count  = 10

Input:  n = 2
Output: count  = 55

Input:  n = 3


Output: count  = 220
 * @author het
 *
 */
//  totalNumber(i, j)    the total Number for index from 0 to i and the ith digit is j
//
//     totalNumber(i+1)  =totalNumber(i+1, 0)+ totalNumber(i+1, 1) + totalNumber(i+1,2) +...+totalNumber(i+1, 9) 
  //                  =   totalNumber(i, 0) +.. totalNumber(i, 9) +
//                           totalNumber(i, 1) + totalNumber(i, 9)+
//                            totalNumber(i,2) +  totalNumber(i, 9)+
//                            
//                            
//                            totalNumber(i, 9)
                            
                            

// i+1< length-1:    totalNumber(i+1) = 10*  totalNumber(i, 9) + 9*  totalNumber(i, 8) + 8 * totalNumber(i, 7)+
     //                       1* totalNumber(i, 0)
// i+1 == length-1    
public class TotalNumberOfND {
    public static int totalNumberOfND(int n){
        if(n <=0) return 0;
        if(n == 1) return 10;
        int [] previous = new int[10];
        for(int i=0;i<previous.length;i+=1){
        	previous[i] = 1;
        }
        int [] current = new int[10];
        for(int digit = 1; digit<n; digit+=1){
        	for(int choosenNumber = 0; choosenNumber<=9;choosenNumber+=1){
        		current[choosenNumber] = 0;
        		for(int previousNumber = 9; previousNumber >= choosenNumber; previousNumber-=1){
        			
        			current[choosenNumber] += previous[previousNumber];
        		}
        	}
        	for(int i=0;i<previous.length;i+=1){
            	previous[i] = current[i];
            }
        }
        int result = 0;
        for(int i=0;i<10;i+=1){
        	result+= current[i];
        }
        return result;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(totalNumberOfND(3));

	}

}
