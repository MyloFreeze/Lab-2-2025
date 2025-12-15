package functions;

//  Представляет одну точку табулированной функции с координатами (x, y)
public class FunctionPoint {
    private double x;
    private double y;

    // Создаёт точку с координатами (0, 0). 
    public FunctionPoint() {
        this.x = 0.0;
        this.y = 0.0;
    }

    // Создаёт точку с заданными координатами. 
    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Конструктор копирования. 
    public FunctionPoint(FunctionPoint other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {return x;}
    public double getY() {return y;}

    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
}