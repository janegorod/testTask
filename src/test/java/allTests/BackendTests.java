package allTests;

import jdk.jfr.ContentType;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;

public class BackendTests {

    @BeforeClass
    public static void initSpec() throws IOException {
    }
         @Test
    public void useSpec() throws IOException {
            URL host = new URL("https://api.whisk-dev.com/");
            HttpURLConnection http = (HttpURLConnection)host.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Bearer {4S6sAfpswCnp0N32xLv5VYgyrdVDCa1ENK0KA781GrzvGRgSlTxQFa5SaH6UPbIL}");

            //http.setRequestProperty("Content-Length", "0");

             String jsonInputString = "{\"name\": \"Upendra\", \"primary\": \"false\"}";
          /*   {
                 "name": "string",
                     "primary": false
             }
             */

             try(OutputStream os = http.getOutputStream()) {
                 byte[] input = jsonInputString.getBytes("utf-8");
                 os.write(input, 0, input.length);
             }

             try(BufferedReader br = new BufferedReader(
                     new InputStreamReader(http.getInputStream(), "utf-8"))) {
                 StringBuilder response = new StringBuilder();
                 String responseLine = null;
                 while ((responseLine = br.readLine()) != null) {
                     response.append(responseLine.trim());
                 }
                 System.out.println(response.toString());
             }
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();


        }

}
