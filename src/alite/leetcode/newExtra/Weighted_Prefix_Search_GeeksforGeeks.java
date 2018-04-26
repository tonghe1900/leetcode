package alite.leetcode.newExtra;
/**
 * Weighted Prefix Search - GeeksforGeeks

Weighted Prefix Search - GeeksforGeeks
Given n strings and a weight associated with each string. The task is to find the maximum weight of 
string having the given prefix. Print "-1" if no string is present with given prefix.

The idea is to create and maintain a Trie. Instead of the normal Trie where we store the character, 
store a number with it, which is maximum value of its prefix. When we encounter the prefix again update 
the value with maximum of existing and new one.
Now, search prefix for maximum value, run through the characters starting from the root,
 if one of character is missing return -1, else return the number stored in the root.

// Structure of a trie node

struct trieNode

{

    // Pointer its children.

    struct trieNode *children[26];

 

    // To store weight of string.

    int weight;

};

 

// Create and return a Trie node

struct trieNode* getNode()

{

    struct trieNode *node = new trieNode;

    node -> weight = INT_MIN;

 

    for (int i = 0; i < 26; i++)

        node -> children[i] = NULL;

}

 

// Inserting the node in the Trie.

struct trieNode* insert(char str[], int wt, int idx,

                                struct trieNode* root)

{

    int cur = str[idx] - 'a';

 

    if (!root -> children[cur])

        root -> children[cur] = getNode();

 

    // Assigning the maximum weight

    root->children[cur]->weight =

                  max(root->children[cur]->weight, wt);

 

    if (idx + 1 != strlen(str))

        root -> children[cur] =

           insert(str, wt, idx + 1, root -> children[cur]);

 

    return root;

}

 

// Search and return the maximum weight.

int searchMaximum(char prefix[], struct trieNode *root)

{

    int idx = 0, n = strlen(prefix), ans = -1;

 

    // Searching the prefix in TRie.

    while (idx < n)

    {

        int cur = prefix[idx] - 'a';

 

        // If prefix not found return -1.

        if (!root->children[cur])

            return -1;

 

        ans = root->children[cur]->weight;

        root = root->children[cur];

        ++idx;

    }

 

    return ans;

}

 

// Return the maximum weight of string having given prefix.

int maxWeight(char str[MAX][MAX], int weight[], int n,

                                       char prefix[])

{

    struct trieNode* root = getNode();

 

    // Inserting all string in the Trie.

    for (int i = 0; i < n; i++)

        root = insert(str[i], weight[i], 0, root);

 

    return searchMaximum(prefix, root);

 

}
brute force - Check all the string for given prefix, if string contains the prefix, compare its weight with maximum value so far.
Read full article from Weighted Prefix Search - GeeksforGeeks
 * @author het
 *
 */
public class Weighted_Prefix_Search_GeeksforGeeks {

}
