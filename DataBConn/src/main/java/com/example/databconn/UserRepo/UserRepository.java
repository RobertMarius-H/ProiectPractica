package com.example.databconn.UserRepo;

import com.example.databconn.config.DatabaseConfig;
import com.example.databconn.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
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


    public void deleteById(Integer id) throws SQLException {
        String query = "DELETE FROM Persoane WHERE ID_Pers = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<User> filterUsers(Integer id, String nume, String prenume, Integer anNastere, Integer idOcupatie, Integer idOrasDomiciliu) {
        String query = "SELECT * FROM Persoane WHERE 1=1";

        if(id != null){
            query += " AND ID_Pers = ?";
        }

        if (nume != null && !nume.isEmpty()) {
            query += " AND Nume LIKE ?";
        }
        if (prenume != null && !prenume.isEmpty()) {
            query += " AND Prenume LIKE ?";
        }
        if (anNastere != null) {
            query += " AND An_Nastere = ?";
        }
        if (idOcupatie != null) {
            query += " AND ID_Ocupatie = ?";
        }
        if (idOrasDomiciliu != null) {
            query += " AND ID_Oras_Domiciliu = ?";
        }

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int paramIndex = 1;

            if(id!= null){
                statement.setInt(paramIndex++,id);
            }

            if (nume != null && !nume.isEmpty()) {
                statement.setString(paramIndex++, "%" + nume + "%");
            }
            if (prenume != null && !prenume.isEmpty()) {
                statement.setString(paramIndex++, "%" + prenume + "%");
            }
            if (anNastere != null) {
                statement.setInt(paramIndex++, anNastere);
            }
            if (idOcupatie != null) {
                statement.setInt(paramIndex++, idOcupatie);
            }
            if (idOrasDomiciliu != null) {
                statement.setInt(paramIndex++, idOrasDomiciliu);
            }
            //System.out.println("checked REPO1");

            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = new User();
                    user.setIdPers(resultSet.getInt("ID_Pers"));
                    user.setNume(resultSet.getString("Nume"));
                    user.setPrenume(resultSet.getString("Prenume"));
                    user.setAnNastere(resultSet.getInt("An_Nastere"));
                    user.setIdOcupatie(resultSet.getInt("ID_Ocupatie"));
                    user.setIdOrasDomiciliu(resultSet.getInt("ID_Oras_Domiciliu"));
                    users.add(user);
                   // System.out.println("checked REPO2");
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}