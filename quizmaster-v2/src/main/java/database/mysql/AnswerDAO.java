package database.mysql;
/**
 * @author Carolina Lira del Alto
 */
import model.Answer;
import model.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class AnswerDAO extends AbstractDAO implements GenericDAO<Answer> {
    public AnswerDAO(DBAccess dbAccess) {super(dbAccess);}

    // Get all ANSWERS: idAnswer, textAnswer, isCorrect, idQuestion
    @Override
    public ArrayList<Answer> getAll() {
        String sql = "SELECT * FROM Answer;";
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        ArrayList<Answer> answers = new ArrayList<>();
        try{
            PreparedStatement ps = getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            Answer answer;
            while (rs.next()) {
                int idAnswer = rs.getInt("idAnswer");
                String textAnswer = rs.getString("textAnswer");
                boolean isCorrect = rs.getBoolean("isCorrect");
                int questionID = rs.getInt("idQuestion");
                Question question = questionDAO.getOneById(questionID);
                answer = new Answer(idAnswer, textAnswer, isCorrect, question);
                answers.add(answer);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return answers;
    }

    // Get all ANSWERS by QuestionID: idAnswer, textAnswer, isCorrect, idQuestion
    public ArrayList<Answer> getAllByQuestionid(int questionid) {
        String sql = "SELECT * FROM Answer WHERE idQuestion = ?;";
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        ArrayList<Answer> answers = new ArrayList<>();
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, questionid);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            Answer answer;
            while (rs.next()) {
                int idAnswer = rs.getInt("idAnswer");
                String textAnswer = rs.getString("textAnswer");
                boolean isCorrect = rs.getBoolean("isCorrect");
                int questionID = rs.getInt("idQuestion");
                Question question = questionDAO.getOneById(questionID);
                answer = new Answer(idAnswer, textAnswer, isCorrect, question);
                answers.add(answer);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return answers;
    }

    // Get one ANSWER by AnswerID
    @Override
    public Answer getOneById(int answerID) {
        String sql = "SELECT * FROM Answer WHERE idAnswer = ?;";
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        Answer answer = null;
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, answerID);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if(rs.next()) {
                int idAnswer = rs.getInt("idAnswer");
                String textAnswer = rs.getString("textAnswer");
                boolean isCorrect = rs.getBoolean("isCorrect");
                int questionID = rs.getInt("idQuestion");
                Question question = questionDAO.getOneById(questionID);
                answer = new Answer(idAnswer, textAnswer, isCorrect, question);
            } else {
                System.out.println("Antwoord met dit ID bestaat niet");
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return answer;
    }

    // Store new ANSWER
    @Override
    public void storeOne(Answer answer) {
        String sql = "INSERT INTO `quizmaster`.`Answer` (`textAnswer`, `isCorrect`, `idQuestion`) VALUES (?, ?, ?);"
        ;
        try{
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, answer.getTextAnswer());
            ps.setBoolean(2, answer.isCorrect());
            ps.setInt(3, answer.getQuestion().getIdQuestion());
            executeInsertPreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Update existing ANSWER
    public void updateOne(Answer answer) {
        String sql = "UPDATE Answer SET textAnswer = ?, isCorrect = ?, idQuestion = ? WHERE idAnswer = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, answer.getTextAnswer());
            ps.setBoolean(2, answer.isCorrect());
            ps.setInt(3, answer.getQuestion().getIdQuestion());
            ps.setInt(4, answer.getIdAnswer());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    // Delete existing ANSWER
    public void deleteOne(Answer answer) {
        String sql = "DELETE FROM Answer WHERE idAnswer = ?;";
        try{
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, answer.getIdAnswer());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}
