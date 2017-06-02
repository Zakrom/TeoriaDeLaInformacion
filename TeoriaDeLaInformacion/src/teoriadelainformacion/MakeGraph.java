/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

/**
 *
 * @author Edgar Valdes
 */
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MakeGraph extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2707712944901661771L;

    public MakeGraph(Tree tree) {
        super("Binary Tree");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        Object v = graph.insertVertex(parent, null, tree.frequency + "", 490, 20, 40,
                30);
        Integer x = 520;
        Integer y = 20;
        Integer move = x / 2;
        if (tree instanceof Node) {
            generateTree(((Node) tree).right, graph, parent, v, x + move - 40, y + 50, "1", move);
            generateTree(((Node) tree).left, graph, parent, v, x - move + 40, y + 50, "0", move);
        }

        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public static void graph(Tree tree) {
        MakeGraph frame = new MakeGraph(tree);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 640);
        frame.setVisible(true);
    }

    private void generateTree(Tree tree, mxGraph graph, Object parent, Object v, Integer x, Integer y, String dir, Integer moves) {

        if (tree instanceof Node) {
            Object newV = graph.insertVertex(parent, null, "" + tree.frequency, x, y, 80, 40);
            graph.insertEdge(parent, null, dir, v, newV);

            Tree left = ((Node) tree).left;
            Tree right = ((Node) tree).right;
            Integer move = moves / 2;
            //Left Branch
            generateTree(left, graph, parent, newV, x - move + 40, y + 50, "0", move);

            //Right Branch
            generateTree(right, graph, parent, newV, x + move - 40, y + 50, "1", move);
        }
        //Final leaf
        if (tree instanceof Leaf) {
            Leaf leaf = (Leaf) tree;
            Object newV = graph.insertVertex(parent, null, leaf.value + "\n" + leaf.frequency, x, y, 80, 40);
            graph.insertEdge(parent, null, dir, v, newV);
        }

        //RightBranch
        //        if (((Node) tree).right instanceof Leaf) {
        //            Leaf leaf = (Leaf) ((Node) tree).right;
        //            Object vRight = graph.insertVertex(parent, null, leaf.value + "\n" + leaf.frequency, xRight, newY, 80,
        //                    40);
        //            graph.insertEdge(parent, null, "0", v, vRight);
        //        } else {
        //            Object vRight = graph.insertVertex(parent, null, tree.frequency + "", xRight, newY, 80,
        //                    30);
        //            graph.insertEdge(parent, null, "0", v, vRight);
        //            generateTree(((Node) tree).right, graph, parent, vRight, xRight, newY);
        //        }
        //
        //        //Left branch
        //        if (((Node) tree).left instanceof Leaf) {
        //            Leaf leaf = (Leaf) ((Node) tree).left;
        //            Object vLeft = graph.insertVertex(parent, null, leaf.value + "\n" + leaf.frequency, xLeft, newY, 80,
        //                    40);
        //            graph.insertEdge(parent, null, "1", v, vLeft);
        //        } else {
        //            Object vLeft = graph.insertVertex(parent, null, tree.frequency + "", xLeft, newY, 80,
        //                    30);
        //            graph.insertEdge(parent, null, "1", v, vLeft);
        //            generateTree(((Node) tree).left, graph, parent, vLeft, xLeft, newY);
        //        }
        //        try {
        //            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
        //                    30);
        //            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
        //                    80, 30);
        //            graph.insertEdge(parent, null, "Edge", v1, v2);
        //        } finally {
        //            graph.getModel().endUpdate();
        //        }
        {

        }
    }

}
