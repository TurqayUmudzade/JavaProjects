import java.awt.*;
import java.io.Serializable;

public class Square implements Serializable {
    int x;
    int y;
    int width;
    int length;
    Color color;
    public Square(int x, int y, int width, int length, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
