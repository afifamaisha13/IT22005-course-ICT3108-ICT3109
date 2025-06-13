import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class BirthdayDAO {
    public List<Birthday> getAllBirthdays() {
        List<Birthday> list = new ArrayList<>();
        String sql = "SELECT * FROM birthdays ORDER BY MONTH(birth_date), DAY(birth_date)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Birthday(rs.getInt("id"), rs.getString("name"), rs.getDate("birth_date").toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addBirthday(Birthday b) {
        String sql = "INSERT INTO birthdays (name, birth_date) VALUES (?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getName());
            stmt.setDate(2, Date.valueOf(b.getBirthDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBirthday(Birthday b) {
        String sql = "UPDATE birthdays SET name = ?, birth_date = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, b.getName());
            stmt.setDate(2, Date.valueOf(b.getBirthDate()));
            stmt.setInt(3, b.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBirthday(int id) {
        String sql = "DELETE FROM birthdays WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Birthday> getTodaysBirthdays() {
        List<Birthday> list = new ArrayList<>();
        String sql = "SELECT * FROM birthdays WHERE MONTH(birth_date) = ? AND DAY(birth_date) = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            LocalDate today = LocalDate.now();
            stmt.setInt(1, today.getMonthValue());
            stmt.setInt(2, today.getDayOfMonth());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Birthday(rs.getInt("id"), rs.getString("name"), rs.getDate("birth_date").toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}