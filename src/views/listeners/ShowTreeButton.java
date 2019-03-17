package views.listeners;

import views.SelectionWindow;
import views.WekaTree;
import weka.core.Instance;
import weka.core.Instances;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static controllers.CatGenerator.fvWekaAttributes;

public class ShowTreeButton extends JButton {
    private SelectionWindow parentView;

    /**
     * A button that will show a tree when clicked.
     *
     * @param parentView the parent view, the container.
     * @param text the button's text.
     */
    public ShowTreeButton(SelectionWindow parentView, String text) {
        super(text);
        this.parentView = parentView;
    }

    /**
     * Called when the user hit the create button.
     * This will create a J48 tree view.
     *
     * @param event the click event.
     */
    public void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);

        SelectButton<Instance>[] selectionButtons = parentView.getSelectButtons();
        Instances instances = new Instances("Rel", fvWekaAttributes, selectionButtons.length);

        // Filter the buttons, to exclude non selected ones
        // and extract their weka instance
        for(SelectButton<Instance> selectButton : selectionButtons) {
            if(selectButton.isActive()) {
                instances.add(selectButton.item);
            }
        }

        // Attempt to show the tree, if failed, show an error
        try {
            WekaTree.showTreeFromInstances(instances);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null, e.getMessage(),
                "Failed to show the decision tree", JOptionPane.ERROR_MESSAGE);
        }
    }
}
