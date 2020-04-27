package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Quiz;
import view.Main;

/**
 * @author lisa kemeling
 * class regelt connectie tussen object Quiz, view CreateUpdateQuiz en database
 */

public class CreateUpdateQuizController {
    public static final String STARTTEKST_MENUBUTTON = "Selecteer een cursus";
    public static final String STARTTEKST_QUIZID = "Automatisch gegenereerd";
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private Quiz quiz;

    @FXML private Label titleLabel;
    @FXML private TextField naamTextfield;
    @FXML private TextField idQuizTextfield;
    @FXML private TextField cesuurTextfield;
    @FXML private MenuButton selectButton;



    public CreateUpdateQuizController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        dbAccess.openConnection();
    }

    public void setup(Quiz quiz) {
        setupMenuButton();
        if(quiz != null) {
            titleLabel.setText("Wijzig Quiz");
            idQuizTextfield.setText(String.valueOf(quiz.getIdQuiz()));
            naamTextfield.setText(quiz.getNaam());
            cesuurTextfield.setText(String.valueOf(quiz.getSuccesdefinitie()));
            selectButton.setText(String.format("%d %s",quiz.getCourse().getIdCourse(),quiz.getCourse().getNameCourse()));
        }
    }
    @FXML
    public void doStore(ActionEvent actionEvent) {
        createQuiz();
        if(quiz != null){
            if(idQuizTextfield.getText().equals(STARTTEKST_QUIZID)){
                quizDAO.storeOne(quiz);
                idQuizTextfield.setText(String.valueOf(quiz.getIdQuiz()));
                Main.alert("Quiz opgeslagen", "Opslaan");
            } else {
                int idQuiz = Integer.parseInt(idQuizTextfield.getText());
                quiz.setIdQuiz(idQuiz);
                quizDAO.updateQuiz(quiz);
                Main.alert("Quiz gewijzigd", "Opslaan");
            }
            goToScreen();
        }
    }

    private void createQuiz(){
        String naam = naamTextfield.getText();
        String succesdefinitie = cesuurTextfield.getText();
        String course = selectButton.getText();
        boolean correcteInvoer = true;
        if(naam.isEmpty()){
            Main.alert("Voer de naam van de Quiz in", "Leeg veld");
            correcteInvoer = false;
        }
        if (!(cesuurCheck(succesdefinitie)) || succesdefinitie.isEmpty()){
        Main.alert("Voer een juiste cesuur in (positief getal)", "Fout in cesuur");
        correcteInvoer = false;
        }
        if(course.equals(STARTTEKST_MENUBUTTON)){
            Main.alert("Selecteer een cursus", "Leeg veld");
            correcteInvoer = false;
        }
        if (correcteInvoer) {
            String[] invoerArray = selectButton.getText().split(" ");
            int id = Integer.parseInt(invoerArray[0]);
            quiz = new Quiz(naam, Integer.parseInt(succesdefinitie), courseDAO.getOneById(id));
        } else {
            quiz = null;
        }

    }

    public void setupMenuButton(){
        for (Course course: courseDAO.getAllById(SessionController.currentUser.getIdUser())) {
            MenuItem item = new MenuItem(String.format("%d %s", course.getIdCourse(), course.getNameCourse()));
            item.setOnAction(event -> selectButton.setText(String.format("%d %s", course.getIdCourse(), course.getNameCourse())));
            selectButton.getItems().add(item);
        }
    }

    @FXML
    public void doBackToMenu(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doBack(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        goToScreen();
    }


    /**
     * Checkt of cesuur een getal is
     * @param cesuur
     * @return geeft true als ingevoerde cesuur een positief getal is
     * @author lisa kemeling
     */
    public boolean cesuurCheck(String cesuur){
        try{
            int i = Integer.parseInt(cesuur);
            if(i > 0) {
                return true;
            } else return false;
        } catch (Exception fout){
            return false;
        }
    }

    /**
     * checkt waar gebruiker vandaan komt om vorig scherm te bepalen
     * @author lisa kemeling
     */
    public void goToScreen() {
        if(SessionController.currentScreen.equals(CoordinatorDashboardController.NAAM_SCREEN)){
            Main.getSceneManager().showCoordinatorDashboard();
        }else {
            Main.getSceneManager().showManageQuizScene();
        }
    }

    }
