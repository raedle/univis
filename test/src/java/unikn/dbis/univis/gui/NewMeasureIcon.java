package unikn.dbis.univis.gui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * NewMeasureIcon.
 * <p/>
 * User: raedler, weiler
 * Date: 10.04.2006
 * Time: 20:24:36
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: NewMeasureIcon.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class NewMeasureIcon implements Icon {
    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.setColor(Color.BLACK);
        g.drawRect(0, 2, 10, 10);
        g.drawString("?", 2, 11);
    }

    public int getIconWidth() {
        return 13;
    }

    public int getIconHeight() {
        return 13;
    }
}