package views.listeners;

import views.SelectionWindow;
import views.WekaDummyTree;
import weka.core.Instance;
import weka.core.Instances;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static controllers.CatGenerator.fvWekaAttributes;

public class ShowTreeButton extends JButton {
    private SelectionWindow parentView;

    public ShowTreeButton(SelectionWindow parentView, String text) {
        super(text);
        this.parentView = parentView;
    }

    /**
     * Called when the user hit the create button.
     * This will create a J48 tree view.
     * @param event
     */
    public void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);
        Instances instances = new Instances("Rel", fvWekaAttributes, 30);

        // TODO: open the frame
        SelectButton[] selectButtons = parentView.getSelectButtons();
        for(SelectButton<Instance> selectButton : selectButtons) {
            if(selectButton.isActive()) {
                instances.add(selectButton.item);
            }
        }

        try {
            WekaDummyTree.main(instances);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
