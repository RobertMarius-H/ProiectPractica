package com.example.databconn.UserRepo;

import com.example.databconn.config.DatabaseConfig;
import com.example.databconn.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {

    public void save(User user) throws SQLException {
        String query = "INSERT INTO Persoane (Nume, Prenume, An_nastere, ID_Ocupatie, ID_Oras_Domiciliu) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getNume());
            statement.setString(2, user.getPrenume());
            statement.setInt(3, user.getAnNastere());
            statement.setInt(4, user.getIdOcupatie());
            statement.setInt(5, user.getIdOrasDomiciliu());

            statement.executeUpdate();
        }
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE Persoane SET Nume = ?, Prenume = ?, An_nastere = ?, ID_Ocupatie = ?, ID_Oras_Domiciliu = ? WHERE ID_Pers = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getNume());
            statement.setString(2, user.getPrenume());
            statement.setInt(3, user.getAnNastere());
            statement.setInt(4, user.getIdOcupatie());
            statement.setInt(5, user.getIdOrasDomiciliu());
            statement.setInt(6, user.getIdPers());

            statement.executeUpdate();
        }
    }

    //
}