package unikn.dbis.univis.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * VisualLine.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 20:14:14
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VisualLine.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VisualLine implements Icon {

      public VisualLine() {

    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 2);
    }

    public int getIconWidth() {
        return 100;
    }

    public int getIconHeight() {
        return 2;
    }
}