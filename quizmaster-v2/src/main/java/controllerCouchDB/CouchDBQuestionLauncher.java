package controllerCouchDB;

import javaCouchDB.CouchDBaccess;
import javaCouchDB.QuestionCouchDBDAO;

public class CouchDBQuestionLauncher {
    private CouchDBaccess db;
    private QuestionCouchDBDAO cdbf;

    public CouchDBQuestionLauncher() {
        super();
        db = new CouchDBaccess();
        cdbf = new QuestionCouchDBDAO(db);
    }

    public static void main(String[] args) {
        CouchDBQuestionLauncher myself = new CouchDBQuestionLauncher();
        myself.run();
    }

    public void run() {
//		Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
        try {
            db.setupConnection();
            System.out.println("Connection open");
        } catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
    }
}
