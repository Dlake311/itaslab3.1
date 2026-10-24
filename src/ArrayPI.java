import java.math.BigInteger;
import java.util.Scanner;

/**
 * Класс ArrayPI: хранит две двумерные матрицы 7x7, которые вводятся с клавиатуры.
 * Классы-наследники (операции над матрицами) получают эти матрицы по наследству.
 */
public class ArrayPI {
    public static final int N = 7;

    protected int[][] matrica1 = new int[N][N]; // матрица A
    protected int[][] matrica2 = new int[N][N]; // матрица B
    protected boolean zapolneny = false;

    public ArrayPI() {
    }

    // Конструктор для наследников: берёт матрицы из уже заполненного объекта
    protected ArrayPI(ArrayPI dannye) {
        this.matrica1 = dannye.matrica1;
        this.matrica2 = dannye.matrica2;
        this.zapolneny = dannye.zapolneny;
    }

    // Ввод обеих матриц с клавиатуры
    public void vvod(Scanner scan) {
        System.out.println("Ввод матрицы A (" + N + "x" + N + ")");
        vvodMatricy(scan, matrica1, "A");
        System.out.println("Ввод матрицы B (" + N + "x" + N + ")");
        vvodMatricy(scan, matrica2, "B");
        zapolneny = true;
    }

    // Матрица вводится построчно: в каждой строке 7 целых чисел через пробел
    protected void vvodMatricy(Scanner scan, int[][] m, String imya) {
        for (int i = 0; i < N; i++) {
            while (true) {
                System.out.print("Строка " + (i + 1) + " матрицы " + imya + " (" + N + " целых чисел через пробел): ");
                String vvod = scan.nextLine().trim();
                String[] chasti = vvod.isEmpty() ? new String[0] : vvod.split("[\\s,;]+");
                if (chasti.length != N) {
                    System.out.println("Ошибка: нужно ровно " + N + " чисел, введено " + chasti.length);
                    continue;
                }
                try {
                    int[] stroka = new int[N];
                    for (int j = 0; j < N; j++) stroka[j] = Integer.parseInt(chasti[j]);
                    m[i] = stroka;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: все элементы должны быть целыми числами");
                }
            }
        }
    }

    public void zadat(int[][] a, int[][] b) {
        matrica1 = a;
        matrica2 = b;
        zapolneny = true;
    }

    public boolean isZapolneny() {
        return zapolneny;
    }

    public int[][] getMatrica1() {
        return matrica1;
    }

    public int[][] getMatrica2() {
        return matrica2;
    }

    // ---------- Общие вспомогательные методы для наследников ----------

    protected static BigInteger[][] vBig(int[][] m) {
        BigInteger[][] r = new BigInteger[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) r[i][j] = BigInteger.valueOf(m[i][j]);
        return r;
    }

    // Произведение двух матриц N x N
    protected static BigInteger[][] proizvedenie(BigInteger[][] x, BigInteger[][] y) {
        BigInteger[][] r = new BigInteger[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                BigInteger sum = BigInteger.ZERO;
                for (int k = 0; k < N; k++) sum = sum.add(x[i][k].multiply(y[k][j]));
                r[i][j] = sum;
            }
        }
        return r;
    }

    // Форматированный вывод: рамка и столбцы одинаковой ширины
    public static void pechat(String zagolovok, BigInteger[][] m) {
        int w = 3;
        for (BigInteger[] stroka : m)
            for (BigInteger v : stroka) w = Math.max(w, v.toString().length());

        StringBuilder liniya = new StringBuilder("+");
        for (int j = 0; j < N; j++) liniya.append("-".repeat(w + 2)).append("+");

        System.out.println(zagolovok + ":");
        System.out.println(liniya);
        for (BigInteger[] stroka : m) {
            StringBuilder sb = new StringBuilder("|");
            for (BigInteger v : stroka) sb.append(String.format(" %" + w + "s |", v));
            System.out.println(sb);
        }
        System.out.println(liniya);
    }
}
