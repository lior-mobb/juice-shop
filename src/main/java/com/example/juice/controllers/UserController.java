package com.example.juice.controllers;

import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/search")
    public String searchUsers(@RequestParam String username) throws SQLException {
        // VULNERABLE: Direct SQL injection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/juice");
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(query);

        StringBuilder result = new StringBuilder();
        while (rs.next()) {
            result.append(rs.getString("username")).append("\n");
        }
        return result.toString();
    }
}
