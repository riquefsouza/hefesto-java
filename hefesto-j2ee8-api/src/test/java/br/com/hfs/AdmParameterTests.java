package br.com.hfs;

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
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(2)
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
		
		assertEquals("USER", dto.getDescription());
		assertThat("USER").isEqualTo(dto.getDescription());
	}

	@Test
	@Order(3)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"OLD_PARAMETER\","
					+ "\"general\": false,"
					+ "\"admParameters\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admParameter");
		
		AdmParameterDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		String slocation = r.getHeader("Location");
		
		assertEquals("OLD_PARAMETER", dto.getDescription());
		assertEquals(uriBase + "admParameter/3", slocation);
	}
	
	@Test
	@Order(4)
	public void updateTest() {
		AdmParameterDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_PARAMETER\","
					+ "\"general\": false,"
					+ "\"admParameters\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admParameter/3")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		assertEquals("NEW_PARAMETER", dto.getDescription());
	}
	
	@Test
	@Order(5)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admParameter/3")
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
}
