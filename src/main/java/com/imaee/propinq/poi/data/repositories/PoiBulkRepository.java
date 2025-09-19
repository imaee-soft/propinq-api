package com.imaee.propinq.poi.data.repositories;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PoiBulkRepository {

    private final JdbcTemplate jdbc;

    // Usar nombres y tipos que coincidan con la tabla
    public record Row(String id, String type, String name, double latitude, double longitude) {}

    public void truncate() {
        jdbc.execute("TRUNCATE TABLE poi");
    }

    public void insertBatch(List<Row> rows) { insertBatch(rows, 8000); }
    public void insertBatch(List<Row> rows, int chunkSize) {
        if (rows == null || rows.isEmpty()) return;
        final String sql = "INSERT INTO poi (id, type, name, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        for (int i = 0; i < rows.size(); i += chunkSize) {
            var chunk = rows.subList(i, Math.min(i + chunkSize, rows.size()));
            jdbc.batchUpdate(sql, chunk, chunk.size(), (ps, r) -> {
                ps.setString(1, r.id());
                ps.setString(2, r.type());      // <- tipo string, no ordinal
                ps.setString(3, r.name());
                ps.setDouble(4, r.latitude());
                ps.setDouble(5, r.longitude());
            });
        }
    }

    public void upsertBatch(List<Row> rows) { upsertBatch(rows, 8000); }
    public void upsertBatch(List<Row> rows, int chunkSize) {
        if (rows == null || rows.isEmpty()) return;
        final String sql = """
            INSERT INTO poi (id, type, name, latitude, longitude)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
              type=VALUES(type), name=VALUES(name), latitude=VALUES(latitude), longitude=VALUES(longitude)
            """;
        for (int i = 0; i < rows.size(); i += chunkSize) {
            var chunk = rows.subList(i, Math.min(i + chunkSize, rows.size()));
            jdbc.batchUpdate(sql, chunk, chunk.size(), (ps, r) -> {
                ps.setString(1, r.id());
                ps.setString(2, r.type());      // <- tipo string, no ordinal
                ps.setString(3, r.name());
                ps.setDouble(4, r.latitude());
                ps.setDouble(5, r.longitude());
            });
        }
    }
}