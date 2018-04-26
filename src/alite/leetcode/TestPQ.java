package alite.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestPQ {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxChoosableInteger = 20;
		PriorityQueue<Integer> pg = new PriorityQueue<Integer>(maxChoosableInteger, new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				
				return (-1)* o1.compareTo(o2);
			}});
		for(int i=1; i<=maxChoosableInteger; i+=1){
			pg.add(i);
		}
		for(int i=1; i<=maxChoosableInteger; i+=1){
			System.out.println(pg.poll());
		}

	}

}
