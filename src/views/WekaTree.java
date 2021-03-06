package views;

import weka.classifiers.trees.J48;
import weka.core.*;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
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
            // confidence Factor
            "-C", "0.9",

            // min num of objects
            "-M", "1"
        };

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes()-1);

        // Set class index
        isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);

        // Set the J48 options
        tree.setOptions(options);

        // Train classifier
        tree.buildClassifier(isTrainingSet);

        // display classifier
        final javax.swing.JFrame jf =
            new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");

        // Set the behaviors
        jf.getContentPane().setLayout(new BorderLayout());

        // Maximize the window (maximized and full-size)
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setSize(Toolkit.getDefaultToolkit().getScreenSize());

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
