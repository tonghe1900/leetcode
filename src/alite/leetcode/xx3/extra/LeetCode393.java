package alite.leetcode.xx3.extra;
///**
// * LeetCode 393 - UTF-8 Validation
//
//https://leetcode.com/problems/utf-8-validation/
//A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
//For 1-byte character, the first bit is a 0, followed by its unicode code.
//For n-bytes character, the first n-bits are all one's, the n+1 bit is 0, followed by n-1 bytes with most significant 2 bits being 10.
//This is how the UTF-8 encoding would work:
//   Char. number range  |        UTF-8 octet sequence
//      (hexadecimal)    |              (binary)
//   --------------------+---------------------------------------------
//   0000 0000-0000 007F | 0xxxxxxx
//   0000 0080-0000 07FF | 110xxxxx 10xxxxxx
//   0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
//   0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
//Given an array of integers representing the data, return whether it is a valid utf-8 encoding.
//Note:
//The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.
//Example 1:
//data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.
//
//Return true.
//It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
//Example 2:
//data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.
//
//Return false.
//The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
//The next byte is a continuation byte which starts with 10 and that's correct.
//But the second continuation byte does not start with 10, so it is invalid.
//http://www.cnblogs.com/grandyang/p/5847597.html
//这种方法也是要记连续1的个数，如果是标识字节，先将其向右平移五位，如果得到110，则说明后面跟了一个字节，否则向右平移四位，如果得到1110，则说明后面跟了两个字节，否则向右平移三位，如果得到11110，则说明后面跟了三个字节，否则向右平移七位，如果为1的话，说明是10000000这种情况，不能当标识字节，直接返回false。在非标识字节中，向右平移六位，如果得到的不是10，则说明不是以10开头的，直接返回false，否则cnt自减1，成功完成遍历返回true
//
//    bool validUtf8(vector<int>& data) {
//        int cnt = 0;
//        for (int d : data) {
//            if (cnt == 0) {
//                if ((d >> 5) == 0b110) cnt = 1;
//                else if ((d >> 4) == 0b1110) cnt = 2;
//                else if ((d >> 3) == 0b11110) cnt = 3;
//                else if (d >> 7) return false;
//            } else {
//                if ((d >> 6) != 0b10) return false;
//                --cnt;
//            }
//        }
//        return cnt == 0;
//    }
//http://blog.csdn.net/mebiuw/article/details/52445248
//而解题的核心在于： 
//1、按照规则，识别byte应该有几位 
//2、如果是2-4bytes的数据，继续检查后面的数据是否10开头再返回1，而是1byte则直接返回
//    public boolean validUtf8(int[] data) {
//        int n = data.length;
//        int skip = 0b10000000;
//        int check = 0;
//        for (int i = 0; i < data.length; i++) {
//            if (check > 0) {
//                if ((data[i] & skip) == skip) 
//                    check--;
//                else 
//                    return false;
//            } else {
//                check = getHeadType(data[i]);
//                if (check < 0) return false;
//            }
//        }
//        return check == 0;
//    }
//    /**
//     * 检查*/
//    public int getHeadType(int num) {
//        if ((num & 0b11110000) == 0b11110000) return 3;
//        if ((num & 0b11100000) == 0b11100000) return 2;
//        if ((num & 0b11000000) == 0b11000000) return 1;
//        if ((num & 0b10000000) == 0b10000000) return -1; //error
//        return 0;
//    }
//
//http://bookshadow.com/weblog/2016/09/04/leetcode-utf-8-validation/
//    def validUtf8(self, data):
//        """
//        :type data: List[int]
//        :rtype: bool
//        """
//        masks = [0x0, 0x80, 0xE0, 0xF0, 0xF8]
//        bits = [0x0, 0x0, 0xC0, 0xE0, 0xF0]
//        while data:
//            for x in (4, 3, 2, 1, 0):
//                if data[0] & masks[x] == bits[x]:
//                    break
//            if x == 0 or len(data) < x:
//                return False
//            for y in range(1, x):
//                if data[y] & 0xC0 != 0x80:
//                    return False
//            data = data[x:]
//        return True
// * @author het
// *
// */
public class LeetCode393 {
//	http://blog.csdn.net/mebiuw/article/details/52445248
//	而解题的核心在于： 
//	1、按照规则，识别byte应该有几位 
//	2、如果是2-4bytes的数据，继续检查后面的数据是否10开头再返回1，而是1byte则直接返回
	
//	Given an array of integers representing the data, return whether it is a valid utf-8 encoding.
//			//Note:
//			//The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.
//			//Example 1:
//			//data = [197, 130, 1], which represents the octet sequence: 11000101 10000010 00000001.
//			//
//			//Return true.
//			//It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
//			//Example 2:
//			//data = [235, 140, 4], which represented the octet sequence: 11101011 10001100 00000100.
//			//
//			//Return false.
//			//The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
//			//The next byte is a continuation byte which starts with 10 and that's correct.
//			//But the second continuation byte does not start with 10, so it is invalid.
//			//http://www.cnblogs.com/grandyang/p/5847597.html
//			//这种方法也是要记连续1的个数，如果是标识字节，先将其向右平移五位，如果得到110，则说明后面跟了一个字节，否则向右平移四位，如果得到1110，则说明后面跟了两个字节，否则向右平移三位，如果得到11110，则说明后面跟了三个字节，否则向右平移七位，如果为1的话，说明是10000000这种情况，不能当标识字节，直接返回false。在非标识字节中，向右平移六位，如果得到的不是10，则说明不是以10开头的，直接返回false，否则cnt自减1，成功完成遍历返回true
//			//
	//   0000 0000-0000 007F | 0xxxxxxx
//  0000 0080-0000 07FF | 110xxxxx 10xxxxxx
//  0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
//  0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
	    public static boolean validUtf8(int[] data) {
	        int n = data.length;
	        int skip = 0b10000000;
	        int check = 0;
	        for (int i = 0; i < data.length; i++) {
	            if (check > 0) {
	                if ((data[i] & skip) == skip) 
	                    check--;
	                else 
	                    return false;
	            } else {
	                check = getHeadType(data[i]);
	                if (check < 0) return false;
	            }
	        }
	        return check == 0;
	    }
	    /**
	     * 检查*/
	    public static int getHeadType(int num) {
	        if ((num & 0b11110000) == 0b11110000) return 3;
	        if ((num & 0b11100000) == 0b11100000) return 2;
	        if ((num & 0b11000000) == 0b11000000) return 1;
	        if ((num & 0b10000000) == 0b10000000) return -1; //error
	        return 0;
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i= 0b11110000;
		System.out.println(validUtf8(new int[]{235, 140, 4}));

	}

}
