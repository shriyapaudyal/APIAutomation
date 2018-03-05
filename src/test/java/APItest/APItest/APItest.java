package APItest.APItest;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.json.*;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APItest {
	String token;

	@BeforeTest
	public String TokenGenerate() {
		// Read JSON Response Body using Rest-Assured
		RestAssured.baseURI = "http://172.18.11.116:8082/api/v1/app";
		RequestSpecification request = RestAssured.given();
		// to post json
		request.header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject();
		requestParams.put("UserName", "first@yopmail.com"); // Cast
		requestParams.put("Password", "brain123");
		requestParams.put("DeviceId", "");
		requestParams.put("PushToken", "");
		request.body(requestParams.toJSONString());
		Response response = request.post("/login");
		String responseBody = response.getBody().asString();
		// to pharse string 
		Pattern pattern = Pattern.compile("((\"t)\\w+(.*?),)");
		Matcher matcher = pattern.matcher(responseBody);
		String here;
		if (matcher.find()) {
			here = (matcher.group(1));
			// System.out.println(here);
			String splittertok = here.split("\"")[3];
			token = splittertok;
		}
		return token;
	}

	@Test
	public void LoginAPI() {

		// Read JSON Response Body using Rest-Assured
		RestAssured.baseURI = "http://172.18.11.116:8082/api/v1/app";
		RequestSpecification request = RestAssured.given();
		// to post json
		request.header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject();
		requestParams.put("UserName", "first@yopmail.com"); // Cast
		requestParams.put("Password", "brain123");
		requestParams.put("DeviceId", "");
		requestParams.put("PushToken", "");
		request.body(requestParams.toJSONString());
		Response response = request.post("/login");
		String responseBody = response.getBody().asString();
		// to validiate response code
		JsonPath jsonPathEvaluator = response.jsonPath();
		int Statuscode = jsonPathEvaluator.get("code");
		// System.out.println("status code:" + Statuscode);
		Assert.assertEquals(Statuscode, 200, "API is working,Verified");
	}

	@Test
	public void savemessage() {
		TokenGenerate();
		RestAssured.baseURI = "http://172.18.11.116:8082/api/v1/app";
		RequestSpecification request = RestAssured.given();
		// multiple header
		request.header("Authorization", "bearer " + token);
		request.header("Content-Type", "application/json");
		JSONObject requestParams = new JSONObject();
		requestParams.put("InquiryId", null); // Cast
		requestParams.put("CommonId", "18");
		requestParams.put("GlobalMessageId", "");
		requestParams.put("UserEmail", "saroj.poudel@braindigit.com");
		requestParams.put("AgentEmail", "");
		requestParams.put("MessageCategory", "general");
		requestParams.put("Message", "try message");
		requestParams.put("PostedDate", "2017-09-06T11:56:33.2543488+05:45");
		requestParams.put("AgentId", "18");
		request.body(requestParams.toJSONString());
		Response response = request.post("/inquiry/savemessage");
		String responseBody = response.getBody().asString();
		JsonPath jsonPathEvaluator = response.jsonPath();
		int Statuscode = jsonPathEvaluator.get("code");
		Assert.assertEquals(Statuscode, 200, "API is working,Verified");
	}
	

}
