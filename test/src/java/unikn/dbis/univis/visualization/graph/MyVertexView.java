package unikn.dbis.univis.visualization.graph;

import org.jgraph.graph.VertexView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.CellView;
import org.jgraph.JGraph;

import javax.swing.*;
import java.awt.*;

/**
 * TODO: document me!!!
 * <p/>
 * <code>MyVertexView</code>.
 * <p/>
 * User: raedler, weiler
 * Date: 11.04.2006
 * Time: 23:09:00
 *
 * @author Roman R&auml;dle
 * @author Andreas Weiler
 * @version $Id: MyVertexView.java 338 2006-10-08 23:11:30Z raedler $
 * @since UniVis Explorer 0.1
 */
public class MyVertexView extends VertexView {

    protected static MyRenderer renderer = new MyRenderer();

    /**
     * Constructs a vertex view for the specified model object and the specified
     * child views.
     *
     * @param cell reference to the model object
     */
    public MyVertexView(Object cell) {
        super(cell);
    }

    /**
     * Returns a renderer for the class.
     */
    @Override
    public CellViewRenderer getRenderer() {
        return renderer;
    }

    public static class MyRenderer extends JTree implements CellViewRenderer {
        /**
         * Configure and return the renderer based on the passed in
         * components. The value is typically set from messaging the
         * graph with <code>convertValueToString</code>.
         * We recommend you check the value's class and throw an
         * illegal argument exception if it's not correct.
         *
         * @param graph   the graph that that defines the rendering context.
         * @param view    the view that should be rendered.
         * @param sel     whether the object is selected.
         * @param focus   whether the object has the focus.
         * @param preview whether we are drawing a preview.
         * @return the component used to render the value.
         */
        public Component getRendererComponent(JGraph graph, CellView view, boolean sel, boolean focus, boolean preview) {

            return this;
        }
    }
}