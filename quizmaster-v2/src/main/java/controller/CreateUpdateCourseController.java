package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.RoleDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.User;
import view.Main;

/**
 * @author lisa kemeling
 * class regelt connectie tussen object Course, view CreateUpdateCourse en database
 */

public class CreateUpdateCourseController {
    public static final String STARTTEKST_MENUBUTTON = "Selecteer een coordinator";
    public static final String STARTTEKST_COURSEID = "Automatisch gegenereerd";
    private DBAccess dbAccess;
    private CourseDAO courseDAO;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private Course course;

    @FXML private Label titleLabel;
    @FXML private TextField naamTextfield;
    @FXML private TextField idCourseTextfield;
    @FXML private MenuButton selectButton;

    public CreateUpdateCourseController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.courseDAO = new CourseDAO(dbAccess);
        this.userDAO = new UserDAO(dbAccess);
        this.roleDAO =  new RoleDAO(dbAccess);
        dbAccess.openConnection();
    }

    public void setup(Course course) {
        setupMenuButton();
        if(course != null) {
            titleLabel.setText("Wijzig Cursus");
            idCourseTextfield.setText(String.valueOf(course.getIdCourse()));
            naamTextfield.setText(course.getNameCourse());
            selectButton.setText(String.format("%d %s",course.getUser().getIdUser(), course.getUser().getGebruikersnaam()));
        }
    }

    public void setupMenuButton(){
        for (User user: userDAO.getAllbyidRole(roleDAO.getIdByName("coordinator"))) {
            MenuItem item = new MenuItem(String.format("%d %s", user.getIdUser(), user.getGebruikersnaam()));
            item.setOnAction(event -> selectButton.setText(String.format("%d %s", user.getIdUser(), user.getGebruikersnaam())));
            selectButton.getItems().add(item);
        }
    }

    public void createCourse(){
        String nameCourse = naamTextfield.getText();
        String user = selectButton.getText();
        boolean correcteInvoer = true;
        if(nameCourse.isEmpty()){
            Main.alert("Voer de naam van de cursus in", "Leeg veld");
            correcteInvoer = false;
        }
        if(user.equals(STARTTEKST_MENUBUTTON)){
            Main.alert("Selecteer een coördinator", "Leeg veld");
            correcteInvoer = false;
        }
        if (correcteInvoer) {
            String[] invoerArray = selectButton.getText().split(" ");
            int id = Integer.parseInt(invoerArray[0]);
            course = new Course(nameCourse, userDAO.getOneById(id));
        } else {
            course = null;
        }
    }

    @FXML
    public void doStore(ActionEvent event) {
        createCourse();
        if(course != null){
            if(idCourseTextfield.getText().equals(STARTTEKST_COURSEID)){
                courseDAO.storeOne(course);
                idCourseTextfield.setText(String.valueOf(course.getIdCourse()));
                Main.alert("Cursus opgeslagen", "Opslaan");
            } else {
                int idCourse = Integer.parseInt(idCourseTextfield.getText());
                course.setIdCourse(idCourse);
                courseDAO.updateCourse(course);
                Main.alert("Cursus gewijzigd", "Wijzigen");
            }
            goToScreen();
        }
    }

    @FXML
    public void doBack(ActionEvent actionEvent) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        goToScreen();
    }

    @FXML
    public void doBackToMenu(ActionEvent event) {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showWelcomeScene();
    }

    public void goToScreen() {
        if(SessionController.currentScreen.equals(CoordinatorDashboardController.NAAM_SCREEN)){
            Main.getSceneManager().showCoordinatorDashboard();
        }else {
            Main.getSceneManager().showManageCoursesScene();
        }
    }


}
