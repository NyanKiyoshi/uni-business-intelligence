package controllers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;

public class ImageGenerator {
    private static ClassLoader classLoader = ImageGenerator.class.getClassLoader();

    private static URL loadResource(String filename) throws NoSuchFileException {
        URL foundURL = classLoader.getResource(filename);

        if (foundURL == null) {
            throw new NoSuchFileException(
                "The file " + filename + " was not found in the resources");
        }

        return foundURL;
    }

    public ImageIcon BuildImage(String baseName, String[] overlayNames) throws IOException {
        BufferedImage baseImage = ImageIO.read(loadResource(baseName));
        Graphics g = baseImage.getGraphics();

        for (String overlayName : overlayNames) {
            BufferedImage overlay = ImageIO.read(loadResource(overlayName + ".png"));
            g.drawImage(overlay, 0, 0, null);
        }

        ImageIcon resultIcon = new ImageIcon();
        resultIcon.setImage(baseImage);

        g.dispose();
        return resultIcon;
    }
}
