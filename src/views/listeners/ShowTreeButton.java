package views.listeners;

import views.SelectionWindow;
import views.WekaTree;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static controllers.CatGenerator.*;

public class ShowTreeButton extends JButton {
    private SelectionWindow parentView;

    private static void showError(String message) {
        JOptionPane.showMessageDialog(
            null, message,
            "Failed to show the decision tree", JOptionPane.ERROR_MESSAGE);
    }

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
        Instance item;

        // Filter the buttons, to exclude non selected ones
        // and extract their weka instance
        for(SelectButton<Instance> selectButton : selectionButtons) {
            String selectedValue = FALSE;

            if(selectButton.isActive()) {
                selectedValue = TRUE;
                ++selectedCount;
            }

            item = selectButton.item;
            Attribute attr = item.attribute(item.numAttributes() - 1);
            item.setValue(attr, selectedValue);
        }

        if(selectedCount < 2) {
            showError("Please select at least 2 items");
            return;
        }

        // Attempt to show the tree, if failed, show an error
        try {
            WekaTree.showTreeFromInstances(parentView.getInstances());
        }
        catch (Exception e) {
            e.printStackTrace();
            showError(e.getMessage());
        }
    }
}
