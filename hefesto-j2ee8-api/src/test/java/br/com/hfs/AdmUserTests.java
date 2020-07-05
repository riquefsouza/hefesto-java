package br.com.hfs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmUserTests extends BaseTests {

	@Test
	@Order(1)
	void findAllTest() {
		AdmUserDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admUser")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO[].class);
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(2)
	public void findByIdTest() {
		AdmUserDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admUser/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("USER", dto.getLogin());
		assertThat("USER").isEqualTo(dto.getLogin());
	}

	@Test
	@Order(3)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"OLD_USER\","
					+ "\"general\": false,"
					+ "\"admUsers\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admUser");
		
		AdmUserDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		String slocation = r.getHeader("Location");
		
		assertEquals("OLD_USER", dto.getLogin());
		assertEquals(uriBase + "admUser/3", slocation);
	}
	
	@Test
	@Order(4)
	public void updateTest() {
		AdmUserDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_USER\","
					+ "\"general\": false,"
					+ "\"admUsers\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admUser/3")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("NEW_USER", dto.getLogin());
	}
	
	@Test
	@Order(5)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admUser/3")
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
}
