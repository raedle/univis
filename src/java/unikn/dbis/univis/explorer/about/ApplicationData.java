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
package unikn.dbis.univis.explorer.about;

import javax.swing.*;

import unikn.dbis.univis.ApplicationInfo;
import unikn.dbis.univis.images.VImageDummy;

/**
 * TODO: document me!!!
 * <p/>
 * <code>ApplicationData</code>.
 * <p/>
 * User: raedler
 * Date: 17.10.2006
 * Time: 23:59:31
 *
 * @author Roman R&auml;dle
 * @version $Id: ApplicationData.java 342 2006-10-17 23:12:42Z raedler $
 * @since UniVis Explorer 0.3.1.0
 */
public class ApplicationData extends javax.swing.JPanel {

    private javax.swing.JPanel authors;
    private javax.swing.JLabel authorsL;
    private javax.swing.JLabel headlineL;
    private javax.swing.JLabel java;
    private javax.swing.JLabel javaHome;
    private javax.swing.JLabel javaHomeL;
    private javax.swing.JLabel javaL;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel operatingSystem;
    private javax.swing.JLabel operatingSystemL;
    private javax.swing.JLabel productVersion;
    private javax.swing.JLabel productVersionL;
    private javax.swing.JLabel vendor;
    private javax.swing.JLabel vendorL;
    private javax.swing.JLabel vm;
    private javax.swing.JLabel vmL;

    /** Creates new form ApplicationData */
    public ApplicationData() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        logo = new javax.swing.JLabel();
        headlineL = new javax.swing.JLabel();
        productVersionL = new javax.swing.JLabel();
        operatingSystemL = new javax.swing.JLabel();
        javaL = new javax.swing.JLabel();
        vmL = new javax.swing.JLabel();
        vendorL = new javax.swing.JLabel();
        javaHomeL = new javax.swing.JLabel();
        authors = new javax.swing.JPanel();
        authorsL = new javax.swing.JLabel();
        productVersion = new javax.swing.JLabel();
        operatingSystem = new javax.swing.JLabel();
        java = new javax.swing.JLabel();
        vm = new javax.swing.JLabel();
        vendor = new javax.swing.JLabel();
        javaHome = new javax.swing.JLabel();

        org.jdesktop.layout.GroupLayout logoLayout = new org.jdesktop.layout.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 50, Short.MAX_VALUE)
        );
        logoLayout.setVerticalGroup(
            logoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 50, Short.MAX_VALUE)
        );

        headlineL.setFont(new java.awt.Font("Tahoma", 0, 18));
        headlineL.setText(ApplicationInfo.getApplicationName() + " Information");

        productVersionL.setFont(new java.awt.Font("Tahoma", 1, 11));
        productVersionL.setText("Product Version:");

        operatingSystemL.setFont(new java.awt.Font("Tahoma", 1, 11));
        operatingSystemL.setText("Operating System:");

        javaL.setFont(new java.awt.Font("Tahoma", 1, 11));
        javaL.setText("Java:");

        vmL.setFont(new java.awt.Font("Tahoma", 1, 11));
        vmL.setText("VM:");

        vendorL.setFont(new java.awt.Font("Tahoma", 1, 11));
        vendorL.setText("Vendor:");

        javaHomeL.setFont(new java.awt.Font("Tahoma", 1, 11));
        javaHomeL.setText("Java Home:");

        authors.setLayout(new javax.swing.BoxLayout(authors, javax.swing.BoxLayout.Y_AXIS));

        authorsL.setFont(new java.awt.Font("Tahoma", 1, 11));
        authorsL.setText("Author(s):");

        logo.setIcon(new ImageIcon(VImageDummy.class.getResource("univis_explorer_logo.png")));
        productVersion.setText(ApplicationInfo.getApplicationName() + " " + ApplicationInfo.getVersion() + " (#build: " + ApplicationInfo.getBuildtime() + ")");
        operatingSystem.setText(System.getProperty("os.name") + " version " + System.getProperty("os.version") + " running on " + System.getProperty("os.arch"));
        java.setText(System.getProperty("java.version"));
        vm.setText(System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version"));
        vendor.setText(System.getProperty("java.vm.vendor"));
        javaHome.setText(System.getProperty("java.home"));

        for (String author : ApplicationInfo.getAuthors()) {
            authors.add(new JLabel(author));
        }

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(logo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, headlineL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(productVersionL)
                            .add(authorsL)
                            .add(operatingSystemL)
                            .add(javaL)
                            .add(vmL)
                            .add(vendorL)
                            .add(javaHomeL))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(javaHome)
                            .add(layout.createSequentialGroup()
                                .add(vendor)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 148, Short.MAX_VALUE))
                            .add(vm)
                            .add(java)
                            .add(operatingSystem)
                            .add(authors, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .add(productVersion))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(logo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(25, 25, 25)
                        .add(headlineL)))
                .add(3, 3, 3)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(productVersionL)
                    .add(productVersion))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(operatingSystemL)
                    .add(operatingSystem))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(javaL)
                    .add(java))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(vmL)
                    .add(vm))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(vendorL)
                    .add(vendor))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(javaHomeL)
                    .add(javaHome))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(authorsL)
                    .add(authors, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
}

