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

public class CreateUpdateQuestionController {
    private DBAccess dbAccess;
    private QuestionDAO questionDAO;
    private Question question;
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private AnswerDAO answerDAO;
    private UserDAO userDAO;

    @FXML private Label titleLabel;
    @FXML private TextField vraagIDTextfield;
    @FXML private TextField vraagTextTextfield;
    @FXML private MenuButton quizMenuButton;

    @FXML TextField antwoordEen;
    @FXML TextField antwoordTwee;
    @FXML TextField antwoordDrie;
    @FXML TextField antwoordVier;

    Answer answerEen;
    Answer answerTwee;
    Answer answerDrie;
    Answer answerVier;

    public CreateUpdateQuestionController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.questionDAO = new QuestionDAO(dbAccess);
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.userDAO = new UserDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);
        dbAccess.openConnection();
    }
    public void setup(Question question) {
        setupMenuButton();
        if(question != null) {
            titleLabel.setText("Wijzig Vraag & Antwoorden");
            vraagIDTextfield.setText(String.valueOf(question.getIdQuestion()));
            vraagTextTextfield.setText(question.getTextQuestion());
            quizMenuButton.setText(String.format("%d %s",question.getQuiz().getIdQuiz(),question.getQuiz().getNaam()));
            showAnswers(question.getIdQuestion());
        }
    }
    public void setupMenuButton(){
        for (Course course : courseDAO.getAllById(SessionController.currentUser.getIdUser())) {
            for (Quiz quiz : quizDAO.getAllByCourseId(course.getIdCourse())) {
                MenuItem item = new MenuItem(String.format("%d %s", quiz.getIdQuiz(), quiz.getNaam()));
                item.setOnAction(event -> quizMenuButton.setText(String.format("%d %s", quiz.getIdQuiz(), quiz.getNaam())));
                quizMenuButton.getItems().add(item);
            }
        }
    }
    @FXML public void doStore(ActionEvent actionEvent) {
        createQuestion();
        if (question != null) {

            if(vraagIDTextfield.getText().equals(("Automatisch gegenereerd"))) {
            questionDAO.storeOne(question);

            answerEen = new Answer( antwoordEen.getText(),  true, question);
            answerTwee = new Answer( antwoordTwee.getText(), false, question);
            answerDrie = new Answer( antwoordDrie.getText(), false,question);
            answerVier = new Answer(antwoordVier.getText(), false,question);


            answerDAO.storeOne(answerEen);
            answerDAO.storeOne(answerTwee);
            answerDAO.storeOne(answerDrie);
            answerDAO.storeOne(answerVier);

            vraagIDTextfield.setText(String.valueOf(question.getIdQuestion()));
            Main.alert("Vraag opgeslagen","Opslaan");
                goToScreen();
            } else {
                int idQuestion = Integer.parseInt(vraagIDTextfield.getText());
                question.setIdQuestion(idQuestion);
                questionDAO.updateOne(question);

                answerEen.setTextAnswer(antwoordEen.getText());
                answerTwee.setTextAnswer(antwoordTwee.getText());
                answerDrie.setTextAnswer(antwoordDrie.getText());
                answerVier.setTextAnswer(antwoordVier.getText());

                answerDAO.updateOne(answerEen);
                answerDAO.updateOne(answerTwee);
                answerDAO.updateOne(answerDrie);
                answerDAO.updateOne(answerVier);

                Main.alert("Vraag gewijzigd", "Wijzigen");
                goToScreen();
            }
        }

    }
    private void createQuestion() {
        String textQuestion = vraagTextTextfield.getText();
        String quiz = quizMenuButton.getText();
        boolean correcteInvoer = true;
        if (textQuestion.isEmpty()) {
            correcteInvoer = false;
        }
        if (quiz.isEmpty() || quiz.equals(("Selecteer een quiz"))) {
            correcteInvoer = false;
        }
        if (antwoordEen.getText().isEmpty() || antwoordTwee.getText().isEmpty() || antwoordDrie.getText().isEmpty() || antwoordVier.getText().isEmpty()) {
            correcteInvoer = false;
        }
        if (!correcteInvoer) {
            Main.alert("Vul alle velden in","Leeg veld");
        }
        if (correcteInvoer) {
            String[] invoerArray = quizMenuButton.getText().split(" ");
            int id = Integer.parseInt(invoerArray[0]);
            question = new Question(textQuestion, quizDAO.getOneById(id));
        }
        else {
            question = null;
        }
    }
    @FXML public void doBackToList(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showManageQuestionsScene();
    }
    @FXML public void doBackToMenu(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showWelcomeScene();
    }

    public void showAnswers(int idQuestion){
        ArrayList<Answer> allAnswers = answerDAO.getAllByQuestionid(idQuestion);
        for (Answer a : allAnswers){
            if(a.isCorrect()){
                antwoordEen.setText(a.getTextAnswer());
                answerEen = a;
            } else if(antwoordTwee.getText().equals("")){
                antwoordTwee.setText(a.getTextAnswer());
                answerTwee = a;
            } else if(antwoordDrie.getText().equals("")){
                antwoordDrie.setText(a.getTextAnswer());
                answerDrie = a;
            } else if(antwoordVier.getText().equals("")){
                antwoordVier.setText(a.getTextAnswer());
                answerVier = a;
            }
        }

}

    @FXML public void doBack(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        goToScreen();
    }

    public void goToScreen() {
        if(SessionController.currentScreen.equals(CoordinatorDashboardController.NAAM_SCREEN)) {
            Main.getSceneManager().showCoordinatorDashboard();
        }else {
            Main.getSceneManager().showManageQuestionsScene();
        }
    }

}