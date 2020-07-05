package br.com.hfs;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseTests {

	protected static RequestSpecification spec;
	
	protected static String uriBase = "http://localhost:8080/api/v1/";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		spec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri(uriBase)
				.addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter())
				.build();
	}
}
