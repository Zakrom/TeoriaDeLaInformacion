/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Edgar Valdes
 */
public class TreeDisplayer extends JFrame {

    /**
     *
     */
    private static List<HuffmanResult> treeObjs = new ArrayList<HuffmanResult>();
    private static final long serialVersionUID = -2707712944901661771L;

    public TreeDisplayer() throws IOException {

        super("Hello, World!");

        Properties properties = TeoriaDeLaInformacion.loadProperties("characters.properties");

        for (Entry<Object, Object> entry : properties.entrySet()) {
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            byte[] bytes = DatatypeConverter.parseHexBinary(k);
            String realKey = new String(bytes);

            HuffmanResult line = new HuffmanResult();
            line.setBinArray(v);
            line.setValue(realKey.charAt(0));
            treeObjs.add(line);
        }

        Integer max = 0;
        for (HuffmanResult hf : treeObjs) {
            Integer n = Integer.parseInt(hf.getBinArray(), 2);
            if (n > max) {
                max = n;
            }
        }

        System.out.println(max.toString());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
                    30);
            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
                    80, 30);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    public static void displayTree() throws IOException {
        TreeDisplayer frame = new TreeDisplayer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }
}
