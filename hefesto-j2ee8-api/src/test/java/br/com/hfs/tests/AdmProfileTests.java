package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmProfileDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmProfileTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"OLD_PROFILE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admProfile");
		
		AdmProfileDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PROFILE", dto.getDescription());
		assertEquals(uriBase + "admProfile/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmProfileDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_PROFILE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admProfile/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO.class);
		
		assertEquals("NEW_PROFILE", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admProfile/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmProfileDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admProfile")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO[].class);
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmProfileDTO dto = 
		given()
			.spec(spec)
			//.relaxedHTTPSValidation()
			//.header("Accept", "application/json")
			//.contentType(ContentType.JSON)
			//.param("foo1", "bar1")
			//.param("foo2", "bar2")
		.when()
			.get("admProfile/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			//.body("headers.host", is("postman-echo.com"))
			//.body("description", containsString("[ADMIN, USER]"));
			.extract().as(AdmProfileDTO.class);
		
		assertEquals("USER", dto.getDescription());
		assertThat("USER").isEqualTo(dto.getDescription());
	}
	
}
