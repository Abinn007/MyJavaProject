package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Main;

/**
 * Dit is de loginscherm van Quizmaster.
 * Dit scherm checkt of alle velden (correct) ingevuld zijn.

 * @author Wesley Wong
 */

public class LoginController {
    private final static String GEEN_GB = "Vul een gebruikersnaam in";
    private final static String GEEN_WW = "Vul een wachtwoord in";
    private final static String ONKBD_GB = "Gebruikersnaam is onbekend";
    private final static String FOUT_WW = "Wachtwoord is incorrect";

    private DBAccess db;
    private UserDAO userDAO;

    @FXML private TextField nameTextField;
    @FXML private TextField passwordField;
    @FXML private Label errorGb;
    @FXML private Label errorWw;

    public LoginController() {
        super();
        this.db = Main.getDBaccess();
        this.userDAO = new UserDAO(db);
    }


    /**
     * Login knop. Checkt of de velden correct ingevuld zijn en geeft response wanneer dat niet zo is
     */
    @FXML
    public void doLogin() {
        Main.getDBaccess().openConnection();

        String gbInvoer = nameTextField.getText();
        String wwInvoer = passwordField.getText();

        // reset error labels naar leeg;
        errorGb.setText("");
        errorWw.setText("");

        boolean gaDoor = true;

        // check of alle velden correct ingevuld zijn
        if (gbInvoer.isEmpty()) {
            errorGb.setText(GEEN_GB);
            gaDoor = false;
        } else if (userDAO.getOneByName(gbInvoer) == null) {
            errorGb.setText(ONKBD_GB);
            gaDoor = false;
        } else if (wwInvoer.isEmpty()) {
            errorWw.setText(GEEN_WW);
            gaDoor = false;
        } else if (!userDAO.getOneByName(gbInvoer).getWachtwoord().equals(wwInvoer)) {
            errorWw.setText(FOUT_WW);
            gaDoor = false;
        }

        // zo ja, ga naar het volgende scherm
        if (gaDoor) {
            SessionController.setCurrentUser(userDAO.getOneByName(gbInvoer));
            Main.getSceneManager().showWelcomeScene();
        }
    }

    /**
     * Exit knop
     */
    @FXML
    public void doQuit(){
        db.closeConnection();
        Platform.exit();
    }
}