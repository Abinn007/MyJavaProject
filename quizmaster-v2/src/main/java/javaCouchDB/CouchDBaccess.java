package javaCouchDB;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;


import com.google.gson.JsonObject;

public class CouchDBaccess {

    private CouchDbClient client;

    public void setupConnection() {
        CouchDbProperties properties = new CouchDbProperties();

        //Set the database name
        properties.setDbName("quizmaster");

        //Create the database if it didn't already exist
        properties.setCreateDbIfNotExist(true);

        //Server is running on localhost
        properties.setHost("localhost");

        //Set the port CouchDB is running on (5984)
        properties.setPort(5984);

        properties.setProtocol("http");

        //uncomment to set the username
        properties.setUsername("admin");
        //uncomment to set the password
        properties.setPassword("hrg123");
        //Create the database client and setup the connection with given
        //properties

        client = new CouchDbClient(properties);
    }

    public String saveDocument(JsonObject document) {
        Response response = client.save(document);
        return response.getId();
    }

    public CouchDbClient getClient() {
        return  client;
    }

}
