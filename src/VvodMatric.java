import java.sql.SQLException;

/** Пункт меню 3: ввод двух матриц, сохранение каждой в БД и форматированный вывод в консоль. */
public class VvodMatric extends PunktMenu {
    private final ArrayPI dannye;

    public VvodMatric(Baza b, ArrayPI dannye) {
        super(b);
        this.dannye = dannye;
    }

    @Override
    public String nazvanie() {
        return "Ввести две матрицы с клавиатуры и каждую из них сохранить в PostgreSQL с последующим форматированным выводом в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        dannye.vvod(b.scan);
        // Новые исходные матрицы - старые результаты операций больше не актуальны
        MatricyBD.udalitRezultaty(b);
        MatricyBD.sohranit(b, "A", ArrayPI.vBig(dannye.getMatrica1()));
        MatricyBD.sohranit(b, "B", ArrayPI.vBig(dannye.getMatrica2()));
        System.out.println();
        MatricyBD.vyvestiIzBD(b, "A");
        System.out.println();
        MatricyBD.vyvestiIzBD(b, "B");
    }
}
