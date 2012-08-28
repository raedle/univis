/**
 *
 */
package unikn.dbis.univis.navigation.tree;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * @author daritho
 */
public class JTreeExample extends JFrame {

    public JTreeExample() {
        super("JTreeExample");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTree tree = new JTree();

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                System.out.println("user selected: " + e.getNewLeadSelectionPath().getLastPathComponent());
            }
        });

        add(tree);

        pack();
        setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new JTreeExample();
    }

}