package unikn.dbis.univis.navigation.tree;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>VCubeFlagIcon</code>.
 * <p/>
 * User: raedler
 * Date: 03.11.2005
 * Time: 14:46:40
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: VCubeFlagIcon.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class VCubeFlagIcon implements Icon {

    private Color flagColor;

    public VCubeFlagIcon(Color flagColor) {
        this.flagColor = flagColor;
    }

    /**
     * Draw the icon at the specified location.  Icon implementations
     * may use the Component argument to get properties useful for
     * painting, e.g. the foreground or background color.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 3, 11);

        g.setColor(flagColor);
        g.fillRect(x, y, 2, 10);
    }

    /**
     * Returns the icon's width.
     *
     * @return an int specifying the fixed width of the icon.
     */
    public int getIconWidth() {
        return 5;
    }

    /**
     * Returns the icon's height.
     *
     * @return an int specifying the fixed height of the icon.
     */
    public int getIconHeight() {
        return 12;
    }
}