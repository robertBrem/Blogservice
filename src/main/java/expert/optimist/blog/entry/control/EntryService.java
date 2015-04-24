package expert.optimist.blog.entry.control;

import expert.optimist.blog.comment.control.CommentService;
import expert.optimist.blog.comment.entity.Comment;
import expert.optimist.blog.entry.entity.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class EntryService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CommentService commentService;

    public Set<Entry> getAll() {
        @SuppressWarnings("unchecked")
        List<Entry> entries = em.createNamedQuery("Entries.getAll").getResultList();
        return new HashSet<>(entries);
    }

    public Entry create(Entry entry) {
        entry.setCreationDate(LocalDateTime.now());
        return em.merge(entry);
    }

    public Entry get(Long id) {
        return (Entry) em.createNamedQuery("Entries.findById").setParameter("id", id).getSingleResult();
    }

    public Comment addComment(Long id, Comment comment) {
        Entry entry = get(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with id: " + id + " does not exist!");
        }
        comment.setEntry(entry);
        return commentService.createComment(comment);
    }
}