import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Общий вид операции над матрицами. Каждую операцию реализует
 * свой класс-наследник ArrayPI, поэтому их можно выполнять в цикле (полиморфно).
 */
public interface Operaciya {
    // Название операции для вывода
    String nazvanie();

    // Выполнить операцию; результат - одна или несколько матриц с именами
    LinkedHashMap<String, BigInteger[][]> vypolnit(Scanner scan);
}
