package important.backtrack;
/**
 * Find length of the longest consecutive path from a given starting character
Given a matrix of characters. Find length of the longest path from a given character, such that all characters in the path are consecutive to each other, i.e., every character in path is next to previous in alphabetical order. It is allowed to move in all 8 directions from a cell.
￼
Example
Input: mat[][] = { {a, c, d},
                   {h, b, e},
                   {i, g, f}}
      Starting Point = 'e'

Output: 5
If starting point is 'e', then longest path with consecutive 
characters is "e f g h i".

Input: mat[R][C] = { {b, e, f},
                     {h, d, a},
                     {i, c, a}};
      Starting Point = 'b'

Output: 1
'c' is not present in all adjacent cells of 'b'
 * @author het
 *
 */
public class LongestConsecutivePath {
    private int[] dx = {-1, 0, 1};
    private int[] dy = {-1, 0, 1};
	public int longConsecutivePath(char mat[][]){
		if(mat == null || mat.length == 0 || mat[0] == null || mat[0].length ==0 ) return 0;
		//int [] longest = new int[1];
		int longest = 1;
		for(int i=0;i<mat.length;i+=1){
			for(int j=0;j<mat[0].length;j+=1){
				longest = Math.max(longest, longConsecutivePath(mat, new Integer[mat.length][mat[0].length], i, j));
			}
		}
		return longest;
		
	}

	private int longConsecutivePath(char[][] mat,
			Integer[][] cache, int i, int j) {
		int height  = cache.length;
		int length = cache[0].length;
		if(i <0 || j<0 || i >= height|| j>= length){
			return 0;
		}
		if(cache[i][j] != null){
			return cache[i][j];
		}
		Integer currentLength = 1;
		for(int changeX: dx){
			for(int changeY: dy){
				if((changeX != 0 || changeY !=0) && i+changeY >=0 && i+changeY< height && j+changeX>=0 && j+changeX<length){
					currentLength =Math.max(currentLength, (mat[i][j] - mat[i+changeY][j+changeX] != -1)? 1 : (1+ longConsecutivePath(mat, cache, i+changeY, j+changeX)));
					
				}
				
			}
		}
		
		cache[i][j] = currentLength;
		
		return currentLength;
		
	
	}
	
	public static void main(String [] args){
		char mat[][] = { {'a', 'c', 'd'},
                {'h', 'b', 'e'},
                {'i', 'g', 'f'}};
		System.out.println(new LongestConsecutivePath().longConsecutivePath(mat, new Integer[mat.length][mat[0].length], 1, 2));
	}
}
