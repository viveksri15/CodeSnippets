package in.viveksrivastava.random;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphVisualizer extends JFrame {

    private static final long serialVersionUID = -2363125914587687720L;

    public void visualize(Integer[] bestSolution, ArrayList<double[]> coorinates) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        Object v1 = null, vl = null;
        graph.getModel().beginUpdate();

        {
            double x1 = coorinates.get(bestSolution[0])[0];
            double y1 = coorinates.get(bestSolution[0])[1];
            double x = x1 * 10 + 50;
            double y = y1 * 10 + 50;
            v1 = graph.insertVertex(parent, null, bestSolution[0] + "," + x1
                    + "," + y1, x, y, 10, 10);
            vl = v1;
        }

        for (int i = 1; i < bestSolution.length; i++) {
            double x1 = coorinates.get(bestSolution[i])[0];
            double y1 = coorinates.get(bestSolution[i])[1];
            double x = x1 / 2;
            double y = y1 / 2;
            double x2 = coorinates.get(bestSolution[i - 1])[0];
            double y2 = coorinates.get(bestSolution[i - 1])[1];
            /*System.err.println("PLOTTING " + bestSelected[i] + "=" + +x1 + " "
                    + y1 + " " + bestSelected[i - 1] + " " + x2 + " " + y2);*/
            /*Object v2 = graph.insertVertex(parent, null, bestSolution[i] + ","
					+ x1 + "," + y1, x, y, 10, 10);*/
            Object v2 = graph.insertVertex(parent, null, bestSolution[i] + "",
                    x, y, 10, 10);
            graph.insertEdge(parent, null, "", v1, v2);
            v1 = v2;
        }
        graph.insertEdge(parent, null, "", v1, vl);
        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}