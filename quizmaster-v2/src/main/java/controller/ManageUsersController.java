package controller;
import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Course;
import model.Group;
import model.User;
import view.Main;


/**
 * Beheer voor alle Users. Te gebruiken door de Technisch Beheerder
 *
 * @author Wesley Wong
 */
public class ManageUsersController {

    public static final String SELECTEER = "Selecteer een gebruiker";

    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;

    @FXML
    private ListView<User> userList;

    public void setup() {
        openDBAccess();

        // vult lijst van Users
        refreshList();
    }



    /**
     * Maak nieuwe gebruiker aan
     */
    @FXML
    public void doCreateUser() {
        Main.getSceneManager().showCreateUpdateUserScene(null);
    }

    /**
     * Wijzig bestaande gebruiker
     */
    @FXML
    public void doUpdateUser() {
        if(userList.getSelectionModel().getSelectedItem() == null) {
            Main.alert(SELECTEER, "Wijzig gebruiker");
        } else {
            Main.getSceneManager().showCreateUpdateUserScene(userList.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Delete gebruiker, check of gebruiker verantwoordelijk is voor een cursus of groep
     */
    @FXML
    public void doDeleteUser() {
        User selectedUser = userList.getSelectionModel().getSelectedItem();

        boolean gaDoor = true; // checkt op referentiële integriteit

        // check of user geselecteerd is
        if (selectedUser == null) {
            Main.alert(SELECTEER, "Verwijder gebruiker");

            // vraag om bevestiging
        } else if (Main.askConfirmation("gebruiker")) {

            // check of gebruiker voor een cursus of groep verantwoordelijk is
            StringBuilder sb = new StringBuilder();
            sb.append(selectedUser.getGebruikersnaam()).append(" is verantwoordelijk voor:");

            // check op cursus en zet in bericht
            if (!courseDAO.getAllById(selectedUser.getIdUser()).isEmpty()) {
                sb.append("\n\nCursus(sen):");
                for (Course c : courseDAO.getAllById(selectedUser.getIdUser())) {
                    sb.append("\n").append(c.getIdCourse()).append(" ").append(c.getNameCourse());
                }
                gaDoor = false;
            }

            // check op groep en zet in bericht
            if (!groupDAO.getAllByIdUser(selectedUser.getIdUser()).isEmpty()) {
                sb.append("\n\nGroep(en):");
                for (Group g : groupDAO.getAllByIdUser((selectedUser.getIdUser()))) {
                    sb.append("\n").append(g.getIdGroup()).append(" ").append(g.getNameGroup());
                }
                gaDoor = false;
            }

            if (!gaDoor){
                // laat gebruiker zien waarvoor de gebruiker verantwoordelijk is
                sb.append("\n\nGebruiker kan niet worden verwijderd.");
                Main.alert(sb.toString(), "Verwijder gebruiker");

            } else {
                // delete gebruiker
                userDAO.deleteOne(selectedUser);
                refreshList();
            }
        }
    }


    /**
     * Menu knop
     */
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }


    /**
     * Ververs gebruikerslijst
     */
    public void refreshList() {
        userList.getItems().clear();
        for (User u : userDAO.getAll()){
            userList.getItems().add(u);
        }
    }


    /**
     * Opent DBAccess voor alle relevante DAOs
     */
    private void openDBAccess() {
        DBAccess dbAccess = Main.getDBaccess();
        this.userDAO = new UserDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.groupDAO = new GroupDAO(dbAccess);
        dbAccess.openConnection();
    }


}
