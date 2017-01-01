package edge;

/**
 * Created by Connor Waslo on 12/25/2016.
 */
public class Color {

    private int red, green, blue;

    public Color(int r, int g, int b) {
        if (r > 255) r = 255;
        else if (r < 0) r = 0;

        if (g > 255) g = 255;
        else if (g < 0) g = 0;

        if (b > 255) b = 255;
        else if (b < 0) b = 0;

        red = r;
        green = g;
        blue = b;
    }

    public void setColors(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
