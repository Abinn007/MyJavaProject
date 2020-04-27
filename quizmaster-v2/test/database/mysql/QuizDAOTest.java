package database.mysql;

import model.Course;
import model.Quiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lisa kemeling
 * Testclass voor QuizDAO
 */
class QuizDAOTest {
    private DBAccess dbAccess = Main.getDBaccess();
    private QuizDAO quizDAO = new QuizDAO(dbAccess);
    private CourseDAO courseDAO = new CourseDAO(dbAccess);


    @Test
    void getOneById() {
        dbAccess.openConnection();
        Quiz expected = new Quiz(1, "Aardrijkskunde Semester 1", 1, courseDAO.getOneById(1));
        Quiz actual = quizDAO.getOneById(1);
        assertEquals(expected, actual);
        dbAccess.closeConnection();
    }


    @Test
    void getOneByCourseId() {
        dbAccess.openConnection();
        Quiz expected = new Quiz(1, "Aardrijkskunde Semester 1", 1, courseDAO.getOneById(1));
        Quiz actual = quizDAO.getOneByCourseId(1);
        assertEquals(expected.getIdQuiz(), actual.getIdQuiz());
        assertEquals(expected.getNaam(), actual.getNaam());
        assertEquals(expected, actual);
        dbAccess.closeConnection();
    }

    @Test
    void deleteOne(){
        dbAccess.openConnection();
        Quiz quiz = new Quiz("Je raad het nooit", 1, courseDAO.getOneById(1));
        quizDAO.storeOne(quiz);
        int idQuiz = quiz.getIdQuiz();
        quizDAO.deleteOne(quiz);
        assertNull(quizDAO.getOneById(idQuiz));
        dbAccess.closeConnection();
    }

    @Test
    void storeOne(){
        dbAccess.openConnection();
        Quiz quiz = new Quiz("Je raad het nooit", 1, courseDAO.getOneById(1));
        quizDAO.storeOne(quiz);
        int id = quiz.getIdQuiz();
        assertNotNull(quizDAO.getOneById(id));
        quizDAO.deleteOne(quiz);
        dbAccess.closeConnection();
    }



}