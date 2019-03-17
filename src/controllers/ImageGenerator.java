package controllers;

import models.Cat;
import weka.core.Instance;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;

import static controllers.CatGenerator.ATTRIBUTE_COUNT;

/**
 * Generates an icon from given layers.
 */
public class ImageGenerator {
    private static ClassLoader classLoader = ImageGenerator.class.getClassLoader();

    /**
     * Loads a given resource.
     *
     * @param filename the resource's name/ path
     * @return the loaded resource
     * @throws NoSuchFileException if the resource is not found
     */
    private static URL loadResource(String filename) throws NoSuchFileException {
        URL foundURL = classLoader.getResource(filename);

        if (foundURL == null) {
            throw new NoSuchFileException(
                "The file " + filename + " was not found in the resources");
        }

        return foundURL;
    }

    /**
     * Builds an icon from given overlays and a base global size.
     *
     * @param dimension the icon's dimensions
     * @param overlayNames the layers filenames (resources)
     * @return the generated icon
     * @throws IOException if a layer failed to load (other than not being found)
     */
    private static ImageIcon BuildImage(Dimension dimension, String[] overlayNames) throws IOException {
        BufferedImage baseImage = new BufferedImage(
            dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = baseImage.getGraphics();

        String resourceName;
        for (String overlayName : overlayNames) {
            resourceName = overlayName + ".png";
            try {
                BufferedImage overlay = ImageIO.read(loadResource(resourceName));
                g.drawImage(overlay, 0, 0, null);
            } catch (NoSuchFileException exc) {
                // Skip non-existing layers
                System.err.println("Skipping: " + resourceName);
            }
        }

        ImageIcon resultIcon = new ImageIcon();
        resultIcon.setImage(baseImage);

        g.dispose();
        return resultIcon;
    }

    /**
     * Builds an icon from a given weka instance.
     *
     * @param dimension the dimensions of the icon
     * @param instance the instance to generate the icon from
     * @return the generated icon
     * @throws IOException if a layer failed to load (other than not found)
     */
    public static ImageIcon BuildInstanceImage(Dimension dimension, Instance instance) throws IOException {
        assert instance.numAttributes() == ATTRIBUTE_COUNT;

        String[] layers = new String[ATTRIBUTE_COUNT];
        String attributeName;
        String attributeValue;
        for (int i = 0; i < ATTRIBUTE_COUNT; ++i) {
            attributeName = Cat.Values[i][0];
            attributeValue = instance.stringValue(
                CatGenerator.fvWekaAttributes.elementAt(i));

            layers[i] = attributeName + "_" + attributeValue.toLowerCase().replace(" ", "");
        }

        return BuildImage(dimension, layers);
    }
}
