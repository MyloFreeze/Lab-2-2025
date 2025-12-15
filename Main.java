import functions.*;

public class Main {
    public static void main(String[] args) {
        // Создаём функцию на основе произвольных значений
        double[] values = {1.0, 3.0, 5.0, 2.0, 6.0, 7.0, 26.0, 8.0};
        TabulatedFunction func = new TabulatedFunction(2.0, 9.0, values);

        System.out.println("Исходная функция:");
        func.printTabFun();

        System.out.println("\n Удаляем точку с индексом 2");
        func.deletePoint(2);
        func.printTabFun();

        System.out.println("\n Добавляем новую точку (0.0, -1.0)");
        func.addPoint(new FunctionPoint(0.0, -1.0));
        func.printTabFun();

        System.out.println("\n Интерполяция в различных точках");
        double[] testX = {-1.0, 0.5, 3.0, 7.5, 9.0, 10.0};
        for (double x : testX) {
            double y = func.getFunctionValue(x);
            if (Double.isNaN(y)) {
                System.out.printf("f(%.1f) = вне области определения%n", x);
            } else {
                System.out.printf("f(%.1f) = %.3f%n", x, y);
            }
        }

        System.out.println("\n Изменяем точку с индексом 1");
        func.setPoint(1, new FunctionPoint(1.0, 100.0));
        func.printTabFun();

        System.out.println("\n Границы функции: [" +
                func.getLeftLimit() + "; " +
                func.getRightLimit() + "]");
    }
}