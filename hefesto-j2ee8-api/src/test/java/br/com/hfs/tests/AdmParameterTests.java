package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmParameterDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmParameterTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"code\": \"OLD_CODE\","
					+ "\"description\": \"OLD_PARAMETER\","
					+ "\"value\": \"OLD_VALUE\","
					+ "\"admParameterCategory\": {\"id\": 1,\"description\": \"Court Parameters\",\"order\": 1}}")
		.when()
			.post("admParameter");
		
		AdmParameterDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PARAMETER", dto.getDescription());
		assertEquals(uriBase + "admParameter/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmParameterDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"code\": \"NEW_CODE\","
					+ "\"description\": \"NEW_PARAMETER\","
					+ "\"value\": \"NEW_VALUE\","
					+ "\"admParameterCategory\": {\"id\": 1,\"description\": \"Court Parameters\",\"order\": 1}}")
		.when()
			.put("admParameter/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		assertEquals("NEW_PARAMETER", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admParameter/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmParameterDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admParameter")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO[].class);
		
		assertEquals(15, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmParameterDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admParameter/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		assertEquals("TRT1", dto.getValue());
		assertThat("TRT1").isEqualTo(dto.getValue());
	}
			
}
