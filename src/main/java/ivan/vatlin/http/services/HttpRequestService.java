package ivan.vatlin.http.services;

import java.util.Map;

public interface HttpRequestService {
    void request(String urlAddress, String method, Map<String, String> headers, Map<String, String> parameters);
}
