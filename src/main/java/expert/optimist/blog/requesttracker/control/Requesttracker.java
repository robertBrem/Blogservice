package expert.optimist.blog.requesttracker.control;


import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

@Stateless
public class Requesttracker {

    public static final String REST_SERVICE_URL = "http://localhost:8080/requesttracker/resources/requests";

    public String sendRequest(JsonObject object) {
        Client client = ClientBuilder.newClient();
        return client
                .target(REST_SERVICE_URL)
                .request()
                .post(Entity.json(object)).toString();
    }

}
