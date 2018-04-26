package alite.leetcode.newExtra;
/**
 * smallest permutation from signature i,d

https://www.careercup.com/question?id=14912744

You are given an array of n elements [1,2,....n]. For example {3,2,1,6,7,4,5}.
Now we create a signature of this array by comparing every consecutive pir of elements. If they increase, 
write I else write D. For example for the above array, the signature would be "DDIIDI". The signature thus has a length of N-1.
 Now the question is given a signature, compute the lexicographically smallest permutation of [1,2,....n].
  Write the below function in language of your choice.
DDIIDDIDDD
321654
11










vector* FindPermute(const string& signature);
int lasti=1
For i = 1...siglen
    If signature[i] == 'I'
        print reverse(lasti, i)
        lasti=i+1
print reverse(lasti, siglen)
reverse(1,1) is 1, and reverse(4,6) is 654.
The pseudocode works in linear time and constant space, and uses 1 based indexing, which is why the indexing looks kind of strange. It is equivalent to starting with a list 1234567...n, finding all groups of Ds, and reversing sections of the initial list that the Ds overlap.

This greedy algorithm stems from the idea that if the list starts with an I, the output must start with a 1, 
if it starts with a single D, it must start 21, DD, 321 and so forth. Whenever it sees an I, it picks the minimum element 
that can be placed, and is therefore lexicographically minimal.
from sys import stdin

def solve(sign):
    result = []
    last = 1
    for i in range(1, len(sign) + 1):
        if sign[i - 1] == 'I':
            result += range(i, last - 1, -1)
            last = i + 1
    return result += range (len(sign) + 1, last - 1, -1)

for line in stdin.readlines(): 
    print solve(line[:-1]) http://www.1point3acres.com/bbs/thread-138432-1-1.html
Give a list of numbers:. From 1point 3acres bbs
3, 5, 9, 2
Generate a signature for the list of numbers:
i, i, d

Example:
6, 8, 3, 5, 2
i,d,i,d

Question is:. 鍥磋鎴戜滑@1point 3 acres
Given a signature for a list of number, trying to print out a list of number that fits the signature, with lowest value of the highest number, and at the same time, no duplicated numbers.
Numbers[1, N]

1,...n 可以满足任何 长度为n-1 的 类似于 ddididid 这样的wiggle sequence，问题是如何构造。

我用的的是链表，如果当前的字符是i，就把数字放到最后，如果是d，就把当前数字放到最近的i的位置

时间O(n) 空间O(1) (如果结果可以用链表表示的话)，应该是最优解吧
    public void findWiggle(String path) {
        ListNode fakeHead = new ListNode(0);
        //start from 1
        fakeHead.next = new ListNode(1);
        ListNode iInsert = fakeHead.next;
        ListNode dInsert = fakeHead;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'i') {
                iInsert.next = new ListNode(i+2);
                iInsert = iInsert.next;
                dInsert = dInsert.next;
            } else {
                ListNode tmp =  dInsert.next;
                dInsert.next = new ListNode(i+2);. more info on 1point3acres.com
                dInsert.next.next = tmp;
            }
        } 鏉ユ簮涓€浜�.涓夊垎鍦拌鍧�. 
        print(fakeHead.next);
    }


at 3:38 AM No comments: 
Email This
BlogThis!
Share to Twitter
Share to Facebook
Share to Pinterest

Labels: Google Interview, Greedy Algorithm
Return all Numbers lesser than maximum number - Google interview

http://www.1point3acres.com/bbs/thread-205789-1-1.html
给一个list的digit，给一个maximum number，要求返回所有由这些digit组成的且不超过max的数字，一个digit可以重复使用比如{3,7,8}, max=1000, 返回{3,33,333,37,38...7,738,...}
突然想到我明明问了她0不能放在第一位的，结果写的时候给忘了...orz
然后写完跑了几个test case，并且跑完她问我有没有可以优化的，我说可以对list排序一下，这样遇到大于max的可以直接返回，不用考虑后序结果
写的时候也忘记考虑有重复数字了。。。orz.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
最后又问了复杂度，一开始答O(n^2)，后来觉得不对，改成了O(n!)。【每题必问复杂度。。。
void helper(vector<int>& digits, int tmp, int maxNumber, vector<int> &res) {
    if (tmp > maxNumber) {
        return;
    }
    if (tmp != 0) {.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
        res.push_back(tmp);. Waral 鍗氬鏈夋洿澶氭枃绔�,
    }
    for (int i = 0; i < digits.size(); i++) {. visit 1point3acres.com for more.
        helper(digits, tmp * 10 + digits[i], maxNumber, res);
    }
}

vector<int> getNumbers(vector<int>& digits, int maxNumber) {.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
    vector<int> res;
    sort(digits.begin(), digits.end());
    helper(digits,0, maxNumber, res);
    if (digits[0] == 0) {
        res.push_back(0);
    }
    return res;
}


public List<String> getNumbers(List<int> digits, int maxNumber) {
        LinkedList<int> result = new LinkedList();. Waral 鍗氬鏈夋洿澶氭枃绔�,
        recursiveGetNumbers(digits, 0, maxNumber, 0, result);
        return result;
}

public void recursiveGetNumbers(List<int> digits, int level, int maxNumber, int curResult, List<int> result) {.1point3acres缃�
        if (level == digits.length) {
                if (curResult <= maxNumber) {
                        result.add(curResult);
                }
                return;
        }
        recursiveGetNumbers(digits, level+1, maxNumber, curResult, result);
        curResult = curResult * 10 + digits[level];
        recursiveGetNumbers(digits, level+1, maxNumber, curResult, result);. 鍥磋鎴戜滑@1point 3 acres
}

X. BFS
public List<Integer> getNumbers(int[] digits, int maxNumber) {
                List<Integer> result = new ArrayList<Integer>();
                if(digits.length == 0) return result;
                
                Arrays.sort(digits);
                Queue<Integer> queue = new LinkedList<Integer>();
                queue.add(0);
                
                while(!queue.isEmpty()){
                        int size = queue.size();
                        for(int i = 0; i < size; i++){.鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
                                int previous = queue.poll();
                                for(int j = 0; j < digits.length; j++){
                                        int current = previous * 10 + digits[j];
                                        if(current <= maxNumber && current != 0){
                                                queue.add(current);
                                                result.add(current);
                                        }. from: 1point3acres.com/bbs 
                                }
                        }
                }
                
                if(digits[0] == 0) result.add(0, 0); // corner case when there is 0 in digits array
                return result;
        }


at 1:35 AM No comments: 
Email This
BlogThis!
Share to Twitter
Share to Facebook
Share to Pinterest

Labels: Google Interview, LeetCode, Review
Print BST Kth level alternatively - Google Intreview

http://www.1point3acres.com/bbs/thread-198684-1-1.html
第一轮: Print BST Kth level alternatively比如  5 ,k = 2的话， 打印结果1 8 3 6
    /  \
   2    7
  / \  / \
  1 3 6  8
比如  5 ,k = 2的话， 打印结果3 8 6
    /  \
   2    7
    \  / \-google 1point3acres
    3 6  8

第一题从空间complexity来说确实是他的小。stack就是前序遍历非递归算法那个stack，栈内元素照理说是不超过树的高度的k。但是BFS需要维护每层所有元素，空间上是2^k。
另外对DFS来说假设树是BST是有意义的。因为两个指针有可能会相互错过对方。BST可以用来判断终止条件（左指针指向的元素<右指针）。

第一题用dfs的话space complexity只有O(k)
第一轮确实是应该双向DFS，我当时把自己绕晕了


private boolean initializeStack(TreeNode root, Stack<TreeNode> stack, int k, boolean isLeft){
    stack.push(root);
    if(stack.size() == k) return true;. Waral 鍗氬鏈夋洿澶氭枃绔�,
    if(isLeft){
    if(root.left !=null){
    if(initializeStack(root.left, stack, k, isLeft)) return true;
}
if(root.right != null){
    if(initializeStack(root.right, stack, k, isLeft)) return true;
}
}else{
if(root.right != null){
    if(initializeStack(root.right, stack, k, isLeft)) return true;
}. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
    if(root.left !=null){
    if(initializeStack(root.left, stack, k, isLeft)) return true;
}
}-google 1point3acres
stack.pop();. 1point3acres.com/bbs
return false;
}


int nextNode(Stack<TreeNode> stack, int k, boolean isLeft){
    int ans = stack.peek().val;
    TreeNode here = stack.pop();
    if(isLeft){
        while(!stack.isEmpty() && ((here == stack.peek().left && stack.peek().right == null) || here == stack.peek().right)){
            here = stack.pop();
}
if(stack.isEmpty()) return ans;. 1point3acres.com/bbs
initializeStack(stack.peek().right, stack, k, isLeft);
}else{. from: 1point3acres.com/bbs 
    while(!stack.isEmpty() && ((here == stack.peek().right && stack.peek().left == null) || here == stack.peek().left)){
    here = stack.pop();
}
if(stack.isEmpty()) return ans;
initializeStack(stack.peek().left, stack, k, isLeft);
}
return ans;
}


    public List<Integer> kthLevel(TreeNode root, int k){
        Stack<TreeNode> leftStack = new Stack<>();
        Stack<TreeNode> rightStack = new Stack<>();
        if(root == null) return new LinkedList<>();
        initializeStack(root, leftStack, k, true);
        initializeStack(root, rightStack, k, false);
        List<Integer> ans = new LinkedList<>();
while(true){
    int left = nextNode(leftStack, k, true);
    int right = nextNode(rightStack, k , false);
    if(left == right){. 涓€浜�-涓夊垎-鍦帮紝鐙鍙戝竷
    ans.add(left); return ans;
}else if(left < right){
    ans.add(left); ans.add(right);
}else{
    return ans;
}
}
}

这道题虽然说是BST,但跟BST没啥关系。. From 1point 3acres bbs
一开始我没有很好的思路，然后面试官提示DFS/BFS，我就想到BFS+deque的做法：
void printBSTKthLevelAlt(TreeNode *root, int k) {
  if (root == nullptr || k < 0) {
    return;
  }
  deque<TreeNode*> q;. 鍥磋鎴戜滑@1point 3 acres
  int level = 0;
  q.push_back(root);
  while(!q.empty()) {
    if (level == k) {
      printLevel(q);
      return;
    }
    auto ls = q.size();
    for(auto i = 0; i < ls; ++i) {
      auto node = q.front();
      q.pop_front();
      if (q->left != nullptr) {
        q.push_back(q->left);
      }
      if (q->right != nullptr) {
        q.push_back(q->right);
      }
    }
    ++k;. 1point3acres.com/bbs
  }
}

void printLevel(const deque<TreeNode*>& q) {
  bool left = true;
  while (!q.empty()) {
    TreeNode* node;
    if (left) {
      node = q.front();
      q.pop_front();
    }
    else {. From 1point 3acres bbs
      node = q.back();
      q.pop_back();
    }. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
    cout << node->val << ' ';
    left != left;
  }
  cout << endl;
}
然后面试官问我time&space complexity是什么，我答道O(n), O(2^k)。
然后他说怎么可以在保持time complexity的同时优化space。我就开始纠结了。. 鍥磋鎴戜滑@1point 3 acres
他提示说用DFS，我比划了半天也没想出来。然后时间快到的时候，他告诉了我他的思路：
维护两个stack，一个stack先push right child，另一个先push left child
比如我们有      a1                        这棵树，我们想打印k=3这层，
                 /            \
        a2             a3
          /    \        /      \
     a4    a5      a6      a7
    / \   /  \   /   \    /  \
   a8 a9 a10 a11 a12 a13 a14 a15

k = 1
s1: a1/a3/a2
s2: a1/a2/a3
k = 2
s1: a1/a3/a2/a5/a4
s2: a1/a2/a3/a6/a7. 鍥磋鎴戜滑@1point 3 acres
k = 3
s1: a1/a3/a2/a5/a4/a9/a8
s2: a1/a2/a3/a6/a7/a14/a15
print: a8 a15 a9 a14
k = 2
s1: a1/a3/a2/a5
s2: a1/a2/a3/a6
k = 3. 鐣欏鐢宠璁哄潧-涓€浜╀笁鍒嗗湴
s1: a1/a3/a2/a5/a11/a10. 1point3acres.com/bbs
s2: a1/a2/a3/a6/a12/a13
print: a10 a13 a11 a12
finish
这个思路确实比我的巧妙，但先不说这个思路写作代码会很繁琐，我就没明白这样怎么更节省空间了。
因为使用了两个stack，对空间的占用应该是半斤八两的。而且在
k = 3
s1: a1/a3/a2/a5/a4/a9/a8
s2: a1/a2/a3/a6/a7/a14/a15
这个时候，至少存了10个节点，如果用BFS的做法只会存8个节点。

struct TreeNode {. Waral 鍗氬鏈夋洿澶氭枃绔�,
    int val;
    TreeNode* left, * right;. Waral 鍗氬鏈夋洿澶氭枃绔�,
    TreeNode(int v) : val(v), left(NULL), right(NULL) {};
};

class TreeIterator {
public:
        TreeIterator(TreeNode* root, int k) : max_level(k) {. visit 1point3acres.com for more.
                if (root) stk.push(make_pair(0, root));
        }
        
        bool has_next() { return !stk.empty(); }
        
        int next() {
                if (stk.empty()) throw "out of range";  .鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
                int ret_val = stk.top().second->val;
                stk.pop();
                traverse();
                return ret_val;
        };        
        .鐣欏璁哄潧-涓€浜�-涓夊垎鍦�
        virtual TreeNode* get_left (TreeNode *n)=0;
        virtual TreeNode* get_right(TreeNode *n)=0;

protected:        . 1point3acres.com/bbs
        void traverse() {
                while (!stk.empty() && stk.top().first != max_level) {. 鍥磋鎴戜滑@1point 3 acres
                        int next_level = stk.top().first+1;
                        TreeNode* left  = get_left (stk.top().second);
                        TreeNode* right = get_right(stk.top().second);
                        stk.pop();
                        if (right) stk.push(make_pair(next_level, right));
                        if (left)  stk.push(make_pair(next_level, left ));
                }                
        }
        stack<pair<int, TreeNode*>> stk;
        int max_level;
        
};

class TreeForwardIterator : public TreeIterator {. Waral 鍗氬鏈夋洿澶氭枃绔�,
public:
    TreeForwardIterator(TreeNode* root, int k) : TreeIterator(root, k) { traverse(); };
        TreeNode* get_left (TreeNode* n) { return n->left;  };. 鍥磋鎴戜滑@1point 3 acres
        TreeNode* get_right(TreeNode* n) { return n->right; };
};


class TreeReverseIterator : public TreeIterator {-google 1point3acres
public:. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
    TreeReverseIterator(TreeNode* root, int k) : TreeIterator(root, k) { traverse(); };
        TreeNode* get_left (TreeNode* n) { return n->right; };
    TreeNode* get_right(TreeNode* n) { return n->left;  };        
};

vector<int> tree_level_alt(TreeNode *root, int k) {. 1point3acres.com/bbs
        TreeForwardIterator i_left (root, k);
        TreeReverseIterator i_right(root, k);
        vector<int> result;
        . 1point 3acres 璁哄潧
        int l = INT_MIN, r = INT_MAX;. more info on 1point3acres.com
        while (l < r){
                l = i_left.has_next()  ? i_left.next()  : INT_MAX;
                r = i_right.has_next() ? i_right.next() : INT_MIN;
                if (l < r) { result.push_back(l); result.push_back(r);}
                else { if (l == r) result.push_back(l); }. 1point3acres.com/bbs
        }
        return result;
};

 * @author het
 *
 */
