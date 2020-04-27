package controller;

import database.mysql.DBAccess;
import database.mysql.RoleDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Role;
import model.User;
import view.Main;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * In deze klasse kan de Technisch Beheerder gebruikers aanmaken, wijzigen en verwijderen
 * Als extra feature bevat het een wachtwoord generator en kan alleen de huidige gebruiker zijn wachtwoord zien
 *
 * @author Wesley Wong
 */

public class CreateUpdateUserController {

    public static final String ALL_CHARS = "ABCDEFGHIKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private String wachtwoordUser;
    private User updateUser;

    @FXML
    private TextField idUser;
    @FXML
    private TextField naamTextfield;
    @FXML
    private TextField wachtwoordTextfield;
    @FXML
    private MenuButton rolButton;
    @FXML
    private Label titleLabel;

    /**
     * Setup, deze wordt geinitilaliseerd bij het oproepen van dit scherm
     * @param user      user dat eventueel gewijzigd wordt
     */
    public void setup(User user) {

        openDBAccess();

        // vul Rol dropdrownlijst
        for(Role r : roleDAO.getAll()) {
            MenuItem m = new MenuItem(r.getNameRole());
            m.setOnAction(a -> rolButton.setText(r.getNameRole()));
            rolButton.getItems().add(m
            );
            // set default
            rolButton.setText("administrator");
        }
        // check of een gebruiker aangemaakt of geupdate moet worden
        if(user != null) {
            updateUser = user;

            idUser.setText(String.valueOf(updateUser.getIdUser()));
            naamTextfield.setText(updateUser.getGebruikersnaam());
            wachtwoordUser = updateUser.getWachtwoord();
            wachtwoordTextfield.setText(wachtwoordUser);
            rolButton.setText(user.getNameRolById(updateUser.getIdRol()));

            titleLabel.setText("Wijzig Gebruiker");

            // hide wachtwoord van iedereen behalve de current user
            if (!updateUser.getGebruikersnaam().equals(SessionController.currentUser.getGebruikersnaam())){
                hideWachtwoord();
            }
        } else {
            wachtwoordTextfield.setDisable(true);
            wachtwoordTextfield.setEditable(false);
        }
    }

    /**
     * Gebruiker opslaan
     */
    public void doStore() {
        boolean gaDoor = true;
        String invoerNaam = naamTextfield.getText();

        // check of de gebruikersnaam geen gekke tekens bevat
        if(!Pattern.matches("[a-zA-Z0-9]+", invoerNaam)) {
            Main.alert("Vul een gebruikersnaam in met alleen letters of cijfers", "Nieuwe gebruiker");
            gaDoor = false;
        }

        // check of gebruikersnaam al bestaat bij nieuwe gebruiker toevoegen
        if(updateUser == null && userDAO.getOneByName(invoerNaam) != null) {
            Main.alert("Deze gebruikersnaam is bezet", "Nieuwe gebruiker");
            gaDoor = false;
        }

        // check of alle velden ingevuld zijn
        if (invoerNaam.isEmpty() || wachtwoordTextfield.getText().isEmpty()) {
            Main.alert("Vul alle velden in", "Nieuwe gebruiker");
            gaDoor = false;
        }

        // check of een bestaande user geupdate of nieuwe user aangemaakt moet worden.
        if(gaDoor) {
            if (updateUser != null) {
                updateUser.setGebruikersnaam(invoerNaam);
                updateUser.setWachtwoord(wachtwoordUser);
                updateUser.setIdRol(roleDAO.getIdByName(rolButton.getText()));
                userDAO.updateUser(updateUser);

            } else {
                // aanmaken en oplsaan van nieuwe user
                User user = new User(
                        naamTextfield.getText(),
                        wachtwoordUser,
                        roleDAO.getIdByName(rolButton.getText()));
                userDAO.storeOne(user);
            }
            Main.alert("Gebruiker opgeslagen", "Opslaan");
            Main.getSceneManager().showManageUserScene();
        }

    }

    /**
     * Telt de lengte van een wachtwoord en maakt daarvan *'s
     * Disabled ook wachtwoordTextField
     */
    private void hideWachtwoord() {
        int passlength = wachtwoordUser.length();
        StringBuilder sb=new StringBuilder();

        for (int i = 0; i < passlength ; i++) {
            sb.insert(0, "*");
            wachtwoordTextfield.setText(sb.toString());
        }
        wachtwoordTextfield.setDisable(true);
        wachtwoordTextfield.setEditable(false);
    }

    /**
     * Maakt random een wachtwoord aan en vult deze in wachtwoordTextField
     */
    public void generatePass() {
        String chars = ALL_CHARS;

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        // genereer wachtwoord
        for (int i = 0; i < 8; i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            stringBuilder.append(chars.charAt(randomIndex));
        }
        wachtwoordUser = stringBuilder.toString();
        // check of het verborgen moet worden
        if(updateUser != null) {
            if (!updateUser.getGebruikersnaam().equals(SessionController.currentUser.getGebruikersnaam())) {
                hideWachtwoord();
            } else {
                wachtwoordTextfield.setText(wachtwoordUser);
            }
        } else {
            wachtwoordTextfield.setText(wachtwoordUser);
            hideWachtwoord();
        }
    }

    /**
     * Opent DBAccess voor relevante DAOs
     */
    private void openDBAccess() {
        DBAccess dbAccess = Main.getDBaccess();
        this.roleDAO = new RoleDAO(dbAccess);
        this.userDAO = new UserDAO(dbAccess);
        dbAccess.openConnection();
    }


    /**
     * Overige knoppen
     */
    public void doBackToManageUsers() {
        Main.getSceneManager().showManageUserScene();

    }

    public void doBackToMenu() {
        Main.getSceneManager().showWelcomeScene();
    }


    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }


}
