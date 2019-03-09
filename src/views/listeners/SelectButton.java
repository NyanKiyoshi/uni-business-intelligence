package views.listeners;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectButton<T> extends JButton {
    private boolean isActive = false;
    private Color activeColor = new Color(0x6b, 0xea, 0x6e);
    private Color inactiveColor = Color.PINK;

    public final T item;

    public SelectButton(T item, ImageIcon imageIcon) {
        this.item = item;

        this.updateStyle();
        this.setIcon(imageIcon);
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
