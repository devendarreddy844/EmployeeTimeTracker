package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Users;

public class DAO {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/employee_db?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "deva2002#";

    private static final String VALIDATE_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = ?";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Users validate(String username, String password) {
        Users user = null;

        Connection connection = null;
        PreparedStatement validateStmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            validateStmt = connection.prepareStatement(VALIDATE_USER_SQL);
            validateStmt.setString(1, username);
            validateStmt.setString(2, password);

            rs = validateStmt.executeQuery();
            if (rs.next()) {
                user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, validateStmt, rs);
        }

        return user;
    }
}
