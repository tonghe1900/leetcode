package alite.leetcode.newExtra.finished;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapping
'1' = 'A','B','C'
'2' = 'D','E','F'
...
'9' =

input: 112
output :ouput = [AAD, BBD, CCD, AAE, AAF, BBE, BBF, CCE, CCF]
 * @author het
 *
 */
public class T {
	
	 private static Map<Character, List<Character>> getMapping(){
		 Map<Character, List<Character>> result = new HashMap<>();
		 for(char c = '1'; c<='9'; c+=1){
			 char start = (char)('A' + (c - '1')*3);
			 if(!result.containsKey(c)){
				 result.put(c, new ArrayList<Character>());
			 }
			 List<Character> list = result.get(c);
			 for(char m = start; m<='Z'&& m<= start +2; m+=1){
				 list.add(m);
			 }
			 
		 }
		 return result;
	 }
     public static List<String> getMapping(String input, Map<Character, List<Character>> mapping){
    	 List<String> result = new ArrayList<>();
    	 if(input == null || input.length() == 0) return result;
    	 getMapping(result, input, mapping, 0, input.length(), new StringBuilder());
    	 return result;
     }

	private static void getMapping(List<String> result, String input,
			Map<Character, List<Character>> mapping, int start, int length, StringBuilder sb) {
		if(sb.length() == length){
			result.add(sb.toString());
			return;
		}
		List<Character> mappingChars = mapping.get(input.charAt(start));
		
		//List<Character> chars =mappingChars.size() > 1? new ArrayList<>(mappingChars) : mappingChars;
		for(Character character: mappingChars){
			int lengthForSb = sb.length();
			if(mappingChars.size() > 1){
				mapping.put(input.charAt(start), Arrays.asList(character));
			}
			
			 getMapping(result, input, mapping, start+1, length, sb.append(character));
			 sb.setLength(lengthForSb);
			 mapping.put(input.charAt(start), mappingChars);
		}
		
	}
	
	public static void main(String [] args){
		System.out.println(getMapping());
		System.out.println(getMapping("11244",getMapping()));
	}
}
