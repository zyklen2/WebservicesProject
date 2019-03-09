import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class getData {
    public void getData(String food) {
        String baseUri = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
        try {
            HttpClient client = HttpClients.createDefault();
            StringBuilder builder = new StringBuilder();
            builder.append(baseUri);
            builder.append(food);
            String listStubsUri = builder.toString();
            HttpGet getStubMethod = new HttpGet(listStubsUri);
            HttpResponse getStubResponse = client.execute(getStubMethod);
            int getStubStatusCode = getStubResponse.getStatusLine().getStatusCode();
            if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
                // Statuscode ungleich 2xx behandeln
                return;
            }
            JSONObject jsonObj = readJsonFromUrl(listStubsUri);
            JSONArray part = jsonObj.getJSONArray("meals");
            String out = part.getJSONObject();
            System.out.println(part);
        } catch (IOException e) {
            // Fehler behandeln
            System.out.println(e);
        }
    }

        public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
        }
        private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
