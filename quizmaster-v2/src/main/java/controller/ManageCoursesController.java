package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Course;
import model.Group;
import view.Main;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageCoursesController {
    public static final String VRAAG_BEVESTIGING = "Weet je zeker dat je deze cursus wil verwijderen?";
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    ListView<Course> courseList;

    public ManageCoursesController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.courseDAO = new CourseDAO(dbAccess);
        dbAccess.openConnection();
    }

    public void setup() {
        List<Course> allCourses = courseDAO.getAll();
        for (Course course : allCourses) {
            courseList.getItems().add(course);
        }
    }
    @FXML
    public void doMenu(ActionEvent event){
        dbAccess.closeConnection();
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doCreateCourse(ActionEvent event){
        Main.getSceneManager().showCreateUpdateCourseScene(null);
    }

    @FXML
    public void doUpdateCourse(ActionEvent event){
        Course course = courseList.getSelectionModel().getSelectedItem();
        if(course == null){
            Main.alert("Selecteer eerst een cursus", "Wijzig cursus");
            return;
        }
        Main.getSceneManager().showCreateUpdateCourseScene(course);
    }

        @FXML
    public void doDeleteCourse(ActionEvent event){
            groupDAO = new GroupDAO(dbAccess);
            Course course = courseList.getSelectionModel().getSelectedItem();
            if (course == null){
                Main.alert("Selecteer eerst een cursus", "Verwijder cursus");
                return;
            }
            //als er nog een groep is, informeer gebruiker
            ArrayList<Group> groups = groupDAO.getAllByIdCourse(course.getIdCourse());
            if(!groups.isEmpty()){
                StringBuilder sb = new StringBuilder();
                sb.append("Aan cursus ").append(course.getNameCourse()).append(" zijn nog groepen verbonden:\n");
                for (Group group : groups) {
                    sb.append("\nNaam groep: ").append(group.getNameGroup()).append("\nID: ").append(group.getIdGroup());
                }
                sb.append("\n\nCursus kan niet worden verwijderd");
                Main.alert(sb.toString(), "Verwijder cursus");
                return;
            }

            //als er geen groep is verbonden, vraag bevestiging
            if(Main.askConfirmation("cursus")) {
                courseDAO.deleteOne(course);
                courseList.getItems().clear();
                setup();
            }
        }
}
