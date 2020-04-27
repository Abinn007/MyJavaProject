package database.mysql;
/**
 * @author Carolina Lira del Alto
 */
import model.Question;
import model.Quiz;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class QuestionDAO extends AbstractDAO implements GenericDAO<Question> {
    public QuestionDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    // Get all QUESTIONS: idQuestion, textQuestion, idQuiz
    @Override
    public ArrayList<Question> getAll() {
        String sql = "SELECT * FROM Question;";
        QuizDAO quizDAO = new QuizDAO(dBaccess);
        ArrayList<Question> questions = new ArrayList<>();
        try{
            PreparedStatement ps = getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            Question question;
            while (rs.next()) {
                int idQuestion = rs.getInt("idQuestion");
                String textQuestion = rs.getString("textQuestion");
                int quizID = rs.getInt("idQuiz");
                Quiz quiz = quizDAO.getOneById(quizID);
                question = new Question(idQuestion, textQuestion, quiz);
                questions.add(question);
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return questions;
    }

    // Get one QUESTION by QuestionID
    @Override
    public Question getOneById(int questionID) {
        String sql = "SELECT * FROM Question WHERE idQuestion = ?;";
        QuizDAO quizDAO = new QuizDAO(dBaccess);
        Question question = null;
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, questionID);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if(rs.next()) {
                int idQuestion = rs.getInt("idQuestion");
                String textQuestion = rs.getString("textQuestion");
                int idQuiz = rs.getInt("idQuiz");
                Quiz quiz = quizDAO.getOneById(idQuiz);
                question = new Question(idQuestion, textQuestion, quiz);
            } else {
                System.out.println("Vraag met dit ID bestaat niet");
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return question;
    }

    // Store new QUESTION
    @Override
    public void storeOne(Question question) {
        String sql = "INSERT INTO `quizmaster`.`Question` (`textQuestion`, `idQuiz`) VALUES (?, ?);";
        try{
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, question.getTextQuestion());
            ps.setInt(2, question.getQuiz().getIdQuiz());
            int id = executeInsertPreparedStatement(ps);
            question.setIdQuestion(id);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Update existing QUESTION
    public void updateOne(Question question) {
        String sql = "UPDATE Question SET textQuestion = ?, idQuiz = ? WHERE idQuestion = ?;";
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, question.getTextQuestion());
            ps.setInt(2, question.getQuiz().getIdQuiz());
            ps.setInt(3, question.getIdQuestion());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Delete existing QUESTION
    public void deleteOne(Question question) {
        String sql = "DELETE FROM Question WHERE idQuestion = ?;";
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, question.getIdQuestion());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Get all QUESTIONS by QuizID
    public ArrayList<Question> getAllFromQuizId(int id) {
        String sql = "SELECT * FROM Question WHERE idQuiz = ?;";
        QuizDAO quizDAO = new QuizDAO(dBaccess);
        ArrayList<Question> questions = new ArrayList<>();
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            Question question;
            while (rs.next()) {
                int idQuestion = rs.getInt("idQuestion");
                String textQuestion = rs.getString("textQuestion");
                int quizID = rs.getInt("idQuiz");
                Quiz quiz = quizDAO.getOneById(quizID);
                question = new Question(idQuestion, textQuestion, quiz);
                questions.add(question);
            }
        } catch(SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return questions;
    }
}
