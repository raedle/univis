package unikn.dbis.univis.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * VisualCube.
 * <p/>
 * User: raedler, weiler
 * Date: 08.04.2006
 * Time: 19:19:30
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VisualCube.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VisualCube implements Icon {

    private Color cubeColor;
    private boolean threeD;
    private int width;
    private int height;
    private int start;

    public VisualCube(Color cubeColor, boolean threeD, int width, int height, int start) {
        this.cubeColor = cubeColor;
        this.threeD = threeD;
        this.width = width;
        this.height = height;
        this.start = start;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(cubeColor);
        g.fillRect(0, start, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRect(0, start, 10 ,10);

        if (threeD == true) {
        g.setColor(cubeColor);
        Polygon poly1 = new Polygon();
        poly1.addPoint(0,10);
        poly1.addPoint(5,5);
        poly1.addPoint(15,5);
        poly1.addPoint(10,10);
        g.fillPolygon(poly1);
        g.setColor(Color.BLACK);
        g.drawPolygon(poly1);

        g.setColor(cubeColor);
        Polygon poly2 = new Polygon();
        poly2.addPoint(10,20);
        poly2.addPoint(15,15);
        poly2.addPoint(15,5);
        poly2.addPoint(10,10);
        g.fillPolygon(poly2);
        g.setColor(Color.BLACK);
        g.drawPolygon(poly2);


        }
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }
    
}