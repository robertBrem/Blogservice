package expert.optimist.requesttracker.client.control;

import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

@Stateless
public class Requesttracker {

    public static final String REST_SERVICE_URL = "http://104.167.115.229:8083/requesttracker/resources/requests";
//    public static final String REST_SERVICE_URL = "http://localhost:8080/requesttracker/resources/requests";

    public String sendRequest(JsonObject object) {
        Client client = ClientBuilder.newClient();
        return client
                .target(REST_SERVICE_URL)
                .request()
                .async()
                .post(Entity.json(object)).toString();
    }

}