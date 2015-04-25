package expert.optimist.blog.entry.control;

import expert.optimist.blog.comment.control.CommentService;
import expert.optimist.blog.comment.entity.Comment;
import expert.optimist.blog.entry.entity.Entry;
import org.jboss.annotation.security.SecurityDomain;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
@SecurityDomain("keycloak")
public class EntryService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CommentService commentService;

    @Resource
    private SessionContext ctx;

    @PermitAll
    public Set<Entry> getAll() {
        @SuppressWarnings("unchecked")
        List<Entry> entries = em.createNamedQuery("Entries.getAll").getResultList();
        return new HashSet<>(entries);
    }

    @RolesAllowed("admin")
    public Entry create(Entry entry) {
        if (entry.getId() != null) {
            Entry dbEntry = get(entry.getId());
            if (dbEntry != null) {
                throw new IllegalArgumentException(entry.getId() + " does exist!");
            }
        }

        entry.setCreationDate(LocalDateTime.now());
        entry.setUrlTitle(entry.getTitle().replaceAll("\\s", "_"));
        updateModFields(entry);
        return em.merge(entry);
    }

    @PermitAll
    public Entry get(Long id) {
        return (Entry) em.createNamedQuery("Entries.findById").setParameter("id", id).getSingleResult();
    }

    @RolesAllowed("admin")
    public Entry update(Entry entry) {
        Entry dbEntry = get(entry.getId());
        if (dbEntry == null) {
            throw new IllegalArgumentException(entry.getId() + " does not exist!");
        }
        updateModFields(entry);
        return em.merge(entry);
    }

    @RolesAllowed("admin")
    public void delete(Long id) {
        Entry entry = get(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with id: " + id + " does not exist!");
        }
        em.remove(entry);
    }

    @PermitAll
    public Comment addComment(Long id, Comment comment) {
        Entry entry = get(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with id: " + id + " does not exist!");
        }
        comment.setEntry(entry);
        return commentService.createComment(comment);
    }

    private void updateModFields(Entry entry) {
        entry.setModDate(LocalDateTime.now());
        entry.setModUser(ctx.getCallerPrincipal().getName());
    }
}