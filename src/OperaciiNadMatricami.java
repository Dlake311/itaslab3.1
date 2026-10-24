import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

/**
 * Пункт меню 4: умножение, сложение, вычитание и возведение в степень.
 * Каждая операция - отдельный класс-наследник ArrayPI; здесь они выполняются по очереди,
 * результат каждой сохраняется в БД и выводится в консоль.
 */
public class OperaciiNadMatricami extends PunktMenu {
    private final ArrayPI dannye;

    public OperaciiNadMatricami(Baza b, ArrayPI dannye) {
        super(b);
        this.dannye = dannye;
    }

    @Override
    public String nazvanie() {
        return "Перемножить, сложить, вычесть, возвести в степень матрицы, а также сохранить результаты в PostgreSQL и вывести в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;

        // Если в этом запуске матрицы не вводили - берём сохранённые в таблице
        if (!dannye.isZapolneny()) {
            BigInteger[][] a = MatricyBD.zagruzit(b, "A");
            BigInteger[][] bb = MatricyBD.zagruzit(b, "B");
            if (a == null || bb == null) {
                System.out.println("Сначала введите матрицы (пункт 3)");
                return;
            }
            dannye.zadat(MatricyBD.vInt(a), MatricyBD.vInt(bb));
            System.out.println("Матрицы A и B загружены из таблицы '" + b.tablename + "'");
        }

        ArrayPI.pechat("Матрица A", ArrayPI.vBig(dannye.getMatrica1()));
        ArrayPI.pechat("Матрица B", ArrayPI.vBig(dannye.getMatrica2()));

        // Все операции - наследники ArrayPI, работаем с ними через общий интерфейс
        Operaciya[] operacii = {
                new Umnozhenie(dannye),
                new Slozhenie(dannye),
                new Vychitanie(dannye),
                new Stepen(dannye)
        };

        MatricyBD.udalitRezultaty(b);
        for (Operaciya op : operacii) {
            System.out.println("\n===== " + op.nazvanie() + " =====");
            for (Map.Entry<String, BigInteger[][]> e : op.vypolnit(b.scan).entrySet()) {
                MatricyBD.sohranit(b, e.getKey(), e.getValue());
                MatricyBD.vyvestiIzBD(b, e.getKey());
            }
        }
    }
}
