package expert.optimist.blog.comment.control;

import expert.optimist.blog.comment.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    public Comment createComment(Comment comment) {
        return em.merge(comment);
    }

}
