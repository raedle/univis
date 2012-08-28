package unikn.dbis.univis.visualization.graph;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

/**
 * TODO: document me!!!
 * <p/>
 * <code>MyCellViewFactory</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 11.04.2006
 * Time: 23:05:18
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: MyCellViewFactory.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class MyCellViewFactory extends DefaultCellViewFactory {

    /**
     * Constructs a view for the specified cell and associates it
     * with the specified object using the specified CellMapper.
     *
     * @param cell reference to the object in the model
     */
    public CellView createView(GraphModel model, Object cell) {

        CellView view = null;

        if (model.isPort(cell)) {
            view = createPortView(cell);
        }
        else if (model.isEdge(cell)) {
            view = createEdgeView(cell);
        }
        else {
            view = createVertexView(cell);
        }

        return view;
    }

    /**
     * Constructs a VertexView view for the specified object.
     */
    @Override
    protected VertexView createVertexView(Object cell) {
        return new MyVertexView(cell);
    }
}