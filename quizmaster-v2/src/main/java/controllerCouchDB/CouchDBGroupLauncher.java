package controllerCouchDB;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javaCouchDB.CouchDBaccess;
import javaCouchDB.GroupCouchDBDAO;
import model.Group;
import view.Main;

import java.util.ArrayList;


/**
 * @author HRGautam
 */



public class CouchDBGroupLauncher {

    private CouchDBaccess db;
    private GroupCouchDBDAO cdbf;


    public CouchDBGroupLauncher() {
        super();
        db = new CouchDBaccess();
        cdbf = new GroupCouchDBDAO(db);
    }

    public static void main(String[] args) {
        CouchDBGroupLauncher launcher = new CouchDBGroupLauncher();
        launcher.run();
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
        GroupDAO groupDAO = new GroupDAO(db);
        db.openConnection();

        // Data's wegschrijven naar CouchDB . Is uitgecomment andders wordt de data's weer opgeslagen in de databaase.
       /* ArrayList<Group> groups = groupDAO.getAll();
        for (Group group : groups) {
            cdbf.saveSingleGroup(group);
        }*/

        // Een groep terughalen van CouchDB met zijn id.
        Group groupMetID = cdbf.getGroupById("5e47a68abec64807ac0957ebb019ba49");
     	System.out.println(groupMetID);


        // Een groep terughalen van CouchDB met zijn naam.
     	Group group = cdbf.getGroupByName("Groep 2-B");
        System.out.println(group);
    }


}