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
package unikn.dbis.univis.dataexchange.schemaimport;

import org.dom4j.io.SAXReader;
import org.dom4j.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.awt.*;

import unikn.dbis.univis.meta.*;
import unikn.dbis.univis.meta.impl.*;
import unikn.dbis.univis.hibernate.util.HibernateUtil;
import unikn.dbis.univis.hibernate.TransactionCallback;

/**
 * TODO: document me!!!
 * <p/>
 * <code>SchemaImport</code>.
 * <p/>
 * User: raedler
 * Date: 27.09.2006
 * Time: 15:30:30
 *
 * @author Roman R&auml;dle
 * @version $Id: SchemaImport.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.3
 */
public class SchemaImport {

    private Document document;

    private SessionFactory sf;

    public SchemaImport(File file) {

        SAXReader reader = new SAXReader(false);

        try {
            document = reader.read(file);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }

        sf = HibernateUtil.getSessionFactory();

        importDimensions();
        importMeasures();
        importFunctions();
        importCubes();

        sf.close();
    }

    private void importDimensions() {

        String prefix = "/DiceBox/Dimensions/Dimension";

        //noinspection unchecked
        List<Element> list = document.selectNodes(prefix);

        for (int i = 1; i <= list.size(); i++) {
            final VDimension dimension = new VDimensionImpl();

            dimension.setKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@key"));
            dimension.setI18nKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@i18n"));
            dimension.setDragable(ImportUtil.getValue(document, Boolean.class, prefix + "[" + i + "]/@dragable", Boolean.FALSE));
            dimension.setTableName(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@table"));
            dimension.setForeignKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@foreignKey"));
            dimension.setVisible(ImportUtil.getValue(document, Boolean.class, prefix + "[" + i + "]/@visible", Boolean.TRUE));
            dimension.setDependent(ImportUtil.getValue(document, Boolean.class, prefix + "[" + i + "]/@dependent", Boolean.FALSE));

            HibernateUtil.execute(new TransactionCallback() {
                public void execute(Session session, Transaction trx) throws RuntimeException {
                    session.saveOrUpdate(dimension);
                }
            });
        }
    }

    private void importMeasures() {

        String prefix = "/DiceBox/Measures/Measure";

        //noinspection unchecked
        List<Element> list = document.selectNodes(prefix);

        for (int i = 1; i <= list.size(); i++) {
            final VMeasure measure = new VMeasureImpl();

            measure.setKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@key"));
            measure.setI18nKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@i18n"));
            measure.setColumn(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@column"));

            HibernateUtil.execute(new TransactionCallback() {
                public void execute(Session session, Transaction trx) throws RuntimeException {
                    session.saveOrUpdate(measure);
                }
            });
        }
    }

    private void importFunctions() {

        String prefix = "/DiceBox/Functions/Function";

        //noinspection unchecked
        List<Element> list = document.selectNodes(prefix);

        for (int i = 1; i <= list.size(); i++) {
            final VFunction function = new VFunctionImpl();

            function.setKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@key"));
            function.setI18nKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@i18n"));
            function.setDefinition(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@definition"));

            HibernateUtil.execute(new TransactionCallback() {
                public void execute(Session session, Transaction trx) throws RuntimeException {
                    session.saveOrUpdate(function);
                }
            });
        }
    }

    private Session session;

    private void importCubes() {

        session = sf.openSession();

        String prefix = "/DiceBox/Cubes/Cube";

        VDiceBox diceBox = new VDiceBoxImpl();
        diceBox.setName("UniVis Explorer");

        //noinspection unchecked
        List<Element> list = document.selectNodes(prefix);

        for (int i = 1; i <= list.size(); i++) {
            VCube cube = new VCubeImpl();

            cube.setKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@key"));
            cube.setI18nKey(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@i18n"));
            cube.setTableName(ImportUtil.getValue(document, String.class, prefix + "[" + i + "]/@table"));
            cube.setColor(new Color(ImportUtil.getValue(document, Integer.class, prefix + "[" + i + "]/@color")));

            Transaction trx = session.beginTransaction();
            session.saveOrUpdate(cube);
            trx.commit();

            VHierarchy hierarchy = new VHierarchyImpl();
            hierarchy.setDataReference(cube);
            hierarchy.setParent(diceBox);
            diceBox.getChildren().add(hierarchy);

            VHierarchy classification = new VHierarchyImpl();

            VClassification clazz = new VClassificationImpl();
            clazz.setKey("cls:dimension_" + i);
            clazz.setI18nKey("dimensions");
            clazz.setType("dimension");

            classification.setDataReference(clazz);
            classification.setParent(hierarchy);
            hierarchy.getChildren().add(classification);

            test(prefix, classification, i, "/Dimension-Ref", cube);

            // #############################################

            classification = new VHierarchyImpl();

            clazz = new VClassificationImpl();
            clazz.setKey("cls:measure_" + i);
            clazz.setI18nKey("measures");
            clazz.setType("measure");

            classification.setDataReference(clazz);
            classification.setParent(hierarchy);
            hierarchy.getChildren().add(classification);

            test(prefix, classification, i, "/Measure-Ref", cube);

            // #############################################

            classification = new VHierarchyImpl();

            clazz = new VClassificationImpl();
            clazz.setKey("cls:function_" + i);
            clazz.setI18nKey("functions");
            clazz.setType("function");

            classification.setDataReference(clazz);
            classification.setParent(hierarchy);
            hierarchy.getChildren().add(classification);

            test(prefix, classification, i, "/Function-Ref", cube);

            // #############################################

            trx = session.beginTransaction();
            session.saveOrUpdate(cube);
            trx.commit();
        }

        Transaction trx = session.beginTransaction();
        session.saveOrUpdate(diceBox);
        trx.commit();

        session.close();
    }

    private void test(String prefix, VHierarchy hierarchy, int i, String suffix, VCube cube) {

        List liste = document.selectNodes(prefix + "[" + i + "]" + suffix);
        for (Object o : liste) {
            if (o instanceof Element) {

                Element ref = (Element) o;
                String key = ref.attribute("key").getText();

                VDataReference dataReference = (VDataReference) session.createQuery("from " + VDataReferenceImpl.class.getName() + " where key = '" + key + "'").uniqueResult();

                if (dataReference instanceof VDimension) {
                    ((VDimension) dataReference).getSupportedCubes().add(cube);
                }

                System.out.println("((VCubeImpl) cube).getId() = " + ((VCubeImpl) cube).getId());
                System.out.println("dataReference = " + dataReference);

                VHierarchy child = new VHierarchyImpl();
                child.setDataReference(dataReference);
                child.setParent(hierarchy);
                hierarchy.getChildren().add(child);

                Transaction trx = session.beginTransaction();
                session.saveOrUpdate(hierarchy);
                session.saveOrUpdate(child);
                trx.commit();

                print((Element) o, child, cube);
            }
        }
    }

    public void print(Element element, VHierarchy parent, VCube cube) {

        for (Iterator iter = element.elementIterator(); iter.hasNext();) {

            Object next = iter.next();

            if (next instanceof Element) {

                Element ref = (Element) next;
                String key = ref.attribute("key").getText();

                VDimension dimension = (VDimension) session.createQuery("from " + VDataReferenceImpl.class.getName() + " where key = '" + key + "'").uniqueResult();

                dimension.getSupportedCubes().add(cube);

                System.out.println("dimension = " + dimension);
                System.out.println("cube = " + cube);
                System.out.println("((VCubeImpl) cube).getId() = " + ((VCubeImpl) cube).getId());

                //session = sf.openSession();
                VHierarchy hierarchy = new VHierarchyImpl();
                hierarchy.setDataReference(dimension);
                hierarchy.setParent(parent);
                parent.getChildren().add(hierarchy);

                print(ref, hierarchy, cube);
            }
        }
    }

    public static void main(String[] args) {
        new SchemaImport(new File("d:/Projects/UniVis Explorer/db-schema/sample.vs.xml"));
    }
}
