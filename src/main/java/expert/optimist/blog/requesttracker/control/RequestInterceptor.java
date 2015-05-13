package expert.optimist.blog.requesttracker.control;

import expert.optimist.blog.entry.boundary.EntryEndpoint;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

public class RequestInterceptor {

    @Inject
    Requesttracker tracker;

    @AroundInvoke
    protected Object protocolInvocation(final InvocationContext ic)
            throws Exception {
        Object target = ic.getTarget();
        if (!(target instanceof EntryEndpoint)) {
            return ic.proceed();
        }
        EntryEndpoint endpoint = (EntryEndpoint) target;
        HttpServletRequest req = endpoint.getRequest();

        JsonObject request = Json.createObjectBuilder()
                .add("service", endpoint.getClass().getName())
                .add("function", ic.getMethod().getName())
                .add("callerIp", req.getRemoteAddr())
                .add("callerPort", req.getRemotePort())
                .add("callerHost", req.getRemoteHost())
                .build();
        tracker.sendRequest(request);
        return ic.proceed();
    }
}