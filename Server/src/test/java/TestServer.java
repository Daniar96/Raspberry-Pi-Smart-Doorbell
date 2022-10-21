
import org.junit.jupiter.api.Test;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import static com.group17.server.SecurityFunctions.getRandomString;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestServer {
    String DEFAULT_USERNAME = "newuser@gmail.com";
    String DEFAULT_PASSWORD = "password";
    String username;
    String pass;

    @Test
    public void testRegisterSucces() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/Server_war/api/login");

        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "application/json");

        username = getRandomString();
        pass = getRandomString();




        String inputJson = "{\n" +
                "  \"username\": \"" + DEFAULT_USERNAME + "\",\n" +
                "  \"password\": \"" + DEFAULT_PASSWORD + "\"\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);

        post.setEntity(stringEntity);

        HttpResponse response = client.execute(post);

        String rsp = EntityUtils.toString(response.getEntity());
        assertEquals(200, response.getStatusLine().getStatusCode());

    }

    @Test
    public void testRegisterFailure() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/Server_war/api/login");

        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "application/json");

        username = getRandomString();
        pass = getRandomString();




        String inputJson = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + pass + "\"\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);

        post.setEntity(stringEntity);

        HttpResponse response = client.execute(post);

        String rsp = EntityUtils.toString(response.getEntity());
        assertEquals(403, response.getStatusLine().getStatusCode());

    }


}
