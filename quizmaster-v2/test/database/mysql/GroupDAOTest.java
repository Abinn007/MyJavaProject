package database.mysql;
import model.Course;
import model.Group;
import model.User;
import org.junit.jupiter.api.Test;
import view.Main;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HRGautam
 * Testclass voor unittesting GroupDAO
 */

class GroupDAOTest {
    private DBAccess dbAccess = Main.getDBaccess();
    private GroupDAO groupDAO = new GroupDAO(dbAccess);
    private UserDAO userDAO = new UserDAO(dbAccess);
    private CourseDAO courseDAO = new CourseDAO(dbAccess);


    //Om een groep naar de databse weg te schrijven
    @Test
    void storeOne(){
        dbAccess.openConnection();
        User testDocent = userDAO.getOneById(5);
        Course testCourse = courseDAO.getOneById(3);
        Group testGroup = new Group("House",testCourse,testDocent);
        groupDAO.storeOne(testGroup);
        dbAccess.closeConnection();
    }

    // Om een groep uit de database halen met idNummer.
    @Test
    void getOneById() {
        dbAccess.openConnection();
        Group expected = new Group (3,"Groep 3-C",courseDAO.getOneById(3),userDAO.getOneById(2));
        Group actual = groupDAO.getOneById(3);
        assertEquals(expected, actual);
        dbAccess.closeConnection();
       }


    // Om alle groupen uit de database te halen
    @Test
    void getAll() {
        dbAccess.openConnection();
        ArrayList<Group> testGroups = groupDAO.getAll();
        for (Group group : testGroups) {
            assertNotNull(group);
        }
        dbAccess.closeConnection();
    }

}
