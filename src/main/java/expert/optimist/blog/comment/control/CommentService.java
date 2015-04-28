package expert.optimist.blog.comment.control;

import expert.optimist.blog.TextConverter;
import expert.optimist.blog.comment.entity.Comment;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private SessionContext ctx;

    @Inject
    private TextConverter textConverter;

    public Comment get(Long id) {
        return em.find(Comment.class, id);
    }

    public Comment findByUrlTitle(String urlTitle) {
        return (Comment) em.createNamedQuery("Comments.findByUrlTitle").setParameter("urlTitle", urlTitle).getSingleResult();
    }

    public Comment create(Comment comment) {
        comment.setCreationDate(LocalDateTime.now());
        comment.setUrlTitle(textConverter.getUrlTitle(comment.getAuthor(), comment.getCreationDate()));
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
