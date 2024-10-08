package com.example.databconn.UserRepo;

import com.example.databconn.config.DatabaseConfig;
import com.example.databconn.model.UserLogin;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserLoginRepository {

    // Save a new UserLogin record
    public void save(UserLogin user) throws SQLException {
        String query = "INSERT INTO user_inregistrat (username, password, status, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();
        }
    }

    // Update an existing UserLogin record
    public void update(UserLogin user) throws SQLException {
        String query = "UPDATE user_inregistrat SET username = ?, password = ?, status = ?, email = ? WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getStatus());
            statement.setString(4, user.getEmail());
            statement.setLong(5, user.getId());

            statement.executeUpdate();
        }
    }

    // Find UserLogin by username
    public UserLogin findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM user_inregistrat WHERE username = ?";
        UserLogin user = null;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new UserLogin();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setStatus(resultSet.getString("status"));
                user.setEmail(resultSet.getString("email"));
            }
        }

        return user;
    }

    // Delete UserLogin by id
    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM user_inregistrat WHERE id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
