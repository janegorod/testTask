package resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Singleton
public class Service {

    public String sendGetRequest(HashMap<String, String> params, String urlToSend, HashMap<String, String> headers){
        String response = "";
        String fullUrl = urlToSend + (params != null? ("?" + mapToString(params)) : "");
        try {
            URL url = new URL(fullUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            try {
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestMethod("GET");

                if(headers != null && !headers.isEmpty()){
                    for(Map.Entry<String,String> entry : headers.entrySet()){
                        if(entry.getKey().equals("type")){
                            con.setRequestProperty("Content-Type", (entry.getValue().equals("json")) ? "application/json; charset=UTF-8" : "application/x-www-form-urlencoded");
                        }

                        if(entry.getKey().equals("token")){
                            con.setRequestProperty("Authorization", entry.getValue());
                        }
                    }
                }

                int status = con.getResponseCode();

                if (status == 200) {
                    response = parseResponse(con);
                } else {
                    response = "ERROR: " + status;
                }
            }catch (Exception e){
                response = "ERROR: ";
            }
            finally {
                con.disconnect();
            }
        } catch (Exception e) {;
            response = "ERROR: " + e.getMessage();
        }
        return response;
    }

    private String parseResponse(HttpURLConnection con) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            try {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line+"\n");
                }
                result = sb.toString();
            }catch (Exception e){
                System.out.println(e.toString());
            }finally {
                reader.close();
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public static String sendPostRequest(String urlToSend, String type, String parameters, String token) {
        try {
            URL url = new URL(urlToSend);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", (type.equals("json")) ? "application/json; charset=UTF-8" : "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", (type.equals("json")) ? "application/json" : "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            con.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes(StandardCharsets.UTF_8).length));
            con.setRequestProperty("charset", "utf-8");
            if(token != null){
                con.setRequestProperty("Authorization", token);
            }
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream os = con.getOutputStream();
            os.write(parameters.getBytes("UTF-8"));
            os.close();

            BufferedReader br = null;

            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String output;

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

//            int status = con.getResponseCode();
            con.disconnect();

//            if(status == 200){
//                return "SUCCESS";
//            } else {
//                return sb.toString();
//            }
            return sb.toString();
        } catch (Exception e){
            System.out.println(e.toString());
            return "ERROR";
        }
    }


    private String mapToString(HashMap<String, String> params){
        try {
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : params.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            //byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            return sj.toString();
        }catch (Exception e){
            System.out.println(e.toString());
            return "";
        }
    }
}

