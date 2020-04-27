package javaCouchDB;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Group;

import java.util.List;


/**
 * * Deze CouchDB DAO class is om data's te wegschrijven naar CouchDB database en terughalen van database.
 * @author HRGautam
 */


public class GroupCouchDBDAO {
    private CouchDBaccess db;
    private Gson gson;

    public GroupCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    // Data's wegschrijven naar CouchDB
    public String saveSingleGroup(Group group) {
        String jsonstring = gson.toJson(group);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }


    // Een groep terughalen van database CouchDB met zijn id.
    public Group getGroupById(String doc_Id) {
        JsonObject group = db.getClient().find(JsonObject.class, doc_Id);
        Group resultaat = gson.fromJson(group, Group.class);
        return resultaat;
    }


    // Een groep terughalen van database CouchDB met zijn naam.
    public Group getGroupByName(String naam) {
        Group resultaat = null;
        List<JsonObject> allegroup = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject group : allegroup) {
            resultaat = gson.fromJson(group, Group.class);
            if (resultaat.getNameGroup().equals(naam)) {
                return resultaat;
            }
        }
        return  resultaat;
    }

     }
