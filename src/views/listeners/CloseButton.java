package views.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class CloseButton extends JButton {
    private JFrame parentView;

    /**
     * A dummy close button, that will close the parent view when clicked.
     *
     * @param parentView the parent view.
     * @param text the button's text.
     */
    public CloseButton(JFrame parentView, String text) {
        super(text);
        this.parentView = parentView;
    }

    /**
     * Called when the user hit the close button.
     * @param event the click event.
     */
    public void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);

        this.parentView.dispatchEvent(new WindowEvent(
                this.parentView, WindowEvent.WINDOW_CLOSING));
    }
}
