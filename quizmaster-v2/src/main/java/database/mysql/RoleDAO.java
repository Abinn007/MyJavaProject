package database.mysql;
import java.sql.*;
import java.util.ArrayList;
import model.*;
/**
 * Deze DAO is voor de Role tabel dat idRol linked aan een naam
 *
 * @author Wesley Wong
 */
public class RoleDAO extends AbstractDAO {
    public RoleDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    /**
     * @return ArrayList<Role>   ArrayList met alle rollen
     */
    public ArrayList<Role> getAll() {
        {
            String sql = "Select * From Role";
            ArrayList<Role> result = new ArrayList<>();
            try {
                PreparedStatement ps = getStatement(sql);
                ResultSet rs = super.executeSelectPreparedStatement(ps);
                Role role;
                while (rs.next()) {
                    int idRole = rs.getInt("idRole");
                    String nameRole = rs.getString("nameRole");
                    role = new Role(idRole,nameRole);
                    result.add(role);
                }
            } catch (SQLException e) {
                System.out.println("SQL error " + e.getMessage());
            }
            return result;
        }
    }

    /**
     * Zoek op idRole, return nameRol
     * @param id    idRole om op te zoeken
     * @return      Role dat bij de idRole hoort
     */
    public Role getOneById(int id) {
        String sql = "Select * From Role Where idRole = ?";
        Role result = null;
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if (rs.next()) {
                result = createRole(rs);
            } else {
                System.out.println("Rol met dit id bestaat niet");
            }
        }
        catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    /**
     * Zoek op idRole, return nameRole als String
     * @param id    idRole om op te zoeken
     * @return      String met role naam dat bij idRole hoort
     */
    public String getNamebyId(int id) {
        String sql = "Select * From quizmaster.role Where idRole = ?";
        String result = null;
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if (rs.next()) {
                result  = createRole(rs).toString();
            } else {
                System.out.println("Rol met dit id bestaat niet");
            }
        }
        catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    /**
     * Zoek op nameRole en krijg terug de idRole
     * @param name  nameRole om op te zoeken
     * @return      bijbehorende idRol als int
     */
    public int getIdByName(String name) {
        String sql = "Select * From quizmaster.role Where nameRole = ?";
        int result = 0;
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, name);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if (rs.next()) {
                result  = createRole(rs).getIdRole();
            }
        }
        catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    /**
     * Maakt uit een resultset een nieuwe Role aan
     */
    private Role createRole(ResultSet rs) throws SQLException {
        int idRole = rs.getInt("idRole");
        String nameRole = rs.getString("nameRole");
        return new Role(idRole, nameRole);
    }


}