package controller;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Group;
import view.Main;
import java.util.List;


/**
 * @author HRGautam
 * Deze controller bevat beheer functie voor groep
 */

public class ManageGroupsController {
    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    ListView<Group> groupList;

   // constructors en connectie maken met database.
    public ManageGroupsController() {
        super();
        this.dbAccess = Main.getDBaccess();
        this.groupDAO = new GroupDAO(dbAccess);
        dbAccess.openConnection();
    }
    //Lijst van alle groepen te laten zien in groepenbeheer scherm.
    public void setup() {
        List<Group> allGroups = groupDAO.getAll();
        for (Group group : allGroups) {
            groupList.getItems().add(group);
        }
    }
    // Om terug naar welcomescherm te gaan.
    @FXML
    public void doMenu() {
        dbAccess.closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showWelcomeScene();
    }
    // Om naar createUpdateScherm  te gaan om een nieuwe grorp aan te maken.
    @FXML
    public void doCreateGroup() {
        Main.getSceneManager().showCreateUpdateGroupScene(null);
    }


    // Om naar createUpdateScherm te gaan  om een groep te wijzegen.
    @FXML
    public void doUpdateGroup() {
        Group group = groupList.getSelectionModel().getSelectedItem();
        if (group == null) {
            Main.alert("Selecteer eerst een groep", "Wijzig groep");
            return;
        }
        Main.getSceneManager().showCreateUpdateGroupScene(group);
    }

    // Om een groep te verwijderen
    @FXML
    public void doDeleteGroup() {
        Group group = groupList.getSelectionModel().getSelectedItem();

        if (group == null) {
            Main.alert("Selecteer eerst een groep", "Verwijder groep");
            return;
        }
        if(Main.askConfirmation("groep")) {
            groupDAO.deleteGroup(group);
            groupList.getItems().clear();
            setup();
        }
    }
}