import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

public class getDataOld {
    public void getData() {
        String stubsApiBaseUri = "http://localhost:7819/RTCP/rest/stubs/";
        String domain = "default";
        String environment = "addNumbers";
        String stubName = "1+1=2";
        try {
            HttpClient client = HttpClients.createDefault();

            URIBuilder builder = new URIBuilder(stubsApiBaseUri);
            builder.addParameter("domain", domain);
            builder.addParameter("env", environment);
            builder.addParameter("stub", stubName);
            String listStubsUri = builder.build().toString();
            HttpGet getStubMethod = new HttpGet(listStubsUri);
            HttpResponse getStubResponse = client.execute(getStubMethod);
            int getStubStatusCode = getStubResponse.getStatusLine()
                    .getStatusCode();
            if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
                // Statuscode ungleich 2xx behandeln
                return;
            }
            String responseBody = EntityUtils
                    .toString(getStubResponse.getEntity());
            // Annahme, dass nur ein Stub übereinstimmt
            String stubRelativeUri = XPathFactory
                    .newInstance()
                    .newXPath()
                    .evaluate("/stubs/stub/@href",
                            new InputSource(new StringReader(responseBody)));
            String stubAbsoluteUri = new URI(stubsApiBaseUri).resolve(
                    stubRelativeUri).toString();

            HttpPost startStubMethod = new HttpPost(stubAbsoluteUri);
            ContentType contentType = ContentType.APPLICATION_XML
                    .withCharset("utf-8");
            String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<start-stub />";
            startStubMethod.setEntity(new ByteArrayEntity(data
                    .getBytes(contentType.getCharset()), contentType));
            HttpResponse startStubResponse = client.execute(startStubMethod);
            int startStubStatusCode = startStubResponse.getStatusLine()
                    .getStatusCode();
            if (startStubStatusCode < 200 || startStubStatusCode >= 300) {
                // Statuscode ungleich 2xx behandeln
                return;
            }
            // Wenn Sie den Status des Stubs, der gestartet wird, überprüfen möchten,
            // können Sie aus den Antwortdaten die Stubinstanz-URI abrufen und eine
            // Aktualisierungsabfrage an die URI senden.
            System.out.println(startStubStatusCode);
            String startStubResponseBody = EntityUtils.toString(startStubResponse
                    .getEntity());
            System.out.println(startStubResponseBody);

        } catch(URISyntaxException |IOException |
        XPathExpressionException e)

        {
            // Fehler behandeln
            System.out.println(e);
        }
}
}
