package alite.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alite.leetcode.Multi_Search.Trie.TrieNode;

public  class Multi_Search {
	public static Trie createTreeFromStrings(String[] smalls, int maxSize) {
		   Trie tree = new Trie();
		 for (String s : smalls) {
		  if (s.length() <= maxSize) {
		   tree.insertString(s, 0);
		  }
		 }
		 return tree;
		}

		public static ArrayList<String> findStringsAtLoc(TrieNode root, String big, int start) {
		 ArrayList<String> strings = new ArrayList<String>();
		 int index = start;
		 while (index < big.length()) {
		  root = root.getChild(big.charAt(index));
		  if (root == null) break;
		  if (root.terminates()) {
		   strings.add(big.substring(start, index + 1));
		  }
		  index++;
		  
		 }
		 return strings;
		}

		public static void insertIntoHashMap(ArrayList<String> strings, Map<String, Integer> map, int index) {
		 for (String s : strings) {
		  map.put(s, index);
		 }
		}

//		public static Map<String, Integer> searchAll(String big, String[] smalls) {
//		 Map<String, Integer> lookup = new HashMap<String, Integer>();
//		 TrieNode root = createTreeFromStrings(smalls, big.length()).getRoot();
//		 
//		 for (int i = 0; i < big.length(); i++) {
//		  ArrayList<String> strings = findStringsAtLoc(root, big, i);
//		  insertIntoHashMap(strings, lookup, i);
//		 }
//		 
//		 return lookup;
//		} 

		public static void subtractValue(ArrayList<Integer> locations, int delta) {
		 if (locations == null) return;
		 for (int i = 0; i < locations.size(); i++) {
		  locations.set(i, locations.get(i) - delta);
		 }
		}

		public static Trie createTrieFromString(String s) {
		 Trie trie = new Trie();
		 for (int i = 0; i < s.length(); i++) {
		   String suffix = s.substring(i);
		   trie.insertString(suffix, i);
		 }
		 return trie;
		}

		public static Map<String, List<Integer>> searchAll(String big, String[] smalls) {
			Map<String,  List<Integer>> lookup = new HashMap<>();
		 Trie tree = createTrieFromString(big);
		 for (String s : smalls) {
		  /* Get terminating location of each occurrence.*/
		    ArrayList<Integer> locations = tree.search(s);
		    
		    /* Adjust to starting location. */
		    subtractValue(locations, s.length());
		    
		    /* Insert. */
		    lookup.put(s, locations);
		 }
		 
		 return lookup;
		} 
		public static class Trie {
		 private TrieNode root = new TrieNode();
		 
		 public ArrayList<Integer> search(String s) {
		  return root.search(s);
		 }
		 
		 public void insertString(String str, int location) {
		  root.insertString(str, location);
		 }
		 
		 public TrieNode getRoot() {
		  return root;
		 }

		 public static  class TrieNode {
		  private HashMap<Character, TrieNode> children;
		  private ArrayList<Integer> indexes;
		  
		  public TrieNode() { 
		   children = new HashMap<Character, TrieNode>();
		   indexes = new ArrayList<Integer>();
		  }
		  
		  
		  public void insertString(String s, int index) {
		   if (s == null) return;
		   indexes.add(index);
		   if (s.length() > 0) {
		    char value = s.charAt(0);
		    TrieNode child = null;
		    if (children.containsKey(value)) {
		     child = children.get(value);
		    } else {
		     child = new TrieNode();
		     children.put(value, child);
		    }
		    String remainder = s.substring(1);
		    child.insertString(remainder, index + 1);
		   } else {
		    children.put('\0', null);   //children.put('\0', null);
		   }
		  }
		  
		  public ArrayList<Integer> search(String s) {
		   if (s == null || s.length() == 0) {
		    return indexes;
		   } else {
		    char first = s.charAt(0);
		    if (children.containsKey(first)) {
		     String remainder = s.substring(1);
		     return children.get(first).search(remainder);
		    }
		   }
		   return null;
		  }
		  
		  public boolean terminates() {
		   return children.containsKey('\0');
		  }
		  
		  public TrieNode getChild(char c) {
		   return children.get(c);
		  }
		 }


		public static boolean isSubstringAtLocation(String big, String small, int offset) {
		  for (int i = 0; i < small.length(); i++) {
		    if (big.charAt(offset + i) != small.charAt(i)) {
		      return false;
		    }
		  }
		  return true;
		}

		public static ArrayList<Integer> search(String big, String small) {
		  ArrayList<Integer> locations = new ArrayList<Integer>();
		  for (int i = 0; i < big.length() - small.length() + 1; i++) {
		    if (isSubstringAtLocation(big, small, i)) {
		      locations.add(i);
		    }
		  }
		  return locations;
		}

		public static Map<String, List<Integer>> searchAll(String big, String[] smalls) {
			Map<String, List<Integer>> lookup = new HashMap<>();
		  for (String small : smalls) {
		    List<Integer> locations = search(big, small);
		    lookup.put(small, locations);
		  }
		  return lookup;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, List<Integer>> result = Multi_Search.searchAll("abcdefgcdcd", new String[]{"de", "cd"});
		System.out.println(result);
	}
		}

}
