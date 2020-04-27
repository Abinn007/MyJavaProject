package view;

import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Main methode dat de Quizmaster TZN launched
 */


public class Main extends Application {

    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess db = null;

    public static DBAccess getDBaccess() {
        if (db == null) {
            db = new DBAccess("Quizmaster","userQuizmaster", "tznQuizmaster");
        }
        return db;
    }


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Quizmaster TZN");
        getSceneManager().showLoginScene();
        primaryStage.show();
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    /**
     * Universele alert voor alle schermen
     * @param str       inhoud van alert
     * @param titel     titel van alert
     */
    public static void alert(String str, String titel){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setTitle(titel);
        alert.setContentText(str);
        alert.showAndWait();
    }

    /**
     * Confirmation alert voor alle schermen
     * @param type    type van wat wordt verwijderd, wordt getoond in bericht
     * @return        boolean met wat de gebruiker ingevoerd heeft
     */
    public static boolean askConfirmation(String type){
        Alert check = new Alert(Alert.AlertType.CONFIRMATION, String.format("Wil je deze %s verwijderen?",type));
        check.setHeaderText("Verwijderen");
        check.setTitle("Bevestigen");
        check.getButtonTypes().clear();
        ButtonType bevestig = new ButtonType("Ja");
        check.getButtonTypes().add(bevestig);
        ButtonType annuleren = new ButtonType("Annuleer");
        check.getButtonTypes().add(annuleren);
        Optional<ButtonType> result = check.showAndWait();
        return result.isPresent() && result.get() == bevestig;
    }

}