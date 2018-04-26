package alite.leetcode.xx4.finalLeft;

import java.util.Arrays;
import java.util.Comparator;

//import java.util.HashSet;
//
/////**
//// * https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
////Given a list of numbers, a[0], a[1], a[2], … , a[N-1], where 0 <= a[i] < 2^32. Find the maximum result of a[i] XOR a[j].
////Could you do this in O(n) runtime?
////
////Input: [3, 10, 5, 25, 2, 8]
////Output: 28
////
////X.  http://bookshadow.com/weblog/2016/10/15/leetcode-maximum-xor-of-two-numbers-in-an-array/
////解法I 分治法（Divide and Conquer）+ 位运算（Bit Manipulation）
////求数组中两两数字的异或运算的最大值，朴素的解法是O(n ^ 2)，不满足题目要求。
////异或运算（XOR）的实质是“同零异一”。
////题目描述中a[i]的范围[0, 2^32)，至多有32位，因此可以通过位运算处理。
////递归函数定义为：solve(nums0, nums1, mask)。
////
////令mask从最高位（1<<31）向最低位（1）逐步右移，将数组nums按照如下规则分成两部分：
////
////nums中，与mask按位与结果为0的所有数字，记为nums0
////
////nums中，与mask按位与结果为1的所有数字，记为nums1
////
////如果nums0与nums1其一为空，同0或同1，其异或结果一定为0
////说明nums中所有数字在该二进制位
////如果nums0与nums1均不为空，说明nums中的数字在该位的异或值为1，令mask右移一位，再将nums0与nums1进一步划分成四部分：
////
////nums0中，与mask按位与结果为0的所有数字，记为nums00
////
////nums0中，与mask按位与结果为1的所有数字，记为nums01
////
////nums1中，与mask按位与结果为0的所有数字，记为nums10
////
////nums1中，与mask按位与结果为1的所有数字，记为nums11
////
////分支1：如果nums00与nums11同时存在，递归求解solve(nums00, nums11, mask)的结果
////
////分支2：如果nums01与nums10同时存在，递归求解solve(nums01, nums10, mask)的结果
////
////从上述两个分支中求最大值ans
////
////若ans为0，说明上述两分支均不存在，nums中mask对应二进制位的所有数字同0或者同1，此时计算分支3
////
////分支3：递归计算solve(nums0, nums1, mask) - mask
////
////最后将结果+mask * 2，并返回
////    def findMaximumXOR(self, nums):
////        """
////        :type nums: List[int]
////        :rtype: int
////        """
////        def solve(nums0, nums1, mask):
////            if mask <= 1: return mask
////
////            mask /= 2
////            nums01 = [n for n in nums0 if n & mask]
////            nums00 = [n for n in nums0 if not n & mask]
////            nums11 = [n for n in nums1 if n & mask]
////            nums10 = [n for n in nums1 if not n & mask]
////
////            ans = 0
////            if nums10 and nums01:
////                ans = max(ans, solve(nums10, nums01, mask))
////            if nums00 and nums11:
////                ans = max(ans, solve(nums00, nums11, mask))
////            if not ans:
////                ans = solve(nums0, nums1, mask) - mask
////            return ans + mask * 2
////        mask = 1 << 31
////        while mask:
////            nums0 = [n for n in nums if not n & mask]
////            nums1 = [n for n in nums if n & mask]
////            if nums0 and nums1: break
////            mask >>= 1
////        return solve(nums0, nums1, mask)
////
////X.
////解法II Set数据结构 + 位运算
////https://discuss.leetcode.com/topic/63299/python-6-lines-bit-by-bit/2
////Build the answer bit by bit from left to right. Let's say we already know the largest first 
//seven bits we can create. How to find the largest first eight bits we can create? 
//		Well it's that maximal seven-bits prefix followed by 0 or 1. Append 0 and then
//		try to create the 1 one (i.e., answer ^ 1) from two eight-bits prefixes from nums. If we can, then change that 0 to 1.
////    int findMaximumXOR(vector<int>& nums) {
////        int res = 0;
////        unordered_set<int> pre;
////        for (int i = 31; i >= 0; i--) {
////            res <<= 1;
////            pre.clear();
////            for (auto n : nums) 
////                pre.insert(n >> i);
////            for (auto p : pre)
////                if (pre.find((res ^ 1) ^ p) != pre.end()) {
////                    res++;
////                    break;
////                }
////        }
////        return res;
////    }
////https://discuss.leetcode.com/topic/63213/java-o-n-solution-using-bit-manipulation-and-hashmap
////example: Given [14, 11, 7, 2], which in binary are [1110, 1011, 0111, 0010].
////Since the MSB is 3, I'll start from i = 3 to make it simplify.
////i = 3, set = {1000, 0000}, max = 1000
////i = 2, set = {1100, 1000, 0100, 0000}, max = 1100
////i = 1, set = {1110, 1010, 0110, 0010}, max = 1100
////i = 0, set = {1110, 1011, 0111, 0010}, max = 1100
////The mask is 1000, 1100, 1110, 1111.
////    public int findMaximumXOR(int[] nums) {
////        int max = 0, mask = 0;
////        for (int i = 31; i >= 0; i--) {
////            mask |= (1 << i);
////            HashSet<Integer> set = new HashSet<Integer>();
////            for (int num : nums) {
////                set.add(num & mask); // reserve Left bits and ignore Right bits
////            }
////            
////            /* Use 0 to keep the bit, 1 to find XOR
////             * 0 ^ 0 = 0 
////             * 0 ^ 1 = 1
////             * 1 ^ 0 = 1
////             * 1 ^ 1 = 0
////             */
////            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
////            for (int prefix : set) {
////                if (set.contains(tmp ^ prefix)) {
////                    max = tmp;
////                }
////            }
////        }
////        return max;
////    }
////I saw deeply and found out a very important xor feature I missed, that is: if a^b=c, then a^c=b, b^c=a. That's why the answer using this code:
////            for(int prefix : set){
////                if(set.contains(tmp ^ prefix)) {
////                    max = tmp;
////                    break;
////                }
////            }
////Agreed. Actually, this is the most important operation that made this code linear. Instead of choosing 2 from n, we verify if we can achieve maximum value -- 1 -- on each bit reversely based on the feature you have mentioned
////to iteratively determine what would be each bit of the final result from left to right. And it narrows down the candidate group iteration by iteration. e.g. assume input are a,b,c,d,...z, 26 integers in total. In first iteration, if you found that a, d, e, h, u differs on the MSB(most significant bit), so you are sure your final result's MSB is set. Now in second iteration, you try to see if among a, d, e, h, u there are at least two numbers make the 2nd MSB differs, if yes, then definitely, the 2nd MSB will be set in the final result. And maybe at this point the candidate group shinks from a,d,e,h,u to a, e, h. Implicitly, every iteration, you are narrowing down the candidate group, but you don't need to track how the group is shrinking, you only cares about the final result.
////    public int findMaximumXOR(int[] nums) {
////        int max = 0, mask = 0;
////        for(int i = 31; i >= 0; i--){
////            mask = mask | (1 << i);
////            Set<Integer> set = new HashSet<>();
////            for(int num : nums){
////                set.add(num & mask);
////            }
////            int tmp = max | (1 << i);
////            for(int prefix : set){
////                if(set.contains(tmp ^ prefix)) {
////                    max = tmp;
////                    break;
////                }
////            }
////        }
////        return max;
////    }
////解法III 字典树（Trie） + 位运算
////https://discuss.leetcode.com/topic/63207/java-o-n-solution-using-trie
////    class Trie {
////        Trie[] children;
////        public Trie() {
////            children = new Trie[2];
////        }
////    }
////    
////    public int findMaximumXOR(int[] nums) {
////        if(nums == null || nums.length == 0) {
////            return 0;
////        }
////        // Init Trie.
////        Trie root = new Trie();
////        for(int num: nums) {
////            Trie curNode = root;
////            for(int i = 31; i >= 0; i --) {
////                int curBit = (num >>> i) & 1;
////                if(curNode.children[curBit] == null) {
////                    curNode.children[curBit] = new Trie();
////                }
////                curNode = curNode.children[curBit];
////            }
////        }
////        int max = Integer.MIN_VALUE;
////        for(int num: nums) {
////            Trie curNode = root;
////            int curSum = 0;
////            for(int i = 31; i >= 0; i --) {
////                int curBit = (num >>> i) & 1;
////                if(curNode.children[curBit ^ 1] != null) {
////                    curSum += (1 << i);
////                    curNode = curNode.children[curBit ^ 1];
////                }else {
////                    curNode = curNode.children[curBit];
////                }
////            }
////            max = Math.max(curSum, max);
////        }
////        return max;
////    }
////It gets me TLE as well. Ditching the Trie class and just using Object[] gets it accepted in about 185 ms:
////    public int findMaximumXOR(int[] nums) {
////        if(nums == null || nums.length == 0) {
////            return 0;
////        }
////        // Init Trie.
////        Object[] root = {null, null};
////        for(int num: nums) {
////            Object[] curNode = root;
////            for(int i = 31; i >= 0; i --) {
////                int curBit = (num >>> i) & 1;
////                if(curNode[curBit] == null) {
////                    curNode[curBit] = new Object[]{null, null};
////                }
////                curNode = (Object[]) curNode[curBit];
////            }
////        }
////        int max = Integer.MIN_VALUE;
////        for(int num: nums) {
////            Object[] curNode = root;
////            int curSum = 0;
////            for(int i = 31; i >= 0; i --) {
////                int curBit = (num >>> i) & 1;
////                if(curNode[curBit ^ 1] != null) {
////                    curSum += (1 << i);
////                    curNode = (Object[]) curNode[curBit ^ 1];
////                }else {
////                    curNode = (Object[]) curNode[curBit];
////                }
////            }
////            max = Math.max(curSum, max);
////        }
////        return max;
////    }
////https://discuss.leetcode.com/topic/64753/31ms-o-n-java-solution-using-trie
////We add the number into the trie and find the max possible XOR result at the same time.
////Node.set() method will set the new node in the trie if needed and return the new node.
////After setting the node, find the opposite bit in the trie to maximize the possible XOR result.
////    public class Node {
////        Node one;
////        Node zero;
////        Node() {
////            this.one = null;
////            this.zero = null;
////        }
////        Node set(int n) {
////            if (n == 0) {
////                if (this.one == null) this.one = new Node();
////                return this.one;
////            }
////            if (this.zero == null) this.zero = new Node();
////            return this.zero;
////        }
////    }
////    
////    public int findMaximumXOR(int[] nums) {
////        Node root = new Node();
////        int re = 0;
////        for (int num : nums) {
////            int digit = num;
////            int tmp = 0;
////            Node setNode = root;
////            Node findNode = root;
////            int pos = 30;
////            while (pos >= 0) {
////                digit = (num >> pos) & 1;
////                setNode = setNode.set(digit);
////                if (digit == 1) {
////                    if (findNode.one != null) {
////                        tmp = tmp | (1 << pos);
////                        findNode = findNode.one;
////                    } else {
////                        findNode = findNode.zero;
////                    }
////                } else {
////                    if (findNode.zero != null) {
////                        tmp = tmp | (1 << pos);
////                        findNode = findNode.zero;
////                    } else {
////                        findNode = findNode.one;
////                    }
////                }
////                pos--;
////            }
////            re = Math.max(re, tmp);
////        }
////        return re;
////    }
//// * @author het
//// *
//// */
//public class LeetCode421MaximumXORofTwoNumbersinanArray {
//	public static int findMaximumXOR(int[] nums) {
//        int max = 0, mask = 0;
//        for (int i = 31; i >= 0; i--) {
//            mask |= (1 << i);
//            HashSet<Integer> set = new HashSet<Integer>();
//            for (int num : nums) {
//                set.add(num & mask); // reserve Left bits and ignore Right bits
//            }
//            
//            /* Use 0 to keep the bit, 1 to find XOR
//             * 0 ^ 0 = 0 
//             * 0 ^ 1 = 1
//             * 1 ^ 0 = 1
//             * 1 ^ 1 = 0
//             */
//            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
//            for (int prefix : set) {
//                if (set.contains(tmp ^ prefix)) {
//                    max = tmp;
//                }
//            }
//        }
//        return max;
//    }
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(findMaximumXOR(new int[]{3, 10, 5, 25, 2, 8}));
//
//	}
//	
////	example: Given [14, 11, 7, 2], which in binary are [1110, 1011, 0111, 0010].
////	Since the MSB is 3, I'll start from i = 3 to make it simplify.
////	i = 3, set = {1000, 0000}, max = 1000
////	i = 2, set = {1100, 1000, 0100, 0000}, max = 1100
////	i = 1, set = {1110, 1010, 0110, 0010}, max = 1100
////	i = 0, set = {1110, 1011, 0111, 0010}, max = 1100
////	The mask is 1000, 1100, 1110, 1111.
////	    public int findMaximumXOR(int[] nums) {
////	        int max = 0, mask = 0;
////	        for (int i = 31; i >= 0; i--) {
////	            mask |= (1 << i);
////	            HashSet<Integer> set = new HashSet<Integer>();
////	            for (int num : nums) {
////	                set.add(num & mask); // reserve Left bits and ignore Right bits
////	            }
////	            
////	            /* Use 0 to keep the bit, 1 to find XOR
////	             * 0 ^ 0 = 0 
////	             * 0 ^ 1 = 1
////	             * 1 ^ 0 = 1
////	             * 1 ^ 1 = 0
////	             */
////	            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
////	            for (int prefix : set) {
////	                if (set.contains(tmp ^ prefix)) {
////	                    max = tmp;
////	                }
////	            }
////	        }
////	        return max;
////	    }
////	I saw deeply and found out a very important xor feature I missed, that is: if a^b=c, then a^c=b, b^c=a. That's why the answer using this code:
////	            for(int prefix : set){
////	                if(set.contains(tmp ^ prefix)) {
////	                    max = tmp;
////	                    break;
////	                }
////	            }
////	Agreed. Actually, this is the most important operation that made this code linear. Instead of choosing 2 from n, we verify if we can achieve maximum value -- 1 -- on each bit reversely based on the feature you have mentioned
////	to iteratively determine what would be each bit of the final result from left to right. And it narrows down the candidate group iteration by iteration. e.g. assume input are a,b,c,d,...z, 26 integers in total. In first iteration, if you found that a, d, e, h, u differs on the MSB(most significant bit), so you are sure your final result's MSB is set. Now in second iteration, you try to see if among a, d, e, h, u there are at least two numbers make the 2nd MSB differs, if yes, then definitely, the 2nd MSB will be set in the final result. And maybe at this point the candidate group shinks from a,d,e,h,u to a, e, h. Implicitly, every iteration, you are narrowing down the candidate group, but you don't need to track how the group is shrinking, you only cares about the final result.
////	    public int findMaximumXOR(int[] nums) {
////	        int max = 0, mask = 0;
////	        for(int i = 31; i >= 0; i--){
////	            mask = mask | (1 << i);
////	            Set<Integer> set = new HashSet<>();
////	            for(int num : nums){
////	                set.add(num & mask);
////	            }
////	            int tmp = max | (1 << i);
////	            for(int prefix : set){
////	                if(set.contains(tmp ^ prefix)) {
////	                    max = tmp;
////	                    break;
////	                }
////	            }
////	        }
////	        return max;
////	    }

