package database.mysql;

import model.Course;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author lisa kemeling
 * class voor ophalen, insert, update en delete gegevens uit database Quizmaster tabel Quiz
 */

public class QuizDAO extends AbstractDAO implements GenericDAO<Quiz> {

    public QuizDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    public ArrayList<Quiz> getAllByCourseId(int courseId){
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quiz WHERE idCourse = ?;";
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Quiz quiz;
            while(resultSet.next()){
                int idQuiz = resultSet.getInt("idQuiz");
                String naam = resultSet.getString("nameQuiz");
                int succesdefinitie = resultSet.getInt("resultaatdefinitie");
                Course course = courseDAO.getOneById(courseId);
                quiz = new Quiz(idQuiz, naam, succesdefinitie, course);
                quizzes.add(quiz);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return quizzes;
    }


    @Override
    public ArrayList<Quiz> getAll() {
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        String sql = "SELECT * FROM Quiz;";
        ArrayList<Quiz> quizzes = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Quiz quiz;
            while(resultSet.next()){
                int idQuiz = resultSet.getInt("idQuiz");
                String naam = resultSet.getString("nameQuiz");
                int succesdefinitie = resultSet.getInt("resultaatdefinitie");
                int courseId = resultSet.getInt("idCourse");
                Course course = courseDAO.getOneById(courseId);
                quiz = new Quiz(idQuiz, naam, succesdefinitie, course);
                quizzes.add(quiz);
            }
        }catch(SQLException fout){
            System.out.println("SQL error" + fout.getMessage());
        }
        return quizzes;
    }

    @Override
    public Quiz getOneById(int id) {
        String sql = "SELECT * FROM Quiz WHERE idQuiz = ?;";
        Quiz quiz = null;
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if(resultSet.next()){
                String nameQuiz = resultSet.getString("nameQuiz");
                int succesdefinitie = resultSet.getInt("resultaatdefinitie");
                int idC = resultSet.getInt("idCourse");
                Course course = courseDAO.getOneById(idC);
                quiz = new Quiz(id, nameQuiz, succesdefinitie, course);
            } else {
                System.out.println("Quiz met dit id nummer bestaat niet");
            }
        }catch(SQLException fout){
            System.out.println("SQL error " + fout.getMessage());
        }
        return quiz;
    }

    public Quiz getOneByCourseId(int id) {
        String sql = "SELECT * FROM Quiz WHERE idCourse = ?;";
        Quiz quiz = null;
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if(resultSet.next()){
                String nameQuiz = resultSet.getString("nameQuiz");
                int succesdefinitie = resultSet.getInt("resultaatdefinitie");
                int idQ = resultSet.getInt("idQuiz");
                Course course = courseDAO.getOneById(id);
                quiz = new Quiz(idQ, nameQuiz, succesdefinitie, course);
            } else {
                System.out.println("Quiz met dit id nummer bestaat niet");
            }
        }catch(SQLException fout){
            System.out.println("SQL error " + fout.getMessage());
        }
        return quiz;
    }


    @Override
    public void storeOne(Quiz quiz) {
        String sql = "INSERT INTO `Quizmaster`.`Quiz` (`nameQuiz`, `resultaatdefinitie`, `idCourse`) VALUES (?, ?, ?);";
        try{
            PreparedStatement preparedStatement = getStatementWithKey(sql);
            preparedStatement.setString(1, quiz.getNaam());
            preparedStatement.setInt(2,quiz.getSuccesdefinitie());
            preparedStatement.setInt(3, quiz.getCourse().getIdCourse());
            int key = executeInsertPreparedStatement(preparedStatement);
            quiz.setIdQuiz(key);
        }catch(SQLException fout){
            System.out.println("SQL error " + fout.getMessage());
        }
    }

    public void updateQuiz(Quiz quiz){
        String sql = "UPDATE Quiz SET nameQuiz = ?, resultaatdefinitie = ?, idCourse = ? WHERE idQuiz = ?;";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, quiz.getNaam());
            preparedStatement.setInt(2, quiz.getSuccesdefinitie());
            preparedStatement.setInt(3, quiz.getCourse().getIdCourse());
            preparedStatement.setInt(4, quiz.getIdQuiz());
            executeManipulatePreparedStatement(preparedStatement);
        }catch(SQLException fout){
            System.out.println("SQL error "+ fout.getMessage());
        }
    }

    public void deleteOne(Quiz quiz){
        String sql = "DELETE FROM Quiz WHERE idQuiz = ?;";
        try{
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, quiz.getIdQuiz());
            executeManipulatePreparedStatement(preparedStatement);
        }catch (SQLException fout){
            System.out.println("SQL error "+ fout.getMessage());
        }
    }


}
