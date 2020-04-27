package database.mysql;

import java.sql.*;
import java.util.ArrayList;

import model.*;

/**

 * Deze DAO creert CRUD functionaliteit voor model.User
 *
 * @author Wesley Wong
 */

@SuppressWarnings("ALL")
public class UserDAO extends AbstractDAO implements GenericDAO<User>{

    public UserDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    /**
     * @return ArrayList:   Deze bevat alle users van de database
     */
    public ArrayList<User> getAll() {
        {
            String sql = "Select * From User";
            ArrayList<User> result = new ArrayList<>();
            try {
                PreparedStatement ps = getStatement(sql);
                ResultSet rs = super.executeSelectPreparedStatement(ps);
                User user;
                while (rs.next()) {
                    int idUser = rs.getInt("idUser");
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    int idrol = Integer.parseInt(rs.getString("idRole"));
                            user = new User(idUser,gebruikersnaam,wachtwoord,idrol);
                            result.add(user);
                }
            } catch (SQLException e) {
                System.out.println("SQL error " + e.getMessage());
            }
            return result;
        }
    }

    /**
     * @param  id: idUser van User dat teruggegeven wordt
     * @return User: User aan de hand van gegeven idUser (int)
     */
    @Override
    public User getOneById(int id) {

        String sql = "Select * From User Where idUser = ?";

        User result = null;
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if (rs.next()) {
                result = createUser(rs);
            } else {
                System.out.println("User met dit id bestaat niet");
            }
        }
        catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    public User getOneByName(String userName){
        String sql = "SELECT * FROM User WHERE gebruikersnaam = ?;";
        User user = null;
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()){
                user = createUser(resultSet);

            }
        } catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return user;
    }

    /**
     * Maakt een user uit een resultset
     * @param rs:            wordt gemaakt na een MySQL statement
     * @return User:         vanuit een ResultSet
     * @throws SQLException: vangt vanuit de database
     */
    private User createUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("idUser");
        String gebruikersnaam = rs.getString("gebruikersnaam");
        String wachtwoord = rs.getString("wachtwoord");
        int idrol = Integer.parseInt(rs.getString("idRole"));
        return new User(id,gebruikersnaam, wachtwoord, idrol);
    }

    /**
     * @param idRole:           idRole van de Users die teruggegeven worden
     * @return ArrayList<User>: ArrayList van alle users met opgegeven idRole
     */
    public ArrayList<User> getAllbyidRole(int idRole) {
        {
            String sql = "Select * From User WHERE idRole = ?";
            ArrayList<User> result = new ArrayList<>();
            try {
                PreparedStatement ps = getStatement(sql);
                ps.setInt(1, idRole);
                ResultSet rs = super.executeSelectPreparedStatement(ps);
                User user;
                while (rs.next()) {
                    int idUser = rs.getInt("idUser");
                    String gebruikersnaam = rs.getString("gebruikersnaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    int idrol = Integer.parseInt(rs.getString("idRole"));
                    user = new User(idUser,gebruikersnaam,wachtwoord,idrol);
                    result.add(user);
                }
            } catch (SQLException e) {
                System.out.println("SQL error " + e.getMessage());
            }
            return result;
        }
    }

    /**
     * @param user:     User dat geslagen wordt in de database
     */
    @Override
    public void storeOne(User user) {
        String sql = "Insert into User(gebruikersnaam, wachtwoord, IdRole) values(?,?,?) ;";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, user.getGebruikersnaam());
            ps.setString(2, user.getWachtwoord());
            ps.setInt(3, user.getIdRol());
            executeManipulatePreparedStatement(ps);
            System.out.println("Gebruiker is opgeslagen");
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());

        }
    }

    /**
     * @param user:     User dat verwijdert wordt uit database
     */
    public void deleteOne(User user) {
        String sql = "DELETE FROM USER WHERE gebruikersnaam = ?;";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, user.getGebruikersnaam());
            executeManipulatePreparedStatement(ps);
            System.out.println("Gebruiker is verwijderd");
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());

        }
    }

    /**
     * @param user:     User dat aangepast wordt in de database
     */
    public void updateUser (User user){
        String sql = "update user set gebruikersnaam=?, wachtwoord=?, idRole=? where idUser =?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, user.getGebruikersnaam());
            ps.setString(2, user.getWachtwoord());
            ps.setInt(3, user.getIdRol());
            ps.setInt(4, user.getIdUser());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}
