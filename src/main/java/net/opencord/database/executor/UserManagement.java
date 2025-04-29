package net.opencord.database.executor;

import net.opencord.database.Maria;
import java.util.UUID;

public class UserManagement {

    public static void createUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        Maria.execute(sql, username, password, email);
    }

    public static boolean userExists(String email) {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE email = ?";
        var result = Maria.execute(sql, email);

        if (result.isEmpty()) {
            return false;
        }

        Object countValue = result.get(0).get("count");
        int count = 0;

        if (countValue instanceof Number) {
            count = ((Number) countValue).intValue();
        } else if (countValue != null) {
            try {
                count = Integer.parseInt(countValue.toString());
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return count > 0;
    }


    public static void deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        Maria.execute(sql, email);
    }

    public static String searchUUIDForName(String uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        var result = Maria.execute(sql, uuid);

        if (result.isEmpty()) {
            return null; // or handle user not found
        }

        try {
            return (String) result.get(0).get("username");
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateUserPassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        Maria.execute(sql, newPassword, email);
    }

}