public class Anothersome {
//	This greedy algorithm stems from the idea that if the list starts with an I, the output must start with a 1, 
//	if it starts with a single D, it must start 21, DD, 321 and so forth. Whenever it sees an I, it picks the minimum element 
//	that can be placed, and is therefore lexicographically minimal.
//	from sys import stdin
//
//	def solve(sign):
//	    result = []
//	    last = 1
//	    for i in range(1, len(sign) + 1):
//	        if sign[i - 1] == 'I':
//	            result += range(i, last - 1, -1)
//	            last = i + 1
//	    return result += range (len(sign) + 1, last - 1, -1)
//
//	for line in stdin.readlines(): 
//	    print solve(line[:-1]) http://www.1point3acres.com/bbs/thread-138432-1-1.html
//	Give a list of numbers:. From 1point 3acres bbs
//	3, 5, 9, 2
	
//	DDIIDDIDDD
	// 3 2 1 6 7   5 4  11   10  9 8 
//	321654
//	11
	
	// 
	
	// IIIDDDIII
	//DDDIIDDD
	//IIIIIIIIIIIDDDDDDDDDDDD
	//IIIDD
	public static int[] decode(String code){
		if(code == null || code.length() ==0 ) return new int[]{};
		int [] result = new int[code.length()+1];
		//StringBuilder r = new StringBuilder();
		int min = 1;
		int start = 0;
		int numberOfI = 0;
		int numberOfD = 0;
		for(int i=0;i<code.length();i+=1){
			//if(code.charAt(i ) == 'I' &&(code.charAt(i-1) == 'D'))
			// DI
			if(code.charAt(i) == 'I'){
				numberOfI+=1;
			}else{
				numberOfD +=1;
				
			}
			
			if((code.charAt(i+1) == 'I'|| i+1>=code.length() )&& code.charAt(i) == 'D'){
				for(int k =0 ;k<numberOfD;k+=1){
					result [start + numberOfI +((i ==0 && numberOfI == 0)? 1:0) ] = min  + numberOfD-1;
				}
				
			}
		}
		
		return result;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] result = decode("IIIIIIIIIIIDDDDDDDDDDDD");
		print (result);
		

	}
	private static void print(int[] result) {
		for(int e: result){
			System.out.println(e+",");
		}
		System.out.println();
	}

}
