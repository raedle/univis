/*
 * Copyright 2005-2006 UniVis Explorer development team.
 *
 * This file is part of UniVis Explorer
 * (http://phobos22.inf.uni-konstanz.de/univis).
 *
 * UniVis Explorer is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package unikn.dbis.univis.explorer;

import unikn.dbis.univis.navigation.tree.VTree;
import unikn.dbis.univis.meta.impl.VDiceBoxImpl;
import unikn.dbis.univis.meta.VDiceBox;
import unikn.dbis.univis.hibernate.util.HibernateUtil;
import unikn.dbis.univis.icon.VIcons;
import unikn.dbis.univis.util.ComponentUtilities;
import unikn.dbis.univis.visualization.VVisualization;
import unikn.dbis.univis.visualization.Visualizable;
import unikn.dbis.univis.visualization.chart.ChartType;
import unikn.dbis.univis.visualization.graph.VGraph;
import unikn.dbis.univis.message.MessageResolver;
import unikn.dbis.univis.message.Internationalizable;
import unikn.dbis.univis.message.swing.VLabel;
import unikn.dbis.univis.message.swing.VMenu;
import unikn.dbis.univis.message.swing.VMenuItem;
import unikn.dbis.univis.system.Constants;
import unikn.dbis.univis.images.VImageDummy;
import unikn.dbis.univis.visualization.pivottable.VPivotTable;
import unikn.dbis.univis.swingx.StackedBox;
import unikn.dbis.univis.dataexchange.schemaimport.SchemaImport;
import unikn.dbis.univis.ApplicationInfo;
import unikn.dbis.univis.explorer.about.AboutPanel;
import unikn.dbis.univis.explorer.license.LicenseDialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.*;
import java.awt.dnd.DragSource;
import java.awt.dnd.DnDConstants;
import java.util.Locale;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VExplorer</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 17:06:14
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VExplorer.java 342 2006-10-17 23:12:42Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VExplorer extends JFrame implements Internationalizable {

    /**
     * Starting the univis explorer.
     */
    public static void main(String args[]) {

        InputStream imageStream = null;
        try {
            imageStream = VImageDummy.class.getResource("splash_screen.png").openStream();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        final VSplashScreen splashScreen = new VSplashScreen(imageStream);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VExplorer explorer = new VExplorer();
                    explorer.setVisible(true);
                    explorer.requestFocus();
                    explorer.toFront();

                    if (splashScreen != null) {
                        splashScreen.destroy();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.exit(100);
                }
            }
        });
    }

    // The logger to log info, error and other occuring messages
    // or exceptions.
    public static final transient Log LOG = LogFactory.getLog(VExplorer.class);

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = HibernateUtil.getSessionFactory().openSession().connection();
        }
        connection.commit();

        return connection;
    }

    private JToolBar toolbar = new JToolBar();

    private JSplitPane split = new JSplitPane();

    private JPanel navigation = new JPanel(new BorderLayout());
    private JPanel underStatePanel = new JPanel(new BorderLayout());
    private JPanel statePanel = new JPanel(new GridBagLayout());
    private VVisualization visualization = new VVisualization(new BorderLayout());

    private VTree tree;

    private Visualizable visualizable;
    private JScrollPane visualScrolling;

