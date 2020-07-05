package br.com.hfs.tests;

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
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"url\": \"admin/admTest/listTest.xhtml\","
					+ "\"description\": \"OLD_PAGE\"}")
		.when()
			.post("admPage");
		
		AdmPageDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PAGE", dto.getDescription());
		assertEquals(uriBase + "admPage/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmPageDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"url\": \"admin/admTest/listTest.xhtml\","
					+ "\"description\": \"NEW_PAGE\"}")
		.when()
			.put("admPage/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		assertEquals("NEW_PAGE", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admPage/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
	@Test
	@Order(4)
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
		
		assertEquals(11, dtos.length);
	}

	@Test
	@Order(5)
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
		
		assertEquals("admin/admParameterCategory/editAdmParameterCategory.xhtml", dto.getUrl());
		assertThat("admin/admParameterCategory/editAdmParameterCategory.xhtml").isEqualTo(dto.getUrl());
	}
}
