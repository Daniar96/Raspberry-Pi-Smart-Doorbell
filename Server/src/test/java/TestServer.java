import org.apache.http.client.methods.HttpPut;
import org.junit.jupiter.api.BeforeEach;
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
    String DEFAULT_USERNAME = "bogdan@gmail.com";
    String DEFAULT_PASSWORD = "password";
    String username;
    String pass;
    CloseableHttpClient client;
    String loginUrl = "http://localhost:8080/Server_war/api/login";

    @BeforeEach
    public void init(){
        client =  HttpClients.createDefault();
        username = getRandomString();
        pass = getRandomString();
    }

    @Test
    public void testLoginSuccess() throws IOException {
        HttpPost post = new HttpPost(loginUrl);
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "application/json");
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
    public void testLoginFailure() throws IOException {
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

    @Test
    public void testRegisterSuccess() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(loginUrl);

        put.setHeader("Content-type", "application/json");
        put.setHeader("Accept", "application/json");

        String inputJson = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + pass + "\"\n" +
                "}";

        StringEntity stringEntity = new StringEntity(inputJson);

        put.setEntity(stringEntity);

        HttpResponse response = client.execute(put);

        String rsp = EntityUtils.toString(response.getEntity());
        assertEquals(200, response.getStatusLine().getStatusCode());

    }

    @Test
    public void canLoginNewUser(){

    }


}
