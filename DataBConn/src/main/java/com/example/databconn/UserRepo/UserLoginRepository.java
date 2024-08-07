package com.example.databconn.UserRepo;

import com.example.databconn.config.DatabaseConfig;
import com.example.databconn.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserLoginRepository {

    public void save(UserLogin user) throws SQLException {
        String query = "INSERT INTO user_inregistrat (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();
        }
    }

    public void update(UserLogin user) throws SQLException {
        String query = "UPDATE user_inregistrat SET username = ?, password = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());

            statement.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM user_inregistrat WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

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
            }
        }

        return user;
    }
}
