package views;

import controllers.WrapLayout;
import views.listeners.CloseButton;
import views.listeners.SelectButton;
import views.listeners.ShowTreeButton;
import weka.core.Instance;
import weka.core.Instances;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.Enumeration;

import static controllers.CatGenerator.generatesInstances;

public class SelectionWindow extends JFrame {
    private final static String FORM_SUBTITLE =
            "Ces images sont-elles dans la classe ?";

    private Instances instances = generatesInstances(30);
    private SelectButton<Instance>[] selectButtons = new SelectButton[this.instances.numInstances()];

    public SelectionWindow() {
        // Set the form behavior information
        this.setSize(800, 630);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("L'informatique décisionnelle: des exemples.");

        // Create and set the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.setContentPane(contentPane);

        // Create the content pane title
        contentPane.add(this.createFormTitle(), BorderLayout.NORTH);

        // Create the buttons
        contentPane.add(this.createSelectionBoxes());

        // Create the content pane action buttons
        contentPane.add(this.createFormButtons(), BorderLayout.SOUTH);
    }

    private JLabel createFormTitle() {
        JLabel formTitle = new JLabel(FORM_SUBTITLE);
        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formTitle.setFont(formTitle.getFont().deriveFont(21.0f));
        return formTitle;
    }

    private JComponent createSelectionBoxes() {
        JPanel containerPane = new JPanel();
        containerPane.setLayout(new WrapLayout());

        Enumeration<Instance> it = this.instances.enumerateInstances();
        int buttonPos = 0;

        while (it.hasMoreElements()) {
            SelectButton<Instance> button = new SelectButton<>(it.nextElement(), null);

            // Set the button properties
            button.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            button.setPreferredSize(new Dimension(100, 100));

            // Add the buttons to the containers
            this.selectButtons[buttonPos++] = button;
            containerPane.add(button);
        }

        return containerPane;
    }

    /*
     * This creates a submit and a cancel button
     * and adds them to the frame.
     *
     * The cancel button will close the form,
     * meanwhile the submit button will check the input data
     * and create a new student if everything is good.
     * Will send an error otherwise.
     */
    private JPanel createFormButtons() {
        JPanel containerPane = new JPanel();

        // register the buttons
        containerPane.add(new ShowTreeButton(this, "Lancer"));
        containerPane.add(new CloseButton(this, "Fermer"));

        return containerPane;
    }

    public SelectButton<Instance>[] getSelectButtons() {
        return this.selectButtons;
    }

    public static void main(String[] args) {
        JFrame frame = new SelectionWindow();
        frame.setVisible(true);
    }
}
