import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Класс-наследник: возведение матриц A и B в степень n
 * (матричное умножение матрицы на себя n раз; A^0 - единичная матрица).
 */
public final class Stepen extends ArrayPI implements Operaciya {
    public static final int MAX_STEPEN = 20;

    public Stepen(ArrayPI dannye) {
        super(dannye);
    }

    @Override
    public String nazvanie() {
        return "Возведение матриц в степень";
    }

    @Override
    public LinkedHashMap<String, BigInteger[][]> vypolnit(Scanner scan) {
        int n;
        while (true) {
            System.out.print("Введите степень (целое число от 0 до " + MAX_STEPEN + "): ");
            try {
                n = Integer.parseInt(scan.nextLine().trim());
                if (n >= 0 && n <= MAX_STEPEN) break;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Ошибка: нужно целое число от 0 до " + MAX_STEPEN);
        }
        LinkedHashMap<String, BigInteger[][]> res = new LinkedHashMap<>();
        res.put("A^" + n, vStepen(vBig(matrica1), n));
        res.put("B^" + n, vStepen(vBig(matrica2), n));
        return res;
    }

    private BigInteger[][] vStepen(BigInteger[][] m, int n) {
        BigInteger[][] r = new BigInteger[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) r[i][j] = i == j ? BigInteger.ONE : BigInteger.ZERO;
        for (int k = 0; k < n; k++) r = proizvedenie(r, m);
        return r;
    }
}
