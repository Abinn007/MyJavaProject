package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.List;

/**
 * @author lisa kemeling
 * controller voor view dashboard coordinator
 * zorgt dat gebruiker kan kiezen voor een cursus en bijbehorende quizzes en vragen
 */
public class CoordinatorDashboardController {
    public static final String NAAM_SCREEN = "Dashboard";
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;

    @FXML private Label informatieVraagLabel;
    @FXML private Label informatieLabel;
    @FXML private ListView<Course> courseList;
    @FXML private ListView<Quiz> quizList;
    @FXML private ListView<Question> questionList;

    public CoordinatorDashboardController() {
        this.dbAccess = Main.getDBaccess();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.questionDAO = new QuestionDAO(dbAccess);
        dbAccess.openConnection();

    }


    public void setup() {
        setupCourseList();
        SessionController.setCurrentScreen(NAAM_SCREEN);
        courseList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldCourse, newCourse) -> {
                   quizList.getItems().clear();
                    for (Course course : courseList.getSelectionModel().getSelectedItems()) {
                        informatieLabel.setText("");
                        quizList.getItems().addAll(quizDAO.getAllByCourseId(course.getIdCourse()));
                        checkOpQuiz("Cursus heeft geen Quiz");
                    }
                });

        quizList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldQuiz, newQuiz) -> {
                    questionList.getItems().clear();
                    for (Quiz quiz : quizList.getSelectionModel().getSelectedItems()) {
                        informatieVraagLabel.setText("");
                        questionList.getItems().addAll(questionDAO.getAllFromQuizId(quiz.getIdQuiz()));
                        checkOpQuestion("Quiz heeft geen vragen");
                    }
                });
    }

    public void setupCourseList(){
        List<Course> courses =  courseDAO.getAllById(SessionController.currentUser.getIdUser());
        for (Course course : courses) {
            courseList.getItems().add(course);
        }
    }


    public void doNewQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(null);
    }

    public void doEditQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
        if (quiz == null){
            Main.alert("Selecteer eerst een quiz", "Wijzig quiz");
            return;
        }
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);

    }

    public void doNewQuestion() {
        Main.getSceneManager().showCreateUpdateQuestionScene(null);
    }

    public void doEditQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();
        if (question == null){
            Main.alert("Selecteer eerst een vraag", "Foutmelding");
            return;
        }
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doMenu() {
        dbAccess.closeConnection();
        Main.getSceneManager().showWelcomeScene();
    }

    public void checkOpQuiz(String tekst) {
        if (quizList.getItems().isEmpty()){
            informatieLabel.setText(tekst);
        }
    }

    public void checkOpQuestion(String tekst) {
        if (quizList.getItems().isEmpty()){
            informatieVraagLabel.setText(tekst);
        }
    }
}
