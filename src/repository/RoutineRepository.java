package repository;

import db.DatabaseConnection;
import exception.DatabaseOperationException;
import model.Routine;
import model.RoutineType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoutineRepository {

    public Routine create(Routine routine) {
        String sql = "INSERT INTO routines (name, type_id) VALUES (?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, routine.getName());
            ps.setInt(2, routine.getType().getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    routine.setId(rs.getInt("id"));
                    return routine;
                }
                throw new DatabaseOperationException("Insert routine failed: no id returned.", null);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in create Routine.", e);
        }
    }

    public List<Routine> getAll() {
        String sql = """
                SELECT r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM routines r
                JOIN routine_types t ON r.type_id = t.id
                ORDER BY r.id
                """;

        List<Routine> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RoutineType type = new RoutineType(rs.getInt("tid"), rs.getString("tname"));
                Routine routine = new Routine(rs.getInt("rid"), rs.getString("rname"), type);
                list.add(routine);
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getAll Routine.", e);
        }
    }

    public Routine getById(int id) {
        String sql = """
                SELECT r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM routines r
                JOIN routine_types t ON r.type_id = t.id
                WHERE r.id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RoutineType type = new RoutineType(rs.getInt("tid"), rs.getString("tname"));
                    return new Routine(rs.getInt("rid"), rs.getString("rname"), type);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getById Routine.", e);
        }
    }

    public boolean update(int id, Routine newData) {
        String sql = "UPDATE routines SET name = ?, type_id = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newData.getName());
            ps.setInt(2, newData.getType().getId());
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in update Routine.", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM routines WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in delete Routine.", e);
        }
    }

    public Routine getByNameAndType(String name, int typeId) {
        String sql = """
                SELECT r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM routines r
                JOIN routine_types t ON r.type_id = t.id
                WHERE r.name = ? AND r.type_id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, typeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RoutineType type = new RoutineType(rs.getInt("tid"), rs.getString("tname"));
                    return new Routine(rs.getInt("rid"), rs.getString("rname"), type);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getByNameAndType Routine.", e);
        }
    }
}
