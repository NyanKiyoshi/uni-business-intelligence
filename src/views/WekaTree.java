package views;

import weka.classifiers.trees.J48;
import weka.core.*;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import java.awt.*;

public class WekaTree {
    /**
     * It creates a JFrame with a decision tree visualisation component
     *
     * @param isTrainingSet the instances to generate the tree from.
     * @throws Exception may occur if the internal library failed to call
     * its external components.
     */
    public static void showTreeFromInstances(Instances isTrainingSet) throws Exception {
        J48 tree = new J48();
        String[] options = {
        };

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes()-1);

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);

        // Train classifier
        tree.buildClassifier(isTrainingSet);

        // Set the J48 options
        tree.setOptions(options);

        // display classifier
        final javax.swing.JFrame jf =
            new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");

        // Set the behaviors
        jf.setSize(500,400);
        jf.getContentPane().setLayout(new BorderLayout());

        // Create the tree view visualizer component
        // and put it to the center of the frame
        TreeVisualizer tv = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
        jf.getContentPane().add(tv, BorderLayout.CENTER);

        // When the window gets closed, dispose the jframe, to prevent leaks
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                jf.dispose();
            }
        });

        // Show the frame
        jf.setVisible(true);
        tv.fitToScreen();
    }
}
