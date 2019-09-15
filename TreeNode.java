import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class TreeNode {

    //To check whether TreeNode is a leaf
    protected boolean isLeaf;
    //To check whether TreeNode is a root
    protected boolean isRoot;

    public List<Pair<Integer, Double>> getPairSets() {
        return pairSets;
    }

    //To know the height of the tree
    protected int treeHeight = 0;
    //To check whether TreeNode is a parent
    protected TreeNode parent;
    //To store the previous TreeNode
    protected TreeNode prev;
    //To store the next TreeNode
    protected TreeNode next;
    //A set of key value pairs for the leaf nodes
    protected List<Pair<Integer, Double>> pairSets;
    //To store all the child nodes
    protected List<TreeNode> children;

    /*
   Creates a leafNode with empty list of key value pairs and child nodes
     */
    public TreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        pairSets = new ArrayList<Pair<Integer, Double>>();

        if (!isLeaf) {
            children = new ArrayList<TreeNode>();
        }
    }

    /*
    Creates a root node
     */
    public TreeNode(boolean isLeaf, boolean isRoot) {
        this(isLeaf);
        this.isRoot = isRoot;
    }

    /*
     Insert (key, value)- Insert a given key and value pair
     */
    private void insert(int key, Double value) {
        Pair<Integer, Double> pair = new Pair<Integer, Double>(key, value);
        //In case , the pairSet is empty we directly insert it
        if (pairSets.size() == 0) {
            pairSets.add(pair);
            return;
        }

        for (int i = 0; i < pairSets.size(); i++) {
            if (pairSets.get(i).getKey().compareTo(key) > 0) {
                //insert to the first position
                if (i == 0) {
                    pairSets.add(0, pair);
                    return;
                    //insert to ith position
                } else {
                    //values.add(value);
                    pairSets.add(i, pair);
                    return;
                }
            }
        }
        //inserting the pair to the last
        pairSets.add(pairSets.size(), pair);


    }

    protected void deletePairNode(int key, BplusTree tree) {
        TreeNode tr = tree.root;

        //Removing in the first height of the tree
        if (tr.isLeaf && tr.isRoot) {
            for (int i = 0; i < tr.getPairs().size(); i++) {
                if (tr.getPairs().get(i).getKey() == key) {
                    tr.pairSets.remove(i);
                }

            }
        }
        while (!tr.isLeaf) {

            int pairSize = tr.getChildren().size();
            int[] pairListKey = new int[pairSize];

            for (int i = 0, k = 0; i < pairSize; i++) {
                for (int j = 0; j < tr.getChildren().get(i).getPairs().size(); j++) {
                    pairListKey[k++] = tr.getChildren().get(i).getPairs().get(j).getKey();
                }
            }


        /*    int ind=binary_search(pairListKey,key,0,pairSize-1);
            System.out.println("index="+ind);
            if(ind==-1){
                if(key<pairListKey[0]){

                }
                else if(key<pairListKey[pairSize-1]){

                }
            }
            else{
                tr=tr.getChildren().get(ind);
            }*/
        }
        //  tree.root = tr;
        return;
    }

    /*
    Case- Search (key1, key2): returns values such that in the range key1 <= key <= key2
     */
    public List<Double> searchRange(int key1, int key2, BplusTree tree) {
        List<Pair<Integer, Double>> p2 = getAllPairs(tree);

        List<Double> searchList = new ArrayList<Double>();
        for (int i = 0; i < p2.size(); i++) {
            if (p2.get(i).getKey() >= key1 && p2.get(i).getKey() <= key2) {
                searchList.add(p2.get(i).getValue());
            }
        }
        return searchList;
    }

    /*
    Case - Deleting a key from the tree when it exists
    Otherwise the tree remains the same
     */
    public List<Pair<Integer, Double>> deleteFromNode(int key, BplusTree tree) {
        List<Pair<Integer, Double>> newPair = getAllPairs(tree);
        return updateInNode(key, tree);
    }

    /*
     Search (key): returns the value associated with the key
     */
    public String search(int key, BplusTree tree) {
        Stack<TreeNode> st = new Stack<TreeNode>();
        st.add(tree.root);
        //Since search is suppose to return Null in case of element not found
        String searchResult = "Null";
        Double result = null;
        boolean reachedLeaf = false;
        //The loop runs until it reaches the leaf Node
        while (!reachedLeaf) {
            int size = st.size();
            for (int i = 0; i < size; i++) {
                TreeNode n = st.pop();
                if (n.isLeaf) {
                    reachedLeaf = true;
                    for (Pair<Integer, Double> p : n.pairSets) {
                        if (p.getKey() == key) {
                            result = p.getValue();
                            break;
                        }
                    }

                    st.clear();
                    break;
                }
                //Push the pairSets into Stack
                Stack<Pair<Integer, Double>> keyStack = new Stack<>();
                for (Pair<Integer, Double> p : n.pairSets) {
                    keyStack.push(p);
                }
                //To identify the key value element
                boolean found = false;
                int index = -1;
                int stackSize = keyStack.size();
                for (int j = 0; j < stackSize; j++) {
                    Pair<Integer, Double> p = keyStack.pop();
                    if (key >= p.getKey()) {
                        found = true;
                        index = n.pairSets.size() - j;
                        break;
                    }
                }
                //When the child nodes of the index node
                if (found) {
                    st.clear();
                    st.add(n.children.get(index));
                    break;
                }
                //Otherwise the leftmost child
                else {
                    st.clear();
                    st.add(n.children.get(0));
                    break;
                }
            }
        }
        //Return the value for the searched key
        if (null != result) searchResult = result + "";
        return searchResult;
    }

    //Fetch all the key-value pairs of a leaf nodes
    public List<Pair<Integer, Double>> getAllPairs(BplusTree tree) {
        TreeNode r = tree.root;
        while (!r.isLeaf) {
            r = r.children.get(0);
        }
        Pair<Integer, Double> t = r.getPairs().get(0);
        List<Pair<Integer, Double>> p2 = new ArrayList<Pair<Integer, Double>>();

        while (null != r) {
            for (int pi = 0; pi < r.getPairs().size(); pi++) {
                p2.add(r.getPairs().get(pi));
            }
            r = r.next;
        }
        return p2;
    }

    public void insertOrModify(int key, Double value, BplusTree tree) {
        /*
        Case 1 - Leaf node
         */
        if (isLeaf) {
            // When the no of elements doesnt exceed the order
            if (containsDuplicateElement(key) && pairSets.size() <= tree.getTreeOrder()) {
                System.out.println("Duplicate keys are not allowed");
                return;
            } else if (!(containsDuplicateElement(key)) && pairSets.size() < tree.getTreeOrder() - 1) {
                insert(key, value);
                if (parent != null) {
                    //Modifying the parent node
                    parent.modifyInsert(tree);
                }
                if (treeHeight == 0) {
                    tree.setTreeHeight(1);
                }

                return;
            }
            /*
            Case 2- when insert increases the no of elements than m
             */
            else {
                //split to left node and right node
                TreeNode left = new TreeNode(true);
                TreeNode right = new TreeNode(true);
                //set link
                if (prev != null) {
                    prev.setNext(left);
                    left.setPrev(prev);
                }
                if (next != null) {
                    next.setPrev(right);
                    right.setNext(next);
                }
                if (prev == null) {
                    tree.setHead(left);
                }

                left.setNext(right);
                right.setPrev(left);
                prev = null;
                next = null;
                //Sending the left and right leaf nodes to splitTheNode function to form the tree structure
                spiltTheNode(key, value, left, right, tree);

                /*
                Case 3- when it is not a root node
                 */
                if (parent != null) {
                    int index = parent.getChildren().indexOf(this);
                    parent.getChildren().remove(this);
                    left.setParent(parent);
                    right.setParent(parent);
                    parent.getChildren().add(index, left);
                    parent.getChildren().add(index + 1, right);
                    parent.pairSets.add(index, right.pairSets.get(0));
                    setChildren(null);

                    //Updating the parent node
                    parent.modifyInsert(tree);
                    setParent(null);
                }
                 /*
                    Case 4 - Root node
                     */
                else {
                    isRoot = false;
                    TreeNode parent = new TreeNode(false, true);
                    tree.setRoot(parent);
                    left.setParent(parent);
                    right.setParent(parent);
                    parent.getChildren().add(left);
                    parent.getChildren().add(right);
                    parent.pairSets.add(right.pairSets.get(0));
                    setChildren(null);

                    //Modifying the root node
                    parent.modifyInsert(tree);
                }
                return;

            }
            /*
            Case 5 - Not a leaf node
             */
        } else {
            /*
            Scenario 1 : The key is less than the leftmost key
                        We choose the first childNode
             */
            if (Integer.compare(key, pairSets.get(0).getKey()) <= 0) {
                children.get(0).insertOrModify(key, value, tree);
            }
            /*
            Scenario 2 : The key is more than the leftmost key
                        We choose the last childNode
             */
            else if (Integer.compare(key, (pairSets.get(pairSets.size() - 1).getKey())) >= 0) {
                children.get(children.size() - 1).insertOrModify(key, value, tree);
                 /*
            Scenario 3 : The key is in between the first and the last node
             */
            } else {
                //Implementing binary search to find the position of the key
                binary_search(0, pairSets.size() - 1, key, value, tree);

            }
        }
    }

    public List<Pair<Integer, Double>> updateInNode(int key, BplusTree tree) {
        List<Pair<Integer, Double>> newPair = getAllPairs(tree);
        if (null != search(key, tree)) {
            for (int i = 0; i < newPair.size(); i++) {
                if (newPair.get(i).getKey() == key) {
                    newPair.remove(i);
                    break;
                }
            }
        }
        return newPair;
    }


    /*
    In case of the tree order exceeding the no of elements in a node
     */
    private void spiltTheNode(int key, Double value, TreeNode left,
                              TreeNode right, BplusTree tree) {
        // key size left node
        int leftSize = (int) Math.ceil(tree.getTreeOrder() / 2);
        boolean b = false;
        Pair<Integer, Double> p = new Pair<Integer, Double>(key, value);//false: new pair is not inserted, true: new pair is inserted
        for (int i = 0; i < pairSets.size(); i++) {
            if (leftSize != 0) {
                leftSize--;

                if (!b && pairSets.get(i).getKey().compareTo(key) > 0) {
                    left.pairSets.add(p);
                    //     left.pairs.
                    b = true;
                    i--;
                } else {
                    left.pairSets.add(pairSets.get(i));
                }
            } else {
                if (!b && pairSets.get(i).getKey().compareTo(key) > 0) {
                    right.pairSets.add(p);
                    b = true;
                    i--;
                } else {
                    right.pairSets.add(pairSets.get(i));
                }
            }
        }
        if (!b) {
            right.pairSets.add(new Pair<Integer, Double>(key, value));
        }
    }

    public void updatePairsNode(List<Pair<Integer, Double>> pairSet, BplusTree tree) {
      for(int i=0;i<pairSet.size();i++){
          insertOrModify(pairSet.get(i).getKey(),pairSet.get(i).getValue(),tree);
      }
    }

    /**
     * update index node after insert
     */
    protected void modifyInsert(BplusTree tree) {
        // if number of children is larger than order number, split node
        if (children.size() > tree.getTreeOrder()) {
            //split to left node and right node
            TreeNode left = new TreeNode(false);
            TreeNode right = new TreeNode(false);
            //the key's size of left node and right node
            int leftSize = (tree.getTreeOrder() + 1) / 2 + (tree.getTreeOrder() + 1) % 2;
            int rightSize = (tree.getTreeOrder() + 1) / 2;

            //copy children node to new node and update keys
            for (int i = 0; i < leftSize; i++) {
                left.children.add(children.get(i));
                children.get(i).parent = left;
            }
            for (int i = 0; i < rightSize; i++) {
                right.children.add(children.get(leftSize + i));
                children.get(leftSize + i).parent = right;
            }
            for (int i = 0; i < leftSize - 1; i++) {
                left.pairSets.add(pairSets.get(i));
            }
            for (int i = 0; i < rightSize - 1; i++) {
                right.pairSets.add(pairSets.get(leftSize + i));
            }

            //if it is not root node
            if (parent != null) {

                int index = parent.getChildren().indexOf(this);
                parent.getChildren().remove(this);
                left.setParent(parent);
                right.setParent(parent);
                parent.getChildren().add(index, left);
                parent.getChildren().add(index + 1, right);
                parent.pairSets.add(index, new Pair<>(pairSets.get(leftSize - 1).getKey(), null));
                // parent.pairs.add(index, pairs.get(leftSize - 1));
                setChildren(null);

                // update node key for parent node
                parent.modifyInsert(tree);
                setParent(null);
                //if it is root node
            } else {
                isRoot = false;
                TreeNode parent = new TreeNode(false, true);
                tree.setRoot(parent);
                tree.setTreeHeight(tree.getTreeHeight() + 1);
                left.setParent(parent);
                right.setParent(parent);
                parent.getChildren().add(left);
                parent.getChildren().add(right);
                parent.pairSets.add(new Pair<>(pairSets.get(leftSize - 1).getKey(), null));
                setChildren(null);

            }
        }
    }

    //Implementing binary search method to find the key in the range of pairSets
    protected void binary_search(int left, int right, int key, Double value, BplusTree tree) {
        int middle = 0, compare = 0;
        while (left <= right) {
            middle = (left + right) / 2;
            compare = pairSets.get(middle).getKey().compareTo(key);
            if (compare == 0) {
                children.get(middle + 1).insertOrModify(key, value, tree);
                break;
            } else if (compare < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        if (left > right) {
            children.get(left).insertOrModify(key, value, tree);
        }
    }

    /**
     * confirm the node pair contains the key or not
     * Since duplicate values are not allowed
     */
    protected boolean containsDuplicateElement(int key) {
        for (Pair<Integer, Double> pair : pairSets) {
            if (pair.getKey().compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public TreeNode getPrev() {
        return prev;
    }

    public void setPrev(TreeNode prev) {
        this.prev = prev;
    }

    public TreeNode getNext() {
        return next;
    }

    public void setNext(TreeNode next) {
        this.next = next;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<Pair<Integer, Double>> getPairs() {
        return pairSets;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

}
