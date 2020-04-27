package controller;

import database.mysql.DBAccess;
import database.mysql.QuizResultDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Quiz;
import model.QuizResult;
import view.Main;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;



public class StudentFeedbackController {
    private DBAccess dbAccess;
    private QuizResultDAO quizResultDAO;
    private ArrayList<QuizResult> results;


    @FXML
    private Label feedbackLabel;
    @FXML
    private ListView<QuizResult> feedbackList;
    @FXML
    private ImageView smileImage;
    @FXML
    private Label geslaagdLabel;

    public StudentFeedbackController() {
        this.dbAccess = Main.getDBaccess();
        this.quizResultDAO = new QuizResultDAO(dbAccess);
        dbAccess.openConnection();

    }
    public void setup(Quiz quiz) {
        feedbackLabel.setText("Feedback voor quiz: " + quiz.getNaam());

        //zodat de lijst niet aangeklikt kan worden
        blockMouseClick();

        //lijst maken met overzicht pogingen
        results = quizResultDAO.getAllResultsForStudent(quiz.getIdQuiz(), SessionController.currentUser.getIdUser());
        for (QuizResult qr : results) {
            feedbackList.getItems().add(qr);
        }
        //smiley tonen aangepast op resultaat
        setupImage(quiz);
    }

    public void setupImage(Quiz quiz){
        QuizResult quizResult = quizResultDAO.getLatestResult(quiz.getIdQuiz(), SessionController.currentUser.getIdUser());
        if(quizResult.getPassed()){
            Image image = new Image("file:images/smile.png");
            smileImage.setImage(image);
            geslaagdLabel.setText("Gefeliciteerd, je bent geslaagd!");
        } else{
            Image image = new Image("file:images/sad.png");
            smileImage.setImage(image);
            geslaagdLabel.setText("Helaas, je bent niet geslaagd!");
        }
    }

    public void doMenu() {
        dbAccess.closeConnection();
        Main.getSceneManager().showWelcomeScene();
    }

    public void blockMouseClick(){
        feedbackList.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });
    }

}

