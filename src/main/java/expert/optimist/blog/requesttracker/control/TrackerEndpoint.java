package expert.optimist.blog.requesttracker.control;

import javax.servlet.http.HttpServletRequest;

public interface TrackerEndpoint {
    HttpServletRequest getRequest();
}
