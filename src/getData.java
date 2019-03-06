import java.io .*;
import java.net .*;
import javax.xml.xpath.*;
import org.apache.http.*;
import org.apache.http.client .*;
import org.apache.http.client.methods .*;
import org.apache.http.client.utils .*;
import org.apache.http.entity .*;
import org.apache.http.impl.client .*;
import org.apache.http.util .*;
import org.xml.sax .*;
public class getData {
    public void getData() {
        String stubsApiBaseUri = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
        String domain = "default";
        String environment = "addNumbers";
        String stubName = "1+1=2";
        try {
            HttpClient client = HttpClients.createDefault();

            StringBuilder builder = new StringBuilder();
            builder.append(stubsApiBaseUri);
            String food="Arrabiata";
            builder.append(food);
            String listStubsUri = builder.toString();
            HttpGet getStubMethod = new HttpGet(listStubsUri);
            HttpResponse getStubResponse = client.execute(getStubMethod);
            int getStubStatusCode = getStubResponse.getStatusLine()
                    .getStatusCode();
            if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
                // Statuscode ungleich 2xx behandeln
                return;
            }
            System.out.println(getStubResponse.getEntity());
        }
        catch(IOException e)
        {
            // Fehler behandeln
            System.out.println(e);
        }
    }
}
