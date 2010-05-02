package signature.display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import signature.AbstractVertexSignature;
import signature.ColoredTree;

/**
 * Creates images of signature trees (<code>ColoredTree</code>s) from signature
 * strings or colored tree objects.
 * 
 * @author maclean
 *
 */
public class TreeDrawer {
    
    /**
     * Make an image for each signature string in the list, each of size w*h.
     * 
     * @param signatureStrings the list of signature strings
     * @param directoryPath the directory to write the images into
     * @param w the width of each image
     * @param h the height of each image
     */
    public static void makeTreeImages(
            List<String> signatureStrings, String directoryPath, int w, int h) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        int i = 0;
        for (String signature : signatureStrings) {
            ColoredTree coloredTree = AbstractVertexSignature.parse(signature);
            File file = new File(directory, "signature_" + i + ".png");
            if (file.isDirectory()) {
                System.out.println("DIR");
                return;
            }
            makeImage(coloredTree, file, w, h);
            i++;
        }
    }
    
    /**
     * Make an image for this colored tree.
     * 
     * @param coloredTree the colored tree to draw
     * @param file the file to write the image to
     * @param w the width of the image
     * @param h the height of the image
     */
    public static void makeImage(
            ColoredTree coloredTree, File file, int w, int h) {
        DisplayableColoredTree displayTree = 
            new DisplayableColoredTree(coloredTree, w, h);
        BufferedImage image = 
            new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        displayTree.paint(image.getGraphics());
        try {
            file.createNewFile();
            ImageIO.write(image, "PNG", file);
            System.out.println("Made image " + file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
