package alite.leetcode.newest;
/**
 * Two elements whose sum is closest to zero

http://www.geeksforgeeks.org/two-elements-whose-sum-is-closest-to-zero/
Question: An Array of integers is given, both +ve and -ve. You need to find the two elements such that their sum is closest to zero.


    static void minAbsSumPair(int arr[], int n)

    {

      // Variables to keep track of current sum and minimum sum

      int sum, min_sum = 999999;

      

      // left and right index variables

      int l = 0, r = n-1;

      

      // variable to keep track of the left and right pair for min_sum

      int min_l = l, min_r = n-1;

      

      /* Array should have at least two elements*/

      if(n < 2)

      {

        System.out.println("Invalid Input");

        return;

      }

      

      /* Sort the elements */

      sort(arr, l, r);

      

      while(l < r)

      {

        sum = arr[l] + arr[r];

      

        /*If abs(sum) is less then update the result items*/

        if(Math.abs(sum) < Math.abs(min_sum))

        {

          min_sum = sum;

          min_l = l;

          min_r = r;

        }

        if(sum < 0)

          l++;

        else

          r--;

      }

      

       

      System.out.println(" The two elements whose "+

                              "sum is minimum are "+

                        arr[min_l]+ " and "+arr[min_r]);

    }

Brute force

    static void minAbsSumPair(int arr[], int arr_size)

    {

      int inv_count = 0;

      int l, r, min_sum, sum, min_l, min_r;

      

      /* Array should have at least two elements*/

      if(arr_size < 2)

      {

        System.out.println("Invalid Input");

        return;

      }

      

      /* Initialization of values */

      min_l = 0;

      min_r = 1;

      min_sum = arr[0] + arr[1];

      

      for(l = 0; l < arr_size - 1; l++)

      {

        for(r = l+1; r < arr_size; r++)

        {

          sum = arr[l] + arr[r];

          if(Math.abs(min_sum) > Math.abs(sum))

          {

            min_sum = sum;

            min_l = l;

            min_r = r;

          }

        }

      }

      

      System.out.println(" The two elements whose "+

                              "sum is minimum are "+

                        arr[min_l]+ " and "+arr[min_r]);

    }
 * @author het
 *
 */
public class Twoelementswhosesumisclosesttozero {

}
