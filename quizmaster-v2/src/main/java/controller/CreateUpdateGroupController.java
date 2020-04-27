package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Group;
import model.User;
import view.Main;

import java.util.ArrayList;

/**
 * @author HRGautam
 * Deze controller bevat CRUD functie voor groep
 */

  public class CreateUpdateGroupController {
    private  UserDAO userDAO;
    private GroupDAO groupDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private Group group;
    private RoleDAO roleDAO;


    @FXML
    private TextField idGroupTextField;
    @FXML
    private TextField nameGroupTextField;
    @FXML
    private Label titleLabel;
    @FXML
    private MenuButton teacherBotton;
    @FXML
    private MenuButton courseBotton;

    // Constructor
    public CreateUpdateGroupController (){
        super();
        this.dbAccess=Main.getDBaccess();
        this.groupDAO=new GroupDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.userDAO = new UserDAO(dbAccess);
        this.roleDAO = new RoleDAO(dbAccess);
        dbAccess.openConnection();
    }
        // Setup voor een groep aanmaken en wijzigen
      public void setup(Group group) {
          for (Course course : courseDAO.getAll()) {
              MenuItem item = new MenuItem(String.format("%d %s", course.getIdCourse(), course.getNameCourse()));
              item.setOnAction(event -> courseBotton.setText(String.format("%d %s", course.getIdCourse(), course.getNameCourse())));
              courseBotton.getItems().add(item);
          }
              ArrayList<User> docent = userDAO.getAllbyidRole(roleDAO.getIdByName("docent"));
              ArrayList<User> coordinator = userDAO.getAllbyidRole(roleDAO.getIdByName("coordinator"));
              docent.addAll(coordinator);
              for ( User user : docent){
              MenuItem item = new MenuItem(String.format("%d %s", user.getIdUser(), user.getGebruikersnaam()));
              item.setOnAction(event -> teacherBotton.setText(String.format("%d %s", user.getIdUser(), user.getGebruikersnaam())));
              teacherBotton.getItems().add(item);
          }

          if (group != null) {
              titleLabel.setText("Wijzig Groep");
              idGroupTextField.setText(String.valueOf(group.getIdGroup()));
              nameGroupTextField.setText(group.getNameGroup());
              courseBotton.setText(String.format("%d %s",group.getCourse().getIdCourse(),group.getCourse().getNameCourse()));
              teacherBotton.setText(String.format("%d %s",group.getUser().getIdUser(),group.getUser().getGebruikersnaam()));
          }
      }

    // Om een aangemaakte groep en gewijzigde groep op te slaan.
    public void doCreateUpdateGroup() {
        createGroup();
       if (group != null) {
           if(idGroupTextField.getText().equals("Automatisch gegenereerd")){
               groupDAO.storeOne(group);
               Main.alert("Groep opgeslagen", "Opslaan");

           }else {
               int idGroup = Integer.parseInt(idGroupTextField.getText());
               group.setIdGroup(idGroup);
               groupDAO.updateGroup(group);
               Main.alert("Groep gewijzigd", "Wijzigen");
           }
           Main.getSceneManager().showManageGroupsScene();
       }
}


        // Maakt een nieuwe groep van de gegevens die de gebruiker invult.
        public void createGroup() {
            String nameGroup = nameGroupTextField.getText();
            String courseField = courseBotton.getText();
            String userField = teacherBotton.getText();
            boolean correcteInvoer = true;
            if (nameGroup.isEmpty()) {
                Main.alert("Voer de naam van de groep in","Leeg veld");
                correcteInvoer = false;
            }else if (courseField.equals("Select cursus")) {
                Main.alert("Selecteer een cursus ","Leeg veld");
                correcteInvoer = false;
            } else if( userField.equals("Select docent")) {
                Main.alert("Selecteer een docent","Leeg veld");
                correcteInvoer = false;
            }
                if (correcteInvoer) {
                    String [] courseArray = courseBotton.getText().split(" ");
                    int courseId = Integer.parseInt(courseArray[0]);
                    Course course = courseDAO.getOneById(courseId);
                    String [] userArray = teacherBotton.getText().split(" ");
                    int teacherId = Integer.parseInt(userArray[0]);
                    User user = userDAO.getOneById(teacherId);
                    group = new Group(nameGroup, course, user);
                } else {
                    group = null;
                }
            }

    // Om terug naar beheerscherm te gaan.
    @FXML
    public void doBeheerScherm() {
        Main.getSceneManager().showManageGroupsScene();
    }

    // Om terug naar hoofdmenu / welcomescherm te gaan.
    @FXML
    public void doMenu() {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showWelcomeScene();
    }

}

