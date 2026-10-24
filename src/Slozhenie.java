import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Scanner;

/** Класс-наследник: сложение матриц A + B. */
public final class Slozhenie extends ArrayPI implements Operaciya {

    public Slozhenie(ArrayPI dannye) {
        super(dannye);
    }

    @Override
    public String nazvanie() {
        return "Сложение матриц";
    }

    @Override
    public LinkedHashMap<String, BigInteger[][]> vypolnit(Scanner scan) {
        BigInteger[][] r = new BigInteger[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                r[i][j] = BigInteger.valueOf((long) matrica1[i][j] + matrica2[i][j]);
        LinkedHashMap<String, BigInteger[][]> res = new LinkedHashMap<>();
        res.put("A+B", r);
        return res;
    }
}
