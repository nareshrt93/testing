package co.qa.tests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class PostAPITest extends TestBase{

	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	String header;
	
	
	@BeforeMethod
	public void setup() throws IOException {
		testBase = new TestBase();
		serviceURL = prop.getProperty("baseurl");
		apiURL = prop.getProperty("APIurl");
		url = serviceURL + apiURL;
		
	}
	
	
	@Test
	public void PostAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		
		 //------jackson api
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("macbeth", "king"); //expected users object
		
		//object(class users to JSON) - Marshelling
		mapper.writeValue(new File("F:\\Eclipse\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		
		//json to jsonString
		String usersJsonString = mapper.writeValueAsString(users);
		
		System.out.println(usersJsonString);
		
		httpResponse = restClient.post(url, usersJsonString, headerMap);//call api
		
		
		//validate response
		//1.status code
		int statusCode =httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);
		
		//2. JsonString
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		
		System.out.println("response string-------->"+responseString);
		JSONObject responseJson = new JSONObject(responseString);

		System.out.println("response json------->"+responseJson);
		
		
		//json to java object ----unmarshelling
		Users userObj = mapper.readValue(responseString, Users.class); //acutal users object
		System.out.println(userObj);
		
		
		Assert.assertTrue(users.getName().equals(userObj.getName()));
		
		
		Assert.assertTrue(users.getJob().equals(userObj.getJob()));
	
		System.out.println(userObj.getCreatedAt());
				
		System.out.println(userObj.getId());
	
	}
	
}
