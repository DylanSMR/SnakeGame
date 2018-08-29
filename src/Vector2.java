import java.awt.*;

class Vector2 {
    private int x;
    private int y;

    private int width;
    private int height;

    private Color tileColor;

    Vector2(int x, int y){
        this.x = x;
        this.y = y;

        width = 10;
        height = 10;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    boolean compareTo(int x, int y){
        return x == this.x && y == this.y;
    }

    // Compares
    boolean compareTo(Vector2 vector){
        return vector.getX() == this.x && vector.getY() == this.y;
    }

    Color getTileColor() {
        return tileColor;
    }

    void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }
}
