package expert.optimist.blog.comment.control;

import expert.optimist.blog.comment.entity.Comment;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private SessionContext ctx;

    public Comment get(Long id) {
        return em.find(Comment.class, id);
    }

    public Comment create(Comment comment) {
        comment.setCreationDate(LocalDateTime.now());
        String author = comment.getAuthor().replaceAll("\\s", "_");
        String dateTime = comment.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm_ss"));
        comment.setUrlTitle(author + "__" + dateTime);
        updateModFields(comment);
        return em.merge(comment);
    }

    public Comment update(Comment comment) {
        Comment oldComment = get(comment.getId());
        comment.setEntry(oldComment.getEntry());
        comment.setChildren(oldComment.getChildren());
        updateModFields(comment);
        return em.merge(comment);
    }

    public void delete(Long id) {
        Comment comment = get(id);
        if (comment == null) {
            throw new IllegalArgumentException("Comment with id: " + id + "does not exists!");
        }
        em.remove(comment);
    }

    private void updateModFields(Comment comment) {
        comment.setModDate(LocalDateTime.now());
        comment.setModUser(ctx.getCallerPrincipal().getName());
    }

}
