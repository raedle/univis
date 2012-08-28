package unikn.dbis.univis.navigation.tree;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.*;

import unikn.dbis.univis.meta.VCube;

/**
 * TODO: document me!!!
 * <p/>
 * <code>TreePopupTest</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 01:19:32
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: TreePopupTest.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class TreePopupTest extends JFrame {

    private JSplitPane splitPane = new JSplitPane();

    private JTree tree = new JTree();

    private Popup popup;

    /**
     * Constructs a new frame that is initially invisible.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public TreePopupTest() throws HeadlessException {

        setTitle("TreePopup Test");

        setSize(new Dimension(800, 600));

        initLayout();
        initListener();
    }

    private void initLayout() {
        getContentPane().add(splitPane, BorderLayout.CENTER);

        splitPane.setLeftComponent(tree);

        tree.setCellRenderer(new DefaultTreeCellRenderer() {


            /**
             * Configures the renderer based on the passed in components.
             * The value is set from messaging the tree with
             * <code>convertValueToText</code>, which ultimately invokes
             * <code>toString</code> on <code>value</code>.
             * The foreground color is set based on the selection and the icon
             * is set based on on leaf and expanded.
             */
            @Override
            public Component getTreeCellRendererComponent(final JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));

                panel.add(c);

                if (!leaf) {
                    ImageIcon icon = new ImageIcon("images/view.png");
                    UVIcon view = new UVIcon(icon);
                    panel.add(view);
                }

                panel.setBackground(Color.WHITE);
                if (hasFocus) {
                    panel.setBackground(c.getBackground());
                    panel.setBorder(((JLabel) c).getBorder());
                }

                return panel;
            }
        });
    }

    private void initListener() {

        JButton button = new JButton(new ImageIcon(VCube.class.getResource("../images/view.png")));
        button.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed button.");

                if (popup != null) popup.hide();
            }
        });

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(new JLabel("Measure Auswahl"));
        panel.add(new JCheckBox("Information Engineering"));
        panel.add(new JCheckBox("Wirtschaftspaedagogik"));
        panel.add(button);

        tree.addMouseListener(new MouseAdapter() {

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e The mouse event.
             */
            @Override
            public void mouseReleased(MouseEvent e) {

                System.out.println("TreePopup.mouseReleased");

                if (e.isPopupTrigger()) {

                    TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());

                    if (popup != null) popup.hide();

                    if (treePath != null) {
                        tree.setSelectionRow(tree.getRowForPath(treePath));
                        popup = PopupFactory.getSharedInstance().getPopup(tree, panel, e.getX(), e.getY() + (int) (panel.getPreferredSize().getHeight() / 2));
                        popup.show();
                    }
                }
                else {
                    if (popup != null) popup.hide();
                }
            }
        });
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new TreePopupTest().setVisible(true);
    }
}
