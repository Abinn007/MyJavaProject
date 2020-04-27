package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import model.Course;
import model.Quiz;
import model.QuizResult;
import model.User;
import view.Main;

/**
 * De voortgang van een student kan bijgehouden worden in de studentProgress view
 *
 * @author Wesley Wong
 */

public class StudentProgressController {

    private UserDAO userDAO;
    private QuizDAO quizDAO;
    private User selectedUser;
    private CourseRegistrationDAO courseRegistrationDAO;
    private QuizResultDAO quizresultDAO;


    @FXML
    private ListView<User> studentList;
    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<QuizResult> resultList;

    /**
     * Setup dat wordt aangeroepen door de SceneManager
     */
    public void setup() {
        openDBAccess();

        // zet alle studenten in studentList
        for (User u : userDAO.getAllbyidRole(1)) {
            studentList.getItems().add(u);
        }

        // als een student geselecteerd wordt, laat alle bijbehorende cursussen zien
        studentList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldStudent, newStudent) -> {
                    courseList.getItems().clear();
                    for (Course c : courseRegistrationDAO.getAllCoursesByIdStudent(studentList.getSelectionModel().getSelectedItem().getIdUser())) {
                        courseList.getItems().addAll(c);
                    }
                    selectedUser = studentList.getSelectionModel().getSelectedItem();

                });

        // als een cursus geselecteer wordt, laat alle bijbehorende quizzes zien
        courseList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldCourse, newCourse) -> {
                    quizList.getItems().clear();
                    for (Course course : courseList.getSelectionModel().getSelectedItems()) {
                        quizList.getItems().addAll(quizDAO.getAllByCourseId(course.getIdCourse()));
                    }
                });

        // als een quiz geselecteerd is, laat alle results zien
        quizList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldQuiz, newQuiz) -> {
                    resultList.getItems().clear();
                    for (Quiz quiz : quizList.getSelectionModel().getSelectedItems()) {
                        resultList.getItems().addAll(quizresultDAO.getAllResultsForStudent(quiz.getIdQuiz(), selectedUser.getIdUser()));
                    }
                });

        fraudePlegen();

    }

    /**
     * Met deze functie kan een docent fraude plegen door een resultaat te selecteren en op Alt+G te drukken :)
     */
    private void fraudePlegen() {
        resultList.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.G, KeyCombination.ALT_DOWN), () -> {
            QuizResult qr;
            qr = resultList.getSelectionModel().getSelectedItem();
            if (qr != null) {
                if (qr.getPassed()) {
                    qr.setPassed(false);
                } else {
                    qr.setPassed(true);
                }
                quizresultDAO.updateQuizResult(qr);
                resultList.getItems().clear();
                for (Quiz quiz : quizList.getSelectionModel().getSelectedItems()) {
                    resultList.getItems().addAll(quizresultDAO.getAllResultsForStudent(quiz.getIdQuiz(), selectedUser.getIdUser()));
                }
            }
        });
    }

    /**
     * Opent DBAccess voor relevante DAOs
     */
    private void openDBAccess() {
        DBAccess dbAccess = Main.getDBaccess();
        this.userDAO = new UserDAO(dbAccess);
        this.quizDAO = new QuizDAO(dbAccess);
        this.quizresultDAO = new QuizResultDAO(dbAccess);
        this.courseRegistrationDAO = new CourseRegistrationDAO(dbAccess);
        dbAccess.openConnection();
    }

    /**
     * Menu knop
     */
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }
}
