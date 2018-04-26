package alite.leetcode.xx4;
/**
 * https://leetcode.com/problems/validate-ip-address/
In this problem, your job to write a function to check whether a input string is a valid IPv4 address or IPv6 address or neither.
IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;
Besides, you need to keep in mind that leading zeros in the IPv4 is illegal. For example, the address 172.16.254.01 is illegal.
IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a legal one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading zeros and using upper cases).
However, we don't replace a consecutive group of zero value with a single empty group using two consecutive colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.
Besides, you need to keep in mind that extra leading zeros in the IPv6 is also illegal. For example, the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is also illegal.
Note: You could assume there is no extra space in the test cases and there may some special characters in the input string.
Example 1:
Input: "172.16.254.1"

Output: "IPv4"

Explanation: This is a valid IPv4 address, return "IPv4".
Example 2:
Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"

Output: "IPv6"

Explanation: This is a valid IPv6 address, return "IPv6".
Example 3:
Input: "256.256.256.256"

Output: "Neither"

Explanation: This is neither a IPv4 address nor a IPv6 address.

http://bookshadow.com/weblog/2016/12/11/leetcode-validate-ip-address/
字符串模拟题，根据规则逐一验证即可
    def validIPAddress(self, IP):
        """
        :type IP: str
        :rtype: str
        """
        if self.validIPV4(IP):
            return 'IPv4'
        if self.validIPV6(IP):
            return 'IPv6'
        return 'Neither'

    def validIPV4(self, IP):
        parts = IP.split('.')
        if len(parts) != 4: return False
        for part in parts:
            if not part: return False
            if not part.isdigit(): return False
            if part[0] == '0' and len(part) > 1: return False
            if int(part) > 255: return False
        return True

    def validIPV6(self, IP):
        parts = IP.split(':')
        if len(parts) != 8: return False
        for part in parts:
            if not part: return False
            if len(part) > 4: return False
            if any(c not in string.hexdigits for c in part): return False
        return True
 * @author het
 *
 */
public class LeetCode468 {
	
	
//	Example 1:
//		Input: "172.16.254.1"
//
//		Output: "IPv4"
//
//		Explanation: This is a valid IPv4 address, return "IPv4".
//		Example 2:
//		Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
//
//		Output: "IPv6"
//
//		Explanation: This is a valid IPv6 address, return "IPv6".
//		Example 3:
//		Input: "256.256.256.256"
//
//		Output: "Neither"
//
//		Explanation: This is neither a IPv4 address nor a IPv6 address.

//		http://bookshadow.com/weblog/2016/12/11/leetcode-validate-ip-address/
//		字符串模拟题，根据规则逐一验证即可
//		    def validIPAddress(self, IP):
//		        """
//		        :type IP: str
//		        :rtype: str
//		        """
//		        if self.validIPV4(IP):
//		            return 'IPv4'
//		        if self.validIPV6(IP):
//		            return 'IPv6'
//		        return 'Neither'
//
//		    def validIPV4(self, IP):
//		        parts = IP.split('.')
//		        if len(parts) != 4: return False
//		        for part in parts:
//		            if not part: return False
//		            if not part.isdigit(): return False
//		            if part[0] == '0' and len(part) > 1: return False
//		            if int(part) > 255: return False
//		        return True
//
//		    def validIPV6(self, IP):
//		        parts = IP.split(':')
//		        if len(parts) != 8: return False   //if len(parts) != 8: return False
//		        for part in parts:
//		            if not part: return False
//		            if len(part) > 4: return False   // <=4hexdigits
//		            if any(c not in string.hexdigits for c in part): return False
//		        return True
    public String validIPAddress(String IP){
    	if(validIPV4(IP)){
    		return "IPv4";
    	}
    	if(validIPV6(IP)){
    		return "IPv6";
    	}
    	return "Neither";
    }
	private boolean validIPV4(String iP) {
		if(iP == null) return false;
		String [] parts = iP.split(".");
		if(parts.length !=4) return false;
		for(String part: parts){
			if(part.isEmpty()) return false;
			if(!isDigit(part)) return false;
			char first = part.charAt(0);
			if(first == '0' && part.length() > 1) return false;
			if(Integer.parseInt(part) > 255) return false;
			
		}
		return true;
	}
	private boolean isDigit(String part) {
		char[]  chars = part.toCharArray();
		for(char c: chars){
			if(!Character.isDigit(c)){
				return false;
			}
		}
		
		return true;
	}
	private boolean validIPV6(String iP) {
		if(iP == null) return false;
		String [] parts = iP.split(":");
		if(parts.length !=8) return false;
		for(String part: parts){
			if(part.isEmpty()) return false;
			if(part.length() > 4 ) return false;
			if(!isHexdigits(part)) return false;
			
			
		}
		return true;
	}
	private boolean isHexdigits(String part) {
	
//		char[]  chars = part.toCharArray();
//		for(char c: chars){
//			if(!Character..is.isHexdigits(part)){
//				return false;
//			}
//		}
		
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
