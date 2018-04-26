package important.backtrack;

import java.util.HashMap;
import java.util.Map;

/**
 * How to print maximum number of A’s using given four keys
This is a famous interview question asked in Google, Paytm and many other company interviews.
Below is the problem statement.
Imagine you have a special keyboard with the following keys: 
Key 1:  Prints 'A' on screen
Key 2: (Ctrl-A): Select screen
Key 3: (Ctrl-C): Copy selection to buffer
Key 4: (Ctrl-V): Print buffer on screen appending it
                 after what has already been printed. 

If you can only press the keyboard for N times (with the above four
keys), write a program to produce maximum numbers of A's. That is to
say, the input parameter is N (No. of keys that you can press), the 
output is M (No. of As that you can produce).
Examples:
Input:  N = 3
Output: 3
We can at most get 3 A's on screen by pressing 
following key sequence.
A, A, A

Input:  N = 7
Output: 9
We can at most get 9 A's on screen by pressing 
following key sequence.
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V

Input:  N = 11
Output: 27
We can at most get 27 A's on screen by pressing 
following key sequence.
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V, Ctrl A, 
Ctrl C, Ctrl V, Ctrl V
 * @author het
 *
 */
public class MaxPrint {
	// O(4* n * numberOnScreen* numberInBuffer* numberOfSelected)
    public int maxPrint(int N){
    	int numberOnScreen = 0 ; int numberInBuffer = 0;int numberOfSelected= 0;
    	return maxPrint(1, numberOfSelected, numberOnScreen,  numberInBuffer,   N, new HashMap<String, Integer>());
    }
    
    private String createKey(int i, int numberOfSelected, int numberOnScreen, int numberInBuffer){
    	return i+","+numberOfSelected+","+numberOnScreen+","+numberInBuffer;
    }
	private int maxPrint(int i,int numberOfSelected,  int numberOnScreen, int numberInBuffer, int n, Map<String, Integer> cache) {
		if(i == n+1) return 0;
	    String key = createKey(i, numberOfSelected, numberOnScreen, numberInBuffer);
	    if(cache.containsKey(key)){
	    	return cache.get(key);
	    }
	    int maxPrint = 0;
	    maxPrint = Math.max(maxPrint, 1+ maxPrint(i+1,numberOfSelected,  numberOnScreen+1, numberInBuffer, n, cache));
	    maxPrint = Math.max(maxPrint, maxPrint(i+1, numberOnScreen, numberOnScreen, numberInBuffer, n, cache));
	    maxPrint = Math.max(maxPrint, maxPrint(i+1, numberOfSelected, numberOnScreen, numberOfSelected, n, cache));
	    maxPrint = Math.max(maxPrint, numberInBuffer+maxPrint(i+1, numberOfSelected, numberOnScreen+numberInBuffer, numberInBuffer, n , cache));
	    cache.put(key, maxPrint);
	    return maxPrint;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new MaxPrint().maxPrint(20));

	}

}
