package com.project.organizer.dao;

import com.project.organizer.model.Literature;
import com.project.organizer.datab.ConnectionForDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LiteratureDaoImpl implements LiteratureDao {
    private Connection connection;

    public LiteratureDaoImpl() {
        try {
            this.connection = ConnectionForDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Literature literature) {
        String sql = "INSERT INTO literature (title, author, year, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, literature.getTitle());
            stmt.setString(2, literature.getAuthor());
            stmt.setInt(3, literature.getYear());
            stmt.setString(4, literature.getDescription());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    literature.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Literature findById(Long id) {
        String sql = "SELECT * FROM literature WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractLiterature(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Literature> findAll() {
        List<Literature> literatureList = new ArrayList<>();
        String sql = "SELECT * FROM literature";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                literatureList.add(extractLiterature(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return literatureList;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM literature WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Literature extractLiterature(ResultSet rs) throws SQLException {
        Literature literature = new Literature();
        literature.setId(rs.getLong("id"));
        literature.setTitle(rs.getString("title"));
        literature.setAuthor(rs.getString("author"));
        literature.setYear(rs.getInt("year"));
        literature.setDescription(rs.getString("description"));
        return literature;
    }
}
