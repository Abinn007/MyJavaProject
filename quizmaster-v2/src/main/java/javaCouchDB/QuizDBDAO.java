
package javaCouchDB;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Quiz;

/**
  *@author lisa kemeling
  *Database Acces Object voor een Quiz om in CouchDB op te slaan, te updaten en verwijderen.
**/

public class QuizDBDAO {
    private CouchDBaccess couchDBaccess;
    private Gson gson;

    public QuizDBDAO(CouchDBaccess couchDBaccess) {
        super();
        this.couchDBaccess = couchDBaccess;
        this.gson = new Gson();
    }

    public String saveSingleQuiz(Quiz quiz){
        String jsonstring = gson.toJson(quiz);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = couchDBaccess.saveDocument(jsonObject);
        return doc_Id;
    }

}

