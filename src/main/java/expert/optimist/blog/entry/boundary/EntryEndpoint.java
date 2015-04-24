package expert.optimist.blog.entry.boundary;


import expert.optimist.blog.entry.control.EntryService;
import expert.optimist.blog.entry.entity.Entry;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("entries")
public class EntryEndpoint {

    @Inject
    EntryService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Entry> getAll() {
        return service.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Entry get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Entry create(Entry entry) {
        return service.create(entry);
    }

}
