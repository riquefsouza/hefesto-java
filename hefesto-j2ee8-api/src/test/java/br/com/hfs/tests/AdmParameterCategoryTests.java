package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.hfs.controller.dto.AdmParameterCategoryDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmParameterCategoryTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"order\": 6,"
					+ "\"description\": \"OLD_PARAMETER_CATEGORY\"}")
		.when()
			.post("admParameterCategory");
		
		AdmParameterCategoryDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PARAMETER_CATEGORY", dto.getDescription());
		assertEquals(uriBase + "admParameterCategory/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmParameterCategoryDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"order\": 6,"
					+ "\"description\": \"NEW_PARAMETER_CATEGORY\"}")
		.when()
			.put("admParameterCategory/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		assertEquals("NEW_PARAMETER_CATEGORY", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admParameterCategory/" + id)
		.then()
			.statusCode(Status.OK.getStatusCode());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmParameterCategoryDTO[] dtos =
		given()
			.spec(spec)
		.when()
			.get("admParameterCategory")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO[].class);
		
		assertEquals(5, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmParameterCategoryDTO dto = 
		given()
			.spec(spec)
		.when()
			.get("admParameterCategory/2")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		assertEquals("Login Parameters", dto.getDescription());
		assertThat("Login Parameters").isEqualTo(dto.getDescription());
	}
	
}