//Given a list of numbers, a[0], a[1], a[2], … , a[N-1], where 0 <= a[i] < 2^32. Find the maximum result of a[i] XOR a[j].
//////Could you do this in O(n) runtime?
//////
//////Input: [3, 10, 5, 25, 2, 8]
//////Output: 28               //a[]
public class LeetCode421MaximumXORofTwoNumbersinanArray {
	        static class BinaryTrie{
	        	private Node root ;
	        	static class Node{
	        		 // 1-> true,   0-> false;
	        		Node [] children = new Node[2];
	        		boolean isNumber;
	        		
	        		
	        		
	        		
	        	}
	        	
	            public void insert(long num){
	            	root = insert(root, num, 0);
	            }

				private Node insert(Node root, long num, int startDigit) {
					
					if(root == null){
						root = new Node();
					}
                     if(startDigit == 32) {
						root.isNumber = true;
						return root;
					}
					int index =(int) (num >> (31 -  startDigit)) & 1;
					root.children[index] = insert(root.children[index], num, startDigit+1);
					return root;
					
				}
				
				public Interval findFirstMatchDepth(long num){
					Interval interval = new Interval();
					 findFirstMatchDepth(root, num, 0, interval);
					 return interval;
				}
				
				static class Interval{
					Integer startDigit ;
					Integer length ;
					Interval(Integer startDigit, Integer length){
						this.startDigit= startDigit;
						this.length = length;
					}
					Interval(){
						
					}
					
