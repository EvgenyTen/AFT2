package redmine.api.implementations;

import lombok.Getter;
import redmine.Property;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RestRequest implements Request {

    private final String uri;
    private final HttpMethods method;
    private final Map<String, String> parameters;
    private final Map<String, String> headers;
    private final Object body;

    public RestRequest(String uri, HttpMethods method, Map<String, String> parameters, Map<String, String> headers, Object body) {
        Objects.requireNonNull(uri, "В запросе должен быть uri");
        Objects.requireNonNull(method, "В запросе должен быть указан метод запроса");
        String baseUri = Property.getStringProperty("apiHost");
        this.uri = baseUri + uri;
        this.method = method;
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        this.parameters = parameters;
        if (headers == null) {
            headers = new HashMap<>();
        }
        this.headers = headers;
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(method).append(" ").append(uri);
        if (parameters.size() > 0) {
            sb.append("?");
            parameters.forEach((key, value) -> sb.append(key).append("=").append(value).append("&"));
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(System.lineSeparator());
        headers.forEach((key, value) -> sb.append(key).append("=").append(value).append(System.lineSeparator()));
        sb.append(System.lineSeparator());
        if (body != null) {
            sb.append(body.toString());
        }
        return sb.toString();
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public HttpMethods getMethod() {
        return method;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Object getBody() {
        return body;
    }
}
