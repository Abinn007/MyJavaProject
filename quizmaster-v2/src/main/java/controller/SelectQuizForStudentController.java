package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;
import model.Quiz;
import model.QuizResult;
import view.Main;

import java.util.ArrayList;

/**
 * Hier kan de student een quiz kiezen om te maken
 * Daarbij laat het de laatste poging met datum, tijd en resultaat zien van de gebruiker
 *
 * @author Wesley Wong
 */

public class SelectQuizForStudentController {

    @FXML
    private Label resultaatLabel;
    @FXML
    private Label laatstePogingLabel;
    @FXML
    private Label laatstePogingTitel;
    @FXML
    private Label resultaatTitel;

    @FXML
    private Label aantalVragenLabel;
    @FXML
    private Label aantalVragenTitel;

    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private Label noQuizzes;

    private CourseRegistrationDAO courseRegistrationDAO;
    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private QuizResultDAO quizresultdao;

    /**
     * Toont alle Quizzes die via Courses gerelateerd zijn aan de gebruiker
     */
    public void setup() {
        openDBAccess();

        int idCurrentUser = SessionController.currentUser.getIdUser();

        // zoek alle courses gerelateerd aan gebruiker
        ArrayList<Course> userCourses = courseRegistrationDAO.getAllCoursesByIdStudent(idCurrentUser);
        for (Course course : userCourses) {

            // zoek alle quizzes die erbij horen op
            for (Quiz q : quizDAO.getAllByCourseId(course.getIdCourse())) {

                // voeg ze toe aan de lijst
                if(!questionDAO.getAllFromQuizId(q.getIdQuiz()).isEmpty()) {
                    quizList.getItems().add(q);
                }
            }
        }

        // laat informatie over laatste poging zien
        showLastAttempt();

        // als lijst leeg is, geef een melding
        if(quizList.getItems().isEmpty()){
            noQuizzes.setText("Schrijf je in voor een cursus");
        }

    }

    /**
     * Laat informatie over de laatste poging rechts van de ListView zien
     */
    private void showLastAttempt() {
        quizList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldQuiz, newQuiz) -> {

                    QuizResult qr;
                    Quiz quiz = quizDAO.getOneById(observableValue.getValue().getIdQuiz());

                    // zoek informatie op over laatste result
                    try {
                         qr = quizresultdao.getLatestResult(quiz.getIdQuiz(), SessionController.currentUser.getIdUser());
                    } catch (NullPointerException e) {
                        qr = null;
                    }

                    // als deze niet null is laat informatie zien
                    if(qr != null) {
                        String rs = getGeslaagd(qr);
                        resultaatTitel.setText("Resultaat:");
                        resultaatLabel.setText(rs);
                        laatstePogingTitel.setText("Laatste Poging:");
                        laatstePogingLabel.setText(qr.getDate());
                    } else {
                        // als er geen result is leeg velden
                        laatstePogingTitel.setText("");
                        resultaatTitel.setText("");
                        resultaatLabel.setText("");
                        laatstePogingLabel.setText("");
                    }

                    aantalVragenTitel.setText("Aantal Vragen:");
                    aantalVragenLabel.setText(String.valueOf(questionDAO.getAllFromQuizId(quiz.getIdQuiz()).size()));

                });
    }

    /**
     * @param qr        qr waarvan "passed" gelezen wordt
     * @return          een String met "Geslaagd" of "Niet Geslaagd
     */
    private String getGeslaagd(QuizResult qr) {
        String resultaat;
        if (qr.getPassed()) {
            resultaat = "Geslaagd";
        } else {
            resultaat = "Niet Geslaagd";
        }
        return String.format("%s", resultaat);
    }


    /**
     * doQuiz start de geselecteerde quiz
     */
    public void doQuiz() {
        Quiz selectedQuiz = quizList.getSelectionModel().getSelectedItem();
        int idCurrentUser = SessionController.currentUser.getIdUser();

        // check of quiz geslecteerd is
        if (quizList.getSelectionModel().getSelectedItem() != null) {

            // check of quiz vragen heeft
            if(!questionDAO.getAllFromQuizId(selectedQuiz.getIdQuiz()).isEmpty()) {
                QuizResult qr = new QuizResult(selectedQuiz.getIdQuiz(), idCurrentUser);
                quizresultdao.storeOne(qr);
                Main.getSceneManager().showFillOutQuiz(selectedQuiz);

            } else {
                alert("Deze quiz bevat geen vragen");
                Main.getSceneManager().showStudentFeedback(selectedQuiz);
            }

        } else {
            // als niets geselecteerd is
            alert("Selecteer een quiz");
        }
    }


    /**
     * Menu knop
     */
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    /**
     * Geeft alert met ingevoerde string
     * @param str   String dat wordt getoont bij alert
     */
    public void alert(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(str);
        alert.showAndWait();
    }

    /**
     * Opent dbAccess voor alle relevante DAOs
     */
    private void openDBAccess() {
        DBAccess dbAccess = Main.getDBaccess();
        this.quizDAO = new QuizDAO(dbAccess);
        this.questionDAO = new QuestionDAO(dbAccess);
        this.courseRegistrationDAO = new CourseRegistrationDAO(dbAccess);
        this.quizresultdao = new QuizResultDAO(dbAccess);
        dbAccess.openConnection();
    }
}
