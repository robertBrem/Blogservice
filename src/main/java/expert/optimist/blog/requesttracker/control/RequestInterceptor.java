package expert.optimist.blog.requesttracker.control;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

public class RequestInterceptor {

    public static final String PROXY_IDENTIFIER = "$Proxy$_$$_";

    @Inject
    Requesttracker tracker;

    @AroundInvoke
    protected Object protocolInvocation(final InvocationContext ic)
            throws Exception {
        Object target = ic.getTarget();
        if (!(target instanceof TrackerEndpoint)) {
            return ic.proceed();
        }

        TrackerEndpoint endpoint = (TrackerEndpoint) target;
        HttpServletRequest req = endpoint.getRequest();
        String serviceName = getNameOfProxy(endpoint.getClass());
        String methodName = ic.getMethod().getName();
        JsonObject request = getJsonRequest(req, serviceName, methodName);
        tracker.sendRequest(request);

        return ic.proceed();
    }

    public JsonObject getJsonRequest(HttpServletRequest req, String serviceName, String methodName) {
        return Json.createObjectBuilder()
                .add("service", serviceName)
                .add("function", methodName)
                .add("callerIp", req.getRemoteAddr())
                .add("callerPort", req.getRemotePort())
                .add("callerHost", req.getRemoteHost())
                .build();
    }

    public String getNameOfProxy(Class<?> proxyClass) {
        String proxyName = proxyClass.getName();
        int proxyIndex = proxyName.indexOf(PROXY_IDENTIFIER);
        return proxyName.substring(0, proxyIndex);
    }
}