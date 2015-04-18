package expert.optimist.blog.blogservice.control;

import expert.optimist.blog.blogservice.entity.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class EntryService {

    @PersistenceContext
    EntityManager em;

    public Set<Entry> getAll() {
        List<Entry> entries = em.createNamedQuery("Entries.getAll").getResultList();
        return new HashSet<>(entries);
    }

    public Entry create(Entry entry) {
        entry.setCreationDate(LocalDateTime.now());
        return em.merge(entry);
    }

}
