package family.tree;
import javax.swing.*;
import java.awt.*;

/**
 * Project File Name: BinaryTree.java
 * Project Date: 4/19/2018
 * Project Author: Damian Ayres
 * @author dayre
 */
public class BinaryTree {
    
    private class Node {
        String value;           //string value to be stored in the node
        Node left, right;       //left and right child
        
        /**
         * Constructor for leaf nodes
         * @param val The string value to store in the node.
         */
        Node (String val) {
            value = val;
            left = null;
            right = null;
        }
        /**
         * Constructor for non-leaf nodes
         * @param val The string value to initialize in the node
         * @param leftChild The link to the left child of the node
         * @param rightChild The link to the right child of the node
         */
        Node (String val, Node leftChild, Node rightChild) {
            value = val;
            left = leftChild;
            right = rightChild;
        }        
    }
    
    /**
     * The BTreeDisplay class graphically displays the binary tree 
     * in a JPanel.The JPanel is recursively partitioned into a top 
     * part displaying the root, and two lower parts displaying
     * the left and right subtrees
     */
    private class BTreeDisplay extends JPanel {
        /**
         * Constructor
         * @param tree The root of the binary tree to display
         */
        BTreeDisplay (Node tree) {
            setBorder(BorderFactory.createEtchedBorder());
            setLayout(new BorderLayout());
            if (tree != null){
                String value = String.valueOf(tree.value);
                int pos = SwingConstants.CENTER;
                JLabel rootLabel = new JLabel(value, pos);
                add(rootLabel, BorderLayout.NORTH);
                JPanel panel = new JPanel(new GridLayout(1,2));
                panel.add(new BTreeDisplay(tree.left));
                panel.add(new BTreeDisplay(tree.right));
                add(panel);
            }
        }
    }
    
    private Node root = null;   //the root of a binary tree
    
    /**
     * The getView method creates and returns a graphical view 
     * of the binary tree
     * @return A panel that displays a view of the tree
     */
    public JPanel getView() {
        return new BTreeDisplay(root);
    }
    
    /**
     * The private addRoot method creates the root of the tree
     * @param val The string value to add as the root
     * @param btree The root of the binary tree
     * @return The root of the resulting binary tree
     */
    private Node addRoot(String val, Node btree) {
        if (btree == null) {
            return new Node(val);
        }
        return btree;
    }
    
    /**
     * The public addRoot method adds the root value to the tree
     * by calling the private addRoot method and passing it to the 
     * root of the tree
     * @param val The string value to add as the root node
     * @return true
     */
    public boolean addRoot(String val) {
        root = addRoot(val, root);
        return true;
    }
    
    /**
     * The private leftChild method passes a parent string to the findNode
     * method.  If the node with that value is found, and the left child 
     * of the node is empty, a new node created with the value of the child
     * string is added as the left child of that node
     * @param parent The string value of the parent node
     * @param child The string value of the child node
     * @param btree The node that is added
     * @return 
     */
    private Node leftChild(String parent, String child, Node btree) {
        Node parentNode;
        Node leftChild = new Node(child);
        parentNode = findNode(parent, root);
        if (parentNode != null){
            if (parentNode.left == null){
                parentNode.left = leftChild;
            }
        }
        return btree;
    }
    
    /**
     * The public leftChild method takes a parent and child string and passes
     * them to the private leftChild method
     * @param parent The string value of the parent node
     * @param child The string value of the child node
     * @return True
     */
    public boolean leftChild(String parent, String child) {
        root = leftChild(parent, child, root);
        return true;
    }
    
    /**
     * The private rightChild method passes a parent string to the findNode
     * method.  If the node with that value is found, and the right child 
     * of the node is empty, a new node created with the value of the child
     * string is added as the right child of that node
     * @param parent The string value of the parent node
     * @param child The string value of the child node
     * @param btree The root node
     * @return The node that was added
     */
    private Node rightChild(String parent, String child, Node btree) {
        Node parentNode;
        Node rightChild = new Node(child);
        parentNode = findNode(parent, root);
        if (parentNode != null){
            if (parentNode.right == null){
                parentNode.right = rightChild;
            }
        }
        return btree;
    }
    
