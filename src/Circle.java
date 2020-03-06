import java.awt.*;
import java.io.Serializable;

public class Circle implements Serializable {
    int x;
    int y;
    int width;
    int length;
    Color color;

    Circle(int x, int y, int width, int length,Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length=length;
        this.color=color;
    }
}