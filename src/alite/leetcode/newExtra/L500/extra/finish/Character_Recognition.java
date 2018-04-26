package alite.leetcode.newExtra.L500.extra.finish;
/**
 * Character recognition

http://wangsc.ga/posts/20170224-interview-char-rec/
Character recognition is the conversion of images into text. For now we consider each character in the picture
 is a N*M matrix with only zeros and ones, and we need to recognize K characters. You are to write a program 
 to find minimal number of pixels so that we can recognize each character.

For example, we have only two characters T and L, and the matrix size is 3*3, we can think T and L are

1 1 1  1 0 0


0 1 0  1 0 0


0 1 0  1 1 1

So we can recognize the character with only botton-left pixel, the anwser is 1;
Limits

Memory limit per test: 256 megabytes
Time limit per test: The faster the better
Compile & Environment

C++

g++ Main.cc –o Main –fno-asm –Wall –lm –static –std=c++0x –DONLINE_JUDGE

Java

J2SE8 


Maximum stack size is 50m

Input

The first line of input is three integers N, M, K (1 <= N, M <=10, 2 <= K <= 6).
Which represents the size of matrix and number of characters. Then is following K blocks, which represents the matrix. Notice that each block starts with a blank line
Output

You should output the minimum number of pixels, which is the anwser.
Test Case


input


1 3 3


1 1 0


0 0 1


1 0 1




output


2




input


3 3 2


1 1 1


0 1 0


0 1 0


1 0 0


1 0 0


1 1 1


output


1

Generalize problem

Given K bit serie with same length (N*M), find the minimum bits required to distinguish them.

input


2


101110


010010


3


11110


11001


11101


4


111111


010101


000101


110111


5


0000101101


0000101010


1001010011


1001010110


0001111101


output


1


2


3


?



    /**


     * return min min: number of indices required for distinguish series


     */


//    private static int minDigit(List<char[]> series, int k, int l) {
//
//
//        // store all the subsets of {0,..., l-1}
//
//
//        Set<List<Integer>> subsets = new HashSet<List<Integer>>();
//
//
//        // init .. empty set is not included
//
//
//        subsets = getSubsets(l);
//
//
//
//
//        int min = l;
//
//
//
//
//        for (List<Integer> list : subsets) {
//
//
//            // if longer than minimum length
//
//
//            // we don't need to consider
//
//
//            if (list.size() > min)
//
//
//                continue;
//
//
//
//
//            if (isValidIndex(list, series)) {
//
//
//                min = list.size();
//
//
//            }
//
//
//        }
//
//
//
//
//        return min;
//
//
//    }
//
//
//
//
//    /**
//
//
//     * validation of indices
//
//
//     */
//
//
//    private static boolean isValidIndex(List<Integer> list, List<char[]> series) {
//
//
//        Set<String> set = new HashSet<String>();
//
//
//        for (char[] arr : series) {
//
//
//            String tmp = "";
//
//
//            for (int i = 0; i < list.size(); i++) {
//
//
//                tmp = tmp + arr[list.get(i)];
//
//
//            }
//
//
//            // if the combination exist in the set
//
//
//            // means the list of index is not valid
//
//
//            if (set.contains(tmp)) {
//
//
//                return false;
//
//
//            } else {
//
//
//                set.add(tmp);
//
//
//            }
//
//
//        }
//
//
//
//
//        return true;
//
//
//    }
//
//
//
//
//    /**
//
//
//     * get subsets
//
//
//     */
//
//
//    private static Set<List<Integer>> getSubsets(int num) {
//
//
//        Set<List<Integer>> subsets = new HashSet<List<Integer>>();
//
//
//        List<Integer> ls = null;
//
//
//
//
//        int ind = 0;
//
//
//
//
//        // i start from 1, because, if i start from 0
//
//
//        // an empty set will be generate.
//
//
//        for (int i = 1; i < Math.pow(2, num); i++) {
//
//
//            // init
//
//
//            int tmp = i;
//
//
//            ind = 0;
//
//
//            ls = new ArrayList<Integer>();
//
//
//            while (tmp != 0) {
//
//
//                if ((tmp & 1) == 1) {
//
//
//                    ls.add(ind);
//
//
//                }
//
//
//                tmp = tmp >> 1;
//
//
//                ind++;
//
//
//            }
//
//
//            subsets.add(ls);
//
//
//        }
//
//
//
//
//        return subsets;
//
//
//    }
//
//
//http://www.voidcn.com/blog/woainiwss/article/p-6022583.html
// * @author het
// *
// */
//public class Character_Recognition {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
