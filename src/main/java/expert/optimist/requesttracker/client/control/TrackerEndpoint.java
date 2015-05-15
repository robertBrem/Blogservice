package expert.optimist.requesttracker.client.control;

import javax.servlet.http.HttpServletRequest;

public interface TrackerEndpoint {
    HttpServletRequest getRequest();
}