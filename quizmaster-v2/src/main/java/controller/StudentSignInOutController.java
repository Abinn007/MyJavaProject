package controller;

import database.mysql.CourseDAO;
import database.mysql.CourseRegistrationDAO;
import database.mysql.DBAccess;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;
import model.User;
import view.Main;

/**
 * In deze StudentSignInOutController kan een student zich aan- of afmelden voor een cursus
 *
 * @author Wesley Wong
 */
public class StudentSignInOutController {

    public static final String GEEN_CURSUSSEN_INGESCHREVEN = "Je bent voor geen cursussen ingeschreven";
    public static final String ALLE_CURSUSSEN_INGESCHREVEN = "Je bent voor alle cursussen ingeschreven";

    public Label geenCursusRechts;
    public Label geenCursusLinks;
    private CourseDAO courseDAO;
    private CourseRegistrationDAO courseRegistrationDAO;
    DBAccess dbAccess;
    private Course signedIn;
    private Course signedOUt;

    @FXML
    private ListView<Course> signedOutCourseList;
    @FXML
    private ListView<Course> signedInCourseList;


    public void setup() {
        openDBAccess();
        refreshList();
    }

    /**
     * In deze methode wordt de lijst aangevuld en eventueel geupdate
     */
    private void refreshList() {
        // reset alle velden
        resetLists();

        // haal alle cursussen op
        for (Course c : courseDAO.getAll()) {
            // deze boolean houdt bij of de gebruiker op een cursus ingeschreven is of niet
            boolean ingeschrevenIn = false;

            // haal alle users op die ingeschreven staan in de cursus
            for (User u : courseRegistrationDAO.getAllStudentsByIdCourse(c.getIdCourse())) {

                // als deze ingeschreven is gaat de booelean op true
                if (u.getIdUser() == SessionController.currentUser.getIdUser()) {
                    ingeschrevenIn = true;
                    break;
                }
            }

            // als de boolean true is wordt hij bij de ingeschreven lijst gezet, anders bij de uitgeschreven
            if (ingeschrevenIn) {
                signedInCourseList.getItems().add(c);
            } else {
                signedOutCourseList.getItems().add(c);
            }
        }
        // laat tekst zien als ViewList leeg is
        textInEmptyList();
    }

    /**
     * Wijst de gebruiker erop aan dat de ViewList leeg is
     */
    private void textInEmptyList() {
        if(signedInCourseList.getItems().isEmpty()){
            geenCursusRechts.setText(GEEN_CURSUSSEN_INGESCHREVEN);
        }
        if(signedOutCourseList.getItems().isEmpty()){
            geenCursusLinks.setText(ALLE_CURSUSSEN_INGESCHREVEN);
        }
    }

    /**
     * Reset de velden en "geen cursus" medlingen
     */
    private void resetLists() {
        signedOutCourseList.getItems().clear();
        signedInCourseList.getItems().clear();
        geenCursusRechts.setText("");
        geenCursusLinks.setText("");
    }

    /**
     * SignedIn knop van de interface om van cursus af te melden
     */
    public void doSignIn() {

        signedIn = signedInCourseList.getSelectionModel().getSelectedItem();
        signedOUt = signedOutCourseList.getSelectionModel().getSelectedItem();

        // check of iets geselecteerd is
        if (signedIn == null && signedOUt == null){
            alert("Selecteer een cursus");
            // als aan de signedOut kant iets geselecteerd is
        } else if (signedOUt != null) {
            // toevoegen aan database en lijsten verversen
            courseRegistrationDAO.storeOne(SessionController.currentUser.getIdUser(), signedOUt.getIdCourse());
            refreshList();
        } else {
            // als aan de signedIn kant iets geselecteerd is melden
            alert(String.format("Je bent al ingeschreven voor %s", signedIn.getNameCourse()));
        }
    }

    /**
     * SignedOut knop van de interface om van cursus af te melden
     */
    public void doSignOut() {

         signedIn = signedInCourseList.getSelectionModel().getSelectedItem();
         signedOUt = signedOutCourseList.getSelectionModel().getSelectedItem();

         // check of iets selecteerd is
        if (signedIn == null && signedOUt == null){
            alert("Selecteer een cursus");
            // als aan de signedIn kant iets geselecteerd
        } else if (signedIn != null) {
            // verwijderen van database en lijsten verversen
            courseRegistrationDAO.deleteOne(SessionController.currentUser.getIdUser(), signedIn.getIdCourse());
            refreshList();
        } else {
            alert(String.format("Je bent al uitgeschreven voor %s", signedOUt.getNameCourse()));
        }
    }

    /**
     * Zet alert op scherm met tekst van String input
     * @param str   dit komt op de alert te staan
     */
    public void alert(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(str);
        alert.showAndWait();
    }

    /**
     * Opent DBAccess voor de relevante DAOs
     */
    private void openDBAccess() {
        this.dbAccess = Main.getDBaccess();
        this.courseRegistrationDAO =new CourseRegistrationDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        dbAccess.openConnection();
    }

    /**
     * Menu knop
     */
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

}