					public String toString(){
						return startDigit+","+length;
					}
				}
				// 0 1  2   31   -1
private void findFirstMatchDepth(Node node, long num, int startDigit, Interval interval) {
					if(node == null) {
						return;
						
					}
					int index =(int) (num >> (31 -  startDigit)) & 1;
					if(node.children[index]!=null){
						if(interval.startDigit == null){
							interval.startDigit = startDigit;
							interval.length = 1;
						}else{
							interval.length+=1;
						}
						findFirstMatchDepth(node.children[index], num, startDigit+1, interval);
						
					}else{
						if(interval.startDigit == null){
							
							if(node.children[1-index] ==null){
								return;
							}else{
								findFirstMatchDepth(node.children[1-index], num, startDigit+1, interval);
							}
							
						}else{
							return;
						}
					}
					
					
				}



//private void findFirstMatchDepth(Node node, long num, int startDigit, Interval interval) {
//	if(node == null) {
//		return;
//		
//	}
//	int index =(int) (num >> (31 -  startDigit)) & 1;
//	if(node.children[index]!=null){
//		if(interval.startDigit == null){
//			interval.startDigit = startDigit;
//			interval.length = 1;
//		}else{
//			interval.length+=1;
//		}
//		findFirstMatchDepth(node.children[index], num, startDigit+1, interval);
//		
//	}else{
//		if(interval.startDigit == null){
//			
//			if(node.children[1-index] ==null){
//				return;
//			}else{
//				findFirstMatchDepth(node.children[1-index], num, startDigit+1, interval);
//			}
//			
//		}else{
//			return;
//		}
//	}
//	
//	
//}

//				public int matchDepth(long num){
//					
//				}

//				private int matchDepth(Node root, long num, ) {
//					int index =(int) (num >> (31 -  startDigit)) & 1;
//					return 0;
//				}
//	      =
	        
