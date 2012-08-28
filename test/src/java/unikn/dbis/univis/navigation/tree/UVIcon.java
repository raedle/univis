package unikn.dbis.univis.navigation.tree;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>UVIcon</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 09.04.2006
 * Time: 01:20:51
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: UVIcon.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class UVIcon extends JComponent {

    private Icon icon;

    public UVIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p/>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoker super's implementation you must honor the opaque property,
     * that is
     * if this component is opaque, you must completely fill in the background
     * in a non-opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p/>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see javax.swing.plaf.ComponentUI
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        icon.paintIcon(this, g, 10, 0);
    }

    /**
     * Returns the current width of this component.
     * This method is preferable to writing
     * <code>component.getBounds().width</code>, or
     * <code>component.getSize().width</code> because it doesn't cause any
     * heap allocations.
     *
     * @return the current width of this component
     */
    @Override
    public int getWidth() {
        return icon.getIconWidth() + 10;
    }

    /**
     * Returns the current height of this component.
     * This method is preferable to writing
     * <code>component.getBounds().height</code>, or
     * <code>component.getSize().height</code> because it doesn't cause any
     * heap allocations.
     *
     * @return the current height of this component
     */
    @Override
    public int getHeight() {
        return icon.getIconHeight();
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }
}