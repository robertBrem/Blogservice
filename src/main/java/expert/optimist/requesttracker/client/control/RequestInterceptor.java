package expert.optimist.requesttracker.client.control;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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

        Object proceed = ic.proceed();

        TrackerEndpoint endpoint = (TrackerEndpoint) target;
        HttpServletRequest req = endpoint.getRequest();
        String serviceName = getNameOfProxy(endpoint.getClass());
        Method method = ic.getMethod();
        Object[] parameters = ic.getParameters();
        JsonObject request = getJsonRequest(req, serviceName, method, parameters);
        tracker.sendRequest(request);

        return proceed;
    }

    public JsonObject getJsonRequest(HttpServletRequest req, String serviceName, Method method, Object[] parameters) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object parameter : parameters) {
            arrayBuilder.add(parameter.toString());
        }

        return Json.createObjectBuilder()
                .add("className", serviceName)
                .add("functionName", method.getName())
                .add("functionParameterValues", arrayBuilder.build())
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