public class Rectangle {
    private int width;
    private int height;
    private TopLeft topLeft;
    private String name;
    private int ID;

    public Rectangle() {
    }

    public Rectangle(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public Rectangle(String name, int width, int height, TopLeft topLeft) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
    }

    public Rectangle(String name, int ID, int width, int height, TopLeft topLeft) {
        this.name = name;
        this.ID = ID;
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Rectangle(int width, int height, int ID) {
        this.width = width;
        this.height = height;
        this.ID = ID;
    }

    public Rectangle(int width, int height, TopLeft topLeft) {
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TopLeft getTopLeft() {
        return this.topLeft;
    }

    public void setTopLeft(TopLeft topLeft) {
        this.topLeft = topLeft;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
