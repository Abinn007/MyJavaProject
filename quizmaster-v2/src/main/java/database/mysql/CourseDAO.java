package database.mysql;

import model.Course;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author lisa Kemeling
 * class voor ophalen, insert, update en delete gegevens uit database Quizmaster tabel Course
 */

public class CourseDAO extends AbstractDAO implements GenericDAO<Course> {
    public CourseDAO(DBAccess dBaccess) {
        super(dBaccess);
    }


    public ArrayList<Course> getAllById(int idCoordinator) {
        UserDAO userDAO = new UserDAO(dBaccess);
        String sql = "SELECT * FROM Course WHERE idCoordinator = ?;";
        ArrayList<Course> courses = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, idCoordinator);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Course course;
            while(resultSet.next()){
                int id = resultSet.getInt("idCourse");
                String nameCourse = resultSet.getString("nameCourse");
                User user = userDAO.getOneById(idCoordinator);
                course = new Course(id , nameCourse, user);
                courses.add(course);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return courses;
    }


    @Override
    public ArrayList<Course> getAll() {
        UserDAO userDAO = new UserDAO(dBaccess);
        String sql = "SELECT * FROM Course;";
        ArrayList<Course> courses = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Course course;
            while(resultSet.next()){
                int id = resultSet.getInt("idCourse");
                String nameCourse = resultSet.getString("nameCourse");
                User user = userDAO.getOneById(resultSet.getInt("idCoordinator"));
                course = new Course(id , nameCourse, user);
                courses.add(course);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return courses;
    }




    @Override
    public Course getOneById(int id) {
        String sql = "SELECT * FROM Course WHERE idCourse = ?;";
        Course course = null;
        UserDAO userDAO = new UserDAO(dBaccess);
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if(resultSet.next()){
                String nameCourse = resultSet.getString("nameCourse");
                User user = userDAO.getOneById(resultSet.getInt("idCoordinator"));
                course = new Course(id, nameCourse, user);
            } else {
                System.out.println("Quiz met dit id nummer bestaat niet");
            }
        }catch(SQLException fout){
            System.out.println("SQL error " + fout.getMessage());
        }
        return course;
    }


    @Override
    public void storeOne(Course course) {
        String sql = "INSERT INTO `Quizmaster`.`course` (`nameCourse`, `idCoordinator`) VALUES (?, ?);";
        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, course.getNameCourse());
            preparedStatement.setInt(2,course.getUser().getIdUser());
            int key = executeInsertPreparedStatement(preparedStatement);
            course.setIdCourse(key);
        }catch(SQLException fout){
            System.out.println("SQL error " + fout.getMessage());
        }

    }

    public void deleteOne(Course course){
        String sql = "DELETE FROM Course WHERE idCourse = ?;";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, course.getIdCourse());
            executeManipulatePreparedStatement(preparedStatement);
        }catch (SQLException fout){
            System.out.println("SQL error "+ fout.getMessage());
        }
    }

    public void updateCourse(Course course){
        String sql = "UPDATE Course SET nameCourse = ?, idCoordinator = ? WHERE idCourse = ?;";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, course.getNameCourse());
            preparedStatement.setInt(2, course.getUser().getIdUser());
            preparedStatement.setInt(3, course.getIdCourse());
            executeManipulatePreparedStatement(preparedStatement);
        }catch(SQLException fout){
            System.out.println("SQL error "+ fout.getMessage());
        }
    }
}