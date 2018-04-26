import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class StripePainter {
	
	// 26 colors    1 - stripes.length()  // get the wrong color sections
	// minStrokes(stripes, left, right ) = Sum( minStrokes(stripes, LeftForOneSection, rightForOneSection)) + 1
	//  for the length of stroke in 1 .. stripes.length()    the color occurs most in the section stroked
	//
	public int minStrokes(String stripes){
		return minStrokes(stripes, 0, stripes.length()-1, new HashMap<String,Integer>());
	}
	private String createKey(int left, int right){
		return left+","+right;
	}
	
	class Boundary{
		int left;
		int right;
		public Boundary(int left, int right) {
			super();
			this.left = left;
			this.right = right;
		}
		
	}
	
	private int minStrokes(String stripes, int left, int right, Map<String, Integer> cache){
		if(left > right) return 0;
		if(left == right) return 1;
		String key = createKey(left, right);
		if(cache.containsKey(key)) return cache.get(key);
		int minStrokes = right - left +1;
		for(int strokeLength = minStrokes; strokeLength>=1; strokeLength+=1){
			Set<Character> colors = getColorChoice(stripes, left, left+strokeLength-1);
			
			Map<Character, List<Boundary>> map = getWrongColorSections(colors, stripes, left, left+strokeLength-1);
			int currentStrokeNum = 1+ minStrokes(stripes, left+strokeLength, right, cache) ;
			for(Character color: colors){
				
				List<Boundary> list = map.get(color);
				for(Boundary boundary : list){
					currentStrokeNum += minStrokes(stripes, boundary.left, boundary.right , cache);
				}
				minStrokes = Math.min(minStrokes, currentStrokeNum );
			}
		}
		
		cache.put(key, minStrokes);
		return minStrokes;
		
		
		
	}

	private Map<Character, List<Boundary>> getWrongColorSections(Set<Character> colors,
			String stripes, int left, int right) {
		Map<Character,  List<Boundary>> map = new HashMap<>();
		
		for(Character color: colors){
			
		}
		return null;
	}
	private Set<Character> getColorChoice(String stripes, int left, int right) {
		Set<Character> set = new HashSet<>();
		for(int i= left; i<=right; i+=1){
			set.add(stripes.charAt(i));
		}
		
		return set;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
