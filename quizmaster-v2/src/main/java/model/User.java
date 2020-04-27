package model;

import database.mysql.DBAccess;
import database.mysql.RoleDAO;
import view.Main;

import java.util.Objects;

/**
 *
 * Met de model.User worden de gebruikers van Quizmaster aangemaakt.
 *
 * @author Wesley Wong
 */

public class User {

    private int idUser;
    private String gebruikersnaam;
    private String wachtwoord;
    private int idrol;

    /**
     * Constructor met idUser wordt gebruikt om users op te halen uit de database
     */
    public User(int idUser, String gebruikersnaam, String wachtwoord, int idrol) {
        this.idUser = idUser;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.idrol = idrol;
    }

    /**
     * Constuctor zonder idUser is om gebruikers weg te schrijven.
     * idUser wordt zelf toegekend in de database (auto increment).
     */
    public User(String gebruikersnaam, String wachtwoord, int idrol) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.idrol = idrol;
    }

    /**
     * Deze wordt gebruikt om een default SessionController.currentUser te declareren
     */
    public User() {
    }


    /**
     * Getters & Stters
     */
    public int getIdUser() {
        return this.idUser;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public void setIdRol(int idrol) {
        this.idrol = idrol;
    }

    public int getIdRol()
    {
        return this.idrol;
    }


    /**
     * Methodes
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %d\nGebruikersnaam: %s\nRol: %s\n", getIdUser(), getGebruikersnaam(),getNameRolById(getIdRol())
                );
    }

    // Deze wordt gebruikt in de toString methode om een rolnaam te laten zien in plaats van idRol
    public String getNameRolById(int idrol){
        DBAccess db = Main.getDBaccess();
        RoleDAO roleDAO = new RoleDAO(db);
        return roleDAO.getNamebyId(idrol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idrol == user.idrol &&
                gebruikersnaam.equals(user.gebruikersnaam) &&
                wachtwoord.equals(user.wachtwoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gebruikersnaam, wachtwoord, idrol);
    }
}

