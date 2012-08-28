package unikn.dbis.univis.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Paul Chef
 * Date: 08.04.2006
 * Time: 19:28:40
 * To change this template use File | Settings | File Templates.
 */
public class CubeLabel extends JLabel {

    public CubeLabel(Color color, String desc, boolean threeD, int width, int height, int start) {
        this.setIcon(new VisualCube(color, threeD, width, height, start));
        this.setText(desc);
        this.setIconTextGap(3);
    }
}
