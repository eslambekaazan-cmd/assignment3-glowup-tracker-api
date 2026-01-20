package repository;

import db.DatabaseConnection;
import exception.DatabaseOperationException;
import model.RoutineType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoutineTypeRepository {

    public RoutineType create(RoutineType type) {
        String sql = "INSERT INTO routine_types (name) VALUES (?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type.getName());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    type.setId(rs.getInt("id"));
                    return type;
                }
                throw new DatabaseOperationException("Insert failed: no id returned.", null);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in create RoutineType.", e);
        }
    }

    public List<RoutineType> getAll() {
        String sql = "SELECT id, name FROM routine_types ORDER BY id";
        List<RoutineType> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RoutineType t = new RoutineType(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                list.add(t);
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getAll RoutineType.", e);
        }
    }

    public RoutineType getById(int id) {
        String sql = "SELECT id, name FROM routine_types WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RoutineType(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getById RoutineType.", e);
        }
    }

    public RoutineType getByName(String name) {
        String sql = "SELECT id, name FROM routine_types WHERE name = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RoutineType(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getByName RoutineType.", e);
        }
    }

    public boolean update(int id, RoutineType newData) {
        String sql = "UPDATE routine_types SET name = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newData.getName());
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in update RoutineType.", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM routine_types WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in delete RoutineType.", e);
        }
    }
}
