package views;

import controllers.CatGenerator;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import java.awt.*;

import static controllers.CatGenerator.fvWekaAttributes;

public class WekaDummyTree {
    public static void main(String[] args) throws Exception {
        J48 tree = new J48();
        String[] options = {
            // confidence Factor
            "-C", "0.9",

            // min num of objects
            "-M", "1"
        };

        // Create an empty training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);

        // Create and add instances
        for (int i = 0; i < 15; i++) {
            isTrainingSet.add(CatGenerator.generateInstance());
        }

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);

        // Train classifier
        tree.buildClassifier(isTrainingSet);

        // Set the J48 options
        tree.setOptions(options);

        // display classifier
        final javax.swing.JFrame jf =
            new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
        jf.setSize(500,400);
        jf.getContentPane().setLayout(new BorderLayout());
        TreeVisualizer tv = new TreeVisualizer(null,
            tree.graph(),
            new PlaceNode2());
        jf.getContentPane().add(tv, BorderLayout.CENTER);
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                jf.dispose();
            }
        });

        jf.setVisible(true);
        tv.fitToScreen();
    }
}