	  //////Input: [3, 10, 5, 25, 2, 8]
	  //////Output: 28               //a[]
        	public static long findMaximumXOR(long[] nums) {
        		BinaryTrie trie = new BinaryTrie();
        		for(long num: nums){
        			trie.insert(num);
        			
        		}
        		Interval []  intervals = new Interval[nums.length];
        		for(int i=0;i<nums.length;i+=1){
        			intervals[i] = trie.findFirstMatchDepth(~nums[i]);
        		}
        		Arrays.sort(intervals, new Comparator<Interval>(){

					@Override
					public int compare(Interval i1, Interval i2) {
					    if(i1.startDigit !=null && i2.startDigit == null) return -1;
					    if(i2.startDigit !=null && i1.startDigit == null) return 1;
					    if(i2.startDigit ==null && i1.startDigit == null) return 0;
						if(i1.startDigit.equals(i2.startDigit) ) return i2.length.compareTo(i1.length);
						return i1.startDigit.compareTo(i2.startDigit);
					}
        			
        		});
        		Interval first = intervals[0];
				System.out.println(first);
        		int result = 0;
        		for(int i=0;i<first.length;i+=1){
        			result = result | (1<<(31-first.startDigit-i));
        		}
        		return result;
        	}
        	
        	public static void main(String [] args){
        		long[] nums = new long[]{3, 10, 5, 25, 2, 8};
				System.out.println(findMaximumXOR(nums));
				for(int i=0;i<nums.length-1;i+=1){
					for(int j=i+1;j<nums.length;j+=1){
						System.out.println(nums[i] ^nums[j]);
					}
				}
//        		BinaryTrie trie = new BinaryTrie();
//        		trie.insert(-1);4+8+16
//        		//trie.insert(16);
//        		trie.insert(-2);
//        		trie.insert(-3);
//        		System.out.println(trie.findFirstMatchDepth(16+8+4));
        	        	}
        	
        	
	        }
	        

}
