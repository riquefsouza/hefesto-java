package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import br.com.hfs.admin.controller.dto.AdmMenuDTO;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmMenuTests extends BaseTests {

	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"description\": \"OLD_MENU\","
					+ "\"order\": 1,"
					+ "\"idPage\": 1,"
					+ "\"idMenuParent\": 1}")
		.when()
			.post("admMenu");
		
		AdmMenuDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_MENU", dto.getDescription());
		assertEquals(uriBase + "admMenu/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmMenuDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"description\": \"NEW_MENU\","
					+ "\"order\": 1,"
					+ "\"idPage\": 1,"
					+ "\"idMenuParent\": 1}")
		.when()
			.put("admMenu/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		assertEquals("NEW_MENU", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admMenu/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
	@Test
	@Order(4)
	void findAllTest() {
		AdmMenuDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admMenu")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO[].class);
		
		assertEquals(6, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmMenuDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admMenu/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		assertEquals("Category of Configuration Parameters", dto.getDescription());
		assertThat("Category of Configuration Parameters").isEqualTo(dto.getDescription());
	}
	
}
