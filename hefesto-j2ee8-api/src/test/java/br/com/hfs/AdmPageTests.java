package br.com.hfs;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmPageDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmPageTests extends BaseTests {

	@Test
	@Order(1)
	void findAllTest() {
		AdmPageDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admPage")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO[].class);
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(2)
	public void findByIdTest() {
		AdmPageDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admPage/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
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
					+ "\"description\": \"OLD_PAGE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admPage");
		
		AdmPageDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		String slocation = r.getHeader("Location");
		
		assertEquals("OLD_PAGE", dto.getDescription());
		assertEquals(uriBase + "admPage/3", slocation);
	}
	
	@Test
	@Order(4)
	public void updateTest() {
		AdmPageDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_PAGE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admPage/3")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		assertEquals("NEW_PAGE", dto.getDescription());
	}
	
	@Test
	@Order(5)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admPage/3")
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
}
