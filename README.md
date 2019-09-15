# B-Tree
B+Tree implementation - Insert,Search,Search Range,Delete
B+ tree is a type of tree data structure. It represents sorted data in a way that allows for efficient
insertion and removal of elements. It is a dynamic, multilevel index with maximum and minimum
bounds on the number of keys in each node. In this tree, records (data) can only be stored on
the leaf nodes while internal nodes can only store the key values. B+ Tree are used to store the
large amount of data which cannot be stored in the main memory. Due to the fact that, size of
main memory is always limited, the internal nodes (keys to access records) of the B+ tree are
stored in the main memory whereas, leaf nodes are stored in the secondary memory.
Advantages of B+ Tree:
• Records can be fetched in equal number of disk accesses
• Height of the tree remains balanced and less as compare to B tree.
• We can access the data stored in a B+ tree sequentially as well as directly
• Keys are used for indexing
• Faster search queries as the data is stored only on the leaf nodes.
Functions
Function name: public BPlusTree(int treeOrder)
Structure: Creates a tree with the given order and the root and leaf as true.
Description Creates a B+ tree object with the given order
Parameters treeOrder Order of the Tree
Return Value A tree object is created with order 3
Function name: public static void insert(int key, Double value)
Structure: Inserts TreeNode into B+ tree. If the number of nodes in the B+ leaf node is more than
the order ‘m’ of the node, then we perform split and send a pointer to the parent and then
recursively merge the node with the parent. If the tree is empty, then we directly insert the node
and set it as the root.
Description inserting a node into B+ tree
Parameters int key
Double value
Key, value pair is inserted into the tree.
Return Value No return
Function name: public Double search (int key)
Structure: Searches from the root and then the internal nodes direct the search towards the key
until it reaches the leaf node. If the key is present, then the function returns the value of stored in
the key. If the key is not present in the node, then the function returns NULL.
Description Searches if the key is present in the node.
Parameters int key
Return Value Value if the key is present, else Null
Function name: public void deleteNode(int key)
Structure: Deletes the key from the leaf nodes, if the key is present. Starts from the root and
moves through the internal nodes and then deletes the key, value pair from the node.
Description Deletes the key from the B+ tree
Parameters int key
Return Value None
Function name: public static List<Double> searchRange(int key1, int key2)
Structure: Returns the values that are present between two keys. The search begins from key1
and then proceeds through the linked list and then returns the values corresponding to the keys
that are present between key1 and key2.
Description Performs range search between key1 and key2.
Parameters int key1
int key2
Does not include key1 and includes key2
Return Value Values corresponding to keys between two keys.
Files:
Maintree.java:
This file handles input file and has routines for calling B+ tree functions.
The search results are written into the output file.
BplusTree.java:
This file has implementation of B+ tree.
Constants.java:
An interface for all the keywords
Treenode.java:
The tree node structures are inserted, deleted and updated here.
