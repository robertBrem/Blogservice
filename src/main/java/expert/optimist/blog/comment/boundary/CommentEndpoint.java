package expert.optimist.blog.comment.boundary;

import expert.optimist.blog.comment.control.CommentService;
import expert.optimist.blog.comment.entity.Comment;
import expert.optimist.requesttracker.client.control.RequestInterceptor;
import expert.optimist.requesttracker.client.control.TrackerEndpoint;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Interceptors(RequestInterceptor.class)
@Path("comments")
public class CommentEndpoint implements TrackerEndpoint {

    @Inject
    private CommentService service;

    @Context
    HttpServletRequest request;

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @GET
    @Path("{urlTitle}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment get(@PathParam("urlTitle") String urlTitle) {
        return service.findByUrlTitle(urlTitle);
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment update(@PathParam("id") Long id, Comment comment) {
        if (comment.getId() == null) {
            comment.setId(id);
        } else {
            if (!comment.getId().equals(id)) {
                throw new IllegalArgumentException("Id: " + id + " does not match id of comment: " + comment.getId());
            }
        }
        return service.update(comment);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

}