//    private Point startSelection;
//    private Point endSelection;
//    private Rectangle selection;

    private JPopupMenu chartsMenu = new JPopupMenu();
    private JPopupMenu languageMenu = new JPopupMenu();
    private JPopupMenu settingsMenu = new JPopupMenu();

    private JCheckBoxMenuItem resize = new JCheckBoxMenuItem(VIcons.ARROW_INOUT);
    private JRadioButtonMenuItem german = new JRadioButtonMenuItem(VIcons.FLAG_DE);
    private JRadioButtonMenuItem english = new JRadioButtonMenuItem(VIcons.FLAG_GB);
    private JRadioButtonMenuItem barChartHorizontal = new JRadioButtonMenuItem(VIcons.CHART_BAR_HORIZONTAL);
    private JRadioButtonMenuItem barChartVertical = new JRadioButtonMenuItem(VIcons.CHART_BAR_VERTICAL);
    private JRadioButtonMenuItem pieChart = new JRadioButtonMenuItem(VIcons.CHART_PIE);
    private JRadioButtonMenuItem areaChart = new JRadioButtonMenuItem(VIcons.CHART_AREA);
    private JRadioButtonMenuItem ringChart = new JRadioButtonMenuItem(VIcons.CHART_RING);
    private JRadioButtonMenuItem pivotTable = new JRadioButtonMenuItem(VIcons.TABLE);
    private JRadioButtonMenuItem heads = new JRadioButtonMenuItem(VIcons.STUDENTS_CASES);
    private JRadioButtonMenuItem cases = new JRadioButtonMenuItem(VIcons.STUDENTS_HEADS);
    private JRadioButtonMenuItem amount = new JRadioButtonMenuItem(VIcons.EURO);

    private JButton refresh = new JButton(VIcons.ARROW_REFRESH);
    private JButton undo = new JButton(VIcons.ARROW_UNDO);
    private JButton redo = new JButton(VIcons.ARROW_REDO);
    private JButton delete = new JButton(VIcons.DELETE);
    private JButton charts = new JButton(VIcons.CHART);
    private JButton languages = new JButton(VIcons.WORLD);
    private JButton zoomIn = new JButton(VIcons.ZOOM_IN);
    private JButton zoomOut = new JButton(VIcons.ZOOM_OUT);
    private JButton settings = new JButton(VIcons.APPLICATION_FORM_EDIT);
    private JButton rotateClockwise = new JButton(VIcons.SHAPE_ROTATE_CLOCKWISE);
    private JButton rotateAnticlockwise = new JButton(VIcons.SHAPE_ROTATE_ANTICLOCKWISE);
    private JMenuItem newMeasure = new JMenuItem(VIcons.NEW_MEASURE);
    private JMenuItem newLanguage = new JMenuItem(VIcons.NEW_WORLD);

    private String languageMessage;
    private String languageTitle;
    private boolean pivotCheck = false;

    private VLabel measureLabel;
    private VLabel chartLabel;
    private VLabel whatChartLabel;
    private VLabel languageLabel;
    private JLabel whatLanguageLabel;
    private VLabel dateLabel;
    private JLabel whatDateLabel;
    private VLabel timeLabel;
    private VClock whatTimeLabel;
    private JPanel complete;

    private Session session;

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
    public VExplorer() throws HeadlessException {
        super(ApplicationInfo.getApplicationName() + " " + ApplicationInfo.getVersionText() + " - (c) 2005-2006 a.d. - DBIS, University of Konstanz");

        try {
            setIconImage(ImageIO.read(VImageDummy.class.getResourceAsStream("UniVis_Icon.png")));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Centers the explorer on screen.
        setLocationRelativeTo(null);

        split.setDividerLocation(300);
        split.setOneTouchExpandable(true);

        session = HibernateUtil.getSessionFactory().openSession();

        initMenuBar();
        initToolbar();
        initNavigation();
        initStatePanel();
        initVisualization();
        initDragAndDrop();

        Container container = getContentPane();
        container.add(toolbar, BorderLayout.NORTH);
        container.add(split, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {

            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if (connection != null) {
                    try {
                        connection.commit();
                        connection.close();

                        session.close();
                    }
                    catch (SQLException sqle) {
                        sqle.printStackTrace();
                    }
                }
            }
        });

        /*graph.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                System.out.println("VExplorer.mousePressed");
                startSelection = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                System.out.println("VExplorer.mouseReleased");
                endSelection = e.getPoint();

                selection = new Rectangle((int) startSelection.getX(), (int) startSelection.getY(), (int) (endSelection.getX() - startSelection.getX()), (int) (endSelection.getY() - startSelection.getY()));

                graphScrollPane.scrollRectToVisible(selection);
                graph.scrollRectToVisible(selection);

                graphScrollPane.getViewport().setViewPosition(selection.getLocation());
                graphScrollPane.repaint();

                //graph.getGraph().setScale(selection.getWidth() / graphScrollPane.getViewport().getSize().getWidth());

                System.out.println("SIZE: " + graphScrollPane.getViewport().getSize());
                System.out.println("selection = " + selection);
            }
        });*/

        ComponentUtilities.repaintComponentTree(this);
    }

    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        VMenu program = new VMenu(Constants.PROGRAM);

        VMenuItem schemaImport = new VMenuItem(Constants.SCHEMA_IMPORT, VIcons.SCHEMA_IMPORT);
        schemaImport.setAccelerator(KeyStroke.getKeyStroke('I', Event.CTRL_MASK));
        schemaImport.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.addChoosableFileFilter(new FileFilter() {

                    /**
                     * Whether the given file is accepted by this filter.
                     */
                    public boolean accept(File f) {
                        return f.getName().endsWith(".vs.xml") || f.isDirectory();
                    }

                    /**
                     * The description of this filter. For example: "JPG and GIF Images"
                     *
                     * @see javax.swing.filechooser.FileView#getName
                     */
                    public String getDescription() {
                        return "UniVis Schema (*.vs.xml)";
                    }
                });

                int option = fileChooser.showOpenDialog(VExplorer.this);

                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    new SchemaImport(file);
                    refreshTree();
                    JOptionPane.showMessageDialog(VExplorer.this, "Schema Import erfolgreich beendet.", "Schema Import", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        program.add(schemaImport);
        program.addSeparator();

        VMenuItem exit = new VMenuItem(Constants.EXIT, VIcons.EXIT);
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', Event.ALT_MASK));
        exit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        program.add(exit);

        VMenu lafMenu = new VMenu(Constants.LOOK_AND_FEEL);

        ButtonGroup lafGroup = new ButtonGroup();
        for (final UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem lafMenuItem = new JRadioButtonMenuItem(laf.getName());

            // Set the current Look And Feel as selected.
            if (UIManager.getLookAndFeel().getName().equals(laf.getName())) {
                lafMenuItem.setSelected(true);
            }

            lafMenuItem.addActionListener(new ActionListener() {
                /**
                 * Invoked when an action occurs.
                 */
                public void actionPerformed(ActionEvent e) {
                    updateLookAndFeel(laf.getClassName());
                }
            });

            lafGroup.add(lafMenuItem);
            lafMenu.add(lafMenuItem);
        }

        JMenu questionMark = new JMenu("?");

        VMenuItem license = new VMenuItem(Constants.LICENSE);
        license.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                new LicenseDialog(VExplorer.this);
            }
        });
        questionMark.add(license);

        final VMenuItem about = new VMenuItem(Constants.ABOUT, VIcons.INFORMATION);
        about.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                new AboutPanel(VExplorer.this);
            }
        });
        questionMark.add(about);

        menuBar.add(program);
        menuBar.add(lafMenu);
        menuBar.add(questionMark);

        setJMenuBar(menuBar);
    }

    private void updateLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        ComponentUtilities.repaintComponentTree(this);
    }

    private void initStatePanel() {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        chartLabel = new VLabel(Constants.CHART);
        whatChartLabel = new VLabel(Constants.BAR_CHART_HORIZONTAL);
        complete = new JPanel();
        complete.add(chartLabel);
        complete.add(whatChartLabel);
        statePanel.add(complete, gbc);

        measureLabel = new VLabel(Constants.MEASURE);
        complete = new JPanel();
        complete.add(measureLabel);
        complete.add(tree.getWhatMeasureLabel());
        ++gbc.gridy;
        statePanel.add(complete, gbc);

        languageLabel = new VLabel(Constants.LANGUAGE);
        whatLanguageLabel = new JLabel(Locale.GERMAN.getDisplayName());
        complete = new JPanel();
        complete.add(languageLabel);
        complete.add(whatLanguageLabel);
        ++gbc.gridy;
        statePanel.add(complete, gbc);

        timeLabel = new VLabel(Constants.TIME);
        whatTimeLabel = new VClock();
        complete = new JPanel();
        complete.add(timeLabel);
        complete.add(whatTimeLabel);
        ++gbc.gridy;
        statePanel.add(complete, gbc);

        dateLabel = new VLabel(Constants.DATE);
        whatDateLabel = new JLabel(whatTimeLabel.getDate());
        complete = new JPanel();
        complete.add(dateLabel);
        complete.add(whatDateLabel);
        ++gbc.gridy;
        statePanel.add(complete, gbc);

        StackedBox box = new StackedBox();
        box.addBox("Status", statePanel, BorderLayout.WEST);
        underStatePanel.add(box, BorderLayout.CENTER);
    }

    private void initToolbar() {

        refresh.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {

                    /**
                     * When an object implementing interface <code>Runnable</code> is used
                     * to create a thread, starting the thread causes the object's
                     * <code>run</code> method to be called in that separately executing
                     * thread.
                     * <p/>
                     * The general contract of the method <code>run</code> is that it may
                     * take any action whatsoever.
                     *
                     * @see Thread#run()
                     */
                    public void run() {
                        refreshTree();
                    }
                };

                System.out.println("ECHO1");

                SwingUtilities.invokeLater(runnable);

                System.out.println("ECHO3");
            }
        });


        makeChartsMenu();
        makeSettingsMenu();
        makeLanguageMenu();

        charts.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                chartsMenu.show(charts, 0, charts.getHeight());
            }
        });

        settings.addMouseListener(new MouseAdapter() {

            /**
             * Invoked when the mouse has been clicked on a component.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                settingsMenu.show(settings, 0, settings.getHeight());
            }
        });

        languages.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                languageMenu.show(languages, 0, languages.getHeight());
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizable.clear();
                tree.updateUI();
            }
        });

        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizable.zoomIn();
            }
        });

        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizable.zoomOut();
            }
        });

        rotateClockwise.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                visualizable.rotateRight();
            }
        });

        rotateAnticlockwise.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                visualizable.rotateLeft();
            }
        });

        undo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(undo)) {
                    visualizable.undo();
                    tree.updateUI();
                }
            }
        });

        toolbar.add(refresh);
        toolbar.add(undo);
        toolbar.add(redo);
        toolbar.add(delete);
        toolbar.addSeparator();
        toolbar.add(charts);
        toolbar.add(zoomIn);
        toolbar.add(zoomOut);
        toolbar.add(rotateClockwise);
        toolbar.add(rotateAnticlockwise);
        toolbar.addSeparator();
        toolbar.add(settings);
        toolbar.addSeparator();
        toolbar.add(languages);
    }

    private void makeChartsMenu() {

        makeActionListenerCharts(barChartHorizontal, ChartType.BAR_CHART_HORIZONTAL, "barChartHorizontal");
        makeActionListenerCharts(barChartVertical, ChartType.BAR_CHART_VERTICAL, "barChartVertical");
        makeActionListenerCharts(pieChart, ChartType.PIE_CHART, "pieChart");
        makeActionListenerCharts(areaChart, ChartType.AREA_CHART, "areaChart");
        makeActionListenerCharts(ringChart, ChartType.RING_CHART, "ringChart");

        pivotTable.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {

                if (pivotCheck == false) {
                    visualizable.clear();
                }
                whatChartLabel.setI18NKey("pivottable");
                setVisualization(new VPivotTable());
                pivotCheck = true;
            }
        });

        ButtonGroup charts = new ButtonGroup();
        barChartHorizontal.setSelected(true);
        charts.add(barChartHorizontal);
        charts.add(barChartVertical);
        charts.add(pieChart);
        charts.add(areaChart);
        charts.add(ringChart);
        charts.add(pivotTable);

        chartsMenu.add(barChartHorizontal);
        chartsMenu.add(barChartVertical);
        chartsMenu.add(pieChart);
        chartsMenu.add(areaChart);
        chartsMenu.add(ringChart);
        chartsMenu.add(pivotTable);
    }

    private void makeSettingsMenu() {

        resize.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                visualizable.setMoveable(!visualizable.isMoveable());

                if (visualizable instanceof Component) {
                    ComponentUtilities.repaintComponentTree((Component) VExplorer.this.visualizable);
                }
            }
        });

        settingsMenu.add(resize);
    }

    private void makeLanguageMenu() {

        german.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Locale locale = Locale.GERMAN;
                MessageResolver.setLocale(locale);
                whatLanguageLabel.setText(locale.getDisplayName(locale));
                ComponentUtilities.repaintComponentTree(VExplorer.this);
            }
        });

        english.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Locale locale = Locale.ENGLISH;
                MessageResolver.setLocale(locale);
                whatLanguageLabel.setText(locale.getDisplayName(locale));
                ComponentUtilities.repaintComponentTree(VExplorer.this);
            }
        });

        newLanguage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(VExplorer.this.getContentPane(), languageMessage, languageTitle, 1);
            }
        });

        final ButtonGroup languageGroup = new ButtonGroup();
        german.setSelected(true);
        languageGroup.add(german);
        languageGroup.add(english);
        languageMenu.add(german);
        languageMenu.add(english);
        languageMenu.add(newLanguage);
    }

    private void initNavigation() {

        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        //Session session = sessionFactory.openSession();

        VDiceBox diceBox = (VDiceBox) session.createQuery("from " + VDiceBoxImpl.class.getName() + " where name = 'UniVis Explorer'").uniqueResult();

        if (diceBox == null) {
            tree = new VTree();
        }
        else {
            tree = new VTree(diceBox);
        }

        //session.close();

        navigation.add(new JScrollPane(tree), BorderLayout.CENTER);
        navigation.add(underStatePanel, BorderLayout.SOUTH);

        split.setLeftComponent(navigation);
    }

    private void initVisualization() {
        setVisualization(new VGraph());
    }

    private void setVisualization(Visualizable visualizable) {

        // Set visualizable for interactive operations.
        this.visualizable = visualizable;

        visualization.removeAll();

        if (visualizable instanceof Component) {
            visualScrolling = new JScrollPane((Component) visualizable);
        }

        visualization.setBackground(Color.WHITE);
        visualization.add(visualScrolling, BorderLayout.CENTER);

        split.setRightComponent(visualization);
        split.validate();
    }

    private void initDragAndDrop() {
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.addDragSourceListener(tree);
        dragSource.createDefaultDragGestureRecognizer(tree, DnDConstants.ACTION_COPY, tree);
    }

    public static void publishException(Exception e) {
        System.out.println("REMOVED BECAUSE SHOULDN'T BE STATIC EXPLORER");
        //IncidentInfo info = new IncidentInfo("Header", e.getMessage(), "asdfa\nasdfasdf\nasdfsadf\n\nsadfa", e);
        //JXErrorDialog.showDialog(explorer, info);
        //new VDialog(explorer, e);
    }

    /**
     * @param radioButtonMenuItem Item which gets the Listener.
     * @param chartType           The type of the current chart.
     */
    public void makeActionListenerCharts(final JRadioButtonMenuItem radioButtonMenuItem, final ChartType chartType, final String i18NKey) {

        radioButtonMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (visualizable instanceof VGraph) {
                    ((VGraph) visualizable).setChartType(chartType);
                }
                else {
                    VGraph graph = new VGraph();
                    if (pivotCheck == true) {
                        visualizable.clear();
                        pivotCheck = false;
                    }
                    graph.setChartType(chartType);
                    setVisualization(graph);
                }

                whatChartLabel.setI18NKey(i18NKey);
            }
        });
    }

    private void refreshTree() {
        System.out.println("ECHO2");

        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        //Session session = sessionFactory.openSession();
        VDiceBox diceBox = (VDiceBox) session.createQuery("from " + VDiceBoxImpl.class.getName() + " where name = 'UniVis Explorer'").uniqueResult();

        if (diceBox != null) {
            tree.refresh(diceBox);
        }

        //session.close();
    }

    public void internationalize() {
        // Sets the text for the MenuItems.
        german.setText(MessageResolver.getMessage(Constants.GERMAN));
        english.setText(MessageResolver.getMessage(Constants.ENGLISH));
        barChartHorizontal.setText(MessageResolver.getMessage(Constants.BAR_CHART));
        barChartVertical.setText(MessageResolver.getMessage(Constants.BAR_CHART));
        pieChart.setText(MessageResolver.getMessage(Constants.PIE_CHART));
        areaChart.setText(MessageResolver.getMessage(Constants.AREA_CHART));
        ringChart.setText(MessageResolver.getMessage(Constants.RING_CHART));
        pivotTable.setText(MessageResolver.getMessage(Constants.PIVOT_TABLE));
        heads.setText(MessageResolver.getMessage(Constants.HEADS));
        cases.setText(MessageResolver.getMessage(Constants.CASES));
        amount.setText(MessageResolver.getMessage(Constants.AMOUNT));
        resize.setText(MessageResolver.getMessage(Constants.GRAPH_EDITABLE));

        // Sets the tooltip for the Buttons.
        settings.setToolTipText(MessageResolver.getMessage(Constants.SETTINGS_TOOLTIP));
        resize.setToolTipText(MessageResolver.getMessage(Constants.GRAPH_EDITABLE_TOOLTIP));
        refresh.setToolTipText(MessageResolver.getMessage(Constants.REFRESH_TOOLTIP));
        undo.setToolTipText(MessageResolver.getMessage(Constants.UNDO_TOOLTIP));
        redo.setToolTipText(MessageResolver.getMessage(Constants.REDO_TOOLTIP));
        delete.setToolTipText(MessageResolver.getMessage(Constants.DELETE_TOOLTIP));
        charts.setToolTipText(MessageResolver.getMessage(Constants.CHARTS_TOOLTIP));
        newMeasure.setText(MessageResolver.getMessage(Constants.NEW_MEASURE));
        newMeasure.setToolTipText(MessageResolver.getMessage(Constants.NEW_MEASURE_TOOLTIP));
        newLanguage.setText(MessageResolver.getMessage(Constants.NEW_LANGUAGE));
        newLanguage.setToolTipText(MessageResolver.getMessage(Constants.NEW_LANGUAGE));
        languages.setToolTipText(MessageResolver.getMessage(Constants.LANGUAGES_TOOLTIP));
        zoomIn.setToolTipText(MessageResolver.getMessage(Constants.ZOOM_IN_TOOLTIP));
        zoomOut.setToolTipText(MessageResolver.getMessage(Constants.ZOOM_OUT_TOOLTIP));
        rotateClockwise.setToolTipText(MessageResolver.getMessage(Constants.ROTATE_CLOCKWISE_TOOLTIP));
        rotateAnticlockwise.setToolTipText(MessageResolver.getMessage(Constants.ROTATE_ANTICLOCKWISE_TOOLTIP));
        languageMessage = MessageResolver.getMessage(Constants.NEW_LANGUAGE_MESSAGE);
        languageTitle = MessageResolver.getMessage(Constants.NEW_LANGUAGE);
    }
}