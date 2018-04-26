package alite.leetcode.xx3.extra;

import java.util.Arrays;
import java.util.Random;

///**
// * LeetCode 384 - Shuffle an Array
//
//https://www.hrwhisper.me/leetcode-shuffle-array/
//Shuffle a set of numbers without duplicates.
//Example:
//
//
//// Init an array with set 1, 2, and 3.
//int[] nums = {1,2,3};
//Solution solution = new Solution(nums);
//
//// Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
//solution.shuffle();
//
//// Resets the array back to its original configuration [1,2,3].
//solution.reset();
//
//// Returns the random shuffling of array [1,2,3].
//solution.shuffle();
//思路：用swap，每次从[i,n-1]中随机一个数，和第i个数交换即可。
//当然，上面的代码是每次洗牌从上一次的结果继续进行的，这也符合我们对于洗牌的感觉。
//如果每次从初始数组进行洗牌，那么reset数组只需要返回初始数组。。
//
//    private int[] nums;
//
//    private int[] output;
//
//    private Random random;
//
//    
//
//    public Solution(int[] nums) {
//
//        this.nums = nums;
//
//        this.output = Arrays.copyOf(nums,nums.length);
//
//        this.random = new Random();
//
//    }
//
//    
//
//    /** Resets the array to its original configuration and return it. */
//
//    public int[] reset() {
//
//        return this.output = Arrays.copyOf(nums,nums.length);
//
//    }
//
//    
//
//    /** Returns a random shuffling of the array. */
//
//    public int[] shuffle() {
//
//        int n = output.length;
//
//  for (int i = 0; i < n; i++) {
//
//   int _id = random.nextInt(n-i);
//
//   int temp = output[i];
//
//   output[i] = output[i+_id];
//
//   output[i+_id] = temp;
//
//  }
//
//  return output;
//
//    }
//
//http://www.learn4master.com/interview-questions/leetcode/leetcode-shuffle-an-array-java
//
//    int[] origin;
//
//    int[] res;
//
//    Random r;
//
//    public Solution(int[] nums) {
//
//        origin = nums;
//
//        res = Arrays.copyOf(nums, nums.length);
//
//        r = new Random();
//
//    }
//
//    
//
//    /** Resets the array to its original configuration and return it. */
//
//    public int[] reset() {
//
//        res = Arrays.copyOf(origin, origin.length);
//
//        return res;
//
//    }
//
//    
//
//    /** Returns a random shuffling of the array. */
//
//    public int[] shuffle() {
//
//        int right = res.length - 1;
//
//        int j;
//
//        
//
//        while(right > 0) {
//
//            j = r.nextInt(right + 1);
//
//            swap(res, j, right);
//
//            right--;
//
//        }
//
//        return res;
//
//    }
//
//    
//
//    void swap(int[] A, int i, int j) {
//
//        int tmp = A[i];
//
//        A[i] = A[j];
//
//        A[j] = tmp;
//
//    }
//
//http://www.guoting.org/leetcode/leetcode-384-shuffle-an-array/
//    private int[] nums;
//    private Random rand;
//
//    public Solution(int[] nums){
//        this.nums=nums;
//        this.rand=new Random();
//    }
//
//    /** Resets the array to its original configuration and return it. */
//    public int[] reset(){
//        return nums;
//    }
//
//    /** Returns a random shuffling of the array. */
//    public int[] shuffle(){
//        if(nums==null) return null;
//        int[] arr=nums.clone();
//        for(int i=1;i<arr.length;i++) {
//            int j=rand.nextInt(i+1);
//            swap(arr,i,j);
//        }
//        return arr;
//    }
//
//    private void swap(int[] arr,int i,int j){
//        int tmp=arr[i];
//        arr[i]=arr[j];
//        arr[j]=tmp;
//    }
//http://www.cnblogs.com/grandyang/p/5783392.html
//这道题让我们给数组洗牌，也就是随机打乱顺序，那么由于之前那道题Linked List Random Node我们接触到了水塘抽样的思想，这道题实际上这道题也是用类似的思路，我们遍历数组每个位置，每次都随机生成一个坐标位置，然后交换当前遍历位置和随机生成的坐标位置的数字，这样如果数组有n个数字，那么我们也随机交换了n组位置，从而达到了洗牌的目的
//    Solution(vector<int> nums): v(nums) {}
//    
//    /** Resets the array to its original configuration and return it. */
//    vector<int> reset() {
//        return v;
//    }
//    
//    /** Returns a random shuffling of the array. */
//    vector<int> shuffle() {
//        vector<int> res = v;
//        for (int i = 0; i < res.size(); ++i) {
//            int t = rand() % res.size();
//            swap(res[i], res[t]);
//        }
//        return res;
//    }
// * @author het
// *
// */
public class LeetCode384 {
	private int[] nums;

    private int[] output;

    private Random random;

    

    public LeetCode384(int[] nums) {

        this.nums = nums;

        this.output = Arrays.copyOf(nums,nums.length);

        this.random = new Random();

    }

    

    /** Resets the array to its original configuration and return it. */

    public int[] reset() {

        return this.output = Arrays.copyOf(nums,nums.length);

    }

    

    /** Returns a random shuffling of the array. */

    public int[] shuffle() {

        int n = output.length;

  for (int i = 0; i < n; i++) {

   int _id = random.nextInt(n-i);

   int temp = output[i];

   output[i] = output[i+_id];

   output[i+_id] = temp;

  }

  return output;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
