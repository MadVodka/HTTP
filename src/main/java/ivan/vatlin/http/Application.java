package ivan.vatlin.http;

import ivan.vatlin.http.services.DemoService;
import ivan.vatlin.http.services.DemoServiceImpl;
import ivan.vatlin.http.services.HttpRequestService;
import ivan.vatlin.http.services.HttpRequestServiceImpl;

public class Application {
    public static void main(String[] args) {
        HttpRequestService httpRequestService = new HttpRequestServiceImpl();
        DemoService demoService = new DemoServiceImpl(httpRequestService);
        demoService.run();
    }
}
