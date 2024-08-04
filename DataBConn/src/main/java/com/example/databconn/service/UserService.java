package com.example.databconn.service;

import com.example.databconn.UserRepo.UserRepository;
import com.example.databconn.config.DatabaseConfig;
import com.example.databconn.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository = new UserRepository();

    // Metodă privată pentru a mapa ResultSet la obiectul User
    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setIdPers(resultSet.getInt("ID_Pers"));
        user.setNume(resultSet.getString("Nume"));
        user.setPrenume(resultSet.getString("Prenume"));
        user.setAnNastere(resultSet.getInt("An_nastere"));
        user.setIdOcupatie(resultSet.getInt("ID_Ocupatie"));
        user.setIdOrasDomiciliu(resultSet.getInt("ID_Oras_Domiciliu"));
        return user;
    }

    // Metodă pentru a obține toți utilizatorii
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Persoane";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            logger.error("Error while retrieving users", e);
        }

        return users;
    }

    // Metodă pentru a obține un utilizator după ID
    public User getUserById(Integer id) {
        User user = null;
        String query = "SELECT * FROM Persoane WHERE ID_Pers = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = mapUser(resultSet);
            }

        } catch (SQLException e) {
            logger.error("Error while retrieving user with ID: {}", id, e);
        }

        return user;
    }

    // Metodă pentru a salva un utilizator
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (SQLException e) {
            logger.error("Error while saving user", e);
        }
    }

    public void updateUser(User user) {
        try {
            userRepository.update(user);
        } catch (SQLException e) {
            logger.error("Error while updating user", e);
        }
    }

    // Metodă pentru a șterge un utilizator după ID
    public void deleteUserById(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (SQLException e) {
            logger.error("Error while deleting user with ID: {}", id, e);
        }
    }
}