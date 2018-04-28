package family.tree;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.List;
import java.util.*;

/**
 * Project File Name: BinaryTreeDemo.java
 * Project Date: 4/19/2018
 * Project Author: Damian Ayres
 * @author dayre
 */
public class BinaryTreeDemo extends JFrame {
    private BinaryTree binTree = new BinaryTree();
    private JLabel cmdResultLabel;
    private JTextField cmdResultTextField;
    private JList relatives;
    private DefaultListModel model;
    private JLabel cmdLabel;
    private JTextField cmdTextField;
    
    public BinaryTreeDemo() {
        setTitle("Family Tree");
        
        //cmdText and cmdResult label in North
        JPanel resultPanel = new JPanel(new GridLayout(1,2));
        cmdResultLabel = new JLabel("Command Result: ");
        cmdResultTextField = new JTextField();
        model = new DefaultListModel();
        relatives = new JList(model);
        resultPanel.add(cmdResultLabel);
        resultPanel.add(relatives);
        cmdResultTextField.setEditable(false);
        
        add(resultPanel, BorderLayout.NORTH);
        
        //Leave center for binary tree view
        
        //cmdLabel and cmdTextField in South
        cmdLabel = new JLabel("Command: ");
        cmdTextField = new JTextField();
        JPanel cmdPanel = new JPanel(new GridLayout(1,2));
        cmdPanel.add(cmdLabel);
        cmdPanel.add(cmdTextField);
        ActionListener cmdLis = new CmdTextListener();
        cmdTextField.addActionListener(cmdLis);
        add(cmdPanel, BorderLayout.SOUTH);
        
        //Set up the frame
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);        
    }
    
    JPanel view = null;
    
    private class CmdTextListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String cmdStr = cmdTextField.getText();
            Scanner sc = new Scanner(cmdStr);
            String cmd = sc.next();
            
            //the root name command
            if (cmd.equals("root")){
                String element = sc.next();
                binTree.addRoot(element);
                if (view != null){
                    remove(view);
                }
                view = binTree.getView();
                add(view);
                cmdTextField.setText("");
                pack();
                validate();
                return;
            }
            //the left parent child command
            if (cmd.equals("left")){
                String parent = sc.next();
                String child = sc.next();
                binTree.leftChild(parent, child);
                if (view != null) {
                    remove(view);
                }
                view = binTree.getView();
                add(view);
                cmdTextField.setText("");
                pack();
                validate();
                return;                
            }
            //the right parent child command
            if (cmd.equals("right")){
                String parent = sc.next();
                String child = sc.next();
                binTree.rightChild(parent, child);
                if (view != null) {
                    remove(view);
                }
                view = binTree.getView();
                add(view);
                cmdTextField.setText("");
                pack();
                validate();
                return;                   
            }
            //the descendants person command
            if (cmd.equals("descendants")){
                String current = sc.next();
                binTree.clearList();
                binTree.findChildren(current);
                relatives.setModel(binTree.getModel());
                cmdTextField.setText("");
                return;
            }
            //the ancestors person command
            if (cmd.equals("ancestors")){
                String current = sc.next();
                binTree.clearList();
                binTree.findParents(current);
                relatives.setModel(binTree.getModel());
                cmdTextField.setText("");
                return;                
            }
        }
    }
}
