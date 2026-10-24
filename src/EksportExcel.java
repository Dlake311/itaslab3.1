import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 5: выгрузка всех матриц из БД в Excel (CSV) и вывод их в консоль. */
public class EksportExcel extends PunktMenu {

    public EksportExcel(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Сохранить результаты из PostgreSQL в Excel и вывести их в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        File file = new File(b.papkaDlyaFaila(), b.tablename + ".csv");
        List<String> imena = MatricyBD.vseImena(b);

        System.out.println("\n=== Содержимое таблицы '" + b.tablename + "' ===");
        // CSV с BOM и разделителем ';' - Excel открывает его с правильной кодировкой
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            w.write('﻿');
            for (String imya : imena) {
                BigInteger[][] m = MatricyBD.zagruzit(b, imya);
                if (m == null) continue;
                ArrayPI.pechat("Матрица " + imya, m);

                w.write("Матрица " + imya + "\n");
                w.write("Строка");
                for (int j = 1; j <= ArrayPI.N; j++) w.write(";Столбец " + j);
                w.write("\n");
                for (int i = 0; i < m.length; i++) {
                    w.write(String.valueOf(i + 1));
                    for (BigInteger v : m[i]) w.write(";" + v);
                    w.write("\n");
                }
                w.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
            return;
        }

        if (imena.isEmpty()) System.out.println("Таблица пуста");
        System.out.println("Данные сохранены в Excel-файл: " + file.getAbsolutePath() + " (матриц: " + imena.size() + ")");
        b.otkrytFail(file);
    }
}
