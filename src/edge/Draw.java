package edge;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Created by Connor Waslo on 12/24/2016.
 */
public class Draw extends JPanel {

    private BufferedImage img;
    private Color[][] pixels;

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        try {
            img = ImageIO.read(new File("res/space.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pixels = getPixels(img);
        g2d.drawImage(img, 2500, 0, this);
        for (int row = 0; row < pixels.length - 1; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                if (hasEdge(row, col, row + 1, col)) {
                    g2d.setColor(java.awt.Color.BLACK);
                    g2d.drawRect(col, row, 1, 1);
                }
            }

            System.out.println("");
        }
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length - 1; col++) {
                if (hasEdge(row, col, row, col + 1)) {
                    g2d.setColor(java.awt.Color.BLACK);
                    g2d.drawRect(col, row, 1, 1);
                }
            }
        }

        //Image img = Toolkit.getDefaultToolkit().getImage("res/Landscape.jpg");

        g2d.finalize();
    }

    public boolean hasEdge(int r1, int c1, int r2, int c2) {
        /*if (pixels[r][c] - pixels[r+1][c] > 550000) {
            return true;
        }*/

        int dRed = pixels[r1][c1].getRed() - pixels[r2][c2].getRed();
        int dGreen = pixels[r1][c1].getGreen() - pixels[r2][c2].getGreen();
        int dBlue = pixels[r1][c1].getBlue() - pixels[r2][c2].getBlue();

        int sum = dRed + dGreen + dBlue;

        // Level with gradient of conditions selected by user
        // CONDITION 1: sum > 100 && (dRed > 30 && dGreen > 30 && dBlue > 30)
        // CONDITION 2: sum > 80 && (dRed > 20 && dGreen > 20 && dBlue > 20)

        if (sum > 100 && (dRed > 30 && dGreen > 30 && dBlue > 30))
            return true;

        return false;
    }

    public Color[][] getPixels(BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        Color[][] result = new Color[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                result[r][c] = new Color(0, 0, 0);
            }
        }

        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                result[row][col].setColors(((int) pixels[pixel + 1] & 0xff),
                        (((int) pixels[pixel + 2] & 0xff) << 8),
                        (((int) pixels[pixel + 3] & 0xff) << 16));

                /*int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;*/

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int red = (((int) pixels[pixel + 2] & 0xff) << 16);
                int green = (((int) pixels[pixel + 1] & 0xff) << 8);
                int blue = ((int) pixels[pixel] & 0xff);

                result[row][col].setColors(red, green, blue);

                /*int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;*/
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }
}
