package alite.leetcode.xx2.sucess;
/**
 * [LeetCode]238. Product of Array Except Self
字数203 阅读68 评论0 喜欢0
Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Solve it without division and in O(n).

For example, given [1,2,3,4], return [24,12,8,6].
c代码
#include <assert.h>
#include <stdlib.h>

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* productExceptSelf(int* nums, int numsSize, int* returnSize) {
    int i = 0;
    int product = 1;
    int* products = (int *)malloc(sizeof(int) * numsSize);
    for(i = 0; i < numsSize; i++) {
        products[i] = product;
        product *= nums[i];
    }
    product = 1;
    for(i = numsSize-1; i >= 0; i--) {
        products[i] *= product;
        product *= nums[i];
    }
    *returnSize = numsSize;
    return products;
}

int main() {
    int nums[4] = {1,2,3,4};
    int returnSize = 0;
    int* products = productExceptSelf(nums, 4, &returnSize);
    assert(returnSize == 4);
    assert(products[0] == 24);
    assert(products[1] == 12);
    assert(products[2] == 8);
    assert(products[3] == 6);

    return 0;
}
 * @author het
 *
 */
public class L238 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
