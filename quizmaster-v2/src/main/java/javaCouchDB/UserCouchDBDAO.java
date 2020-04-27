package javaCouchDB;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import java.util.List;

public class UserCouchDBDAO {
	
	private CouchDBaccess db;
	private Gson gson;
	
	public UserCouchDBDAO(CouchDBaccess db) {
		super();
		this.db = db;
		gson = new Gson();
	}
	
	public String saveSingleUser(User user) {
		String jsonstring = gson.toJson(user);
		System.out.println(jsonstring);
		JsonParser parser = new JsonParser();
		JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
		String doc_Id = db.saveDocument(jsonobject);
		return doc_Id;
	}

}

