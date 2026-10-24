import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Scanner;

/** Класс-наследник: перемножение матриц A x B. */
public final class Umnozhenie extends ArrayPI implements Operaciya {

    public Umnozhenie(ArrayPI dannye) {
        super(dannye);
    }

    @Override
    public String nazvanie() {
        return "Умножение матриц";
    }

    @Override
    public LinkedHashMap<String, BigInteger[][]> vypolnit(Scanner scan) {
        LinkedHashMap<String, BigInteger[][]> res = new LinkedHashMap<>();
        res.put("A*B", proizvedenie(vBig(matrica1), vBig(matrica2)));
        return res;
    }
}
