package br.com.hfs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmMenuDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmMenuTests extends BaseTests {

	@Test
	@Order(1)
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
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(2)
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
					+ "\"description\": \"OLD_MENU\","
					+ "\"general\": false,"
					+ "\"admMenus\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admMenu");
		
		AdmMenuDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		String slocation = r.getHeader("Location");
		
		assertEquals("OLD_MENU", dto.getDescription());
		assertEquals(uriBase + "admMenu/3", slocation);
	}
	
	@Test
	@Order(4)
	public void updateTest() {
		AdmMenuDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_MENU\","
					+ "\"general\": false,"
					+ "\"admMenus\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admMenu/3")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		assertEquals("NEW_MENU", dto.getDescription());
	}
	
	@Test
	@Order(5)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admMenu/3")
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
}
