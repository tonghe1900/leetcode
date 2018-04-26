package alite.leetcode.newExtra;
/**
 * http://www.geeksforgeeks.org/print-nodes-binary-tree-k-leaves/
Given a binary tree and a integer value K, the task is to find all nodes in given binary tree having K leaves
 in subtree rooted with them.



Here any node having K leaves means sum of leaves in left subtree and in right
 subtree must be equal to K. So to solve this problem we use Postorder traversal of tree.
  First we calculate leaves in left subtree then in right subtree and if sum is equal to K, 
  then print current node. In each recursive call we return sum of leaves of left subtree and right subtree 
  to it’s ancestor.


int kLeaves(struct Node *ptr,int k)

{

    // Base Conditions : No leaves

    if (ptr == NULL)

        return 0;

 

    // if node is leaf

    if (ptr->left == NULL && ptr->right == NULL)

        return 1;

 

    // total leaves in subtree rooted with this

    // node

    int total = kLeaves(ptr->left, k) +

                kLeaves(ptr->right, k);

 

    // Print this node if total is k

    if (k == total)

        cout << ptr->data << " ";

 

    return total;

}





Count digits in a factorial - GeeksforGeeks

Count digits in a factorial | Set 1 - GeeksforGeeks
Given an integer n, find the number of digits that appear in its factorial, where factorial is defined as, factorial(n) = 1*2*3*4……..*n and factorial(0) = 1

A naive solution would be to calculate the n! first and then calculate the number of digits present in it. However as the value for n! can be very large, it would become cumbersome to store them in a variable (Unless you’re working in python!) .
A better solution would be to use the useful property of logarithms to calculate the required answer.
We know,
log(a*b) = log(a) + log(b)

Therefore
log( n! ) = log(1*2*3....... * n) 
          = log(1) + log(2) + ........ +log(n)

Now, observe that the floor value of log base 
10 increased by 1, of any number, gives the
number of digits present in that number.

Hence, output would be : floor(log(n!)) + 1.

int findDigits(int n)

{

    // factorial exists only for n>=0

    if (n < 0)

        return 0;

 

    // base case

    if (n <= 1)

        return 1;

 

    // else iterate through n and calculate the

    // value

    double digits = 0;

    for (int i=2; i<=n; i++)

        digits += log10(i);

 

    return floor(digits) + 1;

}

http://www.geeksforgeeks.org/count-digits-factorial-set-2/
However that solution would not be able to handle cases where n >10^6
So, can we improve our solution ?
Yes ! we can.
We can use Kamenetsky’s formula to find our answer !
It approximates the number of digits in a factorial by :
f(x) =    log10( ((n/e)^n) * sqrt(2*pi*n))

Thus , we can pretty easily use the property of logarithms to ,
f(x) = n* log10(( n/ e)) + log10(2*pi*n)/2 

And that’s it !
Our solution can handle very large inputs that can be accommodated in a 32 bit integer,

long long findDigits(int n)

{

    // factorial of -ve number doesn't exists

    if (n < 0)

        return 0;

 

    // base case

    if (n <= 1)

        return 1;

 

    // Use Kamenetsky formula to calculate the

    // number of digits

    double x = ((n*log10(n/M_E)+log10(2*M_PI*n)/2.0));

 

    return floor(x)+1;

}

Read full article from Count digits in a factorial | Set 1 - GeeksforGeeks
 * @author het
 *
 */
public class Print_all_nodes_in_a_binary_tree_having_K_leaves {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
