package br.com.hfs;

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
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(2)
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
					+ "\"description\": \"OLD_PARAMETER_CATEGORY\","
					+ "\"general\": false,"
					+ "\"admParameterCategorys\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admParameterCategory");
		
		AdmParameterCategoryDTO dto = r.then()
			.statusCode(Status.CREATED.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		String slocation = r.getHeader("Location");
		
		assertEquals("OLD_PARAMETER_CATEGORY", dto.getDescription());
		assertEquals(uriBase + "admParameterCategory/3", slocation);
	}
	
	@Test
	@Order(4)
	public void updateTest() {
		AdmParameterCategoryDTO dto =
		given()
			.spec(spec)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_PARAMETER_CATEGORY\","
					+ "\"general\": false,"
					+ "\"admParameterCategorys\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admParameterCategory/3")
		.then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		assertEquals("NEW_PARAMETER_CATEGORY", dto.getDescription());
	}
	
	@Test
	@Order(5)
	public void deleteTest() {
		given()
			.spec(spec)
		.when()
			.delete("admParameterCategory/3")
		.then()
			.statusCode(Status.OK.getStatusCode());
	}
	
}
