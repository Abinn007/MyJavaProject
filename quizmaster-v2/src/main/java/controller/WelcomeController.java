package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.Main;

/**
 * Dit scherm komt na het inloggen en geeft de taken weer aan de hand van rol gebruiker.
 *
 * @author WesleyWong
 */

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private  MenuButton taskMenuButton ;

    public void setup(){

        welcomeLabel.setText("Welkom " + SessionController.currentUser.getGebruikersnaam()+"!");

        // De taken van currentUser ophalen en deze in de menu zetten
        for (String s : Main.getSceneManager().getTaken(SessionController.currentUser.getIdRol())){
            MenuItem m = new MenuItem(s);
            m.setOnAction(event -> Main.getSceneManager().showSceneBijTaak(s));
            taskMenuButton.getItems().add(m);
        }
    }

    // Uitlog knop
    public void doLogout() {
        SessionController.logoutCurrentUser();
        Main.getSceneManager().showLoginScene();
    }

}

