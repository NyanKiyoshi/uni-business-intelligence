package views.listeners;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectButton extends JButton {
    private boolean isActive = false;
    private Color activeColor = new Color(0x6b, 0xea, 0x6e);
    private Color inactiveColor = Color.PINK;

    public SelectButton(String text) {
        super(text);
        this.updateStyle();
    }

    public SelectButton() {
        this("");
    }

    private void updateStyle() {
        int borderType;
        Color backgroundColor;

        if (this.isActive) {
            borderType = BevelBorder.LOWERED;
            backgroundColor = this.activeColor;
        }
        else {
            borderType = BevelBorder.RAISED;
            backgroundColor = this.inactiveColor;
        }

        this.setBorder(new SoftBevelBorder(borderType));
        this.setBackground(backgroundColor);
    }

    public void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);

        // Toggle the button state
        this.isActive = !this.isActive;
        this.updateStyle();
    }

    public boolean isActive() {
        return this.isActive;
    }
}
