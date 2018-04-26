package important.backtrack;
/**
 * Shortest Common Supersequence
Given two strings str1 and str2, find the shortest string that has both str1 and str2 as subsequences.
Examples:
Input:   str1 = "geek",  str2 = "eke"
Output: "geeke"


AGGTAB
GXTXAYB
   G  X
AG
GA

AGXGTXAYB

Input:   str1 = "AGGTAB",  str2 = "GXTXAYB"
Output:  "AGXGTXAYB"
 * @author het
 *
 */
public class ShortestCommonSubsequence {
    
	public String shorest(String str1, String str2){
		if((str1 == null|| str1.length() == 0) && (str2 == null|| str2.length() == 0)) return "";
		if ((str1 == null|| str1.length() == 0) ||  (str2 == null|| str2.length() == 0)) return (str1 == null|| str1.length() == 0) ? str2:str1;
		return shorest(str1, 0, str2, 0, new String[str1.length()][str2.length()]);
	}
	
	private String shorest(String str1, int index1, String str2, int index2, String[][] cache) {
		if(index1 == str1.length() && index2 == str2.length() ) return "";
		if(index1 == str1.length() || index2 == str2.length() )  return index1 == str1.length()? str2.substring(index2):str1.substring(index1);
		if(cache[index1][index2] != null) return cache[index1][index2];
		
		char char1 = str1.charAt(index1);
		char char2 = str2.charAt(index2);
		String subResult1 = shorest(str1, index1+1, str2, index2, cache);
		String subResult2 = shorest(str1, index1, str2, index2+1, cache);
		String result1 = getResult(char1, subResult1);
		String result2 = getResult(char2, subResult2);
		String result = result1.length() < result2.length()? result1: result2;
		cache[index1][index2] = result;
		return result;
	}
//3
	private String getResult(char c, String subResult) {
		if(subResult.isEmpty()){
			return ""+c;
		}
		
		return c == subResult.charAt(0)? subResult: c+subResult;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new ShortestCommonSubsequence().shorest("AGGTAB",  "GXTXAYB"));
		
		

	}

}
