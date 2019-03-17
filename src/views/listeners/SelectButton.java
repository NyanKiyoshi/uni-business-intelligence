package views.listeners;

import org.jetbrains.annotations.Nullable;

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

    /**
     * A selectable button that is associated to a T item and an icon.
     * @param item the object associated to the button.
     * @param imageIcon the button's icon (nullable).
     */
    public SelectButton(T item, @Nullable ImageIcon imageIcon) {
        this.item = item;

        this.updateStyle();
        this.setIcon(imageIcon);
    }

    /**
     * Called whenever a update is being done to the button.
     * It will create lowered borders if the button is selected,
     * raised borders otherwise.
     *
     * And will set a green or red background depending
     * if the button is selected or not.
     */
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

    /**
     * Toggles the button whenever a click is performed on it.
     * @param event the click event.
     */
    public void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(event);

        // Toggle the button state
        this.isActive = !this.isActive;
        this.updateStyle();
    }

    /**
     * Whether the button is selected or not.
     * @return the current state of the button.
     */
    public boolean isActive() {
        return this.isActive;
    }
}
