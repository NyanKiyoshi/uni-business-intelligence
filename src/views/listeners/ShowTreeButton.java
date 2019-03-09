package views.listeners;

import views.SelectionWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

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

        // TODO: open the frame
    }
}