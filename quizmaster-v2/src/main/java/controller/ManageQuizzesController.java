package controller;


import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Course;
import model.Quiz;
import view.Main;

import java.util.List;
import java.util.Optional;

/**
 * @author lisa kemeling
 * Dit is de controller voor handelingen op het Quiz berheerscherm- managerQuizzesController
 */
public class ManageQuizzesController {
    public static final String NAAM_SCREEN = "Manage";
    public static final String VRAAG_BEVESTIGING = "Weet u zeker dat u deze quiz wilt verwijderen?";
    private QuizDAO quizDAO;
    private DBAccess dbAccess;
    private CourseDAO courseDAO;
    List<Quiz> quizzes;
    List<Course> courses;

    @FXML
    ListView<Quiz> quizList;

    public ManageQuizzesController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        dbAccess.openConnection();
    }

    public void setup() {
        SessionController.setCurrentScreen(NAAM_SCREEN);
        courses = courseDAO.getAllById(SessionController.currentUser.getIdUser());
        for (Course course : courses) {
            quizzes = quizDAO.getAllByCourseId(course.getIdCourse());
            quizList.getItems().addAll(quizzes);
        }
    }
    @FXML
    public void doMenu(ActionEvent event){
        dbAccess.closeConnection();
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doCreateQuiz(ActionEvent event){
        Main.getSceneManager().showCreateUpdateQuizScene(null);
    }

    @FXML
    public void doUpdateQuiz(ActionEvent event){
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        if (quiz == null){
            Main.alert("Selecteer eerst een quiz", "Wijzig quiz");
            return;
        }
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    @FXML
    public void doDeleteQuiz(ActionEvent event){
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        if (quiz == null){
            Main.alert("Selecteer eerst een quiz", "Wijzig quiz");
            return;
        }
        if(Main.askConfirmation("quiz")) {
            quizDAO.deleteOne(quiz);
            quizList.getItems().clear();
            setup();
        }
    }
}