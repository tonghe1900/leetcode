package alite.leetcode.xx3.extra;
/**
 * LeetCode 398 - Random Pick Index

http://bookshadow.com/weblog/2016/09/11/leetcode-random-pick-index/
Given an array of integers with possible duplicates, randomly output the index of a given
 target number. You can assume that the given target number must exist in the array.
Note:
The array size can be very large. Solution that uses too much extra space will not pass the judge.
Example:
int[] nums = new int[] {1,2,3,3,3};
Solution solution = new Solution(nums);

// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
solution.pick(3);

// pick(1) should return 0. Since in the array only nums[0] is equal to 1.
solution.pick(1);
https://discuss.leetcode.com/topic/58322/what-on-earth-is-meant-by-too-much-memory
Like mine, O(N) memory, O(N) init, O(1) pick.
Like @dettier's Reservoir Sampling. O(1) init, O(1) memory, but O(N) to pick.
Like @chin-heng's binary search: O(N) memory, O(N lg N) init, O(lg N) pick.
X. O(n) init, O(1) pick
    public Solution(int[] nums) {
        for (int i=0; i<nums.length; i++) {
            int num = nums[i];
            if (!indexes.containsKey(num))
                indexes.put(num, new ArrayList<Integer>());
            indexes.get(num).add(i);
        }
    }
    
    public int pick(int target) {
        List<Integer> indexes = this.indexes.get(target);
        int i = (int) (Math.random() * indexes.size());
        return indexes.get(i);
    }
    
    private Map<Integer, List<Integer>> indexes = new HashMap<>();
X. O(nlogn) init, O(logn) pick
https://discuss.leetcode.com/topic/58295/share-my-c-solution-o-lg-n-to-pick-o-nlg-n-for-sorting
Pre-process sorting for O(nlg(n))
Pick in O(lg(n)) using binary search
O(n) space to store value/index pairs
    typedef pair<int, int> pp; // <value, index>

    static bool comp(const pp& i, const pp& j) { return (i.first < j.first); }

    vector<pp> mNums;

    Solution(vector<int> nums) {
        for(int i = 0; i < nums.size(); i++) {
            mNums.push_back(pp({nums[i], i}));
        }
        sort(mNums.begin(), mNums.end(), comp);
    }

    int pick(int target) {
        pair<vector<pp>::iterator, vector<pp>::iterator> bounds = equal_range(mNums.begin(), mNums.end(), pp({target,0}), comp);
        int s = bounds.first - mNums.begin();
        int e = bounds.second - mNums.begin();
        int r = e - s;
        return mNums[s + (rand() % r)].second;
    }

X. O(1) init, O(n) pick
https://discuss.leetcode.com/topic/58301/simple-reservoir-sampling-solution
So basically you may return -1 at the end? Correct me if I am wrong, I think the question requires a result which has been guaranteed.
Oops, I got it. Actually random.nextInt(++counter) this will guarantee that at least the first one will be picked u
 this is because we need to save current element with probability 1 / (total # of encountered candidates). So it's 100% for first target element, 50% for the second one, and so on...
To those who don't understand why it works. Consider the example in the OJ
{1,2,3,3,3} with target 3, you want to select 2,3,4 with a probability of 1/3 each.
2 : It's probability of selection is 1 * (1/2) * (2/3) = 1/3
3 : It's probability of selection is (1/2) * (2/3) = 1/3
4 : It's probability of selection is just 1/3
So they are each randomly selected.
In rand.nextInt(n), n sure is the exclusive boundary. But for n <= 0, the method will throw an IllegalArgumentException. nmust be a positive number.
So counter++ is wrong.
r.nextInt(1) always return 0
public class Solution {

    int[] nums;
    Random rnd;

    public Solution(int[] nums) {
        this.nums = nums;
        this.rnd = new Random();
    }
    
    public int pick(int target) {
        int result = -1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != target)
                continue;
            if (rnd.nextInt(++count) == 0)
                result = i;
        }
        
        return result;
    }
}


https://discuss.leetcode.com/topic/58467/java-o-n-variant-of-reservoir-sampling
https://discuss.leetcode.com/topic/58356/o-n-for-java-any-other-good-idea
    private int[] nums;

    public Solution(int[] nums) {
        this.nums = nums;
    }
    
    private Random r = new Random();
    
    public int pick(int target) {
        int ret = -1;
        if (nums == null) {
            return ret;
        }
        int upbound = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (r.nextInt(upbound) == 0) {
                    ret = i;
                } 
                upbound++;
            }
        }
        return ret;
    }

    def __init__(self, nums):
        """
        
        :type nums: List[int]
        :type numsSize: int
        """
        size = len(nums)
        self.next = [0] * (size + 1)
        self.head = collections.defaultdict(int)
        for i, n in enumerate(nums):
            self.next[i + 1] = self.head[n]
            self.head[n] = i + 1

    def pick(self, target):
        """
        :type target: int
        :rtype: int
        """
        cnt = 0
        idx = self.head[target]
        while idx > 0:
            cnt += 1
            idx = self.next[idx]
        c = int(random.random() * cnt)
        idx = self.head[target]
        for x in range(c):
            idx = self.next[idx]
        return idx - 1

X. Offline
https://discuss.leetcode.com/topic/58394/simple-java-solution-o-n
This is appropriate when this api is called many times
    static int[] nums; 

    public Solution(int[] nums) {
        this.nums=nums;
    }
    
    public int pick(int target) {
        
        List<Integer> mm = new ArrayList<Integer>();
     
     for(int i=0; i<nums.length; i++){
      if(nums[i]==target)
       mm.add(i);
     }
     Random rn=new Random();
     return mm.get(rn.nextInt(mm.size()));
    }
https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleMedium/398.html
 * @author het
 *
 */
public class LeetCode398 {
	 public Solution(int[] nums) {
	        for (int i=0; i<nums.length; i++) {
	            int num = nums[i];
	            if (!indexes.containsKey(num))
	                indexes.put(num, new ArrayList<Integer>());
	            indexes.get(num).add(i);
	        }
	    }
	    
	    public int pick(int target) {
	        List<Integer> indexes = this.indexes.get(target);
	        int i = (int) (Math.random() * indexes.size());
	        return indexes.get(i);
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
