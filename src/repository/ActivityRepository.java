package repository;

import db.DatabaseConnection;
import exception.DatabaseOperationException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository {

    public SelfCareActivityBase create(SelfCareActivityBase activity) {
        String sql = """
                INSERT INTO activities (name, kind, routine_id, minutes, intensity, product, step_count)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, activity.getName());
            ps.setString(2, activity.getKind());
            ps.setInt(3, activity.getRoutine().getId());

           
            if (activity instanceof WellnessActivity w) {
                ps.setInt(4, w.getMinutes());
                ps.setString(5, w.getIntensity());
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            } else if (activity instanceof BeautyCareActivity b) {
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, b.getProduct());
                ps.setInt(7, b.getStepCount());
            } else {
                
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    activity.setId(rs.getInt("id"));
                    return activity;
                }
                throw new DatabaseOperationException("Insert activity failed: no id returned.", null);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in create Activity.", e);
        }
    }

    public List<SelfCareActivityBase> getAll() {
        String sql = """
                SELECT a.id AS aid, a.name AS aname, a.kind, a.routine_id,
                       a.minutes, a.intensity, a.product, a.step_count,
                       r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM activities a
                JOIN routines r ON a.routine_id = r.id
                JOIN routine_types t ON r.type_id = t.id
                ORDER BY a.id
                """;

        List<SelfCareActivityBase> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToActivity(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getAll Activity.", e);
        }
    }

    public SelfCareActivityBase getById(int id) {
        String sql = """
                SELECT a.id AS aid, a.name AS aname, a.kind, a.routine_id,
                       a.minutes, a.intensity, a.product, a.step_count,
                       r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM activities a
                JOIN routines r ON a.routine_id = r.id
                JOIN routine_types t ON r.type_id = t.id
                WHERE a.id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToActivity(rs);
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getById Activity.", e);
        }
    }

    public boolean update(int id, SelfCareActivityBase newData) {
        String sql = """
                UPDATE activities
                SET name = ?, kind = ?, routine_id = ?, minutes = ?, intensity = ?, product = ?, step_count = ?
                WHERE id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newData.getName());
            ps.setString(2, newData.getKind());
            ps.setInt(3, newData.getRoutine().getId());

            if (newData instanceof WellnessActivity w) {
                ps.setInt(4, w.getMinutes());
                ps.setString(5, w.getIntensity());
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            } else if (newData instanceof BeautyCareActivity b) {
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, b.getProduct());
                ps.setInt(7, b.getStepCount());
            } else {
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            }

            ps.setInt(8, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in update Activity.", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM activities WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in delete Activity.", e);
        }
    }

    public SelfCareActivityBase getByNameAndRoutine(String name, int routineId) {
        String sql = """
                SELECT a.id AS aid, a.name AS aname, a.kind, a.routine_id,
                       a.minutes, a.intensity, a.product, a.step_count,
                       r.id AS rid, r.name AS rname,
                       t.id AS tid, t.name AS tname
                FROM activities a
                JOIN routines r ON a.routine_id = r.id
                JOIN routine_types t ON r.type_id = t.id
                WHERE a.name = ? AND a.routine_id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, routineId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToActivity(rs);
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("DB error in getByNameAndRoutine Activity.", e);
        }
    }

    private SelfCareActivityBase mapRowToActivity(ResultSet rs) throws SQLException {
        RoutineType type = new RoutineType(rs.getInt("tid"), rs.getString("tname"));
        Routine routine = new Routine(rs.getInt("rid"), rs.getString("rname"), type);

        String kind = rs.getString("kind");

        if ("WELLNESS".equalsIgnoreCase(kind)) {
            int minutes = rs.getInt("minutes");
            String intensity = rs.getString("intensity");
            return new WellnessActivity(rs.getInt("aid"), rs.getString("aname"), routine, minutes, intensity);
        } else { 
            String product = rs.getString("product");
            int stepCount = rs.getInt("step_count");
            return new BeautyCareActivity(rs.getInt("aid"), rs.getString("aname"), routine, product, stepCount);
        }
    }
}
