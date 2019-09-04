package ivan.vatlin.http.services;

import com.sun.istack.internal.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpRequestServiceImpl implements HttpRequestService {
    public void request(String urlAddress, String method,
                        @Nullable Map<String, String> headers, @Nullable Map<String, String> parameters) {
        try {
            URL url = new URL(urlAddress);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod(method);
            setHttpHeaders(httpsURLConnection, headers);

            setHttpParameters(httpsURLConnection, parameters);


            System.out.println(method + " request to " + urlAddress + "\n");
            String response = getResponseData(httpsURLConnection);
            System.out.println(response);
        } catch (MalformedURLException e) {
            System.out.println("Check your url");
        } catch (IOException e) {
            System.out.println("Error during connection");
        }
    }

    private HttpURLConnection setHttpHeaders(HttpURLConnection httpURLConnection,
                                             Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(httpURLConnection::setRequestProperty);
        }
        return httpURLConnection;
    }

    private String prepareParameters(Map<String, String> parameters) throws IOException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    private void setHttpParameters(HttpURLConnection httpURLConnection, Map<String, String> parameters) throws IOException {
        if (parameters != null) {
            httpURLConnection.setDoOutput(true);
            try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream())) {
                dataOutputStream.writeBytes(prepareParameters(parameters));
                dataOutputStream.flush();
            }
        }
    }

    private String getResponseData(HttpURLConnection httpURLConnection) throws IOException {
        StringBuilder response = new StringBuilder();
        response.append("----- Response headers -----\n");

        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        headerFields.forEach((key, headerValues) -> {
            response.append(key).append(":");
            for (String header : headerValues) {
                response.append(header).append(",");
            }
            response.append("\n");
        });

        try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            response.append("\n").append("----- Response body -----").append("\n");

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                response.append(inputString);
            }
        }

        return response.toString();
    }
}
