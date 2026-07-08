package com.unfamoussoul.test.database;

import com.unfamoussoul.sapi.api.database.DatabaseConfig;
import com.unfamoussoul.sapi.api.database.DatabaseHandler;
import com.unfamoussoul.sapi.api.database.Migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TestDatabaseHandler extends DatabaseHandler {

    public TestDatabaseHandler(DatabaseConfig config) {
        super(
                config,
                List.of(
                        new Migration(1, c -> c.createStatement()
                                .execute("CREATE TABLE logbook (id INTEGER PRIMARY KEY, route TEXT)")),
                        new Migration(2, c -> c.createStatement()
                                .execute("ALTER TABLE logbook ADD COLUMN ip TEXT"))
                )
        );
    }

    public void addLog(String route, String ip) {
        try (Connection conn = connection()) {

            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO logbook (route, ip) VALUES (?, ?)")) {
                ps.setString(1, route);
                ps.setString(2, ip);
                ps.executeUpdate();
            }

        } catch (SQLException _) {
            //
        }
    }
}
