package co.qa.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{


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
	
	
	@Test(priority=1)
	public void getAPITestWithoutHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		httpResponse = restClient.get(url);
		
		//status code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("status code ---->"+statusCode);
		
		Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status code is not 200");
		
		//json string
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("response JSON----->"+ responseJSON);
		
		//single value assertion
		//per page
		String perValuePage =TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("the per value page is ------>" +perValuePage);
		
		Assert.assertEquals(Integer.parseInt(perValuePage), 6);
		
		
		//total value
		String totalValue =TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("the total value page is ------>" +totalValue);
		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get value from JSON Array
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		
		 System.out.println(first_name);
		 System.out.println(id);
		 System.out.println(last_name);
		 System.out.println(avatar);
		
		//headers
		Header[] allHeaders = httpResponse.getAllHeaders();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
			
		for(Header eachHeader: allHeaders )	{
			headerMap.put(eachHeader.getName(), eachHeader.getValue());
		}
		 
		System.out.println("header------>"+headerMap);
			
	}
	
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		
		
		
		httpResponse = restClient.get(url,headerMap );
		
		//status code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("status code ---->"+statusCode);
		
		Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status code is not 200");
		
		//json string
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("response JSON----->"+ responseJSON);
		
		//single value assertion
		//**************************************************************************************************************//
		//per page
		String perValuePage =TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("the per value page is ------>" +perValuePage);
		
		Assert.assertEquals(Integer.parseInt(perValuePage), 6);
		//**************************************************************************************************************//
		
		//total value
		String totalValue =TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println("the total value page is ------>" +totalValue);
		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		//**************************************************************************************************************//
		
		
		//get value from JSON Array
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String last_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		
		 System.out.println(first_name);
		 System.out.println(id);
		 System.out.println(last_name);
		 System.out.println(avatar);
		
		//headers
		Header[] allHeaders = httpResponse.getAllHeaders();
		
		HashMap<String, String> headerAllMap = new HashMap<String, String>();
			
		for(Header eachHeader: allHeaders )	{
			headerAllMap.put(eachHeader.getName(), eachHeader.getValue());
		}
		

		
		//**************************************************************************************************************//
		//header value assertion
				//server
				header = "Server";
				String server =TestUtil.getHeaderValueJpath(headerAllMap, header);
				System.out.println("the header value page is ------>" +server);
				
				Assert.assertEquals(server, "cloudflare");
		//**************************************************************************************************************//	
		//header value assertion
		//cf_request_id
				header = "cf-request-id";
				String cf_request_id =TestUtil.getHeaderValueJpath(headerAllMap, header);
				System.out.println("the header value page is ------>" +cf_request_id);
		
		//Assert.assertEquals(cf_request_id, "cloudflare");
		//**************************************************************************************************************//
		
		
		System.out.println("header------>"+headerAllMap);
		
			
	}
	
	
}
