package unikn.dbis.univis.navigation.tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>unikn.dbis.univis.navigation.tree.CloneTreeNodeTest</code>.
 * User: rro
 * Date: 08.04.2006
 * Time: 21:51:53
 *
 * @author Roman R&auml;dle
 * @version $Id: CloneTreeNodeTest.java 338 2006-10-08 23:11:30Z raedler $
 */
public class CloneTreeNodeTest {

    public static void main(String[] args) {

        JFrame frame = new JFrame("CloneTreeNodeTest");

        DefaultMutableTreeNode node = new DefaultMutableTreeNode("PARENT");
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("CHILD1");
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("CHILD2");
        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("CHILD3");

        node.add(child1);
        child1.add(child2);
        child2.add(child3);
        child3.add((MutableTreeNode) child1.clone());
        child3.add((MutableTreeNode) child1.clone());
        child3.add((MutableTreeNode) child1.clone());
        child3.add((MutableTreeNode) child1.clone());

        JTree tree = new JTree(node);

        frame.getContentPane().add(tree);

        frame.pack();

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
