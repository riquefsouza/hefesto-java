package br.com.hfs.tests;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTests {
    
	protected static final String AUTHORIZATION = "Authorization";
	
	private static final String BEARER = "Bearer ";
	
	private static final String uriSystem = "http://localhost:8084/";
	
	protected static RequestSpecification spec;
	
	protected static final String uriBase = uriSystem + "api/v1/";
	
	protected static Long id;

	protected static String accessToken;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		accessToken = BEARER + obtainAccessToken("admin", "123456");
		
		spec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri(uriBase)
				.addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter())
				.build();
	}
	
    protected static String obtainAccessToken(String username, String password) {
    	RequestSpecification specAuth = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri(uriSystem)
				.addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter())
				.build();
		
		final Response response = 
		given()
			.spec(specAuth)
			.accept(ContentType.JSON)
			.body("{\"login\": \"" + username + "\","
					+ "\"password\": \"" + password + "\"}")
		.when()
			.post("auth");

        return response.jsonPath().getString("token");
        
    }
}