    /**
     * The public rightChild method takes a parent and child string and 
     * passes them to the private rightChild method
     * @param parent The string value of the parent node
     * @param child The string value of the child node
     * @return True
     */
    public boolean rightChild(String parent, String child) {
        root = rightChild(parent, child, root);
        return true;
    }
    
    /**
     * The findNode method traverses the binary tree recursively 
     * in a preorder fashion
     * @param val The string value of the node being searched for
     * @param btree The root of the binary tree
     * @return Null if the node is not found
     */
    public Node findNode(String val, Node btree){
        Node current = btree;
        
        //Visit the node
        if (current.value.equals(val)){
            return current;
        }
        
        if (current.left != null){
            Node nodeFound = findNode(val, current.left);
            if (nodeFound != null){
                return nodeFound;
            }
        }
        
        if (current.right != null){
            Node nodeFound = findNode(val, current.right);
            if (nodeFound != null){
                return nodeFound;
            }
        }
        
        return null;
    }
    
    /* 
    *    The list of descendants and ancestors we will use for the JList 
    *    in BinaryTreeDemo
    */
    private DefaultListModel model = new DefaultListModel();
    
    /**
     * The getModel method returns the contents of our DefaulListModel 
     * to update the JList component in BinaryTreeDemo
     * @return The model DefaulListModel object
     */
    public DefaultListModel getModel() {
        return this.model;
    }
    
    /**
     * The clearList method clears the contents of the model DefaultListModel
     * whenever the descendants or ancestors method is called 
     * from BinaryTreeDemo
     */
    public void clearList() {
        this.model.clear();
    }
    
    /**
     * The private findChildren method traverses the binary tree in a preorder
     * fashion and adds the value string of the nodes to the model object
     * to be passed into the JList in BinaryTreeDemo
     * @param btree The node returned from the findNode method
     */
    private void findChildren(Node btree) {
        if (btree != null) {
            findChildren(btree.left);
            System.out.print(btree.value + " ");
            model.addElement(btree.value);
            findChildren(btree.right);
        }
    }
    
    /**
     * The public findChildren method calls the findNode method first
     * before passing the node to the private findChildren method if it exists
     * @param val The string value of the node to search for
     */
    public void findChildren(String val){
        Node current;
        current = findNode(val, root);
        if (current != null) {
            findChildren(current);
        }
    }
    
    /**
     * The private findParents function takes the root node and the string 
     * value of the node to search for from the public findParents function.
     * Because the root node is always the initial ancestor, set as current node 
     * and add the value to the ancestors list, then call the findNode function on the 
     * left or right child nodes if they exist.  Call the findParents function
     * again on whichever side does not return null, which adds the value of that
     * node to the ancestors list.  The function continues recursively in this manner
     * until the value of the current node equals the val string
     * @param val The string value of the node to search for
     * @param btree The current node
     * @return Null if the node is not found
     */
    private Node findParents(String val, Node btree) {
        Node current = btree;
        model.addElement(current.value);
        
        //visit the node
        if (current.value.equals(val)){
            return current;
        }        
        
        if (current.left != null){
            Node nodeFound = findNode(val, current.left);
            if (nodeFound != null){
                nodeFound = findParents(val, current.left);
                return nodeFound;
            }
        }
        
        if (current.right != null){
            Node nodeFound = findNode(val, current.right);
            if (nodeFound != null){
                nodeFound = findParents(val, current.right);
                return nodeFound;
            }
        }        
        
        return null;
    }
    
    /**
     * The public findParents method calls the findNode method first
     * before passing the node to the private findParents method if it exists
     * @param val The string value of the node to search for
     */
    public void findParents (String val) {
        Node current;
        current = findNode(val, root);
        if (current != null) {
            findParents(val, root);
        }
    }
    
    
}
