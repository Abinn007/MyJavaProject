package controller;
/**
 * @author Carolina Lira del Alto
 */
import database.mysql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Answer;
import model.Course;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageQuestionsController {
    public static final String VRAAG_BEVESTIGING = "Weet je zeker dat je deze vraag wil verwijderen?";
    public static final String CURRENT_SCREEN = "Manage";
    private DBAccess dbAccess;
    private CourseDAO courseDAO;
    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private String myString;
    private Question question = null;
    int idEen;
    int idTwee;
    int idDrie;
    int idVier;

    // Connects with DB to create list of questions
    @FXML
    ListView<Question> questionList;

    // Connects with DB
    public ManageQuestionsController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.courseDAO = new CourseDAO(dbAccess);
        this.quizDAO = new QuizDAO(dbAccess);
        this.questionDAO = new QuestionDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);
        dbAccess.openConnection();
    }

    // Displays only the QUESTIONS assigned to logged in USER
    public void setup() {
        SessionController.setCurrentScreen(CURRENT_SCREEN);
        for (Course course : courseDAO.getAllById(SessionController.currentUser.getIdUser())) {
            for (Quiz quiz : quizDAO.getAllByCourseId(course.getIdCourse())) {
                for (Question question : questionDAO.getAllFromQuizId(quiz.getIdQuiz()))
                    questionList.getItems().add(question);
            }
        }
    }

    @FXML
    public void handleMouseClick() {
        question = questionList.getSelectionModel().getSelectedItem();
    }

    // Closes Manage Questions Controller and returns to Welcome screen
    public void doMenu() {
        dbAccess.closeConnection();
        Main.getSceneManager().showWelcomeScene();
    }

    // Opens screen to create new question
    public void doCreateQuestion() {
        Main.getSceneManager().showCreateUpdateQuestionScene(null);
    }

    // Opens screen to update question. User must select one question before updating it
    public void doUpdateQuestion() {
        question = questionList.getSelectionModel().getSelectedItem();
        if (question == null) {
            Main.alert("Selecteer een vraag", "Selecteer");
            return;
        }
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    // Opens screen to delete question. User must select one question before deleting it
    public void doDeleteQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();
        if (question == null) {
            Main.alert("Selecteer een vraag", "Selecteer");
            return;
        }
        if(Main.askConfirmation(("vraag"))){
            questionDAO.deleteOne(question);
            questionList.getItems().clear();
            setup();
        }
    }
}
