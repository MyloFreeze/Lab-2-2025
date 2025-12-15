package functions;

// Класс для представления табулированной функции одной переменной.
// Точки всегда хранятся в порядке возрастания x.
public class TabulatedFunction {
    private FunctionPoint[] points;   // массив с запасом ёмкости
    private int count;                // текущее количество точек

    public TabulatedFunction(double leftX, double rightX, int pointCount) {
        if (pointCount < 2) {
            throw new IllegalArgumentException("Требуется минимум 2 точки.");
        }
        this.count = pointCount;
        this.points = new FunctionPoint[2 * count]; // выделяем память с запасом
        double step = (rightX - leftX) / (count - 1);
        for (int i = 0; i < count; i++) {
            points[i] = new FunctionPoint(leftX + step * i, 0.0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        if (values.length < 2) {
            throw new IllegalArgumentException("Требуется минимум 2 значения.");
        }
        this.count = values.length;
        this.points = new FunctionPoint[2 * count];
        double step = (rightX - leftX) / (count - 1);
        for (int i = 0; i < count; i++) {
            points[i] = new FunctionPoint(leftX + step * i, values[i]);
        }
    }

    public double getLeftLimit() {return points[0].getX();}
    public double getRightLimit() {return points[count - 1].getX();}

    public double getFunctionValue(double x) {
        if (x < points[0].getX() || x > points[count - 1].getX()) {
            return Double.NaN;
        }

        // Сначала проверяем точное совпадение — это ускоряет работу и улучшает точность
        for (int i = 0; i < count; i++) {
            if (Double.compare(points[i].getX(), x) == 0) {
                return points[i].getY();
            }
        }

        // Поиск интервала [i, i+1], в который попадает x
        int i = 0;
        while (i < count - 1 && points[i + 1].getX() < x) {
            i++;
        }

        // Линейная интерполяция между points[i] и points[i+1]
        double x1 = points[i].getX();
        double y1 = points[i].getY();
        double x2 = points[i + 1].getX();
        double y2 = points[i + 1].getY();
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }

    public int getPointsCount() {
        return count;
    }

    public FunctionPoint getPoint(int index) {
        return new FunctionPoint(points[index]); // возвращаем копию — инкапсуляция
    }

    public void setPoint(int index, FunctionPoint point) {
        double newX = point.getX();
        if (index == 0) {
            // Для первой точки достаточно, чтобы x был меньше следующей
            if (newX < points[1].getX()) {
                points[index] = new FunctionPoint(point);
            }
        } else if (index == count - 1) {
            // Для последней — больше предыдущей
            if (newX > points[count - 2].getX()) {
                points[index] = new FunctionPoint(point);
            }
        } else {
            // Для внутренних — должен лежать строго между соседями
            if (points[index - 1].getX() < newX && newX < points[index + 1].getX()) {
                points[index] = new FunctionPoint(point);
            }
        }
    }

    public double getPointX(int index) {
        return points[index].getX();
    }

    public void setPointX(int index, double x) {
        setPoint(index, new FunctionPoint(x, points[index].getY()));
    }

    public double getPointY(int index) {
        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        points[index].setY(y); // y не влияет на порядок — проверка не нужна
    }

    public void deletePoint(int index) {
        if (count <= 2) return; // нельзя оставить меньше двух точек
        for (int i = index; i < count - 1; i++) {
            points[i] = points[i + 1];
        }
        points[count - 1] = null; // избегаем утечки ссылок
        count--;
    }

    public void addPoint(FunctionPoint point) {
        double x = point.getX();

        // Если x уже есть — заменяем значение (как указано в задании)
        for (int i = 0; i < count; i++) {
            if (Double.compare(points[i].getX(), x) == 0) {
                setPoint(i, point);
                return;
            }
        }

        // При нехватке места расширяем массив на 50%
        if (count >= points.length) {
            FunctionPoint[] newPoints = new FunctionPoint[points.length + (points.length / 2)];
            System.arraycopy(points, 0, newPoints, 0, count);
            points = newPoints;
        }

        // Находим позицию для вставки, сохраняя порядок по x
        int insertIndex = 0;
        while (insertIndex < count && points[insertIndex].getX() < x) {
            insertIndex++;
        }

        // Сдвигаем хвост вправо
        System.arraycopy(points, insertIndex, points, insertIndex + 1, count - insertIndex);
        points[insertIndex] = new FunctionPoint(point); // копируем, а не присваиваем ссылку
        count++;
    }

    public void printTabFun() {
        for (int i = 0; i < count; i++) {
            System.out.printf(" x: %.3f y: %.3f%n", points[i].getX(), points[i].getY());
        }
    }
}