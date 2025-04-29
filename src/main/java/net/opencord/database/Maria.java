package net.opencord.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
public class Maria {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public Maria(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static List<Map<String, Object>> execute(String sql, Object... params) {
        if (sql.trim().toUpperCase().startsWith("SELECT")) {
            return jdbcTemplate.queryForList(sql, params);
        } else {
            jdbcTemplate.update(sql, params);
            return List.of();
        }
    }

    public static void initialize() {
        execute("CREATE TABLE IF NOT EXISTS users (uuid CHAR(36) PRIMARY KEY DEFAULT (UUID()), username VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL)");
    }

}
