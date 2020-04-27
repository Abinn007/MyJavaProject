package database.mysql;

import model.User;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private DBAccess dbAccess = Main.getDBaccess();
    private UserDAO userDAO = new UserDAO(dbAccess);


        @Test
        void getOneByName() {
            dbAccess.openConnection();

            User expected = new User(1,"BadrHari", "TZN19", 1);
            User actual = userDAO.getOneByName("BadrHari");

            assertEquals(actual, expected);
            dbAccess.closeConnection();
        }

        @Test
        void storeOne() {
            dbAccess.openConnection();

            User actual = new User("TestUserXYZ", "TZN9", 1);
            User expected = actual;

            userDAO.storeOne(actual);
            actual = userDAO.getOneByName("TestUserXYZ");

            assertEquals(actual, expected);

            userDAO.deleteOne(actual);
            dbAccess.closeConnection();
        }

        @Test
        void updateUser() {
            dbAccess.openConnection();

            User actual = new User("TestUserXYZ", "TZN9", 1);
            userDAO.storeOne(actual);

            actual = userDAO.getOneByName("TestUserXYZ");
            userDAO.storeOne(actual);

            actual.setGebruikersnaam("TestUserABC");
            actual.setWachtwoord("TestPassABC");
            userDAO.updateUser(actual);

            User expected = new User("TestUserABC", "TestPassABC",1);

            assertEquals(actual, expected);

            userDAO.deleteOne(actual);
            dbAccess.closeConnection();
        }

    }