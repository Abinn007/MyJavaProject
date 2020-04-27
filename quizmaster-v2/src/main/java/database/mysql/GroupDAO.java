package database.mysql;
import model.Course;
import model.Group;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Deze DAO class is om data's weg te schrijven naar SQL database en terughalen vanuit database.
 * @author HRGautam
 */
public class GroupDAO extends AbstractDAO implements GenericDAO<Group> {

    public GroupDAO(DBAccess dBaccess) {

        super(dBaccess);

    }
    // Om een group toe te voegen in de database.
    @Override
    public void storeOne(Group group) {
        String sql = "INSERT INTO Quizmaster.Group (nameGroup, idCourse, idTeacher) values(?,?,?) ;";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, group.getNameGroup());
            ps.setInt(2,group.getCourse().getIdCourse());
            ps.setInt(3,group.getUser().getIdUser());
            executeManipulatePreparedStatement(ps);

        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Om een lijst van alle groepen te halen
    @Override
    public ArrayList<Group> getAll() {
        UserDAO userDAO = new UserDAO(dBaccess);
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quizmaster.Group";
        ArrayList<Group> result = new ArrayList<>();
        try {
            PreparedStatement ps = getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            while (rs.next()) {
                int idGroup = rs.getInt("idGroup");
                String groupNaam = rs.getString("nameGroup");
                Course course =courseDAO.getOneById(rs.getInt("idCourse"));
                User teacher =userDAO.getOneById(rs.getInt("idTeacher"));
                result.add(new Group(idGroup,groupNaam,course,teacher));
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    // Om een lijst van alle groepen te halen met een idDocent.

    public ArrayList<Group> getAllByIdUser(int idUser) {
        UserDAO userDAO = new UserDAO(dBaccess);
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quizmaster.Group WHERE idTeacher = ?";
        ArrayList<Group> result = new ArrayList<>();
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, idUser);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            while (rs.next()) {
                int idGroup = rs.getInt("idGroup");
                String groupNaam = rs.getString("nameGroup");
                Course course =courseDAO.getOneById(rs.getInt("idCourse"));
                User teacher =userDAO.getOneById(rs.getInt("idTeacher"));
                result.add(new Group(idGroup,groupNaam,course,teacher));
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    // Om een lijst van alle groepen te halen met idCourse

    public ArrayList<Group> getAllByIdCourse(int idCourse) {
        UserDAO userDAO = new UserDAO(dBaccess);
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quizmaster.Group WHERE idCourse = ?";
        ArrayList<Group> result = new ArrayList<>();
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, idCourse);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            while (rs.next()) {
                int idGroup = rs.getInt("idGroup");
                String groupNaam = rs.getString("nameGroup");
                Course course =courseDAO.getOneById(idCourse);
                User teacher =userDAO.getOneById(rs.getInt("idTeacher"));
                result.add(new Group(idGroup,groupNaam,course,teacher));
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }


    // Om een group te halen met idGroup
    @Override
    public Group getOneById(int idGroup) {
        UserDAO userDAO = new UserDAO(dBaccess);
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quizmaster.Group Where idGroup = ?";
        Group result = null;
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, idGroup);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if (rs.next()) {
                int groupId = rs.getInt("idGroup");
                String groupNaam = rs.getString("nameGroup");
                Course course =courseDAO.getOneById(rs.getInt("idCourse"));
                User teacher =userDAO.getOneById(rs.getInt("idTeacher"));
                result = new Group(groupId,groupNaam,course,teacher);
            }else {
                System.out.println("Group met dit id  bestaat niet");
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    // Om een  groep te wijzigen
    public void updateGroup(Group group) {
        String sql = "UPDATE Quizmaster.Group SET nameGroup = ?,idCourse = ?, idTeacher = ? where idGroup = ?;";
        try {
            PreparedStatement ps = getStatement(sql);

            ps.setString(1,group.getNameGroup());
            ps.setInt(2,group.getCourse().getIdCourse());
            ps.setInt(3, group.getUser().getIdUser());
            ps.setInt(4, group.getIdGroup());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Om een groep te verwijderen
    public void deleteGroup(Group group) {
        String sql = "DELETE FROM Quizmaster.Group WHERE idGroup = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, group.getIdGroup());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }



}