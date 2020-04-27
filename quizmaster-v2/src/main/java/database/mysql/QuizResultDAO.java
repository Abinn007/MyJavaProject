package database.mysql;

import model.QuizResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Deze DAO creert CRUD functionaliteit voor QuizResult
 *
 * @author Wesley Wong
 */


public class QuizResultDAO extends AbstractDAO{
    public QuizResultDAO(DBAccess dBaccess) {
        super(dBaccess);
    }

    /**
     * Slaat een QuizResult op
     * @param qr     QuizResult om op te slaan.
     */
    public void storeOne(QuizResult qr) {
        String sql = "INSERT INTO quizmaster.quizresult (`idQuiz`, `idStudent`, `passed`, `datetime`) VALUES (?, ?, ?, CURRENT_TIMESTAMP);";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setInt(1,qr.getIdQuiz());
            ps.setInt(2, qr.getIdStudent());
            ps.setBoolean(3, false);
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * Update een QuizResult
     * @param qr     quizresult om te updaten
     */
    public void updateQuizResult (QuizResult qr){
        String sql = "UPDATE quizmaster.quizresult SET passed = ? WHERE idAttempt = ?";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setBoolean(1, qr.getPassed());
            ps.setInt(2, qr.getIdAttempt());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * Haalt de laatste QuizResult van de gebruiker bij een specifieke quiz
     * @param idquiz        quiz waarop gezocht wordt
     * @param idstudent     user waarop gezocht wordt
     * @return              de laatste QuizResult op basis van idAttempt met de combinatie van idQuiz en idStudent
     */
    public QuizResult getLatestResult(int idquiz, int idstudent){
        QuizResult qr = null;
        String sql = "SELECT * From QuizResult WHERE idQuiz = ? AND idStudent = ? ORDER BY idAttempt DESC LIMIT 1;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1,idquiz);
            ps.setInt(2,idstudent);
            ResultSet rs = executeSelectPreparedStatement(ps);
            if(rs.next()){
                int ida = rs.getInt("idAttempt");
                int idq = rs.getInt("idQuiz");
                int idu = rs.getInt("idStudent");
                Boolean passed = rs.getBoolean("passed");
                String date = rs.getString("datetime");
                qr = new QuizResult(ida, idq, idu, passed,date);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return qr;
    }

    /**
     * Alle resultaten van een student bij een quiz
     * @param idQuiz      quiz waarop gezocht wordt
     * @param idUser      gebruiker waarop gezocht wordt
     * @return            ArrayList<QuizResult> met alle resultaten
     */
    public ArrayList<QuizResult> getAllResultsForStudent(int idQuiz, int idUser) {
        ArrayList<QuizResult> results = new ArrayList<>();
        String sql = "SELECT * FROM Quizmaster.QuizResult WHERE idQuiz = ? AND idStudent = ? ORDER BY DATETIME DESC;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setInt(1, idQuiz);
            preparedStatement.setInt(2, idUser);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                Boolean passed = resultSet.getBoolean("passed");
                int attempt = resultSet.getInt("idAttempt");
                String date = resultSet.getString("datetime");
                QuizResult quizResult = new QuizResult(attempt, idQuiz, idUser, passed, date);
                results.add(quizResult);
            }
        } catch (SQLException fout) {
            System.out.println("SQL Error: " + fout.getMessage());
        }
        return results;
    }
}
