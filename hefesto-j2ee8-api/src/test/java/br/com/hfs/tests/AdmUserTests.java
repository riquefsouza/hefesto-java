package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import br.com.hfs.admin.controller.dto.AdmUserDTO;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmUserTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"login\": \"OLD_USER\","
					+ "\"password\": \"OLD_PASSWORD\","
					+ "\"name\": \"OLD_NAME\","
					+ "\"email\": \"old_mail@gmail.com\"}")
		.when()
			.post("admUser");
		
		AdmUserDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_USER", dto.getLogin());
		assertEquals(uriBase + "admUser/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmUserDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"login\": \"NEW_USER\","
					+ "\"password\": \"NEW_PASSWORD\","
					+ "\"name\": \"NEW_NAME\","
					+ "\"email\": \"new_mail@gmail.com\"}")
		.when()
			.put("admUser/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("NEW_USER", dto.getLogin());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admUser/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
	@Test
	@Order(4)
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
		
		assertEquals(1, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmUserDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admUser/1")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("admin", dto.getLogin());
		assertThat("admin").isEqualTo(dto.getLogin());
	}
}
