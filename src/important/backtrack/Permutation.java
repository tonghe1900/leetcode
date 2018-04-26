package important.backtrack;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
    public List<String> permutate(String input){
    	List<String> list = new ArrayList<>();
    	permutate( list, input.toCharArray(), 0, input.length() - 1);
    	return list;
    }
	private void permutate(List<String> list, char[] input, int start, int end) {
		if(start == end){
			list.add(new String(input));
			return;
		}
		for(int i =  start;i<=end;i+=1){
			swap(input, start, i);
			permutate(list, input, start+1, end);
			swap(input, i, start);
		}
		
		
		
	}
	private void swap(char[] input, int i, int j) {
		char temp = input[i];
		input[i] =  input[j];
		input[j] = temp;
		
		
	}
	public static void main(String[] args) {
		System.out.println(new Permutation().permutate("ABC"));
		System.out.println(new Permutation().permutate("AAA"));

	}

}
