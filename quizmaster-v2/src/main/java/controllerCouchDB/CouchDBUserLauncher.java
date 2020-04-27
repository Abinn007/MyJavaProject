
package controllerCouchDB;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javaCouchDB.CouchDBaccess;
import javaCouchDB.UserCouchDBDAO;
import model.User;
import view.Main;

import java.util.ArrayList;



/**
 * @author Wesley wong
 */




public class CouchDBUserLauncher {
	

	private CouchDBaccess db;
	private UserCouchDBDAO cdbf;
	
	public CouchDBUserLauncher() {
		super();
		db = new CouchDBaccess();
		cdbf = new UserCouchDBDAO(db);
	}
	
	public static void main(String[] args) {
		CouchDBUserLauncher myself = new CouchDBUserLauncher();
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
		DBAccess db = Main.getDBaccess();
		UserDAO userDAO = new UserDAO(db);
		db.openConnection();

		ArrayList<User> allusers = userDAO.getAll();
		for (User user : allusers) {
			cdbf.saveSingleUser(user);
		}
	}
}

