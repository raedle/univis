package unikn.dbis.univis.gui;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// TODO: document me!!!

/**
 * ObjectTransferable.
 * <p/>
 * User: weiler
 * Date: 29.11.2005
 * Time: 22:11:50
 *
 * @author Andreas Weiler
 * @version $Id: ObjectTransferable.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 1.0
 */
public class ObjectTransferable implements Transferable {

    public static final DataFlavor OBJECT_DATA_FLAVOR = new DataFlavor(ObjectTransferable.class, "OBJECT_DATA_FLAVOR");

    private DataFlavor[] flavors = { OBJECT_DATA_FLAVOR };

    private Map<DataFlavor, Object> flavoredObjects = new HashMap<DataFlavor, Object>();

    public ObjectTransferable() {
       //flavoredObjects.put(dataFlavor, o);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {

        for (DataFlavor dataFlavor : flavors) {
            if (dataFlavor.equals(flavor)) {
                return true;
            }
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        Object o = flavoredObjects.get(flavor);

        if (o != null) {
            return o;
        }

        throw new UnsupportedFlavorException(flavor);
    }
}
