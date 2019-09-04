package ivan.vatlin.http.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DemoServiceImpl implements DemoService {
    private HttpRequestService httpRequestService;

    public DemoServiceImpl(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public void run() {
        testGetRequest();
        System.out.println();
        testPostRequest();
    }

    private void testGetRequest() {
        httpRequestService.request("https://ya.ru", "GET", null, null);
    }

    private void testPostRequest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Opera/9.60 (Windows NT 6.0; U; en) Presto/2.1.1");
        headers.put("accept", "application/json");

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("name", "George");
        parameters.put("sex", "male");
        parameters.put("height", "188");
        parameters.put("weight", "87");

        httpRequestService.request("https://httpbin.org/post", "POST", headers, parameters);
    }
}
