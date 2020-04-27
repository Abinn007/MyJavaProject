

package controllerCouchDB;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javaCouchDB.CouchDBaccess;
import javaCouchDB.QuizDBDAO;
import model.Quiz;
import view.Main;

import java.util.List;


/**
 * @author lisa kemeling
 */


public class CouchDBQuizLauncher {
    private CouchDBaccess couchDBaccess;
    private QuizDBDAO quizDBDAO;
    private DBAccess dbAccess;
    private QuizDAO quizDAO;
    public List<Quiz> quizzes;

    public CouchDBQuizLauncher() {
        super();
        this.couchDBaccess = new CouchDBaccess();
        this.quizDBDAO = new QuizDBDAO(couchDBaccess);
        this.dbAccess = Main.getDBaccess();
        this.quizDAO = new QuizDAO(dbAccess);
        dbAccess.openConnection();
    }

    public static void main(String[] args) {
        CouchDBQuizLauncher myself = new CouchDBQuizLauncher();
        myself.run();
    }

    public void run(){
        try{
            couchDBaccess.setupConnection();
            System.out.println("Verbinding gemaakt");
        }catch(Exception fout){
            System.out.println("\nGeen verbinding gemaakt");
        }
        quizzes = quizDAO.getAll();
        for (Quiz quiz :
                quizzes) {
            quizDBDAO.saveSingleQuiz(quiz);
        }

    }


}


