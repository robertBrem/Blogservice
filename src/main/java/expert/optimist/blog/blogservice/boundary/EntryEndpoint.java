package expert.optimist.blog.blogservice.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@Path("entries")
public class EntryEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getAll() {
        Set<String> entries = new HashSet<>();
        return entries;
    }

}
