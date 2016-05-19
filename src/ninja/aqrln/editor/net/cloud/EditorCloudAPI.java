package ninja.aqrln.editor.net.cloud;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alexey Orlenko
 */
public class EditorCloudAPI {
    private static final String HOST = "https://editor.aqrln.ninja/";
    private static final String PUBLISH_ENDPOINT = HOST + "api/submit.json";
    private static final String DOCUMENT_URL = HOST + "doc/";

    private static String getAccessToken() {
        String token = System.getenv("EDITOR_TOKEN");
        if (token == null) {
            token = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
        }

        return token;
    }

    private static String doPostRequest(String htmlData) {
        String response = null;

        try {
            URL url = new URL(PUBLISH_ENDPOINT);

            Map<String, String> params = new LinkedHashMap<>();
            params.put("accessToken", getAccessToken());
            params.put("content", htmlData);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }

                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);

            connection.getOutputStream().write(postDataBytes);

            Reader stream = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            int c;
            while ((c = stream.read()) != -1) {
                stringBuilder.append((char) c);
            }

            response = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString(), "Connection error", JOptionPane.ERROR_MESSAGE);
        }

        return response;
    }

    public static String publishDocument(String htmlContent) {
        String response = doPostRequest(htmlContent);

        if (response == null) {
            return null;
        }

        if (response.contains("error")) {
            JOptionPane.showMessageDialog(null, "Invalid access token", "Connection error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int initialPos = response.indexOf(':') + 2;
        int finalPos = response.lastIndexOf('"');
        String id = response.substring(initialPos, finalPos);

        return DOCUMENT_URL + id + "/";
    }
}
