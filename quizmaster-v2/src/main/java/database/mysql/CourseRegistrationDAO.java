package database.mysql;
import model.Course;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO voor een tussentabel van Courses en Users
 * Hierin wordt opgeslagen in welke Courses Users (studenten) ingeschreven staan
 *
 * @author Wesley Wong
 */

public class CourseRegistrationDAO extends AbstractDAO{

    public CourseRegistrationDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    /**
     * Slaat een RegistrationCourse op met een idStudent (ofterwel idUser) en idCourse.
     * @param idStudent     idUser
     * @param idCourse      id van Course waarop User met idStudent ingeschreven wordt
     */
    public void storeOne(int idStudent, int idCourse) {
        String sql = "INSERT INTO quizmaster.courseregistration (`idStudent`, `idCourse`) VALUES (?, ?);";
            try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setInt(1,idStudent);
            ps.setInt(2, idCourse);
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * Haalt alle Users op die ingeschreven staant bij idCourse
     * @param idCourse  id van Course waarop gezocht wordt
     * @return          ArrayList<User> waarin alle gebruikers zitten die ingeschreven staan
     */
    public ArrayList<User> getAllStudentsByIdCourse(int idCourse){
        String sql = "SELECT * FROM quizmaster.courseregistration where idCourse = ?";
        UserDAO userDAO = new UserDAO(dBaccess);
        ArrayList<User> usersByIdCourse = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, idCourse);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                int idStudent = resultSet.getInt("idStudent");
                User u = userDAO.getOneById(idStudent);
                usersByIdCourse.add(u);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return usersByIdCourse;
    }

    /**
     * Zoekt alle curussen op van een Student
     * @param idStudent     id van Student (idUser waarop gezocht wordt)
     * @return              een ArrayList<Course> van alle cursussen waarpo idStudent ingeschreven staat
     */
    public ArrayList<Course> getAllCoursesByIdStudent(int idStudent){
        String sql = "SELECT * FROM quizmaster.courseregistration where idStudent = ?";
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        ArrayList<Course> usersByIdCourse = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, idStudent);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while(resultSet.next()){
                int idCourse = resultSet.getInt("idCourse");
                Course c = courseDAO.getOneById(idCourse);
                usersByIdCourse.add(c);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return usersByIdCourse;
    }

    /**
     * Delete een record van RegistrationCourse
     * @param idStudent    id van Student (idUser)
     * @param idCourse     id van Course (idCourse)
     */
    public void deleteOne(int idStudent, int idCourse){
        String sql = "DELETE FROM quizmaster.courseregistration WHERE idStudent = ? AND idCourse = ?;";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, idStudent);
            preparedStatement.setInt(2, idCourse);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException fout){
            System.out.println("SQL error "+ fout.getMessage());
        }
    }
}

