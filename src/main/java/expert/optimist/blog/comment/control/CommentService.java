package expert.optimist.blog.comment.control;

import expert.optimist.blog.comment.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    public Comment createComment(Comment comment) {
        comment.setModDate(LocalDateTime.now());
        return em.merge(comment);
    }

}
