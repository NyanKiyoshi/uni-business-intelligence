package views;

import controllers.ImageGenerator;
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
import java.io.IOException;
import java.util.Enumeration;

import static controllers.CatGenerator.generatesInstances;

/**
 * The selection window is a JFrame allowing the user to view and select
 * boxes (buttons) having given attributes to generate a decision tree from.
 */
public class SelectionWindow extends JFrame {
    private final static String FORM_SUBTITLE =
            "Ces images sont-elles dans la classe ?";

    private Instances instances = generatesInstances(30);
    private SelectButton<Instance>[] selectButtons = new SelectButton[this.instances.numInstances()];
    private static final Dimension IMAGE_DIMENSION = new Dimension(100, 100);

    /**
     * Constructs the selection window of icons.
     *
     * @throws IOException
     * Happens if an existing file failed to load, e.g. not enough permissions.
     */
    public SelectionWindow() throws IOException {
        // Set the form's default size
        this.setSize(800, 630);

        // Exit the program when the form gets closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the title
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

    /**
     * It creates a bold label containing the form's sublabel.
     * @return the created JLabel component to use in the form.
     */
    private JLabel createFormTitle() {
        JLabel formTitle = new JLabel(FORM_SUBTITLE);
        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formTitle.setFont(formTitle.getFont().deriveFont(21.0f));
        return formTitle;
    }

    /**
     * It creates the different selection icons from the generated instances.
     * It creates a grid of 100x100 (default) button boxes (of a given count).
     * @return The panel containing the grid of buttons.
     * @throws IOException Happens if a icon file failed to load.
     */
    private JComponent createSelectionBoxes() throws IOException {
        JPanel containerPane = new JPanel();
        containerPane.setLayout(new WrapLayout());

        Enumeration<Instance> it = this.instances.enumerateInstances();
        int buttonPos = 0;
        Instance instance;

        while (it.hasMoreElements()) {
            instance = it.nextElement();
            SelectButton<Instance> button = new SelectButton<>(
                instance, ImageGenerator.BuildInstanceImage(IMAGE_DIMENSION, instance));

            // Set the button properties
            button.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            button.setPreferredSize(IMAGE_DIMENSION);

            // Add the buttons to the containers
            this.selectButtons[buttonPos++] = button;
            containerPane.add(button);
        }

        return containerPane;
    }

    /**
     * This creates a submit and a cancel button
     * and adds them to the frame.
     *
     * The cancel button will close the form,
     * meanwhile the submit button will check the input data
     * and create a new student if everything is good.
     * Will send an error otherwise.
     *
     * @return The created panel containing the buttons.
     */
    private JPanel createFormButtons() {
        JPanel containerPane = new JPanel();

        // register the buttons
        containerPane.add(new ShowTreeButton(this, "Lancer"));
        containerPane.add(new CloseButton(this, "Fermer"));

        return containerPane;
    }

    /**
     * A getter of the instance's selection button boxes.
     * @return The instance's selection buttons.
     */
    public SelectButton<Instance>[] getSelectButtons() {
        return this.selectButtons;
    }

    /**
     * The entry point of the selection window,
     * it creates a new window and shows it.
     *
     * It may fail and show an error message if an IO exception occurs,
     * e.g. not enough privileges to load a icon file.
     *
     * @param args The passed command line arguments (unused).
     */
    public static void main(String[] args) {
        try {
            JFrame frame = new SelectionWindow();
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null, e.getMessage(),
                "Failed to load images", JOptionPane.ERROR_MESSAGE);
        }
    }
}
