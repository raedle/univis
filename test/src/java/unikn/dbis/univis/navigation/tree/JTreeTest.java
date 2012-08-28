package unikn.dbis.univis.navigation.tree;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.tree.*;

import unikn.dbis.univis.hibernate.util.HibernateUtil;
import unikn.dbis.univis.meta.impl.VDiceBoxImpl;
import unikn.dbis.univis.meta.VDimension;
import unikn.dbis.univis.navigation.tree.VTreeHelper;
import unikn.dbis.univis.icon.VIcons;

import java.awt.event.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>JTreeTest</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 03.11.2005
 * Time: 14:36:45
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: JTreeTest.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class JTreeTest extends JTree {

    public JTreeTest() {

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            /**
             * Configures the renderer based on the passed in components.
             * The value is set from messaging the tree with
             * <code>convertValueToText</code>, which ultimately invokes
             * <code>toString</code> on <code>value</code>.
             * The foreground color is set based on the selection and the icon
             * is set based on on leaf and expanded.
             */
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component label = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

                boolean summable = false;
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object o = node.getUserObject();

                    if (o instanceof VDimension) {
                        summable = ((VDimension) o).isDragable();
                    }
                }

                TreeRow treeRow = new TreeRow();
                treeRow.setContent(label);
                treeRow.setIcon(new UVIcon(VIcons.FILTER));

                /*
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(Color.WHITE);
                panel.add(new JLabel(), BorderLayout.CENTER);

                if (summable) {
                    UVIcon view = new UVIcon(VIcons.VIEW);
                    panel.add(view, BorderLayout.EAST);
                }
                */

                return treeRow;
            }
        };

        setEditable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("RIGHT CLICK");
                    //editor.actionPerformed(new ActionEvent(JTreeTest.this, 0, "asdf"));

                    /*
                    int row = JTreeTest.this.getRowForLocation(e.getX(), e.getY());

                    JTreeTest tree = JTreeTest.this;

                    Component c = renderer.getTreeCellRendererComponent(tree, "nothing", tree.isRowSelected(row), tree.isExpanded(row), false, row, tree.hasFocus());


                    if (c instanceof TreeRow) {
                        UVIcon icon = ((TreeRow) c).getIcon();

                        System.out.println("BOUNDS: " + icon.getX());

                        System.out.println("ECHO: " + icon.getBounds().contains(e.getPoint()));


                    }
                    */

                    System.out.println("e.getX() = " + e.getX());
                    System.out.println("e.getY() = " + e.getY());

                    System.out.println("COMP: " + e.getComponent());
                    System.out.println("DEEP: " + SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY()));

                    //editor.getTreeCellEditorComponent(JTreeTest.this, "asdf", true, JTreeTest.this.isE)
                }
            }
        });

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        /*
        java.util.List l = session.createQuery("from " + VDataReference.class.getName()).list();

        for (Object o : l) {
            System.out.println("CLASS: " + o.getClass());

            if (o instanceof VDimension) {
                System.out.println("SUMM: " + ((VDimension) o).isDragable());
            }
        }
        session.close();
        */

        VDiceBoxImpl diceBox = (VDiceBoxImpl) session.createQuery("from " + VDiceBoxImpl.class.getName() + " where name = 'UniVis Explorer'").uniqueResult();

        DefaultTreeModel model = new DefaultTreeModel(VTreeHelper.createDefaultTree(diceBox));
        setModel(model);

        /*
        for (VHierarchy hierarchy : diceBox.getChildren()) {
            System.out.println("CLASS: " + hierarchy.getDataReference().getClass());
        }
        */

        /*
        setCellRenderer(new TreeCellRenderer() {

            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

                JPanel panel = null;

                if (value instanceof VDimension) {

                    VDimension dimension = (VDimension) value;

                    panel = new JPanel(new GridLayout(1, dimension.getSupportedCubes().size()));

                    for (VCube cube : dimension.getSupportedCubes()) {
                        VCubeFlagIcon flag = new VCubeFlagIcon(cube.getColor());
                        flag.setPreferredSize(new Dimension(5, 12));
                        flag.setSize(new Dimension(5, 12));
                        flag.setMaximumSize(new Dimension(5, 12));
                        flag.setMinimumSize(new Dimension(5, 12));
                        panel.add(flag);
                    }
                }

                if (panel == null) {
                    panel = new JPanel();
                }

                panel.add(new JLabel(value.toString()));

                if (selected) {
                    panel.setBackground(Color.BLUE);
                }

                return panel;
            }
        });
        */

        /*
        addMouseListener(new MouseAdapter() {
            /**
             * Invoked when the mouse has been clicked on a component.
             *
            @Override
            public void mouseClicked(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {

                    TreePath path = JTreeTest.this.getPathForLocation(e.getX(), e.getY());

                    System.out.println("PARENT: " + path.getParentPath().getLastPathComponent());

                    JTreeTest.this.setSelectionPath(path);

                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) JTreeTest.this.getLastSelectedPathComponent();

                    Object o = node.getUserObject();

                    if (o instanceof VDimension) {

                        VDimension dimension = (VDimension) o;

                        if (dimension.isDragable()) {

                            JPopupMenu menu = new JPopupMenu("Test");

                            System.out.println("TABLE_NAME: " + dimension.getTableName());

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/univis", "univis", "UniVis");

                                Statement stmt = connection.createStatement();

                                ResultSet result = stmt.executeQuery("SELECT name FROM " + dimension.getTableName());

                                while (result.next()) {
                                    menu.add(result.getString(1));
                                    System.out.println("TEST: " + result.getString(1));
                                }
                            }
                            catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                            menu.show(JTreeTest.this, e.getX(), e.getY());
                        }
                    }
                }
            }
        });
        */
    }

    public class TreeRow extends JPanel {

        private UVIcon icon;

        public TreeRow() {
            super(new BorderLayout());

            setBackground(Color.WHITE);
        }

        public void setContent(Component c) {
            add(c, BorderLayout.CENTER);
        }

        public void setIcon(UVIcon icon) {

            this.icon = icon;

            add(icon, BorderLayout.EAST);
        }

        public UVIcon getIcon() {
            return icon;
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("JTreeTest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new JScrollPane(new JTreeTest()));

        frame.setSize(new Dimension(300, 500));
        frame.setVisible(true);
    }
}