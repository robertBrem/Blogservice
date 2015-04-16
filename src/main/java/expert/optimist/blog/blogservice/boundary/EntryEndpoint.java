package expert.optimist.blog.blogservice.boundary;


import expert.optimist.blog.blogservice.entity.Entry;

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
    public Set<Entry> getAll() {
        Set<Entry> entries = new HashSet<>();
        Entry entry = new Entry();
        entry.setAuthor("Robert Brem");
        entry.setTitle("Erster Eintrag");
        entry.setTeaser("Ein kleiner Anrisstext.");
        entry.setContent("Der grosse Inhalt!");
        entries.add(entry);
        return entries;
    }

}
