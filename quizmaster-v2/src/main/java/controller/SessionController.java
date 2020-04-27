package controller;

import model.*;

/**
 * Deze controller houdt bij wie de currentUser is & op enkele schermen wat de currentScreen is
 *
 * @author Lisa Kemeling, Wesley Wong
 */

public class SessionController {

    public static User currentUser = new User();
    public static String currentScreen = "";

    public static void setCurrentUser(User currentUser) {
        SessionController.currentUser = currentUser;
    }

    public static void logoutCurrentUser(){
        currentUser = null;
    }

    public static void setCurrentScreen(String currentScreen) {
        SessionController.currentScreen = currentScreen;
    }

}
