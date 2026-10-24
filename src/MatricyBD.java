import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сохранение матриц в таблицу и чтение их обратно.
 * Каждая строка матрицы - одна запись таблицы: (matrica, stroka, s1..s7).
 * Столбцы NUMERIC, поэтому большие числа (например, при возведении в степень) не переполняются.
 */
public class MatricyBD {

    // Сохранить матрицу под именем imya (старая матрица с тем же именем заменяется)
    static void sohranit(Baza b, String imya, BigInteger[][] m) throws SQLException {
        PreparedStatement del = b.conn.prepareStatement("DELETE FROM " + b.tablename + " WHERE matrica = ?");
        del.setString(1, imya);
        del.executeUpdate();
        del.close();

        PreparedStatement ps = b.conn.prepareStatement("INSERT INTO " + b.tablename
                + " (matrica, stroka, s1, s2, s3, s4, s5, s6, s7) VALUES (?,?,?,?,?,?,?,?,?)");
        for (int i = 0; i < m.length; i++) {
            ps.setString(1, imya);
            ps.setInt(2, i + 1);
            for (int j = 0; j < ArrayPI.N; j++) ps.setBigDecimal(3 + j, new BigDecimal(m[i][j]));
            ps.executeUpdate();
        }
        ps.close();
        System.out.println("Матрица " + imya + " сохранена в таблицу '" + b.tablename + "'");
    }

    // Удалить все результаты операций (оставить только исходные A и B)
    static void udalitRezultaty(Baza b) throws SQLException {
        b.stat.executeUpdate("DELETE FROM " + b.tablename + " WHERE matrica NOT IN ('A', 'B')");
    }

    // Прочитать матрицу из таблицы; null, если её там нет
    static BigInteger[][] zagruzit(Baza b, String imya) throws SQLException {
        PreparedStatement ps = b.conn.prepareStatement("SELECT * FROM " + b.tablename
                + " WHERE matrica = ? ORDER BY stroka");
        ps.setString(1, imya);
        ResultSet rs = ps.executeQuery();
        BigInteger[][] m = new BigInteger[ArrayPI.N][ArrayPI.N];
        int strok = 0;
        while (rs.next() && strok < ArrayPI.N) {
            for (int j = 0; j < ArrayPI.N; j++) m[strok][j] = rs.getBigDecimal("s" + (j + 1)).toBigInteger();
            strok++;
        }
        rs.close();
        ps.close();
        return strok == ArrayPI.N ? m : null;
    }

    // Имена всех матриц в таблице в порядке сохранения
    static List<String> vseImena(Baza b) throws SQLException {
        List<String> imena = new ArrayList<>();
        ResultSet rs = b.stat.executeQuery("SELECT matrica FROM " + b.tablename
                + " GROUP BY matrica ORDER BY MIN(id)");
        while (rs.next()) imena.add(rs.getString(1));
        rs.close();
        return imena;
    }

    // Прочитать матрицу из БД и вывести в консоль
    static void vyvestiIzBD(Baza b, String imya) throws SQLException {
        BigInteger[][] m = zagruzit(b, imya);
        if (m == null) System.out.println("Матрицы " + imya + " нет в таблице");
        else ArrayPI.pechat("Матрица " + imya + " (данные из БД)", m);
    }

    static int[][] vInt(BigInteger[][] m) {
        int[][] r = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[i].length; j++) r[i][j] = m[i][j].intValue();
        return r;
    }
}
