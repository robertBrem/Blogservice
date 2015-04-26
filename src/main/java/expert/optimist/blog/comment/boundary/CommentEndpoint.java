package expert.optimist.blog.comment.boundary;

import expert.optimist.blog.comment.control.CommentService;
import expert.optimist.blog.comment.entity.Comment;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("comments")
public class CommentEndpoint {

    @Inject
    private CommentService service;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment get(@PathParam("id") Long id) {
        return service.get(id);
    }

}
