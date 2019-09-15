
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;

public class BplusTree {
    private final static Logger LOGGER = Logger.getLogger(BplusTree.class.getName());
    protected TreeNode root;
    protected int treeOrder;
    protected int treeHeight;
    protected TreeNode head;


    /**
     * head node of leaf node
     */
    public TreeNode getHead() {
        return head;
    }

    public void setHead(TreeNode head) {
        this.head = head;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public int getTreeOrder() {
        return treeOrder;
    }

    public void setTreeOrder(int treeOrder) {
        this.treeOrder = treeOrder;
    }

    public int getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(int treeHeight) {
        this.treeHeight = treeHeight;
    }

    public BplusTree(int treeOrder) {
        try {
            if (treeOrder < 3) {
                throw new Exception("treeOrder=" + treeOrder);
            }
            this.treeOrder = treeOrder;
            root = new TreeNode(true, true);
            head = root;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "The treeOrder of B+ tree should always be more than 2 whereas given ", e);
        }

    }


    /*
     Insert (key, value)
     */
    public void insert(int key, Double value) {

        root.insertOrModify(key, value, this);
    }

    /*
Delete(key)
     */
    public void deleteNode(int key) {
        TreeNode delNode = new TreeNode(true, true);
        Stack<TreeNode> st = new Stack<TreeNode>();
        while (!delNode.isLeaf) {
            delNode = delNode.children.get(0);
        }
        updateNode(key);

        if (delNode.isLeaf && delNode.isRoot) {
            for (int i = 0; i < delNode.getPairs().size(); i++) {
                if (delNode.getPairs().get(i).getKey() == key) {
                    delNode.pairSets.remove(i);
                }

            }
        }
    }
 /*
    Search (key1, key2): returns values such that in the range key1 <= key <= key2
     */

    public String searchRangeTree(int key1, int key2) {

        return Arrays.toString(root.searchRange(key1, key2, this).toArray()).replace("[", "").replace("]", "");
    }
    /*
    Search (key): returns the value associated with the key
     */
    public String searchNode(int key) {
        return root.search(key, this).toString();

    }

    public void updateNode(int key) {
        List<Pair<Integer, Double>> newPairSet = root.deleteFromNode(key, this);
        root = new TreeNode(true, true);
        head = root;
        for (int i = 0; i < newPairSet.size(); i++) {
            root.insertOrModify(newPairSet.get(i).getKey(), newPairSet.get(i).getValue(), this);
        }
    }



}
