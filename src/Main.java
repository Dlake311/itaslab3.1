import java.sql.SQLException;

/**
 * Лабораторная работа 3, часть 1 (сложный вариант).
 * ООП: класс ArrayPI (две матрицы 7x7) и классы-наследники для каждой операции:
 * Umnozhenie, Slozhenie, Vychitanie, Stepen.
 * Каждый пункт меню - отдельный класс-наследник PunktMenu.
 */
public class Main {
    public static void main(String[] args) {
        Baza b = new Baza("id SERIAL PRIMARY KEY, matrica VARCHAR(10), stroka INT, "
                + "s1 NUMERIC, s2 NUMERIC, s3 NUMERIC, s4 NUMERIC, s5 NUMERIC, s6 NUMERIC, s7 NUMERIC");
        if (!b.podkl()) return;

        ArrayPI dannye = new ArrayPI();
        PunktMenu[] menu = {
                new VyvodTablic(b),
                new SozdanieTablicy(b),
                new VvodMatric(b, dannye),
                new OperaciiNadMatricami(b, dannye),
                new EksportExcel(b)
        };

        while (true) {
            System.out.println();
            System.out.println("========== МЕНЮ ==========");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ". " + menu[i].nazvanie());
            }
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int vib = b.chitatVybor();
            if (vib == 0) {
                b.zakr();
                System.out.println("Работа завершена");
                return;
            }
            if (vib < 1 || vib > menu.length) {
                System.out.println("Нет такого пункта меню, введите число от 0 до " + menu.length);
                continue;
            }
            try {
                menu[vib - 1].vypolnit();
            } catch (SQLException e) {
                System.out.println("Ошибка работы с базой данных: " + e.getMessage());
            }
        }
    }
}
